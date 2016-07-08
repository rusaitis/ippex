/*
 * class ObservableInt
 */

package observable;

import java.util.Observable;

/**
 * Observable mechanism for an integer variable. Taken from
 * Listing 21.14 of "Special Edition, Using Java"
 *
 * @version $Revision: 1.1.1.1 $, $Date: 1999/11/15 20:37:49 $.
 */

/*
 * $Log: ObservableInt.java,v $
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.0 03-25-97 Extricate from DataFrame2.java and give a number.
 */
public class ObservableInt extends Observable
{
  int value;

  public ObservableInt() {
    value = 0;            // Default
  }

  public ObservableInt(int newValue) {
    value = newValue;
  }

  public synchronized void setValue(int newValue) {
/*
 * Be sure the value really is changing...
 */
    if (newValue != value) {
      value = newValue;
      setChanged();
      notifyObservers();
    }
  }

  public synchronized int getValue() {
    return value;
  }
}
