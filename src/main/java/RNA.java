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

import java.io.File;


import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.raphaelbauer.lajolla.ngramto3dtranslators.INGramTo3DTranslator;
import com.raphaelbauer.lajolla.ngramto3dtranslators.NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults;
import com.raphaelbauer.lajolla.scoringfunctions.EScoringFunctionRelativeSettings;
import com.raphaelbauer.lajolla.scoringfunctions.IScoringFunction;
import com.raphaelbauer.lajolla.scoringfunctions.ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE;
import com.raphaelbauer.lajolla.transformation.IFileToStringTranslator;
import com.raphaelbauer.lajolla.transformation.IResidueToStringTransformer;
import com.raphaelbauer.lajolla.transformation.rna.etatheta.OptimizedStructureToEtaThetaCharacterTransformer;
import com.raphaelbauer.lajolla.transformation.rna.etatheta.PDBRNATranslator;
import com.raphaelbauer.lajolla.transformation.rna.etatheta.RNAEtaThetaMatchRunner;
import com.raphaelbauer.lajolla.utilities.SystemOutUtils;

/*
 * 
 * @author raphael.andre.bauer@gmail.com
 *
 */
public class RNA {

	/**
	 * Main
	 * 
	 * @param args
	 *            are eaten by apache.cli
	 */
	public static void main(final String[] args) {



		////////////////////////////////////////////////////////////////////////
		// input files or directories:
		////////////////////////////////////////////////////////////////////////
		String queryDirOrFile = "";

		String targetDirOfFile = "";

		String outputDir = "";


		////////////////////////////////////////////////////////////////////////
		// mini tuning...
		////////////////////////////////////////////////////////////////////////
		boolean dealWithAllModels = false;
		
		
		double minimumRefinementScore = 0.2d;
		
		int numberOfResultsToWriteOut = 1;


		////////////////////////////////////////////////////////////////////////
		// advanced stuff:
		////////////////////////////////////////////////////////////////////////
		int advancedNGramSize = 7;

		//int advancedAngleDiscretion = 90;
		
		
		//double advancedScoringRadius = 2.0d;
		
		
		EScoringFunctionRelativeSettings scoringFunctionRelativeSettings
			= EScoringFunctionRelativeSettings.basedOnSizeOfSmaller;
	
	
	
		
		
		
		
		
		
		
		long beginTime = System.currentTimeMillis();
		
		
		SystemOutUtils.showSplashScreen();
		
		System.out.println("LaJolla RNA - RNA similarity screening and alignment");


		try {

			Options opt = new Options();

			opt
					.addOption("h", "help", false,
							"Print help for this application");

			opt.addOption("t", "target", true, "target pdb file (directory or file)");

			opt.addOption("q", "query", true, "query pdb file (directory or file)");

			opt.addOption("o", "outputdir", true,
					"Directory where to store result files");

			opt.addOption("am", "allmodels", false,
					"Deal with all models (slower) (DEFAULT: "
							+ dealWithAllModels + ")");

			

			opt
			.addOption("sm", false,
					"Score based on smaller structure (symmetric score)");
			

			
			opt.addOption("ref", "minrefinementscore", true,
					"minimum refinement score needed (DEFAULT: " +
					minimumRefinementScore
					+ ")");
			
			
			opt.addOption("nr", "numres", true,
					"Number of results to write out per target found: " +
					numberOfResultsToWriteOut
					+ ")");
			
			
			
//			opt.addOption("za", "anglediscretion", true,
//					"ADVANCED: discretion angle for phi psi (DEFAULT: " 
//					+ advancedAngleDiscretion
//							+ ")");
			
			
			opt.addOption("zn", "ngramsize", true,
					"ADVANCED: size of ngram window (DEFAULT: " + advancedNGramSize + ")");
			

			
			
//			opt.addOption("zc", "scoringradius", true,
//					"ADVANCED: radius taken for final scoring (DEFAULT: " +
//					advancedScoringRadius
//					+ ")");
			
			
			
			

			BasicParser parser = new BasicParser();
			CommandLine cl = parser.parse(opt, args);
			
			
			
			// no arguments given => print help:
			if (args.length == 0) {
				//System.out.println("cl get arg list length : " + cl.getArgList().size());
				HelpFormatter f = new HelpFormatter();
				f.printHelp("java -cp lajolla.jar RNAEtaTheta [options]", opt);
				System.exit(1);
			}

			
			

			if (cl.hasOption('h')) {
				HelpFormatter f = new HelpFormatter();
				f.printHelp("java -cp lajolla.jar RNAEtaTheta [options]", opt);
				System.exit(1);
			}

			

			if (cl.hasOption("sm")) {
				
				scoringFunctionRelativeSettings
					= EScoringFunctionRelativeSettings.basedOnSizeOfSmaller;
				
			}


				if (cl.getOptionValue("t") == null) {
					System.out.println("[ERROR] -t (target directory with pdb files) not set");

					System.exit(1);

				} else {
					targetDirOfFile = cl.getOptionValue("t");

				}
				
				
				if (cl.getOptionValue("q") == null) {
					System.out.println("[INFO] -q not set (query directory or file)");
					System.out.println("       => using same parameters for -q and -t (all against all matching)");
					
					queryDirOrFile = targetDirOfFile;

					//System.exit(1);

				} else {
					queryDirOrFile = cl.getOptionValue("q");

				}
				

				if (cl.getOptionValue("o") == null) {	
					
					 
					 
					System.out.println("[INFO] -o not set (output directory)");
					System.out.println("       guessing a name and using that as output dir: ");
					
					outputDir =  new File(targetDirOfFile).getName() + "-" + System.currentTimeMillis() + File.separator;
					System.out.println("       " + outputDir);
					//System.exit(1);

				} else {
					outputDir = cl.getOptionValue("o");

				}



				
				if (cl.hasOption("ref")) {

					minimumRefinementScore 
						= Double.parseDouble(cl.getOptionValue("ref"));
				}

				
				if (cl.hasOption("am")) {

					dealWithAllModels = false;
				}
				
				if (cl.hasOption("nr")) {

					numberOfResultsToWriteOut = Integer.parseInt(cl.getOptionValue("nr"));
				}
				
				
				
				
				////////////////////////////////////////////////////////////////
				// advanced parameters:
				////////////////////////////////////////////////////////////////
				
				
//				if (cl.hasOption("za")) {
//
//					advancedAngleDiscretion = Integer.parseInt(cl.getOptionValue("za"));
//				}
//				
				
				if (cl.hasOption("zn")) {

					advancedNGramSize = Integer.parseInt(cl.getOptionValue("zn"));
				}

				

				
				
//				if (cl.hasOption("zc")) {
//
//					advancedScoringRadius = Integer.parseInt(cl.getOptionValue("zc"));
//				}
				
				
				
				IResidueToStringTransformer iResidueToStringTransformer
					= new OptimizedStructureToEtaThetaCharacterTransformer();

				
				
				IScoringFunction scoringFunction 
					= new ScoreAccordingToScoringAtomDistanceOnlyIfNGramsAreSimilarFastNotIdealAndBasedOnTMSCORE(
							scoringFunctionRelativeSettings);
					
				
				INGramTo3DTranslator nGramTo3DTranslator
				= new NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults();
			
				
				
				
				IFileToStringTranslator iFileToStringTranslator 
					= new PDBRNATranslator(
						iResidueToStringTransformer, 
						!dealWithAllModels,
						scoringFunction,
						nGramTo3DTranslator
					);


				RNAEtaThetaMatchRunner.executeSearch(
						advancedNGramSize, 
						iFileToStringTranslator,
						iResidueToStringTransformer,
						targetDirOfFile, 
						queryDirOrFile, 
						outputDir + File.separator,
						!dealWithAllModels,
						minimumRefinementScore,
						numberOfResultsToWriteOut);
				

				long completeTimeInSecs = (System.currentTimeMillis() - beginTime) / 1000;

				String formattedCompleteTimeTakenInHHMMSS = String
						.format("%1$02d:%2$02d:%3$02d", completeTimeInSecs
								/ (60 * 60), (completeTimeInSecs / 60) % 60,
								completeTimeInSecs % 60);

				System.out.println("time taken: "
						+ formattedCompleteTimeTakenInHHMMSS);

			
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}



}
