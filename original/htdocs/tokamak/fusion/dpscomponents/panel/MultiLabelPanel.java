/*
 * class MultiLabelPanel
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
package dpscomponents.panel;

import java.lang.String;
import java.awt.Panel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Label;
import java.awt.GridLayout;

/**
 * This does the obvious: generates a single Label using mulitple
 * lines of text.  The only alignment allowed at present is CENTER.
 *
 * @version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:48 $.
 * @author Daren Stotler
 */

/*
 * $Log: MultiLabelPanel.java,v $
 * Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.0 03-04-97 Finally assign a version number. This being a relatively
 *              simple and obvious class, the implementation is 
 *              essentially unchanged from the initial version.
 */
public class MultiLabelPanel extends Panel {
  
  /**
   * Most general constructor, allowing specification of the font
   * and the color of the text.
   * @param lines array of strings representing the lines to be drawn
   * @param num_lines number of lines in that array
   * @param font font to be used for the text
   * @param color this is the color used for the text
   */
  public MultiLabelPanel(String[] lines, int num_lines, Font font, Color color) {
    this(lines, num_lines, font);
    setForeground(color);
  }

  /**
   * Intermediate constructor, allowing specification of the font
   * only.
   * @param lines array of strings representing the lines to be drawn
   * @param num_lines number of lines in that array
   * @param font font to be used for the text
   */
  public MultiLabelPanel(String[] lines, int num_lines, Font font) {
    this(lines, num_lines);
    setFont(font);
  }

  /**
   * Intermediate constructor, number 2, allowing specification of the 
   * color.
   * @param lines array of strings representing the lines to be drawn
   * @param num_lines number of lines in that array
   * @param color this is the color used for the text
   */
  public MultiLabelPanel(String[] lines, int num_lines, Color color) {
    this(lines, num_lines);
    setForeground(color);
  }

  /** Simplest constructor.
   * @param lines array of strings representing the lines to be drawn
   * @param num_lines number of lines in that array
   */
  public MultiLabelPanel(String[] lines, int num_lines) {
    setLayout(new GridLayout(num_lines, 0));
    for (int i=0; i<num_lines; i++) {
      add(new Label(lines[i], Label.CENTER));
    }
  }
}








