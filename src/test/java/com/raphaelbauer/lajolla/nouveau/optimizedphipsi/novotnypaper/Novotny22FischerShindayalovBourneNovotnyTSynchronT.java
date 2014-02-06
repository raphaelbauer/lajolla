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
package com.raphaelbauer.lajolla.nouveau.optimizedphipsi.novotnypaper;

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


/**
 * 
 * cite from 
 * 
 * Evaluation of protein fold comparison servers Novotny et al.
 * 
 * ...
 * In a second experiment, we used 10 nontrivial structural 
 * similarities that were taken from Fischer et al.[34] and 
 * that also have been studied by Shindyalov and Bourne.[11] In addition, 
 * the structure of ribosome antiassociation factor IF6 (PDB code 1g61) 
 * was included in this experiment. This protein was initially described 
 * as having a new fold[35] because at the time of publication, the authors 
 * failed to find any structural similarity in the PDB using DALI. However, 
 * it turned out that its fold is similar to that of the amidino-transferases.
 * [36][37] Thus, the 11 difficult cases were 1bgeB (similarity with 2gmfA), 
 * 1cewI (1molA), 1cid (2rhe), 1crl (1ede), 1fxiA (1ubq), 1ten (3hhrB), 1tie (4fgf), 
 * 2azaA (1paz), 2sim (1nsbA), 3hlaB (2rhe), and 1g61 (1jdw). In these tests, 
 * success was defined as the ability of a program to retrieve the target structure 
 * or a close relative (mutant, complex, or homologue).
 * ...
 * 
 * 
 */
public class Novotny22FischerShindayalovBourneNovotnyTSynchronT extends TestCase {
	
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
