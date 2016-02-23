/*
 * class DataTable
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
package swingman;

import java.util.*;

/**
 * Maintains an array based list of data points and serves as the data backbone
 * of a graph.  This is part of the 
 * <code>swingman</code> package, a swing-based replacement of 
 * Brookshaw's <code>graph</code> package used previously.
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:31:15 $.
 * @author Will Fisher
 */

/*
 * $Log: DataTable.java,v $
 * Revision 1.1  2004/10/07 20:31:15  dstotler
 * Added to repository.
 *
 *
 */

public class DataTable extends Observable {
	
	private boolean lock_ = false;
	private boolean autoScale_ = true;
	private double scaleSX, scaleSY, scaleLX, scaleLY;
	public double[] init;
	protected int stride = 2;
	private List dataTable = Collections.synchronizedList(new ArrayList());
	// Create the comparator (compares values)
	private Comparator doubleComparator = new Comparator() {
		public int compare (Object obj1, Object obj2) {
			double x1 = ((Double[])obj1)[0].doubleValue();
			double x2 = ((Double[])obj2)[0].doubleValue();
			return ((x1 < x2) ? -1 : ((x1 == x2) ? 0 : 1));
		}
	};
    /**
     * Constructor for this class in which the data already exist.
     * The only supported data structure is a length 2*n 1-D array 
     * with n (x, y) pairs, i.e., {x1, y1, x2, y2, ..., xn, yn}.
     * @param initData initial data array
     */
	public DataTable (double[] initData) {
		// Put initial data into table
		init = initData;
		for (int i = 0; i < initData.length;  i += 2) {
			Double[] data = {new Double(initData[i]), new Double(initData[i+1])};
			dataTable.add(data);
		}
		// Sort the initial data
		sortTable();
	}
    /**
     * A default constructor.
     */
	public DataTable() {
		// Do nothing here
	}
    /** 
     * Sorts the data in order of increasing x value.
     */	
	private void sortTable() {
		Collections.sort(dataTable, doubleComparator);
	}
    /**
     * Number of (x,y) pairs in the DataTable.
     * @return integer number of pairs
     */
	public int countPairs() {
		return dataTable.size();
	}
    /**
     * Adds a new (x,y) pair to to the DataTable, resorts the
     * pairs, and alerts observers to the change.
     * @param x value of the independent variable of the new pair
     * @param y value of the dependent variable of the new pair
     */ 
	public void add (double x, double y) {
		if (!lock_) {
			// Add and then sort
			Double[] data = {new Double(x), new Double(y)};
			dataTable.add(data);
			sortTable();
			// Notify observers of change
			setChanged();  notifyObservers();
		}
	}
    /**
     * Removes a pair, identified by its position in the list,
     * from the DataTable.  The first and last points cannot
     * be removed (a specific requirement for this application).
     * So, index has to be between 1 and n-2, inclusive, where
     * there are n pairs to start with.
     * @param index point to be removed.
     */
	public void remove (int index) {
		if (!lock_) {
		        // Remove and index from the data table
			if ((index != 0) && (index != countPairs()-1))
			    dataTable.remove(index);
			// Notify observers of change
			setChanged();  notifyObservers();
		}
	}
    /**
     * Resets the DataTable to use the data array specified when
     * the constructor was called.
     */	
	public void reset() {
		// Reset data first
		dataTable.clear();
		for (int i = 0; i < init.length;  i += 2) {
			Double[] data = {new Double(init[i]), new Double(init[i+1])};
			dataTable.add(data);
		}
		// Sort the data
		sortTable();
		// Notify observers of change
		setChanged();  notifyObservers();
	}
    /**
     * Directly replace a pair, identified by its position in 
     * the list, with a new (x,y) pair.
     * @param index point to be replaced
     * @param x value of the independent variable of the new point
     * @param y value of the dependent variable of the new point
     */
	public void replace (int index, double x, double y) {
		if (!lock_) {
			// Replace the data point
			Double[] data = {new Double(x), new Double(y)};
			dataTable.set(index, data);
			// Notify observers of change
			setChanged();  notifyObservers();
		}
	}
    /**
     * Get the (x,y) pair occupying the position in the list
     * identified by the integer argument.
     * @param index point to be extracted
     * @return the corresponding (x,y) pair 
     */
	public double[] getPair (int index) {
		Double[] data = (Double[])dataTable.get(index);
		double[] doubleData = {data[0].doubleValue(), data[1].doubleValue()};
		return doubleData;
	}
    /**
     * Find by linear interpolation, or extrapolation, through the
     * data in the DataTable the value of the dependent variable
     * corresponding to the input independent variable.
     * @param x value of the independent variable
     * @return corresponding interpolated value of the dependent variable
     */
	public double interpolate(double x) {
	    int j;
	    double pointj[] = new double[stride];
	    int np = countPairs();
	    
	    for (j = 0; j < np; j++) {
		    pointj = getPair(j);
		    if (pointj[0] == x) {
			    return pointj[1];
		    }
		    else if (pointj[0] > x) {
			    break;
		    }
	    }
	    /* 
	     * Handle cases outside existing range:
	     */
	    double xp = pointj[0];
	    double yp = pointj[1];
	    double xm;
	    double ym;
	    
	    if (j == 0) {
		    pointj = getPair(1);		
	    }
	    else if (j == np) {
		    pointj = getPair(np-2);
	    }
	    else {
		    pointj = getPair(j-1);
	    }
	    /* 
	     * Can now treat linearly extrapolated cases together with usual
	     * interpolated cases.
	     */
	    xm = pointj[0];
	    ym = pointj[1];
	    if (xm == xp) {
		    return 0.5*(ym+yp);
	    }
	    else {
		    return ym + (x - xm)*(yp - ym)/(xp - xm);
	    }
	}
    /** 
     * Returns a "scale", e.g., for use in labeling a graph
     * axis.  With automatic scaling, this is based on the data 
     * currently in the DataTable.  Otherwise, values specified
     * with the setScale method are used.
     * @return array containing the max. and min. x and y values
     */
	public double[] calcScale() {
		// Find the scale
		double tpair[] = getPair(0);
		double xLargest = tpair[0];
		double xSmallest = tpair[0];
		double yLargest = tpair[1];
		double ySmallest = tpair[1];
		
		for (int i = 1; i < countPairs(); i++) {
			double pair[] = getPair(i);
			if (pair[0] > xLargest)
				xLargest = pair[0];
			if (pair[0] < xSmallest)
				xSmallest = pair[0];
			if (pair[1] > yLargest)
				yLargest = pair[1];
			if (pair[1] < ySmallest)
				ySmallest = pair[1];
		}
		
		if (!autoScale_) {
			xLargest = scaleLX;
			yLargest = scaleLY;
			xSmallest = scaleSX;
			ySmallest = scaleSY;
		}
		
		// Calculate the scale
		double xDiff = xLargest - xSmallest;
		double yDiff = yLargest - ySmallest;
		
		// Return values
		double[] temp = {xDiff, yDiff, xSmallest, ySmallest};
		return temp;
	}
    /**
     * Multiplies all of the dependent variable values in the
     * DataTable by the specified factor.
     * @param factor the multiplier to be applied
     */
	public void multiplyData (double factor) {
		// Iterate through data and multiply by factor
		ListIterator it = dataTable.listIterator();
		while (it.hasNext()) {
			Double[] data = (Double[])it.next();
			data[1] = new Double(((data[1].doubleValue())*factor));
			it.set(data);
		}
	}
    /**
     * Locks (e.g., prevents points from being changed) or unlocks the 
     * DataTable.
     * @param lock desired state of the DataTable
     */
	public void setLock (boolean lock) {
		lock_ = lock;
	}
    /**
     * Returns the current lock state
     * @return the lock state
     */
        public boolean hasLock() {
	    return lock_;
        }
    /**
     * Allows the axis scale to be externally specified
     * @param x1 smallest value of the independent variable
     * @param y1 smallest value of the dependent variable
     * @param x2 largest value of the independent variable
     * @param y2 largest value of the dependent variable
     */
	public void setScale (double x1, double y1, double x2, double y2) {
		scaleSX = x1;  scaleSY = y1;
		scaleLX = x2;  scaleLY = y2;
		autoScale_ = false;
	}
    /** 
     * Enables automatic scaling of the data.  If invoked, the
     * scale will be determined directly from the DataTable.
     */
	public void setAutoScale() {
		autoScale_ = true;
	}
}
