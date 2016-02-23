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
**    Class  Range
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
 *    A  popup window for altering the range of the plot
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:12:08 $.
 * @author Leigh Brookshaw, Daren Stotler
 */

/*
 * $Log: Range.java,v $
 * Revision 1.1  2004/10/07 20:12:08  dstotler
 * Added to repository.
 *
 *
 */


class Range extends Frame implements ActionListener {

     G2Dint g2d = null;
    //     waveform g2d = null;


     private Label xminLabel = new Label("Xmin");
     private Label yminLabel = new Label("Ymin");
     private Label xmaxLabel = new Label("Xmax");
     private Label ymaxLabel = new Label("Ymax");

     private TextField xminText = new TextField(20);
     private TextField yminText = new TextField(20);
     private TextField xmaxText = new TextField(20);
     private TextField ymaxText = new TextField(20);

     private Button cancel = new Button("Cancel");
     private Button done   = new Button("Done");


     public Range(G2Dint g) {
	 //     public Range(waveform g) {

         g2d = g;

         setLayout(new GridLayout(5,2,5,10) ) ;

         xminLabel.setAlignment(Label.LEFT);
         xmaxLabel.setAlignment(Label.LEFT);
         yminLabel.setAlignment(Label.LEFT);
         ymaxLabel.setAlignment(Label.LEFT);


         add("xminLabel",xminLabel);
         add("xminText",xminText);

         add("xmaxLabel",xmaxLabel);
         add("xmaxText",xmaxText);

         add("yminLabel",yminLabel);
         add("yminText",yminText);

         add("ymaxLabel",ymaxLabel);
         add("ymaxText",ymaxText);

         add("cancel", cancel);
         cancel.setBackground(Color.red);
	 cancel.addActionListener(this);

         add("done",done);
         done.setBackground(Color.green);
	 done.addActionListener(this);

         setSize(250,250);
         super.setTitle("Set Plot Range");
     }

     public Double getXmin() {
         try {
               return Double.valueOf(xminText.getText());
             }
         catch (Exception ex) {
               return null;
	     }
     }
     
     public Double getXmax() {
         try {
               return Double.valueOf(xmaxText.getText());
             }
         catch (Exception ex) {
               return null;
	     }
     }

     public Double getYmin() {
         try {
               return Double.valueOf(yminText.getText());
             }
         catch (Exception ex) {
               return null;
	     }
     }
     
     public Double getYmax() {
         try {
               return Double.valueOf(ymaxText.getText());
             }
         catch (Exception ex) {
               return null;
	     }
     }


     public void setSize( int x, int y) {
        super.setSize(x,y);
     }
 
     public void requestFocus() {
       xminText.requestFocus();
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
	          g2d.rangeAction();
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
