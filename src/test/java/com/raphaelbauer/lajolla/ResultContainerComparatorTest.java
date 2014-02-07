package com.raphaelbauer.lajolla;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import com.raphaelbauer.lajolla.comparators.ResultContainerComparator;
import com.raphaelbauer.lajolla.container.ResultContainer;
import com.raphaelbauer.lajolla.container.ScoreContainer;

public class ResultContainerComparatorTest {

  @Test
  public void testComparator() {

    ResultContainer resultContainerSmall = new ResultContainer(null, null,
            new ScoreContainer(0.1, Double.NaN, 0, 0, 0));

    ResultContainer resultContainerBig
            = new ResultContainer(null, null, new ScoreContainer(0.2, Double.NaN, 0, 0, 0));

    ResultContainerComparator resultContainerComparator
            = new ResultContainerComparator();

    assertThat(
            resultContainerComparator.compare(resultContainerSmall, resultContainerBig),
            equalTo(1));

    assertThat(
            resultContainerComparator.compare(resultContainerBig, resultContainerSmall),
            equalTo(-1));

    assertThat(
            resultContainerComparator.compare(resultContainerBig, resultContainerBig),
            equalTo(0));

  }
}
