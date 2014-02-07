package com.raphaelbauer.lajolla.protein.manualexecution;

import java.io.File;


import junit.framework.TestCase;
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.transformation.protein.OptimizedPhiPsiToOneCharacterTransformer;
import com.raphaelbauer.lajolla.transformation.protein.PDBProteinTranslator;
import com.raphaelbauer.lajolla.transformation.protein.ProteinMatchRunner;
import com.raphaelbauer.lajolla.utilities.DeleteDirRecursively;

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
