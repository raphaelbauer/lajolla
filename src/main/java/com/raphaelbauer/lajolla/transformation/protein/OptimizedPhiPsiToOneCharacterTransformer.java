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
package com.raphaelbauer.lajolla.transformation.protein;

import java.util.ArrayList;
import java.util.List;

import org.biojava.bio.structure.AminoAcid;
import org.biojava.bio.structure.Calc;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;

import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;


/**
 * AngleSequences gets a pdb file with a chain and converts it to
 * a string that can be indexed further on.

 * @author petmoo
 *
 */
public class OptimizedPhiPsiToOneCharacterTransformer 
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
		
		//System.out.println(new Character(translationTableFromAngleToCharacter[5]).toString());
		
		return new Character(translationTableFromAngleToCharacter[4]).toString();
	}
	
	/**
	 * @param stepSizeForDiscreteAngleTransformation the step size
	 */
	public OptimizedPhiPsiToOneCharacterTransformer() {
		

		
		
	}

//	
//	/**
//	 * 
//	 * 
//	 * 
//	 * @param struc the input structure in biojava format
//	 * @return the converted phi psi angles as strings...
//	 */
//	public final ArrayList<String> getAngleSequences(final Structure struc) {
//		
//		ArrayList<String> sequences = new ArrayList<String>();
//				
//		int numberOfModels = struc.nrModels();
//		
//		
//		for (int currentModel = 0; 
//				currentModel < numberOfModels; 
//				currentModel++) {
//			
//			
//			List<Chain> chains = struc.getModel(currentModel);
//			
//			int numberOfChains = chains.size();
//			
//			for (int currentChain = 0; 
//					currentChain < numberOfChains; 
//					currentChain++) {
//
//				
//				List <Group> tempGroups 
//					= chains.get(currentChain).getAtomGroups();
//				
//				String thisTranslatedPhiPsiAngles = getStringFromResidues(tempGroups);
//				
//
//				sequences.add(thisTranslatedPhiPsiAngles);
//								
//				
//			}
//		}
//		
//		return sequences;
//	}
	

	
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
		AminoAcid a;
		//second amino
		AminoAcid b;
		//third amino
		AminoAcid c;
		
        double phi;
        double psi;
        

        for (int i = 0; i < aminoLength; i++) {
        	
            phi = -1d;
        	psi = -1d; 
            
        	// amino acid in the middle...
            b = (AminoAcid) aminos.get(i);
            
            
            // does the a exist in that case?
            if (i > 0) {
            	a = (AminoAcid) aminos.get(i - 1);
            	
	            try {
	            	phi = Calc.getPhi(a, b);
	            	
	            } catch (StructureException e) {
	            	//e.printStackTrace();
	            }
            }
            
            
            //does the c exist in that case?
            if (i < aminoLength - 1) {
            	
                c = (AminoAcid) aminos.get(i + 1);
                
                try {
                    psi = Calc.getPsi(b, c);
                    
                } catch (StructureException e) {
                	//e.printStackTrace();
                }
            }
            
            
            
            
            
            if (phi==-1|| psi == -1) {
            	//translatedAngleSeq.append("H");
            	
            	translatedAngleSeq.append(translationTableFromAngleToCharacter[4]);
            	//translatedAngleSeq.append("x");
            	
            	
            } else {
            	
            	int thisCombinationsPosition 
            		= angleToPositionTranslator(phi, psi);
            	//System.out.println("phi: " + phi);
            	//System.out.println("psi: " + psi);
            	//System.out.println("thiscomb: " + thisCombinationsPosition);
            
            
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
		
		
		
		
		if ((phi < -20) && (psi >= 50)) {
			//beta sheets
			
			return 0;
			
			
		} else if ((phi < -20 ) && (psi < 50 )){
			// alpha helices
			return 1;
			
			
			
		} else if (((phi > 20) && (phi < 120)) && ((psi < 100) && (psi > -40))){
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
