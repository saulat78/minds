package com.minds.trading.bot.tradingbot.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.minds.trading.bot.tradingbot.config.WebSecurityConfig;

@Controller
public class LoginController
{

	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	@RequestMapping(value=WebSecurityConfig.LOGIN_URL, method = RequestMethod.GET )
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		log.info("LAunching login page...");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		return mav;
	}
}
