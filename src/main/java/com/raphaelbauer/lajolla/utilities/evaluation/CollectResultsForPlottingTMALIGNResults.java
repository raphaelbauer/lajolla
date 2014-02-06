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
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.biojava.bio.structure.SVDSuperimposer;

public class CollectResultsForPlottingTMALIGNResults {

	public static void main(String [] a) throws Exception{
		
		
		
		System.out.println("arg1: pathtodirwithresults");
		System.out.println("arg2: completePathtoCathListFile");
		System.out.println("arg3: where to write out results");
		
		
		for (String inputS : a) {
			
			System.out.println(a);
			
		}
		
		
		String dirWithResults = a[0]; //"/media/truecrypt1/benchmarking/results/tm_align_against_cath_s35v320.H/";
		//= a[0];

		
		
		
		String completePathToCathListFile = a[1]; //= "/media/truecrypt1/benchmarking/datasets/CathDomainList.S35";
		
		String outputDirWithTxtFileToResults = a[2]; //=   "/home/ra/workspace_work/lajolla2-royal-flush/src/main/python/protein_figures/t30.txt";
		
		
		
		

		////////////////////////////////////////////////////////////////////////
		// 1. read in mapping file
		HashMap<String, String> fileToCATHMapping;
		

		fileToCATHMapping 
			= CollectResultsForPlottingLaJollaResults.getMappingFileNamesToCATH(
						completePathToCathListFile);
		
		

		HashMap<String, Integer> numberOfOccurrencesOfThisCATCombination
			= new HashMap<String, Integer>();
		
		
		String [] filez = new File(dirWithResults).list();
	
		for (String file : filez) {
				
			if (new File(dirWithResults + file).isDirectory()) {	
			
				String queryName = file;
				
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
		// 2. parse and collect results
		

		HashMap<String, ArrayList<ResultEntry>> queryToResultingEntriesMap = 
			
			generateResultsForTMAlignDir(
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
			
			fw.write(threshold +"\t" + ((double)resultOfThredhold[0]/(double)numberOfFilesThatCanBeFoundTheoretically) + "\t" + top1 + "\t" + top5 + "\t" + "1" + "\n");
			
		}
		
		


		
		fw.close();
		
		

		
		
		
		
		
				
				
	}
	
	
	

	
	
	
	static public HashMap<String, ArrayList<ResultEntry>> generateResultsForTMAlignDir(
			String dirWithResults,
			HashSet<String> thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation,
			HashMap<String, String> fileToCATHMapping)  
				throws Exception {
		
		
		HashMap<String, ArrayList<ResultEntry>> queryToResultingEntriesMap 
			= new HashMap<String, ArrayList<ResultEntry>>();
		
		HashSet<String> nonIdenticalMatchingDirsSet = new HashSet<String>();
		
		
		
		//for each dir
		String [] filez = new File(dirWithResults).list();
		System.out.println("num of checked files with tmalign: "  + filez.length);
		
		
		
		int temp_counter = 0;
		
		for (String queryFileName : filez) {
			System.out.println("generating: " + queryFileName + "  number: " + temp_counter++);
				
			if (new File(dirWithResults + queryFileName).isDirectory()) {
				
				
				if (thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation.contains(
						fileToCATHMapping.get(queryFileName))) {
					
					 //do nothing... this entry cannot be found...
					
					
					
					
				} else {
						
				
				
				//System.out.println("checking dir number: " + temp_counter++ + " of " + filez.length);
				
				String [] filezInsideResultDir = new File(dirWithResults + queryFileName).list();
				
		
								
				for (String resultFile : filezInsideResultDir) {
					
					String targetFileName = resultFile.split("_")[1].split("\\.")[0];
					
					
					
					
					//do not collect identical results:
					if (queryFileName.equals(targetFileName)) {
						
						//continue;
						
					} else if (thisCATOnlyHasOneRepresentativeAndIsNotSuitableForEvaluation.contains(
							fileToCATHMapping.get(targetFileName))) {
						
						// do nothing... this entry is not of importance and cannot be found
					
					
					} else {
						
						
						
						nonIdenticalMatchingDirsSet.add(queryFileName);
						
						
						//System.out.println(dirWithResults + queryFileName + "/" + resultFile);
						FileReader fis2 = new FileReader(dirWithResults + queryFileName + "/" + resultFile);

						
						BufferedReader bis2 = new BufferedReader(fis2);
						
						String line2 = "";
						
						
						while ((line2 = bis2.readLine()) != null) {
							
							
							if (line2.contains("TM-score=")) {
								
								
								String scoreuncomplete = line2.split("TM-score=")[1];
								String score = scoreuncomplete.split(",")[0];

								ResultEntry re = new ResultEntry();
								
								re.fileName = targetFileName;
								re.score = Double.parseDouble(score);
								
								
								
								if (queryToResultingEntriesMap.containsKey(queryFileName)) {
									
									queryToResultingEntriesMap.get(queryFileName).add(re);
								
								
								} else {
								
									ArrayList<ResultEntry> tempList 
										=  new ArrayList<ResultEntry>();
								
									tempList.add(re);
								

									queryToResultingEntriesMap.put(queryFileName, tempList);//
								
								
								}
								
								
							}
								
						
						
					}
						
						bis2.close();
						fis2.close();
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
	
	
	
		
		
	
	
	

}



