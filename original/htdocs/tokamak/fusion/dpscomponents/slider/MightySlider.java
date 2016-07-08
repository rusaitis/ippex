/*
 * class MightySlider
 */
package dpscomponents.slider;

import java.awt.*;
import java.awt.event.*;

/**
 * A Slider is a widget that varies between a minimum and a maximum
 * value. The user can drag a "thumb" to change the current value. As
 * the slider is dragged, Motion() is called. When the slider is
 * released, Release() is called. Override these two methods to give
 * the slider behavior.
 *
 * @version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:48 $.
 *		<dt> <b>Authors:</b>
 *		<dd> <A HREF="http://www.cs.brown.edu/people/amd/">Adam Doppelt</A>,
 *		<dd> with some significant modifications by
 *		<dd> <A HREF="http://amber.wpi.edu/~thethe">Benjamin "Quincy" Cabell V</A>,
 *		<dd> <A HREF="http://amber.wpi.edu/~thethe/Documents/Besiex/Java/index.html">Besiex Software</A>.
 *      <dd> <A HREF="http://w3.pppl.gov/~dstotler/">Daren Stotler</A>.
 *	</dl>
 */

/*
 * $Log: MightySlider.java,v $
 * Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
 * Import SSFD into CVS
 *
 * <dl>
 * 	<dt> <b>Previous Version:</b>
 * 	<dd> 2.10,  5 November 1998
 * </dl>
 * 
 * <p>
 * <b>Changes prior to insertion in CVS:</b>
 *
 * <ul>
 *   <li> Make textHeight_ non-static and non-final so it can be reset.
 *   <li> Updated event methods to Java 1.1 event model. Significant consequence is that the "action"
 *        method now resides solely in the parent container.
 *  </ul>
 * <dl>
 */



public class MightySlider extends Canvas implements MouseListener, MouseMotionListener {
/*
   Changed private to protected so MightySlider can be subclassed.
*/
	protected final static int THUMB_SIZE = 14;
	protected final static int BUFFER = 2;

	protected int textHeight_ = 18;
	protected final static int TEXT_BUFFER = 3;
    
	protected final static int DEFAULT_WIDTH = 100;
	protected final static int DEFAULT_HEIGHT = 15;

	protected final static int MIN_WIDTH = 2 * (THUMB_SIZE + BUFFER + 1);
	protected final static int MIN_HEIGHT = 2 * (BUFFER + 1);

	protected final static double DEFAULT_DOUBLE_MIN = 1.0d;
	protected final static double DEFAULT_DOUBLE_MAX = 100.0d;
	
	int pixel_;
	int pixelMin_, pixelMax_;
	
	int resizeFixWidth=0;
	int resizeFixHeight=0;

	Color backgroundColor_, thumbColor_, barColor_, slashColor_, textColor_;
	Font font_;

	double dmin_,dmax_,dvalue_;
	boolean doubleMode=false;

	
/**
 * Constructs a slider.
 */
   public MightySlider () {
		dmin_ = DEFAULT_DOUBLE_MIN;
		dmax_ = DEFAULT_DOUBLE_MAX;

		setSize(0,0);
		font_ = new Font("TimesRoman", Font.PLAIN, 12);
		backgroundColor_ = Color.lightGray;
		thumbColor_ = Color.lightGray;
		barColor_ = Color.lightGray.darker();
		slashColor_ = Color.black;
		textColor_ = Color.black;
		SetValue(dmin_);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void SetDoubleMode(boolean theMode) {
		doubleMode = theMode;
	}


/**
 * The preferred size for this slider component is the same as its minimum size.
 */
 
	public Dimension getPreferredSize() {
		return (getMinimumSize());
	}
	
/**
 * The minimum size of this slider is determined by any resizes the user has called
 * on this component between the time it was instantiated and the time at which it
 * is added to the layout manager (which calls this getMinimumSize() method), or if
 * the user has not resized this component, the default height and width are used.
 * The programmer should be careful to ensure that they do not resize this component
 * to a size smaller than is needed to fit the display value (since no checking is 
 * done here to prevent that case).
 */
 
	public Dimension getMinimumSize() {
		if ((getSize().width == 0) || (getSize().height == 0)) {
			setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT + textHeight_);
		}
		
		return (new Dimension(getSize().width,getSize().height));
	}

/**
 * This method is called when the "thumb" of the slider is dragged by
 * the user. Must be overridden to give the slider some behavior.
 */
    public void Motion () { 
	 
	 }

/**
 * This method is called when the "thumb" of the slider is released
 * after being dragged. Must be overridden to give the slider some
 * behavior.
 */
    public void Release () {
	 
	 }
    
/**
 * Sets the maximum value for the slider.
 * @param num The new maximum (passed as a double).
 */
	public void SetMaximum (double num) {
		dmax_ = num;
		if (dmax_ < dmin_) {
		    double t = dmin_;
		    dmin_ = dmax_;
		    dmax_ = t;
		}
		SetValue(dmin_);
	}
   
/**
 * Sets the maximum value for the slider.
 * @param num The new maximum (passed as an integer).
 */
	public void SetMaximum (int num) {
		SetMaximum((double) num);
	}
	
/**
 * Sets the minimum value for the slider.
 * @param num The new minimum (passed as a double).
 */
	public void SetMinimum (double num) {
		dmin_ = num;
		if (dmax_ < dmin_) {
			double t = dmin_;
			dmin_ = dmax_;
			dmax_ = t;
		}
		SetValue(dmin_);
	}

/**
 * Sets the minimum value for the slider.
 * @param num The new minimum (passed as an integer).
 */

	public void SetMinimum (int num) {
		SetMinimum ((double) num);
	}
    
/**
 * Sets the current value for the slider. The thumb will move to
 * reflect the new setting.
 * @param num The new setting for the slider (passed as a double).
 */
	
	public void SetValue (double num) {
		dvalue_ = num;
	
		if (dvalue_ < dmin_)
			dvalue_ = dmin_;
		else 
			if (dvalue_ > dmax_)
				dvalue_ = dmax_;
	
		if (dvalue_ != dmin_)
			pixel_ = (int)(Math.round(Math.abs((double)(dvalue_ - dmin_) /
					(double)(dmax_ - dmin_)) *
					(double)(pixelMax_ - pixelMin_)) +
					pixelMin_);
		else
			pixel_ = pixelMin_;

		repaint();
	}
    
/**
 * Sets the current value for the slider. The thumb will move to
 * reflect the new setting.
 * @param num The new setting for the slider (passed as an integer).
 */

	public void SetValue (int num) {
		SetValue((double) num);
	}
	
	
/**
 * Sets the height of the slider. This is the height of the entire
 * slider canvas, including space reserved for displaying the
 * current value.
 * @param num The new height.
 */
	public void SetHeight (int num) {
		int numGranted;		
		if (num < MIN_HEIGHT + textHeight_)
			numGranted = MIN_HEIGHT + textHeight_;
		else
			numGranted = num;

		resizeFixHeight = numGranted;
		
		setSize(getSize().width, numGranted);
		repaint();
	}
    
/**
 * Sets the width of the slider. This is the width of the actual
 * slider box.
 * @param num The new width.
 */
    public void SetWidth (int num) {
	if (num < MIN_WIDTH)
	    num = MIN_WIDTH;
	setSize(num, getSize().height);

	resizeFixWidth = num;
	
	repaint();	
    }
    
/**
 * Returns the current value for the slider.
 * @return The current value for the slider.
 */
	public double GetValue () {
		return dvalue_;
	}

/**
 * Sets the background color for the slider. The "background" is the
 * area outside of the bar.
 * @param color The new background color.
 */
	public void SetBackgroundColor(Color color) {
		backgroundColor_ = color;
		repaint();
	}

/**
 * Sets the color for the slider's thumb. The "thumb" is the box that
 * the user can slide back and forth.
 * @param color The new thumb color.
 */
	public void SetThumbColor(Color color) {
		thumbColor_ = color;
		repaint();
	}

/**
 * Sets the color for the slider's bar. The "bar" is the rectangle
 * that the thumb slides around in.
 * @param color The new bar color.
 */
	public void SetBarColor (Color color) {
		barColor_ = color;
		repaint();
	}

/**
 * Sets the slash color for the slider. The "slash" is the little
 * vertical line on the thumb.
 * @param color The new slash color.
 */
	public void SetSlashColor(Color color) {
		slashColor_ = color;
		repaint();
	}

/**
 * Sets the color for the slider`s text.
 * @param color The new text color.
 */
	public void SetTextColor(Color color) {
		textColor_ = color;
		repaint();
	}

/**
 * Sets the font for the slider`s text.
 * @param font The new font.
 */
	public void SetFont(Font font) {
		font_ = font;
		repaint();
	}
    
/**
 * An internal method used to handle repaint events.
 */
	public void paint(Graphics g) {
		int width = getSize().width;	
		int height = getSize().height;

		g.setColor(backgroundColor_);
		g.fillRect(0, 0, width, textHeight_);

		g.setColor(barColor_);
		g.fill3DRect(0, textHeight_,
		     width, height - textHeight_, false);

		g.setColor(thumbColor_);	
		g.fill3DRect(pixel_ - THUMB_SIZE, textHeight_ + BUFFER,
			     THUMB_SIZE * 2 + 1, height - 2 * BUFFER - textHeight_,
			     true);
	
		g.setColor(slashColor_);
		g.drawLine(pixel_, textHeight_ + BUFFER + 1,
			   pixel_, height - 2 * BUFFER);

		g.setColor(textColor_);
		g.setFont(font_);		

		String str;

		if (doubleMode)
			str = String.valueOf(dvalue_); //new Double(dvalue_);
		else
			str = String.valueOf((int) dvalue_);
		
		g.drawString(str, pixel_ -
			     (int)(getFontMetrics(font_).stringWidth(str) / 2),
			     textHeight_ - TEXT_BUFFER);
	}

/**
 * An internal method used to handle mouse sliding events.
 */
	void HandleMouse(int x) {
		double percent;
		int width = getSize().width;
		pixel_ = Math.max(x, pixelMin_);
		pixel_ = Math.min(pixel_, pixelMax_);

		if (pixel_ != pixelMin_)
		    percent = (((double)pixel_ - pixelMin_) / (pixelMax_ - pixelMin_));
		else
			percent = 0;
	
		if (doubleMode)
			dvalue_ = percent * (dmax_ - dmin_) + dmin_;
		else
			dvalue_ = (Math.round(percent * (double)(dmax_ - dmin_))) + dmin_;
	
		paint(getGraphics());
	}
    
/**
 * Here are the MouseListener methods. Only two are nontrivial.
 * An internal method used to handle mouse down events.
 */
	    public void mousePressed(MouseEvent evt) {
		HandleMouse(evt.getX());
		Motion();
	}
/**
 * An internal method used to handle mouse up events.
 */
	    public void mouseReleased(MouseEvent evt) {
		HandleMouse(evt.getX());
		Release();
	}
	
    public void mouseClicked(MouseEvent evt)
	{
	}
	public void mouseEntered(MouseEvent evt)
	{
	}
	public void mouseExited(MouseEvent evt)
	{
	}

/**
 * And the MouseMotionListener methods. Only need the drag one.
 * An internal method used to handle mouse drag events.
 */
	    public void mouseDragged(MouseEvent evt) {
		HandleMouse(evt.getX());
		Motion();	
	}
	public void mouseMoved(MouseEvent evt) {
	}

/**
 * An internal method used to handle resizing.
 */
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		pixelMin_ = THUMB_SIZE + BUFFER;
		pixelMax_ = width - THUMB_SIZE - BUFFER - 1;
		if (dvalue_ != dmin_)
			pixel_ = (int)(Math.round(Math.abs((double)(dvalue_ - dmin_) /
						(double)(dmax_ - dmin_)) *
						(double)(pixelMax_ - pixelMin_)) +
					   pixelMin_);
		else
			pixel_ = pixelMin_;
	}
}

