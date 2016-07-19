/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2012  Jeffrey K. Rutt
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
 *     (1) by mat
 *     (2) by last name
 */
public class WrestlerMatNameSort implements Comparator<Wrestler> {

    public int compare(Wrestler w1, Wrestler w2) {
        int rv;
        if (w1.getGroup() == null) {
            return -1;
        } else if (w2.getGroup() == null) {
            return -1;
        }

        String w1Mat = w1.getGroup().getMat();
        String w2Mat = w2.getGroup().getMat();
        rv = ((w1Mat == null) || (w2Mat == null)) ? 0 : w1Mat.compareTo(w2Mat);
        if (rv != 0) {
            return rv;
        } else {
            String w1Last = w1.getLastName();
            String w2Last = w2.getLastName();
            rv = ((w1Last == null) || (w2Last == null)) ? 0 : w1Last.compareTo(w2Last);
            if (rv != 0) {
                return rv;
            } else {
                String w1First = w1.getFirstName();
                String w2First = w2.getFirstName();
                rv = ((w1First == null) || (w2First == null)) ? 0 : w1First.compareTo(w2First);
                return rv;
            }
        }
    }
}
