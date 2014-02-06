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
package com.raphaelbauer.lajolla.nouveau.optimizedphipsi;

import java.io.File;

import junit.framework.TestCase;
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import com.raphaelbauer.lajolla.scoringfunctions.EScoringFunctionRelativeSettings;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.transformation.protein.BetterOptimizedPhiPsiTranslator;
import com.raphaelbauer.lajolla.transformation.protein.PDBProteinTranslator;
import com.raphaelbauer.lajolla.transformation.protein.ProteinMatchRunner;
import com.raphaelbauer.lajolla.utilities.DeleteDirRecursively;

public class AlignmentThioredoxinFlyHumanTest extends TestCase {
	
	
	static IResidueToStringTransformer iResidueToStringTransformer
		//= new OptimizedPhiPsiToOneCharacterTransformer();
		= new BetterOptimizedPhiPsiTranslator();

	static IScoringFunction scoringFunction 
		= new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
				EScoringFunctionRelativeSettings.basedOnSizeOfQueryWhatIsTheTargetInTMSCORE);
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	
public void testAlign1XWCwith3TRX() {
	
		
		String tempDir = "src/test/tmp"; 

		int ngramSize = 20;
		
		
		String pathToTargetDirOrFile = "src/test/resources/thioredoxin_fly_human/1XWC.pdb"; 
		String pathToQueryDirOrFile = "src/test/resources/thioredoxin_fly_human/3TRX.pdb"; 
		
		
		String outputFilePath 
			= tempDir + File.separator + this.getClass().getSimpleName()+"/"; 
		
		boolean dealOnlyWithFirstModel = true;
		

		
		IFileToStringTranslator iFileToStringTranslator 
			= new PDBProteinTranslator(
				iResidueToStringTransformer, 
				dealOnlyWithFirstModel,
				scoringFunction,
				ngramTo3DTranslator);
		
	
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.80;
		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));
		
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
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 1) {
			fail();
		}

		
		File resultDirName = new File(outputFilePath);
		
		for (String dirName : resultDirName.list()) {
			
			if (new File(outputFilePath + File.separator + dirName).list().length != 2 ) {
				fail();
					
			}
			
		}
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}



public void testAlign3TRXwith1XWC() {
	
	
	String tempDir = "src/test/tmp"; 

	int ngramSize = 20;
	
	
	String pathToTargetDirOrFile = "src/test/resources/thioredoxin_fly_human/3TRX.pdb"; 
	String pathToQueryDirOrFile = "src/test/resources/thioredoxin_fly_human/1XWC.pdb"; 
	
	
	String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 
	
	boolean dealOnlyWithFirstModel = true;

	IFileToStringTranslator iFileToStringTranslator 
		= new PDBProteinTranslator(
			iResidueToStringTransformer, 
			dealOnlyWithFirstModel,
			scoringFunction,
			ngramTo3DTranslator);
	

	double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.80;
	

	
	//set up and clean:
	DeleteDirRecursively.deleteDir(new File(tempDir));

	
	
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
		
		
	
	String [] dirNames = new File(outputFilePath).list();
	
	
	

	if (dirNames.length != 1) {
		fail();
	}

	
	File resultDirName = new File(outputFilePath);
	
	for (String dirName : resultDirName.list()) {
		
		if (new File(outputFilePath + File.separator + dirName).list().length != 2 ) {
			fail();
				
		}
		
	}
		
	
	//tear down and clean:
	//DeleteDirRecursively.deleteDir(new File(tempDir));
	
	
	
}


}
