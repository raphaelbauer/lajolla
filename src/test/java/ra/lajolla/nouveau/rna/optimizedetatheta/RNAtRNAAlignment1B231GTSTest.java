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



////////////
//
// this testcase does not yield good results => has to beinvestigated 
// trnas are aligned in the wrong direction...




//////////////////////////////////////////////////////////////////////////////////////////
// => no real bug. these are simply two different proteins. or relativel different proteins



public class RNAtRNAAlignment1B231GTSTest extends TestCase {
	
	
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	
	
	
	static String tempDir = "src/test/tmp/"; 
	
	
	static IResidueToStringTransformer iResidueToStringTransformer
		= new OptimizedStructureToEtaThetaCharacterTransformer();



	static IScoringFunction scoringFunction 
		= new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();

	
	
	
	
	public void testAlignment1B231GTS() {
	
		
		String tempDir = "src/test/tmp/"; 

		int ngramSize = 6;
		
		
		String pathToTargetDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.etatheta/trna_simple/1B23.pdb"; 
		String pathToQueryDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.etatheta/trna_simple/1GTS.pdb"; 
		
		
		String outputFilePath = tempDir + this.getClass().getSimpleName() + File.separator;
		
		boolean dealOnlyWithFirstModel = true;
		
		
		IFileToStringTranslator iFileToStringTranslator 
			= new PDBRNATranslator(
					iResidueToStringTransformer, 
					dealOnlyWithFirstModel,
					scoringFunction,
					ngramTo3DTranslator);
		
		
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.3;
		

		
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
		File chainADir = new File(outputFilePath + File.separator + "1GTS.pdb-model-0-chain-B");
		
		String [] allFiles = chainADir.list();
		
		if (allFiles.length != 2) {
			fail();
		}
		
		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
//	
//	
//	
public void testAlignment1GTS1B23() {
	
		

		int ngramSize = 6;
		
		
		String pathToTargetDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.etatheta/trna_simple/1GTS.pdb"; 
		String pathToQueryDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.etatheta/trna_simple/1B23.pdb"; 
		
		
		String outputFilePath = tempDir + this.getClass().getSimpleName() + File.separator;
		
		boolean dealOnlyWithFirstModel = true;
		
		
		IFileToStringTranslator iFileToStringTranslator 
			= new PDBRNATranslator(
					iResidueToStringTransformer, 
					dealOnlyWithFirstModel,
					scoringFunction,
					ngramTo3DTranslator);
		
		
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.3;
		

		
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
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
//
}
