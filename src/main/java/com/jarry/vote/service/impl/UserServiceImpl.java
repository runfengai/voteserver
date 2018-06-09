package com.jarry.vote.service.impl;

import com.jarry.vote.mapper.UserInfoMapper;
import com.jarry.vote.model.MessageData;
import com.jarry.vote.model.response.UserInfo;
import com.jarry.vote.service.UserService;
import com.jarry.vote.util.Constant;
import com.mysql.jdbc.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: Jarry
 * Date: 2018-03-04
 * Time: 17:11
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public MessageData login(String userName, String password) {
        List<UserInfo> user = userInfoMapper.checkAccountByUserName(userName);
        if (user == null || user.size() == 0)
            return new MessageData(Constant.MSG_LOGIN_ERROR_USER_NULL, Constant.CODE_LOGIN_ERROR_USER_NULL, null);

        List<UserInfo> userInfo = userInfoMapper.login(userName, password);
        if (userInfo != null && userInfo.size() > 0) {
            Map<String, Object> res = new HashMap<>();
            res.put("userId", userInfo.get(0).getUserId());
            res.put("userName", userInfo.get(0).getUserName());
            res.put("school", userInfo.get(0).getSchool());
            res.put("type", userInfo.get(0).getType());
            return new MessageData(Constant.MSG_LOGIN_SUCCESS, Constant.CODE_SUCCESS, res);
        }
        return new MessageData(Constant.MSG_LOGIN_ERROR_USER, Constant.CODE_ERROR);


    }

    @Override
    public MessageData register(UserInfo userInfo) {
        List<UserInfo> user = userInfoMapper.checkAccountByUserName(userInfo.getUserName());
        if (user != null && user.size() > 0)
            return new MessageData(Constant.MSG_LOGIN_ERROR_USER_EXIST, Constant.CODE_LOGIN_ERROR_USER_EXIST, null);
        int result = userInfoMapper.register(userInfo);
        if (result > 0) {//成功
            Map<String, Object> res = new HashMap<>();
            res.put("userId", userInfo.getUserId());
            res.put("userName", userInfo.getUserName());
            res.put("school", userInfo.getSchool());
            res.put("type", userInfo.getType());
            return new MessageData("注册成功", Constant.CODE_SUCCESS, res);
        } else {
            return new MessageData(Constant.MSG_REGISTER_ERROR, Constant.CODE_REGISTER_ERROR, null);
        }
    }

    @Override
    public MessageData userInfoById(String userId) {
        List<UserInfo> users = userInfoMapper.getUserInfoById(userId);
        if (users == null || users.size() == 0)
            return new MessageData(Constant.CODE_USER_NOT_EXIST, Constant.CODE_USER_NOT_EXIST, users);
        UserInfo userInfo = users.get(0);
        return MessageData.createSuccessMsg(userInfo);
    }

    @Override
    public MessageData userInfoByPhone(String phone) {
        List<UserInfo> users = userInfoMapper.getUserInfoByPhone(phone);
        if (users == null || users.size() == 0)
            return new MessageData(Constant.CODE_USER_NOT_EXIST, Constant.CODE_USER_NOT_EXIST, users);
        UserInfo userInfo = users.get(0);
        return MessageData.createSuccessMsg(userInfo);
    }
}
