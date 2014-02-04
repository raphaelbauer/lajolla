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
package ra.lajolla;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import ra.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import ra.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import ra.lajolla.scoringfunctions.IScoringFunction;
import ra.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import ra.lajolla.transformation.IFileToStringTranslator;
import ra.lajolla.transformation.IResidueToStringTransformer;
import ra.lajolla.transformation.protein.BetterOptimizedPhiPsiTranslator;
import ra.lajolla.transformation.protein.PDBProteinTranslator;
import ra.lajolla.utilities.FileOperationsManager2;

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
