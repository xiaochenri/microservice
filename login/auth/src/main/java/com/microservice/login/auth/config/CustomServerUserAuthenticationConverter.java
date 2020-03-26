package com.microservice.login.auth.config;

import com.microservice.login.auth.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义的 tokenconverter   使得 check_token 端点可以获取到用户的全部信息    默认只能获取到用户名
 */
public class CustomServerUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(USERNAME, authentication.getName());

        //自定义传递
        response.put("custom","custom");
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

}
