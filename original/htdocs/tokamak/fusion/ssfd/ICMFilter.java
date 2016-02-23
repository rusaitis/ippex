/*
 * class ICMFilter
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

import java.awt.image.RGBImageFilter;
import java.awt.image.IndexColorModel;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.Color;

/**
 * Uses an integer parameter to select a color in an IndexColorModel (hence
 * the name of the class) that will be used to replace every "color" (a
 * pixel with a nonzero saturation). Other pixels are made transparent.
 *
 * @version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:49 $.
 * @author Daren Stotler
 * @see RGBImageFilter
 */

 /*
  * $Log: ICMFilter.java,v $
  * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
  * Import SSFD into CVS
  *
  *
  * VERSION HISTORY PRIOR TO CVS:
  * 
  * 1.0 02-19-97 Split off filtering logic from the OverlayPanel class, where
  *              it had undergone a number of different implementations.
  */
public class ICMFilter extends RGBImageFilter {

  private int perfValue_;
  private IndexColorModel pal_;

  /**
   * Grabs the input parameters and initializes the corresponding instance
   * variables. Also, lets the superclass know that the filter only cares
   * about the color of a pixel, and not about where in the image it's
   * located.
   * @param pv integer which will be used as an index into the color model
   * @param icm that color model
   */
  public ICMFilter(int pv, IndexColorModel icm) {
    perfValue_ = pv;
    pal_ = icm;
    canFilterIndexColorModel = true;  // RGBImageFilter variable
  }

  /**
   * Checks the saturation of the input color. If nonzero, changes 
   * the color to that selected by the index and the color model
   * sent to the constructor.
   * @param x horizontal location of the pixel (not used)
   * @param y vertical location of the pixel (not used)
   * @param rgb color of the pixel being filtered
   */
  public int filterRGB(int x, int y, int rgb) {
    DirectColorModel cm = (DirectColorModel)ColorModel.getRGBdefault();

    /*
     * Convert from RGB to HSB. Shouldn't there be a neater
     * way to do this? Don't really need alf here.
     */
    int alf = cm.getAlpha(rgb);
    int r = cm.getRed(rgb);
    int g = cm.getGreen(rgb);
    int b = cm.getBlue(rgb);

    float hsb[] = new float[3];
    hsb = Color.RGBtoHSB(r,g,b,hsb);

    /*
     * This might not be too general...just pick out those pixels with nonzero
     * saturation (i.e., a color), and switch to the indexed color.
     */
    if (hsb[1] > 0.) {
      r = pal_.getRed(perfValue_);
      if (r < 0) r += 256;

      g = pal_.getGreen(perfValue_);
      if (g < 0) g += 256;

      b = pal_.getBlue(perfValue_);
      if (b < 0) b += 256;

      alf = pal_.getAlpha(perfValue_);
      if (alf < 0) alf += 256;
    }
    else {
    /*
     * Otherwise, make the pixel completely transparent.
     */
      alf = 0;
    }

    return (alf << 24) | (r << 16) | (g << 8) | (b);
  }
}
