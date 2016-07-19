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
 * A class to test bouttime.sort.BoutSort.
 */
public class BoutSortTest {

    public BoutSortTest() { }

    /**
     * Test of compare method, of class BoutSort.
     */
    @Test
    public void testCompare() {
        Bout b1 = new Bout(null, null, null, null, null);
        Bout b2 = new Bout(null, null, null, null, null);
        BoutSort instance = new BoutSort();

        b1.setSequence(1);
        b2.setSequence(2);

        b1.setRound("2");
        b2.setRound("1");
        assertTrue(instance.compare(b1, b2) > 0);

        b1.setRound("1");
        b2.setRound("2");
        assertTrue(instance.compare(b1, b2) < 0);

        b1.setSequence(1);
        b2.setSequence(2);
        assertTrue(instance.compare(b1, b2) < 0);

        b1.setRound("1");
        b2.setRound("1");
        assertTrue(instance.compare(b1, b2) < 0);

        b1.setSequence(1);
        b2.setSequence(10);
        assertTrue(instance.compare(b1, b2) < 0);

        b1.setSequence(1);
        b2.setSequence(1);
        assertTrue(instance.compare(b1, b2) == 0);
    }

}