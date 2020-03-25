package com.microservice.login.auth.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hqc
 * @date 2020/3/25 10:30
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

				String header = request.getHeader("Authorization");
				String name = authentication.getName();
				if (header == null || !header.startsWith("Basic ")) {
					throw new UnapprovedClientAuthenticationException("请求头中无client信息");
				}

				String[] tokens = extractAndDecodeHeader(header, request);
				assert tokens.length == 2;
				String clientId = tokens[0];
				String clientSecret = tokens[1];

				ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

				if (clientDetails == null) {
					throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
				} else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
					throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
				}

				TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "custom");
				OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

				OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

				//直接在此处将token 与用户信息 存储到redis中，并将token值返回前端
				OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(token.getValue());
				writer.flush();
			}
		};
	}

	/**解析Authorization中的信息
	 * @param header
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

		return null;
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
