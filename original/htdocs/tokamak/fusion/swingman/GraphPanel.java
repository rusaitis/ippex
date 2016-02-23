/*
 * class GraphPanel
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

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
 * A JLayeredPane that serves as the actual graphing space.  This is part 
 * of the <code>swingman</code> package, a swing-based replacement of 
 * Brookshaw's <code>graph</code> package used previously.
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:33:38 $.
 * @author Will Fisher
 * @see DataTable
 */

/*
 * $Log: GraphPanel.java,v $
 * Revision 1.1  2004/10/07 20:33:38  dstotler
 * Added to repository.
 *
 *
 */

public class GraphPanel extends JLayeredPane implements ActionListener {
	
	// Class variables
	private int alength, i;
        protected static final int GRAPH = 0, WAVEFORM = 1;
        protected int type;
	protected DataTable data;
	protected double[][] markerArray;
	public boolean gridLines = false, lock_ = false;
	public ClearPanel barPanel;
        protected BasicStroke thinStroke;
	protected ArrayList pointsArray = new ArrayList();
	private ArrayList axes;
    /**
     * Initializes the internal objects for the data and axes.  Sets up the
     * panel that contains the moving bar.
     * @param _data DataTable object representing the plotted data
     * @param _axes array containing the graph's axes
     */
	public GraphPanel(DataTable _data, ArrayList _axes) {
		// Initialization variables
		axes = _axes;
		data = _data;
		type = GRAPH;
		
		// Define stanard stroke
		thinStroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		
		// Set background
		this.setPreferredSize(new Dimension(350, 200));
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		barPanel = new ClearPanel();
		add(barPanel, new Integer(5));
		barPanel.setSize((int)getBounds().getWidth(), (int)getBounds().getHeight());
	}
    /**
     * Overrides the parent class' paint method.  This draws everything, 
     * including the actual line between the points.
     * @param g graphics context for this object
     */
	public void paintComponent (Graphics g) {
		// Get bounds object
		super.paintComponent(g);
		final Rectangle bounds = getBounds();
		
		// Determine full X and Y pixel values to work with
		double xPixels = bounds.width;
		double yPixels = bounds.height;
		
		// Get the panel graphics object
		final Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Reset marker location counter
		resetMarker();
		
		// Draw the graph border
		g2.setStroke(thinStroke);
		g2.draw(new Rectangle2D.Double(0.0, 0.0, xPixels-1, yPixels-1));
		
		// Paint the axes
		for (int k = 0; k < axes.size(); k++) {
		    if ((!data.hasLock()) || ((type == GRAPH) && (lock_ == false))) {
			((Axis)axes.get(k)).paintAxis(g2, bounds, gridLines, data);
		    }
		}
		
		double[] t = data.calcScale();
		final double xDiff = t[0];
		final double yDiff = t[1];
		final double xSmallest = t[2];
		final double ySmallest = t[3];
		markerArray = new double[data.countPairs()][2];
		
		int k = -1;
		for (i = 0; i < data.countPairs()-1; i++) {
		    double[] plotPoint0 = data.getPair(i);
		    double[] plotPoint1 = data.getPair(i+1);
		    double xt0 = ((plotPoint0[0] - xSmallest) / xDiff) * bounds.width;
		    double yt0 = ((plotPoint0[1] - ySmallest) / yDiff) * bounds.height;
		    double xt1 = ((plotPoint1[0] - xSmallest) / xDiff) * bounds.width;
		    double yt1 = ((plotPoint1[1] - ySmallest) / yDiff) * bounds.height;
		    Point2D.Double tPoint0 = new Point2D.Double(xt0, bounds.height-yt0);
		    Point2D.Double tPoint1 = new Point2D.Double(xt1, bounds.height-yt1);
		    g2.setColor(Color.BLACK);
		    g2.setStroke(thinStroke);
		    g2.draw(new Line2D.Double(tPoint0, tPoint1));
		    
		    if (type == WAVEFORM) {
			// Add marker positions to marker array
			if (i == 0) {
			    markerArray[++k][0] = xt0;
			    markerArray[k][1] = bounds.height-yt0;
			}
			markerArray[++k][0] = xt1;
			markerArray[k][1] = bounds.height-yt1;
		    }
		}
		
		// Make sure the clear panel is the right size
		barPanel.setSize(this.getSize());
	}
    /**
     * Paint the markers used to denote the graph's points when it is associated
     * with a Waveform.
     * @param g2 the current graphics object
     * @param markers 2-D array of marker positions
     */
	public void paintMarkers (Graphics2D g2, double[][] markers) {
		// Draw the markers given the array of locations
		double radius;
		Rectangle bounds = getBounds();
		for (int k = 0; k < markers.length; k++) {
			radius = 6.0;
			// Now do the drawing part
			drawMarker(markers[k][0], markers[k][1], radius, g2);
		}
	}
    /**
     * Draws a circle as an individual marker.  Also, the last parameter
     * allows this point to be added to an internal list of points; this
     * is used by the Waveform subclass.
     * @param x independent variable value for the marker
     * @param y dependent variable value for the marker
     * @param r radius of the marker 
     * @param g2 graphics object on which the marker is to be drawn
     * @param c color of the marker
     * @param recordPoint denotes whether or not to the point list
     */
	public void drawMarker (double x, double y, double r, Graphics2D g2, Color c, boolean recordPoint) {
		// Draw a circle
		g2.setColor(c);
		g2.draw(new Ellipse2D.Double(x-r/2, y-r/2, r, r));
		Integer[] pointsData = {new Integer((int)(x)), new Integer((int)(y)), new Integer((int)r)};
		if (recordPoint) {
			pointsArray.add(pointsData);
		}
	}
    /**
     * Draws a circle as an individual marker.  This method automatically
     * adds this point to an internal list of points used by the Waveform
     * subclass.
     * @param x independent variable value for the marker
     * @param y dependent variable value for the marker
     * @param r radius of the marker 
     * @param g2 graphics object on which the marker is to be drawn
     */
	public void drawMarker (double x, double y, double r, Graphics2D g2) {
		// Convenience method for everything BUT the temporary marker drawing
		drawMarker(x, y, r, g2, Color.BLUE, true);
	}
    /**
     * Associates the input DataTable with this graph.
     * @param _data the input DataTable
     */	
	public void setDataTable (DataTable _data) {
		data = _data;
	}
    /**
     * Enables or disables grid lines on the plot.
     * @param gl set to true to get grid lines
     */	
	public void setGridLines (boolean gl) {
		gridLines = gl;
	}
    /**
     * Resets (clears) the internal list of points.
     */
	public void resetMarker() {
		pointsArray.clear();
	}
    /**
     * This is the ActionListener method; it just resets
     * the graph when called.
     * @param e the ActionEvent
     */
	public void actionPerformed(ActionEvent e) {
		// Calling for a reset, now reset the graph
		data.reset();
		data.setScale(0, 0, 20.0, data.init[3]);
	}
    /** 
     * Sets the position of the vertical bar.
     * @param x value of the independent variable for the bar
     */
	public void setClearPanelLocation (double x) {
		barPanel.setLocation((int)x, 0);
	}
    /**
     * Simple class representing the scanning vertical bar.
     */
	class ClearPanel extends JPanel {

	    // Class bar position variable and x pixel variable
	    double barPos = -1.0;
	    double xPixel;
	    /**
	     * Paints the vertical bar.
	     * @param g graphics object on which the bar is drawn
	     */
		public void paintComponent (Graphics g) {
			// Make sure I'm transparent
			super.paintComponent(g);
			setOpaque(false);

			// Now draw the scanning bar!
			Graphics2D g2 = (Graphics2D)g;
			if (barPos != -1.0) {
				xPixel = (((barPos - data.calcScale()[2]) / data.calcScale()[0]) * getBounds().width);
				g2.draw(new Line2D.Double(xPixel, 0, xPixel, getBounds().height));
			}
		}
	    /** 
	     * Sets the position of the vertical bar.
	     * @param x value of the independent variable for the bar.
	     */
		public void setBarPosition (double x) {
			barPos = x;
			repaint();		
		}
	}
}
