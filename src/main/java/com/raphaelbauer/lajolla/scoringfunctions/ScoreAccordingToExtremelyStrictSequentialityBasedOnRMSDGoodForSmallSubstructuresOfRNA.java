package com.raphaelbauer.lajolla.scoringfunctions;

//package ra.lajolla.scoringfunctions;
//
//import java.util.List;
//
//import org.biojava.bio.structure.Atom;
//import org.biojava.bio.structure.Calc;
//import org.biojava.bio.structure.StructureException;
//
//import ra.lajolla.container.ScoreContainer;
//import ra.lajolla.transformation.IResidueToStringTransformer;
//
//
//
///**
// * This scoring is:
// * - very fast
// * - scores according to n-gram similarity
// * - extends the ngram to the left and to the right and checks everything
// * 
// * => DOWNSIDE:
// * - is sequential what is good if you are searching for RNAs, but
// *   it does not find non-sequential homologues as the scoring does not
// *   work in that case...
// * 
// * @author ra
// *
// */
//public class ScoreAccordingToExtremelyStrictSequentialityBasedOnRMSDGoodForSmallSubstructuresOfRNA implements IScoringFunction{
//
//	
//	/**
//	 * 
//	 * @param chain1
//	 * @param chain2
//	 * @return
//	 * @throws StructureException
//	 */
//	public ScoreContainer scoreRefindeDoWhateveryouwantButGiveMeAScoreAndAnAlignment(
//			List<Atom> chain1,
//			List<Atom> chain2, 
//			String chain1NGram,
//			String chain2NGram,
//			int chain1NGramStartPosition,
//			int chain2NGramStartPosition,
//			int nGramLength,
//			String atomToUseForRefinement,
//			double advancedScoringRadius,
//			IResidueToStringTransformer residueToStringTransformer) {
//		
//		
//		
//		int numberOfExpectedMatchingNGrams = Math.min(chain1NGram.length(), chain2NGram.length());
//		//System.out.println("num of matching n-grams: " + numberOfExpectedMatchingNGrams);
//		
//		int numberOfMatchingNGrams = 0;
//		
//		double rmsdAdditionVariable = 0d;
//		
//		double tmScoreSumAdder = 0d;
//		
//		
//		
//		//   HHHH
//		//iabewbfusabfdsa
//		//zubdsaufbzusafuasubf
//		//        HHHH
//		
//		
//		
//		/////////////////////////////////////
//		// check left of n-gram		
//		int chain1_i = chain1NGramStartPosition -1;
//		int chain2_i = chain2NGramStartPosition -1;
//				
//		while (true) {
//			
//			//System.out.println(tmScoreSumAdder);
//
//			
//			if (chain1_i > -1 && chain2_i > -1) {
//				
//				//numberOfExpectedMatchingNGrams++;
//				
//				
//				//System.out.println(" position: chain1 " + chain1_i + " - chain2 " + chain2_i);
//				///System.out.println(" left: " +chain1NGram.charAt(chain1_i) + " - " + chain2NGram.charAt(chain2_i));
//				
//				
//				try {
//					double distance = Calc.getDistance(chain1.get(chain1_i), chain2.get(chain2_i));
//					rmsdAdditionVariable += Math.pow(distance, 2);
//					
//					
//					tmScoreSumAdder += TMScoreHelper.calculateTermOfSum(numberOfMatchingNGrams, distance);
//					
//					
//				} catch (StructureException e) {
//					// TODO Auto-generated catch block
//					//e.printStackTrace();
//				}
//				
//				if (chain1NGram.charAt(chain1_i) == chain2NGram.charAt(chain2_i)) {
//					
//					numberOfMatchingNGrams++;
//					
//				} //else break;
//				
//				
//			} else break;
//			
//			
//			chain1_i = chain1_i - 1;
//			chain2_i = chain2_i - 1;
//			
//	
//			
//		}
//		
//		
//		
//		
//		//////////////////////////////////////
//		//check right (including n-gram)
//		chain1_i = chain1NGramStartPosition;
//		chain2_i = chain2NGramStartPosition;
//		
//		//boolean runLoop = true;
//				
//		while (true) {
//
//			
//			if (chain1_i < chain1NGram.length() && chain2_i < chain2NGram.length()) {
//				
//				//System.out.println(" position: chain1 " + chain1_i + " - chain2 " + chain2_i);
//				//System.out.println(" right, incl ngram: " + chain1NGram.charAt(chain1_i) + " - " + chain2NGram.charAt(chain2_i));
//				
//				
//				//numberOfExpectedMatchingNGrams++;
//				
//				
//				try {
//					
//					
//					double distance = Calc.getDistance(chain1.get(chain1_i), chain2.get(chain2_i));
//					
//					rmsdAdditionVariable += Math.pow(distance, 2);
//					
//
//					tmScoreSumAdder += TMScoreHelper.calculateTermOfSum(numberOfMatchingNGrams, distance);
//					
//					
//				} catch (StructureException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				
//				if (chain1NGram.charAt(chain1_i) == chain2NGram.charAt(chain2_i)) {
//					
//					numberOfMatchingNGrams++;
//				}
//					
//			 //else {
//				//System.out.println("braking");
//					
//				//break;
//					
//			//}
//		} else {
//			break;
//		}
//			
//			
//
//			chain1_i = chain1_i + 1;
//			chain2_i = chain2_i + 1;
//				
//				
//				
//		}
//		
//	
//			
//		
//		
//		
//		//System.out.println("found:   " + numberOfMatchingNGrams);
//		
//		
//		//System.out.println("all matching bla:   " + numberOfAllNGrams);
//		
//		//System.out.println("ngramlength:   " + nGramLength);
//		
//		
//
//		double rmsd = Math.sqrt(rmsdAdditionVariable / (double) numberOfExpectedMatchingNGrams);
//
//		//System.out.println(tmScoreSumAdder);
//
//		
//		ScoreContainer scoreContainer 
//		= new ScoreContainer(TMScoreHelper.calculateWholeTMScore(numberOfExpectedMatchingNGrams, tmScoreSumAdder),
//		rmsd);
//		
//		//System.out.println ("tmscore: " + scoreContainer.getRMSD());
//		
//		return scoreContainer;
//		
//		
//	}
//	
//	
//	
//	
//
//}
