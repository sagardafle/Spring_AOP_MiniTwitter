package edu.sjsu.cmpe275.aop;

import java.io.IOException;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
	private static ApplicationContext applnctxt;
	private static TweetService tweetserviceimplementation;
	private static TweetStats tweetstatsimplementation;

	public static void main(String[] args) throws IllegalArgumentException, IOException {
		try{
		applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
		tweetserviceimplementation = (TweetService) applnctxt.getBean("tweetServiceImpl");
		tweetstatsimplementation = (TweetStats) applnctxt.getBean("tweetStatsImpl");
		tweetstatsimplementation.resetStats();
		tweetserviceimplementation.tweet("Sagar", "This first message on Twitter!!");
		tweetserviceimplementation.tweet("Sagar", "This is my second message");
		tweetserviceimplementation.tweet("Sagar", "This is my third message on Twitter!!");
		tweetserviceimplementation.tweet("Vikas",
				"I am your big bro bro. I win dude. This is my second message on Twitter!!Do you get that ? or not ?????");
		tweetserviceimplementation.tweet("Vikas",
				"Nor hence hoped her after other known defer his. For county now sister engage had season better had waited. Occasional mrs interested far.");
		tweetserviceimplementation.tweet("Vikas", "For county now sister engage had whose.");
		tweetserviceimplementation.tweet("Tim",
				"The arc pun waves the nostalgia past the sitting professional. The spigot abides after a shaky tongue. The gate argues. The verse grants a .");
		tweetserviceimplementation.tweet("Tim",
				"The arc pun waves the nostalgia past the sitting professional. The spigot abides after a shaky tongue. The gate argues. The verse grants a .");
		tweetserviceimplementation.follow("GG", "foobar");
		tweetserviceimplementation.follow("Vikas", "Dada");
		tweetserviceimplementation.follow("GG", "Dafle");
		tweetserviceimplementation.follow("Vikas", "Sadhana");
		tweetserviceimplementation.follow("Pappa", "Sadhana");
		tweetserviceimplementation.follow("Pappa", "Sadhana");
		tweetserviceimplementation.follow("Pappa", "Sadhana");

		
		//tweetstatsimplementation.resetStats();

		System.out.println("LONGEST TWEET LENGTH " + tweetstatsimplementation.getLengthOfLongestTweet());
		System.out.println("Most Productive User is " + tweetstatsimplementation.getMostProductiveUser());
		System.out.println("Most Active Follower is " + tweetstatsimplementation.getMostActiveFollower());
		} catch (Exception e){
			System.out.println("Main ne throw feka re baba !!!");
		}
	}
}
