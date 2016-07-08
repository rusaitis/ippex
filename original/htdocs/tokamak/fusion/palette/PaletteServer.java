/*
 * class PaletteServer
 *
 *    Copyright (C) 1996-1999, Daren Stotler
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
package palette;

import java.lang.String;
import java.lang.System;
import java.awt.image.IndexColorModel;

/**
 * This class exists only to initialize instances of the IndexColorModel.
 * The idea is that most of the "palettes" (this is the name for the
 * analogous object in NCSA Heirarchical Data Format files) we would want
 * to use are rather cumbersome to generate on the fly, especially for
 * an arbitrary number of values. So, we gather such code here
 * where a palette of arbitrary size can be retrieved easily. 
 *
 * Use of this class is pretty simple:
 * <ol> 
 * <li> To examine the list of available palettes, do:
 * <pre>
 * String[] list = PaletteServer.getPaletteList();
 * </pre>
 * <li> To define a "hotMetal" palette of length n:
 * <pre>
 * IndexColorModel hm = PaletteServer.getPalette("hotMetal",n);
 * </pre>
 * <li> Other types of palettes can be added to the list by modifying or
 *    extending this class. In either case, the variable paletteList__
 *    must be updated, a corresponding entry must be  made in the
 *    scanPaletteList method, and a method analogous to getHotMetalColors
 *    must be established to define the new palette.
 * </ol>
 * @version $Revision: 1.2 $, $Date: 2004/10/07 20:22:47 $.
 * @author Daren Stotler
 * @see TestApplications.PaletteTest
 */

/*
 * $Log: PaletteServer.java,v $
 * Revision 1.2  2004/10/07 20:22:47  dstotler
 * Changes to javadoc comments.
 *
 * Revision 1.1.1.1  1999/11/15 20:37:49  dstotler
 * Import SSFD into CVS
 *
 *
 * VERSION HISTORY PRIOR TO CVS:
 *
 * 1.0 02-13-97 Finally give it a number.
 * 
 * It was terribly difficult to come to a decision about the 
 * data representation here. The question was whether the colors
 * needed a new data structure, or if IndexColorModel was sufficient.
 * My sense now after using this version for some time is that it
 * will fly. We just need a reason to implement some more palettes!
 */
public class PaletteServer {

  /*
   * This list needs to be extended when new palettes are implemented.
   * Also, the method for generating the colors (e.g., like getHotMetalColors),
   * has to be added, an entry in scanpaletteList__ inserted.
   */
  protected static final String[] paletteList__ = {"hotMetal"};

  /*
   * These palettes are all a maximum of 256 colors.
   */
  private static final int paletteBits__ = 8;

  /*
   * This class is not to be instantiated.
   */
  private PaletteServer() {}

  /**
   * Gives the caller a list of the palettes which can be generated.
   * @return string array of the available palette names
   */
  public static String[] getPaletteList() {
    return (paletteList__);
  }

  /*
   * Algorithm used to generate the "Hot Metal" palette. This palette
   * goes from black -> red -> orange -> yellow -> white. Some other versions
   * stop at yellow.
   * @param r byte array containing the red components
   * @param g byte array containing the green components
   * @param b byte array containing the blue components
   * @param s number of colors in the palette
   */
  private static void getHotMetalColors(byte r[], byte g[], byte b[], int s) {

    int rojo, verde, azul;     // Spanish for red, green, blue
    double thirdSize = s / 3.; // The palette is divided into 3 sections
    double mark1 = thirdSize;
    double mark2 = mark1 + thirdSize;

    for (int i=0; i < s; i++) {        
      if (i < mark1) {                         // First section
	rojo = (int) (255. * ((i+1)/thirdSize));
	verde = 0;
	azul = 0;
      }
      else if (i < mark2) {                    // Second section
	rojo = (int) 255;
	verde = (int) (255. * ((i+1)-mark1) / thirdSize);
	azul = 0;
      }
      else {                                   // Third section
	rojo = (int) 255;
	verde = (int) 255;
	azul = (int) (255. * ((i+1)-mark2) / thirdSize);
      }
      if (rojo < 0) rojo = 0;
      if (rojo > 255) rojo = (int) 255;
      if (verde < 0) verde = 0;
      if (verde > 255) verde = (int) 255;
      if (azul < 0) azul = 0;
      if (azul > 255) azul = (int) 255;

      r[i] = (byte) rojo;
      g[i] = (byte) verde;
      b[i] = (byte) azul;
    }
  }

  /*
   * This is just a simple default implemented just so we can
   * return something.
   * @param r byte array containing the red components
   * @param g byte array containing the green components
   * @param b byte array containing the blue components
   * @param s number of colors in the palette
   */
  private static void getDefaultColors(byte r[], byte g[], byte b[], int s) {
    for (int i=0; i < s; i++) {
      r[i] = (byte) 255;
      g[i] = (byte) 255;
      b[i] = (byte) 255;
      }
  }
  /* 
   * This is just an if-then-else loop to make the associations between
   * the palette name and the methods listed here. It may be possible to
   * implement this class with interfaces & method references.
   * @param pal String containing the palette name
   * @param r byte array containing the red components
   * @param g byte array containing the green components
   * @param b byte array containing the blue components
   * @param s number of colors in the palette
   */
  protected static void scanPaletteList(String pal, byte r[], byte g[], 
				      byte b[], int s) {
    if (pal.equalsIgnoreCase("hotMetal")) {
      getHotMetalColors(r, g, b, s);
    }
    else {
      getDefaultColors(r, g, b, s);
      System.out.println("palette unavailable");
    }
  }

  /**
   * These are the main public methods for this class. The name of
   * a palette and its size is requested. An IndexColorModel is
   * instantiated and returned. This version has no transparency.
   * @param pal String containing the palette name
   * @param s number of colors in the palette
   * @return corresponding instance of IndexColorModel
   */
  public static IndexColorModel getPalette(String pal, int s) {

    byte red[] = new byte[256];
    byte green[] = new byte[256];
    byte blue[] = new byte[256];
    IndexColorModel icm;
    scanPaletteList(pal, red, green, blue, s);
    icm = new IndexColorModel(paletteBits__, s, red, green, blue);
    return (icm);
  }

  /**
   * These are the main public methods for this class. The name of
   * a palette and its size is requested. An IndexColorModel is
   * instantiated and returned. Just as in the IndexColorModel class,
   * offer some options with regard to transparency. Here, use just a
   * single pixel.
   * @param pal String containing the palette name
   * @param s number of colors in the palette
   * @param trans the transparent pixel
   * @return corresponding instance of IndexColorModel
   */
  public static IndexColorModel getPalette(String pal, int s, int trans) {

    byte red[] = new byte[256];
    byte green[] = new byte[256];
    byte blue[] = new byte[256];
    IndexColorModel icm;
    scanPaletteList(pal, red, green, blue, s);
    icm = new IndexColorModel(paletteBits__, s, red, green, blue, trans);
    return (icm);
  }

  /**
   * These are the main public methods for this class. The name of
   * a palette and its size is requested. An IndexColorModel is
   * instantiated and returned. Just as in the IndexColorModel class,
   * offer some options with regard to transparency. In this case,
   * the caller can select a different transparency for every pixel.
   * @param pal String containing the palette name
   * @param s number of colors in the palette
   * @param a array of transparency (alpha) values
   * @return corresponding instance of IndexColorModel
   */
  public static IndexColorModel getPalette(String pal, int s, byte a[]) {

    byte red[] = new byte[256];
    byte green[] = new byte[256];
    byte blue[] = new byte[256];
    IndexColorModel icm;
    scanPaletteList(pal, red, green, blue, s);
    icm = new IndexColorModel(paletteBits__, s, red, green, blue, a);
    return (icm);
  }
}
    
