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
package ra.lajolla.nouveau.rna.optimizedetatheta;

import java.io.File;

import junit.framework.TestCase;
import ra.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import ra.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import ra.lajolla.scoringfunctions.IScoringFunction;
import ra.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import ra.lajolla.transformation.IFileToStringTranslator;
import ra.lajolla.transformation.IResidueToStringTransformer;
import ra.lajolla.transformation.rna.etatheta.OptimizedStructureToEtaThetaCharacterTransformer;
import ra.lajolla.transformation.rna.etatheta.PDBRNATranslator;
import ra.lajolla.transformation.rna.etatheta.RNAEtaThetaMatchRunner;
import ra.lajolla.utilities.DeleteDirRecursively;

public class PaperDIALTest extends TestCase {
	
	static String tempDir = "src/test/tmp/"; 
	

	static IResidueToStringTransformer iResidueToStringTransformer
		= new OptimizedStructureToEtaThetaCharacterTransformer();
	
	static INGramTo3DTranslator nGramTo3DTranslator
		= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();
	
	

	static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();
	
	/**
	 * not from the sarsa paper... but a difficult example. or an ambiguous ine...
	 * 
	 * similar to the paper from the DIAL paper, but the pdb entries disapperared somehow...
	 * 
	 * 
	 */
	public void testMultipleTwoDifferentTRNAs() {
		
		int ngramSize = 5;
		
		String file1 = "src/test/resources/sarsa_rna_paper/multipleglobal/trnas/1asz.pdb";
		String file2 = "src/test/resources/sarsa_rna_paper/multipleglobal/trnas/2csx.pdb";
		
		
		
		String outputFilePath
		= tempDir 
			+ File.separator 
			+ this.getClass().getSimpleName() 
			+ File.separator;
		  
		
		boolean dealOnlyWithFirstModel = true;
		
		
		
		
		IFileToStringTranslator iFileToStringTranslator 
		= new PDBRNATranslator(
				iResidueToStringTransformer, 
				dealOnlyWithFirstModel,
				scoringFunction,
				nGramTo3DTranslator);



		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.27;
		

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(outputFilePath));

		
		
		RNAEtaThetaMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				file1, 
				file2, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		

			String resultDirFileName = outputFilePath + File.separator + "2csx.pdb-model-0-chain-C";
			
			
			if (new File(resultDirFileName).list().length != 2 ) {
				
				fail();
				
			}
			
			
		
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}

}
