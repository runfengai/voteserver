package com.jarry.vote.service;

import com.jarry.vote.model.MessageData;
import com.jarry.vote.model.response.UserInfo;

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
