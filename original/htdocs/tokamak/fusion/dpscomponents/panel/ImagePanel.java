/*
 * class ImagePanel.java
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
package dpscomponents.panel;

import java.lang.System;
import java.lang.String;
import java.awt.Panel;
import java.awt.Image;
import java.awt.Graphics;
import java.applet.Applet;
import java.awt.MediaTracker;
import java.awt.Dimension;

/**
 * This is a (hopefully general) class used to turn images into
 * "layout-able" components with a definite size.
 *
 * @version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:48 $.
 * @author Daren Stotler
 */

/*
 * $Log: ImagePanel.java,v $
 * Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.1 08-23-99 Remove deprecated Java 1.0 methods.
 * 1.0 03-05-97 Finally assign a version number. The implementation is
 *              just the obvious thing. 
 */
public class ImagePanel extends Panel {
  private Image img_;
  private int imgWidth_;
  private int imgHeight_;

  /**
   * Constructor.  Use Applet here to make this more general. 
   * @param foo Applet which provides the CodeBase needed to
   *            read the image
   * @param img_name String containing the name of the image to be read
   */
  public ImagePanel(Applet foo, String img_name) {
    super();

    /*
     * Can't be any more general (e.g., use getParent() to get 
     * Container) since getCodeBase() is first defined for Applet.
     */
    img_ = foo.getImage(foo.getCodeBase(),img_name);
    MediaTracker tracker = new MediaTracker(this);
    tracker.addImage(img_,0);

    /*
     * Force code to wait for the image to be loaded so that we can 
     * obtain a nontrivial size for the image. 
     */
    try {
      tracker.waitForID(0);
    } catch (InterruptedException e) {
      System.out.println("Error in loading image" + img_name);
      return;
    }
    imgHeight_ = img_.getHeight(this);
    imgWidth_ = img_.getWidth(this);
    setBounds(0, 0, imgWidth_, imgHeight_);

  }

  /** 
   * Ye olde paint method used to actually draw the image and
   * nothing more.
   * @param g Graphics context where the image will be drawn
   */
  public void paint(Graphics g) {
      g.drawImage(img_, 0, 0, imgWidth_, imgHeight_, this);
  }

  /**
   * I assume this doesn't get used much.
   * @return a minimum Dimension for the panel
   */
  public Dimension getMinimumSize() {
    return (new Dimension(imgWidth_/2,imgHeight_/2));
  }
  
  /**
   * This is the one I expect to get called.
   * @return the preferred Dimension of the panel; same as the image size
   */
  public Dimension getPreferredSize() {
    return (new Dimension(imgWidth_,imgHeight_));
  }
}
