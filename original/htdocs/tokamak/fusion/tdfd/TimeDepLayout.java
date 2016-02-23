/*
 * class TimeDepLayout
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
import java.util.*;
import java.beans.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;
import java.text.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;
import javax.swing.text.*;
import palette.*;
import swingman.*;
/**
 * This class handles the layout of all of the images, user interface
 * widgets, and text of the TimeDepFusionDemo applet frame.  It creates
 * the four graphs and has the code for changing the plasma image and
 * labels during animation.  It does this in part via the Observer interface,
 * watching the Time thread.
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:40:55 $.
 * @author Will Fisher
 * @see TimeDepFusionDemo
 * @see Time
 * @see ImageModifier
 * @see swingman.Graph
 * @see swingman.DoubleJSlider
 */

/* 
 * $Log: TimeDepLayout.java,v $
 * Revision 1.1  2004/10/07 20:40:55  dstotler
 * Added to repository.
 *
 */
public class TimeDepLayout extends JLayeredPane implements Observer {
	
	// TimeDepFusionDemo layout variables
	private Image[] images;
	private TimeDepFusionDemo controller;
	private JButton magnetButton, powerButton, densityButton, graphButton;
	private JLabel tempLabel, scoreLabel, magnetLabel, powerLabel;
	private JLabel densityLabel, plasmaImage, scoreTextLabel, tempTextLabel;
	private ImageModifier plasmaFilter;
	private Time timeThread;
	private MediaTracker media;
	private Image plasma;
	private IndexColorModel hot_metal;
	private DecimalFormat formatter, formatter2;
	private FilteredImageSource plasmaFilterSource;
	public JButton startButton, stepButton;
	public SliderFieldPanel magnetPanel, powerPanel, densityPanel;
	public Graph magnetGraph, powerGraph, densityGraph, outputGraph;
	public JFrame magnetWindow, powerWindow, densityWindow, graphWindow, tableWindow;
    /**
     * This constructor takes as arguments an array of the images to be 
     * displayed and references to the other principal objects,
     * the TimeDepFusionDemo applet and Time thread.
     * @param _images array of Images for the tokamak, plasma, and density
     * @param _controller the TimeDepFusionDemo applet
     * @param _time the Time thread
     */
	public TimeDepLayout(Image[] _images, TimeDepFusionDemo _controller, Time _time) {
		this.images = _images;
		this.controller = _controller;
		this.timeThread = _time;
		
		media = new MediaTracker(this);
		media.addImage(images[0], 0);
		media.addImage(images[1], 1);
		media.addImage(images[2], 2);
		media.addImage(images[3], 3);
		
		// Create the plasma image
		plasma = images[1];

		// Create the formatter
		formatter = new DecimalFormat("###.00");
		formatter2 = new DecimalFormat("###.0E0");
		
		/*
		 * Set array of transparency values to be used with in defining
		 * the IndexColorModel. The ICM will be fully transparent for the
		 * lowest index, ramping up to fully opaque with the square root 
		 * of the index.
		 */
		int num_colors = 150;
		byte alpha[] = new byte[256];
		int alf;
		for (int i=0; i < num_colors; i++) {
		  alf = (int) (255. * Math.pow((double) i/(num_colors-1),0.5));
		  alpha[i] = (byte) alf;
		}
      
		//now initialize imagemodifier with this ICM
		hot_metal = PaletteServer.getPalette("hotMetal", num_colors, alpha);
		plasmaFilter = new ImageModifier(hot_metal);
		plasmaFilterSource = new FilteredImageSource(plasma.getSource(), plasmaFilter);
		
		// Initialize the layout
		initLayout();
		
		// Add components as a "slave" to the time class
		timeThread.addObserver(magnetGraph);
		timeThread.addObserver(powerGraph);
		timeThread.addObserver(densityGraph);
		timeThread.addObserver(outputGraph);
		timeThread.addObserver(this);
	}
    /** 
     * A separate initialization method called by the constructor.
     */
	public void initLayout() {
	        // Reset the layout first
		setLayout(null);
		
		// Wait for these two images then go
		try {
			media.waitForAll(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JLabel tokamakImage = new JLabel(new ImageIcon(images[0]));
		plasmaImage = new JLabel(new ImageIcon(images[1]));
		plasmaImage.setDoubleBuffered(true);
		
		Font big = new Font("Dialog", Font.BOLD, 16);
		graphButton = new JButton("Show Graph");
		startButton = new JButton("START");
		stepButton = new JButton("STEP");
		JPanel startPanel = new JPanel(new BorderLayout());
		startButton.setFont(big);
		stepButton.setFont(big);
		graphButton.addActionListener(controller);
		startButton.addActionListener(controller);
		stepButton.addActionListener(controller);
		startPanel.add(startButton, BorderLayout.CENTER);
		startPanel.setBorder(new EtchedBorder());
		
		magnetButton = new JButton("Details");
		powerButton = new JButton("Details");
		densityButton = new JButton("Details");
		magnetButton.setActionCommand("Magnet");
		powerButton.setActionCommand("Power");
		densityButton.setActionCommand("Density");
		magnetButton.addActionListener(controller);
		powerButton.addActionListener(controller);
		densityButton.addActionListener(controller);
		
		// Make labels big to accomodate numbers
		Font medium = new Font("Dialog", Font.BOLD, 13);
		scoreTextLabel = new JLabel("Score:");
		scoreTextLabel.setFont(medium);
		tempTextLabel = new JLabel("Temp:");
		tempTextLabel.setFont(medium);
		scoreLabel = new JLabel("");
		tempLabel = new JLabel("");
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		JPanel textSubPanel = new JPanel();
		textSubPanel.setLayout(new BoxLayout(textSubPanel, BoxLayout.Y_AXIS));
		JPanel valueSubPanel = new JPanel();
		valueSubPanel.setLayout(new BoxLayout(valueSubPanel, BoxLayout.Y_AXIS));
		scoreTextLabel.setAlignmentX(1.0f);
		tempTextLabel.setAlignmentX(1.0f);
		textSubPanel.add(scoreTextLabel);
		textSubPanel.add(tempTextLabel);
		scoreLabel.setAlignmentX(0.0f);
		tempLabel.setAlignmentX(0.0f);
		valueSubPanel.add(scoreLabel);
		valueSubPanel.add(tempLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		labelPanel.add(textSubPanel);
		labelPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		labelPanel.add(valueSubPanel);
		labelPanel.add(Box.createHorizontalGlue());
		labelPanel.setBorder(new EtchedBorder());

		magnetPanel = new SliderFieldPanel("Field:", 0.0, 14.0, 2.0, magnetButton, null);
		powerPanel = new SliderFieldPanel("Power:", 0.0, 100.0, 10.0, powerButton, null);
		Image[] tImages = {images[2], images[3]};
		densityPanel = new SliderFieldPanel("Density:", 0.0, 6.0, 1.0, densityButton, tImages);
		
		// Set up the input/output windows
		double[] data = {0.0, 6.0588, 7.5/1.9445, 7.0, 7.5, 6.8, 17.5, 6.8, 20.0, 4.5};
		DataTable tempDT = new DataTable(data);
		tempDT.setScale(0.0, 0.0, 20.0, 7.0);
		Axis xaxis = new Axis("Time (seconds)", Axis.BOTTOM);
		Axis yaxis = new Axis("Magnetic Field (teslas)", Axis.LEFT);
		yaxis.setOrientation(Axis.VERTICAL);
		magnetGraph = new Graph(tempDT, true);
		magnetGraph.setAxis(yaxis);
		magnetGraph.setAxis(xaxis);
		magnetGraph.setTitle("Magnetic Field");
		magnetGraph.resetButton.addActionListener(magnetPanel);
		magnetWindow = new JFrame("Magnetic Field Details");
		magnetWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		magnetWindow.getContentPane().add(magnetGraph);
		magnetWindow.pack();
		magnetWindow.setLocation(650, 380);
		
		double[] data1 = {0.0, 0.0, 7.5, .5, 8.05, 40.0, 22.45, 40.0, 20.0, 39.0};
		DataTable tempDT1 = new DataTable(data1);
		tempDT1.setScale(0.0, 0.0, 20.0, 50.0);
		Axis xaxis1 = new Axis("Time (seconds)", Axis.BOTTOM);
		Axis yaxis1 = new Axis("Heating Power (megawatts)", Axis.LEFT);
		yaxis1.setOrientation(Axis.VERTICAL);
		powerGraph = new Graph(tempDT1, true);
		powerGraph.setAxis(yaxis1);
		powerGraph.setAxis(xaxis1);
		powerGraph.setTitle("Auxiliary Power");
		powerGraph.resetButton.addActionListener(powerPanel);
		powerWindow = new JFrame("Auxiliary Field Details");
		powerWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		powerWindow.getContentPane().add(powerGraph);
		powerWindow.pack();
		powerWindow.setLocation(650, 50);
		
		double[] data2 = {0.0, 0.0, 7.5, 1.5, 8.0, 1.5, 11.0, 2.9, 17.5, 2.9, 20.0, 1.1};
		DataTable tempDT2 = new DataTable(data2);
		tempDT2.setScale(0.0, 0.0, 20.0, 3.0);
		Axis xaxis2 = new Axis("Time (seconds)", Axis.BOTTOM);
		Axis yaxis2 = new Axis("Density (10^20/m^3)", Axis.LEFT);
		yaxis2.setOrientation(Axis.VERTICAL);
		densityGraph = new Graph(tempDT2, true);
		densityGraph.setAxis(yaxis2);
		densityGraph.setAxis(xaxis2);
		densityGraph.setTitle("Plasma Density");
		densityGraph.resetButton.addActionListener(densityPanel);
		densityWindow = new JFrame("Plasma Density Details");
		densityWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		densityWindow.getContentPane().add(densityGraph);
		densityWindow.pack();
		densityWindow.setLocation(25, 500);

		double[] data3 = {0.0, 0.0};
		DataTable tempDT3 = new DataTable(data3);
		tempDT3.setScale(0.0, 0.0, 20.0, 150.0);
		Axis xaxis3 = new Axis("Time", Axis.BOTTOM);
		Axis yaxis3 = new Axis("Score", Axis.LEFT);
		yaxis3.setOrientation(Axis.VERTICAL);
		outputGraph = new Graph(tempDT3, false);
		outputGraph.setAxis(yaxis3);
		outputGraph.setAxis(xaxis3);
		outputGraph.setTitle("Fusion Output Power");
		graphWindow = new JFrame("Output Graph");
		graphWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		graphWindow.getContentPane().add(outputGraph);
		graphWindow.pack();
		graphWindow.setLocation(460, 500);
		
		magnetLabel = new JLabel("<html><body>Magnetic Field<center>(teslas)</center></body></html>");
		magnetLabel.setFont(big);
		powerLabel = new JLabel("<html><body>Auxiliary Heating<center>(megawatts)</center></body></html>");
		powerLabel.setFont(big);
		densityLabel = new JLabel("<html><body>Plasma Density<center>(10<sup><font size='-1'>20</font></sup>/m<sup><font size='-1'>3</font></sup>)</center></body></html>");
		densityLabel.setFont(big);
		
		// Create some observer connections
		magnetPanel.addObserver(magnetGraph);
		powerPanel.addObserver(powerGraph);
		densityPanel.addObserver(densityGraph);
		
		// Add objects to their respective panes (layers)
		// NOTE: I have to set all the bounds and locations manually because there is no layout manager!
		add(tokamakImage, new Integer(5));
		tokamakImage.setBounds(0, 0, 600, 255);
		add(plasmaImage, new Integer(8));
		plasmaImage.setSize(plasmaImage.getPreferredSize());
		plasmaImage.setLocation(202, 118);

		add(graphButton, new Integer(15));
		graphButton.setSize(graphButton.getPreferredSize());
		graphButton.setLocation(495, 300);
		add(startPanel, new Integer(15));
		startPanel.setSize(graphButton.getPreferredSize());
		startPanel.setLocation(495, 330);
		/*add(stepButton, new Integer(15));
		stepButton.setSize(stepButton.getPreferredSize());
		stepButton.setLocation(487, 330);*/;
		
		add(powerPanel.panel, new Integer(15));
		powerPanel.panel.setSize(powerPanel.panel.getPreferredSize());		
		powerPanel.panel.setLocation(487, 128);
		add(densityPanel.panel, new Integer(15));
		densityPanel.panel.setSize(densityPanel.panel.getPreferredSize());	
		densityPanel.panel.setLocation(15, 300);
		add(magnetPanel.panel, new Integer(15));
		magnetPanel.panel.setSize(magnetPanel.panel.getPreferredSize());	
		magnetPanel.panel.setLocation(6, 96);
		
		add(magnetLabel, new Integer(20));
		magnetLabel.setSize(magnetLabel.getPreferredSize());
		magnetLabel.setLocation(12, 50);
		add(densityLabel, new Integer(20));
		densityLabel.setSize(densityLabel.getPreferredSize());
		densityLabel.setLocation(60, 250);
		add(powerLabel, new Integer(20));
		powerLabel.setSize(powerLabel.getPreferredSize());
		powerLabel.setLocation(487, 80);
		
		add(labelPanel, new Integer(15));
		labelPanel.setSize(160, 50);
		labelPanel.setLocation(265, 300);
	}
    /**
     * This is the one method associated with the Observer interface.
     * It watches the Time thread, updating the temperature and
     * score values as they evolve.
     */
	public void update (Observable obj, Object _args) {
	        final Object args = _args;
		if (args != null) {
			// Update the temperature and score labels
		        SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
				scoreLabel.setText(formatter.format(((Double[])args)[2].doubleValue()));
				tempLabel.setText(formatter2.format(((Double[])args)[3].doubleValue()) + "\u00B0C");
			    }
			});
			
			// Convert the double value into an integer corresponding to a color in the IndexColorModel
			// 150.0 is the largest score we're assuming
			double score = (((Double[])args)[2].doubleValue()) % 150.0;
			int numcol = hot_metal.getMapSize();
			double pvmax = 150.0;
			double pvmin = 0.0;
			double pvdbl = (score - pvmin) / (pvmax - pvmin);
			int pv = (int)((numcol - 1.0) * pvdbl);
			if (pv < 0) pv = 0;
			if (pv > numcol - 1) pv = numcol - 1;

			long start = System.currentTimeMillis();
			plasmaFilter.setHue(pv);
			plasma = createImage(plasmaFilterSource);
			MediaTracker mt = new MediaTracker(this);
			mt.addImage(plasma, 0);
			try {
				mt.waitForID(0);
			} catch (InterruptedException e) { }
			finally {
			        mt = null;
			}
			((ImageIcon)(plasmaImage.getIcon())).setImage(plasma);
			plasmaImage.repaint();
		}
	}
    /** 
     * This is a simple class having a JPanel that holds the slider and
     * associated objects.  It implements all of the user interface
     * capabilities associated with the slider.
     */	
	public class SliderFieldPanel extends Observable
	    implements ChangeListener, PropertyChangeListener, ActionListener {
		
		private DoubleJSlider slider;
		private JFormattedTextField field;
		private JLabel titleLabel;
		private Hashtable labels;
		public JPanel panel;
		private Double init;
		private final int PRECISION = 2;
	    /**
	     * Constructor for this class.
	     * @param title string title associated with the slider
	     * @param min minimum value to be used for the slider
	     * @param max maximum value to be used for the slider
	     */
		public SliderFieldPanel (String title, double min, double max, double spacing,
		    JButton detailsButton, Image[] sfpImages) {
			// Create the formatter
			NumberFormatter formatter = new NumberFormatter();
			formatter.setMinimum(new Double(min));
			formatter.setMaximum(new Double(max));
			
			// Create the objects
			panel = new JPanel(new BorderLayout());
			panel.setOpaque(true);
			titleLabel = new JLabel(title, JLabel.CENTER);
			field = new JFormattedTextField(formatter);
			slider = new DoubleJSlider("blah", min, max, true);
			
			// Create and set the dictionary (hashtable)
			labels = new Hashtable();
			labels.put(new Integer((int)(min*Math.pow(10, PRECISION))),
				   new JLabel(new Integer((int)min).toString()));
			labels.put(new Integer((int)(max*Math.pow(10, PRECISION))),
				   new JLabel(new Integer((int)max).toString()));
			slider.setLabelTable(labels);
			
			// Add the listeners
			field.addPropertyChangeListener(this);
			slider.addChangeListener(this);
			
			// Format these few objects
			slider.setDoubleMajorTickSpacing(max-min);
			slider.setDoubleMinorTickSpacing(spacing);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.setPreferredSize(new Dimension(120, (int)slider.getPreferredSize().getHeight()));
			// Set initial value to center of slider
			field.setValue(new Double((min+max)/2));
			field.setColumns(5);
			
			// Set the initial value to hold
			init = (Double)field.getValue();
			
			// When the user presses enter...
			field.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "checkInput");
			field.getActionMap().put("checkInput", new AbstractAction() {
				public void actionPerformed (ActionEvent e) {
					if (!field.isEditValid()) {
						Toolkit.getDefaultToolkit().beep();
						field.selectAll();
					} else try {
						field.commitEdit();
					} catch (java.text.ParseException e2) { }
				}
			});
			
			// Add the components to the panel
			JPanel topPanel = new JPanel();
			topPanel.add(titleLabel);
			topPanel.add(field);
			panel.setBorder(new EtchedBorder());
			panel.add(topPanel, BorderLayout.NORTH);
			panel.add(slider, BorderLayout.CENTER);
			panel.add(detailsButton, BorderLayout.SOUTH);
			if (sfpImages != null) {
				panel.add(new JLabel(new ImageIcon(sfpImages[0])), BorderLayout.WEST);
				panel.add(new JLabel(new ImageIcon(sfpImages[1])), BorderLayout.EAST);
			}
		}
		
		public void setEnabled (boolean enabled) {
		        // Enable or disable the children!
			field.setEnabled(enabled);
			slider.setEnabled(enabled);
		}
	    /** 
	     * Method associated with the PropertyChange interface.
	     */
		public void propertyChange (PropertyChangeEvent e) {
			if (e.getPropertyName().equals("value")) {
				Number value = (Number)e.getNewValue();
				if (value != null) {
					slider.setDoubleValue(value.doubleValue());
					// Notify observers
					setChanged();
					notifyObservers(new Double(value.doubleValue()));
				}
			}
		}
	    /**
	     * Method associated with the ChangeListener interface.
	     * It watches the slider and updates the displayed text
	     * as the slider moves.
	     */
		public void stateChanged (ChangeEvent e) {
			DoubleJSlider source = (DoubleJSlider)e.getSource();
			double value = (double)source.getDoubleValue();
			// Now update field to make slider
			field.setText(String.valueOf(value));
			// Notify observers
			setChanged();
			notifyObservers(new Double(source.getDoubleValue()));
		}
	    /**
	     * Method associated with the ActionListener interface.
	     * It resets the slider and text to the value provided.
	     */
		public void actionPerformed (ActionEvent e) {
			// Reset this slider
			field.setText(String.valueOf(init.doubleValue()));
			slider.setDoubleValue(init.doubleValue());
		}
	}
}
