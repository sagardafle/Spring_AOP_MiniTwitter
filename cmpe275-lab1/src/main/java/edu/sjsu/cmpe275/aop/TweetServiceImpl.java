package edu.sjsu.cmpe275.aop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class TweetServiceImpl implements TweetService {

	HashMap<String, String> tweetHashMap = new HashMap<>();
	static int i = 0;

	@Override
	public void tweet(String user, String message) throws IllegalArgumentException, IOException {
		System.out.println("Inside the tweet message of TweetServiceImpl");
		if (message.length() > 140)
			throw new IllegalArgumentException();
		Random rand = new Random();
		int temp = rand.nextInt(100 / 2);
		System.out.println("Temp == " + temp);
		if (temp > 1) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~Throwing an IOexception " + temp);
			throw new IOException();
		}
	}

	@Override
	public void follow(String follower, String followee) throws IOException {

		// if(i==0){
		// System.out.println("=====VALUE OF i======"+i);
		// i+=1;
		// throw new IOException();
		// }
		System.out.println(follower + " is now following " + followee);

	}

}
