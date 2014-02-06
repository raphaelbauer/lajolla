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
package com.raphaelbauer.lajolla.utilities;

import java.io.File;


public class AnalyseResultThymidylateSynthase {
	
	
	public static void main(String [] a) {

		String inputDir 
			= "/home/ra/workspace_work/src/test/resources/thymidylate_synthase_pdb/";
		
		String resultDir = "/media/truecrypt1/tmp/ThymidylateSynthaseTest/";
		
		

		int numberOfAllChains = 267;
		
		
		
		File resultDirFile = new File(resultDir);
		String [] allDirsOfResult = resultDirFile.list();
		
		

		
		int numberOf90PercentFoundFiles = 0;
		
		int numberOf80PercentFoundFiles = 0;
		
		int numberOf70PercentFoundFiles = 0;
		
		int rest = 0;
		
		//jedes result dir:
		//System.out.println("all here: " + allDirsOfResult.length);
		
		for (int i = 0; i < allDirsOfResult.length; i++ ) {
			//System.out.println(resultDir + "/" + allDirsOfResult[i]);
			
			String [] allFoundfilesHere = new File(resultDir + "/" + allDirsOfResult[i]).list();
			
			//.out.println(allFoundfilesHere.length);
			
			int filesFound = (allFoundfilesHere.length-1)/2;
			
			//System.out.println(filesFound);
			
			double percentage = (double) filesFound / (double) allDirsOfResult.length;
			
			if (percentage >= 0.6) {
				
				numberOf90PercentFoundFiles++;
				
			} else if (percentage >=0.5){
				
				numberOf80PercentFoundFiles++;
				
			} else if (percentage >= 0.4){
				numberOf70PercentFoundFiles++;
				
			} else {rest++;}
		

		}
		
		
		
		System.out.println("60% and bigger: " + numberOf90PercentFoundFiles);
		System.out.println("50% and bigger: " + numberOf80PercentFoundFiles);
		System.out.println("40% and bigger: " + numberOf70PercentFoundFiles);
		System.out.println("rest: " + rest);
		
		
		
		
		
	}

}
