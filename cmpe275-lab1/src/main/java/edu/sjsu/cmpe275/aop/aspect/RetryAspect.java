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
	// variable to keep a track of the max length of a successfully tweeted
	// message.
	private static int maxTweetLength = 0;

	// variables to store the arguments from the tweet method.
	private String username = "";
	private String userMessage = "";

	// variables to store the arguments from the follow method.
	private String userFollower = "";
	private String userFollowee = "";

	// Error types
	private static final String IOException = "java.io.IOException";
	private static final String IllegalArgumentException = "java.lang.IllegalArgumentException";

	// Declare a TreeMap to store the user-tweets message.
	private TreeMap<String, ArrayList<String>> userMessagesMap = new TreeMap<String, ArrayList<String>>();
	// ArrayList to store the user tweet messages.
	ArrayList<String> messagelist = new ArrayList<String>();

	@After("execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))")
	public void tweetTheMessage(JoinPoint joinPoint) throws IOException {
		System.out.println("IN the tweetTheMessage");
		applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
		tweetservicectxt = (TweetService) applnctxt.getBean("tweetServiceImpl", TweetService.class);
		System.out.println("Inside retry aspect");
		username = (String) joinPoint.getArgs()[0];
		userMessage = (String) joinPoint.getArgs()[1];
		/**
		 * add the username,message to the hashmap
		 */
		// if(!userMessagesMap.containsKey(username)){
		// messagelist.clear();
		// }
		messagelist.add(userMessage);
		userMessagesMap.put(username, messagelist);
		printHashMap(userMessagesMap);

	}

	/**
	 * After Throwing implementation for the tweet message. This will retry the
	 * tweet message maximum 2 times after the failure.
	 */
	@AfterThrowing(pointcut = "execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))", throwing = "error")
	public void retryTweet(JoinPoint joinPoint, Throwable error) throws IOException {
		applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
		tweetservicectxt = (TweetService) applnctxt.getBean("tweetServiceImpl", TweetService.class);
		System.out.println("Inside retry aspect");
		username = (String) joinPoint.getArgs()[0];
		userMessage = (String) joinPoint.getArgs()[1];

		// Some Error has occured. Track it.
		if (error.toString().equals(IOException)) {
			System.out.println("IO Exception occured " + error);
			retryTweetMethod += 1;
			System.out.println("Inside the IF");
			if (retryTweetMethod <= 2) {
				System.out.println("Retrycounter= " + retryTweetMethod);
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

	public static void printHashMap(TreeMap<String, ArrayList<String>> usertweeetshm) {
		Iterator it = usertweeetshm.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			for (String individualtweet : (ArrayList<String>) pair.getValue()) {
				if (individualtweet.length() > maxTweetLength) {
					maxTweetLength = individualtweet.length();
				}
			}
			it.remove(); // avoids a ConcurrentModificationException
		}

	}

}
