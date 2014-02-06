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
package com.raphaelbauer.lajolla.nouveau.optimizedphipsi;

import java.io.File;
import java.util.BitSet;

import junit.framework.TestCase;
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import com.raphaelbauer.lajolla.scoringfunctions.EScoringFunctionRelativeSettings;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.transformation.protein.BetterOptimizedPhiPsiTranslator;
import com.raphaelbauer.lajolla.transformation.protein.PDBProteinTranslator;
import com.raphaelbauer.lajolla.transformation.protein.ProteinMatchRunner;
import com.raphaelbauer.lajolla.utilities.DeleteDirRecursively;

public class AlignmentAstrald15711Test extends TestCase {

  static String tempDir = "src/test/tmp";

  static IResidueToStringTransformer iResidueToStringTransformer
          = new BetterOptimizedPhiPsiTranslator();

  static IScoringFunction scoringFunction
          //= new ScoreAccordingToScoringAtomDistance();
          = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
                  EScoringFunctionRelativeSettings.basedOnSizeOfQueryWhatIsTheTargetInTMSCORE);

  public void testAllAgainstAll() {

    int ngramSize = 15;

    String pathToTargetDirOrFile = "src/test/resources/ASTRAL/d.157.1.1/";
    String pathToQueryDirOrFile = "src/test/resources/ASTRAL/d.157.1.1/";

    String outputFilePath = tempDir + File.separator + this.getClass().getSimpleName() + "/";

    boolean dealOnlyWithFirstModel = true;

    INGramTo3DTranslator nGramTo3DTranslator
            = new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

    IFileToStringTranslator iFileToStringTranslator
            = new PDBProteinTranslator(
                    iResidueToStringTransformer,
                    dealOnlyWithFirstModel,
                    scoringFunction,
                    nGramTo3DTranslator);

    double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.36;

    //set up and clean:
    DeleteDirRecursively.deleteDir(new File(tempDir));

    ProteinMatchRunner.executeSearch(
            ngramSize,
            iFileToStringTranslator,
            iResidueToStringTransformer,
            pathToTargetDirOrFile,
            pathToQueryDirOrFile,
            outputFilePath,
            dealOnlyWithFirstModel,
            thresholdOfRefinementScoreUnderWichResultIsOmitted,
            1);

		//check if there are two result dirs with 2 perfect matches each...
    String[] dirNames = new File(outputFilePath).list();

    // 0. check if there are 8 result dirs in general:
    if (dirNames.length != 8) {
      fail();
    }

    // 1. for each resultdir check if there are enough result files:
    for (String dirName : dirNames) {

      String resultDirFileName = outputFilePath + File.separator + dirName;
			//System.out.println(resultDirFileName + " " + new File(resultDirFileName).list().length);

      if (new File(resultDirFileName).list().length != 9) {

        fail();

      }

    }

		//tear down and clean:
    //DeleteDirRecursively.deleteDir(new File(tempDir));
  }

}
