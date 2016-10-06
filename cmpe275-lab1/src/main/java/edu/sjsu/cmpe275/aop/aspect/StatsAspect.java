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

	// Declare a TreeMap to store the user-tweets message.
	// ArrayList to store the user tweet messages.
	// static ArrayList<String> messagelist = new ArrayList<String>();

	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))")
	public void tweetTheMessage(JoinPoint joinPoint) throws IOException {
		System.out.println("IN the tweetTheMessage");
		applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
		applnctxt.getBean("tweetServiceImpl", TweetService.class);
		System.out.println("Inside retry aspect");
		username = (String) joinPoint.getArgs()[0];
		userMessage = (String) joinPoint.getArgs()[1];

		if (userMessage.length() > TweetStatsImpl.lengthOfLongestTweet && userMessage.length() <= 140) {
			TweetStatsImpl.lengthOfLongestTweet = userMessage.length();
		}
		/**
		 * add the username,message to the hashmap
		 */
		if (TweetStatsImpl.mostProductiveUserMap.containsKey(username)) {
			TweetStatsImpl.mostProductiveUserMap.put(username,
					(TweetStatsImpl.mostProductiveUserMap.get(username) + userMessage.length()));
		} else {
			TweetStatsImpl.mostProductiveUserMap.put(username, userMessage.length());
		}
	}

	@Before("execution(public * edu.sjsu.cmpe275.aop.TweetStatsImpl.getMostProductiveUser(..))")
	public void getTheMostProductiveUser(JoinPoint joinPoint) {
		System.out.println("Inside the aspect method -- getTheMostProductiveUser");

		int maxTweetLengthInMap = (Collections.max(TweetStatsImpl.mostProductiveUserMap.values())); // to
		// fetch
		// themax
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
		printTreeMap(TweetStatsImpl.mostProductiveUserMap);
	}

	public static void printTreeMap(Map<String, Integer> mostProductiveUserMap) {
		Iterator<?> it = mostProductiveUserMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

}
