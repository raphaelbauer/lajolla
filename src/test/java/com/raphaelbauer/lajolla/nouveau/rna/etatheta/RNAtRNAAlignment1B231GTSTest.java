package com.raphaelbauer.lajolla.nouveau.rna.etatheta;

//package ra.lajolla.nouveau.rna.etatheta;
//
//import java.io.File;
//
//import junit.framework.TestCase;
//import ra.lajolla.scoringfunctions.IScoringFunction;
//import ra.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistance;
//import ra.lajolla.transformation.IFileToStringTranslator;
//import ra.lajolla.transformation.rna.etatheta.PDBRNATranslator;
//import ra.lajolla.transformation.rna.etatheta.RNAEtaThetaMatchRunner;
//import ra.lajolla.utilities.DeleteDirRecursively;
//
//
//
//////////////
////
//// this testcase does not yield good results => has to beinvestigated 
//// trnas are aligned in the wrong direction...
//
//
//
//
////////////////////////////////////////////////////////////////////////////////////////////
//// => no real bug. these are simply two different proteins. or relativel different proteins
//
//
//
//public class RNAtRNAAlignment1B231GTSTest extends TestCase {
//	
//	
//	static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistance();
//	
//	
//	public void testAlignment1B231GTS() {
//	
//		
//		String tempDir = "src/test/tmp/"; 
//
//		int ngramSize = 10;
//		int angleDiscretion = 90;
//		
//		
//		String pathToTargetDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.etatheta/trna_simple/1B23.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.etatheta/trna_simple/1GTS.pdb"; 
//		
//		
//		String outputFilePath = tempDir + this.getClass().getSimpleName() + File.separator;
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IFileToStringTranslator iFileToStringTranslator 
//			= new PDBRNATranslator(
//					angleDiscretion, 
//					dealOnlyWithFirstModel,
//					scoringFunction);
//		
//		
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.2;
//		
//
//		
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//
//		
//		
//		RNAEtaThetaMatchRunner.executeSearch(
//				ngramSize, 
//				angleDiscretion, 
//				iFileToStringTranslator, 
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
//		File chainADir = new File(outputFilePath + File.separator + "1GTS.pdb-model-0-chain-B");
//		
//		String [] allFiles = chainADir.list();
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
//		//tear down and clean:
//		//DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}
////	
////	
////	
//public void testAlignment1GTS1B23() {
//	
//		String tempDir = "src/test/tmp/"; 
//
//		int ngramSize = 10;
//		int angleDiscretion = 90;
//		
//		
//		String pathToTargetDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.etatheta/trna_simple/1GTS.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.etatheta/trna_simple/1B23.pdb"; 
//		
//		
//		String outputFilePath = tempDir + this.getClass().getSimpleName() + File.separator;
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IFileToStringTranslator iFileToStringTranslator 
//			= new PDBRNATranslator(
//					angleDiscretion, 
//					dealOnlyWithFirstModel,
//					scoringFunction);
//		
//		
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.2;
//		
//
//		
//		//set up and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//
//		
//		
//		RNAEtaThetaMatchRunner.executeSearch(
//				ngramSize, 
//				angleDiscretion, 
//				iFileToStringTranslator, 
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
//		File chainADir = new File(outputFilePath + File.separator + "1B23.pdb-model-0-chain-R/");
//		
//		String [] allFiles = chainADir.list();
//		
//		if (allFiles.length != 2) {
//			fail();
//		}
//	
//		
//		//tear down and clean:
//		//DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}
////
//}
