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

import bouttime.stat.ThreeColumnStat;
import java.util.*;

/**
 * A class to sort ThreeColumnStat objects :
 *     (1) by colunmOne
 *     (2) by columnTwo
 *     (3) by columnThree
 */
public class ThreeColumnStatSort implements Comparator<ThreeColumnStat> {
    public int compare(ThreeColumnStat s1, ThreeColumnStat s2) {
        int rv;
        String c11 = s1.getColumnOne();
        String c21 = s2.getColumnOne();
        rv = c11.compareTo(c21);
        if (rv != 0) {
            return rv;
        } else {
            String c12 = s1.getColumnTwo();
            String c22 = s2.getColumnTwo();
            rv = c12.compareTo(c22);
            if (rv != 0) {
                return rv;
            } else {
                String c13 = s1.getColumnThree();
                String c23 = s2.getColumnThree();
                return c13.compareTo(c23);
            }
        }
    }
}
