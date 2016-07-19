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

import bouttime.model.Wrestler;
import org.fest.swing.core.matcher.DialogMatcher;
import org.fest.swing.core.matcher.JButtonMatcher;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JTableCellFixture;
import org.fest.swing.fixture.JTableFixture;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the application's 'Master List' tab.
 */
public class ScratchListTabTest extends BaseTabTest {

    @Override
    protected void onSetUp() {
        super.onSetUp();

        if (!setUpExisting(frame, "./test/bouttime/resources/potus.xml")) {
            return;
        }

        view = getView(frame);
        dao = getDao(frame);
        assertEquals(42, dao.getAllWrestlers().size());
        assertEquals(0, dao.getScratchedWrestlers().size());

        frame.tabbedPane("mainTabPane").selectTab("Scratch List");
        frame.textBox("count").requireText("0");
    }

    private void scratchWrestlers(int numScratch) {
        frame.tabbedPane().selectTab("Master List");

        for (int i = 0; i < numScratch; i++) {
            frame.table("table").selectRows(i);
            Wrestler w = getWrestlerFromDao(frame.table("table"), i, dao);
            assertNotNull(w);
            assertFalse(w.isScratched());
            assertNull(w.getComment());
            frame.button("scratchButton").click();
            DialogFixture dialog = frame.dialog(DialogMatcher.withTitle("Enter a comment"));
            String scratchString = "Scratch " + i;
            dialog.textBox().enterText(scratchString);
            dialog.button(JButtonMatcher.withName("OptionPane.button").andText("OK")).click();
            assertTrue(w.isScratched());
            assertEquals(scratchString, w.getComment());
        }

        frame.tabbedPane().selectTab("Scratch List");
        frame.textBox("count").requireText(Integer.toString(numScratch));
    }

    @Test
    public void testRestore() {
        scratchWrestlers(2);
        frame.table("table").selectRows(0);
        Wrestler w = getWrestlerFromDao(frame.table("table"), 0, dao);
        assertNotNull(w);
        assertTrue(w.isScratched());
        assertNotNull(w.getComment());
        frame.button("restoreButton").click();
        frame.textBox("count").requireText("1");
        assertFalse(w.isScratched());
        assertNull(w.getComment());

        // Test right-click too
        frame.table("table").selectRows(0).rightClick();
        w = getWrestlerFromDao(frame.table("table"), 0, dao);
        assertNotNull(w);
        assertTrue(w.isScratched());
        assertNotNull(w.getComment());
        frame.menuItem("restoreMenuItem").click();
        frame.textBox("count").requireText("0");
        assertFalse(w.isScratched());
        assertNull(w.getComment());
    }

    @Test
    public void testSave() {
        scratchWrestlers(1);
        frame.table("table").selectRows(0);
        Wrestler w = getWrestlerFromDao(frame.table("table"), 0, dao);
        assertNotNull(w);
        assertTrue(w.isScratched());
        assertNotNull(w.getComment());
        JTableFixture table = frame.table("table");
        JTableCellFixture cell = table.cell(table.cell("Scratch 0"));
        String scratchString = "New value";
        cell.enterValue(scratchString);
        frame.button("saveButton").click();
        assertTrue(w.isScratched());
        assertEquals(scratchString, w.getComment());

        // Test right-click too
        w = getWrestlerFromDao(frame.table("table"), 0, dao);
        assertNotNull(w);
        assertTrue(w.isScratched());
        assertNotNull(w.getComment());
        table = frame.table("table");
        cell = table.cell(table.cell(scratchString));
        scratchString = "Another value";
        cell.enterValue(scratchString);
        frame.table("table").selectRows(0).rightClick();
        frame.menuItem("saveMenuItem").click();
        assertTrue(w.isScratched());
        assertEquals(scratchString, w.getComment());

    }
}
