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

package bouttime.boutmaker.bracket8;

import bouttime.boutmaker.bracket4.Bracket4BoutMaker;
import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * A utility class to make bouts for a 8-man bracket.
 */
public class Bracket8BoutMaker {
    static Logger logger = Logger.getLogger(Bracket8BoutMaker.class);

    protected Bout r1b1;    protected Bout r1b2;    protected Bout r1b3;
    protected Bout r1b4;    protected Bout r2b1;    protected Bout r2b2;
    protected Bout r2b3;    protected Bout r2b4;    protected Bout r3b1;
    protected Bout r3b2;    protected Bout r4b1;    protected Bout r4b2;
    protected Bout r4b3;    protected Bout r5b1;

    protected Wrestler w1;       protected Wrestler w2;
    protected Wrestler w3;       protected Wrestler w4;
    protected Wrestler w5;       protected Wrestler w6;
    protected Wrestler w7;       protected Wrestler w8;

    public void makeBouts(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size " + g.getNumWrestlers());

        getWrestlerSeedValues(g);
        ArrayList<Bout> bList = new ArrayList<Bout>();
        int rounds = 4;

        bList.addAll(makeRound1Bouts(g, dummy));
        bList.addAll(makeRound2Bouts(g, dummy));
        bList.addAll(makeRound3Bouts(g, dummy));
        bList.addAll(makeRound4Bouts(g, dummy, fifthPlaceEnabled));
        if (secondPlaceChallengeEnabled && (g.getWrestlers().size() > 2)) {
            bList.addAll(makeRound5Bouts(g, dummy));
            rounds = 5;
        } else {
            this.r5b1 = null;
        }

        linkBouts();
        g.setBouts(bList);
        g.setNumRounds(rounds);
        g.setBracketType(Group.BRACKET_TYPE_8MAN_DOUBLE);

        logger.trace("Total bouts is [" + g.getNumBouts() + "]");
        return;
    }

    protected void getWrestlerSeedValues(Group g) {
        this.w1 = g.getWrestlerAtSeed(1);
        this.w2 = g.getWrestlerAtSeed(2);
        this.w3 = g.getWrestlerAtSeed(3);
        this.w4 = g.getWrestlerAtSeed(4);
        this.w5 = g.getWrestlerAtSeed(5);
        this.w6 = g.getWrestlerAtSeed(6);
        this.w7 = g.getWrestlerAtSeed(7);
        this.w8 = g.getWrestlerAtSeed(8);
    }

    /**
     * Create bouts for round 1 for the given group.
     * @param g Group to make bouts for.
     * @param d A dummy wrestler to use when creating the bouts.
     * @return A list of bouts for this round.
     */
    protected List<Bout> makeRound1Bouts(Group g, Wrestler d) {
        int byeCount = 0;
        boolean isBye;
        List<Bout> bList = new ArrayList<Bout>();

        isBye = ((this.w1 == null) || (this.w8 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b1 = new Bout(this.w1, this.w8, g, Bout.ROUND_1, 1, "", isBye, false);
        bList.add(this.r1b1);

        isBye = ((this.w5 == null) || (this.w4 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b2 = new Bout(this.w5, this.w4, g, Bout.ROUND_1, 2, "", isBye, false);
        bList.add(this.r1b2);

        isBye = ((this.w3 == null) || (this.w6 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b3 = new Bout(this.w3, this.w6, g, Bout.ROUND_1, 3, "", isBye, false);
        bList.add(this.r1b3);

        isBye = ((this.w7 == null) || (this.w2 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b4 = new Bout(this.w7, this.w2, g, Bout.ROUND_1, 4, "", isBye, false);
        bList.add(this.r1b4);

        logger.debug("Round 1 byes = " + byeCount);
        return bList;
    }

    /**
     * Create bouts for round 2 for the given group.
     * @param g Group to make bouts for.
     * @param d A dummy wrestler to use when creating the bouts.
     * @return A list of bouts for this round.
     */
    protected List<Bout> makeRound2Bouts(Group g, Wrestler d) {
        int byeCount = 0;
        List<Bout> bList = new ArrayList<Bout>();
        Wrestler red;
        Wrestler green;
        boolean isBye;

        /////////////
        // Front-side
        /////////////
        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b1, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b2, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b1 = new Bout(red, green, g, Bout.ROUND_2, 1, "B", isBye, false);
        bList.add(this.r2b1);

        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b3, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b4, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b2 = new Bout(red, green, g, Bout.ROUND_2, 2, "C", isBye, false);
        bList.add(this.r2b2);

        ////////////
        // Back-side
        ////////////
        isBye = (this.r1b1.isBye() || this.r1b2.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b3 = new Bout(d, d, g, Bout.ROUND_2, 3, "", isBye, true);
        bList.add(this.r2b3);

        isBye = (this.r1b3.isBye() || this.r1b4.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b4 = new Bout(d, d, g, Bout.ROUND_2, 4, "", isBye, true);
        bList.add(this.r2b4);

        logger.debug("Round 2 byes = " + byeCount);
        return bList;
    }

    /**
     * Create bouts for round 3 for the given group.
     * @param g Group to make bouts for.
     * @param d A dummy wrestler to use when creating the bouts.
     * @return A list of bouts for this round.
     */
    protected List<Bout> makeRound3Bouts(Group g, Wrestler d) {
        int byeCount = 0;
        List<Bout> bList = new ArrayList<Bout>();
        boolean isBye;

        isBye = ((r1b1.isBye() && r1b2.isBye()) || r2b2.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r3b1 = new Bout(d, d, g, Bout.ROUND_3, 1, "X", isBye, true);
        bList.add(this.r3b1);

        isBye = ((r1b3.isBye() && r1b4.isBye()) || r2b1.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r3b2 = new Bout(d, d, g, Bout.ROUND_3, 2, "Y", isBye, true);
        bList.add(this.r3b2);

        logger.debug("Round 3 byes = " + byeCount);
        return bList;
    }

    /**
     * Create bouts for round 4 for the given group.
     * @param g Group to make bouts for.
     * @param d A dummy wrestler to use when creating the bouts.
     * @return A list of bouts for this round.
     */
    protected List<Bout> makeRound4Bouts(Group g, Wrestler d, boolean doFifthPlace) {
        int byeCount = 0;
        List<Bout> bList = new ArrayList<Bout>();
        Wrestler red;
        Wrestler green;
        boolean isBye;
        int numWrestlers = g.getNumWrestlers();

        ///////////////
        // Championship
        ///////////////
        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r2b1, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r2b2, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r4b1 = new Bout(red, green, g, Bout.ROUND_4, 1, Bout.FIRST_PLACE, isBye, false);
        bList.add(this.r4b1);

        ////////////
        // 3rd place
        ////////////
        isBye = (numWrestlers < 4) ? true : false;
        this.r4b2 = new Bout(d, d, g, Bout.ROUND_4, 2, Bout.THIRD_PLACE, isBye, true);
        bList.add(this.r4b2);

        ////////////
        // 5th place
        ////////////
        if (doFifthPlace && (numWrestlers > 5)) {
            isBye = (this.r3b1.isBye() || this.r3b2.isBye()) ? true : false;
            byeCount += (isBye) ? 1 : 0;
            this.r4b3 = new Bout(red, green, g, Bout.ROUND_4, 3, Bout.FIFTH_PLACE, isBye, true);
            bList.add(this.r4b3);
        } else {
            this.r4b3 = null;
        }

        logger.debug("Round 4 byes = " + byeCount);
        return bList;
    }

    /**
     * Create bouts for round 5 for the given group.
     * @param g Group to make bouts for.
     * @param d A dummy wrestler to use when creating the bouts.
     * @return A list of bouts for this round.
     */
    protected List<Bout> makeRound5Bouts(Group g, Wrestler d) {
        List<Bout> bList = new ArrayList<Bout>();

        this.r5b1 = new Bout(d, d, g, Bout.ROUND_5, 1, Bout.SECOND_PLACE, false, true);
        bList.add(this.r5b1);

        return bList;
    }

    /**
     * Link the bouts together so the winners and losers will advance.
     */
    protected void linkBouts() {
        // Round 1
        this.r1b1.setWinnerNextBout(this.r2b1);
        this.r1b1.setLoserNextBout(this.r2b3);

        this.r1b2.setWinnerNextBout(this.r2b1);
        this.r1b2.setLoserNextBout(this.r2b3);

        this.r1b3.setWinnerNextBout(this.r2b2);
        this.r1b3.setLoserNextBout(this.r2b4);

        this.r1b4.setWinnerNextBout(this.r2b2);
        this.r1b4.setLoserNextBout(this.r2b4);

        // Round 2 : front-side
        this.r2b1.setWinnerNextBout(this.r4b1);
        this.r2b1.setLoserNextBout(this.r3b2);

        this.r2b2.setWinnerNextBout(this.r4b1);
        this.r2b2.setLoserNextBout(this.r3b1);

        // Round 2 : back-side
        this.r2b3.setWinnerNextBout(this.r3b1);
        this.r2b3.setLoserNextBout(null);

        this.r2b4.setWinnerNextBout(this.r3b2);
        this.r2b4.setLoserNextBout(null);

        // Round 3 (back-side)
        this.r3b1.setWinnerNextBout(this.r4b2);
        this.r3b1.setLoserNextBout(this.r4b3);

        this.r3b2.setWinnerNextBout(this.r4b2);
        this.r3b2.setLoserNextBout(this.r4b3);

        // Round 4 : front-side (championship)
        this.r4b1.setWinnerNextBout(null);  // champion
        this.r4b1.setLoserNextBout(this.r5b1);

        // Round 4 : back-side (3rd place)
        this.r4b2.setWinnerNextBout(this.r5b1);
        this.r4b2.setLoserNextBout(null);   // 4th place
    }
}
