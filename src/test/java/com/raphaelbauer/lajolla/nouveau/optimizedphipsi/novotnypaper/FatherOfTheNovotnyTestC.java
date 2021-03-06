package com.raphaelbauer.lajolla.nouveau.optimizedphipsi.novotnypaper;

import com.raphaelbauer.lajolla.TestingConstant;
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

public class FatherOfTheNovotnyTestC extends TestCase {

  int ngramSize = 20;

  public static String tempDir = "src/test/tmp";

  public static boolean dealOnlyWithFirstModel = true;

  public static double thresholdOfRefinementScoreUnderWichResultIsOmitted = 0.5;

  public static IResidueToStringTransformer iResidueToStringTransformer
          = new BetterOptimizedPhiPsiTranslator();

  public static IScoringFunction scoringFunction
          = new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
                  EScoringFunctionRelativeSettings.basedOnSizeOfQueryWhatIsTheTargetInTMSCORE);

	//1furA03
  public static INGramTo3DTranslator nGramTo3DTranslator
          = new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();

  public static String pathToCATH = TestingConstant.baseDirOfTestSets + "datasets/CathDomainPdb.S60.v3.2.0/";

  public static IFileToStringTranslator iFileToStringTranslator
          = new PDBProteinTranslator(
                  iResidueToStringTransformer,
                  dealOnlyWithFirstModel,
                  scoringFunction,
                  nGramTo3DTranslator);

}
