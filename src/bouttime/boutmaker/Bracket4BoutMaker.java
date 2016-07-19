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
 * A utility class to make bouts for a 4-man bracket.
 * @deprecated Replaced with bouttime.boutmaker.bracket4.Bracket4BoutMaker
 */
public class Bracket4BoutMaker {
    static Logger logger = Logger.getLogger(Bracket4BoutMaker.class);

    /**
     * Make bouts for a 4-man bracket with 3 wrestlers.
     * @param g The group to make bouts for.
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make3Bracket(Group g, Boolean secondPlaceChallengeEnabled,
            Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 3");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b2 = null;   // semi-final bout 1 (seed2 vs. seed3)
        Bout r2b1 = null;   // championship
        Bout r3b1 = null;   // 2nd-place challenge
        int rounds = 2;

        ///////////////
        // Round 1
        // Semi-finals
        //////////////

        r1b2 = new Bout(list.get(1), list.get(2), g, "1", 2);
        bList.add(r1b2);

        ////////////
        // Round 2
        ////////////

        // Championship bout
        r2b1 = new Bout(list.get(0), dummy, g, "2", 1, "A");
        bList.add(r2b1);

        ////////////
        // Round 3
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r3b1 = new Bout(dummy, dummy, g, "3", 1);
            bList.add(r3b1);
            rounds = 3;
        }

        //////////////
        // Link bouts
        //////////////

        r1b2.setWinnerNextBout(r2b1);

        if (secondPlaceChallengeEnabled) {
            r2b1.setLoserNextBout(r3b1);
            r1b2.setLoserNextBout(r3b1);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }

    /**
     * Make bouts for a 4-man bracket with 4 wrestlers.
     * @param g The group to make bouts for.
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make4Bracket(Group g, Boolean secondPlaceChallengeEnabled,
            Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 4");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b1 = null;   // semi-final bout 1 (seed1 vs. seed4)
        Bout r1b2 = null;   // semi-final bout 1 (seed2 vs. seed3)
        Bout r2b1 = null;   // championship
        Bout r2b2 = null;   // 3rd place
        Bout r3b1 = null;   // 2nd place challenge
        int rounds = 2;

        ///////////////
        // Round 1
        // Semi-finals
        //////////////
        r1b1 = new Bout(list.get(0), list.get(3), g, "1", 1);
        bList.add(r1b1);

        r1b2 = new Bout(list.get(1), list.get(2), g, "1", 2);
        bList.add(r1b2);

        ////////////
        // Round 2
        ////////////

        // Championship bout
        r2b1 = new Bout(dummy, dummy, g, "2", 1, "A");
        bList.add(r2b1);

        // 3rd Place bout
        r2b2 = new Bout(dummy, dummy, g, "2", 2, "Z");
        bList.add(r2b2);

        ////////////
        // Round 3
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r3b1 = new Bout(dummy, dummy, g, "3", 1);
            bList.add(r3b1);
            rounds = 3;
        }

        //////////////
        // Link bouts
        //////////////
        r1b1.setWinnerNextBout(r2b1);
        r1b1.setLoserNextBout(r2b2);

        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r2b2);

        if (secondPlaceChallengeEnabled) {
            r2b1.setLoserNextBout(r3b1);
            r2b2.setWinnerNextBout(r3b1);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }
}
