package com.raphaelbauer.lajolla.nouveau.rna.etatheta;

//package ra.lajolla.nouveau.rna.etatheta;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.HashSet;
//
//import junit.framework.TestCase;
//import ra.lajolla.scoringfunctions.IScoringFunction;
//import ra.lajolla.scoringfunctions.ScoreAccordingToNGramSimilarities;
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
//public class PaperSARSATest extends TestCase {
//	
//
//	static String tempDir = "src/test/tmp/"; 
//	
//	static IScoringFunction scoringFunction 
//		= new ScoreAccordingToNGramSimilarities();
//	
//	
//	
//	public void testPairwiseglobal1() {
//		
//			int ngramSize = 10;
//			int angleDiscretion = 90;
//			
//			
//			String file1 = "src/test/resources/sarsa_rna_paper/pairwiseglobal/1/1u8d.pdb";
//			String file2 = "src/test/resources/sarsa_rna_paper/pairwiseglobal/1/1y26.pdb";
//			
//			
//			
//			String outputFilePath
//			= tempDir 
//				+ File.separator 
//				+ this.getClass().getSimpleName() 
//				+ File.separator;
//			  
//			
//			boolean dealOnlyWithFirstModel = true;
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
//			double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.4;
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
//				String resultDirFileName = outputFilePath + File.separator + "1y26.pdb-model-0-chain-X";
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
//	public void testPairwiseglobal2() {
//		
//		int ngramSize = 10;
//		int angleDiscretion = 90;
//		
//		
//		String file1 = "src/test/resources/sarsa_rna_paper/pairwiseglobal/2/1u8d.pdb";
//		String file2 = "src/test/resources/sarsa_rna_paper/pairwiseglobal/2/1y26_incomplete.pdb";
//		
//		
//		
//		String outputFilePath
//		= tempDir 
//			+ File.separator 
//			+ this.getClass().getSimpleName() 
//			+ File.separator;
//		  
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		
//		IFileToStringTranslator iFileToStringTranslator 
//		= new PDBRNATranslator(
//				angleDiscretion, 
//				dealOnlyWithFirstModel,
//				scoringFunction);
//	
//	
//	
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.57;
//		
//
//		
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(outputFilePath));
//
//		
//		
//		RNAEtaThetaMatchRunner.executeSearch(
//				ngramSize, 
//				angleDiscretion, 
//				iFileToStringTranslator, 
//				file1, 
//				file2, 
//				outputFilePath, 
//				dealOnlyWithFirstModel, 
//				thresholdOfRefinementScoreUnderWichResultIsOmitted,
//				1);
//			
//		
//		
//
//			
//			
//		
//		
//		//check if there are two result dirs with 2 perfect matches each...
//		String [] dirNames = new File(outputFilePath).list();
//		
//		
//		
//		for (String dirName : dirNames) {
//			
//			String resultDirFileName = outputFilePath + File.separator + "1y26_incomplete.pdb-model-0-chain-X";
//			
//			
//			if (new File(resultDirFileName).list().length != 2 ) {
//				
//				fail();
//				
//			}
//			
//			
//		}
//
//		
//		
//		
//		
//			
//		
//		//tear down and clean:
//		//DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}
//	
//	
//public void testPairwiseSemiglobal() {
//		
//		int ngramSize = 3;
//		int angleDiscretion = 90;
//		
//		
//		String file1 = "src/test/resources/sarsa_rna_paper/pairwisesemiglobal/1hr2.pdb";
//		String file2 = "src/test/resources/sarsa_rna_paper/pairwisesemiglobal/1j5a.pdb";
//		
//		
//		
//		String outputFilePath
//		= tempDir 
//			+ File.separator 
//			+ this.getClass().getSimpleName() 
//			+ File.separator;
//		  
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//
//		
//		IFileToStringTranslator iFileToStringTranslator 
//		= new PDBRNATranslator(
//				angleDiscretion, 
//				dealOnlyWithFirstModel,
//				scoringFunction);
//	
//	
//	
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.4;
//		
//
//		
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(outputFilePath));
//
//		
//		
//		RNAEtaThetaMatchRunner.executeSearch(
//				ngramSize, 
//				angleDiscretion, 
//				iFileToStringTranslator, 
//				file1, 
//				file2, 
//				outputFilePath, 
//				dealOnlyWithFirstModel, 
//				thresholdOfRefinementScoreUnderWichResultIsOmitted,
//				100);
//			
//		
//		
//
//			
//			
//		
//		
//		//check if there are two result dirs with 2 perfect matches each...
//		
//		
//
//		
//		String [] dirNames = new File(outputFilePath).list();
//		
//		
//		
//		for (String dirName : dirNames) {
//			
//			String resultDirFileName = outputFilePath + File.separator + "1j5a.pdb-model-0-chain-A";
//			
//			
//			if (new File(resultDirFileName).list().length != 3 ) {
//				
//				fail();
//				
//			}
//			
//			
//		}
//		
//
//		
//		
//		
//			
//		
//		//tear down and clean:
//		//DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}
//	
//
//
//	
//	
//public void testPairwiseLocalStructureAlignment() {
//		
//		int ngramSize = 4;
//		int angleDiscretion = 90;
//		
//		
//		String file1 = "src/test/resources/sarsa_rna_paper/pairwiselocalstructuralalignment/1u8d.pdb";
//		String file2 = "src/test/resources/sarsa_rna_paper/pairwiselocalstructuralalignment/1y26.pdb";
//		
//		
//		
//		String outputFilePath
//		= tempDir 
//			+ File.separator 
//			+ this.getClass().getSimpleName() 
//			+ File.separator;
//		  
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		
//	
//		IFileToStringTranslator iFileToStringTranslator 
//		= new PDBRNATranslator(
//				angleDiscretion, 
//				dealOnlyWithFirstModel,
//				scoringFunction);
//	
//	
//	
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.5;
//		
//
//		
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(outputFilePath));
//
//		
//		
//		RNAEtaThetaMatchRunner.executeSearch(
//				ngramSize, 
//				angleDiscretion, 
//				iFileToStringTranslator, 
//				file1, 
//				file2, 
//				outputFilePath, 
//				dealOnlyWithFirstModel, 
//				thresholdOfRefinementScoreUnderWichResultIsOmitted,
//				5);
//			
//			
//		
//		//check if there are two result dirs with 2 perfect matches each...
//		
//		String [] dirNames = new File(outputFilePath).list();
//		
//		
//		
//		for (String dirName : dirNames) {
//			
//			String resultDirFileName = outputFilePath + File.separator + "1y26.pdb-model-0-chain-X";
//			
//			
//			if (new File(resultDirFileName).list().length != 3 ) {
//				
//				fail();
//				
//			}
//			
//			
//		}
//		
//
//		
//		
//		
//			
//		
//		//tear down and clean:
//		//DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}
//	
//
//public void testPairwiseNormalizedLocalStructureAlignment() {
//	
//	int ngramSize = 5;
//	int angleDiscretion = 90;
//	
//	
//	String file1 = "src/test/resources/sarsa_rna_paper/pairwisenormalizedlocalstructural/1l2x.pdb";
//	String file2 = "src/test/resources/sarsa_rna_paper/pairwisenormalizedlocalstructural/2a43.pdb";
//	
//	
//	
//	String outputFilePath
//	= tempDir 
//		+ File.separator 
//		+ this.getClass().getSimpleName() 
//		+ File.separator;
//	  
//	
//	boolean dealOnlyWithFirstModel = true;
//	
//	IFileToStringTranslator iFileToStringTranslator 
//	= new PDBRNATranslator(
//			angleDiscretion, 
//			dealOnlyWithFirstModel,
//			scoringFunction);
//
//
//
//	double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.27;
//	
//
//	
//	//set up and clean:
//	DeleteDirRecursively.deleteDir(new File(outputFilePath));
//
//	
//	
//	RNAEtaThetaMatchRunner.executeSearch(
//			ngramSize, 
//			angleDiscretion, 
//			iFileToStringTranslator, 
//			file1, 
//			file2, 
//			outputFilePath, 
//			dealOnlyWithFirstModel, 
//			thresholdOfRefinementScoreUnderWichResultIsOmitted,
//			1);
//		
//	
//	
//
//		
//		
//	
//	
//	//check if there are two result dirs with 2 perfect matches each...
//	
//	
//	String [] dirNames = new File(outputFilePath).list();
//	
//	
//	
//	for (String dirName : dirNames) {
//		
//		String resultDirFileName = outputFilePath + File.separator + "2a43.pdb-model-0-chain-A";
//		
//		
//		if (new File(resultDirFileName).list().length != 2 ) {
//			
//			fail();
//			
//		}
//		
//		
//	}
//	
//
//	
//	
//	
//	
//		
//	
//	//tear down and clean:
//	//DeleteDirRecursively.deleteDir(new File(tempDir));
//	
//	
//	
//}
//
//
//
//
//
//
//
//
//public void testMultipleAlignmentTRNA() {
//	
//	int ngramSize = 8;
//	int angleDiscretion = 180;
//	
//	String file1 = "src/test/resources/sarsa_rna_paper/multipleglobal/trnas";
//	String file2 = "src/test/resources/sarsa_rna_paper/multipleglobal/trnas";
//	
//	
//	
//	String outputFilePath
//	= tempDir 
//		+ File.separator 
//		+ this.getClass().getSimpleName() 
//		+ File.separator;
//	  
//	
//	boolean dealOnlyWithFirstModel = true;
//	
//	IFileToStringTranslator iFileToStringTranslator 
//	= new PDBRNATranslator(
//			angleDiscretion, 
//			dealOnlyWithFirstModel,
//			scoringFunction);
//
//
//
//	double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.1;
//	
//
//	
//	//set up and clean:
//	DeleteDirRecursively.deleteDir(new File(outputFilePath));
//
//	
//	
//	RNAEtaThetaMatchRunner.executeSearch(
//			ngramSize, 
//			angleDiscretion, 
//			iFileToStringTranslator, 
//			file1, 
//			file2, 
//			outputFilePath, 
//			dealOnlyWithFirstModel, 
//			thresholdOfRefinementScoreUnderWichResultIsOmitted,
//			1);
//		
//	
//	
//
//		
//		
//	
//	
//	//check if there are two result dirs with 2 perfect matches each...
//	
//	
//
//	String [] dirNames = new File(outputFilePath).list();
//	
//	
//	
//	for (String dirName : dirNames) {
//		
//		String resultDirFileName = outputFilePath + File.separator + "1y26.pdb-model-0-chain-X";
//		
//		
//		if (new File(resultDirFileName).list().length != 3 ) {
//			
//			fail();
//			
//		}
//		
//		
//	}
//	
//
//	
//	
//	
//		
//	
//	//tear down and clean:
//	//DeleteDirRecursively.deleteDir(new File(tempDir));
//	
//	
//	
//}
//
//
//
//
//
//public void testMultipleAlignmentPseudoknots() {
//	
//	int ngramSize = 6;
//	int angleDiscretion = 90;
//	
//	String file1 = "src/test/resources/sarsa_rna_paper/multipleglobal/pseudoknots";
//	String file2 = "src/test/resources/sarsa_rna_paper/multipleglobal/pseudoknots";
//	
//	
//	
//	String outputFilePath
//	= tempDir 
//		+ File.separator 
//		+ this.getClass().getSimpleName() 
//		+ File.separator;
//	  
//	
//	boolean dealOnlyWithFirstModel = true;
//	
//	IFileToStringTranslator iFileToStringTranslator 
//	= new PDBRNATranslator(
//			angleDiscretion, 
//			dealOnlyWithFirstModel,
//			scoringFunction);
//
//
//
//	double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.1;
//	
//
//	
//	//set up and clean:
//	DeleteDirRecursively.deleteDir(new File(outputFilePath));
//
//	
//	
//	RNAEtaThetaMatchRunner.executeSearch(
//			ngramSize, 
//			angleDiscretion, 
//			iFileToStringTranslator, 
//			file1, 
//			file2, 
//			outputFilePath, 
//			dealOnlyWithFirstModel, 
//			thresholdOfRefinementScoreUnderWichResultIsOmitted,
//			1);
//		
//	
//	
//
//		
//		
//	
//	//these two are correct:
//		String resultDirFileName = outputFilePath + File.separator + "2ap0.pdb-model-0-chain-A";
//		
//		HashSet<String> hashSet = new HashSet<String>();
//		
//		hashSet.add("t-1kpy.pdb-m-0-c-A-SCORE-0.21-0.pdb");
//		hashSet.add("t-1l2x.pdb-m-0-c-A-SCORE-0.26-0.pdb");
//		hashSet.add("t-1yg4.pdb-m-0-c-A-SCORE-0.36-0.pdb");
//		hashSet.add("t-2ap0.pdb-m-0-c-A-SCORE-1.00-0.pdb");
//		hashSet.add("t-2ap5.pdb-m-0-c-A-SCORE-0.43-0.pdb");
//		hashSet.add("q-2ap0.pdb_original.pdb");
//			
//		
//		for (String file : new File(resultDirFileName).list()) {
//			
//			System.out.println(file);
//			if (!hashSet.contains(file)) {
//				fail();
//				
//			}
//			
//		}
//		
//
//		resultDirFileName = outputFilePath + File.separator + "1yg4.pdb-model-0-chain-A";
//		
//		hashSet = new HashSet<String>();
//		
//		hashSet.add("t-2ap5.pdb-m-0-c-A-SCORE-0.32-0.pdb");
//		hashSet.add("t-2ap0.pdb-m-0-c-A-SCORE-0.36-0.pdb");
//		hashSet.add("t-1yg4.pdb-m-0-c-A-SCORE-1.00-0.pdb");
//		hashSet.add("t-1l2x.pdb-m-0-c-A-SCORE-0.26-0.pdb");
//		hashSet.add("t-1kpy.pdb-m-0-c-A-SCORE-0.14-0.pdb");
//		hashSet.add("q-1yg4.pdb_original.pdb");
//			
//		
//		for (String file : new File(resultDirFileName).list()) {
//			
//			System.out.println(file);
//			if (!hashSet.contains(file)) {
//				fail();
//				
//			}
//			
//		}
//		
//		
//
//	
//	
//	
//
//	//tear down and clean:
//	//DeleteDirRecursively.deleteDir(new File(tempDir));
//	
//	
//	
//}
//
//
//
//
//}
