/*
 * class NumRecRungeKutta
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
package rootsolvers;
/**
 * This class is the implementation of the Numerical Recipes Runge-Kutta
 * ODE solver with their adaptive step size routine, stepper, and the 
 * Runge-Kutta algorithm, rkck.  In the book, the stepper is named rkqs. 
 * We have also extended the driver to replace its loosely specified
 * output points with a list of desired values for the independent
 * variable.  This list is actually part of the ODESolved interface.
 *
 * The driver routine can be used with other algorithms.  In fact,
 * one could imagine making this an abstract class with this implementation
 * of driver and an abstract (but still static) stepper class.  Unfortunately,
 * that arrangement is incompatible with the Java language specification.
 * The alternative is to begin with a fully implemented class - this one.
 * Alternative solvers can be created by extending this one and overriding
 * (hiding) the stepper method.  The class NumRecAnotherSolver was included
 * in this package to illustrate how (at least until a real alternative
 * is coded up).
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:26:09 $.
 * @author Daren Stotler
 * @see ODESolved
 * @see NumRecAnotherSolver
 * @see TestApplications.TestODESolved
 * @see TestApplications.TDFTest
 */

/*
 * $Log: NumRecRungeKutta.java,v $
 * Revision 1.1  2004/10/07 20:26:09  dstotler
 * Added to repository.
 *
 */
public class NumRecRungeKutta {
    /*
     * Misc. internal variables used by the driver
     */
    private static final int MAXSTEP = 10000;     // Maximum no. of steps
    private static final double TINY = 1.e-30;    // Used in yscal
    /*
     * Would have liked to put these in the argument list for stepper, but
     * they are passed-by-value, so stepper can't change them.
     */
    protected static double hdid_;                // Stepsize taken by stepper
    protected static double hnext_;               // Estimated next stepsize
    /*
     * Local pointer to object describing the ODE to be solved
     */
    protected static ODESolved os_;             
    protected static int numEq_;                 // Number of ODE's
    /**
     * This routine was derived from the Numerical Recipes driver.  The 
     * "driver" uses information on the previous step and suggested next
     * step size from the stepper routine, together with a specification
     * of the output points, to set the desired next step size.  This
     * version differs from that of Numerical Recipes in that a list of
     * desired output points (values of the independent variable) is
     * obtained from the ODESolved object.  Rather than return the 
     * results of the computation, they are passed to the ODESolved
     * object via its set_results method.
     * @param s pointer to the object describing the ODE to be solved
     * @exception RootSolverException thrown for a variety of reasons
     */
    public static void driver(ODESolved s) throws RootSolverException {
	/*
	 * Initialization
	 */
	os_ = s;
	double x1 = os_.get_initial_x();
	double y1[] = os_.get_initial_ys();
	numEq_ = y1.length;            
	double x_outs[] = os_.get_desired_xs();
	int n_outs = x_outs.length;         // Number of output points
	double x2 = x_outs[n_outs-1];       // Last value
	double h = os_.get_initial_step_size();
	if (h * (x2 - x1) <= 0.) {
	    throw new RootSolverException("Initial step size is zero or of wrong sign");
	}
	double hmin = os_.get_min_step_size();
	double x = x1;                    // Current value of indep. variable
	double hlast = 0.;                // Last step size chosen by stepper
	int nok = 0;                      // Number of good steps
	int nbad = 0;                     // Number of bad steps
	/* 
	 * This is the precision for hitting desired x values.  Since
	 * this routine adjusts the step to hit them exactly, we should
	 * be able to set this to machine accuracy.
	 */
	double x_eps = 1.e-10;             
	double[] y = new double[numEq_];  // Current values of dep. variable
	for (int i=0; i < numEq_; i++) {
	    y[i] = y1[i];
	}
	double[] dydx = new double[numEq_];   // Current derivatives
	double[] yscal = new double[numEq_];  // Scaling factors for errors
	/*
	 * Define output arrays and store initial values
	 */
	double[] xp = new double[n_outs+1];  
	double[][] yp = new double[n_outs+1][numEq_]; 
	int i_out = 0;
	xp[i_out] = x1;
	for (int i=0; i < numEq_; i++) {
	    yp[i_out][i] = y1[i];
	}
	i_out++;
	/*
	 * We're using i_out as the index into xp, so it's shifted by 1
	 * relative to x_outs.
	 */
	double x_next = x_outs[i_out-1];
	/*
	 * Use a single array to get results from stepper method
	 */
	double[] xyOut = new double[numEq_+1];
	/*
	 * MAIN LOOP. Note that the maximum number of steps is hardwired
	 */
	for (int nstep=1; nstep <= MAXSTEP; nstep++) {
	    dydx = os_.derivatives(y, x);
	    /*
	     * Set the scaling values used to monitor accuracy
	     */
	    for (int i=0; i < numEq_; i++) {
		yscal[i] = Math.abs(y[i]) + Math.abs(h*dydx[i]) + TINY;
	    }
	    /*
	     * Take a step...
	     */
	    xyOut = stepper(y, dydx, x, h, yscal);
	    for (int i=0; i < numEq_; i++) {
		y[i] = xyOut[i];
	    }
	    x = xyOut[numEq_];
	    if (hdid_ == h) {
		nok++;
	    }
	    else {
		nbad++;
	    }
	    /*
	     * Check to see if we've reached the next desired x value
	     */
	    if (Math.abs(x - x_next) < x_eps) {
		xp[i_out] = x;             // Copy to output array
		for (int i=0; i < numEq_; i++) {
		    yp[i_out][i] = y[i];
		}
		if (i_out == n_outs) {     // Send to ODESolved
		    os_.set_results(yp, xp);
		    return;                // We're done
		}
		else {                     // Otherwise, go to next x value
		    i_out++;
		    x_next = x_outs[i_out-1];
		}
	    }                           
	    /*
	     * Check requested step size against minimum.  We need to 
	     * do this test prior to choosing h to hit x_next since
	     * doing so may leave us with a very small step.  We can
	     * live with that situation since it is not an indication
	     * that the algorithm is failing.
	     */
	    if (Math.abs(hnext_) < hmin) {
		throw new RootSolverException("Stepsize smaller than minimum in driver");
	    }
	    /*
	     * If hnext_ goes beyond next desired x, reset h, but save
	     * h_next_ for possible use on the step after that (i.e., the 
	     * subsequent else-if).
	     */
	    if ((x + hnext_ - x_next)*(x2 - x1) > 0.) {
		h = x_next - x;
		hlast = hnext_;
	    }
	    /*
	     * May not want to use an hnext_ that results from a step
	     * taken to hit a desired x, i.e., because the h used in that
	     * step was shorter than it could have been.  Have stored the
	     * proposed value of h for that step as hlast.  By otherwise
	     * setting hlast to zero, we can keep track of when h was
	     * forcibly shortened.
	     * 
	     * As above, we need to also be sure that hlast doesn't
	     * take us past x_next.  Since this occurence indicates that
	     * hlast exceeded the spacing between output x values, 
	     * (i.e., was too big to be useful) allow
	     * hlast to be reset to zero in this case as well.
	     */
	    else if (Math.abs(hlast) > Math.abs(hnext_)) {
		if ((x + hlast - x_next)*(x2 - x1) > 0.) {
		    h = x_next - x;
		}
		else {
		    h = hlast;
		}
		hlast = 0;
	    }
	    /* 
	     * The default case: use the proposed hnext_.
	     */
	    else {
		h = hnext_;
		hlast = 0.;
	    }
	}                                                // Loop over nstep
	throw new RootSolverException("Too many steps in driver");
    }
    /*
     * This implementation of the stepper method provids a fifth-order 
     * Runge-Kutta step with monitoring of local truncation error
     * to ensure accuracy and adjust step size.  Even though this method
     * appears to be generic, the step size adjustments have been tailored
     * (by the Numerical Recipes folks) to the Runge-Kutta algorithm.
     * The starting values of the
     * independent x and dependent y variables, as well as the derivatives
     * dydx are input. The stepsize to be attempted is htry. The accuracy
     * required eps is obtained from the ODESolved class; the
     * argument yscal is the vector used to scale the error estimate. On
     * return, the class variables hdid_ and hnext_ show the actual and 
     * estimated next stepsizes, respectively.
     * @param y array of initial values of the dependent variables
     * @param dydx array of initial values of their derivatives
     * @param x initial value of the independent variable
     * @param htry stepsize to be attempted
     * @param yscal array of scale factors to be used with the error estimate
     * @exception RootSolverException is thrown for a variety of reasons
     * @return array of final values of the dependent and independent
     * (in the last index) variables
     */
    public static double[] stepper(double[] y, double[] dydx, double x, 
				   double htry, double[] yscal)
	throws RootSolverException {
	/*
	 * Parameters that govern changes in the step size (taken directly
	 * from Numerical Recipes). 
	 */
	final double SAFETY = 0.9;
	final double PGROW = -0.2;
	final double PSHRINK = -0.25;
	final double ERRCON = 1.89e-4;

	double h = htry;                     // Initial trial step size
	double errmax;
	double eps = os_.get_relative_error();
	if (eps <= 0.) {
	    throw new RootSolverException("stepper routine needs a positive value for the relative error.");
	}
	/*
	 * Array of error estimates for the dependent variables 
	 * computed in rkck.
	 */
	double[] yerr = new double[numEq_]; 
	double[] ytemp = new double[numEq_];   // Dep. vars. returned from rkck
	/*
	 * This is the array that will be returned to driver.  Note that the
	 * dependent variable occupies the last index of the array.
	 */
	double[] xyOut = new double[numEq_+1]; 

	do {
	    /* 
	     * Take a step with this value of h
	     */
	    ytemp = rkck(y, dydx, x, h, yerr);  
	    for (int i = 0; i < numEq_; i++) {
		xyOut[i] = ytemp[i];
	    }
	    /* 
	     * Compare the estimated error in the result, yerr, with 
	     * that specified by yscal and eps.
	     */
	    errmax = 0.;
	    for (int i=0; i < numEq_; i++) {    
		errmax = Math.max(errmax,Math.abs(yerr[i]/yscal[i]));
	    }
	    errmax /= eps;              
	    double htemp;
	    if (errmax > 1.) {                 
		/* 
		 * If error is too large, reduce stepsize...
		 */
		htemp = SAFETY * h * (Math.pow(errmax,PSHRINK));
		/*
		 *  By no more than a factor of 10...
		 */
		htemp = Math.max(Math.abs(htemp), 0.1*Math.abs(h)); 
		if (h < 0.) {
		    h = -htemp;
		}
		else {
		    h = htemp;
		}
		double xnew = x + h;
		if (xnew == x) {
		    throw new RootSolverException("Stepsize underflow in stepper");
		}
	    }
	} while (errmax > 1.);                // And try again...
    /*
     * Step has succeeded.
     */	                                     
	if (errmax > ERRCON) {                // Increase step size
	    hnext_ = SAFETY * h * (Math.pow(errmax,PGROW));
	}
	else {
	    hnext_ = 5. * h;                  // But by no more than 5.
	}
	hdid_ = h;
	x += h;
	xyOut[numEq_] = x;
       	return (xyOut);
    }
    /**
     * This method implements the Cash-Karp Runge Kutta algorithm as
     * described in Numerical Recipes.
     * It takes as input the values of y[] and derivatives dydx[] at x,
     * along with the interval h. The incremented dependent variables are
     * returned as the method values. An error estimate found by the
     * embedded fourth order method is returned via the array yerr.
     * @param y array of initial values of the dependent variables
     * @param dydx array of initial values of their derivatives
     * @param x initial value of the independent variable
     * @param h stepsize
     * @param yerr array of (returned) estimated errors
     * @exception RootSolverException passed up from derivatives method
     * @return array of final values of the dependent variables
     */
    private static double[] rkck(double[] y, double[] dydx, double x, 
				 double h, double[] yerr) 
	throws RootSolverException {
	/*
	 * Constants for the algorithm routine rkck
	 */
	final double A2 = 0.2;
	final double A3 = 0.3;
	final double A4 = 0.6;
	final double A5 = 1.0;
	final double A6 = 0.875;
	final double B21 = 0.2;
	final double B31 = 3./40.;
	final double B32 = 9./40.;
	final double B41 = 0.3;
	final double B42 = -0.9;
	final double B43 = 1.2;
	final double B51 = -11./54.;
	final double B52 = 2.5;
	final double B53 = -70./27.;
	final double B54 = 35./27.;
	final double B61 = 1631./55296.;
	final double B62 = 175./512.;    
	final double B63 = 575./13824.;  
	final double B64 = 44275./110592.;
	final double B65 = 253./4096.;  
        final double C1 = 37./378.;     
	final double C3 = 250./621.;    
	final double C4 = 125./594.;     
	final double C6 = 512./1771.;    
	final double DC1 = C1 - 2825./27648.;  
	final double DC3 = C3 - 18575./48384.;
	final double DC4 = C4 - 13525./55296.;
	final double DC5 = -277./14336.;
	final double DC6 = C6 - 0.25;   

	double[] ytemp = new double[numEq_];
                                                   // First step
	for (int i=0; i < numEq_; i++) { 
	    ytemp[i] = y[i] + B21 * h * dydx[i];
	}
	double[] ak2 = new double[numEq_];
	ak2 = os_.derivatives(ytemp, x + A2 * h);
                                                   // Second step
	for (int i=0; i < numEq_; i++) { 
	    ytemp[i] = y[i] + h * (B31*dydx[i] + B32*ak2[i]);
	}
	double[] ak3 = new double[numEq_];
	ak3 = os_.derivatives(ytemp, x + A3 * h);
                                                   // Third step
	for (int i=0; i < numEq_; i++) {
	    ytemp[i] = y[i] + h * (B41*dydx[i] + B42*ak2[i] + B43*ak3[i]);
	}
	double[] ak4 = new double[numEq_];
	ak4 = os_.derivatives(ytemp, x + A4 * h);
                                                   // Fourth step
	for (int i=0; i < numEq_; i++) {
	    ytemp[i] = y[i] + h * (B51*dydx[i] + B52*ak2[i] + B53*ak3[i] 
				   + B54*ak4[i]);
	}
	double[] ak5 = new double[numEq_];
	ak5 = os_.derivatives(ytemp, x + A5 * h);
                                                    // Fifth step
	for (int i=0; i < numEq_; i++) {
	    ytemp[i] = y[i] + h * (B61*dydx[i] + B62*ak2[i] + B63*ak3[i] 
				   + B64*ak4[i] + B65*ak5[i]);
	}
	double[] ak6 = new double[numEq_];
	ak6 = os_.derivatives(ytemp, x + A6 * h);
	/*
	 * Sixth step; accumulate increments with proper weights. 
	 * Also, estimate error as difference between fourth and fifth 
         * order methods.
	 */
	for (int i=0; i < numEq_; i++) {
	    ytemp[i] = y[i] + h*(C1*dydx[i] + C3*ak3[i] + C4*ak4[i] + C6*ak6[i]);
	    yerr[i] = h * (DC1*dydx[i] + DC3*ak3[i] + DC4*ak4[i] 
			    + DC5*ak5[i] + DC6*ak6[i]);
	}
	return (ytemp);
    }
    }

