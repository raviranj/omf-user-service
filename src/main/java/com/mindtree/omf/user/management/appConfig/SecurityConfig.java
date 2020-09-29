package com.mindtree.omf.user.management.appConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().antMatcher("/**").authorizeRequests().antMatchers("/api/omf/user/saveAnonymousUser/**")
				.anonymous().antMatchers("/api/omf/user/anonymous/placeOrder/**").anonymous().anyRequest()
				.authenticated().and().oauth2Login().defaultSuccessUrl("/api/omf/user/saveRegisteredUser", true).and()
				.rememberMe();
	}
}