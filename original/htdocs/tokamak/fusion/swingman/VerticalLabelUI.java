/*
 * class VerticalLabelUI
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
package swingman;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.geom.*;

/**
 * Draws a vertical label by applying an AffineTransform on a JLabel.  Will
 * says this code was written "elsewhere".  This is part of the 
 * <code>swingman</code> package, a swing-based replacement of 
 * Brookshaw's <code>graph</code> package used previously.
 *
 * @version $Revision: 1.1 $, $Date: 2004/10/07 20:34:26 $.
 * @author Will Fisher and others
 * @see DataTable
 */

/*
 * $Log: VerticalLabelUI.java,v $
 * Revision 1.1  2004/10/07 20:34:26  dstotler
 * Added to repository.
 *
 *
 */

public class VerticalLabelUI extends BasicLabelUI {

	public Dimension getPreferredSize(JComponent c) {
		Dimension dim = super.getPreferredSize(c);
		return (new Dimension(dim.height, dim.width));
	}

	private static Rectangle paintIconR = new Rectangle();
	private static Rectangle paintTextR = new Rectangle();
	private static Rectangle paintViewR = new Rectangle();
	private static Insets paintViewInsets = new Insets(0, 0, 0, 0);
    /**
     * Overrides the parent class paint method, implementing the desired
     * rotation in the process.  The component argument is needed to 
     * determine sizes.
     * @param g the graphics context in which the painting is done
     * @param c the component to be repainted
     */
	public void paint(Graphics g, JComponent c) {

		JLabel label = (JLabel)c;
		String text = label.getText();

		FontMetrics fm = g.getFontMetrics();
		paintViewInsets = c.getInsets(paintViewInsets);

		paintViewR.x = paintViewInsets.left;
		paintViewR.y = paintViewInsets.top;

		//use inverted height and width
		paintViewR.height =
			c.getWidth() - (paintViewInsets.left + paintViewInsets.right);
		paintViewR.width =
			c.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

		paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
		paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

		String clippedText =
			layoutCL(label, fm, text, null, paintViewR, paintIconR, paintTextR);

		Graphics2D g2 = (Graphics2D)g;
		AffineTransform tr = g2.getTransform();

		//rotate
		g2.rotate(- (Math.PI / 2));
		g2.translate(- (c.getHeight()), 0);

		if (text != null) {
			int textX = paintTextR.x;
			int textY = paintTextR.y + fm.getAscent();

			if (label.isEnabled()) {
				paintEnabledText(label, g, clippedText, textX, textY);
			} else {
				paintDisabledText(label, g, clippedText, textX, textY);
			}
		}

		g2.setTransform(tr);
	}
}
