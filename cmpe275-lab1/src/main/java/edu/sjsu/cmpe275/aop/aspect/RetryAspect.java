/**
* The RetryAspect class is an aspect which has two advices.
* One advise is for the tweet method and other for the follow method.
* This class attempts to retry the follow and tweet method atleast twice in case of IOExceptions.
*
* @author  Sagar Dafle 
*/

package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
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
	private static int retryTweetMethod = 2;
	// variable to maintain a retry follow counter.
	private static int retryFollowMethod = 0;

	public static boolean tempBool = true;
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
	 *  After Throwing implementation for the tweet message. 
	 *  This will retry the tweet message maximum 2 times after the failure.
	 *
	 * @param  joinPoint A JoinPoint for getting details about a point in the exectuon of program.
	 * @param  error The error object from the target class.
	 * @throws IOException  Incase the twweet method throws another exception.
	 * 
	 */
	 @AfterThrowing(pointcut = "execution(public void  edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))", throwing = "error")
	 public void retryTweet(JoinPoint joinPoint, Throwable error) throws IOException {
	 try {
	 System.out.println("AOP retryTweet called");
	 applnctxt = new ClassPathXmlApplicationContext("context.xml");
	 tweetservicectxt = (TweetService) applnctxt.getBean("tweetService",
	 TweetService.class);
	
	 System.out.println("Inside retry aspect --retryTweet method");
	 username = (String) joinPoint.getArgs()[0];
	 userMessage = (String) joinPoint.getArgs()[1];
	 if (error.toString().equals(IOException)) {
	 System.out.println("IO Exception occured " + error);
	 retryTweetMethod -= 1;
	 System.out.println("Inside the IF of retrytweet");
	 if (retryTweetMethod >= 0) {
	 System.out.println("retryTweetMethod Counter= " + retryTweetMethod);
	 tweetservicectxt.tweet(username, userMessage);
	 } else {
		 retryTweetMethod = 2;
	 System.out.println("INFO : Maximum re-tries attempt (" +
	 (retryTweetMethod )
	 + ") to execute the tweet message has been reached.");
	 
	 }
	 } else if (error.toString().equals(IllegalArgumentException)) {
	 System.out.println("ERROR: Your message length is " +
	 userMessage.length()
	 + " characters ,which cannot be greater than 140 error. Throwing error :"
	 + error);
	 }
	 } catch (IOException error1) {
	 System.out.println("Exception caught in the tweet aspect !! " +
	 error1.getClass());
	 }
	
	 }

//	@Around("execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))")
//	public Object handletweearound(ProceedingJoinPoint joinPoint) throws Throwable {
//		Object result = null;
//		try {
//			// joinPoint.
//			applnctxt = new ClassPathXmlApplicationContext("context.xml");
//			tweetservicectxt = (TweetService) applnctxt.getBean("tweetService", TweetService.class);
//			result = joinPoint.proceed();
//
//			System.out.println("... Running after advice...");
//			// tempBool = true;
//			return result;
//		} catch (Exception error) {
//			// tempBool = false;
//			if (error.toString().equals(IOException)) {
//				tempBool = false;
//				// try{
//				System.out.println("IO Exception occured " + error);
//				retryTweetMethod -= 1;
//				System.out.println("Inside the IF of retrytweet");
//				if (retryTweetMethod >= 0) {
//					System.out.println("retryTweetMethod Counter= " + retryTweetMethod);
//					// joinPoint.proceed();
//					((TweetService) joinPoint.getThis()).tweet(joinPoint.getArgs()[0].toString(), joinPoint.getArgs()[1].toString());
//					//tweetservicectxt.tweet(joinPoint.getArgs()[0].toString(), joinPoint.getArgs()[1].toString());
//				} else {
//					tempBool = false;
//					retryTweetMethod = 2;
//					System.out.println("ERROR : Maximum re-tries attempt (" + (retryTweetMethod)
//							+ ") to execute the tweet message has been reached.");
//					return error;
//
//				}
//				// } catch(Exception e){
//				// System.out.println("Caught nested exception via proceed");
//				// }
//
//			} else if (error.toString().equals(IllegalArgumentException)) {
//				System.out.println("ERROR: Your message length is " + userMessage.length()
//						+ " characters ,which cannot be greater than 140 error. Throwing error :" + error);
//			}
//		}
//		return joinPoint;
//	}
	 
	 /**
		 *  After Throwing implementation for the follow message. 
		 *  This will retry the follow message maximum 2 times after the failure.
		 *
		 * @param  joinPoint A JoinPoint for getting details about a point in the execution of program.
		 * @param  error The error object from the follow target method.
		 * @throws IOException  In case the follow method throws another exception.
		 * 
		 */
	@AfterThrowing(pointcut = "execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.follow(..))", throwing = "error")
	public void retryFollow(JoinPoint joinPoint, Throwable error) throws IOException {
		applnctxt = new ClassPathXmlApplicationContext("context.xml");
		tweetservicectxt = (TweetService) applnctxt.getBean("tweetService", TweetService.class);
		System.out.println("Inside retry aspect");
		userFollower = (String) joinPoint.getArgs()[0];
		userFollowee = (String) joinPoint.getArgs()[1];
		System.out.println("Follower " + joinPoint.getArgs()[0]);
		System.out.println("Followee " + joinPoint.getArgs()[1]);
		try {
			if (error.toString().equals(IOException)) {
				System.out.println("IO Exception occured " + error);
				retryFollowMethod += 1;
				System.out.println("Inside the IF");
				if (retryFollowMethod <= 2) {
					System.out.println("Retrycounter= " + retryFollowMethod);
					// ((TweetService) joinPoint.getThis()).follow(userFollower,
					// userFollowee);
					tweetservicectxt.follow(userFollower, userFollowee);
				} else {
					System.out.println("INFO : Maximum re-tries attempt (" + (retryFollowMethod - 1)
							+ ") to execute the follow message has been reached.");
					retryFollowMethod = 0;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception caught in the aspect !! " + e.getMessage().toString());
		}

	}
}
