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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.biojava.bio.structure.AminoAcid;
import org.biojava.bio.structure.Calc;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;


public class PlotGenerationPhiPsi {
	
	public static void main(String [] a) {	
		
		String inputFileOrDir = "/home/ra/Desktop/tm_align_benchmark_set/";
		 //"src/test/resources/thymidylate_synthase_pdb/";
		
		ArrayList<ArrayList<String>> anglesToBeWrittenOut
			= new ArrayList<ArrayList<String>>();
		
		ArrayList<String> phisToBeWrittenOut = new ArrayList<String>();
		
		ArrayList<String> psisToBeWrittenOut = new ArrayList<String>();
		
		anglesToBeWrittenOut.add(phisToBeWrittenOut);
		anglesToBeWrittenOut.add(psisToBeWrittenOut);
		
		
		if (new File(inputFileOrDir).isDirectory()) {
			//is a dir
			
			
			
			String [] fileNames = new File(inputFileOrDir).list();
			
			
			for (String file : fileNames) {
				
				if (!(file.endsWith(".pdb") || file.endsWith(".ent"))){
					continue;
				}
				
				System.out.println("here");
				
				
				String completeFileName = inputFileOrDir + File.separator + file;
				
				Structure structure = SwissKnife.loadPDBFile(completeFileName);
				
				
				
				
				for (Chain chain : structure.getChains()) {

					List<Group> groups = chain.getAtomGroups("amino");

					 ArrayList<ArrayList<String>> temp 
					 	= getPhiPsiFromResidues(groups);
					 
					 ArrayList<String> phis = temp.get(0);
					 ArrayList<String> psis = temp.get(1);
					 
					 phisToBeWrittenOut.addAll(phis);
					 psisToBeWrittenOut.addAll(psis);
					 
					
				}
				
				
				
				
				
				
			}
			
			
			
		} else {
			
			//is a file
			
			
			Structure structure = SwissKnife.loadPDBFile(inputFileOrDir);
			
			
			
			
			for (Chain chain : structure.getChains()) {

				List<Group> groups = chain.getAtomGroups();

				 ArrayList<ArrayList<String>> temp 
				 	= getPhiPsiFromResidues(groups);
				 
				 ArrayList<String> phis = temp.get(0);
				 ArrayList<String> psis = temp.get(1);
				 
				 phisToBeWrittenOut.addAll(phis);
				 psisToBeWrittenOut.addAll(psis);
				 
				 
				
			}
			
			
			
			
		}
		
		
		
		

		try {
			
		 
		 FileWriter fw;
	
			
				fw = new FileWriter("/home/ra/Desktop/phipsi.csv");
		
			
			 PrintWriter pw = new PrintWriter(fw);
			 
			ArrayList<String> phis = anglesToBeWrittenOut.get(0);
			ArrayList<String> psis = anglesToBeWrittenOut.get(1);
			
			
			
			for (int i = 0; i < phis.size(); i++) {
				 pw.write(phis.get(i).replace(".", ",") + " " );
				 
				 pw.write(psis.get(i).replace(".", ",") + "\n");
				
				
			}
			
			 

			 pw.close();
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			
		 


		
		
	}
	
	
	
	
	
	

	/**
	 * gets a group of aminos (== chain), calculates the phi psi and transforms
	 * the phi psi into the characters needed for hashing / indexing...
	 * 
	 * @param aminos the group of aminos
	 * @return string with transformed characters
	 */
	static public final ArrayList<ArrayList<String>> getPhiPsiFromResidues(final List<Group> aminos) {
		
		// for the return...
		ArrayList<String> translatedPhi = new ArrayList<String>();
		
		ArrayList<String> translatedPsi = new ArrayList<String>();
        
		
        //first amino
		AminoAcid a;
		//second amino
		AminoAcid b;
		//third amino
		AminoAcid c;
		
        double phi;
        double psi;
        

        for (int i = 0; i < aminos.size(); i++) {
        	
            phi = 0.0d;
        	psi = 0.0d; 
            
        	// amino acid in the middle...
            b = (AminoAcid) aminos.get(i);
            
            
            // does the a exist in that case?
            if (i > 0) {
            	a = (AminoAcid) aminos.get(i - 1);
            	
	            try {
	            	phi = (double) Calc.getPhi(a, b);
	            	
	            } catch (StructureException e) {
	            	//e.printStackTrace();
	            }
            }
            
            
            //does the c exist in that case?
            if (i < aminos.size() - 1) {
            	
                c = (AminoAcid) aminos.get(i + 1);
                
                try {
                    psi = (double) Calc.getPsi(b, c);
                    
                } catch (StructureException e) {
                	//e.printStackTrace();
                }
            }
            
            
            
            
            
            //for a discretion step of eg. 4 this looks like:
            // => so a seqence in the length of the residue's length is generated
            // and can be used for a translation...
            //
            // ^
            // | 1  2  3  4
            // | 5  6  7  8
            // | 9  10 11 12
            // | 13 14 15 16
            // -------------->
            

            if ((phi != 0) && (psi != 0.0)) {
            
            	translatedPhi.add(Double.toString(phi));
            	translatedPsi.add(Double.toString(psi));
            	
            }
            
        }
        
        
        ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
        temp.add(translatedPhi);
        temp.add(translatedPsi);

		return temp;
	}

	

}
