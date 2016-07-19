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

import bouttime.model.Wrestler;
import java.util.Comparator;

/**
 * A class to sort Wrestlers by their place value.
 */
public class WrestlerPlaceSort implements Comparator<Wrestler> {
    public int compare(Wrestler w1, Wrestler w2) {
        Integer w1Place = w1.getPlace();
        Integer w2Place = w2.getPlace();
        if (w1Place < w2Place) {
            return -1;
        } else if (w1Place > w2Place) {
            return 1;
        } else {
            return 0;
        }
    }
}
