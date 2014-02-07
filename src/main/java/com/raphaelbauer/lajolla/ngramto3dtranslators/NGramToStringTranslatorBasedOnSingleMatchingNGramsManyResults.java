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
package com.raphaelbauer.lajolla.ngramto3dtranslators;

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

import com.raphaelbauer.lajolla.container.ResultContainer;
import com.raphaelbauer.lajolla.container.ScoreContainer;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.utilities.Utility;

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

    ArrayList<ResultContainer> allResults = new ArrayList<>();
    // march through each corresponding stuff and calc RMSD:

    for (int i = 0; i < anchorsQueryTarget.size(); i++) {

      // collect the possibly matching entries
      ArrayList<Atom> queryAtomsForSuperposition = new ArrayList<>();

      ArrayList<Atom> targetAtomsForSuperposition = new ArrayList<>();

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

        if (queryAtomRefinementArray.length > 0
                && targetAtomRefinementArray.length > 0) {

          SVDSuperimposer svdSuperimposer = new SVDSuperimposer(
                  queryAtomRefinementArray, targetAtomRefinementArray);

          double rmsd = SVDSuperimposer.getRMS(
                  queryAtomRefinementArray,
                  targetAtomRefinementArray);

          if (Double.isNaN(rmsd)) {
            // nothing..

          } else {

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
              break;
            }
          }
        }
      } catch (StructureException e) {
        e.printStackTrace();
      }

    }

    return allResults;

  }

}
