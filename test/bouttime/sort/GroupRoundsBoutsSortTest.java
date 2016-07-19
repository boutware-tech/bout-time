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

import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A class to test bouttime.sort.GroupRoundsBoutsSort.
 */
public class GroupRoundsBoutsSortTest {

    public GroupRoundsBoutsSortTest() { }

    /**
     * Test of compare method, of class GroupRoundsBoutsSort.
     */
    @Test
    public void testCompare() {
        Bout b1 = new Bout(null, null, null, "1", null);
        Bout b2 = new Bout(null, null, null, "1", null);
        Bout b3 = new Bout(null, null, null, "1", null);
        Bout b4 = new Bout(null, null, null, "1", null);

        Wrestler w1 = new Wrestler();
        Wrestler w2 = new Wrestler();
        List<Wrestler> list1 = new ArrayList<Wrestler>();
        list1.add(w1);
        list1.add(w2);
        Group g1 = new Group(list1);
        g1.setNumRounds(1);
        List<Bout> bList1 = new ArrayList<Bout>();
        bList1.add(b1);
        g1.setBouts(bList1);

        Wrestler w3 = new Wrestler();
        Wrestler w4 = new Wrestler();
        List<Wrestler> list2 = new ArrayList<Wrestler>();
        list2.add(w3);
        list2.add(w4);
        Group g2 = new Group(list2);
        g2.setNumRounds(1);
        List<Bout> bList2 = new ArrayList<Bout>();
        bList2.add(b3);
        g2.setBouts(bList2);

        GroupRoundsBoutsSort instance = new GroupRoundsBoutsSort();
        assertTrue(instance.compare(g1, g2) == 0);

        g1.setNumRounds(2);
        assertTrue(instance.compare(g1, g2) < 0);

        g2.setNumRounds(5);
        assertTrue(instance.compare(g1, g2) > 0);

        g1.setNumRounds(1);
        g2.setNumRounds(1);
        bList2.add(b4);
        assertTrue(instance.compare(g1, g2) > 0);
        
        bList1.add(b3);
        bList1.add(b4);
        assertTrue(instance.compare(g1, g2) < 0);
    }

}