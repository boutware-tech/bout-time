/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2012  Jeffrey K. Rutt
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

package bouttime.report.boutsequence;

import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import bouttime.sort.BoutSort;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * A class to calculate the sequence of bouts for the bout sequence report.
 */
public class BoutSequence {
    static Logger logger = Logger.getLogger(BoutSequence.class);
    
    static protected List<Bout> calculate(Wrestler w) {
        if (w == null) {
            logger.warn("Wrestler is null");
            return null;
        }
        
        if (w.getGroup() == null) {
            logger.warn("Wrestler not assigned to a group");
            return null;
        }
        
        List<Bout> groupBoutList = w.getGroup().getBouts(false);
        if ((groupBoutList == null) || groupBoutList.isEmpty()) {
            logger.warn(String.format("Group bout list for wrestler [%s] is null or empty", w.getShortName()));
            return null;
        }
        
        List<Bout> list = null;
        
        String bracketType = w.getGroup().getBracketType();
        if (Group.BRACKET_TYPE_ROUNDROBIN.equalsIgnoreCase(bracketType)) {
            list = getBoutSequenceRoundRobin(w, groupBoutList);
        } else {
            list = getBoutSequenceBracket(w, groupBoutList);
        }
        
        return list;
    }
    
    static private List<Bout> getBoutSequenceBracket(Wrestler w, List<Bout> groupBoutList) {
        List<Bout> bList = new ArrayList<Bout>();
        Bout bout = getFirstBout(w, groupBoutList);
        
        while (bout != null) {
            if (bout.isBye()) {
                bout = bout.getWinnerNextBout();
                continue;
            }
            
            List<Bout> bl = getBouts(bout);
            bList.addAll(bl);
            bout = bout.getWinnerNextBout();
        }
        
        return bList;
    }
    
    static private List<Bout> getBouts(Bout b) {
        List<Bout> bList = new ArrayList<Bout>();
        bList.add(b);
        b = b.getLoserNextBout();
        while (b != null) {
            if (!b.isBye()) {
                bList.add(0, b);
                
                // check for 5th place bout
                if (b.getLoserNextBout() != null) {
                    bList.add(0, b.getLoserNextBout());
                }
            }
            
            b = b.getWinnerNextBout();
        }
        
        return bList;
    }
    
    static private List<Bout> getBoutSequenceRoundRobin(Wrestler w, List<Bout> groupBoutList) {
        Collections.sort(groupBoutList, new BoutSort());
        List<Bout> bList = new ArrayList<Bout>();
        for (Bout b : groupBoutList) {
            if (!b.isBye() && (w.equals(b.getRed()) || w.equals(b.getGreen()))) {
                bList.add(b);
            }
        }
        
        return bList;
    }
    
    static private Bout getFirstBout(Wrestler w, List<Bout> groupBoutList) {
        Bout firstBout = null;
        Collections.sort(groupBoutList, new BoutSort());
        
        for (Bout b : groupBoutList) {
            if (!b.isBye() && (w.equals(b.getRed()) || w.equals(b.getGreen()))) {
                firstBout = b;
                break;
            }
        }
        
        logger.debug(String.format("First bout for [%s] is %s", w.getShortName(), firstBout.getBoutNum()));
        return firstBout;
    }
}
