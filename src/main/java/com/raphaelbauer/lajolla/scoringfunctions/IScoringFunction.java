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
