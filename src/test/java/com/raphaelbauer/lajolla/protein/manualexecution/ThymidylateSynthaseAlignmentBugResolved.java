package com.raphaelbauer.lajolla.protein.manualexecution;

import java.io.File;


import junit.framework.TestCase;
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
