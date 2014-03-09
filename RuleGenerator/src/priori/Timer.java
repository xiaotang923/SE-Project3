/*
 * Timer - class to time execution of Java code in milliseconds
 *
 * Created on Dec 2, 2003 - Paul J. Wagner
 */

package priori;

import java.util.Date;

/**
 * @author WAGNERPJ
 */
public class Timer {
	private long startTime;			// start time in absolute milliseconds
	private long stopTime;			// stop time in absolute milliseconds
	
	// --- default constructor
	public Timer() {
		startTime = 0;
		stopTime = 0;
	}
	
	// --- startTimer - get a starting time
	void start() {
		Date now = new Date();
		startTime = now.getTime();
	}

	// --- stopTimer - get an ending time
	void stop() {
		Date now = new Date();
		stopTime = now.getTime();
		
		System.out.println("Elapsed time in miliSec: " + getTotal());
	}

	// --- getTotal - return the elapsed time
	long getTotal() {
		long result = 0;
		if (stopTime > startTime) {
			result = stopTime - startTime;
		}
		
		return result;
	}
}	// end - class Timer
