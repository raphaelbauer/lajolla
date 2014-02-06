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
package com.raphaelbauer.lajolla;

import java.io.Serializable;
import java.util.Hashtable;

public class PDBInfo implements Serializable {

	private String fileName;
	private int m_modelNr;
	private String chainNumber;

	public PDBInfo(String fileName, int modelNr, String chainNumber) {

		this.fileName = fileName;
		m_modelNr = modelNr;
		this.chainNumber = chainNumber;
		// m_sequenceDB_ID = sequenceDB_ID;
	}

	public String getPDBCode() {
		return fileName;
	}

	public int getModelNr() {
		return m_modelNr;
	}

	public String getChainID() {
		return chainNumber;
	}


}
