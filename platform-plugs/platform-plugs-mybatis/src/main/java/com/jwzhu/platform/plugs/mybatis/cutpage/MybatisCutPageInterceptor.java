package com.jwzhu.platform.plugs.mybatis.cutpage;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import com.jwzhu.platform.common.bean.PageBean;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.util.ReflectUtil;

@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisCutPageInterceptor implements Interceptor {

    private enum DataBaseType {
        ORACLE {
            @Override
            public String getLimitSql(String sql, PageBean<?> pageBean) {
                StringBuilder pageSql = new StringBuilder(100);
                int beginIndex = (pageBean.getCurrentPage() - 1) * pageBean.getPageSize();
                int endIndex = pageBean.getCurrentPage() * pageBean.getPageSize();
                pageSql.append("select * from (select temp.*, rownum row_id from ( ");
                pageSql.append(sql);
                pageSql.append(" ) temp where rownum <= ").append(endIndex);
                pageSql.append(") where row_id > ").append(beginIndex);
                return pageSql.toString();
            }
        },
        MYSQL {
            @Override
            public String getLimitSql(String sql, PageBean<?> pageBean) {
                StringBuilder pageSql = new StringBuilder(100);
                int beginIndex = (pageBean.getCurrentPage() - 1) * pageBean.getPageSize();
                pageSql.append(sql);
                pageSql.append(" limit ").append(beginIndex).append(",").append(pageBean.getPageSize());
                return pageSql.toString();
            }
        };

        /**
         * 初始化数据库类型
         */
        public static DataBaseType initDataBaseType(String dataBaseType) {
            if ("ORACLE".equalsIgnoreCase(dataBaseType)) {
                return ORACLE;
            } else if ("MYSQL".equalsIgnoreCase(dataBaseType)) {
                return MYSQL;
            } else {
                throw new SystemException("不被支持的数据库类型");
            }
        }

        /**
         * 获取切分语句
         */
        public abstract String getLimitSql(String sql, PageBean<?> pageBean);
    }

    private DataBaseType dataBaseType;

    private String countSQLSuffix;

    private HashSet<String> sqlNames = new HashSet<>();

    /**
     * 进行切分
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private Object doCutPage(Invocation invocation, PageBean pageBean) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
//        ReflectUtil.setFieldValue(mappedStatement.getParameterMap(), "parameterMappings", new ArrayList<>());
        BoundSql boundSql = mappedStatement.getBoundSql(pageBean);

//        List parameterMappings = (List) ReflectUtil.getFieldValue(boundSql, "parameterMappings");
//        ReflectUtil.setFieldValue(mappedStatement.getParameterMap(), "parameterMappings", parameterMappings);

        Configuration configuration = mappedStatement.getConfiguration();
        if (sqlNames.isEmpty()) {
            this.initStatementMap(configuration);
        }

        if (pageBean.needCut()) {
            pageBean.setTotalCount(this.getCount(invocation, configuration, mappedStatement, boundSql, pageBean));

            if ((pageBean.getTotalCount() == 0 || pageBean.getCurrentPage() > pageBean.getTotalPage())) {
                pageBean.setList(new ArrayList<>());
                return new ArrayList<>();
            }

            String limitSql = getLimitSql(boundSql, pageBean);
            BoundSql dataBoundSql = new BoundSql(configuration, limitSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            Object additionalParameters = ReflectUtil.getFieldValue(boundSql, "additionalParameters");
            ReflectUtil.setFieldValue(dataBoundSql, "additionalParameters", additionalParameters);
            Object metaParameters = ReflectUtil.getFieldValue(boundSql, "metaParameters");
            ReflectUtil.setFieldValue(dataBoundSql, "metaParameters", metaParameters);

            MappedStatement newMappedStatement = copyFromMappedStatement(mappedStatement, new PageSqlSource(dataBoundSql));

            invocation.getArgs()[0] = newMappedStatement;
        }

        Object object = invocation.proceed();
        if (object instanceof List) {
            List<?> data = (List<?>) object;
            pageBean.setList(data);
        }
        if (!pageBean.needCut() && pageBean.getList() != null) {
            pageBean.setTotalCount(pageBean.getList().size());
        }
        return object;
    }

    public static class PageSqlSource implements SqlSource{
        private BoundSql boundSql;

        PageSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return this.boundSql;
        }
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(),ms.getId(),newSqlSource,ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        if(ms.getKeyProperties() != null && ms.getKeyProperties().length !=0){
            StringBuilder keyProperties = new StringBuilder();
            for(String keyProperty : ms.getKeyProperties()){
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length()-1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }

        return builder.build();
    }

    /**
     * 执行统计SQL
     */
    private int exeCountSql(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Connection connection, Object parameter) {
        int totalSize = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(this.getCountSql(boundSql.getSql()));
            ParameterHandler handler = configuration.newParameterHandler(mappedStatement, parameter, boundSql);
            handler.setParameters(stmt);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalSize = rs.getInt(1);
            }
        } catch (Exception e) {
            throw new SystemException("执行统计SQL失败", e);
        }
        return totalSize;
    }

    /**
     * 获取数据库连接
     */
    private Connection getConnection(Transaction transaction, Log statementLog) throws SQLException {
        Connection connection = transaction.getConnection();
        if (statementLog.isDebugEnabled()) {
            return ConnectionLogger.newInstance(connection, statementLog, 1);
        } else {
            return connection;
        }
    }

    /**
     * 获取结果总数
     */
    private int getCount(Invocation invocation, Configuration configuration, MappedStatement statement, BoundSql boundSql, Object parameter) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        int count = 0;
        String countSqlId;
        if (statement.getId().endsWith(countSQLSuffix)) {
            countSqlId = statement.getId();
        } else {
            countSqlId = statement.getId() + countSQLSuffix;
        }
        if (sqlNames.contains(countSqlId)) {
            invocation.getArgs()[0] = statement;
            List<?> data = (List<?>) invocation.proceed();
            if (data.size() > 0) {
                count = Integer.parseInt(data.get(0).toString());
            }
        } else {
            Executor ext = (Executor) invocation.getTarget();
            Connection conn = getConnection(ext.getTransaction(), statement.getStatementLog());
            count = exeCountSql(configuration, statement, boundSql, conn, parameter);
        }
        return count;
    }

    /**
     * 获取统计语句
     */
    private String getCountSql(String sql) {
        return "select count(1) from (" + sql + ") T";
    }

    /**
     * 获取切分SQL
     */
    private String getLimitSql(BoundSql boundSql, PageBean<?> pageBean) {
        if (!pageBean.needCut()) {
            return boundSql.getSql();
        }
        return dataBaseType.getLimitSql(boundSql.getSql(), pageBean);
    }

    /**
     * 获取分页参数
     */
    private PageBean<?> getPageBean(Object[] parameter) {
        for (Object obj : parameter) {
            if (obj instanceof PageBean) {
                return (PageBean<?>) obj;
            }
        }
        return null;
    }

    /**
     * 初始化SQL语句
     */
    private synchronized void initStatementMap(Configuration configuration) {
        if (!sqlNames.isEmpty()) {
            return;
        }
        Collection<String> statements = configuration.getMappedStatementNames();
        sqlNames.addAll(statements);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] parameter = invocation.getArgs();
        PageBean<?> page = getPageBean(parameter);
        if (page == null) {
            return invocation.proceed();
        } else {
            return this.doCutPage(invocation, page);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String dataBaseType = properties.getProperty("dataBaseType");
        this.dataBaseType = DataBaseType.initDataBaseType(dataBaseType);
        this.countSQLSuffix = properties.getProperty("countSQLSuffix");
        this.countSQLSuffix = this.countSQLSuffix == null ? "Count" : this.countSQLSuffix;
    }
}
