/*
 * class ExpTauE
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

import java.lang.Math;

/**
 * Uses a standard power law expression for the energy confinement time.
 * The exponents are specified at instantiation. The resulting 
 * expression can then be evaluated for when a set of global plasma
 * parameters is provided.
 *
 * There is also a <a href="http://w3.pppl.gov/~dstotler/SystemsCode/">
 * User's Manual</a> for the
 * full-featured FORTRAN code upon which this code is based.
 * The corresponding equation numbers are mentioned in the code below.
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 20:28:22 $.
 * @author Daren Stotler
 */

/*
 * $Log: ExpTauE.java,v $
 * Revision 1.2  2004/10/07 20:28:22  dstotler
 * Change to javadoc comments.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.0 02-13-97 Finally give it a number.
 *
 * This is a simple and obvious class. It hasn't changed since the initial
 * implementation.
 */
public class ExpTauE {

  /*
   * The constructor stores the overall coefficient and 8 exponents
   * which comprise the power law fit expression in this array, Eq. 42.
   */
  private double txp_[] = new double [9];

  /**
   * Defines a scaling expression for the energy confinement time.
   * @param constant overall multiplicative constant
   * @param mass_exp exponent on the average ion mass
   * @param elongation_exp exponent on the plasma elongation
   * @param current_exp exponent on the plasma current
   * @param density_exp exponent on the line-averaged electron density
   * @param bfield_exp exponent on the toroidal magnetic field
   * @param rminor_exp exponent on the plasma minor radius
   * @param rmajor_exp exponent on the plasma major radius
   * @param pinput_exp exponent on the net plasma input power
   */
  public ExpTauE(double constant, double mass_exp, double elongation_exp, 
          double current_exp, double density_exp, double bfield_exp, 
          double rminor_exp, double rmajor_exp, double pinput_exp) 
    {
      txp_[0] = constant;
      txp_[1] = mass_exp;
      txp_[2] = elongation_exp;
      txp_[3] = current_exp;
      txp_[4] = density_exp;
      txp_[5] = bfield_exp;
      txp_[6] = rminor_exp;
      txp_[7] = rmajor_exp;
      txp_[8] = pinput_exp;
    }

  /**
   * Evaluate the scaling expression for the energy confinement time.
   * @param aibar average ion mass (amu)
   * @param elongation the plasma elongation (dimensionless)
   * @param current plasma current (in amps)
   * @param density line-averaged electron density (in m**-3)
   * @param bfield toroidal magnetic field (in tesla)
   * @param rminor plasma minor radius (in m)
   * @param rmajor plasma major radius (in m)
   * @param pinput net input power to the plasma (in watts)
   * @return energy confinement time in seconds
   */
  public double calcExpTauE(double aibar, double elongation, double current, 
                            double density, double bfield, double rminor, 
                            double rmajor, double pinput) 
    {
      double ztaue = txp_[0] 
        * Math.pow(aibar,txp_[1]) 
        * Math.pow(elongation,txp_[2])
        * Math.pow(current*1.e-6,txp_[3])
        * Math.pow(density*1.e-19,txp_[4])
        * Math.pow(bfield,txp_[5])
        * Math.pow(rminor,txp_[6])
        * Math.pow(rmajor,txp_[7])
        * Math.pow(Math.max(pinput*1.e-6,1.),txp_[8]); // Note minimum value!

      return (ztaue);
    }
}
                         
