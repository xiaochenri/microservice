package com.microservice.login.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hqc
 * @date 2020/3/25 11:22
 */
@Slf4j
public class OauthLogoutHandler implements LogoutHandler {

    @Autowired
    private TokenStore tokenStore;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Assert.notNull(tokenStore,"tokenStore is not be null");

        //此处需定义如何获取前端传递的token值
        String token = "";

        //移除token
        if(StringUtils.isNoneBlank(token)){
            OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token);
            OAuth2RefreshToken refreshToken;
            if (existingAccessToken != null) {
                if (existingAccessToken.getRefreshToken() != null) {
                    log.info("remove refreshToken!", existingAccessToken.getRefreshToken());
                    refreshToken = existingAccessToken.getRefreshToken();
                    tokenStore.removeRefreshToken(refreshToken);
                }
                log.info("remove existingAccessToken!", existingAccessToken);
                tokenStore.removeAccessToken(existingAccessToken);
            }
        }

    }
}
