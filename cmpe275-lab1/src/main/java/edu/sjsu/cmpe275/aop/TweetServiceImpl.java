package edu.sjsu.cmpe275.aop;

import java.io.IOException;
import java.util.HashMap;

public class TweetServiceImpl implements TweetService {

	HashMap<String, String> tweetHashMap = new HashMap<>();

	@Override
	public void tweet(String user, String message) throws IllegalArgumentException, IOException {
		System.out.println("Inside the tweet message of TweetServiceImpl");
		if (message.length() > 140)
			throw new IllegalArgumentException();
		// else throw new IOException();
	}

	@Override
	public void follow(String follower, String followee) throws IOException {
		System.out.println(follower + " is now following " + followee);
		// throw new IOException();
	}

}
