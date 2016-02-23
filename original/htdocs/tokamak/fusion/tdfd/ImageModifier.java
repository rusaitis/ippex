/*
 * class ImageModifier
 *
 *    Copyright (C) 2004, Will Fisher, Daren Stotler
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
package tdfd;

import java.awt.image.*;
import java.awt.Color;
/**
 * Subclass of RGBImageFilter is used to apply the color selected from
 * the index color model, via the score value.  In the original steady state
 * Virtual Tokamak, the plasma image was monochrome.  Here, it is
 * 3-D in appearance with highlights.  Recoloring it
 * then becomes much more complicated.  The implementation below is one 
 * possible approach; others are possible.  Furthermore, this approach
 * was designed specifically with the "hot metal" color palette in
 * mind; it may not work so well with others.
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:36:55 $.
 * @author Will Fisher
 * @see ssfd.ICMFilter
 */

/*
 * $Log: ImageModifier.java,v $
 * Revision 1.1  2004/10/07 20:36:55  dstotler
 * Added to repository.
 *
 */
public class ImageModifier extends RGBImageFilter {
	
	private float[] originalHSB = new float[4], changeHSB = new float[4];
	private IndexColorModel icm_;
        private int index_ = 0;
    /**
     * Initialize instance variable corresponding to the input parameter.
     * @param icm the index color model
     */
	public ImageModifier(IndexColorModel icm) {
		icm_ = icm;
		canFilterIndexColorModel = true;
	}
    /**
     * This is the abstract method of RGBImageFilter.  The original
     * color of the particular pixel is input and the modified color
     * is returned.
     * @param x horizontal location of the pixel (not used)
     * @param y vertical location of the pixel (not used)
     * @param rgb color of the pixel being filtered
     */
	public int filterRGB (int x, int y, int rgb) {
		// Get the alpha channel
		int alpha = (rgb >> 24) & 0xFF;
		if (alpha > 0) {
			// Make a new color that represents this rgb color
			int red = (rgb >> 16) & 0xFF;
			int green = (rgb >> 8) & 0xFF;
			int blue = (rgb) & 0xFF;
			Color.RGBtoHSB(red, green, blue, originalHSB);

			if (index_ > 100) {
			    // Increase the brightness by decreasing saturation
			    originalHSB[1] -= ((float)(index_-100.0)/50.0 * .7);
			    originalHSB[2] += ((float)(index_-100.0)/50.0 * .4);
			    if (originalHSB[2] > 1.0) {
				originalHSB[2] = 1.0f;
			    }
			    if (originalHSB[1] < 0.0) {
				originalHSB[1] = 0.0f;
			    }
			}
			
			alpha = (((int)changeHSB[3]) << 24) | (0x00FFFFFF);
			int newColor = Color.HSBtoRGB(changeHSB[0], originalHSB[1], originalHSB[2]);
			newColor = (newColor & alpha);

			// Return the new color
			return newColor;
		}
		else {
			return rgb;
		}
	}
	
    /**
     * Uses the index to be used with the IndexColorModel to get that color
     * and convert it into HSB.  This is subsequently used by the 
     * filterRGB method.
     * @param index integer which will be used as an index into the color model
     */
	public void setHue (int index) {
		Color.RGBtoHSB(icm_.getRed(index), icm_.getGreen(index), icm_.getBlue(index), changeHSB);
		changeHSB[3] = icm_.getAlpha(index);
		index_ = index;
	}
}
