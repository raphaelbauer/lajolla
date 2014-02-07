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
