/*
 * class FormatSlider
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
package dpscomponents.slider;

import java.awt.Graphics;
import java.lang.String;
import java.lang.Math;
import java.text.NumberFormat;

/**
 * Simple class to override the paint method of MightySlider so as to
 * format the slider value with a specified number of digits. If a 
 * negative number of digits is requested, the text and the corresponding
 * text area are not drawn at all.
 *
 * @version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:48 $.
 * @author Daren Stotler
 * @see MightySlider
 */

/*
 * $Log: FormatSlider.java,v $
 * Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.3 08-26-99 Replace RoundedDouble with NumberFormat.
 * 1.2 08-19-99 If numDigits_ is negative, don't draw string or text area.
 * 1.1 12-14-98 Allow negative values of numDigits_ to signify that no 
 *              value string is printed (for use with connected text input).
 * 1.0 04-09-97 Finally give it a number.
 */
public class FormatSlider extends MightySlider {

  protected int numDigits_=0;
  private NumberFormat nf_;

  public FormatSlider () {
    super();
    nf_ = NumberFormat.getInstance();
    nf_.setMinimumFractionDigits(numDigits_);
    nf_.setMaximumFractionDigits(numDigits_);
  }

/**
 * An internal method used to handle repaint events.
 */
  public void paint(Graphics g) {
    int width = getSize().width;	
    int height = getSize().height;

    if (numDigits_ >= 0) {
      g.setColor(backgroundColor_);
      g.fillRect(0, 0, width, textHeight_);
    }

    g.setColor(barColor_);
    g.fill3DRect(0, textHeight_,
		 width, height - textHeight_, false);

    g.setColor(thumbColor_);	
    g.fill3DRect(pixel_ - THUMB_SIZE, textHeight_ + BUFFER,
		 THUMB_SIZE * 2 + 1, height - 2 * BUFFER - textHeight_,
		 true);
	
    g.setColor(slashColor_);
    g.drawLine(pixel_, textHeight_ + BUFFER + 1,
	       pixel_, height - 2 * BUFFER);

    g.setColor(textColor_);
    g.setFont(font_);		

    String str;

    if (numDigits_ == 0) 
      {
	str = String.valueOf((int) dvalue_);
      }
    else if (numDigits_ > 0)
      {
//	RoundedDouble rd = new RoundedDouble(dvalue_,numDigits_);
//	str = rd.toString();
	str = nf_.format(dvalue_);
      }
    else                   // For negative values, don't print anything
      {
	str = "";
      }

    if (numDigits_ >= 0) {
      g.drawString(str, pixel_ -
		   (int)(getFontMetrics(font_).stringWidth(str) / 2),
		   textHeight_ - TEXT_BUFFER);
    }
  }

  /**
   * Sets the number of digits to the right of the decimal to appear
   * in the slider value.
   */
  public void SetNumDigits(int n) 
    {
      numDigits_ = n;
      nf_.setMinimumFractionDigits(numDigits_);
      nf_.setMaximumFractionDigits(numDigits_);
      if (numDigits_ <= 0) {
	textHeight_ = 0;
      }
    }

  /**
   * Provided this method for backward compatibility in case it gets used. 
   * Note that setting the number of digits = 0 is the same as 
   * doubleMode = false and that setting the number of digits = 4 will fill 
   * the string, unlike the original.
   *
   * @see MightySlider#SetDoubleMode
   */
  public void SetDoubleMode(boolean theMode) 
    {
      if (theMode)
	numDigits_ = 4;
      else
	numDigits_ = 0;
      nf_.setMinimumFractionDigits(numDigits_);
      nf_.setMaximumFractionDigits(numDigits_);
    }

  /**
   * An internal method used to handle mouse sliding events.
   */
  void HandleMouse(int x) {
    double percent;
    int width = getSize().width;
    pixel_ = Math.max(x, pixelMin_);
    pixel_ = Math.min(pixel_, pixelMax_);

    if (pixel_ != pixelMin_)
      percent = (((double)pixel_ - pixelMin_) / (pixelMax_ - pixelMin_));
    else
      percent = 0;
	
/*
 * For any nonzero value, compute value at full accuracy. For "integer mode",
 * round off value.
 */
    if (numDigits_!=0)
      dvalue_ = percent * (dmax_ - dmin_) + dmin_;
    else
      dvalue_ = (Math.round(percent * (double)(dmax_ - dmin_))) + dmin_;
	
    paint(getGraphics());
  }

}


