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

package bouttime.dao.xml;

import bouttime.boutmaker.BoutMaker;
import bouttime.fileinput.ExcelFileInputConfig;
import bouttime.fileinput.TextFileInputConfig;
import bouttime.fileinput.XMLFileInputConfig;
import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for bouttime.dao.xml.XmlDao class.
 */
public class XmlDaoTest {

    private XmlDao openNew(String filename) {
        XmlDao d = new XmlDao();
        assertTrue(d.openNew(filename));
        return d;
    }

    private void closeAndDelete(XmlDao d, String filename) {
        d.close();
        assertFalse(d.isOpen());
        new File(filename).delete();
    }

    /**
     * Test of openNew method, of class XmlDao.
     */
    @Test
    public void testOpenNew() {
        String filename = "testOpenNew.xml";
        XmlDao d = openNew(filename);
        closeAndDelete(d, filename);
    }

    /**
     * Test of openExisting method, of class XmlDao.
     */
    //@Ignore  // This is thoroughly tested in bouttime.gui.*TabTest tests.
    @Test
    public void testOpenExisting() {
        XmlDao d = new XmlDao();
        assertTrue(d.openExisting("test/bouttime/resources/potus.xml"));
        assertTrue(d.isOpen());
        assertNotNull(d.getAllWrestlers());
        d.close();
        assertFalse(d.isOpen());
    }

    /**
     * Test of close method, of class XmlDao.
     */
    @Test
    public void testClose() {
        // Open a new dummy DAO first.
        String filename = "testClose.xml";
        XmlDao d = openNew(filename);

        d.close();
        assertFalse(d.isOpen());

        // clean-up
        new File(filename).delete();
    }

    /**
     * Test of flush method, of class XmlDao.
     */
    @Test
    public void testFlush() {
        String filename = "testFlush.xml";
        XmlDao d = openNew(filename);
        d.flush();
        assertTrue(d.isOpen());
        closeAndDelete(d, filename);
    }

    /**
     * Test of createWrestler method, of class XmlDao.
     */
    @Test
    public void testCreateWrestler() {
        String filename = "testCreateWrestler.xml";
        XmlDao d = openNew(filename);
        assertNotNull(d.createWrestler("Test", "Johnny", "Testers", "Rookie", "1", "100", "A"));
        closeAndDelete(d, filename);
    }

    /**
     * Test of add method, of class XmlDao.
     */
    @Test
    public void testAdd_Wrestler() {
        String filename = "testAdd_Wrestler.xml";
        XmlDao d = openNew(filename);
        assertTrue(d.add(new Wrestler("Test", "Susan", "Testers", "Rookie", "2", "88", "B")));
        // Should fail -- adding a duplicate
        assertFalse(d.add(new Wrestler("Test", "Susan", "Testers", "Rookie", "2", "88", "B")));
        closeAndDelete(d, filename);
    }

    /**
     * Test of addWrestler method, of class XmlDao.
     */
    @Test
    public void testAddWrestler() {
        String filename = "testAddWrestler.xml";
        XmlDao d = openNew(filename);
        assertTrue(d.addWrestler(new Wrestler("Test", "Mary", "Testers", "Rookie", "3", "89", "C")));
        // Should fail -- adding a duplicate
        assertFalse(d.addWrestler(new Wrestler("Test", "Mary", "Testers", "Rookie", "3", "89", "C")));
        closeAndDelete(d, filename);
    }

    /**
     * Test of addWrestlerToGroup method, of class XmlDao.
     */
    @Test
    public void testAddWrestlerToGroup() {
        String filename = "testAddWrestlerToGroup.xml";
        XmlDao d = openNew(filename);
        List<Wrestler> list = new ArrayList<Wrestler>();
        list.add(new Wrestler("Test", "Susan", "Testers", "Rookie", "2", "88", "B"));
        list.add(new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C"));
        Group g = new Group(list);
        Wrestler w = new Wrestler("Test", "Johnny", "Testers", "Rookie", "2", "88", "A");
        assertTrue(d.addWrestlerToGroup(w, g));
        closeAndDelete(d, filename);
    }

    /**
     * Test of removeWrestlerFromGroup method, of class XmlDao.
     */
    @Test
    public void testRemoveWrestlerFromGroup() {
        String filename = "testRemoveWrestlerFromGroup.xml";
        XmlDao d = openNew(filename);
        List<Wrestler> list = new ArrayList<Wrestler>();
        list.add(new Wrestler("Test", "Susan", "Testers", "Rookie", "2", "88", "B"));
        list.add(new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C"));
        Wrestler w = new Wrestler("Test", "Johnny", "Testers", "Rookie", "2", "88", "A");
        list.add(w);
        Group g = new Group(list);
        assertTrue(d.removeWrestlerFromGroup(w, g));
        closeAndDelete(d, filename);
    }

    /**
     * Test of updateWrestler method, of class XmlDao.
     */
    @Test
    public void testUpdateWrestler() {
        String filename = "testUpdateWrestler.xml";
        XmlDao d = openNew(filename);
        String team = "Testers";
        Wrestler w = new Wrestler("Test", "Johnny", team, "Rookie", "2", "88", "A");
        assertTrue(d.addWrestler(w));
        String newTeam = "New Team";
        w.setTeamName(newTeam);
        d.updateWrestler(w);
        assertEquals(newTeam, w.getTeamName());
        closeAndDelete(d, filename);
    }

    /**
     * Test of deleteWrestler method, of class XmlDao.
     */
    @Test
    public void testDeleteWrestler() {
        String filename = "testDeleteWrestler.xml";
        XmlDao d = openNew(filename);
        Wrestler w = new Wrestler("Test", "Susan", "Testers", "Rookie", "2", "88", "B");
        assertTrue(d.add(w));
        assertTrue(d.deleteWrestler(w));
        assertEquals(0, d.getAllWrestlers().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getDummyWrestler method, of class XmlDao.
     */
    @Test
    public void testGetDummyWrestler() {
        String filename = "testGetDummyWrestler.xml";
        XmlDao d = openNew(filename);
        assertNotNull(d.getDummyWrestler());
        closeAndDelete(d, filename);
    }

    /**
     * Test of deleteWrestlers method, of class XmlDao.
     */
    @Test
    public void testDeleteWrestlers() {
        String filename = "testDeleteWrestlers.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "2", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Testers", "Rookie", "2", "88", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getAllWrestlers().size());
        List<Wrestler> list = new ArrayList<Wrestler>();
        list.add(w1);
        list.add(w2);
        assertTrue(d.deleteWrestlers(list));
        assertEquals(1, d.getAllWrestlers().size());
        assertEquals(w3, d.getAllWrestlers().get(0));
        closeAndDelete(d, filename);
    }

    /**
     * Test of getAllWrestlers method, of class XmlDao.
     */
    @Test
    public void testGetAllWrestlers() {
        String filename = "testGetAllWrestlers.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "2", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Testers", "Rookie", "2", "88", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getAllWrestlers().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getWrestlersByGroup method, of class XmlDao.
     */
    @Test
    public void testGetWrestlersByGroup() {
        String filename = "testGetWrestlersByGroup.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "2", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Testers", "Rookie", "2", "88", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        List<Wrestler> list = new ArrayList<Wrestler>();
        list.add(w1);
        list.add(w2);
        Group group = new Group(list);
        assertTrue(d.addGroup(group));
        assertEquals(2, d.getWrestlersByGroup(group).size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getWrestlersByClass method, of class XmlDao.
     */
    @Test
    public void testGetWrestlersByClass() {
        String filename = "testGetWrestlersByClass.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "2", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Testers", "Open", "2", "88", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(2, d.getWrestlersByClass("Rookie").size());
        assertEquals(0, d.getWrestlersByClass("Testers").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getWrestlersByAgeDivision method, of class XmlDao.
     */
    @Test
    public void testGetWrestlersByAgeDivision() {
        String filename = "testGetWrestlersByAgeDivision.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Testers", "Open", "2", "88", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(1, d.getWrestlersByAgeDivision("1").size());
        assertEquals(0, d.getWrestlersByAgeDivision("Rookie").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getWrestlersByWeightClass method, of class XmlDao.
     */
    @Test
    public void testGetWrestlersByWeightClass() {
        String filename = "testGetWrestlersByWeightClass.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Testers", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(2, d.getWrestlersByWeightClass("88").size());
        assertEquals(0, d.getWrestlersByWeightClass("Rookie").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getWrestlersByClassDiv method, of class XmlDao.
     */
    @Test
    public void testGetWrestlersByClassDiv() {
        String filename = "testGetWrestlersByClassDiv.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Testers", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(1, d.getWrestlersByClassDiv("Rookie", "1").size());
        assertEquals(0, d.getWrestlersByClassDiv("Rookie", "5").size());
        assertEquals(0, d.getWrestlersByClassDiv("Tester", "2").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getWrestlersByClassDivWeight method, of class XmlDao.
     */
    @Test
    public void testGetWrestlersByClassDivWeight() {
        String filename = "testGetWrestlersByClassDivWeight.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Testers", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(1, d.getWrestlersByClassDivWeight("Rookie", "1", "88").size());
        assertEquals(0, d.getWrestlersByClassDivWeight("Rookie", "2", "55").size());
        assertEquals(0, d.getWrestlersByClassDivWeight("Tester", "2", "99").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getWrestlersByTeam method, of class XmlDao.
     */
    @Test
    public void testGetWrestlersByTeam() {
        String filename = "testGetWrestlersByTeam.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(2, d.getWrestlersByTeam("Testers").size());
        assertEquals(0, d.getWrestlersByTeam("Rookie").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getWrestlersByTeamClass method, of class XmlDao.
     */
    @Test
    public void testGetWrestlersByTeamClass() {
        String filename = "testGetWrestlersByTeamClass.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(2, d.getWrestlersByTeamClass("Testers", "Rookie").size());
        assertEquals(0, d.getWrestlersByTeamClass("Rookie", "Testers").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getUngroupedWrestlers method, of class XmlDao.
     */
    @Test
    public void testGetUngroupedWrestlers() {
        String filename = "testGetUngroupedWrestlers.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list = new ArrayList<Wrestler>();
        list.add(w1);
        list.add(w2);
        assertNotNull(d.createGroup(list));
        assertEquals(1, d.getUngroupedWrestlers().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getFlaggedWrestlers method, of class XmlDao.
     */
    @SuppressWarnings(value = "deprecation")
    @Test
    public void testGetFlaggedWrestlers() {
        String filename = "testGetFlaggedWrestlers.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(0, d.getFlaggedWrestlers().size());
        w1.setFlagged(true);
        assertEquals(1, d.getFlaggedWrestlers().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getScratchedWrestlers method, of class XmlDao.
     */
    @Test
    public void testGetScratchedWrestlers() {
        String filename = "testGetScratchedWrestlers.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(0, d.getScratchedWrestlers().size());
        w1.setScratched(true);
        assertEquals(1, d.getScratchedWrestlers().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of createGroup method, of class XmlDao.
     */
    @Test
    public void testCreateGroup() {
        String filename = "testCreateGroup.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list = new ArrayList<Wrestler>();
        list.add(w1);
        list.add(w2);
        assertNotNull(d.createGroup(list));
        assertEquals(1, d.getUngroupedWrestlers().size());
        assertEquals(1, d.getAllGroups().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of add method, of class XmlDao.
     */
    @Test
    public void testAdd_Group() {
        String filename = "testAdd_Group.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list = new ArrayList<Wrestler>();
        list.add(w1);
        list.add(w2);
        Group group = new Group(list);
        assertTrue(d.add(group));
        assertEquals(1, d.getUngroupedWrestlers().size());
        assertEquals(1, d.getAllGroups().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of addGroup method, of class XmlDao.
     */
    @Test
    public void testAddGroup() {
        String filename = "testAddGroup.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list = new ArrayList<Wrestler>();
        list.add(w1);
        list.add(w2);
        Group group = new Group(list);
        assertTrue(d.addGroup(group));
        assertEquals(1, d.getUngroupedWrestlers().size());
        assertEquals(1, d.getAllGroups().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of updateGroup method, of class XmlDao.
     */
    @Test
    public void testUpdateGroup() {
        String filename = "testUpdateGroup.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list = new ArrayList<Wrestler>();
        list.add(w1);
        list.add(w2);
        Group group = new Group(list);
        assertTrue(d.addGroup(group));
        assertEquals(1, d.getUngroupedWrestlers().size());
        assertEquals(1, d.getAllGroups().size());
        d.updateGroup(group);
        assertEquals(1, d.getUngroupedWrestlers().size());
        assertEquals(1, d.getAllGroups().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of deleteGroup method, of class XmlDao.
     */
    @Test
    public void testDeleteGroup() {
        String filename = "testDeleteGroup.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list = new ArrayList<Wrestler>();
        list.add(w1);
        list.add(w2);
        Group group = new Group(list);
        assertTrue(d.addGroup(group));
        assertEquals(1, d.getUngroupedWrestlers().size());
        assertEquals(1, d.getAllGroups().size());
        assertTrue(d.deleteGroup(group));
        assertEquals(3, d.getUngroupedWrestlers().size());
        assertEquals(0, d.getAllGroups().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of deleteGroups method, of class XmlDao.
     */
    @Test
    public void testDeleteGroups() {
        String filename = "testDeleteGroups.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list1 = new ArrayList<Wrestler>();
        list1.add(w1);
        list1.add(w2);
        Group group1 = new Group(list1);
        assertTrue(d.addGroup(group1));
        assertEquals(1, d.getUngroupedWrestlers().size());
        List<Wrestler> list2 = new ArrayList<Wrestler>();
        list2.add(w3);
        Group group2 = new Group(list2);
        assertTrue(d.addGroup(group2));
        assertEquals(0, d.getUngroupedWrestlers().size());
        assertEquals(2, d.getAllGroups().size());
        List<Group> glist = new ArrayList<Group>();
        glist.add(group1);
        glist.add(group2);
        assertTrue(d.deleteGroups(glist));
        assertEquals(3, d.getUngroupedWrestlers().size());
        assertEquals(0, d.getAllGroups().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getAllGroups method, of class XmlDao.
     */
    @Test
    public void testGetAllGroups() {
        String filename = "testGetAllGroups.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list1 = new ArrayList<Wrestler>();
        list1.add(w1);
        list1.add(w2);
        Group group1 = new Group(list1);
        assertTrue(d.addGroup(group1));
        assertEquals(1, d.getUngroupedWrestlers().size());
        assertEquals(1, d.getAllGroups().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getGroupsByDiv method, of class XmlDao.
     */
    @Test
    public void testGetGroupsByDiv() {
        String filename = "testGetGroupsByDiv.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "2", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list1 = new ArrayList<Wrestler>();
        list1.add(w1);
        list1.add(w2);
        Group group1 = new Group(list1);
        assertTrue(d.addGroup(group1));
        assertEquals(1, d.getUngroupedWrestlers().size());
        assertEquals(1, d.getGroupsByDiv("2").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getGroupsByMat method, of class XmlDao.
     */
    @Test
    public void testGetGroupsByMat() {
        String filename = "testGetGroupsByMat.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list1 = new ArrayList<Wrestler>();
        list1.add(w1);
        list1.add(w2);
        Group group1 = new Group(list1);
        assertTrue(d.addGroup(group1));
        assertEquals(1, d.getUngroupedWrestlers().size());
        group1.setMat("1");
        assertEquals(1, d.getGroupsByMat("1").size());
        assertEquals(0, d.getGroupsByMat("0").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getGroupsBySession method, of class XmlDao.
     */
    @Test
    public void testGetGroupsBySession() {
        String filename = "testGetGroupsBySession.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list1 = new ArrayList<Wrestler>();
        list1.add(w1);
        list1.add(w2);
        Group group1 = new Group(list1);
        assertTrue(d.addGroup(group1));
        assertEquals(1, d.getUngroupedWrestlers().size());
        group1.setSession("AM");
        assertEquals(1, d.getGroupsBySession("AM").size());
        assertEquals(0, d.getGroupsBySession("PM").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getGroupsBySessionMat method, of class XmlDao.
     */
    @Test
    public void testGetGroupsBySessionMat() {
        String filename = "testGetGroupsBySessionMat.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list1 = new ArrayList<Wrestler>();
        list1.add(w1);
        list1.add(w2);
        Group group1 = new Group(list1);
        assertTrue(d.addGroup(group1));
        assertEquals(1, d.getUngroupedWrestlers().size());
        group1.setSession("AM");
        group1.setMat("1");
        assertEquals(1, d.getGroupsBySessionMat("AM", "1").size());
        assertEquals(0, d.getGroupsBySessionMat("PM", "1").size());
        assertEquals(0, d.getGroupsBySessionMat("AM", "0").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of createBout method, of class XmlDao.
     */
    @Test
    public void testCreateBout_7args() {
        String filename = "testCreateBout_7args.xml";
        XmlDao d = openNew(filename);
        assertNotNull(d.createBout(null, null, null, "1", "A", null, null));
        assertEquals(0, d.getAllBouts().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of createBout method, of class XmlDao.
     */
    @Test
    public void testCreateBout_5args() {
        String filename = "testCreateBout_5args.xml";
        XmlDao d = openNew(filename);
        assertNotNull(d.createBout(null, null, null, "1", "A"));
        assertEquals(0, d.getAllBouts().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of add method, of class XmlDao.
     */
    @Test
    public void testAdd_Bout() {
        String filename = "testAdd_Bout.xml";
        XmlDao d = openNew(filename);
        Bout b = new Bout(null, null, null, "1", 1, "A");
        assertTrue(d.add(b));
        assertEquals(0, d.getAllBouts().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of addBout method, of class XmlDao.
     */
    @Test
    public void testAddBout() {
        String filename = "testAddBout.xml";
        XmlDao d = openNew(filename);
        Bout b = new Bout(null, null, null, "1", 1, "A");
        assertTrue(d.addBout(b));
        assertEquals(0, d.getAllBouts().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of updateBout method, of class XmlDao.
     */
    @Test
    public void testUpdateBout() {
        String filename = "testUpdateBout.xml";
        XmlDao d = openNew(filename);
        Bout b = new Bout(null, null, null, "1", 1, "A");
        assertTrue(d.addBout(b));
        assertEquals(0, d.getAllBouts().size());
        b.setBoutNum("21");
        assertTrue(d.updateBout(b));
        assertEquals(0, d.getAllBouts().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of deleteBout method, of class XmlDao.
     */
    @Test
    public void testDeleteBout() {
        String filename = "testDeleteBout.xml";
        XmlDao d = openNew(filename);
        Bout b = new Bout(null, null, null, "1", 1, "A");
        assertTrue(d.addBout(b));
        assertEquals(0, d.getAllBouts().size());
        assertTrue(d.deleteBout(b));
        assertEquals(0, d.getAllBouts().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getAllBouts method, of class XmlDao.
     */
    @Test
    public void testGetAllBouts() {
        String filename = "testGetAllBouts.xml";
        XmlDao d = openNew(filename);
        Bout b = new Bout(null, null, null, "1", 1, "A");
        assertTrue(d.addBout(b));
        assertEquals(0, d.getAllBouts().size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getBoutsByGroup method, of class XmlDao.
     */
    @Test
    public void testGetBoutsByGroup() {
        String filename = "testGetBoutsByGroup.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list1 = new ArrayList<Wrestler>();
        list1.add(w1);
        list1.add(w2);
        Group group1 = new Group(list1);
        assertTrue(d.addGroup(group1));
        BoutMaker.makeBouts(group1, false, false, false, 3, d.getDummyWrestler());
        assertEquals(1, d.getBoutsByGroup(group1).size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getBoutsByMat method, of class XmlDao.
     */
    @Test
    public void testGetBoutsByMat() {
        String filename = "testGetBoutsByMat.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list1 = new ArrayList<Wrestler>();
        list1.add(w1);
        list1.add(w2);
        Group group1 = new Group(list1);
        BoutMaker.makeBouts(group1, false, false, false, 3, d.getDummyWrestler());
        group1.setMat("1");
        assertTrue(d.addGroup(group1));
        assertEquals(1, d.getBoutsByMat("1").size());
        assertEquals(0, d.getBoutsByMat("5").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getBoutsBySession method, of class XmlDao.
     */
    @Test
    public void testGetBoutsBySession() {
        String filename = "testGetBoutsBySession.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list1 = new ArrayList<Wrestler>();
        list1.add(w1);
        list1.add(w2);
        Group group1 = new Group(list1);
        BoutMaker.makeBouts(group1, false, false, false, 3, d.getDummyWrestler());
        group1.setSession("AM");
        assertTrue(d.addGroup(group1));
        assertEquals(1, d.getBoutsBySession("AM").size());
        assertEquals(0, d.getBoutsBySession("PM").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getBoutsByMatSession method, of class XmlDao.
     */
    @Test
    public void testGetBoutsByMatSession() {
        String filename = "testGetBoutsByMatSession.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(3, d.getUngroupedWrestlers().size());
        List<Wrestler> list1 = new ArrayList<Wrestler>();
        list1.add(w1);
        list1.add(w2);
        Group group1 = new Group(list1);
        BoutMaker.makeBouts(group1, false, false, false, 3, d.getDummyWrestler());
        group1.setMat("1");
        group1.setSession("AM");
        assertTrue(d.addGroup(group1));
        assertEquals(1, d.getBoutsByMatSession("1", "AM").size());
        assertEquals(0, d.getBoutsByMatSession("5", "AM").size());
        assertEquals(0, d.getBoutsByMatSession("1", "PM").size());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getName method, of class XmlDao.
     */
    @Test
    public void testGetName() {
        String filename = "testGetName.xml";
        XmlDao d = openNew(filename);
        String expResult = "Test Tournament";
        d.setName(expResult);
        assertEquals(expResult, d.getName());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setName method, of class XmlDao.
     */
    @Test
    public void testSetName() {
        String filename = "testSetName.xml";
        XmlDao d = openNew(filename);
        String expResult = "Test Tournament";
        d.setName(expResult);
        assertEquals(expResult, d.getName());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getSite method, of class XmlDao.
     */
    @Test
    public void testGetSite() {
        String filename = "testGetSite.xml";
        XmlDao d = openNew(filename);
        String expResult = "Test Site";
        d.setSite(expResult);
        assertEquals(expResult, d.getSite());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setSite method, of class XmlDao.
     */
    @Test
    public void testSetSite() {
        String filename = "testSetSite.xml";
        XmlDao d = openNew(filename);
        String expResult = "Test Site";
        d.setSite(expResult);
        assertEquals(expResult, d.getSite());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getCity method, of class XmlDao.
     */
    @Test
    public void testGetCity() {
        String filename = "testGetCity.xml";
        XmlDao d = openNew(filename);
        String expResult = "Test City";
        d.setCity(expResult);
        assertEquals(expResult, d.getCity());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setCity method, of class XmlDao.
     */
    @Test
    public void testSetCity() {
        String filename = "testSetCity.xml";
        XmlDao d = openNew(filename);
        String expResult = "Test City";
        d.setCity(expResult);
        assertEquals(expResult, d.getCity());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getState method, of class XmlDao.
     */
    @Test
    public void testGetState() {
        String filename = "testGetState.xml";
        XmlDao d = openNew(filename);
        String expResult = "Test State";
        d.setState(expResult);
        assertEquals(expResult, d.getState());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setState method, of class XmlDao.
     */
    @Test
    public void testSetState() {
        String filename = "testSetState.xml";
        XmlDao d = openNew(filename);
        String expResult = "Test State";
        d.setState(expResult);
        assertEquals(expResult, d.getState());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getMonth method, of class XmlDao.
     */
    @Test
    public void testGetMonth() {
        String filename = "testGetMonth.xml";
        XmlDao d = openNew(filename);
        String expResult = "January";
        d.setMonth(expResult);
        assertEquals(expResult, d.getMonth());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setMonth method, of class XmlDao.
     */
    @Test
    public void testSetMonth() {
        String filename = "testSetMonth.xml";
        XmlDao d = openNew(filename);
        String expResult = "January";
        d.setMonth(expResult);
        assertEquals(expResult, d.getMonth());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getDay method, of class XmlDao.
     */
    @Test
    public void testGetDay() {
        String filename = "testGetDay.xml";
        XmlDao d = openNew(filename);
        Integer expResult = Integer.valueOf(1);
        d.setDay(expResult);
        assertEquals(expResult, d.getDay());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setDay method, of class XmlDao.
     */
    @Test
    public void testSetDay() {
        String filename = "testSetDay.xml";
        XmlDao d = openNew(filename);
        Integer expResult = Integer.valueOf(1);
        d.setDay(expResult);
        assertEquals(expResult, d.getDay());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getYear method, of class XmlDao.
     */
    @Test
    public void testGetYear() {
        String filename = "testGetYear.xml";
        XmlDao d = openNew(filename);
        Integer expResult = Integer.valueOf(2010);
        d.setYear(expResult);
        assertEquals(expResult, d.getYear());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setYear method, of class XmlDao.
     */
    @Test
    public void testSetYear() {
        String filename = "testSetYear.xml";
        XmlDao d = openNew(filename);
        Integer expResult = Integer.valueOf(2010);
        d.setYear(expResult);
        assertEquals(expResult, d.getYear());
        closeAndDelete(d, filename);
    }

    /**
     * Test of isFifthPlaceEnabled method, of class XmlDao.
     */
    @Test
    public void testIsFifthPlaceEnabled() {
        String filename = "testIsFifthPlaceEnabled.xml";
        XmlDao d = openNew(filename);
        assertFalse(d.isFifthPlaceEnabled());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setFifthPlaceEnabled method, of class XmlDao.
     */
    @Test
    public void testSetFifthPlaceEnabled() {
        String filename = "testSetFifthPlaceEnabled.xml";
        XmlDao d = openNew(filename);
        assertFalse(d.isFifthPlaceEnabled());
        d.setFifthPlaceEnabled(true);
        assertTrue(d.isFifthPlaceEnabled());
        closeAndDelete(d, filename);
    }

    /**
     * Test of isSecondPlaceChallengeEnabled method, of class XmlDao.
     */
    @Test
    public void testIsSecondPlaceChallengeEnabled() {
        String filename = "testIsSecondPlaceChallengeEnabled.xml";
        XmlDao d = openNew(filename);
        assertFalse(d.isSecondPlaceChallengeEnabled());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setSecondPlaceChallengeEnabled method, of class XmlDao.
     */
    @Test
    public void testSetSecondPlaceChallengeEnabled() {
        String filename = "testSetSecondPlaceChallengeEnabled.xml";
        XmlDao d = openNew(filename);
        assertFalse(d.isSecondPlaceChallengeEnabled());
        d.setSecondPlaceChallengeEnabled(true);
        assertTrue(d.isSecondPlaceChallengeEnabled());
        closeAndDelete(d, filename);
    }

    /**
     * Test of isRoundRobinEnabled method, of class XmlDao.
     */
    @Test
    public void testIsRoundRobinEnabled() {
        String filename = "testIsRoundRobinEnabled.xml";
        XmlDao d = openNew(filename);
        assertFalse(d.isRoundRobinEnabled());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setRoundRobinEnabled method, of class XmlDao.
     */
    @Test
    public void testSetRoundRobinEnabled() {
        String filename = "testSetRoundRobinEnabled.xml";
        XmlDao d = openNew(filename);
        assertFalse(d.isRoundRobinEnabled());
        d.setRoundRobinEnabled(true);
        assertTrue(d.isRoundRobinEnabled());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getRoundRobinMax method, of class XmlDao.
     */
    @Test
    public void testGetRoundRobinMax() {
        String filename = "testGetRoundRobinMax.xml";
        XmlDao d = openNew(filename);
        Integer expResult = Integer.valueOf(5);
        d.setRoundRobinMax(expResult);
        assertEquals(expResult, d.getRoundRobinMax());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setRoundRobinMax method, of class XmlDao.
     */
    @Test
    public void testSetRoundRobinMax() {
        String filename = "testSetRoundRobinMax.xml";
        XmlDao d = openNew(filename);
        Integer expResult = Integer.valueOf(5);
        d.setRoundRobinMax(expResult);
        assertEquals(expResult, d.getRoundRobinMax());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getMatValues method, of class XmlDao.
     */
    @Test
    public void testGetMatValues() {
        String filename = "testGetMatValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "1,2,3";
        d.setMatValues(expResult);
        assertEquals(expResult, d.getMatValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setMatValues method, of class XmlDao.
     */
    @Test
    public void testSetMatValues() {
        String filename = "testSetMatValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "1,2,3";
        d.setMatValues(expResult);
        assertEquals(expResult, d.getMatValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getBoutTimeValues method, of class XmlDao.
     */
    @Test
    public void testGetBoutTimeValues() {
        String filename = "testGetBoutTimeValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "1:00, 1:30";
        d.setBoutTimeValues(expResult);
        assertEquals(expResult, d.getBoutTimeValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setBoutTimeValues method, of class XmlDao.
     */
    @Test
    public void testSetBoutTimeValues() {
        String filename = "testSetBoutTimeValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "1:00, 1:30";
        d.setBoutTimeValues(expResult);
        assertEquals(expResult, d.getBoutTimeValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getClassificationValues method, of class XmlDao.
     */
    @Test
    public void testGetClassificationValues() {
        String filename = "testGetClassificationValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "Rookie, Open";
        d.setClassificationValues(expResult);
        assertEquals(expResult, d.getClassificationValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setClassificationValues method, of class XmlDao.
     */
    @Test
    public void testSetClassificationValues() {
        String filename = "testSetClassificationValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "Rookie, Open";
        d.setClassificationValues(expResult);
        assertEquals(expResult, d.getClassificationValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getAgeDivisionValues method, of class XmlDao.
     */
    @Test
    public void testGetAgeDivisionValues() {
        String filename = "testGetAgeDivisionValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "1,2,3,4";
        d.setAgeDivisionValues(expResult);
        assertEquals(expResult, d.getAgeDivisionValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setAgeDivisionValues method, of class XmlDao.
     */
    @Test
    public void testSetAgeDivisionValues() {
        String filename = "testSetAgeDivisionValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "1,2,3,4";
        d.setAgeDivisionValues(expResult);
        assertEquals(expResult, d.getAgeDivisionValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getSessionValues method, of class XmlDao.
     */
    @Test
    public void testGetSessionValues() {
        String filename = "testGetSessionValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "AM,PM";
        d.setSessionValues(expResult);
        assertEquals(expResult, d.getSessionValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setSessionValues method, of class XmlDao.
     */
    @Test
    public void testSetSessionValues() {
        String filename = "testSetSessionValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "AM,PM";
        d.setSessionValues(expResult);
        assertEquals(expResult, d.getSessionValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getWeightClassValues method, of class XmlDao.
     */
    @Test
    public void testGetWeightClassValues() {
        String filename = "testGetWeightClassValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "55,66,77,88,99,111";
        d.setWeightClassValues(expResult);
        assertEquals(expResult, d.getWeightClassValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setWeightClassValues method, of class XmlDao.
     */
    @Test
    public void testSetWeightClassValues() {
        String filename = "testSetWeightClassValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "55,66,77,88,99,111";
        d.setWeightClassValues(expResult);
        assertEquals(expResult, d.getWeightClassValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getExcelFileInputConfig method, of class XmlDao.
     */
    @Test
    public void testGetExcelFileInputConfig() {
        String filename = "testGetExcelFileInputConfig.xml";
        XmlDao d = openNew(filename);
        ExcelFileInputConfig expResult = new ExcelFileInputConfig();
        d.setExcelFileInputConfig(expResult);
        assertEquals(expResult, d.getExcelFileInputConfig());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setExcelFileInputConfig method, of class XmlDao.
     */
    @Test
    public void testSetExcelFileInputConfig() {
        String filename = "testSetExcelFileInputConfig.xml";
        XmlDao d = openNew(filename);
        ExcelFileInputConfig expResult = new ExcelFileInputConfig();
        d.setExcelFileInputConfig(expResult);
        assertEquals(expResult, d.getExcelFileInputConfig());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getTextFileInputConfig method, of class XmlDao.
     */
    @Test
    public void testGetTextFileInputConfig() {
        String filename = "testGetTextFileInputConfig.xml";
        XmlDao d = openNew(filename);
        TextFileInputConfig expResult = new TextFileInputConfig();
        d.setTextFileInputConfig(expResult);
        assertEquals(expResult, d.getTextFileInputConfig());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setTextFileInputConfig method, of class XmlDao.
     */
    @Test
    public void testSetTextFileInputConfig() {
        String filename = "testSetTextFileInputConfig.xml";
        XmlDao d = openNew(filename);
        TextFileInputConfig expResult = new TextFileInputConfig();
        d.setTextFileInputConfig(expResult);
        assertEquals(expResult, d.getTextFileInputConfig());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getXmlFileInputConfig method, of class XmlDao.
     */
    @Test
    public void testGetXmlFileInputConfig() {
        String filename = "testGetXmlFileInputConfig.xml";
        XmlDao d = openNew(filename);
        XMLFileInputConfig expResult = new XMLFileInputConfig();
        d.setXmlFileInputConfig(expResult);
        assertEquals(expResult, d.getXmlFileInputConfig());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setXmlFileInputConfig method, of class XmlDao.
     */
    @Test
    public void testSetXmlFileInputConfig() {
        String filename = "testSetXmlFileInputConfig.xml";
        XmlDao d = openNew(filename);
        XMLFileInputConfig expResult = new XMLFileInputConfig();
        d.setXmlFileInputConfig(expResult);
        assertEquals(expResult, d.getXmlFileInputConfig());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getMaxAward method, of class XmlDao.
     */
    @Test
    public void testGetMaxAward() {
        String filename = "testGetMaxAward.xml";
        XmlDao d = openNew(filename);
        Integer expResult = Integer.valueOf(3);
        d.setMaxAward(expResult);
        assertEquals(expResult, d.getMaxAward());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setMaxAward method, of class XmlDao.
     */
    @Test
    public void testSetMaxAward() {
        String filename = "testSetMaxAward.xml";
        XmlDao d = openNew(filename);
        Integer expResult = Integer.valueOf(3);
        d.setMaxAward(expResult);
        assertEquals(expResult, d.getMaxAward());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getBoutsheetWatermarkValues method, of class XmlDao.
     */
    @Test
    public void testGetBoutsheetWatermarkValues() {
        String filename = "testGetBoutsheetWatermarkValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "Rookie";
        d.setBoutsheetWatermarkValues(expResult);
        assertEquals(expResult, d.getBoutsheetWatermarkValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setBoutsheetWatermarkValues method, of class XmlDao.
     */
    @Test
    public void testSetBoutsheetWatermarkValues() {
        String filename = "testSetBoutsheetWatermarkValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "Rookie";
        d.setBoutsheetWatermarkValues(expResult);
        assertEquals(expResult, d.getBoutsheetWatermarkValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getBracketsheetWatermarkValues method, of class XmlDao.
     */
    @Test
    public void testGetBracketsheetWatermarkValues() {
        String filename = "testGetBracketsheetWatermarkValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "Rookie";
        d.setBracketsheetWatermarkValues(expResult);
        assertEquals(expResult, d.getBracketsheetWatermarkValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of setBracketsheetWatermarkValues method, of class XmlDao.
     */
    @Test
    public void testSetBracketsheetWatermarkValues() {
        String filename = "testGetBracketsheetWatermarkValues.xml";
        XmlDao d = openNew(filename);
        String expResult = "Rookie";
        d.setBracketsheetWatermarkValues(expResult);
        assertEquals(expResult, d.getBracketsheetWatermarkValues());
        closeAndDelete(d, filename);
    }

    /**
     * Test of getTeams method, of class XmlDao.
     */
    @Test
    public void testGetTeams() {
        String filename = "testGetTeams.xml";
        XmlDao d = openNew(filename);
        Wrestler w1 = new Wrestler("Test", "Susan", "Testers", "Rookie", "1", "88", "B");
        Wrestler w2 = new Wrestler("Test", "Mary", "Testers", "Rookie", "2", "88", "C");
        Wrestler w3 = new Wrestler("Test", "Johnny", "Trouble", "Open", "2", "99", "A");
        assertTrue(d.addWrestler(w1));
        assertTrue(d.addWrestler(w2));
        assertTrue(d.addWrestler(w3));
        assertEquals(2, d.getTeams().size());
        closeAndDelete(d, filename);
    }

}