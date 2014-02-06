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
//import ra.lajolla.transformation.protein.PDBProteinTranslator;
//import ra.lajolla.transformation.protein.ProteinMatchRunner;
//import ra.lajolla.transformation.protein.StructureToPhiPsiToOneCharacterTransformer;
//import ra.lajolla.utilities.DeleteDirRecursively;
//
//public class ProteinAlignmentTestNonIdentical extends TestCase {
//	
//	
//	
//	
//	/**
//	 * 
//	 * 
//	 * too exact => should yield NO results...!
//	 * 
//	 * 
//	 */
//	public void testIdenticalMatchAndSearchAndAlignment() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 10;
//		int angleDiscretion = 90;
//
//		
//		
//		String pathToTargetDirOrFile = "src/test/resources/thymidylate_difficult/pdb1axw.ent"; 
//		String pathToQueryDirOrFile = "src/test/resources/thymidylate_difficult/pdb1j3j.ent"; 
//		
//		
//		String outputFilePath =  tempDir + File.separator
//			+this.getClass().getSimpleName()
//			+ File.separator;
//		
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//			= new StructureToPhiPsiToOneCharacterTransformer(
//					angleDiscretion);
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
//		
//		// 0.999 ==> identical:
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.99999;
//		
//
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
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
//		//check if there are two result dirs with 2 perfect matches each...
//		
//		
//
//
//	
//		
//		//1. stupid test... simply check if the scores of both result files are ok:
//		File chainADir = new File(outputFilePath + File.separator + "pdb1j3j.ent-model-0-chain-A/");
//		
//		String [] allFiles = chainADir.list();
//		
//		
//		if (allFiles.length > 0) {
//			fail();
//			
//		}
//		
//		
//		//1. stupid test... simply check if the scores of both result files are ok:
//		 chainADir = new File(outputFilePath + File.separator + "pdb1j3j.ent-model-0-chain-B/");
//		
//		allFiles = chainADir.list();
//		
//		
//		if (allFiles.length > 0) {
//			fail();
//			
//		}
//		
//		
//		
//		
//		//1. stupid test... simply check if the scores of both result files are ok:
//		 chainADir = new File(outputFilePath + File.separator + "pdb1j3j.ent-model-0-chain-C/");
//		
//		allFiles = chainADir.list();
//		
//		
//		if (allFiles.length > 0) {
//			fail();
//			
//		}
//		//1. stupid test... simply check if the scores of both result files are ok:
//		 chainADir = new File(outputFilePath + File.separator + "pdb1j3j.ent-model-0-chain-D/");
//		
//		allFiles = chainADir.list();
//		
//		
//		if (allFiles.length > 0) {
//			fail();
//			
//		}
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
//	
//	
//	
//	
//	
//	
//	/**
//	 * 
//	 * 
//	 * too exact => should yield results => RMSD is better!
//	 * 
//	 * 
//	 */
//	public void testAlignmentsOfDifferentThimidylateSynthases() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 10;
//		int angleDiscretion = 90;
//
//		
//		
//		String pathToTargetDirOrFile = "src/test/resources/thymidylate_difficult/pdb1axw.ent"; 
//		String pathToQueryDirOrFile = "src/test/resources/thymidylate_difficult/pdb1j3j.ent"; 
//		
//		
//		String outputFilePath = tempDir + File.separator
//			+this.getClass().getSimpleName()
//			+ File.separator;
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//		= new StructureToPhiPsiToOneCharacterTransformer(angleDiscretion);
//	
//		IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistance();
//		
//		
//		
//	IFileToStringTranslator iFileToStringTranslator 
//		= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel,
//				scoringFunction);
//		
//		
//		// 0.999 ==> identical:
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.25;
//		
//
//		
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//
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
//	
//		
//		//1. stupid test... simply check if the scores of both result files are ok:
//		File chainADir = new File(outputFilePath + File.separator + "pdb1j3j.ent-model-0-chain-A/");
//		
//		String [] allFiles = chainADir.list();
//		
//		
//		if (allFiles.length > 0) {
//			fail();
//			
//		}
//		
//		
//		//1. stupid test... simply check if the scores of both result files are ok:
//		 chainADir = new File(outputFilePath + File.separator + "pdb1j3j.ent-model-0-chain-B/");
//		
//		allFiles = chainADir.list();
//		
//		
//		if (allFiles.length > 0) {
//			fail();
//			
//		}
//		
//		
//		
//		
//		//1. stupid test... simply check if the scores of both result files are ok:
//		 chainADir = new File(outputFilePath + File.separator + "pdb1j3j.ent-model-0-chain-C/");
//		
//		allFiles = chainADir.list();
//		
//		
//		if (allFiles.length != 3) {
//			fail();
//			
//		}
//		//1. stupid test... simply check if the scores of both result files are ok:
//		 chainADir = new File(outputFilePath + File.separator + "pdb1j3j.ent-model-0-chain-D/");
//		
//		allFiles = chainADir.list();
//		
//		
//		if (allFiles.length != 3) {
//			fail();
//			
//		}
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
//}
