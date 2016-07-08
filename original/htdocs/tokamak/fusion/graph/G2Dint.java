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

/*
**************************************************************************
**
**    Class  G2Dint
**
**************************************************************************
**    Copyright (C) 1995, 1996 Leigh Brookshaw
**
**    This program is free software; you can redistribute it and/or modify
**    it under the terms of the GNU General Public License as published by
**    the Free Software Foundation; either version 2 of the License, or
**    (at your option) any later version.
**
**    This program is distributed in the hope that it will be useful,
**    but WITHOUT ANY WARRANTY; without even the implied warranty of
**    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**    GNU General Public License for more details.
**
**    You should have received a copy of the GNU General Public License
**    along with this program; if not, write to the Free Software
**    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
**************************************************************************
**
**    This class is an extension of Graph2D class.
**    It adds interactive selection of the plotting range
**    and can display the mouse position in user coordinates.
**
*************************************************************************/

/**
 *    This class is an extension of Graph2D class.
 *    It adds interactive selection of the plotting range
 *    and can display the mouse position in user coordinates.
 *
 *    <h4>Mouse Events</h4>
 *    <dl>
 *     <dt>MouseDown
 *     <dd>Starts the range selection
 *     <dt>MouseDrag
 *     <dd>Drag out a rectangular range selection
 *     <dt>MouseUp
 *     <dd>Replot with modified plotting range.
 *     <dt>
 *    </dl>
 *    <h4>KeyDown Events</h4>
 *    <dl>
 *     <dt>R
 *     <dd>Redraw plot with default limits
 *     <dt>r
 *     <dd>Redraw plot using current limits
 *     <dt>m
 *     <dd>Pop window to enter manually plot range
 *     <dt>c
 *     <dd>Toggle pop-up window that displays the mouse position
 *         in user coordinates
 *     <dt>d
 *     <dd>Show coordinates of the closest data point to the cursor
 *     <dt>D
 *     <dd>Hide data coordinates pop-window
 *     <dt>h
 *     <dd>This key pressed in Any pop-window at any-time will hide it.
 *         <B>Note:</B> Due to differences in event handling between Java
 *         1.0 and 1.1, this feature no longer works. A Close button has
 *         been added to the pop-windows instead.
 *    </dl>
 *    <P>
 *
 * @version $Revision: 1.2 $, $Date: 2004/10/07 20:00:30 $.
 * @author Leigh Brookshaw, Daren Stotler
 */

/*
 * $Log: G2Dint.java,v $
 * Revision 1.2  2004/10/07 20:00:30  dstotler
 * Switch some instance variables from private to protected to facilitate subclassing. Move Gin and Range to separate files.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.10 08-11-99 Extensive changes to bring into Java 1.1 compliance.
 * 1.09 07-02-96 Brookshaw's original version.
 */

public class G2Dint extends Graph2D implements MouseListener, MouseMotionListener, KeyListener {

/**
 *    Set to true when a rectangle is being dragged out by the mouse
 */
      protected boolean drag = false;
/**
 *    User limits. The user has set the limits using the mouse drag option
 */
      protected boolean userlimits = false;

/**
 *    Ths popup window for the cursor position command
 */
      protected Gin cpgin = null;
/**
 *    Ths popup window for the data point command
 */
      private Gin dpgin = null;
/**
 *    The popup window to manually set the range
 */
      private Range range = null;
/**
 *    Button Down position
 */
      private int x0,y0;
/**
 *    Button Drag position
 */
      protected int  x1,y1;
/*
**    Previous Button Drag position
*/
      private int x1old, y1old;

/*
**    Coordinates of mouse click
*/
      protected int mousex,mousey;

/**
 *    Attached X Axis which must be registered with this class.
 *    This is one of the axes used to find the drag range.
 *    If no X axis is registered no mouse drag.
 */
      protected Axis xaxis;
/**
 *    Attached Y Axis which must be registered with this class.
 *    This is one of the axes used to find the drag range.
 *    If no Y axis is registered no mouse drag.
 */
      protected Axis yaxis;


/**
 *     Constructor implemented to set up listeners.
 */
      public G2Dint () {
	addMouseListener(this);
	addMouseMotionListener(this);
	addKeyListener(this);
      }

/**
 *    Create Xaxis to be used for the drag scaling
 */
      public Axis createXAxis() {
         xaxis = super.createAxis(Axis.BOTTOM);
         return xaxis;
      }
/**
 *    Create Yaxis to be used for the drag scaling
 */
      public Axis createYAxis() {
         yaxis = super.createAxis(Axis.LEFT);
         return yaxis;
      }
/**
 *    Attach axis to be used for the drag scaling. X axes are assumed to
 *    have Axis position Axis.BOTTOM or Axis.TOP. Y axes are assumed
 *    to have position Axis.LEFT or Axis.RIGHT.
 * @param a Axis to attach
 * @see Axis
 */
      public void attachAxis(Axis a) {
         if(a==null) return;

         super.attachAxis(a);         

         if(a.getAxisPos() == Axis.BOTTOM || a.getAxisPos() == Axis.TOP) {
              xaxis = a;
         } else {
              yaxis = a;
         }
      }
/**
 *  New update method incorporating mouse dragging.
 */
    public void update(Graphics g) {
          Rectangle r = getBounds();
          Color c = g.getColor();

	  /* The r.x and r.y returned from bounds is relative to the
	  ** parents space so set them equal to zero
          */
          r.x = 0;
          r.y = 0;



          if(drag) {
	    /**
	     * Set the dragColor. Do it everytime just incase someone
             * is playing silly buggers with the background color. 
	     */
  	     g.setColor(DataBackground);

             float hsb[] = Color.RGBtoHSB(
                           DataBackground.getRed(),
                           DataBackground.getGreen(),
                           DataBackground.getBlue(),
                            null);

              if(hsb[2] < 0.5) g.setXORMode(Color.white);
              else             g.setXORMode(Color.black);

/*
**         Drag out the new box.
**         Use drawLine instead of drawRect to avoid problems
**         when width and heights become negative. Seems drawRect
**         can't handle it!
*/

	   /*
	   ** Draw over old box to erase it. This works because XORMode
	   ** has been set. If from one call to the next the background
           ** color changes going to get some odd results.
	   */
           g.drawLine(x0, y0, x1old, y0);
           g.drawLine(x1old, y0, x1old, y1old);
           g.drawLine(x1old, y1old, x0, y1old);
           g.drawLine(x0, y1old, x0, y0);
	   /*
	   ** draw out new box
	   */
           g.drawLine(x0, y0, x1, y0);
           g.drawLine(x1, y0, x1, y1);
           g.drawLine(x1, y1, x0, y1);
           g.drawLine(x0, y1, x0, y0);
	   /*
	   ** Set color back to default color
	   */
           g.setColor(c);

           x1old = x1;
           y1old = y1;

           return;
           }

          if( clearAll ) {
             g.setColor(getBackground());
             g.fillRect(r.x,r.y,r.width,r.height);
             g.setColor(c);
          }
          if( paintAll ) paint(g);
    }

/**
 * Handle the Key Down events.
 */
/*
   public boolean keyDown(Event e, int key) {
*/
   public void keyTyped(KeyEvent e) {

               char key = e.getKeyChar();

//               if(xaxis==null || yaxis==null) return false;

               switch ( key ) {

               case 'R':
                          xaxis.resetRange();
                          yaxis.resetRange();

                          userlimits = false;

                          repaint();
                          return;
               case 'r':
                              repaint();
                              return;
 	       case 'c':
                             if( cpgin == null) cpgin = new Gin("Position");
                             if( cpgin.isVisible() ) {
                                     cpgin.setVisible(false);
  		             } else {
                                    cpgin.setVisible(true);
		             }
                             return;
 	       case 'D':
                            if(dpgin != null) dpgin.setVisible(false);
                            return; 
 	       case 'd':
                            if(dpgin == null) dpgin = new Gin("Data Point");
                            dpgin.setVisible(true);
/*
   WHAT DO WE DO WITH e.x & e.y HERE???
                            double d[] = getClosestPoint(e.x, e.y);
   We probably need to do some tests on x and y...
*/
			    int x = mousex;
			    int y = mousey;
                            double d[] = getClosestPoint(x, y);
			  
                            dpgin.setXlabel( d[0] );
                            dpgin.setYlabel( d[1] );
                            int ix = xaxis.getInteger(d[0]);
                            int iy = yaxis.getInteger(d[1]);
                            if( ix >= datarect.x &&
                                ix <= datarect.x +datarect.width && 
                                iy >= datarect.y &&
                                iy <= datarect.y +datarect.height ) {
                                Graphics g = getGraphics();
                                g.fillOval(ix-4, iy-4, 8, 8);
 			    }
                            return;
		case 'm':
                            if(range == null) range = new Range(this);
                       
                            range.setVisible(true);
                            range.requestFocus();
                            userlimits = true;
                            return;
	       default:
//                             System.out.println("KeyPress "+e.key);
                             return;
               }

	     }

   public void keyPressed(KeyEvent e) {
   }

   public void keyReleased(KeyEvent e) {
   }

/**
 * Handle the Mouse Down events
 */
/*
    public boolean mouseDown(Event e, int x, int y) {
*/
    public void mousePressed(MouseEvent e) {

//                if(xaxis==null || yaxis==null) return false;
		/*
		** Soon as the mouse button is pressed request the Focus
		** otherwise we will miss key events
		*/
                requestFocus();

/*
   Replacing to Java 1.0 arguments:
*/
		int x = e.getX();
		int y = e.getY();

                x0 = x;
                y0 = y;

                drag = true; 
                x1old = x0;
                y1old = y0;


                if(x0 < datarect.x) x0 = datarect.x;
                else
                if(x0 > datarect.x + datarect.width ) 
                    x0 = datarect.x + datarect.width;

                if(y0 < datarect.y) y0 = datarect.y;
                else
                if(y0 > datarect.y + datarect.height ) 
                    y0 = datarect.y + datarect.height;


//                return true;
     }
/**
 * Handle the Mouse Up events
 */
/*
    public boolean mouseUp(Event e, int x, int y) {
*/
    public void mouseReleased(MouseEvent e) {

//                if(xaxis==null || yaxis==null) return false;

/*
   Replacing the Java 1.0 arguments:
*/
		int x = e.getX();
		int y = e.getY();

                x1   = x;
                y1   = y;

                if(drag) userlimits = true;

                drag = false;


                if(x1 < datarect.x) x1 = datarect.x;
                else
                if(x1 > datarect.x + datarect.width ) 
                    x1 = datarect.x + datarect.width;

                if(y1 < datarect.y) y1 = datarect.y;
                else
                if(y1 > datarect.y + datarect.height ) 
                    y1 = datarect.y + datarect.height;


                if( Math.abs(x0-x1) > 5 &&  Math.abs(y0-y1) > 5 ) {
                   if(x0 < x1 ) {                
                      xaxis.minimum = xaxis.getDouble(x0);
                      xaxis.maximum = xaxis.getDouble(x1);
                   } else {
                      xaxis.maximum = xaxis.getDouble(x0);
                      xaxis.minimum = xaxis.getDouble(x1);
                   }

                   if(y0 >y1 ) {                
                      yaxis.minimum = yaxis.getDouble(y0);
                      yaxis.maximum = yaxis.getDouble(y1);
                   } else {
                      yaxis.maximum = yaxis.getDouble(y0);
                      yaxis.minimum = yaxis.getDouble(y1);
                   }

                   repaint();
                 }
//                return true;
	      }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mouseClicked(MouseEvent e) {
      mousex=e.getX();
      mousey=e.getY();
    }

/**
 * Handle the Mouse Drag events
 */
/*
    public boolean mouseDrag(Event e, int x, int y) {
*/
    public void mouseDragged(MouseEvent e) {

//                if(xaxis==null || yaxis==null) return false;

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
                
                if(cpgin != null && cpgin.isVisible()) {
                            cpgin.setXlabel(  xaxis.getDouble(x1) );
                            cpgin.setYlabel(  yaxis.getDouble(y1) );
                }

                repaint();

 //               return true;


    }
/** 
 * Handle the Mouse Mouve events
 */
/*
    public boolean mouseMove(Event e, int x, int y) {
*/
    public void mouseMoved(MouseEvent e) {

 //               if(xaxis==null || yaxis==null) return false;

                x1   = e.getX();
                y1   = e.getY();
                
                if(cpgin != null && cpgin.isVisible()) {
                            cpgin.setXlabel(  xaxis.getDouble(x1) );
                            cpgin.setYlabel(  yaxis.getDouble(y1) );
                }

//                return true;

    }
/**
 * Handle the Action Events.
 * This handler allows external classes (pop-up windows etc.) to
 * communicate to this class asyncronously.
 */ 
/*
    public boolean action(Event e, Object a) {
*/

/** 
 * Replace the original action method, intended to be generic, with
 * a specific one for clarity. This method will be invoked by an instance
 * of the Range class.
 */
    public void rangeAction() {


//          if(xaxis==null || yaxis==null) return false;

//          if(e.target instanceof Range && range != null) {
          if(range != null) {
            Double d;
            double txmin = xaxis.minimum;
            double txmax = xaxis.maximum;
            double tymin = yaxis.minimum;
            double tymax = yaxis.maximum;



            d = range.getXmin();
            if(d != null) txmin = d.doubleValue();
            d = range.getXmax();
            if(d != null) txmax = d.doubleValue();
            d = range.getYmin();
            if(d != null) tymin = d.doubleValue();
            d = range.getYmax();
            if(d != null) tymax = d.doubleValue();


            if( txmax > txmin && tymax > tymin ) {
                 xaxis.minimum = txmin;
                 xaxis.maximum = txmax;
                 yaxis.minimum = tymin;
                 yaxis.maximum = tymax;
	    }




            repaint();


//            return true;
          }
//           return false;
    }


/**
 *   Find the closest data point to the cursor
 */
     protected double[] getClosestPoint(int ix, int iy) {
        DataSet ds;
        int   i;
        double a[] = new double[3];
        double distsq = -1.0;
        double data[] = {0.0, 0.0};
        double x = xaxis.getDouble(ix);
        double y = yaxis.getDouble(iy);

        //System.out.println("getClosestPoint: x="+x+", y="+y);

        for (i=0; i<dataset.size(); i++) {
               ds = (DataSet)(dataset.elementAt(i));

               a = ds.getClosestPoint(x,y);

               if( distsq < 0.0 || distsq > a[2] ) {
                                    data[0] = a[0];
                                    data[1] = a[1];
                                    distsq  = a[2];
	        }
	}
        return data;

      }

}

