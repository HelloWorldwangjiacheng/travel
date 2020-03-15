package com.exam.travel.config;


import com.exam.travel.model.User;

/**
 * @author w1586
 */

public class UserContext {
	
	private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

	public static void setUser(User user) {
		userHolder.set(user);
	}

	public static User getUser() {
		return userHolder.get();
	}
}
