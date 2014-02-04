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

public class BindingSiteDetectionSimpleIdenticalFrom1C4KTest extends TestCase {
	
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();
	
	
	IScoringFunction scoringFunction =
		new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
				EScoringFunctionRelativeSettings.basedOnSizeOfSmaller);
	

	
	
	public void testFindIdenticalBindingSiteof1C4K_length10() {
	
		
		String tempDir = "src/test/tmp"; 

		int ngramSize = 10;
		
		                                
		String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/1C4K.pdb"; 
		String pathToQueryDirOrFile = "src/test/resources/bindingsitedetection/1C4K_A_955.ent"; 
		
		
		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() +"/"; 
		
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
		
	
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99;
		

		
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
		
		
		
		
		
		File chainADir = new File(outputFilePath + File.separator + "1C4K_A_955.ent-model-0-chain-A");
		
		String [] allFiles = chainADir.list();
		
		if (allFiles.length !=2) {
			fail();
		}
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
	
	
//	
//	
//public void testFindIdenticalBindingSiteof1C4K_length9() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 9;
//		int angleDiscretion = 90;
//		
//		                                
//		String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/1C4K.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/bindingsitedetection/1C4K_A_955.ent"; 
//		
//		
//		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() +"/"; 
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer		= new OptimizedPhiPsiToOneCharacterTransformer();
//	
//	IFileToStringTranslator iFileToStringTranslator 
//		= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel);
//		
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99;
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
//		File chainADir = new File(outputFilePath + File.separator + "1C4K_A_955.ent-model-0-chain-A");
//		
//		String [] allFiles = chainADir.list();
//		
//		if (allFiles.length != 2) {
//			fail();
//		}
//		
//		
//			
//		
//		//tear down and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}
//
//
//
//public void testFindIdenticalBindingSiteof1C4K_length8() {
//	
//	
//	String tempDir = "src/test/tmp"; 
//
//	int ngramSize = 8;
//	int angleDiscretion = 90;
//	
//	                                
//	String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/1C4K.pdb"; 
//	String pathToQueryDirOrFile = "src/test/resources/bindingsitedetection/1C4K_A_955.ent"; 
//	
//	
//	String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() +"/"; 
//	
//	boolean dealOnlyWithFirstModel = true;
//	
//	IResidueToStringTransformer iResidueToStringTransformer		= new OptimizedPhiPsiToOneCharacterTransformer();
//IFileToStringTranslator iFileToStringTranslator 
//	= new PDBProteinTranslator(
//			iResidueToStringTransformer, 
//			dealOnlyWithFirstModel);
//	
//	double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99;
//	
//
//	
//	//set up and clean:
//	DeleteDirRecursively.deleteDir(new File(tempDir));
//
//	
//	
//	ProteinMatchRunner.executeSearch(
//			ngramSize, 
//			iFileToStringTranslator, 
//			iResidueToStringTransformer,
//			pathToTargetDirOrFile, 
//			pathToQueryDirOrFile, 
//			outputFilePath, 
//			dealOnlyWithFirstModel, 
//			thresholdOfRefinementScoreUnderWichResultIsOmitted,
//			1);
//		
//		
//	
//	
//	//check if there are two result dirs with 2 perfect matches each...
//	
//	
//	
//	
//	
//	File chainADir = new File(outputFilePath + File.separator + "1C4K_A_955.ent-model-0-chain-A");
//	
//	String [] allFiles = chainADir.list();
//	
//	if (allFiles.length !=2) {
//		fail();
//	}
//	
//	
//		
//	
//	//tear down and clean:
//	DeleteDirRecursively.deleteDir(new File(tempDir));
//	
//	
//	
//}
//	
//	
//
//	public void testFindIdenticalBindingSiteof1C4K_length7() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 7;
//		int angleDiscretion = 90;
//		
//		                                
//		String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/1C4K.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/bindingsitedetection/1C4K_A_955.ent"; 
//		
//		
//		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() +"/"; 
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer		= new OptimizedPhiPsiToOneCharacterTransformer();
//	
//	IFileToStringTranslator iFileToStringTranslator 
//		= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel);
//		
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99;
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
//		File chainADir = new File(outputFilePath + File.separator + "1C4K_A_955.ent-model-0-chain-A");
//		
//		String [] allFiles = chainADir.list();
//		
//		if (allFiles.length !=2) {
//			fail();
//		}
//		
//		
//			
//		
//		//tear down and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}
//	
//	
//	public void testFindIdenticalBindingSiteof1C4K_length6() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 6;
//		int angleDiscretion = 90;
//		
//		                                
//		String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/1C4K.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/bindingsitedetection/1C4K_A_955.ent"; 
//		
//		
//		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() +"/"; 
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer		= new OptimizedPhiPsiToOneCharacterTransformer();
//	
//	IFileToStringTranslator iFileToStringTranslator 
//		= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel);
//		
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99;
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
//		File chainADir = new File(outputFilePath + File.separator + "1C4K_A_955.ent-model-0-chain-A");
//		
//		String [] allFiles = chainADir.list();
//		
//		if (allFiles.length != 2) {
//			fail();
//		}
//		
//		
//			
//		
//		//tear down and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}
//	
//	
//	
//	
//	
//	public void testFindIdenticalBindingSiteof1C4K_length5() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 5;
//		int angleDiscretion = 90;
//		
//		                                
//		String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/1C4K.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/bindingsitedetection/1C4K_A_955.ent"; 
//		
//		
//		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() +"/"; 
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer		= new OptimizedPhiPsiToOneCharacterTransformer();
//	
//	IFileToStringTranslator iFileToStringTranslator 
//		= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel);
//		
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99;
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
//		File chainADir = new File(outputFilePath + File.separator + "1C4K_A_955.ent-model-0-chain-A");
//		
//		String [] allFiles = chainADir.list();
//		
//		if (allFiles.length != 2) {
//			fail();
//		}
//		
//		
//			
//		
//		//tear down and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}
//	
//	
//	
//
//	public void testFindIdenticalBindingSiteof1C4K_length4() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 4;
//		int angleDiscretion = 90;
//		
//		                                
//		String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/1C4K.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/bindingsitedetection/1C4K_A_955.ent"; 
//		
//		
//		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName() + File.separator;
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer		= new OptimizedPhiPsiToOneCharacterTransformer();
//	
//	IFileToStringTranslator iFileToStringTranslator 
//		= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel);
//		
//	
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99;
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
//		File chainADir = new File(outputFilePath + File.separator + "1C4K_A_955.ent-model-0-chain-A");
//		
//		String [] allFiles = chainADir.list();
//		
//		if (allFiles.length !=2) {
//			fail();
//		}
//		
//		
//			
//		
//		//tear down and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}

}
