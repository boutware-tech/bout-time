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

package bouttime.sort;

import java.util.List;
import java.util.ArrayList;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A class to test bouttime.sort.WrestlerMatNameSort.
 */
public class WrestlerMatNameSortTest {
    
    public WrestlerMatNameSortTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of compare method, of class WrestlerMatNameSort.
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

        WrestlerMatNameSort instance = new WrestlerMatNameSort();
        assertEquals(-1, instance.compare(w1, w2));
        
        List<Wrestler> g1List = new ArrayList<Wrestler>();
        g1List.add(w1);
        List<Wrestler> g2List = new ArrayList<Wrestler>();
        g2List.add(w2);
        
        Group g1 = new Group(g1List);
        assertEquals(-1, instance.compare(w1, w2));
        
        Group g2 = new Group(g2List);
        assertEquals(0, instance.compare(w1, w2));
        
        g1.setMat("1");
        assertEquals(1, instance.compare(w1, w2));
        g2.setMat("2");
        assertEquals(-1, instance.compare(w1, w2));
        
        g1.setMat("3");
        assertEquals(1, instance.compare(w1, w2));

        g1.setMat("1");
        g2.setMat("1");
        w2.setLastName("Best");
        assertTrue(instance.compare(w1, w2) > 0);

        w1.setLastName("Aest");
        assertTrue(instance.compare(w1, w2) < 0);

        w1.setLastName("Best");
        w1.setFirstName("The");
        w2.setFirstName("The");
        assertTrue(instance.compare(w1, w2) == 0);
        
        w2.setFirstName("Uhe");
        assertTrue(instance.compare(w1, w2) < 0);
    }
}
