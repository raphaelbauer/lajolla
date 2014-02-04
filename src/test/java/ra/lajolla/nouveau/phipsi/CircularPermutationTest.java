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
//public class CircularPermutationTest extends TestCase {
//
//	/**
//	 * test if all 2 chains are crosswise found:
//	 */
//	public void test1AQI_1BOO() {
//	
//		String tempDir = "src/test/tmp/"; 
//		
//
//		int ngramSize = 11;
//		int angleDiscretion = 90;
//		 
//		
//		String pathToTargetDirOrFile = "src/test/resources/protein_alignment_tests/circular_permutated/1aqi_1boo/1AQI.pdb";
//
//		String pathToQueryDirOrFile = "src/test/resources/protein_alignment_tests/circular_permutated/1aqi_1boo/1BOO.pdb";
//		
//		
//		String outputFilePath = tempDir + this.getClass().getSimpleName() + File.separator;
//		
//		
//		
//		boolean dealOnlyWithFirstModel = true;
//
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
//				false,
//				scoringFunction);
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
//		//1. stupid test... simply check if the scores of both result files are ok:
//		File chainADir = new File(outputFilePath + File.separator + "1BOO.pdb-model-0-chain-A/");
//		
//		String [] allFiles = chainADir.list();
//		
//		if (allFiles.length != 3) {
//			fail();
//		}
//	
//		
//		
//		
//			//fail();
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
