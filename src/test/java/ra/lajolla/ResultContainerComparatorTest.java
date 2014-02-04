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
package ra.lajolla;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import ra.lajolla.comparators.ResultContainerComparator;
import ra.lajolla.container.ResultContainer;
import ra.lajolla.container.ScoreContainer;

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
