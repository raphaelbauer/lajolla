package com.raphaelbauer.lajolla.nouveau.rna.etatheta;

//package ra.lajolla.nouveau.rna.etatheta;
//
//import java.io.File;
//
//import junit.framework.TestCase;
//import ra.lajolla.scoringfunctions.IScoringFunction;
//import ra.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistance;
//import ra.lajolla.transformation.IFileToStringTranslator;
//import ra.lajolla.transformation.IResidueToStringTransformer;
//import ra.lajolla.transformation.protein.PDBProteinTranslator;
//import ra.lajolla.transformation.protein.ProteinMatchRunner;
//import ra.lajolla.transformation.rna.etatheta.PDBRNATranslator;
//import ra.lajolla.transformation.rna.etatheta.RNAEtaThetaMatchRunner;
//import ra.lajolla.transformation.rna.etatheta.StructureToEtaThetaCharacterTransformer;
//import ra.lajolla.utilities.DeleteDirRecursively;
//
//public class PaperSara extends TestCase {
//	
//
//	static String tempDir = "src/test/tmp/"; 
//	
//	
//	/**
//	 * 
//	 * from the sarsa paper: capriotti et al:
//	 * http://bioinformatics.oxfordjournals.org/cgi/content/full/24/16/i112
//	 * 
//	 * The present results quantify the increase in 
//	 * accuracy of RNA structure alignment compared to existing 
//	 * methods such as ARTS. Although such differences in accuracy may seem 
//	 * small, our benchmark indicates that in average the observed differences 
//	 * are statistically significant at the 95% confidence level. For example, 
//	 * the structural alignment of a sarcin/ricin domain 28S rRNA (PDB code 
//	 * 1q96 chain A) with a 5S Ribosomal RNA (PDB code 1un6 chain E) results in 
//	 * 50% more base pairs and 15.7% more nucleotides aligned by SARA compared 
//	 * to the ARTS alignment (Fig. 6). The difference in such alignment are due 
//	 * to a more accurate superposition of a loop region at the tip of the 
//	 * hairpin, which results in a slightly increase of the RMSD from 1.66 
//	 * Å to 1.78 Å.
//	 * 
//	 */
//	public void test1Q96A1UN6E_10() {
//		
//
//
//		
//			int ngramSize = 10;
//			int angleDiscretion = 90;
//			
//			
//			String file1 = "src/test/resources/paper_sara/1Q96_A.pdb";
//			String file2 = "src/test/resources/paper_sara/1UN6_E.pdb";
//			
//			
//			String outputFilePath
//			= tempDir 
//				+ File.separator 
//				+ this.getClass().getSimpleName() 
//				+ File.separator;
//			  
//			
//			
//			boolean dealOnlyWithFirstModel = true;
//			
//			
//			IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistance();
//			
//			IFileToStringTranslator iFileToStringTranslator 
//			= new PDBRNATranslator(
//					angleDiscretion, 
//					dealOnlyWithFirstModel,
//					scoringFunction);
//		
//		
//		
//			double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.49;
//			
//
//			
//			//set up and clean:
//			DeleteDirRecursively.deleteDir(new File(outputFilePath));
//
//			
//			
//			RNAEtaThetaMatchRunner.executeSearch(
//					ngramSize, 
//					angleDiscretion, 
//					iFileToStringTranslator, 
//					file1, 
//					file2, 
//					outputFilePath, 
//					dealOnlyWithFirstModel, 
//					thresholdOfRefinementScoreUnderWichResultIsOmitted,
//					1);
//					
//			//check if there are two result dirs with 2 perfect matches each...
//			
//			// 1. for each resultdir check if there are enough result files:
//			
//			String [] dirNames = new File(outputFilePath).list();
//			
//			
//			
//			for (String dirName : dirNames) {
//				
//				String resultDirFileName = outputFilePath + File.separator + "1UN6_E.pdb-model-0-chain-E";
//				
//				
//				if (new File(resultDirFileName).list().length != 2 ) {
//					
//					fail();
//					
//				}
//				
//				
//			}
//
//			
//			
//			
//				
//			
//			//tear down and clean:
//			//DeleteDirRecursively.deleteDir(new File(tempDir));
//			
//			
//			
//		}
//	
//	/**
//	 * 
//	 * from the sarsa paper: capriotti et al:
//	 * http://bioinformatics.oxfordjournals.org/cgi/content/full/24/16/i112
//	 * 
//	 * The present results quantify the increase in 
//	 * accuracy of RNA structure alignment compared to existing 
//	 * methods such as ARTS. Although such differences in accuracy may seem 
//	 * small, our benchmark indicates that in average the observed differences 
//	 * are statistically significant at the 95% confidence level. For example, 
//	 * the structural alignment of a sarcin/ricin domain 28S rRNA (PDB code 
//	 * 1q96 chain A) with a 5S Ribosomal RNA (PDB code 1un6 chain E) results in 
//	 * 50% more base pairs and 15.7% more nucleotides aligned by SARA compared 
//	 * to the ARTS alignment (Fig. 6). The difference in such alignment are due 
//	 * to a more accurate superposition of a loop region at the tip of the 
//	 * hairpin, which results in a slightly increase of the RMSD from 1.66 
//	 * Å to 1.78 Å.
//	 * 
//	 */
//	public void test1Q96A1UN6E_6() {
//
//		
//		
//			int ngramSize = 6;
//			int angleDiscretion = 90;
//			
//			
//			String file1 = "src/test/resources/paper_sara/1Q96_A.pdb";
//			String file2 = "src/test/resources/paper_sara/1UN6_E.pdb";
//			  
//			
//			
//			String outputFilePath
//				= tempDir 
//					+ File.separator 
//					+ this.getClass().getSimpleName() 
//					+ File.separator;
//
//				
//			boolean dealOnlyWithFirstModel = true;
//			
//			
//			IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistance();
//			
//			
//			
//			
//			IFileToStringTranslator iFileToStringTranslator 
//			= new PDBRNATranslator(
//					angleDiscretion, 
//					dealOnlyWithFirstModel,
//					scoringFunction);
//		
//		
//		
//			double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.57;
//			
//
//			
//			//set up and clean:
//			DeleteDirRecursively.deleteDir(new File(outputFilePath));
//
//			
//			
//			RNAEtaThetaMatchRunner.executeSearch(
//					ngramSize, 
//					angleDiscretion, 
//					iFileToStringTranslator, 
//					file1, 
//					file2, 
//					outputFilePath, 
//					dealOnlyWithFirstModel, 
//					thresholdOfRefinementScoreUnderWichResultIsOmitted,
//					1);
//					
//			//check if there are two result dirs with 2 perfect matches each...
//			
//			// 1. for each resultdir check if there are enough result files:
//			
//			String [] dirNames = new File(outputFilePath).list();
//			
//			
//			
//			for (String dirName : dirNames) {
//				
//				String resultDirFileName = outputFilePath + File.separator + "1UN6_E.pdb-model-0-chain-E";
//				
//				
//				if (new File(resultDirFileName).list().length != 2 ) {
//					
//					fail();
//					
//				}
//				
//				
//			}
//
//			
//			
//			
//				
//			
//			//tear down and clean:
//			//DeleteDirRecursively.deleteDir(new File(tempDir));
//			
//			
//			
//		}
//
//
//
//}
