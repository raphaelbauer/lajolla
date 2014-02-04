//package ra.lajolla.nouveau.rna.etatheta;
//
//import java.io.File;
//import java.util.ArrayList;
//
//import junit.framework.TestCase;
//import ra.lajolla.scoringfunctions.IScoringFunction;
//import ra.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistance;
//import ra.lajolla.transformation.IFileToStringTranslator;
//import ra.lajolla.transformation.protein.PDBProteinTranslator;
//import ra.lajolla.transformation.protein.ProteinMatchRunner;
//import ra.lajolla.transformation.rna.etatheta.PDBRNATranslator;
//import ra.lajolla.transformation.rna.etatheta.RNAEtaThetaMatchRunner;
//import ra.lajolla.utilities.DeleteDirRecursively;
//import ra.lajolla.utilities.CollectFilesRecursiveLowMemory;
//
//public class RNAIdenticalAlignmentTest extends TestCase {
//	
//	static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistance();
//	
//	
//	
//	public void testIdenticalMatchAndSearchAndAlignment() {
//	
//		
//		String tempDir = "src/test/tmp/"; 
//
//		int ngramSize = 26;
//		int angleDiscretion = 90;
//		
//		
//		String pathToTargetDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.etatheta/1B23.pdb"; 
//		String pathToQueryDirOrFile = "src/test/resources/ra.lajolla.transformation.rna.etatheta/1B23.pdb"; 
//		
//		
//		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName() + File.separator;
//		
//		boolean dealOnlyWithFirstModel = true;
//		
//		IFileToStringTranslator iFileToStringTranslator 
//			= new PDBRNATranslator(
//					angleDiscretion, 
//					dealOnlyWithFirstModel,
//					scoringFunction);
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
//			
//			fail();
//			
//			
//		}
//		
////		for (String string : allFiles) {
////			//System.out.println(string);
////			
////			//1B23.pdb1B23.pdb-rmsd-1.4784174356601802E-14-alignmentscore-1.0-1225896101163-mod
////			if (!new File(string).getName().startsWith(
////					"1B23.pdb1B23.pdb-rmsd-1.2183340000656062E-14-alignmentscore-1.0-")) {
////				fail();
////				
////				
////			}
////			
////		}
////		
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
