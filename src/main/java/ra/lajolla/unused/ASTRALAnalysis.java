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
package ra.lajolla.unused;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ra.lajolla.utilities.CollectFilesRecursiveLowMemory;
import ra.lajolla.utilities.SCOP;


public class ASTRALAnalysis {
	
	public static void main(String [] a) {
		
		String astralDir = "/home/ra/lajolla/astral/";
		
		//dir with unterverzeichnissen:
		String resultDir = "/home/ra/tmp/astral_self_PROOne_za_90_zc_2.0_zn_9_zr1_4.0_zr2_3.0_ref_0.3_r_10.0d_no_bug/";
		
		HashMap<String, SCOP> astralFileSCOPMapping= new HashMap<String, SCOP>();

		HashMap<String, Integer> scopClassesInASTRALCounter 	
			= new HashMap<String, Integer>();
		
		
		
		
		
		//1. hashtable generieren mit file => SCOP
		CollectFilesRecursiveLowMemory cflm = new CollectFilesRecursiveLowMemory();
		ArrayList<String> allAstralFiles = cflm.collectAllFilesInDirectory(astralDir);
		
		for (String fileNameWithPath : allAstralFiles) {
			
			try {
				String fileName = new File(fileNameWithPath).getName();
				
				FileReader fr = new FileReader(fileNameWithPath);
				BufferedReader br = new BufferedReader(fr);
				
				String scop = "";
				String line = "";
				
				while((line = br.readLine()) != null) {
					
					if (line.contains("REMARK  99 ASTRAL SCOP-sccs:")) {
						
						scop = line.split("REMARK  99 ASTRAL SCOP-sccs:")[1].trim();
						//System.out.println(scop);
						
						
						break;
						
					}
					
				}
				
				br.close();
				fr.close();
				
				
				
				if (scop.equals("d.157.1.1")) {
					
					System.out.println("d.157: " + fileNameWithPath);
					
				}
				
				
				if (!scop.equals("")) {
					
					astralFileSCOPMapping.put(fileName, new SCOP(scop));
					
					
					if (scopClassesInASTRALCounter.containsKey(scop)) {
						
						Integer integer = scopClassesInASTRALCounter.get(scop);
						//System.out.println(integer);
						
						integer++;
						
						//System.out.println(integer);
						
						scopClassesInASTRALCounter.put(scop,integer);
						
						//System.out.println("after +1 : " 
						//		+ scopClassesInASTRALCounter.get(scop));
						
						
						
						
					} else {
						
						scopClassesInASTRALCounter.put(scop, 1);
						
						
					}
					
					
					
				}
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			

		}
		
		
		//2. ergebnisse durchscreenen...
		
		
		File [] allFilesInResultDir = new File(resultDir).listFiles();
		
		
		for (File thisDir : allFilesInResultDir) {
			
			
			
			
			if (thisDir.isDirectory()) {
				//System.out.println("isDir");
				//get scop of this dir...
				//d1y13a_.ent-model-0-chain-A
				
				String tmp_fileNameOfDir = thisDir.getName().split("-")[0];
				
				
				//System.out.println(tmp_fileNameOfDir);
				SCOP thisSCOP = astralFileSCOPMapping.get(tmp_fileNameOfDir);
				//System.out.println(thisSCOP);
				
				
				//get scop for each file of this dir...
				File [] allResultsOfThisMatching = thisDir.listFiles();
				
				
				
				System.out.println("query: " + tmp_fileNameOfDir + " scop: "+ thisSCOP.toString() + " number of files with that scop domain: " + scopClassesInASTRALCounter.get(thisSCOP.toString()));
				for (File resultFile : allResultsOfThisMatching) {
					
					
					if (resultFile.getName().endsWith("1.pdb")) {
						
						
						//System.out.println(resultFile.getName());
						String thisName = resultFile.getName();
						
						
						//stupid:
						String partWithoutQueryName = thisName.split(".ent")[1];
						//System.out.println(partWithoutQueryName);
						
						String targetNameFileName = partWithoutQueryName+".ent";
						
						
						SCOP tScop = astralFileSCOPMapping.get(targetNameFileName);
						
						System.out.println("t:" + targetNameFileName + " scop: " + tScop.toString());
						
						
						
						
						
						
						
						
					}
					
					
				}

			}
			
		}
		
		
		
		

		
	}

}
