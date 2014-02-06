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
package com.raphaelbauer.lajolla.utilities;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemOutUtils {
	
	
	public static void showSplashScreen() {
		
		
		
		// Artwork generated via: 
		// http://www.network-science.de/ascii/
		// font is graffiti...
		
		Properties properties = new Properties();
		

		try {
			String sConfigFile = "all.properties";

			InputStream in = SystemOutUtils.class.getClassLoader().getResourceAsStream(
					sConfigFile);

			properties.load(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		System.out.println(".____                 ____.      .__  .__                 ________ " );
		System.out.println("|    |   _____       |    | ____ |  | |  | ____royal flush\\_____  \\" );
		System.out.println("|    |   \\__  \\      |    |/  _ \\|  | |  | \\__  \\   \\  \\/ //  ____/" );
		System.out.println("|    |___ / __ \\_/\\__|    (  <_> )  |_|  |__/ __ \\_  \\   //       \\" );
		System.out.println("|_______ (____  /\\________|\\____/|____/____(____  /   \\_/ \\_______ \\");
		System.out.println("        \\/    \\/http://lajolla.sf.net           \\/         version\\/"
		        	+ properties.getProperty("version"));
		        	
	}

}
