package com.neu.edu.LMS.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@Aspect
public class Aspectlogger {

    public void LogToFile(String msg) throws IOException {
        FileWriter fw = new FileWriter("C:\\Users\\praty\\Desktop\\WedToolsDev_Yusuf\\Lab\\FinalProject_WebTools_LMS\\LMS_Application_Logging.txt", true);
        fw.write(msg);
        fw.close();
    }

    @Pointcut("execution(* com.neu.edu.LMS.controller.*.*(..))")
    private void logcontroller() {
    }

    @Before("logcontroller()")
    public void doBefore(JoinPoint jp) throws IOException {
    	LogToFile("\nTime: " + new Date() + "\t\tBefore entering method: " + jp.getSignature().getName());

    }

    @AfterReturning("logcontroller()")
    public void doAfterrunning(JoinPoint jp) throws IOException {
    	LogToFile("\nTime: " + new Date() + "\t\tMethod: " + jp.getSignature().getName() + " returned successfully");
    }


    @AfterThrowing("logcontroller()")
    public void doAfterThorw(JoinPoint jp) throws IOException {
    	LogToFile("\nTime: " + new Date() + "\t\tMethod: " + jp.getSignature().getName() + " threw an exception");
    }
    
    @Around("logcontroller()")
    public Object doAroundlog(ProceedingJoinPoint pjp) throws Throwable {
    	LogToFile("\nTime: " + new Date() + "\t\tBefore Invoking this Method: " + pjp.getSignature().getName());
    	//pjp.proceed();
    	Object val = null;
    	try {
    		val = pjp.proceed();
    	} catch (Throwable t) {
    		t.printStackTrace();
    	}
    	LogToFile("\nTime: " + new Date() + "\t\tAfter Invoking  Method Return: " + val);
    	return val;
    } 
}

