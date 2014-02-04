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
package ra.lajolla.transformation.rna.suite;

import java.util.List;

import org.biojava.bio.structure.Group;

import ra.lajolla.transformation.IResidueToStringTransformer;


/**
 * 
 * Only used to determine the non-existend suites...
 * 
 * => conversion is done with suiterna.py
 *
 */
public class DummySuiteTransformer 
	implements IResidueToStringTransformer { 
	
	



	public String getStringMeaningNoResultAtThatPosition() {
		

		return "NA";
	}

	/**
	 * @param stepSizeForDiscreteAngleTransformation the step size
	 */
	public DummySuiteTransformer() {
		

		
		
	}

	

	
	/**
	 * gets a group of aminos (== chain), calculates the phi psi and transforms
	 * the phi psi into the characters needed for hashing / indexing...
	 * 
	 * @param aminos the group of aminos
	 * @return string with transformed characters
	 */
	public final String getStringFromResidues(final List<Group> nucleotidesAndSimilarMolecules) {
		
		return null;
	}


	public int getNumberOfCharactersInStringCorrespondToOneResidue() {
		// TODO Auto-generated method stub
		return 2;
	}

	
	
	
	
	
}
