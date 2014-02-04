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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureImpl;

public class PDBConvenienceWriter {

	
	
	
	public static void writeOutSingleChain(Chain chain, String fileNamAndPath) {
		
		
		try {
			
			Structure newpdb = new StructureImpl();
			newpdb.setPDBCode("Java");
			newpdb.setName("Aligned with superrafi");
			newpdb.setNmr(false);

			
			ArrayList<Chain> model = new ArrayList<Chain>();
			model.add(chain);
			
			
			
			newpdb.addModel(model);
			

			
			String pdbstr = newpdb.toPDB();
			
			
			
			
			FileOutputStream fileOutputStream 
				= new FileOutputStream(fileNamAndPath); 
			
			BufferedOutputStream bufferedOutputStream 
				= new BufferedOutputStream(fileOutputStream);
			
			PrintStream printStream = new PrintStream(
					bufferedOutputStream );

			
			printStream.print(pdbstr);

			printStream.close();
			bufferedOutputStream.close();
			fileOutputStream.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
}
