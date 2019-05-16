# 项目介绍
    本项目基于Spring Boot2 Maven构建的Web后台项目基础平台，提供Web后台项目基础功能，基础平台独立部署也可继续在此项目上开发。各业务系统接入平台，无需实现业务无关的基础功能
    已实现：用户管理、权限管理模块。
    规划中：文件系统、任务调度系统等
    
# 后台结构
       .
       ├── platform-common
       ├── platform-core
       ├── platform-permission
       └── platform-plugs
           ├── platform-plugs-cache
           │   ├── platform-plugs-cache-base
           │   └── platform-plugs-cache-redis
           ├── platform-plugs-jsonescape
           │   ├── platform-plugs-jsonescape-base
           │   ├── platform-plugs-jsonescape-bind
           │   └── platform-plugs-jsonescape-jackson
           ├── platform-plugs-mybatis
           └── platform-plugs-web
           
     platform-common：框架提供和使用的基本Bean参数、枚举、异常、工具类
     platform-core：基础平台核心项目，目前包括系统用户、权限（角色、资源）
     platform-permission：接口权限远程验证实现包（尚未实现）
     platform-plugs-cache-base：缓存基础包
     platform-plugs-cache-redis：缓存Redis实现包
     platform-plugs-jsonescape-base：JSON序列化转义基础包，包含JSON缓存基础实现、序列化器扫描注册
     platform-plugs-jsonescape-bind：JSON序列化转义数据绑定包。包含Boolean、Integer、Long、Short、String绑定注解，转义器接口、转义器注解
     platform-plugs-jsonescape-jackson：JSON序列化转义Jackson实现
     platform-plugs-mybatis：MyBatis组件包，包含自动分页插件、MyBatis配置文件
     platform-plugs-web：Web项目基础组件包，包含异常处理、访问凭证、请求基础参数、响应结果封装、接口权限验证
