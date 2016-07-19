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
 * Unit tests for bouttime.model.Group class.
 */
public class GroupTest {

    private Wrestler testRed;
    private Wrestler testGreen;
    private List<Wrestler> list;
    private Group group;

    public GroupTest() { }

    @BeforeClass
    public static void setUpClass() throws Exception { }

    @AfterClass
    public static void tearDownClass() throws Exception { }

    @Before
    public void setUp() {
        this.testRed = new Wrestler("Washington", "George", "Eagles", "Rookie",
                "1", "76", "A");
        this.testGreen = new Wrestler("Adams", "John", "Hawks", "Rookie", "1",
                "87", "B");
        this.list = new ArrayList<Wrestler>();
        list.add(this.testRed);
        list.add(this.testGreen);
        this.group = new Group(list);
    }

    @After
    public void tearDown() { }

    /**
     * Test of getBoutTime method, of class Group.
     */
    @Test
    public void testGetBoutTime() {
        System.out.println("getBoutTime");
        Group g = this.group;
        String expResult = "";
        String result = g.getBoutTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBoutTime method, of class Group.
     */
    @Test
    public void testSetBoutTime() {
        System.out.println("setBoutTime");
        String expResult = "";
        Group g = this.group;
        g.setBoutTime(expResult);
        String result = g.getBoutTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumBouts method, of class Group.
     */
    @Test
    public void testGetNumBouts() {
        System.out.println("getNumBouts");
        Group g = this.group;
        int expResult = 0;
        int result = g.getNumBouts();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumRounds method, of class Group.
     */
    @Test
    public void testGetNumRounds() {
        System.out.println("getNumRounds");
        Group g = this.group;
        int expResult = 0;
        int result = g.getNumRounds();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNumRounds method, of class Group.
     */
    @Test
    public void testSetNumRounds() {
        System.out.println("setNumRounds");
        int expResult = 3;
        Group g = this.group;
        g.setNumRounds(expResult);
        int result = g.getNumRounds();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBracketType method, of class Group.
     */
    @Test
    public void testGetBracketType() {
        System.out.println("getBracketType");
        Group g = this.group;
        String expResult = null;
        String result = g.getBracketType();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBracketType method, of class Group.
     */
    @Test
    public void testSetBracketType() {
        System.out.println("setBracketType");
        String expResult = Group.BRACKET_TYPE_2MAN;
        Group g = this.group;
        g.setBracketType(expResult);
        String result = g.getBracketType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMat method, of class Group.
     */
    @Test
    public void testGetMat() {
        System.out.println("getMat");
        Group g = this.group;
        String expResult = "";
        String result = g.getMat();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMat method, of class Group.
     */
    @Test
    public void testSetMat() {
        System.out.println("setMat");
        String expResult = "1";
        Group g = this.group;
        g.setMat(expResult);
        String result = g.getMat();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Group.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Group g = this.group;
        String expResult = "Rookie:1:76-87";
        String result = g.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSession method, of class Group.
     */
    @Test
    public void testGetSession() {
        System.out.println("getSession");
        Group g = this.group;
        String expResult = "";
        String result = g.getSession();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSession method, of class Group.
     */
    @Test
    public void testSetSession() {
        System.out.println("setSession");
        String expResult = "AM";
        Group g = this.group;
        g.setSession(expResult);
        String result = g.getSession();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumWrestlers method, of class Group.
     */
    @Test
    public void testGetNumWrestlers() {
        System.out.println("getNumWrestlers");
        Group g = this.group;
        int expResult = 2;
        int result = g.getNumWrestlers();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class Group.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Group g = this.group;
        Integer expResult = null;
        Integer result = g.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Group.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Integer expResult = 5;
        Group g = this.group;
        g.setId(expResult);
        Integer result = g.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of isLocked method, of class Group.
     */
    @Test
    public void testIsLocked() {
        System.out.println("isLocked");
        Group g = this.group;
        boolean expResult = false;
        boolean result = g.isLocked();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLocked method, of class Group.
     */
    @Test
    public void testSetLocked() {
        System.out.println("setLocked");
        boolean expResult = true;
        Group g = this.group;
        g.setLocked(expResult);
        boolean result = g.isLocked();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWrestlers method, of class Group.
     */
    @Test
    public void testGetWrestlers() {
        System.out.println("getWrestlers");
        Group g = this.group;
        List expResult = this.list;
        List result = g.getWrestlers();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWrestlers method, of class Group.
     */
    @Test
    public void testSetWrestlers() {
        System.out.println("setWrestlers");
        List<Wrestler> expResult = null;
        Group g = this.group;
        g.setWrestlers(expResult);
        List<Wrestler> result = g.getWrestlers();
        assertEquals(expResult, result);
    }

    /**
     * Test of addWrestler method, of class Group.
     */
    @Test
    public void testAddWrestler() {
        System.out.println("addWrestler");
        Wrestler w = new Wrestler("Jefferson", "Thomas", "Falcons", "Rookie",
                "1", "98", "C");
        Group g = this.group;
        g.addWrestler(w);
        int result = g.getNumWrestlers();
        assertEquals(3, result);
    }

    /**
     * Test of addWrestlers method, of class Group.
     */
    @Test
    public void testAddWrestlers() {
        System.out.println("addWrestlers");
        List<Wrestler> wList = new ArrayList<Wrestler>();
        Wrestler w1 = new Wrestler("Jefferson", "Thomas", "Falcons", "Rookie",
                "1", "98", "C");
        wList.add(w1);
        Group g = this.group;
        g.addWrestlers(wList);
        int result = g.getNumWrestlers();
        assertEquals(3, result);
    }

    /**
     * Test of removeWrestler method, of class Group.
     */
    @Test
    public void testRemoveWrestler() {
        System.out.println("removeWrestler");
        Group g = this.group;
        g.removeWrestler(this.testGreen);
        int result = g.getNumWrestlers();
        assertEquals(1, result);
    }

    /**
     * Test of removeWresters method, of class Group.
     */
    @Test
    public void testRemoveWresters() {
        System.out.println("removeWresters");
        List<Wrestler> wList = new ArrayList<Wrestler>();
        wList.add(this.testRed);
        Group g = this.group;
        g.removeWresters(wList);
        int result = g.getNumWrestlers();
        assertEquals(1, result);
    }

    /**
     * Test of getBouts method, of class Group.
     */
    @Test
    public void testGetBouts_0args() {
        System.out.println("getBouts");
        Group g = this.group;
        List expResult = null;
        List result = g.getBouts();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBouts method, of class Group.
     */
    @Test
    public void testSetBouts() {
        System.out.println("setBouts");
        List<Bout> bList = new ArrayList<Bout>();
        Bout b = new Bout(this.testRed, this.testGreen, null, Bout.ROUND_1, 1, "A",
                false, false);
        bList.add(b);
        Group g = this.group;
        g.setBouts(bList);
        int result = g.getNumBouts();
        assertEquals(1, result);
    }

    /**
     * Test of getBouts method, of class Group.
     */
    @Test
    public void testGetBouts_boolean() {
        System.out.println("getBouts");
        List<Bout> bList = new ArrayList<Bout>();
        Bout b1 = new Bout(this.testRed, this.testGreen, null, Bout.ROUND_1, 1, "A",
                true, false);
        Bout b2 = new Bout(this.testRed, this.testGreen, null, Bout.ROUND_2, 1, "A",
                false, false);
        bList.add(b1);
        bList.add(b2);
        Group g = this.group;
        g.setBouts(bList);
        List expResult = new ArrayList<Bout>(bList);
        List result = g.getBouts(true);
        assertEquals(expResult, result);
        expResult.remove(b1);
        result = g.getBouts(false);
        assertEquals(expResult, result);
    }

    /**
     * Test of getBoutsByRound method, of class Group.
     */
    @Test
    public void testGetBoutsByRound_String() {
        System.out.println("getBoutsByRound");
        List<Bout> bList = new ArrayList<Bout>();
        Bout b1 = new Bout(this.testRed, this.testGreen, null, Bout.ROUND_1, 1, "A",
                true, false);
        Bout b2 = new Bout(this.testRed, this.testGreen, null, Bout.ROUND_2, 1, "A",
                false, false);
        bList.add(b1);
        bList.add(b2);
        String round = Bout.ROUND_2;
        Group g = this.group;
        g.setBouts(bList);
        List expResult = new ArrayList<Bout>(bList);
        expResult.remove(b1);
        List result = g.getBoutsByRound(round);
        assertEquals(expResult, result);
    }

    /**
     * Test of getBoutsByRound method, of class Group.
     */
    @Test
    public void testGetBoutsByRound_String_boolean() {
        System.out.println("getBoutsByRound");
        List<Bout> bList = new ArrayList<Bout>();
        Bout b1 = new Bout(this.testRed, this.testGreen, null, Bout.ROUND_1, 1, "A",
                true, false);
        Bout b2 = new Bout(this.testRed, this.testGreen, null, Bout.ROUND_2, 1, "A",
                false, false);
        bList.add(b1);
        bList.add(b2);
        String round = Bout.ROUND_1;
        Group g = this.group;
        g.setBouts(bList);
        List expResult = new ArrayList<Bout>(bList);
        expResult.remove(b2);
        List result = g.getBoutsByRound(round, true);
        assertEquals(expResult, result);
        expResult.remove(b1);
        result = g.getBoutsByRound(round, false);
        assertEquals(expResult, result);
    }

    /**
     * Test of getBout method, of class Group.
     */
    @Test
    public void testGetBout() {
        System.out.println("getBout");
        List<Bout> bList = new ArrayList<Bout>();
        Bout b1 = new Bout(this.testRed, this.testGreen, null, Bout.ROUND_1, 1, "A",
                true, false);
        Bout b2 = new Bout(this.testRed, this.testGreen, null, Bout.ROUND_2, 1, "A",
                false, false);
        bList.add(b1);
        bList.add(b2);
        String round = Bout.ROUND_2;
        int sequence = 1;
        Group g = this.group;
        g.setBouts(bList);
        Bout expResult = b2;
        Bout result = g.getBout(round, sequence);
        assertEquals(expResult, result);
    }

    /**
     * Test of getWeightClass method, of class Group.
     */
    @Test
    public void testGetWeightClass() {
        System.out.println("getWeightClass");
        Group g = this.group;
        String expResult = "76-87";
        String result = g.getWeightClass();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAgeDivision method, of class Group.
     */
    @Test
    public void testGetAgeDivision() {
        System.out.println("getAgeDivision");
        Group g = this.group;
        String expResult = "1";
        String result = g.getAgeDivision();
        assertEquals(expResult, result);
    }

    /**
     * Test of getClassification method, of class Group.
     */
    @Test
    public void testGetClassification() {
        System.out.println("getClassification");
        Group g = this.group;
        String expResult = "Rookie";
        String result = g.getClassification();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Group.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Group g = this.group;
        String expResult = "Rookie:1:76-87";
        String result = g.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWrestlerAtSeed method, of class Group.
     */
    @Test
    public void testGetWrestlerAtSeed() {
        System.out.println("getWrestlerAtSeed");
        int seed = 1;
        Group g = this.group;
        this.testGreen.setSeed(1);
        this.testRed.setSeed(2);
        Wrestler expResult = this.testGreen;
        Wrestler result = g.getWrestlerAtSeed(seed);
        assertEquals(expResult, result);
    }

}