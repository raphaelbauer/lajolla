/*
 * Copyright (c) Raphael A. Bauer (mechanical.bauer@gmail.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 */
package ra.lajolla.nouveau.optimizedphipsi.novotnypaper;

import java.io.File;

import junit.framework.TestCase;
import ra.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import ra.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import ra.lajolla.scoringfunctions.EScoringFunctionRelativeSettings;
import ra.lajolla.scoringfunctions.IScoringFunction;
import ra.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import ra.lajolla.transformation.IFileToStringTranslator;
import ra.lajolla.transformation.IResidueToStringTransformer;
import ra.lajolla.transformation.protein.BetterOptimizedPhiPsiTranslator;
import ra.lajolla.transformation.protein.PDBProteinTranslator;
import ra.lajolla.transformation.protein.ProteinMatchRunner;
import ra.lajolla.utilities.DeleteDirRecursively;


/**
 * 
 * cite from 
 * 
 * Evaluation of protein fold comparison servers Novotny et al.
 * 
 * ...
 * In a second experiment, we used 10 nontrivial structural 
 * similarities that were taken from Fischer et al.[34] and 
 * that also have been studied by Shindyalov and Bourne.[11] In addition, 
 * the structure of ribosome antiassociation factor IF6 (PDB code 1g61) 
 * was included in this experiment. This protein was initially described 
 * as having a new fold[35] because at the time of publication, the authors 
 * failed to find any structural similarity in the PDB using DALI. However, 
 * it turned out that its fold is similar to that of the amidino-transferases.
 * [36][37] Thus, the 11 difficult cases were 1bgeB (similarity with 2gmfA), 
 * 1cewI (1molA), 1cid (2rhe), 1crl (1ede), 1fxiA (1ubq), 1ten (3hhrB), 1tie (4fgf), 
 * 2azaA (1paz), 2sim (1nsbA), 3hlaB (2rhe), and 1g61 (1jdw). In these tests, 
 * success was defined as the ability of a program to retrieve the target structure 
 * or a close relative (mutant, complex, or homologue).
 * 
 * ===>
 * Especially:
 * We also conducted a test with the target and query structure swapped to 
 * investigate if the programs were symmetric in their ability to retrieve hits
 *  and in the details of the structural alignment (number of aligned residues 
 *  and RMSD). For this test, we used 1molA (1cewI) for most servers. For 
 *  TOPSCAN, we used 1nsbA (2sim) and for DEJAVU and SSM, we used 1ubq (1fxiA) 
 *  because these servers failed to retrieve 1molA when queried with 1cewI. 
 *  
 *  Because PRIDE failed for all 11 difficult cases, we used 1dyw (1lop) to 
 *  assess the symmetry of its results.
 * 
 * 
 * 
 * ...
 * 
 * 
 */
public class Novotny21FischerShindayalovBourneNovotnyT extends FatherOfTheNovotnyTestC {
	

	public static double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.2;
	
	int ngramSize = 15;

	
	
public void test_1_1bgeB_2gmfA() {
	
	
	
	String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/1_1bgeB_2gmfA/1BGE.pdb"; 

	String pathToTargetDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/1_1bgeB_2gmfA/2GMF.pdb"; 
	
		
		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		
		ProteinMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				pathToTargetDirOrFile, 
				pathToQueryDirOrFile, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
			
			
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 8) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
			
			if (new File(resultDirFileName).list().length != 9) {
				
				fail();
				
			}
			
			
		}
		
		
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}


public void test_2_1cewI_1molA() {
	
	

	String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/2_1cewI_1molA/1CEW.pdb"; 
	
		
	
	String pathToTargetDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/2_1cewI_1molA/1MOL.pdb"; 

		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		
		ProteinMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				pathToTargetDirOrFile, 
				pathToQueryDirOrFile, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
			
			
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 8) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
			
			if (new File(resultDirFileName).list().length != 9) {
				
				fail();
				
			}
			
			
		}
		
		
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}


public void test_3_1cid_2rhe() {
	
	int ngramSize = 10;
	
	

	String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/3_1cid_2rhe/1CID.pdb"; 
	
		
	
	String pathToTargetDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/3_1cid_2rhe/2RHE.pdb"; 

		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		
		ProteinMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				pathToTargetDirOrFile, 
				pathToQueryDirOrFile, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
			
			
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 8) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
			
			if (new File(resultDirFileName).list().length != 9) {
				
				fail();
				
			}
			
			
		}
		
		
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}





public void test_4_1crl_1ede() {
	
	

	String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/4_1crl_1ede/1CRL.pdb"; 
	
		
	
	String pathToTargetDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/4_1crl_1ede/1EDE.pdb"; 

		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		
		ProteinMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				pathToTargetDirOrFile, 
				pathToQueryDirOrFile, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
			
			
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 8) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
			
			if (new File(resultDirFileName).list().length != 9) {
				
				fail();
				
			}
			
			
		}
		
		
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}




public void test_5_1fxiA_1ubq() {
	
	String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/5_1fxiA_1ubq/1FXI.pdb"; 
	
		
	
	String pathToTargetDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/5_1fxiA_1ubq/1UBQ.pdb"; 

		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		
		ProteinMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				pathToTargetDirOrFile, 
				pathToQueryDirOrFile, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
			
			
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 8) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
			
			if (new File(resultDirFileName).list().length != 9) {
				
				fail();
				
			}
			
			
		}
		
		
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}




public void test_6_1ten_3hhrB() {
	
	
	ngramSize = 10;
	
	String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/6_1ten_3hhrB/1TEN.pdb"; 
	
		
	
	String pathToTargetDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/6_1ten_3hhrB/3HHR.pdb"; 

		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		
		ProteinMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				pathToTargetDirOrFile, 
				pathToQueryDirOrFile, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
			
			
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 8) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
			
			if (new File(resultDirFileName).list().length != 9) {
				
				fail();
				
			}
			
			
		}
		
		
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}




public void test_7_1tie_4fgf() {
	
	

	ngramSize = 10;
	
	
	
	String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/7_1tie_4fgf/1TIE.pdb"; 
	
		
	
	String pathToTargetDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/7_1tie_4fgf/4FGF.pdb"; 

		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		
		ProteinMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				pathToTargetDirOrFile, 
				pathToQueryDirOrFile, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
			
			
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 8) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
			
			if (new File(resultDirFileName).list().length != 9) {
				
				fail();
				
			}
			
			
		}
		
		
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}




public void test_8_2azaA_1paz() {
	

	ngramSize = 10;
	
	String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/8_2azaA_1paz/2AZA.pdb"; 
	
		
	
	String pathToTargetDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/8_2azaA_1paz/1PAZ.pdb"; 

		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		
		ProteinMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				pathToTargetDirOrFile, 
				pathToQueryDirOrFile, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
			
			
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 8) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
			
			if (new File(resultDirFileName).list().length != 9) {
				
				fail();
				
			}
			
			
		}
		
		
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}






public void test_9_2sim_1nsbA() {
	

	ngramSize = 10;
	
	String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/9_2sim_1nsbA/2SIM.pdb"; 
	
		
	
	String pathToTargetDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/9_2sim_1nsbA/1NSB.pdb"; 

		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		
		ProteinMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				pathToTargetDirOrFile, 
				pathToQueryDirOrFile, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
			
			
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 8) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
			
			if (new File(resultDirFileName).list().length != 9) {
				
				fail();
				
			}
			
			
		}
		
		
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}





public void test_010_3hlaB_2rhe() {
	

	ngramSize = 10;
	
	String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/010_3hlaB_2rhe/3HLA.pdb"; 
	
		
	
	String pathToTargetDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/010_3hlaB_2rhe/2RHE.pdb"; 

		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		
		ProteinMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				pathToTargetDirOrFile, 
				pathToQueryDirOrFile, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
			
			
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 8) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
			
			if (new File(resultDirFileName).list().length != 9) {
				
				fail();
				
			}
			
			
		}
		
		
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}





public void test_011_1g61_1jdw() {
	

	//ngramSize = 10;
	
	String pathToQueryDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/011_1g61_1jdw/1G61.pdb"; 
	
		
	
	String pathToTargetDirOrFile = "/media/truecrypt1/benchmarking/datasets/FischerNovotnyAntiassociationFactor/011_1g61_1jdw/1JDW.pdb"; 

		
		String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName()+"/"; 

		
		//set up and clean:
		DeleteDirRecursively.deleteDir(new File(tempDir));

		
		
		ProteinMatchRunner.executeSearch(
				ngramSize, 
				iFileToStringTranslator, 
				iResidueToStringTransformer,
				pathToTargetDirOrFile, 
				pathToQueryDirOrFile, 
				outputFilePath, 
				dealOnlyWithFirstModel, 
				thresholdOfRefinementScoreUnderWichResultIsOmitted,
				1);
			
			
		
		
		//check if there are two result dirs with 2 perfect matches each...
		
		//check if there are two result dirs with 2 perfect matches each...
		String [] dirNames = new File(outputFilePath).list();
		
		
		// 0. check if there are 8 result dirs in general:
		if (dirNames.length != 8) {
			fail();
		}
		
		
		
		// 1. for each resultdir check if there are enough result files:
		for (String dirName : dirNames) {
			
			String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);
			
			if (new File(resultDirFileName).list().length != 9) {
				
				fail();
				
			}
			
			
		}
		
		
		
		

		
		
		
			
		
		//tear down and clean:
		//DeleteDirRecursively.deleteDir(new File(tempDir));
		
		
		
	}

}
