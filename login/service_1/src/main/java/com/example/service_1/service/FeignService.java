package com.example.service_1.service;

import feign.ReflectiveFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * {@link ReflectiveFeign newInstance构建代理对象}
 */
@FeignClient(name = "service2", decode404 = true)
public interface FeignService {

    @GetMapping(value = "/test")
    String getByFeign(@RequestParam("param") String test);
}
