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
//public class AlignmentAstrald15711Test extends TestCase {
//	
//	
//	
//public void testAllAgainstAll() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 10;
//		int angleDiscretion = 90;
//		
//		
//		String pathToTargetDirOrFile = "src/test/resources/ASTRAL/d.157.1.1/"; 
//		String pathToQueryDirOrFile = "src/test/resources/ASTRAL/d.157.1.1/"; 
//		
//		
//		String outputFilePath = "src/test/tmp/"+this.getClass().getSimpleName()+"/"; 
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
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.3;
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
//				1
//				);
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
//		if (dirNames.length != 8) {
//			fail();
//		}
//		
//		
//		
//		// 1. for each resultdir check if there are enough result files:
//		for (String dirName : dirNames) {
//			
//			String resultDirFileName = outputFilePath + File.separator + dirName;
//			//System.out.println(resultDirFileName);
//			
//			if (new File(resultDirFileName).list().length != 9) {
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
//}
