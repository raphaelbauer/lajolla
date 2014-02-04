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
package ra.lajolla.transformation;

import java.util.List;

import org.biojava.bio.structure.Group;

public interface IResidueToStringTransformer {

	public String getStringFromResidues(List<Group> thisGroup);
	
	public int getNumberOfCharactersInStringCorrespondToOneResidue();
	
	
	
	/**
	 * @return
	 */
	public String getStringMeaningNoResultAtThatPosition();

}
