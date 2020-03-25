package com.microservice.login.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.microservice.login.auth.handler.OauthLogoutHandler;
import com.microservice.login.auth.handler.OauthLogoutSuccessHandler;
import com.microservice.login.auth.pwd.PwdAuthenticationSecurityConfig;
import com.microservice.login.auth.service.UserService;

/**
 * @author hqc
 * @date 2020/3/25 10:09
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private PwdAuthenticationSecurityConfig pwdAuthenticationSecurityConfig;

	@Override
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().permitAll().and().formLogin()
				.loginPage("/login.html").loginProcessingUrl("/login/pwd")
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler).and().logout()
				.logoutUrl("/logout")
				.logoutSuccessHandler(new OauthLogoutSuccessHandler())
				.addLogoutHandler(new OauthLogoutHandler())
				.clearAuthentication(true).and().csrf().disable();

		http.apply(pwdAuthenticationSecurityConfig);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userService);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(false);
	}
}
