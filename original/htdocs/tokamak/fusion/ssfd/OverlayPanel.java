/*
 * class OverlayPanel
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

import java.lang.String;
import java.lang.System;
import java.awt.Panel;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.IndexColorModel;
import java.awt.image.FilteredImageSource;
import java.util.Observer;
import java.util.Observable;
import java.applet.Applet;
import observable.ObservableDbl;

/**
 * This class was developed with a particular application in mind (hinted at
 * by the names of the images), but may possibly find other applications.
 * The goal is to overlay two images. The top image is filtered and
 * redrawn whenever the ObservableDbl calls its update method. 
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:50 $.
 * @author Daren Stotler
 * @see ICMFilter
 * @see SteadyStateFusionDemo
 * @see ObservableDbl
 */

/*
 * $Log: OverlayPanel.java,v $
 * Revision 1.1.1.1  1999/11/15 20:37:50  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 2.1 08-23-99 Update for Java 1.1 event model.
 * 2.0 02-19-97 Following the example in Graphic Java, split off filtering
 *              duties into ICMFilter. This lets us get rid of the "mask"
 *              concept and its associated methods.
 * 1.0 12-16-96 Earlier versions were not tracked. At this date, added
 *              the offsets for the overlaid image to speed up
 *              filtering and redrawing.
 */
public class OverlayPanel extends Panel implements Observer {

  /*
   * Images: base, original overlaid, and filtered overlaid. The
   * original application has a plasma being overlaid on a drawing
   * of a cut-open tokamak. The filter changes the color of the plasma;
   * the area in the plasma image which is not part of the plasma 
   * was set to transparent by the filter, hence the name.
   */
  private Image imgTok_, imgPlasma_, transpImage_;
  private int imgHeight_, imgWidth_, imgHeight2_, imgWidth2_;
  private int xshift_, yshift_;
  private IndexColorModel pal_;
  private ObservableDbl perfValue_;

  /**
   * Loads the images, sets their size variables, and otherwise initializes
   * the OverlayPanel instance variables.
   * @param foo needed to get the images
   * @param imgname1 name of the first (base) image
   * @param imgname2 name of the second (overlaid) image
   * @param xs horizontal shift of the origin of the overlaid image
   *           relative to the base image
   * @param ys vertical shift of the origin of the overlaid image 
   *           relative to the base image
   * @param od (observable) value used to select the color in the 
   *           IndexColorModel; the double is converted to an integer
   *           using its allowed range of values and the number of 
   *           colors in the IndexColorModel
   * @param icm list of colors which will be used to depict the current
   *            value of od in the overlaid image
   */
  public OverlayPanel(Applet foo, String imgname1, String imgname2, 
		      int xs, int ys, ObservableDbl od, IndexColorModel icm) {

    super();
    perfValue_ = od;
    /*
     * Let the Observable know we're here
     */
    perfValue_.addObserver(this);
    pal_ = icm;
    xshift_ = xs;
    yshift_ = ys;

    /*
     * Force code to wait for the image to be loaded so that we can 
     * obtain a nontrivial size for the image. 
     */
    imgTok_ = foo.getImage(foo.getCodeBase(),imgname1);
    MediaTracker tracker = new MediaTracker(this);
    tracker.addImage(imgTok_,0);
    imgPlasma_ = foo.getImage(foo.getCodeBase(),imgname2);
    tracker.addImage(imgPlasma_,0);
    try {
      tracker.waitForID(0);
    } catch (InterruptedException e) {
      System.out.println("Error in loading image");
      return;
    }
    imgWidth_ = imgTok_.getWidth(this);
    imgHeight_ = imgTok_.getHeight(this);
    imgWidth2_ = imgPlasma_.getWidth(this);
    imgHeight2_ = imgPlasma_.getHeight(this);

    /*
     * The overlaid image should fit inside the base image
     */
    if (xshift_+imgWidth2_ > imgWidth_ || yshift_+imgHeight2_ > imgHeight_) {
      System.out.println("Overlaid image larger than base image");
    }
    setBounds(0, 0, imgWidth_, imgHeight_);  
  }

  /**
   * This is the requisite method for the Observer interface. It's called
   * whenever an Observable changes. At this point, its new value and the
   * IndexColorModel are used to filter the overlaid image.
   * @see Observer#update
   */
  public void update(Observable obs, Object arg) {

    /*
     * Convert the double value into an integer corresponding to
     * a color in the IndexColorModel.
     */
    int numcol = pal_.getMapSize();
    double pvmax = perfValue_.getMaximum();
    double pvmin = perfValue_.getMinimum();
    double pvdbl = (perfValue_.getValue() - pvmin) / (pvmax - pvmin);
    int pv = (int) ((numcol - 1.) * pvdbl);
    if (pv < 0) {
      pv = 0;
    }
    if (pv > numcol - 1) {
      pv = numcol - 1;  
    }

    FilteredImageSource source 
      = new FilteredImageSource(imgPlasma_.getSource(),
				new ICMFilter(pv,pal_));

    transpImage_ = createImage(source);
    repaint();
  }

  /**
   * Draws the base and overlaid images. If the overlaid image exists
   * it is used. Otherwise, the original is drawn.
   * @param g graphics context on which the drawing is done
   */
  public void paint(Graphics g) {
     g.drawImage(imgTok_, 0,0, imgWidth_, imgHeight_, this);
    if (transpImage_ != null) {
      g.drawImage(transpImage_, xshift_, yshift_, imgWidth2_, imgHeight2_, this);
    }
    else {
      g.drawImage(imgPlasma_, xshift_, yshift_, imgWidth2_, imgHeight2_, this);
    }
  }

  /**
   * Sets a minimum Dimension for this panel, in case anybody cares.
   * @return "minimum" Dimension of the panel
   */
  public Dimension getMinimumSize() {
    return (new Dimension(imgWidth_/2,imgHeight_/2));
  }
  
  /**
   * Sets the size to be that of the base image.
   * @return size of the image
   */
  public Dimension getPreferredSize() {
    return (new Dimension(imgWidth_,imgHeight_));
  }
}

