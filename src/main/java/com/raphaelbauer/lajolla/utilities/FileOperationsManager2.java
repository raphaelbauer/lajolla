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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Structure;

import com.raphaelbauer.lajolla.SequenceDB;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;

public class FileOperationsManager2 {
	
	/**
	 * loads a sequenceDB from specified path.
	 * returns null when not saved correctly
	 * 
	 * @param pathToSequenceDB a path like "/../.../sequence.db.ser" to the seqdb
	 * @return the sequenceDB or null when there are errors
	 */
	public static SequenceDB loadSerializedSequenceDB(String pathToSequenceDB) {
		
        try {
        	
    		ObjectInputStream objstream = new ObjectInputStream(
    				new BufferedInputStream(
    						new FileInputStream(pathToSequenceDB)));
    		
    	    SequenceDB sequenceDB = (SequenceDB) objstream.readObject();
    	    objstream.close();
    	       
    	    return sequenceDB;
    	    
    	    
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 */
	public static SequenceDB generateSequenceDBRecursivelyFromDirOrFile(
			String rootDirectory,
			IFileToStringTranslator thisTranslator,
			int ngramSize) {
		
		
		return thisTranslator.getSequencesRecursivelyFromDirOrFile(
				rootDirectory, ngramSize);
		
		
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * @param pathWithFileName
	 * @param sequenceDB
	 */
	public static void saveSequenceDB(
			String pathWithFileName, 
			SequenceDB sequenceDB) {
		
		
	    ObjectOutputStream objstream;
	    
	    
		try {
			
			objstream = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(pathWithFileName)));
			
		    objstream.writeObject(sequenceDB);
		    objstream.close();
		    
		    
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
