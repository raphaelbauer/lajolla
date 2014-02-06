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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RemoveSeqResFromRAND {
	
	
	public static void main(String [] a) {
		
		try {
			String randDir = "/media/truecrypt1/benchmarking/datasets/rna/sara-rand-rna/";
			
			String outputDir = "/media/truecrypt1/benchmarking/datasets/rna/sara-rand-rna-fixed/";
			
			RecursiveFileCollector recursiveFileCollector = new RecursiveFileCollector();

			recursiveFileCollector
					.collectFilesRecursively(randDir);

			ArrayList<String> allFiles = recursiveFileCollector.getArrayListWithFiles();
			
			
			
			new File(outputDir).mkdirs();
			
			
			
			
			for (String file : allFiles) {
				
				StringBuffer output = new StringBuffer();
				
				
				FileReader fis = new FileReader(file);
				
				BufferedReader bis = new BufferedReader(fis);
				
				
				String line;
				
				while ((line = bis.readLine()) != null) {
					
					if (line.startsWith("SEQRES")) {
						
						
					}else if (line.startsWith("HEADER")) {
						
						
					} else if (line.startsWith("COMPND")) {
						
											}
 				//else if (line.startsWith("TER")) {
//						
//						
//					}
//					
//					
//					else {
//						
						
						
						//String beforeChain = line.substring(0,22);
						
						
						//String afterChain = line.substring(22,output.length());
						
						
 				else {
						
						output.append(line.replaceAll("A  ", "A A").replaceAll("G  ", "G A").replaceAll("U  ", "U A").replaceAll("C  ", "C A") + "\n");
				}
					
					
				}
				
				
				
				String filenameOnly = new File(file).getName();
				
				
				FileWriter fileWriter = new FileWriter(outputDir + File.separator + filenameOnly);
				
				BufferedWriter br = new BufferedWriter(fileWriter);
				
				br.write(output.toString());
				
				br.close();
				fileWriter.close();
				
				
				
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
