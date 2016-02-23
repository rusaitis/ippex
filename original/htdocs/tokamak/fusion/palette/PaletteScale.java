/*
 * class PaletteScale
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
package palette;

import java.lang.String;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;

/**
 * This class draws a Canvas-subclass component which graphically relates
 * an IndexColorModel with its index values. This class was initially
 * intended to be used with just the paletteServer class, but later
 * implementation of that with the IndexColorModel allowed this class
 * to be generalized.
 *
 * @version $Revision: 1.3 $, $Date: 2004/10/07 20:22:36 $.
 * @author Daren Stotler
 * @see PaletteServer
 * @see #preferredSize
 * @see TestApplications.PaletteTest
 */

/*
 * $Log: PaletteScale.java,v $
 * Revision 1.3  2004/10/07 20:22:36  dstotler
 * Change to javadoc comments.
 *
 * Revision 1.2  1999/11/19 21:36:46  dstotler
 * Added font to constructor so we could control the size from the
 * applet.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 * 
 * 1.1 08-23-99 Replace deprecated Java 1.0 methods.
 * 1.0 02-17-97 Finally give it a number.
 *
 * Because of the simple nature of the graphical elements in the AWT,
 * the implementation here is very tedious. A fair amount of flexibility
 * was built in (although not all of it is presently available to the
 * outside world) in the hope that no one will ever be tempted to 
 * modify the interior drawing operations. THIS IS STRONGLY DISCOURAGED.
 */

public class PaletteScale extends Canvas {

  /*
   * Constants (LW, TW, TLS could conceivably be externally adjustable).
   * Note that everything works better if LW is odd.
   */
  private static final int VERTICAL = 0;    // Values of orientation_
  private static final int HORIZONTAL = 1;
  private static final int LW = 3;          // Line widths in pixels
  private static final int TW = 4;          // Tick width in pixels
  private static final int TLS = 4;         // Space between ticks and
                                            // their labels
  /*                                             
   * Main instance variables
   */
  private int totalWidth_, totalHeight_;   // Dimension including lines
  private int scaleWidth_, scaleHeight_;   // Dimension of imageScale_ only
  private int orientation_;                // VERTICAL or HORIZONTAL

  private Image imageScale_;               // Image of color palette

  private int numDivisions_;               // Number of (equal) divisions
  private String[] tickLabels_;            // numDivisions_ + 1 labels for them
  private String scaleLabel_;              // Overall label
  private Font tickFont_;                  // Font for all labels

  /*
   * Instance formatting variables 
   */
  private int shift_;           // Needed to accomodate first tick label width
  private int[] tickLocations_;            // Location of tick marks 
  private int[] tickLabelX_, tickLabelY_;  // Coordinates of tick labels
  private int scaleLabelX_, scaleLabelY_;  // Coordinates of overall label

  private int maxX_, maxY_;                // For Size methods

  /**
   * Lone constructor for the class. The scale is automatically oriented
   * so that the scale runs along and is labeled on its longer axis.
   * @param pal IndexColorModel being used to paint the scale
   * @param w width of the image part of the scale (plus outline)
   * @param h height of the image; if needed, the preferredSize
   *          method provides the total size
   * @param labs String array with labs[0] being the overall label,
   *             and labs[1 -> N] being the individual tick mark
   *             labels; the scale will consist of N equal pieces
   * @param font font to be used for string
   */
  public PaletteScale(IndexColorModel pal, int w, int h, String[] labs, Font font) {
  
    int rojo, verde, azul, alf;
    totalWidth_ = w;
    totalHeight_ = h;
    
    /*
     * Choose orientation_ so the scale runs along and is labeled on its
     * longer axis.
     */
    if (totalHeight_ > totalWidth_) {
      orientation_ = VERTICAL;
      scaleWidth_ = totalWidth_-2*LW+1;  // <- So image fits inside the lines.
      scaleHeight_ = totalHeight_-LW+2;  // <- Image begins (ends) at center of
                              //  first (last) tick mark; i.e., aligned exactly
    }
    else {
      orientation_ = HORIZONTAL;
      scaleWidth_ = totalWidth_-LW+2;
      scaleHeight_ = totalHeight_-2*LW+1;
    }

    /*
     * Generate Image
     */
    int max_value = pal.getMapSize() - 1;
    int scale[] = new int[scaleWidth_ * scaleHeight_];
    int index = 0;
      int value = 0;
    for (int y=0; y<scaleHeight_; y++) {
      for (int x=0; x<scaleWidth_; x++) {
	if (orientation_ == VERTICAL) {
	  /*
	   * Due to a bug in Netscape's IndexColorModel, have to explicitly 
	   * evaluate the colors here and transmit the image in the RGB 
	   * color model. The lines commented out represent the original 
           * approach.
	   */
//	  scale[index] = (int) (max_value * (scaleHeight_-1 - y) 
//				/ ((float)(scaleHeight_-1)));
	  value = (int) (max_value * (scaleHeight_-1 - y) 
				/ ((float)(scaleHeight_-1)));
	}
	else if (orientation_ == HORIZONTAL) {
//	  scale[index] = (int) (max_value * x       // "+x" here, "-y" above!
//				/ ((float)(scaleWidth_-1)));
	  value = (int) (max_value * x              // "+x" here, "-y" above!
				/ ((float)(scaleWidth_-1)));
	}
	rojo = pal.getRed(value);
	if (rojo < 0) rojo += 256;
	verde = pal.getGreen(value);
	if (verde < 0) verde += 256;
	azul = pal.getBlue(value);
	if (azul < 0) azul += 256;
	alf = pal.getAlpha(value);
	if (alf < 0) alf += 256;

	scale[index] = (alf << 24) | (rojo << 16) | (verde << 8) | (azul);
	index++;
      }
    }
//    imageScale_ = createImage(new MemoryImageSource(scaleWidth_, scaleHeight_, pal, 
//						 scale, 0, scaleWidth_));
    imageScale_ = createImage(new MemoryImageSource(scaleWidth_, scaleHeight_,  
						 scale, 0, scaleWidth_));

    /*
     * Set instance variables and sizes for labels. Note that the arguments to
     * drawString point to the left edge (smaller x) of the baseline (larger y)
     * of the first character.
     */
    scaleLabel_ = labs[0];
    numDivisions_ = labs.length - 2;
    tickLabels_ = new String[numDivisions_+1];
    tickFont_ = font;
    FontMetrics tick_metrics = getFontMetrics(tickFont_);
    int label_height = tick_metrics.getAscent();
    int label_width = 0;
    for (int i=0; i<numDivisions_+1; i++) {
      tickLabels_[i] = labs[i+1];
      int temp = tick_metrics.stringWidth(tickLabels_[i]);
      label_width = temp > label_width ? temp : label_width;
    }
    shift_ = 0;
    if (orientation_ == VERTICAL) {
      shift_ = label_height / 2;
      scaleLabelX_ = totalWidth_ + TW + TLS + label_width + TLS;
      maxX_ = scaleLabelX_ + tick_metrics.stringWidth(scaleLabel_);
      scaleLabelY_ = (totalHeight_ + label_height/2) / 2 + shift_;
      maxY_ = totalHeight_ + shift_ + shift_;  // 2nd shift_ for label height
    }
    else if (orientation_ == HORIZONTAL) {
      shift_ = tick_metrics.stringWidth(tickLabels_[0]) / 2;
      scaleLabelX_ = (totalWidth_ - tick_metrics.stringWidth(scaleLabel_)) / 2
	+ shift_;

      // Add here also half-width of last label
      maxX_ = totalWidth_ + shift_ 
	+ tick_metrics.stringWidth(tickLabels_[numDivisions_])/2; 
      scaleLabelY_ = totalHeight_ + TW + TLS + label_height + TLS 
	+ label_height;
      
      // Replace label_height to catch descent of string
      maxY_ = scaleLabelY_ - label_height + tick_metrics.getHeight();  

    }
    tickLocations_ = new int[numDivisions_+1];
    tickLabelX_ = new int[numDivisions_+1];
    tickLabelY_ = new int[numDivisions_+1];
    for (int j=0; j < numDivisions_+1; j++) {
      if (orientation_ == VERTICAL) {
	tickLocations_[j] = (int) (j * ((double) (scaleHeight_-1) 
					/ numDivisions_) + (LW-1)/2 + shift_);
	tickLabelX_[j] = totalWidth_ + TW + TLS;
	tickLabelY_[numDivisions_-j] = tickLocations_[j] + shift_;
      }
      else if (orientation_ == HORIZONTAL) { 
	tickLocations_[j] = (int) (j * ((double) (scaleWidth_-1) 
					/ numDivisions_) + (LW-1)/2) + shift_;
	tickLabelX_[j] = tickLocations_[j] 
	  - tick_metrics.stringWidth(tickLabels_[j]) / 2;
	tickLabelY_[j] = totalHeight_ + TW + TLS + label_height;
      }
    }
  }

  /**
   * Lone paint method for the class.
   * @param g graphics context in which the scale will be drawn
   */
  public void paint(Graphics g) {
    g.setFont(tickFont_);
    g.setColor(Color.black);
    if (orientation_ == VERTICAL) {
      g.drawImage(imageScale_,LW,(LW-1)/2+shift_,scaleWidth_,scaleHeight_,this);
    }
    else if (orientation_ == HORIZONTAL) {
      g.drawImage(imageScale_,(LW-1)/2+shift_,LW,scaleWidth_,scaleHeight_,this);
    }

    /*
     * Each value of i draws one pixel width of the bounding rectangle
     * and the tick lines
     */
    for (int i=0; i<LW; i++) {
      if (orientation_ == VERTICAL) {
	g.drawRect(i,i+shift_,totalWidth_-2*i,totalHeight_-2*i);
      }
      else if (orientation_ == HORIZONTAL) {
	g.drawRect(i+shift_,i,totalWidth_-2*i,totalHeight_-2*i);
      }
      for (int j=0; j < numDivisions_ + 1; j++) {

	/*
	 * The stuff at the end sees to it that the tick lines are
	 * centered on the tickLocations_, which represent the precise division
	 * locations. 
	 */
	int tickLocations_i = tickLocations_[j] - (LW - 1)/2 + i;
	if (orientation_ == VERTICAL) {
	  g.drawLine(0,tickLocations_i,totalWidth_+TW,tickLocations_i);
	}
	else if (orientation_ == HORIZONTAL) {
	  g.drawLine(tickLocations_i,0,tickLocations_i,totalHeight_+TW);
	}
      }
    }
    for (int j=0; j < numDivisions_ + 1; j++) {
      g.drawString(tickLabels_[j],tickLabelX_[j],tickLabelY_[j]);
    }
    g.drawString(scaleLabel_,scaleLabelX_,scaleLabelY_);
  }
    
  /**
   * This is the preferred size based on the initial height and
   * width used in the constructor, plus the font and tick sizes.
   * @return Dimension of palette scale
   */
  public Dimension getPreferredSize() {
    Dimension temp = new Dimension();
    temp.width = maxX_;
    temp.height = maxY_;
    return temp;
  }

  /**
   * Allegedly provides a minimum Dimension, but not sure when this
   * is ever called.
   * @return "minimum" Dimension of the scale
   */
  public Dimension getMinimumSize() {
    Dimension temp = new Dimension();
    temp.width = maxX_/2;
    temp.height = maxY_/2;
    return temp;
  }
}

