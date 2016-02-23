/*
 * class DblTextField
 */

package dpscomponents;

import java.awt.TextField;
import java.awt.event.TextListener;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.KeyEvent;

/**
 * A text field that accepts integers or floating point numbers.
 * Its value is returned as a double.
 * 
 * This class is based on and very similar to the
 * IntTextField class appearing in Core Java by
 * Horstmann & Cornell (Sun Microsystems Press / Prentice-Hall, 1997).
 * The original author was Cay Horstmann (his version 1.1, 27 Mar 1997).
 * Their original copyright appears in this source code.
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 19:57:20 $.
 * @author Daren Stotler, Cay Horstmann
 */

/*
 * $Log: DblTextField.java,v $
 * Revision 1.2  2004/10/07 19:57:20  dstotler
 * Change to javadoc comment.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 * 
 * 1.0 08-19-99 Original version.
 */

/*
 * Original author's copyright notice:
 *
 * Cay S. Horstmann & Gary Cornell, Core Java
 * Published By Sun Microsystems Press/Prentice-Hall
 * Copyright (C) 1997 Sun Microsystems Inc.
 * All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this 
 * software and its documentation for NON-COMMERCIAL purposes
 * and without fee is hereby granted provided that this 
 * copyright notice appears in all copies. 
 * 
 * THE AUTHORS AND PUBLISHER MAKE NO REPRESENTATIONS OR 
 * WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER 
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. THE AUTHORS
 * AND PUBLISHER SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED 
 * BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING 
 * THIS SOFTWARE OR ITS DERIVATIVES.
 */
 
public class DblTextField extends TextField 
implements TextListener, KeyListener {

  private String lastValue_;
  private int lastCaretPosition_;

  /**
   * Creates the text field
   * @param defval an integer default,
   * @param size the number of columns in the text field
   */
  public DblTextField(double defval, int size){
    super("" + defval, size);
    addTextListener(this);
    addKeyListener(this);
    lastValue_ = "" + defval;
  }

  /**
   * Requisite method for the KeyListener interface. This version
   * registers only those characters anticipated for a floating point
   * number.
   * @see KeyListener#keyTyped
   */
  public void keyTyped(KeyEvent evt) {
    char ch = evt.getKeyChar();
    if (!('0' <= ch && ch <= '9' 
	  || ch == '-'
	  || ch == '.'
	  || Character.isISOControl(ch)))
      evt.consume();
    else
      lastCaretPosition_ = getCaretPosition();
  }

  /**
   * Null implementation of this method from the KeyListener interface.
   * @see KeyListener#keyPressed
   */
  public void keyPressed(KeyEvent evt) {
  }

  /**
   * Null implementation of this method from the KeyListener interface.
   * @see KeyListener#keyReleased
   */
  public void keyReleased(KeyEvent evt) {
  }

  /**
   * Requisite method for the TextListener interface.
   * The appropriate internal methods are called in response.
   * @see TextListener#textValueChanged
   */
  public void textValueChanged(TextEvent evt) {
    checkValue();  
  }

  /**
   * Checks value of text to determine if it forms a valid double.
   */
  private void checkValue() {
    try {
      Double.valueOf(getText().trim() + "0");
      lastValue_ = getText();
    }
    catch(NumberFormatException e) {
      setText(lastValue_);
      setCaretPosition(lastCaretPosition_);
    }
  }

  /**
   * Get the data value.  If invalid, returns the valid prefix (or 0 if none)
   * This only happens when the field is blank or contains just a 
   * single.
   * @return the data value as a double
   */
   public double getValue() {
     checkValue();
     try {
       return Double.valueOf(getText().trim()).doubleValue();
     }
     catch(NumberFormatException e) {
       return 0; 
     }
   }
}
