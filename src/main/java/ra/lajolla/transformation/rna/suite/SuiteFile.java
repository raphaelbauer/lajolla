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
package ra.lajolla.transformation.rna.suite;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * reads in suite file and provides facility to iterate over it...
 * 
 * file looks like:
 * ur0052H.pdb	tt1a1a1a1a1a1a1z6ntt9a1a1a1a1att1att1a1b4bootttt9a1a1e1.....
 * arn035H.pdb	1a1a1a&a1a1a1a1a1a1c1L1a1a
 * 
 * 
 * @author ra
 *
 */
public class SuiteFile {
	
	
	ArrayList<SuiteFileEntry> allBuckets 
		= new ArrayList<SuiteFileEntry>();
	
	
	//ArrayList<String> nameArray = new ArrayList<String>();
	//ArrayList<String> suiteArray = new ArrayList<String>();
	
	
	/**
	 * 
	 * 
	 * @param inputFile
	 */
	public SuiteFile(String inputFile) {
		
		//path MODEL CHAIN suite
		String patternString = "(.*)\t(.*)\t(.*)\t(.*)";
		// compile that pattern...
		Pattern pattern = Pattern.compile(patternString);
		
			
		 try {
		        FileInputStream fstream 
		        	= new FileInputStream(inputFile);

		        DataInputStream in = new DataInputStream(fstream); 
		        BufferedReader br = new BufferedReader(
		        		new InputStreamReader(in));
		           
		        String strLine;
		        
		        int tempCounter = 0;
		        while ((strLine = br.readLine()) != null)   {
		        	
		        	String tempName = "";
		        	int tempModelID = 0;
		        	String tempChainID = "";
		        	String tempSuite = "";
		        	
		        	
		        	tempCounter++;
		        	//System.out.println("parsing line: " 
		        	//	+ tempCounter);
		        	
		        	Matcher matcher = pattern.matcher(strLine);

					boolean matchFound = matcher.find();

					if ((matchFound) && (matcher.groupCount() >= 1)) {
						
						tempName = matcher.group(1);
						tempModelID = Integer.parseInt(matcher.group(2));
						tempChainID = matcher.group(3);
						tempSuite = matcher.group(4);
						
						
						allBuckets.add(new SuiteFileEntry(tempSuite, tempName, tempModelID, tempChainID));
						//nameArray.add(tempName);
						//suiteArray.add(tempSuite);
						
						
						
						
						
					}
		        	
		        }
		        

		        in.close();
		        
		        } catch (Exception e){
		        	
		          System.err.println("Error: " + e.getMessage());
		          
		        }
		        
		        
		
		
		
		
	}
	
	
	
	public ArrayList<SuiteFileEntry> getAllBuckets() {
		
		return this.allBuckets;
	}
	
	
	
	

}
