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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ra.lajolla.utilities.SystemOutUtils;

public class Main {

  public static void main(String[] args) {

    Properties properties = new java.util.Properties();

    try {
      String sConfigFile = "all.properties";

      InputStream in = Main.class.getClassLoader().getResourceAsStream(
              sConfigFile);

      properties.load(in);
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    SystemOutUtils.showSplashScreen();

    System.out.println(
            "\nBling Bling - cool to see you around.\n\n"
            + "LaJolla is a software package that uses an efficient technique\n"
            + "to generate alignments for Proteins and for RNAs. \n\n"
            + "Try the follwing: \n\n"
            + "1. alignment of protein structures: \n"
            + "   type in: java -cp lajolla.jar PRO -h\n"
            + "2. alignment of RNAs via suite codes: \n"
            + "   type in: java -cp lajolla.jar RNASuite -h\n"
            + "3. alignment of RNAs via eta theta dihedral angles: \n"
            + "   type in: java -cp lajolla.jar RNAEtaTheta -h\n"
            + "\n"
            + "\n"
            + "More infos: http://lajolla.sf.net");

  }

}
