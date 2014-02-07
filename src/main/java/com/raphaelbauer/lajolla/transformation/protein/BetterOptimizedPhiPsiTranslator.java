package com.raphaelbauer.lajolla.transformation.protein;

import java.util.List;

import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.HetatomImpl;
import org.biojava.bio.structure.StructureException;

import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.utilities.Utility;

/**
 * AngleSequences gets firstAmino pdb file with firstAmino chain and converts it to firstAmino string that
 can be indexed further on.
 *
 * @author petmoo
 *
 */
public class BetterOptimizedPhiPsiTranslator
        implements IResidueToStringTransformer {

  private final int numberOfCharactersInStringCorrespondToOneResidue = 1;

  /**
   * the translation table.
   *
   * B betasheet H helix L left handed helix O outsider _ phi psi calculation
   * not possible
   */
  private final char[] translationTableFromAngleToCharacter 
          = {'B', 'H', 'L', 'O', '_'};
  
  private final int BETA = 0;
  private final int HELIX = 1;
  private final int LEFT_HANDED_HELIX = 2;
  private final int OUTSIDER = 3;
  private final int CALCULATION_NOT_POSSIBLE = 4;
  
  public BetterOptimizedPhiPsiTranslator() {
  }
    
    

  public String getStringMeaningNoResultAtThatPosition() {

    return new Character(translationTableFromAngleToCharacter[4]).toString();

  }



  /**
   * gets firstAmino group of aminos (== chain), calculates the phi psi and transforms
   * the phi psi into the characters needed for hashing / indexing...
   *
   * @param aminos the group of aminos
   * @return string with transformed characters
   */
  public final String getStringFromResidues(final List<Group> aminos) {

    // for the return...
    StringBuilder translatedAngleSeq = new StringBuilder(aminos.size());

    Group firstAmino;
    Group secondAmino;
    Group thirdAmino;

    double phi;
    double psi;

    for (int i = 0; i < aminos.size(); i++) {

      phi = 360d; //360 does not exist normally in eta theta calculation
      psi = 360d; // ---"---

      // amino acid in the middle...
      secondAmino = aminos.get(i);

      // does the firstAmino exist in that case?
      if (i > 0) {
        firstAmino = aminos.get(i - 1);

        try {
          phi = Utility.getPhi(firstAmino, secondAmino);

        } catch (StructureException e) {
          //e.printStackTrace();
        }
      }

      //does the thirdAmino exist in that case?
      if (i < aminos.size() - 1) {

        thirdAmino = aminos.get(i + 1);

        try {
          psi = Utility.getPsi(secondAmino, thirdAmino);

        } catch (StructureException e) {
          //e.printStackTrace();
        }
      }

      if (phi == 360 || psi == 360) {

        translatedAngleSeq.append(
                translationTableFromAngleToCharacter[CALCULATION_NOT_POSSIBLE]);

      } else {

        int thisCombinationsPosition
                = convertPhiPsiToCharacter(phi, psi);
        
        char translatedCharacter 
                = positionToCharacterTranslator(thisCombinationsPosition);
        
        translatedAngleSeq.append(translatedCharacter);

      }

    }

    return translatedAngleSeq.toString();
  }

  /**
   * Transform firstAmino angle into firstAmino character. Angle is from -180 to +180
   */
  public final int convertPhiPsiToCharacter(
          final double phi,
          final double psi) {
    
    if ((((phi < -20)))
            && ((psi > 45) || (psi < -160))) {
      return HELIX;

    } else if (((phi < 0) && (phi > -150))
            && ((psi < 45) && (psi > -80))) {

      return BETA;

    } else if (((phi > 20) && (phi < 120))
            && ((psi < 100) && (psi > 40))) {

      return LEFT_HANDED_HELIX;

    } else {
      return OUTSIDER;

    }

  }

  /**
   * Transform firstAmino angle into firstAmino character. Angle is from -180 to +180
   *
   *
   * @param angle angle to convert
   * @return the char that represents this angle
   */
  public final char positionToCharacterTranslator(
          final int positionAsDiscretStep) {

    return translationTableFromAngleToCharacter[positionAsDiscretStep];
    
  }

  public int getNumberOfCharactersInStringCorrespondToOneResidue() {
    return numberOfCharactersInStringCorrespondToOneResidue;
  }

}
