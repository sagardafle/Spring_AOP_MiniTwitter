package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.sjsu.cmpe275.aop.TweetService;
import edu.sjsu.cmpe275.aop.TweetServiceImpl;
import edu.sjsu.cmpe275.aop.TweetStatsImpl;

@Aspect
public class StatsAspect {
	// variables to get the bean objects.
	private static ApplicationContext applnctxt;
	private static TweetService tweetservicectxt;

	// variables to store the arguments from the tweet method.
	private String username = "";
	private String userMessage = "";

	// variable to keep track of max length of tweeted message.
	private static int maxTweetLength = 0;

	// Declare a TreeMap to store the user-tweets message.
	private static TreeMap<String, ArrayList<String>> userMessagesMap = new TreeMap<String, ArrayList<String>>();
	// ArrayList to store the user tweet messages.
	static ArrayList<String> messagelist = new ArrayList<String>();

	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))")
	public void tweetTheMessage(JoinPoint joinPoint) throws IOException {
		System.out.println("IN the tweetTheMessage");
		applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
		tweetservicectxt = applnctxt.getBean("tweetServiceImpl", TweetService.class);
		System.out.println("Inside retry aspect");
		username = (String) joinPoint.getArgs()[0];
		userMessage = (String) joinPoint.getArgs()[1];
		
		if(userMessage.length() > TweetStatsImpl.lengthOfLongestTweet && userMessage.length()<=140){
			TweetStatsImpl.lengthOfLongestTweet = userMessage.length();
		}
		/**
		 * add the username,message to the hashmap
		 */
		messagelist.add(userMessage);
		userMessagesMap.put(username, messagelist);
		
		printHashMap(userMessagesMap);

	}

	public static void printHashMap(TreeMap<String, ArrayList<String>> usertweeetshm) {
		Iterator<?> it = usertweeetshm.entrySet().iterator();
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
