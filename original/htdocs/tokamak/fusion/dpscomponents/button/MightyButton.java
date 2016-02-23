/* 
 * class MightyButton
 *
 * Copyright (c) 1996 Besiex Software. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that credit is this copyright notice
 * appears in all copies.  Blah, blah, blah. Blah, blah.
 */
package dpscomponents.button;
 
import java.awt.*;
import java.applet.*;
import java.util.*;
import java.awt.event.*;

/**    
 The MightyButton class is used to create various types of buttons, 
 MOMENTARY, TOGGLE, PERMANENT, or RADIO.  The appearance of the MightyButton
 in its states (PRESSED, NOT_PRESSED, and DISABLED) is determined by the
 <code>buttonName</code> passed to the constructor (this name is used to
 load in the 3 GIFs).  See constructor for details. <p>
 
 Using the MightyRadioButtonGroup class supplied, any MightyButton can be a 
 radio button. <p>
 
 Button Types:<br>
 <UL>
 	<LI>A MOMENTARY button will stay PRESSED only as long as the mouse is down over it.
 	<LI>A TOGGLE button will become PRESSED if NOT_PRESSED, and NOT_PRESSED if PRESSED when the mouse
 	button is pressed over the MightyButton.
 	<LI>A PERMANENT button will stay PRESSED forever, once PRESSED.
 	<LI>A RADIO button is used with the MightyRadioButtonGroup for radio button functionality.
 </UL><P>
 
 Behavior is added to MightyButton by subclassing it and overriding the action method.

@version $Revision: 1.2 $, $Date: 2004/10/07 19:58:25 $.
@author <A HREF="http://amber.wpi.edu/~thethe">Benjamin "Quincy" Cabell V</A>
@author <A HREF="http://amber.wpi.edu/~thethe/Documents/Besiex/Java/index.html">Besiex Software</A>
@author built from ImageButton by <A HREF="http://www.cs.brown.edu/people/amd/">Adam Doppelt</A>
*/
 
/*
$Log: MightyButton.java,v $
Revision 1.2  2004/10/07 19:58:25  dstotler
Changes to javadoc comment.

Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
Import SSFD into CVS


<p>
<b>Changes prior to insertion in CVS: </b>

<ul>
    <li> Updated event methods to Java 1.1 event model. Significant consequence is that the "action"
         method now resides solely in the parent container.
 	<li> Automatically detects whether the MightyButton is in a stand-alone application or an applet and accesses the images
		accordingly.
</ul>
 
 
 */

public class MightyButton extends Canvas implements MouseListener {

	// The applet in which this button will appear (this is needed for getImage, etc.)	
	static Applet appletHandle=null;

	// The button types
	public final static int MOMENTARY=0;		// Button only stays pushed in while mouse
															// is down.
	public final static int RADIO=1;				// Button can be placed into a radio button
															// group.
	public final static int PERMANENT=2;		// Button will only change states once.
	public final static int TOGGLE=3;			// PUSH ON / PUSH OFF

	// This button's type
	int buttonType=0;

	// The button states
	public final static int PRESSED=0;
	public final static int NOT_PRESSED=1;
	public final static int DISABLED=2;

	// This button's state
	int buttonState = NOT_PRESSED;

	// This button's images
	Image imageOfNotPressed;
	Image imageOfPressed;
	Image imageOfDisabled;
	Image imageOfCurrentState;

	boolean imagesLoaded=false;
	
	//This button's boolean states 
	boolean actionCanBeCalled=true;
	boolean hasBeenPressed=false;
	boolean buttonPressedOnLastMouseDown=false;
	
	// The button's text related variables
	Font textFont;
	String buttonText;
	Color textColor;
	int xOffset;
	int yOffset;
	
	// The container that holds the other buttons in this button's radio group
	Vector buttonsForRadioPress = new Vector(10,10);

	// The name that loadImage() uses to get this button's images
	String buttonName;


/**
 * Construct an image button. An image button is composed of three
 * images, one for each possible state of the button. If null is
 * passed for pressed and/or disabled, those states will draw using the
 * normal image.  Many thanks to Bobby Arnold (rharnold@csupomona.edu) for
 * his recommendation on getting the Applet's handle from within a component. <p>
 *
 * @param theButtonName The file name of the button, without the _p.gif,_n.gif,_d.gif.  
 * Here's how it works:  I tack on "_p.gif" for the image of a pressed button,
 * "_n.gif" for the image of a not-pressed button, and "_d.gif" for the image of a 
 * disabled button.  So, if you pass in "iAmAButton" as theButtonName, this
 * code will load in iAmAButton_p.gif, iAmAButton_n.gif, and iAmAButton_d.gif.
 * Get it?  It was meant to make things easy.  You also have to specify the location of the
 * button image file here.  In the case of a stand-alone app this must be the absolute path
 * to the file, in the case of an applet, this is relative to the codebase.<p>
 *
 * @param theButtonText When the button is displayed, you can have it displayed with
 * text on top of it (so people know what the button does).  Text color, font, and
 * size can all be set (default color is black, default font is Helvetica Bold 12). <p>
 *
 * @param theButtonType What kind of button is this?  MOMENTARY, TOGGLE, PERMANENT, or
 * RADIO.
 * */

	public MightyButton (String theButtonName,String theButtonText,
			int theButtonType) {
		this(null,theButtonName,theButtonText,theButtonType);
	}


/**
 * Constructor for a MOMENTARY MightyButton with no text put onto the button. <P>
 *
 * @param theApplet A handle to the Applet; This is required for using 
 * getDocumentBase(), getCodeBase(), and getImage(URL, String).  There should be,
 * nay, must be a way to get the Applet handle without having to pass it in,
 * but I couldn't figure one out.  Anyone know if it can be done (and how)?<P>
 *
 */

	public MightyButton (Applet theApplet,String theButtonName) {
		this(theApplet,theButtonName,null,MOMENTARY);
	}


/**
 * Construct an image button.  This constructor is included only
 * for backward compatability.  The handle to the Applet need not be passed
 * to the constructor any more. <p>
 **/
	
	public MightyButton (Applet theApplet,String theButtonName,String theButtonText,
			int theButtonType) {

		buttonName = theButtonName;
		appletHandle = theApplet;
		
		SetButtonType(theButtonType);
		
		SetFont(null);
		SetTextColor(null);
		SetButtonText(theButtonText);
		
		addMouseListener(this);
	}


/**
 * Returns the type of this button, as MOMENTARY, PERMANENT, TOGGLE, or RADIO.
 */
	public int GetButtonType () {
		return (buttonType);
	}

/**
 * Sets the type of this button, to MOMENTARY, PERMANENT, TOGGLE, or RADIO.
 *
 * @param theType The button type to make this button, MOMENTARY, PERMANENT, TOGGLE, or RADIO
 */
	public void SetButtonType (int theType) {
		buttonType = theType;
	}

/**
 * INTERNAL USE ONLY.<BR>
 * Loads in the images for this button using the buttonName convention.
 */
	void loadImages() {	
		MediaTracker tracker = new MediaTracker(this);

		String fileNameNotPressed = new String (buttonName+"_n.gif");
		String fileNamePressed = new String (buttonName+"_p.gif");
		String fileNameDisabled = new String (buttonName+"_d.gif");

		if (appletHandle == null) {		
			Toolkit tk = getToolkit();

			imageOfNotPressed = tk.getImage(fileNameNotPressed);
			imageOfPressed = tk.getImage(fileNamePressed);
			imageOfDisabled = tk.getImage(fileNameDisabled);
		} else {
			imageOfNotPressed = appletHandle.getImage(appletHandle.getCodeBase(),
					fileNameNotPressed);
			imageOfPressed = appletHandle.getImage(appletHandle.getCodeBase(),
					fileNamePressed);
			imageOfDisabled = appletHandle.getImage(appletHandle.getCodeBase(),
					fileNameDisabled);
		}
		
		tracker.addImage(imageOfNotPressed,0);
		tracker.addImage(imageOfPressed,1);
		tracker.addImage(imageOfDisabled,2);
		tracker.checkAll(true);

		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
			System.out.println("InterruptedException waiting for images.");
		}

		if (tracker.checkAll()) {
			for (int i=0;i<3;i++)
				if (tracker.isErrorID(i))
					System.out.println("Error getting button images!");
		}
		imagesLoaded = true;
	}

/**
 * This method implements the button's type.  When the button is released, this
 * method gets called for changing the button's state (if appropriate to the 
 * button's type). INTERNAL USE ONLY.
 */

	public void buttonReact() {

		switch (buttonType) {

			case (MOMENTARY)	:		SetState(NOT_PRESSED);
											break;
		
			case (RADIO) 		:		MightyButton c;
									
											for (int i=0;i<buttonsForRadioPress.size();i++) {
												c = (MightyButton) buttonsForRadioPress.elementAt(i);
												c.SetState(NOT_PRESSED);
											}

											SetState(PRESSED);
											break;
											
			case (PERMANENT) 	: 		break;
	
			case (TOGGLE)		:		if (!buttonPressedOnLastMouseDown) {
												SetState(NOT_PRESSED);
											}
											break;
		}
	}

/**
 * This method gets called by the MightyRadioButtonGroup when a radio button is added
 * to the group this button is in.  Each time a new button is added to the group,
 * the old list is cleared, then this method gets called for each button in the 
 * group. INTERNAL USE ONLY. <p>
 *
 * @param theButton The MightyButton that gets added to this button's list of 
 * radio buttons to unpress when this button is pressed.
 */

	public void addButtonForRadioPress(MightyButton theButton) {
		buttonsForRadioPress.addElement(theButton);
	}

/**
 * This method gets called by the MightyRadioButtonGroup when a radio button is added
 * to the group this button is in, to clear the current list so the new list can be
 * added. INTERNAL USE ONLY.
 */

	public void clearAllButtonForRadioPress() {
		buttonsForRadioPress.removeAllElements();
	}
/**
 * This method is called to recalculate the necessary dimensions of the button,
 * based upon the button image's size, then resize the button to this size.
 * The reason this code is called add behavior to the button. 
 * INTERNAL USE ONLY.
 */

	public void autoResize() {

		int width = imageOfNotPressed.getWidth(this);
		int height = imageOfNotPressed.getHeight(this);

		if (imageOfPressed != null) {
		    width = Math.max(width, imageOfPressed.getWidth(this));
		    height = Math.max(height, imageOfPressed.getHeight(this));	    
		}

		if (imageOfDisabled != null) {
		    width = Math.max(width, imageOfDisabled.getWidth(this));
		    height = Math.max(height, imageOfDisabled.getHeight(this));
		}

		setSize(width, height);
	}

/**
 * The preferred dimension is in fact the same as the minimum size.
 */
	public Dimension getPreferredSize () {
		return (getMinimumSize());	
	}

/**
 * The minimum size is the size necessary to display the entire image that makes 
 * up the button.
 */
	public Dimension getMinimumSize () {

		if (!imagesLoaded) {
			if (appletHandle == null)
				appletHandle = GetAppletHandle();

			loadImages();
			SetImages(imageOfNotPressed,imageOfPressed,imageOfDisabled);
			setVisible(true);
			autoResize();
			calculateTextOffsets();
		}
		else {
			autoResize();
		}
		return (new Dimension(getSize().width,getSize().height));
	}

/**
 * This method returns the current state of the button using the enumerated button
 * states: PRESSED, NOT_PRESSED, and DISABLED.
 */

	public int GetState() {
		return (buttonState);
	}


/**
 * This method sets the state of the button using the enumerated button states:
 * PRESSED, NOT_PRESSED, and DISABLED. <p>
 * @param theState The state to which to set the button.
 */

	public void SetState(int theState) {
		switch (theState) {
			case (PRESSED)		:				if (buttonState==NOT_PRESSED) {
														if (imageOfPressed != null) {
															buttonState = PRESSED;
															imageOfCurrentState = imageOfPressed;
															instant();
														}
													}
													break;

			case (NOT_PRESSED)	:				if (buttonState==PRESSED) { 
														if (imageOfNotPressed != null) {
															buttonState = NOT_PRESSED;
															imageOfCurrentState = imageOfNotPressed;
															instant();
														}
													}
													break;

			case (DISABLED)	:				if (imageOfDisabled != null) {
														buttonState = DISABLED;
														imageOfCurrentState = imageOfDisabled;
														instant();
													}
													break;
		}
	}


/**
 * This method sets the text that is placed on top of the button's image. <p>
 * @param theButtonText The text to display in the button.
 */

	public void SetButtonText (String theButtonText) {
		buttonText = theButtonText;

		if (buttonText != null)
			calculateTextOffsets();
	}
	
/**
 * This method sets font that the aforementioned button text is drawn in. <p>
 * @param theFont The font to use for the button's text.
 */

	public void SetFont (Font theFont) {
		if (theFont == null) 
			textFont = new Font("Helvetica", Font.BOLD, 12);
		else
			textFont = theFont;

		if (buttonText != null)
			calculateTextOffsets();
	}

/**
 * This method sets the color of the text that the very much aforementioned button 
 * text is drawn in. <p>
 * @param theColor The color to use for the button's text.
 */
	
	public void SetTextColor (Color theColor) {
		if (theColor == null)
			textColor = Color.black;
		else
			textColor = theColor;
	}
	
/**
 * This method sets the images that will be used for the button's states.  This could
 * be used if all of a sudden you wanted to change the appearance of a button, after
 * it had already been instantiated. <p>
 * @param theImageOfNotPressed The image for the button when button's state is NOT_PRESSED<P>
 * @param theImageOfPressed The image for the button when button's state is PRESSED<P>
 * @param theImageOfDisabled The imaged for the button when button's state is DISABLED
 */
	
	public void SetImages (Image theImageOfNotPressed, Image theImageOfPressed, 
			Image theImageOfDisabled) {
		imageOfNotPressed = theImageOfNotPressed;
		imageOfPressed = theImageOfPressed;
		imageOfDisabled = theImageOfDisabled;
		imageOfCurrentState = imageOfNotPressed;
	}

/**
 * Mouse down causes the button to be pressed if the button is enabled. <p>
 * @param evt The MouseEvent that has occured. INTERNAL USE ONLY. <p>
 */

	public void mousePressed(MouseEvent evt) {
						  

		if (((buttonType == PERMANENT) && (!hasBeenPressed)) 
				|| ((buttonType != PERMANENT) && (buttonState != PRESSED) 
				&& (buttonState != DISABLED))) {

			SetState(PRESSED);
			
			buttonPressedOnLastMouseDown=true;
			hasBeenPressed = true;
		}
		else {
			buttonPressedOnLastMouseDown = false;
		}
	}
/**
 * When the mouse button goes up, the button reacts. 
 * Consistent with the Java 1.1 event model, the "action" call has
 * been moved to the parent container. INTERNAL USE ONLY. <p>
 * @param evt The MouseEvent that has occured. <p>
 */
	public void mouseReleased(MouseEvent evt) {
		buttonReact();
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
 * This method tries to instantly get a paint.
 */

	void instant() {
		Graphics onscreen = getGraphics();
		if (onscreen != null)
		    paint(onscreen);
	}

/**
 * This method calculates the offset for placing text centered within the button,
 * given the current font and button size. INTERNAL USE ONLY. <p>
 */
 
 	public void calculateTextOffsets () {
		if ((getSize().width != 0) && (getSize().height != 0)) {
			FontMetrics textFontMetrics = getFontMetrics(textFont);
			setFont(textFont);
     	
			xOffset = (int) ((getSize().width - textFontMetrics.stringWidth(buttonText))/2); 
			yOffset = getSize().height - (int) ((getSize().height - textFontMetrics.getAscent()
					+textFontMetrics.getDescent())/2);
	
			// There seems to be a bug here...  the above padding line should be equal to:
			// padding = (int) ((box.height - textFontMetrics.getAscent())/2);
		}
	}


/**
 * This method returns the handle of the Applet in which this button appears.
 * Essentially, the process is fairly cool.  Start at this button's instance,
 * get the parent. Check the name of the superclass.  If the name of the superclass
 * is "java.awt.Applet" then the parent that was just gotten is the instance of the
 * Applet.  Cast this Applet and return.  If the superclass' name was not
 * java.awt.Applet, get the parent's parent and repeat the process of checking the
 * superclass name.  Continue this process until either the parent is null (in which
 * case there is no parent Applet and null is returned), or the Applet instance is
 * found.  Many thanks to Bobby Arnold (rharnold@csupomona.edu) for his recommendation
 * on getting to the Applet handle from within a Component. INTERNAL USE ONLY.
 */

	Applet GetAppletHandle () {
		Component parentAsComponent = this;
	
		String nameOfClass;
		String superclassName;
		Class superclass;
		
		while (parentAsComponent != null) {
			parentAsComponent = (Component) parentAsComponent.getParent();

			if (parentAsComponent != null) {
				nameOfClass = parentAsComponent.getClass().getName();
				superclass = parentAsComponent.getClass().getSuperclass();
				superclassName = superclass.getName();
			
				if (superclassName.equals("java.applet.Applet"))
					return ((Applet) parentAsComponent);
			}
		}			

		return null;
	}

    
/**
 * This method paints the whole kit-and-caboodle. INTERNAL USE ONLY. <p>
 * @param graphics The graphics context of this button.
 */

	public void paint(Graphics graphics) {
		graphics.drawImage(imageOfCurrentState, 0, 0, this);
		
		// Draw the Text
		if (buttonText != null)
			{
				graphics.setColor (textColor); 
				graphics.drawString (buttonText, xOffset, yOffset);
			}
	}

/**
 * This method is of no great importance.  So little in importance, I won't even
 * bother telling you what it doesn't do;  Because I live in America, a land of
 * passion, liberty, crime, degredation, aluminum, and the finest politicians money
 * can buy.  I love the idea of this country.  It is the implementation that is 
 * beginning to worry me. <p>
 */

	public boolean imageUpdate(Image image, int infoflags, int x, int y,
			int width, int height) {
		return true;
	}

}

/**    
 * MightyRadioButtonGroup is a button group for use with any MightyButton of type
 * RADIO. <p>  
 *
 * Use this group just like you do Java's CheckButtonGroup.  Create an instance of
 * a MightyRadioButtonGroup, then add to it the buttons you want in the group.
 * It's as simple as falling down. <p>
 *
 * <dl>
 * 	<dt> <b>Version:</b>
 * 	<dd> 1.25,  29 December 1995
 * </dl>
 * 
 * <dl>
 *		<dt> <b>Authors:</b>
 *		<dd> <A HREF="http://amber.wpi.edu/~thethe">Benjamin "Quincy" Cabell V</A>,
 *		<dd> <A HREF="http://amber.wpi.edu/~thethe/Documents/Besiex/Java/index.html">Besiex Software</A>.
 *	</dl>
 */

