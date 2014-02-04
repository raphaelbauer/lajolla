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

public class ProteinAlignmentIdenticalTest extends TestCase {
	
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();
	
	static IScoringFunction scoringFunction 
	= new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
			EScoringFunctionRelativeSettings.basedOnSizeOfQueryWhatIsTheTargetInTMSCORE);
	
	

	
//	
//	
//	public void testIdenticalMatchAndSearchAndAlignmentLength7() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 7;
//		
//		
//		String pathToTargetDirOrFile = "src/test/resources/1ali.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/1ali.pdb"; 
//		
//		
//		String outputFilePath = tempDir+File.separator+this.getClass().getSimpleName()+"/"; 
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//			= new OptimizedPhiPsiToOneCharacterTransformer();
//	
//	IFileToStringTranslator iFileToStringTranslator 
//		= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel);
//		
//	
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99999;
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
//		//1. stupid test... simply check if the scores of both result files are ok:
//		File chainADir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-A/");
//		
//		String [] allFiles = chainADir.list();
//		
//		if (allFiles.length != 3) {
//			fail();
//		}
//		
//		//2. stupid test... simply check if the scores of both result files are ok:
//		File chainBDir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-B/");
//		
//		allFiles = chainBDir.list();
//		
//		if (allFiles.length != 3) {
//			fail();
//		}
//		
//		
//		
//		
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
//	public void testIdenticalMatchAndSearchAndAlignmentLength9() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 9;
//		int angleDiscretion = 90;
//		
//		
//		String pathToTargetDirOrFile = "src/test/resources/1ali.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/1ali.pdb"; 
//		
//		
//		String outputFilePath = tempDir+File.separator+this.getClass().getSimpleName()+"/"; 
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//		= new OptimizedPhiPsiToOneCharacterTransformer();
//	
//	IFileToStringTranslator iFileToStringTranslator 
//		= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel);
//		
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99999;
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
//		//1. stupid test... simply check if the scores of both result files are ok:
//		File chainADir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-A/");
//		
//		String [] allFiles = chainADir.list();
//		
//		if (allFiles.length != 3) {
//			fail();
//		}
//		
//		//2. stupid test... simply check if the scores of both result files are ok:
//		File chainBDir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-B/");
//		
//		allFiles = chainBDir.list();
//		
//		if (allFiles.length != 3) {
//			fail();
//		}
//		
//		
//		
//		
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
	
	public void testIdenticalMatchAndSearchAndAlignmentLength15() {
	
		
		String tempDir = "src/test/tmp"; 

		int ngramSize = 15;
		
		
		String pathToTargetDirOrFile = "src/test/resources/1ali.pdb"; 
		String pathToQueryDirOrFile = "src/test/resources/1ali.pdb"; 
		
		
		String outputFilePath = tempDir+ File.separator +this.getClass().getSimpleName()+"/"; 
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
		
		
		
		//1. stupid test... simply check if the scores of both result files are ok:
		File chainADir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-A/");
		
		String [] allFiles = chainADir.list();
		
		if (allFiles.length != 3) {
			fail();
		}
		
		//2. stupid test... simply check if the scores of both result files are ok:
		File chainBDir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-B/");
		
		allFiles = chainBDir.list();
		
		if (allFiles.length != 3) {
			fail();
		}
		
		
		
		
		
		
			
		
		//tear down and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
	

	public void testIdenticalMatchAndSearchAndAlignmentLength20() {
	
		
		String tempDir = "src/test/tmp"; 

		int ngramSize = 20;
		int angleDiscretion = 90;
		
		
		String pathToTargetDirOrFile = "src/test/resources/1ali.pdb"; 
		String pathToQueryDirOrFile = "src/test/resources/1ali.pdb"; 
		
		
		String outputFilePath = tempDir+ File.separator +this.getClass().getSimpleName()+"/"; 
		
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
		
		
		
		//1. stupid test... simply check if the scores of both result files are ok:
		File chainADir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-A/");
		
		String [] allFiles = chainADir.list();
		
		if (allFiles.length != 3) {
			fail();
		}
		
		//2. stupid test... simply check if the scores of both result files are ok:
		File chainBDir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-B/");
		
		allFiles = chainBDir.list();
		
		if (allFiles.length != 3) {
			fail();
		}
		
		
		
		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}

	
	public void testIdenticalMatchAndSearchAndAlignmentLength100() {
	
		
		String tempDir = "src/test/tmp"; 

		int ngramSize = 100;
		
		String pathToTargetDirOrFile = "src/test/resources/1ali.pdb"; 
		String pathToQueryDirOrFile = "src/test/resources/1ali.pdb"; 
		
		
		String outputFilePath = 
			tempDir 
			+ File.separator 
			+ this.getClass().getSimpleName() 
			+ "/"; 
		
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
		
		
		
		//1. stupid test... simply check if the scores of both result files are ok:
		File chainADir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-A/");
		
		String [] allFiles = chainADir.list();
		
		if (allFiles.length != 3) {
			fail();
		}
		
		//2. stupid test... simply check if the scores of both result files are ok:
		File chainBDir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-B/");
		
		allFiles = chainBDir.list();
		
		if (allFiles.length != 3) {
			fail();
		}
		
		
		
		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}


}
