package com.raphaelbauer.lajolla.transformation;

import com.raphaelbauer.lajolla.SequenceDB;
import com.raphaelbauer.lajolla.chaingroupfilter.IChainGroupFilter;

public interface IFileToStringTranslator {

  public SequenceDB getSequencesRecursivelyFromDirOrFile(
          String inputFile,
          int ngramSize);

  public IChainGroupFilter getChainGroupFilter();

}
