package com.example.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 将网关配置为资源服务器，这样携带token的请求头过来时直接进行权限验证OAuth2AuthenticationProcessingFilter
 *
 * 此处 需要注意的是 配置远程调用验证/oauth/check_token 会传递应用的ID以及clientSecret到授权服务器进行数据库查询验证确保配置服务准确性
 *
 * 前端绑定token的传递形式 header头 Authorization：bearer822dd781-bdb6-4b35-934c-312b0d9c18b9
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration
        extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests()
                .anyRequest().authenticated();
        http.headers().frameOptions().disable();
        http.csrf().disable();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
            throws Exception {
        super.configure(resources);
    }

}
