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
 * A class to test bouttime.sort.WrestlerClassDivWtAlphaSort.
 */
public class WrestlerClassDivWtAlphaSortTest {

    public WrestlerClassDivWtAlphaSortTest() { }

    /**
     * Test of compare method, of class WrestlerClassDivWtAlphaSort.
     */
    @Test
    public void testCompare() {
        Wrestler w1 = new Wrestler();
        w1.setClassification("Rookie");
        w1.setAgeDivision("1");
        w1.setWeightClass("88");
        w1.setLastName("Test");

        Wrestler w2 = new Wrestler();
        w2.setClassification("Rookie");
        w2.setAgeDivision("1");
        w2.setWeightClass("88");
        w2.setLastName("Test");

        WrestlerClassDivWtAlphaSort instance = new WrestlerClassDivWtAlphaSort();
        assertTrue(instance.compare(w1, w2) == 0);

        w2.setLastName("Best");
        assertTrue(instance.compare(w1, w2) > 0);

        w1.setLastName("Aest");
        assertTrue(instance.compare(w1, w2) < 0);

        w2.setWeightClass("99");
        assertTrue(instance.compare(w1, w2) < 0);

        w1.setWeightClass("120");
        assertTrue(instance.compare(w1, w2) > 0);

        w2.setAgeDivision("2");
        assertTrue(instance.compare(w1, w2) < 0);

        w1.setAgeDivision("5");
        assertTrue(instance.compare(w1, w2) > 0);

        w2.setClassification("Varsity");
        assertTrue(instance.compare(w1, w2) < 0);

        w1.setClassification("Warsity");
        assertTrue(instance.compare(w1, w2) > 0);
    }

}