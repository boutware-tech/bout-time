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

import bouttime.dao.Dao;
import bouttime.mainview.BoutTimeApp;
import bouttime.model.Bout;
import bouttime.model.Wrestler;
import bouttime.sort.WrestlerMatNameSort;
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
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;

/**
 * A class to generate a report of bout sequences.
 */
public class BoutSequenceReport {

    static Logger logger = Logger.getLogger(BoutSequenceReport.class);
    private static final String outputDirectory;
    private static String outputFilePath;

    // This stuff never changes, so only need to do it once.
    // Putting it in a static initialization block to ensure this.
    static {
        Application app = Application.getInstance(BoutTimeApp.class);
        ApplicationContext appCtx = app.getContext();
        ResourceMap map = appCtx.getResourceMap(BoutSequenceReport.class);
        outputDirectory = map.getString("report.dir");
        outputFilePath = String.format("%s/%s", outputDirectory,
                map.getString("report.filename"));
    }

    public static String getoutputFilePath() {
        return outputFilePath;
    }

    public static boolean generateReport(Dao dao, String team, String session) {
        if (!dao.isOpen()) {
            return false;
        }
        
        if (session != null) {
            session = session.trim();
        }

        // step 1: creation of a document-object
        Document document = new Document();
        boolean pageAdded = false;
        
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
            List<Wrestler> wrestlers = dao.getWrestlersByTeam(team);
            if ((wrestlers == null) || wrestlers.isEmpty()) {
                logger.warn("No wrestlers for team : " + team);
                document.close();
                return false;
            }

            PdfPTable datatable = addBoutSequences(wrestlers, session);
            if (datatable != null) {
                addHeader(dao, document, team, session);
                document.add(datatable);
                pageAdded = true;
            }

        } catch (DocumentException de) {
            logger.error("Document Exception", de);
            document.close();
            return false;
        }
        
        if (!pageAdded) {
            logger.warn("No pages in report.");
            // Closing the document with no pages will result in IOException
            return false;
        }

        // step 5: we close the document
        document.close();

        return true;
    }

    public static boolean generateReport(Dao dao, String session) {
        if (!dao.isOpen()) {
            return false;
        }
        
        if (session != null) {
            session = session.trim();
        }

        List<String> teams = dao.getTeams();
        if ((teams == null) || teams.isEmpty()) {
            logger.warn("Cannot generate report : no teams");
            return false;
        }

        // step 1: creation of a document-object
        Document document = new Document();

        int pages = 0;
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
            for (String team : teams) {
                List<Wrestler> wrestlers = dao.getWrestlersByTeam(team);
                if ((wrestlers == null) || wrestlers.isEmpty()) {
                    logger.warn("No wrestlers for team : " + team);
                    continue;
                }

                PdfPTable datatable = addBoutSequences(wrestlers, session);
                if (datatable != null) {
                    addHeader(dao, document, team, session);
                    document.add(datatable);
                    document.newPage();
                    pages++;
                }
            }

        } catch (DocumentException de) {
            logger.error("Document Exception", de);
            document.close();
            return false;
        }
        
        if (pages == 0) {
            logger.warn("No pages in report for.");
            // Closing the document with no pages will result in IOException
            return false;
        }

        // step 5: we close the document
        document.close();

        return true;
    }

    private static void addHeader(Dao dao, Document document, String team, String session) throws DocumentException {
        Paragraph p1 = new Paragraph(new Paragraph(String.format("%s    %s %s, %s",
                dao.getName(), dao.getMonth(), dao.getDay(), dao.getYear()),
                FontFactory.getFont(FontFactory.HELVETICA, 10)));
        document.add(p1);

        String instructions = "1) The first bout is the first number found in square brackets '[ ]'.\n" +
                "2) If this bout is won, the next bout is the next number found in square brackets.\n" +
                "3) If a bout in square brackets is lost, the next bout is to the LEFT (unless Round Robin).  From this point, go RIGHT-to-LEFT.\n";
        if (dao.isFifthPlaceEnabled()) {
            instructions += "4) Fifth place bouts are in angle brackets '< >'.\n";
        }
        Paragraph p2 = new Paragraph(new Paragraph(instructions,
                FontFactory.getFont(FontFactory.HELVETICA, 8)));
        document.add(p2);
        
        StringBuilder sb = new StringBuilder(team);
        if (session != null) {
            sb.append("  /  Session : ");
            sb.append(session);
        }
        Paragraph p3 = new Paragraph(new Paragraph(sb.toString(),
                FontFactory.getFont(FontFactory.HELVETICA, 12)));
        document.add(p3);
    }

    private static PdfPTable addBoutSequences(List<Wrestler> wList, String session) throws DocumentException {
        // create and add the table
        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
        Font detailFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
        PdfPCell headerCell = new PdfPCell();
        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerCell.setPadding(3);
        headerCell.setBorderWidth(2);
        PdfPTable datatable = new PdfPTable(3);
        int colWidths[] = {5, 25, 70};   // percentage
        datatable.setWidths(colWidths);
        datatable.setWidthPercentage(100);
        datatable.getDefaultCell().setPadding(3);
        datatable.getDefaultCell().setBorderWidth(2);
        datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        headerCell.setPhrase(new Phrase("Mat", headerFont));
        datatable.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Name", headerFont));
        datatable.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Bout Sequence",
                headerFont));
        datatable.addCell(headerCell);

        datatable.setHeaderRows(1); // this is the end of the table header

        datatable.getDefaultCell().setBorderWidth(1);
        datatable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        Collections.sort(wList, new WrestlerMatNameSort());

        int i = 0;
        for (Wrestler w : wList) {
            if (w.getGroup() == null) {
                logger.debug(String.format("Wrestler [%s] is not in a group, skipping.", w.getShortName()));
                continue;
            }
            
            if ((session != null) && !session.equalsIgnoreCase(
                w.getGroup().getSession())) {
                logger.debug(String.format("Wrestler [%s] is in a group but not in session %s, skipping.",
                        w.getShortName(), session));
                continue;
            }

            if ((i++ % 2) == 0) {
                datatable.getDefaultCell().setGrayFill(0.9f);
            } else {
                datatable.getDefaultCell().setGrayFill(1);
            }

            List<Bout> bList = BoutSequence.calculate(w);
            datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            datatable.addCell(new Phrase(w.getGroup().getMat(), detailFont));
            datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            datatable.addCell(new Phrase(String.format("%s %s", w.getFirstName(), w.getLastName()), detailFont));
            datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            String boutSequenceString = getBoutSequenceString(bList);
            datatable.addCell(new Phrase(boutSequenceString, detailFont));
        }

        datatable.setSpacingBefore(5f);
        datatable.setSpacingAfter(15f);
        return (i > 0 ? datatable : null);
    }

    private static String getBoutSequenceString(List<Bout> bList) {
        if ((bList == null) || bList.isEmpty()) {
            logger.debug("Bout list is null or empty");
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Bout b : bList) {
            if (b.isConsolation()) {
                if (Bout.FIFTH_PLACE.equalsIgnoreCase(b.getLabel())) {
                    sb.append(String.format("<%s> ", b.getBoutNum()));
                } else {
                    sb.append(String.format("%s ", b.getBoutNum()));
                }
            } else {
                sb.append(String.format("[%s] ", b.getBoutNum()));
            }
        }

        return sb.toString();
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
