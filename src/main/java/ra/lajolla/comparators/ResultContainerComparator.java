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
 package ra.lajolla.comparators;

import java.util.Comparator;

import ra.lajolla.container.ResultContainer;


public class ResultContainerComparator implements Comparator<ResultContainer> {
	@Override
	public int compare(
			ResultContainer resultContainer1, 
			ResultContainer resultContainer2) {
		
		if (resultContainer1.getScoreContainer().getOverallSCORE() > resultContainer2.getScoreContainer().getOverallSCORE()) {
			
			return -1;
			
		} else if (resultContainer1.getScoreContainer().getOverallSCORE() < resultContainer2.getScoreContainer().getOverallSCORE()) {
			return 1;
			
		} else {
			return 0;
		}
	}
}

