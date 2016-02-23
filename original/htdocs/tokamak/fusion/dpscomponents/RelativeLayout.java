
package dpscomponents;

import java.awt.* ;
import java.util.* ;

/*
Title RelativeLayout

$Log: RelativeLayout.java,v $
Revision 1.1.1.1  1999/11/15 20:37:48  dstotler
Import SSFD into CVS


 VERSION HISTORY PRIOR TO CVS:
------------------------
Daren Stotler:
 Replaced use of getBounds() to set height & width with
getPreferredSize(). Original lines commented out below.
Updated to replace deprecated Java 1.0 methods (reshape with setBounds,
bounds with getBounds, etc.).
--------------------------------------
Difference since version 2:
- added taking getInsets (useful when in external frames) into account
- include the test case in the source
- test case changed to work on Win95/NT also
Difference since version 1:
- removed the need to subclass GUI components
--------------------------------------
*/

/**
This is for placing components relatively to each other or to the parent
panel with an offset. The offset direction depends intuitively on the
relative position required. 

Relative positions are :
<ul>
<li> RelativeLayout.above
<li> RelativeLayout.under
<li> RelativeLayout.left
<li> RelativeLayout.right
<li> RelativeLayout.aboveRight
<li> RelativeLayout.underRight
<li> RelativeLayout.leftBottom
<li> RelativeLayout.rightBottom
<li> RelativeLayout.upperLeftCorner
<li> RelativeLayout.upperRightCorner
<li> RelativeLayout.lowerRightCorner
<li> RelativeLayout.lowerLeftCorner
<li> RelativeLayout.insideUpperLeft
<li> RelativeLayout.insideUpperRight
<li> RelativeLayout.insideLowerRight
<li> RelativeLayout.insideLowerLeft
<li> RelativeLayout.aboveLeft
<li> RelativeLayout.underLeft
<li> RelativeLayout.leftTop
<li> RelativeLayout.rightTop
<li> RelativeLayout.insideTop
<li> RelativeLayout.insideBottom
<li> RelativeLayout.insideRight
<li> RelativeLayout.insideLeft
</ul>
all of which are named after what they implement. Above is centered above the
reference, aboveLeft is same left-aligned...

 Minimal example provided for testing:
<pre>
import java.awt.*;
import java.applet.*;

public class testmin extends Applet {
    Label inst_TopView;
    Label inst_MOSTransistor;

    Dimension d;

    public void init() {
	RelativeLayout rl = new RelativeLayout(new Dimension(200, 100));
	setLayout( rl );
	add( "1", inst_MOSTransistor = new Label("MOS"));
	rl.setConstraint( inst_MOSTransistor, null, RelativeLayout.insideUpperLeft, 5, 5 );
	inst_MOSTransistor.setBackground( Color.green );
	add( "1", inst_TopView = new Label("TopView"));
	rl.setConstraint( inst_TopView, inst_MOSTransistor, RelativeLayout.right, 5, 0 );
	setBounds( 0, 0, 200, 100 );
    }

}
</pre>
@author Jean-Claude Dufourd, Daren Stotler
@version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:48 $.
Author's original version: 3 18.01.96
*/

public class RelativeLayout implements LayoutManager {

    public final static char above = 'A';
    public final static char under = 'B';
    public final static char left = 'C';
    public final static char right = 'D';
    public final static char aboveRight = 'E';
    public final static char underRight = 'F';
    public final static char leftBottom = 'G';
    public final static char rightBottom = 'H';
    public final static char upperLeftCorner = 'I';
    public final static char upperRightCorner = 'J';
    public final static char lowerRightCorner = 'K';
    public final static char lowerLeftCorner = 'L';
    public final static char insideUpperLeft = 'M';
    public final static char insideUpperRight = 'N';
    public final static char insideLowerRight = 'O';
    public final static char insideLowerLeft = 'P';
    public final static char aboveLeft = 'Q';
    public final static char underLeft = 'R';
    public final static char leftTop = 'S';
    public final static char rightTop = 'T';
    public final static char insideTop = 'U';
    public final static char insideBottom = 'V';
    public final static char insideRight = 'W';
    public final static char insideLeft = 'X';

    Dimension d;
    Vector components = new Vector(10,10);
    Vector constraints = new Vector(10,10);

    /* RelativeLayout has a fixed size */
    public RelativeLayout(Dimension dim) {
	d = dim;
    }

    public void addLayoutComponent(String name, Component comp) {
	components.addElement( comp );
	constraints.addElement( null );
    }

    public void removeLayoutComponent(Component comp) {
	int i = components.indexOf( comp );
	if (i != -1) {
	    components.removeElementAt( i );
	    constraints.removeElementAt( i );
	}
    }

    public Dimension minimumLayoutSize(Container target) {
	return d;
    }
    
    public Dimension preferredLayoutSize(Container target) {
	return d;
    }

    public void setConstraint( Component self, Component ref, char where, int dx, int dy ) {
	int i = components.indexOf( self );
	// ignore unknown components
	if (i != -1) {
	    RelativeConstraint c = new RelativeConstraint( ref, where, dx, dy );
	    constraints.setElementAt( c, i );
	}
    }

    public void layoutContainer(Container target) {
	Enumeration e = components.elements();
	Rectangle oneBounds, referenceBounds, targetBounds; // = target.getBounds();
	Insets insets = target.getInsets();
	RelativeConstraint constr;
	Dimension d;
	Component c;
	int i = -1;
//	targetBounds.x = targetBounds.x + insets.left ;
//	targetBounds.y = targetBounds.y + insets.top ;
	targetBounds = new Rectangle();
	targetBounds.x = insets.left;
	targetBounds.y = insets.top;
	targetBounds.width = this.d.width;
	targetBounds.height = this.d.height;
	while (e.hasMoreElements()) {
	    i = i + 1;
	    constr = (RelativeConstraint)constraints.elementAt( i );
	    c = (Component)e.nextElement();
	    d = c.getPreferredSize();
	//    oneBounds = c.getBounds();
	    oneBounds = new Rectangle();
	    oneBounds.x = c.getBounds().x;
	    oneBounds.y = c.getBounds().y;
	    oneBounds.width = d.width;
	    oneBounds.height = d.height;
	    if (constr.reference == null) referenceBounds = targetBounds;
	    else referenceBounds = constr.reference.getBounds();
	    switch (constr.position) {
	      case RelativeLayout.above:
		c.setBounds( referenceBounds.x + referenceBounds.width/2 - oneBounds.width/2 + constr.dx, 
			   referenceBounds.y - constr.dy - oneBounds.height,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.under:
		c.setBounds( referenceBounds.x + referenceBounds.width/2 - oneBounds.width/2 + constr.dx, 
			   referenceBounds.y + constr.dy + referenceBounds.height,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.left:
		c.setBounds( referenceBounds.x - constr.dx - oneBounds.width, 
			   referenceBounds.y + referenceBounds.height/2 - oneBounds.height/2 + constr.dy,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.right:
		c.setBounds( referenceBounds.x + constr.dx + referenceBounds.width, 
			   referenceBounds.y + referenceBounds.height/2 - oneBounds.height/2 + constr.dy,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.aboveRight:
		c.setBounds( referenceBounds.x + referenceBounds.width - oneBounds.width - constr.dx, 
			   referenceBounds.y - constr.dy - oneBounds.height,
			   d.width, d.height );
		break;
	      case RelativeLayout.underRight:
		c.setBounds( referenceBounds.x + referenceBounds.width - oneBounds.width - constr.dx, 
			   referenceBounds.y + constr.dy + referenceBounds.height,
			   d.width, d.height );
		break;
	      case RelativeLayout.leftBottom:
		c.setBounds( referenceBounds.x - constr.dx - oneBounds.width, 
			   referenceBounds.y + referenceBounds.height - oneBounds.height - constr.dy,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.rightBottom:
		c.setBounds( referenceBounds.x + constr.dx + referenceBounds.width, 
			   referenceBounds.y + referenceBounds.height - oneBounds.height - constr.dy,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.upperLeftCorner:
		c.setBounds( referenceBounds.x - constr.dx - oneBounds.width, 
			   referenceBounds.y - constr.dy - oneBounds.height,
			   d.width, d.height );
		break;
	      case RelativeLayout.upperRightCorner:
		c.setBounds( referenceBounds.x + referenceBounds.width + constr.dx, 
			   referenceBounds.y - constr.dy - oneBounds.height,
			   d.width, d.height );
		break;
	      case RelativeLayout.lowerRightCorner:
		c.setBounds( referenceBounds.x + referenceBounds.width + constr.dx, 
			   referenceBounds.y + constr.dy + referenceBounds.height,
			   d.width, d.height );
		break;
	      case RelativeLayout.lowerLeftCorner:
		c.setBounds( referenceBounds.x - oneBounds.width - constr.dx, 
			   referenceBounds.y + constr.dy + referenceBounds.height,
			   d.width, d.height );
		break;
	      case RelativeLayout.insideUpperLeft:
		c.setBounds( referenceBounds.x + constr.dx,
			   referenceBounds.y + constr.dy,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.insideUpperRight:
		c.setBounds( referenceBounds.x + referenceBounds.width - oneBounds.width - constr.dx,
			   referenceBounds.y + constr.dy,
			   d.width, d.height );
		break;
	      case RelativeLayout.insideLowerRight:
		c.setBounds( referenceBounds.x + referenceBounds.width - oneBounds.width - constr.dx,
			   referenceBounds.y + referenceBounds.height - oneBounds.height - constr.dy,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.insideLowerLeft:
		c.setBounds( referenceBounds.x + constr.dx,
			   referenceBounds.y + referenceBounds.height - oneBounds.height - constr.dy,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.aboveLeft:
		c.setBounds( referenceBounds.x + constr.dx, 
			   referenceBounds.y - constr.dy - oneBounds.height,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.underLeft:
		c.setBounds( referenceBounds.x + constr.dx,
			   referenceBounds.y + constr.dy + referenceBounds.height,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.leftTop:
		c.setBounds( referenceBounds.x - constr.dx - oneBounds.width,
			   referenceBounds.y + constr.dy,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.rightTop:
		c.setBounds( referenceBounds.x + constr.dx + referenceBounds.width,
			   referenceBounds.y + constr.dy,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.insideTop:
		c.setBounds( referenceBounds.x + referenceBounds.width/2 - oneBounds.width/2 + constr.dx, 
			   referenceBounds.y + constr.dy,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.insideBottom:
		c.setBounds( referenceBounds.x + referenceBounds.width/2 - oneBounds.width/2 + constr.dx, 
			   referenceBounds.y - constr.dy + referenceBounds.height - oneBounds.height,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.insideLeft:
		c.setBounds( referenceBounds.x + constr.dx, 
			   referenceBounds.y + referenceBounds.height/2 - oneBounds.height/2 + constr.dy,
			   d.width, d.height ); 
		break;
	      case RelativeLayout.insideRight:
		c.setBounds( referenceBounds.x - constr.dx + referenceBounds.width - oneBounds.width, 
			   referenceBounds.y + referenceBounds.height/2 - oneBounds.height/2 + constr.dy,
			   d.width, d.height ); 
		break;
	      default:
		c.setBounds( 0, 0, d.width, d.height );
		break;
	    }
	}
    }
}

class RelativeConstraint {
    Component reference;
    int position, dx, dy;
    
    RelativeConstraint( Component ref, char where, int x, int y ) {
	reference = ref;
	position = where;
	dx = x;
	dy = y;
    }

}

