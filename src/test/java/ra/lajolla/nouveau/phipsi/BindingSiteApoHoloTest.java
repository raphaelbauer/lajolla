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
//public class BindingSiteApoHoloTest extends TestCase {
//	
//	
//public void testLength11() {
//	
//		
//		String tempDir = "src/test/tmp"; 
//
//		int ngramSize = 11;
//		int angleDiscretion = 90;
//		
//		//apoform
//		String pathToTargetDirOrFile    = "src/test/resources/bindingsitedetection/dominic_testfall/proteine/1ABB_C.ent";   
//		
//		//binding site of holo
//		String pathToQueryDirOrFile=  "src/test/resources/bindingsitedetection/dominic_testfall/binding_sites/1H5U_A_999.ent"; 
//		
//		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() +"/"; 
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//			= new StructureToPhiPsiToOneCharacterTransformer(90);
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
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.70;
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
//		
//		
//		File chainADir = new File(outputFilePath + File.separator + "1H5U_A_999.ent-model-0-chain-A");
//		
//		String [] allFiles = chainADir.list();
//
//		
//		if (allFiles.length !=2) {
//			fail();
//		}
//		
//		//tear down and clean:
//		DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//	}
//	
//	
////	
////	public void testLength10() {
////	
////		
////		String tempDir = "src/test/tmp"; 
////
////		int ngramSize = 10;
////		int angleDiscretion = 90;
////		
////		//apoform
////		String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/dominic_testfall/proteine/1ABB_C.ent";   
////		
////		//binding site of holo
////		String pathToQueryDirOrFile =  "src/test/resources/bindingsitedetection/dominic_testfall/binding_sites/1H5U_A_999.ent"; 
////		
////		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() +"/"; 
////		
////		boolean dealOnlyWithFirstModel = true;
////		
////		IResidueToStringTransformer iResidueToStringTransformer
////			= new StructureToPhiPsiToOneCharacterTransformer(90);
////	
////		IFileToStringTranslator iFileToStringTranslator 
////			= new PDBProteinTranslator(
////				iResidueToStringTransformer, 
////				dealOnlyWithFirstModel);
////		
////		
////		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.79;
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
////		
////		
////		File chainADir = new File(outputFilePath + File.separator + "1H5U_A_999.ent-model-0-chain-A");
////		
////		String [] allFiles = chainADir.list();
////
////		
////		if (allFiles.length !=2) {
////			fail();
////		}
////		
////		//tear down and clean:
////		DeleteDirRecursively.deleteDir(new File(tempDir));
////		
////	}
////	
////	
////	public void testLength8() {
////	
////		
////		String tempDir = "src/test/tmp"; 
////
////		int ngramSize = 8;
////		
////		//apoform
////		String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/dominic_testfall/proteine/1ABB_C.ent";   
////		
////		//binding site of holo
////		String pathToQueryDirOrFile =  "src/test/resources/bindingsitedetection/dominic_testfall/binding_sites/1H5U_A_999.ent"; 
////		
////		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() + "/"; 
////		
////		boolean dealOnlyWithFirstModel = true;
////
////		IResidueToStringTransformer iResidueToStringTransformer
////		= new StructureToPhiPsiToOneCharacterTransformer(90);
////		IFileToStringTranslator iFileToStringTranslator 
////			= new PDBProteinTranslator(
////				iResidueToStringTransformer, 
////				dealOnlyWithFirstModel);
////		
////		
////		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.74;
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
////		
////		
////		File chainADir = new File(outputFilePath + File.separator + "1H5U_A_999.ent-model-0-chain-A");
////		
////		String [] allFiles = chainADir.list();
////
////		
////		if (allFiles.length != 2) {
////			fail();
////		}
////		
////		//tear down and clean:
////		DeleteDirRecursively.deleteDir(new File(tempDir));
////		
////	}
////	
////	
////public void testLength6() {
////	
////		
////		String tempDir = "src/test/tmp"; 
////
////		int ngramSize = 6;
////		
////		//apoform
////		String pathToTargetDirOrFile = "src/test/resources/bindingsitedetection/dominic_testfall/proteine/1ABB_C.ent";   
////		
////		//binding site of holo
////		String pathToQueryDirOrFile =  "src/test/resources/bindingsitedetection/dominic_testfall/binding_sites/1H5U_A_999.ent"; 
////		
////		String outputFilePath = "src/test/tmp/" + this.getClass().getSimpleName() +"/"; 
////		
////		boolean dealOnlyWithFirstModel = true;
////
////		IResidueToStringTransformer iResidueToStringTransformer
////		= new StructureToPhiPsiToOneCharacterTransformer(90);
////	
////		IFileToStringTranslator iFileToStringTranslator 
////			= new PDBProteinTranslator(
////				iResidueToStringTransformer, 
////				dealOnlyWithFirstModel);
////		
////		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.77;
////		
////		//set up and clean:
////		DeleteDirRecursively.deleteDir(new File(tempDir));
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
////		//check if there are two result dirs with 2 perfect matches each...
////
////		
////		File chainADir = new File(outputFilePath + File.separator + "1H5U_A_999.ent-model-0-chain-A");
////		
////		String [] allFiles = chainADir.list();
////
////		
////		if (allFiles.length != 2) {
////			fail();
////		}
////		
////		//tear down and clean:
////		DeleteDirRecursively.deleteDir(new File(tempDir));
////		
////	}
//
//}
