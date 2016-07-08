/*
 * class SteadyStateFusionReactor
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
package ssfd;

import rootsolvers.Solved;
import observable.ObservableDbl;

/**
 * This class takes an instance of the FusionReactor class and solves
 * for the value of the parameter Ox which yields calcdWdt = 0, i.e.,
 * steady state. The neat thing about using an ObservableDbl for the
 * solution parameter is that in principle any of the FusionReactor
 * ObservableDbl's could be used. In practice, though, only for a
 * few (at least one!) of them can one bound the root as is required
 * for the bisection technique.
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 20:28:36 $.
 * @author Daren Stotler
 * @see ssfd.SteadyStateFusionDemo#init
 * @see FusionReactor
 * @see ssfd.SteadyStateFusionDemo#solveReactor
 * @see rootsolvers.Solvers
 */

/*
 * $Log: SteadyStateFusionReactor.java,v $
 * Revision 1.2  2004/10/07 20:28:36  dstotler
 * Change to javadoc comments.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:50  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.1 01-22-98 Replace root-solvers with simpler interface approach.
 * 1.0 02-13-97 Finally give it a number.
 *
 * The methods in this class have changed little since the initial
 * implementation taken from the ASPECT code, apart from the introduction
 * of Observables.
 */
public class SteadyStateFusionReactor implements Solved {

  private FusionReactor reactorSolved;
  private ObservableDbl Ox;

  /**
   * Initializes the class variables corresponding to the parameters:
   * @param f instance of FusionReactor which is being solved
   * @param od variable to be used in the solution algorithm
   */
  public SteadyStateFusionReactor(FusionReactor f, ObservableDbl od) {
    reactorSolved = f;
    Ox = od;
  }

  /**
   * The function being solved is essentially the time rate of change
   * of the total plasma energy, calcdWdt from the FusionReactor
   * class. Note the use of the ObservableDbl to subtly transfer the
   * root information back out. And, be aware that the 
   * IncompleteReactorExcpn is being caught here, for better or worse.
   * @param x double representing the solution parameter
   * @return function value
   */
  public double calcfOfX(double x) {
    Ox.setValue(x);
    double fX;
    try {
      fX = reactorSolved.calcdWdt();
    } catch (IncompleteReactorExcpn e) {
      System.out.println("sSFR.calcfOfX caught exception: " + e.getMessage());
      return (0.);
    }
    return (fX);
  }
}

