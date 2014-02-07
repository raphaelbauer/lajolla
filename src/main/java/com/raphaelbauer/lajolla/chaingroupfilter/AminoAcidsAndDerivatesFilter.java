package com.raphaelbauer.lajolla.chaingroupfilter;

import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.ChainImpl;
import org.biojava.bio.structure.Group;

import com.raphaelbauer.lajolla.utilities.Utility;

public class AminoAcidsAndDerivatesFilter implements IChainGroupFilter {

  @Override
  public Chain filter(Chain chain) {

    Chain returnChain = new ChainImpl();

    for (Group group : chain.getAtomGroups()) {

      if (Utility.isAminoAcidOrAminoAcidDerivate(group)) {

        returnChain.addGroup(group);

      }

    }

    return returnChain;

  }

}
