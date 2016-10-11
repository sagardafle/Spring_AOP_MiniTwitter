/**
* The TweetStatsImpl program implements the TweetStats interface.
* It computes all the tweet stats as per the project requirement.
*
* @author  Sagar Dafle 
*/

package edu.sjsu.cmpe275.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import edu.sjsu.cmpe275.aop.TweetStats;

public class TweetStatsImpl implements TweetStats {

	public static int lengthOfLongestTweet = 0;
	public static String mostActiveFollower = null;
	public static String mostProductiveUser = null;

	// Declare a TreeMap to keep a track of the length of the total messages
	// tweeted by each user.
	public static Map<String, Integer> mostProductiveUserMap = new TreeMap<String, Integer>();

	// A treemap to keep track of the most active follower.
	public static Map<String, Set<String>> mostActiveFollowerUserMap = new TreeMap<String, Set<String>>();

	/**
	 * Resets all the stats for the tweetstats functionality. This resets the
	 * length of the longest Tweet,most active follower and the most productive
	 * user.
	 */
	@Override
	public void resetStats() {
		System.out.println("Resetting all the user stats");
		lengthOfLongestTweet = 0; // resets the counter for the length of the
									// longest Tweet.
		if (mostActiveFollowerUserMap.size() != 0)
			mostActiveFollowerUserMap.clear();
		mostActiveFollower = ""; // resets the counter for the most active
									// follower.
		if (mostProductiveUserMap.size() != 0)
			mostProductiveUserMap.clear();
		mostProductiveUser = ""; // resets the counter for the most productive
									// user.
	}

	/*
	 * getLengthOfLongestTweet Returns the he length of the longest tweet.
	 * 
	 * @return lengthOfLongestTweet. The length of the longest tweet.
	 */
	@Override
	public int getLengthOfLongestTweet() {
		// TODO Auto-generated method stub
		return lengthOfLongestTweet;
	}

	/*
	 * getMostActiveFollower Returns the most active follower for the Twitter
	 * service.
	 * 
	 * @return mostActiveFollower.Tthe most active follower for the Twitter
	 * service.
	 */
	@Override
	public String getMostActiveFollower() {
		System.out.println("Inside TweetStatsImpl getMostActiveFollower");
		return mostActiveFollower;
	}

	/*
	 * getMostProductiveUser Returns the most productive user for the Twitter
	 * service.
	 * 
	 * @return mostProductiveUser. The most productive user for the Twitter
	 * service.
	 */
	@Override
	public String getMostProductiveUser() {
		System.out.println("Inside TweetStatsImpl getMostProductiveUser");
		return mostProductiveUser;
	}

}
