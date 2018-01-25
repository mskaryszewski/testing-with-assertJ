package com.mskaryszewski;

import static com.mskaryszewski.customAssertion.UserAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.in;
import static org.assertj.core.api.Assertions.notIn;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.regex.Pattern;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.mskaryszewski.customAssertion.User;

import lombok.Data;

/**
 * Basic test class showing convenience of testing with AssertJ.
 * 
 * @author  Michal Skaryszewski
 * @version 0.1
 * @since   0.1
 *
 */
public class AssertJTest {

	private final String ONE   = "ONE";
	private final String TWO   = "TWO";
	private final String THREE = "THREE";
	private final int TEN      = 10;
	private final int FIVE     = 5;
	
	/**
	 * Basic tests of ints with AssertJ.
	 */
	@Test
	public void integerTest() {
		assertThat(TEN)
			.isGreaterThan(FIVE)
			.isNotNegative()
			.isNotZero();
	}

	/**
	 * Basic tests of Strings with AssertJ.
	 */
	@Test
	public void stringTest() {
		final String NAME = "Michal";
		assertThat(NAME)
			.as("Name is %s", NAME)  // prints a nice message when fails
			.isNotNull()
			.isNotEmpty()
			.isEqualTo(NAME)
			.containsOnlyOnce("ic")
			.doesNotContain("mic")
			.endsWith("al")
			.matches(Pattern.compile(NAME));
	}

	/**
	 * Basic tests of {@link java.util.Collection} with AssertJ.<br>
	 * First assertion error interrupts further checks.
	 * 
	 * @see com.mskaryszewski.AssertJTest#softAssertionTest() Collection test showing all errors at the end of test case
	 */
	@Test
	public void collectionTest() {
		final List<String> strings = Lists.newArrayList(ONE, TWO, THREE);
		assertThat(strings)
			.contains(ONE)
			.contains(TWO, atIndex(1))
			.containsOnlyOnce(ONE, TWO)
			.containsExactly(ONE, TWO, THREE)
			.containsSequence(TWO, THREE)
			.doesNotHaveDuplicates()
			.doesNotContainNull()
			.isNotNull()
			.isNotEmpty()
			.doesNotHaveDuplicates()
			.hasSize(3)
			.size()
				.isGreaterThan(1)
				.isLessThanOrEqualTo(3)
				.isBetween(2, 4)
				.returnToIterable()
			.doesNotContain("FOUR");
	}

	/**
	 * Basic tests of filtered collections with AssertJ.
	 */
	@Test
	public void filtersWithCollectionsTest() {

		/**
		 * Class heping to show usage of AssertJ's filters.
		 * 
		 * @author Michal Skaryszewski
		 *
		 */
		@Data
		final class Person {
			private final String firstName;
			private final String secondName;
		}

		final Person davidDuchowny  = new Person("David", "Duchowny");
		final Person davidCarradine = new Person("David", "Carradine");
		final Person davidHarbour   = new Person("David", "Harbour");
		final Person bradPitt       = new Person("Brad", "Pitt");

		final List<Person> actors = Lists.newArrayList(
				davidDuchowny,
				davidCarradine,
				davidHarbour,
				bradPitt);

		assertThat(actors)
			.filteredOn("firstName",  "David")
			.filteredOn("firstName",  notIn("Marc", "Kirk"))
			.filteredOn("secondName", in("Duchowny", "Carradine"))
			.containsOnly(davidDuchowny, davidCarradine);
	}


	/**
	 * Basic tests of {@link java.time.LocalDate} with AssertJ.
	 */
	@Test
	public void dateTest() {
		final LocalDate beginningOfYear2000 = LocalDate.of(2000, Month.JANUARY, 1);
		final LocalDate beginningOfYear2001 = LocalDate.of(2001, Month.JANUARY, 1);
		final LocalDate beginningOfYear2002 = LocalDate.of(2002, Month.JANUARY, 1);

		assertThat(beginningOfYear2001)
			.isNotNull()
			.isStrictlyBetween(beginningOfYear2000, beginningOfYear2002)
			.isNotSameAs(beginningOfYear2000);
	}

	/**
	 * Basic tests of exceptions with AssertJ.
	 */
	@Test
	public void exceptionTest() {
		assertThatThrownBy(() -> { throw new Exception("Exception encountered!"); })
			.isInstanceOf(Exception.class)
			.hasMessageContaining("Exception");

		assertThatExceptionOfType(Exception.class)
			.isThrownBy(() -> { throw new Exception("Exception encountered!"); })
			.withMessage("%s", "Exception encountered!")
			.withMessageContaining("Exception")
			.withNoCause();
	}

	/**
	 * Basic tests of {@link java.util.Collection} and {@link org.assertj.core.api.SoftAssertions} with AssertJ.<br>
	 * In case there are multiple assertion errors,
	 * they will be collected and thrown at the end of this test case.<br>
	 * 
	 * @see com.mskaryszewski.AssertJTest#collectionTest() Collection Test Breaking After First Error
	 */
	@Test
	public void softAssertionTest() {
		final List<String> strings = Lists.newArrayList(ONE, TWO, THREE);
		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(strings)
			.contains(ONE)
			.containsOnlyOnce(ONE, TWO)
			.containsExactly(ONE, TWO, THREE)
			.doesNotContainNull()
			.isNotNull()
			.isNotEmpty()
			.doesNotHaveDuplicates()
			.hasSize(3)
			.size()
				.isGreaterThan(1)
				.isLessThanOrEqualTo(3)
				.isBetween(2, 4)
				.returnToIterable()
			.doesNotContain("FOUR");
		softly.assertAll();
	}

	/**
	 * Basic tests of custom assertion with AssertJ.
	 */
	@Test
	public void customAssertonTest() {
		User user = new User("David");
		assertThat(user)
			.userDavid();
	}
}
