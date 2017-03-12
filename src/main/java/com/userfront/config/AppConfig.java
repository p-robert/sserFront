package com.userfront.config;

import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.userfront.domain.Email;

@Configuration
@ComponentScan(basePackages="com.userfront")
@EnableAspectJAutoProxy
@ImportResource("classpath:application/application-context.xml")
public class AppConfig {

	@Bean
	public BlockingQueue<Email> blockingQueue(){
		return new ArrayBlockingQueue<>(10);
	}
	
	@Bean
	public ExecutorService executorService() {
		return Executors.newSingleThreadExecutor();
	}
	
	@Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }

     @Bean
     public MessageSource messageSource() {
     final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
     messageSource.setBasename("classpath:messages");
     messageSource.setUseCodeAsDefaultMessage(true);
     messageSource.setDefaultEncoding("UTF-8");
     messageSource.setCacheSeconds(0);
     return messageSource;
     }
     
   
     
     
}
