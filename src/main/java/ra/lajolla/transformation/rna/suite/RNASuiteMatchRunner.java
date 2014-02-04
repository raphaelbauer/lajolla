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
package ra.lajolla.transformation.rna.suite;
import java.io.File;

import ra.lajolla.PDBInfo;
import ra.lajolla.SequenceDB;
import ra.lajolla.transformation.IFileToStringTranslator;
import ra.lajolla.utilities.FileOperationsManager2;


public class RNASuiteMatchRunner {
	

	
	
//	public static void executeSearch(
//			int advancedNGramSize, 
//			IFileToStringTranslator iFileToStringTranslator,
//			String pathToTargetDirOrFile, 
//			String pathToQueryDirOrFile,
//			String outputFilePath,
//			double thresholdOfRMSDOverWhichResultIsOmitted,
//			double thresholdOfRefinementScoreUnderWichResultIsOmitted) {
//
//		
//		
//		executeSearch(advancedNGramSize, 
//				iFileToStringTranslator, 
//				pathToTargetDirOrFile, 
//				pathToQueryDirOrFile, outputFilePath, 
//				thresholdOfRefinementScoreUnderWichResultIsOmitted);
//		
//		
//	}
	
	
	/**
	 * well.. the runner that can be called from main... and doing everyting
	 * 
	 * @param advancedNGramSize
	 * @param angleDiscretion
	 */
	public static void executeSearch(
			int advancedNGramSize, 
			IFileToStringTranslator iFileToStringTranslator,
			String pathToTargetDirOrFile, 
			String pathToQueryDirOrFile,
			String outputFilePath,
			double thresholdOfRefinementScoreUnderWichResultIsOmitted,
			int numberOfBestResultsToWriteOut) {

		
		SequenceDB sequenceDB = FileOperationsManager2
				.generateSequenceDBRecursivelyFromDirOrFile(
						pathToTargetDirOrFile, 
						iFileToStringTranslator,
						advancedNGramSize);

		// STEP 2: get all target files:
		// collect all files recursively:
		
		
		
		if (new File(pathToQueryDirOrFile).isDirectory()) {
			System.err.println("[ERROR] input must be a suite file not a dir!");
			System.exit(0);
		}

		
		SuiteFile suiteFile
			= new SuiteFile(pathToQueryDirOrFile);
	
		for (SuiteFileEntry suiteFileEntry : suiteFile.getAllBuckets()) {

			PDBInfo pdbInfo = new PDBInfo(
					suiteFileEntry.getIdentifierOfThisEntitiy(),
					suiteFileEntry.getModelID(),
					suiteFileEntry.getChainID());
			
			
			
			
			////////////////////////////////////////////////////////////////////
			// remove entries at the beginning or the end that
			// do not correspond to a residue:
			////////////////////////////////////////////////////////////////////
			
			
			sequenceDB.getExtendedRanking(
					suiteFileEntry.getSuiteString(),
					pdbInfo, 
					outputFilePath
							+ new File(pdbInfo.getPDBCode()).getName()
							+ "-model-" + pdbInfo.getModelNr() + "-chain-"
							+ pdbInfo.getChainID() + "/", 
							//"nucleotide",
							"P",
							thresholdOfRefinementScoreUnderWichResultIsOmitted,
							numberOfBestResultsToWriteOut,
							iFileToStringTranslator.getChainGroupFilter());
			
			
						
		}
		
		
		
		
		
	

	}

}
