package com.jarry.chat.controller;

import com.jarry.chat.model.MessageData;
import com.jarry.chat.model.request.LoginParam;
import com.jarry.chat.model.response.UserInfo;
import com.jarry.chat.service.UserService;
import com.jarry.chat.util.Constant;
import com.jarry.chat.util.ErrorMap;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 用户登录
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userInfoService;

    @RequestMapping(value = "/login")
    MessageData login(LoginParam param) {
        logger.info("login----->", param);
        if (param == null) return new MessageData(Constant.MSG_PARAM_NULL, Constant.CODE_PARAM_NULL);
        else if (StringUtils.isNullOrEmpty(param.getUserName())) {
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_REGISTER_USER_NAME_NULL), Constant.CODE_REGISTER_USER_NAME_NULL);
        } else if (StringUtils.isNullOrEmpty(param.getPassword())) {
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_LOGIN_ERROR_PWD_NULL), Constant.CODE_LOGIN_ERROR_PWD_NULL);
        }
        return userInfoService.login(param.getUserName(), param.getPassword());
    }

    @RequestMapping(value = "/register")
    MessageData register(UserInfo userInfo) {
        logger.info("register----->", userInfo);
        //数据校验
        if (userInfo == null) return new MessageData(Constant.MSG_PARAM_NULL, Constant.CODE_PARAM_NULL);
        else if (StringUtils.isNullOrEmpty(userInfo.getUserName())) {
            return new MessageData(Constant.MSG_REGISTER_USER_NAME_NULL, Constant.CODE_REGISTER_USER_NAME_NULL);
        } else if (StringUtils.isNullOrEmpty(userInfo.getPassword())) {
            return new MessageData(Constant.MSG_REGISTER_PWD_NULL, Constant.CODE_REGISTER_PWD_NULL);
        } else if (userInfo.getPassword().length() < 6) {
            return new MessageData(Constant.MSG_REGISTER_PWD_6, Constant.CODE_REGISTER_PWD_6);
        } else if (StringUtils.isNullOrEmpty(userInfo.getSchool())) {
            return new MessageData(Constant.MSG_REGISTER_SCHOOL_NULL, Constant.CODE_REGISTER_SCHOOL_NULL);
        } else if (0 != userInfo.getType()) {
            return new MessageData(Constant.MSG_REGISTER_REFUSED, Constant.CODE_REGISTER_REFUSED);
        }
        //生成userid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        userInfo.setUserId(uuid);
        return userInfoService.register(userInfo);
    }

    @RequestMapping(value = "/info")
    MessageData userInfo(UserInfo param) {
        logger.info("userInfo----->", param);
        if (param == null) return new MessageData(Constant.MSG_PARAM_NULL, Constant.CODE_PARAM_NULL);
        String userId = param.getUserId();
        String phone = param.getPhone();

        if (!StringUtils.isNullOrEmpty(userId)) {//根据userId查询用户
            return userInfoService.userInfoById(userId);
        }
        if (!StringUtils.isNullOrEmpty(phone)) {//根据手机号查询用户
            return userInfoService.userInfoByPhone(phone);
        }
        return new MessageData(ErrorMap.getErrorStr(Constant.CODE_USER_INFO_NULL), Constant.CODE_USER_INFO_NULL);
    }
}
