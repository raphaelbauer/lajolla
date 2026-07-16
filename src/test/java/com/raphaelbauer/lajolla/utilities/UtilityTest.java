package com.raphaelbauer.lajolla.utilities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.raphaelbauer.lajolla.utilities.Utility;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UtilityTest {

  @Test
  public void testScoring() {

    assertThat(
            Utility.getNumberOfDifferentCharsInThisString(""),
            equalTo(0));

    assertThat(
            Utility.getNumberOfDifferentCharsInThisString("!!!!!!"),
            equalTo(1));

    assertThat(
            Utility.getNumberOfDifferentCharsInThisString("!#!#!#!#!#!"),
            equalTo(2));

    assertThat(
            Utility.getNumberOfDifferentCharsInThisString("123456"),
            equalTo(6));

  }

}
