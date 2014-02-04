//package ra.lajolla.transformation.protein;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.biojava.bio.structure.AminoAcid;
//import org.biojava.bio.structure.Calc;
//import org.biojava.bio.structure.Chain;
//import org.biojava.bio.structure.Group;
//import org.biojava.bio.structure.Structure;
//import org.biojava.bio.structure.StructureException;
//
//import ra.lajolla.transformation.IResidueToStringTransformer;
//
//
///**
// * AngleSequences gets a pdb file with a chain and converts it to
// * a string that can be indexed further on.
//
// * @author petmoo
// *
// */
//public class StructureToPhiPsiToOneCharacterTransformer 
//	implements IResidueToStringTransformer { 
//
//	
//	// 32 is the space... all characters upwards until 127 are usable
//	// the characters downwards are commands
//	final int startPositionOfCharactersToUseInTransformation = 34;
//
//	final int characterToUseWhenNothingFound = 33;
//	
//	private final int numberOfCharactersInStringCorrespondToOneResidue = 1;
//	
//	/** the step size. */
//	private int angleSizeUsedForDiscretization;
//	
//	/** the translation table. */
//	private char[] translationTableFromAngleToCharacter;
//
//	
//	
//	
//
//
//
//	/**
//	 * @param stepSizeForDiscreteAngleTransformation the step size
//	 */
//	public StructureToPhiPsiToOneCharacterTransformer(
//			final int stepSizeForDiscreteAngleTransformation) {
//		
//		
//		//FIXME =>. .. use other ascii table or so..
//		if (stepSizeForDiscreteAngleTransformation < 4) {
//			System.out.println(
//					"ERROR: stepSizeForDiscreteAngleTransformation <  not feasible with standard ASCII table...");
//			System.out.println("ask petmoo or ra to fix that ;)");
//			System.exit(10);
//		}
//		
//		
//		
//		//set member variable...
//		this.angleSizeUsedForDiscretization
//			= stepSizeForDiscreteAngleTransformation;
//		
//		// unterteilt 360 grad entsprechend dem winkelabstand in 
//		// bestimmte anzahl von kategorien 
//		// (bsp. step = 10 -> 36 kategorien)
//		
//		//number of discrete buckets where to store angles
//		int numberOfDiscreteSteps 
//			= 360 / this.angleSizeUsedForDiscretization;
//		
//		
//		
//		// => ...
//		translationTableFromAngleToCharacter 
//			= new char[(numberOfDiscreteSteps * numberOfDiscreteSteps) + 1];
//		
//
//					
//		for (int i = 0; i < (numberOfDiscreteSteps * numberOfDiscreteSteps); i++) {
//			
//			translationTableFromAngleToCharacter[i]
//			    = (char) (i + startPositionOfCharactersToUseInTransformation);
//		}
//		
//		
//		// set the last bucket to the first bucket...
//		// 360 degree are again O degree for instance...
//		// so they get the same character...:
//		translationTableFromAngleToCharacter[numberOfDiscreteSteps * numberOfDiscreteSteps] 
//		     = (char) (startPositionOfCharactersToUseInTransformation);
//		
//		
//		
//	}
//
//	
//	/**
//	 * 
//	 * 
//	 * 
//	 * @param struc the input structure in biojava format
//	 * @return the converted phi psi angles as strings...
//	 */
//	public final ArrayList<String> getAngleSequences(final Structure struc) {
//		
//		ArrayList<String> sequences = new ArrayList<String>();
//				
//		int numberOfModels = struc.nrModels();
//		
//		
//		for (int currentModel = 0; 
//				currentModel < numberOfModels; 
//				currentModel++) {
//			
//			
//			List<Chain> chains = struc.getModel(currentModel);
//			
//			int numberOfChains = chains.size();
//			
//			for (int currentChain = 0; 
//					currentChain < numberOfChains; 
//					currentChain++) {
//
//				
//				List <Group> tempGroups 
//					= chains.get(currentChain).getAtomGroups();
//				
//				String thisTranslatedPhiPsiAngles = getStringFromResidues(tempGroups);
//				
//
//				sequences.add(thisTranslatedPhiPsiAngles);
//								
//				
//			}
//		}
//		
//		return sequences;
//	}
//	
//
//	
//	/**
//	 * gets a group of aminos (== chain), calculates the phi psi and transforms
//	 * the phi psi into the characters needed for hashing / indexing...
//	 * 
//	 * @param aminos the group of aminos
//	 * @return string with transformed characters
//	 */
//	public final String getStringFromResidues(final List<Group> aminos) {
//		
//		//int temp_ok_counter = 0;
//		//int temp_wrong_counter = 0;
//		
//		// for the return...
//		StringBuffer translatedAngleSeq = new StringBuffer();
//        
//		// expected lengt
//		int aminoLength = aminos.size();
//		
//        translatedAngleSeq.ensureCapacity(aminoLength);
//		
//        //first amino
//		AminoAcid a;
//		//second amino
//		AminoAcid b;
//		//third amino
//		AminoAcid c;
//		
//        double phi;
//        double psi;
//        
//
//        for (int i = 0; i < aminoLength; i++) {
//        	
//            phi = -1d;
//        	psi = -1d; 
//            
//        	// amino acid in the middle...
//            b = (AminoAcid) aminos.get(i);
//            
//            
//            // does the a exist in that case?
//            if (i > 0) {
//            	a = (AminoAcid) aminos.get(i - 1);
//            	
//	            try {
//	            	phi = Calc.getPhi(a, b);
//	            	
//	            } catch (StructureException e) {
//	            	//e.printStackTrace();
//	            }
//            }
//            
//            
//            //does the c exist in that case?
//            if (i < aminoLength - 1) {
//            	
//                c = (AminoAcid) aminos.get(i + 1);
//                
//                try {
//                    psi = Calc.getPsi(b, c);
//                    
//                } catch (StructureException e) {
//                	//e.printStackTrace();
//                }
//            }
//            
//            
//            
//            
//            
//            if (phi==-1|| psi == -1) {
//            	//translatedAngleSeq.append("H");
//            	
//            	translatedAngleSeq.append((char) (characterToUseWhenNothingFound));
//            	//translatedAngleSeq.append("x");
//            	
//            	//temp_wrong_counter++;
//            } else {
//            	
//            	//temp_ok_counter++;
//            
//            
//            
//            
//            //for a discretion step of eg. 4 this looks like:
//            // => so a seqence in the length of the residue's length is generated
//            // and can be used for a translation...
//            //
//            // ^
//            // | 1  2  3  4
//            // | 5  6  7  8
//            // | 9  10 11 12
//            // | 13 14 15 16
//            // -------------->
//            
//            int thisCombinationsPosition 
//            	= angleToPositionTranslator(phi, psi);
//            
//            
//            translatedAngleSeq.append(
//            		positionToCharacterTranslator(
//            				thisCombinationsPosition));
//            }
//            
//        }
//        
//        //System.out.println("converting ok: " + temp_ok_counter);
//       // System.out.println("converting wrong: " + temp_wrong_counter);
//        
//		return translatedAngleSeq.toString();
//	}
//
//	
//	
//	
//	
//	/**
//	 * Transform a angle into a character.
//	 * Angle is from -180 to +180
//	 * 
//	 * 
//	 * @param angle angle to convert
//	 * @return the char that represents this angle
//	 */
//	public final int angleToPositionTranslator(
//			final double phi,
//			final double psi) {
//		
//		
//		
//		int positionAsDiscretStep1
//			= (int) Math.floor((phi + 180.0d)
//					/ angleSizeUsedForDiscretization);
//		
//		//System.out.println(phi);
//		//System.out.println(positionAsDiscretStep1);
//		int positionAsDiscretStep2 
//		= (int) Math.floor((psi + 180.0d)
//				/ angleSizeUsedForDiscretization);
//		
//		//System.out.println(psi);
//		//System.out.println(positionAsDiscretStep2);
//		return (positionAsDiscretStep1 * positionAsDiscretStep2);
//		//return translationTableFromAngleToCharacter[positionAsDiscretStep];
//	}
//	
//	
//	
//	
//	/**
//	 * Transform a angle into a character.
//	 * Angle is from -180 to +180
//	 * 
//	 * 
//	 * @param angle angle to convert
//	 * @return the char that represents this angle
//	 */
//	public final char positionToCharacterTranslator(
//			final int positionAsDiscretStep) {
//		
//		return translationTableFromAngleToCharacter[positionAsDiscretStep];
//	}
//	
//	
//	
//	
//	
//	
//	public int getNumberOfCharactersInStringCorrespondToOneResidue() {
//		return numberOfCharactersInStringCorrespondToOneResidue;
//	}
//
//	
//	
//}
