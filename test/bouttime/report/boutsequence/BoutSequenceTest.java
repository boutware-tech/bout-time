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

package bouttime.report.boutsequence;

import bouttime.model.Bout;
import bouttime.sort.BoutSort;
import java.util.Collections;
import bouttime.boutmaker.BoutMaker;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A class to test bouttime.report.boutsequence.BoutSequence.
 */
public class BoutSequenceTest {
    
    static private List<Wrestler> wrestlerList;
    
    public BoutSequenceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        wrestlerList = new ArrayList<Wrestler>();
        wrestlerList.add(new Wrestler("Adamson", "Adam", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Bradson", "Brad", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Carlson", "Carl", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Davidson", "David", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Ericson", "Eric", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Frankson", "Frank", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Garrison", "Gary", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Harrison", "Harry", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Isaacson", "Isaac", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Jefferson", "Jeff", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Kyleson", "Kyle", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Larrison", "Larry", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Markson", "Mark", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Neilson", "Neil", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Owenson", "Owen", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Peterson", "Peter", "E", "R", "1", "100", "1"));
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
    
    /***************************************************************************
     * TEST METHODS
     **************************************************************************/

    /**
     * Test 8-man bracket : standard, 1st seed.
     */
    @Test
    public void testCalculate8BracketStdSeed1() {
        testCalculateStd(8, 1, new String [] {"12","9","7","1","12","10","5","11"});
    }
    
    /**
     * Test 8-man bracket : 2nd, 1st seed.
     */
    @Test
    public void testCalculate8Bracket2ndSeed1() {
        testCalculate2nd(8, 1, new String [] {"13","12","9","7","1","13","12","10","5","13","11"});
    }

    /**
     * Test 8-man bracket : standard, 2nd seed
     */
    @Test
    public void testCalculate8BracketStdSeed2() {
        testCalculateStd(8, 2, new String [] {"12","10","8","4","12","9","6","11"});
    }

    /**
     * Test 7-man bracket : standard, 1st seed.
     */
    @Test
    public void testCalculate7BracketStdSeed1() {
        testCalculateStd(7, 1, new String [] {"10","8","4","9"});
    }

    /**
     * Test 6-man bracket : standard, 2nd seed.
     */
    @Test
    public void testCalculate6BracketStdSeed2() {
        testCalculateStd(6, 2, new String [] {"8","5","4","7"});
    }

    /**
     * Test 16-man bracket : standard, 1st seed.
     */
    @Test
    public void testCalculate16BracketStdSeed1() {
        testCalculateStd(16, 1, new String [] {"28","25","23","17","13","1","28","26","24","20","9","28","25","21","27"});
    }

    /**
     * Test 16-man bracket : 2nd, 1st seed.
     */
    @Test
    public void testCalculate16Bracket2ndSeed1() {
        testCalculate2nd(16, 1, new String [] {"29","28","25","23","17","13","1","29","28","26","24","20","9","29","28","25","21","29","27"});
    }

    /**
     * Test 16-man bracket : standard, 14th seed.
     */
    @Test
    public void testCalculate16BracketStdSeed14() {
        testCalculateStd(16, 14, new String [] {"28","26","24","19","15","5","28","25","23","18","11","28","26","22","27"});
    }

    /**
     * Test 10-man bracket : standard, 4th seed.
     */
    @Test
    public void testCalculate10BracketStdSeed4() {
        testCalculateStd(10, 4, new String [] {"16","14","12","4","16","13","9","15"});
    }
    
    /***************************************************************************
     * HELPER METHODS
     **************************************************************************/
    
    private void testCalculateStd(int groupSize, int seed, String [] expected) {
        testCalculate(groupSize, seed, expected, false, false, false, 1);
    }
    
    private void testCalculate2nd(int groupSize, int seed, String [] expected) {
        testCalculate(groupSize, seed, expected, false, true, false, 1);
    }
    
    private void testCalculate5th(int groupSize, int seed, String [] expected) {
        testCalculate(groupSize, seed, expected, true, false, false, 1);
    }
    
    private void testCalculate2nd5th(int groupSize, int seed, String [] expected) {
        testCalculate(groupSize, seed, expected, true, true, false, 1);
    }
    
    private void testCalculateRr5(int groupSize, int seed, String [] expected) {
        testCalculate(groupSize, seed, expected, false, false, true, 5);
    }
    
    private void testCalculate(int groupSize, int seed, String [] expected, boolean fifth, boolean second, boolean rr, int rrMax) {
        Group group = generateGroup(groupSize);
        BoutMaker.makeBouts(group, fifth, second, rr, rrMax, new Wrestler());
        assignBoutNumbers(group);
        
        List<Bout> result = BoutSequence.calculate(group.getWrestlerAtSeed(seed));
        //debugResult(result);
        checkBracketResult(expected, result);
    }
    
    private Group generateGroup(int num) {
        List<Wrestler> wList = new ArrayList<Wrestler>();
        for (int i = 0; i < num; i++) {
            wList.add(wrestlerList.get(i));
        }
        
        Group g = new Group(wList);
        return g;
    }
    
    private void assignBoutNumbers(Group g) {
        Collections.sort(g.getBouts(true), new BoutSort());
        Integer boutNum = new Integer(1);
        for (Bout b : g.getBouts()) {
            b.setBoutNum(boutNum.toString());
            boutNum++;
        }
    }
    
    private void checkBracketResult(String [] expected, List<Bout> result) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals("bout " + i, expected[i], result.get(i).getBoutNum());
        }
    }
    
    private void debugResult(List<Bout> result) {
        for (Bout b : result) {
            if (b.isConsolation()) {
                System.out.println(String.format("%2s  %2s  %2s  %s", b.getBoutNum(), b.getRound(), b.getSequence(), b.getLabel()));
            } else {
                System.out.println(String.format("[ %2s ]  %2s  %2s  %s", b.getBoutNum(), b.getRound(), b.getSequence(), b.getLabel()));
            }
        }
        System.out.println("----------");
    }
}
