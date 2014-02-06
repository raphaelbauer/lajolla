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
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.transformation.protein.BetterOptimizedPhiPsiTranslator;
import com.raphaelbauer.lajolla.transformation.protein.PDBProteinTranslator;
import com.raphaelbauer.lajolla.transformation.protein.ProteinMatchRunner;
import com.raphaelbauer.lajolla.transformation.rna.etatheta.OptimizedStructureToEtaThetaCharacterTransformer;
import com.raphaelbauer.lajolla.utilities.DeleteDirRecursively;

public class RossmannFoldTododo extends TestCase {

	
	
	static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();
	
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	
	
	/**
	 * 2uag against all rossmann
	 * 
	 * => like in diss of patrick may...
	 */
	public void test2uag_against_others() {
	
		
		
		String tempDir = "src/test/tmp/"; 
		

		DeleteDirRecursively.deleteDir(new File(tempDir));
		
		

		int ngramSize = 10;
		int angleDiscretion = 90;
		 
		

		String pathToQueryDirOrFile = "src/test/resources/protein_alignment_tests/rossmann_fold/2UAG.pdb";
		String pathToTargetDirOrFile = "src/test/resources/protein_alignment_tests/rossmann_fold";
		
		
		String outputFilePath = tempDir + this.getClass().getSimpleName() + File.separator;
		
		
		
		boolean dealOnlyWithFirstModel = true;
		
		IResidueToStringTransformer iResidueToStringTransformer
			= new BetterOptimizedPhiPsiTranslator();
	
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
				1);
			
			
		
		
		
			//fail();
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
	
	
	
	
	
	
	
	

}
