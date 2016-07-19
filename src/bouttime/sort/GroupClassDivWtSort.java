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

package bouttime.sort;

import bouttime.model.Group;
import java.util.*;

/**
 * A class to sort Group objects :
 *     (1) by classification
 *     (2) by age division
 *     (3) by weight class
 */
public class GroupClassDivWtSort implements Comparator<Group> {
    public int compare(Group g1, Group g2) {
        int rv;
        String g1Class = g1.getClassification();
        String g2Class = g2.getClassification();
        rv = ((g1Class == null) || (g2Class == null)) ? 0 : g1Class.compareTo(g2Class);
        if (rv != 0) {
            return rv;
        } else {
            String g1Div = g1.getAgeDivision();
            String g2Div = g2.getAgeDivision();
            rv = ((g1Div == null) || (g2Div == null)) ? 0 : g1Div.compareTo(g2Div);
            if (rv != 0) {
                return rv;
            } else {
                String g1Wt = g1.getWeightClass();
                String g2Wt = g2.getWeightClass();
                try {
                    Integer g1WtInt = new Integer(g1Wt);
                    Integer g2WtInt = new Integer(g2Wt);
                    rv = ((g1WtInt == null) || (g2WtInt == null)) ? 0 : g1WtInt.compareTo(g2WtInt);
                } catch (NumberFormatException nfe) {
                    rv = ((g1Wt == null) || (g2Wt == null)) ? 0 : g1Wt.compareTo(g2Wt);
                }
                
                return rv;
            }
        }
    }
}
