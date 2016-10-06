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
	// // Declare a TreeMap to store the user-tweets message.
	// public static TreeMap<String, ArrayList<String>> userMessagesMap = new
	// TreeMap<String, ArrayList<String>>();
	// // ArrayList to store the user tweet messages.
	// public static ArrayList<String> messagelist = new ArrayList<String>();

	// Declare a HashMap to keep a track of the length of the total messages
	// tweeted by each user.
	public static Map<String, Integer> mostProductiveUserMap = new TreeMap<String, Integer>();

	@Override
	public void resetStats() {
		// TODO Auto-generated method stub
		lengthOfLongestTweet = 0; // resets the counter for the length of the
									// longest Tweet.
		mostActiveFollower = ""; // resets the counter for the most active
									// follower.
		mostProductiveUserMap.clear();
		mostProductiveUser = ""; // resets the counter for the most productive
									// user.
	}

	@Override
	public int getLengthOfLongestTweet() {
		// TODO Auto-generated method stub
		return lengthOfLongestTweet;
	}

	@Override
	public String getMostActiveFollower() {
		System.out.println("Inside TweetStatsImpl getMostActiveFollower");
		return mostActiveFollower;
	}

	@Override
	public String getMostProductiveUser() {
		// TODO Auto-generated method stub
		return mostProductiveUser;
	}

}
