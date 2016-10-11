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

	/**
	 * Main method as a start point to the application.
	 * 
	 * @param args
	 *            Command line arguments(null for this app)
	 * @throws IllegalArgumentException
	 *             for message length boundaries
	 * @throws IOException
	 *             fornetwork issues.
	 */
	public static void main(String[] args) throws IllegalArgumentException, IOException {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
		TweetService tweeter = (TweetService) ctx.getBean("tweetService");
		TweetStats stats = (TweetStats) ctx.getBean("tweetStats");

		try {
			stats.resetStats();
			tweeter.tweet("Sagar", "This first message on Twitter!!");
			tweeter.tweet("Sagar", "This is my second message");
			tweeter.tweet("Sagar", "This is my third message on Twitter!!");
			tweeter.tweet("Vikas",
					"I am your big bro bro. I win dude. This is my second message on Twitter!!Do you get that ? or not ?????");
			tweeter.tweet("Vikas",
					"Nor hence hoped her after other known defer his. For county now sister engage had season better had waited. Occasional mrs interested far.");
			tweeter.tweet("Vikas", "For county now sister engage had whose.");
			tweeter.tweet("Tim",
					"The arc pun waves the nostalgia past the sitting professional. The spigot abides after a shaky tongue. The gate argues. The verse grants a .");
			tweeter.tweet("Tim",
					"The arc pun waves the nostalgia past the sitting professional. The spigot abides after a shaky tongue. The gate argues. The verse grants a .");
			// tweeter.follow("GG", "foobar");
			// tweeter.follow("GG", "Dafle");
			tweeter.follow("gg", "foobar");
			tweeter.follow("qwerty", "Sadhana");
			tweeter.follow("qwerty", "ee");
			tweeter.follow("qwerty", "asap");
			tweeter.follow("qwerty", "tt");
			// tweeter.follow("Vikas", "Sadhana");
			// tweeter.follow("Pappa", "Sadhana");
			// tweeter.follow("Pappa", "Pappa");
			// tweeter.follow("Pappa", "Sadhana");
		} catch (Exception e) {
			// e.printStackTrace();
		}

		System.out.println("#####Most productive user:####### " + stats.getMostProductiveUser());
		System.out.println("#####Most active follower:##### " + stats.getMostActiveFollower());
		System.out.println("#####Length of the longest tweet:##### " + stats.getLengthOfLongestTweet());
		ctx.close();

	}
}
