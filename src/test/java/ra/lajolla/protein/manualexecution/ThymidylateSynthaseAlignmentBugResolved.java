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
package ra.lajolla.protein.manualexecution;

import java.io.File;


import junit.framework.TestCase;
import ra.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import ra.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import ra.lajolla.scoringfunctions.IScoringFunction;
import ra.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import ra.lajolla.transformation.IFileToStringTranslator;
import ra.lajolla.transformation.IResidueToStringTransformer;
import ra.lajolla.transformation.protein.OptimizedPhiPsiToOneCharacterTransformer;
import ra.lajolla.transformation.protein.PDBProteinTranslator;
import ra.lajolla.transformation.protein.ProteinMatchRunner;
import ra.lajolla.utilities.DeleteDirRecursively;


/**
 * A testcase that did not work in version 1.
 * submitted by elke.
 * 
 * An 
 * 
 * 
 * 
 * @author ra
 *
 */
public class ThymidylateSynthaseAlignmentBugResolved extends TestCase{

	
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();
	
	public void testAll() {
		

		//String dirWithPDBFiles = "src/test/resources/ra.lajolla.nouveau.phipsi.ThymidylateSynthaseAlignmentBugTest/";
		String dirWithPDBFiles = "src/test/resources/ThymidylateSynthaseAlignmentBugTestSmallTestset";
		
		String tempDir = "src/test/tmp"; 

		int ngramSize = 10;
		int angleseparation = 90;
		
		
		String pathToTargetDirOrFile = dirWithPDBFiles;
		String pathToQueryDirOrFile = dirWithPDBFiles;
		
		
		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() + "/"; 
		boolean dealOnlyWithFirstModel = true;
		
		
		IResidueToStringTransformer iResidueToStringTransformer
			= new OptimizedPhiPsiToOneCharacterTransformer();
	
	IFileToStringTranslator iFileToStringTranslator 
		= new PDBProteinTranslator(
				iResidueToStringTransformer, 
				dealOnlyWithFirstModel,
				scoringFunction,
				ngramTo3DTranslator);
		
	
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.2;
		

		
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
				100);
				
//		
//
//		//check if there are two result dirs with 2 perfect matches each...
//		String [] dirNames = new File(outputFilePath).list();
//		
//			
//		
//		if (dirNames.length != 1) {
//			fail();
//		}
//		
//		
//		
//		// 1. for each resultdir check if there are enough result files:
//		for (String dirName : dirNames) {
//			
//			String resultDirFileName = outputFilePath + File.separator + dirName;
//			
//			System.out.println(resultDirFileName);
//			
//			if (new File(resultDirFileName).list().length != 2) {
//				
//				fail();
//				
//			}
//			
//			
//		}

	
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
	
	
	
	
	
	
}
