/*
 * class TestODESolved
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
package TestApplications;

import java.text.NumberFormat;
import rootsolvers.ODESolved;
import rootsolvers.NumRecRungeKutta;
import rootsolvers.RootSolverException;
/**
 * This is an initial test implementation of an interface designed to provide
 * the information needed to integrate an ordinary differential
 * equation.  The equation integrated here is just that for simple
 * harmonic motion, <i>d<sup>2</sup> y / d x<sup>2</sup> = -y</i>.  
 * This is transformed into two
 * 1st order ODEs, <i>d y<sub>1</sub> / dx = y<sub>2</sub>, 
 * d y<sub>2</sub> / dx = -y<sub>1</sub></i>.  The initial condition
 * at <i>x = 0</i> is <i>y<sub>1</sub> = 0.</i>, <i>y<sub>2</sub> = 1.</i>; 
 * the final x value is <i>2 pi</i>.  The particular
 * solution in this case is <i>y<sub>1</sub> = y = sin(x)</i>; 
 * <i>y<sub>2</sub> = cos(x)</i>.  Hence, the final
 * values should be <i>y<sub>1</sub> = 0.</i>, <i>y<sub>2</sub> = 1</i>.
 *  
 * Output from this class should look something like:
 * <pre>
 *  step    x      y1       y2	  
 * 0  0.00000  0.00000  1.00000	  
 * 1  0.31416  0.30902  0.95106	  
 * 2  0.62832  0.58779  0.80902	  
 * 3  0.94248  0.80902  0.58779	  
 * 4  1.25664  0.95106  0.30902	  
 * 5  1.57080  1.00000  -0.00000	  
 * 6  1.88496  0.95106  -0.30902	  
 * 7  2.19911  0.80902  -0.58779	  
 * 8  2.51327  0.58779  -0.80902	  
 * 9  2.82743  0.30902  -0.95106	  
 * 10  3.14159  -0.00000  -1.00000	  
 * 11  3.45575  -0.30902  -0.95106	  
 * 12  3.76991  -0.58779  -0.80902	  
 * 13  4.08407  -0.80902  -0.58779	  
 * 14  4.39823  -0.95106  -0.30902	  
 * 15  4.71239  -1.00000  0.00000	  
 * 16  5.02655  -0.95106  0.30902	  
 * 17  5.34071  -0.80902  0.58779	  
 * 18  5.65487  -0.58779  0.80902	  
 * 19  5.96903  -0.30902  0.95106	  
 * 20  6.28319  0.00000  1.00000 
 *</pre> 
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 19:52:40 $.
 * @author Daren Stotler
 * @see rootsolvers.NumRecRungeKutta
 * @see rootsolvers.ODESolved
 * @see rootsolvers.Solved
 * @see rootsolvers.Solvers
 */

/*
 * $Log: TestODESolved.java,v $
 * Revision 1.1  2004/10/07 19:52:40  dstotler
 * Added to repository.
 *
 */
public class TestODESolved implements ODESolved {

    private static int neq_ = 2;              // Number of equations	
    private static int nsteps_ = 20;	      // Number of output steps	
    private static boolean solved_ = false;   // Used by is_solved	
    private static double[] xp_;	      // Output indep. var. values
    private static double[][] yp_;	      // Output dep. var. values

    public static void main(String args[]) {
	TestODESolved os = new TestODESolved();
	try {
	    NumRecRungeKutta.driver(os);
	} catch (RootSolverException e) {
	    System.out.println("Runge Kutta routine or ODE had a problem: " + e.getMessage());
	}
    }

    /**
     * These are the derivatives of the dependent variables (possibly more
     * than one) with respect to the independent variable.
     * @param y array of the local values of the dependent variables
     * @param x local values of the independent variable at which the
     * derivatives are to be evaluated
     * @exception RootSolverException is thrown when something isn't as expected
     * @return array of values of the derivatives
     */
    public double[] derivatives(double[] y, double x) throws RootSolverException {
	double[] y_prime = new double[neq_];
	/*
	 * Check size of y
	 */
	if (y.length != neq_) {
	    throw new RootSolverException("Call to derivatives with wrong size y array");
	}
	y_prime[0] = y[1];
	y_prime[1] = -y[0];
	return(y_prime);
    }
    public double get_initial_x() {
	double x1 = 0.;
	return(x1);
    }
    /**
     * Returns the initial array of values of the dependent variable.
     */
    public double[] get_initial_ys() {
	double[] y1 = new double[neq_];
	y1[0] = 0.0;
	y1[1] = 1.0;
	return(y1);
    }
    /**
     * Returns the desired relative integration accuracy.  Whether and how
     * this parameter is used depends on the integration routine.
     */
    public double get_relative_error() {
	return(1.e-4);
    }
    /**
     * Returns the desired absolute integration accuracy.  Whether and how
     * this parameter is used depends on the integration routine.
     * NumRecRungeKutta does NOT need this, so we just return 0 here.
     */
    public double get_absolute_error() {
	return(0.);
    }
    /**
     * Returns the (absolute) initial step size.  
     */
    public double get_initial_step_size() {
	return(1.e-3);
    }
    /**
     * Returns the minimum step size.  Some integration routines may allow 
     * this to be zero so that other mechanisms are invoked to stop failed 
     * (i.e., not making adequate progress) integrations.
     */
    public double get_min_step_size() {
	return(0.);
    }
    /**
     * Returns an array of one or more values at which the solution of the
     * ODE is desired.
     */
    public double[] get_desired_xs() {
	double[] x_steps = new double[nsteps_];
	double delta_x = 2.*Math.PI / (double) nsteps_;
	for (int i=0; i < nsteps_; i++) {
	    x_steps[i] = delta_x * (double) (i+1);
	}
	return(x_steps);
    }
    /**
     * This method provides a means of receiving the dependent variables
     * computed at each of the desired x values.  Following the initial
     * implementation, the desired ordering of subscripts for y is
     * output step first (left index) and equation number second (on the
     * right).  This should be consistent with array storage order.
     * The method should compare subscripts with those expected
     * and throw an exception when they don't agree.
     * @param y array of dependent variables at each of the x values
     * @param x array of independent variables at which the ODE has been solved
     * @exception RootSolverException is thrown if the arrays are not of the
     * expected size.
     */
    public void set_results(double[][] y, double[] x) throws RootSolverException {
	xp_ = new double[nsteps_+1];
	yp_ = new double[nsteps_+1][neq_];
	NumberFormat nf = NumberFormat.getInstance();
	nf.setMinimumFractionDigits(5);
	nf.setMaximumFractionDigits(5);
	if (x.length != nsteps_+1) {
	    throw new RootSolverException("Result x is wrong size");
	}
	/*
	 * For reasons I don't understand, the length of the entire y
	 * array corresponds to that of the first subscript.  The length
	 * of the second subscript is tested below.
	 */
	if (y.length != (nsteps_+1)) {
	    throw new RootSolverException("Result y is wrong size");
	}
	System.out.println(" step    x      y1       y2");
	for (int i=0; i < nsteps_+1; i++) {
	    xp_[i] = x[i];
	    if (y[i].length != neq_) {
		throw new RootSolverException("Result y subscript order is wrong");
	    }
	    for (int j=0; j < neq_; j++) {
		yp_[i][j] = y[i][j];
	    }
	    System.out.println(i + "  " + nf.format(x[i]) + "  "
			       + nf.format(y[i][0]) + "  " 
			       + nf.format(y[i][1]));
	}
	solved_ = true;
    }
    /*
     * This method provides a means of alerting others that the ODE has been
     * solved and that the set_results method has been called.  I.e., this
     * method should return "true" in that case and "false" otherwise.
     * @return flag indicating whether or not the ODE has been solved.
     */
    public boolean is_solved() {
	return(solved_);
    }
    /**
     * Provides the Jacobian of the differential equations.  For clarity, if
     * <i>f<sub>i</sub> = d y<sub>i</sub> / dx</i> (where 
     * <i>i = 0</i> to <i>n-1</i>, with <i>n</i> being the number of 
     * equations) this routine 
     * should compute and return <i>d f<sub>i</sub> / dx</i> and 
     * <i>d f<sub>i</sub> / d y<sub>j</sub></i>.  The former
     * is an <i>n</i> element 1-D array; 
     * the latter an <i>n</i> x <i>n</i> array.  To avoid returning
     * data via the argument list, suggest having the routine return an
     * suggest that this return an <i>n</i> (first index) x <i>n+1</i> 
     * (second index, with
     * "1" representing <i>d f<sub>i</sub> / dx</i>) array.  
     * Whether or not this method is
     * needed depends on the algorithm being used to integrate the equations.
     * @param y array of the local values of the dependent variables
     * @param x local values of the independent variable at which the
     * derivatives are to be evaluated
     * @exception RootSolverException is thrown when something isn't as expected
     * @return array representing the Jacobian
     */
    public double[][] jacobian(double[] y, double x) throws RootSolverException {
	/*
	 * The <code>NumRecRungeKutta</code> integration 
	 * class we're using does not
	 * need this, so we have just a dummy method here.
	 */
	double[][] jac = new double[neq_][neq_+1];
	return(jac);
    }
}

