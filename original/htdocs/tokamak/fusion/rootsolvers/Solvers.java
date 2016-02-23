/*
 * class Bisection
 *
 *    Copyright (C) 1996-1999, Daren Stotler
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
 * This class is intended to contain a variety of root-solving algorithms,
 * each with possibly distinct interfaces. The function to be solved is
 * made available through the Solved interface with its calcf)OfX method.
 * Hence, each algorithm method will take as a first argument a Solved object.
 * Done in this manner, these algorithm methods can all be static. 
 * It is also sensible to have the algorithm methods return the root as a
 * double. The rest of the arguments may vary with the algorithm. Use of
 * this class and the Solved interface is demonstrated in the SolversTest
 * application. This approach to using an interface closely follows the 
 * examples in Core Java 1.1, Volume 1 by Horstmann & Cornell.
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 20:27:55 $.
 * @author Daren Stotler
 * @see Solvers
 * @see Solved
 * @see RootSolverException
 * @see TestApplications.SolversTest
 */

/*
 * $Log: Solvers.java,v $
 * Revision 1.2  2004/10/07 20:27:55  dstotler
 * Change to javadoc comments.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 2.0 01-21-98 Overhaul approach in favor of the simpler one from Horstmann.
 * 1.0 02-13-97 Finally give it a number.
 * 
 */
public class Solvers {

  /**
   * If one can bracket the root, the bisection algorithm can be used and 
   * will find one root with virtual certainty. For more information, see 
   * W. H. Press, B. P. Flannery, S. A. Teukolsky and W. T. Vetterling, 
   * Numerical Recipes (Cambridge Univ., New York, 1988).
   * @param s Solved object containing the function to be solved
   * @param x1 one bound on the range of the root
   * @param x2 other bound
   * @exception RootSolverException thrown when a root isn't found
   * @return root of the function
   */
  public static double bisection(Solved s, double x1, double x2) throws RootSolverException {
    double xacc = 1.e-3;
    int jmax = 40;

    double rtbis, dx, xmid;

    double fmid = s.calcfOfX(x2);

    double f = s.calcfOfX(x1);
    
    if (f * fmid > 0)
      throw new RootSolverException("Interval does not bracket root");

    if (f < 0.) {
      rtbis = x1;
      dx = x2 - x1;
    }
    else {
      rtbis = x2;
      dx = x1 - x2;
    }

    for (int j=0; j<jmax; j++) {
      dx = 0.5 * dx;
      xmid = rtbis + dx;
      fmid = s.calcfOfX(xmid);
      if (fmid <= 0.) rtbis = xmid;
      if (Math.abs(dx) < xacc || fmid == 0.) return xmid;
    }
    throw new RootSolverException("Convergence criteria unsatisfied");
  }
}


