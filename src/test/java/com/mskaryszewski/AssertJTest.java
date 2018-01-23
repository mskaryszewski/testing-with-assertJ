package com.mskaryszewski;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class AssertJTest {

	@Test
	public void integerTest() {
		final List<Integer> numbers = Lists.newArrayList(1, 2, 3);
		assertThat(numbers).hasSize(3);
	}

}
