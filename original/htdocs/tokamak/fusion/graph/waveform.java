/*
 * class waveform
 * 
 *    Copyright (C) 2004, Daren Stotler
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
package graph;

import java.awt.*;
import java.applet.*;
import java.net.URL;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

/**
 *    This class is an extension of Graph2D class.
 *    It adds interactive selection of the plotting range
 *    and can display the mouse position in user coordinates.
 *
 *    <h4>Mouse Events</h4>
 *    <dl>
 *     <dt>MousePress
 *     <dd>Starts the range selection
 *     <dt>MouseDrag
 *     <dd>Drag out a rectangular range selection
 *     <dt>MouseUp
 *     <dd>Replot with modified plotting range.
 *     <dt>
 *    </dl>
 *    <h4>Shift-Mouse Events</h4>
 *    <dl>
 *    <dt>MouseClick
 *    <dd>If near an existing point (highlighted on MouseDown), that point is deleted,
 *    <dd>Otherwise, a new point is added.
 *    <dt>MousePress
 *    <dd>Done near an existing point, highlights that point for deletion or dragging
 *    <dt>MouseDrag
 *    <dd>Drags the highlighted point to a new location
 *    <dt>MouseUp
 *    <dd>Adds the dragged point to the data set and deletes the original
 *    </dl>
 *    <h4>KeyDown Events</h4>
 *    <dl>
 *     <dt>R
 *     <dd>Redraw plot with default limits
 *     <dt>r
 *     <dd>Redraw plot using current limits
 *     <dt>m
 *     <dd>Pop window to enter manually plot range
 *     <dt>a
 *     <dd>Pop up a window to manually enter a point
 *     <dt>c
 *     <dd>Toggle pop-up window that displays the mouse position
 *         in user coordinates
 *     <dt>d
 *     <dd>Show coordinates of the closest data point to the cursor
 *     <dt>D
 *     <dd>Hide data coordinates pop-window
 *    </dl>
 *    <P>
 * Constraints:
 * These data sets are intended to be used in conjunction with numerical
 * integration schemes. For this reason, the abscissa must be monotonically
 * increasing. The initial data set is checked to be sure this is true. The
 * various operations are constrained to preserve it.
 *
 * A flag can be set which fixes the end values of the abscissas to their
 * initial values
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:20:25 $.
 * @author Leigh Brookshaw, Daren Stotler
 * @see G2Dint
 * @see WFDataSet
 * @see TestApplications.waveformtest
 */

/*
 * $Log: waveform.java,v $
 * Revision 1.1  2004/10/07 20:20:25  dstotler
 * Added to repository.
 *
 *
 */

public class waveform extends G2Dint {

    WFDataSet data1;

    /**
     * Set to true when a point is being dragged by the mouse
     */
    protected boolean pointdrag = false;
    /** 
     * Set to true with a shift-click of the mouse (to distinguish a click
     * from a drag)
     */
    protected boolean shiftclicked = false;
    /**
     * Keeps track of the index of the point being dragged
     */
    protected int id_drag;
    /*
     * Temporary DataSet used to show dragging of a point
     */
    protected WFDataSet tempset;
    /**
     * Flag that determines whether or not x values of the endpoints are fixed.
     */
    protected boolean endxfixed = true;
    /**
     * Flag that determines whether or not the y values of the data set are
     * normalized.
     */
    protected boolean normalized = false;
    /**
     * Flag specifying whether or not all y values are required to be 
     * positive.  Do not currently have an analogous flag for x since the
     * endxfixed flag can do effectively the same thing.
     */
    protected boolean allypositive = true;
    /**
     * The popup window for manually inputting a point
     */
    private PointInput pointin = null;
    /*
    **     Factor used in relating distance used to discern points to be 
    **     added from those to be deleted to the markerscale.  I.e., the 
    **     deletion criterion is now phrased in terms of pixels.
    */
    private int deletedist = 2;
    /**    
     *     Number of points in the primary DataSet, data1.  Need to 
     *     keep separate since the number of points can change.
     */
    protected int np;

    public waveform (WFDataSet ds) throws Exception {

	super();
	data1=ds;
	np=data1.dataPoints();

	/* 
	 * Check constraints on the initial data.  The manipulations performed
	 * by the various methods in this class assume these to be true and
	 * will enforce them as the data points are changed.
	 */
	boolean testorder = data1.inOrder();
	if (!testorder) throw
			    new Exception("Abscissa of data must be monotonically increasing!");
	if (allypositive && (data1.getYmin() <= 0.)) throw
							 new Exception("All data ordinates must be non-negative!");
	if (normalized) data1.normalize();
	attachDataSet(data1);
    }

    /**
     * Handle the Key Down events.
     */
    public void keyTyped(KeyEvent e) {

	char key = e.getKeyChar();

	System.out.println("key = " + key);

	switch ( key ) {

	case 'a':
	    if (pointin == null) pointin = new PointInput(this);
	    pointin.setVisible(true);
	    pointin.requestFocus();
	    return;

	default:
	    /* 
	     * Will this take care of the others?
	     */
	    super.keyTyped(e);
	    return;
	}

    }

    /**
     * Handle the Mouse Press events
     */
    public void mousePressed(MouseEvent e) {
	/*
	** Soon as the mouse button is pressed request the Focus
	** otherwise we will miss key events
	*/
	requestFocus();
	int x = e.getX();
	int y = e.getY();
		
	if (!e.isShiftDown()) {
	    super.mousePressed(e);
	}
	else {
	    shiftclicked = true;
	    double d[] = new double[2];
	    d[0] = xaxis.getDouble(x);
	    d[1] = yaxis.getDouble(y);
	    double dclose[] = new double[3];
	    dclose = data1.getClosestPoint(d[0],d[1]);
	    /*
	     * Compute distance of mouse from closest point in pixel
	     * terms.  Note: have to do this here since it involves
	     * both axis and data properties.
	     */
	    int xclose = xaxis.getInteger(dclose[0]);
	    int yclose = yaxis.getInteger(dclose[1]);
	    double pixel_distance = java.lang.Math.sqrt((xclose-x)*(xclose-x)+(yclose-y)*(yclose-y));
	    System.out.println("pixel distance = " + pixel_distance);
	    System.out.println("markerscale = " + data1.markerscale);
	    if (pixel_distance < deletedist*data1.markerscale) {
		d[0]=dclose[0];
		d[1]=dclose[1];
		id_drag=1;
		try {
		    id_drag = data1.getIndex(d);
		}
		catch(Exception ex) {
		    System.out.println("Unable to grab point in mousePressed."); 
		}
		Graphics g  = getGraphics();
		g.setColor(Color.red);
		getMarkers().draw(g, data1.marker, data1.markerscale+1, xclose, yclose);
		int pt1 = id_drag-1;
		int pt2 = id_drag;
		int pt3 = id_drag+1;
		if (pt1 < 0) pt1=id_drag;
		if (pt3 >= np) pt3=id_drag;
		double tempdata[] = new double[6];
		double temppt[] = new double[2];
		temppt = data1.getPoint(pt1);
		tempdata[0] = temppt[0];
		tempdata[1] = temppt[1];
		temppt = data1.getPoint(pt2);
		tempdata[2] = temppt[0];
		tempdata[3] = temppt[1];
		temppt = data1.getPoint(pt3);
		tempdata[4] = temppt[0];
		tempdata[5] = temppt[1];
		tempset = null;
		try {
		    tempset = new WFDataSet(tempdata,3);
		    attachDataSet(tempset);
		}
		catch (Exception ex2) {
		    System.out.println("Failed to load temporary data.");
		}
		xaxis.attachDataSet(tempset);
		yaxis.attachDataSet(tempset);
		tempset.marker=data1.marker;
		tempset.markerscale=data1.markerscale+1;
		tempset.markercolor=Color.blue;
		tempset.linecolor=Color.blue;
	    }
	}
    }
    /**
     * Handle the Mouse Release events
     */
    public void mouseReleased(MouseEvent e) {
	int x = e.getX();
	int y = e.getY();

	if (!shiftclicked) {
	    /* 
	     * This will also do a repaint();
	     */
	    super.mouseReleased(e);
	}
	else {
	    if (pointdrag) {
		double d[] = new double[2];
		/*
		 * Recall that x1 and y1 are set in mouseDragged.
		 * Should be same as x and y here.
		 */
		d[0] = xaxis.getDouble(x1); 
		d[1] = yaxis.getDouble(y1);
		/*
		 * Here we enforce the endxfixed option. 
		 */
		if ((endxfixed) && (id_drag == 0 || id_drag == np-1)) {
		    d[0] = data1.getPoint(id_drag)[0];
		}
		/*
		 * Accept the new point only if it is between the
		 * adjacent points or is an end point.  This also
		 * prevents an end point from being dragged passed
		 * passed the next-to-end point.  A final test
		 * ensures that the point has a positive y if
		 * that is being required according to allypositive. 
		 */
		if ( ((id_drag == 0) 
		      || (d[0] > tempset.getPoint(0)[0])) 
		     && ((id_drag == np-1) 
			 || (d[0] < tempset.getPoint(2)[0]))
		     && (!allypositive || (d[1] > 0.))) {
		    data1.swapPoint(id_drag,d);
		    if (normalized) data1.normalize();
		}
		pointdrag=false;
	    }
	    detachDataSet(tempset);
	    shiftclicked=false;
	}
	repaint();
    }

   public void mouseClicked(MouseEvent e) {
      mousex=e.getX();
      mousey=e.getY();

      int modifiers = e.getModifiers();
      /*
       * This first gets the point closest to the mouse click. If that point is
       * less than deletedist from the mouse click, the point gets deleted.
       */
      if (e.isShiftDown()) {
	double d[] = new double[2];
	d[0] = xaxis.getDouble(mousex);
	d[1] = yaxis.getDouble(mousey);
	System.out.println("add / delete point: "+d[0]);
	System.out.println(d[1]);
	if (endxfixed) {
	    /*
	     * Throw out points outside of the current x range.
	     * Note that this assumes the x values are in order,
	     * as was verified at the start. Could use assertion
	     * facility to check.
	     */
	    double xmin = data1.getPoint(0)[0];
	    double xmax = data1.getPoint(np-1)[0];
	    if ((d[0] < xmin) || (d[0] > xmax)) {
		System.out.println("abscissa out of allowed range");
		return;
	    }
	}
	int imatch = data1.matchX(d[0]);
	if (allypositive && (d[1] <= 0.)) {
	    System.out.println("ordinate of new point must be non-negative");
	    return;
	}
	double dclose[] = new double[3];
	dclose = data1.getClosestPoint(d[0],d[1]);
	System.out.println("distance to nearest point = "+dclose[2]);
	int xclose = xaxis.getInteger(dclose[0]);
	int yclose = yaxis.getInteger(dclose[1]);
	double pixel_distance = java.lang.Math.sqrt(
	      (xclose-mousex)*(xclose-mousex)+(yclose-mousey)*(yclose-mousey));
	if (pixel_distance < deletedist*data1.markerscale) {
	  d[0]=dclose[0];
	  d[1]=dclose[1];
	  int id=-1;
	  try {
	      id = data1.getIndex(d);
	  }
	  catch(Exception ex) {
	      System.out.println("getIndex fails to find a point in data set!");
	  }
	  if ((endxfixed) && (id == 0 || id == np-1)) {
	      System.out.println("cannot delete first or last point in this mode");
	      return;
	  }
	}
	else if ((imatch >= 0) && (imatch < np)) {
	    System.out.println("abscissa of new point matches existing point: "
			       +imatch);
	    return;
	}
	System.out.println("revised point for addition / deletion: "+d[0]);
	System.out.println(d[1]);
	np=data1.changePoint(d);
	if (normalized) data1.normalize();
	repaint();
      }
    }

/**
 * Handle the Mouse Drag events
 */
    public void mouseDragged(MouseEvent e) {

	x1   = e.getX();
	y1   = e.getY();
	if(drag) {
	    if(x1 < datarect.x) x1 = datarect.x;
	    else
		if(x1 > datarect.x + datarect.width ) 
		    x1 = datarect.x + datarect.width;
	    if(y1 < datarect.y) y1 = datarect.y;
	    else
		if(y1 > datarect.y + datarect.height ) 
		    y1 = datarect.y + datarect.height;
	}
	else if (shiftclicked) {
	    pointdrag = true;
	    double d[] = new double[2];
	    d[0] = xaxis.getDouble(x1);
	    d[1] = yaxis.getDouble(y1);
	    /*
	     * Here we enforce the endxfixed option. 
	     */
	    if ((endxfixed) && (id_drag == 0 || id_drag == np-1)) {
		d[0] = data1.getPoint(id_drag)[0];
	    }
	    /*
	     * Accept the new point only if it is between the
	     * adjacent points or is an end point.  This also
	     * prevents an end point from being dragged passed
	     * passed the next-to-end point. A final test
	     * ensures that the point has a positive y if
	     * that is being required according to allypositive. 
	     */
	    if ( ((id_drag == 0) 
		  || (d[0] > tempset.getPoint(0)[0])) 
		 && ((id_drag == np-1) 
		     || (d[0] < tempset.getPoint(2)[0]))
		 && (!allypositive || (d[1] > 0.))) {
		tempset.swapPoint(1,d);
		if (id_drag == 0) tempset.swapPoint(0,d);
		if (id_drag == np-1) tempset.swapPoint(2,d);
	    }
	    /*
	     * Otherwise, cancel the dragging event
	     */
	    else {
		detachDataSet(tempset);
	    }
	}
	
	if(cpgin != null && cpgin.isVisible()) {
	    cpgin.setXlabel(  xaxis.getDouble(x1) );
	    cpgin.setYlabel(  yaxis.getDouble(y1) );
	}

	repaint();
    }
    /** 
     * Action method for inputting a point manually.
     */
    public void pointAction() {
	if (pointin != null) {
	    double d[] = new double[2];
	    Double dobjx = pointin.getXval();
	    Double dobjy = pointin.getYval();
	    if ((dobjx != null) && (dobjy != null)) {
		d[0] = dobjx.doubleValue();
		d[1] = dobjy.doubleValue();
		int i = data1.matchX(d[0]);
		if ((i >= 0) && (i < np)) {
		    System.out.println("abscissa of new point matches existing point: "+i);
		    return;
		}
		if (allypositive && (d[1] <= 0.)) {
		    System.out.println("ordinate of new point must be non-negative");
		    return;
		}
		if (endxfixed) {
		    /*
		     * Throw out points outside of the current x range.
		     * Note that this assumes the x values are in order,
		     * as was verified at the start. Could use assertion
		     * facility to check.
		     */
		    double xmin = data1.getPoint(0)[0];
		    double xmax = data1.getPoint(np-1)[0];
		    if ((d[0] < xmin) || (d[0] > xmax)) {
			System.out.println("abscissa out of allowed range");
			return;
		    }
		}
		np=data1.changePoint(d);
		if (normalized) data1.normalize();
		repaint();
	    }
	}
    }
}	





