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
package ra.lajolla.container;

public class PositionPDBInfoContainer {
	
	
	private int pdbInfoID;
	
	private int position;
	

	public int getPdbInfoID() {
		return pdbInfoID;
	}

	public int getPosition() {
		return position;
	}

	public PositionPDBInfoContainer(int pdbInfoID, int position) {
		super();
		this.pdbInfoID = pdbInfoID;
		this.position = position;
	}
	
	
	
	
	

}
