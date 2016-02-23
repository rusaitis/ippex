/**
 * Class    : DoubleJSlider
 * Copyright: 2002 Andreas Gohr, Frank Schubert
 *            Mar 2003: Modified by Arne Morken,
 *                      MindMatters, www.mindmatters.no
 * License  : GPL2 or higher
 * 
 * Info     : This JSlider uses doubles for its values
 */
package swingman;

import javax.swing.*;

/**
 * Imported this class into the <code>swingman</code> package as a 
 * replacement for the <code>MightySlider</code> and subclasses that
 * were used previously.
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:32:04 $.
 */

/*
 * $Log: DoubleJSlider.java,v $
 * Revision 1.1  2004/10/07 20:32:04  dstotler
 * Added to repository.
 *
 *
 */

public class DoubleJSlider extends JSlider {
	private boolean mIsDouble = true;
	private String mDecFormat = "0.00";
	private double mPrecision = 2;
	private String mName = "";

	public boolean isDouble() {
		return mIsDouble;
	}
	public String getDecFormat() {
		return mDecFormat;
	}
	public double getPrecision() {
		return mPrecision;
	}
	public String getName() {
		return mName;
	}
	public void setPrecision(double prec) {
		mPrecision = prec;
	}
	public void setName(String name) {
		mName = name;
	}

	/**
	 * Constructor - initializes with 0.0,100.0,50.0
	 */
	public DoubleJSlider() {
		super();
		setName("");
		setDoubleMinimum(0.0);
		setDoubleMaximum(100.0);
		setDoubleValue(50.0);
	}

	/**
	 * Constructor
	 */
	public DoubleJSlider(
		String name,
		double min,
		double max,
		boolean isDouble) {
		super();
		setName(name);
		setDoubleMinimum(min);
		setDoubleMaximum(max);
		setDoubleValue((min + max) / 2);
		mIsDouble = isDouble;
		if (!mIsDouble)
			mDecFormat = "0";
	}

	/**
	 * returns Maximum in double precision
	 */
	public double getDoubleMaximum() {
		return (getMaximum() / Math.pow(10.0, mPrecision));
	}

	/**
	 * returns Minimum in double precision
	 */
	public double getDoubleMinimum() {
		return (getMinimum() / Math.pow(10.0, mPrecision));
	}

	/**
	 * returns Value in double precision
	 */
	public double getDoubleValue() {
		return (getValue() / Math.pow(10.0, mPrecision));
	}

	/**
	 * sets Maximum in double precision
	 */
	public void setDoubleMaximum(double max) {
		setMaximum((int) (max * Math.pow(10.0, mPrecision)));
	}

	/**
	 * sets Minimum in double precision
	 */
	public void setDoubleMinimum(double min) {
		setMinimum((int) (min * Math.pow(10.0, mPrecision)));
	}

	/**
	 * sets Value in double precision
	 */
	public void setDoubleValue(double val) {
		setValue((int) (val * Math.pow(10.0, mPrecision)));
		setToolTipText(Double.toString(val));
	}

	public void setDoubleMajorTickSpacing(double val) {
		setMajorTickSpacing((int) (val * Math.pow(10.0, mPrecision)));
	}

	public void setDoubleMinorTickSpacing(double val) {
		setMinorTickSpacing((int) (val * Math.pow(10.0, mPrecision)));
	}
}
