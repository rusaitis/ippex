/*
 * class IncompleteReactorExcpn
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

import java.lang.String;

/**
 * Indicates that one or more of the parameters needed to specify the
 * "reactor" has been set incorrectly (e.g., is negative). Note that
 * the name of this class was abbreviated to keep the file names below
 * 32 characters; had a problem with this on Macs.
 *
 * @version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:49 $.
 * @author Daren Stotler
 */

 /*
  * $Log: IncompleteReactorExcpn.java,v $
  * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
  * Import SSFD into CVS
  *
  *
  * VERSION HISTORY PRIOR TO CVS:
  *
  * 1.0 02-12-97 Finally give it a number.
  *
  * Apart from the name change, hasn't been altered since original
  * creation.
  */
public class IncompleteReactorExcpn extends Exception {

  /**
   * Construct an IncompleteReactorExcpn exception with a message.
   * @param s message describing a particular instance of this exception
   */
  IncompleteReactorExcpn(String s) {
    super(s);
  }
}
