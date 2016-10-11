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
	 * @param args Command line arguments(null for this app)
	 * @throws IllegalArgumentException for message length boundaries
	 * @throws IOException fornetwork issues.
	 */
	public static void main(String[] args) throws IllegalArgumentException, IOException {
		/**
		 * 
		 
//		try{
		applnctxt = new ClassPathXmlApplicationContext("aop-bean.xml");
		tweetserviceimplementation = (TweetService) applnctxt.getBean("tweetServiceImpl");
		tweetstatsimplementation = (TweetStats) applnctxt.getBean("tweetStatsImpl");
		tweetstatsimplementation.resetStats();
		tweetserviceimplementation.tweet("Sagar", "This first message on Twitter!!");
//		tweetserviceimplementation.tweet("Sagar", "This is my second message");
//		tweetserviceimplementation.tweet("Sagar", "This is my third message on Twitter!!");
//		tweetserviceimplementation.tweet("Vikas",
//				"I am your big bro bro. I win dude. This is my second message on Twitter!!Do you get that ? or not ?????");
//		tweetserviceimplementation.tweet("Vikas",
//				"Nor hence hoped her after other known defer his. For county now sister engage had season better had waited. Occasional mrs interested far.");
//		tweetserviceimplementation.tweet("Vikas", "For county now sister engage had whose.");
//		tweetserviceimplementation.tweet("Tim",
//				"The arc pun waves the nostalgia past the sitting professional. The spigot abides after a shaky tongue. The gate argues. The verse grants a .");
//		tweetserviceimplementation.tweet("Tim",
//				"The arc pun waves the nostalgia past the sitting professional. The spigot abides after a shaky tongue. The gate argues. The verse grants a .");
//		tweetserviceimplementation.follow("GG", "foobar");
//		tweetserviceimplementation.follow("Vikas", "Dada");
//		tweetserviceimplementation.follow("GG", "Dafle");
//		tweetserviceimplementation.follow("Vikas", "Sadhana");
//		tweetserviceimplementation.follow("Pappa", "Sadhana");
//		tweetserviceimplementation.follow("Pappa", "Sadhana");
//		tweetserviceimplementation.follow("Pappa", "Sadhana");

		
		//tweetstatsimplementation.resetStats();

		
//		} catch (Exception e){
			//System.out.println("MAIN CAUGHT EXCEPTION!!!");
			System.out.println("LONGEST TWEET LENGTH " + tweetstatsimplementation.getLengthOfLongestTweet());
			System.out.println("Most Productive User is " + tweetstatsimplementation.getMostProductiveUser());
			System.out.println("Most Active Follower is " + tweetstatsimplementation.getMostActiveFollower());
//		}
	
	*/

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        TweetService tweeter = (TweetService) ctx.getBean("tweetService");
        TweetStats stats = (TweetStats) ctx.getBean("tweetStats");

        try {
        	tweeter.tweet("Sagar", "This first message on Twitter!!");
//			tweeter.tweet("Sagar", "This is my second message");
////			tweeter.tweet("Sagar", "This is my third message on Twitter!!");
//			tweeter.tweet("Vikas", "I am your big bro bro. I win dude. This is my second message on Twitter!!Do you get that ? or not ?????");
//			tweeter.tweet("Vikas",
//					"Nor hence hoped her after other known defer his. For county now sister engage had season better had waited. Occasional mrs interested far.");
//			tweeter.tweet("Vikas", "For county now sister engage had whose.");
//			tweeter.tweet("Tim",
//					"The arc pun waves the nostalgia past the sitting professional. The spigot abides after a shaky tongue. The gate argues. The verse grants a .");
//			tweeter.tweet("Tim",
//					"The arc pun waves the nostalgia past the sitting professional. The spigot abides after a shaky tongue. The gate argues. The verse grants a .");
			//tweeter.follow("GG", "foobar");
			//tweeter.follow("Vikas", "Dada");
//			tweeter.follow("GG", "Dafle");
//			tweeter.follow("GG", "GG");
//			tweeter.follow("Vikas", "Sadhana");
//			tweeter.follow("Vikas", "Sadhana");
//			tweeter.follow("Pappa", "Sadhana");
//			tweeter.follow("Pappa", "Sadhana");
//			tweeter.follow("Pappa", "Sadhana");
        } catch (Exception e) {
            //e.printStackTrace();
        }

        System.out.println("#####Most productive user:####### " + stats.getMostProductiveUser());
        System.out.println("#####Most active follower:##### " + stats.getMostActiveFollower());
        System.out.println("#####Length of the longest tweet:##### " + stats.getLengthOfLongestTweet());
        ctx.close();
		
	}
}
