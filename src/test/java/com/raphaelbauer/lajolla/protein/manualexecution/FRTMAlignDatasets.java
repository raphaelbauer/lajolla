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
package com.raphaelbauer.lajolla.protein.manualexecution;

import java.io.File;


import junit.framework.TestCase;
import org.junit.Test;
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.transformation.protein.OptimizedPhiPsiToOneCharacterTransformer;
import com.raphaelbauer.lajolla.transformation.protein.PDBProteinTranslator;
import com.raphaelbauer.lajolla.transformation.protein.ProteinMatchRunner;
import com.raphaelbauer.lajolla.utilities.DeleteDirRecursively;

public class FRTMAlignDatasets extends TestCase {
	
	
	static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();
	
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

  
	@Test
	public void testAllAgainstAll() {
		
			int ngramSize = 8;
			int angleDiscretion = 90;
			
			
			String pathToTargetDirOrFile = "/home/ra/Desktop/dataset2/pdb/"; 
			String pathToQueryDirOrFile = "/home/ra/Desktop/dataset2/pdb/";
			
			
			String outputFilePath = "/media/truecrypt1/tmp/dataset2/";
			
			boolean dealOnlyWithFirstModel = true;
			
			IResidueToStringTransformer iResidueToStringTransformer
			= new OptimizedPhiPsiToOneCharacterTransformer();//(angleDiscretion);
		
		IFileToStringTranslator iFileToStringTranslator 
			= new PDBProteinTranslator(
					iResidueToStringTransformer, 
					dealOnlyWithFirstModel,
					scoringFunction,
					ngramTo3DTranslator);
		
		
			double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.3;
			

			
			//set up and clean:
			DeleteDirRecursively.deleteDir(new File(outputFilePath));

			
			
			ProteinMatchRunner.executeSearch(
					ngramSize, 
					iFileToStringTranslator, 
					iResidueToStringTransformer,
					pathToTargetDirOrFile, 
					pathToQueryDirOrFile, 
					outputFilePath, 
					dealOnlyWithFirstModel, 
					thresholdOfRefinementScoreUnderWichResultIsOmitted,
					1);
				
				
			
			
			//check if there are two result dirs with 2 perfect matches each...
			
			

			
			
			
				
			
			//tear down and clean:
			//DeleteDirRecursively.deleteDir(new File(tempDir));
			
			
			
		}
	
	

}
