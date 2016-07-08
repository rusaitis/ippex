/*
 * class ObservableRatio
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

/**
 * This rather neat class uses allows a relationship (here, a ratio) to
 * be maintained between two Observable doubles. When either of the two
 * changes, the other is modified to maintain the ratio between them.
 *
 * @version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:49 $.
 * @author Daren Stotler
 * @see ObservableDbl
 */

/*
 * $Log: ObservableRatio.java,v $
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.0 02-24-97 Finally give it a number.
 *
 * Implementation really hasn't changed since its original form.
 * The initial application envisioned was for correlating the
 * toroidal plasma current and magnetic field in a tokamak; the
 * ratio of the two can be specified in terms of the safety factor, q.
 */ 
public class ObservableRatio implements Observer {

  private ObservableDbl valueOne_, valueTwo_;
  private double ratio_;

  /**
   * The constructor requires not only the names of the two ObservableDbl's,
   * but also their ratio.
   * @param od1 first ObservableDbl
   * @param od2 second ObservableDbl
   * @param r ratio of the first ObservableDbl to the second
   */
  public ObservableRatio(ObservableDbl od1, ObservableDbl od2, double r) {
    valueOne_ = od1;
    valueOne_.addObserver(this);
    
    valueTwo_ = od2;
    valueTwo_.addObserver(this);

    ratio_ = r;    // ratio_ == valueOne_ / valueTwo_

    if (valueOne_.getValue() == 0. && valueTwo_.getValue() != 0.) {
      valueOne_.setValue(ratio_*valueTwo_.getValue());
    }
    if (valueTwo_.getValue() == 0. && valueOne_.getValue() != 0.) {
      valueTwo_.setValue(valueOne_.getValue() / ratio_);
    }

  }
  /**
   * This method watches a change in one of the two ObservableDbl's
   * and then updates the other. Note: this will probably get called 
   * twice with each change. The second call will have no effect.
   */
  public void update(Observable obs, Object arg) {
    if (obs == valueOne_) {
      valueTwo_.setValue(valueOne_.getValue() / ratio_);
    }
    if (obs == valueTwo_) {
      valueOne_.setValue(ratio_ * valueTwo_.getValue());
    }
  }
}
