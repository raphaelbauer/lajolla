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

import java.io.File;
import java.util.ArrayList;

/**
 * Utility class collecting recursively all Files in a directory.
 *
 * @author ra
 *
 */
public class CollectFilesRecursiveLowMemory {

	
	
    
    /** All Files go here. */
	private ArrayList < String > collectedFilesVector 
		= new  ArrayList < String > ();
	
	
	
	////////////////////////////////////////////////////////////////////////////
	// OLD WORKER:
	////////////////////////////////////////////////////////////////////////////
	/**
	 * A method collecting all files in a directory recursively.
	 * 
	 * @param directoryFile The Directory where I shoud descend.
	 * @return Vector a Vector containing all retrieved files in this directury
	 * and underneath.
	 */
	public ArrayList <String> collectAllFilesInDirectory(
			final String directoryFile) {
		
		//System.out.println("hey!" + directoryFile);
		//log.info(directoryFile);
		
		traverse(directoryFile);	
		return collectedFilesVector;
		
	}
	
	
	/** 
	 * The recursive method. Going down and collecting everything
	 * can exclude specific filenames from collecting...
	 * 
	 * @param dir File or directory. If File then added to the collectedFiles
	 * Vector if Directory it is called recursively.
	 * @param excludeFileList Array of Strings containing Filenames that
	 * should be excluded from this array.
	 */
    private void traverse(
    		final String dirString) { 
    	
    	
    	File dir = new File(dirString);
    
    	if (dir.isFile()) {

    		
    			collectedFilesVector.add(dir.getAbsolutePath());
    		
    	}
    	
    	

        if (dir.isDirectory()) {
            String[] children = dir.list();
            
            for (int i = 0; i < children.length; i++) {
            	
                traverse(dir.getPath() + File.separator + children[i]);
                
            }
        }
    }  
	
 

}
