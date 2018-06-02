package com.jarry.chat.service;

import com.jarry.chat.model.MessageData;
import com.jarry.chat.model.response.UserInfo;

import java.util.List;

/**
 * Description:
 * User: Jarry
 * Date: 2018-03-04
 * Time: 17:10
 */
public interface UserService {
    MessageData login(String userName, String password);

    MessageData register(UserInfo userInfo);

    MessageData userInfoById(String userId);

    MessageData userInfoByPhone(String phone);
}
