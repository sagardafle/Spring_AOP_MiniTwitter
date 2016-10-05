package edu.sjsu.cmpe275.aop.aspect;

import java.util.Arrays;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;

import edu.sjsu.cmpe275.aop.TweetService;
import edu.sjsu.cmpe275.aop.TweetStats;

public class StatsAspect implements MethodInterceptor {
	private static ApplicationContext applnctxt;
	private static TweetStats tweetstatsctxt;
	
	private String methodName = "";
	
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		methodName = methodInvocation.getMethod().getName();
		System.out.println("==============Inside stats aspect ===============");
		System.out.println("Method name : " +methodName);
		//System.out.println("Method arguments : " + Arrays.toString(methodInvocation.getArguments()));

		// same with MethodBeforeAdvice
		System.out.println("HijackAroundMethod : Before method hijacked!");

		try {
			// proceed to original method call
			Object result = methodInvocation.proceed();

			// same with AfterReturningAdvice
			System.out.println("HijackAroundMethod : Before after hijacked! --" + result.toString());

			return result;

		} catch (IllegalArgumentException e) {
			// same with ThrowsAdvice
			System.out.println("HijackAroundMethod : Throw exception hijacked!");
			throw e;
		}
	}
}
