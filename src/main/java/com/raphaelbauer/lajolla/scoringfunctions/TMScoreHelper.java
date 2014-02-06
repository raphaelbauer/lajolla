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


/**
 * TM SCORE is:
 * 
 * 
 * int numberOfAllAtomsToCompare 
 * 
 * int numberOfFoundAtoms
 * 
 * 
 * 
 * 1/numberOfAllAtomsToCompare *
 * 
 * 
 *   numberOfFoundAtoms
 *      SUM                   1 /  ( 1+(distance(ithting /       )^2
 *       
 * 
 * 
 * @author ra
 *
 */
public class TMScoreHelper {
	
	
	public static double calculateTermOfSum(int numberOfAllAtoms, double distanceOfTheTwoAtoms) {
		
		
		double valueOfd0LTarget = 1.24d * Math.cbrt((numberOfAllAtoms-15)) - 1.8d;
		//System.out.println(valueOfd0LTarget);
		
		double completeValue = 1 / ( 1d + Math.pow((distanceOfTheTwoAtoms / valueOfd0LTarget), 2d));
		
		//System.out.println("complete: " + completeValue);
		return completeValue;

		
	}
		
	public static double calculateWholeTMScore(int numberOfAllAtoms, double termOfSum) {
		
		return ((1d/numberOfAllAtoms) * termOfSum);
		
	}
	

}
