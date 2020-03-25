package com.microservice.login.auth.service;

import com.microservice.login.auth.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author hqc
 * @date 2020/3/25 11:35
 */
public interface UserService extends UserDetailsService {

    User findByNameAndPwd(String userName,String pwd);

    User loadUserByUsername(String userName);

}
