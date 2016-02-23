/*
 * class ObservableDbl
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

import java.util.Observable;

/**
 * Observable mechanism for a double variable. Adapted from
 * Listing 21.14 of "Special Edition, Using Java". Added the
 * maximum and minimum features.
 * 
 * @version $Revision: 1.2 $, $Date: 2004/10/07 20:21:52 $.
 * @author Daren Stotler
 * @see TestApplications.ObservableTest
 */

/*
 * $Log: ObservableDbl.java,v $
 * Revision 1.2  2004/10/07 20:21:52  dstotler
 * Change to javadoc comments.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 * 
 * 1.0 02-24-97 Finally give it a number.
 *
 * This implementation closely follows that of ObservableInt in
 * the "Special Edition, Using Java" book. The maximum and minimum
 * features proved desirable when used in conjunction with sliders
 * and with variables having a well-defined range.
 */
public class ObservableDbl extends Observable {
  private double value_, max_, min_;

  /**
   * Simplest constructor with a default value initialization.
   */
  public ObservableDbl() {
    value_ = 0.;            // Default
  }

  /**
   * Initializes the class instance with a specified value for
   * the observable double.
   * @param new_value initial value
   */
  public ObservableDbl(double new_value) {
    value_ = new_value;
  }

  /**
   * Call this method to change the value of the observable.
   * Observers will be notified of the change.
   * @param new_value altered value for double.
   */
  public synchronized void setValue(double new_value) {
    /*
     * Be sure the value really is changing...
     */
    if (new_value != value_) {
      value_ = new_value;
      setChanged();
      notifyObservers();
    }
  }

  /**
   * Get the current value of the observable. 
   */
  public synchronized double getValue() {
    return value_;
  }

  /**
   * Set the maximum allowed value for the ObservableDbl.
   * @param m maximum value
   */
  public synchronized void setMaximum(double m) {
    max_ = m;
  }

  /**
   * Get the maximum allowed value for the ObservableDbl.
   */
  public synchronized double getMaximum() {
    return max_;
  }

  /**
   * Set the minimum allowed value for the ObservableDbl.
   * @param m minimum value
   */
  public synchronized void setMinimum(double m) {
    min_ = m;
  }

  /**
   * Get the minimum allowed value for the ObservableDbl.
   */
  public synchronized double getMinimum() {
    return min_;
  }
}
      
