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
package com.raphaelbauer.lajolla.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;

import com.raphaelbauer.lajolla.chaingroupfilter.IChainGroupFilter;
import com.raphaelbauer.lajolla.chaingroupfilter.NucleotidesAndDerivatesFilter;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.transformation.rna.etatheta.OptimizedStructureToEtaThetaCharacterTransformer;
import com.raphaelbauer.lajolla.transformation.rna.etatheta.StructureToEtaThetaCharacterTransformer;


public class MainWriteOutNGramsOfStructuresForMarcus {
	
	public static void main (String [] a) {
		
		
		String [] dbs = {
				"sara-rand-rna-fixed"};
				//"darts_set", 
				//"hi-res_cleaned", 
				//"rnaDB05", 
				//"single_ribosome"};
		
		
		IResidueToStringTransformer [] transformers =
		{new OptimizedStructureToEtaThetaCharacterTransformer(),
				new StructureToEtaThetaCharacterTransformer(90)};
		
		IChainGroupFilter chaingroupFilter = new NucleotidesAndDerivatesFilter();
		
		
		
		String dbDir = "/media/truecrypt1/benchmarking/datasets/rna/";

		String outputDir = "/home/ra/marcus/";
		
		
		
		for (String databaseName : dbs) {
		
		
			for (IResidueToStringTransformer residueToStringTransformer : transformers) {
				
				String outputFile = 
					outputDir 
					+ residueToStringTransformer.getClass().getName() 
					+ "_" + databaseName
					+ ".txt";
				
				try {
					generateFilesWithTranslations(
							outputFile, 
							dbDir, 
							databaseName, 
							residueToStringTransformer, 
							chaingroupFilter
							);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		
		
	}
		
	}
	
	
	
	
	
	
	public static void generateFilesWithTranslations (
			String outputFile, 
			String parentDBDir, 
			String dbName,
			IResidueToStringTransformer iResidueToStringTransformer,
			IChainGroupFilter chainGroupFilter) throws Exception {
		
		
		
		FileWriter fw = new FileWriter(outputFile);
		
		 PrintWriter pw = new PrintWriter(fw);
		 

		 
		

		String pathToDirWithFiles =  parentDBDir + File.separator + dbName;
	

		ArrayList<String> allQueryFiles = new ArrayList<String>();

		if (new File(pathToDirWithFiles).isFile()) {

			allQueryFiles.add(pathToDirWithFiles);

		} else {

			RecursiveFileCollector recursiveFileCollector = new RecursiveFileCollector();

			recursiveFileCollector
					.collectFilesRecursively(pathToDirWithFiles);

			allQueryFiles = recursiveFileCollector.getArrayListWithFiles();
		}

		// PDBProteinTranslator pdbProteinTranslator2
		// = new PDBProteinTranslator(angleDiscretion);

		// STEP 3: getRankingsForTheBest10...

		//int counter = 0;

		for (String currentFile : allQueryFiles) {

			Structure struc = SwissKnife.loadPDBFile(currentFile);
			
			

			// System.out.println("struc: " + struc);

			// sometimes structures are null (eg: pdb1c0q.ent)
			// then skip it:
			if (struc != null) {

				int nrModels = struc.nrModels();


				//StructureToEtaThetaCharacterTransformer anglesCalc 
				//	= new StructureToEtaThetaCharacterTransformer(
				//		advancedAngleDiscretion);

				for (int modelNr = 0; modelNr < nrModels; modelNr++) {

					List<Chain> chains = struc.getModel(modelNr);

					int nrChains = chains.size();
					
					for (int chainNr = 0; chainNr < nrChains; chainNr++) {
						

						List<Group> allNucleotideLikeGroups 
							= chainGroupFilter.filter(chains.get(
											chainNr)).getAtomGroups();
											
						
						
						
						String angleSequence 
							= iResidueToStringTransformer.getStringFromResidues(
									allNucleotideLikeGroups);
						
						

						System.out.println("angleseq: " + angleSequence);
						
						if(angleSequence.length() > 0) {
							
							
							//pw.write(currentFile);
							//pw.write("\t");
							//pw.write(modelNr);
							//pw.write("\t");	
							//pw.write(chainNr);
							//pw.write("\t");	
							pw.write(angleSequence);
							pw.write("\n");	
							
							
							
							
						//counter++;
							

							//System.out.println("pmatchrunner searching: " +currentFile  + " chain " +  chains.get(chainNr).getName());


		
						}
					}

				
			
				}}
		}
		
		pw.close();
		fw.close();

	}


}
