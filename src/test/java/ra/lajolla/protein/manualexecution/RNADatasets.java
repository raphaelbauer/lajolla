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
import ra.lajolla.transformation.rna.etatheta.OptimizedStructureToEtaThetaCharacterTransformer;
import ra.lajolla.transformation.rna.etatheta.PDBRNATranslator;
import ra.lajolla.transformation.rna.etatheta.RNAEtaThetaMatchRunner;
import ra.lajolla.utilities.DeleteDirRecursively;

public class RNADatasets extends TestCase {
	
	


	static IResidueToStringTransformer iResidueToStringTransformer
		= new OptimizedStructureToEtaThetaCharacterTransformer();
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();


	static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();
	
	
	public void testhirestest() {
	
		
		String tempDir = "/media/truecrypt1/tmp/trna_chains/";

		int ngramSize = 10;
		
		
		String pathToTargetDirOrFile = "/home/ra/lajolla/trna_chains/"; 
		String pathToQueryDirOrFile = pathToTargetDirOrFile;
		
		
		String outputFilePath = tempDir;
		
		boolean dealOnlyWithFirstModel = true;
		
		IFileToStringTranslator iFileToStringTranslator 
			= new PDBRNATranslator(
					iResidueToStringTransformer, 
					dealOnlyWithFirstModel,
					scoringFunction,
					ngramTo3DTranslator);
		
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.1;
		

		
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
			
			
		
		
		
	}

}
