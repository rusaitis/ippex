/*
 * class Axis
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
import java.awt.*;
import java.awt.geom.*;

/**
 * Draws graph axes, labels, and tick marks.  This is part of the 
 * <code>swingman</code> package, a swing-based replacement of 
 * Brookshaw's <code>graph</code> package used previously.
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:30:25 $.
 * @author Will Fisher
 * @see DataTable
 */

/*
 * $Log: Axis.java,v $
 * Revision 1.1  2004/10/07 20:30:25  dstotler
 * Added to repository.
 *
 *
 */
public class Axis {
	
    // Constants then normal variables
    public static final int LEFT = 0, BOTTOM = 1, RIGHT = 2, TOP = 3, VERTICAL = 5;
    private JLabel label;
    private int orientation, pos;
    private JPanel ticksPanel, fullPanel;
    private int[][] layout = {{1, 2}, {2, 1}};
    private Font defaultFont;
    private DataTable data_;
    /**
     * Initializes the axis with the input title and position.	The 
     * latter can be one of LEFT, BOTTOM, RIGHT, or TOP.
     * @param title axis label
     * @param _pos position of the axis relative to the graph
     */
    public Axis(String title, int _pos) {
	// Initialize stuff
	pos = _pos;
	label = new JLabel(title, JLabel.CENTER);
	ticksPanel = new JPanel() {
		public void paintComponent(Graphics g) {
		    // Do nothing!
		    Graphics2D g2 = (Graphics2D)g;
		    super.paintComponent(g);
		    if (data_ != null) {
			paintTicks(g2, data_);
		    }
		}
	    };
	fullPanel = new JPanel(new GridLayout(layout[pos%2][0], layout[pos%2][1]));
	if ((pos == LEFT) || (pos == TOP))	{
	    fullPanel.add(label);
	    fullPanel.add(ticksPanel);
	}
	else {
	    fullPanel.add(ticksPanel);
	    fullPanel.add(label);
	}
		
	// Use default font and colors here
	defaultFont = new Font("SansSerif", Font.BOLD, 12);
	label.setFont(defaultFont);
	label.setForeground(Color.BLACK);	
    }
    /**
     * Sets (or resets) the axis label.
     * @param title axis label.
     */
    public void setTitle(String title) {
	// Generate new label
	label.setText(title);
    }
    /**
     * Sets the orientation of the label.  The default
     * is "horizontal".  This method can be called
     * with an orientation of VERTICAL otherwise.
     * @param _orientation desired orientation of the axis label
     */	
    public void setOrientation(int _orientation) {
	// Rotate the label with the Graphics2D methods
	orientation = _orientation;
	if (orientation == VERTICAL) {
	    label.setUI(new VerticalLabelUI());
	}
    }
    /**
     * Returns the orientation of the axis label.
     * @return the orientation as an integer constant
     */
    public int getOrientation() {
	// Return orientation
	return orientation;
	}	
    /**
     * Returns the axis position relative to the graph. 
     * @return the position as an integer constant
     */
    public int getPosition() {
	return pos;
    }	
    /**
     * Returns the complete, fully drawn JPanel for the axis with
     * its labels and tick marks.
     * @return a JPanel
     */		
    public JPanel getAxisPanel() {
	return fullPanel;
    }
    /**
     * Draws tick marks and labels suitable for the input 
     * DataTable.
     * @param g2 object on which the ticks will be drawn
     * @param data DataTable that will be used with the graph
     */
    public void paintTicks(Graphics2D g2, DataTable data) {
	// Determine full X and Y pixel values to work with
	Rectangle bounds = ticksPanel.getBounds();
	double xPixels = bounds.width-1;
	double yPixels = bounds.height-1;
		
	// Get some important numbers
	double[] t = data.calcScale();
	double xDiff = t[0];
	double yDiff = t[1];
	double xSmallest = t[2];
	String text = null;
		
	// Label frequency
	int freq = 2;

	// Define tick numbers
	double tick, tickPixel, tickHeight, tickSeparation, tickNumber, tickNumberSeparation;
	double majorTicks = 5.0;
	if (orientation == VERTICAL)
	    tickSeparation = bounds.height / (majorTicks * 2);
	else
	    tickSeparation = bounds.width / (majorTicks * 2);
	tickNumberSeparation = ((orientation == VERTICAL) ? yDiff : xDiff) / (majorTicks * 2);
		
	// Calculate tick heights
	double smallerAxis = (xPixels >= yPixels) ? yPixels : xPixels;

	for (tick = 0.0; tick <= majorTicks*2; tick += 1.0) {
	    // Temporary pixel values
	    tickPixel = (tickSeparation * (tick));
	    tickNumber = (tickNumberSeparation * tick) + xSmallest;
	    tickNumber = (Math.ceil(tickNumber*10)/10);
		
	    if (tick % freq == 0) {
		text = new Double(tickNumber).toString();
	    }
	    else {
		// Draw nothing...
		text = "";
	    }

	    if (orientation == VERTICAL) {
		// Draw y-axis tick
		Rectangle2D labelBounds = defaultFont.getStringBounds(text, g2.getFontRenderContext());
		int yloc = (int)(yPixels - (tickPixel+2) + (labelBounds.getHeight()/2.));
		if (yloc <= labelBounds.getHeight()/2.+3)
		    yloc += labelBounds.getHeight()/2.;
		else if (yloc >= yPixels-(labelBounds.getHeight()/2.))
		    yloc -= labelBounds.getHeight()/2.;
		ticksPanel.setFont(defaultFont);
		g2.drawString(text, 0, yloc);
	    }
	    else {
		// Draw x-axis tick
		Rectangle2D labelBounds = defaultFont.getStringBounds(text, g2.getFontRenderContext());
		int xloc = (int)(tickPixel-(labelBounds.getWidth()/2));
		if (xloc <= 0)
		    xloc += labelBounds.getWidth()/2;
		else if (xloc >= xPixels-(labelBounds.getWidth()/2)-2)
		    xloc -= labelBounds.getWidth()/2;
		ticksPanel.setFont(defaultFont);
		g2.drawString(text, xloc, 15);
	    }
	}
		
    }
    /**
     * Draw the entire axis object.
     * @param g2 object on which the axis will be drawn
     * @param bounds rectangular boundary for the axis
     * @param gridLines determines whether or not grid lines are drawn
     * @param data DataTable that will be used with the graph
     */	
    public void paintAxis (Graphics2D g2, Rectangle bounds, boolean gridLines, DataTable data) {
		// Now set the right width
	    /*if (orientation == VERTICAL) {
		    int width = (int)(defaultFont.getStringBounds("100.00",
				        ((Graphics2D)(ticksPanel.getGraphics())).getFontRenderContext()).getWidth());
		    ticksPanel.setPreferredSize(new Dimension(width, (int)(ticksPanel.getSize().getHeight())));
		    ticksPanel.setSize(new Dimension(width, (int)(ticksPanel.getSize().getHeight())));
		    width += (int)(label.getSize().getHeight());
		    fullPanel.setPreferredSize(new Dimension(width, (int)(fullPanel.getSize().getHeight())));
		    fullPanel.setSize(new Dimension(width, (int)(fullPanel.getSize().getHeight())));
		    }*/
		
	// Determine full X and Y pixel values to work with
	double xPixels = bounds.width-1;
	double yPixels = bounds.height-1;
	Point2D.Double tPoint0, tPoint1;
		
	// Get some important numbers
	double[] t = data.calcScale();
	double xDiff = t[0];
	double yDiff = t[1];
	double xSmallest = t[2];
		
	// Calculate tick frequency
	int freq = 1;
	if (xPixels < 400)
	    freq = 2;
	if (xPixels < 200)
	    freq = 3;

	// Define tick numbers
	double tick, tickPixel, tickHeight, tickSeparation, tickNumber, tickNumberSeparation;
	double majorTicks = 5.0;
	gridLines = true;
	if (orientation == VERTICAL)
	    tickSeparation = bounds.height / (majorTicks * 2);
	else
	    tickSeparation = bounds.width / (majorTicks * 2);
	tickNumberSeparation = ((orientation == VERTICAL) ? yDiff : xDiff) / (majorTicks * 2);
		
	// Calculate tick heights
	double smallerAxis = (xPixels >= yPixels) ? yPixels : xPixels;
	int majorTickHeight = (int)Math.ceil(smallerAxis * .02);
	int[] tickHeights = {majorTickHeight, majorTickHeight/2};

	for (tick = 0.0; tick <= majorTicks*2; tick += 1.0) {
	    // Temporary pixel values
	    tickPixel = (tickSeparation * (tick));
	    tickNumber = (tickNumberSeparation * tick) + xSmallest;
	    tickNumber = (Math.ceil(tickNumber*10)/10);

	    // Draw the tick lines
	    if (gridLines && (tick % 2 == 0)) {
		tickHeight = ((orientation == VERTICAL) ? xPixels : yPixels);
		g2.setColor(Color.GRAY);
		// Now draw the lines
		if (orientation == VERTICAL) {
		    // Draw y-axis tick
		    tPoint0 = new Point2D.Double(0.0, yPixels-tickPixel);
		    tPoint1 = new Point2D.Double(tickHeight, yPixels-tickPixel);
		    g2.draw(new Line2D.Double(tPoint0, tPoint1));
		}
		else {
		    // Draw x-axis tick			
		    tPoint0 = new Point2D.Double(tickPixel, yPixels);
		    tPoint1 = new Point2D.Double(tickPixel, yPixels-tickHeight);
		    g2.draw(new Line2D.Double(tPoint0, tPoint1));
		}
	    }

	    tickHeight =  tickHeights[((int)tick % 2)];
	    g2.setColor(Color.BLACK);
	    if (orientation == VERTICAL) {
		// Draw y-axis tick
		tPoint0 = new Point2D.Double(0.0, yPixels-tickPixel);
		tPoint1 = new Point2D.Double(tickHeight, yPixels-tickPixel);
		g2.draw(new Line2D.Double(tPoint0, tPoint1));
	    }
	    else {
		// Draw x-axis tick			
		tPoint0 = new Point2D.Double(tickPixel, yPixels);
		tPoint1 = new Point2D.Double(tickPixel, yPixels-tickHeight);
		g2.draw(new Line2D.Double(tPoint0, tPoint1));
	    }
	}
	// Paint the ticks now!
	data_ = data;
    }
}
