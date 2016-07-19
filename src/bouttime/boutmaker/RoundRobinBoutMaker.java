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
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * A utility class to make bouts for round robin groups.
 */
public class RoundRobinBoutMaker {
    static Logger logger = Logger.getLogger(RoundRobinBoutMaker.class);

    protected static void make2RoundRobin(Group g, List<Wrestler> list) {
        logger.trace("Making bouts for round robin group of size 2");

        List<Bout> bList = new ArrayList<Bout>();

        // Round 1
        Bout r1b1 = new Bout(list.get(0), list.get(1), g, "1", 1);
        bList.add(r1b1);

        // Round 2
        Bout r2b1 = new Bout(list.get(0), list.get(1), g, "2", 1);
        bList.add(r2b1);

        // Round 3
        Bout r3b1 = new Bout(list.get(0), list.get(1), g, "3", 1);
        bList.add(r3b1);

        g.setBouts(bList);
        g.setNumRounds(3);
        g.setBracketType(Group.BRACKET_TYPE_ROUNDROBIN);
    }

    protected static void make3RoundRobin(Group g, List<Wrestler> list) {
        logger.trace("Making bouts for round robin group of size 3");

        List<Bout> bList = new ArrayList<Bout>();

        // Round 1
        Bout r1b1 = new Bout(list.get(0), list.get(1), g, "1", 1);
        bList.add(r1b1);

        // Round 2
        Bout r2b1 = new Bout(list.get(0), list.get(2), g, "2", 1);
        bList.add(r2b1);

        // Round 3
        Bout r3b1 = new Bout(list.get(1), list.get(2), g, "3", 1);
        bList.add(r3b1);

        g.setBouts(bList);
        g.setNumRounds(3);
        g.setBracketType(Group.BRACKET_TYPE_ROUNDROBIN);
    }

    protected static void make4RoundRobin(Group g, List<Wrestler> list) {
        logger.trace("Making bouts for round robin group of size 4");

        List<Bout> bList = new ArrayList<Bout>();

        // Round 1
        Bout r1b1 = new Bout(list.get(0), list.get(1), g, "1", 1);
        Bout r1b2 = new Bout(list.get(2), list.get(3), g, "1", 2);
        bList.add(r1b1);
        bList.add(r1b2);

        // Round 2
        Bout r2b1 = new Bout(list.get(0), list.get(2), g, "2", 1);
        Bout r2b2 = new Bout(list.get(1), list.get(3), g, "2", 2);
        bList.add(r2b1);
        bList.add(r2b2);

        // Round 3
        Bout r3b1 = new Bout(list.get(0), list.get(3), g, "3", 1);
        Bout r3b2 = new Bout(list.get(1), list.get(2), g, "3", 2);
        bList.add(r3b1);
        bList.add(r3b2);

        g.setBouts(bList);
        g.setNumRounds(3);
        g.setBracketType(Group.BRACKET_TYPE_ROUNDROBIN);
    }

    protected static void make5RoundRobin(Group g, List<Wrestler> list) {
        logger.trace("Making bouts for round robin group of size 5");

        List<Bout> bList = new ArrayList<Bout>();

        // Round 1
        Bout r1b1 = new Bout(list.get(0), list.get(1), g, "1", 1);
        Bout r1b2 = new Bout(list.get(2), list.get(3), g, "1", 2);
        bList.add(r1b1);
        bList.add(r1b2);

        // Round 2
        Bout r2b1 = new Bout(list.get(0), list.get(2), g, "2", 1);
        Bout r2b2 = new Bout(list.get(1), list.get(4), g, "2", 2);
        bList.add(r2b1);
        bList.add(r2b2);

        // Round 3
        Bout r3b1 = new Bout(list.get(0), list.get(4), g, "3", 1);
        Bout r3b2 = new Bout(list.get(1), list.get(3), g, "3", 2);
        bList.add(r3b1);
        bList.add(r3b2);

        // Round 4
        Bout r4b1 = new Bout(list.get(0), list.get(3), g, "4", 1);
        Bout r4b2 = new Bout(list.get(2), list.get(4), g, "4", 2);
        bList.add(r4b1);
        bList.add(r4b2);

        // Round 5
        Bout r5b1 = new Bout(list.get(1), list.get(2), g, "5", 1);
        Bout r5b2 = new Bout(list.get(3), list.get(4), g, "5", 2);
        bList.add(r5b1);
        bList.add(r5b2);

        g.setBouts(bList);
        g.setNumRounds(5);
        g.setBracketType(Group.BRACKET_TYPE_ROUNDROBIN);
    }
}
