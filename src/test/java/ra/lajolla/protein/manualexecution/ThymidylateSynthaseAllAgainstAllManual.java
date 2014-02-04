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
package ra.lajolla.protein.manualexecution;

import java.io.File;


import junit.framework.TestCase;
import ra.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import ra.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import ra.lajolla.scoringfunctions.IScoringFunction;
import ra.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import ra.lajolla.transformation.IFileToStringTranslator;
import ra.lajolla.transformation.IResidueToStringTransformer;
import ra.lajolla.transformation.protein.OptimizedPhiPsiToOneCharacterTransformer;
import ra.lajolla.transformation.protein.PDBProteinTranslator;
import ra.lajolla.transformation.protein.ProteinMatchRunner;
import ra.lajolla.utilities.DeleteDirRecursively;

public class ThymidylateSynthaseAllAgainstAllManual extends TestCase {
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	
	
	static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();
	
	
	public void testAllAgainstAll() {
		
			
			String tempDir = "/media/truecrypt1/tmp/thymidylate_synthase/"; 

			int ngramSize = 15;
			int angleDiscretion = 90;
			
			
			String pathToTargetDirOrFile = "src/test/resources/thymidylate_synthase_pdb/"; 
			String pathToQueryDirOrFile = "src/test/resources/thymidylate_synthase_pdb/"; 
			
			
			String outputFilePath = tempDir +this.getClass().getSimpleName()+"/"; 
			
			boolean dealOnlyWithFirstModel = true;
			
			//IResidueToStringTransformer iResidueToStringTransformer
			//= new StructureToPhiPsiToOneCharacterTransformer(angleDiscretion);
			

			IResidueToStringTransformer iResidueToStringTransformer
			= new OptimizedPhiPsiToOneCharacterTransformer();
		
		IFileToStringTranslator iFileToStringTranslator 
			= new PDBProteinTranslator(
					iResidueToStringTransformer, 
					dealOnlyWithFirstModel,
					scoringFunction,
					ngramTo3DTranslator);
		
		
			double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.2;
			

			
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
			
			

			
			
			
				
			
			//tear down and clean:
			//DeleteDirRecursively.deleteDir(new File(tempDir));
			
			
			
		}
	
	

}