//*****Physics constants and variables*************

var K_BOLT = 1.6021e-16; // Boltzmann's constant
var MU_0 = 4.0e-7 * Math.PI;  // Permittivity
var E_ALPHA_0 = 3.5e3;        // Alpha energy (keV)

var elon= 2.0;
var zEff = 1.65;       // Z-effective
var zImp = 6.0;        // Z of dominant impurity
var rnDnDT = 0.5;      // deuterium / (deuterium + tritium) ratio
var cTau = 1.80;       // Confinement enhancement multiplier
var tauPHe = 15.;      // Helium ash particle confinement time (seconds)
var rmin = 0.795;
var rmaj = 2.59;
var q = 2.3;
var Nhe = 0; //Helium ash
var cAsh = 0; //Helium ash coefficient

var area = Math.PI*rmin*rmin*elon;
var volume = 2*Math.PI*rmaj*area;

var b_c_ratio = q * 1.e6 * MU_0 * rmaj / (Math.PI * rmin*rmin * (1. + elon*elon));

//************Programatic variables***********

var finalOut = {};
var finalTemp = 0;
var finalScore = 0;
// var magVal = 14;
var magVal = 1;
var densVal = 2.5;
var powVal = 0.19;
var magMax = 20;
var densMax = 10;
var powMax = 10;

var aliens = [];
var totalDudes = 1000;

var ParticleSpread = 0; //20
var LarmourRadius = 0; //5
var ThetaMax = 23; // as multiples of (2PI)

function writeMessage(message) {
      text.setText(message);
      layer.draw();
}

function displayScore(){
    finalTemp = bisection();
    //finalTemp = 250;
    finalOut = calcScore(finalTemp);
    finalScore = finalOut.score;
    iter.tween = new Konva.Tween({
    node:iter,
    opacity:finalScore/169,
    duration:0
    }).play();
    layer.draw();
    writeMessage('Mag: ' + magVal + '\n' + 'Dens: ' + densVal + '\n' + 'Pow: ' + powVal + '\n\n' + 'Temp: ' + finalTemp  + '\n' + 'Score: ' + finalScore + '\n\n' + 'P_oh: ' + finalOut.P_oh * 1.e-6 + '\n' + 'P_alpha: ' + finalOut.P_alpha * 1.e-6 + '\n' + 'P_cond: ' + finalOut.P_cond * 1.e-6 + '\n' + 'P_rad: ' + finalOut.P_rad * 1.e-6 + '\n\n' + 'Beta thermal: ' + finalOut.beta_th + '\n' + 'Beta alpha: ' + finalOut.beta_falpha + '\n'+ 'Beta: ' + finalOut.beta + '\n' + 'Beta max: ' + finalOut.betamax + '\n\n' + 'Density: ' + finalOut.densreal*1.e-20 + '\n' + 'Dens_max: ' + finalOut.densmax*1.e-20 + '\n');
}

// displayScore();

//**************************Physics**********************************

function bisection() {
    var Tmin = 1.;
    var Tmax = 1000.;
    var Dt = Tmax - Tmin;
    var maxloops = 20;
    var t_left = Tmin;
    var t_right = Tmax;
    var t_mid = Tmin + Dt/2.;
    var f_left = calcdwdt(t_left);
    var f_right = calcdwdt(t_right);
    var f_mid = calcdwdt(t_mid);
    var t_final;
    if (f_left*f_right >= 0) {
      return (-1.)
      console.log('returned in bisection');
    }

    for (i=0 ; i<maxloops ; i++) {

        if (f_left * f_mid >= 0) {
            t_left = t_mid;
        } else {
            t_right = t_mid;
        }
        Dt = (t_right-t_left)/2.;
        t_mid = t_left + Dt;
        f_left = calcdwdt(t_left);
        f_right = calcdwdt(t_right);
        f_mid = calcdwdt(t_mid);

        if (i == (maxloops-1)) {
            t_final = t_mid;
        }
    }
    return(t_final);
}


function calcdwdt(temperature) {
    var mag = magVal;
    var P_aux_MW = powVal;
    var dens = densVal;

    var densreal = dens * 1.e20; //in m^-3
    var tempkev = temperature / 11.604; //in keV
    var P_aux= P_aux_MW * 1.e6; //in Watts

    //density ratio calculations
    var rnHene = Nhe / (dens * 1.e20 * volume);
    var rnDTne = (zImp - zEff - 2.*(zImp - 2.)*rnHene) / (zImp - 1.);
    var rnZne = (zEff - rnDTne - 4.*rnHene) / (zImp * zImp);
    var rnine = rnDTne + rnHene + rnZne;

    //Wtot calculation
    var Wtot = 1.5 * densreal * K_BOLT * tempkev * ( 1. + rnine) * volume;

    //P_alpha calculation
    var za1=-21.377692;
    var za2=-25.204054;
    var za3=-7.1013427E-2;
    var za4=1.9375451E-4;
    var za5=4.9246592E-6;
    var za6=-3.9836572E-8;
    var zrsg=.2935;

    var s5 = 1.e20 * Math.exp(za1 / Math.pow(tempkev,zrsg) + za2 + tempkev*(za3 + tempkev*(za4 + tempkev*(za5 + tempkev*za6)))) * 1.e20 * 1.e-6;
    var den20 = rnDTne * densreal / 1.e20;

    var P_alpha = E_ALPHA_0 * K_BOLT * rnDnDT * (1. - rnDnDT) * den20 * den20 * s5 * volume;

    //P_oh calculation
    var current = mag/b_c_ratio*1.e6; //current in amps
    var CoulLog = 37.8 - Math.log(Math.sqrt(densreal)/tempkev);

    var J0 = current / area;   // Central current density, Eq. 26
    var resnc = 2.5;              // Neoclassical resistivity correction
    var P_oh = 1.65e-9 * zEff * CoulLog * resnc * Math.pow(tempkev,-1.5) * J0 * J0 * volume;

    //P_rad calculation
    var P_rad = 1.0e06 * 0.0168 * dens * dens * Math.sqrt(tempkev/10.) * zEff * volume;

    //tau_E, tau_eff and P_cond calculation
    var aibar = 2.5; //average ion mass (amu)
    var P_in = P_alpha + P_oh + P_aux - P_rad;
    var P_in_max = Math.max(P_in*1.e-6,1.);
    var tau_array = [aibar, elon, current*1.e-6, densreal*1.e-19, mag, rmin, rmaj, P_in_max];
    var exps = [0.0381, 0.5, 0.5, 0.85, 0.1, 0.2, 0.3, 1.2, -0.5];

    var tau_E = exps[0]
      * Math.pow(tau_array[0],exps[1])
      * Math.pow(tau_array[1],exps[2])
      * Math.pow(tau_array[2],exps[3])
      * Math.pow(tau_array[3],exps[4])
      * Math.pow(tau_array[4],exps[5])
      * Math.pow(tau_array[5],exps[6])
      * Math.pow(tau_array[6],exps[7])
      * Math.pow(tau_array[7],exps[8]);


    var tau_na = 7.e-22 * densreal * rmin * rmaj * rmaj * q;

    var tau_eff = 1. / Math.sqrt(1./(tau_na*tau_na) + Math.pow(cTau * tau_E,-2));

    var P_cond = Wtot / tau_eff;

    var dwdt = P_in - P_cond; //  = dW/dt This is what must be found to be zero

    return(dwdt);
}

function calcScore(temperature){

    //***This is all the same as the calcdwdt function****
    var mag = magVal
    var P_aux_MW = powVal;
    var dens = densVal;

    var densreal = dens * 1.e20; //in m^-3
    var tempkev = temperature / 11.604; //in keV
    var P_aux= P_aux_MW * 1.e6; //in Watts

    //density ratio calculations
    var rnHene = Nhe / (dens * 1.e20 * volume);
    var rnDTne = (zImp - zEff - 2.*(zImp - 2.)*rnHene) / (zImp - 1.);
    var rnZne = (zEff - rnDTne - 4.*rnHene) / (zImp * zImp);
    var rnine = rnDTne + rnHene + rnZne;

    //Wtot calculation
    var Wtot = 1.5 * densreal * K_BOLT * tempkev * ( 1. + rnine) * volume;

    //P_alpha calculation
    var za1=-21.377692;
    var za2=-25.204054;
    var za3=-7.1013427E-2;
    var za4=1.9375451E-4;
    var za5=4.9246592E-6;
    var za6=-3.9836572E-8;
    var zrsg=.2935;

    var s5 = 1.e20 * Math.exp(za1 / Math.pow(tempkev,zrsg) + za2 + tempkev*(za3 + tempkev*(za4 + tempkev*(za5 + tempkev*za6)))) * 1.e20 * 1.e-6;
    var den20 = rnDTne * densreal / 1.e20;

    var P_alpha = E_ALPHA_0 * K_BOLT * rnDnDT * (1. - rnDnDT) * den20 * den20 * s5 * volume;

    //P_oh calculation
    var current = mag/b_c_ratio*1.e6; //current in amps
    var CoulLog = 37.8 - Math.log(Math.sqrt(densreal)/tempkev);

    var J0 = current / area;   // Central current density, Eq. 26
    var resnc = 2.5;              // Neoclassical resistivity correction
    var P_oh = 1.65e-9 * zEff * CoulLog * resnc * Math.pow(tempkev,-1.5) * J0 * J0 * volume;

    //P_rad calculation
    var P_rad = 1.0e06 * 0.0168 * dens * dens * Math.sqrt(tempkev/10.) * zEff * volume;

    //tau_E, tau_eff and P_cond calculation
    var aibar = 2.5; //average ion mass (amu)
    var P_in = P_alpha + P_oh + P_aux - P_rad;
    var P_in_max = Math.max(P_in*1.e-6,1.);
    var tau_array = [aibar, elon, current*1.e-6, densreal*1.e-19, mag, rmin, rmaj, P_in_max];
    var exps = [0.0381, 0.5, 0.5, 0.85, 0.1, 0.2, 0.3, 1.2, -0.5];

    var tau_E = exps[0]
    * Math.pow(tau_array[0],exps[1])
    * Math.pow(tau_array[1],exps[2])
    * Math.pow(tau_array[2],exps[3])
    * Math.pow(tau_array[3],exps[4])
    * Math.pow(tau_array[4],exps[5])
    * Math.pow(tau_array[5],exps[6])
    * Math.pow(tau_array[6],exps[7])
    * Math.pow(tau_array[7],exps[8]);


    var tau_na = 7.e-22 * densreal * rmin * rmaj * rmaj * q;

    var tau_eff = 1. / Math.sqrt(1./(tau_na*tau_na) + Math.pow(cTau * tau_E,-2));

    var P_cond = Wtot / tau_eff;

    var dwdt = P_in - P_cond;

    //****Up to here it's the same as calcdwdt


    //Calculation of rate of change of Helium Ash (this is a placeholder for when He values are included)

    var dNHedt = cAsh * (P_alpha / (E_ALPHA_0 * K_BOLT)- Nhe/ tauPHe);

    //************Beta calculation**************

    // Source rate of alpha particles.

    var znsour = P_alpha / (E_ALPHA_0 * K_BOLT * volume);


    // Compute electron, deuterium, tritium, and impurity Coulomb
    // logarithms, Eq. 63. The impurity mass (zamimp) is approximately
    // twice its nuclear charge.

    var zlame = 37.8 - Math.log(Math.sqrt(densreal)/tempkev);
    var zlamd = 45.5 + Math.log(Math.sqrt(tempkev / densreal) * 4.*2./(4.+2.));
    var zlamt = 45.5 + Math.log(Math.sqrt(tempkev / densreal) * 4.*3./(4.+3.));
    var zamimp = 2. * zImp;
    var zlamz = 45.5 + Math.log(Math.sqrt(tempkev / densreal) * 4.*zamimp/(4.+zamimp));

    /*
    * Critical slowing down energy at which loss of energy to electrons
    * equals the loss to ions, Eq. 62.
    */
    var zecrit = 4.0*14.8 * tempkev * Math.pow((((rnDnDT * zlamd / 2. + (1.-rnDnDT) * zlamt / 3.) * rnDTne + rnZne * zlamz * zImp*zImp / zamimp) / zlame),(2./3.));
    var zvcrat = Math.sqrt(E_ALPHA_0 / zecrit); // Velocity ratio, Eq. 64

    var ztauth = 0.371 * Math.pow(tempkev/10.,1.5) * (1.0e20/densreal) * (17./zlame);//Slowing down time, Eq. 65

    var zeavg = E_ALPHA_0*K_BOLT * 0.5 * (1.-Math.pow(zvcrat,(-2.)) * (Math.log((1.+Math.pow(zvcrat,3.))/Math.pow((1.+zvcrat),3.)) / 3. + (2. * Math.atan((2.*zvcrat - 1.) / Math.sqrt(3.)) + Math.PI / 3.) / Math.sqrt(3.))); // Avg. alpha energy, Eq. 66

    /*
    * Time to thermalize, Eq. 67
    */
    var ztaunf = ztauth * Math.log( 1. + Math.pow(zvcrat,3.) ) / 3.;

    /*
    * Alpha beta, Eq. 69; thermal beta, Eq. 61; total beta, Eq. 70;
    * alpha density, Eq. 68; alpha / electron density:
    */

    var pbetfa = (2.*MU_0/(mag*mag))*(2./3.)*(znsour*ztauth*zeavg);
    var zbetth = (2.*MU_0/(mag*mag))*densreal * ( 1. + rnine)*K_BOLT*tempkev;
    var beta  = 100 * pbetfa + zbetth;  //In percentage


    //***********************************************

    //Beta limit Using the Troyon limit with a coefficient of 3.0

    var betamax = (3.0 * current*1.e-6 / (rmin * mag));

    //Density limit
    /*
    * This is the Greenwald limit; it's normally taken to
    * be the line-averaged density. But, here, we have effectively
    * flat profiles.
    */

    var densmax = 1.e20 * current*1.e-6 / (Math.PI * rmin * rmin);
    var Q_phy = 0.;
    var score = 0.;


    //Calculate score

    if (densreal < densmax && beta < betamax) {
        Q_phy = 5. * P_alpha / (P_aux + P_oh);
        score = 100. * Math.pow(Q_phy/100.,0.3);
    }
    var out = {
        score:score,
        beta:beta,
        beta_falpha:pbetfa,
        beta_th:zbetth,
        betamax:betamax,
        densreal:densreal,
        densmax:densmax,
        Q_phy:Q_phy,
        P_in,P_in,
        P_alpha:P_alpha,
        P_oh:P_oh,
        P_cond:P_cond,
        P_rad:P_rad
    }

    return(out);

}