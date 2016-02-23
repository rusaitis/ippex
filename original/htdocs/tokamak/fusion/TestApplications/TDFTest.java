/*
 * class TDFTest
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
package TestApplications;

import rootsolvers.NumRecRungeKutta;
import rootsolvers.RootSolverException;
import swingman.DataTable;
import java.text.NumberFormat;
import tdfd.TimeDepFusionSolver;
/**
 * This application is a text based interface to the time dependent
 * fusion solver (TimeDepFusionSolver) class.  Its principal purpose
 * is to facilitate benchmarking against the original ASPECT Fortran
 * code.  The input "waveform" data are handled here with a magnitude
 * and a normalized array providing the time variation, matching the
 * specification used in ASPECT.  Once the TimeDepFusionSolver object
 * has been created and initialized, it is passed (as an implementation
 * of the ODESolved interface) to the NumRecRungeKutta routine to be
 * integrated.  The results of the calculation are returned as an
 * array of DataTable objects.  The identity and order of the entries
 * in that array are specified in TimeDepFusionSolver.  A separate
 * output time grid is used here since the results arrays have none;
 * however, they are currently set up to match exactly.  The 
 * interpolate routine of the DataTable class is used just in case
 * they differ.  The resulting data are printed in tabular format
 * and should closely match (see also the file tdout in this directory):
 * <pre>
  step  t (s)  ne (1.e20 m^-3)  Paux (MW)  T (keV)   W (MJ)    Palpha (MW)  Score  Wheat (MJ)  N_He (1.e20)
  0    1.50000  0.30000        0.00000   1.00000     0.84929    0.00002    0.41685    0.00000    2.00000
  1    1.70000  0.34000        0.00000   3.52179     3.40637    0.01874    5.39148    4.54559    1.97354
  2    1.90000  0.38000        0.00000   3.68766     4.00168    0.02953    6.27640    7.60384    1.94749
  3    2.10000  0.42000        0.00000   3.72183     4.47766    0.03882    6.80996    10.61489    1.92182
  4    2.30000  0.46000        0.00000   3.73859     4.93866    0.04883    7.27660    13.64593    1.89652
  5    2.50000  0.50000        0.00000   3.75272     5.39981    0.06001    7.71879    16.70684    1.87159
  6    2.70000  0.54000        0.00000   3.76653     5.86377    0.07250    8.14541    19.79971    1.84704
  7    2.90000  0.58000        0.00000   3.78037     6.33102    0.08636    8.55937    22.92532    1.82286
  8    3.10000  0.62000        0.00000   3.79428     6.80163    0.10165    8.96219    26.08426    1.79905
  9    3.30000  0.66000        0.00000   3.80821     7.27553    0.11842    9.35492    29.27709    1.77561
  10    3.50000  0.70000        0.00000   3.82211     7.75266    0.13671    9.73840    32.50438    1.75254
  11    3.70000  0.74000        0.00000   3.83597     8.23293    0.15657    10.11334    35.76674    1.72985
  12    3.90000  0.78000        0.00000   3.84966     8.71606    0.17802    10.48176    39.06456    1.70753
  13    4.10000  0.82000        0.00000   3.86137     9.19767    0.20074    10.84117    42.39358    1.68558
  14    4.30000  0.86000        0.00000   3.87161     9.67838    0.22476    11.18722    45.75385    1.66401
  15    4.50000  0.90000        0.00000   3.88120     10.15974    0.25021    11.52368    49.14704    1.64281
  16    4.70000  0.94000        0.00000   3.89042     10.64235    0.27717    11.85223    52.57424    1.62199
  17    4.90000  0.98000        0.00000   3.89941     11.12644    0.30568    12.17376    56.03628    1.60154
  18    5.10000  1.02000        0.00000   3.90821     11.61206    0.33576    12.48880    59.53390    1.58146
  19    5.30000  1.06000        0.00000   3.91684     12.09921    0.36745    12.79772    63.06780    1.56176
  20    5.50000  1.10000        0.00000   3.92530     12.58784    0.40077    13.10081    66.63865    1.54244
  21    5.70000  1.14000        0.00000   3.93361     13.07791    0.43573    13.39830    70.24713    1.52349
  22    5.90000  1.18000        0.00000   3.94175     13.56937    0.47235    13.69040    73.89391    1.50492
  23    6.10000  1.22000        0.00000   3.94974     14.06216    0.51064    13.97732    77.57967    1.48673
  24    6.30000  1.26000        0.00000   3.95758     14.55624    0.55063    14.25921    81.30507    1.46892
  25    6.50000  1.30000        0.00000   3.96525     15.05154    0.59233    14.53625    85.07079    1.45148
  26    6.70000  1.34000        0.00000   3.97278     15.54803    0.63574    14.80857    88.87750    1.43443
  27    6.90000  1.38000        0.00000   3.98014     16.04564    0.68088    15.07632    92.72587    1.41777
  28    7.10000  1.42000        0.00000   3.98736     16.54435    0.72777    15.33963    96.61658    1.40148
  29    7.30000  1.46000        0.00000   3.99443     17.04409    0.77641    15.59862    100.55028    1.38559
  30    7.50000  1.50000        0.00000   4.00136     17.54482    0.82681    15.85341    104.52767    1.37007
  31    7.70000  1.50000        0.00000   4.09389     17.95155    0.90408    16.44544    108.46833    1.35500
  32    7.90000  1.50000        0.00000   4.15448     18.21819    0.95730    16.83637    112.32327    1.34036
  33    8.10000  1.55000        60.00000   5.18581     23.50360    2.31602    14.44695    121.93113    1.32726
  34    8.30000  1.65000        60.00000   6.65750     32.12989    6.04929    19.59691    136.92189    1.32418
  35    8.50000  1.75000        60.00000   7.71742     39.51097    10.67990    23.42234    152.22464    1.33609
  36    8.70000  1.85000        60.00000   8.54153     46.23543    15.96006    26.54325    168.25230    1.36543
  37    8.90000  1.95000        60.00000   9.21784     52.59714    21.82521    29.24560    185.22937    1.41413
  38    9.10000  2.05000        60.00000   9.79764     58.77282    28.28892    31.68338    203.32412    1.48402
  39    9.30000  2.15000        60.00000   10.31356     64.88223    35.40284    33.94837    222.68952    1.57703
  40    9.50000  2.25000        60.00000   10.78779     71.01435    43.24062    36.09944    243.48017    1.69526
  41    9.70000  2.35000        60.00000   11.23612     77.24030    51.89106    38.17645    265.86085    1.84112
  42    9.90000  2.45000        60.00000   11.67030     83.62051    61.45479    40.20745    290.01168    2.01728
  43    10.10000  2.55000        60.00000   12.09934     90.20858    72.04190    42.21265    316.13159    2.22678
  44    10.30000  2.65000        60.00000   12.53030     97.05339    83.76944    44.20659    344.44079    2.47300
  45    10.50000  2.75000        60.00000   12.96877     104.19996    96.75784    46.19939    375.18234    2.75972
  46    10.70000  2.85000        60.00000   13.41911     111.68928    111.12574    48.19742    408.62265    3.09108
  47    10.90000  2.95000        60.00000   13.88452     119.55726    126.98260    50.20360    445.05067    3.47156
  48    11.10000  3.00000        60.00000   14.60607     127.81428    144.11878    52.20197    484.75898    3.90574
  49    11.30000  3.00000        60.00000   15.59223     136.32158    161.46941    54.08051    527.87413    4.39545
  50    11.50000  3.00000        60.00000   16.59250     144.92220    178.48699    55.79162    574.38710    4.93974
  51    11.70000  3.00000        60.00000   17.58687     153.43935    194.58265    57.30940    624.17881    5.53564
  52    11.90000  3.00000        60.00000   18.55332     161.68029    209.22197    58.61699    677.01607    6.17823
  53    12.10000  3.00000        0.00000   19.07699     166.03688    215.36399    166.16027    726.41299    6.85499
  54    12.30000  3.00000        0.00000   19.14658     166.43721    213.61683    166.01596    769.71507    7.52430
  55    12.50000  3.00000        0.00000   19.18269     166.55036    211.37204    165.62560    812.61543    8.17766
  56    12.70000  3.00000        0.00000   19.18699     166.39223    208.68650    165.00748    855.02124    8.81361
  57    12.90000  3.00000        0.00000   19.16137     165.98051    205.61529    164.18025    896.85079    9.43092
  58    13.10000  3.00000        0.00000   19.10784     165.33388    202.21043    163.16232    938.03307    10.02856
  59    13.30000  3.00000        0.00000   19.02849     164.47134    198.52008    161.97144    978.50706    10.60571
  60    13.50000  3.00000        0.00000   18.92539     163.41163    194.58811    160.62438    1,018.22088    11.16169
  61    13.70000  3.00000        0.00000   18.80056     162.17285    190.45394    159.13675    1,057.13095    11.69601
  62    13.90000  3.00000        0.00000   18.65593     160.77214    186.15250    157.52284    1,095.20111    12.20830
  63    14.10000  3.00000        0.00000   18.49330     159.22550    181.71445    155.79560    1,132.40170    12.69831
  64    14.30000  3.00000        0.00000   18.31437     157.54768    177.16639    153.96659    1,168.70881    13.16591
  65    14.50000  3.00000        0.00000   18.12066     155.75208    172.53115    152.04605    1,204.10340    13.61103
  66    14.70000  3.00000        0.00000   17.91357     153.85076    167.82815    150.04290    1,238.57068    14.03372
  67    14.90000  3.00000        0.00000   17.69434     151.85446    163.07370    147.96485    1,272.09943    14.43404
  68    15.10000  3.00000        0.00000   17.46409     149.77265    158.28132    145.81841    1,304.68138    14.81215
  69    15.30000  3.00000        0.00000   17.22379     147.61358    153.46208    143.60902    1,336.31076    15.16822
  70    15.50000  3.00000        0.00000   16.97429     145.38435    148.62485    141.34111    1,366.98377    15.50246
  71    15.70000  3.00000        0.00000   16.71630     143.09099    143.77661    139.01814    1,396.69825    15.81511
  72    15.90000  3.00000        0.00000   16.45045     140.73853    138.92267    136.64272    1,425.45327    16.10644
  73    16.10000  3.00000        0.00000   16.17724     138.33111    134.06694    134.21662    1,453.24887    16.37670
  74    16.30000  3.00000        0.00000   15.89709     135.87198    129.21211    131.74088    1,480.08577    16.62619
  75    16.50000  3.00000        0.00000   15.61032     133.36365    124.35989    129.21581    1,505.96520    16.85517
  76    16.70000  3.00000        0.00000   15.31716     130.80794    119.51117    126.64111    1,530.88870    17.06393
  77    16.90000  3.00000        0.00000   15.01780     128.20600    114.66625    124.01587    1,554.85800    17.25275
  78    17.10000  3.00000        0.00000   14.71235     125.55844    109.82501    121.33864    1,577.87493    17.42192
  79    17.30000  3.00000        0.00000   14.40083     122.86538    104.98713    118.60749    1,599.94138    17.57170
  80    17.50000  3.00000        0.00000   14.08327     120.12650    100.15226    115.82005    1,621.05928    17.70235
  81    17.70000  3.00000        0.00000   13.74933     117.25352    95.17572    113.62728    1,641.21435    17.81398
  82    17.90000  3.00000        0.00000   13.38977     114.16746    89.93528    111.17446    1,660.35612    17.90602
  83    18.10000  3.00000        0.00000   13.00626     110.88250    84.46733    108.46401    1,678.43768    17.97786
  84    18.30000  3.00000        0.00000   12.60061     107.41389    78.81257    105.49971    1,695.41999    18.02903
  85    18.50000  3.00000        0.00000   12.17480     103.77814    73.01641    102.28718    1,711.27283    18.05922
  86    18.70000  3.00000        0.00000   11.73095     99.99310    67.12930    98.83442    1,725.97580    18.06830
  87    18.90000  3.00000        0.00000   11.27141     96.07817    61.20674    95.15240    1,739.51939    18.05632
  88    19.10000  3.00000        0.00000   10.79870     92.05445    55.30892    91.25571    1,751.90603    18.02354
  89    19.30000  3.00000        0.00000   10.31558     87.94484    49.49983    87.16313    1,763.15095    17.97043
  90    19.50000  3.00000        0.00000   9.82502     83.77401    43.84565    82.89813    1,773.28286    17.89770
  91    19.70000  3.00000        0.00000   9.33017     79.56830    38.41257    78.48912    1,782.34426    17.80628
  92    19.90000  3.00000        0.00000   8.83436     75.35539    33.26388    73.96942    1,790.39117    17.69730
  93    20.10000  3.00000        0.00000   8.34099     71.16377    28.45676    69.37682    1,797.49229    17.57212
  * </pre>
  *
  * @version $Revision: 1.2 $, $Date: 2004/11/30 22:12:14 $.
  * @author Daren Stotler
  * @see tdfd.TimeDepFusionSolver
  * @see rootsolvers.ODESolved
  * @see rootsolvers.NumRecRungeKutta
  */

/*
 * $Log: TDFTest.java,v $
 * Revision 1.2  2004/11/30 22:12:14  dstotler
 * Extend to add helium ash independent variable, results of additional equations, new modes.  This is a new benchmark with different inputs and output.
 *
 * Revision 1.1  2004/10/07 19:51:58  dstotler
 * Added to repository.
 *
 */
public class TDFTest {

    public static void main(String args[]) {
	int nsteps = 93;
	double tinit = 1.5;
	double tfinal = 20.1;
	double tempinit = 1.0;
	/* 
	 * Ash accumulation test.  Set up the inputs so that ash accumulation
	 * would be noticeable. This was run with helium ash confinement
	 * time (in FusionSolver) set to 15. seconds.  
	 */
	double nheinit = 2.e20;
	TimeDepFusionSolver td = new TimeDepFusionSolver(TimeDepFusionSolver.ADV_ASH_ACCUMULATION, nsteps, tinit, tfinal, tempinit, nheinit);
	/*
	 * Use Will's DataTable to define the time-dependence of density,
	 * Paux, and the B-field.  The maximum values are specified separately
	 * for testing purposes.
	 */
	double max_density = 3.0;
	/*
	 * This waveform keeps the density up at the
	 * end of the run so that the alpha power
	 * drop can't be blamed on a drop in electron density.
	 */
	double[] time_density = {0.0, 0.0, 7.5, 0.5, 8.0, 0.5, 11.0, 1.0,
				 17.5, 1.0, 27.5, 1.0};

	for (int i=1; i<(2*6); i+=2) { 
	    time_density[i] *= max_density;
	}
	DataTable dtDensity = new DataTable(time_density);

	/*
	 * Revised auxiliary heating waveform is intended to be a
	 * sharp burst that heats the plasma to near ignition
	 * over 4 seconds and then turns off.  The magnitude 
	 * was chosen to stay below the beta limit.
	 */
	double max_paux = 60.;
	double[] time_paux = {0.0, 0.0, 7.95, 0.0, 8.05, 1.0, 11.95, 1.0,
			      12.05, 0.0, 27.5, 0.0};
	for (int i=1; i<(2*6); i+=2) {
	    time_paux[i] *= max_paux;
	}
	DataTable dtPaux = new DataTable(time_paux);

	/*
	 * This is the highest magnetic field we allow
	 * the users to choose.  Selected here to get
	 * the plasma to near ignition.
	 */
	double max_bfield = 14.0;
	double[] time_bfield = {0.0, 0.748, 7.5/1.9445, 0.889, 7.5, 1.0, 
			      17.5, 1.0, 25.0, 0.593};
	for (int i=1; i<(2*5); i+=2) {
	    time_bfield[i] *= max_bfield;
	}
	DataTable dtBField = new DataTable(time_bfield);

	td.setInputs(dtBField, dtDensity, dtPaux);

	try {
	    NumRecRungeKutta.driver(td);
	} catch (RootSolverException e) {
	    System.out.println("RungeKutta routine or ODE had a problem: " + e.getMessage());
	}
	while (!td.is_solved()) {
	    // Loop until solved
	}
	DataTable[] results = td.get_results();
	
	double delta_t = (tfinal - tinit) / (double) nsteps;
	System.out.println("  step  t (s)  ne (1.e20 m^-3)  Paux (MW)  T (keV)   W (MJ)    Palpha (MW)  Score  Wheat (MJ)  N_He (1.e20)");
	NumberFormat nf = NumberFormat.getInstance();
	nf.setMinimumFractionDigits(5);
	nf.setMaximumFractionDigits(5);
	double density;
	double Paux;
	double temperature;
	double Wtot;
	double Palpha;
	double Score;
	double Wheat;
	double Nhe;
	for (int i=0; i < nsteps+1; i++) {
	    double t = tinit + delta_t * (double) (i);
	    density = dtDensity.interpolate(t);
	    Paux = dtPaux.interpolate(t);
	    /* 
	     * Convert temperature from degrees Celsius to keV.
	     */
	    temperature = (results[1].interpolate(t)+273.15)/1.1604e7;
	    Wtot = results[2].interpolate(t);
	    Palpha = results[4].interpolate(t);
	    Score = results[0].interpolate(t);
	    Wheat = results[5].interpolate(t);
	    Nhe = results[3].interpolate(t);
	    System.out.println("  " + i + "    " + nf.format(t) + "  "
			       + nf.format(density) + "        "
			       + nf.format(Paux) + "   "
			       + nf.format(temperature) + "     "
			       + nf.format(Wtot) + "    " 
			       + nf.format(Palpha) + "    "
			       + nf.format(Score) + "    "
		               + nf.format(Wheat) + "    "
			       + nf.format(Nhe));
	}
    }
}
