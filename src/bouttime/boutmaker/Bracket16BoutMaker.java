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
 * A utility class to make bouts for a 16-man bracket.
 * @deprecated Replaced with bouttime.boutmaker.bracket16.Bracket16BoutMaker
 */
public class Bracket16BoutMaker {
    static Logger logger = Logger.getLogger(Bracket16BoutMaker.class);

    ////////////////////////////////////////////////////////////////////////////
    //   D E S I G N    N O T E  :
    //
    // Making a general-purpose 16-man bracket method was way too confusing.
    // So we're making an individual method for each of the various sizes that
    // use a 16-man bracket.  This adds more code, some of which is redudant.
    // However, the code is much more readable and easier to debug and maintain.
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Make bouts for a 16-man bracket with 9 wrestlers.
     * @param g The group to make bouts for.
     * @param fifthPlaceEnabled
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make9Bracket(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 9");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b2 = null;   // initial round bout 1 (seed9 vs. seed8)
        Bout r2b1 = null;   // front-side quarter-final bout 1
        Bout r2b2 = null;   // front-side quarter-final bout 2
        Bout r2b3 = null;   // front-side quarter-final bout 3
        Bout r2b4 = null;   // front-side quarter-final bout 4
        Bout r3b1 = null;   // back-side quarter-final-2 bout 1
        Bout r4b1 = null;   // front-side semi-final bout 1
        Bout r4b2 = null;   // front-side semi-final bout 2
        Bout r4b3 = null;   // back-side semi-final-1 bout 1
        Bout r4b4 = null;   // back-side semi-final-1 bout 2
        Bout r5b1 = null;   // back-side semi-final-2 bout 1
        Bout r5b2 = null;   // back-side semi-final-2 bout 2
        Bout r6b1 = null;   // championship
        Bout r6b2 = null;   // 3rd place
        Bout r6b3 = null;   // 5th place
        Bout r7b1 = null;   // 2nd place challenge
        int rounds = 6;

        /////////////
        // Round 1
        /////////////

        // Front-side : initial round
        r1b2 = new Bout(list.get(8), list.get(7), g, "1", 1);
        bList.add(r1b2);

        /////////////
        // Round 2
        /////////////

        // Front-side : quarter-finals
        r2b1 = new Bout(list.get(0), dummy, g, "2", 1, "D");
        bList.add(r2b1);
        r2b2 = new Bout(list.get(3), list.get(4), g, "2", 2, "E");
        bList.add(r2b2);
        r2b3 = new Bout(list.get(2), list.get(5), g, "2", 3, "F");
        bList.add(r2b3);
        r2b4 = new Bout(list.get(1), list.get(6), g, "2", 4, "G");
        bList.add(r2b4);

        // Back-side : quarter-final-1
        // no bouts

        /////////////
        // Round 3
        /////////////

        // Back-side : quarter-finals-2
        r3b1 = new Bout(dummy, dummy, g, "3", 1);
        bList.add(r3b1);

        ////////////
        // Round 4
        ////////////

        // Front-side : semi-finals
        r4b1 = new Bout(dummy, dummy, g, "4", 1, "B");
        bList.add(r4b1);
        r4b2 = new Bout(dummy, dummy, g, "4", 2, "C");
        bList.add(r4b2);

        // Back-side : semi-finals-1
        r4b3 = new Bout(dummy, dummy, g, "4", 3);
        bList.add(r4b3);
        r4b4 = new Bout(dummy, dummy, g, "4", 4);
        bList.add(r4b4);

        ////////////
        // Round 5
        ////////////

        // Back-side : semi-finals-2
        r5b1 = new Bout(dummy, dummy, g, "5", 1, "X");
        bList.add(r5b1);
        r5b2 = new Bout(dummy, dummy, g, "5", 2, "Y");
        bList.add(r5b2);

        ////////////
        // Round 6
        ////////////

        // Front-side : championship
        r6b1 = new Bout(dummy, dummy, g, "6", 1, "A");
        bList.add(r6b1);

        // Back-side : 3rd place
        r6b2 = new Bout(dummy, dummy, g, "6", 2, "Z");
        bList.add(r6b2);

        if (fifthPlaceEnabled) {
            r6b3 = new Bout(dummy, dummy, g, "6", 3);
            bList.add(r6b3);
        }

        ////////////
        // Round 7
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r7b1 = new Bout(dummy, dummy, g, "7", 1);
            bList.add(r7b1);
            rounds = 7;
        }

        //////////////
        // Link bouts
        //////////////

        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r3b1);

        r2b1.setWinnerNextBout(r4b1);
        r2b1.setLoserNextBout(r4b4);
        r2b2.setWinnerNextBout(r4b1);
        r2b2.setLoserNextBout(r4b4);
        r2b3.setWinnerNextBout(r4b2);
        r2b3.setLoserNextBout(r4b3);
        r2b4.setWinnerNextBout(r4b2);
        r2b4.setLoserNextBout(r3b1);

        r3b1.setWinnerNextBout(r4b3);

        r4b1.setWinnerNextBout(r6b1);
        r4b1.setLoserNextBout(r5b1);
        r4b2.setWinnerNextBout(r6b1);
        r4b2.setLoserNextBout(r5b2);
        r4b3.setWinnerNextBout(r5b1);
        r4b4.setWinnerNextBout(r5b2);

        r5b1.setWinnerNextBout(r6b2);
        r5b2.setWinnerNextBout(r6b2);

        if (secondPlaceChallengeEnabled) {
            r6b1.setLoserNextBout(r7b1);
            r6b2.setWinnerNextBout(r7b1);
        }

        if (fifthPlaceEnabled) {
            r5b1.setLoserNextBout(r6b3);
            r5b2.setLoserNextBout(r6b3);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }

    /**
     * Make bouts for a 16-man bracket with 10 wrestlers.
     * @param g The group to make bouts for.
     * @param fifthPlaceEnabled
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make10Bracket(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 10");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b2 = null;   // initial round bout 1 (seed9 vs. seed8)
        Bout r1b7 = null;   // initial round bout 1 (seed7 vs. seed10)
        Bout r2b1 = null;   // front-side quarter-final bout 1
        Bout r2b2 = null;   // front-side quarter-final bout 2
        Bout r2b3 = null;   // front-side quarter-final bout 3
        Bout r2b4 = null;   // front-side quarter-final bout 4
        Bout r3b1 = null;   // back-side quarter-final-2 bout 1
        Bout r3b4 = null;   // back-side quarter-final-2 bout 4
        Bout r4b1 = null;   // front-side semi-final bout 1
        Bout r4b2 = null;   // front-side semi-final bout 2
        Bout r4b3 = null;   // back-side semi-final-1 bout 1
        Bout r4b4 = null;   // back-side semi-final-1 bout 2
        Bout r5b1 = null;   // back-side semi-final-2 bout 1
        Bout r5b2 = null;   // back-side semi-final-2 bout 2
        Bout r6b1 = null;   // championship
        Bout r6b2 = null;   // 3rd place
        Bout r6b3 = null;   // 5th place
        Bout r7b1 = null;   // 2nd place challenge
        int rounds = 6;

        /////////////
        // Round 1
        /////////////

        // Front-side : initial round
        r1b2 = new Bout(list.get(8), list.get(7), g, "1", 1);
        bList.add(r1b2);
        r1b7 = new Bout(list.get(6), list.get(9), g, "1", 2);
        bList.add(r1b7);

        /////////////
        // Round 2
        /////////////

        // Front-side : quarter-finals
        r2b1 = new Bout(list.get(0), dummy, g, "2", 1, "D");
        bList.add(r2b1);
        r2b2 = new Bout(list.get(3), list.get(4), g, "2", 2, "E");
        bList.add(r2b2);
        r2b3 = new Bout(list.get(2), list.get(5), g, "2", 3, "F");
        bList.add(r2b3);
        r2b4 = new Bout(list.get(1), dummy, g, "2", 4, "G");
        bList.add(r2b4);

        // Back-side : quarter-final-1
        // no bouts

        /////////////
        // Round 3
        /////////////

        // Back-side : quarter-finals-2
        r3b1 = new Bout(dummy, dummy, g, "3", 1);
        bList.add(r3b1);
        r3b4 = new Bout(dummy, dummy, g, "3", 2);
        bList.add(r3b4);

        ////////////
        // Round 4
        ////////////

        // Front-side : semi-finals
        r4b1 = new Bout(dummy, dummy, g, "4", 1, "B");
        bList.add(r4b1);
        r4b2 = new Bout(dummy, dummy, g, "4", 2, "C");
        bList.add(r4b2);

        // Back-side : semi-finals-1
        r4b3 = new Bout(dummy, dummy, g, "4", 3);
        bList.add(r4b3);
        r4b4 = new Bout(dummy, dummy, g, "4", 4);
        bList.add(r4b4);

        ////////////
        // Round 5
        ////////////

        // Back-side : semi-finals-2
        r5b1 = new Bout(dummy, dummy, g, "5", 1, "X");
        bList.add(r5b1);
        r5b2 = new Bout(dummy, dummy, g, "5", 2, "Y");
        bList.add(r5b2);

        ////////////
        // Round 6
        ////////////

        // Front-side : championship
        r6b1 = new Bout(dummy, dummy, g, "6", 1, "A");
        bList.add(r6b1);

        // Back-side : 3rd place
        r6b2 = new Bout(dummy, dummy, g, "6", 2, "Z");
        bList.add(r6b2);

        if (fifthPlaceEnabled) {
            r6b3 = new Bout(dummy, dummy, g, "6", 3);
            bList.add(r6b3);
        }

        ////////////
        // Round 7
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r7b1 = new Bout(dummy, dummy, g, "7", 1);
            bList.add(r7b1);
            rounds = 7;
        }

        //////////////
        // Link bouts
        //////////////

        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r3b1);
        r1b7.setWinnerNextBout(r2b4);
        r1b7.setLoserNextBout(r3b4);

        r2b1.setWinnerNextBout(r4b1);
        r2b1.setLoserNextBout(r3b4);
        r2b2.setWinnerNextBout(r4b1);
        r2b2.setLoserNextBout(r4b4);
        r2b3.setWinnerNextBout(r4b2);
        r2b3.setLoserNextBout(r4b3);
        r2b4.setWinnerNextBout(r4b2);
        r2b4.setLoserNextBout(r3b1);

        r3b1.setWinnerNextBout(r4b3);
        r3b4.setWinnerNextBout(r4b4);

        r4b1.setWinnerNextBout(r6b1);
        r4b1.setLoserNextBout(r5b1);
        r4b2.setWinnerNextBout(r6b1);
        r4b2.setLoserNextBout(r5b2);
        r4b3.setWinnerNextBout(r5b1);
        r4b4.setWinnerNextBout(r5b2);

        r5b1.setWinnerNextBout(r6b2);
        r5b2.setWinnerNextBout(r6b2);

        if (secondPlaceChallengeEnabled) {
            r6b1.setLoserNextBout(r7b1);
            r6b2.setWinnerNextBout(r7b1);
        }

        if (fifthPlaceEnabled) {
            r5b1.setLoserNextBout(r6b3);
            r5b2.setLoserNextBout(r6b3);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }

    /**
     * Make bouts for a 16-man bracket with 11 wrestlers.
     * @param g The group to make bouts for.
     * @param fifthPlaceEnabled
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make11Bracket(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 11");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b2 = null;   // initial round bout 1 (seed9 vs. seed8)
        Bout r1b6 = null;   // initial round bout 1 (seed11 vs. seed6)
        Bout r1b7 = null;   // initial round bout 1 (seed7 vs. seed10)
        Bout r2b1 = null;   // front-side quarter-final bout 1
        Bout r2b2 = null;   // front-side quarter-final bout 2
        Bout r2b3 = null;   // front-side quarter-final bout 3
        Bout r2b4 = null;   // front-side quarter-final bout 4
        Bout r3b1 = null;   // back-side quarter-final-2 bout 1
        Bout r3b3 = null;   // back-side quarter-final-2 bout 3
        Bout r3b4 = null;   // back-side quarter-final-2 bout 4
        Bout r4b1 = null;   // front-side semi-final bout 1
        Bout r4b2 = null;   // front-side semi-final bout 2
        Bout r4b3 = null;   // back-side semi-final-1 bout 1
        Bout r4b4 = null;   // back-side semi-final-1 bout 2
        Bout r5b1 = null;   // back-side semi-final-2 bout 1
        Bout r5b2 = null;   // back-side semi-final-2 bout 2
        Bout r6b1 = null;   // championship
        Bout r6b2 = null;   // 3rd place
        Bout r6b3 = null;   // 5th place
        Bout r7b1 = null;   // 2nd place challenge
        int rounds = 6;

        /////////////
        // Round 1
        /////////////

        // Front-side : initial round
        r1b2 = new Bout(list.get(8), list.get(7), g, "1", 1);
        bList.add(r1b2);
        r1b6 = new Bout(list.get(10), list.get(5), g, "1", 2);
        bList.add(r1b6);
        r1b7 = new Bout(list.get(6), list.get(9), g, "1", 3);
        bList.add(r1b7);

        /////////////
        // Round 2
        /////////////

        // Front-side : quarter-finals
        r2b1 = new Bout(list.get(0), dummy, g, "2", 1, "D");
        bList.add(r2b1);
        r2b2 = new Bout(list.get(3), list.get(4), g, "2", 2, "E");
        bList.add(r2b2);
        r2b3 = new Bout(list.get(2), dummy, g, "2", 3, "F");
        bList.add(r2b3);
        r2b4 = new Bout(list.get(1), dummy, g, "2", 4, "G");
        bList.add(r2b4);

        // Back-side : quarter-final-1
        // no bouts

        /////////////
        // Round 3
        /////////////

        // Back-side : quarter-finals-2
        r3b1 = new Bout(dummy, dummy, g, "3", 1);
        bList.add(r3b1);
        r3b3 = new Bout(dummy, dummy, g, "3", 2);
        bList.add(r3b3);
        r3b4 = new Bout(dummy, dummy, g, "3", 3);
        bList.add(r3b4);

        ////////////
        // Round 4
        ////////////

        // Front-side : semi-finals
        r4b1 = new Bout(dummy, dummy, g, "4", 1, "B");
        bList.add(r4b1);
        r4b2 = new Bout(dummy, dummy, g, "4", 2, "C");
        bList.add(r4b2);

        // Back-side : semi-finals-1
        r4b3 = new Bout(dummy, dummy, g, "4", 3);
        bList.add(r4b3);
        r4b4 = new Bout(dummy, dummy, g, "4", 4);
        bList.add(r4b4);

        ////////////
        // Round 5
        ////////////

        // Back-side : semi-finals-2
        r5b1 = new Bout(dummy, dummy, g, "5", 1, "X");
        bList.add(r5b1);
        r5b2 = new Bout(dummy, dummy, g, "5", 2, "Y");
        bList.add(r5b2);

        ////////////
        // Round 6
        ////////////

        // Front-side : championship
        r6b1 = new Bout(dummy, dummy, g, "6", 1, "A");
        bList.add(r6b1);

        // Back-side : 3rd place
        r6b2 = new Bout(dummy, dummy, g, "6", 2, "Z");
        bList.add(r6b2);

        if (fifthPlaceEnabled) {
            r6b3 = new Bout(dummy, dummy, g, "6", 3);
            bList.add(r6b3);
        }

        ////////////
        // Round 7
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r7b1 = new Bout(dummy, dummy, g, "7", 1);
            bList.add(r7b1);
            rounds = 7;
        }

        //////////////
        // Link bouts
        //////////////

        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r3b1);
        r1b6.setWinnerNextBout(r2b3);
        r1b6.setLoserNextBout(r3b3);
        r1b7.setWinnerNextBout(r2b4);
        r1b7.setLoserNextBout(r3b4);

        r2b1.setWinnerNextBout(r4b1);
        r2b1.setLoserNextBout(r3b4);
        r2b2.setWinnerNextBout(r4b1);
        r2b2.setLoserNextBout(r3b3);
        r2b3.setWinnerNextBout(r4b2);
        r2b3.setLoserNextBout(r4b3);
        r2b4.setWinnerNextBout(r4b2);
        r2b4.setLoserNextBout(r3b1);

        r3b1.setWinnerNextBout(r4b3);
        r3b3.setWinnerNextBout(r4b4);
        r3b4.setWinnerNextBout(r4b4);

        r4b1.setWinnerNextBout(r6b1);
        r4b1.setLoserNextBout(r5b1);
        r4b2.setWinnerNextBout(r6b1);
        r4b2.setLoserNextBout(r5b2);
        r4b3.setWinnerNextBout(r5b1);
        r4b4.setWinnerNextBout(r5b2);

        r5b1.setWinnerNextBout(r6b2);
        r5b2.setWinnerNextBout(r6b2);

        if (secondPlaceChallengeEnabled) {
            r6b1.setLoserNextBout(r7b1);
            r6b2.setWinnerNextBout(r7b1);
        }

        if (fifthPlaceEnabled) {
            r5b1.setLoserNextBout(r6b3);
            r5b2.setLoserNextBout(r6b3);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }

    /**
     * Make bouts for a 16-man bracket with 12 wrestlers.
     * @param g The group to make bouts for.
     * @param fifthPlaceEnabled
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make12Bracket(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 12");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b2 = null;   // initial round bout 1 (seed9 vs. seed8)
        Bout r1b3 = null;   // initial round bout 1 (seed5 vs. seed12)
        Bout r1b6 = null;   // initial round bout 1 (seed11 vs. seed6)
        Bout r1b7 = null;   // initial round bout 1 (seed7 vs. seed10)
        Bout r2b1 = null;   // front-side quarter-final bout 1
        Bout r2b2 = null;   // front-side quarter-final bout 2
        Bout r2b3 = null;   // front-side quarter-final bout 3
        Bout r2b4 = null;   // front-side quarter-final bout 4
        Bout r3b1 = null;   // back-side quarter-final-2 bout 1
        Bout r3b2 = null;   // back-side quarter-final-2 bout 2
        Bout r3b3 = null;   // back-side quarter-final-2 bout 3
        Bout r3b4 = null;   // back-side quarter-final-2 bout 4
        Bout r4b1 = null;   // front-side semi-final bout 1
        Bout r4b2 = null;   // front-side semi-final bout 2
        Bout r4b3 = null;   // back-side semi-final-1 bout 1
        Bout r4b4 = null;   // back-side semi-final-1 bout 2
        Bout r5b1 = null;   // back-side semi-final-2 bout 1
        Bout r5b2 = null;   // back-side semi-final-2 bout 2
        Bout r6b1 = null;   // championship
        Bout r6b2 = null;   // 3rd place
        Bout r6b3 = null;   // 5th place
        Bout r7b1 = null;   // 2nd place challenge
        int rounds = 6;

        /////////////
        // Round 1
        /////////////

        // Front-side : initial round
        r1b2 = new Bout(list.get(8), list.get(7), g, "1", 1);
        bList.add(r1b2);
        r1b3 = new Bout(list.get(4), list.get(11), g, "1", 2);
        bList.add(r1b3);
        r1b6 = new Bout(list.get(10), list.get(5), g, "1", 3);
        bList.add(r1b6);
        r1b7 = new Bout(list.get(6), list.get(9), g, "1", 4);
        bList.add(r1b7);

        /////////////
        // Round 2
        /////////////

        // Front-side : quarter-finals
        r2b1 = new Bout(list.get(0), dummy, g, "2", 1, "D");
        bList.add(r2b1);
        r2b2 = new Bout(list.get(3), dummy, g, "2", 2, "E");
        bList.add(r2b2);
        r2b3 = new Bout(list.get(2), dummy, g, "2", 3, "F");
        bList.add(r2b3);
        r2b4 = new Bout(list.get(1), dummy, g, "2", 4, "G");
        bList.add(r2b4);

        // Back-side : quarter-final-1
        // no bouts

        /////////////
        // Round 3
        /////////////

        // Back-side : quarter-finals-2
        r3b1 = new Bout(dummy, dummy, g, "3", 1);
        bList.add(r3b1);
        r3b2 = new Bout(dummy, dummy, g, "3", 2);
        bList.add(r3b2);
        r3b3 = new Bout(dummy, dummy, g, "3", 3);
        bList.add(r3b3);
        r3b4 = new Bout(dummy, dummy, g, "3", 4);
        bList.add(r3b4);

        ////////////
        // Round 4
        ////////////

        // Front-side : semi-finals
        r4b1 = new Bout(dummy, dummy, g, "4", 1, "B");
        bList.add(r4b1);
        r4b2 = new Bout(dummy, dummy, g, "4", 2, "C");
        bList.add(r4b2);

        // Back-side : semi-finals-1
        r4b3 = new Bout(dummy, dummy, g, "4", 3);
        bList.add(r4b3);
        r4b4 = new Bout(dummy, dummy, g, "4", 4);
        bList.add(r4b4);

        ////////////
        // Round 5
        ////////////

        // Back-side : semi-finals-2
        r5b1 = new Bout(dummy, dummy, g, "5", 1, "X");
        bList.add(r5b1);
        r5b2 = new Bout(dummy, dummy, g, "5", 2, "Y");
        bList.add(r5b2);

        ////////////
        // Round 6
        ////////////

        // Front-side : championship
        r6b1 = new Bout(dummy, dummy, g, "6", 1, "A");
        bList.add(r6b1);

        // Back-side : 3rd place
        r6b2 = new Bout(dummy, dummy, g, "6", 2, "Z");
        bList.add(r6b2);

        if (fifthPlaceEnabled) {
            r6b3 = new Bout(dummy, dummy, g, "6", 3);
            bList.add(r6b3);
        }

        ////////////
        // Round 7
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r7b1 = new Bout(dummy, dummy, g, "7", 1);
            bList.add(r7b1);
            rounds = 7;
        }

        //////////////
        // Link bouts
        //////////////

        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r3b1);
        r1b3.setWinnerNextBout(r2b2);
        r1b3.setLoserNextBout(r3b2);
        r1b6.setWinnerNextBout(r2b3);
        r1b6.setLoserNextBout(r3b3);
        r1b7.setWinnerNextBout(r2b4);
        r1b7.setLoserNextBout(r3b4);

        r2b1.setWinnerNextBout(r4b1);
        r2b1.setLoserNextBout(r3b4);
        r2b2.setWinnerNextBout(r4b1);
        r2b2.setLoserNextBout(r3b3);
        r2b3.setWinnerNextBout(r4b2);
        r2b3.setLoserNextBout(r3b2);
        r2b4.setWinnerNextBout(r4b2);
        r2b4.setLoserNextBout(r3b1);

        r3b1.setWinnerNextBout(r4b3);
        r3b2.setWinnerNextBout(r4b3);
        r3b3.setWinnerNextBout(r4b4);
        r3b4.setWinnerNextBout(r4b4);

        r4b1.setWinnerNextBout(r6b1);
        r4b1.setLoserNextBout(r5b1);
        r4b2.setWinnerNextBout(r6b1);
        r4b2.setLoserNextBout(r5b2);
        r4b3.setWinnerNextBout(r5b1);
        r4b4.setWinnerNextBout(r5b2);

        r5b1.setWinnerNextBout(r6b2);
        r5b2.setWinnerNextBout(r6b2);

        if (secondPlaceChallengeEnabled) {
            r6b1.setLoserNextBout(r7b1);
            r6b2.setWinnerNextBout(r7b1);
        }

        if (fifthPlaceEnabled) {
            r5b1.setLoserNextBout(r6b3);
            r5b2.setLoserNextBout(r6b3);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }

    /**
     * Make bouts for a 16-man bracket with 13 wrestlers.
     * @param g The group to make bouts for.
     * @param fifthPlaceEnabled
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make13Bracket(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 13");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b2 = null;   // initial round bout 1 (seed9 vs. seed8)
        Bout r1b3 = null;   // initial round bout 1 (seed5 vs. seed12)
        Bout r1b4 = null;   // initial round bout 1 (seed13 vs. seed4)
        Bout r1b6 = null;   // initial round bout 1 (seed11 vs. seed6)
        Bout r1b7 = null;   // initial round bout 1 (seed7 vs. seed10)
        Bout r2b1 = null;   // front-side quarter-final bout 1
        Bout r2b2 = null;   // front-side quarter-final bout 2
        Bout r2b3 = null;   // front-side quarter-final bout 3
        Bout r2b4 = null;   // front-side quarter-final bout 4
        Bout r2b6 = null;   // back-side quarter-final-1 bout 2
        Bout r3b1 = null;   // back-side quarter-final-2 bout 1
        Bout r3b2 = null;   // back-side quarter-final-2 bout 2
        Bout r3b3 = null;   // back-side quarter-final-2 bout 3
        Bout r3b4 = null;   // back-side quarter-final-2 bout 4
        Bout r4b1 = null;   // front-side semi-final bout 1
        Bout r4b2 = null;   // front-side semi-final bout 2
        Bout r4b3 = null;   // back-side semi-final-1 bout 1
        Bout r4b4 = null;   // back-side semi-final-1 bout 2
        Bout r5b1 = null;   // back-side semi-final-2 bout 1
        Bout r5b2 = null;   // back-side semi-final-2 bout 2
        Bout r6b1 = null;   // championship
        Bout r6b2 = null;   // 3rd place
        Bout r6b3 = null;   // 5th place
        Bout r7b1 = null;   // 2nd place challenge
        int rounds = 6;

        /////////////
        // Round 1
        /////////////

        // Front-side : initial round
        r1b2 = new Bout(list.get(8), list.get(7), g, "1", 1);
        bList.add(r1b2);
        r1b3 = new Bout(list.get(4), list.get(11), g, "1", 2);
        bList.add(r1b3);
        r1b4 = new Bout(list.get(12), list.get(3), g, "1", 3);
        bList.add(r1b4);
        r1b6 = new Bout(list.get(10), list.get(5), g, "1", 4);
        bList.add(r1b6);
        r1b7 = new Bout(list.get(6), list.get(9), g, "1", 5);
        bList.add(r1b7);

        /////////////
        // Round 2
        /////////////

        // Front-side : quarter-finals
        r2b1 = new Bout(list.get(0), dummy, g, "2", 1, "D");
        bList.add(r2b1);
        r2b2 = new Bout(dummy, dummy, g, "2", 2, "E");
        bList.add(r2b2);
        r2b3 = new Bout(list.get(2), dummy, g, "2", 3, "F");
        bList.add(r2b3);
        r2b4 = new Bout(list.get(1), dummy, g, "2", 4, "G");
        bList.add(r2b4);

        // Back-side : quarter-final-1
        r2b6 = new Bout(dummy, dummy, g, "2", 5);
        bList.add(r2b6);

        /////////////
        // Round 3
        /////////////

        // Back-side : quarter-finals-2
        r3b1 = new Bout(dummy, dummy, g, "3", 1);
        bList.add(r3b1);
        r3b2 = new Bout(dummy, dummy, g, "3", 2);
        bList.add(r3b2);
        r3b3 = new Bout(dummy, dummy, g, "3", 3);
        bList.add(r3b3);
        r3b4 = new Bout(dummy, dummy, g, "3", 4);
        bList.add(r3b4);

        ////////////
        // Round 4
        ////////////

        // Front-side : semi-finals
        r4b1 = new Bout(dummy, dummy, g, "4", 1, "B");
        bList.add(r4b1);
        r4b2 = new Bout(dummy, dummy, g, "4", 2, "C");
        bList.add(r4b2);

        // Back-side : semi-finals-1
        r4b3 = new Bout(dummy, dummy, g, "4", 3);
        bList.add(r4b3);
        r4b4 = new Bout(dummy, dummy, g, "4", 4);
        bList.add(r4b4);

        ////////////
        // Round 5
        ////////////

        // Back-side : semi-finals-2
        r5b1 = new Bout(dummy, dummy, g, "5", 1, "X");
        bList.add(r5b1);
        r5b2 = new Bout(dummy, dummy, g, "5", 2, "Y");
        bList.add(r5b2);

        ////////////
        // Round 6
        ////////////

        // Front-side : championship
        r6b1 = new Bout(dummy, dummy, g, "6", 1, "A");
        bList.add(r6b1);

        // Back-side : 3rd place
        r6b2 = new Bout(dummy, dummy, g, "6", 2, "Z");
        bList.add(r6b2);

        if (fifthPlaceEnabled) {
            r6b3 = new Bout(dummy, dummy, g, "6", 3);
            bList.add(r6b3);
        }

        ////////////
        // Round 7
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r7b1 = new Bout(dummy, dummy, g, "7", 1);
            bList.add(r7b1);
            rounds = 7;
        }

        //////////////
        // Link bouts
        //////////////

        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r3b1);
        r1b3.setWinnerNextBout(r2b2);
        r1b3.setLoserNextBout(r2b6);
        r1b4.setWinnerNextBout(r2b2);
        r1b4.setLoserNextBout(r2b6);
        r1b6.setWinnerNextBout(r2b3);
        r1b6.setLoserNextBout(r3b3);
        r1b7.setWinnerNextBout(r2b4);
        r1b7.setLoserNextBout(r3b4);

        r2b1.setWinnerNextBout(r4b1);
        r2b1.setLoserNextBout(r3b4);
        r2b2.setWinnerNextBout(r4b1);
        r2b2.setLoserNextBout(r3b3);
        r2b3.setWinnerNextBout(r4b2);
        r2b3.setLoserNextBout(r3b2);
        r2b4.setWinnerNextBout(r4b2);
        r2b4.setLoserNextBout(r3b1);

        r2b6.setWinnerNextBout(r3b2);

        r3b1.setWinnerNextBout(r4b3);
        r3b2.setWinnerNextBout(r4b3);
        r3b3.setWinnerNextBout(r4b4);
        r3b4.setWinnerNextBout(r4b4);

        r4b1.setWinnerNextBout(r6b1);
        r4b1.setLoserNextBout(r5b1);
        r4b2.setWinnerNextBout(r6b1);
        r4b2.setLoserNextBout(r5b2);
        r4b3.setWinnerNextBout(r5b1);
        r4b4.setWinnerNextBout(r5b2);

        r5b1.setWinnerNextBout(r6b2);
        r5b2.setWinnerNextBout(r6b2);

        if (secondPlaceChallengeEnabled) {
            r6b1.setLoserNextBout(r7b1);
            r6b2.setWinnerNextBout(r7b1);
        }

        if (fifthPlaceEnabled) {
            r5b1.setLoserNextBout(r6b3);
            r5b2.setLoserNextBout(r6b3);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }

    /**
     * Make bouts for a 16-man bracket with 14 wrestlers.
     * @param g The group to make bouts for.
     * @param fifthPlaceEnabled
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make14Bracket(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 14");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b2 = null;   // initial round bout 1 (seed9 vs. seed8)
        Bout r1b3 = null;   // initial round bout 1 (seed5 vs. seed12)
        Bout r1b4 = null;   // initial round bout 1 (seed13 vs. seed4)
        Bout r1b5 = null;   // initial round bout 1 (seed3 vs. seed14)
        Bout r1b6 = null;   // initial round bout 1 (seed11 vs. seed6)
        Bout r1b7 = null;   // initial round bout 1 (seed7 vs. seed10)
        Bout r2b1 = null;   // front-side quarter-final bout 1
        Bout r2b2 = null;   // front-side quarter-final bout 2
        Bout r2b3 = null;   // front-side quarter-final bout 3
        Bout r2b4 = null;   // front-side quarter-final bout 4
        Bout r2b6 = null;   // back-side quarter-final-1 bout 2
        Bout r2b7 = null;   // back-side quarter-final-1 bout 3
        Bout r3b1 = null;   // back-side quarter-final-2 bout 1
        Bout r3b2 = null;   // back-side quarter-final-2 bout 2
        Bout r3b3 = null;   // back-side quarter-final-2 bout 3
        Bout r3b4 = null;   // back-side quarter-final-2 bout 4
        Bout r4b1 = null;   // front-side semi-final bout 1
        Bout r4b2 = null;   // front-side semi-final bout 2
        Bout r4b3 = null;   // back-side semi-final-1 bout 1
        Bout r4b4 = null;   // back-side semi-final-1 bout 2
        Bout r5b1 = null;   // back-side semi-final-2 bout 1
        Bout r5b2 = null;   // back-side semi-final-2 bout 2
        Bout r6b1 = null;   // championship
        Bout r6b2 = null;   // 3rd place
        Bout r6b3 = null;   // 5th place
        Bout r7b1 = null;   // 2nd place challenge
        int rounds = 6;

        /////////////
        // Round 1
        /////////////

        // Front-side : initial round
        r1b2 = new Bout(list.get(8), list.get(7), g, "1", 1);
        bList.add(r1b2);
        r1b3 = new Bout(list.get(4), list.get(11), g, "1", 2);
        bList.add(r1b3);
        r1b4 = new Bout(list.get(12), list.get(3), g, "1", 3);
        bList.add(r1b4);
        r1b5 = new Bout(list.get(2), list.get(13), g, "1", 4);
        bList.add(r1b5);
        r1b6 = new Bout(list.get(10), list.get(5), g, "1", 5);
        bList.add(r1b6);
        r1b7 = new Bout(list.get(6), list.get(9), g, "1", 6);
        bList.add(r1b7);

        /////////////
        // Round 2
        /////////////

        // Front-side : quarter-finals
        r2b1 = new Bout(list.get(0), dummy, g, "2", 1, "D");
        bList.add(r2b1);
        r2b2 = new Bout(dummy, dummy, g, "2", 2, "E");
        bList.add(r2b2);
        r2b3 = new Bout(dummy, dummy, g, "2", 3, "F");
        bList.add(r2b3);
        r2b4 = new Bout(list.get(1), dummy, g, "2", 4, "G");
        bList.add(r2b4);

        // Back-side : quarter-final-1
        r2b6 = new Bout(dummy, dummy, g, "2", 5);
        bList.add(r2b6);
        r2b7 = new Bout(dummy, dummy, g, "2", 6);
        bList.add(r2b7);

        /////////////
        // Round 3
        /////////////

        // Back-side : quarter-finals-2
        r3b1 = new Bout(dummy, dummy, g, "3", 1);
        bList.add(r3b1);
        r3b2 = new Bout(dummy, dummy, g, "3", 2);
        bList.add(r3b2);
        r3b3 = new Bout(dummy, dummy, g, "3", 3);
        bList.add(r3b3);
        r3b4 = new Bout(dummy, dummy, g, "3", 4);
        bList.add(r3b4);

        ////////////
        // Round 4
        ////////////

        // Front-side : semi-finals
        r4b1 = new Bout(dummy, dummy, g, "4", 1, "B");
        bList.add(r4b1);
        r4b2 = new Bout(dummy, dummy, g, "4", 2, "C");
        bList.add(r4b2);

        // Back-side : semi-finals-1
        r4b3 = new Bout(dummy, dummy, g, "4", 3);
        bList.add(r4b3);
        r4b4 = new Bout(dummy, dummy, g, "4", 4);
        bList.add(r4b4);

        ////////////
        // Round 5
        ////////////

        // Back-side : semi-finals-2
        r5b1 = new Bout(dummy, dummy, g, "5", 1, "X");
        bList.add(r5b1);
        r5b2 = new Bout(dummy, dummy, g, "5", 2, "Y");
        bList.add(r5b2);

        ////////////
        // Round 6
        ////////////

        // Front-side : championship
        r6b1 = new Bout(dummy, dummy, g, "6", 1, "A");
        bList.add(r6b1);

        // Back-side : 3rd place
        r6b2 = new Bout(dummy, dummy, g, "6", 2, "Z");
        bList.add(r6b2);

        if (fifthPlaceEnabled) {
            r6b3 = new Bout(dummy, dummy, g, "6", 3);
            bList.add(r6b3);
        }

        ////////////
        // Round 7
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r7b1 = new Bout(dummy, dummy, g, "7", 1);
            bList.add(r7b1);
            rounds = 7;
        }

        //////////////
        // Link bouts
        //////////////

        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r3b1);
        r1b3.setWinnerNextBout(r2b2);
        r1b3.setLoserNextBout(r2b6);
        r1b4.setWinnerNextBout(r2b2);
        r1b4.setLoserNextBout(r2b6);
        r1b5.setWinnerNextBout(r2b3);
        r1b5.setLoserNextBout(r2b7);
        r1b6.setWinnerNextBout(r2b3);
        r1b6.setLoserNextBout(r2b7);
        r1b7.setWinnerNextBout(r2b4);
        r1b7.setLoserNextBout(r3b4);

        r2b1.setWinnerNextBout(r4b1);
        r2b1.setLoserNextBout(r3b4);
        r2b2.setWinnerNextBout(r4b1);
        r2b2.setLoserNextBout(r3b3);
        r2b3.setWinnerNextBout(r4b2);
        r2b3.setLoserNextBout(r3b2);
        r2b4.setWinnerNextBout(r4b2);
        r2b4.setLoserNextBout(r3b1);

        r2b6.setWinnerNextBout(r3b2);
        r2b7.setWinnerNextBout(r3b3);

        r3b1.setWinnerNextBout(r4b3);
        r3b2.setWinnerNextBout(r4b3);
        r3b3.setWinnerNextBout(r4b4);
        r3b4.setWinnerNextBout(r4b4);

        r4b1.setWinnerNextBout(r6b1);
        r4b1.setLoserNextBout(r5b1);
        r4b2.setWinnerNextBout(r6b1);
        r4b2.setLoserNextBout(r5b2);
        r4b3.setWinnerNextBout(r5b1);
        r4b4.setWinnerNextBout(r5b2);

        r5b1.setWinnerNextBout(r6b2);
        r5b2.setWinnerNextBout(r6b2);

        if (secondPlaceChallengeEnabled) {
            r6b1.setLoserNextBout(r7b1);
            r6b2.setWinnerNextBout(r7b1);
        }

        if (fifthPlaceEnabled) {
            r5b1.setLoserNextBout(r6b3);
            r5b2.setLoserNextBout(r6b3);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }

    /**
     * Make bouts for a 16-man bracket with 15 wrestlers.
     * @param g The group to make bouts for.
     * @param fifthPlaceEnabled
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make15Bracket(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 15");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b2 = null;   // initial round bout 1 (seed9 vs. seed8)
        Bout r1b3 = null;   // initial round bout 1 (seed5 vs. seed12)
        Bout r1b4 = null;   // initial round bout 1 (seed13 vs. seed4)
        Bout r1b5 = null;   // initial round bout 1 (seed3 vs. seed14)
        Bout r1b6 = null;   // initial round bout 1 (seed11 vs. seed6)
        Bout r1b7 = null;   // initial round bout 1 (seed7 vs. seed10)
        Bout r1b8 = null;   // initial round bout 1 (seed2 vs. seed15)
        Bout r2b1 = null;   // front-side quarter-final bout 1
        Bout r2b2 = null;   // front-side quarter-final bout 2
        Bout r2b3 = null;   // front-side quarter-final bout 3
        Bout r2b4 = null;   // front-side quarter-final bout 4
        Bout r2b6 = null;   // back-side quarter-final-1 bout 2
        Bout r2b7 = null;   // back-side quarter-final-1 bout 3
        Bout r2b8 = null;   // back-side quarter-final-1 bout 4
        Bout r3b1 = null;   // back-side quarter-final-2 bout 1
        Bout r3b2 = null;   // back-side quarter-final-2 bout 2
        Bout r3b3 = null;   // back-side quarter-final-2 bout 3
        Bout r3b4 = null;   // back-side quarter-final-2 bout 4
        Bout r4b1 = null;   // front-side semi-final bout 1
        Bout r4b2 = null;   // front-side semi-final bout 2
        Bout r4b3 = null;   // back-side semi-final-1 bout 1
        Bout r4b4 = null;   // back-side semi-final-1 bout 2
        Bout r5b1 = null;   // back-side semi-final-2 bout 1
        Bout r5b2 = null;   // back-side semi-final-2 bout 2
        Bout r6b1 = null;   // championship
        Bout r6b2 = null;   // 3rd place
        Bout r6b3 = null;   // 5th place
        Bout r7b1 = null;   // 2nd place challenge
        int rounds = 6;

        /////////////
        // Round 1
        /////////////

        // Front-side : initial round
        r1b2 = new Bout(list.get(8), list.get(7), g, "1", 1);
        bList.add(r1b2);
        r1b3 = new Bout(list.get(4), list.get(11), g, "1", 2);
        bList.add(r1b3);
        r1b4 = new Bout(list.get(12), list.get(3), g, "1", 3);
        bList.add(r1b4);
        r1b5 = new Bout(list.get(2), list.get(13), g, "1", 4);
        bList.add(r1b5);
        r1b6 = new Bout(list.get(10), list.get(5), g, "1", 5);
        bList.add(r1b6);
        r1b7 = new Bout(list.get(6), list.get(9), g, "1", 6);
        bList.add(r1b7);
        r1b8 = new Bout(list.get(1), list.get(14), g, "1", 7);
        bList.add(r1b8);

        /////////////
        // Round 2
        /////////////

        // Front-side : quarter-finals
        r2b1 = new Bout(list.get(0), dummy, g, "2", 1, "D");
        bList.add(r2b1);
        r2b2 = new Bout(dummy, dummy, g, "2", 2, "E");
        bList.add(r2b2);
        r2b3 = new Bout(dummy, dummy, g, "2", 3, "F");
        bList.add(r2b3);
        r2b4 = new Bout(dummy, dummy, g, "2", 4, "G");
        bList.add(r2b4);

        // Back-side : quarter-final-1
        r2b6 = new Bout(dummy, dummy, g, "2", 5);
        bList.add(r2b6);
        r2b7 = new Bout(dummy, dummy, g, "2", 6);
        bList.add(r2b7);
        r2b8 = new Bout(dummy, dummy, g, "2", 7);
        bList.add(r2b8);

        /////////////
        // Round 3
        /////////////

        // Back-side : quarter-finals-2
        r3b1 = new Bout(dummy, dummy, g, "3", 1);
        bList.add(r3b1);
        r3b2 = new Bout(dummy, dummy, g, "3", 2);
        bList.add(r3b2);
        r3b3 = new Bout(dummy, dummy, g, "3", 3);
        bList.add(r3b3);
        r3b4 = new Bout(dummy, dummy, g, "3", 4);
        bList.add(r3b4);

        ////////////
        // Round 4
        ////////////

        // Front-side : semi-finals
        r4b1 = new Bout(dummy, dummy, g, "4", 1, "B");
        bList.add(r4b1);
        r4b2 = new Bout(dummy, dummy, g, "4", 2, "C");
        bList.add(r4b2);

        // Back-side : semi-finals-1
        r4b3 = new Bout(dummy, dummy, g, "4", 3);
        bList.add(r4b3);
        r4b4 = new Bout(dummy, dummy, g, "4", 4);
        bList.add(r4b4);

        ////////////
        // Round 5
        ////////////

        // Back-side : semi-finals-2
        r5b1 = new Bout(dummy, dummy, g, "5", 1, "X");
        bList.add(r5b1);
        r5b2 = new Bout(dummy, dummy, g, "5", 2, "Y");
        bList.add(r5b2);

        ////////////
        // Round 6
        ////////////

        // Front-side : championship
        r6b1 = new Bout(dummy, dummy, g, "6", 1, "A");
        bList.add(r6b1);

        // Back-side : 3rd place
        r6b2 = new Bout(dummy, dummy, g, "6", 2, "Z");
        bList.add(r6b2);

        if (fifthPlaceEnabled) {
            r6b3 = new Bout(dummy, dummy, g, "6", 3);
            bList.add(r6b3);
        }

        ////////////
        // Round 7
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r7b1 = new Bout(dummy, dummy, g, "7", 1);
            bList.add(r7b1);
            rounds = 7;
        }

        //////////////
        // Link bouts
        //////////////

        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r3b1);
        r1b3.setWinnerNextBout(r2b2);
        r1b3.setLoserNextBout(r2b6);
        r1b4.setWinnerNextBout(r2b2);
        r1b4.setLoserNextBout(r2b6);
        r1b5.setWinnerNextBout(r2b3);
        r1b5.setLoserNextBout(r2b7);
        r1b6.setWinnerNextBout(r2b3);
        r1b6.setLoserNextBout(r2b7);
        r1b7.setWinnerNextBout(r2b4);
        r1b7.setLoserNextBout(r2b8);
        r1b8.setWinnerNextBout(r2b4);
        r1b8.setLoserNextBout(r2b8);

        r2b1.setWinnerNextBout(r4b1);
        r2b1.setLoserNextBout(r3b4);
        r2b2.setWinnerNextBout(r4b1);
        r2b2.setLoserNextBout(r3b3);
        r2b3.setWinnerNextBout(r4b2);
        r2b3.setLoserNextBout(r3b2);
        r2b4.setWinnerNextBout(r4b2);
        r2b4.setLoserNextBout(r3b1);

        r2b6.setWinnerNextBout(r3b2);
        r2b7.setWinnerNextBout(r3b3);
        r2b8.setWinnerNextBout(r3b4);

        r3b1.setWinnerNextBout(r4b3);
        r3b2.setWinnerNextBout(r4b3);
        r3b3.setWinnerNextBout(r4b4);
        r3b4.setWinnerNextBout(r4b4);

        r4b1.setWinnerNextBout(r6b1);
        r4b1.setLoserNextBout(r5b1);
        r4b2.setWinnerNextBout(r6b1);
        r4b2.setLoserNextBout(r5b2);
        r4b3.setWinnerNextBout(r5b1);
        r4b4.setWinnerNextBout(r5b2);

        r5b1.setWinnerNextBout(r6b2);
        r5b2.setWinnerNextBout(r6b2);

        if (secondPlaceChallengeEnabled) {
            r6b1.setLoserNextBout(r7b1);
            r6b2.setWinnerNextBout(r7b1);
        }

        if (fifthPlaceEnabled) {
            r5b1.setLoserNextBout(r6b3);
            r5b2.setLoserNextBout(r6b3);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }

    /**
     * Make bouts for a 16-man bracket with 16 wrestlers.
     * @param g The group to make bouts for.
     * @param fifthPlaceEnabled
     * @param secondPlaceChallengeEnabled
     * @param dummy
     */
    protected static void make16Bracket(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size 16");

        List<Wrestler> list = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());
        List<Bout> bList = new ArrayList<Bout>();
        Bout r1b1 = null;   // initial round bout 1 (seed1 vs. seed16)
        Bout r1b2 = null;   // initial round bout 1 (seed9 vs. seed8)
        Bout r1b3 = null;   // initial round bout 1 (seed5 vs. seed12)
        Bout r1b4 = null;   // initial round bout 1 (seed13 vs. seed4)
        Bout r1b5 = null;   // initial round bout 1 (seed3 vs. seed14)
        Bout r1b6 = null;   // initial round bout 1 (seed11 vs. seed6)
        Bout r1b7 = null;   // initial round bout 1 (seed7 vs. seed10)
        Bout r1b8 = null;   // initial round bout 1 (seed2 vs. seed15)
        Bout r2b1 = null;   // front-side quarter-final bout 1
        Bout r2b2 = null;   // front-side quarter-final bout 2
        Bout r2b3 = null;   // front-side quarter-final bout 3
        Bout r2b4 = null;   // front-side quarter-final bout 4
        Bout r2b5 = null;   // back-side quarter-final-1 bout 1
        Bout r2b6 = null;   // back-side quarter-final-1 bout 2
        Bout r2b7 = null;   // back-side quarter-final-1 bout 3
        Bout r2b8 = null;   // back-side quarter-final-1 bout 4
        Bout r3b1 = null;   // back-side quarter-final-2 bout 1
        Bout r3b2 = null;   // back-side quarter-final-2 bout 2
        Bout r3b3 = null;   // back-side quarter-final-2 bout 3
        Bout r3b4 = null;   // back-side quarter-final-2 bout 4
        Bout r4b1 = null;   // front-side semi-final bout 1
        Bout r4b2 = null;   // front-side semi-final bout 2
        Bout r4b3 = null;   // back-side semi-final-1 bout 1
        Bout r4b4 = null;   // back-side semi-final-1 bout 2
        Bout r5b1 = null;   // back-side semi-final-2 bout 1
        Bout r5b2 = null;   // back-side semi-final-2 bout 2
        Bout r6b1 = null;   // championship
        Bout r6b2 = null;   // 3rd place
        Bout r6b3 = null;   // 5th place
        Bout r7b1 = null;   // 2nd place challenge
        int rounds = 6;

        /////////////
        // Round 1
        /////////////

        // Front-side : initial round
        r1b1 = new Bout(list.get(0), list.get(15), g, "1", 1);
        bList.add(r1b1);
        r1b2 = new Bout(list.get(8), list.get(7), g, "1", 2);
        bList.add(r1b2);
        r1b3 = new Bout(list.get(4), list.get(11), g, "1", 3);
        bList.add(r1b3);
        r1b4 = new Bout(list.get(12), list.get(3), g, "1", 4);
        bList.add(r1b4);
        r1b5 = new Bout(list.get(2), list.get(13), g, "1", 5);
        bList.add(r1b5);
        r1b6 = new Bout(list.get(10), list.get(5), g, "1", 6);
        bList.add(r1b6);
        r1b7 = new Bout(list.get(6), list.get(9), g, "1", 7);
        bList.add(r1b7);
        r1b8 = new Bout(list.get(1), list.get(14), g, "1", 8);
        bList.add(r1b8);

        /////////////
        // Round 2
        /////////////

        // Front-side : quarter-finals
        r2b1 = new Bout(dummy, dummy, g, "2", 1, "D");
        bList.add(r2b1);
        r2b2 = new Bout(dummy, dummy, g, "2", 2, "E");
        bList.add(r2b2);
        r2b3 = new Bout(dummy, dummy, g, "2", 3, "F");
        bList.add(r2b3);
        r2b4 = new Bout(dummy, dummy, g, "2", 4, "G");
        bList.add(r2b4);

        // Back-side : quarter-final-1
        r2b5 = new Bout(dummy, dummy, g, "2", 5);
        bList.add(r2b5);
        r2b6 = new Bout(dummy, dummy, g, "2", 6);
        bList.add(r2b6);
        r2b7 = new Bout(dummy, dummy, g, "2", 7);
        bList.add(r2b7);
        r2b8 = new Bout(dummy, dummy, g, "2", 8);
        bList.add(r2b8);

        /////////////
        // Round 3
        /////////////

        // Back-side : quarter-finals-2
        r3b1 = new Bout(dummy, dummy, g, "3", 1);
        bList.add(r3b1);
        r3b2 = new Bout(dummy, dummy, g, "3", 2);
        bList.add(r3b2);
        r3b3 = new Bout(dummy, dummy, g, "3", 3);
        bList.add(r3b3);
        r3b4 = new Bout(dummy, dummy, g, "3", 4);
        bList.add(r3b4);

        ////////////
        // Round 4
        ////////////

        // Front-side : semi-finals
        r4b1 = new Bout(dummy, dummy, g, "4", 1, "B");
        bList.add(r4b1);
        r4b2 = new Bout(dummy, dummy, g, "4", 2, "C");
        bList.add(r4b2);

        // Back-side : semi-finals-1
        r4b3 = new Bout(dummy, dummy, g, "4", 3);
        bList.add(r4b3);
        r4b4 = new Bout(dummy, dummy, g, "4", 4);
        bList.add(r4b4);

        ////////////
        // Round 5
        ////////////

        // Back-side : semi-finals-2
        r5b1 = new Bout(dummy, dummy, g, "5", 1, "X");
        bList.add(r5b1);
        r5b2 = new Bout(dummy, dummy, g, "5", 2, "Y");
        bList.add(r5b2);
        
        ////////////
        // Round 6
        ////////////

        // Front-side : championship
        r6b1 = new Bout(dummy, dummy, g, "6", 1, "A");
        bList.add(r6b1);

        // Back-side : 3rd place
        r6b2 = new Bout(dummy, dummy, g, "6", 2, "Z");
        bList.add(r6b2);

        if (fifthPlaceEnabled) {
            r6b3 = new Bout(dummy, dummy, g, "6", 3);
            bList.add(r6b3);
        }

        ////////////
        // Round 7
        ////////////

        // 2nd place challenge
        if (secondPlaceChallengeEnabled) {
            r7b1 = new Bout(dummy, dummy, g, "7", 1);
            bList.add(r7b1);
            rounds = 7;
        }

        //////////////
        // Link bouts
        //////////////

        r1b1.setWinnerNextBout(r2b1);
        r1b1.setLoserNextBout(r2b5);
        r1b2.setWinnerNextBout(r2b1);
        r1b2.setLoserNextBout(r2b5);
        r1b3.setWinnerNextBout(r2b2);
        r1b3.setLoserNextBout(r2b6);
        r1b4.setWinnerNextBout(r2b2);
        r1b4.setLoserNextBout(r2b6);
        r1b5.setWinnerNextBout(r2b3);
        r1b5.setLoserNextBout(r2b7);
        r1b6.setWinnerNextBout(r2b3);
        r1b6.setLoserNextBout(r2b7);
        r1b7.setWinnerNextBout(r2b4);
        r1b7.setLoserNextBout(r2b8);
        r1b8.setWinnerNextBout(r2b4);
        r1b8.setLoserNextBout(r2b8);

        r2b1.setWinnerNextBout(r4b1);
        r2b1.setLoserNextBout(r3b4);
        r2b2.setWinnerNextBout(r4b1);
        r2b2.setLoserNextBout(r3b3);
        r2b3.setWinnerNextBout(r4b2);
        r2b3.setLoserNextBout(r3b2);
        r2b4.setWinnerNextBout(r4b2);
        r2b4.setLoserNextBout(r3b1);

        r2b5.setWinnerNextBout(r3b1);
        r2b6.setWinnerNextBout(r3b2);
        r2b7.setWinnerNextBout(r3b3);
        r2b8.setWinnerNextBout(r3b4);

        r3b1.setWinnerNextBout(r4b3);
        r3b2.setWinnerNextBout(r4b3);
        r3b3.setWinnerNextBout(r4b4);
        r3b4.setWinnerNextBout(r4b4);

        r4b1.setWinnerNextBout(r6b1);
        r4b1.setLoserNextBout(r5b1);
        r4b2.setWinnerNextBout(r6b1);
        r4b2.setLoserNextBout(r5b2);
        r4b3.setWinnerNextBout(r5b1);
        r4b4.setWinnerNextBout(r5b2);

        r5b1.setWinnerNextBout(r6b2);
        r5b2.setWinnerNextBout(r6b2);

        if (secondPlaceChallengeEnabled) {
            r6b1.setLoserNextBout(r7b1);
            r6b2.setWinnerNextBout(r7b1);
        }

        if (fifthPlaceEnabled) {
            r5b1.setLoserNextBout(r6b3);
            r5b2.setLoserNextBout(r6b3);
        }

        g.setBouts(bList);
        g.setNumRounds(rounds);
    }
}
