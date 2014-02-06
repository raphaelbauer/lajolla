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
 *//*
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
 *//*
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
 *//*
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
package com.raphaelbauer.lajolla.transformation.protein;

import java.util.List;

import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.HetatomImpl;
import org.biojava.bio.structure.StructureException;

import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.utilities.Utility;


/**
 * AngleSequences gets a pdb file with a chain and converts it to
 * a string that can be indexed further on.

 * @author petmoo
 *
 */
public class BetterOptimizedPhiPsiTranslator
	implements IResidueToStringTransformer { 

	
	private final int numberOfCharactersInStringCorrespondToOneResidue = 1;
	
	/** the translation table. */
	private char[] translationTableFromAngleToCharacter =
		{'B','H','L','O','_'};
	
	// B betasheet
	// H helix
	// L left handed helix
	// O outsider
	// _ phi psi calculation not possible
	
	public String getStringMeaningNoResultAtThatPosition() {
		
		return new Character(translationTableFromAngleToCharacter[4]).toString();
	}



	/**
	 * @param stepSizeForDiscreteAngleTransformation the step size
	 */
	public BetterOptimizedPhiPsiTranslator() {
		

		
		
	}

	

	

	
	/**
	 * gets a group of aminos (== chain), calculates the phi psi and transforms
	 * the phi psi into the characters needed for hashing / indexing...
	 * 
	 * @param aminos the group of aminos
	 * @return string with transformed characters
	 */
	public final String getStringFromResidues(final List<Group> aminos) {
		
		
		// for the return...
		StringBuffer translatedAngleSeq = new StringBuffer();
        
		// expected lengt
		int aminoLength = aminos.size();
		
        translatedAngleSeq.ensureCapacity(aminoLength);
		
        //first amino
		HetatomImpl a;
		//second amino
		HetatomImpl b;
		//third amino
		HetatomImpl c;
		
        double phi;
        double psi;
        

        for (int i = 0; i < aminoLength; i++) {
        	
            phi = 360d; //360 does not exist normally in eta theta calculation
        	psi = 360d; // ---"---
            
        	// amino acid in the middle...
            b = (HetatomImpl) aminos.get(i);
            
            
            // does the a exist in that case?
            if (i > 0) {
            	a = (HetatomImpl) aminos.get(i - 1);
            	
	            try {
	            	phi = Utility.getPhi(a, b);
	            	
	            } catch (StructureException e) {
	            	//e.printStackTrace();
	            }
            }
            
            
            //does the c exist in that case?
            if (i < aminoLength - 1) {
            	
                c =  (HetatomImpl) aminos.get(i + 1);
                
                try {
                    psi = Utility.getPsi(b, c);
                    
                } catch (StructureException e) {
                	//e.printStackTrace();
                }
            }
            
            
            
            
            
            if (phi == 360 || psi == 360) {
            	//translatedAngleSeq.append("H");
            	
            	translatedAngleSeq.append(translationTableFromAngleToCharacter[4]);
            	//translatedAngleSeq.append("x");
            	
            	
            } else {
            	
            	int thisCombinationsPosition 
            		= angleToPositionTranslator(phi, psi);
            	
            	//System.out.println("phi " + phi);
            	//System.out.println("psi " + psi);
            	
            	//System.out.println("int is " + thisCombinationsPosition);
            
            	translatedAngleSeq.append(
            		positionToCharacterTranslator(
            				thisCombinationsPosition));
            }
            
        }

		return translatedAngleSeq.toString();
	}

	
	
	
	
	/**
	 * Transform a angle into a character.
	 * Angle is from -180 to +180
	 * 
	 * 
	 * @param angle angle to convert
	 * @return the char that represents this angle
	 */
	public final int angleToPositionTranslator(
			final double phi,
			final double psi) {
		
		
		////////////////////////////////////////////////////////////////////////
		// Here comes the magic:
		////////////////////////////////////////////////////////////////////////
		
		
		
		
		//|| (phi > 135)
		if ((((phi < -20 )  ))				
				&& ((psi > 45 ) || (psi < -160 ))){
			// alpha helices
			return 0;
			
			
			
		} else if (((phi < 0) && (phi > -150))
				&& ((psi < 45) && ( psi > -80)) ) {
			//beta sheets
			
			return 1;
			
			
		}  else if (((phi > 20) && (phi < 120)) 
				&& ((psi < 100) && (psi > 40))){
			// left handed helices
			return 2;
			
		}
			

		else {
			// an outsider
			return 3;
			
			
		}
		
		
	}
	
	
	
	
	/**
	 * Transform a angle into a character.
	 * Angle is from -180 to +180
	 * 
	 * 
	 * @param angle angle to convert
	 * @return the char that represents this angle
	 */
	public final char positionToCharacterTranslator(
			final int positionAsDiscretStep) {
		
		return translationTableFromAngleToCharacter[positionAsDiscretStep];
	}
	
	
	
	
	
	
	public int getNumberOfCharactersInStringCorrespondToOneResidue() {
		return numberOfCharactersInStringCorrespondToOneResidue;
	}

	
	
}
