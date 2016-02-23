/*
 * class DataFrame2
 */
package ssfd;

import java.awt.*;
import java.util.*;
import java.applet.*;
import java.lang.*;
import observable.ObservableDbl;
import observable.ObservableInt;

/**
 *  This program is designed to work with Daren Stotler's
 *  SSFD program.  It takes the density, bfield, and aux heating
 *  inputs from that prgram and puts them in a datatable along
 *  with the Q value and temp calculated in the simulation.
 *  The datatable is drawn in a separate window.
 *
 * @author Matthew Fritz
 */

/*
 *  There is one bug that I know of:  on the MAC the window is
 *  initially drawn blank, but will draw properly subsequently.
 *  I wasn't able to track down the cause of this problem.
 *  Some things that can be added or improved in the future:
 *  1)  make the window scrollable.  Right now the table starts 
 *  deleting the first points after a bunch of trials have been
 *  done.  2)  The table could be modified to indicate the units
 *  of the paramaters.
 */
public class DataFrame2 extends Frame implements Observer {
  Vector var1v, var2v, var3v, var4v, var5v;
  ObservableDbl  var1, var2, var3, var4, var5;
  ObservableInt index;
  String[] Labels = new String[6];
  String[] labels;
  double x1, x2, x3, x4, x5;
  int trial;

  DataFrame2(ObservableInt Index, ObservableDbl Var1, ObservableDbl Var2, ObservableDbl Var3, ObservableDbl Var4, ObservableDbl Var5, String[] labels){

//  These Vectors hold the data
    var1v=new Vector();
    var2v=new Vector();
    var3v=new Vector();
    var4v=new Vector();
    var5v=new Vector();

//  index is the "special" variable.  When it changes, update is called
    index=Index;
    index.addObserver(this);
//  labels is an array of strings holding the column headings
  System.arraycopy(labels, 0, Labels, 0,6);
//  Labels[1]=labels[1];
//  Labels[2]=labels[2];
//  Labels[3]=
//  Method to receive data and add to vectors
    var1=Var1;
    var2=Var2;
    var3=Var3;
    var4=Var4;
    var5=Var5;
   
    
  }

public void update(Observable obs, Object ar){
//  Add new data to the vector with the rest of the data
//  the data is rounded to the hundredths place
  x1=Math.floor(100*var1.getValue())/100;
  x2=Math.floor(100*var2.getValue())/100;
  x3=Math.floor(100*var3.getValue())/100;
  x4=Math.floor(100*var4.getValue())/100;
  x5=Math.floor(100*var5.getValue())/100;
 // System.out.println("HERE");
 // System.out.println(x5);
//  they are added to the vector as strings to make drawing easier
  var1v.addElement(String.valueOf(x1));
  var2v.addElement(String.valueOf(x2));
  var3v.addElement(String.valueOf(x3));
  var4v.addElement(String.valueOf(x4));
  var5v.addElement(String.valueOf(x5));
  trial=index.getValue();
  repaint();
}


//  Method to paint the datatable
    public void paint(Graphics g) {
//f  max is the maximum number of data points to show in the data table
      int max=25;

//   end is the number of data points to display (<=max)
      int end;

//  Write the Table Headings
    g.setColor(Color.blue);
    g.drawString(Labels[0], 50,70);
    g.drawString(Labels[1], 84,70); 
    g.drawString(Labels[2], 125,70);
    g.drawString(Labels[3], 173,70);
    g.drawString(Labels[4], 224,70);
    g.drawString(Labels[5], 280,70);

//  Write the data
      int start;
//   Test to see if there are more than max number of data points, if so
//   only show the most recent

      if (trial>max) {start=trial-max;
        end=25;}
      else {start=0;
	    end=trial;}
// System.out.println(trial);
//  Loop to get data from vectors and print the data table
//  the i loop runs though each trial (the rows)
//  the j loop prints the columns
      for (int i=1; i<=end; i+=1){
	for (int j=1; j<7; j +=1) {
	  if(trial==0) return;
	  g.setColor(Color.black);
	  g.drawRect(40, i*25+50, 40, 25);
	  g.drawString(String.valueOf(start+i), 50, i*25+70);
	  g.drawRect(80, i*25+50, 40, 25);
	  g.drawString(String.valueOf(var1v.elementAt(start+i-1)), 85, i*25+70);
	  g.drawRect(120, i*25+50, 50, 25);
	  g.drawString(String.valueOf(var2v.elementAt(start+i-1)), 125, i*25+70);
	  g.drawRect(170, i*25+50, 50, 25);
	  g.drawString(String.valueOf(var3v.elementAt(start+i-1)), 180, i*25+70);
	  g.drawRect(220, i*25+50, 50, 25);
	  g.drawString(String.valueOf(var4v.elementAt(start+i-1)), 225, i*25+70);
	  g.drawRect(270, i*25+50, 50, 25);
	  g.setColor(Color.red);
	  g.drawString(String.valueOf(var5v.elementAt(start+i-1)), 275, i*25+70);
	  
	}
      }
    }
}












