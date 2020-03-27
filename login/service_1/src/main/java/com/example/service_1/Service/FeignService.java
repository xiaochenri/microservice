package com.example.service_1.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service2", decode404 = true)
public interface FeignService {

    @GetMapping(value = "/test")
    String getByFeign(@RequestParam("param") String test);
}
