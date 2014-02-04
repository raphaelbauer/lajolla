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
package ra.lajolla.container;

import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.jama.Matrix;

public class ResultContainer {
	
	private Atom translationVector;
	
	private Matrix rotationMatrix;
	
	private ScoreContainer scoreContainer;
	
	

	public ResultContainer(
			Matrix rotationMatrix, 
			Atom translationVector,
			ScoreContainer scoreContainer) {
		
		super();
		this.rotationMatrix = rotationMatrix;
		this.scoreContainer = scoreContainer;
		
		this.translationVector = translationVector;
	}
	
	
	
	
	public Atom getTranslationVector() {
		return translationVector;
	}

	public Matrix getRotationMatrix() {
		return rotationMatrix;
	}

	public ScoreContainer getScoreContainer() {
		return this.scoreContainer;
	}
	
	

	

	
	
	

}
