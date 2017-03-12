package com.userfront.aspects;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAspect {
	private Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

	@AfterThrowing(pointcut = "execution(* *(..))"
			+ "&& ("
			+ "within(com.userfront.dao..*)"
			+ ")"
			+ "", throwing = "exception")
	public void processException(RuntimeException exception) throws Throwable {
		logger.error(exception.getMessage());
	}

}