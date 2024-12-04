package com.cybersoft.demospringsecurity.config;

import com.cybersoft.demospringsecurity.modal.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataUserConfig {

    @Value("${cybersoft.username}")
    private String username;

    @Value("${cybersoft.password}")
    private String password;


    @Bean("a")
//    @Primary
    public User createUser(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }

    @Bean("b")
    public User createUser1(){
        User user = new User();
        user.setUsername("user1");
        user.setPassword("123");

        return user;
    }
}
