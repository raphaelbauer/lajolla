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

import junit.framework.TestCase;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import com.raphaelbauer.lajolla.utilities.SwissKnife;

public class SwissKnifeTest {
	
	
	@Test
	public void testSCOPParsing() {
		
		String pathToFile = "src/test/resources/d1phka_.ent";
		
		String scopString = SwissKnife.getSCOPClassFromASTRALFile(pathToFile);
		
    assertThat(scopString, equalTo("d.144.1.7"));
	
	}

}
