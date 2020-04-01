package com.example.service_1.Dao;

import com.example.service_1.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hqc
 * @date 2020/4/1 10:51
 */
public interface TestDao extends JpaRepository<Test,String> {

}
