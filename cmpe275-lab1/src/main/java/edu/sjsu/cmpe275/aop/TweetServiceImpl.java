package edu.sjsu.cmpe275.aop;

/**
 * author daffy
 */
import java.io.IOException;
import java.util.Random;

public class TweetServiceImpl implements TweetService {

    /***
     * Following is a dummy implementation.
     * You can tweak the implementation to suit your need, but this file is NOT part of the submission.
     */
	/**
	 * Tweet Method.
	 */
	@Override
    public void tweet(String user, String message) throws IllegalArgumentException, IOException {
		System.out.println("Inside the tweet message of TweetServiceImpl");
		if (message.length() > 140)
			throw new IllegalArgumentException();
		Random rand = new Random();
		int temp = rand.nextInt(100 / 2);
		System.out.println("Temp Tweet== " + temp);
		if (temp >1) {
			///System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~Throwing an IOexception " + temp);
			throw new IOException();
		} 
    	System.out.printf("User %s tweeted message: %s\n", user, message);
    }

	@Override
    public void follow(String follower, String followee) throws IOException {
		
		Random rand = new Random();
		int temp = rand.nextInt(100 / 2);
		System.out.println("Temp Follow== " + temp);
//		if (temp >15) {
//			///System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~Throwing an IOexception " + temp);
//			throw new IOException();
//		} 
       	System.out.printf("User %s followed user %s \n", follower, followee);
    }

}
