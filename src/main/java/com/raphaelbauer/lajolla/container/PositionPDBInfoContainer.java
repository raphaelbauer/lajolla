package com.raphaelbauer.lajolla.container;

public class PositionPDBInfoContainer {

  private int pdbInfoID;

  private int position;

  public PositionPDBInfoContainer(int pdbInfoID, int position) {
    super();
    this.pdbInfoID = pdbInfoID;
    this.position = position;
  }

  public int getPdbInfoID() {
    return pdbInfoID;
  }

  public int getPosition() {
    return position;
  }

}