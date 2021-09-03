package com.xworkz.vaccination.util;

import org.apache.log4j.Logger;

public class RandomNumberGenerator {

	private static Logger log = Logger.getLogger(RandomNumberGenerator.class);

	public static int randomNumberGenerator(int min, int max) {
		log.info("Invoked randomNumberGenerator");
		// Generate random int value from 50 to 100
		log.info("Random value in int from " + min + " to " + max + ":");
		int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
		log.info(random_int);

		return random_int;
	}
}
