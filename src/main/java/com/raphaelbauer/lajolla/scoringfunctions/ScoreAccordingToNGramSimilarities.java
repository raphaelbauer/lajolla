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
//
//
//
//public class ScoreAccordingToNGramSimilarities implements IScoringFunction{
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
//			List<Atom> chain1_original,
//			List<Atom> chain2_original, 
//			String completeNGramSequenceChain1_original,
//			String completeNGramSequenceChain2_original,
//			int queryPosition_unused,
//			int targetPosition_unused,
//			int nGramLength_unused,
//			String atomToUseForRefinement,
//			double advancedScoringRadius,
//			int numberOfCharactersInStringCorrespondToOneResidue) {
//		
//		
//
//		
//		
//		
//		//always score the smaller chain against the bigger chain
//		
//		List<Atom> chainSmaller;
//		String chainSmallerNGramSequence;
//		
//		List<Atom> chainBigger;
//		String chainBiggerNGramSequence;
//		
//		if (chain1_original.size() > chain2_original.size()) {
//			chainBigger = chain1_original;
//			chainSmaller = chain2_original;
//			
//			chainBiggerNGramSequence = completeNGramSequenceChain1_original;
//			chainSmallerNGramSequence = completeNGramSequenceChain2_original;
//			 
//		} else {
//			chainBigger = chain2_original;
//			chainSmaller = chain1_original;
//			
//			chainBiggerNGramSequence = completeNGramSequenceChain2_original;
//			chainSmallerNGramSequence = completeNGramSequenceChain1_original;
//		}
//
//		
//		
//		////System.out.println(chainBiggerNGramSequence);
//		//System.out.println(chainSmallerNGramSequence);
//		double SCORE;
//
//		// int allAtomsToCheck = 0;
//		int foundAtoms = 0;
//
////		////////////////////////////////////////////////////////////////////////
////		// preliminary stuff: copy it into an array
////		////////////////////////////////////////////////////////////////////////
////		ArrayList<Atom> chainSmallerAtomsUsedForRefinement = new ArrayList<Atom>();
////
////		for (int i = 0; i < chainSmaller.getAtomLength(); i++) {
////
////			Group g = chainSmaller.getAtomGroup(i);
////
////			try {
////				chainSmallerAtomsUsedForRefinement.add(g
////						.getAtom(atomToUseForRefinement));
////			} catch (StructureException e) {
////				// TODO Auto-generated catch block
////				// e.printStackTrace();
////			}
////		}
////
////		
////		
////		ArrayList<Atom> chainBiggerAtomsUsedForRefinement = new ArrayList<Atom>();
////
////		for (int i = 0; i < chainBigger.getAtomLength(); i++) {
////
////			Group g = chainBigger.getAtomGroup(i);
////
////			try {
////				chainBiggerAtomsUsedForRefinement.add(g
////						.getAtom(atomToUseForRefinement));
////			} catch (StructureException e) {
////				// TODO Auto-generated catch block
////				// e.printStackTrace();
////			}
////		}
////		
//		
//		
//		
//		
//		
//		////////////////////////////////////////////////////////////////////////
//		// preliminary check: are chain1_original ok? and are chain2_original oke?
//		//
//		////////////////////////////////////////////////////////////////////////
//		if (chainBigger.size() 
//				!= chainBiggerNGramSequence.length()) {
//			 
//			
//			System.out.println("[ERROR] chainBiggerAtomsUsedForRefinement.size() != chainBiggerNGramSequence.length()");
//			System.out.println("[ERROR] chainBiggerAtomsUsedForRefinement == " + chainBigger.size());
//			System.out.println("[ERROR] chainBiggerNGramSequence.length() == " + chainBiggerNGramSequence.length());//.size() != completeNGramSequenceChain2_original.length");
//
//			System.out.println("[ERROR] MEDIC! Raphael should fix me - this should not happen");
//							
//			return new ScoreContainer(0d,0d);
//			
//		}
//		
//		if (chainSmaller.size() 
//				!= chainSmallerNGramSequence.length()) {
//			
//			System.out.println("[ERROR] chainSmallerAtomsUsedForRefinement.size() != chainSmallerNGramSequence.length()");
//			System.out.println("[ERROR] chainSmallerAtomsUsedForRefinement.size() == " + chainSmaller.size());
//			System.out.println("[ERROR] chainSmallerNGramSequence.length() == " + chainSmallerNGramSequence.length());//.size() != completeNGramSequenceChain2_original.length");
//
//			
//			System.out.println("[ERROR] MEDIC! Raphael should fix me - this should not happen");
//				
//			return new ScoreContainer(0d,0d);
//			
//		}
//		
//		
//		
//		
//
//		
//		
//		////////////////////////////////////////////////////////////////////////
//		// actual scoring:
//		for (int i = 0; i < chainSmaller.size(); i++) {
//
//			for (int j = 0; j < chainBigger.size(); j++) {
//
//				try {
//					double distance = Calc.getDistance(
//							chainSmaller.get(i),
//							chainBigger.get(j));
//
//					
//					
//					
//					if (distance < advancedScoringRadius) {
//						
//						//System.out.println("i is: " + i);
//						//System.out.println("numcharacters is: " + numberOfCharactersInStringCorrespondToOneResidue);
//						//System.out.println("length " +  chainSmallerNGramSequence.length());
//						//System.out.println("chainresidues is: " + chainSmallerAtomsUsedForRefinement.size());
//
//						String smallerOneAtThatPosition = chainSmallerNGramSequence.substring(
//							(i * numberOfCharactersInStringCorrespondToOneResidue),
//							(i * numberOfCharactersInStringCorrespondToOneResidue) + numberOfCharactersInStringCorrespondToOneResidue);
//							
//						
//						//System.out.println(smallerOneAtThatPosition);
//
//						String biggerOneAtThatPosition = chainBiggerNGramSequence.substring(
//							(j * numberOfCharactersInStringCorrespondToOneResidue),
//							(j * numberOfCharactersInStringCorrespondToOneResidue) + numberOfCharactersInStringCorrespondToOneResidue);
//						
//						//System.out.println(smallerOneAtThatPosition +" and "+ biggerOneAtThatPosition);
//						
//						
//						if (smallerOneAtThatPosition.equals(biggerOneAtThatPosition)) {
//						
//							//System.out.println("...similar");
//							foundAtoms++;
//							break;
//						}
//
//					}
//				} catch (StructureException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//
//		}
//
//		// System.out.println("found:   " + (foundAtoms / allAtomsToCheck));
//		
//		
//	
//		//score:
//		SCORE = ((double)foundAtoms / chainSmaller.size());
//		
//		
//		return new ScoreContainer(SCORE, Double.NaN);
//				
//
//	}
//}
