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
package ra.lajolla.nouveau.rna.optimizedetatheta.rotherspezial;

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

public class RotherSpezial extends TestCase {
	
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
	public void testTRNAs() {
		
		int ngramSize = 10;
		
		String query = "src/test/resources/rna/kristian_spezial/trnas";
		String target = query;
		
		
		
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



		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.39;
		

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(outputFilePath));

		
		
		RNAEtaThetaMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				query, 
				target, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		

		

		String resultDirFileName = outputFilePath + File.separator + "1B23.pdb-model-0-chain-R";
		
		
		if (new File(resultDirFileName).list().length != 4 ) {
			
			fail();
			
		}
		
		resultDirFileName = outputFilePath + File.separator + "1EHZ.pdb-model-0-chain-A";
		
		
		if (new File(resultDirFileName).list().length != 4 ) {
			
			fail();
			
		}
		
		resultDirFileName = outputFilePath + File.separator + "2ow8_0.pdb-model-0-chain-0";
		
		
		if (new File(resultDirFileName).list().length != 4 ) {
			
			fail();
			
		}
		
		

		
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
	
	
	
	
	/**
	 * not from the sarsa paper... but a difficult example. or an ambiguous ine...
	 * 
	 * similar to the paper from the DIAL paper, but the pdb entries disapperared somehow...
	 * 
	 * 
	 */
	public void testTRNA_Lena_1ehzA_1eiy_C() {
		
		int ngramSize = 10;
		
		String query = "src/test/resources/rna/kristian_spezial/trna_lena/1ehz_A.pdb";
		String target = "src/test/resources/rna/kristian_spezial/trna_lena/1eiy_C.pdb";
		
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



		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.3;
		

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(outputFilePath));

		
		
		RNAEtaThetaMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				query, 
				target, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		

			String resultDirFileName = outputFilePath + File.separator + "1eiy_C.pdb-model-0-chain-C";
			
			
			if (new File(resultDirFileName).list().length != 2 ) {
				
				fail();
				
			}
			
			
		
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
	
	
	

	
	
	/**
	 * not from the sarsa paper... but a difficult example. or an ambiguous ine...
	 * 
	 * similar to the paper from the DIAL paper, but the pdb entries disapperared somehow...
	 * 
	 * 
	 */
	public void testTRNA_Lena_1ehzA_2iy5_T() {
		
		int ngramSize = 10;
		
		String query = "src/test/resources/rna/kristian_spezial/trna_lena/1ehz_A.pdb";
		String target = "src/test/resources/rna/kristian_spezial/trna_lena/2iy5_T.pdb";
		
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



		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.3;
		

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(outputFilePath));

		
		
		RNAEtaThetaMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				query, 
				target, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		

			String resultDirFileName = outputFilePath + File.separator + "2iy5_T.pdb-model-0-chain-T";
			
			
			if (new File(resultDirFileName).list().length != 2 ) {
				
				fail();
				
			}

			
		
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}

}
