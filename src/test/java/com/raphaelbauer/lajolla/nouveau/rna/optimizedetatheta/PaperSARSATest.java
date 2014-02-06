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

public class PaperSARSATest extends TestCase {
	

	static String tempDir = "src/test/tmp/"; 
	
	static IResidueToStringTransformer iResidueToStringTransformer
	= new OptimizedStructureToEtaThetaCharacterTransformer();
	
	
	static IScoringFunction scoringFunction 
		= new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();
	

	static INGramTo3DTranslator ngramTo3DTranslator
		= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	


	
	
	public void testPairwiseglobal1() {
		
			int ngramSize = 10;
			
			
			String file1 = "src/test/resources/sarsa_rna_paper/pairwiseglobal/1/1u8d.pdb";
			String file2 = "src/test/resources/sarsa_rna_paper/pairwiseglobal/1/1y26.pdb";
			
			
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
					ngramTo3DTranslator);
		
		
		
			double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.67;
			

			
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
			
			// 1. for each resultdir check if there are enough result files:
			
			String [] dirNames = new File(outputFilePath).list();
			
			
			
			for (String dirName : dirNames) {
				
				String resultDirFileName = outputFilePath + File.separator + "1y26.pdb-model-0-chain-X";
				
				
				if (new File(resultDirFileName).list().length != 2 ) {
					
					fail();
					
				}
				
				
			}

			
			
			
				
			
			//tear down and clean:
			//DeleteDirRecursively.deleteDir(new File(tempDir));
			
			
			
		}
	
	
	public void testPairwiseglobal2() {
		
		
		
		int ngramSize = 10;
		
		
		String file1 = "src/test/resources/sarsa_rna_paper/pairwiseglobal/2/1u8d.pdb";
		String file2 = "src/test/resources/sarsa_rna_paper/pairwiseglobal/2/1y26_incomplete.pdb";
		
		
		
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
				ngramTo3DTranslator);
	
	
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.56;
		

		
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

		String resultDirFileName = outputFilePath + File.separator + "1y26_incomplete.pdb-model-0-chain-X";
			
			
			if (new File(resultDirFileName).list().length != 2 ) {
				
				fail();
				
			}
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
	
	
public void testPairwiseSemiglobal() {
	
	
	//IScoringFunction scoringFunction 
	 //=  new ScoreAccordingToExtremelyStrictSequentialityBasedOnRMSDGoodForSmallSubstructuresOfRNA();
//= new ScoreAccordingToNGramSimilarities();
//= new ScoreAccordingToScoringAtomDistanceAndSequentiality();
//= new ScoreAccordingToNGramSimilarities();
	
	
		
		int ngramSize = 5;
		
		
		String file1 = "src/test/resources/sarsa_rna_paper/pairwisesemiglobal/1hr2.pdb";
		
		String file2 = "src/test/resources/sarsa_rna_paper/pairwisesemiglobal/1j5a.pdb";		
		
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
				ngramTo3DTranslator);
	
	
	
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.60;
		

		
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

		String [] dirNames = new File(outputFilePath).list();
		
		
		
			
			String resultDirFileName = outputFilePath + File.separator + "1j5a.pdb-model-0-chain-A";
			
			
			if (new File(resultDirFileName).list().length != 2 ) {
				
				fail();
				
			}
			
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
	


	
	
public void testPairwiseLocalStructureAlignment() {
	
	
	//IScoringFunction scoringFunction 
	 //=  new ScoreAccordingToExtremelyStrictSequentialityBasedOnRMSDGoodForSmallSubstructuresOfRNA();
//= new ScoreAccordingToNGramSimilarities();
//= new ScoreAccordingToScoringAtomDistanceAndSequentiality();
//= new ScoreAccordingToNGramSimilarities();
	
		
		int ngramSize = 5;
				
		String file1 = "src/test/resources/sarsa_rna_paper/pairwiselocalstructuralalignment/1u8d.pdb";
		String file2 = "src/test/resources/sarsa_rna_paper/pairwiselocalstructuralalignment/1y26.pdb";
		

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
				ngramTo3DTranslator);
	
	
	
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.8;

		
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
	
			
			String resultDirFileName = outputFilePath + File.separator + "1y26.pdb-model-0-chain-X";
			
			
			if (new File(resultDirFileName).list().length != 2 ) {
				
				fail();
				
			}
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
	

public void testPairwiseNormalizedLocalStructureAlignment() {
	
	
	
	int ngramSize = 5;
	
	
	String file1 = "src/test/resources/sarsa_rna_paper/pairwisenormalizedlocalstructural/1l2x.pdb";
	String file2 = "src/test/resources/sarsa_rna_paper/pairwisenormalizedlocalstructural/2a43.pdb";
	
	
	
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
			ngramTo3DTranslator);


	double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.5;
	

	
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
	
		
		String resultDirFileName = outputFilePath + File.separator + "2a43.pdb-model-0-chain-A";
		
		
		if (new File(resultDirFileName).list().length != 2 ) {
			
			fail();
			
		}
		
		
	
	

	
	
	
	
		
	
	//tear down and clean:
	//DeleteDirRecursively.deleteDir(new File(tempDir));
	
	
	
}








public void testMultipleAlignmentTRNA() {
	
	

	int ngramSize = 10;
	
	String file1 = "src/test/resources/sarsa_rna_paper/multipleglobal/trnas";
	String file2 = "src/test/resources/sarsa_rna_paper/multipleglobal/trnas";
	
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
			ngramTo3DTranslator);



	double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.20;
	

	
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
			
		
	
	

		
		
	String [] dirNames = new File(outputFilePath).list();
	
	
	
	for (String dirName : dirNames) {
		
		String resultDirFileName = outputFilePath + File.separator + dirName;
		
		
		if (new File(resultDirFileName).list().length != 7 ) {
			
			fail();
			
		}
		
		
	}

	
	
	//tear down and clean:
	//DeleteDirRecursively.deleteDir(new File(tempDir));
	
	
	
}





public void testMultipleAlignmentPseudoknots() {
	

	int ngramSize = 6;
	
	String file1 = "src/test/resources/sarsa_rna_paper/multipleglobal/pseudoknots";
	String file2 = "src/test/resources/sarsa_rna_paper/multipleglobal/pseudoknots";
	
	
	
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
			ngramTo3DTranslator);



	double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.1;
	

	
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
			
	
	
	
	
	
	String [] dirNames = new File(outputFilePath).list();
	
	
	
	for (String dirName : dirNames) {
		
		String resultDirFileName = outputFilePath + File.separator + dirName;
		
		
		if (new File(resultDirFileName).list().length != 6 ) {
			
			fail();
			
		}
		
		
	}

	


	//tear down and clean:
	//DeleteDirRecursively.deleteDir(new File(tempDir));
	
	
	
}




}
