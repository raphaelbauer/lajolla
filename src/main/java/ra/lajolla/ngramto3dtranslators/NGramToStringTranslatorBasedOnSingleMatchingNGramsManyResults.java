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
package ra.lajolla.ngramto3dtranslators;

import java.util.ArrayList;
import java.util.List;

import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Calc;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.SVDSuperimposer;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.StructureImpl;
import org.biojava.bio.structure.jama.Matrix;

import ra.lajolla.container.ResultContainer;
import ra.lajolla.container.ScoreContainer;
import ra.lajolla.scoringfunctions.IScoringFunction;
import ra.lajolla.transformation.IResidueToStringTransformer;
import ra.lajolla.utilities.Utility;

public class NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults 
	implements INGramTo3DTranslator {

	

	public ArrayList<ResultContainer> determineAllGoodSuperpositionsAndWriteOut(
			List<Atom> queryChainAtomArray, 
			List<Atom> targetChainAtomArray,
			String queryChainNGramSequence,
			String targetChainNGramSequence,
			ArrayList<int[]> anchorsQueryTarget, 
			int ngramLength,
			double advancedScoringRadius,
			String atomToUseForRefinement,
			double minimumRefinementScore,
			IScoringFunction scoringFunction,
			IResidueToStringTransformer residueToStringTransformer) {
		
		
		
		
		//Chain queryChain = 	Utility.convertAtomArrayToChain(queryChainAtomArray);
		
		
		ArrayList<ResultContainer> allResults
			= new ArrayList<ResultContainer>();
		// march through each corresponding stuff and calc RMSD:
		//System.out.println("number of to be checked possible anchors: "
		//		+ anchorsQueryTarget.size());

		for (int i = 0; i < anchorsQueryTarget.size(); i++) {
			
			//System.out.println("q:"+anchorsQueryTarget.get(i)[0] + " and t:" + anchorsQueryTarget.get(i)[1]);
			//System.out.println(i);

			// collect the possibly matching entries

			ArrayList<Atom> queryAtomsForSuperposition = new ArrayList<Atom>();

			ArrayList<Atom> targetAtomsForSuperposition = new ArrayList<Atom>();

			//System.out.println(queryChain.getAtomLength() +  " - " + queryChain.getAtomGroups().size());
			
			//List<Group> allQueryChainAtomGroups = queryChain
			//		.getAtomGroups();

			//List<Group> allTargetChainAtomGroups = targetChain
			//		.getAtomGroups();

			// anchor[0] ==> query
			// anchor[1] ==> target

			// step through this n-gram and add the atoms of
			// this n-gram to the superimposer...
			
		
			for (int j = 0; j < ngramLength; j++) {

				int queryPosition = ((anchorsQueryTarget.get(i)[0] + j) 
						/ residueToStringTransformer.getNumberOfCharactersInStringCorrespondToOneResidue());

				int targetPosition = ((anchorsQueryTarget.get(i)[1] + j) 
						/ residueToStringTransformer.getNumberOfCharactersInStringCorrespondToOneResidue());

				
				// //////////////////////////////////////////////////////
				// looks strange? this is to determine that the position
				// is in the residues. we had problems with suite codes
				// at the end of a suite that do not correspond to
				// a residue => out of array exception... this omits it:
				// //////////////////////////////////////////////////////
				if ((queryPosition < queryChainAtomArray.size())
						&& (targetPosition < targetChainAtomArray.size())) {

//					Group queryGroup = allQueryChainAtomGroups
//							.get(queryPosition);
//
//					Group targetGroup = allTargetChainAtomGroups
//							.get(targetPosition);

					//if ((queryGroup.hasAtom(atomToUseForRefinement))
					//		&& (targetGroup.hasAtom(atomToUseForRefinement))) {

				

//							Atom queryAtomForRefinement;
//							Atom targetAtomForRefinement;
//							queryAtomForRefinement = queryGroup
//									.getAtom(atomToUseForRefinement);
//
//							targetAtomForRefinement = targetGroup
//									.getAtom(atomToUseForRefinement);

							queryAtomsForSuperposition
									.add(queryChainAtomArray.get(queryPosition));

							targetAtomsForSuperposition
									.add(targetChainAtomArray.get(targetPosition));


					} else {
						// nothing...
					}

				
			}

			// rotate, shift and check rmsd for all...:

			Atom[] queryAtomRefinementArray = Utility
					.convertArrayListAtomsToArray(queryAtomsForSuperposition);

			Atom[] targetAtomRefinementArray = Utility
					.convertArrayListAtomsToArray(targetAtomsForSuperposition);

			try {

				//long start = System.currentTimeMillis();

				//System.out.println(queryAtomRefinementArray.length);
				
				//System.out.println(targetAtomRefinementArray.length);
				
				
				
				
				if (queryAtomRefinementArray.length > 0 &&
						targetAtomRefinementArray.length > 0) {
				
					SVDSuperimposer svdSuperimposer = new SVDSuperimposer(
						queryAtomRefinementArray, targetAtomRefinementArray);

					double rmsd = SVDSuperimposer.getRMS(
							queryAtomRefinementArray,
							targetAtomRefinementArray);
					
					//System.out.println(rmsd);
				

				// System.out.println("time SVD: "
				// + (System.currentTimeMillis()-start));

				if (Double.isNaN(rmsd)) {
					// noting..

				} else {

					//start = System.currentTimeMillis();
					Matrix rotationMatrix = svdSuperimposer.getRotation();
					Atom translationVector = svdSuperimposer.getTranslation();

					
					//this is done, beacause it makes the cloning easier::
					Chain targetChain = Utility.convertAtomArrayToChain(
							targetChainAtomArray);
					
					//here:
					Chain targetChainTranslatedRotated = (Chain) targetChain
							.clone();

					//rotate and traslate...:
					Structure struc2 = new StructureImpl(
							targetChainTranslatedRotated);

					Calc.rotate(struc2, rotationMatrix);

					// shift structure 2 onto structure one ...
					Calc.shift(struc2, translationVector);
					// System.out.println("time rotation/translation: "
					// + (System.currentTimeMillis()-start));

					// /////////////////////////////////////////////////////////
					// refine and score it:
					// /////////////////////////////////////////////////////////
					// [0] = rmsd
					// [1] = score num ofa ligned residues of q / all resiudes
					// of q
					// => based on CA or P

					//start = System.currentTimeMillis();
					
					
					

					int startQueryPositionNGram = (anchorsQueryTarget.get(i)[0]
							/ residueToStringTransformer.getNumberOfCharactersInStringCorrespondToOneResidue());

					int startTargetPositionNGram = (anchorsQueryTarget.get(i)[1] 
							/ residueToStringTransformer.getNumberOfCharactersInStringCorrespondToOneResidue());

					
					
					ScoreContainer scoreContainer = scoringFunction
						.scoreRefindeDoWhateveryouwantButGiveMeAScoreAndAnAlignment(
							queryChainAtomArray, 
							Utility.convertChainToAtomList(targetChainTranslatedRotated), 
							queryChainNGramSequence,
							targetChainNGramSequence,
							startQueryPositionNGram,
							startTargetPositionNGram,
							ngramLength,
							atomToUseForRefinement,
							advancedScoringRadius,
							residueToStringTransformer);
							
					
					
					if (scoreContainer.getOverallSCORE() >= minimumRefinementScore) {
						allResults.add(new ResultContainer(
							(Matrix) rotationMatrix.clone(), 
							(Atom) translationVector.clone(), 
							scoreContainer));
					}
					
					
					//identical match
					if (scoreContainer.getOverallSCORE() > 0.9999) {
						//System.out.println("identical!");
						break;
					}
					


					// System.out.println("time scoring: " +
					// (System.currentTimeMillis()-start));

					//System.out.println("SCORE is: " + SCORE);

				}
				}
			} catch (StructureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return allResults;

	} 


	
		
		
		
	
	
}
