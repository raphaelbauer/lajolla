package com.raphaelbauer.lajolla.comparators;

import java.util.Comparator;

public class MappingComparator implements Comparator<int[]> {
	@Override
	public int compare(int[] o1, int[] o2) {
		
		int result = o1[0] - o2[0];
		
		if (result > 0) {
			return -1;
		} else if (result < 0) {
			return 1;
		} else return 0;
	}

}
