package com.jwzhu.platform.core;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jwzhu.platform.common.enums.AvailableStatus;
import com.jwzhu.platform.common.enums.YesOrNo;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.util.HttpUtil;
import com.jwzhu.platform.common.web.RequestInfo;
import com.jwzhu.platform.core.admin.manager.LoginManager;
import com.jwzhu.platform.core.permission.manager.ResourceManager;
import com.jwzhu.platform.core.permission.model.Menu;
import com.jwzhu.platform.permission.PermissionService;
import com.jwzhu.platform.plugs.cache.base.CacheUtil;
import com.jwzhu.platform.plugs.web.annotations.ControllerHandler;
import com.jwzhu.platform.plugs.web.permission.PermissionType;
import com.jwzhu.platform.plugs.web.response.ResponseCode;
import com.jwzhu.platform.plugs.web.response.WebResult;

@Controller
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    private ResourceManager resourceManager;
    @Autowired
    private LoginManager userManager;
    @Autowired
    private CacheUtil cacheUtil;

    @GetMapping({"", "login"})
    @ControllerHandler(permissionType = PermissionType.No)
    public ModelAndView login(ModelAndView view) {
        view.setViewName("login/login");
        return view;
    }

    @GetMapping("checkLogin")
    @ControllerHandler(permissionType = PermissionType.No)
    public ModelAndView checkLogin(ModelAndView view, HttpServletRequest request) {
        if (RequestInfo.getRequestUser() != null) {
            String subHost = request.getParameter("subHost");
            if (StringUtil.isEmpty(subHost)) {
                view.setViewName("redirect:/");
            } else {
                logger.info("{}初始化子系统登录信息", subHost);
                String originUrl = request.getParameter("originUrl");
                HttpUtil.RequestBean requestBean = new HttpUtil.RequestBean();
                requestBean.setUrl(subHost + "/subInitLoginInfo");
                requestBean.addHeader("x-requested-with", "XMLHttpRequest");
                requestBean.addHeader("Token", RequestInfo.getRequestToken());
                requestBean.addParam("resourceCodes", JSON.toJSONString(resourceManager.getPermissions(PermissionService.PermissionCacheType.Code)));
                requestBean.addParam("resourceUrls", JSON.toJSONString(resourceManager.getPermissions(PermissionService.PermissionCacheType.Url)));
                String resultStr = HttpUtil.doPost(requestBean);
                WebResult<String> result = JSON.parseObject(resultStr, new TypeReference<WebResult<String>>() {
                });
                if (result.getCode() != ResponseCode.SUCCESS.getCode()) {
                    throw new SystemException("初始化子系统登录信息失败");
                }

                cacheUtil.sAdd("SubSystem:" + RequestInfo.getRequestToken(), subHost);

                UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(subHost + "/setTokenToCookie");
                uriComponentsBuilder.queryParam("Token", RequestInfo.getRequestToken());
                uriComponentsBuilder.queryParam("originUrl", originUrl);
                view.setViewName("redirect:" + uriComponentsBuilder.toUriString());
            }
        } else {
            view.setViewName("login/noticeLogin");
        }

        return view;
    }

    @RequestMapping("main")
    @ControllerHandler(permissionType = PermissionType.Only_Login)
    public ModelAndView main(ModelAndView view) {
        view.setViewName("main");
        view.addObject("admin", userManager.getById(RequestInfo.getRequestUser().getId()));
        return view;
    }

    @GetMapping("queryMenu")
    @ResponseBody
    @ControllerHandler(permissionType = PermissionType.Only_Login)
    public List<Menu> queryMenu() {
        return resourceManager.queryMenu();
    }

    @GetMapping("yesOrNo")
    @ResponseBody
    @ControllerHandler(permissionType = PermissionType.Only_Login)
    public Map<Short, String> yesOrNo() {
        return YesOrNo.map;
    }

    @GetMapping("availableStatus")
    @ResponseBody
    @ControllerHandler(permissionType = PermissionType.Only_Login)
    public Map<Short, String> availableStatus() {
        return AvailableStatus.map;
    }

}
