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

import java.util.Comparator;

public class TheBiggerTheBetterScoreComparator implements Comparator<ResultEntry> {
	
	
	public int compare(ResultEntry re1, ResultEntry re2) {
		
		return - Double.compare(re1.score, re2.score);
	
	}
	

	
}