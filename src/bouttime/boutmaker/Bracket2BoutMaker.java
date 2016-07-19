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

package bouttime.boutmaker;

import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import bouttime.sort.WrestlerSeedSort;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * A utility class to make bouts for a 2-man bracket.
 * @deprecated Replaced with bouttime.boutmaker.bracket2.Bracket2BoutMaker
 */
public class Bracket2BoutMaker {
    static Logger logger = Logger.getLogger(Bracket2BoutMaker.class);

    /**
     * Make bouts for a 2-man bracket.
     * @param g The group to make bouts for.
     */
    protected static void make2Bracket(Group g) {
        logger.trace("Making bouts for bracket group of size 2");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        int rounds = 1;

        // Only 1 Bout
        Bout r1b1 = new Bout(list.get(0), list.get(1), g, "1", 1, "A");
        bList.add(r1b1);

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }

}
