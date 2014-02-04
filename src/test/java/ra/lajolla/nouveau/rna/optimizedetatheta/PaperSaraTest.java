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
package ra.lajolla.nouveau.rna.optimizedetatheta;

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

public class PaperSaraTest extends TestCase {
	

	static String tempDir = "src/test/tmp/"; 
	
	static IResidueToStringTransformer iResidueToStringTransformer
	= new OptimizedStructureToEtaThetaCharacterTransformer();



	static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();


	
	static INGramTo3DTranslator ngramTo3DTranslator
		= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

	/**
	 * 
	 * from the sarsa paper: capriotti et al:
	 * http://bioinformatics.oxfordjournals.org/cgi/content/full/24/16/i112
	 * 
	 * The present results quantify the increase in 
	 * accuracy of RNA structure alignment compared to existing 
	 * methods such as ARTS. Although such differences in accuracy may seem 
	 * small, our benchmark indicates that in average the observed differences 
	 * are statistically significant at the 95% confidence level. For example, 
	 * the structural alignment of a sarcin/ricin domain 28S rRNA (PDB code 
	 * 1q96 chain A) with a 5S Ribosomal RNA (PDB code 1un6 chain E) results in 
	 * 50% more base pairs and 15.7% more nucleotides aligned by SARA compared 
	 * to the ARTS alignment (Fig. 6). The difference in such alignment are due 
	 * to a more accurate superposition of a loop region at the tip of the 
	 * hairpin, which results in a slightly increase of the RMSD from 1.66 
	 * Å to 1.78 Å.
	 * 
	 */
	public void test1Q96A1UN6E_10() {
		


		
			int ngramSize = 10;
			
			
			String file1 = "src/test/resources/paper_sara/1Q96_A.pdb";
			String file2 = "src/test/resources/paper_sara/1UN6_E.pdb";
			
			
			String outputFilePath
			= tempDir 
				+ File.separator 
				+ this.getClass().getSimpleName() 
				+ File.separator;
			  
			
			
			boolean dealOnlyWithFirstModel = true;
			
			
			IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();
			
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
					
			//check if there are two result dirs with 2 perfect matches each...
			
			// 1. for each resultdir check if there are enough result files:
			
			String [] dirNames = new File(outputFilePath).list();
			
			
				String resultDirFileName = outputFilePath + File.separator + "1UN6_E.pdb-model-0-chain-E";
				
				
				if (new File(resultDirFileName).list().length != 2 ) {
					
					fail();
					
				}
				
				
			

			
			
			
				
			
			//tear down and clean:
			//DeleteDirRecursively.deleteDir(new File(tempDir));
			
			
			
		}
	
	/**
	 * 
	 * from the sarsa paper: capriotti et al:
	 * http://bioinformatics.oxfordjournals.org/cgi/content/full/24/16/i112
	 * 
	 * The present results quantify the increase in 
	 * accuracy of RNA structure alignment compared to existing 
	 * methods such as ARTS. Although such differences in accuracy may seem 
	 * small, our benchmark indicates that in average the observed differences 
	 * are statistically significant at the 95% confidence level. For example, 
	 * the structural alignment of a sarcin/ricin domain 28S rRNA (PDB code 
	 * 1q96 chain A) with a 5S Ribosomal RNA (PDB code 1un6 chain E) results in 
	 * 50% more base pairs and 15.7% more nucleotides aligned by SARA compared 
	 * to the ARTS alignment (Fig. 6). The difference in such alignment are due 
	 * to a more accurate superposition of a loop region at the tip of the 
	 * hairpin, which results in a slightly increase of the RMSD from 1.66 
	 * Å to 1.78 Å.
	 * 
	 */
	public void test1Q96A1UN6E_6() {

		
		
			int ngramSize = 6;
			
			String file1 = "src/test/resources/paper_sara/1Q96_A.pdb";
			String file2 = "src/test/resources/paper_sara/1UN6_E.pdb";
			  
			
			
			String outputFilePath
				= tempDir 
					+ File.separator 
					+ this.getClass().getSimpleName() 
					+ File.separator;

				
			boolean dealOnlyWithFirstModel = true;
			
			
			IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();
			
			
			
			
			IFileToStringTranslator iFileToStringTranslator 
			= new PDBRNATranslator(
					iResidueToStringTransformer, 
					dealOnlyWithFirstModel,
					scoringFunction,
					ngramTo3DTranslator);
		
		
		
			double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.34;
			

			
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
			
			
				
				String resultDirFileName = outputFilePath + File.separator + "1UN6_E.pdb-model-0-chain-E";
				
				
				if (new File(resultDirFileName).list().length != 2 ) {
					
					fail();
					
				}
				
				
			

			
			
			
				
			
			//tear down and clean:
			//DeleteDirRecursively.deleteDir(new File(tempDir));
			
			
			
		}



}
