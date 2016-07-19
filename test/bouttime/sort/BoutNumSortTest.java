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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A class to test bouttime.sort.BoutNumSort.
 */
public class BoutNumSortTest {

    public BoutNumSortTest() { }

    /**
     * Test of compare method, of class BoutNumSort.
     */
    @Test
    public void testCompare() {
        Bout b1 = new Bout(null, null, null, null, null);
        Bout b2 = new Bout(null, null, null, null, null);
        BoutNumSort instance = new BoutNumSort();
        assertEquals(0, instance.compare(b1, b2));

        b1.setBoutNum("1");
        b2.setBoutNum("1");
        assertTrue(instance.compare(b1, b2) == 0);

        b1.setBoutNum("1");
        b2.setBoutNum("2");
        assertTrue(instance.compare(b1, b2) < 0);

        b1.setBoutNum("2");
        b2.setBoutNum("1");
        assertTrue(instance.compare(b1, b2) > 0);

        b1.setBoutNum("100");
        b2.setBoutNum("1");
        assertTrue(instance.compare(b1, b2) > 0);

        b1.setBoutNum("1");
        b2.setBoutNum("1A");
        assertTrue(instance.compare(b1, b2) < 0);

        b1.setBoutNum("1B");
        b2.setBoutNum("1A");
        assertTrue(instance.compare(b1, b2) > 0);

        b1.setBoutNum("1A");
        b2.setBoutNum("2A");
        assertTrue(instance.compare(b1, b2) < 0);

        b1.setBoutNum("A");
        b2.setBoutNum("2");
        assertTrue(instance.compare(b1, b2) > 0);
    }

}