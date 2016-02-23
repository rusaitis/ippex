/*
 * class FusionReactor
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
package ssfd;

import java.lang.Math;
import java.util.Observer;
import java.util.Observable;
import observable.ObservableDbl;
import observable.ObservableRatio;

/**
 * The instance variables of this class specify the parameters of a 
 * hypothetical fusion reactor. The class is largely comprised of
 * methods for evaluating the principle energy source and sink terms
 * in the reactor. 
 *
 * There is also a <a href="http://w3.pppl.gov/~dstotler/SystemsCode/">
 * User's Manual</a> for the
 * full-featured FORTRAN code upon which this code is based.
 * The corresponding equation numbers are mentioned in the code.
 *
 * @version $Revision: 1.4 $, $Date: 2004/11/30 22:13:58 $.
 * @author Daren Stotler
 * @see ExpTauE
 */

/*
 * $Log: FusionReactor.java,v $
 * Revision 1.4  2004/11/30 22:13:58  dstotler
 * Add helium ash particle number and accumulation rate.
 *
 * Revision 1.3  2004/10/07 20:29:01  dstotler
 * Add calcWtot and other methods needed for time integration.
 *
 * Revision 1.2  2000/01/21 20:39:39  dstotler
 * Changed energy confinement time multiplier from 1.85 to 1.80.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.0 02-06-97 Finally give it a number.
 *
 * The methods in this class have changed little since the initial
 * implementation taken from the ASPECT code, apart from the introduction
 * of Observables.
 */
public class FusionReactor implements Observer {

  /*
   * Observable variables which provide the methods of information transfer
   * from and to other classes.
   */
    private ObservableDbl oBField_, oCurrent_, oDensity_, oPaux_, 
                        oTemperature_, oScore_, oNHe_;
                       
  /*
   * These can in general be adjusted, but here we hold 'em fixed for
   * the life of the object.
   */
    private double rMinor_, rMajor_, qCyl_, cAsh_;
    private double elongation_, zEff_, zImp_, rnDnDT_, cTau_,   // Local inputs
      tauPHe_;                                                
    private double volume_, xSArea_, rnDTne_, rnine_, rnZne_;   // Derived
    private boolean tFixed_;                                    // Mode switch

    /*
     * Expression for the auxiliary-heated energy confinement time.
     */
    private ExpTauE tauEForm_;

  /*
   * Constants
   */
  private static final double K_BOLTZMANN = 1.6021e-16; // Boltzmann's constant
  private static final double MU_0 = 4.0e-7 * Math.PI;  // Permittivity 
  private static final double E_ALPHA_0 = 3.5e3;        // Alpha energy (keV)

  /**
   * Constructs and initializes a hypothetical fusion reactor with
   * the specified parameters:
   * @param a plasma minor radius in meters
   * @param R plasma major radius in meters
   * @param q equivalent cylindrical safety factor
   * @param bf (observable) toroidal magnetic field in tesla
   * @param de (observable) electron density in m**-3
   * @param pa (observable) auxiliary heating power in mega-watts
   * @param te (observable) electron = ion temperature in 1.e6 K
   * @param bq (observable; output) measure of reactor performance
   * @param t expression for the auxiliary heated confinement time
   * @exception IncompleteReactorExcpn one or more of these parameters
   *                                   is invalid
   * This is the original in which the helium ash density is NOT
   * externally specified.
   */
    public FusionReactor(double a, double R, double q, 
			 ObservableDbl bf, ObservableDbl de, ObservableDbl pa, 
			 ObservableDbl te, ObservableDbl bq, 
			 ExpTauE t) throws IncompleteReactorExcpn {
	/*
	 * Call the new constructor defaulting the helium ash observable
	 * to zero value and zeroing out the coefficient, cAsh_:
	 */
	this(a, R, q, 0., bf, de, pa, te, new ObservableDbl(0.), bq, t);
    }
  /**
   * Constructs and initializes a hypothetical fusion reactor with
   * the specified parameters:
   * @param a plasma minor radius in meters
   * @param R plasma major radius in meters
   * @param q equivalent cylindrical safety factor
   * @param ca multiplier on the helium ash accumulation rate
   * @param bf (observable) toroidal magnetic field in tesla
   * @param de (observable) electron density in 1.e20 m**-3
   * @param pa (observable) auxiliary heating power in mega-watts
   * @param te (observable) electron = ion temperature in 1.e6 K
   * @param nhe (observable) number of helium ash atoms
   * @param bq (observable; output) measure of reactor performance
   * @param t expression for the auxiliary heated confinement time
   * @exception IncompleteReactorExcpn one or more of these parameters
   *                                   is invalid
   * This is the revised version in which the helium ash density is 
   * externally specified.
   */
    public FusionReactor(double a, double R, double q, double ca,
			 ObservableDbl bf, ObservableDbl de, ObservableDbl pa, 
			 ObservableDbl te, ObservableDbl nhe, 
			 ObservableDbl bq, ExpTauE t) 
	throws IncompleteReactorExcpn {
	
	rMinor_ = a;
	rMajor_ = R;
	qCyl_ = q;
	tauEForm_ = t;
	cAsh_ = ca;
      
	oBField_ = bf;
	oDensity_ = de;
	oPaux_ = pa;
	oNHe_ = nhe;
	/*
	 * This is currently the only observer explicit watched by
	 * this class.  In principle, there could be others.
	 */
	oNHe_.addObserver(this);
	oTemperature_ = te;
	oScore_ = bq;
	/*
	 * This switch specifies whether the temperature or the total
	 * energy is considered externally specified. Invoking the
	 * <code>setTemp</code> method resets this to <code>false</code>.
	 */
	tFixed_ = true;
      
	init();
    }

  /**
   * Hardwire some of the remaining variables; computed derived
   * quantities. Check inputs as well.
   * @exception IncompleteReactorExcpn one or more of these parameters
   *                                   is invalid.
   */
  private void init() throws IncompleteReactorExcpn {

    /*
     * The present implementation assumes purely elliptical
     * cross sections. Values are again for BPX.
     */
    elongation_ = 2.0;  
    zEff_ = 1.65;       // Z-effective
    zImp_ = 6.0;        // Z of dominant impurity
    rnDnDT_ = 0.5;      // deuterium / (deuterium + tritium) ratio
    cTau_ = 1.80;       // Confinement enhancement multiplier
    tauPHe_ = 15.;      // Helium ash particle confinement time (seconds)

    if (rMinor_ <= 0.)
      throw new IncompleteReactorExcpn("rMinor_ nonpositive: " + rMinor_);
    if (rMajor_ <= 0.)
      throw new IncompleteReactorExcpn("rMajor_ nonpositive: " + rMajor_);
    if (elongation_ <= 0.)
      throw new IncompleteReactorExcpn("elongation_ nonpositive: "
				       +elongation_);
    if (zEff_ <= 0.)
      throw new IncompleteReactorExcpn("zEff_ nonpositive: " + zEff_);

    if (cAsh_ < 0.)
	throw new IncompleteReactorExcpn("cAsh_ negative: " + cAsh_);

    /*
     * With elliptical cross sections, area and volume expressions
     * are trivial.
     */
    xSArea_ = Math.PI*rMinor_*rMinor_*elongation_;  
    volume_ = 2.*Math.PI*rMajor_*xSArea_;

    /*
     * Set density ratios using initial helium ash number
     */
    setRnine();

    /*
     * Since qCyl_ varies much less from one reactor design to
     * another than do the magnetic field or plasma current,
     * we hold qCyl_ fixed and compute either the current or
     * field from it using the ObservableRatio, b_c_ratio.
     * Now set up to read the field and set the current.
     */
    oCurrent_ = new ObservableDbl(0.);
    double b_c_ratio = qCyl_ * 1.e6 * MU_0 * rMajor_   // = bfield / current
      / (Math.PI * rMinor_*rMinor_ * (1. + elongation_*elongation_));
    ObservableRatio o_b_o_c = new ObservableRatio(oBField_, oCurrent_, b_c_ratio);
  }

    public void setRnine() throws IncompleteReactorExcpn {
	/*
	 * Calculate density ratios, Eqs. 54 - 56
	 */
	if (oDensity_.getValue()*volume_ <= 0.)
	    throw new IncompleteReactorExcpn(
	   "oDensity_ * volume_ nonpositive: " + oDensity_.getValue());
	double rnHene = oNHe_.getValue() / (oDensity_.getValue() * 1.e20 * volume_);
	rnDTne_ = (zImp_ - zEff_ - 2.*(zImp_ - 2.)*rnHene) / (zImp_ - 1.);
	/*
	 * Allow for case where the chose zEff_ is too small for this value
	 * of rnHene.
	 */
	if (rnDTne_ <= 0.) {
	    rnDTne_ = 0.0;
	    zEff_ = zImp_ - 2.*(zImp_ - 2.) * rnHene;
	}
	rnZne_ = (zEff_ - rnDTne_ - 4.*rnHene) / (zImp_ * zImp_);
	rnine_ = rnDTne_ + rnHene + rnZne_;           // Ion / electron density
    }

  /**
   * Calculate the fusion power released in the form of energetic alpha 
   * particles.
   * @return fusion alpha power in watts
   * @exception IncompleteReactorExcpn one or more of these parameters
   *                                   is invalid.
   */
  public double calcPalpha() throws IncompleteReactorExcpn {

    /*
     * The following are implemented with limiting values of the form factors,
     * assuming flat profiles. Hence, the variables "density" and "temperature"
     * are synonymous with both average and central values. Incorporation of
     * profile effects should be done with care referring to the original code.
     */
    double density = oDensity_.getValue() * 1.e20;           // in m**-3
    double temperature = oTemperature_.getValue() / 11.604;  // in keV
    if (density <= 0.)
      throw new IncompleteReactorExcpn("density nonpositive: " + density);
    if (temperature <= 0.)
      throw new IncompleteReactorExcpn("temperature nonpositve: "+temperature);

    /*
     * DT reaction rate formula from L. M. Hively, Nucl. Fusion 17, 873 (1977).
     */
    double za1, za2, za3, za4, za5, za6, zrsg;
    za1=-21.377692;
    za2=-25.204054;
    za3=-7.1013427e-2;
    za4=1.9375451e-4;
    za5=4.9246592e-6;
    za6=-3.9836572e-8;
    zrsg=.2935;

    /* 
     * Note the careful placement of the 1.e20 factors (from den20, below)
     * so as to reduce the chances for overflow or underflow. The 1.e-6
     * factor puts the reaction rate itself in units of m**3/s.
     */
    double formula = 1.e20 * Math.exp(za1 / Math.pow(temperature,zrsg) + za2 
				+ temperature*(za3 + temperature*(za4 
				      + temperature*(za5 + temperature*za6)))) 
	              * 1.e20 * 1.e-6;
    double den20 = rnDTne_ * density / 1.e20;

    return (E_ALPHA_0 * K_BOLTZMANN * rnDnDT_ * (1. - rnDnDT_) * den20 * den20 
            * formula * volume_);
  }

  /**
   * Calculate the resistive, Ohmic heating of the plasma by the
   * toroidal current.
   * @return ohmic heating power in watts
   */
  public double calcPoh() throws IncompleteReactorExcpn {

    double density = oDensity_.getValue() * 1.e20;           // in m**-3
    double temperature = oTemperature_.getValue() / 11.604;  // in keV
    double current = oCurrent_.getValue() * 1.e6;            // in Amps
    /*
     * Coulomb log from nrl formulary converted to MKS-keV, Eq. 25
     */
    double zcolam = 37.8 - Math.log(Math.sqrt(density)/temperature);

    /*
     * Formulation from Eq. 24 is consistent with Uckan, Fusion Tech. 14, 
     * 299 (1988). 
     */
    double zj0 = current / xSArea_;   // Central current density, Eq. 26
    double resnc = 2.5;              // Neoclassical resistivity correction
    double fpoh = 1.65e-9 * zEff_ * zcolam * resnc 
      * Math.pow(temperature,-1.5) * zj0 * zj0 * volume_;

    return (fpoh);
  }

  /**
   * Calculate total radiated power lost from the plasma.
   * @return radiated power in watts
   */
  public double calcPrad() throws IncompleteReactorExcpn {

    double density = oDensity_.getValue() * 1.e20;            // in m**-3
    double temperature = oTemperature_.getValue() / 11.604;   // in keV
    /*
     * Presently just include Bremsstrahlung radiation (in W), Eq. 27
     */
    double den20 = density / 1.e20;
    double fpbr = 1.0e06 * 0.0168 * den20 * den20 
      * Math.sqrt(temperature/10.) * zEff_ * volume_;

    return (fpbr);
  }

  /**
   * Uses the definition of the energy confinement time to infer the
   * amount of heat conducted out of the plasma.
   * @param pinput net heating power needed for power-degrading scalings
   * @return heat lost by conduction in watts
   * @exception IncompleteReactorExcpn one or more of these parameters
   *                                   is invalid.
   */
  public double calcPcon(double pinput) throws IncompleteReactorExcpn {

    double current = oCurrent_.getValue() * 1.e6;           // in Amps
    double density = oDensity_.getValue() * 1.e20;          // in m**-3
    double bfield = oBField_.getValue();

    if (current <= 0.)
      throw new IncompleteReactorExcpn("current nonpositive: " + current);
    if (bfield <= 0.)
      throw new IncompleteReactorExcpn("bfield nonpositive: " + bfield);

    double w_tot = calcWtot();

    /*
     * Confinement time expressions:
     * ztaue   is the auxiliary heating confinement time given by instance
     *         tau of the ExpTauE class. 
     * ztauNA  is the neoAlcator confinement time
     * zatuEff is the geometric mean with the multiplier cTau_ on the auxiliary
     *         heated time. In ASPECT the exponent (2) is a variable.
     * Note that aibar is hardwired to 2.5
     */
    double tau_e = tauEForm_.calcExpTauE(2.5, elongation_, current, density, 
                                        bfield, rMinor_, rMajor_, pinput);

    double tau_na = 7.e-22 * density * rMinor_ * rMajor_ * rMajor_ * qCyl_;

    double tau_eff = 1. / 
      Math.sqrt(1./(tau_na*tau_na) + Math.pow(cTau_ * tau_e,-2));
      
    return (w_tot / tau_eff);
  }

  /**
   * Evaluate the power balance and calculate the time rate of
   * change of the integrated plasma energy.
   * @return rate of change of the plasma energy
   * @exception IncompleteReactorExcpn one or more of these parameters
   *                                   is invalid.
   */   
  public double calcdWdt() throws IncompleteReactorExcpn {

    double p_alpha = calcPalpha(); 
    double p_oh= calcPoh();
    double p_rad = calcPrad();
    double p_aux = oPaux_.getValue() * 1.e6;    
    double p_input = p_alpha + p_oh + p_aux - p_rad;
    double p_con = calcPcon(p_input);

    return (p_input - p_con);
  }

    /**
     * Evaluate the time rate of change of the number of helium ash atoms.
     */
    public double calcdNHedt() throws IncompleteReactorExcpn {
	double p_alpha = calcPalpha();
	double dNHedt = cAsh_ * (p_alpha / (E_ALPHA_0 * K_BOLTZMANN)
				 - oNHe_.getValue() / tauPHe_);
	return(dNHedt);
    }

    /**
     * Simply compute and return the plasma energy.
     */
    public double calcWtot() throws IncompleteReactorExcpn {
	double density = oDensity_.getValue() * 1.e20;          // in m**-3
	double temperature = oTemperature_.getValue() / 11.604; // in keV
	/*
	 * Total thermal energy, Eq. 30, in joules.
	 */
	double w_tot = 1.5 * density * K_BOLTZMANN * temperature 
	    * ( 1. + rnine_ ) * volume_;
	return(w_tot);
    }
    /**
     * This is the effectively the inverse of <code>calcWtot</code>.
     * Note that unlike the other methods, the plasma energy 
     * <code>w_tot</code> is an argument.  Codes using this method
     * should consider it as specified and the plasma temperature
     * as derived.  Codes not using this method should consider the
     * temperature as specified (via the corresponding ObservableDbl)
     * and the plasma energy as derived.  Attempting to call this method
     * and to externally set the temperature value will lead to 
     * unpredictable results.
     */
    public void setTemp(double w_tot) throws IncompleteReactorExcpn {
	tFixed_ = false;
	double density = oDensity_.getValue() * 1.e20;          // in m**-3
	if (density <= 0.)
	    throw new IncompleteReactorExcpn("density nonpositive: " + density);
	/*
	 * See corresponding expressions in <code>calcWtot</code>
	 */
	double temperature = w_tot 
	    / (1.5 * density * K_BOLTZMANN * ( 1. + rnine_ ) * volume_);
	/*
	 * The observable is in units of 1.e6 K; the above formula uses keV.
	 */
	oTemperature_.setValue(temperature * 11.604);
    }
  /**
   * Calculate a figure of merit.
   * This will:
   * <ol>
   * <li> be 100 at Q = fusion
   * power divided by total heating power, 
   * <li> be more sensitive to changes in
   * Q at low Q than at high Q.
   * </ol>
   * This score also takes into account the
   * density and beta limits; the score = 0 when they are exceeded.
   * The score value is propagated through the rest of the code by
   * the ObservableDbl oScore_.
   * @see #calcBetaMax
   * @see #calcDenMax
   * @exception IncompleteReactorExcpn one or more of these parameters
   *                                   is invalid.
   */   
  public void calcOScore() throws IncompleteReactorExcpn {

   double p_alpha = calcPalpha(); 
    double p_oh= calcPoh();
    double p_aux = oPaux_.getValue() * 1.e6;

    double beta=0.;
    try {
      beta = calcBetaTot();
    } catch (IncompleteReactorExcpn e) {
      System.out.println("performanceValue caught exception: "+e.getMessage());
      oScore_.setValue(0.);
    }
    double betamax = calcBetaMax();
    double denmax = calcDenMax();
    double density = oDensity_.getValue() * 1.e20;

    if (beta > betamax || density > denmax) {
      System.out.println("Disruption, beta/betamax = " + beta/betamax
			 + ", density/denmax = " + density/denmax);
      oScore_.setValue(0.);
    }
    else {                                 
      double q = 5.*p_alpha / (p_aux + p_oh);       // Fusion Q
      oScore_.setValue(100.*Math.pow(q/100.,0.3));  // Ad hoc score formula
    }
  }

  /**
   * Calculate the total (thermal + fast alpha) plasma beta.
   * @return ratio of the plasma to magnetic field pressure (dimensionless)
   * @exception IncompleteReactorExcpn one or more of these parameters
   *                                   is invalid.
   */
  public double calcBetaTot() throws IncompleteReactorExcpn {

    double density = oDensity_.getValue() * 1.e20;           // in m**-3
    double temperature = oTemperature_.getValue() / 11.604;  // in keV
    double bfield = oBField_.getValue();

    /*
     * Source rate of alpha particles.
     */
    double znsour = calcPalpha() / (E_ALPHA_0 * K_BOLTZMANN * volume_);

    /*
     * Compute electron, deuterium, tritium, and impurity Coulomb 
     * logarithms, Eq. 63. The impurity mass (zamimp) is approximately 
     * twice its nuclear charge.
     */
    double zlame = 37.8 - Math.log(Math.sqrt(density)/temperature);
    double zlamd = 45.5 + Math.log(Math.sqrt(temperature / density) 
                                   * 4.*2./(4.+2.));
    double zlamt = 45.5 + Math.log(Math.sqrt(temperature / density) 
                                   * 4.*3./(4.+3.));
    double zamimp = 2. * zImp_;
    double zlamz = 45.5 + Math.log(Math.sqrt(temperature / density) 
                                     * 4.*zamimp/(4.+zamimp));

    /*      
     * Critical slowing down energy at which loss of energy to electrons 
     * equals the loss to ions, Eq. 62.
     */
    double zecrit = 4.0*14.8 * temperature * 
      Math.pow((((rnDnDT_ * zlamd / 2. + (1.-rnDnDT_) * zlamt / 3.) * rnDTne_
                 + rnZne_ * zlamz * zImp_*zImp_ / zamimp)
                / zlame),(2./3.));
    double zvcrat = Math.sqrt(E_ALPHA_0 / zecrit); // Velocity ratio, Eq. 64

    double ztauth = 0.371 * Math.pow(temperature/10.,1.5) // Slowing down 
      * (1.0e20/density) * (17./zlame);                   // time, Eq. 65 

    double zeavg = E_ALPHA_0*K_BOLTZMANN * 0.5 // Avg. alpha energy, Eq. 66
      * (1.-Math.pow(zvcrat,(-2.))
         *(Math.log((1.+Math.pow(zvcrat,3.))/Math.pow((1.+zvcrat),3.)) / 3.
           + (2. * Math.atan((2.*zvcrat - 1.) / Math.sqrt(3.))
              + Math.PI / 3.) / Math.sqrt(3.)));

    /*
     * Time to thermalize, Eq. 67 
     */
    double ztaunf = ztauth * Math.log( 1. + Math.pow(zvcrat,3.) ) / 3.;

    /*
     * Alpha beta, Eq. 69; thermal beta, Eq. 61; total beta, Eq. 70;
     * alpha density, Eq. 68; alpha / electron density:
     */
    double pbetfa = (2.*MU_0/bfield*bfield)*(2./3.)*(znsour*ztauth*zeavg);
    double zbetth = (2.*MU_0/bfield*bfield)*density
      *( 1. + rnine_ )*K_BOLTZMANN*temperature;
    double pbeta  = pbetfa + zbetth;

    return(pbeta);
  }  

  /**
   * Calculate maximum allowed beta value.
   * @return maximum allowed ratio of plasma to magnetic field pressure
   */
  public double calcBetaMax() 
    {
    double current = oCurrent_.getValue();            // in mega-amps!
    double bfield = oBField_.getValue();

    /*
     * Use the Troyon limit with a coefficient of 3.0
     */
    return (3.0 * current / (rMinor_ * bfield));
    }

  /** 
   * Calculate the maximum allowed density.
   * @return maximum allowed electron density in m**-3
   */
  public double calcDenMax()
    {
      double current = oCurrent_.getValue();              // in mega-amps!

      /*
       * This is the Greenwald limit; it's normally taken to
       * be the line-averaged density. But, here, we have effectively
       * flat profiles.
       */
      return (1.e20 * current / (Math.PI*rMinor_*rMinor_));
    }
    /**
     * Watch for changes in observables monitored by this class.
     * Currently, there is only one.
     */
    public void update(Observable obs, Object arg)
    { 
	if (obs == oNHe_) {
	    /*
	     * This ensures that  the current temperature
	     * and total energy are consistent when the plasma
	     * energy is externally specified (rather than the
	     * temperature).
	     */
	    try {
		if (!tFixed_) {
		    double w_tot;
		    w_tot = calcWtot();
		    setRnine();
		    setTemp(w_tot);
		} 
		else {
		    setRnine();
		}
	    } catch (IncompleteReactorExcpn e) {
		System.out.println("update caught exception: "+e.getMessage());
	    }
	}
    }
    }
