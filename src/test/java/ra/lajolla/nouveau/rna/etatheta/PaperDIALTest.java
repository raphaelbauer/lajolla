//package ra.lajolla.nouveau.rna.etatheta;
//
//import java.io.File;
//
//import junit.framework.TestCase;
//import ra.lajolla.scoringfunctions.IScoringFunction;
//import ra.lajolla.transformation.IFileToStringTranslator;
//import ra.lajolla.transformation.rna.etatheta.PDBRNATranslator;
//import ra.lajolla.transformation.rna.etatheta.RNAEtaThetaMatchRunner;
//import ra.lajolla.utilities.DeleteDirRecursively;
//
//public class PaperDIALTest extends TestCase {
//	
//	static String tempDir = "src/test/tmp/"; 
//	
//	
//	/**
//	 * not from the sarsa paper... but a difficult example. or an ambiguous ine...
//	 * 
//	 * similar to the paper from the DIAL paper, but the pdb entries disapperared somehow...
//	 * 
//	 * 
//	 */
//	public void testMultipleTwoDifferentTRNAs() {
//		
//		int ngramSize = 12;
//		int angleDiscretion = 90;
//		
//		String file1 = "src/test/resources/sarsa_rna_paper/multipleglobal/trnas/1asz.pdb";
//		String file2 = "src/test/resources/sarsa_rna_paper/multipleglobal/trnas/2csx.pdb";
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
//		IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistance();
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
//		double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.31;
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
//		//check if there are two result dirs with 2 perfect matches each...
//		
//		
//
//		String [] dirNames = new File(outputFilePath).list();
//		
//		
//		
//		for (String dirName : dirNames) {
//			
//			String resultDirFileName = outputFilePath + File.separator + "2csx.pdb-model-0-chain-C";
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
//		//tear down and clean:
//		//DeleteDirRecursively.deleteDir(new File(tempDir));
//		
//		
//		
//	}
//
//}
