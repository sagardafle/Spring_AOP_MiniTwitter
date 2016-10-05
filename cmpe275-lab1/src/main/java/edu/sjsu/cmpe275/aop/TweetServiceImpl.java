package edu.sjsu.cmpe275.aop;

import java.io.IOException;
import java.util.HashMap;



public class TweetServiceImpl implements TweetService {

	HashMap<String,String> tweetHashMap = new HashMap<>();
	@Override
	public void tweet(String user, String message) throws IllegalArgumentException, IOException {
		if(message.length()>140) throw new IllegalArgumentException();
	}

	@Override
	public void follow(String follower, String followee) throws IOException {
			System.out.println(" "+follower+" is now following "+followee);
	}

}
