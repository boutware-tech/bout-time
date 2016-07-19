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

import bouttime.model.Wrestler;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A class to test bouttime.sort.WrestlerPlaceSort.
 */
public class WrestlerPlaceSortTest {

    public WrestlerPlaceSortTest() { }

    /**
     * Test of compare method, of class WrestlerPlaceSort.
     */
    @Test
    public void testCompare() {
        Wrestler w1 = new Wrestler();
        Wrestler w2 = new Wrestler();

        WrestlerPlaceSort instance = new WrestlerPlaceSort();

        w1.setPlace(1);
        w2.setPlace(1);
        assertTrue(instance.compare(w1, w2) == 0);

        w1.setPlace(2);
        assertTrue(instance.compare(w1, w2) > 0);

        w2.setPlace(5);
        assertTrue(instance.compare(w1, w2) < 0);
    }

}