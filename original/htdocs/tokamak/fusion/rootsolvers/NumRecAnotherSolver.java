/*
 * class NumRecAnotherSolver
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
 * This is a compilable, DUMMY, skeleton class demonstrating the 
 * intended approach to extending NumRecRungeKutta to utilize another 
 * algorithm.  The original "driver" method would be reused, but the 
 * "stepper" would be hidden. 
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:25:16 $.
 * @author Daren Stotler
 * @see ODESolved
 * @see NumRecRungeKutta
 */

/*
 * $Log: NumRecAnotherSolver.java,v $
 * Revision 1.1  2004/10/07 20:25:16  dstotler
 * Added to repository.
 *
 */
public class NumRecAnotherSolver extends NumRecRungeKutta {

    public static double[] stepper(double[] y, double[] dydx, double x, 
				   double htry, double[] yscal)
	throws RootSolverException {
	double[] xyOut = new double[numEq_+1];

	for (int i=0; i < numEq_+1; i++) {
	    xyOut[i] = 0.;
	}
	return (xyOut);
    }
}
