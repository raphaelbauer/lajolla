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
package com.raphaelbauer.lajolla;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;


import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Calc;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureImpl;

import com.raphaelbauer.lajolla.chaingroupfilter.IChainGroupFilter;
import com.raphaelbauer.lajolla.comparators.ResultContainerComparator;
import com.raphaelbauer.lajolla.container.PositionPDBInfoContainer;
import com.raphaelbauer.lajolla.container.ResultContainer;
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.utilities.PDBConvenienceWriter;
import com.raphaelbauer.lajolla.utilities.SwissKnife;
import com.raphaelbauer.lajolla.utilities.Utility;

public class SequenceDB implements Serializable {
	
	
	private static double advancedScoringRadius = 3d;

	/** Standard serializable constant. */
	private static final long serialVersionUID = -7829858292457971501L;

	/** The important hashtable storing all the sequences as n-grams. */
	private Hashtable<String, ArrayList<PositionPDBInfoContainer>> hashtableNgramsToIDAndPosition 
		= new Hashtable<String, ArrayList<PositionPDBInfoContainer>>();

	/** counts up the number of stored ids for that hashtable. */
	int idCounter = 0;

	/**
	 * A helper hashtable storing relations between IDs (from the
	 * hashtableNgramsToID to an IDObject containing the pdb id and chain and so
	 * on).
	 */
	private Hashtable<Integer, PDBInfo> idToPDBInfoMapping 
		= new Hashtable<Integer, PDBInfo>();
	
	/**
	 * A helper hashtable storing relations between IDs (from the
	 * hashtableNgramsToID to an IDObject containing the pdb id and chain and so
	 * on).
	 */
	private Hashtable<Integer, String> idToNGramSequenceMapping 
		= new Hashtable<Integer, String>();
	
	

	private int ngramLength;
	
	//private int numberOfCharactersInStringCorrespondToOneResidue;
	
	private IScoringFunction scoringFunction;
	
	
	/** used to get the meaningless n-grams... */
	private IResidueToStringTransformer residueToStringTransformer;
	
	
	/** 
	 * Doing the rotation and using a scoring function to determine
	 * if this is a good result:
	 */
	private INGramTo3DTranslator ngramTo3DTranslator;

	public SequenceDB(
			int ngramLength,
			//int numberOfCharactersInStringCorrespondToOneResidue,
			IScoringFunction scoringFunction,
			IResidueToStringTransformer residueToStringTransformer,
			INGramTo3DTranslator ngramTo3DTranslator
			) {
		
		this.ngramLength = ngramLength;
		this.ngramTo3DTranslator = ngramTo3DTranslator;
		//this.numberOfCharactersInStringCorrespondToOneResidue 
		//	= numberOfCharactersInStringCorrespondToOneResidue;
		
		this.residueToStringTransformer = residueToStringTransformer;
		this.scoringFunction = scoringFunction;
		
		
		
		
		
		
	}

	/**
	 * Add string to hashtable.
	 * 
	 * 
	 * @param stringToBeIndexed
	 *            string (eg. phi psi converted) that will be stored
	 * @param seqDB_ID
	 */
	public void addNGramWithPDBInfoToSequenceDB(
			final String stringToBeIndexed, 
			PDBInfo pdbInfo) {
		
		//long start = System.currentTimeMillis();
		//System.out.println("t:" + stringToBeIndexed);

		idToPDBInfoMapping.put(idCounter, pdbInfo);
		idToNGramSequenceMapping.put(idCounter, stringToBeIndexed);

		int maximumPositionToBeCalculated = stringToBeIndexed.length()
				- ngramLength;

		String key;

		for (int i = 0; i <= maximumPositionToBeCalculated; i++) {

			key = stringToBeIndexed.substring(i, i + ngramLength);
			
			
			if (!key.contains(residueToStringTransformer.getStringMeaningNoResultAtThatPosition())) {
			

				if (hashtableNgramsToIDAndPosition.containsKey(key)) {

					hashtableNgramsToIDAndPosition.get(key).add(
							new PositionPDBInfoContainer(idCounter, i));

				} else {
					ArrayList<PositionPDBInfoContainer> list = new ArrayList<PositionPDBInfoContainer>();

					list.add(new PositionPDBInfoContainer(idCounter, i));
					hashtableNgramsToIDAndPosition.put(key, list);
				}
			
			}

		// => ++ the counter:
		
		}
		idCounter++;
		
		//System.out.println("indexing took: " + (System.currentTimeMillis() - start));

	}

	/**
	 * 
	 * 
	 * @param searchSeq
	 * @param onlyReturnTop10
	 * @return
	 */
	public void getExtendedRanking(
			String searchSeq, 
			PDBInfo queryPDBInfo,
			String dirWhereToStoreResults, 
			//String typeOfHetGroupToAlign,
			String atomToUseForRefinement, 
			double minimumRefinementScore,
			int maximumNumberOfResultsToGenerate,
			IChainGroupFilter chainGroupFilter) {
		
		
		
			
		//System.out.println("q:"+searchSeq);

		//
		//Long start = System.currentTimeMillis();
		
		//bug against empty chain identifier... workaround. this should not happen
		// normally, but happens in the datasets of skolnick and tm-align
		dirWhereToStoreResults = dirWhereToStoreResults.replace(' ', '_');
		new File(dirWhereToStoreResults).mkdirs();
		
		//System.out.println("mkdir took: " + (System.currentTimeMillis() - start));

		/**
		 * Integer idInHashtable... int[0] position in source (the query
		 * structure) int[1] position in target (in DB)
		 * 
		 */
		Hashtable<Integer, ArrayList<int[]>> thisQueryMapping 
			= new Hashtable<Integer, ArrayList<int[]>>();

		
		////////////////////////////////////////////////////////////////////////
		// Scan through hasthtable with n-grams:
		////////////////////////////////////////////////////////////////////////
		
		
		//start = System.currentTimeMillis();
		for (int positionInQuerySequence = 0; 
				positionInQuerySequence <= searchSeq.length() - ngramLength; 
				positionInQuerySequence++) {
			
		
			String subseq = searchSeq.substring(positionInQuerySequence,
					positionInQuerySequence + ngramLength);
			
			if (!subseq.contains(
					residueToStringTransformer.getStringMeaningNoResultAtThatPosition())) {
				

				
				
			//System.out.println("searching: " + subseq);

			
			//System.out.println(subseq);
				// search this subseq:
				if (hashtableNgramsToIDAndPosition.containsKey(subseq)) {
					//System.out.println("found subseq");

					// get the IDs where this subseq is contained...
					ArrayList<PositionPDBInfoContainer> idAndPDBInfoContainingSubstring 
						= hashtableNgramsToIDAndPosition.get(subseq);

					for (int j = 0; j < idAndPDBInfoContainingSubstring.size(); j++) {

						int internalIDOfPDBEntry = idAndPDBInfoContainingSubstring
								.get(j).getPdbInfoID();

						////////////////////////////////////////////////////////
						// ////
						// add the position mapping to the position mapping
						// table
						// of this query...
						// if it is there add it.. otherwise create it...
						if (thisQueryMapping.containsKey(internalIDOfPDBEntry)) {

							ArrayList<int[]> thisPositionMapping = thisQueryMapping
									.get(internalIDOfPDBEntry);

							int[] positionMapping = {
									positionInQuerySequence,
									idAndPDBInfoContainingSubstring.get(j)
											.getPosition() };

							thisPositionMapping.add(positionMapping);
							// mapping.put(internalIDOfPDBEntry,
							// positionMapping);

						} else {

							ArrayList<int[]> thisPositionMapping = new ArrayList<int[]>();

							int[] positionMapping = {
									positionInQuerySequence,
									idAndPDBInfoContainingSubstring.get(j)
											.getPosition() };

							thisPositionMapping.add(positionMapping);

							thisQueryMapping.put(internalIDOfPDBEntry,
									thisPositionMapping);

						}
						// end adding position mapping
					}
				
			}
			}
		}
		
		// 1) get filename of 1 and of 2:
		// System.out.println("queryChain loading: " +
		// queryPDBInfo.getPDBCode());
		
		// the chain that is written out
		Chain queryChainOriginal = null;
		
		// the chain that is used for mapping and scoring and so on
		// normally the reduced chain only has backbone atoms or so
		// determined by the filter...
		List<Atom> queryChainReducedAmountOfAtoms = null;
		
		//if (atomToUseForRefinement.equals("CA")) {
			
			//System.out.println(queryPDBInfo.getChainID());
		
		queryChainOriginal = 
			SwissKnife.loadPDBChainFromPDBInfo(
					queryPDBInfo, false); //true
			
	
			//okay... this is supposed to keep all other atoms as well...
			
//			
//		//} else {
//			queryChainReducedAmountOfAtoms 
//				= SwissKnife.loadPDBChainFromPDBInfo(queryPDBInfo, false);
//			
//			
//			
//			queryChainReducedAmountOfAtoms = chainGroupFilter.filter(queryChainReducedAmountOfAtoms);
//			System.out.println("in seqdb - after filtering qeueryChain: " + queryChainOriginal.getAtomLength());
//
//			
//		//}
		

		
		if (queryChainOriginal == null) {
			return;
		}
		
		queryChainReducedAmountOfAtoms 
			= Utility.removeAllAtomExcept(atomToUseForRefinement, chainGroupFilter.filter(
				queryChainOriginal));
		
		
		

		

		
		for (Entry<Integer, ArrayList<int[]>> entry : thisQueryMapping.entrySet()) {
			
			
			//check if 
			
			
			//ArrayList<int[]> anchor = determineMeaningfulAnchors(entry.getValue());
			
			ArrayList<int[]> anchorsList = entry.getValue();
			//anchor[0] == query
			//anchor[1] == target

			
			

			Chain targetChainOriginal = null;
			List<Atom> targetChainReducedAmountOfAtoms = null;
			//int idOfTargetChain = entry.getKey();
			
			//if (atomToUseForRefinement.equals("CA")) {
			
			targetChainOriginal = SwissKnife.loadPDBChainFromPDBInfo(
					idToPDBInfoMapping.get(entry.getKey()), 
					false); //true
			

//				targetChainReducedAmountOfAtoms = SwissKnife.loadPDBChainFromPDBInfo(
//						idToPDBInfoMapping.get(entry.getKey()), 
//						false); //true
//			//} else {
//				targetChainReducedAmountOfAtoms = SwissKnife.loadPDBChainFromPDBInfo(
//						idToPDBInfoMapping.get(entry.getKey()), 
//						false);	
//				targetChainOriginal = targetChainReducedAmountOfAtoms;
//				
//			//}
//			
	
			////////////////////////////////////////////////////////////////////
			// check that the identifier maps to pdb ids..
			if (targetChainOriginal == null) {
				continue;
			}
			
			targetChainReducedAmountOfAtoms = 
				Utility.removeAllAtomExcept(atomToUseForRefinement, 
						chainGroupFilter.filter((Chain) targetChainOriginal.clone()));
			
			


			
			//System.out.println("time targetChain reading (only CA): " 
			//		+ (System.currentTimeMillis()-start));

			

//			System.out.println("q:"  
//					+ new File(idToPDBInfoMapping.get(entry.getKey()).getPDBCode()).getName()
//					+ "-m-"
//				+ idToPDBInfoMapping.get(entry.getKey()).getModelNr() 
//				+ "-c-"
//				+ idToPDBInfoMapping.get(entry.getKey()).getChainID());
			
			//start = System.currentTimeMillis();
			List<ResultContainer> allResults = 
				ngramTo3DTranslator.determineAllGoodSuperpositionsAndWriteOut(	
						queryChainReducedAmountOfAtoms, 
						targetChainReducedAmountOfAtoms,
						searchSeq,
						idToNGramSequenceMapping.get(entry.getKey()),
						anchorsList, 
						ngramLength,
						advancedScoringRadius,
						atomToUseForRefinement,
						minimumRefinementScore,
						scoringFunction,
						residueToStringTransformer);
			
			
			
					//queryChainReducedAmountOfAtoms,
					//targetChainReducedAmountOfAtoms,
					//searchSeq,
					//idToNGramSequenceMapping.get(entry.getKey()),
					//anchorsList,
					//ngramLength,
					//typeOfHetGroupToAlign,
					//atomToUseForRefinement,
					//minimumRefinementScore);
			
			
			//System.out.println("");
			
			
			////////////////////////////////////////////////////////////////////
			// write out the results!
			
			int targetFileOutputCounter = 0;
			boolean queryFileAlreadyWrittenOut = false;
			

			//sort results (NOT NECSSARY IN PRINCIPLE	
			Collections.sort(allResults, new ResultContainerComparator());
			
			
			
			for (ResultContainer resultContainer : allResults) {
				
				//break if it is over number of alignments to be written out:
				if (maximumNumberOfResultsToGenerate != -1) {
					if (targetFileOutputCounter
							>= maximumNumberOfResultsToGenerate) {
						
						//raus aus der for schleife:
						break;
					}
				}
				
				
				
//				//load queryChain if it is not yet loaded.
//				if (queryChainOriginal == null) {
//					queryChainOriginal = SwissKnife.loadPDBChainFromPDBInfo(
//							queryPDBInfo,  true);
//					
//				}
//				
//				if (targetChainOriginal == null) {
//					targetChainOriginal = SwissKnife.loadPDBChainFromPDBInfo(
//						idToPDBInfoMapping.get(entry.getKey()), 
//						false);
//				}
				
				
				

				
				if (!queryFileAlreadyWrittenOut) {
					String queryOutFile = dirWhereToStoreResults + "q-"
							+ new File(queryPDBInfo.getPDBCode()).getName()
							+ "_original.pdb";

					PDBConvenienceWriter.writeOutSingleChain(
							queryChainOriginal,
							queryOutFile);

					queryFileAlreadyWrittenOut = true;
				}
				
				
				
				
				
				Chain targetChainOriginalClone
					= (Chain) targetChainOriginal.clone();
				
				Structure struc2 
					= new StructureImpl(targetChainOriginalClone);
				
				Calc.rotate(struc2, resultContainer.getRotationMatrix());
				
								// shift structure 2 onto structure one ...
				Calc.shift(struc2, resultContainer.getTranslationVector());
				
				DecimalFormat df = (DecimalFormat) DecimalFormat
										.getInstance(Locale.US);
								
				df.applyPattern("#0.00");
				
				
				
				String rmsd;
				
				//System.out.println("rmsd: " + resultContainer.getScoreContainer().getRMSD());
				
				if (Double.isNaN(resultContainer.getScoreContainer().getRMSD())) {
					//System.out.println("is NAN");
					
					rmsd = "NA";
					
				} else {
					//System.out.println("is value");
					rmsd = df.format(resultContainer.getScoreContainer().getRMSD());
				}
				

				String outfile = dirWhereToStoreResults + "t-"
					+ new File(idToPDBInfoMapping.get(entry.getKey()).getPDBCode()).getName()
					+ "-m-"
					+ idToPDBInfoMapping.get(entry.getKey()).getModelNr() 
					+ "-c-"
					+ idToPDBInfoMapping.get(entry.getKey()).getChainID() 
					//+ "-RMSD-"
					//+ df.format(resultContainer.getRMSD()) 
					+ "-RS-"
					+ rmsd
					+ "-TM-"
					+ df.format(resultContainer.getScoreContainer().getOverallSCORE())
					+ "-L1-" +resultContainer.getScoreContainer().getLengthQueryTarget()
					+ "-L2-"+resultContainer.getScoreContainer().getLengthTemplate()
					+ "-LAL-" + resultContainer.getScoreContainer().getNumberOfAlignedResidues()
					+"-"
					+ targetFileOutputCounter++;
				
				

				
				//bug against empty chain identifier... workaround. this should not happen
				// normally, but happens in the datasets of skolnick and tm-align
				outfile = outfile.replace(' ', '_');
				
				PDBConvenienceWriter.writeOutSingleChain(
						targetChainOriginalClone, outfile
					+ ".pdb");
				
			}
	
		}
	}
	
	
	
	
		
		
		

	
		
		
		



	/**
	 * number of stored strings. - there can be more than one stored string for
	 * one pdb file => more than one model, more than one chain etc...
	 * 
	 * @return
	 */
	public int getNumberOfStoredStrings() {

		return this.idCounter;

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public PDBInfo getPDBInfoForID(int id) {

		PDBInfo pdbInfo = idToPDBInfoMapping.get(new Integer(id));

		return pdbInfo;

	}




}
