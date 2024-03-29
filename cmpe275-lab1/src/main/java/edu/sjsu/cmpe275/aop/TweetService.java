/**
 * @author daffy
 *
 */

package edu.sjsu.cmpe275.aop;

import java.io.IOException;

public interface TweetService {
	// Please do NOT change this file.
    /**
     * @param user . The name of user.
     * @param message . The tweet message by the user.
     * @throws IllegalArgumentException if the message is more than 140 characters as measured by string length.
     * @throws IOException if there is a network failure
     */
    void tweet(String user, String message) throws IllegalArgumentException, IOException;

    /**
     * @param follower . The follower for follow service.
     * @param followee . The followee for follow service.
     * @throws IOException if there is a network failure
     */
    void follow(String follower, String followee) throws IOException;
}
