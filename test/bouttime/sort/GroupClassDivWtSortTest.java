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
import bouttime.model.Wrestler;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A class to test bouttime.sort.GroupClassDivWtSort.
 */
public class GroupClassDivWtSortTest {

    public GroupClassDivWtSortTest() { }

    /**
     * Test of compare method, of class GroupClassDivWtSort.
     */
    @Test
    public void testCompare() {
        Wrestler w1 = new Wrestler();
        w1.setClassification("Rookie");
        w1.setAgeDivision("1");
        w1.setWeightClass("88");
        Wrestler w2 = new Wrestler();
        w2.setClassification("Rookie");
        w2.setAgeDivision("1");
        w2.setWeightClass("88");
        List<Wrestler> list1 = new ArrayList<Wrestler>();
        list1.add(w1);
        list1.add(w2);
        Group g1 = new Group(list1);

        Wrestler w3 = new Wrestler();
        w3.setClassification("Rookie");
        w3.setAgeDivision("1");
        w3.setWeightClass("88");
        Wrestler w4 = new Wrestler();
        w4.setClassification("Rookie");
        w4.setAgeDivision("1");
        w4.setWeightClass("88");
        List<Wrestler> list2 = new ArrayList<Wrestler>();
        list2.add(w3);
        list2.add(w4);
        Group g2 = new Group(list2);

        GroupClassDivWtSort instance = new GroupClassDivWtSort();
        assertTrue(instance.compare(g1, g2) == 0);

        w3.setWeightClass("99");
        w4.setWeightClass("99");
        assertTrue(instance.compare(g1, g2) < 0);

        w3.setWeightClass("77");
        w4.setWeightClass("77");
        assertTrue(instance.compare(g1, g2) > 0);

        w3.setWeightClass("177");
        w4.setWeightClass("177");
        assertTrue(instance.compare(g1, g2) < 0);

        w3.setWeightClass("88");
        w4.setWeightClass("95");
        assertTrue(instance.compare(g1, g2) < 0);

        w3.setWeightClass("95");
        w4.setWeightClass("88");
        assertTrue(instance.compare(g1, g2) < 0);

        w3.setAgeDivision("2");
        w4.setAgeDivision("2");
        assertTrue(instance.compare(g1, g2) < 0);

        w1.setAgeDivision("3");
        w2.setAgeDivision("3");
        assertTrue(instance.compare(g1, g2) > 0);

        w3.setClassification("Open");
        w4.setClassification("Open");
        assertTrue(instance.compare(g1, g2) > 0);
    }

}