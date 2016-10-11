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
import java.util.TreeMap;

import edu.sjsu.cmpe275.aop.TweetStats;

public class TweetStatsImpl implements TweetStats {

	public static int lengthOfLongestTweet = 0;
	public static String mostActiveFollower = "";
	public static String mostProductiveUser = "";

	// Declare a TreeMap to keep a track of the length of the total messages
	// tweeted by each user.
	public static Map<String, Integer> mostProductiveUserMap = new TreeMap<String, Integer>();
	
	//A treemap to keep track of the most active follower.
	public static Map<String, Integer> mostActiveFollowerUserMap = new TreeMap<String, Integer>();

	/**
	 * Resets all the stats for the tweetstats functionality.
	 * This resets the length of the longest Tweet,most active follower and the most productive user.
	 */
	@Override
	public void resetStats() {
		System.out.println("Resetting all the user stats");
	}

	
	/* (non-Javadoc)
	 * @see edu.sjsu.cmpe275.aop.TweetStats#getLengthOfLongestTweet()
	 * @return  the length of the longest tweet.
	 */
	@Override
	public int getLengthOfLongestTweet() {
		// TODO Auto-generated method stub
		return lengthOfLongestTweet;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.sjsu.cmpe275.aop.TweetStats#getMostActiveFollower()
	 * @return  the most active follower for the Twitter service.
	 */
	@Override
	public String getMostActiveFollower() {
		System.out.println("Inside TweetStatsImpl getMostActiveFollower");
		return mostActiveFollower;
	}

	
	/* (non-Javadoc)
	 * @see edu.sjsu.cmpe275.aop.TweetStats#getMostProductiveUser()
	 * @return  the most productive user for the Twitter service.
	 */
	@Override
	public String getMostProductiveUser() {
		System.out.println("Inside TweetStatsImpl getMostProductiveUser");
		return mostProductiveUser;
	}

}
