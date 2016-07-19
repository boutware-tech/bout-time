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


package bouttime.boutmaker.bracket32;

import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import java.util.List;

/**
 * A utility class to make bouts for a 27-man group in a 32-man bracket.
 * Top 5 seeds have a first round bye.
 * @deprecated Replaced with bouttime.boutmaker.bracket32.Bracket32BoutMaker
 */
public class Bracket27BoutMaker extends Bracket28BoutMaker {

    @Override
    protected List<Bout> makeRound1Bouts(List<Wrestler> wList, Group g, Wrestler d) {
        List<Bout> bList = super.makeRound1Bouts(wList, g, d);
        bList.remove(r1b5);
        return bList;
    }

    @Override
    protected List<Bout> makeRound2Bouts(List<Wrestler> wList, Group g, Wrestler d) {
        List<Bout> bList = super.makeRound2Bouts(wList, g, d);
        r2b3.setRed(wList.get(4));
        bList.remove(r2b11);
        return bList;
    }
}
