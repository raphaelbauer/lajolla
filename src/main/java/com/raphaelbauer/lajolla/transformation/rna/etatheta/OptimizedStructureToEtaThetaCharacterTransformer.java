package com.raphaelbauer.lajolla.transformation.rna.etatheta;

import java.util.ArrayList;
import java.util.List;

import org.biojava.bio.structure.AminoAcid;
import org.biojava.bio.structure.Calc;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.HetatomImpl;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;

import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.utilities.Utility;

/**
 * AngleSequences gets a pdb file with a chain and converts it to a string that
 * can be indexed further on.
 *
 * @author petmoo
 *
 */
public class OptimizedStructureToEtaThetaCharacterTransformer
        implements IResidueToStringTransformer {

  private final int numberOfCharactersInStringCorrespondToOneResidue = 1;

  /**
   * the translation table.
   */
  private char[] translationTableFromAngleToCharacter
          = {'1', '2', '3', '4', 'O', '_'};

	// 1 big cluster in the 4 corners
  // 2 smaller cluster upper middle right
  // 3 smaller cluster lower middle
  // 4 smaller cluster lower middle left
  // 0 outsider
  // _ calculation not possible
  public String getStringMeaningNoResultAtThatPosition() {

		//System.out.println(new Character(translationTableFromAngleToCharacter[5]).toString());
    return new Character(translationTableFromAngleToCharacter[5]).toString();
  }

  /**
   * @param stepSizeForDiscreteAngleTransformation the step size
   */
  public OptimizedStructureToEtaThetaCharacterTransformer() {

  }

  /**
   *
   *
   *
   * @param struc the input structure in biojava format
   * @return the converted phi psi angles as strings...
   */
  public final ArrayList<String> getAngleSequences(final Structure struc) {

    ArrayList<String> sequences = new ArrayList<String>();

    int numberOfModels = struc.nrModels();

    for (int currentModel = 0;
            currentModel < numberOfModels;
            currentModel++) {

      List<Chain> chains = struc.getModel(currentModel);

      int numberOfChains = chains.size();

      for (int currentChain = 0;
              currentChain < numberOfChains;
              currentChain++) {

        List<Group> tempGroups
                = chains.get(currentChain).getAtomGroups();

        String thisTranslatedEtaTheta = getStringFromResidues(tempGroups);

        sequences.add(thisTranslatedEtaTheta);

      }
    }

    return sequences;
  }

  /**
   * gets a group of aminos (== chain), calculates the phi psi and transforms
   * the phi psi into the characters needed for hashing / indexing...
   *
   * @param aminos the group of aminos
   * @return string with transformed characters
   */
  public final String getStringFromResidues(final List<Group> nucleotidesAndSimilarMolecules) {

    // for the return...
    StringBuffer translatedAngleSeq = new StringBuffer();

    // expected lengt
    int nucleotideLength = nucleotidesAndSimilarMolecules.size();

    translatedAngleSeq.ensureCapacity(nucleotideLength);

    //first amino
    HetatomImpl a;
    //second amino
    HetatomImpl b;
    //third amino
    HetatomImpl c;

    double eta;
    double theta;

    for (int i = 0; i < nucleotideLength; i++) {

      eta = 360d;
      theta = 360d;

      ///check if the other two aminos exist:
      if ((i > 0) && (i < nucleotideLength - 1)) {

        a = (HetatomImpl) nucleotidesAndSimilarMolecules.get(i - 1);

        b = (HetatomImpl) nucleotidesAndSimilarMolecules.get(i);

        c = (HetatomImpl) nucleotidesAndSimilarMolecules.get(i + 1);

        try {
          eta = Utility.getEta(a, b, c);

          theta = Utility.getTheta(b, c);

        } catch (StructureException e) {
          //e.printStackTrace();
        }

      }

      if ((eta == 360) || (theta == 360)) {
            	//translatedAngleSeq.append("H");

        translatedAngleSeq.append(translationTableFromAngleToCharacter[5]);
            	//translatedAngleSeq.append("x");

      } else {

        int thisCombinationsPosition
                = angleToPositionTranslator(eta, theta);

        translatedAngleSeq.append(
                positionToCharacterTranslator(
                        thisCombinationsPosition));
      }

    }

    return translatedAngleSeq.toString();
  }

  /**
   * Transform a angle into a character. Angle is from -180 to +180
   *
   *
   * @param angle angle to convert
   * @return the char that represents this angle
   */
  public final int angleToPositionTranslator(
          final double eta,
          final double theta) {

		////////////////////////////////////////////////////////////////////////
    // Here comes the magic:
    ////////////////////////////////////////////////////////////////////////
    // big cluster in the corners
    if (((eta < -130) || (eta > 100))
            && ((theta < -50) || (theta > 50))) {

      return 0;

      //cluster upper middle left	
    } else if (((eta < 100) || (eta > 30)
            && (theta > 100))) {
      // alpha helices
      return 1;

      // lower middle
    } else if (((eta > 0) || (eta < 70))
            && ((theta < -90) || (theta > -170))) {
      // left handed helices
      return 2;

      // 4 smaller cluster lower middle left
    } else if (((eta > -100) || (eta < -20))
            && ((theta < -110) || (theta > -170))) {
      // left handed helices
      return 3;

    } else {
      // an outsider
      return 4;

    }

  }

  /**
   * Transform a angle into a character. Angle is from -180 to +180
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
