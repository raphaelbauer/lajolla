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
package com.raphaelbauer.lajolla;

import com.raphaelbauer.lajolla.utilities.SCOP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class SCOPTest {

  @Test
  public void testSCOPParsingValid() {

    SCOP scop = new SCOP("d.144.1.7");

    assertThat(scop.getSCOPClass(), equalTo("d"));
    assertThat(scop.getFold(), equalTo("144"));
    assertThat(scop.getSuperFamily(), equalTo("1"));
    assertThat(scop.getFamily(), equalTo("7"));

  }

  @Test
  public void testSCOPParsingInValid() {

    SCOP scop = new SCOP("NOSCOP!");

    assertThat(scop.getSCOPClass(), equalTo(""));
    assertThat(scop.getFold(), equalTo(""));
    assertThat(scop.getSuperFamily(), equalTo(""));
    assertThat(scop.getFamily(), equalTo(""));

  }
}
