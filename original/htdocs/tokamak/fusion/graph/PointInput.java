/*
 * class PointInput
 *
 *    Copyright (C) 1996-2004, Daren Stotler
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
 *    A  popup window for inputting a point to appear on the plot.
 *    This is just a slightly modified version of Brookshaw's Range class.
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:06:29 $.
 * @author Daren Stotler, Leigh Brookshaw
 * @see Range
 */

/*
 * $Log: PointInput.java,v $
 * Revision 1.1  2004/10/07 20:06:29  dstotler
 * Added to repository.
 *
 *
 */

class PointInput extends Frame implements ActionListener {

//     Graph2D g2d = null;
     waveform g2d = null;


     private Label xLabel = new Label("X");
     private Label yLabel = new Label("Y");

     private TextField xText = new TextField(20);
     private TextField yText = new TextField(20);

     private Button cancel = new Button("Cancel");
     private Button done   = new Button("Done");


//     public Range(Graph2D g) {
     public PointInput(waveform g) {

         g2d = g;

         setLayout(new GridLayout(3,2,5,10) ) ;

         xLabel.setAlignment(Label.LEFT);
         yLabel.setAlignment(Label.LEFT);

         add("xLabel",xLabel);
         add("xText",xText);

         add("yLabel",yLabel);
         add("yText",yText);

         add("cancel", cancel);
         cancel.setBackground(Color.red);
	 cancel.addActionListener(this);

         add("done",done);
         done.setBackground(Color.green);
	 done.addActionListener(this);

         setSize(150,150);
         super.setTitle("Specify Point");
     }

     public Double getXval() {
         try {
               return Double.valueOf(xText.getText());
             }
         catch (Exception ex) {
               return null;
	     }
     }
     
     public Double getYval() {
         try {
               return Double.valueOf(yText.getText());
             }
         catch (Exception ex) {
               return null;
	     }
     }

     public void setSize( int x, int y) {
        super.setSize(x,y);
     }
 
     public void requestFocus() {
       xText.requestFocus();
     }
/*
** Handle the events
*/

/*
   public boolean keyDown(Event e, int key) {

         if(e.target instanceof TextField) {

            if( ( key == 10 || e.key == 13 ) ) {

                        if(xminText.equals(e.target)) {
                          xmaxText.requestFocus();
                          return true;
                        } else
                        if(xmaxText.equals(e.target)) {
                          yminText.requestFocus();
                          return true;
                        } else
                        if(yminText.equals(e.target)) {
                          ymaxText.requestFocus();
                          return true;
                        } else
                        if(ymaxText.equals(e.target)) {
                          xminText.requestFocus();
                          return true;
		        }


                        return true;
            }

	 }

         return false;

   }

*/
/*
    public boolean action(Event e, Object a) {
*/
    public void actionPerformed(ActionEvent e) {

//         if(e.target instanceof Button) {
         String arg = e.getActionCommand();
//             if( done.equals(e.target) && g2d != null) {
         if (arg.equals("Done") && g2d != null) {
	          g2d.pointAction();
//                  g2d.deliverEvent( new Event(this,Event.ACTION_EVENT,this) );
                  this.setVisible(false);
//                  return true;
/*
             } else 
             if( cancel.equals(e.target) ) {
*/
		}
	 else if (arg.equals("Cancel")) {
                  this.setVisible(false);
//                  return true;
	     }
       }


//         return false;
   }
