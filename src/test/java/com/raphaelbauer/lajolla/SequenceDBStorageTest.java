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

    
    assertThat(sequenceDB.getNumberOfStoredStrings(), equalTo(5));

  }

}
