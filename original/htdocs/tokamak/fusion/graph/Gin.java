package graph;

import java.awt.*;
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
**    Class  Gin
**
**************************************************************************
**    Copyright (C) 1996 Leigh Brookshaw
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
**    This class was split off from G2Dint.java.
**
*************************************************************************/

/**
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:02:12 $
 * @author Leigh Brookshaw, Daren Stotler
 */

/*
 * $Log: Gin.java,v $
 * Revision 1.1  2004/10/07 20:02:12  dstotler
 * Added to repository.
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.10 08-11-99 Extensive changes to bring into Java 1.1 compliance.
 * 1.09 07-02-96 Brookshaw's original version.
 */

class Gin extends Frame implements KeyListener, ActionListener {

     private Label xlabel = new Label();
     private Label ylabel = new Label();

  /**
   * Instantiate the class
   */
     public Gin() {

         setLayout(new GridLayout(3,1) ) ;

         xlabel.setAlignment(Label.LEFT);
         ylabel.setAlignment(Label.LEFT);

         this.setFont(new Font("Helvetica", Font.PLAIN, 20));
	 /*
	   Workaround for bug noted below in setFont.
	 */
	 Font f = this.getFont();
	 xlabel.setFont(f);
	 ylabel.setFont(f);

         add("x",xlabel);
         add("y",ylabel);

         super.setTitle("Graphics Input");

	 addKeyListener(this);
	 Button close = new Button("Close");
	 add("close",close);
	 close.addActionListener(this);
         setSize(150,150);
     }
  /**
   * Instantiate the class. 
   * @param title the title to use on the pop-window.
   */

     public Gin(String title) {
         this();
         if(title != null) super.setTitle(title);
       }

  /**
   *  Set the X value
   * @param d The value to set it
   */

     public void setXlabel(double d) {
         xlabel.setText( String.valueOf(d) );
     }

  /**
   *  Set the Y value
   * @param d The value to set it
   */

     public void setYlabel(double d) {
         ylabel.setText( String.valueOf(d) );
     }

  /**
   *  Set the both values
   * @param dx The X value to set
   * @param dy The Y value to set
   */

     public void setLabels(double dx, double dy) {
         xlabel.setText( String.valueOf(dx) );
         ylabel.setText( String.valueOf(dy) );
     }

  /**
   * Set the display font
THIS METHOD WAS CAUSING A CRASH IN THE WINDOWS ENVIRONMENT.  WORKAROUND IS TO
SET THE LABEL FONTS IN THE CONSTRUCTOR.

     public void setFont( Font f ) {

        if ( f == null ) return;
        xlabel.setFont( f );
        ylabel.setFont( f );

     }
   */
  /**
   * Set the size of the window
   * @param x width in pixels
   * @param y height in pixels
   */
     public void setSize( int x, int y) {
        super.setSize(x,y);
     }

  /**
   * Catch the Key Down event 'h'. If the key is pressed then
   * hide this window.
   *
   * @param e The event
   * @param key the key pressed
   *
   * NOTE: Because KeyEvent's are not passed up the component hierarchy
   * (see AWTEvent), this listener method will never be called once 
   * Components (i.e., the labels in this case) are added to the Gin
   * frame. Will leave this code intact in case this behavior changes.
   */

/*
     public boolean keyDown(Event e, int key) {
*/
     public void keyTyped(KeyEvent e) {

             char key = e.getKeyChar();

	       System.out.println("key = " + key);

             if( key == 'h' ) {
                                  System.out.println("key is h");
                                  this.setVisible(false);
                                  return;
				}

//             return false;

     }

     public void keyPressed(KeyEvent e) {
     }

     public void keyReleased(KeyEvent e) {
     }

     public void actionPerformed(ActionEvent e) {
       System.out.println(" In Gins actionPerformed");
       String arg = e.getActionCommand();
       if (arg.equals("Close") && this.isVisible()) {
	 this.setVisible(false);
	 return;
       }
       return;
     }
}
