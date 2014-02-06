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

public class BindingSiteApoHoloTest extends TestCase {
	
	
	
	
	static IResidueToStringTransformer iResidueToStringTransformer
	//= new OptimizedPhiPsiToOneCharacterTransformer();
	= new BetterOptimizedPhiPsiTranslator();

	static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
			EScoringFunctionRelativeSettings.basedOnSizeOfSmaller);
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	
	public void testLength10() {
	
		
		String tempDir = "src/test/tmp"; 

		int ngramSize = 10;
		
		//apoform
		String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/dominic_testfall/proteine/1ABB_C.ent";   
		
		//binding site of holo
		String pathToQueryDirOrFile =  "src/test/resources/bindingsitedetection/dominic_testfall/binding_sites/1H5U_A_999.ent"; 
		
		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() +"/"; 
		
		boolean dealOnlyWithFirstModel = true;
		

		
		
		IFileToStringTranslator iFileToStringTranslator 
			= new PDBProteinTranslator(
				iResidueToStringTransformer, 
				dealOnlyWithFirstModel,
				scoringFunction,
				ngramTo3DTranslator);
		
		
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.6;
		

		
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
		
		
		
		
		
		File chainADir = new File(outputFilePath + File.separator + "1H5U_A_999.ent-model-0-chain-A");
		
		String [] allFiles = chainADir.list();

		
		if (allFiles.length !=2) {
			fail();
		}
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
	}
	
	
//	public void testLength8() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 8;
//		
//		//apoform
//		String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/dominic_testfall/proteine/1ABB_C.ent";   
//		
//		//binding site of holo
//		String pathToQueryDirOrFile =  "src/test/resources/bindingsitedetection/dominic_testfall/binding_sites/1H5U_A_999.ent"; 
//		
//		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() +"/"; 
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//		= new OptimizedPhiPsiToOneCharacterTransformer();
//	
//		IFileToStringTranslator iFileToStringTranslator 
//			= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel);
//		
//		double thresholdOfRMSDOverWhichResultIsOmitted = 5.0;
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.5;
//		
//
//		
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//
//		
//		
//		ProteinMatchRunner.executeSearch(
//				ngramSize, 
//				iFileToStringTranslator, 
//				iResidueToStringTransformer,
//				pathToTargetDirOrFile, 
//				pathToQueryDirOrFile, 
//				outputFilePath, 
//				dealOnlyWithFirstModel, 
//				thresholdOfRefinementScoreUnderWichResultIsOmitted,
//				1);
//			
//			
//		
//		
//		//check if there are two result dirs with 2 perfect matches each...
//		
//		
//		
//		
//		
//		File chainADir = new File(outputFilePath + File.separator + "1H5U_A_999.ent-model-0-chain-A");
//		
//		String [] allFiles = chainADir.list();
//
//		
//		if (allFiles.length != 2) {
//			fail();
//		}
//		
//		//tear down and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//	}
//	
//	
//public void testLength6() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 6;
//		
//		//apoform
//		String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/dominic_testfall/proteine/1ABB_C.ent";   
//		
//		//binding site of holo
//		String pathToQueryDirOrFile =  "src/test/resources/bindingsitedetection/dominic_testfall/binding_sites/1H5U_A_999.ent"; 
//		
//		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() +"/"; 
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//		= new OptimizedPhiPsiToOneCharacterTransformer();
//	
//		IFileToStringTranslator iFileToStringTranslator 
//			= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel);
//		
//		double thresholdOfRMSDOverWhichResultIsOmitted = 5.0;
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.5;
//		
//
//		
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//
//		
//		
//		ProteinMatchRunner.executeSearch(
//				ngramSize, 
//				iFileToStringTranslator, 
//				iResidueToStringTransformer,
//				pathToTargetDirOrFile, 
//				pathToQueryDirOrFile, 
//				outputFilePath, 
//				dealOnlyWithFirstModel, 
//				thresholdOfRefinementScoreUnderWichResultIsOmitted,
//				1);
//			
//			
//		
//		
//		//check if there are two result dirs with 2 perfect matches each...
//		
//		
//		
//		
//		
//		File chainADir = new File(outputFilePath + File.separator + "1H5U_A_999.ent-model-0-chain-A");
//		
//		String [] allFiles = chainADir.list();
//
//		
//		if (allFiles.length != 2) {
//			fail();
//		}
//		
//		//tear down and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//	}

}
