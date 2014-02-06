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

public class ProteinAlignmentNotAllFoundBugTest extends TestCase {
	
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	
	

	static IScoringFunction scoringFunction 
		= new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
				EScoringFunctionRelativeSettings.basedOnSizeOfQueryWhatIsTheTargetInTMSCORE);
	
	
	
	/**
	 * test if all 2 chains are crosswise found:
	 */
	public void testIdenticalMatchAndSearchAndAlignment() {
	
		
		String tempDir = "src/test/tmp/"; 

		int ngramSize = 20;
		 
		
		String pathToTargetDirOrFile = "src/test/resources/testcase_against_not_all_chains_found/1kic_hydrolase.pdb"; 
		String pathToQueryDirOrFile = "src/test/resources/testcase_against_not_all_chains_found/1kic_hydrolase.pdb"; 
		
		
		String outputFilePath = tempDir + this.getClass().getSimpleName() + File.separator;
		boolean dealOnlyWithFirstModel = true;
		
		
		IResidueToStringTransformer iResidueToStringTransformer
		//= new OptimizedPhiPsiToOneCharacterTransformer();
		= new BetterOptimizedPhiPsiTranslator();
		
		
		IFileToStringTranslator iFileToStringTranslator 
			= new PDBProteinTranslator(
					iResidueToStringTransformer, 
					dealOnlyWithFirstModel,
					scoringFunction,
					ngramTo3DTranslator);
		
		
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.9;
		

		
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
		
		
		
		//1. stupid test... simply check if the scores of both result files are ok:
		File chainADir = new File(outputFilePath + File.separator + "1kic_hydrolase.pdb-model-0-chain-A");
		
		String [] allFiles = chainADir.list();
		
		if (allFiles.length !=3) {
			fail();
		}

		
		//2. stupid test... simply check if the scores of both result files are ok:
		File chainBDir = new File(outputFilePath + File.separator + "1kic_hydrolase.pdb-model-0-chain-B");
		
		allFiles = chainBDir.list();
		
		if (allFiles.length !=3) {
			fail();
		}

		
		
		
		
		
			
		
		//tear down and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
	
	
	
	/**
	 * test if similar chains are crosswise identified
	 * => even with another pdb entry being present...
	 * this was a bug as of 2008-10-29
	 */
	public void testDifferentChainsAlignment() {
	
		
		String tempDir = "src/test/tmp/"; 

		int ngramSize = 20;
		int angleDiscretion = 90;
		 
		
		String pathToTargetDirOrFile = "src/test/resources/testcase_against_not_all_chains_found/"; 
		String pathToQueryDirOrFile = "src/test/resources/testcase_against_not_all_chains_found/"; 
		
		
		String outputFilePath = tempDir + this.getClass().getSimpleName() + File.separator;
		boolean dealOnlyWithFirstModel = true;
		
		IResidueToStringTransformer iResidueToStringTransformer
		//= new OptimizedPhiPsiToOneCharacterTransformer();
		= new BetterOptimizedPhiPsiTranslator();
	

		
	IFileToStringTranslator iFileToStringTranslator
		= new PDBProteinTranslator(
				iResidueToStringTransformer, 
				dealOnlyWithFirstModel,
				scoringFunction,
				ngramTo3DTranslator);
		
	
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.9;
		

		
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
			
			
		
		
		//check if there are two result dirs with the perfect and almost perfect matches each...
		File chainADir = new File(outputFilePath + File.separator + "1kic_hydrolase.pdb-model-0-chain-A");
		
		String [] allFiles = chainADir.list();
		
		if (allFiles.length != 3) {
			fail();
		}

		File chainBDir = new File(outputFilePath + File.separator + "1kic_hydrolase.pdb-model-0-chain-B");
		
		allFiles = chainBDir.list();
		
		if (allFiles.length != 3) {
			fail();
		}
		
		
		chainBDir = new File(outputFilePath + File.separator + "pdb1nje.ent-model-0-chain-A");
		
		allFiles = chainBDir.list();
		
		if (allFiles.length != 2) {
			fail();
		}
		

		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
	}
	
	
	
	
	
	
	
	
	

}
