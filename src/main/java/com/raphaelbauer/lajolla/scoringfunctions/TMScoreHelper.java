package com.raphaelbauer.lajolla.scoringfunctions;

/**
 * TM SCORE is:
 *
 *
 * int numberOfAllAtomsToCompare
 *
 * int numberOfFoundAtoms
 *
 *
 *
 * 1/numberOfAllAtomsToCompare *
 *
 *
 * numberOfFoundAtoms SUM 1 / ( 1+(distance(ithting / )^2
 *
 *
 *
 * @author ra
 *
 */
public class TMScoreHelper {

  public static double calculateTermOfSum(int numberOfAllAtoms, double distanceOfTheTwoAtoms) {

    double valueOfd0LTarget = 1.24d * Math.cbrt((numberOfAllAtoms - 15)) - 1.8d;

    double completeValue = 1 / (1d + Math.pow((distanceOfTheTwoAtoms / valueOfd0LTarget), 2d));

    return completeValue;

  }

  public static double calculateWholeTMScore(int numberOfAllAtoms, double termOfSum) {

    return ((1d / numberOfAllAtoms) * termOfSum);

  }

}
