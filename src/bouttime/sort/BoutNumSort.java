/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2009  Jeffrey K. Rutt
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *                  ***COPYRIGHT ENDS HERE***                                */

package bouttime.sort;

import bouttime.model.Bout;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to sort Bout objects by bout numbers.
 */
public class BoutNumSort implements Comparator<Bout> {

    private static Pattern p;

    static {
        // Regular Expression for any non-digit character
        p = Pattern.compile("[^0-9]");
    }

    public int compare(Bout b1, Bout b2) {
        Matcher m;
        String b1Num = b1.getBoutNum();
        String b2Num = b2.getBoutNum();
        Integer b1Int = null;
        String b1Str = "";
        Integer b2Int = null;
        String b2Str = "";
        int rv = 2;

        if ((b1Num == null) || (b2Num == null)) {
            return 0;
        }

        // Parse b1Num
        try {
            // Look for an integer first
            b1Int = Integer.valueOf(b1Num);
        } catch (NumberFormatException nfe) {
            // Wasn't an integer.
            // Look for an integer followed by a character (e.g. 45A).
            // If this doesn't START with an integer, then we'll treat it
            // as a string and sort it that way.
            m = p.matcher(b1Num);
            if (m.find() && (m.end() != 0)) {
                try {
                    String s = b1Num.substring(0, m.start());
                    b1Int = Integer.valueOf(s);
                    b1Str = b1Num.substring(m.start());
                } catch (NumberFormatException nfe2) {
                    System.out.println("No integer in boutNum for bout 1 : " + b1Num);
                }
            }
        }

        // Parse b2Num
        try {
            // Look for an integer first
            b2Int = Integer.valueOf(b2Num);
        } catch (NumberFormatException nfe) {
            // Wasn't an integer.
            // Look for an integer followed by a character (e.g. 45A).
            // If this doesn't START with an integer, then we'll treat it
            // as a string and sort it that way.
            m = p.matcher(b2Num);
            if (m.find() && (m.end() != 0)) {
                try {
                    String s = b2Num.substring(0, m.start());
                    b2Int = Integer.valueOf(s);
                    b2Str = b2Num.substring(m.start());
                } catch (NumberFormatException nfe2) {
                    System.out.println("No integer in boutNum for bout 2 : " + b2Num);
                }
            }
        }

        // compare integers first
        if ((b1Int != null) && (b2Int != null)) {
            rv = b1Int.compareTo(b2Int);
            if (rv == 0) {
                // integers were equal, so compare string-part (if present)
                rv = b1Str.compareTo(b2Str);
            }
        } else {
            // one of the strings did not START with an integer, so compare as strings
            rv = b1Num.compareTo(b2Num);
        }
        
        return rv;
    }
}
