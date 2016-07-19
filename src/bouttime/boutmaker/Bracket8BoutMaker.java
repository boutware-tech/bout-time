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
 * A utility class to make bouts for an 8-man bracket.
 * @deprecated Replaced with bouttime.boutmaker.bracket8.Bracket8BoutMaker
 */
public class Bracket8BoutMaker {
    static Logger logger = Logger.getLogger(Bracket8BoutMaker.class);

    ////////////////////////////////////////////////////////////////////////////
    //   D E S I G N    N O T E  :
    //
    // Making a general-purpose 8-man bracket method was way too confusing.
    // So we're making an individual method for each of the various sizes that
    // use an 8-man bracket.  This adds more code, some of which is redudant.
    // However, the code is much more readable and easier to debug and maintain.
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Make bouts for an 8-man bracket with 5 wrestlers.
     * @param g The group to make bouts for.
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make5Bracket(Group g, Boolean secondPlaceChallengeEnabled,
            Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 5");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b2 = null;   // quarter-final bout 1 (seed4 vs. seed5)
        Bout r2b1 = null;   // front-side semi-final bout 1
        Bout r2b2 = null;   // front-side semi-final bout 2
        Bout r3b1 = null;   // back-side semi-final bout 1
        Bout r4b1 = null;   // championship
        Bout r4b2 = null;   // 3rd place
        Bout r5b1 = null;   // 2nd place challenge
        // no 5th place bout -- only 5 wrestlers!
        int rounds = 4;

        /////////////
        // Round 1
        /////////////

        // Front-side : quarter-finals
        r1b2 = new Bout(list.get(3), list.get(4), g, "1", 1);
        bList.add(r1b2);

        /////////////
        // Round 2
        /////////////

        // Front-side : semi-finals
        r2b1 = new Bout(list.get(0), dummy, g, "2", 1, "B");
        bList.add(r2b1);
        r2b2 = new Bout(list.get(1), list.get(2), g, "2", 1, "C");
        bList.add(r2b2);

        /////////////
        // Round 3
        /////////////

        // Back-side : semi-finals
        r3b1 = new Bout(dummy, dummy, g, "3", 1);
        bList.add(r3b1);

        ////////////
        // Round 4
        ////////////

        // Front-side : championship
        r4b1 = new Bout(dummy, dummy, g, "4", 1, "A");
        bList.add(r4b1);

        // Back-side : 3rd place
        r4b2 = new Bout(dummy, dummy, g, "4", 2, "Z");
        bList.add(r4b2);

        ////////////
        // Round 5
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r5b1 = new Bout(dummy, dummy, g, "5", 1);
            bList.add(r5b1);
            rounds = 5;
        }

        //////////////
        // Link bouts
        //////////////
        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r3b1);
        r2b1.setWinnerNextBout(r4b1);
        r2b1.setLoserNextBout(r4b2);
        r2b2.setWinnerNextBout(r4b1);
        r2b2.setLoserNextBout(r3b1);
        r3b1.setWinnerNextBout(r4b2);
        if (secondPlaceChallengeEnabled) {
            r4b1.setLoserNextBout(r5b1);
            r4b2.setWinnerNextBout(r5b1);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }

    /**
     * Make bouts for an 8-man bracket with 6 wrestlers.
     * @param g The group to make bouts for.
     * @param fifthPlaceEnabled
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make6Bracket(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 6");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b2 = null;   // quarter-final bout 1 (seed4 vs. seed5)
        Bout r1b3 = null;   // quarter-final bout 1 (seed3 vs. seed6)
        Bout r2b1 = null;   // front-side semi-final bout 1
        Bout r2b2 = null;   // front-side semi-final bout 2
        Bout r3b1 = null;   // back-side semi-final bout 1
        Bout r3b2 = null;   // back-side semi-final bout 2
        Bout r4b1 = null;   // championship
        Bout r4b2 = null;   // 3rd place
        Bout r4b3 = null;   // 5th place
        Bout r5b1 = null;   // 2nd place challenge
        int rounds = 4;

        /////////////
        // Round 1
        /////////////

        // Front-side : quarter-finals
        r1b2 = new Bout(list.get(3), list.get(4), g, "1", 1);
        bList.add(r1b2);
        r1b3 = new Bout(list.get(2), list.get(5), g, "1", 2);
        bList.add(r1b3);

        /////////////
        // Round 2
        /////////////

        // Front-side : semi-finals
        r2b1 = new Bout(list.get(0), dummy, g, "2", 1, "B");
        bList.add(r2b1);
        r2b2 = new Bout(list.get(1), dummy, g, "2", 2, "C");
        bList.add(r2b2);

        /////////////
        // Round 3
        /////////////

        // Back-side : semi-finals
        r3b1 = new Bout(dummy, dummy, g, "3", 1);
        bList.add(r3b1);
        r3b2 = new Bout(dummy, dummy, g, "3", 2);
        bList.add(r3b2);

        ////////////
        // Round 4
        ////////////

        // Front-side : championship
        r4b1 = new Bout(dummy, dummy, g, "4", 1, "A");
        bList.add(r4b1);

        // Back-side : 3rd place
        r4b2 = new Bout(dummy, dummy, g, "4", 2, "Z");
        bList.add(r4b2);

        if (fifthPlaceEnabled) {
            r4b3 = new Bout(dummy, dummy, g, "4", 3);
            bList.add(r4b3);
        }

        ////////////
        // Round 5
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r5b1 = new Bout(dummy, dummy, g, "5", 1);
            bList.add(r5b1);
            rounds = 5;
        }

        //////////////
        // Link bouts
        //////////////
        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r3b1);
        r1b3.setWinnerNextBout(r2b2);
        r1b3.setLoserNextBout(r3b2);
        r2b1.setWinnerNextBout(r4b1);
        r2b1.setLoserNextBout(r3b2);
        r2b2.setWinnerNextBout(r4b1);
        r2b2.setLoserNextBout(r3b1);
        r3b1.setWinnerNextBout(r4b2);
        r3b2.setWinnerNextBout(r4b2);
        if (secondPlaceChallengeEnabled) {
            r4b1.setLoserNextBout(r5b1);
            r4b2.setWinnerNextBout(r5b1);
        }

        if (fifthPlaceEnabled) {
            r3b1.setLoserNextBout(r4b3);
            r3b2.setLoserNextBout(r4b3);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }

    /**
     * Make bouts for an 8-man bracket with 7 wrestlers.
     * @param g The group to make bouts for.
     * @param fifthPlaceEnabled
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make7Bracket(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 7");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b2 = null;   // quarter-final bout 1 (seed4 vs. seed5)
        Bout r1b3 = null;   // quarter-final bout 1 (seed3 vs. seed6)
        Bout r1b4 = null;   // quarter-final bout 1 (seed2 vs. seed7)
        Bout r2b1 = null;   // front-side semi-final bout 1
        Bout r2b2 = null;   // front-side semi-final bout 2
        Bout r2b4 = null;   // back-side quarter-final bout 2
        Bout r3b1 = null;   // back-side semi-final bout 1
        Bout r3b2 = null;   // back-side semi-final bout 2
        Bout r4b1 = null;   // championship
        Bout r4b2 = null;   // 3rd place
        Bout r4b3 = null;   // 5th place
        Bout r5b1 = null;   // 2nd place challenge
        int rounds = 4;

        /////////////
        // Round 1
        /////////////

        // Front-side : quarter-finals
        r1b2 = new Bout(list.get(3), list.get(4), g, "1", 1);
        bList.add(r1b2);
        r1b3 = new Bout(list.get(2), list.get(5), g, "1", 2);
        bList.add(r1b3);
        r1b4 = new Bout(list.get(1), list.get(6), g, "1", 3);
        bList.add(r1b4);

        /////////////
        // Round 2
        /////////////

        // Front-side : semi-finals
        r2b1 = new Bout(list.get(0), dummy, g, "2", 1, "B");
        bList.add(r2b1);
        r2b2 = new Bout(dummy, dummy, g, "2", 2, "C");
        bList.add(r2b2);

        // Back-side : quarter-final
        r2b4 = new Bout(dummy, dummy, g, "2", 3);
        bList.add(r2b4);

        /////////////
        // Round 3
        /////////////

        // Back-side : semi-finals
        r3b1 = new Bout(dummy, dummy, g, "3", 1);
        bList.add(r3b1);
        r3b2 = new Bout(dummy, dummy, g, "3", 2);
        bList.add(r3b2);

        ////////////
        // Round 4
        ////////////

        // Front-side : championship
        r4b1 = new Bout(dummy, dummy, g, "4", 1, "A");
        bList.add(r4b1);

        // Back-side : 3rd place
        r4b2 = new Bout(dummy, dummy, g, "4", 2, "Z");
        bList.add(r4b2);

        if (fifthPlaceEnabled) {
            r4b3 = new Bout(dummy, dummy, g, "4", 3);
            bList.add(r4b3);
        }

        ////////////
        // Round 5
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r5b1 = new Bout(dummy, dummy, g, "5", 1);
            bList.add(r5b1);
            rounds = 5;
        }

        //////////////
        // Link bouts
        //////////////
        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r3b1);
        r1b3.setWinnerNextBout(r2b2);
        r1b3.setLoserNextBout(r2b4);
        r1b4.setWinnerNextBout(r2b2);
        r1b4.setLoserNextBout(r2b4);
        r2b1.setWinnerNextBout(r4b1);
        r2b1.setLoserNextBout(r3b2);
        r2b2.setWinnerNextBout(r4b1);
        r2b2.setLoserNextBout(r3b1);
        r2b4.setWinnerNextBout(r3b2);
        r3b1.setWinnerNextBout(r4b2);
        r3b2.setWinnerNextBout(r4b2);
        if (secondPlaceChallengeEnabled) {
            r4b1.setLoserNextBout(r5b1);
            r4b2.setWinnerNextBout(r5b1);
        }

        if (fifthPlaceEnabled) {
            r3b1.setLoserNextBout(r4b3);
            r3b2.setLoserNextBout(r4b3);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }

    /**
     * Make bouts for an 8-man bracket with 8 wrestlers.
     * @param g The group to make bouts for.
     * @param fifthPlaceEnabled
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make8Bracket(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 8");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b1 = null;   // quarter-final bout 1 (seed1 vs. seed8)
        Bout r1b2 = null;   // quarter-final bout 1 (seed4 vs. seed5)
        Bout r1b3 = null;   // quarter-final bout 1 (seed3 vs. seed6)
        Bout r1b4 = null;   // quarter-final bout 1 (seed2 vs. seed7)
        Bout r2b1 = null;   // front-side semi-final bout 1
        Bout r2b2 = null;   // front-side semi-final bout 2
        Bout r2b3 = null;   // back-side quarter-final bout 1
        Bout r2b4 = null;   // back-side quarter-final bout 2
        Bout r3b1 = null;   // back-side semi-final bout 1
        Bout r3b2 = null;   // back-side semi-final bout 2
        Bout r4b1 = null;   // championship
        Bout r4b2 = null;   // 3rd place
        Bout r4b3 = null;   // 5th place
        Bout r5b1 = null;   // 2nd place challenge
        int rounds = 4;

        /////////////
        // Round 1
        /////////////

        // Front-side : quarter-finals
        r1b1 = new Bout(list.get(0), list.get(7), g, "1", 1);
        bList.add(r1b1);
        r1b2 = new Bout(list.get(3), list.get(4), g, "1", 2);
        bList.add(r1b2);
        r1b3 = new Bout(list.get(2), list.get(5), g, "1", 3);
        bList.add(r1b3);
        r1b4 = new Bout(list.get(1), list.get(6), g, "1", 4);
        bList.add(r1b4);

        /////////////
        // Round 2
        /////////////

        // Front-side : semi-finals
        r2b1 = new Bout(dummy, dummy, g, "2", 1, "B");
        bList.add(r2b1);
        r2b2 = new Bout(dummy, dummy, g, "2", 2, "C");
        bList.add(r2b2);

        // Back-side : quarter-final
        r2b3 = new Bout(dummy, dummy, g, "2", 3);
        bList.add(r2b3);
        r2b4 = new Bout(dummy, dummy, g, "2", 4);
        bList.add(r2b4);

        /////////////
        // Round 3
        /////////////

        // Back-side : semi-finals
        r3b1 = new Bout(dummy, dummy, g, "3", 1);
        bList.add(r3b1);
        r3b2 = new Bout(dummy, dummy, g, "3", 2);
        bList.add(r3b2);

        ////////////
        // Round 4
        ////////////

        // Front-side : championship
        r4b1 = new Bout(dummy, dummy, g, "4", 1, "A");
        bList.add(r4b1);

        // Back-side : 3rd place
        r4b2 = new Bout(dummy, dummy, g, "4", 2, "Z");
        bList.add(r4b2);

        if (fifthPlaceEnabled) {
            r4b3 = new Bout(dummy, dummy, g, "4", 3);
            bList.add(r4b3);
        }

        ////////////
        // Round 5
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r5b1 = new Bout(dummy, dummy, g, "5", 1);
            bList.add(r5b1);
            rounds = 5;
        }

        //////////////
        // Link bouts
        //////////////

        r1b1.setWinnerNextBout(r2b1);
        r1b1.setLoserNextBout(r2b3);
        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r2b3);
        r1b3.setWinnerNextBout(r2b2);
        r1b3.setLoserNextBout(r2b4);
        r1b4.setWinnerNextBout(r2b2);
        r1b4.setLoserNextBout(r2b4);
        r2b1.setWinnerNextBout(r4b1);
        r2b1.setLoserNextBout(r3b2);
        r2b2.setWinnerNextBout(r4b1);
        r2b2.setLoserNextBout(r3b1);
        r2b3.setWinnerNextBout(r3b1);
        r2b4.setWinnerNextBout(r3b2);
        r3b1.setWinnerNextBout(r4b2);
        r3b2.setWinnerNextBout(r4b2);
        if (secondPlaceChallengeEnabled) {
            r4b1.setLoserNextBout(r5b1);
            r4b2.setWinnerNextBout(r5b1);
        }

        if (fifthPlaceEnabled) {
            r3b1.setLoserNextBout(r4b3);
            r3b2.setLoserNextBout(r4b3);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }
}
