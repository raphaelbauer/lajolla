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
package ra.lajolla.ngramto3dtranslators;

import java.util.ArrayList;
import java.util.List;

import org.biojava.bio.structure.Atom;

import ra.lajolla.container.ResultContainer;
import ra.lajolla.scoringfunctions.IScoringFunction;
import ra.lajolla.transformation.IResidueToStringTransformer;

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
