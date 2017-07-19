package com.minds.trading.bot.tradingbot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private ThymeleafProperties properties;
	@Bean
    public ViewResolver viewResolver() {
    	 ThymeleafViewResolver resolver = new ThymeleafViewResolver();
         resolver.setTemplateEngine(templateEngine());
         resolver.setOrder(1);
         resolver.setCharacterEncoding("UTF-8");
         return resolver;
    }
    


    @Bean
    //made this @Bean (vs private in Thymeleaf migration docs ), otherwise MessageSource wasn't autowired.
    public SpringTemplateEngine templateEngine() {
    	SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
    	SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix(this.properties.getPrefix());
        templateResolver.setSuffix(this.properties.getSuffix());
        templateResolver.setTemplateMode(this.properties.getMode());
        templateResolver.setCacheable(false);

        return templateResolver;
    }
 
}