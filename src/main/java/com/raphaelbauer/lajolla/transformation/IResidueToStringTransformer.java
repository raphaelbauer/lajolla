package com.raphaelbauer.lajolla.transformation;

import java.util.List;

import org.biojava.bio.structure.Group;

public interface IResidueToStringTransformer {

  public String getStringFromResidues(List<Group> thisGroup);

  public int getNumberOfCharactersInStringCorrespondToOneResidue();

  public String getStringMeaningNoResultAtThatPosition();

}
