/*
 * class TimeDepFusionDemo
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
package tdfd;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import swingman.*;
import ssfd.*;
import rootsolvers.*;

/**
 * This is the JApplet (and application) class controlling the
 * time dependent version of the Virtual Tokamak.  The main interface
 * action handled here is that of the "Start" button.  It also takes
 * handles the buttons that show the time dependent graphs.
 *
 * Further explanation is provided in the accompanying HTML files 
 * and the links therein.
 *
 * @version $Revision: 1.2 $, $Date: 2004/11/30 22:14:30 $.
 * @author Will Fisher
 * @see TimeDepLayout
 * @see TimeDepFusionSolver
 * @see Time
 */

/*
 * $Log: TimeDepFusionDemo.java,v $
 * Revision 1.2  2004/11/30 22:14:30  dstotler
 * Use new interface to TimeDepFusionSolver with initial helium atom number, mode.
 *
 * Revision 1.1  2004/10/07 20:38:51  dstotler
 * Added to repository.
 *
 */
public class TimeDepFusionDemo extends JApplet implements ActionListener, Observer {
	
	// Define applet "global" variables then private variables
	public TimeDepLayout layout;
	private Image[] images;
	private Time time;
	private Thread timeThread;
	private TimeDepFusionSolver fusion;
	
    /** 
     * Initializing method for the application.
     */
	public static void main(String[] args) {
		// Detch images and give them to the tokamak layout class
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image tokamak = toolkit.getImage("tdfd/images/tokamak.gif");
		Image plasma = toolkit.getImage("tdfd/images/plasma.gif");
		Image hiden = toolkit.getImage("ssfd/images/loden1.gif");
		Image loden = toolkit.getImage("ssfd/images/hiden1.gif");
		Image[] tImages = {tokamak, plasma, hiden, loden};
		
		TimeDepFusionDemo tokamakInstance = new TimeDepFusionDemo();
		tokamakInstance.init(tImages);
		
		JFrame main = new JFrame("Potato");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.getContentPane().add(tokamakInstance.layout);
		main.setSize(630, 450);
		main.setVisible(true);
	}
    /**
     * An intermediate initialization method called by the main method.
     * @param tImages Array of images for the tokamak, etc.
     *
     */
	public void init(Image[] tImages) {
		images = tImages;
		init();
	}
    /**
     * Initializing method for the JApplet.
     */
	public void init() {
		if (images == null) {
			Image tokamak = getImage(getURL("tdfd/images/tokamak.gif"));
			Image plasma = getImage(getURL("tdfd/images/plasma.gif"));
			Image hiden = getImage(getURL("ssfd/images/loden1.gif"));
			Image loden = getImage(getURL("ssfd/images/hiden1.gif"));
			Image[] tImages = {tokamak, plasma, hiden, loden};
			images = tImages;
		}
		// Create the controller layer object
		time = new Time();
		time.addObserver(this);
		timeThread = new Thread(time);
		/*
		 * Create the fusion solver object.
		 * The inputs are currently the same as in the corresponding
		 * TestApplication.
		 */
		int nsteps = 93;
		double tinit = 1.5;
		double tfinal = 20.0;
		double tempinit = 1.0;
		/* 
		 * Ash accumulation tests:
		 */
		double nheinit = 2.e20;
		fusion = new TimeDepFusionSolver(TimeDepFusionSolver.ADV_ASH_ACCUMULATION, nsteps, tinit, tfinal, tempinit, nheinit);
		
		// Create a tokamak layout object
		layout = new TimeDepLayout(images, this, time);
		setSize(630, 450);
		this.getContentPane().add(layout);
	}
	
	public URL getURL(String filename) {
		URL codeBase = getCodeBase();
		URL url = null;
		
		try {
			url = new URL(codeBase, filename);
		} catch (java.net.MalformedURLException e) {
			return null;
		}
		return url;
	}
	
	public void startReaction() {
		// Lock the buttons + graphs first
		layout.magnetPanel.setEnabled(false);
		layout.densityPanel.setEnabled(false);
		layout.powerPanel.setEnabled(false);
		layout.magnetWindow.getContentPane().getComponent(0).setEnabled(false);
		layout.densityWindow.getContentPane().getComponent(0).setEnabled(false);
		layout.powerWindow.getContentPane().getComponent(0).setEnabled(false);
		((Graph)layout.graphWindow.getContentPane().getComponent(0)).graph.lock_ = true;
		layout.startButton.setEnabled(false);
		
		// Reset the graph
		((Graph)(layout.graphWindow.getContentPane().getComponent(0))).getDataTable().reset();

		// Solve the data!
		DataTable magnetData = ((Graph)(layout.magnetWindow.getContentPane().getComponent(0))).getDataTable();
		DataTable densityData = ((Graph)(layout.densityWindow.getContentPane().getComponent(0))).getDataTable();
		DataTable powerData = ((Graph)(layout.powerWindow.getContentPane().getComponent(0))).getDataTable();
		fusion.setInputs(magnetData, densityData, powerData);
		try {
		    NumRecRungeKutta.driver(fusion);
		} catch (RootSolverException e) { }
		
		// Get the results and hand them off to the timeThread
		while (!fusion.is_solved()) {
		    // Loop until solved
		}
		time.setSolution(fusion.get_results());
		
		// Start the thread, first run garbage collection
		System.gc();
		timeThread.run();
	}
    /**
     * This overrides JApplet's stop method.
     */
	public void stop() {
		// Stop the applet by indirectly requestion garbage colleciton
		System.out.println("Stopping applet...");
		time = null;  layout = null;  images = null;
	}
    /**
     * This is the one method associated with the ActionListener
     * interface; it is invoked when an action occurs.  In particular,
     * this one looks for all of the available button presses on the
     * applet frame (start, show graph, etc.).
     * @param e reference to the ActionEvent
     */
	public void actionPerformed (ActionEvent e) {
		// Do a big if-else statement to see which button was pressed
		if (e.getActionCommand().equals("Magnet")) {
			layout.magnetWindow.setVisible(true);
		}
		else if (e.getActionCommand().equals("Density")) {
			layout.densityWindow.setVisible(true);
		}
		else if (e.getActionCommand().equals("Power")) {
			layout.powerWindow.setVisible(true);
		}
		else if (e.getActionCommand().equals("START")) {
			// Start the tokamak solver!
			startReaction();
		}
		else if (e.getActionCommand().equals("Show Graph")) {
			layout.graphWindow.setVisible(true);
		}
		else if (e.getActionCommand().equals("Show Table")) {
			layout.tableWindow.setVisible(true);
		}
		else if (e.getActionCommand().equals("STEP")) {
			System.out.println("pressed");
		}
	}
    /**
     * This is the one method associated with the Observer interface. 
     * Presently, this is only watching the Time thread.  When it's
     * done, this method is called and all of the user interface
     * widgets are re-enabled.
     */
	public void update (Observable obj, Object args) {
		if (args == null) {
			// Reset the widgets to enabled
			layout.magnetPanel.setEnabled(true);
			layout.densityPanel.setEnabled(true);
			layout.powerPanel.setEnabled(true);
			layout.magnetWindow.getContentPane().getComponent(0).setEnabled(true);
			layout.densityWindow.getContentPane().getComponent(0).setEnabled(true);
			layout.powerWindow.getContentPane().getComponent(0).setEnabled(true);
			layout.startButton.setEnabled(true);
			layout.stepButton.setEnabled(true);
			((Graph)layout.graphWindow.getContentPane().getComponent(0)).graph.lock_ = true;
			
			// Run system garbage collection
			System.gc();
		}
	}
}
