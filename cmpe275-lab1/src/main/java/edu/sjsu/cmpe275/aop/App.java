package edu.sjsu.cmpe275.aop;

import java.io.IOException;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    private static ApplicationContext applnctxt;
	private static TweetService tweetserviceimplementation;
	private static TweetStats tweetstatsimplementation;

	public static void main( String[] args ) throws IllegalArgumentException, IOException
    {
       applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
       tweetserviceimplementation = (TweetService) applnctxt.getBean("tweetServiceImpl");
       //tweetstatsimplementation.resetStats();
       tweetserviceimplementation.tweet("Sagar", "This first message on Twitter!!");
       tweetserviceimplementation.tweet("Sagar", "This is my second message");
       tweetserviceimplementation.tweet("Sagar", "This is my third message on Twitter!!");
       tweetserviceimplementation.tweet("Vikas", "I am your big bro bro. I win dude. This is my second message on Twitter!!Do you get that ? or not ?????");
       
      // tweetserviceimplementation.tweet("Sagar", "qwertyuiopasdfghjklzxcvbnmqwertyuiopsdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnm");
       tweetserviceimplementation.follow("Sagar", "foobar");
       
       tweetstatsimplementation = (TweetStats) applnctxt.getBean("tweetStatsImpl");
      
       System.out.println("LONGEST TWEET LENGTH "+tweetstatsimplementation.getLengthOfLongestTweet());
       tweetstatsimplementation.getMostActiveFollower();
       tweetstatsimplementation.getMostProductiveUser();
    }
}
