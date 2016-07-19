/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2011  Jeffrey K. Rutt
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

import bouttime.boutmaker.bracket2.Bracket2BoutMaker;
import bouttime.boutmaker.bracket4.Bracket4BoutMaker;
import bouttime.boutmaker.bracket8.Bracket8BoutMaker;
import bouttime.boutmaker.bracket16.Bracket16BoutMaker;
import bouttime.boutmaker.bracket32.*;
import bouttime.model.*;
import bouttime.sort.WrestlerSeedSort;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * A helper/utility class to create the bouts for a Group.
 */
public class BoutMaker {

    static Logger logger = Logger.getLogger(BoutMaker.class);

    /**
     * Main entry point for this class.
     * Create the bouts for the given Group based on the given parameters.
     * 
     * @param group Group to create bouts for.
     * @param fifthPlaceEnabled Is there a bout for 5th place?
     * @param secondPlaceChallengeEnabled Is there a 2nd place challenge bout?
     * @param roundRobinEnabled Are round robins allowed?
     * @param roundRobinMax If round robins are allowed, what is the maximum
     *     number of wrestlers in a group that will use a round robin?
     * @param dummy Dummy wrestler to use when making bouts where a wrestler
     *     cannot be determined.
     */
    public static void makeBouts(Group group, Boolean fifthPlaceEnabled,
            Boolean secondPlaceChallengeEnabled, Boolean roundRobinEnabled,
            Integer roundRobinMax, Wrestler dummy) {

        List<Wrestler> list = new ArrayList<Wrestler>(group.getWrestlers());
        Collections.sort(list, new WrestlerSeedSort());

        logger.debug("Making bouts for group [" + group + "]");

        switch (group.getNumWrestlers()) {

            case 1:
                // No bouts for 1-man group, just set everything correctly
                List<Bout> bList = new ArrayList<Bout>();
                group.setBouts(bList);
                group.setNumRounds(0);
                break;

            case 2:
                if (roundRobinEnabled && (roundRobinMax >= 2)) {
                    RoundRobinBoutMaker.make2RoundRobin(group, list);
                } else {
                    Bracket2BoutMaker bm2 = new Bracket2BoutMaker();
                    bm2.makeBouts(group, dummy);
                }
                break;

            case 3:
                if (roundRobinEnabled && (roundRobinMax >= 3)) {
                    RoundRobinBoutMaker.make3RoundRobin(group, list);
                } else {
                    Bracket4BoutMaker bm4 = new Bracket4BoutMaker();
                    bm4.makeBouts(group, secondPlaceChallengeEnabled, dummy);
                }
                break;

            case 4:
                if (roundRobinEnabled && (roundRobinMax >= 4)) {
                    RoundRobinBoutMaker.make4RoundRobin(group, list);
                } else {
                    Bracket4BoutMaker bm4 = new Bracket4BoutMaker();
                    bm4.makeBouts(group, secondPlaceChallengeEnabled, dummy);
                }
                break;

            case 5:
                if (roundRobinEnabled && (roundRobinMax >= 5)) {
                    RoundRobinBoutMaker.make5RoundRobin(group, list);
                } else {
                    Bracket8BoutMaker bm8 = new Bracket8BoutMaker();
                    bm8.makeBouts(group, fifthPlaceEnabled, secondPlaceChallengeEnabled, dummy);
                }
                break;

            case 6:
            case 7:
            case 8:
                Bracket8BoutMaker bm8 = new Bracket8BoutMaker();
                bm8.makeBouts(group, fifthPlaceEnabled, secondPlaceChallengeEnabled, dummy);
                break;

            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                Bracket16BoutMaker bm16 = new Bracket16BoutMaker();
                bm16.makeBouts(group, fifthPlaceEnabled, secondPlaceChallengeEnabled, dummy);
                break;

            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
                Bracket32BoutMaker bm32 = new Bracket32BoutMaker();
                bm32.makeBouts(group, fifthPlaceEnabled, secondPlaceChallengeEnabled, dummy);
                break;

            default:
                logger.warn("Unexpected number of wrestlers : " + group.getNumWrestlers());

        }

        setBoutTime(group);
    }

    /**
     * Set the bout times for all of the bouts in the given group.
     * We expect the separator between front-side and back-side bout times
     * to be a hyphen (-).  For example : "2:00-1:30".  So front-side bout time
     * is "2:00" and back-side bout time is "1:30".  Front-side bout time
     * is always first.  If there is no hyphen, then front-side and back-side
     * bout times are the same.
     * @param group Group to set the bout times for
     */
    public static void setBoutTime(Group group) {
        if ((group == null) || (group.getBouts() == null)) {
            logger.warn("Unable to set bout time for group : " + group);
            return;
        }

        String boutTimeValues = group.getBoutTime();
        if (boutTimeValues == null) {
            return;
        }

        String frontSideBoutTime;
        String backSideBoutTime;

        if (boutTimeValues.contains("-")) {
            String[] values = boutTimeValues.split("-");
            frontSideBoutTime = values[0].trim();
            backSideBoutTime = values[1].trim();
        } else {
            frontSideBoutTime = boutTimeValues;
            backSideBoutTime = boutTimeValues;
        }
        
        logger.debug(String.format("Setting bout time for group [%s], front-side=%s , back-side=%s",
                group, frontSideBoutTime, backSideBoutTime));

        for (Bout bout : group.getBouts()) {
            if (bout.isConsolation()) {
                bout.setBoutTime(backSideBoutTime);
            } else {
                bout.setBoutTime(frontSideBoutTime);
            }
        }
    }
}
