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

package bouttime.autobouter;

import bouttime.dao.Dao;
import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.sort.BoutSort;
import bouttime.sort.GroupRoundsBoutsSort;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * A class that assigns bout numbers to bouts.
 */
public class AutoBouter {
    static Logger logger = Logger.getLogger(AutoBouter.class);

    public void assignBoutNumbers(Dao dao, List<Group> groupList, int firstNum,
            int minBouts, int suspendVal) {
        logger.trace("BEGIN");
        logger.info("firstNum=" + firstNum + ", minBouts=" + minBouts +
                ", suspendVal=" + suspendVal + ", " + groupList);

        // Sort list of groups by number of rounds and number of bouts in round 1
        List<Group> gList = new ArrayList<Group>(groupList);
        Collections.sort(gList, new GroupRoundsBoutsSort());
        int maxRound = gList.get(0).getNumRounds();
        logger.info("maxRound=" + maxRound + ", " + gList);

        // Create and init list to use when assigning bouts
        List<GroupRound> list = new ArrayList<GroupRound>();
        for (Group g : gList) {
            list.add(new GroupRound(g, 1));
        }

        int boutNum = firstNum;
        for (int i = 1; i <= maxRound; i++) {
            logger.info("---------- iteration " + i + " ----------");
            int total = 0;

            if ((i % 2) == 0) {
                // Even iteration - do all groups that aren't done
                for (GroupRound gr : list) {
                    if (!gr.isDone()) {
                        int bouts = numberBouts(dao, gr, boutNum, suspendVal);
                        total += bouts;
                        boutNum += bouts;
                        gr.incrementRound();
                    }
                }
            } else {
                // Odd iteration - do group if : 
                //     1) it is not done
                //          AND any of the following...
                //     2) it has the same number of rounds as maxRound
                //     3) it has the same number of rounds left as the rounds
                //        that are remaining
                //     4) total bouts for this round is less than minBouts
                int roundsRemaining = maxRound - i;
                for (GroupRound gr : list) {
                    int gNumRounds = gr.getGroup().getNumRounds();
                    int gRoundsLeft = gNumRounds - gr.getRound();
                    if (!gr.isDone() &&
                            ((gNumRounds == maxRound) ||
                             (gRoundsLeft == roundsRemaining) ||
                             (total < minBouts))) {

                        int bouts = numberBouts(dao, gr, boutNum, suspendVal);
                        total += bouts;
                        boutNum += bouts;
                        gr.incrementRound();
                    }
                }
            }

            logger.info("total bouts is " + total);
        }

        logger.trace("END");
    }

    /**
     * Assign bout numbers to bouts in the given group.
     * @param gr The object to assign bouts for
     * @param boutNum The bout number to begin assigning bouts with
     * @return The number of bouts processed
     */
    private int numberBouts(Dao dao, GroupRound gr, int boutNum, int suspendVal) {
        Group g = gr.getGroup();
        int round = gr.getRound();
        int count = 0;
        
        boolean doSuspend = false;
        if (suspendVal > 0) {
            int numRounds = g.getNumRounds();
            int finalsRound = (dao.isSecondPlaceChallengeEnabled()) ? numRounds - 1 : numRounds;
            if (round == finalsRound) {
                logger.debug("numRounds=" + numRounds + ", finalsRound=" + finalsRound);
                doSuspend = true;
            } else if (round == numRounds) {
                logger.info("Suspending for group [" + g + "] : round == numRounds");
                gr.setDone(true);
                return 0;
            }
        }

        String roundStr = Integer.toString(round);
        List<Bout> bList = g.getBoutsByRound(roundStr);
        if (bList == null) {
            return 0;
        }
        
        logger.info("numbering " + bList.size() + " bouts in round " + roundStr +
                " for " + g);
        Collections.sort(bList, new BoutSort());
        for (Bout b : bList) {
            if (doSuspend) {
                int seq = b.getSequence();
                if (seq <= suspendVal) {
                    logger.info("Suspending : sequence=" + seq + ", suspend=" + suspendVal);
                    gr.setDone(true);
                    continue;
                }
            }
            b.setBoutNum(Integer.toString(boutNum));
            boutNum++;
            count++;
        }

        return count;
    }

    /**
     * Private inner class to track a group and the round to assign bouts for.
     */
    private class GroupRound {
        private Group group;
        private int round;
        private boolean done;

        protected GroupRound(Group g, int round) {
            this.group = g;
            this.round = round;
            this.done = false;
        }

        public void setDone(boolean b)  { this.done = b; }

        public boolean isDone () {
            if (this.done || (this.round > this.group.getNumRounds())) {
                return true;
            } else {
                return false;
            }
        }

        public Group getGroup() { return this.group; }

        public int getRound() { return this.round; }
        public void setRound(int round) { this.round = round; }
        public void incrementRound() { this.round++; }
    }
}

