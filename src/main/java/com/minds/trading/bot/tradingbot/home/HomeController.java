package com.minds.trading.bot.tradingbot.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.minds.trading.bot.tradingbot.config.WebSecurityConfig;

@Controller
public class HomeController {

	
	private static Logger log = LoggerFactory.getLogger(HomeController.class);
	
	   @RequestMapping( value=WebSecurityConfig.LOGIN_SUCCESS_URL,  method = RequestMethod.GET )
	   public ModelAndView home() 
	   {
		   log.info("home page..");
		   ModelAndView mav = new ModelAndView();
		   mav.setViewName("home");
	        return mav;
	   }

	  
	  

	    @GetMapping("/admin")
	    public String admin() {
	        return "/admin";
	    }

	    @GetMapping("/user")
	    public String user() {
	        return "/user";
	    }

	    @GetMapping("/about")
	    public String about() {
	        return "/about";
	    }


	    @GetMapping("/403")
	    public String error403() {
	        return "/error/403";
	    }
}
