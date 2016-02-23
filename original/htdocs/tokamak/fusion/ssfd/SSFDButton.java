/*
 * class SSFDButton.java
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
import java.awt.Font;
import java.awt.event.MouseEvent;
import dpscomponents.button.MightyButton;

/**
 * Specific button class for SteadyStateFusionDemo. Most of the parameters
 * just get passed on to the superclass. The only real need for this
 * class is to initiate the solution process in SteadyStateFusionDemo
 * when the button is pressed.
 *
 * @version $Revision: 1.2 $, $Date: 1999/11/19 21:36:58 $.
 * @author Daren Stotler
 * @see MightyButton
 */

/*
 * $Log: SSFDButton.java,v $
 * Revision 1.2  1999/11/19 21:36:58  dstotler
 * Added font to constructor so we could control the size from the
 * applet.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:50  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 * 
 * 1.0 03-05-97 Finally assign a version number. This class is really
 *              trivial.
 */
public class SSFDButton extends MightyButton {
  private SteadyStateFusionDemo foo_;

  /**
   * Lone constructor.
   * @param foo instance of the SteadyStateFusionDemo which uses this button
   * @param name used to identify the file names containing the button images
   * @param buttontext text which will appear on the button 
   * @param type type of button as per those prescribed for MightyButton
   * @param font font to be used.
   */
  public SSFDButton(SteadyStateFusionDemo foo, String name, String buttontext, 
	     int type, Font font) {
    super(foo, name, buttontext, type);
    SetFont(font);
    foo_ = foo;
    setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
   }
    
  /**
   * Called when the button is clicked. This overrides the trivial method
   * from MouseListener implemented in MightyButton. The only action is 
   * to execute the solveReactor method of the SteadyStateFusionDemo object.
  */
  public void mouseClicked(MouseEvent evt) {
    foo_.solveReactor();
  }
}
