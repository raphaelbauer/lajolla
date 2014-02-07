package com.raphaelbauer.lajolla.ngramto3dtranslators;

import java.util.ArrayList;
import java.util.List;

import org.biojava.bio.structure.Atom;

import com.raphaelbauer.lajolla.container.ResultContainer;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;

public interface INGramTo3DTranslator {

  public List<ResultContainer> determineAllGoodSuperpositionsAndWriteOut(
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
          IResidueToStringTransformer residueToStringTransformer);

}
