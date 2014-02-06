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

import com.raphaelbauer.lajolla.SequenceDB;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.transformation.protein.BetterOptimizedPhiPsiTranslator;
import com.raphaelbauer.lajolla.transformation.protein.PDBProteinTranslator;
import com.raphaelbauer.lajolla.utilities.FileOperationsManager2;

public class SequenceDBStorageTest {

  /**
   * testtesttest
   */
  @Test
  public void testFileOperationsManagerLoadSequenceDB() {

    IResidueToStringTransformer iResidueToStringTransformer
            = new BetterOptimizedPhiPsiTranslator();

    IScoringFunction scoringFunction
            = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();

    INGramTo3DTranslator nGramTo3DTranslator
            = new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

    IFileToStringTranslator iFileToStringTranslator
            = new PDBProteinTranslator(
                    iResidueToStringTransformer,
                    false,
                    scoringFunction,
                    nGramTo3DTranslator);

    SequenceDB sequenceDB
            = FileOperationsManager2.generateSequenceDBRecursivelyFromDirOrFile(
                    "src/test/resources/astral_small/",
                    iFileToStringTranslator,
                    8);

    //System.out.println(sequenceDB.getNumberOfStoredStrings());
    //test if all entities got loaded into the seq db:
    assertThat(sequenceDB.getNumberOfStoredStrings(), equalTo(5));

  }

}
