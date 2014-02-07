package com.raphaelbauer.lajolla.comparators;

import java.util.Comparator;

import com.raphaelbauer.lajolla.container.ResultContainer;

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
