package com.microservice.login.auth.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author hqc
 * @date 2020/3/25 10:30
 * <p>
 * 不走/login 登录获取token，逻辑暂时移除，可在此处登录成功后关联oauth2得到token返回
 */
@Slf4j
@Configuration
public class SecurityHandlerConfig {

    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    /**
     * 登录成功
     *
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response, Authentication authentication)
                    throws IOException, ServletException {

            }
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {

        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request,
                                                HttpServletResponse response,
                                                AuthenticationException exception)
                    throws IOException, ServletException {
                String referer = request.getHeader("Referer");

                PrintWriter writer = response.getWriter();
                if (StringUtils.endsWithAny(referer, ".html", ".htm")) {
                    response.setContentType("text/html;charset=utf-8");
                    writer.write("<script type='text/javascript'> " +

                            "alert('登录失败:+" + exception.getMessage() + "+');"
                            + "window.history.back(-1);" + "</script>");
                } else {
                    log.debug("登录失败", exception);
                    response.setContentType("application/json;charset=utf-8");
                    writer.write(
                            JSON.toJSONString(new HashMap<String, Object>() {
                                private static final long serialVersionUID = 1L;

                                {
                                    put("code", 0);
                                    put("message", exception.getMessage());
                                }
                            }));
                    writer.flush();
                }
            }
        };
    }

}
