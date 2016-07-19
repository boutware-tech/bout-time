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

package bouttime.fileoutput;

import bouttime.dao.Dao;
import bouttime.mainview.BoutTimeApp;
import bouttime.model.Wrestler;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.apache.log4j.Logger;

/**
 * This class exports records to a text (i.e. CSV) file.
 */
public class TextFileOutput {
    static Logger logger = Logger.getLogger(TextFileOutput.class);

    public TextFileOutput() {}

    public void outputAllWrestlers(Dao dao) {
        JFrame mainFrame = BoutTimeApp.getApplication().getMainFrame();

        JFileChooser infile = new JFileChooser();
        if (infile.showSaveDialog(mainFrame) != JFileChooser.APPROVE_OPTION) {
            logger.info("operation cancelled");
            return;
        }

        File file = infile.getSelectedFile();
        try {
            PrintWriter pw = new PrintWriter(file);
            List<Wrestler> list = dao.getAllWrestlers();

            if ((list == null) || (list.isEmpty())) {
                pw.print("NO WRESTLERS");
                pw.close();
                logger.info("No wrestlers to output");
                return;
            }

            for (Wrestler w : list) {
                pw.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s\n", w.getLastName(),
                        w.getFirstName(), w.getTeamName(), w.getClassification(),
                        w.getAgeDivision(), w.getWeightClass(), w.getActualWeight(),
                        w.getLevel(), w.getSerialNumber());
            }

            pw.close();
            logger.info("output " + list.size() + " records");
        } catch (java.io.FileNotFoundException fnfe) {
            logger.error("File not found", fnfe);
        }
    }
}
