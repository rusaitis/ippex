/*
 * class RootSolverException
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
 * Indicates failure of the root solving algorithm. The String argument should
 * provide more detail.
 *
 * @version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:49 $.
 * @author Daren Stotler
 */

/*
 * $Log: RootSolverException.java,v $
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.0 03-11-97 Finally give it a number.
 * 
 * In general, this could do alot more. E.g., following a soft failure of
 * a sophisticated algorithm, it could invoke a simpler more robust one
 * to find the root.
 */
public class RootSolverException extends Exception {

  /**
   * Construct a RootSolverException exception with a message.
   * @param s message describing a particular instance of this exception
   */
  public RootSolverException(String s) {
    super(s);
  }
}

