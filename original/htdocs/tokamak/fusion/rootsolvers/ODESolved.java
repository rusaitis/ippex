/*
 * interface ODESolved
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
 * An interface which provides information needed to integrate an ordinary
 * differential equation.  At least for the moment, this is specific to
 * initial value problems. 
 *
 * @version $Revision: 1.2 $, $Date: 2004/11/30 22:13:21 $.
 * @author Daren Stotler
 * @see NumRecRungeKutta
 * @see Solved
 * @see Solvers
 * @see TestApplications.TestODESolved
 */

/*
 * $Log: ODESolved.java,v $
 * Revision 1.2  2004/11/30 22:13:21  dstotler
 * Add exception to get_initial_ys.
 *
 * Revision 1.1  2004/10/07 20:26:53  dstotler
 * Added to repository.
 *
 */
public interface ODESolved {
    /**
     * Provides the derivatives of the dependent variables (possibly more
     * than one) with respect to the independent variable.
     * @param y array of the local values of the dependent variables
     * @param x local values of the independent variable at which the
     * derivatives are to be evaluated
     * @exception RootSolverException is thrown when something isn't as expected
     * @return array of values of the derivatives
     */
    public double[] derivatives(double[] y, double x) throws RootSolverException;
    /**
     * Returns the initial value for the independent variable.
     */
    public double get_initial_x();
    /**
     * Returns the initial array of values of the dependent variable.
     */
    public double[] get_initial_ys() throws RootSolverException;;
    /**
     * Returns the desired relative integration accuracy.  Whether and how
     * this parameter is used depends on the integration routine.
     */
    public double get_relative_error();
    /**
     * Returns the desired absolute integration accuracy.  Whether and how
     * this parameter is used depends on the integration routine.
     */
    public double get_absolute_error();
    /**
     * Returns the (absolute) initial step size.  
     */
    public double get_initial_step_size();
    /**
     * Returns the minimum step size.  Some integration routines may allow 
     * this to be zero so that other mechanisms are invoked to stop failed 
     * (i.e., not making adequate progress) integrations.
     */
    public double get_min_step_size();
    /**
     * Returns an array of one or more values at which the solution of the
     * ODE is desired.
     */
    public double[] get_desired_xs();
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
    public void set_results(double[][] y, double[] x) throws RootSolverException;
    /*
     * This method provides a means of alerting others that the ODE has been
     * solved and that the set_results method has been called.  I.e., this
     * method should return "true" in that case and "false" otherwise.
     * @return flag indicating whether or not the ODE has been solved.
     */
    public boolean is_solved();
    /**
     * Provides the Jacobian of the differential equations.  For clarity, if
     * f[i] = d y[i] / dx (where i=0->n-1, n=number of equations) this routine 
     * should compute and return d f[i] / dx and d f[i] / d y[j].  The former
     * is an n element 1-D array; the latter an n x n array.  To avoid returning
     * data via the argument list, suggest having the routine return an
     * suggest that this return an n (first index) x n+1 (second index, with
     * "1" representing d f[i] / dx) array.  Whether or not this method is
     * needed depends on the algorithm being used to integrate the equations.
     * @param y array of the local values of the dependent variables
     * @param x local values of the independent variable at which the
     * derivatives are to be evaluated
     * @exception RootSolverException is thrown when something isn't as expected
     * @return array representing the Jacobian
     */
    public double[][] jacobian(double[] y, double x) throws RootSolverException;
}

