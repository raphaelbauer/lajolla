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

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;

import com.raphaelbauer.lajolla.PDBInfo;
import com.raphaelbauer.lajolla.SequenceDB;
import com.raphaelbauer.lajolla.chaingroupfilter.AminoAcidsAndDerivatesFilter;
import com.raphaelbauer.lajolla.chaingroupfilter.IChainGroupFilter;
import com.raphaelbauer.lajolla.chaingroupfilter.NucleotidesAndDerivatesFilter;
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.utilities.RecursiveFileCollector;
import com.raphaelbauer.lajolla.utilities.SwissKnife;

public class PDBRNATranslator implements IFileToStringTranslator {

	
	IResidueToStringTransformer residueToStringTransformer;
	
	boolean onlyParseFirstModel = true;
	
	IScoringFunction scoringFunction;
	
	IChainGroupFilter chainGroupFilter = new NucleotidesAndDerivatesFilter();
	
	
	INGramTo3DTranslator ngramTo3DTranslator;
	
	
	public PDBRNATranslator(
			IResidueToStringTransformer iResidueToStringTransformer,
			boolean onlyParseFirstModel,
			IScoringFunction scoringFunction,
			INGramTo3DTranslator ngramTo3DTranslator) {
		
		this.residueToStringTransformer = iResidueToStringTransformer;
		this.onlyParseFirstModel = onlyParseFirstModel;
		this.scoringFunction = scoringFunction;
		
		this.ngramTo3DTranslator = ngramTo3DTranslator;
		
	}
	
	
	/**
	 * Returns the filter that determines which hetero group in 
	 * a pdb file is like a nucleotide and which one not
	 * 
	 * => more precise than biojava implementation, because it 
	 * also recognizes derivates
	 */
	public IChainGroupFilter getChainGroupFilter() {
		return chainGroupFilter;
		
		
	}


	public boolean isOnlyParseFirstModel() {
		return onlyParseFirstModel;
	}


	@Override
	public SequenceDB getSequencesRecursivelyFromDirOrFile(
			String rootDirectorOrFile, 
			int ngramLength) {
		
	
		
		SequenceDB sequenceDB 
			= new SequenceDB(
				ngramLength,
				scoringFunction,
				residueToStringTransformer, 
				ngramTo3DTranslator);
		
		
		File rootDirFileTemp = new File(rootDirectorOrFile);
		
		ArrayList <String> filesToLoadIntoSequenceDB 
			= new ArrayList<String>();
		
		if (rootDirFileTemp.isFile()) {
			
			filesToLoadIntoSequenceDB.add(rootDirectorOrFile);
			
			
		} else {
			//collect all files recursively:
			RecursiveFileCollector recursiveFileCollector
			= new RecursiveFileCollector();

			recursiveFileCollector.collectFilesRecursively(rootDirectorOrFile);
		
			filesToLoadIntoSequenceDB = 	
				recursiveFileCollector.getArrayListWithFiles();
		}
		
		
		
		
		
		
		
		int numberOfPDBFiles = filesToLoadIntoSequenceDB.size();
		
		for (int filesVectorID = 0; 
			filesVectorID < numberOfPDBFiles; 
			filesVectorID++) {
			
						
						
			
			//System.out.println("file number: " + filesVectorID + " " + filesToLoadIntoSequenceDB.get(filesVectorID));
			
			
			//System.out.println("is: " + filesToLoadIntoSequenceDB.get(filesVectorID));
			
			Structure struc = SwissKnife.loadPDBFile(
					filesToLoadIntoSequenceDB.get(filesVectorID));
			
			

			//System.out.println("struc: " + struc);

			
			//sometimes structures are null (eg: pdb1c0q.ent)
			//then skip it:
			if (struc != null) {
			
			int nrModels = struc.nrModels();
			
			
			if (onlyParseFirstModel) {
				nrModels = 1;
			}
			
			
			for (int modelNr = 0; modelNr < nrModels; modelNr++) {
				
				List<Chain> chains = struc.getModel(modelNr);
				
				int nrChains = chains.size();
				for (int chainNr = 0; chainNr < nrChains; chainNr++) {

					//System.out.println("adding...: "+ struc.getPDBCode()+", model "+ modelNr+", chain "+ chainNr+","+ filesVectorID);
					

					

					List<Group> allNucleotideLikeGroups 
						= chainGroupFilter.filter(
								chains.get(
										chainNr)).getAtomGroups();
										//);
					
					//System.out.println(allNucleotideLikeGroups.size());
					
					
					
					String angleSequence 
						= residueToStringTransformer.getStringFromResidues(
							allNucleotideLikeGroups);
					
					//System.out.println("size: "  + angleSequence.length() + " " + angleSequence);
					
					
					
					if (angleSequence.length() > 0) {

						//System.out.println("adding it...");
						//eintrag in seqDB
						PDBInfo pdbInfo = new PDBInfo(
							new File(filesToLoadIntoSequenceDB.get(filesVectorID)).getAbsolutePath(),
							modelNr, 
							chains.get(chainNr).getName());
					
						sequenceDB.addNGramWithPDBInfoToSequenceDB(angleSequence, pdbInfo);
					}
	
				}
			}
			}
		}
		
		
		
		
		return sequenceDB;
	}

	
	
	
	
	

}
