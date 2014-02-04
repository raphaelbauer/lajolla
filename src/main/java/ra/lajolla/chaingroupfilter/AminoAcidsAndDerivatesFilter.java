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
package ra.lajolla.chaingroupfilter;

import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.ChainImpl;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.HetatomImpl;

import ra.lajolla.utilities.Utility;

public class AminoAcidsAndDerivatesFilter implements IChainGroupFilter {

	@Override
	public Chain filter(Chain chain) {

		//System.out.println("length before filtering is: " + chain.getAtomLength());
		
		
		Chain returnChain = new ChainImpl();
		
		for (Group group : chain.getAtomGroups()) {
			
			
			
			if (Utility.isAminoAcidOrAminoAcidDerivate((HetatomImpl) group)) {
				
				
				//if (isThisARegularResidueOrTheFirstAmbiguousOneAForInstance(group)) {
					
				//System.out.println("in adding");
				
					returnChain.addGroup(group);
				//}
				
				

				
			}
			
		}
			

			
			
		

		
		//System.out.println("length after filtering is: " + returnChain.getAtomLength());
		
		// TODO Auto-generated method stub
		return returnChain;

	}

}
