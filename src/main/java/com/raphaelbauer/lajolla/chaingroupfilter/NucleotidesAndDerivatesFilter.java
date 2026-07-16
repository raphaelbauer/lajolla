package com.raphaelbauer.lajolla.chaingroupfilter;

import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.ChainImpl;
import org.biojava.nbio.structure.Group;
import org.biojava.nbio.structure.HetatomImpl;

import com.raphaelbauer.lajolla.utilities.Utility;

public class NucleotidesAndDerivatesFilter implements IChainGroupFilter {

  @Override
  public Chain filter(Chain chain) {

    Chain returnChain = new ChainImpl();

    for (Group group : chain.getAtomGroups()) {

      if (Utility.isNucleotideOrNucleotideDerivate((HetatomImpl) group)) {

        returnChain.addGroup(group);

      }

    }

    return returnChain;
  }

}
