package com.microservice.login.auth.service.impl;

import com.microservice.login.auth.dao.UserDao;
import com.microservice.login.auth.entity.User;
import com.microservice.login.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hqc
 * @date 2020/3/25 11:44
 */
@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByNameAndPwd(String userName, String pwd) {

        Specification<User> specification = ((root, query, criteriaBuilder) -> {

            List<Predicate> list = new ArrayList<>();

            list.add(criteriaBuilder.equal(root.get("userName"),userName));
            list.add(criteriaBuilder.equal(root.get("password"),pwd));

            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        });

        List<User> users = userDao.findAll(specification);

        if (users.size() == 1){
            return users.get(0);
        }

        return null;
    }

    @Override
    public User loadUserByUsername(String userName) {
        return null;
    }
}
