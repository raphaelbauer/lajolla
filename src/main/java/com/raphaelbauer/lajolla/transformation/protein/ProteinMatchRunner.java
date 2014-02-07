package com.raphaelbauer.lajolla.transformation.protein;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;

import com.raphaelbauer.lajolla.PDBInfo;
import com.raphaelbauer.lajolla.SequenceDB;
import com.raphaelbauer.lajolla.chaingroupfilter.AminoAcidsAndDerivatesFilter;
import com.raphaelbauer.lajolla.chaingroupfilter.IChainGroupFilter;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.utilities.FileOperationsManager2;
import com.raphaelbauer.lajolla.utilities.RecursiveFileCollector;
import com.raphaelbauer.lajolla.utilities.SwissKnife;
import com.raphaelbauer.lajolla.utilities.Utility;

public class ProteinMatchRunner {

  /**
   * well.. the runner that can be called from main... and doing everyting
   *
   * @param advancedNGramSize
   * @param advancedAngleDiscretion
   */
  public static void executeSearch(
          int advancedNGramSize,
          //int advancedAngleDiscretion,
          IFileToStringTranslator iFileToStringTranslator,
          IResidueToStringTransformer iResidueToStringTransformer,
          String pathToTargetDirOrFile,
          String pathToQueryDirOrFile,
          String outputFilePath,
          boolean dealOnlyWithFirstModel,
          double minRefinedScore,
          int numberOfResultsToGenerate) {

    long startreadin = System.currentTimeMillis();

		// the filter that determines which hetero atom
    // is an amino acid derivate and which one not...
    IChainGroupFilter chainGroupFilter
            = iFileToStringTranslator.getChainGroupFilter();

    SequenceDB sequenceDB = FileOperationsManager2
            .generateSequenceDBRecursivelyFromDirOrFile(
                    pathToTargetDirOrFile,
                    iFileToStringTranslator,
                    advancedNGramSize);

		//System.out.println("generating db took: " + (System.currentTimeMillis() - startreadin));
		// STEP 2: get all target files:
    // collect all files recursively:
    ArrayList<String> allQueryFiles = new ArrayList<String>();

    if (new File(pathToQueryDirOrFile).isFile()) {

      allQueryFiles.add(pathToQueryDirOrFile);

    } else {

      RecursiveFileCollector recursiveFileCollector = new RecursiveFileCollector();

      recursiveFileCollector
              .collectFilesRecursively(pathToQueryDirOrFile);

      allQueryFiles = recursiveFileCollector.getArrayListWithFiles();
    }

		// PDBProteinTranslator pdbProteinTranslator2
    // = new PDBProteinTranslator(angleDiscretion);
		// STEP 3: getRankingsForTheBest10...
    int counter = 0;

    for (String currentFile : allQueryFiles) {

      Structure struc = SwissKnife.loadPDBFile(currentFile);

			//System.out.println("struc: " + struc);
			// sometimes structures are null (eg: pdb1c0q.ent)
      // then skip it:
      if (struc != null) {

        int nrModels = struc.nrModels();

        if (dealOnlyWithFirstModel) {
          nrModels = 1;

        }

				//StructureToPhiPsiToTwoCharactersTransformer anglesCalc 
        //	= new StructureToPhiPsiToTwoCharactersTransformer(
        //		advancedAngleDiscretion);
        for (int modelNr = 0; modelNr < nrModels; modelNr++) {

          List<Chain> chains = struc.getModel(modelNr);

          int nrChains = chains.size();

          for (int chainNr = 0; chainNr < nrChains; chainNr++) {

            List<Group> allAminoLikeGroups
                    = chainGroupFilter.filter(
                            chains.get(
                                    chainNr)).getAtomGroups();

            String angleSequence
                    = iResidueToStringTransformer.getStringFromResidues(
                            allAminoLikeGroups);

						//System.out.println("searching: " + angleSequence);
            if (Utility.getNumberOfDifferentCharsInThisString(angleSequence) > 1) {

              counter++;

							//System.out.println("searching number: " + counter);
							//System.out.println("pmatchrunner searching: " 
              //			+currentFile  
              //			+ " chain " 
              //			+  chains.get(chainNr).getName());
              sequenceDB.getExtendedRanking(angleSequence,
                      new PDBInfo(currentFile, modelNr, chains.get(chainNr).getName()),
                      outputFilePath
                      + new File(currentFile).getName()
                      + "-model-" + modelNr + "-chain-"
                      + chains.get(chainNr).getName() + "/",
                      //"amino",
                      "CA",
                      minRefinedScore,
                      numberOfResultsToGenerate,
                      chainGroupFilter);
            }
          }

        }
      }
    }

  }

}
