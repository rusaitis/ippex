/*
 * class ObservableLabel 
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

import java.lang.String;
import java.lang.StringBuffer;
import java.awt.Label;
import java.util.Observer;
import java.util.Observable;
import java.text.NumberFormat;

/**
 * Just a simple-minded class to make a label out of the value of
 * an ObservableDbl.
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 20:22:03 $.
 * @author Daren Stotler
 * @see ObservableDbl
 * @see TestApplications.ObservableTest
 */

/*
 * $Log: ObservableLabel.java,v $
 * Revision 1.2  2004/10/07 20:22:03  dstotler
 * Change to javadoc comments.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.1 08-26-99 Replace RoundedDouble with NumberFormat
 * 1.0 02-25-97 Finally give it a number.
 *
 * There have not been any significant changes since original implementation.
 */
public class ObservableLabel extends Label implements Observer  {

  private ObservableDbl itsValue_;
  private String descriptor1_, descriptor2_;
  private NumberFormat nf_;

  /*
   * Number of digits displayed for the double
   */
  protected int precision_ = 2;

  /**
   * Simplest constructor: just the double and no other text
   * @param od an ObservableDbl
   */
  public ObservableLabel(ObservableDbl od) {
    this(od, "");
  }

  /**
   * Another constructor, this time with just one string.
   * @param od an ObservableDbl
   * @param s1 string to appear before the double in the label
   */
  public ObservableLabel(ObservableDbl od, String s1) {
    this(od, s1, LEFT);
  }

  /**
   * Another constructor, this time with one string and an 
   * alignment flag.
   * @param od an ObservableDbl
   * @param s1 string to appear before the double in the label
   * @param align alignment flag (as in Label class)
   * @see Label
   */
  public ObservableLabel(ObservableDbl od, String s1, int align) {
    this(od, s1, "", align);
  }

  /**
   * Last (for now) constructor. Have two strings and an 
   * alignment flag.
   * @param od an ObservableDbl
   * @param s1 string to appear before the double in the label
   * @param s2 string to appear after the double in the label
   * @param align alignment flag (as in Label class)
   * @see Label
   */
  public ObservableLabel(ObservableDbl od, String s1, String s2, int align) {
    itsValue_ = od;
    itsValue_.addObserver(this);
    descriptor1_ = s1;
    descriptor2_ = s2;
    nf_ = NumberFormat.getInstance();
    nf_.setMinimumFractionDigits(precision_);
    nf_.setMaximumFractionDigits(precision_);
    setAlignment(align);
    /*
     * Force initial construction of the whole string.
     * Afterwards it will be altered with changes in 
     * the ObservableDbl.
     */
    update(itsValue_, this);
  }

  /**
   * The one and only method of the Observer interface. This will be
   * called when the ObservableDbl gets called. This method will update
   * the Label accordingly.
   * @param obs observable which was updated
   * @param arg what is being updated
   */
  public void update(Observable obs, Object arg) {
    if (obs == itsValue_) {
      StringBuffer sb1 = new StringBuffer(descriptor1_);
      StringBuffer sb2 = new StringBuffer(descriptor2_);
      String str = ((sb1.append(nf_.format(itsValue_.getValue()))).append(descriptor2_)).toString();
      setText(str);
    }
  } 
   
}
