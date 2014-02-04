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
package ra.lajolla.nouveau.optimizedphipsi;

import java.io.File;

import junit.framework.TestCase;
import ra.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import ra.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import ra.lajolla.scoringfunctions.EScoringFunctionRelativeSettings;
import ra.lajolla.scoringfunctions.IScoringFunction;
import ra.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import ra.lajolla.transformation.IFileToStringTranslator;
import ra.lajolla.transformation.IResidueToStringTransformer;
import ra.lajolla.transformation.protein.BetterOptimizedPhiPsiTranslator;
import ra.lajolla.transformation.protein.PDBProteinTranslator;
import ra.lajolla.transformation.protein.ProteinMatchRunner;
import ra.lajolla.utilities.DeleteDirRecursively;


/**
 * A testcase that did not work in version 1.
 * submitted by elke.
 * 
 * @author ra
 *
 */
public class TMAlignTest extends TestCase {
	
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	
	
	static IResidueToStringTransformer iResidueToStringTransformer
	//= new OptimizedPhiPsiToOneCharacterTransformer();
	= new BetterOptimizedPhiPsiTranslator();
	
	//IResidueToStringTransformer iResidueToStringTransformer
	//	= new StructureToPhiPsiToOneCharacterTransformer(180);

	
	//IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistance();
	
	static IScoringFunction scoringFunction 
		= new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
				EScoringFunctionRelativeSettings.basedOnSizeOfQueryWhatIsTheTargetInTMSCORE);
	


	public void testIdenticalMatchAndSearchAndAlignmenQuery1PQ0Target1g5j() {
	
		String tempDir = "src/test/tmp"; 

		int ngramSize = 10;
		
		String pathToTargetDirOrFile = "src/test/resources/tm_align_paper/1atzA.pdb";
		String pathToQueryDirOrFile = "src/test/resources/tm_align_paper/1auoA.pdb";
		
		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() + "/"; 
		
		boolean dealOnlyWithFirstModel = true;

		IFileToStringTranslator iFileToStringTranslator 
			= new PDBProteinTranslator(
				iResidueToStringTransformer, 
				dealOnlyWithFirstModel,
				scoringFunction,
				ngramTo3DTranslator);
	
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.20;
		
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
		String [] dirNames = new File(outputFilePath).list();
			
		
		if (dirNames.length != 1) { 
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			
			//System.out.println(resultDirFileName);
			
			if (new File(resultDirFileName).list().length != 2) {
				
				fail();
				
			}	
			
		}

		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
	}
	
	
}
