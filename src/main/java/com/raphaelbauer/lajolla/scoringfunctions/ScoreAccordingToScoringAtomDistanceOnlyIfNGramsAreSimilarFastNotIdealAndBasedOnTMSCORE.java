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
package com.raphaelbauer.lajolla.scoringfunctions;

import java.util.BitSet;
import java.util.List;

import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Calc;
import org.biojava.bio.structure.StructureException;

import com.raphaelbauer.lajolla.container.ScoreContainer;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;

public class ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE
        implements IScoringFunction {

//	private List<Atom> chainSmaller;
//	
//	private List<Atom> chainBigger;
//	
//	private String chainNGramSmaller;
//	
//	private String chainNGramBigger;
  private EScoringFunctionRelativeSettings scoringFunctionRelativeSettings;

  private int numberOfExpectedMatchingAtoms;

  private double RMSDAdder;

  private double tmScoreAdder;

  //double SCORE;
  // int allAtomsToCheck = 0;
  private int numberOfAtomsSuccessfullyAligned;

  private static BitSet bitSet = new BitSet();

  /**
   * Standard constructor => scoring is done according to smaller structure -
   * good standard for substructure search - generally okay
   *
   */
  public ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE() {

    scoringFunctionRelativeSettings = EScoringFunctionRelativeSettings.basedOnSizeOfSmaller;

  }

  /**
   * Good for finding overall similar matches based on input structure
   *
   *
   * @param scoreAccordingToSmallerChainOtherWiseScoreAccoringToQueryChain
   */
  public ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
          EScoringFunctionRelativeSettings thisSetting) {

    scoringFunctionRelativeSettings = thisSetting;

  }

  /**
   *
   * @param chain1
   * @param chain2
   * @return
   * @throws StructureException
   */
  public ScoreContainer scoreRefindeDoWhateveryouwantButGiveMeAScoreAndAnAlignment(
          List<Atom> chainQueryIsTargetInTMSCORE,
          List<Atom> chainTargetIsTemplateInTMSCORE,
          String nGramQueryIsTargetInTMSCORE,
          String nGramTargetIsTemplateInTMSCORE,
          int queryPosition_unused,
          int targetPosition_unused,
          int nGramLength_unused,
          String atomToUseForRefinement,
          double advancedScoringRadius,
          IResidueToStringTransformer residueToStringTransformer) {

    //always score the smaller chain against the bigger chain
    //init the variables:
    RMSDAdder = 0d;
    tmScoreAdder = 0d;
    numberOfAtomsSuccessfullyAligned = 0;

    bitSet.clear();

    //if score according to smaller => maximumatoms to be found == smallerChain.soze()
    //otherwise => chain1.size() chain1 == query Chain
    if (scoringFunctionRelativeSettings.equals(
            EScoringFunctionRelativeSettings.basedOnSizeOfSmaller)) {

      if (chainQueryIsTargetInTMSCORE.size() < chainTargetIsTemplateInTMSCORE.size()) {
        numberOfExpectedMatchingAtoms = chainQueryIsTargetInTMSCORE.size();
      } else {
        numberOfExpectedMatchingAtoms = chainTargetIsTemplateInTMSCORE.size();
      }

    } else {
      numberOfExpectedMatchingAtoms = chainQueryIsTargetInTMSCORE.size();
    }

    ////////////////////////////////////////////////////////////////////////
    // actual scoring:
    double overallSmallestDistanceInOuterI = -1;
    double tempDistanceInnerCounterOfJ = -1;

    int bestPositionJ = -1;

    for (int i = 0; i < chainQueryIsTargetInTMSCORE.size(); i++) {

      for (int j = 0; j < chainTargetIsTemplateInTMSCORE.size(); j++) {

        //did i already measure from this j atom?
        // if yes => uninteresting => do not do it and skip it...
        //if (!(bitSet.get(j))) {
        if (bitSet.get(j)) {
          continue;
        } //else:

        try {
          tempDistanceInnerCounterOfJ = Calc.getDistance(
                  chainQueryIsTargetInTMSCORE.get(i),
                  chainTargetIsTemplateInTMSCORE.get(j));

          //System.out.println("tempdistanceInnerCounterJ: " + tempDistanceInnerCounterOfJ);
          if (overallSmallestDistanceInOuterI == -1) {
            overallSmallestDistanceInOuterI = tempDistanceInnerCounterOfJ;

            bestPositionJ = j;
            tempDistanceInnerCounterOfJ = -1;
            //bitSet.set(j);
          } else if (overallSmallestDistanceInOuterI > tempDistanceInnerCounterOfJ) {
            overallSmallestDistanceInOuterI = tempDistanceInnerCounterOfJ;
            bestPositionJ = j;
            //bitSet.set(j);

          } //else {

        } catch (StructureException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

      }

      if ((overallSmallestDistanceInOuterI < advancedScoringRadius)
              && (overallSmallestDistanceInOuterI != -1)) {
			//System.out.println(chainSmallerAtomsUsedForRefinement.get(i).getPDBserial() + "\n " 
        //		+ chainBiggerAtomsUsedForRefinement.get(j).getPDBserial() 
        //		+ "\n " + distance);

        //System.out.println(distance);
        String smallerPosition
                = nGramQueryIsTargetInTMSCORE.substring(
                        i,
                        i + residueToStringTransformer.getNumberOfCharactersInStringCorrespondToOneResidue());

        String biggerPosition = nGramTargetIsTemplateInTMSCORE.substring(
                bestPositionJ,
                bestPositionJ + residueToStringTransformer.getNumberOfCharactersInStringCorrespondToOneResidue());

        if ((smallerPosition.equals(biggerPosition))
                || (smallerPosition.equals(residueToStringTransformer.getStringMeaningNoResultAtThatPosition()))
                || (biggerPosition.equals(residueToStringTransformer.getStringMeaningNoResultAtThatPosition()))) {

          tmScoreAdder += TMScoreHelper.calculateTermOfSum(numberOfExpectedMatchingAtoms, overallSmallestDistanceInOuterI);

          RMSDAdder += Math.pow(overallSmallestDistanceInOuterI, 2);

          bitSet.set(bestPositionJ);

          numberOfAtomsSuccessfullyAligned++;
          //break;
        }
      }

      overallSmallestDistanceInOuterI = -1;
      tempDistanceInnerCounterOfJ = -1;

    }

		// System.out.println("found:   " + (foundAtoms / allAtomsToCheck));
    //score:
    //SCORE = ((double)foundAtoms / chainSmaller.size());
    double rmsd = Math.sqrt(RMSDAdder / (double) numberOfAtomsSuccessfullyAligned);

    //System.out.println("TM: " + TMScoreHelper.calculateWholeTMScore(numberOfExpectedMatchingAtoms, tmScoreAdder));
    ScoreContainer scoreContainer
            = new ScoreContainer(
                    TMScoreHelper.calculateWholeTMScore(numberOfExpectedMatchingAtoms, tmScoreAdder),
                    rmsd,
                    chainQueryIsTargetInTMSCORE.size(),
                    chainTargetIsTemplateInTMSCORE.size(),
                    numberOfAtomsSuccessfullyAligned);

    return scoreContainer;

  }

}
