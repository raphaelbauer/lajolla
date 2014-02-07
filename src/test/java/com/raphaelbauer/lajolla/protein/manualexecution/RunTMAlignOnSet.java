
package com.raphaelbauer.lajolla.protein.manualexecution;

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

public class RunTMAlignOnSet extends TestCase {
	
	
	static IScoringFunction scoringFunction 
		= new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
				EScoringFunctionRelativeSettings.basedOnSizeOfQueryWhatIsTheTargetInTMSCORE);
	
	
	static INGramTo3DTranslator ngramTo3DTranslator
	= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	
	public void testAllAgainstAll() {
		
			int ngramSize = 5;
			
			
			String inputFiles = "/media/truecrypt1/benchmarking/datasets/tm-align/"; 
			//String pathToQueryDirOrFile = "/home/ra/Desktop/dataset2/pdb/";
			
			
			String outputFilePath = "/media/truecrypt1/benchmarking/results/tm-align/";
			
			boolean dealOnlyWithFirstModel = true;
			
			IResidueToStringTransformer iResidueToStringTransformer
				= new BetterOptimizedPhiPsiTranslator();
		
		IFileToStringTranslator iFileToStringTranslator 
			= new PDBProteinTranslator(
					iResidueToStringTransformer, 
					dealOnlyWithFirstModel,
					scoringFunction,
					ngramTo3DTranslator);
		
		
			double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.0000000000001;
			

			
			//set up and clean:
			DeleteDirRecursively.deleteDir(new File(outputFilePath));

			
			
			ProteinMatchRunner.executeSearch(
					ngramSize, 
					iFileToStringTranslator, 
					iResidueToStringTransformer,
					inputFiles, 
					inputFiles, 
					outputFilePath, 
					dealOnlyWithFirstModel, 
					thresholdOfRefinementScoreUnderWichResultIsOmitted,
					1);
				
				
			
			
			//check if there are two result dirs with 2 perfect matches each...
			
			

			
			
			
				
			
			//tear down and clean:
			//DeleteDirRecursively.deleteDir(new File(tempDir));
			
			
			
		}
	
	

}
