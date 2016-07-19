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

package bouttime.stat;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A class to test bouttime.stat.ThreeColumnStat.
 */
public class ThreeColumnStatTest {

    public ThreeColumnStatTest() { }

    /**
     * Test of *ColumnOne methods, of class ThreeColumnStat.
     */
    @Test
    public void testColumnOne() {
        ThreeColumnStat instance = new ThreeColumnStat();
        String expResult = "Foo";
        instance.setColumnOne(expResult);
        assertEquals(expResult, instance.getColumnOne());
    }

    /**
     * Test of *ColumnTwo methods, of class ThreeColumnStat.
     */
    @Test
    public void testColumnTwo() {
        ThreeColumnStat instance = new ThreeColumnStat();
        String expResult = "Foo";
        instance.setColumnTwo(expResult);
        assertEquals(expResult, instance.getColumnTwo());
    }

    /**
     * Test of *ColumnThree methods, of class ThreeColumnStat.
     */
    @Test
    public void testSetColumnThree() {
        ThreeColumnStat instance = new ThreeColumnStat();
        String expResult = "Foo";
        instance.setColumnThree(expResult);
        assertEquals(expResult, instance.getColumnThree());
    }

}