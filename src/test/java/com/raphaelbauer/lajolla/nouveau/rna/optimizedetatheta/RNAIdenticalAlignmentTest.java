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
package com.raphaelbauer.lajolla.nouveau.rna.optimizedetatheta;

import java.io.File;

import junit.framework.TestCase;
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.transformation.rna.etatheta.OptimizedStructureToEtaThetaCharacterTransformer;
import com.raphaelbauer.lajolla.transformation.rna.etatheta.PDBRNATranslator;
import com.raphaelbauer.lajolla.transformation.rna.etatheta.RNAEtaThetaMatchRunner;
import com.raphaelbauer.lajolla.utilities.DeleteDirRecursively;

public class RNAIdenticalAlignmentTest extends TestCase {
	

	
	static IResidueToStringTransformer iResidueToStringTransformer
	= new OptimizedStructureToEtaThetaCharacterTransformer();



	static IScoringFunction scoringFunction 
	= new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();

	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	
	public void testIdenticalMatchAndSearchAndAlignment() {
	
		
		String tempDir = "src/test/tmp/"; 

		int ngramSize = 26;

		
		
		String pathToTargetDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.etatheta/1B23.pdb"; 
		String pathToQueryDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.etatheta/1B23.pdb"; 
		
		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName() + File.separator;
		
		boolean dealOnlyWithFirstModel = true;
		
		IFileToStringTranslator iFileToStringTranslator 
			= new PDBRNATranslator(
					iResidueToStringTransformer, 
					dealOnlyWithFirstModel,
					scoringFunction,
					ngramTo3DTranslator);
		
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99999;
		

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		
		RNAEtaThetaMatchRunner.executeSearch(
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
		File chainADir = new File(outputFilePath + File.separator + "1B23.pdb-model-0-chain-R/");
		
		String [] allFiles = chainADir.list();
		
		if (allFiles.length != 2) {
			
			fail();
			
			
		}

		
		
		
			
		
		//tear down and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}

}
