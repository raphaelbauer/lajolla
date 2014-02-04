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
//import javax.vecmath.Point3d;



public class RaMa {
	
	
//	public static double getEuclidicDistance(Point3d atom1, Point3d atom2) {
//		
//		return Math.sqrt(
//				Math.pow((  atom1.x - atom2.x), 2)
//				+ Math.pow((atom1.y - atom2.y), 2)
//				+ Math.pow((atom1.z - atom2.z), 2)
//				 );
//	
//	}
	
	
	public static double getEuclidicDistance(double [] atom1, double[] atom2) {
		
		return Math.sqrt(
				Math.pow((  atom1[0] - atom2[0]), 2)
				+ Math.pow((atom1[1] - atom2[1]), 2)
				+ Math.pow((atom1[2] - atom2[2]), 2)
				 );
	
	}
	
	
	
	
	public static boolean isAround(
			double yourValue, 
			double theValueToMeet, 
			double threshold) {
		
		if (((theValueToMeet - threshold ) <= yourValue)
			&& ((theValueToMeet + threshold) >= yourValue)) {
			
			return true;
			
		} else return false;
		
		
		
	}
	
	
	/**
	 * For instance 45 points and you want
	 * to get all possible triangles it is
	 * 
	 * 45 over 3
	 * 
	 * @param up
	 * @param down
	 */
	public static long getBinomial(int n, int k) {
		
		//long N = getFaculty(n);
		//long ND = getFaculty(n-k);
		//long K = getFaculty(k);
		
		//return (N/(ND*K));
		//return N;
		return (getFaculty(n) / ( getFaculty(n-k) * getFaculty(k)));
		
		
	}
	
	
	
	

	public static long getFaculty (long val) {
		if (val < 0)
			throw new IllegalArgumentException ("Negative value given.");
		if (val == 0)
			return 1;
		else {
			long bin = 1;
			for (int i = 1; i <= val; i ++) {
				bin *= i;
				
			}
			
			return bin;
		}
	}
	
	
	
	

	
	

}
