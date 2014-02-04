//package ra.lajolla.nouveau.phipsi;
//
//import java.util.ArrayList;
//
//import junit.framework.TestCase;
//
//import org.biojava.bio.structure.Structure;
//
//import ra.lajolla.transformation.protein.StructureToPhiPsiToOneCharacterTransformer;
//import ra.lajolla.utilities.SwissKnife;
//
//
///**
// * 
// * 
// * 
// * @author petmoo
// *
// */
//public class AngleCalculationTest extends TestCase {	
//	
//	
//public void testAngle90() {
//		
//		
//		//read in pdb structure
//		final String smallPDB = "src/test/resources/small.pdb";
//		
//
//		Structure struc = SwissKnife.loadPDBFile(smallPDB);
//		
//		
//		//transform to phi psi and to string
//		int step = 90;
//
//		StructureToPhiPsiToOneCharacterTransformer angleSequences 
//			= new StructureToPhiPsiToOneCharacterTransformer(step);
//		
//		
//		ArrayList<String> result = angleSequences.getAngleSequences(struc);
//		
//		//check string for correctness
//		
//		
//		
//		///System.out.println(result.get(0));
//		
//		if (!result.get(0).equals("!%\"%#\"!")) {
//			fail();
//		}
//		
//	
//	}
//
//
//	public void testAngle5() {
//		
//		
//		//read in pdb structure
//		final String smallPDB = "src/test/resources/small.pdb";
//		
//
//		Structure struc = SwissKnife.loadPDBFile(smallPDB);
//		
//		
//		//transform to phi psi and to string
//		int step = 5;
//
//		StructureToPhiPsiToOneCharacterTransformer angleSequences 
//			= new StructureToPhiPsiToOneCharacterTransformer(step);
//		
//		
//		ArrayList<String> result = angleSequences.getAngleSequences(struc);
//		
//		//check string for correctness
//		
//		
//		
//		//System.out.println(result.get(0));
//		
//		if (!result.get(0).equals("!ԢЀԈʊÐ!")) {
//			fail();
//		}
//		
//	
//	}
//	
//	
//	
//	public void testAngle10() {
//		
//		
//		//read in pdb structure
//		final String smallPDB = "src/test/resources/small.pdb";
//		
//
//		Structure struc = SwissKnife.loadPDBFile(smallPDB);
//		
//		
//		//transform to phi psi and to string
//		int step = 10;
//
//		StructureToPhiPsiToOneCharacterTransformer angleSequences = new StructureToPhiPsiToOneCharacterTransformer(step);
//		
//		
//		ArrayList<String> result = angleSequences.getAngleSequences(struc);
//		
//		//check string for correctness
//		
//		//System.out.println(result.get(0));
//		
//		if (!result.get(0).equals("!ŢĉŖ¼?!")) {
//			fail();
//		}
//		
//	
//	}
//	
//	
//	public void testAngle15() {
//		
//		//read in pdb structure
//		final String smallPDB = "src/test/resources/small.pdb";
//		
//
//		Structure struc = SwissKnife.loadPDBFile(smallPDB);
//		
//		
//		//transform to phi psi and to string
//		int step = 15;
//
//		StructureToPhiPsiToOneCharacterTransformer angleSequences = new StructureToPhiPsiToOneCharacterTransformer(step);
//		
//		
//		ArrayList<String> result = angleSequences.getAngleSequences(struc);
//		
//		//check string for correctness
//		
//		//System.out.println(result.get(0));
//		
//		if (!result.get(0).equals("! §a5!")) {
//			fail();
//		}
//		
//	}
//	
//	
//	
//	
//	
//	public void testAgainstInvalidPDBFile() {
//		
//		//read in pdb structure
//		final String onePDB = "src/test/resources/oneamino.pdb";
//		
//
//		Structure struc = SwissKnife.loadPDBFile(onePDB);
//		
//		
//		//transform to phi psi and to string
//		int step = 15;
//
//		StructureToPhiPsiToOneCharacterTransformer angleSequences = new StructureToPhiPsiToOneCharacterTransformer(step);
//		
//		
//		ArrayList<String> result = angleSequences.getAngleSequences(struc);
//		
//		//System.out.println(result.get(0));
//		//check string for correctness
//		
//		//=> ! means 0 ...
//		if (!result.get(0).equals("!")) {
//			fail();
//		}
//		
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}
