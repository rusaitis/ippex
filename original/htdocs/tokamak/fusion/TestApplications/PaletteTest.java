/*
 * class PaletteTest
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

import palette.PaletteServer;
import palette.PaletteScale;
import java.lang.String;
import java.awt.Font;
import java.awt.Frame;
import java.awt.FlowLayout;
import java.awt.image.IndexColorModel;

/**
 * This is a simple application to demonstrate the usage of the PaletteServer
 * and PaletteScale classes. See the code and README in the TestApplications
 * directory for more information.
 * 
 * @version $Revision: 1.3 $, $Date: 2004/10/07 19:49:08 $.
 * @author Daren Stotler
 * @see palette.PaletteServer
 * @see palette.PaletteScale
 */

/*
 * $Log: PaletteTest.java,v $
 * Revision 1.3  2004/10/07 19:49:08  dstotler
 * Added to TestApplications package.
 *
 * Revision 1.2  2000/01/21 20:39:05  dstotler
 * Added font to argument list for PaletteScale.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.0 04-03-97 Initial implementation.
 */
public class PaletteTest {

  /**
   * This method defines a frame, gets a palette, then draws a scale
   * using the palette.
   */
  public static void main(String args[]) {

    Frame f = new Frame("Palette Test");
    int num_colors=40;
    IndexColorModel hot_metal = PaletteServer.getPalette("hotMetal",num_colors);
    String[] labels = {"Horizontal", "0", "10", "20", "30", "40"};
    Font smalllabelfont = new Font("TimesRoman", Font.BOLD, 14);
    PaletteScale scale = new PaletteScale(hot_metal, 150, 50, labels, 
					  smalllabelfont);
    f.setLayout(new FlowLayout());
    f.add(scale);
    f.setSize(200, 200);
    f.setVisible(true);
  }
}
