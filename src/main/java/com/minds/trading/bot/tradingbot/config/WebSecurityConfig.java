package com.minds.trading.bot.tradingbot.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.minds.trading.bot.tradingbot.login.MindsAuthenticationProvider;
import com.minds.trading.bot.tradingbot.login.MindsPostAuthenticationSuccessHandler;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	public static final String LOGIN_URL = "/login.html";
	public static String[] PERMIT_ALL_URLS = new String[] { LOGIN_URL, "/api/v1/**" };

	public static final String PROCESS_LOGIN_URL = "/dologin";

	public static final String LOGIN_SUCCESS_URL = "/home.html";
	public static final String LOGIN_ERROR_URL = "/login.html?error=authFailure";
	public static final String LOGOUT_URL = "/logout";
	private static Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	private MindsPostAuthenticationSuccessHandler postAuthSuccessHandler;

	@Autowired
	private MindsAuthenticationProvider authProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		Set<String> permitAllUrls = new HashSet<>(Arrays.asList(PERMIT_ALL_URLS));
		String[] publicURLPatterns = permitAllUrls.toArray(new String[permitAllUrls.size()]);
		log.info("Loading MindsSpring Security Configurations - Public URLS - " + publicURLPatterns);

		http.csrf().disable().headers().disable().sessionManagement().sessionFixation().none();
		postAuthSuccessHandler.setDefaultTargetUrl(LOGIN_SUCCESS_URL);

		http.authorizeRequests().antMatchers(PROCESS_LOGIN_URL)
		        .access("isAnonymous() or isAuthenticated()")
				.antMatchers(publicURLPatterns).permitAll()
				.anyRequest()
				.fullyAuthenticated().and()
				.formLogin()
				.defaultSuccessUrl(LOGIN_SUCCESS_URL, true)
				.loginProcessingUrl(PROCESS_LOGIN_URL)
				.loginPage(LOGIN_URL)
				.permitAll()
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(postAuthSuccessHandler)
				.failureUrl(LOGIN_ERROR_URL)
				.permitAll().and().logout()
				.logoutUrl(LOGOUT_URL)
				.invalidateHttpSession(true)
				.logoutSuccessUrl(LOGIN_URL)
				.permitAll().and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.authProvider);
	}

}