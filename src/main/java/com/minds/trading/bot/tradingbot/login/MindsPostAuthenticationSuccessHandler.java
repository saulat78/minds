package com.minds.trading.bot.tradingbot.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MindsPostAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static Logger log = LoggerFactory.getLogger(MindsPostAuthenticationSuccessHandler.class);

	
	public MindsPostAuthenticationSuccessHandler()
		{
			super();
			//super.setUseReferer(true);
			
		}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		//handle(request, response, authentication);
		 super.onAuthenticationSuccess(request, response, authentication);
	}

	/*protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String source = request.getParameter("source");

			getRedirectStrategy().sendRedirect(request, response, WebSecurityConfig.LOGIN_SUCCESS_URL);
	
	}*/
}