/*
 * class waveformtest
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
package TestApplications;

import java.lang.String;
import java.net.URL;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.io.File;
import graph.WFDataSet;
import graph.waveform;
import graph.Markers;
import graph.Axis;
/**
 * This application tests the graph.waveform class, designed to permit
 * graphical editing of a 1-D function.  Note that it reads the
 * file marker.txt.
 * 
 * See the code and README in the 
 * TestApplications directory for more information.
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 19:54:28 $.
 * @author Daren Stotler
 * @see graph.waveform
 * @see graph.WFDataSet
 */

/*
 * $Log: waveformtest.java,v $
 * Revision 1.1  2004/10/07 19:54:28  dstotler
 * Added to repository.
 *
 *
 */
    public class waveformtest {

	public static void main(String args[]) {
	Frame f = new Frame("Waveform Test");

	double[] testdata = {1.,1.,2.,3.,3.,5.,4.,7.,5.,11.};
	int np = testdata.length / 2;
	WFDataSet data1 = null;
	try {
	    data1 = new WFDataSet(testdata,np);
	}
	catch (Exception e) {
	    System.out.println("Failed to load WFDataSet ");
	    e.printStackTrace();
	}
	waveform testform = null;
	try {
	    testform = new waveform(data1);
	}
	catch (Exception e) {
	    System.out.println("Failed to initialize waveform");
	    e.printStackTrace();
	}
	Markers our_markers=new Markers();
	URL markers_url = null;
	File mfile = null;
	try {
	    mfile = new File("TestApplications/marker.txt");
	} catch (Exception e) {
	    System.out.println("Null pathname for marker file");
	    e.printStackTrace();
	}
	try {
	    markers_url = mfile.toURL();
	} catch (Exception e) {
	    System.out.println("Malformed URL for marker file");
	    e.printStackTrace();
	}
	    
	try {
	    our_markers = new Markers(markers_url);
	} 
	catch(Exception e) {
	    System.out.println("Failed to create marker file.");
	}
	if (our_markers != null) {
	    testform.setMarkers(our_markers);
	    data1.marker=1;
	    data1.markerscale=2;
	    data1.markercolor=Color.magenta;
	}
	Axis xaxis = testform.createXAxis();
	xaxis.attachDataSet(data1);
	xaxis.setTitleText("Time (seconds)");
	xaxis.setTitleColor(Color.blue);
	xaxis.setTitleFont(new Font("Helvetica",Font.PLAIN,20));
	xaxis.setLabelFont(new Font("Helvetica",Font.PLAIN,20));

	Axis yaxis = testform.createYAxis();
	yaxis.attachDataSet(data1);
	yaxis.setTitleText("Density (particles per cubic meter)");
	yaxis.setTitleColor(Color.blue);
	yaxis.setTitleFont(new Font("Helvetica",Font.PLAIN,20));
	yaxis.setLabelFont(new Font("Helvetica",Font.PLAIN,20));

	f.setLayout( new BorderLayout() );
	f.add("Center",testform);
	f.setSize(600, 730);
	f.setVisible(true);
    }
}

