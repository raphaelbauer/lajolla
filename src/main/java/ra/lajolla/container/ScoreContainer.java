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

public class ScoreContainer {
	
	

	private double overallScore = Double.NaN;
	
	private double RMSD = Double.NaN;
	
	private int lengthQueryTarget = -1;
	private int numberOfAlignedResidues = -1;
	private int lengthTemplate = -1;
	
	
	
	public int getLengthQueryTarget() {
		return lengthQueryTarget;
	}


	public int getNumberOfAlignedResidues() {
		return numberOfAlignedResidues;
	}


	public int getLengthTemplate() {
		return lengthTemplate;
	}





	public ScoreContainer(
			double overallScore,
			double rmsd, 
			int lengthQueryTarget,
			int lengthTemplate, 
			int numberOfAlignedResidues) {
		super();
		RMSD = rmsd;
		this.lengthQueryTarget = lengthQueryTarget;
		this.lengthTemplate = lengthTemplate;
		this.numberOfAlignedResidues = numberOfAlignedResidues;
		this.overallScore = overallScore;
	}


	public double getOverallSCORE() {
		return overallScore;
	}

	public double getRMSD() {
		return RMSD;
	}
	
	
	
	

}
