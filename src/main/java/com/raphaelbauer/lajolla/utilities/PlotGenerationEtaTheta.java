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
 *//*
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
 *//*
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
 *//*
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
import org.biojava.bio.structure.HetatomImpl;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;



public class PlotGenerationEtaTheta {
	
	
	
	
	
public static void main(String [] a) {
		
		
		
		String inputFileOrDir = "/var/lib/trna-webapp/databases/rnaDB05/";
		
		ArrayList<ArrayList<String>> anglesToBeWrittenOut
			= new ArrayList<ArrayList<String>>();
		
		ArrayList<String> etasToBeWrittenOut = new ArrayList<String>(); 
		ArrayList<String> thetasToBeWrittenOut = new ArrayList<String>(); 
		
		
		
		
		anglesToBeWrittenOut.add(etasToBeWrittenOut);
		anglesToBeWrittenOut.add(thetasToBeWrittenOut);
		
		
		
		if (new File(inputFileOrDir).isDirectory()) {
			//is a dir
			
			
			
			String [] fileNames = new File(inputFileOrDir).list();
			
			
			for (String file : fileNames) {
				
				if (!(file.endsWith(".pdb") || file.endsWith(".ent"))){
					continue;
				}
				
				//System.out.println("collecting: " + file);
				
				
				String completeFileName = inputFileOrDir + File.separator + file;
				
				Structure structure = SwissKnife.loadPDBFile(completeFileName);
				
				
				
				
				for (Chain chain : structure.getChains()) {

					List<Group> groups = chain.getAtomGroups();

					 ArrayList<ArrayList<String>> temp 
					 	= getEtaTheta(groups);
					 
					 //System.out.println("tempsize: " + temp.get(0).size());
					 
					 ArrayList<String> etas = temp.get(0);
					 ArrayList<String> thetas =  temp.get(1);
					 
					 etasToBeWrittenOut.addAll(etas);
					 thetasToBeWrittenOut.addAll(thetas);
					 
					 //System.out.println("size: " + anglesToBeWrittenOut.get(0).size());
					 //System.out.println("size: " + anglesToBeWrittenOut.get(1).size());
					 
					 
					
				}
				
				
				
				
				
				
			}
			
			
			
		} else {
			
			//is a file
			
			
			Structure structure = SwissKnife.loadPDBFile(inputFileOrDir);
			
			
			
			
			for (Chain chain : structure.getChains()) {

				List<Group> groups = chain.getAtomGroups();
				
				 ArrayList<ArrayList<String>> temp 
				 	= getEtaTheta(groups);
				 
				 

				 //System.out.println("tempsize: " + temp.get(0).size());
				 
				 ArrayList<String> etas = temp.get(0);
				 ArrayList<String> thetas =  temp.get(1);
				 
				 etasToBeWrittenOut.addAll(etas);
				 thetasToBeWrittenOut.addAll(thetas);
				 
				 //System.out.println("size: " + anglesToBeWrittenOut.get(0).size());
				 //System.out.println("size: " + anglesToBeWrittenOut.get(1).size());
				 
				 
				
			}
			
			
			
			
		}
		
		
		
		

		try {
			
		 
		 FileWriter fw;
	
			
				fw = new FileWriter("/home/ra/Desktop/etatheta.csv");
		
			
			 PrintWriter pw = new PrintWriter(fw);
			 
			ArrayList<String> etas = anglesToBeWrittenOut.get(0);
			ArrayList<String> thetas = anglesToBeWrittenOut.get(1);
			
			
			
			for (int i = 0; i < etas.size(); i++) {
				 pw.write(etas.get(i).replace(".", ",") + " ");
				 
				 pw.write(thetas.get(i).replace(".", ",") + "\n");
				
				
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
	 * @param nucleotidesAndSimilarMolecules the group of aminos
	 * @return string with transformed characters
	 */
	public static final ArrayList<ArrayList<String>> getEtaTheta(final List<Group> nucleotidesAndSimilarMolecules) {
		
	//System.out.println(nucleotidesAndSimilarMolecules.size());
		
		// for the return...
		ArrayList<String> translatedEta = new ArrayList<String>();
		
		ArrayList<String> translatedTheta = new ArrayList<String>();
        
		
        
		// expected length
		int nucleotideLength = nucleotidesAndSimilarMolecules.size();
		
		
        //first amino
        HetatomImpl a;
		//second amino
        HetatomImpl b;
		//third amino
        HetatomImpl c;
		
        double eta;
        double theta;
        

        for (int i = 0; i < nucleotideLength; i++) {
        	
            eta = 0.0d;
        	theta = 0.0d; 
            
            ///check if the other two aminos exist:
            if ((i>0) && (i < nucleotideLength - 1)) {
            	

                a = (HetatomImpl) nucleotidesAndSimilarMolecules.get(i - 1);
                
                b = (HetatomImpl) nucleotidesAndSimilarMolecules.get(i);

                c = (HetatomImpl) nucleotidesAndSimilarMolecules.get(i + 1);
            	
	            try {
	            	eta = Utility.getEta(a, b, c);
	            	
	            	theta = Utility.getTheta(b, c);
	            	
	            } catch (StructureException e) {
	            	//e.printStackTrace();
	            }

                
            	
            }

            
            
            if ((eta != 0) && (theta != 0.0)) {

            	translatedEta.add(Double.toString(eta));
            
            	translatedTheta.add(Double.toString(theta));
            }

            
        }

        
        ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
        temp.add(translatedEta);
        temp.add(translatedTheta);

		return temp;
	}
	
	
	
	
}
