package com.example.service_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 对于后续服务我认为可以通过服务器端口屏蔽的方式来避免直访服务，服务本身没必要做更多的权限验证
 *
 * 充分发挥微服务的理念，做到任务分类清晰化
 */
@EnableDiscoveryClient
@RestController
@SpringBootApplication
public class Service1Application {

    public static void main(String[] args) {
        SpringApplication.run(Service1Application.class, args);
    }

    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request){

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()){
            String s = headerNames.nextElement();

            System.out.println("header:"+s+"  value:"+request.getHeader(s));
        }

        return "成功访问";
    }
}
