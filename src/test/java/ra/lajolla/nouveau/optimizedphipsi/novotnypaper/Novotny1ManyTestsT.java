//package ra.lajolla.nouveau.optimizedphipsi.novotnypaper;
//
//import java.io.File;
//
//import junit.framework.TestCase;
//import ra.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
//import ra.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
//import ra.lajolla.scoringfunctions.EScoringFunctionRelativeSettings;
//import ra.lajolla.scoringfunctions.IScoringFunction;
//import ra.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
//import ra.lajolla.transformation.IFileToStringTranslator;
//import ra.lajolla.transformation.IResidueToStringTransformer;
//import ra.lajolla.transformation.protein.BetterOptimizedPhiPsiTranslator;
//import ra.lajolla.transformation.protein.PDBProteinTranslator;
//import ra.lajolla.transformation.protein.ProteinMatchRunner;
//import ra.lajolla.utilities.DeleteDirRecursively;
//
//
///**
// * 
// * cite from 
// * 
// * Evaluation of protein fold comparison servers Novotny et al.
// * 
// * To assess the performance of the servers, we selected several test cases. 
// * To get an impression of the overall performance, structural families were 
// * chosen from each of the four main structural classes defined in CATH 
// * (see Table VI). The class with few SSEs was also included to test the 
// * sensitivity of the programs in cases with low secondary structure contents. 
// * From this class, the kringle domain was selected, even though it is actually 
// * classified as mainly- in CATH, despite its low secondary structure 
// * content (<15%). 
// * 
// * 
// * Class	CATa	Nr Hb	PDB entries	Name
//Mainly-alpha	1.10.40	2	1rlr 1yfm 1fur 1auw 1jsw 1hyl 1i0a	ribonucleotide reductase protein R1, domain 1
//Mainly-alpha	1.10.164	3	1aq6 1c3u 1fez 1jud 1zrn	L-2-haloacid dehalogenase, domain 2
//Mainly-alpha	1.25.30	3	1b3u 1bk6 1gcj 1ial 1ibr 1qbk 2bct	armadillo repeat
//Mainly-beta	2.30.110	2	1ci0 1dnl 1eje 1i0r	PNP oxidase
//Mainly-beta	2.40.100	1	1a33 1awg 1cyn 1dyw 1ihg 1lop 1qng 1qoi 2rmc	cyclophilin
//Mainly-beta	2.100.10	3	1c3k 1ciy 1jac 1jot 1dlc 1vmo	vitelline membrane outer layer protein I, subunit A
//Mixed alpha-beta	3.10.70	2	1bkf 1grj 1pbk 1rot 1yat	GreA transcript cleavage factor, domain 2
//Mixed alpha-beta	3.40.91	3	1bhm 1cfr 1d2i 1fok	restriction endonuclease
//Mixed alpha-beta	3.70.10	3	1axc 1b77 1czd 1dml 1ge8 1plq	proliferating cell nuclear antigen
//Few SSEs	2.40.20	1	1b2i 1cea 1kdu 1kiv 1krn 1pk4 1pml 5hgp	plasminogen kringle 4
// * 
// * 
// */
//public class Novotny1ManyTestsT extends FatherOfTheNovotnyTestCase {
//	
//	
//	int ngramSize = 20;
//	
//	public void test1_10_40_1jswB03() {
//
//		String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/CathDomainPdb.S60.v3.2.0/1jswB03"; 		
//		
//		String pathToQueryDirOrFile = "";
//		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 
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
//				
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
//			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
//			
//			if (new File(resultDirFileName).list().length != 9) {
//				
//				fail();
//				
//			}
//			
//			
//		}
//	}
//	
//	
//	
//	public void test1_10_40_1furA03() {
//
//		String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/CathDomainPdb.S60.v3.2.0/1furA03"; 
//
//		String pathToTargetDirOrFile = "/media/truecrypt1/benchmarking/datasets/CathDomainPdb.S60.v3.2.0/"; 
//		
//		
//		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 
//		
//		boolean dealOnlyWithFirstModel = true;
//
//		
//
//	IFileToStringTranslator iFileToStringTranslator 
//		= new PDBProteinTranslator(
//				iResidueToStringTransformer, 
//				dealOnlyWithFirstModel,
//				scoringFunction,
//				nGramTo3DTranslator);
//		
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
//			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
//			
//			if (new File(resultDirFileName).list().length != 9) {
//				
//				fail();
//				
//			}
//			
//			
//		}
//	}
//
//}
