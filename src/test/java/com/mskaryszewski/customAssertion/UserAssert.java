package com.mskaryszewski.customAssertion;

import org.assertj.core.api.AbstractAssert;

/**
 * Custom assertJ assertion.
 * @author Michal Skaryszewski
 *
 */
public class UserAssert extends AbstractAssert<UserAssert, User> {

	public UserAssert userDavid() {
		isNotNull();
		String errorMassage = "\nExpecting User's name <David>\n but was:\n <%s>";

		String name = actual.getName();
		if (!"David".equals(name)) {
			failWithMessage(errorMassage, name);
		}
		return this;
	}

	public UserAssert(User actual) {
		super(actual, UserAssert.class);
	}

	public static UserAssert assertThat(User actual) {
		return new UserAssert(actual);
	}
}