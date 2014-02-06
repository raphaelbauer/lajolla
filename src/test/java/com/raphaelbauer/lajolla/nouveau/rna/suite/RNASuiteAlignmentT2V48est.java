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
package com.raphaelbauer.lajolla.nouveau.rna.suite;

import java.io.File;


import junit.framework.TestCase;
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.transformation.rna.suite.DummySuiteTransformer;
import com.raphaelbauer.lajolla.transformation.rna.suite.PDBRNATranslator;
import com.raphaelbauer.lajolla.transformation.rna.suite.RNASuiteMatchRunner;
import com.raphaelbauer.lajolla.utilities.DeleteDirRecursively;

public class RNASuiteAlignmentT2V48est extends TestCase {
	
	
	static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();
	
	
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	
	/**
	 * These identical matches were not identified by the alg. This
	 * is a test to check if it has been fixed.
	 * 
	 * 
	 */
	public void testIdenticalMatchAndSearchAndAlignment() {
		 
		String tempDir = "src/test/tmp"; 

		int ngramSize = 20;
		
		
		String pathToTargetDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.suite/2V48_self_finding_test.suite"; 
		String pathToQueryDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.suite/2V48_self_finding_test.suite"; 
		
		
		String outputFilePath = "src/test/tmp/" 
			+ this.getClass().getSimpleName() 
			+ File.separator;
		
		
		
		IResidueToStringTransformer residueToStringTransformer
			= new DummySuiteTransformer();
	
		IFileToStringTranslator iFileToStringTranslator 
			= new PDBRNATranslator(scoringFunction, 
				residueToStringTransformer,
				ngramTo3DTranslator);
		
		
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.1;
		

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		RNASuiteMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				pathToTargetDirOrFile, 
				pathToQueryDirOrFile,  
				outputFilePath,  
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
		
			

		
		//check if there are two result dirs with 2 perfect matches each...
		
		
		
		//1. stupid test... simply check if the scores of both result files are ok:
		File chainADir = new File(outputFilePath + File.separator + "2V48.pdb-model-0-chain-A" + File.separator);
		
		String [] allFiles = chainADir.list();
		
		for (String string : allFiles) {
			
			if (!new File(string).getName().startsWith(
					"2V48.pdb2V48.pdb-rmsd-7.08462489154669E-14-alignmentscore-1.0")) {
				fail();
				
				
			}
			
		}
		
		//1. stupid test... simply check if the scores of both result files are ok:
		chainADir = new File(outputFilePath + File.separator + "2V48.pdb-model-0-chain-V" + File.separator);
		
		allFiles = chainADir.list();
		
		for (String string : allFiles) {
			
			if (!new File(string).getName().startsWith(
					"2V48.pdb2V48.pdb-rmsd-7.87852659852368E-15-alignmentscore-1.0")) {
				fail();
				
				
			}
			
		}
		
		//1. stupid test... simply check if the scores of both result files are ok:
		chainADir = new File(outputFilePath + File.separator + "2V48.pdb-model-0-chain-W" + File.separator);
		
		allFiles = chainADir.list();
		
		for (String string : allFiles) {
			
			if (!new File(string).getName().startsWith(
					"2V48.pdb2V48.pdb-rmsd-2.420931135080594E-14-alignmentscore-1.0-")) {
				fail();
				
				
			}
			
		}

		//tear down and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}

}
