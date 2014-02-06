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
package com.raphaelbauer.lajolla.transformation.protein;

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
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.utilities.RecursiveFileCollector;
import com.raphaelbauer.lajolla.utilities.SwissKnife;
import com.raphaelbauer.lajolla.utilities.Utility;

public class PDBProteinTranslator implements IFileToStringTranslator {
	
	
	
	private boolean onlyParseFirstModel;
	
	private IResidueToStringTransformer iResidueToStringTransformer;
	
	private IScoringFunction scoringFunction;
	
	private IChainGroupFilter chainGroupFilter = new AminoAcidsAndDerivatesFilter();
	
	private INGramTo3DTranslator ngramTo3DTranslator;
	
	public PDBProteinTranslator(
			IResidueToStringTransformer iResidueToStringTransformer,
			boolean onlyParseFirstModel,
			IScoringFunction scoringFunction,
			INGramTo3DTranslator ngramTo3DTranslator) {
		
		
		this.onlyParseFirstModel = onlyParseFirstModel;
		
		this.iResidueToStringTransformer = iResidueToStringTransformer;
		
		this.scoringFunction = scoringFunction;
		
		this.ngramTo3DTranslator = ngramTo3DTranslator;
	}
	
	
	
	/**
	 * Returns the filter that determines which hetero group in 
	 * a pdb file is like an amino acid and which one not
	 * 
	 * => more precise than biojava implementation, because it 
	 * also recognizes derivates of amino acids
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
		
		//StructureToPhiPsiToTwoCharactersTransformer anglesCalc 
		//	= new StructureToPhiPsiToTwoCharactersTransformer(
		//			discreeteStepSizeForAngles);
		
		
		
		
		SequenceDB sequenceDB 
			= new SequenceDB(
					ngramLength,
					scoringFunction,
					iResidueToStringTransformer,
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
					
					
					
					//System.out.println("query allAminoLikeGroups before filter: " + (chains.get(chainNr)).getAtomGroups().size());
					List<Group> allAminoLikeGroups 
						= chainGroupFilter.filter(chains.get(
									chainNr)).getAtomGroups();
									
					//System.out.println("query allAminoLikeGroups after filter: " + allAminoLikeGroups.size());
				
				
				String angleSequence 
					= iResidueToStringTransformer.getStringFromResidues(
							allAminoLikeGroups);
				
				//System.out.println("angleSequence: " + angleSequence.length());
				
					
					
					if (Utility.getNumberOfDifferentCharsInThisString(angleSequence) > 1 ) {

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
