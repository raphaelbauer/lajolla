package com.raphaelbauer.lajolla.comparators;

import java.util.Comparator;

import com.raphaelbauer.lajolla.container.ResultContainer;

public class ScoreComparator implements Comparator<int[]> {

  @Override
  public int compare(int[] o1, int[] o2) {
    int result = o1[1] - o2[1];
    if (result > 0) {
      return -1;
    } else if (result < 0) {
      return 1;
    } else {
      return 0;
    }
  }

}
