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
 *//*
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
 *//*
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
 *//*
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


package com.raphaelbauer.lajolla.utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Calc;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.ChainImpl;
import org.biojava.nbio.structure.Group;
import org.biojava.nbio.structure.HetatomImpl;
import org.biojava.nbio.structure.StructureException;

public class Utility {

  public static Atom[] convertArrayListAtomsToArray(ArrayList<Atom> atoms) {

    Atom[] returnStuff = new Atom[atoms.size()];

    for (int i = 0; i < atoms.size(); i++) {

      returnStuff[i] = atoms.get(i);

    }

    return returnStuff;
  }

  public static Atom[] convertChainToAtomArray(Chain chain) {

    Atom[] returnStuff = new Atom[chain.getAtomLength()];
    int returnStuffCounter = 0;

    List<Group> atomGroups = chain.getAtomGroups();

    for (int i = 0; i < atomGroups.size(); i++) {

      List<Atom> atoms = atomGroups.get(i).getAtoms();

      for (int j = 0; j < atoms.size(); j++) {

        returnStuff[returnStuffCounter] = atoms.get(j);
        returnStuffCounter++;
      }

    }

    return returnStuff;
  }

  public static List<Atom> convertChainToAtomList(Chain chain) {

    List<Atom> returnStuff = new ArrayList<Atom>();

		//int returnStuffCounter = 0;
    List<Group> atomGroups = chain.getAtomGroups();

    for (int i = 0; i < atomGroups.size(); i++) {

      List<Atom> atoms = atomGroups.get(i).getAtoms();

      for (int j = 0; j < atoms.size(); j++) {

        returnStuff.add(atoms.get(j));
        //returnStuffCounter++;
      }

    }

    return returnStuff;
  }

  public static Chain convertAtomArrayToChain(List<Atom> atomArray) {

    Chain returnChain = new ChainImpl();

    for (Atom atom : atomArray) {
      Group group = new HetatomImpl();
      // we have to add this. Otherwise cloning does not work (yields a NPE);

      group.setResidueNumber("A", 1, null);

      group.addAtom(atom);

      returnChain.addGroup(group);

    }

    return returnChain;

  }

  public static boolean isNucleotideOrNucleotideDerivate(HetatomImpl hetAtom) {

    if (!hetAtom.hasAtom("C4'")) {
      return false;
    } else if (!hetAtom.hasAtom("P")) {
      return false;
    } else {
      return true;

    }

  }

  public static boolean isAminoAcidOrAminoAcidDerivate(Group hetAtom) {

	  /////////////////////////////////////////////////////////////////////////
    // check if needed atoms exist in groups aka is this a amino acid derivate?
    /////////////////////////////////////////////////////////////////////////  
    if (!hetAtom.hasAtom("C")) {
      return false;
    } else if (!hetAtom.hasAtom("N")) {
      return false;
    } else if (!hetAtom.hasAtom("CA")) {
      return false;
    } else {
      return true;
    }

  }

	////////////////////////////////////////////////////////////////////////////
  // why did i do this and was not using biojava code?
  // - sometimes getChainbyAtom("amino") returns also hetatoms aka amino acids aka MSE and so on
  // - this is bad, beacause these modified aminos should be translated as well...
  // - so the custom functions eat also hetatoms and try to calculate phi psis...
  ////////////////////////////////////////////////////////////////////////////
  /**
   * Returns the named atom of a group, throwing a {@link StructureException}
   * when it is absent.
   *
   * <p>BioJava 3's {@code Group.getAtom(String)} threw a StructureException for
   * a missing atom; BioJava 7 returns {@code null} instead. The angle and
   * connectivity helpers below rely on the throwing behaviour (their callers
   * catch StructureException to skip residues with incomplete backbones), so we
   * restore it here.
   */
  private static Atom requireAtom(Group group, String atomName)
          throws StructureException {

    Atom atom = group.getAtom(atomName);
    if (atom == null) {
      throw new StructureException("atom " + atomName
              + " not found in group " + group.getPDBName());
    }
    return atom;
  }

  /**
   * phi angle.
   *
   * @param a an AminoAcid object
   * @param b an AminoAcid object
   * @return a double
   * @throws StructureException ...
   */
  public static double getPhi(Group a, Group b)
          throws StructureException {

    if (!isAminoAcidOrAminoAcidDerivate(a)) {
      throw new StructureException("can not calc eta - not AminoAcid a");
    }

    if (!isAminoAcidOrAminoAcidDerivate(b)) {
      throw new StructureException("can not calc eta - not AminoAcid b");
    }

    if (!isConnectedAminoAcid(a, b)) {
      throw new StructureException("can not calc Phi - AminoAcids are not connected!");
    }

    Atom a_C = requireAtom(a, "C");
    Atom b_N = requireAtom(b, "N");
    Atom b_CA = requireAtom(b, "CA");
    Atom b_C = requireAtom(b, "C");

    double phi = Calc.torsionAngle(a_C, b_N, b_CA, b_C);
    return phi;
  }

  /**
   * psi angle.
   *
   * @param a an AminoAcid object
   * @param b an AminoAcid object
   * @return a double
   * @throws StructureException ...
   */
  public static double getPsi(Group a, Group b)
          throws StructureException {

    if (!isAminoAcidOrAminoAcidDerivate(a)) {
      throw new StructureException("can not calc eta - not AminoAcid a");
    }

    if (!isAminoAcidOrAminoAcidDerivate(b)) {
      throw new StructureException("can not calc eta - not AminoAcid b");
    }

    if (!isConnectedAminoAcid(a, b)) {
      throw new StructureException("can not calc Psi - AminoAcids are not connected!");

    }

    Atom a_N = requireAtom(a, "N");
    Atom a_CA = requireAtom(a, "CA");
    Atom a_C = requireAtom(a, "C");
    Atom b_N = requireAtom(b, "N");

    double psi = Calc.torsionAngle(a_N, a_CA, a_C, b_N);
    return psi;

  }

  /**
   * test if two amino acids are connected, i.e. if the distance from C to N <
   * 2,5 Angstrom.
   *
   * @param a an AminoAcid object
   * @param b an AminoAcid object
   * @return true if ...
   * @throws StructureException ...
   */
  public static boolean isConnectedAminoAcid(Group a, Group b)
          throws StructureException {
    Atom C = requireAtom(a, "C");
    Atom N = requireAtom(b, "N");

    // one could also check if the CA atoms are < 4 A...
    double distance = Calc.getDistance(C, N);
    if (distance < 2.5) {
      return true;
    } else {
      return false;
    }
  }

   /////////////////////////////////////////////////////////////////////////////
  // Nucleotides
  /////////////////////////////////////////////////////////////////////////////
  /**
   * phi angle.
   *
   * eta: C4'n-1, Pn, C4'n, Pn+1
   *
   * @param a an AminoAcid object
   * @param b an AminoAcid object
   * @return a double
   * @throws StructureException ...
   */
  public static double getEta(HetatomImpl a, HetatomImpl b, HetatomImpl c)
          throws StructureException {

    if (!isConnectedNucleotide(a, b)) {
      throw new StructureException("can not calc eta - nucleotides a ("
              + a.getResidueNumber() + ") b (" + b.getResidueNumber() + ") are not connected!");
    }

    if (!isConnectedNucleotide(b, c)) {
      throw new StructureException("can not calc eta - nucleotides b ("
              + b.getResidueNumber() + ") c (" + c.getResidueNumber() + ") are not connected!");
    }

    if (!isNucleotideOrNucleotideDerivate(a)) {
      throw new StructureException("can not calc eta - not nucleotode a");

    }

    if (!isNucleotideOrNucleotideDerivate(b)) {
      throw new StructureException("can not calc eta - not nucleotode b");

    }
    if (!isNucleotideOrNucleotideDerivate(c)) {
      throw new StructureException("can not calc eta - not nucleotode c");

    }

       /////////////////////////////////////////////////////////////////////////
    // calculate pseuo dihedral angles
    /////////////////////////////////////////////////////////////////////////
    Atom a_C4_minus_1 = requireAtom(a, "C4'");
    Atom b_P = requireAtom(b, "P");

    Atom b_C4 = requireAtom(b, "C4'");
    Atom c_P_plus_1 = requireAtom(c, "P");

    double eta = Calc.torsionAngle(a_C4_minus_1, b_P, b_C4, c_P_plus_1);
    //System.out.println("eta: " + eta);
    return eta;
  }

  /**
   * psi angle. theta: Pn, C4'n, Pn+1, C4'n+1
   *
   * @param b an AminoAcid object
   * @param c an AminoAcid object
   * @return a double
   * @throws StructureException ...
   */
  public static double getTheta(HetatomImpl b, HetatomImpl c)
          throws StructureException {

       /////////////////////////////////////////////////////////////////////////
    // check if needed atoms exist in structure
    /////////////////////////////////////////////////////////////////////////       
    if (!isNucleotideOrNucleotideDerivate(b)) {
      throw new StructureException(
              "can not calc eta - not nucleotode b");

    }
    if (!isNucleotideOrNucleotideDerivate(c)) {
      throw new StructureException(
              "can not calc eta - not nucleotode c");

    }

    if (!isConnectedNucleotide(b, c)) {
      throw new StructureException(
              "can not calc theta - nucleotides are not connected!");
    }

        ////////////////////////////////////////////////////////////////////////
    // calc the pseudo angle:
    //////////////////////////////////////////////////////////////////////// 
    Atom a_P = requireAtom(b, "P");
    Atom a_C4 = requireAtom(b, "C4'");

    Atom b_P = requireAtom(c, "P");
    Atom b_C4 = requireAtom(c, "C4'");

    double theta = Calc.torsionAngle(a_P, a_C4, b_P, b_C4);
    //System.out.println("theta: " + theta);
    return theta;

  }

  /**
   * test if two nucs are connected, i.e. if the distance from P to P < 4
   * Angstrom.
   *
   * @param a an AminoAcid object
   * @param b an AminoAcid object
   * @return true if ...
   * @throws StructureException ...
   */
  public static boolean isConnectedNucleotide(HetatomImpl a, HetatomImpl b)
          throws StructureException {
    Atom a_P = requireAtom(a, "P");
    Atom b_P = requireAtom(b, "P");

    //check if Ps are only 8 A from each other...
    double distance = Calc.getDistance(a_P, b_P);
    //System.out.println(distance);
    if (distance < 10.5) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Problem: Your have sometimes Strings like !!!!!!!!!!!!!!!! with no (or
   * almost no) information content. !!!!!!!!!! would be a helix like structure
   * in the protein case.
   *
   * What happens: - it blows the hashtable - it crashes the search because
   * stupid elements get found everywhere...
   *
   * Solution: - check if a searchstring makes sense in terms of information
   * theory.
   *
   *
   *
   * @param currentSearchString
   * @param frequencyHashMap
   * @return
   */
  public static int getNumberOfDifferentCharsInThisString(
          String currentSearchString) {

    HashSet<String> theseHashes = new HashSet<String>();

    for (int i = 0; i < currentSearchString.length(); i++) {

      theseHashes.add(currentSearchString.substring(i, i + 1));

    }

    return theseHashes.size();

  }


  public static List<Atom> removeAllAtomExcept(String atomUsedForRefinement, Chain chain) {

    List<Atom> returnAtomArray = new ArrayList<Atom>();

    for (int i = 0; i < chain.getAtomLength(); i++) {

      Group g = chain.getAtomGroup(i);

      // BioJava 7's getAtom(String) returns null (instead of throwing) when the
      // group has no such atom; skip those to keep the original behaviour.
      Atom atom = g.getAtom(atomUsedForRefinement);
      if (atom != null) {
        returnAtomArray.add(atom);
      }

    }

    return returnAtomArray;

  }

}
