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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import com.raphaelbauer.lajolla.container.ResultContainer;

public class SortedBagWithResults {
	
	private HashMap<String, ArrayList<ResultEntry>> thisInternalContainer
		=	new HashMap<String, ArrayList<ResultEntry>>();
	
	int numberOfBestResultsToKeep;
	
	Comparator<Double> comparatorThatTellsWhatIsAGoodResults;
	
	
	
	public SortedBagWithResults(
			int numberOfBestResultsToKeep,
			Comparator<Double> comparatorThatTellsWhatIsAGoodResults) {
		
		this.numberOfBestResultsToKeep 
			= numberOfBestResultsToKeep;
		
		this.comparatorThatTellsWhatIsAGoodResults 
		= comparatorThatTellsWhatIsAGoodResults;
		
		
	}
	
	
	
	
	
	
	public void add(String fileName, ResultEntry re) {
		
		ArrayList<ResultEntry> arrayList = thisInternalContainer.get(fileName);
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	

}
