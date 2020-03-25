package com.microservice.login.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.login.auth.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author hqc
 * @date 2020/3/25 11:35
 */
public interface UserDao extends JpaRepository<User, String>,JpaSpecificationExecutor<User> {
}
