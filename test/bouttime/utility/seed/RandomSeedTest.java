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
package bouttime.utility.seed;

import bouttime.sort.WrestlerSeedSort;
import bouttime.model.Group;
import java.util.ArrayList;
import bouttime.model.Wrestler;
import java.util.Collections;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class to test the RandomSeed class.
 */
public class RandomSeedTest {
    static private List<Wrestler> wrestlerList;
    
    public RandomSeedTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        wrestlerList = new ArrayList<Wrestler>();
        wrestlerList.add(new Wrestler("Adamson", "Adam", "A", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Bradson", "Brad", "B", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Carlson", "Carl", "C", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Davidson", "David", "D", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Ericson", "Eric", "E", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Frankson", "Frank", "F", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Garrison", "Gary", "G", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Harrison", "Harry", "H", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Isaacson", "Isaac", "I", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Jefferson", "Jeff", "J", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Kyleson", "Kyle", "K", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Larrison", "Larry", "L", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Markson", "Mark", "M", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Neilson", "Neil", "N", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Owenson", "Owen", "O", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Peterson", "Peter", "P", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Quinnson", "Quinn", "Q", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Robson", "Rob", "R", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Stanson", "Stan", "S", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Timson", "Tim", "T", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Ulison", "Uli", "U", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Vinson", "Vin", "V", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Walterson", "Walter", "W", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Xanderson", "Xander", "X", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Yetison", "Yeti", "Y", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Zakson", "Zak", "Z", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Abeson", "Abe", "AA", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Barryson", "Barry", "BB", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Clarkson", "Clark", "CC", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Danson", "Dan", "DD", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Evanson", "Evan", "EE", "R", "1", "100", "1"));
        wrestlerList.add(new Wrestler("Fredson", "Fred", "FF", "R", "1", "100", "1"));
    }
    
    @After
    public void tearDown() {
    }
    
    /***************************************************************************
     * TEST METHODS
     **************************************************************************/
    
    /**
     * Test 4-man bracket, no same team
     */
    @Test
    public void test4NoSameTeam() {
        genericTestDriver(4);
    }
    
    /**
     * Test 3-man bracket, no same team
     */
    @Test
    public void test3NoSameTeam() {
        genericTestDriver(3);
    }
    
    /**
     * Test 4-man bracket, 2 same team
     */
    @Test
    public void test4_2SameTeam() {
        String teamName = "Same";
        Group group = generateGroup(4);
        group.getWrestlers().get(1).setTeamName(teamName);
        group.getWrestlers().get(2).setTeamName(teamName);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each half
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st half", isTeamInBucket(teamName, wList, RandomSeed.BRACKET4_HALF1));
        Assert.assertTrue("Team not in 2nd half", isTeamInBucket(teamName, wList, RandomSeed.BRACKET4_HALF2));
    }
    
    /**
     * Test 8-man bracket, no same team
     */
    @Test
    public void test8NoSameTeam() {
        genericTestDriver(8);
    }
    
    /**
     * Test 8-man bracket, 2 same team
     */
    @Test
    public void test8_2SameTeam() {
        String teamName = "Same";
        Group group = generateGroup(8);
        group.getWrestlers().get(1).setTeamName(teamName);
        group.getWrestlers().get(6).setTeamName(teamName);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each half
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st half", isTeamInBucket(teamName, wList, RandomSeed.BRACKET8_HALF1));
        Assert.assertTrue("Team not in 2nd half", isTeamInBucket(teamName, wList, RandomSeed.BRACKET8_HALF2));
    }
    
    /**
     * Test 8-man bracket, 2 same geo
     */
    @Test
    public void test8_2SameGeo() {
        String geoName = "Same";
        Group group = generateGroup(8);
        group.getWrestlers().get(1).setGeo(geoName);
        group.getWrestlers().get(6).setGeo(geoName);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each half
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Geo not in 1st half", isGeoInBucket(geoName, wList, RandomSeed.BRACKET8_HALF1));
        Assert.assertTrue("Geo not in 2nd half", isGeoInBucket(geoName, wList, RandomSeed.BRACKET8_HALF2));
    }
    
    /**
     * Test 8-man bracket, 4 same team
     */
    @Test
    public void test8_4SameTeam() {
        String teamName = "Same";
        Group group = generateGroup(8);
        group.getWrestlers().get(1).setTeamName(teamName);
        group.getWrestlers().get(3).setTeamName(teamName);
        group.getWrestlers().get(5).setTeamName(teamName);
        group.getWrestlers().get(7).setTeamName(teamName);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each quarter
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st quarter", isTeamInBucket(teamName, wList, RandomSeed.BRACKET8_QUARTER1));
        Assert.assertTrue("Team not in 2nd quarter", isTeamInBucket(teamName, wList, RandomSeed.BRACKET8_QUARTER2));
        Assert.assertTrue("Team not in 3rd quarter", isTeamInBucket(teamName, wList, RandomSeed.BRACKET8_QUARTER3));
        Assert.assertTrue("Team not in 4th quarter", isTeamInBucket(teamName, wList, RandomSeed.BRACKET8_QUARTER4));
    }
    
    /**
     * Test 8-man bracket, 4 same geo
     */
    @Test
    public void test8_4SameGeo() {
        String geoName = "Same";
        Group group = generateGroup(8);
        group.getWrestlers().get(1).setGeo(geoName);
        group.getWrestlers().get(3).setGeo(geoName);
        group.getWrestlers().get(5).setGeo(geoName);
        group.getWrestlers().get(7).setGeo(geoName);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each quarter
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Geo not in 1st quarter", isGeoInBucket(geoName, wList, RandomSeed.BRACKET8_QUARTER1));
        Assert.assertTrue("Geo not in 2nd quarter", isGeoInBucket(geoName, wList, RandomSeed.BRACKET8_QUARTER2));
        Assert.assertTrue("Geo not in 3rd quarter", isGeoInBucket(geoName, wList, RandomSeed.BRACKET8_QUARTER3));
        Assert.assertTrue("Geo not in 4th quarter", isGeoInBucket(geoName, wList, RandomSeed.BRACKET8_QUARTER4));
    }
    
    /**
     * Test 8-man bracket, 2 same team and geo
     */
    @Test
    public void test8_2SameTeamAndGeo() {
        String name = "Same";
        Group group = generateGroup(8);
        group.getWrestlers().get(1).setGeo(name);
        group.getWrestlers().get(1).setTeamName(name);
        group.getWrestlers().get(5).setGeo(name);
        group.getWrestlers().get(5).setTeamName(name);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each half
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st half", isTeamInBucket(name, wList, RandomSeed.BRACKET8_HALF1));
        Assert.assertTrue("Geo not in 1st half", isGeoInBucket(name, wList, RandomSeed.BRACKET8_HALF2));
        Assert.assertTrue("Team not in 2nd half", isTeamInBucket(name, wList, RandomSeed.BRACKET8_HALF2));
        Assert.assertTrue("Geo not in 2nd half", isGeoInBucket(name, wList, RandomSeed.BRACKET8_HALF2));
    }
    
    /**
     * Test 8-man bracket, 2 same team and 4 same geo
     */
    @Test
    public void test8_2SameTeamAnd4SameGeo() {
        String name = "Same";
        Group group = generateGroup(8);
        group.getWrestlers().get(1).setGeo(name);
        group.getWrestlers().get(1).setTeamName(name);
        group.getWrestlers().get(3).setGeo(name);
        group.getWrestlers().get(5).setGeo(name);
        group.getWrestlers().get(5).setTeamName(name);
        group.getWrestlers().get(7).setGeo(name);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each half
        // And 1 from same geo is in each quarter
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st half", isTeamInBucket(name, wList, RandomSeed.BRACKET8_HALF1));
        Assert.assertTrue("Team not in 2nd half", isTeamInBucket(name, wList, RandomSeed.BRACKET8_HALF2));
        Assert.assertTrue("Geo not in 1st quarter", isGeoInBucket(name, wList, RandomSeed.BRACKET8_QUARTER1));
        Assert.assertTrue("Geo not in 2nd quarter", isGeoInBucket(name, wList, RandomSeed.BRACKET8_QUARTER2));
        Assert.assertTrue("Geo not in 3rd quarter", isGeoInBucket(name, wList, RandomSeed.BRACKET8_QUARTER3));
        Assert.assertTrue("Geo not in 4th quarter", isGeoInBucket(name, wList, RandomSeed.BRACKET8_QUARTER4));
    }

    /**
     * Test 6-man bracket, no same team
     */
    @Test
    public void test6NoSameTeam() {
        genericTestDriver(6);
    }
    
    /**
     * Test 16-man bracket, no same team
     */
    @Test
    public void test16NoSameTeam() {
        genericTestDriver(16);
    }
    
    /**
     * Test 10-man bracket, no same team
     */
    @Test
    public void test10NoSameTeam() {
        genericTestDriver(10);
    }
    
    /**
     * Test 16-man bracket, 2 same team
     */
    @Test
    public void test16_2SameTeam() {
        String teamName = "Same";
        Group group = generateGroup(16);
        group.getWrestlers().get(1).setTeamName(teamName);
        group.getWrestlers().get(6).setTeamName(teamName);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each half
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st half", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_HALF1));
        Assert.assertTrue("Team not in 2nd half", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_HALF2));
    }
    
    /**
     * Test 16-man bracket, 4 same team
     */
    @Test
    public void test16_4SameTeam() {
        String teamName = "Same";
        Group group = generateGroup(16);
        group.getWrestlers().get(1).setTeamName(teamName);
        group.getWrestlers().get(3).setTeamName(teamName);
        group.getWrestlers().get(11).setTeamName(teamName);
        group.getWrestlers().get(13).setTeamName(teamName);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each quarter
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st quarter", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_QUARTER1));
        Assert.assertTrue("Team not in 2nd quarter", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_QUARTER2));
        Assert.assertTrue("Team not in 3rd quarter", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_QUARTER3));
        Assert.assertTrue("Team not in 4th quarter", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_QUARTER4));
    }
    
    /**
     * Test 16-man bracket, 6 same team
     */
    @Test
    public void test16_6SameTeam() {
        String teamName = "Same";
        Group group = generateGroup(16);
        group.getWrestlers().get(1).setTeamName(teamName);
        group.getWrestlers().get(3).setTeamName(teamName);
        group.getWrestlers().get(6).setTeamName(teamName);
        group.getWrestlers().get(11).setTeamName(teamName);
        group.getWrestlers().get(13).setTeamName(teamName);
        group.getWrestlers().get(14).setTeamName(teamName);
        RandomSeed.execute(group);
        
        // Make sure 3 from same team is in each half
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertEquals("Same team in 1st half", 3, countTeamInBucket(teamName, wList, RandomSeed.BRACKET16_HALF1));
        Assert.assertEquals("Same team in 2nd half", 3, countTeamInBucket(teamName, wList, RandomSeed.BRACKET16_HALF2));
    }
    
    /**
     * Test 16-man bracket, 6 same geo
     */
    @Test
    public void test16_6SameGeo() {
        String geoName = "Same";
        Group group = generateGroup(16);
        group.getWrestlers().get(1).setGeo(geoName);
        group.getWrestlers().get(3).setGeo(geoName);
        group.getWrestlers().get(6).setGeo(geoName);
        group.getWrestlers().get(11).setGeo(geoName);
        group.getWrestlers().get(13).setGeo(geoName);
        group.getWrestlers().get(14).setGeo(geoName);
        RandomSeed.execute(group);
        
        // Make sure 3 from same team is in each half
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertEquals("Same geo in 1st half", 3, countGeoInBucket(geoName, wList, RandomSeed.BRACKET16_HALF1));
        Assert.assertEquals("Same geo in 2nd half", 3, countGeoInBucket(geoName, wList, RandomSeed.BRACKET16_HALF2));
    }
    
    /**
     * Test 16-man bracket, 8 same team
     */
    @Test
    public void test16_8SameTeam() {
        String teamName = "Same";
        Group group = generateGroup(16);
        group.getWrestlers().get(1).setTeamName(teamName);
        group.getWrestlers().get(2).setTeamName(teamName);
        group.getWrestlers().get(3).setTeamName(teamName);
        group.getWrestlers().get(4).setTeamName(teamName);
        group.getWrestlers().get(6).setTeamName(teamName);
        group.getWrestlers().get(8).setTeamName(teamName);
        group.getWrestlers().get(11).setTeamName(teamName);
        group.getWrestlers().get(13).setTeamName(teamName);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each eighth
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_EIGHTH1));
        Assert.assertTrue("Team not in 2nd eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_EIGHTH2));
        Assert.assertTrue("Team not in 3rd eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_EIGHTH3));
        Assert.assertTrue("Team not in 4th eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_EIGHTH4));
        Assert.assertTrue("Team not in 5th eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_EIGHTH5));
        Assert.assertTrue("Team not in 6th eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_EIGHTH6));
        Assert.assertTrue("Team not in 7th eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_EIGHTH7));
        Assert.assertTrue("Team not in 8th eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET16_EIGHTH8));
    }
    
    /**
     * Test 16-man bracket, 2 same team and same geo
     */
    @Test
    public void test16_2SameTeamAndGeo() {
        String name = "Same";
        Group group = generateGroup(16);
        group.getWrestlers().get(1).setGeo(name);
        group.getWrestlers().get(1).setTeamName(name);
        group.getWrestlers().get(9).setGeo(name);
        group.getWrestlers().get(9).setTeamName(name);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each half
        // And 1 from same geo is in each half
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st half", isTeamInBucket(name, wList, RandomSeed.BRACKET16_HALF1));
        Assert.assertTrue("Team not in 2nd half", isTeamInBucket(name, wList, RandomSeed.BRACKET16_HALF2));
        Assert.assertTrue("Geo not in 1st half", isGeoInBucket(name, wList, RandomSeed.BRACKET16_HALF1));
        Assert.assertTrue("Geo not in 2nd half", isGeoInBucket(name, wList, RandomSeed.BRACKET16_HALF2));
    }
    
    /**
     * Test 16-man bracket, 2 same team and 4 same geo
     */
    @Test
    public void test16_2SameTeamAnd4SameGeo() {
        String name = "Same";
        Group group = generateGroup(16);
        group.getWrestlers().get(1).setGeo(name);
        group.getWrestlers().get(1).setTeamName(name);
        group.getWrestlers().get(3).setGeo(name);
        group.getWrestlers().get(9).setGeo(name);
        group.getWrestlers().get(9).setTeamName(name);
        group.getWrestlers().get(12).setGeo(name);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each half
        // And 1 from same geo is in each quarter
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st half", isTeamInBucket(name, wList, RandomSeed.BRACKET16_HALF1));
        Assert.assertTrue("Team not in 2nd half", isTeamInBucket(name, wList, RandomSeed.BRACKET16_HALF2));
        Assert.assertTrue("Geo not in 1st quarter", isGeoInBucket(name, wList, RandomSeed.BRACKET16_QUARTER1));
        Assert.assertTrue("Geo not in 2nd quarter", isGeoInBucket(name, wList, RandomSeed.BRACKET16_QUARTER2));
        Assert.assertTrue("Geo not in 3rd quarter", isGeoInBucket(name, wList, RandomSeed.BRACKET16_QUARTER3));
        Assert.assertTrue("Geo not in 4th quarter", isGeoInBucket(name, wList, RandomSeed.BRACKET16_QUARTER4));
    }
    
    /**
     * Test 32-man bracket, no same team
     */
    @Test
    public void test32NoSameTeam() {
        genericTestDriver(32);
    }
    
    /**
     * Test 30-man bracket, no same team
     */
    @Test
    public void test30NoSameTeam() {
        genericTestDriver(30);
    }
    
    /**
     * Test 25-man bracket, no same team
     */
    @Test
    public void test25NoSameTeam() {
        genericTestDriver(25);
    }
    
    /**
     * Test 18-man bracket, no same team
     */
    @Test
    public void test18NoSameTeam() {
        genericTestDriver(18);
    }
    
    /**
     * Test 32-man bracket, 2 same team
     */
    @Test
    public void test32_2SameTeam() {
        String teamName = "Same";
        Group group = generateGroup(32);
        group.getWrestlers().get(7).setTeamName(teamName);
        group.getWrestlers().get(12).setTeamName(teamName);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each half
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st half", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_HALF1));
        Assert.assertTrue("Team not in 2nd half", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_HALF2));
    }
    
    /**
     * Test 32-man bracket, 4 same team
     */
    @Test
    public void test32_4SameTeam() {
        String teamName = "Same";
        Group group = generateGroup(32);
        group.getWrestlers().get(1).setTeamName(teamName);
        group.getWrestlers().get(7).setTeamName(teamName);
        group.getWrestlers().get(12).setTeamName(teamName);
        group.getWrestlers().get(21).setTeamName(teamName);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each quarter
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st quarter", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_QUARTER1));
        Assert.assertTrue("Team not in 2nd quarter", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_QUARTER2));
        Assert.assertTrue("Team not in 3rd quarter", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_QUARTER3));
        Assert.assertTrue("Team not in 4th quarter", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_QUARTER4));
    }
    
    /**
     * Test 32-man bracket, 8 same team
     */
    @Test
    public void test32_8SameTeam() {
        String teamName = "Same";
        Group group = generateGroup(32);
        group.getWrestlers().get(1).setTeamName(teamName);
        group.getWrestlers().get(2).setTeamName(teamName);
        group.getWrestlers().get(3).setTeamName(teamName);
        group.getWrestlers().get(7).setTeamName(teamName);
        group.getWrestlers().get(12).setTeamName(teamName);
        group.getWrestlers().get(18).setTeamName(teamName);
        group.getWrestlers().get(21).setTeamName(teamName);
        group.getWrestlers().get(30).setTeamName(teamName);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each eighth
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_EIGHTH1));
        Assert.assertTrue("Team not in 2nd eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_EIGHTH2));
        Assert.assertTrue("Team not in 3rd eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_EIGHTH3));
        Assert.assertTrue("Team not in 4th eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_EIGHTH4));
        Assert.assertTrue("Team not in 5th eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_EIGHTH5));
        Assert.assertTrue("Team not in 6th eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_EIGHTH6));
        Assert.assertTrue("Team not in 7th eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_EIGHTH7));
        Assert.assertTrue("Team not in 8th eighth", isTeamInBucket(teamName, wList, RandomSeed.BRACKET32_EIGHTH8));
    }
    
    /**
     * Test 32-man bracket, 2 same team and same geo
     */
    @Test
    public void test32_2SameTeamAndGeo() {
        String name = "Same";
        Group group = generateGroup(32);
        group.getWrestlers().get(15).setGeo(name);
        group.getWrestlers().get(15).setTeamName(name);
        group.getWrestlers().get(29).setGeo(name);
        group.getWrestlers().get(29).setTeamName(name);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each half
        // And 1 from same geo is in each half
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st half", isTeamInBucket(name, wList, RandomSeed.BRACKET32_HALF1));
        Assert.assertTrue("Team not in 2nd half", isTeamInBucket(name, wList, RandomSeed.BRACKET32_HALF2));
        Assert.assertTrue("Geo not in 1st half", isGeoInBucket(name, wList, RandomSeed.BRACKET32_HALF1));
        Assert.assertTrue("Geo not in 2nd half", isGeoInBucket(name, wList, RandomSeed.BRACKET32_HALF2));
    }
    
    /**
     * Test 32-man bracket, 2 same team and 4 same geo
     */
    @Test
    public void test32_2SameTeamAnd4SameGeo() {
        String name = "Same";
        Group group = generateGroup(32);
        group.getWrestlers().get(1).setGeo(name);
        group.getWrestlers().get(1).setTeamName(name);
        group.getWrestlers().get(3).setGeo(name);
        group.getWrestlers().get(19).setGeo(name);
        group.getWrestlers().get(19).setTeamName(name);
        group.getWrestlers().get(28).setGeo(name);
        RandomSeed.execute(group);
        
        // Make sure 1 from same team is in each half
        // And 1 from same geo is in each quarter
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        Assert.assertTrue("Team not in 1st half", isTeamInBucket(name, wList, RandomSeed.BRACKET32_HALF1));
        Assert.assertTrue("Team not in 2nd half", isTeamInBucket(name, wList, RandomSeed.BRACKET32_HALF2));
        Assert.assertTrue("Geo not in 1st quarter", isGeoInBucket(name, wList, RandomSeed.BRACKET32_QUARTER1));
        Assert.assertTrue("Geo not in 2nd quarter", isGeoInBucket(name, wList, RandomSeed.BRACKET32_QUARTER2));
        Assert.assertTrue("Geo not in 3rd quarter", isGeoInBucket(name, wList, RandomSeed.BRACKET32_QUARTER3));
        Assert.assertTrue("Geo not in 4th quarter", isGeoInBucket(name, wList, RandomSeed.BRACKET32_QUARTER4));
    }
    
    /***************************************************************************
     * HELPER METHODS
     **************************************************************************/
    
    private Group generateGroup(int num) {
        List<Wrestler> wList = new ArrayList<Wrestler>();
        for (int i = 0; i < num; i++) {
            wList.add(wrestlerList.get(i));
        }
        
        Group g = new Group(wList);
        return g;
    }
    
    private void genericTestDriver(int numInGroup) {
        Group group = generateGroup(numInGroup);
        RandomSeed.execute(group);
        
        if (numInGroup < 5) {
            // May get too many false failures for small groups
            return;
        }
        
        // Save this for comparison later
        Wrestler [] wrestlerArray = new Wrestler [numInGroup];
        List<Wrestler> wList = group.getWrestlers();
        Collections.sort(wList, new WrestlerSeedSort());
        wList.toArray(wrestlerArray);
        
        // Run it again
        RandomSeed.execute(group);
        
        // Check for random-ness
        int sameCount = 0;
        Collections.sort(wList, new WrestlerSeedSort());
        for (Wrestler w : wList) {
            if (w.equals(wrestlerArray[w.getSeed() - 1])) {
                sameCount++;
            }
        }
        
        Assert.assertTrue("More than half are the same : " + sameCount, sameCount < (numInGroup / 2));
    }
    
    private boolean isTeamInBucket(String teamName, List<Wrestler> list, int [] bucket) {
        for (int i : bucket) {
            if ((i < list.size()) && teamName.equalsIgnoreCase(list.get(i).getTeamName())) {
                return true;
            }
        }
        return false;
    }
    
    private int countTeamInBucket(String teamName, List<Wrestler> list, int [] bucket) {
        int count = 0;
        for (int i : bucket) {
            if ((i < list.size()) && teamName.equalsIgnoreCase(list.get(i).getTeamName())) {
                count++;
            }
        }
        return count;
    }
    
    private int countGeoInBucket(String geoName, List<Wrestler> list, int [] bucket) {
        int count = 0;
        for (int i : bucket) {
            if ((i < list.size()) && geoName.equalsIgnoreCase(list.get(i).getGeo())) {
                count++;
            }
        }
        return count;
    }
    
    private boolean isGeoInBucket(String geoName, List<Wrestler> list, int [] bucket) {
        for (int i : bucket) {
            if ((i < list.size()) && geoName.equalsIgnoreCase(list.get(i).getGeo())) {
                return true;
            }
        }
        return false;
    }
}
