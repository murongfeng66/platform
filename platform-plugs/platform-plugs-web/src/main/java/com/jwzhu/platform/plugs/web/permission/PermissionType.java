package com.jwzhu.platform.plugs.web.permission;

import javax.servlet.http.HttpServletRequest;

import com.jwzhu.platform.common.enums.AdminType;
import com.jwzhu.platform.common.exception.NoPermissionException;
import com.jwzhu.platform.common.web.RequestInfo;
import com.jwzhu.platform.common.web.RequestUtil;
import com.jwzhu.platform.permission.PermissionService;
import com.jwzhu.platform.plugs.web.ApplicationContextUtil;

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
     * 仅登录即可
     */
    Only_Login{
        @Override
        public void check() {
            if(RequestInfo.getRequestUser() == null){
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

            HttpServletRequest request = RequestUtil.getRequest();

            PermissionService permissionService = ApplicationContextUtil.getBean(PermissionService.class);
            if(permissionService.checkNoPermission(request.getRequestURI())){
                if(permissionService.checkNoPermission(request.getRequestURL().toString())){
                    throw new NoPermissionException();
                }
            }
        }
    },
    /**
     * 超级管理员
     */
    SupperAdmin{
        @Override
        public void check() {
            if(RequestInfo.getRequestUser().getType() != AdminType.Super.getCode()){
                throw new NoPermissionException();
            }
        }
    };

    public abstract void check();

}
