package com.microservice.login.auth.dao.impl;

import com.microservice.login.auth.dao.UserDao;
import com.microservice.login.auth.entity.User;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * @author hqc
 * @date 2020/3/25 11:37
 */
@Repository
public class UserDaoImpl extends SimpleJpaRepository<User,String> implements UserDao {

    public UserDaoImpl(EntityManager em) {
        super(User.class, em);
    }


}
