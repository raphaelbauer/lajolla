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
import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.utilities.RecursiveFileCollector;
import com.raphaelbauer.lajolla.utilities.SwissKnife;
import com.raphaelbauer.lajolla.utilities.Utility;

public class PDBProteinTranslator implements IFileToStringTranslator {

  private boolean onlyParseFirstModelOfPdbFile;

  private IResidueToStringTransformer iResidueToStringTransformer;

  private IScoringFunction scoringFunction;

  private IChainGroupFilter chainGroupFilter = new AminoAcidsAndDerivatesFilter();

  private INGramTo3DTranslator ngramTo3DTranslator;

  public PDBProteinTranslator(
          IResidueToStringTransformer iResidueToStringTransformer,
          boolean onlyParseFirstModel,
          IScoringFunction scoringFunction,
          INGramTo3DTranslator ngramTo3DTranslator) {

    this.onlyParseFirstModelOfPdbFile = onlyParseFirstModel;

    this.iResidueToStringTransformer = iResidueToStringTransformer;

    this.scoringFunction = scoringFunction;

    this.ngramTo3DTranslator = ngramTo3DTranslator;
  }

  /**
   * Returns the filter that determines which hetero group in a pdb file is like
   * an amino acid and which one not
   *
   * => more precise than biojava implementation, because it also recognizes
   * derivates of amino acids
   */
  public IChainGroupFilter getChainGroupFilter() {
    return chainGroupFilter;

  }

  public boolean isOnlyParseFirstModel() {
    return onlyParseFirstModelOfPdbFile;
  }

  @Override
  public SequenceDB getSequencesRecursivelyFromDirOrFile(
          String rootDirectorOrFile,
          int ngramLength) {

    SequenceDB sequenceDB
            = new SequenceDB(
                    ngramLength,
                    scoringFunction,
                    iResidueToStringTransformer,
                    ngramTo3DTranslator);

    File rootDirFileTemp = new File(rootDirectorOrFile);

    List<String> filesToLoadIntoSequenceDB
            = new ArrayList<>();

    if (rootDirFileTemp.isFile()) {

      filesToLoadIntoSequenceDB.add(rootDirectorOrFile);

    } else {
      //collect all files recursively:
      RecursiveFileCollector recursiveFileCollector
              = new RecursiveFileCollector();

      recursiveFileCollector.collectFilesRecursively(rootDirectorOrFile);

      filesToLoadIntoSequenceDB
              = recursiveFileCollector.getArrayListWithFiles();
    }

    int numberOfPDBFiles = filesToLoadIntoSequenceDB.size();

    for (int filesVectorID = 0;
            filesVectorID < numberOfPDBFiles;
            filesVectorID++) {

      Structure structure = SwissKnife.loadPDBFile(
              filesToLoadIntoSequenceDB.get(filesVectorID));

      //sometimes structures are null (eg: pdb1c0q.ent)
      //then skip it:
      if (structure != null) {

        int numberOfModelsToParse;

        if (onlyParseFirstModelOfPdbFile) {
          numberOfModelsToParse = 1;
        } else {
          numberOfModelsToParse = structure.nrModels();
        }

        for (int modelNumber = 0; modelNumber < numberOfModelsToParse; modelNumber++) {

          List<Chain> chains = structure.getModel(modelNumber);

          for (Chain chain : chains) {

            List<Group> allAminoLikeGroups
                    = chainGroupFilter.filter(chain).getAtomGroups();
            String angleSequence
                    = iResidueToStringTransformer.getStringFromResidues(
                            allAminoLikeGroups);

            if (Utility.getNumberOfDifferentCharsInThisString(angleSequence) > 1) {

              // Add to sequenceDB
              PDBInfo pdbInfo = new PDBInfo(
                      new File(filesToLoadIntoSequenceDB.get(filesVectorID)).getAbsolutePath(),
                      modelNumber,
                      chain.getChainID());

              sequenceDB.addNGramWithPDBInfoToSequenceDB(angleSequence, pdbInfo);
            }

          }
        }
      }
    }

    return sequenceDB;
  }

}
