package graph;

import java.awt.*;
import java.lang.*;

/*
**************************************************************************
**
**                      Class  graph.LoadMessage
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
**    This class was split off from Graph2D.java.
**
*************************************************************************/

/**
 *   This is a separate thread that flashes a message
 *   on the Graph2D canvas that data is loading
 * @version  $Revision: 1.1 $, $Date: 2004/10/07 20:04:59 $.
 * @author   Leigh Brookshaw, Daren Stotler
 */

/*
 * $Log: LoadMessage.java,v $
 * Revision 1.1  2004/10/07 20:04:59  dstotler
 * Added to repository.
 *
 *
 */

public class LoadMessage extends Thread {
        Graph2D  g2d;
        String   message    = "Loading Data ... Please Wait!";
        String   newmessage = null;
        long     visible    = 500;
        long     invisible  = 200;
        Color    foreground = Color.red;
        Graphics lg = null;
        Font     f = null;
    boolean  isalive = false;
        
/**
 *    Instantiate the class
 * @param g2d The Graph2D canvas to draw message on
 *
 */            
        public LoadMessage(Graph2D g2d) {
           this.g2d = g2d;

        }

/**
 *  Instantiate the class
 * @param g2d The Graph2D canvas to draw message on
 * @param s   The string to flash on the canvas
 */            
        public LoadMessage(Graph2D g2d, String s) {

           this(g2d);
           this.message = s;

        }
/**
 *  Instantiate the class
 * @param g The Graph2D canvas to draw message on
 * @param s   The string to flash on the canvas
 * @param visible Number of milliseconds the message is visible
 * @param invisible Number of milliseconds the message is invisible
 */            

        public LoadMessage(Graph2D g, String s, long visible, long invisible) {
            this(g,s);
            this.visible = visible;
            this.invisible = invisible;
        }

/**
 *   begin displaying the message
 */
        public void begin() {
 
	    g2d.clearAll = false;
	    g2d.paintAll = false;
	    isalive = true;

            super.start();

	  }
/**
 *   end displaying message and force a graph repaint
 */
        public void end() {
             
	    //            super.stop();
	    isalive = false;

            g2d.clearAll = true;
            g2d.paintAll = true;

            if(lg != null) lg.dispose();

            g2d.repaint();

	  }
/**
 *   The method to call when the thread starts
 */
        public void run() {
           boolean draw = true;
           FontMetrics fm;
           Rectangle r;
           int sw = 0;
           int sa = 0;
           int x  = 0;
           int y  = 0;

	   setPriority(Thread.MIN_PRIORITY);
	   //     setPriority(Thread.MAX_PRIORITY);

           
	   //           while(true) {
	   while (isalive) {

                if( newmessage != null && draw) {
                    message = newmessage;
                    newmessage = null;
		}

                if(lg == null) {  
                  lg = g2d.getGraphics();
		  if(lg != null) lg = lg.create();
	        }

                if( lg != null) {
                   if(f != null) lg.setFont(f);
                   fm = lg.getFontMetrics(lg.getFont());
                   sw = fm.stringWidth(message);
                   sa = fm.getAscent();
		} else {
                   draw = false;
		 }

                if( draw ) {
                      lg.setColor(foreground);
                      r = g2d.getBounds();
                      x = r.x + (r.width-sw)/2;
                      y = r.y + (r.height+sa)/2;
                      lg.drawString(message, x, y);
		      /*
Was not seeing this message printed in all cases.  Apparently drawing a null
oval, next line, provides a work around.  The message is not printed at all
in those cases in which the data load quickly.  Invoke the sleep method in
LoadData to see the message.
		      */
		      	      lg.drawOval(x, y, 0, 0);
                      g2d.repaint();

                      try { sleep(visible); }
                      catch(Exception e) { }
	        } else {
                      if(lg != null) {
                         lg.setColor(g2d.getBackground());
                         lg.drawString(message, x, y);

                         g2d.repaint();
		       }

                       try { sleep(invisible); }
                       catch(Exception e) { }

		 }

		        draw = !draw;

	      }
	 }

  /**
   * Set the font the message will be displayed in
   * @param f the font
   */
          public void setFont(Font f) {
              this.f = f;
          }
  /**
   *  The foreground color for the message
   * @param c the foreground color
   */

          public void setForeground(Color c) {
              if(c == null) return;
              this.foreground = c;
          }
  /**
   *   Set the message to be displayed
   * @param s the message
   */

          public void setMessage(String s) {
              if(s==null) return;
              newmessage = s;
          }


}
