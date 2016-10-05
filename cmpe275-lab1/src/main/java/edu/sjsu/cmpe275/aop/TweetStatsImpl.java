package edu.sjsu.cmpe275.aop;
import edu.sjsu.cmpe275.aop.TweetStats;

public class TweetStatsImpl implements TweetStats {
	
	public int lengthOfLongestTweet = 0;
	public String mostActiveFollower = "";
	public String mostProductiveUser = "";

	@Override
	public void resetStats() {
		// TODO Auto-generated method stub
		lengthOfLongestTweet = 0; //resets the counter for the length of the longest Tweet.
		mostActiveFollower = ""; //resets the counter for the most active follower.
		mostProductiveUser = ""; //resets the counter for the most productive user.
	}

	@Override
	public int getLengthOfLongestTweet() {
		// TODO Auto-generated method stub
		return lengthOfLongestTweet;
	}

	@Override
	public String getMostActiveFollower() {
		// TODO Auto-generated method stub
		return mostActiveFollower;
	}

	@Override
	public String getMostProductiveUser() {
		// TODO Auto-generated method stub
		return mostProductiveUser;
	}

}
