/*
 * class SolversTest
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
package TestApplications;

import rootsolvers.Solvers;
import rootsolvers.Solved;
import rootsolvers.RootSolverException;

/**
 * This simple application demonstrates and tests the bisection method in
 * the rootsolvers package. See the code and README in the 
 * TestApplications directory for more information.
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 19:50:58 $.
 * @author Daren Stotler
 * @see rootsolvers.Solvers
 */

/*
 * $Log: SolversTest.java,v $
 * Revision 1.2  2004/10/07 19:50:58  dstotler
 * Added to TestApplications package.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 2.0 01-21-98 Switched to simpler interface approach; changed names.
 * 1.0 03-31-97 Initial implementation.
 */
public class SolversTest implements Solved {

  private double a, b, c;

  /** 
   * This application main routine sets up a member of the SolversTest
   * and solves it for both of the roots.
   */
  public static void main(String args[]) {

    SolversTest bt = new SolversTest(1., 2., -35.);
    double root=0.;

    try {                                  // 1st root
      root=Solvers.bisection(bt,4.5,6.2);
    } catch (RootSolverException e) {
      System.out.println("Could not find first root, " + e.getMessage());
    }
    System.out.println("1st root, x = " + root + ", should be between 4.999 and 5.001");

    try {                                   // 2nd root
      root=Solvers.bisection(bt,-6.2,-8.5);
    } catch (RootSolverException f) {
      System.out.println("Could not find second root, " + f.getMessage());
    }
    System.out.println("2nd root, x = " + root + ", should be between -7.001 and -6.999");
  }

  /**
   * This constructor sets the coefficients of the quadratic to be 
   * computed by the calcfOfX method.
   */
  public SolversTest(double alpha, double beta, double gamma) {
    a = alpha;
    b = beta;
    c = gamma;
  }

  /**
   * This is the function to be solved: a simple quadratic for 
   * demonstration purposes.
   * @param x point at which function is to be evaluated
   * @return function value for use in solution algorithm
   */
  public double calcfOfX(double x) {
    return (a*x*x + b*x + c);
  }
}
