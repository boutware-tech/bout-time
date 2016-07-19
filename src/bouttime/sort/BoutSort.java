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

/**
 * A class to sort Bout objects :
 *     (1) by round
 *     (2) by sequence
 */
public class BoutSort implements Comparator<Bout> {

    public int compare(Bout b1, Bout b2) {
        int rv;
        String b1Round = b1.getRound();
        String b2Round = b2.getRound();
        rv = b1Round.compareTo(b2Round);
        if (rv != 0) {
            return rv;
        } else {
            Integer b1seq = b1.getSequence();
            Integer b2seq = b2.getSequence();
            return b1seq.compareTo(b2seq);
        }
    }
}
