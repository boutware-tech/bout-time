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

import bouttime.stat.TwoColumnStat;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A class to test bouttime.sort.TwoColumnStatSort.
 */
public class TwoColumnStatSortTest {

    public TwoColumnStatSortTest() { }

    /**
     * Test of compare method, of class TwoColumnStatSort.
     */
    @Test
    public void testCompare() {
        TwoColumnStat s1 = new TwoColumnStat();
        s1.setColumnOne("A");
        s1.setColumnTwo("B");
        TwoColumnStat s2 = new TwoColumnStat();
        s2.setColumnOne("A");
        s2.setColumnTwo("B");

        TwoColumnStatSort instance = new TwoColumnStatSort();
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
    }

}