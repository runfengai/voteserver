package com.jarry.vote;

import com.jarry.vote.mapper.UserInfoMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description:程序入口
 * User: Jarry
 * Date: 2018-01-28
 * Time: 17:35
 */
//@EnableAutoConfiguration
@SpringBootApplication
@MapperScan({"com.jarry.vote.mapper"})
public class VoteApplication implements CommandLineRunner {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public static void main(String[] args) {
        SpringApplication.run(VoteApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
//        UserInfo userInfo = userInfoMapper.login("18811730078", "123456");
//        System.out.print(userInfo.getUserName());
    }
}
