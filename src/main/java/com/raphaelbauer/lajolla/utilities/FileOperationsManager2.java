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

import com.raphaelbauer.lajolla.SequenceDB;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;

/**
 * Builds a {@link SequenceDB} index from PDB files on disk.
 *
 * <p>Earlier versions could also persist the index via Java serialization, but
 * nothing used that path (the index is always rebuilt in memory from the input
 * directory), so it was removed together with the brittle
 * {@code Serializable} contract on {@link SequenceDB}.
 */
public class FileOperationsManager2 {

  private FileOperationsManager2() {
    // utility class
  }

  /**
   * Builds a {@link SequenceDB} by recursively reading every structure file
   * under the given directory (or a single file).
   *
   * @param rootDirectory directory or file to read
   * @param thisTranslator translator that turns structures into n-gram sequences
   * @param ngramSize size of the n-gram window
   * @return the freshly built in-memory index
   */
  public static SequenceDB generateSequenceDBRecursivelyFromDirOrFile(
          String rootDirectory,
          IFileToStringTranslator thisTranslator,
          int ngramSize) {

    return thisTranslator.getSequencesRecursivelyFromDirOrFile(
            rootDirectory, ngramSize);
  }

}
