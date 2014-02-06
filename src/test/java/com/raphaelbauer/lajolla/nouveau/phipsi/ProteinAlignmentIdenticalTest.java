package com.raphaelbauer.lajolla.nouveau.phipsi;

//package ra.lajolla.nouveau.phipsi;
//
//import java.io.File;
//
//import junit.framework.TestCase;
//import ra.lajolla.scoringfunctions.IScoringFunction;
//import ra.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistance;
//import ra.lajolla.transformation.IFileToStringTranslator;
//import ra.lajolla.transformation.IResidueToStringTransformer;
//import ra.lajolla.transformation.protein.OptimizedPhiPsiToOneCharacterTransformer;
//import ra.lajolla.transformation.protein.PDBProteinTranslator;
//import ra.lajolla.transformation.protein.ProteinMatchRunner;
//import ra.lajolla.transformation.protein.StructureToPhiPsiToOneCharacterTransformer;
//import ra.lajolla.utilities.DeleteDirRecursively;
//
//public class ProteinAlignmentIdenticalTest extends TestCase {
//	
//	
//	
////	public void testIdenticalMatchAndSearchAndAlignmentLength7() {
////	
////		
////		String tempDir = "src/test/tmp"; 
////
////		int ngramSize = 7;
////		
////		
////		String pathToTargetDirOrFile = "src/test/resources/1ali.pdb"; 
////		String pathToQueryDirOrFile = "src/test/resources/1ali.pdb"; 
////		
////		
////		String outputFilePath = tempDir+File.separator+this.getClass().getSimpleName()+"/"; 
////		boolean dealOnlyWithFirstModel = true;
////		
////		IResidueToStringTransformer iResidueToStringTransformer
////			= new StructureToPhiPsiToOneCharacterTransformer(90);
////	
////	IFileToStringTranslator iFileToStringTranslator 
////		= new PDBProteinTranslator(
////				iResidueToStringTransformer, 
////				dealOnlyWithFirstModel);
////		
////	
////		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99999;
////		
////
////		
////		//set up and clean:
////		DeleteDirRecursively.deleteDir(new File(tempDir));
////
////		
////		
////		ProteinMatchRunner.executeSearch(
////				ngramSize, 
////				iFileToStringTranslator, 
////				iResidueToStringTransformer,
////				pathToTargetDirOrFile, 
////				pathToQueryDirOrFile, 
////				outputFilePath, 
////				dealOnlyWithFirstModel, 
////				thresholdOfRefinementScoreUnderWichResultIsOmitted,
////				1);
////			
////			
////		
////		
////		//check if there are two result dirs with 2 perfect matches each...
////		
////		
////		
////		//1. stupid test... simply check if the scores of both result files are ok:
////		File chainADir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-A/");
////		
////		String [] allFiles = chainADir.list();
////		
////		if (allFiles.length != 3) {
////			fail();
////		}
////		
////		//2. stupid test... simply check if the scores of both result files are ok:
////		File chainBDir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-B/");
////		
////		allFiles = chainBDir.list();
////		
////		if (allFiles.length != 3) {
////			fail();
////		}
////		
////		
////		
////		
////		
////		
////			
////		
////		//tear down and clean:
////		DeleteDirRecursively.deleteDir(new File(tempDir));
////		
////		
////		
////	}
////	
////	
////	
////	public void testIdenticalMatchAndSearchAndAlignmentLength9() {
////	
////		
////		String tempDir = "src/test/tmp"; 
////
////		int ngramSize = 9;
////		int angleDiscretion = 90;
////		
////		
////		String pathToTargetDirOrFile = "src/test/resources/1ali.pdb"; 
////		String pathToQueryDirOrFile = "src/test/resources/1ali.pdb"; 
////		
////		
////		String outputFilePath 
////			= tempDir + File.separator + this.getClass().getSimpleName()+"/"; 
////		
////		boolean dealOnlyWithFirstModel = true;
////		
////		IResidueToStringTransformer iResidueToStringTransformer
////		= new StructureToPhiPsiToOneCharacterTransformer(90);
////	
////	IFileToStringTranslator iFileToStringTranslator 
////		= new PDBProteinTranslator(
////				iResidueToStringTransformer, 
////				dealOnlyWithFirstModel);
////		
////		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99999;
////		
////
////		
////		//set up and clean:
////		DeleteDirRecursively.deleteDir(new File(tempDir));
////
////		
////		
////		ProteinMatchRunner.executeSearch(
////				ngramSize, 
////				iFileToStringTranslator, 
////				iResidueToStringTransformer,
////				pathToTargetDirOrFile, 
////				pathToQueryDirOrFile, 
////				outputFilePath, 
////				dealOnlyWithFirstModel, 
////				thresholdOfRefinementScoreUnderWichResultIsOmitted,
////				1);
////			
////			
////		
////		
////		//check if there are two result dirs with 2 perfect matches each...
////		
////		
////		
////		//1. stupid test... simply check if the scores of both result files are ok:
////		File chainADir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-A/");
////		
////		String [] allFiles = chainADir.list();
////		
////		if (allFiles.length != 3) {
////			fail();
////		}
////		
////		//2. stupid test... simply check if the scores of both result files are ok:
////		File chainBDir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-B/");
////		
////		allFiles = chainBDir.list();
////		
////		if (allFiles.length != 3) {
////			fail();
////		}
////		
////		
////		
////		
////		
////		
////			
////		
////		//tear down and clean:
////		DeleteDirRecursively.deleteDir(new File(tempDir));
////		
////		
////		
////	}
//	
//	public void testIdenticalMatchAndSearchAndAlignmentLength11() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 11;
//		int angleDiscretion = 90;
//		
//		
//		String pathToTargetDirOrFile = "src/test/resources/1ali.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/1ali.pdb"; 
//		
//		
//		String outputFilePath = tempDir+ File.separator +this.getClass().getSimpleName()+"/"; 
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//		= new StructureToPhiPsiToOneCharacterTransformer(90);
//	
//		IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistance();
//		
//		
//	IFileToStringTranslator iFileToStringTranslator 
//		= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel,
//				scoringFunction);
//		
//
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99999;
//		
//
//		
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//
//		
//		
//		ProteinMatchRunner.executeSearch(
//				ngramSize, 
//				iFileToStringTranslator, 
//				iResidueToStringTransformer,
//				pathToTargetDirOrFile, 
//				pathToQueryDirOrFile, 
//				outputFilePath, 
//				dealOnlyWithFirstModel, 
//				thresholdOfRefinementScoreUnderWichResultIsOmitted,
//				1);
//			
//			
//		
//		
//		//check if there are two result dirs with 2 perfect matches each...
//		
//		
//		
//		//1. stupid test... simply check if the scores of both result files are ok:
//		File chainADir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-A/");
//		
//		String [] allFiles = chainADir.list();
//		
//		if (allFiles.length != 3) {
//			fail();
//		}
//		
//		//2. stupid test... simply check if the scores of both result files are ok:
//		File chainBDir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-B/");
//		
//		allFiles = chainBDir.list();
//		
//		if (allFiles.length != 3) {
//			fail();
//		}
//		
//		
//		
//		
//		
//		
//			
//		
//		//tear down and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}
//	
//	
//	
//	
//	public void testIdenticalMatchAndSearchAndAlignmentLength20() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 20;
//		int angleDiscretion = 90;
//		
//		
//		String pathToTargetDirOrFile = "src/test/resources/1ali.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/1ali.pdb"; 
//		
//		
//		String outputFilePath = tempDir+  File.separator  + this.getClass().getSimpleName()+"/"; 
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//		= new StructureToPhiPsiToOneCharacterTransformer(90);
//	
//		
//		IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistance();
//		
//		
//		
//		IFileToStringTranslator iFileToStringTranslator 
//			= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel,
//				scoringFunction);
//		
//	
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99999;
//		
//
//		
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//
//		
//		
//		ProteinMatchRunner.executeSearch(
//				ngramSize, 
//				iFileToStringTranslator, 
//				iResidueToStringTransformer,
//				pathToTargetDirOrFile, 
//				pathToQueryDirOrFile, 
//				outputFilePath, 
//				dealOnlyWithFirstModel, 
//				thresholdOfRefinementScoreUnderWichResultIsOmitted,
//				1);
//			
//			
//		
//		
//		//check if there are two result dirs with 2 perfect matches each...
//		
//		
//		
//		//1. stupid test... simply check if the scores of both result files are ok:
//		File chainADir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-A/");
//		
//		String [] allFiles = chainADir.list();
//		
//		if (allFiles.length != 3) {
//			fail();
//		}
//		
//		//2. stupid test... simply check if the scores of both result files are ok:
//		File chainBDir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-B/");
//		
//		allFiles = chainBDir.list();
//		
//		if (allFiles.length != 3) {
//			fail();
//		}
//		
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
//	public void testIdenticalMatchAndSearchAndAlignmentLength100() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 400;
//		
//		String pathToTargetDirOrFile = "src/test/resources/1ali.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/1ali.pdb"; 
//		
//		
//		String outputFilePath = 
//			tempDir 
//			+ File.separator 
//			+ this.getClass().getSimpleName() 
//			+ "/"; 
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//		= new StructureToPhiPsiToOneCharacterTransformer(90);
//		
//		IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistance();
//		
//	
//		IFileToStringTranslator iFileToStringTranslator 
//			= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel,
//				scoringFunction);
//		
//	
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99999;
//		
//
//		
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//
//		
//		
//		ProteinMatchRunner.executeSearch(
//				ngramSize, 
//				iFileToStringTranslator, 
//				iResidueToStringTransformer,
//				pathToTargetDirOrFile, 
//				pathToQueryDirOrFile, 
//				outputFilePath, 
//				dealOnlyWithFirstModel, 
//				thresholdOfRefinementScoreUnderWichResultIsOmitted,
//				1);
//			
//			
//		
//		
//		//check if there are two result dirs with 2 perfect matches each...
//		
//		
//		
//		//1. stupid test... simply check if the scores of both result files are ok:
//		File chainADir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-A/");
//		
//		String [] allFiles = chainADir.list();
//		
//		if (allFiles.length != 2) {
//			fail();
//		}
//		
//		//2. stupid test... simply check if the scores of both result files are ok:
//		File chainBDir = new File(outputFilePath + File.separator + "1ali.pdb-model-0-chain-B/");
//		
//		allFiles = chainBDir.list();
//		
//		if (allFiles.length != 2) {
//			fail();
//		}
//		
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
//}
