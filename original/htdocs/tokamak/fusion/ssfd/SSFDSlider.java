/*
 * class SSFDSlider.java
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

import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.GridLayout;
import observable.ObservableSlider;
import observable.ObsTextField;
import observable.ObservableDbl;

/** 
 * Sets attributes of the slider at once to allow several similar looking
 * implementations to be declared easily. These values are chosen so
 * as to be appropriate for this application (SteadyStateFusionDemo).
 *
 * @version $Revision: 1.2 $, $Date: 1999/11/19 21:37:04 $.
 * @author Daren Stotler
 */

/*
 * $Log: SSFDSlider.java,v $
 * Revision 1.2  1999/11/19 21:37:04  dstotler
 * Added font to constructor so we could control the size from the
 * applet.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:50  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.1 08-23-99 Replace single slider with combined slider and text field.
 * 1.0 03-05-97 Finally assign a version number. The implementation
 *              is directly analogous to the sample provided by
 *              Benjamin "Quincy" Cabell with the MightySlider.
 */
public class SSFDSlider extends Panel {
  private final int SLIDER_HEIGHT = 20;

  /**
   * Only one method.
   * @param value ObservableDbl upon which the slider is based
   * @param width horizontal width of the slider
   * @param min minimum value to be taken on by the slider
   * @param max maximum value to be taken on by the slider
   * @param cols number of columns in the text field
   * @param color color of the background and bar of the slider
   * @param font font to be used.
   */
  public SSFDSlider(ObservableDbl value, int width, int min, int max, int cols, Color color, Font font) {
    ObservableSlider slider = new ObservableSlider(value);
    slider.SetNumDigits(-1);
    slider.SetMaximum(max);
    slider.SetMinimum(min);
    slider.SetWidth(width);
    slider.SetBarColor(color);
    slider.SetHeight(SLIDER_HEIGHT);
    slider.SetBackgroundColor(color);
    slider.SetTextColor(Color.white);
    slider.SetFont(font);
    slider.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
    slider.SetValue(value.getValue());

    double tempdbl = value.getValue();
    ObsTextField text = new ObsTextField(value, tempdbl, cols);
//    ObsTextField text = new ObsTextField(value, value.getValue(), cols);
// By using darker() here, able to match old slider color.
    text.setBackground(color.darker());  
    text.setForeground(Color.white);
    text.setFont(font);
    text.setSize(width, SLIDER_HEIGHT);

    GridLayout gl = new GridLayout(2,1);
    setLayout(gl);
    add(text);
    add(slider);
  } 
}
