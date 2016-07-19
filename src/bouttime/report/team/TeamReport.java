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

package bouttime.report.team;

import bouttime.dao.Dao;
import bouttime.mainview.BoutTimeApp;
import bouttime.model.Wrestler;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.apache.log4j.Logger;

/**
 * A class to generate a report of the teams.
 */
public class TeamReport {
    static Logger logger = Logger.getLogger(TeamReport.class);
    private static String outputDirectory;
    private static String outputFilePath;

    // This stuff never changes, so only need to do it once.
    // Putting it in a static initialization block to ensure this.
    static {
        Application app = Application.getInstance(BoutTimeApp.class);
        ApplicationContext appCtx = app.getContext();
        ResourceMap map = appCtx.getResourceMap(TeamReport.class);
        outputDirectory = map.getString("report.dir");
        outputFilePath = String.format("%s/%s", outputDirectory,
                map.getString("report.filename"));
    }

    public static String getoutputFilePath() {return outputFilePath;}

    /**
     * Generate a summary report of the teams in the tournament.
     * This report lists the teams and the number of wresters on the team.
     * @param dao Dao object to use to retrieve data.
     * @return True if the report was generated.
     */
    public static boolean doSummary(Dao dao) {
        if (!dao.isOpen()) {
            return false;
        }

        // step 1: creation of a document-object
        Document document = new Document();

        try {

            // step 2: creation of the writer
            FileOutputStream fos = createOutputFile();
            if (fos == null) {
                return false;
            }
            PdfWriter.getInstance(document, fos);

            // step 3: we open the document
            document.open();

            // step 4: create and add content

            // create and add the header
            Paragraph p1 = new Paragraph(new Paragraph(String.format("%s    %s %s, %s",
                    dao.getName(), dao.getMonth(), dao.getDay(), dao.getYear()),
                    FontFactory.getFont(FontFactory.HELVETICA, 10)));
            document.add(p1);

            Paragraph p2 = new Paragraph(new Paragraph("Team Summary Report",
                    FontFactory.getFont(FontFactory.HELVETICA, 14)));
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p2);

            int cols = 2;   // Team name and Total
            String classVals = dao.getClassificationValues();
            String [] classes = null;
            if (classVals != null) {
                if (classVals.length() > 0) {
                    classes = classVals.split(",");
                    cols += classes.length;
                }
            }

            // create and add the table
            PdfPTable datatable = new PdfPTable(cols);
            //int colWidths[] = { 55, 15, 15, 15 };   // percentage
            //datatable.setWidths(colWidths);
            datatable.getDefaultCell().setPadding(3);
            datatable.getDefaultCell().setBorderWidth(2);
            datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            datatable.addCell("Team Name");

            // Make a column for each classification value
            int [] classesTotals = null;
            if (classes != null) {
                for (String c : classes) {
                    datatable.addCell(c.trim());
                }
                classesTotals = new int [classes.length];
                for (int i = 0; i < classesTotals.length; i++) {
                    classesTotals[i] = 0;
                }
            }
            datatable.addCell("Total");
            datatable.setHeaderRows(1); // this is the end of the table header

            datatable.getDefaultCell().setBorderWidth(1);

            List<String> teams = dao.getTeams();

            int total = 0;  // total count of all wrestlers in this method
            int i = 0;
            for (String t : teams) {
                if ((i++ % 2) == 0) {
                    datatable.getDefaultCell().setGrayFill(0.9f);
                } else {
                    datatable.getDefaultCell().setGrayFill(1);
                }

                List<Wrestler> wrestlers = dao.getWrestlersByTeam(t);
                int count = wrestlers.size();
                int rowTotal = 0;
                total += count;

                datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                datatable.addCell(t);
                if (classes != null) {
                    //for (String c : classes) {
                    for (int idx = 0; idx < classes.length; idx++) {
                        String c = classes[idx].trim();
                        List<Wrestler> wrestlers2 = dao.getWrestlersByTeamClass(t, c);
                        datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        int count2 = wrestlers2.size();
                        datatable.addCell(Integer.toString(count2));
                        classesTotals[idx] += count2;
                        rowTotal += count2;
                    }
                }

                datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                datatable.addCell(Integer.toString(count));

                // Check if there is an error in the counts.
                if ((classes != null) && (rowTotal != count)) {
                    JFrame mainFrame = BoutTimeApp.getApplication().getMainFrame();
                        JOptionPane.showMessageDialog(mainFrame, "There is an error" +
                        " with the total count for team '" + t + "'.\n" + "This is " +
                        "most likely due to an incorrect classification value\nfor " +
                        "one or more wrestlers.", "Team count error",
                        JOptionPane.WARNING_MESSAGE);
                }
            }

            // Add totals row
            datatable.getDefaultCell().setGrayFill(0.7f);
            datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            datatable.addCell("Total");
            datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            if (classes != null) {
                for (int idx = 0; idx < classesTotals.length; idx++) {
                    datatable.addCell(Integer.toString(classesTotals[idx]));
                }
            }

            datatable.addCell(Integer.toString(total));

            datatable.setSpacingBefore(15f);
            document.add(datatable);
            
        } catch(DocumentException de) {
            logger.error("Document Exception", de);
            return false;
        }

        // step 5: we close the document
        document.close();

        return true;
    }

    /**
     * Generate a detail report of the teams in the tournament.
     * This report includes the teams and all of the wrestlers on the team.
     * @param dao Dao object to use to retrieve data.
     * @return True if the report was generated.
     */
    public static boolean doDetail(Dao dao) {
        if (!dao.isOpen()) {
            return false;
        }

        // step 1: creation of a document-object
        Document document = new Document();

        try {

            // step 2: creation of the writer
            FileOutputStream fos = createOutputFile();
            if (fos == null) {
                return false;
            }
            PdfWriter.getInstance(document, fos);

            // step 3: we open the document
            document.open();

            // step 4: create and add content

            // create and add the header
            Paragraph p1 = new Paragraph(new Paragraph(String.format("%s    %s %s, %s",
                    dao.getName(), dao.getMonth(), dao.getDay(), dao.getYear()),
                    FontFactory.getFont(FontFactory.HELVETICA, 10)));
            document.add(p1);

            Paragraph p2 = new Paragraph(new Paragraph("Team Detail Report",
                    FontFactory.getFont(FontFactory.HELVETICA, 14)));
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p2);

            Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            Font detailFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            PdfPCell headerCell = new PdfPCell();
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell.setPadding(3);
            headerCell.setBorderWidth(2);

            List<String> teams = dao.getTeams();

            for (String t : teams) {

                List<Wrestler> wrestlers = dao.getWrestlersByTeam(t);
                int count = wrestlers.size();

                // create and add the table
                PdfPTable datatable = new PdfPTable(5);
                int colWidths[] = { 30, 30, 20, 10, 10 };   // percentage
                datatable.setWidths(colWidths);
                datatable.setWidthPercentage(100);
                datatable.getDefaultCell().setPadding(3);
                datatable.getDefaultCell().setBorderWidth(2);
                datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                // The header has the team name and the number of entries
                headerCell.setPhrase(new Phrase(t, headerFont));
                headerCell.setColspan(3);
                datatable.addCell(headerCell);
                
                headerCell.setPhrase(new Phrase(String.format("Entries : %d", count),
                        headerFont));
                headerCell.setColspan(2);
                datatable.addCell(headerCell);
                
                datatable.setHeaderRows(1); // this is the end of the table header

                datatable.getDefaultCell().setBorderWidth(1);
                datatable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

                int i = 0;
                for (Wrestler w : wrestlers) {
                    if ((i++ % 2) == 0) {
                        datatable.getDefaultCell().setGrayFill(0.9f);
                    } else {
                        datatable.getDefaultCell().setGrayFill(1);
                    }

                    datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                    datatable.addCell(new Phrase(w.getFirstName(), detailFont));
                    datatable.addCell(new Phrase(w.getLastName(), detailFont));
                    datatable.addCell(new Phrase(w.getClassification(), detailFont));
                    datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    datatable.addCell(new Phrase(w.getAgeDivision(), detailFont));
                    datatable.addCell(new Phrase(w.getWeightClass(), detailFont));
                }

                datatable.setSpacingBefore(5f);
                datatable.setSpacingAfter(15f);
                document.add(datatable);
            }

        } catch(DocumentException de) {
            logger.error("Document Exception", de);
            return false;
        }

        // step 5: we close the document
        document.close();

        return true;
    }

    /**
     * Create the file to write the report to.
     * @return FileOutputStream to write the report to.
     */
    private static FileOutputStream createOutputFile() {
        File tmpDir = new File(outputDirectory);
        if (!tmpDir.exists() || !tmpDir.isDirectory()) {
            if (!tmpDir.mkdir()) {
                logger.warn("Unable to create directory : " + tmpDir);
                return null;
            }
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outputFilePath);
        } catch (java.io.FileNotFoundException ex) {
            logger.error("File Not Found Exception", ex);
            fos = null;
        }

        return fos;
    }
}
