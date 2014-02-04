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






/**
 * A testcase that did not work in version 1.
 * submitted by elke.
 * 
 * An 
 * 
 * 
 * 
 * @author ra
 *
 */
public class AlignmentElkeBugTest extends TestCase{
	
	static IResidueToStringTransformer iResidueToStringTransformer
		//= new OptimizedPhiPsiToOneCharacterTransformer();
		= new BetterOptimizedPhiPsiTranslator();

	static IScoringFunction scoringFunction 
		//= new ScoreAccordingToScoringAtomDistance();
		= new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
				EScoringFunctionRelativeSettings.basedOnSizeOfQueryWhatIsTheTargetInTMSCORE);

	static INGramTo3DTranslator nGramTo3DTranslator
		= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();



	
	public void testIdenticalMatchAndSearchAndAlignmenQuery1PQ0Target1g5j() {
	
		
		String tempDir = "src/test/tmp"; 

		int ngramSize = 20;
		
		
		String pathToTargetDirOrFile = "src/test/resources/protein_alignment_tests/elkebug/pdb1g5j.ent"; 
		String pathToQueryDirOrFile = "src/test/resources/protein_alignment_tests/elkebug/pdb1PQ0.pdb"; 
		
		
		String outputFilePath = "src/test/tmp/"+this.getClass().getSimpleName()+"/"; 
		boolean dealOnlyWithFirstModel = true;
		

	IFileToStringTranslator iFileToStringTranslator 
		= new PDBProteinTranslator(
				iResidueToStringTransformer, 
				dealOnlyWithFirstModel,
				scoringFunction,
				nGramTo3DTranslator);
		
	
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.5;
		

		
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
		String [] dirNames = new File(outputFilePath).list();
			
		
		if (dirNames.length != 1) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			
			System.out.println(resultDirFileName);
			
			if (new File(resultDirFileName).list().length != 2) {
				
				fail();
				
			}
			
			
		}

	
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
	
	
	
	
	public void testIdenticalMatchAndSearchAndAlignmenQuery1g5jTarget1PQ0() {
	
		
		String tempDir = "src/test/tmp"; 

		int ngramSize = 20;
		
		
		String pathToQueryDirOrFile = "src/test/resources/protein_alignment_tests/elkebug/pdb1g5j.ent"; 
		String pathToTargetDirOrFile = "src/test/resources/protein_alignment_tests/elkebug/pdb1PQ0.pdb"; 

		
		String outputFilePath = "src/test/tmp/ProteinAlignmentTest/"; 
		
		boolean dealOnlyWithFirstModel = true;
		
		//IResidueToStringTransformer iResidueToStringTransformer
		//= new StructureToPhiPsiToOneCharacterTransformer(angleDiscretion);
		
		

		IFileToStringTranslator iFileToStringTranslator 
			= new PDBProteinTranslator(
				iResidueToStringTransformer, 
				dealOnlyWithFirstModel,
				scoringFunction,
				nGramTo3DTranslator);
		
		
		
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.5;
		

		
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
		String [] dirNames = new File(outputFilePath).list();
			
		
		if (dirNames.length != 2) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + "pdb1g5j.ent-model-0-chain-A";
			
			
			if (new File(resultDirFileName).list().length != 2 ) {
				
				fail();
				
			}
			
			
		}
		
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + "pdb1g5j.ent-model-0-chain-B";
			
			
			if (new File(resultDirFileName).list().length != 0 ) {
				
				fail();
				
			}
			
			
		}
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}
	
	
}
