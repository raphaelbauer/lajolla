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

/**
 * Utility class collecing recuresevly all Files in a directory.
 *
 * @author ra
 *
 */
public class DeleteDirRecursively {
	
	
    public static boolean deleteDir(File fileOrDir) {
    	
        if (fileOrDir.isDirectory()) {
        	
            String[] children = fileOrDir.list();
            
            for (int i=0; i<children.length; i++) {
            	
                boolean success = deleteDir(new File(fileOrDir, children[i]));
                
                if (!success) {
                	
                    return false;
                    
                }
            }
        }
    

        return fileOrDir.delete();
    }



}
