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
//public class ScoreAccordingToScoringAtomDistanceFastNotIdealAndBasedOnTMSCORE implements IScoringFunction{
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
//			String unused1,
//			String unused2,
//			int queryPosition_unused,
//			int targetPosition_unused,
//			int nGramLength_unused,
//			String atomToUseForRefinement,
//			double advancedScoringRadius,
//			IResidueToStringTransformer residueToStringTransformer) {
//		
//		
//		
//		
//		
//		//always score the smaller chain against the bigger chain
//		
//		List<Atom> chainSmaller;
//		
//		List<Atom> chainBigger;
//		
//		if (chain1_original.size() > chain2_original.size()) {
//			chainBigger = chain1_original;
//			chainSmaller = chain2_original;
//			 
//		} else {
//			chainBigger = chain2_original;
//			chainSmaller = chain1_original;
//		}
//		
//		
//		int numberOfExpectedMatchingAtoms = chainSmaller.size();
//		
//		
//		
//		double RMSDAdder = 0d;
//		
//		double tmScoreAdder = 0d;
//		
//
//		//double SCORE;
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
//						//System.out.println(chainSmallerAtomsUsedForRefinement.get(i).getPDBserial() + "\n " 
//						//		+ chainBiggerAtomsUsedForRefinement.get(j).getPDBserial() 
//						//		+ "\n " + distance);
//						
//						//System.out.println(distance);
//						
//						
//						tmScoreAdder += TMScoreHelper.calculateTermOfSum(numberOfExpectedMatchingAtoms, distance);
//						
//						RMSDAdder += Math.pow(distance, 2);
//						
//						
//
//						foundAtoms++;
//						break;
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
//		//SCORE = ((double)foundAtoms / chainSmaller.size());
//		
//		
//		
//		double rmsd = Math.sqrt(RMSDAdder / (double) numberOfExpectedMatchingAtoms);
//
//		
//		
//		ScoreContainer scoreContainer = new ScoreContainer(
//				TMScoreHelper.calculateWholeTMScore(numberOfExpectedMatchingAtoms, tmScoreAdder),
//				rmsd);
//				
//		return scoreContainer;
//
//	}
//	
//
//}
//
//
//
//
//
//
//
//
