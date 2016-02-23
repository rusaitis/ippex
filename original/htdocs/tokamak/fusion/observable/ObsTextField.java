/*
 * class ObsTextField
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

import java.util.Observer;
import java.util.Observable;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import dpscomponents.DblTextField;

/**
 * This simple extension of the DblTextField ties the value entered
 * into the field with an ObservableDbl variable.
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 20:21:33 $.
 * @author Daren Stotler
 * @see dpscomponents.DblTextField
 * @see ObservableDbl
 * @see TestApplications.ObservableTest
 */

/*
 * $Log: ObsTextField.java,v $
 * Revision 1.2  2004/10/07 20:21:33  dstotler
 * Add reference to corresponding TestApplication.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.1 08-26-99 Replace RoundedDouble with NumberFormat.
 * 1.0 08-23-99 Initial version.
 */
public class ObsTextField extends DblTextField implements Observer {

  private ObservableDbl dblValue_;
  protected int precision_ = 2;
  private NumberFormat nf_;

  /**
   * Constructor.
   * @param od ObservableDbl to be tied to this text field
   * @param defval default value to be displayed in the text field
   * @param size the number of columns in the text field
   */
  public ObsTextField(ObservableDbl od, double defval, int size) {
    super(defval, size);
    dblValue_ = od;
    dblValue_.addObserver(this);
    nf_ = NumberFormat.getInstance();
    nf_.setMinimumFractionDigits(precision_);
    nf_.setMaximumFractionDigits(precision_);
  }

  /**
   * Requisite method for the Observer interface. It's called whenever
   * an Observer changes. This one updates the text field to reflect
   * the change in the ObservableDbl.
   * @see Observer#update
   */
  public void update(Observable obs, Object arg) {
    if (obs == dblValue_) {
      setText(nf_.format(dblValue_.getValue()));
    }
  }

  /**
   * This method overrides the corresponding KeyListener method in
   * DblTextField. It watches for the "Enter" key and checks the
   * text when it appears. The ObservableDbl's maximum and minimum
   * are also enforced.
   * @see java.awt.event.KeyListener#keyReleased
   */
  public void keyReleased(KeyEvent evt) {
    if (evt.getKeyCode() == evt.VK_ENTER) {
/*
   Do this set first, and then reset to match min and max. Otherwise,
   ObservableDbl's  setValue may not notice the change and this
   update method may not get called.
*/
      dblValue_.setValue(getValue());
      if (getValue() < dblValue_.getMinimum()) {
	dblValue_.setValue(dblValue_.getMinimum());
      }
      else if (getValue() > dblValue_.getMaximum()) {
	dblValue_.setValue(dblValue_.getMaximum());
      }
    }
  }
}
