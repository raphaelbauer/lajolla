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

/**
 * <h1>Bucket - a storage for a line from an indexfile.</h1>
 * 
 * @author ra
 */
public class SuiteFileEntry {

    /** 
     * The string out of a file that will be used as index and therefore
     * indexed into the tree.
     */
    private String stringToBeIndexed;

    /**
     * An identifier for that string.
     * For instance a pdb id (1c44) or so.
     */
    private String pathOfThisEntry;


    
    private String chainID;
    
    private int modelID;
    
    /**
     * Simple constructor.
     * 
     * @param suiteToBeIndexed The string that is put into the tree.
     * @param pathOfThisEntry The identifier of that string.
     */
    public SuiteFileEntry(
	    final String suiteToBeIndexed, 
	    final String pathOfThisEntry,
	    final int modelID,
	    final String chainID) {

	super();
	this.stringToBeIndexed = suiteToBeIndexed;
	this.pathOfThisEntry = pathOfThisEntry;
	
	this.chainID = chainID;
	this.modelID = modelID;
	
    }

    /**
     * @return the stringToBeIndexed
     */
    public final String getSuiteString() {
	return stringToBeIndexed;
    }

    public String getChainID() {
		return chainID;
	}

	public int getModelID() {
		return modelID;
	}

	/**
     * @return the label
     */
    public final String getIdentifierOfThisEntitiy() {
	return pathOfThisEntry;
    }

//    public final Object clone() {
//
//	try {
//	    return (BucketToBeIndexedInTree) super.clone();
//
//	} catch (CloneNotSupportedException e) {
//
//	    e.printStackTrace();
//	    return null;
//	}
//
//    }

}
