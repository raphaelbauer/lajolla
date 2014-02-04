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
package ra.lajolla.utilities;

public class RefinementContainer {
	
	
	
	double RMSD;
	
	double SCORE;
	
	String queryAlignment;
	
	String targetAlignment;
	
	int numberOfExpectedResidues;
	
	int numberOfFoundResidues;
	

	public RefinementContainer(
			double rmsd, 
			double score,
			String queryAlignment, 
			String targetAlignment,
			int numberOfExpectedResidues,
			int numberOfFoundResidues) {
		
		super();
		
		
		this.RMSD = rmsd;
		this.SCORE = score;
		this.queryAlignment = queryAlignment;
		this.targetAlignment = targetAlignment;
		this.numberOfExpectedResidues = numberOfExpectedResidues;
		this.numberOfFoundResidues = numberOfFoundResidues;
	}

	public double getRMSD() {
		return RMSD;
	}

	public double getSCORE() {
		return SCORE;
	}

	public String getQueryAlignment() {
		return queryAlignment;
	}

	public int getNumberOfExpectedResidues() {
		return numberOfExpectedResidues;
	}
	
	
	public int getNumberOfFoundResidues() {
		return numberOfFoundResidues;
	}
	
	public String getTargetAlignment() {
		return targetAlignment;
	}
	
	

}
