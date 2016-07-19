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
 * Unit tests for bouttime.model.Bout class.
 */
public class BoutTest {

    private Wrestler testRed;
    private Wrestler testGreen;
    private Bout bout;

    public BoutTest() { }

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
        this.bout = new Bout(testRed, testGreen, null, Bout.ROUND_1, 1, "A",
                false, false);
    }

    @After
    public void tearDown() { }

    /**
     * Test of getFinalResult method, of class Bout.
     */
    @Test
    public void testGetFinalResult() {
        System.out.println("getFinalResult");
        Bout b = this.bout;
        String result = b.getFinalResult();
        assertEquals(null, result);
    }

    /**
     * Test of setFinalResult method, of class Bout.
     */
    @Test
    public void testSetFinalResult() {
        System.out.println("setFinalResult");
        Bout b = this.bout;
        String expResult = "Pin 1:01";
        b.setFinalResult(expResult);
        String result = b.getFinalResult();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGreen method, of class Bout.
     */
    @Test
    public void testGetGreen() {
        System.out.println("getGreen");
        Bout b = this.bout;
        Wrestler expResult = this.testGreen;
        Wrestler result = b.getGreen();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGreen method, of class Bout.
     */
    @Test
    public void testSetGreen() {
        System.out.println("setGreen");
        Wrestler expResult = new Wrestler("Jefferson", "Thomas", "Falcons", "Rookie",
                "1", "98", "C");
        Bout b = this.bout;
        b.setGreen(expResult);
        Wrestler result = b.getGreen();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGroup method, of class Bout.
     */
    @Test
    public void testGetGroup() {
        System.out.println("getGroup");
        Bout b = this.bout;
        Group expResult = null;
        Group result = b.getGroup();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGroup method, of class Bout.
     */
    @Test
    public void testSetGroup() {
        System.out.println("setGroup");
        List<Wrestler> list = new ArrayList<Wrestler>();
        list.add(this.testRed);
        list.add(this.testGreen);
        Group expResult = new Group(list);
        Bout b = this.bout;
        b.setGroup(expResult);
        Group result = b.getGroup();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class Bout.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Bout b = this.bout;
        Integer expResult = null;
        Integer result = b.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Bout.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Integer expResult = 1;
        Bout b = this.bout;
        b.setId(expResult);
        Integer result = b.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSequence method, of class Bout.
     */
    @Test
    public void testGetSequence() {
        System.out.println("getSequence");
        Bout b = this.bout;
        int expResult = 1;
        int result = b.getSequence();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSequence method, of class Bout.
     */
    @Test
    public void testSetSequence() {
        System.out.println("setSequence");
        int expResult = 2;
        Bout b = this.bout;
        b.setSequence(expResult);
        int result = b.getSequence();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBoutTime method, of class Bout.
     */
    @Test
    public void testGetBoutTime() {
        System.out.println("getBoutTime");
        Bout b = this.bout;
        String expResult = null;
        String result = b.getBoutTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBoutTime method, of class Bout.
     */
    @Test
    public void testSetBoutTime() {
        System.out.println("setBoutTime");
        String expResult = "1:00";
        Bout b = this.bout;
        b.setBoutTime(expResult);
        String result = b.getBoutTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLabel method, of class Bout.
     */
    @Test
    public void testGetLabel() {
        System.out.println("getLabel");
        Bout b = this.bout;
        String expResult = "A";
        String result = b.getLabel();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLabel method, of class Bout.
     */
    @Test
    public void testSetLabel() {
        System.out.println("setLabel");
        String expResult = "Championship";
        Bout b = this.bout;
        b.setLabel(expResult);
        String result = b.getLabel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLoserNextBout method, of class Bout.
     */
    @Test
    public void testGetLoserNextBout() {
        System.out.println("getLoserNextBout");
        Bout b = this.bout;
        Bout expResult = null;
        Bout result = b.getLoserNextBout();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLoserNextBout method, of class Bout.
     */
    @Test
    public void testSetLoserNextBout() {
        System.out.println("setLoserNextBout");
        Bout expResult = new Bout(null, null, null, Bout.ROUND_2, 2);
        Bout b = this.bout;
        b.setLoserNextBout(expResult);
        Bout result = b.getLoserNextBout();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRed method, of class Bout.
     */
    @Test
    public void testGetRed() {
        System.out.println("getRed");
        Bout b = this.bout;
        Wrestler expResult = this.testRed;
        Wrestler result = b.getRed();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRed method, of class Bout.
     */
    @Test
    public void testSetRed() {
        System.out.println("setRed");
        Wrestler expResult = new Wrestler("Jefferson", "Thomas", "Falcons", "Rookie",
                "1", "98", "C");
        Bout b = this.bout;
        b.setRed(expResult);
        Wrestler result = b.getRed();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRound method, of class Bout.
     */
    @Test
    public void testGetRound() {
        System.out.println("getRound");
        Bout b = this.bout;
        String expResult = Bout.ROUND_1;
        String result = b.getRound();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRound method, of class Bout.
     */
    @Test
    public void testSetRound() {
        System.out.println("setRound");
        String expResult = Bout.ROUND_2;
        Bout b = this.bout;
        b.setRound(expResult);
        String result = b.getRound();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLoser method, of class Bout.
     */
    @Test
    public void testGetLoser() {
        System.out.println("getLoser");
        Bout b = this.bout;
        Wrestler expResult = null;
        Wrestler result = b.getLoser();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWinner method, of class Bout.
     */
    @Test
    public void testGetWinner() {
        System.out.println("getWinner");
        Bout b = this.bout;
        Wrestler expResult = null;
        Wrestler result = b.getWinner();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWinner method, of class Bout.
     */
    @Test
    public void testSetWinner_String() {
        System.out.println("setWinner");
        Wrestler expResult = this.testGreen;
        Bout b = this.bout;
        b.setWinner(this.testGreen.getFirstInitialLastName());
        Wrestler result = b.getWinner();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWinner method, of class Bout.
     */
    @Test
    public void testSetWinner_Wrestler() {
        System.out.println("setWinner");
        Wrestler expResult = this.testGreen;
        Bout b = this.bout;
        b.setWinner(expResult);
        Wrestler result = b.getWinner();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWinnerNextBout method, of class Bout.
     */
    @Test
    public void testGetWinnerNextBout() {
        System.out.println("getWinnerNextBout");
        Bout b = this.bout;
        Bout expResult = null;
        Bout result = b.getWinnerNextBout();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWinnerNextBout method, of class Bout.
     */
    @Test
    public void testSetWinnerNextBout() {
        System.out.println("setWinnerNextBout");
        Bout expResult = new Bout(null, null, null, Bout.ROUND_2, 2);
        Bout b = this.bout;
        b.setWinnerNextBout(expResult);
        Bout result = b.getWinnerNextBout();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBoutNum method, of class Bout.
     */
    @Test
    public void testGetBoutNum() {
        System.out.println("getBoutNum");
        Bout b = this.bout;
        String expResult = "";
        String result = b.getBoutNum();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBoutNum method, of class Bout.
     */
    @Test
    public void testSetBoutNum() {
        System.out.println("setBoutNum");
        String expResult = "5";
        Bout b = this.bout;
        b.setBoutNum(expResult);
        String result = b.getBoutNum();
        assertEquals(expResult, result);
    }

    /**
     * Test of isBye method, of class Bout.
     */
    @Test
    public void testIsBye() {
        System.out.println("isBye");
        Bout b = this.bout;
        boolean expResult = false;
        boolean result = b.isBye();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBye method, of class Bout.
     */
    @Test
    public void testGetBye() {
        System.out.println("getBye");
        Bout b = this.bout;
        boolean expResult = false;
        boolean result = b.getBye();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBye method, of class Bout.
     */
    @Test
    public void testSetBye() {
        System.out.println("setBye");
        boolean expResult = true;
        Bout b = this.bout;
        b.setBye(expResult);
        boolean result = b.isBye();
        assertEquals(expResult, result);
    }

    /**
     * Test of isConsolation method, of class Bout.
     */
    @Test
    public void testIsConsolation() {
        System.out.println("isConsolation");
        Bout b = this.bout;
        boolean expResult = false;
        boolean result = b.isConsolation();
        assertEquals(expResult, result);
    }

    /**
     * Test of getConsolation method, of class Bout.
     */
    @Test
    public void testGetConsolation() {
        System.out.println("getConsolation");
        Bout b = this.bout;
        boolean expResult = false;
        boolean result = b.getConsolation();
        assertEquals(expResult, result);
    }

    /**
     * Test of setConsolation method, of class Bout.
     */
    @Test
    public void testSetConsolation() {
        System.out.println("setConsolation");
        boolean expResult = true;
        Bout b = this.bout;
        b.setConsolation(expResult);
        boolean result = b.isConsolation();
        assertEquals(expResult, result);
    }

}