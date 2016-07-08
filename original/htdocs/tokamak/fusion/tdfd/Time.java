/*
 * class Time
 *
 *    Copyright (C) 2004, Will Fisher, Daren Stotler
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package tdfd;

import java.util.*;
import swingman.*;
/**
 * This observable class encapsulates a thread that does the animation 
 * timing and further notification to the graphs and the plasma image.
 * 
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:38:00 $.
 * @author Will Fisher
 */

/*
 * $Log: Time.java,v $
 * Revision 1.1  2004/10/07 20:38:00  dstotler
 * Added to repository.
 *
 */
public class Time extends Observable implements Runnable {
	
	// 40 milliseconds running for 20 seconds total
    public static final int TIME_END = 20000, TIME_INTERVAL = 40;
    private Timer timer_;
    private DataTable[] solution_;  
    /**
     * The method associated with the Runnable interface
     */
    public void run() {
	timer_ = new Timer();
	timer_.schedule(new TimeManager(), 0, TIME_INTERVAL);
    }

    public void setSolution (DataTable[] solution) {
	solution_ = solution;
    }
    /**
     * Find the value of the data in the input DataTable
     * corresponding to the input value of the independent
     * variable.  
     * @param dt object containing point-wise function data
     * @param x the value of the independent variable
     * @return interpolated value of the dependent variable
     */
    public double interpolate(DataTable dt, double x) {
	    /*
	     * Will originally had duplicated the method from
	     * DataTable here.  Not sure why; seemed to be exactly
	     * the same.
	     */
	return dt.interpolate(x);
    }
	
	class TimeManager extends TimerTask {
		long start = System.currentTimeMillis();
		int count = 0;
		
		public void run() {
			// Declare the starting time first
			long diff = (System.currentTimeMillis() - start);
			if (diff <= TIME_END) {
				// FORMAT: data identifier, time in millis, score, temperature
				Double[] distData = {new Double(0), new Double(diff),
						     new Double(interpolate(solution_[0], diff/1000.0)),
						     new Double(interpolate(solution_[1], diff/1000.0))};
				// Notify observers of change
				setChanged();  notifyObservers(distData);
			}
			else {
			        // Cancel the timer then tell observers it's over
				this.cancel();
				setChanged();  notifyObservers();
			}
		}
	}
}
