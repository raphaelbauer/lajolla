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
package com.raphaelbauer.lajolla.transformation.rna.etatheta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;

import com.raphaelbauer.lajolla.utilities.RecursiveFileCollector;
import com.raphaelbauer.lajolla.utilities.SwissKnife;

/**
 * Helper class that generates eta theta codes using a certain alphabet for
 * marcus thesis...
 * 
 * 
 * @author ra
 * 
 */
public class GenerateEtaThetaSequences {

	public static void main(String [] args) {
		
		
		////////////////////////////////////////////////////////////////////////
		// input files or directories:
		////////////////////////////////////////////////////////////////////////
		String queryDirOrFile = "";

		String outputFileName = "";
		
		
		////////////////////////////////////////////////////////////////////////
		// mini tuning...
		////////////////////////////////////////////////////////////////////////
		boolean dealWithAllModels = false;
			

		
		////////////////////////////////////////////////////////////////////////
		// advanced stuff:
		////////////////////////////////////////////////////////////////////////

		int advancedAngleDiscretion = 90;


		try {

			Options opt = new Options();

			opt
					.addOption("h", "help", false,
							"Print help for this application");;

			opt.addOption("q", "query", true,
					"query pdb file (directory or file)");

			opt.addOption("o", "outfile", true,
					"File where to store stuff");

			opt.addOption("am", "allmodels", false,
					"Deal with all models (slower) (DEFAULT: "
							+ dealWithAllModels + ")");

			

			opt.addOption("za", "anglediscretion", true,
					"ADVANCED: discretion angle for phi psi (DEFAULT: "
							+ advancedAngleDiscretion + ")");


		

			BasicParser parser = new BasicParser();
			CommandLine cl = parser.parse(opt, args);

			// no arguments given => print help:
			if (args.length == 0) {
				// System.out.println("cl get arg list length : " +
				// cl.getArgList().size());
				HelpFormatter f = new HelpFormatter();
				f.printHelp("java -cp lajolla.jar PRO [options]", opt);
				System.exit(1);
			}

			if (cl.hasOption('h')) {
				HelpFormatter f = new HelpFormatter();
				f.printHelp("java -cp lajolla.jar PRO [options]", opt);
				System.exit(1);
			}

			if (cl.getOptionValue("q") == null) {
				System.out
						.println("[INFO] -q not set (query directory or file)");


				//queryDirOrFile = targetDirOfFile;

				System.exit(1);

			} else {
				queryDirOrFile = cl.getOptionValue("q");

			}
			
			

			if (cl.getOptionValue("o") == null) {
				
				System.out
				.println("[INFO] -o not set (output file)");

				System.exit(1);

			} else {
				outputFileName = cl.getOptionValue("o");

			}


			if (cl.hasOption("am")) {

				dealWithAllModels = false;
			}

			// //////////////////////////////////////////////////////////////
			// advanced parameters:
			// //////////////////////////////////////////////////////////////

			if (cl.hasOption("za")) {

				advancedAngleDiscretion = Integer.parseInt(cl
						.getOptionValue("za"));
			}



			// /////////////////////////////////////////////////////////////
			// start it:
			// execute();
			// STEP 1: build the index - the sequence db:
			
			String result = doIT(
					advancedAngleDiscretion, 
					queryDirOrFile, 
					!dealWithAllModels);
			
			
			
			
			try {
				PrintWriter pw = new PrintWriter(outputFileName);
				
				BufferedWriter bw = new BufferedWriter(pw);
				
				
				bw.append(result);
				
				bw.close();
				pw.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 * @param discreeteStepSizeForAngles
	 * @param ngramLength
	 * @param rootDirectorOrFile
	 * @param onlyParseFirstModel
	 */
	public static String doIT(
			int discreeteStepSizeForAngles,
			String rootDirectorOrFile,
			boolean onlyParseFirstModel) {
		
		
		StringBuffer returnStringBuffer = new StringBuffer();
		

		OptimizedStructureToEtaThetaCharacterTransformer anglesCalc 
			= new OptimizedStructureToEtaThetaCharacterTransformer(
				);


		File rootDirFileTemp = new File(rootDirectorOrFile);

		ArrayList<String> filesToLoadIntoSequenceDB = new ArrayList<String>();

		if (rootDirFileTemp.isFile()) {

			filesToLoadIntoSequenceDB.add(rootDirectorOrFile);

		} else {
			// collect all files recursively:
			RecursiveFileCollector recursiveFileCollector = new RecursiveFileCollector();

			recursiveFileCollector.collectFilesRecursively(rootDirectorOrFile);

			filesToLoadIntoSequenceDB = recursiveFileCollector
					.getArrayListWithFiles();
		}

		int numberOfPDBFiles = filesToLoadIntoSequenceDB.size();

		for (int filesVectorID = 0; filesVectorID < numberOfPDBFiles; filesVectorID++) {

			// System.out.println("file number: " + filesVectorID + " " +
			// filesToLoadIntoSequenceDB.get(filesVectorID));

			// System.out.println("is: " +
			// filesToLoadIntoSequenceDB.get(filesVectorID));

			Structure struc = SwissKnife.loadPDBFile(filesToLoadIntoSequenceDB
					.get(filesVectorID));

			// System.out.println("struc: " + struc);

			// sometimes structures are null (eg: pdb1c0q.ent)
			// then skip it:
			if (struc != null) {

				int nrModels = struc.nrModels();

				if (onlyParseFirstModel) {
					nrModels = 1;
				}

				for (int modelNr = 0; modelNr < nrModels; modelNr++) {

					List<Chain> chains = struc.getModel(modelNr);

					int nrChains = chains.size();
					for (int chainNr = 0; chainNr < nrChains; chainNr++) {

						// System.out.println("adding...: "+
						// struc.getPDBCode()+", model "+ modelNr+", chain "+
						// chainNr+","+ filesVectorID);

						String angleSequence = anglesCalc
								.getStringFromResidues(chains
										.get(chainNr).getAtomGroups(
												"nucleotide"));
						// System.out.println("size: " + angleSequence.length()
						// + " " + angleSequence);

						if (angleSequence.length() > 0) {
							
							returnStringBuffer.append(
									filesToLoadIntoSequenceDB.get(filesVectorID)	
									+ "\t"
									+ modelNr
									+ "\t"
									+ chains.get(chainNr).getName()
									+ "\t"
									+ angleSequence
									+ "\n");

						}

					}
				}
			}
		}
		
		
		return returnStringBuffer.toString();

	}

}
