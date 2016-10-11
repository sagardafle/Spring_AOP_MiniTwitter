package sagardafle.minitwitteraop.cmpe275_lab1;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.sjsu.cmpe275.aop.TweetService;
import edu.sjsu.cmpe275.aop.TweetStats;

public class TweetTest {

	/***
	 * These are dummy test cases. You may add test cases based on your own
	 * need.
	 */

	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
	TweetService tweeter = (TweetService) ctx.getBean("tweetService");
	TweetStats stats = (TweetStats) ctx.getBean("tweetStats");

	@Test
	public void testOne() {
		stats.resetStats();
		try {
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
			tweeter.follow("GG", "foobar");
			tweeter.follow("Vikas", "Dada");
			tweeter.follow("GG", "Dafle");
			tweeter.follow("Vikas", "Sadhana");
			tweeter.follow("Pappa", "Sadhana");
			tweeter.follow("Pappa", "Sadhana");
			tweeter.follow("Pappa", "Sadhana");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("**********Running test case#1***************");
		System.out.println("Most productive user " + stats.getMostProductiveUser());
		System.out.println("Most active follower: " + stats.getMostActiveFollower());
		System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweet());

        assertEquals("Tim",stats.getMostProductiveUser());
		assertEquals("Pappa", stats.getMostActiveFollower());
		assertEquals(140, stats.getLengthOfLongestTweet());
		
		System.out.println("**********End of test case#1***************");
	}

	@Test
	public void testCaseTwo() {
		
	}
}