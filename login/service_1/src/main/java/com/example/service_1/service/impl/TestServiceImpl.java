package com.example.service_1.service.impl;

import com.example.service_1.Dao.TestDao;
import com.example.service_1.entity.Test;
import com.example.service_1.service.FeignService;
import com.example.service_1.service.TestService;
import io.seata.rm.datasource.exec.ExecuteTemplate;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author hqc
 * @date 2020/4/1 10:53
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;
    @Resource
    private FeignService feignService;

    @Override
    @GlobalTransactional(rollbackFor=Exception.class)
    public void saveTest(Test test) {
        testDao.save(test);

        feignService.getByFeign("123");
    }

    /**{@link ExecuteTemplate}
     *
     * 未配置GlobalTransactional时使用本身的事务，需要根据自己的配置来
     * @param test
     */
    @Override
    @Transactional
    public void saveLocalManager(Test test) {
        testDao.save(test);

        throw new RuntimeException("123");
    }


}
