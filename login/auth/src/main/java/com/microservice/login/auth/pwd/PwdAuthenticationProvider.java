package com.microservice.login.auth.pwd;

import com.microservice.login.auth.entity.User;
import com.microservice.login.auth.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

import java.util.UUID;

/**
 * 此处进行authentication验证
 */
@Data
public class PwdAuthenticationProvider implements AuthenticationProvider {

	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		PwdAuthenticationToken token = (PwdAuthenticationToken) authentication;

//		User user = userService
//				.loadUserByUsername((String) token.getPrincipal());
//		if (user == null) {
//			throw new InternalAuthenticationServiceException("用户未注册");
//		}
//
//		String passWord = token.getPassWord();
//		String md5Pwd = DigestUtils.md5Hex(passWord);
//		if (!user.getPassword().equals(md5Pwd)) {
//			throw new InternalAuthenticationServiceException("密码错误");
//		}
		User user = new User();
		String passWord = "123";
		user.setUsername(token.getPrincipal().toString());
		user.setPassword(token.getPassWord());
		user.setId(UUID.randomUUID().toString());

		// 检测用户属性 不通过的抛出异常
		PwdAuthenticationToken authenticationToken = new PwdAuthenticationToken(
				user, user.getAuthorities(), passWord);

		authenticationToken.setDetails(token.getDetails());

		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return PwdAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
