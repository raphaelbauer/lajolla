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
//public class AlignmentThioredoxinFlyHumanTest extends TestCase {
//	
//	
//	
//public void testAlign1XWCwith3TRX() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 10;
//		int angleDiscretion = 90;
//		
//		
//		String pathToTargetDirOrFile = "src/test/resources/thioredoxin_fly_human/1XWC.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/thioredoxin_fly_human/3TRX.pdb"; 
//		
//		
//		String outputFilePath 
//			= tempDir + File.separator + this.getClass().getSimpleName()+"/"; 
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//			= new StructureToPhiPsiToOneCharacterTransformer(angleDiscretion);
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
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.7;
//		
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
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
//		//check if there are two result dirs with 2 perfect matches each...
//		String [] dirNames = new File(outputFilePath).list();
//		
//		
//		// 0. check if there are 8 result dirs in general:
//		if (dirNames.length != 1) {
//			fail();
//		}
//
//		
//		File resultDirName = new File(outputFilePath);
//		
//		for (String dirName : resultDirName.list()) {
//			
//			if (new File(outputFilePath + File.separator + dirName).list().length != 2 ) {
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
//public void testAlign3TRXwith1XWC() {
//	
//	
//	String tempDir = "src/test/tmp"; 
//
//	int ngramSize = 10;
//	int angleDiscretion = 90;
//	
//	
//	String pathToTargetDirOrFile = "src/test/resources/thioredoxin_fly_human/3TRX.pdb"; 
//	String pathToQueryDirOrFile = "src/test/resources/thioredoxin_fly_human/1XWC.pdb"; 
//	
//	
//	String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 
//	
//	boolean dealOnlyWithFirstModel = true;
//	
//	IResidueToStringTransformer iResidueToStringTransformer
//	= new StructureToPhiPsiToOneCharacterTransformer(angleDiscretion);
//
//	IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistance();
//	
//	
//IFileToStringTranslator iFileToStringTranslator 
//	= new PDBProteinTranslator(
//			iResidueToStringTransformer, 
//			dealOnlyWithFirstModel,
//			scoringFunction);
//	
//
//	double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.7;
//	
//
//	
//	//set up and clean:
//	DeleteDirRecursively.deleteDir(new File(tempDir));
//
//	
//	
//	ProteinMatchRunner.executeSearch(
//			ngramSize, 
//			iFileToStringTranslator, 
//			iResidueToStringTransformer,
//			pathToTargetDirOrFile, 
//			pathToQueryDirOrFile, 
//			outputFilePath, 
//			dealOnlyWithFirstModel, 
//			thresholdOfRefinementScoreUnderWichResultIsOmitted,
//			1);
//		
//		
//	
//	String [] dirNames = new File(outputFilePath).list();
//	
//	
//	
//
//	if (dirNames.length != 1) {
//		fail();
//	}
//
//	
//	File resultDirName = new File(outputFilePath);
//	
//	for (String dirName : resultDirName.list()) {
//		
//		if (new File(outputFilePath + File.separator + dirName).list().length != 2 ) {
//			fail();
//				
//		}
//		
//	}
//		
//	
//	//tear down and clean:
//	DeleteDirRecursively.deleteDir(new File(tempDir));
//	
//	
//	
//}
//
//
//}
