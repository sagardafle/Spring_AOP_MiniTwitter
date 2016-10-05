package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.sjsu.cmpe275.aop.TweetService;
import edu.sjsu.cmpe275.aop.TweetServiceImpl;
import edu.sjsu.cmpe275.aop.TweetStatsImpl;

public class RetryAspect implements MethodInterceptor {
	public Map<String,String> usertweetshm = new HashMap<>();
	private int retrycounter =0;
	private String methodName = "";
	private String username ="";
	private String usertweetmessage ="";
	private static ApplicationContext applnctxt;
	private static TweetService tweetservicectxt;
	@Override	
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
		tweetservicectxt = (TweetService) applnctxt.getBean("tweetServiceImplProxy",TweetService.class);
		System.out.println("==============Inside retry aspect ===============");
		methodName = methodInvocation.getMethod().getName();
		System.out.println("Method name : " + methodName);
		username = (String) methodInvocation.getArguments()[0];
		usertweetmessage = (String) methodInvocation.getArguments()[1];
		System.out.println("Method arguments : " + Arrays.toString(methodInvocation.getArguments()));
		if(methodInvocation.getMethod().getName().equals("tweet")){
			System.out.println("Tweet : Before method hijacked!");
			try {
				// proceed to original method call
				Object result = methodInvocation.proceed();
				
				// same with AfterReturningAdvice
				System.out.println("Tweet : Before after hijacked! --");

				return result;

			}catch (IllegalArgumentException illegalargexe) {
				System.out.println("Tweet : IllegalArgumentException occured!");
			}
			
			catch (IOException ioe) {
				System.out.println("Tweet : IOException occured!");
				retrycounter += 1;
				if(retrycounter<=2){
					tweetservicectxt.tweet(username, usertweetmessage);
				}
			}
		}
		return null;
	}
}
