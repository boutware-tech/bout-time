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


package bouttime.boutmaker.bracket32;

import bouttime.boutmaker.bracket4.Bracket4BoutMaker;
import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * A utility class to make bouts for a 32-man bracket.
 */
public class Bracket32BoutMaker {
    static Logger logger = Logger.getLogger(Bracket32BoutMaker.class);
    
    protected Bout r1b1;    protected Bout r1b2;    protected Bout r1b3;
    protected Bout r1b4;    protected Bout r1b5;    protected Bout r1b6;
    protected Bout r1b7;    protected Bout r1b8;    protected Bout r1b9;
    protected Bout r1b10;   protected Bout r1b11;   protected Bout r1b12;
    protected Bout r1b13;   protected Bout r1b14;   protected Bout r1b15;
    protected Bout r1b16;   protected Bout r2b1;    protected Bout r2b2;
    protected Bout r2b3;    protected Bout r2b4;    protected Bout r2b5;
    protected Bout r2b6;    protected Bout r2b7;    protected Bout r2b8;
    protected Bout r2b9;    protected Bout r2b10;   protected Bout r2b11;
    protected Bout r2b12;   protected Bout r2b13;   protected Bout r2b14;
    protected Bout r2b15;   protected Bout r2b16;   protected Bout r3b1;
    protected Bout r3b2;    protected Bout r3b3;    protected Bout r3b4;
    protected Bout r3b5;    protected Bout r3b6;    protected Bout r3b7;
    protected Bout r3b8;    protected Bout r4b1;    protected Bout r4b2;
    protected Bout r4b3;    protected Bout r4b4;    protected Bout r4b5;
    protected Bout r4b6;    protected Bout r4b7;    protected Bout r4b8;
    protected Bout r5b1;    protected Bout r5b2;    protected Bout r5b3;
    protected Bout r5b4;    protected Bout r6b1;    protected Bout r6b2;
    protected Bout r6b3;    protected Bout r6b4;    protected Bout r7b1;
    protected Bout r7b2;    protected Bout r8b1;    protected Bout r8b2;
    protected Bout r8b3;    protected Bout r9b1;

    protected Wrestler w1;       protected Wrestler w2;
    protected Wrestler w3;       protected Wrestler w4;
    protected Wrestler w5;       protected Wrestler w6;
    protected Wrestler w7;       protected Wrestler w8;
    protected Wrestler w9;       protected Wrestler w10;
    protected Wrestler w11;       protected Wrestler w12;
    protected Wrestler w13;       protected Wrestler w14;
    protected Wrestler w15;       protected Wrestler w16;
    protected Wrestler w17;       protected Wrestler w18;
    protected Wrestler w19;       protected Wrestler w20;
    protected Wrestler w21;       protected Wrestler w22;
    protected Wrestler w23;       protected Wrestler w24;
    protected Wrestler w25;       protected Wrestler w26;
    protected Wrestler w27;       protected Wrestler w28;
    protected Wrestler w29;       protected Wrestler w30;
    protected Wrestler w31;       protected Wrestler w32;

    public Bracket32BoutMaker() {}

    public void makeBouts(Group g, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size " + g.getNumWrestlers());

        getWrestlerSeedValues(g);
        ArrayList<Bout> bList = new ArrayList<Bout>();
        int rounds = 8;

        bList.addAll(makeRound1Bouts(g, dummy));
        bList.addAll(makeRound2Bouts(g, dummy));
        bList.addAll(makeRound3Bouts(g, dummy));
        bList.addAll(makeRound4Bouts(g, dummy));
        bList.addAll(makeRound5Bouts(g, dummy));
        bList.addAll(makeRound6Bouts(g, dummy));
        bList.addAll(makeRound7Bouts(g, dummy));
        bList.addAll(makeRound8Bouts(g, dummy, fifthPlaceEnabled));
        if (secondPlaceChallengeEnabled && (g.getWrestlers().size() > 2)) {
            bList.addAll(makeRound9Bouts(g, dummy));
            rounds = 9;
        } else {
            this.r9b1 = null;
        }

        linkBouts();
        g.setBouts(bList);
        g.setNumRounds(rounds);
        g.setBracketType(Group.BRACKET_TYPE_32MAN_DOUBLE);

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
        this.w9 = g.getWrestlerAtSeed(9);
        this.w10 = g.getWrestlerAtSeed(10);
        this.w11 = g.getWrestlerAtSeed(11);
        this.w12 = g.getWrestlerAtSeed(12);
        this.w13 = g.getWrestlerAtSeed(13);
        this.w14 = g.getWrestlerAtSeed(14);
        this.w15 = g.getWrestlerAtSeed(15);
        this.w16 = g.getWrestlerAtSeed(16);
        this.w17 = g.getWrestlerAtSeed(17);
        this.w18 = g.getWrestlerAtSeed(18);
        this.w19 = g.getWrestlerAtSeed(19);
        this.w20 = g.getWrestlerAtSeed(20);
        this.w21 = g.getWrestlerAtSeed(21);
        this.w22 = g.getWrestlerAtSeed(22);
        this.w23 = g.getWrestlerAtSeed(23);
        this.w24 = g.getWrestlerAtSeed(24);
        this.w25 = g.getWrestlerAtSeed(25);
        this.w26 = g.getWrestlerAtSeed(26);
        this.w27 = g.getWrestlerAtSeed(27);
        this.w28 = g.getWrestlerAtSeed(28);
        this.w29 = g.getWrestlerAtSeed(29);
        this.w30 = g.getWrestlerAtSeed(30);
        this.w31 = g.getWrestlerAtSeed(31);
        this.w32 = g.getWrestlerAtSeed(32);
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

        isBye = ((this.w1 == null) || (this.w32 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b1 = new Bout(this.w1, this.w32, g, Bout.ROUND_1, 1, "", isBye, false);
        bList.add(this.r1b1);

        isBye = ((this.w17 == null) || (this.w16 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b2 = new Bout(this.w17, this.w16, g, Bout.ROUND_1, 2, "", isBye, false);
        bList.add(this.r1b2);

        isBye = ((this.w9 == null) || (this.w24 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b3 = new Bout(this.w9, this.w24, g, Bout.ROUND_1, 3, "", isBye, false);
        bList.add(this.r1b3);

        isBye = ((this.w25 == null) || (this.w8 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b4 = new Bout(this.w25, this.w8, g, Bout.ROUND_1, 4, "", isBye, false);
        bList.add(this.r1b4);

        isBye = ((this.w5 == null) || (this.w28 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b5 = new Bout(this.w5, this.w28, g, Bout.ROUND_1, 5, "", isBye, false);
        bList.add(this.r1b5);

        isBye = ((this.w21 == null) || (this.w12 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b6 = new Bout(this.w21, this.w12, g, Bout.ROUND_1, 6, "", isBye, false);
        bList.add(this.r1b6);

        isBye = ((this.w13 == null) || (this.w20 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b7 = new Bout(this.w13, this.w20, g, Bout.ROUND_1, 7, "", isBye, false);
        bList.add(this.r1b7);

        isBye = ((this.w29 == null) || (this.w4 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b8 = new Bout(this.w29, this.w4, g, Bout.ROUND_1, 8, "", isBye, false);
        bList.add(this.r1b8);

        isBye = ((this.w3 == null) || (this.w30 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b9 = new Bout(this.w3, this.w30, g, Bout.ROUND_1, 9, "", isBye, false);
        bList.add(this.r1b9);

        isBye = ((this.w19 == null) || (this.w14 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b10 = new Bout(this.w19, this.w14, g, Bout.ROUND_1, 10, "", isBye, false);
        bList.add(this.r1b10);

        isBye = ((this.w11 == null) || (this.w22 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b11 = new Bout(this.w11, this.w22, g, Bout.ROUND_1, 11, "", isBye, false);
        bList.add(this.r1b11);

        isBye = ((this.w27 == null) || (this.w6 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b12 = new Bout(this.w27, this.w6, g, Bout.ROUND_1, 12, "", isBye, false);
        bList.add(this.r1b12);

        isBye = ((this.w7 == null) || (this.w26 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b13 = new Bout(this.w7, this.w26, g, Bout.ROUND_1, 13, "", isBye, false);
        bList.add(this.r1b13);

        isBye = ((this.w23 == null) || (this.w10 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b14 = new Bout(this.w23, this.w10, g, Bout.ROUND_1, 14, "", isBye, false);
        bList.add(this.r1b14);

        isBye = ((this.w15 == null) || (this.w18 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b15 = new Bout(this.w15, this.w18, g, Bout.ROUND_1, 15, "", isBye, false);
        bList.add(this.r1b15);

        isBye = ((this.w31 == null) || (this.w2 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b16 = new Bout(this.w31, this.w2, g, Bout.ROUND_1, 16, "", isBye, false);
        bList.add(this.r1b16);

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
        this.r2b1 = new Bout(red, green, g, Bout.ROUND_2, 1, "H", isBye, false);
        bList.add(this.r2b1);

        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b3, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b4, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b2 = new Bout(red, green, g, Bout.ROUND_2, 2, "I", isBye, false);
        bList.add(this.r2b2);

        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b5, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b6, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b3 = new Bout(red, green, g, Bout.ROUND_2, 3, "J", isBye, false);
        bList.add(this.r2b3);

        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b7, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b8, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b4 = new Bout(red, green, g, Bout.ROUND_2, 4, "K", isBye, false);
        bList.add(this.r2b4);

        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b9, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b10, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b5 = new Bout(red, green, g, Bout.ROUND_2, 5, "L", isBye, false);
        bList.add(this.r2b5);

        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b11, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b12, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b6 = new Bout(red, green, g, Bout.ROUND_2, 6, "M", isBye, false);
        bList.add(this.r2b6);

        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b13, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b14, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b7 = new Bout(red, green, g, Bout.ROUND_2, 7, "N", isBye, false);
        bList.add(this.r2b7);

        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b15, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r1b16, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b8 = new Bout(red, green, g, Bout.ROUND_2, 8, "O", isBye, false);
        bList.add(this.r2b8);

        ////////////
        // Back-side
        ////////////
        isBye = (this.r1b1.isBye() || this.r1b2.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b9 = new Bout(d, d, g, Bout.ROUND_2, 9, "", isBye, true);
        bList.add(this.r2b9);

        isBye = (this.r1b3.isBye() || this.r1b4.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b10 = new Bout(d, d, g, Bout.ROUND_2, 10, "", isBye, true);
        bList.add(this.r2b10);

        isBye = (this.r1b5.isBye() || this.r1b6.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b11 = new Bout(d, d, g, Bout.ROUND_2, 11, "", isBye, true);
        bList.add(this.r2b11);

        isBye = (this.r1b7.isBye() || this.r1b8.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b12 = new Bout(d, d, g, Bout.ROUND_2, 12, "", isBye, true);
        bList.add(this.r2b12);

        isBye = (this.r1b9.isBye() || this.r1b10.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b13 = new Bout(d, d, g, Bout.ROUND_2, 13, "", isBye, true);
        bList.add(this.r2b13);

        isBye = (this.r1b11.isBye() || this.r1b12.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b14 = new Bout(d, d, g, Bout.ROUND_2, 14, "", isBye, true);
        bList.add(this.r2b14);

        isBye = (this.r1b13.isBye() || this.r1b14.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b15 = new Bout(d, d, g, Bout.ROUND_2, 15, "", isBye, true);
        bList.add(this.r2b15);

        isBye = (this.r1b15.isBye() || this.r1b16.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r2b16 = new Bout(d, d, g, Bout.ROUND_2, 16, "", isBye, true);
        bList.add(this.r2b16);

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

        isBye = ((r1b1.isBye() && r1b2.isBye()) || r2b8.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r3b1 = new Bout(d, d, g, Bout.ROUND_3, 1, "", isBye, true);
        bList.add(this.r3b1);

        isBye = ((r1b3.isBye() && r1b4.isBye()) || r2b7.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r3b2 = new Bout(d, d, g, Bout.ROUND_3, 2, "", isBye, true);
        bList.add(this.r3b2);

        isBye = ((r1b5.isBye() && r1b6.isBye()) || r2b6.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r3b3 = new Bout(d, d, g, Bout.ROUND_3, 3, "", isBye, true);
        bList.add(this.r3b3);

        isBye = ((r1b7.isBye() && r1b8.isBye()) || r2b5.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r3b4 = new Bout(d, d, g, Bout.ROUND_3, 4, "", isBye, true);
        bList.add(this.r3b4);

        isBye = ((r1b9.isBye() && r1b10.isBye()) || r2b4.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r3b5 = new Bout(d, d, g, Bout.ROUND_3, 5, "", isBye, true);
        bList.add(this.r3b5);

        isBye = ((r1b11.isBye() && r1b12.isBye()) || r2b3.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r3b6 = new Bout(d, d, g, Bout.ROUND_3, 6, "", isBye, true);
        bList.add(this.r3b6);

        isBye = ((r1b13.isBye() && r1b14.isBye()) || r2b2.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r3b7 = new Bout(d, d, g, Bout.ROUND_3, 7, "", isBye, true);
        bList.add(this.r3b7);

        isBye = ((r1b15.isBye() && r1b16.isBye()) || r2b1.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r3b8 = new Bout(d, d, g, Bout.ROUND_3, 8, "", isBye, true);
        bList.add(this.r3b8);

        logger.debug("Round 3 byes = " + byeCount);
        return bList;
    }

    /**
     * Create bouts for round 4 for the given group.
     * @param g Group to make bouts for.
     * @param d A dummy wrestler to use when creating the bouts.
     * @return A list of bouts for this round.
     */
    protected List<Bout> makeRound4Bouts(Group g, Wrestler d) {
        int byeCount = 0;
        List<Bout> bList = new ArrayList<Bout>();
        Wrestler red;
        Wrestler green;
        boolean isBye;

        /////////////
        // Front-side
        /////////////
        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r2b1, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r2b2, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r4b1 = new Bout(red, green, g, Bout.ROUND_4, 1, "D", isBye, false);
        bList.add(this.r4b1);

        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r2b3, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r2b4, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r4b2 = new Bout(red, green, g, Bout.ROUND_4, 2, "E", isBye, false);
        bList.add(this.r4b2);

        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r2b5, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r2b6, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r4b3 = new Bout(red, green, g, Bout.ROUND_4, 3, "F", isBye, false);
        bList.add(this.r4b3);

        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r2b7, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r2b8, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r4b4 = new Bout(red, green, g, Bout.ROUND_4, 4, "G", isBye, false);
        bList.add(this.r4b4);

        ////////////
        // Back-side
        ////////////
        isBye = ((r1b1.isBye() && r1b2.isBye() && r2b8.isBye() ||
                 (r1b3.isBye() && r1b4.isBye() && r2b7.isBye()))) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r4b5 = new Bout(d, d, g, Bout.ROUND_4, 5, "", isBye, true);
        bList.add(this.r4b5);

        isBye = ((r1b5.isBye() && r1b5.isBye() && r2b6.isBye() ||
                 (r1b7.isBye() && r1b8.isBye() && r2b5.isBye()))) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r4b6 = new Bout(d, d, g, Bout.ROUND_4, 6, "", isBye, true);
        bList.add(this.r4b6);

        isBye = ((r1b9.isBye() && r1b10.isBye() && r2b4.isBye() ||
                 (r1b11.isBye() && r1b12.isBye() && r2b3.isBye()))) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r4b7 = new Bout(d, d, g, Bout.ROUND_4, 7, "", isBye, true);
        bList.add(this.r4b7);

        isBye = ((r1b13.isBye() && r1b14.isBye() && r2b2.isBye() ||
                 (r1b15.isBye() && r1b16.isBye() && r2b1.isBye()))) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r4b8 = new Bout(d, d, g, Bout.ROUND_4, 8, "", isBye, true);
        bList.add(this.r4b8);

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
        int byeCount = 0;
        List<Bout> bList = new ArrayList<Bout>();
        boolean isBye;

        isBye = ((r1b1.isBye() && r1b2.isBye() && r2b8.isBye() && r1b3.isBye() &&
                r1b4.isBye() && r2b7.isBye()) || r4b2.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r5b1 = new Bout(d, d, g, Bout.ROUND_5, 1, "", isBye, true);
        bList.add(this.r5b1);

        isBye = ((r1b5.isBye() && r1b5.isBye() && r2b6.isBye()  && r1b7.isBye() &&
                r1b8.isBye() && r2b5.isBye()) || r4b1.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r5b2 = new Bout(d, d, g, Bout.ROUND_5, 2, "", isBye, true);
        bList.add(this.r5b2);

        isBye = ((r1b9.isBye() && r1b10.isBye() && r2b4.isBye()  && r1b11.isBye() &&
                r1b12.isBye() && r2b3.isBye()) || r4b4.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r5b3 = new Bout(d, d, g, Bout.ROUND_5, 3, "", isBye, true);
        bList.add(this.r5b3);

        isBye = ((r1b13.isBye() && r1b14.isBye() && r2b2.isBye() && r1b15.isBye() &&
                r1b16.isBye() && r2b1.isBye()) || r4b3.isBye()) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r5b4 = new Bout(d, d, g, Bout.ROUND_5, 4, "", isBye, true);
        bList.add(this.r5b4);

        logger.debug("Round 5 byes = " + byeCount);
        return bList;
    }

    /**
     * Create bouts for round 6 for the given group.
     * @param g Group to make bouts for.
     * @param d A dummy wrestler to use when creating the bouts.
     * @return A list of bouts for this round.
     */
    protected List<Bout> makeRound6Bouts(Group g, Wrestler d) {
        int byeCount = 0;
        List<Bout> bList = new ArrayList<Bout>();
        Wrestler red;
        Wrestler green;
        boolean isBye;

        /////////////
        // Front-side
        /////////////
        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r4b1, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r4b2, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r6b1 = new Bout(red, green, g, Bout.ROUND_6, 1, "B", isBye, false);
        bList.add(this.r6b1);

        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r4b3, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r4b4, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r6b2 = new Bout(red, green, g, Bout.ROUND_6, 2, "C", isBye, false);
        bList.add(this.r6b2);

        ////////////
        // Back-side
        ////////////
        isBye = ((r1b1.isBye() && r1b2.isBye() && r1b3.isBye() && r1b4.isBye() &&
                  r2b8.isBye() && r2b7.isBye() && r4b2.isBye()) ||
                 (r1b5.isBye() && r1b6.isBye() && r1b7.isBye() && r1b8.isBye() &&
                  r2b6.isBye() && r2b5.isBye() && r4b1.isBye())) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r6b3 = new Bout(d, d, g, Bout.ROUND_6, 3, "", isBye, true);
        bList.add(this.r6b3);

        isBye = ((r1b9.isBye() && r1b10.isBye() && r1b11.isBye() && r1b12.isBye() &&
                  r2b4.isBye() && r2b3.isBye() && r4b4.isBye()) ||
                 (r1b13.isBye() && r1b14.isBye() && r1b15.isBye() && r1b16.isBye() &&
                  r2b2.isBye() && r2b1.isBye() && r4b3.isBye())) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r6b4 = new Bout(d, d, g, Bout.ROUND_6, 4, "", isBye, true);
        bList.add(this.r6b4);

        logger.debug("Round 6 byes = " + byeCount);
        return bList;
    }

    /**
     * Create bouts for round 7 for the given group.
     * @param g Group to make bouts for.
     * @param d A dummy wrestler to use when creating the bouts.
     * @return A list of bouts for this round.
     */
    protected List<Bout> makeRound7Bouts(Group g, Wrestler d) {
        int byeCount = 0;
        List<Bout> bList = new ArrayList<Bout>();
        boolean isBye;

        isBye = (r6b1.isBye() ||
                 (r1b1.isBye() && r1b2.isBye() && r1b3.isBye() && r1b4.isBye() &&
                  r2b8.isBye() && r2b7.isBye() && r4b2.isBye() &&
                  r1b5.isBye() && r1b6.isBye() && r1b7.isBye() && r1b8.isBye() &&
                  r2b6.isBye() && r2b5.isBye() && r4b1.isBye())) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r7b1 = new Bout(d, d, g, Bout.ROUND_7, 1, "X", isBye, true);
        bList.add(this.r7b1);

        isBye = (r6b2.isBye() ||
                 (r1b9.isBye() && r1b10.isBye() && r1b11.isBye() && r1b12.isBye() &&
                  r2b4.isBye() && r2b3.isBye() && r4b4.isBye() &&
                  r1b13.isBye() && r1b14.isBye() && r1b15.isBye() && r1b16.isBye() &&
                  r2b2.isBye() && r2b1.isBye() && r4b3.isBye())) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r7b2 = new Bout(d, d, g, Bout.ROUND_7, 2, "Y", isBye, true);
        bList.add(this.r7b2);

        logger.debug("Round 7 byes = " + byeCount);
        return bList;
    }

    /**
     * Create bouts for round 8 for the given group.
     * @param g Group to make bouts for.
     * @param d A dummy wrestler to use when creating the bouts.
     * @param doFifthPlace If true, create a bout for 5th place.
     * @return A list of bouts for this round.
     */
    protected List<Bout> makeRound8Bouts(Group g, Wrestler d,
            Boolean doFifthPlace) {
        
        int byeCount = 0;
        List<Bout> bList = new ArrayList<Bout>();
        Wrestler red;
        Wrestler green;
        boolean isBye;
        int numWrestlers = g.getNumWrestlers();

        ///////////////
        // Championship
        ///////////////
        red = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r6b1, d);
        green = Bracket4BoutMaker.getAutoAdvancingWrestler(this.r6b2, d);
        isBye = ((red == null) || (green == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r8b1 = new Bout(red, green, g, Bout.ROUND_8, 1, Bout.FIRST_PLACE, isBye, false);
        bList.add(this.r8b1);

        ////////////
        // 3rd place
        ////////////
        isBye = (numWrestlers < 4) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r8b2 = new Bout(d, d, g, Bout.ROUND_8, 2, Bout.THIRD_PLACE, isBye, true);
        bList.add(this.r8b2);

        ////////////
        // 5th place
        ////////////
        if (doFifthPlace && (numWrestlers > 5)) {
            isBye = (this.r7b1.isBye() || this.r7b2.isBye()) ? true : false;
            byeCount += (isBye) ? 1 : 0;
            this.r8b3 = new Bout(red, green, g, Bout.ROUND_8, 3, Bout.FIFTH_PLACE, isBye, true);
            bList.add(this.r8b3);
        } else {
            this.r8b3 = null;
        }

        logger.debug("Round 8 byes = " + byeCount);
        return bList;
    }

    /**
     * Create bouts for round 9 for the given group.
     * @param g Group to make bouts for.
     * @param d A dummy wrestler to use when creating the bouts.
     * @return A list of bouts for this round.
     */
    protected List<Bout> makeRound9Bouts(Group g, Wrestler d) {
        List<Bout> bList = new ArrayList<Bout>();

        this.r9b1 = new Bout(d, d, g, Bout.ROUND_9, 1, Bout.SECOND_PLACE, false, true);
        bList.add(this.r9b1);

        return bList;
    }

    /**
     * Link the bouts together so the winners and losers will advance.
     */
    protected void linkBouts() {
        // Round 1
        this.r1b1.setWinnerNextBout(this.r2b1);
        this.r1b1.setLoserNextBout(this.r2b9);

        this.r1b2.setWinnerNextBout(this.r2b1);
        this.r1b2.setLoserNextBout(this.r2b9);

        this.r1b3.setWinnerNextBout(this.r2b2);
        this.r1b3.setLoserNextBout(this.r2b10);

        this.r1b4.setWinnerNextBout(this.r2b2);
        this.r1b4.setLoserNextBout(this.r2b10);

        this.r1b5.setWinnerNextBout(this.r2b3);
        this.r1b5.setLoserNextBout(this.r2b11);

        this.r1b6.setWinnerNextBout(this.r2b3);
        this.r1b6.setLoserNextBout(this.r2b11);

        this.r1b7.setWinnerNextBout(this.r2b4);
        this.r1b7.setLoserNextBout(this.r2b12);

        this.r1b8.setWinnerNextBout(this.r2b4);
        this.r1b8.setLoserNextBout(this.r2b12);

        this.r1b9.setWinnerNextBout(this.r2b5);
        this.r1b9.setLoserNextBout(this.r2b13);

        this.r1b10.setWinnerNextBout(this.r2b5);
        this.r1b10.setLoserNextBout(this.r2b13);

        this.r1b11.setWinnerNextBout(this.r2b6);
        this.r1b11.setLoserNextBout(this.r2b14);

        this.r1b12.setWinnerNextBout(this.r2b6);
        this.r1b12.setLoserNextBout(this.r2b14);

        this.r1b13.setWinnerNextBout(this.r2b7);
        this.r1b13.setLoserNextBout(this.r2b15);

        this.r1b14.setWinnerNextBout(this.r2b7);
        this.r1b14.setLoserNextBout(this.r2b15);

        this.r1b15.setWinnerNextBout(this.r2b8);
        this.r1b15.setLoserNextBout(this.r2b16);

        this.r1b16.setWinnerNextBout(this.r2b8);
        this.r1b16.setLoserNextBout(this.r2b16);

        // Round 2 : front-side
        this.r2b1.setWinnerNextBout(this.r4b1);
        this.r2b1.setLoserNextBout(this.r3b8);

        this.r2b2.setWinnerNextBout(this.r4b1);
        this.r2b2.setLoserNextBout(this.r3b7);

        this.r2b3.setWinnerNextBout(this.r4b2);
        this.r2b3.setLoserNextBout(this.r3b6);

        this.r2b4.setWinnerNextBout(this.r4b2);
        this.r2b4.setLoserNextBout(this.r3b5);

        this.r2b5.setWinnerNextBout(this.r4b3);
        this.r2b5.setLoserNextBout(this.r3b4);

        this.r2b6.setWinnerNextBout(this.r4b3);
        this.r2b6.setLoserNextBout(this.r3b3);

        this.r2b7.setWinnerNextBout(this.r4b4);
        this.r2b7.setLoserNextBout(this.r3b2);

        this.r2b8.setWinnerNextBout(this.r4b4);
        this.r2b8.setLoserNextBout(this.r3b1);

        // Round 2 : back-side
        this.r2b9.setWinnerNextBout(this.r3b1);
        this.r2b9.setLoserNextBout(null);

        this.r2b10.setWinnerNextBout(this.r3b2);
        this.r2b10.setLoserNextBout(null);

        this.r2b11.setWinnerNextBout(this.r3b3);
        this.r2b11.setLoserNextBout(null);

        this.r2b12.setWinnerNextBout(this.r3b4);
        this.r2b12.setLoserNextBout(null);

        this.r2b13.setWinnerNextBout(this.r3b5);
        this.r2b13.setLoserNextBout(null);

        this.r2b14.setWinnerNextBout(this.r3b6);
        this.r2b14.setLoserNextBout(null);

        this.r2b15.setWinnerNextBout(this.r3b7);
        this.r2b15.setLoserNextBout(null);

        this.r2b16.setWinnerNextBout(this.r3b8);
        this.r2b16.setLoserNextBout(null);

        // Round 3 (back-side)
        this.r3b1.setWinnerNextBout(this.r4b5);
        this.r3b1.setLoserNextBout(null);

        this.r3b2.setWinnerNextBout(this.r4b5);
        this.r3b2.setLoserNextBout(null);

        this.r3b3.setWinnerNextBout(this.r4b6);
        this.r3b3.setLoserNextBout(null);

        this.r3b4.setWinnerNextBout(this.r4b6);
        this.r3b4.setLoserNextBout(null);

        this.r3b5.setWinnerNextBout(this.r4b7);
        this.r3b5.setLoserNextBout(null);

        this.r3b6.setWinnerNextBout(this.r4b7);
        this.r3b6.setLoserNextBout(null);

        this.r3b7.setWinnerNextBout(this.r4b8);
        this.r3b7.setLoserNextBout(null);

        this.r3b8.setWinnerNextBout(this.r4b8);
        this.r3b8.setLoserNextBout(null);

        // Round 4 : front-side
        this.r4b1.setWinnerNextBout(this.r6b1);
        this.r4b1.setLoserNextBout(this.r5b2);

        this.r4b2.setWinnerNextBout(this.r6b1);
        this.r4b2.setLoserNextBout(this.r5b1);

        this.r4b3.setWinnerNextBout(this.r6b2);
        this.r4b3.setLoserNextBout(this.r5b4);

        this.r4b4.setWinnerNextBout(this.r6b2);
        this.r4b4.setLoserNextBout(this.r5b3);

        //Round 4 : back-side
        this.r4b5.setWinnerNextBout(this.r5b1);
        this.r4b5.setLoserNextBout(null);

        this.r4b6.setWinnerNextBout(this.r5b2);
        this.r4b6.setLoserNextBout(null);

        this.r4b7.setWinnerNextBout(this.r5b3);
        this.r4b7.setLoserNextBout(null);

        this.r4b8.setWinnerNextBout(this.r5b4);
        this.r4b8.setLoserNextBout(null);

        // Round 5 (back-side)
        this.r5b1.setWinnerNextBout(this.r6b3);
        this.r5b1.setLoserNextBout(null);

        this.r5b2.setWinnerNextBout(this.r6b3);
        this.r5b2.setLoserNextBout(null);

        this.r5b3.setWinnerNextBout(this.r6b4);
        this.r5b3.setLoserNextBout(null);

        this.r5b4.setWinnerNextBout(this.r6b4);
        this.r5b4.setLoserNextBout(null);

        // Round 6 : front-side
        this.r6b1.setWinnerNextBout(this.r8b1);
        this.r6b1.setLoserNextBout(this.r7b2);

        this.r6b2.setWinnerNextBout(this.r8b1);
        this.r6b2.setLoserNextBout(this.r7b1);

        //Round 6 : back-side
        this.r6b3.setWinnerNextBout(this.r7b1);
        this.r6b3.setLoserNextBout(null);

        this.r6b4.setWinnerNextBout(this.r7b2);
        this.r6b4.setLoserNextBout(null);

        // Round 7 (back-side)
        this.r7b1.setWinnerNextBout(this.r8b2);
        this.r7b1.setLoserNextBout(this.r8b3);

        this.r7b2.setWinnerNextBout(this.r8b2);
        this.r7b2.setLoserNextBout(this.r8b3);

        // Round 8 : front-side (championship)
        this.r8b1.setWinnerNextBout(null);  // champion
        this.r8b1.setLoserNextBout(this.r9b1);

        // Round 8 : back-side (3rd place)
        this.r8b2.setWinnerNextBout(this.r9b1);
        this.r8b2.setLoserNextBout(null);   // 4th place
    }
}
