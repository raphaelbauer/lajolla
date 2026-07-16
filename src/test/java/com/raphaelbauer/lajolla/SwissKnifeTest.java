package com.raphaelbauer.lajolla;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import com.raphaelbauer.lajolla.utilities.SwissKnife;

public class SwissKnifeTest {
	
	
	@Test
	public void testSCOPParsing() {
		
		String pathToFile = "src/test/resources/d1phka_.ent";
		
		String scopString = SwissKnife.getSCOPClassFromASTRALFile(pathToFile);
		
    assertThat(scopString, equalTo("d.144.1.7"));
	
	}

}
