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

import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;

import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Calc;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureImpl;
import org.biojava.nbio.structure.geometry.SuperPositionSVD;
import org.biojava.nbio.structure.jama.Matrix;

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

          // Superimpose the target atoms onto the query (reference) atoms.
          // BioJava's SVDSuperimposer was removed in the 4.x -> 7.x line; the
          // replacement operates on Point3d[] and returns a 4x4 transform.
          Point3d[] queryPoints = Calc.atomsToPoints(queryAtomRefinementArray);
          Point3d[] targetPoints = Calc.atomsToPoints(targetAtomRefinementArray);

          SuperPositionSVD svdSuperimposer = new SuperPositionSVD(false);

          Matrix4d transformation = svdSuperimposer.superpose(
                  queryPoints, targetPoints);

          double rmsd = svdSuperimposer.getRmsd(queryPoints, targetPoints);

          if (Double.isNaN(rmsd)) {
            // nothing..

          } else {

            // Split the 4x4 transform back into the rotation matrix + shift
            // vector expected by the downstream Calc.rotate / Calc.shift calls.
            Matrix rotationMatrix = toRotationMatrix(transformation);
            Atom translationVector = Calc.getTranslationVector(transformation);

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
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

    return allResults;

  }

  /**
   * Extracts the 3x3 rotation part of a 4x4 transformation as a BioJava
   * {@link Matrix} suitable for {@code Calc.rotate(Structure, Matrix)}.
   *
   * <p>The matrix is stored <em>transposed</em>: {@code Calc.rotate} multiplies
   * the coordinate as a row vector ({@code x' = x * M}), which is the opposite
   * convention to the column-vector rotation returned by
   * {@link Matrix4d#getRotationScale}. This matches what the old BioJava
   * {@code SVDSuperimposer.getRotation()} returned, so the downstream
   * rotate/shift reproduces the superposition exactly.
   */
  private static Matrix toRotationMatrix(final Matrix4d transformation) {

    Matrix3d rotation = new Matrix3d();
    transformation.getRotationScale(rotation);

    Matrix rotationMatrix = new Matrix(3, 3);
    for (int row = 0; row < 3; row++) {
      for (int column = 0; column < 3; column++) {
        rotationMatrix.set(row, column, rotation.getElement(column, row));
      }
    }

    return rotationMatrix;
  }

}
