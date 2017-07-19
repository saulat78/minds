package com.minds.trading.bot.tradingbot.login;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.minds.trading.bot.tradingbot.user.MindUser;
@Component
public class MindsAuthenticationProvider implements AuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException 
	{
		String usrStr = auth.getName();
		String pwd = auth.getCredentials().toString();
		if("syed".equals(usrStr) && "hashir".equals(pwd))
		{
			MindUser usr = new MindUser();
			usr.setName("Saulat");
			usr.setUser(usrStr);
			List<GrantedAuthority> list = new ArrayList<>();
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usr, pwd,list);
			SecurityContextHolder.getContext().setAuthentication(token);
			return token;
		}
		else
		{
			throw new AuthenticationCredentialsNotFoundException("invalid user or password");
		}
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}


}
