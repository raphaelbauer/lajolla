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
//
///**
// * A testcase that did not work in version 1.
// * submitted by elke.
// * 
// * An 
// * 
// * 
// * 
// * @author ra
// *
// */
//public class AlignmentElkeBugTest extends TestCase{
//
//	
//	public void testIdenticalMatchAndSearchAndAlignmenQuery1PQ0Target1g5j() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 16;
//		int angleseparation = 90;
//		
//		
//		String pathToTargetDirOrFile = "src/test/resources/protein_alignment_tests/elkebug/pdb1g5j.ent"; 
//		String pathToQueryDirOrFile = "src/test/resources/protein_alignment_tests/elkebug/pdb1PQ0.pdb"; 
//		
//		
//		String outputFilePath = "src/test/tmp/"+this.getClass().getSimpleName()+"/"; 
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//			= new StructureToPhiPsiToOneCharacterTransformer(angleseparation);
//	
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
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.56;
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
//
//		//check if there are two result dirs with 2 perfect matches each...
//		String [] dirNames = new File(outputFilePath).list();
//		
//			
//		
//		if (dirNames.length != 1) {
//			fail();
//		}
//		
//		
//		
//		// 1. for each resultdir check if there are enough result files:
//		for (String dirName : dirNames) {
//			
//			String resultDirFileName = outputFilePath + File.separator + dirName;
//			
//			System.out.println(resultDirFileName);
//			
//			if (new File(resultDirFileName).list().length != 2) {
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
//	public void testIdenticalMatchAndSearchAndAlignmenQuery1g5jTarget1PQ0() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 16;
//		int angleseparation = 90;
//		
//		
//		String pathToQueryDirOrFile = "src/test/resources/protein_alignment_tests/elkebug/pdb1g5j.ent"; 
//		String pathToTargetDirOrFile = "src/test/resources/protein_alignment_tests/elkebug/pdb1PQ0.pdb"; 
//
//		
//		String outputFilePath = "src/test/tmp/ProteinAlignmentTest/"; 
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		//IResidueToStringTransformer iResidueToStringTransformer
//		//= new StructureToPhiPsiToOneCharacterTransformer(angleDiscretion);
//
//		IResidueToStringTransformer iResidueToStringTransformer
//			= new StructureToPhiPsiToOneCharacterTransformer(angleseparation);
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
//		
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.56;
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
//		String [] dirNames = new File(outputFilePath).list();
//			
//		
//		if (dirNames.length != 2) {
//			fail();
//		}
//		
//		
//		
//		// 1. for each resultdir check if there are enough result files:
//		for (String dirName : dirNames) {
//			
//			String resultDirFileName = outputFilePath + File.separator + "pdb1g5j.ent-model-0-chain-A";
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
//		for (String dirName : dirNames) {
//			
//			String resultDirFileName = outputFilePath + File.separator + "pdb1g5j.ent-model-0-chain-B";
//			
//			
//			if (new File(resultDirFileName).list().length != 0 ) {
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
//		//tear down and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}
//	
//	
//}
