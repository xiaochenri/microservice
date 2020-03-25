package com.microservice.login.auth.config;

import com.microservice.login.auth.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * @author hqc
 * @date 2020/3/25 09:54
 */
@EnableAuthorizationServer
@Configuration
@AutoConfigureAfter(AuthorizationServerEndpointsConfigurer.class)
public class AuthorizationServerConfig
		extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private DataSource dataSource;

	@Bean
	public TokenStore tokenStore() {
		RedisConnectionFactory redisConnectionFactory = SpringUtil.getApplicationContext().getBean(RedisConnectionFactory.class);
		return new RedisTokenStore(redisConnectionFactory);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		endpoints.authenticationManager(authenticationManager)
				.tokenStore(tokenStore());

	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients)
			throws Exception {

		clients.jdbc(dataSource);
	}

	/**
	 * 对应于配置AuthorizationServer安全认证的相关信息，创建ClientCredentialsTokenEndpointFilter核心过滤器
	 * 
	 * @param security
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) {
		security.tokenKeyAccess("isAuthenticated()")
				.checkTokenAccess("permitAll()")
				// 让/oauth/token支持client_id以及client_secret作登录认证
				.allowFormAuthenticationForClients();
	}

}
