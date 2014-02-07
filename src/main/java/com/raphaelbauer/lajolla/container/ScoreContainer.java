package com.raphaelbauer.lajolla.container;

public class ScoreContainer {

  private double overallScore = Double.NaN;

  private double RMSD = Double.NaN;

  private int lengthQueryTarget = -1;
  
  private int numberOfAlignedResidues = -1;
  
  private int lengthTemplate = -1;

  public ScoreContainer(
          double overallScore,
          double rmsd,
          int lengthQueryTarget,
          int lengthTemplate,
          int numberOfAlignedResidues) {
    super();
    this.RMSD = rmsd;
    this.lengthQueryTarget = lengthQueryTarget;
    this.lengthTemplate = lengthTemplate;
    this.numberOfAlignedResidues = numberOfAlignedResidues;
    this.overallScore = overallScore;
  }
  
  
  public int getLengthQueryTarget() {
    return lengthQueryTarget;
  }

  public int getNumberOfAlignedResidues() {
    return numberOfAlignedResidues;
  }

  public int getLengthTemplate() {
    return lengthTemplate;
  }

  public double getOverallSCORE() {
    return overallScore;
  }

  public double getRMSD() {
    return RMSD;
  }

}
