package com.raphaelbauer.lajolla.nouveau.rna.optimizedetatheta;

import java.io.File;

import junit.framework.TestCase;
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.transformation.rna.etatheta.OptimizedStructureToEtaThetaCharacterTransformer;
import com.raphaelbauer.lajolla.transformation.rna.etatheta.PDBRNATranslator;
import com.raphaelbauer.lajolla.transformation.rna.etatheta.RNAEtaThetaMatchRunner;
import com.raphaelbauer.lajolla.utilities.DeleteDirRecursively;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class PaperDIALTest {

  static String tempDir = "src/test/tmp/";

  static IResidueToStringTransformer iResidueToStringTransformer
          = new OptimizedStructureToEtaThetaCharacterTransformer();

  static INGramTo3DTranslator nGramTo3DTranslator
          = new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

  static IScoringFunction scoringFunction = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE();

  /**
   * not from the sarsa paper... but a difficult example. or an ambiguous ine...
   *
   * similar to the paper from the DIAL paper, but the pdb entries disapperared
   * somehow...
   *
   *
   */
  @Test
  public void testMultipleTwoDifferentTRNAs() {

    int ngramSize = 5;

    String file1 = "src/test/resources/sarsa_rna_paper/multipleglobal/trnas/1asz.pdb";
    String file2 = "src/test/resources/sarsa_rna_paper/multipleglobal/trnas/2csx.pdb";

    String outputFilePath
            = tempDir
            + File.separator
            + this.getClass().getSimpleName()
            + File.separator;

    boolean dealOnlyWithFirstModel = true;

    IFileToStringTranslator iFileToStringTranslator
            = new PDBRNATranslator(
                    iResidueToStringTransformer,
                    dealOnlyWithFirstModel,
                    scoringFunction,
                    nGramTo3DTranslator);

    double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.27;

    //set up and clean:
    DeleteDirRecursively.deleteDir(new File(outputFilePath));

    RNAEtaThetaMatchRunner.executeSearch(
            ngramSize,
            iFileToStringTranslator,
            iResidueToStringTransformer,
            file1,
            file2,
            outputFilePath,
            dealOnlyWithFirstModel,
            thresholdOfRefinementScoreUnderWichResultIsOmitted,
            1);

		//check if there are two result dirs with 2 perfect matches each...
    String resultDirFileName = outputFilePath + File.separator + "2csx.pdb-model-0-chain-C";

    
    assertThat((new File(resultDirFileName)).list().length, equalTo(2));


		//tear down and clean:
    //DeleteDirRecursively.deleteDir(new File(tempDir));
  }

}
