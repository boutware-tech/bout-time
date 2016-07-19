/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2013  Jeffrey K. Rutt
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

import bouttime.model.Wrestler;
import java.util.*;

/**
 * A class to sort Wrestler objects :
 *     (1) by classification
 *     (2) by age division
 *     (3) by weight class
 *     (4) by last name
 */
public class WrestlerClassDivWtAlphaSort implements Comparator<Wrestler> {
    public int compare(Wrestler w1, Wrestler w2) {
        int rv;
        String w1Class = w1.getClassification();
        String w2Class = w2.getClassification();
        rv = ((w1Class == null) || (w2Class == null)) ? 0 : w1Class.compareTo(w2Class);
        if (rv != 0) {
            return rv;
        } else {
            String w1Div = w1.getAgeDivision();
            String w2Div = w2.getAgeDivision();
            rv = ((w1Div == null) || (w2Div == null)) ? 0 : w1Div.compareTo(w2Div);
            if (rv != 0) {
                return rv;
            } else {
                String w1Wt = w1.getWeightClass();
                String w2Wt = w2.getWeightClass();
                try {
                    Integer w1WtInt = new Integer(w1Wt);
                    Integer w2WtInt = new Integer(w2Wt);
                    rv = ((w1WtInt == null) || (w2WtInt == null)) ? 0 : w1WtInt.compareTo(w2WtInt);
                } catch (NumberFormatException nfe) {
                    rv = ((w1Wt == null) || (w2Wt == null)) ? 0 : w1Wt.compareTo(w2Wt);
                }
                if (rv != 0) {
                    return rv;
                } else {
                    String w1Last = w1.getLastName();
                    String w2Last = w2.getLastName();
                    rv = ((w1Last == null) || (w2Last == null)) ? 0 : w1Last.toUpperCase().compareTo(w2Last.toUpperCase());
                    if (rv != 0) {
                        return rv;
                    } else {
                        String w1First = w1.getFirstName();
                        String w2First = w2.getFirstName();
                        rv = ((w1First == null) || (w2First == null)) ? 0 : w1First.toUpperCase().compareTo(w2First.toUpperCase());
                        return rv;
                    }
                }
            }
        }
    }
}
