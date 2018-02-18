package com.mskaryszewski.awaitility;

import org.junit.Test;

import java.time.LocalTime;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.with;
import static org.awaitility.pollinterval.FibonacciPollInterval.fibonacci;

/**
 * Presents usefullness of Awaitility library which verifies
 * expectations of an asynchronous system.
 *
 * Test description:
 * Waits {@link #MAX_TEST_DURATION} minutes for {@link #iteration} to reach {@link #EXPECTED_VALUE}.
 * Time interval between assertions will be determined by Fibonacci sequence starting from {@link #FIBONACCI_OFFSET}
 *
 * @see <a href="https://en.wikipedia.org/wiki/Fibonacci_number">Fibonacci sequence</a>
 * @see <a href="https://github.com/awaitility/awaitility">awaitility</a>
 */
public class AwaitilityTest {

    /**
     * Utility variable enabling presentation of Awaitility library.
     */
    private int iteration                  = 0;
    private final int DIVISOR              = 2;
    private final int EXPECTED_VALUE       = 5;

    private final int MAX_TEST_DURATION    = 1;
    private final int FIBONACCI_OFFSET     = 3;

    @Test
    public void awaitilityTest() {
        System.out.printf("Test started: %s%n", currentTime());
        with()
                .ignoreException(RuntimeException.class)                                           // when assertion throws RuntimeException, suppress it and do not exit the test
                .pollInterval(fibonacci().with().timeUnit(SECONDS).and().offset(FIBONACCI_OFFSET)) // determines time interval between consecutive assertions. By default assertion is called every 100ms
                .await()
                .atMost(MAX_TEST_DURATION, MINUTES)                                                // defines how long we should wait for the test to be successfull
                .untilAsserted(() ->
                        assertThat(getExpectedValue())
                            .isEqualTo(EXPECTED_VALUE));
    }

    /**
     *
     * Returns incremented value of {@link #iteration}
     * Throws {@link RuntimeException} when {@link #iteration} is a multiple of {@link #DIVISOR}
     * @return incremented {@link #iteration}
     */
    private int getExpectedValue() {
        System.out.printf("Current time: %s, iteration #%d%n",
                currentTime(),
                ++iteration);

        if(iteration % DIVISOR == 0)
            throw new RuntimeException();
        return iteration;
    }

    /**
     * Prints current time to present delay between consecutive iterations.
     * @return currentTime as String
     */
    private String currentTime() {
        return LocalTime.now().toString();
    }
}