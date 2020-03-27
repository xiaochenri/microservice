package com.microservice.login.auth.handler;

import com.microservice.login.auth.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link LogoutFilter}
 *
 * @author hqc
 * @date 2020/3/25 11:22
 */
@Slf4j
public class OauthLogoutHandler implements LogoutHandler {

    /**
     * @param request
     * @param response
     * @param authentication
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        TokenStore tokenStore = SpringUtil.getBean(TokenStore.class);
        Assert.notNull(tokenStore, "tokenStore is not be null");

        //此处需定义如何获取前端传递的token值
        String token = request.getParameter("token");

        //移除refreshToken、accessToken
        if (StringUtils.isNoneBlank(token)) {
            OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token);

            if (existingAccessToken != null) {
                if (existingAccessToken.getRefreshToken() != null) {
                    OAuth2RefreshToken refreshToken = existingAccessToken.getRefreshToken();
                    tokenStore.removeRefreshToken(refreshToken);
                }
                tokenStore.removeAccessToken(existingAccessToken);
            }
        }

    }
}
