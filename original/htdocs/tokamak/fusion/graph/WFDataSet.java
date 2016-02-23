/*
 * class WFDataSet
 * 
 *    Copyright (C) 2004, Daren Stotler
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
package graph;

/**
 * This extends the DataSet class to provide the additional functionality
 * we need for the waveform class.
 *
 * @version $Revision $, $Date: 2004/10/07 20:19:45 $.
 * @author Daren Stotler
 * @see DataSet
 * @see waveform
 */

/*
 * $Log: WFDataSet.java,v $
 * Revision 1.1  2004/10/07 20:19:45  dstotler
 * Added to repository.
 *
 *
 */

public class WFDataSet extends DataSet {

    /*
     * Extrapolation methods
     */
    public final static int CONSTANT = 0;
    public final static int LINEAR = 1;

    protected int extrapolation = CONSTANT;

    public WFDataSet() {
    }

    public WFDataSet (int stride) throws Exception {
	super(stride);
    }

    public WFDataSet (double d[], int n) throws Exception {
	super(d, n);
    }
    
    public WFDataSet (double d[], int n, int s) throws Exception{
	super(d, n, s);
    }
    /**
     * Change a point in the data set.
     * If the input point newpoint matches exactly a point in the current
     * data set, that point is deleted.  Otherwise, the input point is 
     * added to the data set. This method returns the number of points
     * np in case the revised value is needed in the calling method.
     * @param newpoint Array containing the point to be changed.
     */
public int changePoint(double newpoint[]) {
    int j;
    int k;
    int jnew=-1;
    double lastpoints[];
    int lastlen;
    double pointj[] = new double[stride];
    int np = dataPoints();
    for (j=0; j<np; j++) {
	pointj = getPoint(j);
	/*
	 * Had originally checked only the first coordinate of the point.
	 * Now check all (using stride) so as to be just as general as
	 * the code in DataSet.  Believe this code would be a little more
	 * obvious if there was a not-and operator. Note that this method
	 * necessarily calls delete and / or append. Hence, do not need
	 * to update range and length.
	 */
	boolean nomatch = false;
	for (k=0; k<stride; k++) {
	    nomatch = nomatch || (pointj[k] != newpoint[k]);
	}
	if (!nomatch) 
	    {
	    delete(j,j);      // Try this - can we delete the current point?
	    np=dataPoints();
	    return np;
	    }
	else if (pointj[0] > newpoint[0]) 
	    {
		jnew = j;
		break;
	    }
    }
    if (jnew == -1) jnew=np;
    lastlen = np - jnew;
    lastpoints = new double[(lastlen+1)*stride];
    for (k=0; k<stride; k++) {
	lastpoints[k]=newpoint[k];
    }
    int l=0;
    while (l < lastlen) {
	pointj = getPoint(jnew+l);
	for (k=0; k<stride; k++) {
	    lastpoints[(l+1)*stride+k] = pointj[k];
	}
	l++;
    }
    if (jnew < np) delete(jnew,np-1);
    try {
	append(lastpoints,lastlen+1);
    }
    catch(Exception e) {
	System.out.println("Error appending new data point.");
    }
    np=dataPoints();
    range(stride);
    return np;
}
    /**
     * Checks the current data points to see if one has an abscissa matching
     * the input point.  If so, the index of that point is returned; if not,
     * return -1.
     * @param x Value of abscissa being checked.
     */
    public int matchX(double x) {
	int j;
	int k = -1;
	double pointj[] = new double[stride];
	int np = dataPoints();
	for (j=0; j<np; j++) {
	    pointj = getPoint(j);
	    if (x == pointj[0]) k=j;
	}
	return k;
    }
    /** 
     * Normalize the data set so the maximum value of the first ordinate is 1.
     * If the current maximum value is less than or equal to 0, do nothing.
     */
    public void normalize() {
	int j;
	int np = dataPoints();
	double pointj[] = new double[stride];
	double ymax = 0.;
	for (j=0; j<np; j++) {
	    pointj = getPoint(j);
	    if (pointj[1] > ymax) ymax = pointj[1];
	}
	if (ymax > 0.) {
	    for (j=0; j<np; j++) {
		pointj = getPoint(j);
		pointj[1] = pointj[1] / ymax;
		swapPoint(j, pointj);
	    }
	}
    }
    /**
     * Get the integer index of the point in the data set corresponding to
     * to the input point. Only an exact match is sought, so calling the
     * method with a point not in the data set will result in an exception.
     * @param point Array containing the point to be looked up.
     * @exception Exception
     *            A generic exception thrown if the point is not in the
     *            current data set.
     */
public int getIndex(double[] point) throws Exception {
    boolean nomatch;

    for (int i=0; i<=length/stride-1; i++) {
	nomatch = false;
	for (int j=0; j<stride; j++) {
	    nomatch = nomatch || (data[i*stride+j] != point[j]);
	}
	if (!nomatch) {
	    return i;
	}
    }
    throw new Exception("WFDataSet:  getIndex fails!");
}
    /**
     * Swap point number index, replacing coordinates with newpoint.
     * @param index Index of point to be replaced.
     * @param newpoint Coordinates of new point.
     */
    public void swapPoint(int index, double newpoint[]){
	for (int j=0; j<stride; j++) {
	    data[index*stride+j] = newpoint[j];
	}
	range(stride);
    }
    /**
     * Just check to see if the x values of the data points are in order 
     * (monotonically increasing); return true if so, false otherwise.
     */
    public boolean inOrder() {
	for (int i=1; i<=length/stride-1; i++) {
	    if (!(data[i*stride] > data[(i-1)*stride])) return false;
	}
	return true;
    }
    public void setExtrapolation(int extrap) {
	extrapolation = extrap;
    }
    /**
     * Linearly interpolate the ordinate corresponding to the input
     * abscissa. Uses the parameter extrapolation to control methods of
     * extrapolation.  Currently allows only constant or linear extrapolation.
     */
    public double interpolate(double x) {
	int j;
	double pointj[] = new double[stride];
	int np = dataPoints();
	for (j=0; j<np; j++) {
	    pointj = getPoint(j);
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
	    if (extrapolation == CONSTANT) {
		return yp;
	    }
	    else if (extrapolation == LINEAR) {
		pointj = getPoint(1);
	    }		
	}
	else if (j == np) {
	    if (extrapolation == CONSTANT) {
		return yp;
	    }
	    else if (extrapolation == LINEAR) {
		pointj = getPoint(np-2);
	    }
	}
	else {
	    pointj = getPoint(j-1);
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
}
