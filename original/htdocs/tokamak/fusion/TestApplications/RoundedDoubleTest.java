/*
 * class RoundedDoubleTest
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

import dpscomponents.RoundedDouble;
import java.lang.String;
import java.lang.System;

/**
 * This application is just a simple main method for testing on the command 
 * line. Expected output is:
 * <pre>
 * double = 1.6666666666666667
 * default rounded string = 2.
 * with 2 digits = 1.67
 * back to double = 1.67
 * </pre>
 * See the code and README in the 
 * TestApplications directory for more information.
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 19:50:31 $.
 * @author Daren Stotler
 * @see dpscomponents.RoundedDouble
 */

/*
 * $Log: RoundedDoubleTest.java,v $
 * Revision 1.2  2004/10/07 19:50:31  dstotler
 * Added to TestApplications package. Updated expected result.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.0 04-07-97 Moved over from RoundedDouble class.
 */
public class RoundedDoubleTest {

  public static void main(String args[]) {
    double d = 5./3.;
    System.out.println("double = " + d);
    RoundedDouble rd = new RoundedDouble(d);
    System.out.println("default rounded string = " + rd.toString());
    rd.setPrecision(2);
    System.out.println("with 2 digits = " + rd.toString());
    d = rd.toDouble();
    System.out.println("back to double = " + d);
  }
}
