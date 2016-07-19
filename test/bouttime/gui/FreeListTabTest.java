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

package bouttime.gui;

import bouttime.model.Group;
import bouttime.model.Wrestler;
import org.fest.swing.core.matcher.DialogMatcher;
import org.fest.swing.core.matcher.JButtonMatcher;
import org.fest.swing.fixture.DialogFixture;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the application's 'Free List' tab.
 */
public class FreeListTabTest extends BaseTabTest {

    @Override
    protected void onSetUp() {
        super.onSetUp();

        if (!setUpExisting(frame, "./test/bouttime/resources/potus.xml")) {
            return;
        }

        view = getView(frame);
        dao = getDao(frame);
        assertEquals(42, dao.getUngroupedWrestlers().size());

        frame.tabbedPane("mainTabPane").selectTab("Free List");
        frame.textBox("count").requireText("42");
    }

    @Test
    public void testFilters() {
        // Show all entries with the class "Open"
        frame.comboBox("classComboBox").selectItem("Open");
        frame.textBox("count").requireText("21");

        // Show all entries with the class "Open" and division "1"
        frame.comboBox("divComboBox").selectItem("1");
        frame.textBox("count").requireText("11");

        // Show all entries with the division "1"
        frame.comboBox("classComboBox").selectItem("All");
        frame.textBox("count").requireText("21");

        // Show all entries with the division "3"
        frame.comboBox("divComboBox").selectItem("3");
        frame.textBox("count").requireText("0");
    }

    @Test
    public void testAddToGroup() {
        // Must create a group first
        frame.table("table").selectRows(0);
        Wrestler w = getWrestlerFromDao(frame.table("table"), 0, dao);
        assertNotNull(w);
        assertNull(w.getGroup());
        frame.button("createGroupButton").click();
        frame.textBox("count").requireText("41");
        assertEquals(41, dao.getUngroupedWrestlers().size());
        Group grp = w.getGroup();
        assertNotNull(grp);
        assertEquals(1, dao.getAllGroups().size());

        // Add a single wrestler to a group
        frame.table("table").selectRows(0);
        frame.label("selectedCounterLabel").requireText("1");
        w = getWrestlerFromDao(frame.table("table"), 0, dao);
        assertNotNull(w);
        assertNull(w.getGroup());
        frame.button("addToGroupButton").click();
        DialogFixture dialog = frame.dialog(DialogMatcher.withTitle("Add wrestler to group"));
        dialog.comboBox().selectItem(0);
        dialog.button(JButtonMatcher.withName("OptionPane.button").andText("OK")).click();
        frame.textBox("count").requireText("40");
        assertEquals(40, dao.getUngroupedWrestlers().size());
        assertEquals(grp, w.getGroup());
        assertEquals(1, dao.getAllGroups().size());

        // Add multiple wrestlers to a group
        frame.table("table").selectRows(0,1,2);
        frame.label("selectedCounterLabel").requireText("3");
        Wrestler w1 = getWrestlerFromDao(frame.table("table"), 0, dao);
        Wrestler w2 = getWrestlerFromDao(frame.table("table"), 1, dao);
        Wrestler w3 = getWrestlerFromDao(frame.table("table"), 2, dao);
        assertNotNull(w1); assertNotNull(w2); assertNotNull(w3);
        assertNull(w1.getGroup()); assertNull(w2.getGroup()); assertNull(w3.getGroup());
        frame.button("addToGroupButton").click();
        dialog = frame.dialog(DialogMatcher.withTitle("Add wrestler to group"));
        dialog.comboBox().selectItem(0);
        dialog.button(JButtonMatcher.withName("OptionPane.button").andText("OK")).click();
        frame.textBox("count").requireText("37");
        assertEquals(37, dao.getUngroupedWrestlers().size());
        assertEquals(grp, w1.getGroup()); assertEquals(grp, w2.getGroup()); assertEquals(grp, w3.getGroup());
        assertEquals(1, dao.getAllGroups().size());

        // Test right-click too
        w = getWrestlerFromDao(frame.table("table"), 0, dao);
        assertNotNull(w);
        assertNull(w.getGroup());
        frame.table("table").selectRows(0).rightClick();
        frame.label("selectedCounterLabel").requireText("1");
        frame.menuItem("addToGroupMenuItem").click();
        dialog = frame.dialog(DialogMatcher.withTitle("Add wrestler to group"));
        dialog.comboBox().selectItem(0);
        dialog.button(JButtonMatcher.withName("OptionPane.button").andText("OK")).click();
        frame.textBox("count").requireText("36");
        assertEquals(36, dao.getUngroupedWrestlers().size());
        assertEquals(grp, w.getGroup());
        assertEquals(1, dao.getAllGroups().size());
    }

    @Test
    public void testCreateGroup() {
        // Create a group with a single wrestler
        frame.table("table").selectRows(0);
        frame.label("selectedCounterLabel").requireText("1");
        Wrestler w = getWrestlerFromDao(frame.table("table"), 0, dao);
        assertNotNull(w);
        assertNull(w.getGroup());
        frame.button("createGroupButton").click();
        frame.textBox("count").requireText("41");
        assertEquals(41, dao.getUngroupedWrestlers().size());
        assertNotNull(w.getGroup());
        assertEquals(1, dao.getAllGroups().size());

        // Create a group with multiple wrestlers
        frame.table("table").selectRows(0,1,2);
        frame.label("selectedCounterLabel").requireText("3");
        Wrestler w1 = getWrestlerFromDao(frame.table("table"), 0, dao);
        Wrestler w2 = getWrestlerFromDao(frame.table("table"), 1, dao);
        Wrestler w3 = getWrestlerFromDao(frame.table("table"), 2, dao);
        assertNotNull(w1); assertNotNull(w2); assertNotNull(w3);
        assertNull(w1.getGroup()); assertNull(w2.getGroup()); assertNull(w3.getGroup());
        frame.button("createGroupButton").click();
        frame.textBox("count").requireText("38");
        assertEquals(38, dao.getUngroupedWrestlers().size());
        assertNotNull(w.getGroup());
        assertEquals(2, dao.getAllGroups().size());
        
        // Test right-click too
        w = getWrestlerFromDao(frame.table("table"), 0, dao);
        assertNotNull(w);
        assertNull(w.getGroup());
        frame.table("table").selectRows(0).rightClick();
        frame.label("selectedCounterLabel").requireText("1");
        frame.menuItem("createGroupMenuItem").click();
        frame.textBox("count").requireText("37");
        assertEquals(37, dao.getUngroupedWrestlers().size());
        assertNotNull(w.getGroup());
        assertEquals(3, dao.getAllGroups().size());
    }
}
