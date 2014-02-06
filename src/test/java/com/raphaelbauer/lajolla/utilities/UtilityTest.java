/*
 * Copyright (c) Raphael A. Bauer (mechanical.bauer@gmail.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 */
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
