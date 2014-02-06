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
package com.raphaelbauer.lajolla.transformation.rna.suite;

import java.io.File;


import com.raphaelbauer.lajolla.PDBInfo;
import com.raphaelbauer.lajolla.SequenceDB;
import com.raphaelbauer.lajolla.chaingroupfilter.IChainGroupFilter;
import com.raphaelbauer.lajolla.chaingroupfilter.NucleotidesAndDerivatesFilter;
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;

public class PDBRNATranslator implements IFileToStringTranslator {

	
	
	IScoringFunction scoringFunction;
	
	IChainGroupFilter chainGroupFilter = new NucleotidesAndDerivatesFilter();
	
	IResidueToStringTransformer residueToStringTransformer;
	
	
	INGramTo3DTranslator ngramTo3DTranslator;
	
	
	/**
	 * to get a mapping of suite files to their pdb stuffies...

	 * @param pathToPDBFilesOfSuite
	 */
	public PDBRNATranslator(
			IScoringFunction scoringFunction,
			IResidueToStringTransformer residueToStringTransformer,
			INGramTo3DTranslator ngramTo3DTranslator) {
		
		this.scoringFunction = scoringFunction;
		
		this.residueToStringTransformer = residueToStringTransformer;
		
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

	/**
	 * This method works on precalculated suite Files that are written in a 
	 * tab separated mannre (like in the suiterna project suiterna.sf.net).
	 * 
	 * 
	 */
	@Override
	public SequenceDB getSequencesRecursivelyFromDirOrFile(
			String rootDirectoryOrFile, 
			int ngramLength) {
		
		
		if (new File(rootDirectoryOrFile).isDirectory()) {
			System.err.println("[ERROR] RNA translator only accepts single files...");
			System.exit(0);
		}
		
		
		//in this case: 2 suite characters correpsond to one nucleotide!
		SequenceDB sequenceDB 
			= new SequenceDB(
					ngramLength, 
					scoringFunction,
					residueToStringTransformer,
					ngramTo3DTranslator);
		
		

			
				
			
			SuiteFile suiteFile
				= new SuiteFile(rootDirectoryOrFile);
			
			
					
			
			for (SuiteFileEntry suiteFileEntry : suiteFile.allBuckets) {
				
				PDBInfo pdbInfo = new PDBInfo(
						suiteFileEntry.getIdentifierOfThisEntitiy(),
						//struc.getPDBCode(), 
						suiteFileEntry.getModelID(), 
						suiteFileEntry.getChainID());
				
				sequenceDB.addNGramWithPDBInfoToSequenceDB(suiteFileEntry.getSuiteString(), pdbInfo);
							
			}
			
		
		
		return sequenceDB;
	}
	
	

}