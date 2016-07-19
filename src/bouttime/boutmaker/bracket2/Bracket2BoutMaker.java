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

package bouttime.boutmaker.bracket2;

import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * A utility class to make bouts for a 2-man bracket.
 */
public class Bracket2BoutMaker {
    static Logger logger = Logger.getLogger(Bracket2BoutMaker.class);

    protected Bout r1b1;

    protected Wrestler w1;       protected Wrestler w2;

    public void makeBouts(Group g, Wrestler dummy) {

        logger.trace("Making bouts for bracket group of size " + g.getNumWrestlers());

        getWrestlerSeedValues(g);
        ArrayList<Bout> bList = new ArrayList<Bout>();
        int rounds = 1;

        bList.addAll(makeRound1Bouts(g, dummy));

        g.setBouts(bList);
        g.setNumRounds(rounds);
        g.setBracketType(Group.BRACKET_TYPE_2MAN);

        logger.trace("Total bouts is [" + g.getNumBouts() + "]");
        return;
    }

    protected void getWrestlerSeedValues(Group g) {
        this.w1 = g.getWrestlerAtSeed(1);
        this.w2 = g.getWrestlerAtSeed(2);
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

        isBye = ((this.w1 == null) || (this.w2 == null)) ? true : false;
        byeCount += (isBye) ? 1 : 0;
        this.r1b1 = new Bout(this.w1, this.w2, g, Bout.ROUND_1, 1, Bout.FIRST_PLACE, isBye, false);
        bList.add(this.r1b1);

        logger.debug("Round 1 byes = " + byeCount);
        return bList;
    }
}
