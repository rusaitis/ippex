package graph;

import java.awt.Font;
import java.awt.Graphics;

/*
**************************************************************************
**
**    Class  TextState
**
**************************************************************************
**    Copyright (C) 1996 Leigh Brookshaw
**
**    This program is free software; you can redistribute it and/or modify
**    it under the terms of the GNU General Public License as published by
**    the Free Software Foundation; either version 2 of the License, or
**    (at your option) any later version.
**
**    This program is distributed in the hope that it will be useful,
**    but WITHOUT ANY WARRANTY; without even the implied warranty of
**    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**    GNU General Public License for more details.
**
**    You should have received a copy of the GNU General Public License
**    along with this program; if not, write to the Free Software
**    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
**************************************************************************
**
**    This class was split off from TextLine.java.
**
*************************************************************************/

/**
 * A structure class used exclusively with the TextLine class.
 * When the Text changes state (new font, new color, new offset)
 * then this class holds the information plus the substring
 * that the state pertains to.
 *
 * @version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:49 $.
 * @author Leigh Brookshaw, Daren Stotler
 */

/*
 * $Log: TextState.java,v $
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.11 11-22-98 Split off from Brookshaw's TextLine.java, V. 1.10.
 */

public class TextState extends Object { 
      Font f         = null;
      StringBuffer s = null;
      int x          = 0;
      int y          = 0;

      
      public TextState() {
              s = new StringBuffer();
	    }


      public TextState copyAll() {
             TextState tmp = copyState();
             if(s.length()==0) return tmp;
             for(int i=0; i<s.length(); i++) { tmp.s.append(s.charAt(i)); }
             return tmp;
	   }


      public TextState copyState() {
             TextState tmp = new TextState();
             tmp.f = f;
             tmp.x = x;
             tmp.y = y;
             return tmp;
	   }


      public String toString() {
             return s.toString();
	   }


      public boolean isEmpty() {
           return (s.length() == 0);
	 }

      public int getWidth(Graphics g) {

           if(g == null || f == null || s.length()==0 ) return 0;

           return g.getFontMetrics(f).stringWidth(s.toString());
      }

      public int getHeight(Graphics g) {
           if(g == null || f == null ) return 0;
           
           return g.getFontMetrics(f).getHeight();
	 }
      public int getAscent(Graphics g) {
           if(g == null || f == null ) return 0;
           
           return g.getFontMetrics(f).getAscent();
	 }
      public int getDescent(Graphics g) {
           if(g == null || f == null ) return 0;
           
           return g.getFontMetrics(f).getDescent();
	 }
      public int getMaxAscent(Graphics g) {
           if(g == null || f == null ) return 0;
           
           return g.getFontMetrics(f).getMaxAscent();
	 }
      public int getMaxDescent(Graphics g) {
           if(g == null || f == null ) return 0;
           
           return g.getFontMetrics(f).getMaxDescent();
	 }
      public int getLeading(Graphics g) {
           if(g == null || f == null ) return 0;
           
           return g.getFontMetrics(f).getLeading();
	 }
}
