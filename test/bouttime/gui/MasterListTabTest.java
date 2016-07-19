/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2011  Jeffrey K. Rutt
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

package bouttime.gui;

import org.fest.swing.fixture.JOptionPaneFixture;
import bouttime.model.Wrestler;
import org.fest.swing.core.matcher.DialogMatcher;
import org.fest.swing.core.matcher.JButtonMatcher;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.fixture.DialogFixture;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Unit tests for the application's 'Master List' tab.
 */
public class MasterListTabTest extends BaseTabTest {

    @Override
    protected void onSetUp() {
        super.onSetUp();

        if (!setUpExisting(frame, "./test/bouttime/resources/potus-grps.xml")) {
            return;
        }

        view = getView(frame);
        dao = getDao(frame);
        assertEquals(42, dao.getAllWrestlers().size());
        
        frame.tabbedPane("mainTabPane").selectTab("Master List");
        frame.textBox("count").requireText("42");
    }

    @Test
    public void testFilters() {
        frame.textBox("textFilter").requireEmpty();

        // Show all entries with the first name "James"
        frame.textBox("textFilter").enterText("James\n");
        frame.textBox("count").requireText("5");

        // Show all entries with the last name "Roosevelt"
        frame.textBox("textFilter").deleteText();
        frame.textBox("textFilter").enterText("Roosevelt");
        frame.comboBox("textFilterComboBox").selectItem("Last");
        frame.textBox("count").requireText("2");

        // Show all entries with the team name "Rep"
        frame.textBox("textFilter").deleteText();
        frame.textBox("textFilter").enterText("Rep");
        frame.comboBox("textFilterComboBox").selectItem("Team");
        frame.textBox("count").requireText("18");

        // Show all entries with the team name "Rep" and class "Rookie"
        frame.comboBox("classComboBox").selectItem("Rookie");
        frame.textBox("count").requireText("6");

        // Show all entries with the class "Rookie"
        frame.textBox("textFilter").deleteText();
        frame.textBox("textFilter").enterText("\n");
        frame.textBox("count").requireText("21");

        // Show all entries with the class "Rookie" and division "1"
        frame.comboBox("divComboBox").selectItem("1");
        frame.textBox("count").requireText("10");

        // Show all entries with the class "Rookie" and division "3"
        frame.comboBox("divComboBox").selectItem("3");
        frame.textBox("count").requireText("0");

        // Show all entries with the class "Rookie" and division "2" and
        // first name ""
        frame.comboBox("divComboBox").selectItem("2");
        frame.textBox("textFilter").enterText("Zachary");
        frame.comboBox("textFilterComboBox").selectItem("First");
        frame.textBox("count").requireText("1");
    }

    @Test
    public void testScratchWrestler() {
        // Scratch a single wrestler
        frame.table("table").selectRows(2);
        Wrestler w = getWrestlerFromDao(frame.table("table"), 2, dao);
        assertNotNull(w);
        assertFalse(w.isScratched());
        assertNull(w.getComment());
        frame.button("scratchButton").click();
        DialogFixture dialog = frame.dialog(DialogMatcher.withTitle("Enter a comment"));
        dialog.textBox().enterText("Single scratch");
        dialog.button(JButtonMatcher.withName("OptionPane.button").andText("OK")).click();
        frame.tabbedPane().selectTab("Scratch List");
        frame.textBox("count").requireText("1");
        assertTrue(w.isScratched());
        assertEquals("Single scratch", w.getComment());
        assertEquals(42, dao.getAllWrestlers().size());

        // Scratch multiple wrestlers
        frame.tabbedPane().selectTab("Master List");
        frame.table("table").selectRows(3, 16, 18);
        Wrestler w1 = getWrestlerFromDao(frame.table("table"), 3, dao);
        Wrestler w2 = getWrestlerFromDao(frame.table("table"), 16, dao);
        Wrestler w3 = getWrestlerFromDao(frame.table("table"), 18, dao);
        assertNotNull(w1); assertNotNull(w2); assertNotNull(w3);
        assertFalse(w1.isScratched()); assertFalse(w2.isScratched()); assertFalse(w3.isScratched());
        assertNull(w1.getComment()); assertNull(w2.getComment()); assertNull(w3.getComment());
        frame.button("scratchButton").click();
        dialog = frame.dialog(DialogMatcher.withTitle("Enter a comment"));
        dialog.textBox().enterText("Multiple scratch");
        dialog.button(JButtonMatcher.withName("OptionPane.button").andText("OK")).click();
        frame.tabbedPane().selectTab("Scratch List");
        frame.textBox("count").requireText("4");
        assertTrue(w1.isScratched()); assertTrue(w2.isScratched()); assertTrue(w3.isScratched());
        assertEquals("Multiple scratch", w1.getComment()); assertEquals("Multiple scratch", w2.getComment()); assertEquals("Multiple scratch", w3.getComment());
        assertEquals(42, dao.getAllWrestlers().size());

        // Test right-click too
        frame.tabbedPane().selectTab("Master List");
        w = getWrestlerFromDao(frame.table("table"), 38, dao);
        assertNotNull(w);
        assertFalse(w.isScratched());
        assertNull(w.getComment());
        frame.table("table").selectRows(38).rightClick();
        frame.menuItem("scratchMenuItem").click();
        dialog = frame.dialog(DialogMatcher.withTitle("Enter a comment"));
        dialog.textBox().enterText("Right-clicked");
        dialog.button(JButtonMatcher.withName("OptionPane.button").andText("OK")).click();
        frame.tabbedPane().selectTab("Scratch List");
        frame.textBox("count").requireText("5");
        assertTrue(w.isScratched());
        assertEquals("Right-clicked", w.getComment());
        assertEquals(42, dao.getAllWrestlers().size());
        
        // Test scratching a wrestler that is in a group
        frame.tabbedPane().selectTab("Master List");
        w = getWrestlerFromDao(frame.table("table"), 0, dao);
        assertNotNull(w);
        assertFalse(w.isScratched());
        assertNull(w.getComment());
        frame.table("table").selectRows(0).rightClick();
        frame.menuItem("scratchMenuItem").click();
        dialog = frame.dialog(DialogMatcher.withTitle("Enter a comment"));
        dialog.textBox().enterText("In a group");
        dialog.button(JButtonMatcher.withName("OptionPane.button").andText("OK")).click();
        JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane().using(robot());
        optionPane.requireTitle("Scratch in group");
        optionPane.okButton().click();
        frame.tabbedPane().selectTab("Scratch List");
        frame.textBox("count").requireText("6");
        assertTrue(w.isScratched());
        assertEquals("In a group", w.getComment());
        assertEquals(42, dao.getAllWrestlers().size());
    }

    @Test
    public void testDeleteWrestler() {
        // Delete a single wrestler
        frame.table("table").selectRows(2);
        Wrestler w = getWrestlerFromDao(frame.table("table"), 2, dao);
        assertNotNull(w);
        frame.button("deleteButton").click();
        frame.textBox("count").requireText("41");
        assertEquals(41, dao.getAllWrestlers().size());
        assertNull(getWrestlerFromDao(w, dao));

        // Delete several wrestlers
        frame.table("table").selectRows(2,17,36);
        Wrestler w1 = getWrestlerFromDao(frame.table("table"), 2, dao);
        Wrestler w2 = getWrestlerFromDao(frame.table("table"), 17, dao);
        Wrestler w3 = getWrestlerFromDao(frame.table("table"), 36, dao);
        assertNotNull(w1); assertNotNull(w2); assertNotNull(w3);
        frame.button("deleteButton").click();
        frame.textBox("count").requireText("38");
        assertEquals(38, dao.getAllWrestlers().size());
        assertNull(getWrestlerFromDao(w1, dao));
        assertNull(getWrestlerFromDao(w2, dao));
        assertNull(getWrestlerFromDao(w3, dao));

        // Test right-click too
        w = getWrestlerFromDao(frame.table("table"), 35, dao);
        assertNotNull(w);
        frame.table("table").selectRows(35).rightClick();
        frame.menuItem("deleteMenuItem").click();
        frame.textBox("count").requireText("37");
        assertEquals(37, dao.getAllWrestlers().size());
        assertNull(getWrestlerFromDao(w, dao));
        
        // Test to make sure cannot delete wrestler in a group
        frame.table("table").selectRows(0);
        w = getWrestlerFromDao(frame.table("table"), 0, dao);
        assertNotNull(w);
        frame.button("deleteButton").click();
        JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane().using(robot());
        optionPane.requireTitle("Cannot Delete - In a group");
        optionPane.okButton().click();
        frame.textBox("count").requireText("37");
        assertEquals(37, dao.getAllWrestlers().size());
        assertNotNull(getWrestlerFromDao(w, dao));
    }

    private void clearMasterListFilter() {
        frame.tabbedPane().selectTab("Master List");
        frame.textBox("textFilter").deleteText();
        frame.textBox("textFilter").enterText("\n");
    }

    @Test
    public void testEditWrestler() {
        // Edit a wrestler
        frame.table("table").selectRows(5);
        frame.button("editButton").click();
        DialogFixture dialog = frame.dialog("Form");
        String editTeam = dialog.textBox("team").target.getText();
        String editClass = (String)dialog.comboBox("classification").target.getSelectedItem();
        String editDiv = (String)dialog.comboBox("div").target.getSelectedItem();
        String editWeight = (String)dialog.comboBox("weight").target.getSelectedItem();
        dialog.textBox("first").deleteText();
        dialog.textBox("first").enterText("Johnny");
        dialog.textBox("last").deleteText();
        dialog.textBox("last").enterText("Test");
        dialog.button("okButton").click();
        frame.textBox("textFilter").enterText("Johnny\n");
        frame.textBox("count").requireText("1");

        // Make sure cancel works too
        clearMasterListFilter();
        frame.table("table").selectRows(5);
        frame.button("editButton").click();
        dialog = frame.dialog("Form");
        dialog.textBox("first").deleteText();
        dialog.textBox("first").enterText("Cancelled");
        dialog.textBox("last").deleteText();
        dialog.textBox("last").enterText("Test");
        dialog.button("cancelButton").click();
        frame.textBox("textFilter").deleteText();
        frame.textBox("textFilter").enterText("Cancelled\n");
        frame.textBox("count").requireText("0");

        // Test right-click too
        clearMasterListFilter();
        frame.table("table").selectRows(12).rightClick();
        frame.menuItem("editMenuItem").click();
        dialog = frame.dialog("Form");
        dialog.textBox("first").deleteText();
        dialog.textBox("first").enterText("Right");
        dialog.textBox("last").deleteText();
        dialog.textBox("last").enterText("Click");
        dialog.button("okButton").click();
        frame.textBox("textFilter").enterText("Right\n");
        frame.textBox("count").requireText("1");

        assertEquals(42, dao.getAllWrestlers().size());
    }

    @Test
    public void testAddNewWrestler() {
        // Add a new wrestler
        frame.button("addNewButton").click();
        DialogFixture dialog = frame.dialog("Form");
        dialog.textBox("first").enterText("Johnny");
        dialog.textBox("last").enterText("Test");
        dialog.textBox("team").enterText("Testers");
        dialog.comboBox("classification").selectItem("Open");
        dialog.comboBox("div").selectItem("3");
        dialog.comboBox("weight").enterText("100");
        dialog.textBox("actWeight").enterText("100");
        dialog.textBox("level").enterText("F");
        dialog.textBox("id").enterText("2121");
        dialog.button("okButton").click();
        frame.textBox("textFilter").enterText("Johnny\n");
        frame.textBox("count").requireText("1");
        assertEquals(43, dao.getAllWrestlers().size());

        // Try to add a duplicate
        clearMasterListFilter();
        frame.button("addNewButton").click();
        dialog = frame.dialog("Form");
        dialog.textBox("first").enterText("Johnny");
        dialog.textBox("last").enterText("Test");
        dialog.textBox("team").enterText("Testers");
        dialog.comboBox("classification").selectItem("Open");
        dialog.comboBox("div").selectItem("3");
        dialog.comboBox("weight").enterText("100");
        dialog.textBox("actWeight").enterText("100");
        dialog.textBox("level").enterText("F");
        dialog.textBox("id").enterText("2121");
        dialog.button("okButton").click();
        frame.textBox("count").requireText("43");
        assertEquals(43, dao.getAllWrestlers().size());
        frame.textBox("textFilter").enterText("Johnny\n");
        frame.textBox("count").requireText("1");  // Should fail silently

        // Make sure cancel works too
        frame.button("addNewButton").click();
        dialog = frame.dialog("Form");
        dialog.textBox("first").enterText("Cancelled");
        dialog.textBox("last").enterText("Test");
        dialog.button("cancelButton").click();
        frame.textBox("textFilter").deleteText();
        frame.textBox("textFilter").enterText("Cancelled\n");
        frame.textBox("count").requireText("0");
        assertEquals(43, dao.getAllWrestlers().size());

        // Test right-click too
        clearMasterListFilter();
        frame.table("table").selectRows(2).rightClick();
        frame.menuItem("addNewMenuItem").click();
        dialog = frame.dialog("Form");
        dialog.textBox("first").enterText("Right");
        dialog.textBox("last").enterText("Click");
        dialog.button("okButton").click();
        frame.textBox("textFilter").deleteText();
        frame.textBox("textFilter").enterText("Right\n");
        frame.textBox("count").requireText("1");
        assertEquals(44, dao.getAllWrestlers().size());
    }
}
