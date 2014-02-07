package com.raphaelbauer.lajolla;

public class PDBInfo {

  private String fileName;
  private int m_modelNr;
  private String chainNumber;

  public PDBInfo(String fileName, int modelNr, String chainNumber) {

    this.fileName = fileName;
    m_modelNr = modelNr;
    this.chainNumber = chainNumber;

  }

  public String getPDBCode() {
    return fileName;
  }

  public int getModelNr() {
    return m_modelNr;
  }

  public String getChainID() {
    return chainNumber;
  }

}
