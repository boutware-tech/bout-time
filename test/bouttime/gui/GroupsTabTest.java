/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2013  Jeffrey K. Rutt
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
import org.fest.swing.core.matcher.DialogMatcher;
import org.fest.swing.core.matcher.JButtonMatcher;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JTableFixture;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the application's 'Groups' tab.
 */
public class GroupsTabTest extends BaseTabTest {

    @Override
    protected void onSetUp() {
        super.onSetUp();

        if (!setUpExisting(frame, "./test/bouttime/resources/potus-grps.xml")) {
            return;
        }

        view = getView(frame);
        dao = getDao(frame);
        assertEquals(42, dao.getAllWrestlers().size());
        assertEquals(47, dao.getAllBouts().size());

        frame.tabbedPane("mainTabPane").selectTab("Groups");
        frame.textBox("boutCount").requireText("47");
    }

    @Test
    public void testFilters() {
        frame.comboBox("classComboBox").selectItem("Rookie");
        frame.textBox("boutCount").requireText("28");

        frame.comboBox("divComboBox").selectItem("1");
        frame.textBox("boutCount").requireText("0");

        frame.comboBox("classComboBox").selectItem("Open");
        frame.textBox("boutCount").requireText("7");

        frame.comboBox("divComboBox").selectItem("All");
        frame.textBox("boutCount").requireText("19");

        frame.comboBox("classComboBox").selectItem("All");
        frame.comboBox("sessionComboBox").selectItem("AM");
        frame.textBox("boutCount").requireText("28");

        frame.comboBox("sessionComboBox").selectItem("All");
        frame.comboBox("matComboBox").selectItem("1");
        frame.textBox("boutCount").requireText("12");

        frame.comboBox("matComboBox").selectItem("All");
        frame.comboBox("boutTimeComboBox").selectItem("1:00");
        frame.textBox("boutCount").requireText("28");

        frame.comboBox("sessionComboBox").selectItem("PM");
        frame.textBox("boutCount").requireText("0");

        frame.panel("groupListFilterPanel").button("clearButton").click();
        frame.textBox("boutCount").requireText("47");

        frame.panel("groupListFilterPanel").table("table").cell(org.fest.swing.data.TableCell.row(0).column(1)).click();
        frame.panel("groupListFilterPanel").button("deleteButton").click();
        frame.textBox("boutCount").requireText("44");
    }

    @Test
    public void testSetGroupAttributes() {
        JTableFixture table = frame.panel("groupListFilterPanel").table("table");
        int r = 0;

        Group g = getGroupFromDao(table, r, dao);
        assertNotNull(g);
        String value = "";
        assertEquals(value, g.getSession());
        assertEquals(value, g.getMat());
        assertEquals(value, g.getBoutTime());

        int col;

        //
        // Test right-click
        //
        r = 1;
        g = getGroupFromDao(table, r, dao);
        assertNotNull(g);
        value = "";
        assertEquals(value, g.getSession());
        assertEquals(value, g.getMat());
        assertEquals(value, g.getBoutTime());

        table.cell(org.fest.swing.data.TableCell.row(r).column(1)).click();
        table.rightClick();
        frame.menuItem("setSessionMenuItem").click();
        DialogFixture dialog = frame.dialog(DialogMatcher.withTitle("Set session"));
        value = "PM";
        dialog.comboBox().selectItem(value);
        dialog.button(JButtonMatcher.withName("OptionPane.button").andText("OK")).click();
        assertEquals(value, g.getSession());
        col = table.columnIndexFor("Session");
        table.cell(org.fest.swing.data.TableCell.row(r).column(col)).requireValue(value);

        table.cell(org.fest.swing.data.TableCell.row(r).column(1)).click();
        table.rightClick();
        frame.menuItem("setMatMenuItem").click();
        dialog = frame.dialog(DialogMatcher.withTitle("Set mat"));
        value = "2";
        dialog.comboBox().selectItem(value);
        dialog.button(JButtonMatcher.withName("OptionPane.button").andText("OK")).click();
        assertEquals(value, g.getMat());
        col = table.columnIndexFor("Mat");
        table.cell(org.fest.swing.data.TableCell.row(r).column(col)).requireValue(value);

        table.cell(org.fest.swing.data.TableCell.row(r).column(1)).click();
        table.rightClick();
        frame.menuItem("setBoutTimeMenuItem").click();
        dialog = frame.dialog(DialogMatcher.withTitle("Set bout time"));
        value = "1:30";
        dialog.comboBox().selectItem(value);
        dialog.button(JButtonMatcher.withName("OptionPane.button").andText("OK")).click();
        assertEquals(value, g.getBoutTime());
        col = table.columnIndexFor("Time");
        table.cell(org.fest.swing.data.TableCell.row(r).column(col)).requireValue(value);
    }
}
