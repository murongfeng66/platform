package com.jwzhu.platform.plugs.web.permission;

import javax.servlet.http.HttpServletRequest;

import com.jwzhu.platform.common.enums.AdminType;
import com.jwzhu.platform.common.exception.NoPermissionException;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.plugs.web.ApplicationContextUtil;
import com.jwzhu.platform.plugs.web.request.RequestBaseParam;
import com.jwzhu.platform.plugs.web.request.RequestUtil;

public enum PermissionType {

    /**
     * 不验证权限
     */
    No{
        @Override
        public void check() {
        }
    },
    /**
     * 近登录即可
     */
    Only_Login{
        @Override
        public void check() {
            if(RequestBaseParam.getRequestUser() == null){
                throw new NoPermissionException();
            }
        }
    },
    /**
     * 验证权限
     */
    HavePermission{
        @Override
        public void check() {
            PermissionType.Only_Login.check();

            if(AdminType.Super.getCode() == RequestBaseParam.getRequestUser().getType()){
                return;
            }

            HttpServletRequest request = RequestUtil.getRequest();
            if(request == null){
                throw new SystemException("请求对象为空");
            }

            PermissionService permissionService = ApplicationContextUtil.getBean(PermissionService.class);
            if(!permissionService.checkPermission(request.getRequestURI())){
                throw new NoPermissionException();
            }
        }
    },
    /**
     * 超级管理员
     */
    SupperAdmin{
        @Override
        public void check() {
            if(RequestBaseParam.getRequestUser().getType() != AdminType.Super.getCode()){
                throw new NoPermissionException();
            }
        }
    };

    public abstract void check();

}
