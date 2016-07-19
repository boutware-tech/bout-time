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

import bouttime.dao.Dao;
import bouttime.gui.panel.MasterListPanel;
import bouttime.mainview.BoutTimeView;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import java.awt.Frame;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.matcher.DialogMatcher;
import org.fest.swing.core.matcher.JButtonMatcher;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.fest.swing.fixture.JTableFixture;
import org.junit.Ignore;
import static org.junit.Assert.*;

/**
 * Base class for the application's tab unit tests.
 */
@Ignore  // This is a base class, so there are no tests to run
public class BaseTabTest extends FestSwingJUnitTestCase {

    protected FrameFixture frame;
    protected bouttime.mainview.BoutTimeView view;
    protected bouttime.dao.Dao dao;

    protected void onSetUp() {
        org.fest.swing.launcher.ApplicationLauncher.application(bouttime.mainview.BoutTimeApp.class).start();
        frame = WindowFinder.findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
            protected boolean isMatching(Frame frame) {
                return "Bout Time".equals(frame.getTitle());
            }
        }).using(robot());
    }

    /**
     * Copy a file.
     * If the dst file does not exist, it is created.
     * @param src Path to the source file
     * @param dst Path to the destination file
     */
    private void copy(String src, String dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) >= 0) {
            if (len > 0) {
                out.write(buf, 0, len);
            }
        }
        out.flush();
        in.close();
        out.close();
    }

    /**
     *
     * @param frame Frame to perform setup operations against
     * @param srcTestFile Existing tournament file to copy a test file from
     * @return False on failure, True on success
     */
    protected boolean setUpExisting(FrameFixture frame, String srcTestFile) {
        DialogFixture welcomeDialog = frame.dialog(DialogMatcher.withName("Form"));
        JButtonFixture welcomeOkButton = welcomeDialog.button(JButtonMatcher.withName("okButton").andText("OK"));
        welcomeOkButton.click();

        DialogFixture fileChooserDialog = frame.dialog(DialogMatcher.withTitle("Open"));
        String userDir = System.getProperty("user.home");
        String testFile = userDir + "/bouttime-test.xml";
        try {
            copy(srcTestFile, testFile);
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        fileChooserDialog.textBox("GTKFileChooser.fileNameTextField").enterText(testFile);
        JButtonFixture approveButton = fileChooserDialog.button(JButtonMatcher.withName("SynthFileChooser.approveButton").andText("OK"));
        approveButton.click();

        Dao d = getDao(frame);
        assertNotNull(d);
        assertTrue(d.isOpen());

        return true;
    }

    protected BoutTimeView getView(FrameFixture frame) {
        frame.tabbedPane("mainTabPane").selectTab("Master List");
        MasterListPanel panel = (MasterListPanel)frame.panel("masterListPanel").target;
        return panel.getView();
    }

    protected Dao getDao(FrameFixture frame) {
        return getView(frame).getDao();
    }

    protected Wrestler getWrestlerFromDao(JTableFixture table, int row, Dao dao) {
        return getWrestlerFromDao(getWrestlerFromTable(table, row), dao);
    }

    protected Wrestler getWrestlerFromTable(JTableFixture table, int row) {
        String[][] tableContents = table.contents();
        Wrestler w = new Wrestler();
        w.setFirstName(tableContents[row][0]);
        w.setLastName(tableContents[row][1]);
        w.setTeamName(tableContents[row][2]);
        w.setClassification(tableContents[row][3]);
        w.setAgeDivision(tableContents[row][4]);
        w.setWeightClass(tableContents[row][5]);
        return w;
    }

    protected Wrestler getWrestlerFromDao(Wrestler wrestler, Dao dao) {
        if (wrestler == null) { return null; }

        List<Wrestler> list = dao.getAllWrestlers();
        for (Wrestler w : list) {
            if (isEqualWrestlers(w, wrestler)) {
                return w;
            }
        }

        return null;
    }

    private boolean isEqualWrestlers(Wrestler w1, Wrestler w2) {
        if (w1 == w2) {
            return true;
        } else if (w1.getLastName().equalsIgnoreCase(w2.getLastName()) &&
                    w1.getFirstName().equalsIgnoreCase(w2.getFirstName()) &&
                    w1.getTeamName().equalsIgnoreCase(w2.getTeamName()) &&
                    w1.getClassification().equalsIgnoreCase(w2.getClassification()) &&
                    w1.getAgeDivision().equalsIgnoreCase(w2.getAgeDivision()) &&
                    w1.getWeightClass().equalsIgnoreCase(w2.getWeightClass())) {
            return true;
        } else {
            return false;
        }
    }

    protected Group getGroupFromDao(JTableFixture table, int row, Dao dao) {
        String[][] tableContents = table.contents();
        String classification = tableContents[row][1];
        String div = tableContents[row][2];
        String wtClass = tableContents[row][3];
        List<Group> list = dao.getAllGroups();
        for (Group g : list) {
            if (classification.equalsIgnoreCase(g.getClassification()) &&
                    div.equalsIgnoreCase(g.getAgeDivision()) &&
                    wtClass.equalsIgnoreCase(g.getWeightClass())) {
                return g;
            }
        }

        return null;
    }
}
