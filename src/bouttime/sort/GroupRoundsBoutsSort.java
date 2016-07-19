/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2010  Jeffrey K. Rutt
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

import bouttime.model.Group;
import java.util.*;

/**
 * A class to sort Group objects :
 *     (1) by number of rounds
 *     (2) by number of bouts in round 1
 *
 * Sort order is highest to lowest.
 */
public class GroupRoundsBoutsSort implements Comparator<Group> {
    public int compare(Group g1, Group g2) {
        int rv;
        Integer g1Rounds = Integer.valueOf(g1.getNumRounds());
        Integer g2Rounds = Integer.valueOf(g2.getNumRounds());
        rv = g2Rounds.compareTo(g1Rounds);  // highest to lowest
        if (rv != 0) {
            return rv;
        } else {
            Integer g1Bouts = Integer.valueOf(g1.getBoutsByRound("1").size());
            Integer g2Bouts = Integer.valueOf(g2.getBoutsByRound("1").size());
            return g2Bouts.compareTo(g1Bouts);  // highest to lowest
        }
    }
}
