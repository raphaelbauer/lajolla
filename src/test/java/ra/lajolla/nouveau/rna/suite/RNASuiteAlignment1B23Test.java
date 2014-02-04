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
package ra.lajolla.nouveau.rna.suite;

import java.io.File;

import junit.framework.TestCase;
import ra.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import ra.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import ra.lajolla.scoringfunctions.IScoringFunction;
import ra.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import ra.lajolla.transformation.IFileToStringTranslator;
import ra.lajolla.transformation.IResidueToStringTransformer;
import ra.lajolla.transformation.rna.suite.DummySuiteTransformer;
import ra.lajolla.transformation.rna.suite.PDBRNATranslator;
import ra.lajolla.transformation.rna.suite.RNASuiteMatchRunner;
import ra.lajolla.utilities.DeleteDirRecursively;

public class RNASuiteAlignment1B23Test extends TestCase {
	
	
	static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	
	public void testIdenticalMatchAndSearchAndAlignment() {
	
		
		String tempDir = "src/test/tmp"; 

		int ngramSize = 20;
		
		
		String pathToTargetDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.suite/1B23_testcase_rna_suite.suite"; 
		String pathToQueryDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.suite/1B23_testcase_rna_suite.suite"; 
		
		
		String outputFilePath = "src/test/tmp/" 
			+ this.getClass().getSimpleName() 
			+ File.separator;
		
		
		IResidueToStringTransformer residueToStringTransformer
			= new DummySuiteTransformer();
		
		IFileToStringTranslator iFileToStringTranslator 
			= new PDBRNATranslator(scoringFunction, 
					residueToStringTransformer,
					ngramTo3DTranslator);
		
		
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99999;
		

		
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
		File chainADir = new File(outputFilePath + File.separator + "1B23.pdb-model-0-chain-R" + File.separator);
		
		String [] allFiles = chainADir.list();
		
		if (allFiles.length != 2) {
			
			fail();
		}
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}

}
