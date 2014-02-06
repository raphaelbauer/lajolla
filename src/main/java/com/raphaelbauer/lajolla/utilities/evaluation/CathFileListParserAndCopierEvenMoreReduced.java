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
package com.raphaelbauer.lajolla.utilities.evaluation;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class CathFileListParserAndCopierEvenMoreReduced {
	
	
	static String CathDir = 
		"/media/truecrypt1/benchmarking/datasets/CathDomainPdb.S35.v3.2.0/";
	
	
	static String completeFileWithDirToCathFileList
		= "/media/truecrypt1/benchmarking/datasets/CathDomainList.S35";
	
	
	/** Where to copy the filtered stuff: */
	static String outputDir =  "/media/truecrypt1/benchmarking/datasets/CathDomainPdb.S35.v3.2.0_filtered_by_H_single/";
	
	
	
	
	
	
	
	public static void main(String [] a) throws Exception {	
		
		
		HashMap<String, Integer> numberOfOccurencesOfThisComboe 
			= new HashMap<String, Integer>();
		
		
		
		HashMap<String, String> fileToCATHMapping = new HashMap<String, String>();
		
		
		
		
		ArrayList<String> allFilesToBeCopied = new ArrayList<String>();
		

		new File(outputDir).mkdirs();
		
		
			
			FileReader fis = new FileReader(completeFileWithDirToCathFileList);

			BufferedReader bis = new BufferedReader(fis);
			
			String line = "";
			
			String currentCombo = "";
			
			
			while ((line = bis.readLine()) != null) {
				
				String [] allEntries = line.split("\\s+");
				
				String fileName = allEntries[0];
				String thisCombo = allEntries[1] + allEntries[2] + allEntries[3] + allEntries[4];
				
				String reducedComboForFilteringOut = allEntries[1] + allEntries[2] + allEntries[3];
				
				
				fileToCATHMapping.put(fileName, reducedComboForFilteringOut);
				
				
				if (currentCombo.equals(thisCombo)) {
					
					//nix
				} else {
					System.out.println("merke: " + fileName);
					allFilesToBeCopied.add(fileName);
					currentCombo = thisCombo;
				}
				
				
				
				//filtering out:
				

			}
			
			
			
			
			
			for (String fileName : allFilesToBeCopied) {
				
				String reducedComboForFilteringOut = fileToCATHMapping.get(fileName);
	
				
				if (!numberOfOccurencesOfThisComboe.containsKey(reducedComboForFilteringOut)) {
					
					numberOfOccurencesOfThisComboe.put(reducedComboForFilteringOut, 1);
					
				} else {
					Integer thisCounter = numberOfOccurencesOfThisComboe.get(reducedComboForFilteringOut);
					
					
					thisCounter = thisCounter +1;
					
					
					numberOfOccurencesOfThisComboe.put(reducedComboForFilteringOut, thisCounter);
					
				}
				
				
				
				
			}
			
			
			
			
			
			
			
			
				
				
//				
//				System.out.println("line:");
//						
//				for (String thisString : allEntries) {
//					
//					System.out.println(thisString);
//					
//				}
//				
				
			
				
				
				
				
			
			
			
			
			
			
			//get single elements => not counting for evalation:
			HashSet<String> isSingleElement = new HashSet<String>();

			for (Entry<String, Integer> thisEntry : numberOfOccurencesOfThisComboe.entrySet()) {
				
				if (thisEntry.getValue() == 1) {
					System.out.println("single: " + thisEntry.getKey());
					
					isSingleElement.add(thisEntry.getKey());
					
				} else {
					System.out.println("not single: " + thisEntry.getKey());
				}
				
				
			}
			
			
			
			
			
			
			
			

			System.out.println(allFilesToBeCopied.size());
			
			
			//copy stuff:
			
			
			for (String file : allFilesToBeCopied) {
				
				if (!isSingleElement.contains(fileToCATHMapping.get(file))) {
			
			
			
			   try {
			        FileChannel srcChannel = 
			          new FileInputStream(CathDir + file).getChannel();
			    
			        FileChannel dstChannel = 
			          new FileOutputStream(outputDir + file).getChannel();
			    
			        dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
			    
			        srcChannel.close();
			        dstChannel.close();
			        
			    } catch (IOException e) {
			    	e.printStackTrace();
			    }
			}
			}
			
			
			

		
	}
	
	


}
