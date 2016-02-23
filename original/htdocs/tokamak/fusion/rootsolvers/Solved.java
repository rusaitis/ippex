/*
 * interface Solved
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
 * An interface which provides the function being solved to root-finding
 * algorithms. This approach to using an interface closely follows the 
 * examples in Core Java 1.1, Volume 1 by Horstmann & Cornell.
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 20:27:37 $.
 * @author Daren Stotler
 * @see RootSolverException
 * @see TestApplications.SolversTest
 */

/*
 * $Log: Solved.java,v $
 * Revision 1.2  2004/10/07 20:27:37  dstotler
 * Added reference to corresponding TestApplication.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 2.0 01-21-98 The end result is simpler than elegant...
 * 1.1 12-09-97 Try a more elegant approach.
 * 1.0 02-12-97 Finally give it a number.
 */
public interface Solved {

  /**
   * This is the function to be solved.
   * @param x point at which function is to be evaluated
   * @return function value for use in solution algorithm
   */
  public double calcfOfX(double x);
}
