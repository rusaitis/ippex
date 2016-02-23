/*
 * class TimeDepFusionSolver
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

import ssfd.*;
import swingman.*;
import rootsolvers.*;
import observable.*;
/**
 * This is an implementation of the ODESolved interface for
 * the 0-D fusion power balance equations embodied in the FusionReactor
 * class. The get_results method will compute some additional
 * output data that can be accessed externally via the get_results
 * method.
 *
 * Interested users can find much more detail in the 
 * <a href="http://w3.pppl.gov/~dstotler/SystemsCode/">User's Manual</a> 
 * for the full-featured FORTRAN code upon which this calculation is based.
 *
 * @version $Revision: 1.2 $, $Date: 2004/11/30 22:15:06 $.
 * @author Daren Stotler, Will Fisher
 * @see rootsolvers.ODESolved
 * @see rootsolvers.NumRecRungeKutta
 * @see ssfd.FusionReactor
 * @see ssfd.ExpTauE
 * @see observable.ObservableDbl
 * @see swingman.DataTable
 * @see TestApplications.TDFTest
 */

/*
 * $Log: TimeDepFusionSolver.java,v $
 * Revision 1.2  2004/11/30 22:15:06  dstotler
 * Add new equations for helium ash, total fusion energy, total heat.  Add mode parameter for controlling options.
 *
 * Revision 1.1  2004/10/07 20:39:51  dstotler
 * Added to repository.
 *
 */
public class TimeDepFusionSolver implements ODESolved {

    /*
     * Machine operating modes, introducing different bits of physics.
     * The intention is for them to correspond to varying levels of 
     * difficulty and to correspond to particular learning objectives.
     */
    /**
     * Quasi-steady mode intended to make clear the link to the 
     * steady-state version.
     */
    public static final int STEADY = 0;
    /**
     * Basic time-dependent mode with variable waveforms; only the
     * plasma energy equation is integrated.
     */
    public static final int BASELINE = 1;
    /**
     * Add in equation for helium ash accumulation.
     */
    public static final int ADV_ASH_ACCUMULATION = 2;
    /**
     * Limit total heat output, but without ash accumulation.
     */
    public static final int ADV_HEAT_LIMITED = 3;
    /**
     * Limit total heat output, together with ash accumulation.
     */
    public static final int ADV_HEAT_AND_ASH = 4;
    /*
     * The machine parameters for this reactor are largely those of the
     * proposed Burning Plasma Experiment (BPX), with the energy confinement
     * time given by the ITER-89P expression.
     * @see http://w3.pppl.gov/~dstotler/SystemsCode/
     */
    private FusionReactor bpx_;
    private ExpTauE iter89P_;
    private DataTable resultScore_;
    private DataTable resultT_;
    private DataTable resultW_;
    private DataTable resultNHe_;
    private DataTable resultPalpha_;
    private DataTable resultWheat_;
    /*
     * Observables are used for all of the user-interface variables.
     */
    private ObservableDbl oBField_, oDensity_, oPaux_, oTemperature_, 
	oScore_, oNHe_;
    /*
     * The time-dependent inputs are represented here by DataTable objects
     */
    private DataTable dtDensity_, dtPaux_, dtBField_;
    /*
     * Fixed parameters governing the integration
     */
    private int mode_;                        // Operating mode
    private int nsteps_;                      // Number of output steps
    private double tinit_;                    // Initial time (seconds)
    private double tfinal_;                   // Final time (seconds)
    private double tempinit_;                 // Initial temperature (keV)
    private double nheinit_;                  // Initial helium ash number

    private static int neq_ = 4;              // Number of equations
    private static boolean solved_ = false;   // Used by is_solved
    /**
     * The constructor takes as arguments some parameters providing
     * external control of the integration.  It then instantiates
     * the FusionReactor and returns.
     * @param mo operating mode 
     * @param ns number of output time steps
     * @param ti time at which integration is started (seconds)
     * @param tf time at which the integration ends (seconds)
     * @param temp initial plasma temperature (keV)
     * @param nhe initial helium ash number
     */
    public TimeDepFusionSolver(int mo, int ns, double ti, double tf, 
			       double temp, double nhe) {
	/* 
	 * Set integration controlling parameters using inputs
	 */
	mode_ = mo;
	nsteps_ = ns;
	tinit_ = ti;
	tfinal_ = tf;
	tempinit_ = temp;
	nheinit_ = nhe;
	/*
	 * Define the ITER-89P confinement time expression
	 */
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
	 * Check mode parameter to see what value to use for the 
	 * helium ash equation multiplier.
	 */
	double cash;
	switch (mode_) {
	case STEADY:
	    cash = 0.;
	    break;
	case BASELINE:
	    cash = 0.;
	    break;
	case ADV_ASH_ACCUMULATION:
	    cash = 1.;
	    break;
	case ADV_HEAT_LIMITED:
	    cash = 0.;
	    break;
	case ADV_HEAT_AND_ASH:
	    cash = 1.;
	    break;
	default:
	    cash = 0.;
	}
	/*
	 * Allocate and initialize Observables.  The values are similar to
	 * those used in one the ASPECT code test input files.
	 */
	oBField_ = new ObservableDbl(8.10);
	oDensity_ = new ObservableDbl(3.);
	oPaux_ = new ObservableDbl(40.);
	oTemperature_ = new ObservableDbl(0.);
	oScore_ = new ObservableDbl(0.);
	oNHe_ = new ObservableDbl(0.);
	/*
	 * Attempt to initialize FusionReactor bpx_.
	 */
	try {
	    bpx_ = new FusionReactor(abpx, rbpx, qbpx, cash, 
				     oBField_, oDensity_, oPaux_, 
				     oTemperature_, oNHe_, oScore_, iter89P_); 
	} catch (IncompleteReactorExcpn e) {
	    System.out.println("TDFTest.main caught exception: " + e.getMessage());
	    return;
	}
    }
    /**
     * This the ODESolved derivatives method that returns the derivatives of 
     * the dependent variable(s) with respect to the independent variable.
     * Currently, the equations for the total plasma energy and the number
     * of helium ash atoms are integrated.  Additional equations representing
     * the time-integrated fusion energy and heating energy (sum of alpha,
     * auxiliary, and ohmic heating) are "solved".
     * total thermal plasma energy.
     *
     * More specifically, the dependent variables are
     * <ol>
     * <li> Plasma energy (in J)
     * <li> Number of helium ash atoms
     * <li> Time integrated sum of alpha, auxiliary, and ohmic heating
     *      powers (in J)
     * <li> Time integral of the fusion power (in J)
     * </ol>
     * @param W current value of the dependent variables
     * @param t current time (in s)
     * @exception RootSolverException is thrown when something isn't as expected
     * @return current value of d W / dt
     */
    public double[] derivatives(double[] W, double t) throws RootSolverException {
	double[] w_dot = new double[neq_];
	/* 
	 * Check size of W
	 */
	if (W.length != neq_) {
	    throw new RootSolverException("Call to derivatives with wrong size W array");
	}
	/*
	 * Get density, Paux, and BField values at time t
	 */
	double density = dtDensity_.interpolate(t);
	double Paux = dtPaux_.interpolate(t);
	double BField = dtBField_.interpolate(t);
	/* 
	 * Set corresponding observables
	 */
	oDensity_.setValue(density);
	oPaux_.setValue(Paux);
	oBField_.setValue(BField);
	/*
	 * Can now evaluate time derivatives of the dependent variables.
	 * These are listed at the top of this class.  Note that the
	 * fusion power is just 5 times the alpha power.
	 */
	double Palpha, Poh;
	try {
	    oNHe_.setValue(W[1]);
	    bpx_.setTemp(W[0]);
	    w_dot[0] = bpx_.calcdWdt();
	    w_dot[1] = bpx_.calcdNHedt();
	    Palpha = bpx_.calcPalpha();
	    Poh = bpx_.calcPoh();
	} catch (IncompleteReactorExcpn e) {
	    throw new RootSolverException("derivatives method caught IncompleteReactorExcpn" + e.getMessage());
	}
	/*
	 * Note that because Paux is externally specified rather than
	 * computed, we have it stored in MW and not in watts like 
	 * the others.
	 */
	w_dot[2] = Palpha + Paux*1.e6 + Poh;
	w_dot[3] = 5.*Palpha;
	return(w_dot);
    }
    /**
     * Returns the initial value for the independent variable.
     */
    public double get_initial_x() {
	/*
	 * From the ASPECT default
	 */
	return(tinit_);
    }
    /**
     * Returns the initial array of values of the dependent variable.
     */
    public double[] get_initial_ys() throws RootSolverException {
	double tinit = get_initial_x();
	double density = dtDensity_.interpolate(tinit);
	oDensity_.setValue(density);
	/* 
	 * Convert input temperature from keV to 1.e6 kelvin
	 */
	oTemperature_.setValue(tempinit_ * 11.604);
	oNHe_.setValue(nheinit_);
	double[] winit = new double[neq_];
	try {
	    winit[0] = bpx_.calcWtot();
	} catch (IncompleteReactorExcpn e) {
	    throw new RootSolverException("get_initial_ys method caught IncompleteReactorExcpn" + e.getMessage());
	}
	winit[1] = nheinit_;
	/*
	 * These are just the direct time integrals of powers.
	 */
	winit[2] = 0.;
	winit[3] = 0.;
	return(winit);
    }
    /**
     * Returns the desired relative integration accuracy.  Whether and how
     * this parameter is used depends on the integration routine.
     */
    public double get_relative_error() {
	return(1.e-4);
    }
    /**
     * Returns the desired absolute integration accuracy.  Whether and how
     * this parameter is used depends on the integration routine.
     * NumRecRungeKutta does NOT need this, so we just return 0 here.
     */
    public double get_absolute_error() {
	return(0.);   
    }
    /**
     * Returns the (absolute) initial step size.  
     */
    public double get_initial_step_size() {
	return(1.e-3);
    }
    /**
     * Returns the minimum step size.  Some integration routines may allow 
     * this to be zero so that other mechanisms are invoked to stop failed 
     * (i.e., not making adequate progress) integrations.
     */
    public double get_min_step_size() {
	return(0.);
    }
    /**
     * Returns an array of one or more values at which the solution of the
     * ODE is desired.
     */
    public double[] get_desired_xs() {
	double[] t_steps = new double[nsteps_];
	double delta_t = (tfinal_ - tinit_) / (double) nsteps_;
	for (int i=0; i < nsteps_; i++) {
	    t_steps[i] = tinit_ + delta_t * (double) (i+1);
	}
	return(t_steps);
    }
    /**
     * This method sets the input datatable object references
     * in preparing for a calculation.
     * @param m DataTable representing the magnetic field variation
     * @param d DataTable representing plasma density variation
     * @param p DataTable representing auxiliary power variation
     */
    public void setInputs(DataTable m, DataTable d, DataTable p) {
	// Set the datatables
	dtBField_ = m;
	dtDensity_ = d;
	dtPaux_ = p;
	// Initialized results
	resultScore_ = new DataTable();
	resultT_ = new DataTable();
	resultW_ = new DataTable();
	resultNHe_ = new DataTable();
	resultPalpha_ = new DataTable();
	resultWheat_ = new DataTable();
    }

    /**
     * This method provides a means of receiving the dependent variables
     * computed at each of the desired x values.  Following the initial
     * implementation, the desired ordering of subscripts for W is
     * output step first (left index) and equation number second (on the
     * right).  This should be consistent with array storage order.
     * The method should compare subscripts with those expected
     * and throw an exception when they don't agree.
     * @param W array of energy values at each time
     * @param t array of time values
     * @exception RootSolverException is thrown if the arrays are not of the
     * expected size.
     */
    public void set_results(double[][] W, double[] t) throws RootSolverException {
	if (t.length != nsteps_+1) {
	    throw new RootSolverException("Result x is wrong size");
	}
	/*
	 * For reasons I don't understand, the length of the entire W
	 * array corresponds to that of the first subscript.  The length
	 * of the second subscript is tested below.
	 */
	if (W.length != (nsteps_+1)) {
	    throw new RootSolverException("Result W is wrong size");
	}
	for (int i=0; i < nsteps_+1; i++) {
	    if (W[i].length != neq_) {
		throw new RootSolverException("Result W subscript order is wrong");
	    }
	    double density = dtDensity_.interpolate(t[i]);
	    double Paux = dtPaux_.interpolate(t[i]);
	    double BField = dtBField_.interpolate(t[i]);
	    oDensity_.setValue(density);
	    oPaux_.setValue(Paux);
	    oBField_.setValue(BField);
	    try {
		oNHe_.setValue(W[i][1]);
		bpx_.setTemp(W[i][0]);
	    } catch (IncompleteReactorExcpn e) {
		throw new RootSolverException("set_results method caught IncompleteReactorExcpn" + e.getMessage());
	    }
	    double temperature = oTemperature_.getValue();
	    double Palpha;
	    try {
		Palpha = bpx_.calcPalpha();
	    } catch (IncompleteReactorExcpn e) {
		throw new RootSolverException("set_results method caught IncompleteReactorExcpn" + e.getMessage());
	    }
	    try {
		bpx_.calcOScore();
	    } catch (IncompleteReactorExcpn e) {
		throw new RootSolverException("set_results method caught IncompleteReactorExcpn" + e.getMessage());
	    }
	    // Enter into datatable
	    resultScore_.add(t[i], oScore_.getValue());
	    /*
	     * The observable is in million kelvin.  Convert to
	     * degrees Celsius.
	     */
	    resultT_.add(t[i], temperature*(1.e6)-273.15);
	    resultW_.add(t[i], W[i][0]*1.e-6);
	    resultNHe_.add(t[i], W[i][1]*1.e-20);
	    resultPalpha_.add(t[i],Palpha*1.e-6);
	    resultWheat_.add(t[i],W[i][2]*1.e-6);
	}
	solved_ = true;
    }
    /*
     * This method provides a means of alerting others that the ODE has been
     * solved and that the set_results method has been called.  I.e., this
     * method should return "true" in that case and "false" otherwise.
     * @return flag indicating whether or not the ODE has been solved.
     */
    public boolean is_solved() {
	return(solved_);
    }

    /**
     * This method returns the results once they've been calculated
     * and thens sets solved_ to false
     */
    public DataTable[] get_results() {
	solved_ = false;
	DataTable[] t = {resultScore_, resultT_, resultW_, resultNHe_, 
			 resultPalpha_, resultWheat_};
	return t;
    }
    /**
     * Provides the Jacobian of the differential equations.  For clarity, if
     * f[i] = d y[i] / dx (where i=0->n-1, n=number of equations) this routine 
     * should compute and return d f[i] / dx and d f[i] / d y[j].  The former
     * is an n element 1-D array; the latter an n x n array.  To avoid returning
     * data via the argument list, suggest having the routine return an
     * suggest that this return an n (first index) x n+1 (second index, with
     * "1" representing d f[i] / dx) array.  Whether or not this method is
     * needed depends on the algorithm being used to integrate the equations.
     * @param y array of the local values of the dependent variables
     * @param x local values of the independent variable at which the
     * derivatives are to be evaluated
     * @exception RootSolverException is thrown when something isn't as expected
     * @return array representing the Jacobian
     */
    public double[][] jacobian(double[] y, double x) throws RootSolverException {
	/*
	 * The NumRecRungeKutta integration class we're using does not
	 * need this, so we have just a dummy method here.
	 */
	double[][] jac = new double[neq_][neq_+1];
	return(jac);
    }
}
