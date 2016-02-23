/*
 * class ObservableSlider
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
package observable;

import dpscomponents.slider.FormatSlider;
import java.util.Observer;
import java.util.Observable;

/**
 * This class provides a slider with a value tied to an ObservableDbl.
 * The base class is the FormatSlider which displays floating
 * point values.
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 20:22:15 $.
 * @author Daren Stotler
 * @see dpscomponents.slider.FormatSlider
 * @see ObservableDbl
 * @see TestApplications.ObservableTest
 */

/*
 * $Log: ObservableSlider.java,v $
 * Revision 1.2  2004/10/07 20:22:15  dstotler
 * Changes to javadoc comments.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.0 02-20-97 Finally give it a number.
 *
 * The implementation here hasn't really changed since we implemented
 * Observables here. At some point, though, we did decide to add
 * the maximum and minimum features here and with ObservableDbl.
 */
public class ObservableSlider extends FormatSlider 
implements Observer {

  private ObservableDbl dblValue_;

  /** 
   * Initializes the class. Lets the ObservableDbl know we're here
   * and grabs its value to use initially.
   * @param new_value ObservableDbl associated with this slider
   */
  public ObservableSlider (ObservableDbl new_value) {
    super();
    dblValue_ = new_value;
    dblValue_.addObserver(this);
    SetValue(dblValue_.getValue());
  }

  /** 
   * In the original MightySlider class, this method was essentially
   * abstract; the subclass had to implement some specific action here.
   * By going to the Observable approach, the need for this specific
   * connection is removed & the following behavior becomes very general.
   * Note that we have opted not to do likewise for the Motion method;
   * the constant message passing it entails seems distasteful.
   */
  public void Release() {
    dblValue_.setValue(GetValue());
  }

  /**
   * This is the one and only method of the Observer interface; it
   * doesn't really do anything unless there is some other do-dad
   * around which can also alter the ObservableDbl used in this object's
   * constructor. I've no idea what the
   * arguments have to do with anything (so much for the generality
   * of the interface).
   * @param obs observable which was updated 
   * @param arg what is being updated (not used)
   */
  public void update(Observable obs, Object arg) {
    if (obs == dblValue_) {
      SetValue(dblValue_.getValue());
    }
  }

  /**
   * Ties the maximum shown on the slider (int) with that of the
   * ObservableDbl.
   * @param max maximum value to be allowed for the slider
   */
  public void SetMaximum(int max) {
    super.SetMaximum(max);
    dblValue_.setMaximum((double) max);
  }

  /**
   * Ties the minimum shown on the slider (int) with that of the
   * ObservableDbl.
   * @param min minimum value to be allowed for the slider
   */
  public void SetMinimum(int min) {
    super.SetMinimum(min);
    dblValue_.setMinimum((double) min);
  }
}
