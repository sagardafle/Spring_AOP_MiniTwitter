package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.sjsu.cmpe275.aop.TweetService;
import edu.sjsu.cmpe275.aop.TweetStatsImpl;

@Aspect
public class StatsAspect {
	// variables to get the bean objects.
	private static ApplicationContext applnctxt;
	// variables to store the arguments from the tweet method.
	private String username = "";
	private String userMessage = "";

	//variables to store the arguments from the follow method.
	private String follower ="";
	private String followee ="";
	

	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))")
	public void tweetTheMessage(JoinPoint joinPoint) throws IOException {
		System.out.println("IN the tweetTheMessage");
		applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
		applnctxt.getBean("tweetServiceImpl", TweetService.class);
		username = (String) joinPoint.getArgs()[0];
		userMessage = (String) joinPoint.getArgs()[1];

		/**
		 * TASK-1 : Compute the length of longest message tweeted.
		 */
		if (userMessage.length() > TweetStatsImpl.lengthOfLongestTweet && userMessage.length() <= 140) {
			TweetStatsImpl.lengthOfLongestTweet = userMessage.length();
		}
		
		/**
		 * DONE- TASK-1.
		 */		
		
		/**
		 * TASK -2 : Computing most productive user . 
		 * Add the tweet data to the mostProductiveUserMap
		 */
		if (TweetStatsImpl.mostProductiveUserMap.containsKey(username)) {
			TweetStatsImpl.mostProductiveUserMap.put(username,
					(TweetStatsImpl.mostProductiveUserMap.get(username) + userMessage.length()));
		} else {
			TweetStatsImpl.mostProductiveUserMap.put(username, userMessage.length());
		}
		
		/**
		 * DONE -- TASK 2. adding the tweet data to the mostProductiveUserMap
		 */
	}
	
	@AfterReturning("execution(public * edu.sjsu.cmpe275.aop.TweetServiceImpl.follow(..))")
	public void followTheUser(JoinPoint joinPoint) throws IOException {
		/**
		 * TASK-1 : Computing the most productive user.
		 * Add the tweet data to mostActiveFollowerUserMap.
		 */
		
		applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
		applnctxt.getBean("tweetServiceImpl", TweetService.class);
		follower = (String) joinPoint.getArgs()[0];
		followee = (String) joinPoint.getArgs()[1];
		
		if (TweetStatsImpl.mostActiveFollowerUserMap.containsKey(follower)) {
			TweetStatsImpl.mostActiveFollowerUserMap.put(follower,
					(TweetStatsImpl.mostActiveFollowerUserMap.get(follower) + 1));
		} else {
			TweetStatsImpl.mostActiveFollowerUserMap.put(follower, 1);
		}
		/**
		 * DONE- TASK-2.
		 */
	}
	
//	@Before("execution(public * edu.sjsu.cmpe275.aop.TweetStatsImpl.resetStats(..))")
//	public void resetAllUserStats() {
//		
//	}

	@Before("execution(public * edu.sjsu.cmpe275.aop.TweetStatsImpl.getMostProductiveUser(..))")
	public void getTheMostProductiveUser() {
		System.out.println("Inside the aspect method -- getTheMostProductiveUser");		
		/**
		 * Computing the most productive user 
		 */
		
		int maxTweetLengthInMap = (Collections.max(TweetStatsImpl.mostProductiveUserMap.values())); // to
		// fetch
		// the max
		// value
		// within
		// hashmap

		if (maxTweetLengthInMap == 0)
			TweetStatsImpl.mostProductiveUser = null; // return null if no user
														// has successfully
														// tweet.
		for (Entry<String, Integer> entry : TweetStatsImpl.mostProductiveUserMap.entrySet()) {
			if (entry.getValue() == maxTweetLengthInMap) {
				TweetStatsImpl.mostProductiveUser = entry.getKey();
				break; // break the loop as we need to return the user based on
						// alphabetical order in the case of clash.
			}
		}
		
		/**
		 * Done -- Computing the most productive user.
		 */
		printTreeMap(TweetStatsImpl.mostProductiveUserMap);
	}
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.TweetStatsImpl.getMostActiveFollower(..))")
	public void getTheMostActiveFollower() {
		System.out.println("Inside the aspect method -- getTheMostActiveFollower");
		
		int maxActiveFollowerCountInMap = (Collections.max(TweetStatsImpl.mostActiveFollowerUserMap.values())); // to
		// fetch
		// the max
		// value
		// within
		// hashmap

		if (maxActiveFollowerCountInMap == 0)
			TweetStatsImpl.mostActiveFollower = null; // return null if no user
														// has successfully
														// tweet.
		for (Entry<String, Integer> entry : TweetStatsImpl.mostActiveFollowerUserMap.entrySet()) {
			if (entry.getValue() == maxActiveFollowerCountInMap) {
				TweetStatsImpl.mostActiveFollower = entry.getKey();
				break; // break the loop as we need to return the user based on
						// alphabetical order in the case of clash.
			}
		}
		printTreeMap(TweetStatsImpl.mostActiveFollowerUserMap);
	}

	public static void printTreeMap(Map<String, Integer> aTreeMap) {
		Iterator<?> it = aTreeMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

}
