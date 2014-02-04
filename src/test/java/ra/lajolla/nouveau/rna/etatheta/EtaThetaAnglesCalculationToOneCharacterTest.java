//package ra.lajolla.nouveau.rna.etatheta;
//
//import java.util.ArrayList;
//
//import junit.framework.TestCase;
//
//import org.biojava.bio.structure.Structure;
//
//import ra.lajolla.transformation.rna.etatheta.StructureToEtaThetaCharacterTransformer;
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
//public class EtaThetaAnglesCalculationToOneCharacterTest extends TestCase {	
//	
//	
//	public void testAngle5() {
//		
//		
//		//read in pdb structure
//		final String smallPDB = "src/test/resources/ra.lajolla.transformation.rna.etatheta/simple_rna_chain.pdb";
//		
//
//		Structure struc = SwissKnife.loadPDBFile(smallPDB);
//		
//		
//		//transform to phi psi and to string
//		int step = 90;
//
//		StructureToEtaThetaCharacterTransformer angleSequences 
//			= new StructureToEtaThetaCharacterTransformer(step);
//		
//		
//		ArrayList<String> result = angleSequences.getAngleSequences(struc);
//		
//		//check string for correctness
//		
//		//System.out.println(result.get(0));
//		
//		if (!result.get(0).equals("\"\"# # # # # \"\"\"\"\" # # # ### # !#\"\"\"\"# # # # # # # # \"\"\"\"\" # \"\"\"\"# # #    !!  # \"! # # # \"\"\"\"#   \"  !# # # # # # # # # # # # #    \"\"\"")) {
//			fail();
//		}
//		
//	
//	}
//	
//	
//	
//
//	
//}
