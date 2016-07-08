/*
 * class SteadyStateFusionDemo
 * 
 *    Copyright (C) 1996-1999, Daren Stotler
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
package ssfd;

import java.lang.String;
import java.lang.System;
import java.lang.Math;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Choice;
import java.awt.Event;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.image.IndexColorModel;
import java.applet.Applet;
import dpscomponents.RelativeLayout;
import dpscomponents.panel.ImagePanel;
import dpscomponents.panel.MultiLabelPanel;
import observable.ObservableDbl;
import observable.ObservableInt;
import observable.ObservableLabel;
import observable.ObservableSlider;
import palette.PaletteServer;
import palette.PaletteScale;
import rootsolvers.Solvers;
import rootsolvers.RootSolverException;
import observable.ObsTextField;

/**
 * This is the Applet class providing the user interface for
 * the SteadyStateFusionReactor class. Further explanation is
 * provided in the accompanying HTML files and links therein.
 * 
 * There is also a <a href="http://w3.pppl.gov/~dstotler/SystemsCode/">
 * User's Manual</a> for the
 * full-featured FORTRAN code upon which this code is based.
 * The corresponding equation numbers are mentioned in the code below.
 *
 * @version $Revision: 1.2 $, $Date: 1999/11/19 21:39:15 $.
 * @author Daren Stotler
 * @see SteadyStateFusionReactor
 * @see FusionReactor
 * @see ExpTauE
 */

/*
 * $Log: SteadyStateFusionDemo.java,v $
 * Revision 1.2  1999/11/19 21:39:15  dstotler
 * Numerous changes to make layout more robust to differences in font
 * size. Principal approach is to choose the font size so that the
 * "Magnetic Field" fits into the space expected to be available for
 * it. Tweaked some layout parameters.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:50  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 5.5 08-23-99 Replace sliders with combined slider and text field.
 * 5.4 11-22-98 Replaced choice events with ItemListener and otherwise
 *              update to Java 1.1.
 * 5.3 01-22-98 Replace root-solvers with simpler interface approach.
 * 5.2 02-03-97 Begin cleaning up code and inserting javadoc comments.
 * 5.1 12-16-96 Use "score" instead of Q & T in celsius on output.
 *              Also, shrink size of filtered image.
 * 5.0 09-17-96 Incorporate changing-plasma output & layout; use
 *              Matt's graph & table options.
 * 4.2 07-16-96 Add temperature to printed output.
 * 4.0 06-18-96 Uses legitimate palette & PaletteScale classes.
 * 3.0 03-15-96 HACK in final-appearing output widgets.
 * 2.0 03-05-96 Do most of the layout and refine input gadgets.
 * 1.0 02-07-96 We begin giving it real version numbers.
 */

/*
 * ACKNOWLEDGEMENTS:
 *
 * <UL>
 *   <LI> <A HREF="http://www.cs.brown.edu/people/amd/">Adam Doppelt</A> 
 *       (original slider),
 *   <LI> <A HREF="http://amber.wpi.edu/~thethe">Benjamin "Quincy" Cabell V</A>,
 *        <A HREF="http://amber.wpi.edu/~thethe/Documents/Besiex/Java/index.html">Besiex Software</A> 
 *        (MightySlider, MightyButton),
 *   <LI> <A HREF="http://www-elec.enst.fr/~dufourd/jcd.html"> Jean-Claude 
 *        Dufourd</A> (RelativeLayout).
 *   <LI> Leigh Brookshaw for the 
 *        <A HREF="http://grendel.sci.usq.edu.au/graph/">graph classes</A> 
 *        (Copyright 1995, 1996).
 *   <LI> Matt Fritz (summer intern at PPPL) for the Data Table and Graph
 *        options.
 *   <LI> Dwight Bashore, Irv Zatz (PPPL engineers), and Caroline Davis
 *        (summer intern at PPPL) for the tokamak graphic.
 * </UL> 
 */
public class SteadyStateFusionDemo extends Applet implements ItemListener {

  /*
   * The energy confinement time expression is known in the literature
   * as "ITER 89-P".
   * @see http://w3.pppl.gov/~dstotler/SystemsCode/
   */
  private ExpTauE iter89P_;

  /*
   * The machine parameters for this reactor are largely those of the
   * proposed Burning Plasma Experiment (BPX).
   * @see http://w3.pppl.gov/~dstotler/SystemsCode/
   */
  private FusionReactor bpx_;
  private SteadyStateFusionReactor steadybpx_;

  /*
   * Observables are used for all of the user-interface variables.
   */
  private ObservableDbl oBField_, oDensity_, oPaux_, oTemperature_, oScore_;
  private ObservableInt oTrial_;
  private int trial_=0;

  /*
   * Data table object displaying user input and output for each try.
   */
  private DataFrame2 dataWindow_;

  /*
   * Graph object depicting history of "score" with each try.
   */
  private QGraph2 graphWindow_;

  /*
   * Labels for table and graph Choices.
   */
  private static final String HIDE_TABLE = "Hide Table";
  private static final String SHOW_TABLE = "Show Table";
  private static final String HIDE_GRAPH = "Hide Graph";
  private static final String SHOW_GRAPH = "Show Graph";

  /*
   * Size of the applet passed on to other methods.
   */
  private Dimension appSize_;

  public void init() {
   
    iter89P_ = new ExpTauE(0.0381, 0.5, 0.5, 0.85, 
                                0.1, 0.2, 0.3, 1.2, -0.5);
    /*
     * For simplicity, the size of the reactor and its magnetic safety
     * factor q, are fixed to those of BPX.
     */
    double abpx = 0.795;
    double rbpx = 2.59;
    double qbpx = 2.3;

    /*
     * Allocate and initialize Observables.
     * related variables.
     */
    oBField_ = new ObservableDbl(7.);
    oDensity_ = new ObservableDbl(3.);
    oPaux_ = new ObservableDbl(40.);
    oTemperature_ = new ObservableDbl(0.);
    oScore_ = new ObservableDbl(0.);
    oTrial_=new ObservableInt(0);

    /*
     * Labels used for the DataFrame.
     */
    String[] labels={"Try", "Field", "Power", "Density", "Temp.", "Score"};
    
    /*
     * Attempt to initialize FusionReactor bpx_.
     */
    try {
      bpx_ = new FusionReactor(abpx, rbpx, qbpx, oBField_, oDensity_, 
			      oPaux_, oTemperature_, oScore_, iter89P_); 
    } catch (IncompleteReactorExcpn e) {
      System.out.println("SSFD.init caught exception: " + e.getMessage());
      return;
    }
    steadybpx_ = new SteadyStateFusionReactor(bpx_, oTemperature_);

    /*
     * Define some principal interface components.
     */
    dataWindow_=new DataFrame2(oTrial_, oBField_, oPaux_, oDensity_, 
			       oTemperature_,
			      oScore_, labels);
    dataWindow_.setSize(340,800);
    graphWindow_=new QGraph2(this, oTrial_, oScore_);
    graphWindow_.setSize(550, 300);

    Font biglabelfont = new Font("TimesRoman", Font.BOLD, 18);
    FontMetrics bigfm = getFontMetrics(biglabelfont);

    /*
     * Check the size of this font. The labels around the tokamak
     * pic have only a predetermined amount of space. Use this 
     * string as a guide. The "120" comes from looking at the background gif.
     */
    int bstringlen = bigfm.stringWidth("Magnetic Field");
    int smallfontsize = 14;
    if (bstringlen > 120) {
      int fontsize = (int) (18. * (120./ (double) bstringlen));
      biglabelfont = new Font("TimesRoman", Font.BOLD, fontsize);
      bigfm = getFontMetrics(biglabelfont);
      smallfontsize = (int) (14. * (120. / (double) bstringlen));
    }
    Font smalllabelfont = new Font("TimesRoman", Font.BOLD, smallfontsize);
    setFont(smalllabelfont);

    SSFDSlider density_slider = new SSFDSlider(oDensity_, 80, 0, 6, 7, 
				      new Color(0,152,114),smalllabelfont);
    density_slider.setFont(smalllabelfont);
    String[] dlines = { "Plasma Density", "(10^20 / m^3)" };
    MultiLabelPanel density_title = new MultiLabelPanel(dlines, 2, 
							biglabelfont);

    SSFDSlider paux_slider = new SSFDSlider(oPaux_, 80, 0, 100, 8, 
				      new Color(198,206,239),smalllabelfont);
    String[] lines = { "Auxiliary","Heating Power","(megawatts)" };
    MultiLabelPanel paux_title = 
      new MultiLabelPanel(lines, 3, biglabelfont);
    
    SSFDSlider bfield_slider = new SSFDSlider(oBField_, 80, 0, 14, 8, 
				        new Color(90,189,99),smalllabelfont);

    density_slider.setSize(80,40);
    paux_slider.setSize(80,40);
    bfield_slider.setSize(80,40);


    String[] blines = { "Magnetic Field","(tesla)" };
    MultiLabelPanel bfield_title = new MultiLabelPanel(blines, 2, 
						       biglabelfont);

    SSFDButton start_button = new SSFDButton(this, "ssfd/images/b4d", "START", 
				   SSFDButton.MOMENTARY,biglabelfont);

    /*
     * Set array of transparency values to be used with in defining
     * the IndexColorModel. The ICM will be fully transparent for the
     * lowest index, ramping up to fully opaque with the square root 
     * of the index.
     */
    int num_colors = 40;
    byte alpha[] = new byte[256];
    int alf;
    for (int i=0; i < num_colors; i++) {
      alf = (int) (255. * Math.pow((double) i/num_colors,0.5));
      alpha[i] = (byte) alf;
    }
      
    IndexColorModel hot_metal = PaletteServer.getPalette("hotMetal", 
							 num_colors, alpha);

    /*
     * Use the hotMetal ICM and the score labels to generate the
     * "scale" graphic which presents the user with the relationship
     * between the score and the color we'll make the plasma (via the
     * hotMetal ICM). Maximum and minimum values for the score have to
     * be defined for this correspondence to be realized.
     */
    String[] score_labels = { "Score", "0", "50", "100" };
    oScore_.setMinimum(0.);
    oScore_.setMaximum(100.);
    PaletteScale scale = new PaletteScale(hot_metal, 125, 25, score_labels,
					  smalllabelfont);
    
    /*
     * The tokamak and plasma graphics are shipped off to OverlayPanel,
     * where along with the score-observable and the hotMetal ICM,
     * they are used to generate the tokamak with "changing color 
     * plasma" which is the central object in the interface.
     */
    OverlayPanel picture_stuff = new OverlayPanel(this, "ssfd/images/tokamak3.gif",
						 "ssfd/images/plasma5.gif", 
						 188, 127, oScore_, hot_metal);

    /* 
     * Labels used to print the score and temperature to the screen.
     */
    ObservableLabel score_value = new ObservableLabel(oScore_, "Score: ", 
						      Label.CENTER);
    score_value.setFont(biglabelfont);
    ObservableLabel t_value = new ObservableLabel(
	oTemperature_,"Temperature: ",Label.CENTER);
    t_value.setFont(biglabelfont);
    Label t_units = new Label("Million Celsius", Label.CENTER);
    t_units.setFont(biglabelfont);
  
    /*
     * RelativeLayout wants to know the applet's size at instantiation;
     * take a stab at it here.
     */
    int app_width = picture_stuff.getPreferredSize().width 
      + getInsets().left + getInsets().right;
    int app_height = picture_stuff.getPreferredSize().height 
	  + getInsets().top + score_value.getPreferredSize().height
          + t_value.getPreferredSize().height + t_units.getPreferredSize().height
	  + getInsets().bottom;
    appSize_ = new Dimension(app_width, app_height);

    /*
     * rl is the layout for the overall applet.
     */
    RelativeLayout applet_layout = new RelativeLayout(new Dimension(
	app_width,app_height));
    setLayout(applet_layout);
    setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);

    /*
     * Use a separate layout manager for arranging the sliders on the
     * tokamak graphic:
     *  - Want to overlay two of the sliders on graphical elements in the 
     *    main tokamak gif.
     *  - Accomplish that by arranging for those graphical elements
     *    (representing a neutral beam box on the left and a magnetic
     *     field power supply on the right) to be vertically centered in
     *     the gif image, with one at the left edge, the other on the
     *      right.
     *  - RelativeLayout's insideLeft and insideRight constraints then 
     *    place these sliders precisely at these locations with small
     *    horizontal shifts chosen by observation.
     *  - The slider labels are added relative to the sliders.
     */
    RelativeLayout pic_stuff_layout
      =new RelativeLayout(picture_stuff.getPreferredSize());
    picture_stuff.setLayout(pic_stuff_layout);


    picture_stuff.add( "1", paux_slider);
    pic_stuff_layout.setConstraint(paux_slider, null, 
				   RelativeLayout.insideLeft,26, 10);
    picture_stuff.add( "1", paux_title);
    pic_stuff_layout.setConstraint(paux_title, paux_slider, 
				   RelativeLayout.above, 0,30);

    picture_stuff.add( "1", bfield_slider);
    pic_stuff_layout.setConstraint(bfield_slider, null, 
				   RelativeLayout.insideRight,25,0);
    picture_stuff.add( "1", bfield_title);
    pic_stuff_layout.setConstraint(bfield_title,bfield_slider,
				   RelativeLayout.above,0,45);

    /*
     * Add the tokamak graphic to the main layout:
     */
    add( "1", picture_stuff);
    applet_layout.setConstraint(picture_stuff, null, 
				RelativeLayout.insideTop, 0, 0);

    /* 
     * Now define a layout for the density slider, its labels, and
     * the little "balls in a box" images which will be placed
     * on either side of the slider.
     */
    ImagePanel low_density = new ImagePanel(this, "ssfd/images/loden1.gif");
    ImagePanel high_density = new ImagePanel(this, "ssfd/images/hiden1.gif");
    Panel density_panel = new Panel();
    int d_p_width = 2*(low_density.getPreferredSize().width + 5) 
      + density_slider.getPreferredSize().width;
    int d_p_height = density_slider.getPreferredSize().height 
      + 2*(bigfm.getHeight()+5)+20;
    RelativeLayout density_panel_layout 
      = new RelativeLayout(new Dimension(d_p_width, d_p_height));
    density_panel.setLayout(density_panel_layout);
    density_panel.add( "1", low_density);
    density_panel_layout.setConstraint(low_density, null, 
				       RelativeLayout.insideUpperLeft,0,10);
    density_panel.add( "1", density_slider);
    density_panel_layout.setConstraint(density_slider, low_density, 
				       RelativeLayout.right,5,0);
    density_panel.add( "1", high_density);
    density_panel_layout.setConstraint(high_density, density_slider, 
				       RelativeLayout.right,5,0);
    density_panel.add( "1", density_title);
    density_panel_layout.setConstraint(density_title, density_slider, 
				       RelativeLayout.under,2,0);

    /*
     * Add the density slider panel, along with the START button, and 
     * the hotMetal ICM scale, to the tokamak graphic.
     */
    picture_stuff.add("1", density_panel);
    pic_stuff_layout.setConstraint(density_panel, null, 
			     RelativeLayout.insideLowerLeft, 0, 0);
    picture_stuff.add( "1", start_button);
    pic_stuff_layout.setConstraint(start_button,null,
				   RelativeLayout.insideLowerRight,0,-5);
    picture_stuff.add( "1", scale);
    pic_stuff_layout.setConstraint(scale, null, 
				   RelativeLayout.insideBottom, 0, 0);

    /*
     *  Yet another panel to hold the Table and Graph choice boxes, as
     *  well as the score and temperature labels.
     */
    Panel bottom_labels = new Panel();
    Choice d=new Choice();
    d.add(HIDE_TABLE);
    d.add(SHOW_TABLE);
    d.addItemListener(this);
    d.setFont(biglabelfont);
    Choice g=new Choice();
    g.add(HIDE_GRAPH);
    g.add(SHOW_GRAPH);
    g.addItemListener(this);
    g.setFont(biglabelfont);

    bottom_labels.setLayout(new GridLayout(3,2));
    bottom_labels.add(score_value);
    bottom_labels.add(d);
    bottom_labels.add(t_value);
    bottom_labels.add(g);
    bottom_labels.add(t_units);

    /*
     * Add this panel to the applet layout, below the tokamak graphic.
     */
    add("1", bottom_labels);
    applet_layout.setConstraint(bottom_labels, picture_stuff, 
				RelativeLayout.under, 0,15);

    /*
     * Explicitly solve the system with the initial slider values.
     */
    solveReactor();
  }

  /**
   * Implement the show / hide behavior associated with the table and
   * graph choices.
   * @param evt Tells which ItemEvent
   */
  public void itemStateChanged(ItemEvent evt) {

    String label=(String)evt.getItem();
    if (label.equals(SHOW_GRAPH)) {
      if (!graphWindow_.isShowing())
	graphWindow_.setVisible(true);
    }
    else if (label.equals(HIDE_GRAPH)) {
      if (graphWindow_.isShowing())
	graphWindow_.setVisible(false);
    }
    else if (label.equals(SHOW_TABLE)) {
      if(!dataWindow_.isShowing())
	dataWindow_.setVisible(true);
    }
    else if (label.equals(HIDE_TABLE)) {
      if (dataWindow_.isShowing())
	dataWindow_.setVisible(false);
    }
  }

  /**
   * Returns estimated size of the applet, as computed above.
   * @see #init
   */
  public Dimension getPreferredSize() {
    return (appSize_);
  }
  /**
   * Returns a hardwired, reasonable set of insets all around.
   */  
  public Insets getInsets() {
    return new Insets(10,10,10,10);
  }

  /**
   * Increments the trial number and invokes the method used to solve
   * the equations describing the reactor. It then computes the score. 
   * If the data or graph windows are showing, they are brought to the
   * front.
   */
  public void solveReactor() {
    trial_++;
   
    try {
      double root=Solvers.bisection(steadybpx_,10.,500.);  // Bracket temperature root - in 1.e6 Kelvin!
    } catch (RootSolverException f) {
      System.out.println("SSFD.solveReactor not solved: " + f.getMessage());
      return;
    }
   
    try {
      bpx_.calcOScore();
    } catch (IncompleteReactorExcpn e) {
      System.out.println("SSFD.solveReactor reactor incomplete: " 
			 + e.getMessage());
      return; 
    }

    /*
     * Changing oTrial_ here will call the update method in QGraph2 
     * and DataFrame2.
     */
    oTrial_.setValue(trial_);
    if (graphWindow_.isShowing()) {
      graphWindow_.toFront();
    }
    if (dataWindow_.isShowing()) {
      dataWindow_.toFront();
    }
    dataWindow_.repaint();
  }
}









				

  
