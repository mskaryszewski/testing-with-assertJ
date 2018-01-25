package com.mskaryszewski.customAssertion;

/**
 * Custom class to provide a static assertThat method that returns our assertion class
 * @author Michal Skaryszewski
 *
 */
public class Assertions {
	public static UserAssert assertThat(User actual) {
		return new UserAssert(actual);
	}
}