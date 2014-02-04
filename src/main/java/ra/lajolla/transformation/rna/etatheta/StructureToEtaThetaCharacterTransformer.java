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
package ra.lajolla.transformation.rna.etatheta;

import java.util.List;

import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.HetatomImpl;
import org.biojava.bio.structure.StructureException;

import ra.lajolla.transformation.IResidueToStringTransformer;
import ra.lajolla.utilities.Utility;


/**
 * AngleSequences gets a pdb file with a chain and converts it to
 * a string that can be indexed further on.

 * @author petmoo
 *
 */
public class StructureToEtaThetaCharacterTransformer 
	implements IResidueToStringTransformer { 
	
	
	private final int numberOfCharactersInStringCorrespondToOneResidue = 2;

	
	
	/** the step size. */
	private int stepSizeForDiscreteAngleTransformation;
	
	/** the translation table. */
	private char[] translationTableFromAngleToCharacter;

	// the characters downwards are commands
	final int beginPrintableASCIICharacter = 32;
	
	/**
	 * @return
	 */
	public String getStringMeaningNoResultAtThatPosition()  {
		
		return new Character(translationTableFromAngleToCharacter[beginPrintableASCIICharacter]).toString();
	}
	
	

	public int getNumberOfCharactersInStringCorrespondToOneResidue() {
		return numberOfCharactersInStringCorrespondToOneResidue;
	}

	
	
	/**
	 * @param stepSizeForDiscreteAngleTransformation the step size
	 */
	public StructureToEtaThetaCharacterTransformer(
			final int stepSizeForDiscreteAngleTransformation) {
		
		
		//FIXME =>. .. use other ascii table or so..
		if (stepSizeForDiscreteAngleTransformation < 4) {
			System.out.println(
					"[ERROR] not feasible with standard ASCII table...");
			System.out.println("ask petmoo or ra to fix that ;)");
			System.exit(10);
		}
		
		
		
		//set member variable...
		this.stepSizeForDiscreteAngleTransformation
			= stepSizeForDiscreteAngleTransformation;
		
		// unterteilt 360 grad entsprechend dem winkelabstand in 
		// bestimmte anzahl von kategorien 
		// (bsp. step = 10 -> 36 kategorien)
		
		//number of discrete buckets where to store angles
		int numberOfDiscreteSteps 
			= 360 / this.stepSizeForDiscreteAngleTransformation;
		
		
		translationTableFromAngleToCharacter 
			= new char[numberOfDiscreteSteps + 1];
		
		
		// 32 is the space... all characters upwards until 127 are usable

					
		for (int i = 0; i < numberOfDiscreteSteps; i++) {
			
			translationTableFromAngleToCharacter[i]
			    = (char) (i + beginPrintableASCIICharacter);
		}
		
		
		// set the last bucket to the first bucket...
		// 360 degree are again O degree for instance...
		// so they get the same character...:
		translationTableFromAngleToCharacter[numberOfDiscreteSteps] 
		     = (char) (beginPrintableASCIICharacter);
		
		
		
	}

	
	

	
	/**
	 * gets a group of aminos (== chain), calculates the phi psi and transforms
	 * the phi psi into the characters needed for hashing / indexing...
	 * 
	 * @param nucleotidesAndSimilarMolecules the group of aminos
	 * @return string with transformed characters
	 */
	public final String getStringFromResidues(final List<Group> nucleotidesAndSimilarMolecules) {
		
		// for the return...
		StringBuffer translatedAngleSeq = new StringBuffer();
        
		// expected lengt
		int nucleotideLength = nucleotidesAndSimilarMolecules.size();
		
        translatedAngleSeq.ensureCapacity(nucleotideLength * 2);
		
        //first amino
        HetatomImpl a;
		//second amino
        HetatomImpl b;
		//third amino
        HetatomImpl c;
		
        double eta;
        double theta;
        

        for (int i = 0; i < nucleotideLength; i++) {
        	
            eta = 0.0d;
        	theta = 0.0d; 
            
            ///check if the other two aminos exist:
            if ((i>0) && (i < nucleotideLength - 1)) {
            	

                a = (HetatomImpl) nucleotidesAndSimilarMolecules.get(i - 1);
                
                b = (HetatomImpl) nucleotidesAndSimilarMolecules.get(i);

                c = (HetatomImpl) nucleotidesAndSimilarMolecules.get(i + 1);
            	
	            try {
	            	eta = Utility.getEta(a, b, c);
	            	
	            	theta = Utility.getTheta(b, c);
	            	
	            } catch (StructureException e) {
	            	//e.printStackTrace();
	            }

                
            	
            }


            translatedAngleSeq.append(angleToCharacter(eta));
            translatedAngleSeq.append(angleToCharacter(theta));
            
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
	public final char angleToCharacter(final double angle) {
		
		int positionAsDiscretStep 
			= (int) Math.floor((angle + 180.0d)
					/ stepSizeForDiscreteAngleTransformation);
		
		
		return translationTableFromAngleToCharacter[positionAsDiscretStep];
	}
	
}
