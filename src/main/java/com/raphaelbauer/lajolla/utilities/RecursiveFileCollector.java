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
import java.util.ArrayList;

public class RecursiveFileCollector {
	
	
	ArrayList<String> arrayListWithFiles;
	
//	private boolean ignoreHidden = true;
//	
//	
//	public void setIgnoreHidden(boolean ignoreHidden) {
//		
//		this.ignoreHidden = ignoreHidden;
//		
//	}
//	
//	
//	public boolean isIgnoreHidden() {
//		
//		return ignoreHidden;
//		
//	}
//	
	
	
	public RecursiveFileCollector() {
		
		this.arrayListWithFiles = new ArrayList<String>();
		
		
	}
	
	public void collectFilesRecursively(String rootDirectory) {
		
		
		//log.debug(dirString);
    	
    	File dir = new File(rootDirectory);
    	
    	//log.debug(dir.getPath());
    	
    	if (dir.isFile()) {
    			
    		if (!dir.isHidden()) {
    			this.arrayListWithFiles.add(dir.getAbsolutePath());
    		}
    	}
    	
        //log.debug(dir);

        if (dir.isDirectory()) {
        	
        	if (!dir.isHidden()) {
        		
        		
        	
        		String[] children = dir.list(); //returns only getName().. so we add the original path:
            
        		for (int i = 0; i < children.length; i++) {
        			collectFilesRecursively(dir.getPath() + File.separator + children[i]);
        		}
        	}
        }
        

	}

	
	public ArrayList<String> getArrayListWithFiles() {
		
		return this.arrayListWithFiles;
			
	}
	
	
	
	/**
	 * resets the contents. Can omit another new() recursive bla... 
	 */
	public void reset() {
		
		arrayListWithFiles = new ArrayList<String>();
		
	}
	
	

	
	
	
	

}
