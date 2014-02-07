package com.raphaelbauer.lajolla.transformation;

import com.raphaelbauer.lajolla.SequenceDB;

public interface IRefinementAtomsToUse {

  public SequenceDB getSequencesDBRecursivelyFromRootDir(
          String inputFile, int ngramSize);

}
