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

package bouttime.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for bouttime.model.Wrestler class.
 */
public class WrestlerTest {

    public WrestlerTest() { }

    @BeforeClass
    public static void setUpClass() throws Exception { }

    @AfterClass
    public static void tearDownClass() throws Exception { }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() { }

    /**
     * Test of getActualWeight method, of class Wrestler.
     */
    @Test
    public void testGetActualWeight() {
        System.out.println("getActualWeight");
        Wrestler instance = new Wrestler();
        String expResult = null;
        String result = instance.getActualWeight();
        assertEquals(expResult, result);
    }

    /**
     * Test of setActualWeight method, of class Wrestler.
     */
    @Test
    public void testSetActualWeight() {
        System.out.println("setActualWeight");
        String expResult = "100";
        Wrestler instance = new Wrestler();
        instance.setActualWeight(expResult);
        String result = instance.getActualWeight();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSerialNumber method, of class Wrestler.
     */
    @Test
    public void testGetSerialNumber() {
        System.out.println("getSerialNumber");
        Wrestler instance = new Wrestler();
        String expResult = null;
        String result = instance.getSerialNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSerialNumber method, of class Wrestler.
     */
    @Test
    public void testSetSerialNumber() {
        System.out.println("setSerialNumber");
        String expResult = "1234ABCD";
        Wrestler instance = new Wrestler();
        instance.setSerialNumber(expResult);
        String result = instance.getSerialNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAgeDivision method, of class Wrestler.
     */
    @Test
    public void testGetAgeDivision() {
        System.out.println("getAgeDivision");
        Wrestler instance = new Wrestler();
        String expResult = "";
        String result = instance.getAgeDivision();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAgeDivision method, of class Wrestler.
     */
    @Test
    public void testSetAgeDivision() {
        System.out.println("setAgeDivision");
        String expResult = "1";
        Wrestler instance = new Wrestler();
        instance.setAgeDivision(expResult);
        String result = instance.getAgeDivision();
        assertEquals(expResult, result);
    }

    /**
     * Test of getClassification method, of class Wrestler.
     */
    @Test
    public void testGetClassification() {
        System.out.println("getClassification");
        Wrestler instance = new Wrestler();
        String expResult = "";
        String result = instance.getClassification();
        assertEquals(expResult, result);
    }

    /**
     * Test of setClassification method, of class Wrestler.
     */
    @Test
    public void testSetClassification() {
        System.out.println("setClassification");
        String expResult = "Rookie";
        Wrestler instance = new Wrestler();
        instance.setClassification(expResult);
        String result = instance.getClassification();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFirstName method, of class Wrestler.
     */
    @Test
    public void testGetFirstName() {
        System.out.println("getFirstName");
        Wrestler instance = new Wrestler();
        String expResult = "";
        String result = instance.getFirstName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFirstName method, of class Wrestler.
     */
    @Test
    public void testSetFirstName() {
        System.out.println("setFirstName");
        String expResult = "George";
        Wrestler instance = new Wrestler();
        instance.setFirstName(expResult);
        String result = instance.getFirstName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class Wrestler.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Wrestler instance = new Wrestler();
        Integer expResult = null;
        Integer result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Wrestler.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Integer expResult = 1001;
        Wrestler instance = new Wrestler();
        instance.setId(expResult);
        Integer result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLastName method, of class Wrestler.
     */
    @Test
    public void testGetLastName() {
        System.out.println("getLastName");
        Wrestler instance = new Wrestler();
        String expResult = "";
        String result = instance.getLastName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLastName method, of class Wrestler.
     */
    @Test
    public void testSetLastName() {
        System.out.println("setLastName");
        String expResult = "Washington";
        Wrestler instance = new Wrestler();
        instance.setLastName(expResult);
        String result = instance.getLastName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLevel method, of class Wrestler.
     */
    @Test
    public void testGetLevel() {
        System.out.println("getLevel");
        Wrestler instance = new Wrestler();
        String expResult = "";
        String result = instance.getLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLevel method, of class Wrestler.
     */
    @Test
    public void testSetLevel() {
        System.out.println("setLevel");
        String expResult = "A";
        Wrestler instance = new Wrestler();
        instance.setLevel(expResult);
        String result = instance.getLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSeed method, of class Wrestler.
     */
    @Test
    public void testGetSeed() {
        System.out.println("getSeed");
        Wrestler instance = new Wrestler();
        Integer expResult = 0;
        Integer result = instance.getSeed();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSeed method, of class Wrestler.
     */
    @Test
    public void testSetSeed() {
        System.out.println("setSeed");
        Integer expResult = 1;
        Wrestler instance = new Wrestler();
        instance.setSeed(expResult);
        Integer result = instance.getSeed();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTeamName method, of class Wrestler.
     */
    @Test
    public void testGetTeamName() {
        System.out.println("getTeamName");
        Wrestler instance = new Wrestler();
        String expResult = "";
        String result = instance.getTeamName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTeamName method, of class Wrestler.
     */
    @Test
    public void testSetTeamName() {
        System.out.println("setTeamName");
        String expResult = "Eagles";
        Wrestler instance = new Wrestler();
        instance.setTeamName(expResult);
        String result = instance.getTeamName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWeightClass method, of class Wrestler.
     */
    @Test
    public void testGetWeightClass() {
        System.out.println("getWeightClass");
        Wrestler instance = new Wrestler();
        String expResult = "";
        String result = instance.getWeightClass();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWeightClass method, of class Wrestler.
     */
    @Test
    public void testSetWeightClass() {
        System.out.println("setWeightClass");
        String expResult = "76";
        Wrestler instance = new Wrestler();
        instance.setWeightClass(expResult);
        String result = instance.getWeightClass();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlace method, of class Wrestler.
     */
    @Test
    public void testGetPlace() {
        System.out.println("getPlace");
        Wrestler instance = new Wrestler();
        Integer expResult = null;
        Integer result = instance.getPlace();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPlace method, of class Wrestler.
     */
    @Test
    public void testSetPlace() {
        System.out.println("setPlace");
        Integer expResult = 1;
        Wrestler instance = new Wrestler();
        instance.setPlace(expResult);
        Integer result = instance.getPlace();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGroup method, of class Wrestler.
     */
    @Test
    public void testGetGroup() {
        System.out.println("getGroup");
        Wrestler instance = new Wrestler();
        Group expResult = null;
        Group result = instance.getGroup();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGroup method, of class Wrestler.
     */
    @Test
    public void testSetGroup() {
        System.out.println("setGroup");
        Wrestler testRed = new Wrestler("Washington", "George", "Eagles", "Rookie",
                "1", "76", "A");
        Wrestler testGreen = new Wrestler("Adams", "John", "Hawks", "Rookie", "1",
                "87", "B");
        List<Wrestler> list = new ArrayList<Wrestler>();
        list.add(testRed);
        list.add(testGreen);
        Group expResult = new Group(list);
        testRed.setGroup(expResult);
        Group result = testRed.getGroup();
        assertEquals(expResult, result);
    }

    /**
     * Test of getComment method, of class Wrestler.
     */
    @Test
    public void testGetComment() {
        System.out.println("getComment");
        Wrestler instance = new Wrestler();
        String expResult = null;
        String result = instance.getComment();
        assertEquals(expResult, result);
    }

    /**
     * Test of setComment method, of class Wrestler.
     */
    @Test
    public void testSetComment() {
        System.out.println("setComment");
        String expResult = "No Show";
        Wrestler instance = new Wrestler();
        instance.setComment(expResult);
        String result = instance.getComment();
        assertEquals(expResult, result);
    }

    /**
     * Test of isFlagged method, of class Wrestler.
     */
    @Test
    public void testIsFlagged() {
        System.out.println("isFlagged");
        Wrestler instance = new Wrestler();
        boolean expResult = false;
        boolean result = instance.isFlagged();
        assertEquals(expResult, result);
    }

    /**
     * Test of isScratched method, of class Wrestler.
     */
    @Test
    public void testIsScratched() {
        System.out.println("isScratched");
        Wrestler instance = new Wrestler();
        boolean expResult = false;
        boolean result = instance.isScratched();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFlagged method, of class Wrestler.
     */
    @Test
    public void testSetFlagged() {
        System.out.println("setFlagged");
        boolean expResult = true;
        Wrestler instance = new Wrestler();
        instance.setFlagged(expResult);
        boolean result = instance.isFlagged();
        assertEquals(expResult, result);
    }

    /**
     * Test of setScratched method, of class Wrestler.
     */
    @Test
    public void testSetScratched() {
        System.out.println("setScratched");
        boolean expResult = true;
        Wrestler instance = new Wrestler();
        instance.setScratched(expResult);
        boolean result = instance.isScratched();
        assertEquals(expResult, result);
    }

    /**
     * Test of getShortName method, of class Wrestler.
     */
    @Test
    public void testGetShortName() {
        System.out.println("getShortName");
        Wrestler instance = new Wrestler("Washington", "George", "Eagles", "Rookie",
                "1", "76", "A");
        String expResult = "Washington (76) Eagles";
        String result = instance.getShortName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getString4Bracket method, of class Wrestler.
     */
    @Test
    public void testGetString4Bracket() {
        System.out.println("getString4Bracket");
        Wrestler instance = new Wrestler("Washington", "George", "Eagles", "Rookie",
                "1", "76", "A");
        String expResult = "George Washington (1/76) Eagles";
        String result = instance.getString4Bracket();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFirstInitialLastName method, of class Wrestler.
     */
    @Test
    public void testGetFirstInitialLastName() {
        System.out.println("getFirstInitialLastName");
        Wrestler instance = new Wrestler("Washington", "George", "Eagles", "Rookie",
                "1", "76", "A");
        String expResult = "G. Washington";
        String result = instance.getFirstInitialLastName();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Wrestler.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Wrestler instance = new Wrestler("Washington", "George", "Eagles", "Rookie",
                "1", "76", "A");
        String expResult = "George Washington:Eagles:Rookie:1:76";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Wrestler.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Wrestler other = new Wrestler("Washington", "George", "Eagles", "Rookie",
                "1", "76", "A");
        Wrestler instance = new Wrestler("Washington", "George", "Eagles", "Rookie",
                "1", "76", "A");
        boolean expResult = true;
        boolean result = instance.equals(other);
        assertEquals(expResult, result);

        other.setWeightClass("88");
        result = instance.equals(other);
        assertEquals(expResult, result);
        
        other.setClassification("Varsity");
        expResult = false;
        result = instance.equals(other);
        assertEquals(expResult, result);
    }

}