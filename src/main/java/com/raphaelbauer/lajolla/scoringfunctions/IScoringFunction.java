package com.raphaelbauer.lajolla.scoringfunctions;

import java.util.List;

import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Chain;

import com.raphaelbauer.lajolla.container.ScoreContainer;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;

public interface IScoringFunction {

  public ScoreContainer scoreRefindeDoWhateveryouwantButGiveMeAScoreAndAnAlignment(
          List<Atom> queryChain,
          List<Atom> targetChainTranslatedRotated,
          String completeNGramSequenceQueryChain,
          String completeNGramSequencetargetChainTranslatedRotated,
          int queryPosition,
          int targetPosition,
          int nGramLength,
          String atomToUseForRefinement,
          double advancedScoringRadius,
          IResidueToStringTransformer residueToStringTransformer);

}
