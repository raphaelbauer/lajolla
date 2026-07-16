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
package com.raphaelbauer.lajolla.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.ChainImpl;
import org.biojava.nbio.structure.Group;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.io.PDBFileParser;
import org.biojava.nbio.structure.io.PDBFileReader;
import org.biojava.nbio.core.util.InputStreamProvider;

import com.raphaelbauer.lajolla.PDBInfo;

public class SwissKnife {

  /**
   *
   *
   * simple method to load a structure from a PDB file.
   *
   * @param path
   * @return
   */
  public static final Structure loadPDBFile(final String path) {

    PDBFileReader pdbreader = new PDBFileReader();

    pdbreader.getFileParsingParameters().setAlignSeqRes(false);
    pdbreader.getFileParsingParameters().setParseCAOnly(false);
    pdbreader.getFileParsingParameters().setParseSecStruc(false);

    Structure struc = null;
    try {
      struc = pdbreader.getStructure(path);
    } catch (IOException ex) {
      Logger.getLogger(SwissKnife.class.getName()).log(Level.SEVERE, null, ex);
    }

    return struc;

  }

  /**
   *
   *
   * simple method to load a structure from a PDB file. - for ambiguous amino
   * acids or nucleotides => only the A version is loaded.. - => something like
   * 20A and 20B
   *
   * @param path
   * @return
   */
  public static final Chain loadPDBChainFromPDBInfo(
          PDBInfo pdbInfo,
          boolean parseCAOnly) {

    Chain returnChain = null;

    InputStreamProvider isp = new InputStreamProvider();

    InputStream inStream;

    BufferedInputStream bis;

    try {

      inStream = isp.getInputStream(pdbInfo.getPDBCode());

      bis = new BufferedInputStream(inStream);

      PDBFileParser pdbpars = new PDBFileParser();
      pdbpars.getFileParsingParameters().setParseSecStruc(false);
      pdbpars.getFileParsingParameters().setAlignSeqRes(false);
      pdbpars.getFileParsingParameters().setParseCAOnly(parseCAOnly);


      Structure struc = pdbpars.parsePDBFile(bis);

		//System.out.println("CHAIN: " + pdbInfo.getChainID());
		//System.out.println("MODEL: " + pdbInfo.getModelNr());
      try {

			//// there is a bug as struc.getChainByPDB( does NOT return
        // all hetatoms (after TER are neglected..
        // so 
        returnChain = struc.getPolyChainByPDB(pdbInfo.getChainID(), pdbInfo.getModelNr());
			//System.out.println("swissknife says: " + returnChain.getAtomLength());

			//if (struc.hasChain(pdbInfo.getChainID()) {
        //returnChain = struc.findChain(pdbInfo.getChainID(), pdbInfo.getModelNr());
//				returnChain = new ChainImpl();
//				
//				
//				//nasty code to deal with 20A 20B and so
//				for (Group group : returnChain_temp.getAtomGroups()) {
//					
//					double thisdouble = Double.NaN;
//					try {
//						Double.parseDouble(group.getPDBCode());
//						
//						
//						
//					} catch (NumberFormatException e) {
//
//						
//						
//						//e.printStackTrace();
//					}
//					
//					if (!(thisdouble == Double.NaN)) {
//						
//						returnChain.addGroup(group);
//						
//					} else {
//						//only add the  "A" group if they are ambigue...
//							if (group.getPDBCode().endsWith("A")) {
//							
//								returnChain.addGroup(group);
//							
//						}
//						
//						
//					}
//					
//					
//						
//						
//					
//					
//				}
			//}
      } catch (RuntimeException e) {
        // getPolyChainByPDB may fail for a missing chain/model; leave
        // returnChain null and let the caller handle it.
      }

      bis.close();
      inStream.close();

    } catch (IOException e) {
      e.printStackTrace();
    }

    return returnChain;

  }

  /**
   *
   *
   * simple method to load a structure from a PDB file.
   *
   * @param path
   * @return
   */
  public static final String getSCOPClassFromASTRALFile(
          final String pathToFile) {

    String returnValue = null;

    FileReader fr;
    BufferedReader br;

    try {
      fr = new FileReader(pathToFile);

      br = new BufferedReader(fr);

      String line = "";

      while ((line = br.readLine()) != null) {

        if (line.startsWith("REMARK  99 ASTRAL SCOP-sccs: ")) {

          returnValue = line.split("REMARK  99 ASTRAL SCOP-sccs: ")[1];

          br.close();
          fr.close();

          break;
        }

      }

      br.close();
      fr.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {

    }

    return returnValue;

  }

}
