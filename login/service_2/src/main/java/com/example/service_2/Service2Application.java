package com.example.service_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@EnableDiscoveryClient
@RestController
@SpringBootApplication
public class Service2Application {

    public static void main(String[] args) {
        SpringApplication.run(Service2Application.class, args);
    }


    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request,String param){

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()){
            String s = headerNames.nextElement();

            System.out.println("header:"+s+"  value:"+request.getHeader(s));
        }

        return param;
    }

}
