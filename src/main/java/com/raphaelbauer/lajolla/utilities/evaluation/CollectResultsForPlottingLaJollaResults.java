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
package com.raphaelbauer.lajolla.utilities.evaluation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


public class CollectResultsForPlottingLaJollaResults {
	

	
	public static void main(String [] a) throws Exception {
		
		System.out.println("arg1: pathtodirwithresults");
		System.out.println("arg2: completePathtoCathListFile");
		System.out.println("arg3: where to write out results");
		
		
		for (String inputS : a) {
			
			System.out.println(a);
			
		}
		
		
		String dirWithResults = a[0];//"/media/truecrypt1/benchmarking/results/CathH_30_lajolla/";			
		
		String completePathToCathListFile = a[1];//= "/media/truecrypt1/benchmarking/datasets/CathDomainList.S35";
		
		String outputDirWithTxtFileToResults = a[2];//;= "/home/ra/workspace_work/lajolla2-royal-flush/src/main/python/protein_figures/l30.txt";
		
		
		////////////////////////////////////////////////////////////////////////
		// 1. read in mapping file
		HashMap<String, String> fileToCATHMapping;
		
		
		
		
		fileToCATHMapping = getMappingFileNamesToCATH(completePathToCathListFile);
		
		
		
		
		
		

		//get single elements => not counting for evalation:
		//HashSet<String> thisFileCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation = new HashSet<String>();
		
		//gucken ob:
		// für jedes file im result dir (2178):
		// sammeln wie oft die parents darin vorkommen
		// => dann die liste mit den eglibilbe neu machenb
		
		
		
		
		
		
		
		

		
		
		
		//gucken ob:
		// für jedes file im result dir (2178):
		// sammeln wie oft die parents darin vorkommen
		// => dann die liste mit den eglibilbe neu machenb
		
		
		//for each dir
		
		

		HashMap<String, Integer> numberOfOccurrencesOfThisCATCombination
			= new HashMap<String, Integer>();
		
		
		String [] filez = new File(dirWithResults).list();
	
		for (String file : filez) {
				
			if (new File(dirWithResults + file).isDirectory()) {	
			
				String queryName = file.split("-")[0];
				
				String thisQueryCATCombi = fileToCATHMapping.get(queryName);
				
				if (numberOfOccurrencesOfThisCATCombination.containsKey(thisQueryCATCombi)) {
					
					Integer thisNumber = numberOfOccurrencesOfThisCATCombination.get(thisQueryCATCombi);
					
					thisNumber = thisNumber + 1 ;
					
					numberOfOccurrencesOfThisCATCombination.put(thisQueryCATCombi, thisNumber);
					
					
				
				} else {
					
					Integer thisNumber = 1;
					
					numberOfOccurrencesOfThisCATCombination.put(thisQueryCATCombi, thisNumber);
					
				
					}
				}
			
				
				
				
		
		}
				
				
				
		
		
		
		//get single elements => not counting for evalation:
		HashSet<String> thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation = new HashSet<String>();

		for (Entry<String, Integer> thisEntry : numberOfOccurrencesOfThisCATCombination.entrySet()) {
			
			if (thisEntry.getValue() == 1) {				
				//System.out.println("single: " + thisEntry.getKey());
				thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation.add(thisEntry.getKey());
				
			} else {
				//System.out.println("not single: " + thisEntry.getKey() + " occurences: " + thisEntry.getValue());
				
			}
			
			
		}
		
		System.out.println("number if singular elements is: " + thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation.size());

		
		int numberOfFilesThatCanBeFoundTheoretically = (new File(dirWithResults).list().length - thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation.size());
		
		
		
		System.out.println(" Number of files that can be found in principle: " +
				numberOfFilesThatCanBeFoundTheoretically);
		
		
		////////////////////////////////////////////////////////////////////////
		// end setup
		
		

		System.out.println("number of single CAT combinations here: " 
				+ thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation.toArray().length);
		
		System.out.println("numberOfFiles: " + fileToCATHMapping.size());

		
		
		
		
		
		

		HashMap<String, ArrayList<ResultEntry>> queryToResultingEntriesMap 
			= generateResultsForLaJollaDir(
					dirWithResults,
					thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation,
					fileToCATHMapping);
		
		
			
		
		
		
		////////////////////////////////////////////////////////////////////////
		// generate threshold steps:...
		
		double thresholdstep = 0.05;
		
		ArrayList<Double> thresholdsteps = new ArrayList<Double>();


		for (double i = 0d; i <= 1.01d; i = i + thresholdstep) {
			thresholdsteps.add(i);
			
			
		}
		
		
		//write out results:
		
		FileWriter fw = new FileWriter(
				new File(outputDirWithTxtFileToResults));

		
		for (double threshold : thresholdsteps) {
			
			
			int [] resultOfThredhold 
				= evaluateResults (
						fileToCATHMapping,
						queryToResultingEntriesMap,
						thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation, 
						1,
						threshold,
						new TheBiggerTheBetterScoreComparator());

			System.out.println("TOP1: " + threshold + " results: " + resultOfThredhold[0] + " correct: " + resultOfThredhold[1]);
			
			
			double top1 = ((double)resultOfThredhold[1] / (double)resultOfThredhold[0]);
			
			resultOfThredhold 
				= evaluateResults (
						fileToCATHMapping,
						queryToResultingEntriesMap,
						thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation, 
						10,
						threshold,
						new TheBiggerTheBetterScoreComparator());
			
			System.out.println("TOP10: " + threshold + " results: " + resultOfThredhold[0] + " correct: " + resultOfThredhold[1]);
			double top5 = ((double)resultOfThredhold[1] / (double)resultOfThredhold[0]);
			
	
			
			// coverage: numberofresults / allresults
			// zuverlsigfkeit: numofcorrectresults / numof results
			
			fw.write(threshold +"\t" + ((double)resultOfThredhold[0]/ (double)numberOfFilesThatCanBeFoundTheoretically) + "\t" + top1 + "\t" + top5 + "\t" + "1" + "\n");
			
		}
		
		


		
		fw.close();
		
		

		
		
		
		
		
				
	}
	
	
	
	
	
	
	
	
	
	
	
	static public HashMap<String, ArrayList<ResultEntry>> generateResultsForLaJollaDir(
			String dirWithResults,
			HashSet<String> thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation,
			HashMap<String, String> fileToCATHMapping) {

		HashMap<String, ArrayList<ResultEntry>> queryToResultingEntriesMap 
			= new HashMap<String, ArrayList<ResultEntry>>();
		
		HashSet<String> nonIdenticalMatchingDirsSet = new HashSet<String>();
		
		//for each dir
		String [] filez = new File(dirWithResults).list();
		System.out.println("num of checked files with lajolla: "  + filez.length);
		
		//int directoryThatWasGenerated = 0;
		//int directoryWithResult = 0;

		
		
		for (String file : filez) {
			//System.out.println(file);
				
			if (new File(dirWithResults + file).isDirectory()) {
				
				String [] filezInsideResultDir 
					= new File(dirWithResults + file).list();
				
				//directoryThatWasGenerated++;
				
				
				//if (filezInsideResultDir.length > 0) {
					
					//directoryWithResult++;
					
				//} else { 
					
				//	System.out.println("no results: " + file );
					
				//}
				
				
				String queryName = file.split("-")[0];
				
				
				if (thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation.contains(fileToCATHMapping.get(queryName))) {					
					// nix... this cannot be found...
				} else {
				
								
				for (String resultFile : filezInsideResultDir) {
					
					
					//System.out.println("resFile: " + resultFile);
					
					if (resultFile.startsWith("t-")) {
						
						//t-3proC01-m-0-c-C-RS-1.73-TM-0.27-L1-91-L2-79-LAL-30-0.pdb
						
						String first = resultFile.split("TM-")[1];
						String foundScore = first.split("-")[0];
						
						//System.out.println("score: " + score);
						
						
						String namet = resultFile.split("t-")[1];
						String foundName = namet.split("-")[0];
						//System.out.println("name: " + name);
						
						
						//do not collect identical results:
						if (queryName.equals(foundName)) {
							
							//no equal matches please...
							
							//continue;
							
						} else if (thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation.contains(fileToCATHMapping.get(foundName))) {
							//do not collect matches of entries were we have no parent CAT class
							
						} else {
						
						
						
							nonIdenticalMatchingDirsSet.add(queryName);
							

							ResultEntry re = new ResultEntry();
							
							re.fileName = foundName;
							re.score = Double.parseDouble(foundScore);
							
							
							
						
							if (queryToResultingEntriesMap.containsKey(queryName)) {
						
								queryToResultingEntriesMap.get(queryName).add(re);
							
							
							} else {
							
								ArrayList<ResultEntry> tempList 
									=  new ArrayList<ResultEntry>();
							
								tempList.add(re);
							

								queryToResultingEntriesMap.put(queryName, tempList);//
							
							
							}
						
						}
						
						
					}
					}
					
				}
				
			}
			
			
			
		}		
		
		
	
		
		

		
		
		
	return queryToResultingEntriesMap;
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static int[] evaluateResults (
			HashMap<String, String> fileToCATHMapping,
			HashMap<String, ArrayList<ResultEntry>> result,
			HashSet<String> hasOnlyOneRepresentativeInUpperCATLevelNotSuitableForValidation,
			int topXToCountAsMatchingThingy,
			double cutoffThreshold,
			Comparator<ResultEntry> sortResultsBy) {
	
		////////////////////////////////////////////////////////////////////////
		// 3. compare and score and generate results :)	
		int allResults = 0;
		int correctResults = 0;
	
		//System.out.println("result entry set: " + result.entrySet().size());
	
	
	for (Map.Entry<String, ArrayList<ResultEntry>> entry : result.entrySet()) {
		
		
		//System.out.println(fileToCATHMapping.get(entry.getKey()));
		

		//System.out.println(fileToCATHMapping.get(entry.getKey()));
		
		if (!hasOnlyOneRepresentativeInUpperCATLevelNotSuitableForValidation.contains(
				fileToCATHMapping.get(entry.getKey()))) {
			

			ArrayList<ResultEntry> resultListOfThisQuery = entry.getValue();
		
 			Collections.sort(resultListOfThisQuery, sortResultsBy);
		
		
			boolean alreadyCountedBecauseOfThreshold = false;
			
		
		for (int i = 0; 
			(i < topXToCountAsMatchingThingy)
			&& (i < resultListOfThisQuery.size());
			i++) {
			
			ResultEntry singleResultEntry = resultListOfThisQuery.get(i);
			
			
			if (singleResultEntry.score >= cutoffThreshold) {
				
				if (!alreadyCountedBecauseOfThreshold) {

					allResults++;
					alreadyCountedBecauseOfThreshold = true;
				}
				

			
				if (fileToCATHMapping.get(entry.getKey())
						.equals(fileToCATHMapping.get(
								singleResultEntry.fileName))) {
					
					
		
					correctResults++;
					
					break;
					//System.out.println("Correct");
				}
		
			}
			
		}
		
		
		
		}
		
		
		
		
		
		
		
	}
	
	
	int [] returnArray = new int[2];
	
	

	
	returnArray[0] = allResults;
	returnArray[1] = correctResults;
		
		
	    
	    return returnArray;
		
		
		
	}
	
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 * @return
	 */
	static public HashMap<String, String>  getMappingFileNamesToCATH(
			String completePathToCathListFile) 
		throws Exception {
		
		HashMap<String, String> fileToCATHMapping = new HashMap<String, String>();
		FileReader fis = new FileReader(completePathToCathListFile);

		
		
		
		
		
		
		
		BufferedReader bis = new BufferedReader(fis);
		String line = "";
		
		
		
		
		
		while ((line = bis.readLine()) != null) {
			
			String [] allEntries = line.split("\\s+");
			
			String fileName = allEntries[0];
			String thisCombo = allEntries[1] + "." +allEntries[2] + "."+ allEntries[3];
			
			fileToCATHMapping.put(fileName, thisCombo);
			
		
					

		}
		
		
		return fileToCATHMapping;
	}


	

	
	
	
	
	

}



