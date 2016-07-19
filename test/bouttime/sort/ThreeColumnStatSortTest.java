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

import bouttime.stat.ThreeColumnStat;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A class to test bouttime.sort.ThreeColumnStatSort.
 */
public class ThreeColumnStatSortTest {

    public ThreeColumnStatSortTest() { }

    /**
     * Test of compare method, of class TwoColumnStatSort.
     */
    @Test
    public void testCompare() {
        ThreeColumnStat s1 = new ThreeColumnStat();
        s1.setColumnOne("A");
        s1.setColumnTwo("B");
        s1.setColumnThree("C");
        ThreeColumnStat s2 = new ThreeColumnStat();
        s2.setColumnOne("A");
        s2.setColumnTwo("B");
        s2.setColumnThree("C");

        ThreeColumnStatSort instance = new ThreeColumnStatSort();
        assertTrue(instance.compare(s1, s2) == 0);

        s1.setColumnOne("B");
        assertTrue(instance.compare(s1, s2) > 0);

        s2.setColumnOne("C");
        assertTrue(instance.compare(s1, s2) < 0);

        s1.setColumnOne("C");
        s1.setColumnTwo("C");
        assertTrue(instance.compare(s1, s2) > 0);

        s2.setColumnTwo("D");
        assertTrue(instance.compare(s1, s2) < 0);

        s1.setColumnTwo("D");
        s1.setColumnThree("D");
        assertTrue(instance.compare(s1, s2) > 0);

        s2.setColumnThree("E");
        assertTrue(instance.compare(s1, s2) < 0);
    }

}