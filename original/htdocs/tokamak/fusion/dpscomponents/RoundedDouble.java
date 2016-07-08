/*
 * class RoundedDouble
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
package dpscomponents;

import java.lang.String;
import java.lang.StringBuffer;
import java.lang.Math;
import java.lang.System;
import java.lang.Double;

/**
 * This class just puts a little more framework around the number
 * formatting apparatus developed for use with sliders. Add a few
 * bells and whistles...
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 19:57:53 $.
 * @author Daren Stotler
 * @see TestApplications.RoundedDoubleTest
 */

/*
 * $Log: RoundedDouble.java,v $
 * Revision 1.2  2004/10/07 19:57:53  dstotler
 * Added a call to Math.round to prevent roundoff error from fouling the result. Change to javadoc comment.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.1 04-07-97 Move main method to separate RoundedDoubleTest class.
 * 1.0 02-27-97 Extract main method from elsewhere and set up class.
 */
public class RoundedDouble {

  private int digits;
  private double thedouble;

  /**
   * Simplest constructor.
   * @param x double which will be rounded.
   */
  public RoundedDouble(double x) {
    this(x,0);
  }

  /**
   * Complete constructor.
   * @param x double which will be rounded.
   * @param n number of digits to the right of the decimal.
   */
  public RoundedDouble(double x, int n) {
    thedouble = x;
    digits = n;
  }

  /**
   * The principal reason for the existence of this class is to generate
   * nicely formatted fixed point numbers. This method provides the string
   * that's needed.
   * @return string corresponding to the rounded double.
   */
  public String toString() {

    if (digits < 0) {
      digits = 0;                          // In lieu of throwing an exception
    }

    double mult = Math.pow(10.d,(double) digits);
    double d = Math.round(thedouble * mult) / mult;

    int dint;
    StringBuffer strb = new StringBuffer();
    if (d >= 0) {
      dint = (int) Math.floor(d);
    }
    else {
      dint = (int) Math.ceil(d);  // "-0.1" etc. lose the "-" here =>
      if (dint == 0) {
	  strb.append("-");             
	}
    }
    strb.append(dint);

    double dfrac = Math.abs(d - dint);
    /*
     * As of Java SDK 1.4.2_05, noticed that this (in corresponding
     * TestApplication) can be off in the last digit, actually 
     * foiling the desired rounding.  This statement is intended
     * to correct that.
     */
    double dfrac2 = Math.round(dfrac * mult) / mult;
    String str = String.valueOf(dfrac2);
    int strLength = str.length();
    /*
     * Trailing zeroes are dropped when the string is formed, as is the
     * decimal for integral values of d. The following replace them.
     */
    if (strLength == 0 || strLength == 1 || strLength == 2) {
      strb.append(".");
      strLength = 2;
    }
    else {
      str = str.substring(1,Math.min(digits+2,strLength));
      strb.append(str);
    }

    for (int i=1; i<=digits-strLength+2; i++) {
      strb.append("0");
    }

    return strb.toString();
  }

  /**
   * Allows the precision of the floating point number to be altered. Since
   * the original double value is not changed, this can be increased or
   * decreased as needed.
   * @param p number of digits to the right of the decimal.
   */
  public void setPrecision(int p) {
    digits = p;
  }

  /**
   * The nicely formatted string of previously specified precision is
   * returned as a double.
   * @return double rounded off to a specified precision.
   */
  public double toDouble() {
    String s = toString();
    Double d = new Double(s);
    return d.doubleValue();
  }
}
