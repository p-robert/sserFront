package com.userfront.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.userfront.domain.Email;

@Aspect
@Component
public class LoggingAspect {
	
	private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Pointcut("execution(* *.sen*(..))")
    public void userMethod() { 
	}

	@Around("userMethod()")
	public Object log (ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();
        LOG.info("Call method " + methodName + " with args " + methodArgs);
        Object result = thisJoinPoint.proceed(); 
        LOG.info("Method " + methodName + " returns " + result);
        return result;
	}
	
	@Pointcut("execution(* com.userfront.util.EmailSender.*(..))")
    public void pointCutService() { 
	}
	
	@Around("pointCutService()")
	public Object logService (ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();
        if(methodName.equals("persistToDB")) {
        	Object[] methodArgs = thisJoinPoint.getArgs();
        	
        	if(methodArgs.length == 1 && methodArgs[0] instanceof Email){
        		LOG.info("The following email will be persisted to database: \n" +  (Email)methodArgs[0] );
        	}
        	 
        } else if(methodName.equals("sentEmail")) {
        	Object[] methodArgs = thisJoinPoint.getArgs();
        	if(methodArgs.length == 1 && methodArgs[0] instanceof Email){
        		LOG.info("The following email will be sent: \n" +  (Email)methodArgs[0] );
        	}
        	 
        }
        
        Object[] methodArgs = thisJoinPoint.getArgs();
        LOG.info("Call method " + methodName + " with args " + methodArgs);
        Object result = thisJoinPoint.proceed(); 
        return result;
	}
	
	
	
}