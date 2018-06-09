package com.jarry.vote.mapper;

import com.jarry.vote.model.response.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 * User: Jarry
 * Date: 2018-01-28
 * Time: 22:04
 */
@Mapper
public interface UserInfoMapper {
    List<UserInfo> login(@Param("userName") String userName, @Param("password") String password);

    List<UserInfo> checkAccountByUserName(@Param("userName") String userName);

    int register(@Param("userInfo") UserInfo userInfo);

    List<UserInfo> getUserInfoById(@Param("userId") String userId);

    List<UserInfo> getUserInfoByPhone(@Param("phone") String phone);
}
