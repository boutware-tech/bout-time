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

package bouttime.boutmaker.bracket4;

import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * A utility class to make bouts for a 4-man bracket.
 */
public class Bracket4BoutMaker {
    static Logger logger = Logger.getLogger(Bracket4BoutMaker.class);

    protected Bout r1b1;    protected Bout r1b2;    protected Bout r2b1;
    protected Bout r2b2;    protected Bout r3b1;

    protected Wrestler w1;       protected Wrestler w2;
    protected Wrestler w3;       protected Wrestler w4;

    public void makeBouts(Group g, Boolean secondPlaceChallengeEnabled,
            Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size " + g.getNumWrestlers());

        getWrestlerSeedValues(g);
        ArrayList<Bout> bList = new ArrayList<Bout>();
        int rounds = 2;

        bList.addAll(makeRound1Bouts(g, dummy));
        bList.addAll(makeRound2Bouts(g, dummy));
        if (secondPlaceChallengeEnabled && (g.getWrestlers().size() > 2)) {
            bList.addAll(makeRound3Bouts(g, dummy));
            rounds = 3;
        } else {
            this.r3b1 = null;
        }

        linkBouts();
        g.setBouts(bList);
        g.setNumRounds(rounds);
        g.setBracketType(Group.BRACKET_TYPE_4MAN_DOUBLE);

        logger.trace("Total bouts is [" + g.getNumBouts() + "]");
        return;
    }

    protected void getWrestlerSeedValues(Group g) {
        this.w1 = g.getWrestlerAtSeed(1);
        this.w2 = g.getWrestlerAtSeed(2);
        this.w3 = g.getWrestlerAtSeed(3);
        this.w4 = g.getWrestlerAtSeed(4);
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

        isBye = ((this.w1 == null) || (this.w4 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b1 = new Bout(this.w1, this.w4, g, Bout.ROUND_1, 1, "", isBye, false);
        bList.add(this.r1b1);

        isBye = ((this.w3 == null) || (this.w2 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b2 = new Bout(this.w3, this.w2, g, Bout.ROUND_1, 2, "", isBye, false);
        bList.add(this.r1b2);

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

        ///////////////
        // Championship
        ///////////////
        red = getAutoAdvancingWrestler(this.r1b1, d);
        green = getAutoAdvancingWrestler(this.r1b2, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b1 = new Bout(red, green, g, Bout.ROUND_2, 1, Bout.FIRST_PLACE, isBye, false);
        bList.add(this.r2b1);

        ////////////
        // 3rd place
        ////////////
        isBye = (this.r1b1.isBye() || this.r1b2.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b2 = new Bout(d, d, g, Bout.ROUND_2, 2, Bout.THIRD_PLACE, isBye, true);
        bList.add(this.r2b2);

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
        List<Bout> bList = new ArrayList<Bout>();

        this.r3b1 = new Bout(d, d, g, Bout.ROUND_3, 1, Bout.SECOND_PLACE, false, true);
        bList.add(this.r3b1);

        return bList;
    }

    /**
     * Link the bouts together so the winners and losers will advance.
     */
    protected void linkBouts() {
        this.r1b1.setWinnerNextBout(this.r2b1);
        this.r1b1.setLoserNextBout(this.r2b2);

        this.r1b2.setWinnerNextBout(this.r2b1);
        this.r1b2.setLoserNextBout(this.r2b2);

        this.r2b1.setWinnerNextBout(null);  // champion
        this.r2b1.setLoserNextBout(this.r3b1);

        this.r2b2.setWinnerNextBout(this.r3b1);
        this.r2b2.setLoserNextBout(null);   // 4th place
    }

    public static Wrestler getAutoAdvancingWrestler(Bout b, Wrestler dummy) {
        if (b.isBye()) {
            Wrestler red = b.getRed();
            if (red != null) {
                return red;
            } else {
                Wrestler green = b.getGreen();
                if (green != null) {
                    return green;
                } else {
                    logger.error("Unexpected bout : no red or green wrestler");
                    return null;
                }
            }
        } else {
            return dummy;
        }
    }
}
