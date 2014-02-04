//package ra.lajolla.unused;
//import java.io.File;
//
//import ra.lajolla.transformation.IFileToStringTranslator;
//import ra.lajolla.transformation.IResidueToStringTransformer;
//import ra.lajolla.transformation.protein.PDBProteinTranslator;
//import ra.lajolla.transformation.protein.ProteinMatchRunner;
//import ra.lajolla.transformation.protein.StructureToPhiPsiToOneCharacterTransformer;
//
//
//public class RunAgainstASTRALPerformanceCheck {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		
//		boolean dealWithAllModels = false;
//		
//		int advancedNGramSize = 7;
//		
//		int advancedAngleDiscretion = 90; 
//		
//		
//		IResidueToStringTransformer iResidueToStringTransformer
//			= new StructureToPhiPsiToOneCharacterTransformer(advancedAngleDiscretion);
//	
//		IFileToStringTranslator iFileToStringTranslator 
//		= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				!dealWithAllModels);
//	
//		
//		
//		String targetDirOfFile//= "/home/ra/lajolla/astral/pdbstyle/i4/d1i4da_.ent";
//			= "/home/ra/lajolla/astral/";
//		
//		String queryDirOrFile = targetDirOfFile;
//			//= "/home/ra/lajolla/astral/pdbstyle/i4/d1i4da_.ent";
//		String outputDir 
//			= "/home/ra/tmp/runagainstASTRALBenchmarking/";
//		
//		
//		double minimumRefinementScore = 0.4d;
//		
//		double advancedScoringRadius =  2d;
//		
//		
//		ProteinMatchRunner.executeSearch(
//				advancedNGramSize, 
//				//advancedAngleDiscretion, 
//				iFileToStringTranslator,
//				iResidueToStringTransformer,
//				targetDirOfFile, 
//				queryDirOrFile, 
//				outputDir + File.separator,
//				!dealWithAllModels,
//				minimumRefinementScore,
//				1);
//
//	}
//
//}
