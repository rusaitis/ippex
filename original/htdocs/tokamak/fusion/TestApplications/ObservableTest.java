/*
 * class ObservableTest
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

import observable.ObservableDbl;
import observable.ObservableLabel;
import observable.ObservableSlider;
import java.lang.String;
import java.awt.Frame;
import java.awt.FlowLayout;
import observable.ObsTextField;

/**
 * This is a simple application to test and demonstrate the ObservableSlider,
 * ObservableDbl, ObservableLabel, and ObsTextField classes. In case the 
 * action is not obvious, moving and releasing the slider bar at the top 
 * of the Frame sets the value of the ObservableDbl. The ObservableLabel at 
 * the bottom is its Observer. As is the purpose of that class, the value 
 * displayed by the label is updated without any additional connections
 * between these two components. The same is true for the text field. 
 * Entering a text value causes the other two to be updated when the "Enter"
 * key is pressed. See the code and README in the TestApplications directory 
 * for more information.
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 19:48:22 $.
 * @author Daren Stotler
 * @see observable.ObservableDbl
 * @see observable.ObservableLabel
 * @see observable.ObservableSlider
 * @see observable.ObsTextField
 */

/*
 * $Log: ObservableTest.java,v $
 * Revision 1.2  2004/10/07 19:48:22  dstotler
 * Added to TestApplications package.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:47  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.1 08-30-99 Added ObsTextField component.
 * 1.0 04-07-97 Initial implementation.
 */
public class ObservableTest {

  public static void main(String args[]) {

    Frame f = new Frame("Observable Test");
    
    ObservableDbl od = new ObservableDbl();

    ObservableSlider os = new ObservableSlider(od);
    os.SetNumDigits(2);
    os.SetMaximum(100);
    os.SetMinimum(0);
    os.SetWidth(200);
    os.SetHeight(40);

    ObservableLabel ol = new ObservableLabel(od,"Slider = ",ObservableLabel.CENTER);

    ObsTextField st = new ObsTextField(od,0.,10);

    f.setLayout(new FlowLayout());
    f.add(os);
    f.add(ol);
    f.add(st);

    f.setSize(300, 150);
    f.setVisible(true);
  }
}
