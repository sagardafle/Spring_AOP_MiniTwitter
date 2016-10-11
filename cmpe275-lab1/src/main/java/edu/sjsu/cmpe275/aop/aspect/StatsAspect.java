/**
* The StatsASpect class is an aspect which has four advices.
* THese advises will help the application to keep track of all the required stats like the length of longest tweet, most productive user and the most active follower.
* 
* @author  Sagar Dafle
*/

package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
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

	// variables to store the arguments from the follow method.
	private String follower = "";
	private String followee = "";
	int maxTweetLengthInMap = 0;
	int maxActiveFollowerCountInMap = 0;

	// Error types
	private static final String IOException = "java.io.IOException";
	private static final String IllegalArgumentException = "java.lang.IllegalArgumentException";

	/**
	 * AfterReturning implementation for the tweet message. This will save the
	 * tweet message in the map. It will compute the length of the longest
	 * tweet, the most productive user .
	 * 
	 * @param joinPoint
	 *            A JoinPoint for getting details about a point in the exectuon
	 *            of program.
	 * @throws IOException
	 *             Incase the tweet method throws another exception.
	 * 
	 */
	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))")
	public void tweetTheMessage(JoinPoint joinPoint) throws IOException {

		System.out.println("IN the tweetTheMessage");
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
		 * TASK -2 : Computing most productive user . Add the tweet data to the
		 * mostProductiveUserMap
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

	/**
	 * After implementation for the follow message. This will save the follow
	 * message details in the map. It will compute the most active follower.This
	 * function will be invoked even if the user fails to follow from the tweet
	 * service.
	 * 
	 * @param joinPoint
	 *            A JoinPoint for getting details about a point in the exectuon
	 *            of program.
	 * @throws IOException
	 *             Incase the follow method throws another exception.
	 * 
	 */

	@After("execution(public * edu.sjsu.cmpe275.aop.TweetServiceImpl.follow(..))")
	public void followTheUser(JoinPoint joinPoint) throws IOException {
		/**
		 * TASK-1 : Computing the most follower user. Add the tweet data to
		 * mostActiveFollowerUserMap.
		 */

		follower = (String) joinPoint.getArgs()[0];
		followee = (String) joinPoint.getArgs()[1];
		Set<String> followeruserset = null;
		if (!follower.equals(followee) || follower != followee) {
			if (!TweetStatsImpl.mostActiveFollowerUserMap.containsKey(follower)) {
				followeruserset = new HashSet<String>();
			} else {
				followeruserset = TweetStatsImpl.mostActiveFollowerUserMap.get(follower);
			}
			followeruserset.add(followee);
			TweetStatsImpl.mostActiveFollowerUserMap.put(follower, followeruserset);
		} else {
			System.out.println("Follower and followee cannot be the same");
		}

		/**
		 * DONE- TASK-1.
		 */
	}

	/**
	 * getTheMostProductiveUser This function will be called before the tweet
	 * message. It will compute the most productive follower.
	 * 
	 */
	@Before("execution(public * edu.sjsu.cmpe275.aop.TweetStatsImpl.getMostProductiveUser(..))")
	public void getTheMostProductiveUser() {
		System.out.println("Inside the aspect method -- getTheMostProductiveUser");
		/**
		 * Computing the most productive user
		 */

		if (TweetStatsImpl.mostProductiveUserMap.size() > 0)
			maxTweetLengthInMap = (Collections.max(TweetStatsImpl.mostProductiveUserMap.values())); // to
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
		// printTreeMap(TweetStatsImpl.mostProductiveUserMap);
	}

	/**
	 * Before implementation for the get most active follower. It will be called
	 * before the calling the tweetstatsimpl class.getTheMostActiveFollower
	 * function.
	 * 
	 */

	@Before("execution(public * edu.sjsu.cmpe275.aop.TweetStatsImpl.getMostActiveFollower(..))")
	public void getTheMostActiveFollower() {
		System.out.println("Inside the aspect method -- getTheMostActiveFollower");
		/**
		 * Removed this implementation as we are supposed to make sure that
		 * there can be only unique follower-followee pair.
		 */
		// if (TweetStatsImpl.mostActiveFollowerUserMap.size() > 0) {
		// int maxActiveFollowerCountInMap =
		// (Collections.max(TweetStatsImpl.mostActiveFollowerUserMap.values()));
		// // to
		// // fetch
		// // the max
		// // value
		// // within
		// // hashmap
		//
		// if (maxActiveFollowerCountInMap == 0)
		// TweetStatsImpl.mostActiveFollower = null; // return null if no
		// // user
		// // has successfully
		// // tweet.
		// for (Entry<String, Integer> entry :
		// TweetStatsImpl.mostActiveFollowerUserMap.entrySet()) {
		// if (entry.getValue() == maxActiveFollowerCountInMap) {
		// TweetStatsImpl.mostActiveFollower = entry.getKey();
		// break; // break the loop as we need to return the user based
		// // on
		// // alphabetical order in the case of clash.
		// }
		// }
		// printTreeMap(TweetStatsImpl.mostActiveFollowerUserMap);

		// }
		// maxActiveFollowerCountInMap initialized to 0 above
		printFollowerMap(TweetStatsImpl.mostActiveFollowerUserMap);
		if (TweetStatsImpl.mostActiveFollowerUserMap.size() > 0) {
			for (Entry<String, Set<String>> entry : TweetStatsImpl.mostActiveFollowerUserMap.entrySet()) {

				if (entry.getValue().size() > maxActiveFollowerCountInMap) {
					maxActiveFollowerCountInMap = entry.getValue().size(); // if
																			// the
																			// size
																			// of
																			// follower
																			// set
																			// is
																			// greater
																			// than
																			// maxActiveFollowerCountInMap,
																			// set
																			// maxActiveFollowerCountInMap
																			// accordingly.
					TweetStatsImpl.mostActiveFollower = entry.getKey(); // The
																		// followername
																		// who
																		// has
																		// the
																		// highest
																		// list
																		// of
																		// followees.
				}
			}
		}

	}

	public static void printTreeMap(Map<String, Integer> aTreeMap) {
		Iterator<?> it = aTreeMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			// System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	public static void printFollowerMap(Map<String, Set<String>> aTreeMap) {
		Iterator<?> it = aTreeMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			// it.remove(); // avoids a ConcurrentModificationException
		}
	}

}
