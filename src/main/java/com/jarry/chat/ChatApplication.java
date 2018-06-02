package com.jarry.chat;

import com.jarry.chat.mapper.UserInfoMapper;
import com.jarry.chat.model.response.UserInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.rmi.runtime.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Description:程序入口
 * User: Jarry
 * Date: 2018-01-28
 * Time: 17:35
 */
//@EnableAutoConfiguration
@SpringBootApplication
@MapperScan({"com.jarry.chat.mapper"})
public class ChatApplication implements CommandLineRunner {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
//        UserInfo userInfo = userInfoMapper.login("18811730078", "123456");
//        System.out.print(userInfo.getUserName());
    }
}
