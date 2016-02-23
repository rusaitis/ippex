/*
 * class QGraph2
 */
package ssfd;

import java.awt.*;
import java.applet.*;
import java.net.URL;
import java.util.*;
import observable.ObservableInt;
import observable.ObservableDbl;
import graph.Graph2D;
import graph.DataSet;
import graph.Axis;

 /**
  *  This program was written to work with Daren Stotler's SSFD program
  *  It takes the Q value calculated by that simulation and plots a graph
  *  of Q versus attempt.  this program uses the graph class library
  *  by Leigh Brookshaw and borrow heavily from his "example1"
  *
  * @author Matthew Fritz
  */
public class QGraph2 extends Frame implements Observer{
      Graph2D graph;
      DataSet data1;
      Axis    xaxis;
      Axis    yaxis;
      double data[];
      int np = 1, n=0;
      URL markers;
      Label l;
      int m=30;
      Random random = new Random();
      ObservableInt index;
      ObservableDbl var1;

	

	QGraph2(Applet Markapp, ObservableInt Index, ObservableDbl
		 Var1) {     
	  Applet markapp;
	  markapp=Markapp;  
	  var1=Var1;

//  When the value of index is changed the update method is called
	  index=Index;
	  index.addObserver(this);
//  Create a starting data set with (0,0) as the only point
	double data[] = {0., 0.};
/*
**      Create the Graph instance and modify the default behaviour
*/
        graph = new Graph2D();
        graph.drawzero = false;
        graph.drawgrid = false;
        graph.borderTop = 50;
	graph.borderBottom=50;
	graph.frame=true;
	graph.framecolor = (Color.blue);



//  Location of the marker file
	String mfile    = "marker.txt";
/*
**      Load a file containing Marker definitions
*/
/*        try {
	  markers = new URL(markapp.getCodeBase(), mfile);
        } catch(Exception e) {
           System.out.println("Failed to create Marker URL!");
        }
        if( !graph.loadMarkerFile(markers) ) {
           System.out.println("Failed to create Marker file!");
	   }
*/
//  Use Border Layout, with graph at the center
	setLayout(new BorderLayout());
	add("Center", graph);
/*
**      Calculate the data Set.
*/
        data1 = graph.loadDataSet(data,np);
	data1.linecolor = new Color (0,0,0);
        data1.marker    = 6;
	data1.markerscale = 2;
        data1.markercolor = new Color(0,0,0);
        data1.legend(200,100,"Score vs Try");
	data1.legendColor(Color.black);
/*
**      Attach data set to the Xaxis
*/
        xaxis = graph.createAxis(Axis.BOTTOM);
        xaxis.attachDataSet(data1);
        xaxis.setTitleText("Try");
        xaxis.setTitleFont(new Font("TimesRoman",Font.PLAIN,20));
        xaxis.setLabelFont(new Font("Helvetica",Font.PLAIN,15));
	xaxis.minor_tic_count=4;
        xaxis.minimum=0.0;
	xaxis.maximum=10.0;
/*
**      Attach data set to the Yaxis
*/
        yaxis = graph.createAxis(Axis.LEFT);
        yaxis.attachDataSet(data1);
        yaxis.setTitleText("Score");
        yaxis.setTitleFont(new Font("TimesRoman",Font.PLAIN,20));
        yaxis.setLabelFont(new Font("Helvetica",Font.PLAIN,15));
        yaxis.setTitleColor( new Color(0,0,0) );
	yaxis.minimum=0.0;
        yaxis.maximum=10.0;
      }



/*******************************************************
**  This method adds a data point to the dataset
*******************************************************/
     
      public void update(Observable obs, Object ar) {
	int i =0;
	int count=index.getValue();
	double data[] = new double[2];
	if(data1 == null) return;

//  If the number of data points exceeds the maximum, delete the first point
	if(count >= m) data1.delete(0,0);

//  Create array with new data point in it 
	data[1] = var1.getValue();
	data[0] = index.getValue();
//  Append the new data point
	try {
	  data1.append(data,1);
	}
	catch (Exception e) {
	  System.out.println("Error appending Data!");
	}

//  Make sure the xaxis minimum is at least 10
	if (data1.xmax < 10.0) xaxis.maximum=10;
//	yaxis.maximum=data1.ymax+5;
//           Repaint the graph with new point
	graph.repaint();
      }
    }











