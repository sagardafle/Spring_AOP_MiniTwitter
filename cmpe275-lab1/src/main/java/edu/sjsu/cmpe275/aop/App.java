package edu.sjsu.cmpe275.aop;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    private static ApplicationContext applnctxt;
	private static TweetServiceImpl tweetserviceimplementation;
	private static TweetStatsImpl tweetstatsimplementation;

	public static void main( String[] args ) throws IllegalArgumentException, IOException
    {
       applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
       tweetserviceimplementation = applnctxt.getBean("tweetServiceImpl",TweetServiceImpl.class);
       tweetserviceimplementation.tweet("Sagar", "This is my first message on Twitter!!");
       tweetserviceimplementation.tweet("Sagar", "This is my second message on Twitter!!");
       tweetserviceimplementation.tweet("Sagar", "This is my third message on Twitter!!");
       tweetserviceimplementation.follow("Sagar", "foobar");
       
       tweetstatsimplementation = applnctxt.getBean("tweetStatsImpl",TweetStatsImpl.class);
       tweetstatsimplementation.getLengthOfLongestTweet();
       tweetstatsimplementation.getMostActiveFollower();
       tweetstatsimplementation.getMostProductiveUser();
    }
}
