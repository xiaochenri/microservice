package com.microservice.login.auth.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microservice.login.auth.pwd.PwdAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * OAuth2相关操作
 */
@Slf4j
@RestController
public class OAuth2Controller {
	@Resource
	private ObjectMapper objectMapper;

	@Resource
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@PostMapping(value = "/oauth/user/token")
	public void getUserTokenInfo(String username, String password,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PwdAuthenticationToken token = new PwdAuthenticationToken(
				username, password);
		writerToken(request, response, token, "用户名或密码错误");
	}

	/**此处是与使用拦截器获取授权信息最大的不同之处
	 *
	 * 以往的过程分为四部分：
	 * 1.未登录时跳转到授权服务器登录
	 * 2.登录后请求/oauth/authorize 获取code 需要传递服务名
	 * 3.携带code请求/oauth/token 获取token 需要传递服务名以及密码
	 * 4.携带token请求/oauth/check_token 获取用户信息
	 *
	 * 授权服务器本身在自己登录之后Spring security将用户信息存储于session的SPRING_SECURITY_CONTEXT中，当你在请求/oauth/authorize时
	 *会把生成的code与session中的用户信息、你传递的服务名信息进行关联，
	 *
	 *当你在请求/oauth/token时会生成真正的token，并将token与authentication信息存储在redis中，这部分是oauth2的工作
	 *
	 *当你在请求/oauth/check_token时就通过token去redis中拿authentication信息
	 *
	 *
	 * 现在的改造：
	 * 用户请求授权服务器之后，直接验证用户信息是否正确（security来验证），验证完成后将security的authentication与clientId等信息一起
	 * 生成OAuth2Authentication，然后直接调用oauth2的token生成代码，生成token，存储token，最后直接返回token给前端
	 *
	 * 这里有一点需要注意的是生成OAuth2Authentication要携带服务的信息，因为网关验证时需要验证这个服务的正确性。
	 *
	 * 单点登录的原理在于用户在同一个浏览器上拿到了授权中心的session ID，可以自动的将此ID下的用户与另外请求的服务ID绑定，达到登录的目的
	 * @param request
	 * @param response
	 * @param token
	 * @param badCredenbtialsMsg
	 * @throws IOException
	 */
	private void writerToken(HttpServletRequest request,
			HttpServletResponse response, AbstractAuthenticationToken token,
			String badCredenbtialsMsg) throws IOException {
		try {
			//这里配置网关的服务ID、密码信息
			final String[] clientInfos = new String[2];
			String clientId = "tttt";
			String clientSecret = "trs";

			ClientDetails clientDetails = getClient(clientId, clientSecret);

			TokenRequest tokenRequest = new TokenRequest(new HashMap<>(),
					clientId, clientDetails.getScope(), "authorization_code");
			OAuth2Request oAuth2Request = tokenRequest
					.createOAuth2Request(clientDetails);

			//此处调用token对应类型的provider来检测数据的合法性
			Authentication authentication = authenticationManager
					.authenticate(token);
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
			OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(
					oAuth2Request, authentication);

			//生成token，存储token
			OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices
					.createAccessToken(oAuth2Authentication);
			oAuth2Authentication.setAuthenticated(true);
			//返回给前端
            PrintWriter writer = response.getWriter();
            writer.write(oAuth2AccessToken.getValue());
            writer.flush();
        } catch (BadCredentialsException
				| InternalAuthenticationServiceException e) {
			exceptionHandler(response, badCredenbtialsMsg);
		} catch (Exception e) {
			exceptionHandler(response, e);
		}
	}

	private void exceptionHandler(HttpServletResponse response, Exception e)
			throws IOException {
		log.error("exceptionHandler-error:", e);
		exceptionHandler(response, e.getMessage());
	}

	private void exceptionHandler(HttpServletResponse response, String msg)
			throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());

	}

	private ClientDetails getClient(String clientId, String clientSecret) {
		ClientDetails clientDetails = clientDetailsService
				.loadClientByClientId(clientId);

		if (clientDetails == null) {
			throw new UnapprovedClientAuthenticationException(
					"clientId对应的信息不存在");
		}
		return clientDetails;
	}
}
