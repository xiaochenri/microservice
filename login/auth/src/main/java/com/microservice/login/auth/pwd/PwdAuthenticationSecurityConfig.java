package com.microservice.login.auth.pwd;

import com.microservice.login.auth.handler.SecurityHandlerConfig;
import com.microservice.login.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class PwdAuthenticationSecurityConfig extends
		SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private UserService userService;

	public void configure(HttpSecurity httpSecurity) throws Exception {


		PwdAuthenticationProvider pwdAuthenticationProvider = new PwdAuthenticationProvider();
		pwdAuthenticationProvider.setUserService(userService);

		httpSecurity.authenticationProvider(pwdAuthenticationProvider);
	}
}
