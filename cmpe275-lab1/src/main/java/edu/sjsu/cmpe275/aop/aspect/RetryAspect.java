package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect; // if needed
import org.aspectj.lang.annotation.Before; // ifneeded
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.sjsu.cmpe275.aop.TweetService;
import edu.sjsu.cmpe275.aop.TweetServiceImpl;

@Aspect
public class RetryAspect {
	// variables to get the bean objects.
	private static ApplicationContext applnctxt;
	private static TweetService tweetservicectxt;

	// variable to maintain a retry tweet counter.
	private static int retryTweetMethod = 0;
	// variable to maintain a retry follow counter.
	private static int retryFollowMethod = 0;

	// variables to store the arguments from the tweet method.
	private String username = "";
	private String userMessage = "";

	// variables to store the arguments from the follow method.
	private String userFollower = "";
	private String userFollowee = "";

	// Error types
	private static final String IOException = "java.io.IOException";
	private static final String IllegalArgumentException = "java.lang.IllegalArgumentException";

	/**
	 * After Throwing implementation for the tweet message. This will retry the
	 * tweet message maximum 2 times after the failure.
	 */
	@AfterThrowing(pointcut = "execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))", throwing = "error")
	public void retryTweet(JoinPoint joinPoint, Throwable error) throws IOException {
		System.out.println("AOP retryTweet called");
		applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
		tweetservicectxt = (TweetService) applnctxt.getBean("tweetServiceImpl", TweetService.class);
		System.out.println("Inside retry aspect");
		username = (String) joinPoint.getArgs()[0];
		userMessage = (String) joinPoint.getArgs()[1];

		// Some Error has occured. Track it.
		if (error.toString().equals(IOException)) {
			System.out.println("IO Exception occured " + error);
			retryTweetMethod += 1;
			System.out.println("Inside the IF of retrytweet");
			if (retryTweetMethod <= 2) {
				System.out.println(" retryTweetMethod Counter= " + retryTweetMethod);
				tweetservicectxt.tweet(username, userMessage);
			} else {
				System.out.println("INFO : Maximum re-tries attempt (" + (retryTweetMethod - 1)
						+ ") to execute the tweet message has been reached.");
			}
		} else if (error.toString().equals(IllegalArgumentException)) {
			System.out.println("ERROR: Your message length is " + userMessage.length()
					+ " characters ,which cannot be greater than 140 error. Throwing error :" + error);
		}

	}

	@AfterThrowing(pointcut = "execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.follow(..))", throwing = "error")
	public void retryFollow(JoinPoint joinPoint, Throwable error) throws IOException {
		applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
		tweetservicectxt = (TweetService) applnctxt.getBean("tweetServiceImpl", TweetService.class);
		System.out.println("Inside retry aspect");
		userFollower = (String) joinPoint.getArgs()[0];
		userFollowee = (String) joinPoint.getArgs()[1];
		System.out.println("Follower " + joinPoint.getArgs()[0]);
		System.out.println("Followee " + joinPoint.getArgs()[1]);

		if (error.toString().equals(IOException)) {
			System.out.println("IO Exception occured " + error);
			retryFollowMethod += 1;
			System.out.println("Inside the IF");
			if (retryFollowMethod <= 2) {
				System.out.println("Retrycounter= " + retryFollowMethod);
				tweetservicectxt.follow(userFollower, userFollowee);
			} else {
				System.out.println("INFO : Maximum re-tries attempt (" + (retryFollowMethod - 1)
						+ ") to execute the follow message has been reached.");	
		}
	}
	}
}
