/*
 * class Waveform
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
package swingman;

import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.event.*;
import java.util.*;

/**
 * An extension of GraphPanel that supports user interaction with the graphing
 * area, permitting markers to be moved, added, and deleted.  This is part of the 
 * <code>swingman</code> package, a swing-based replacement of 
 * Brookshaw's <code>graph</code> package used previously.
 *
 * The currently implemented mouse functionality is:
 * <dl>
 * <dt> MouseClick
 * <dd> If near an existing point, that point is deleted,
 * <dd> Otherwise, a new point is added.
 * <dt> MouseDrag
 * <dd> If near an existing point, a temporary point is drawn
 * indicating that the point is being moved.
 * <dt> MouseRelease
 * <dd> When dragging an existing point to a new location, adds
 * the new point and deletes the original.
 * </dl>
 *
 * Constraints:
 * These data sets are intended to be used in conjunction with numerical
 * integration schemes. For this reason, the abscissa must be monotonically
 * increasing.  Consequently, points cannot be dragged "past" one another
 * along the x-axis.  The abscissa of the first and last point is fixed.
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:35:36 $.
 * @author Will Fisher
 * @see DataTable
 */

/*
 * $Log: Waveform.java,v $
 * Revision 1.1  2004/10/07 20:35:36  dstotler
 * Added to repository.
 *
 *
 */

public class Waveform extends GraphPanel implements MouseInputListener {

	// Mouse variables first
	private int xLoc_ = -1, yLoc_ = -1;
	private boolean mouseDragged_ = false;
	private int inMarker_ = -1;
    /**
     * Initializes the internal objects for the data and axes.  Sets up the
     * panel that contains the moving bar.
     * @param data DataTable object representing the plotted data
     * @param axes array containing the graph's axes
     */
	public Waveform (DataTable data, ArrayList axes) {
		super(data, axes);
		type = WAVEFORM;
		
		// Initialize mouse event listeners
		addMouseListener(this);
		addMouseMotionListener(this);
	}
    /**
     * Overrides the parent class' paint method.  This draws everything, 
     * including the actual line between the points.  Unlike the parent,
     * this also draws the markers, including the temporary ones if they're
     * active.
     * @param g graphics context for this object
     */
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// Paint markers now
		paintMarkers(g2, markerArray);
		
		// Draw temporary marker if active
		drawTempMarker(g2);
	}
    /**
     * Track mouse dragging.  This updates the location of the
     * cursor.
     * @param e the MouseEvent
     */
	public void mouseDragged (MouseEvent e) {
		// Set the mouse dragged variables
		mouseDragged_ = true;
		xLoc_ = e.getX();
		yLoc_ = e.getY();

		// Repaint graph
		repaint();
	}
    /**
     * Respond to pressing the mouse button.  Checks to see
     * whether or not the press is inside one of the markers.	
     * @param e the MouseEvent
     */
	public void mousePressed (MouseEvent e) {
		// See if inside a marker
		int inMarker = insideMarker(e.getX(), e.getY());
		if (inMarker != -1) {
			//inside a marker, set the variables
			mouseDragged_ = false;
			inMarker_ = inMarker;
		}
		else {
			mouseDragged_ = false;
		}
	}
    /**
     * Respond to releasing the mouse button.  This is where most
     * of the action takes place.  Note that this method basically
     * handles a mouse click so that the <code>mouseClicked</code>
     * method does not need to be used.  The functionality implemented
     * here includes:
     * <ul>
     * <li> Clicking inside a marker deletes the point,
     * <li> Clicking outside a marker adds that point to the DataTable,
     * <li> Dragging a marker moves the point to the new coordinates.
     * </ul>
     */
	public void mouseReleased (MouseEvent e) {
		// This is where all the real code goes
		if (!mouseDragged_) {
			//user only clicked on panel
			if (inMarker_ != -1) {
				// Delete marker
				data.remove(inMarker_);
			}
			else {
				double[] t = data.calcScale();
				// Do the number crunching
				double rx = (((e.getX()) / (double)getBounds().width) * t[0]) + t[2];
				double ry = ((((double)getBounds().height-e.getY()) / (double)getBounds().height) * t[1]) + t[3];
				// Actually add to data table
				data.add(rx, ry);
			}
		}
		else {
			if (inMarker_ != -1) {
				double[] t = data.calcScale();
				// Do the number crunching
				double rx = (((xLoc_) / (double)getBounds().width) * t[0]) + t[2];
				double ry = ((((double)getBounds().height-yLoc_) / (double)getBounds().height) * t[1]) + t[3];
				// Actually add to data table whilst removing old marker
				data.replace(inMarker_, rx, ry);
				// Mouse was dragged, now set new position
				xLoc_ = -1;
				yLoc_ = -1;
			}
		}
		// Reset mouse trackers
		mouseDragged_ = false;
		inMarker_= -1;
	}
	
	public void mouseClicked (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited (MouseEvent e) {}
	public void mouseMoved (MouseEvent e) {}
    /**
     * Checks to see whether or not the input coordinates are inside a 
     * marker.  If so, the method returns the index of that data point
     * in the DataTable.
     * @param x pixel value of the independent variable
     * @param y pixel value of the dependent variable
     * @return index of the data point in the DataTable; -1 if outside 
     * a marker.
     */
	public int insideMarker (int x, int y) {

		int markerNumber = -1;
		
		// Cycle through marker locations
		for (int i = 0; i < pointsArray.size(); i++) {
			Integer[] pointsData = (Integer[])pointsArray.get(i);
			int tx = pointsData[0].intValue();
			int ty = pointsData[1].intValue();
			int r = pointsData[2].intValue();
			if (Math.sqrt(Math.pow((tx-x), 2) + Math.pow((ty-y), 2)) <= r) {
				// It's a click inside a marker!
				markerNumber = i;
			}
		}
		return markerNumber;
	}
    /**
     * Draws a temporary marker and lines connecting it to the other
     * data points during dragging of an existing point.  This method
     * handles the constraints noted in the class introduction.
     * Namely, points cannot be dragged "past" one another
     * along the x-axis.  The abscissa of the first and last point is fixed.
     * @param g2 graphics context of the graph
     */
	public void drawTempMarker(Graphics2D g2) {
		// Make sure there is a temporary marker _to_ be drawn
		if ((xLoc_ != -1) && (yLoc_ != -1) && (inMarker_ != -1)) {
			// Some important variables
			double[] plotPoint0 = new double[2];
			double[] plotPoint1 = new double[2];
			double xt0, yt0, xt1, yt1;
			
			// Determine full X and Y pixel values to work with
			Rectangle bounds = getBounds();
			
			// Scale variables
			double[] t = data.calcScale();
			double xDiff = t[0];
			double yDiff = t[1];
			double xSmallest = t[2];
			double ySmallest = t[3];
			
			if (inMarker_ == 0) {
				// If modifying the first point
				plotPoint0 = data.getPair(inMarker_);
			}
			else {
				plotPoint0 = data.getPair(inMarker_-1);
			}
			if (inMarker_ == data.countPairs()-1) {
				// If modifying the last point
				plotPoint1 = data.getPair(inMarker_);
			}
			else {
				plotPoint1 = data.getPair(inMarker_+1);
			}
			
			// Calculate the point locations	
			xt0 = ((plotPoint0[0] - xSmallest) / xDiff) * bounds.width;
			yt0 = ((plotPoint0[1] - ySmallest) / yDiff) * bounds.height;
			xt1 = ((plotPoint1[0] - xSmallest) / xDiff) * bounds.width;
			yt1 = ((plotPoint1[1] - ySmallest) / yDiff) * bounds.height;
			Point2D.Double tPoint0 = new Point2D.Double(xt0, bounds.height-yt0);
			Point2D.Double tPoint1 = new Point2D.Double(xt1, bounds.height-yt1);
			
			// Enforce constraints on the abscissa
			if (xLoc_ <= xt0)
				xLoc_ = (int)xt0+2;
			if (xLoc_ >= xt1)
				xLoc_ = (int)xt1-2;
			// And keep the points inside the current box
			if (xLoc_ < 0)
				xLoc_ = 0;
			if (xLoc_ > bounds.width)
				xLoc_ = bounds.width;
			if (yLoc_ < 0)
				yLoc_ = 0;
			if (yLoc_ > bounds.height)
				yLoc_ = bounds.height;
				
			// If this is the first or last point
			if ((inMarker_ == 0) && (xLoc_ != xt1)) {
				xLoc_ = (int)xt0;
			}
			if ((inMarker_ == data.countPairs()-1) && (xLoc_ != xt1)) {
				xLoc_ = (int)xt1;
			}
				
			// Create the data point
			Point2D.Double dataPoint = new Point2D.Double(xLoc_, yLoc_);
			
			// Do the drawing
			g2.setColor(Color.GRAY);
			g2.setStroke(thinStroke);
			g2.draw(new Line2D.Double(tPoint0, dataPoint));
			g2.draw(new Line2D.Double(dataPoint, tPoint1));
			
			// Draw marker now
			drawMarker(xLoc_, yLoc_, 6., g2, Color.GREEN, false);
		}
	}
}
