/*
 * class MightyRadioButtonGroup
 * 
 * Copyright (c) 1995 Besiex Software. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that credit is this copyright notice
 * appears in all copies.
 */
 
package dpscomponents.button;

import java.awt.*;
import java.applet.*;
import java.util.*;
/**    
 * MightyRadioButtonGroup is a button group for use with any MightyButton of type
 * RADIO.<p>  
 *
 * Use this group just like you do Java's CheckButtonGroup.  Create an instance of
 * a MightyRadioButtonGroup, then add to it the buttons you want in the group.
 * It's as simple as falling down.<p>
 *
 * @version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:48 $.
 * <dl>
 * 	<dt> <b>Original author's Version:</b>
 * 	<dd> 1.50,  29 December 1995
 * </dl>
 * 
 * <dl>
 *		<dt> <b>Authors:</b>
 *		<dd> <A HREF="http://amber.wpi.edu/~thethe">Benjamin "Quincy" Cabell V</A>,
 *		<dd> <A HREF="http://amber.wpi.edu/~thethe/Documents/Besiex/Java/index.html">Besiex Software</A>.
 *	</dl>
 */

/*
 * $Log: MightyRadioButtonGroup.java,v $
 * Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
 * Import SSFD into CVS
 *
 */

public class MightyRadioButtonGroup {

	Vector buttons = new Vector(10,10);

/**
 * Construct a MightyRadioButtonGroup.
 */

	public MightyRadioButtonGroup() {
	}
	
/**
 * Adds a MightyButton to this radio button group.<p>
 *
 * @param theButton The MightyButton to add to this radio button group.
 */

	public void addButton (MightyButton theButton) {
		buttons.addElement(theButton);

		MightyButton c;
		
		for (int i=0;i< buttons.size();i++) {
			c = (MightyButton) buttons.elementAt(i);
			c.clearAllButtonForRadioPress();
						
			for (int j=0;j < buttons.size();j++)
				c.addButtonForRadioPress((MightyButton) buttons.elementAt(j));
		}
	}	

/**
 * Removes a MightyButton to this radio button group.<p>
 *
 * @param theButton The MightyButton to remove from this radio button group.
 */

   public void removeButton(MightyButton theButton) {
		int i = buttons.indexOf(theButton);
		if (i != -1) {
			buttons.removeElementAt( i );
		}
   }
}
