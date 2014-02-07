package com.raphaelbauer.lajolla.utilities;

import com.raphaelbauer.lajolla.utilities.Utility;
import junit.framework.TestCase;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Assert;
import static org.junit.Assert.assertThat;

public class UtilityTest extends TestCase {

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
