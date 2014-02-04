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
 * Many proteins contain more than one domain, and we wanted to investigate 
 * how well the programs handled such multidomain proteins. Two members of 
 * the Src protein kinase family (Src kinase, 2src, and Hck kinase, 2hckA) 
 * were chosen. Their structures contain four distinct domains of different 
 * lengths (62-175 amino acids) and the proteins are about 550 amino acids long. 
 * A perfect result in this case would be if a program first finds all proteins 
 * that contain all four domains and then those that contain three of them, then 
 * two of them and, finally, proteins with only one shared domain
 * 
 * 
 */
public class Novotny33CAlphaTestFailT extends TestCase {
	
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
