import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.raphaelbauer.lajolla.utilities.SystemOutUtils;

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
            + "   type in: java -cp lajolla.jar RNA -h\n"
            + "\n"
            + "\n"
            + "More infos: http://lajolla.sf.net");

  }

}
