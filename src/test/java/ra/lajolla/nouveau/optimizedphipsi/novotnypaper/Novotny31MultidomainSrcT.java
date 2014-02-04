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
package ra.lajolla.nouveau.optimizedphipsi.novotnypaper;

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
 * 
 * cite from 
 * 
 * Evaluation of protein fold comparison servers Novotny et al.
 * 
 * 
 * One of the most frequently posed questions after a new structure has been 
 * determined is whether it has a new fold or not. Therefore, it is of interest 
 * to check if the programs, when confronted with a new fold, are able to 
 * recognize this fact by failing to find significant hits in their database. 
 * To investigate this, we selected a number of proteins classified as singletons 
 * in CATH (i.e., without any structural neighbors at the topology level). 
 * We selected four test cases, one from each of the main classes in the CATH 
 * classification: transcription factor Stat-4 (1bgf; CATH code 1.10.532.10; 
 * mainly ), phosphomannose isomerase (1pmi; CATH code 2.30.41.10; mainly ), 
 * colicin Ia (1cii; CATH code 3.30.305.10; mixed -), and pyruvyl-dependent
 *  histidine decarboxylase (1pya; CATH code 4.10.510.10; few SSEs).
 * 
 * 
 */
public class Novotny31MultidomainSrcT extends TestCase {
	
	static String tempDir = "src/test/tmp";
	
	static IResidueToStringTransformer iResidueToStringTransformer
		= new BetterOptimizedPhiPsiTranslator();
	
	static IScoringFunction scoringFunction 
		//= new ScoreAccordingToScoringAtomDistance();
		= new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
				EScoringFunctionRelativeSettings.basedOnSizeOfQueryWhatIsTheTargetInTMSCORE);
	
	
	
	
public void testAllAgainstAll() {

		int ngramSize = 15;
			
		String pathToTargetDirOrFile = "src/test/resources/ASTRAL/d.157.1.1/"; 
		String pathToQueryDirOrFile = "src/test/resources/ASTRAL/d.157.1.1/"; 
		
		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 
		
		boolean dealOnlyWithFirstModel = true;

		INGramTo3DTranslator nGramTo3DTranslator
		= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();
	
		
	
	IFileToStringTranslator iFileToStringTranslator 
		= new PDBProteinTranslator(
				iResidueToStringTransformer, 
				dealOnlyWithFirstModel,
				scoringFunction,
				nGramTo3DTranslator);
		
		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.40;
		

		
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
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 8) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
			
			if (new File(resultDirFileName).list().length != 9) {
				
				fail();
				
			}
			
			
		}
		
		
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}

}
