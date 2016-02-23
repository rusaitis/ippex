/*
 * class Graph
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
package swingman;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;

/**
 * A JPanel class that incorporates all of the graph related elements,
 * including 2 axes, 1 graph, 1 title, and some spacer elements.
 * This is part of the  <code>swingman</code> package, a swing-based 
 * replacement of Brookshaw's <code>graph</code> package used previously.
 * This may or may not posses the interactive "waveform" capabilities.
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:32:51 $.
 * @author Will Fisher
 * @see DataTable
 * @see GraphPanel
 * @see Waveform
 */

/*
 * $Log: Graph.java,v $
 * Revision 1.1  2004/10/07 20:32:51  dstotler
 * Added to repository.
 *
 *
 */

public class Graph extends JPanel implements Observer {

	private DataTable data;
	private DataTable init;
	private JLabel titleLabel;
	private String title = "Graph";
	private ArrayList axes = new ArrayList(4);
	public GraphPanel graph;
	private JPanel spacerPanel, buttonPanel;
	public JButton resetButton;
    /**
     * Initializes a graph based on the specified DataTable.
     * If the second argument is true, the graph will be interactive,
     * e.g., the user can add, subtract points, etc.
     * @param _data the DataTable object to be graphed
     * @param isWaveform whether or not graph is interactive
     */
	public Graph (DataTable _data, boolean isWaveform) {
		// Set data item
		data = _data;
		init = _data;
		data.addObserver(this);
		if (!isWaveform)
			graph = new GraphPanel(data, axes);
		else
			graph = new Waveform(data, axes);
		graph.setDoubleBuffered(true);
		spacerPanel = new JPanel();
		buttonPanel = new JPanel();
		resetButton = new JButton("Reset");
		resetButton.addActionListener(graph);
		buttonPanel.add(resetButton, BorderLayout.CENTER);
		
		titleLabel = new JLabel("", JLabel.CENTER);
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		// Initialize layout!
		initLayout();	
		resetButton.setMargin(new Insets(2, 2, 2, 2));
	}
    /**
     * Resets the layout of the graph.
     */
	public void initLayout() {
		// Reset the layout first
	        removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// Set defaults
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		
		// Set the margin
		int m = 5;
		EmptyBorder margins = new EmptyBorder(m, m, m, m);
		setBorder(margins);
		
		// Add the title
		gbc.weighty = 0.0;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(titleLabel, gbc);
		
		// Add center graph space
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridwidth = 1;
		this.add(graph, gbc);
		
		// Add spacer components
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(spacerPanel, gbc);
		gbc.gridx = 2;
		this.add(spacerPanel, gbc);
		gbc.gridy = 2;
		this.add(spacerPanel, gbc);
		gbc.gridx = 0;
		this.add(buttonPanel, gbc);

		// Add the axis parts
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		try {
			this.add(((Axis)axes.get(Axis.LEFT)).getAxisPanel(), gbc);
		} catch (IndexOutOfBoundsException e) {
			this.add(spacerPanel, gbc);
		}
		gbc.gridx = 2;
		try {
			this.add(((Axis)axes.get(Axis.RIGHT)).getAxisPanel(), gbc);
		} catch (IndexOutOfBoundsException e) {
			this.add(spacerPanel, gbc);
		}
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.gridx = 1;
		gbc.gridy = 2;
		try {
			this.add(((Axis)axes.get(Axis.BOTTOM)).getAxisPanel(), gbc);
		} catch (IndexOutOfBoundsException e) {
			this.add(spacerPanel, gbc);
		}
		gbc.gridy = 0;
		try {
			this.add(((Axis)axes.get(Axis.TOP)).getAxisPanel(), gbc);
		} catch (IndexOutOfBoundsException e) {
			this.add(spacerPanel, gbc);
		}		
	}
    /**
     * Sets or changes the title of the graph.
     * @param _title the desired title
     */
	public void setTitle (String _title) {
		// Re-write the label
		titleLabel.setText(_title);
		title = _title;
		initLayout();
	}
    /** 
     * Sets or changes the DataTable associated with the graph.
     * @param _data the DataTable to be graphed
     */
	public void setDataTable (DataTable _data) {
		data = _data;
		graph.setDataTable(data);
	}
    /**
     * Returns the DataTable currently associated with the graph.
     * @return the current DataTable
     */
	public DataTable getDataTable() {
		return data;
	}
    /**
     * Resets the DataTable to the one used in the instantiation
     * of the object.
     */	
	public void resetDataTable() {
		data = init;
	}
    /**
     * Adds the specified axis to the graph.  The position
     * of the axis is gotten from it.
     * @param axis the axis to be added
     */
	public void setAxis (Axis axis) {
		axes.add(axis.getPosition(), axis);
		// Refresh layout
		initLayout();
	}
    /**
     * Enables or disables the use of grid lines on the graph.
     * @param gl set to true to get grid lines
     */
	public void setGridLines (boolean gl) {
		graph.setGridLines(gl);
	}
    /**
     * Method associated with the Observer interface.  Tasks performed
     * include: repainting, rescale data and graph for a new slider value,
     * and repaint as requested by the Time object.
     */
	public void update (Observable obj, Object args) {
		// See what the source is...
		if (obj.getClass().toString().equals("class swingman.DataTable")) {
			// Getting update from data, repaint
			graph.repaint();
		}
		else if (obj.getClass().toString().equals("class tdfd.TimeDepLayout$SliderFieldPanel")) {
			// Getting update from slider, redo numbers
			double value = ((Double)args).doubleValue();
			data.multiplyData(value/(data.calcScale()[1]+data.calcScale()[3]));
			data.setScale(0.,0., 20.0, value);
			// Now update all the data
			repaint();
		}
		else if (obj.getClass().toString().equals("class tdfd.Time")) {
		    if (args != null) {
			// Getting update from time counter, repaint scanning bar
			double time = ((Double[])args)[1].doubleValue()/1000.0;
			graph.barPanel.setBarPosition(time);
			// Now add a new data point for the output graph
			if (graph.type == graph.GRAPH)
			    data.add(time, ((Double[])args)[2].doubleValue());
		    } else {
			// Removing the bar from the graphs
			graph.barPanel.setBarPosition(-1.0);
		    }
		}
	}
    /**
     * Lock or unlock the DataTable associated with the graph.
     * As suggested by the name, calling this with an argument
     * of "true" unlocks the DataTable.
     * @param enabled set "true" to unlock, "false" to lock.
     */
	public void setEnabled (boolean enabled) {
		data.setLock(!enabled);
	}
}
