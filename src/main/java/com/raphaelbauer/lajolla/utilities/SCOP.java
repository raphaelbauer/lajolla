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

public class SCOP {
	
	
	String SCOPClass;
	
	String fold;
	
	String superFamily;
	
	String family;
	
	/**
	 * 
	 * @param scopString
	 */
	public SCOP(String scopString) {
				
		String [] scopStringSeperated = scopString.split("\\.");
				
		if (scopStringSeperated.length != 4) {
			
			SCOPClass = "";
			fold = "";
			superFamily = "";
			family = "";
				
		} else {
		
			SCOPClass = scopStringSeperated[0];
			fold =  scopStringSeperated[1];		
			superFamily = scopStringSeperated[2];		
			family = scopStringSeperated[3];
		
		}
		
	}


	public String getSCOPClass() {
		return SCOPClass;
	}


	public String getFold() {
		return fold;
	}


	public String getSuperFamily() {
		return superFamily;
	}


	public String getFamily() {
		return family;
	}
	
	
	public String toString() {
		return SCOPClass+"."+fold+"."+superFamily+"."+family;
		
	}

}
