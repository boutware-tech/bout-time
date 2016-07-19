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

package bouttime.report.weighin;

import bouttime.mainview.BoutTimeApp;
import bouttime.model.Wrestler;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.apache.log4j.Logger;

/**
 * A class to generate a report to be used for weigh-in.
 */
public class WeighInReport {
    static Logger logger = Logger.getLogger(WeighInReport.class);
    private static String outputDirectory;
    private static String outputFilePath;

    // This stuff never changes, so only need to do it once.
    // Putting it in a static initialization block to ensure this.
    static {
        Application app = Application.getInstance(BoutTimeApp.class);
        ApplicationContext appCtx = app.getContext();
        ResourceMap map = appCtx.getResourceMap(WeighInReport.class);
        outputDirectory = map.getString("report.dir");
        outputFilePath = String.format("%s/%s", outputDirectory,
                map.getString("report.filename"));
    }

    public static String getoutputFilePath() {return outputFilePath;}
    
    /**
     * Generate a report of the entries in the tournament for weigh-in.
     * @param sections List of sections for the report.  Each section will start
     *     on a new page.
     * @param headerString String to be used for the header of each page of the report.
     * @return True if the report was generated.
     */
    public static boolean doReport(List<List<Wrestler>> sections, String headerString) {
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
            if (headerString != null) {
                Paragraph p1 = new Paragraph(new Paragraph(headerString,
                        FontFactory.getFont(FontFactory.HELVETICA, 10)));
                document.add(p1);
            }

            Font detailFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);

            for (List<Wrestler> wrestlers : sections) {
                // create and add the table
                PdfPTable datatable = new PdfPTable(7);
                int colWidths[] = { 20, 20, 20, 10, 10, 10, 10 };   // percentage
                datatable.setWidths(colWidths);
                datatable.setWidthPercentage(100);
                datatable.getDefaultCell().setPadding(3);
                datatable.getDefaultCell().setBorderWidth(2);
                datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                
                //datatable.setHeaderRows(1); // this is the end of the table header

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
                    datatable.addCell(new Phrase(w.getLastName(), detailFont));
                    datatable.addCell(new Phrase(w.getFirstName(), detailFont));
                    datatable.addCell(new Phrase(w.getTeamName(), detailFont));
                    datatable.addCell(new Phrase(w.getClassification(), detailFont));
                    datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    datatable.addCell(new Phrase(w.getAgeDivision(), detailFont));
                    datatable.addCell(new Phrase(w.getWeightClass(), detailFont));
                    datatable.addCell(new Phrase()); // actual weight
                }

                datatable.setSpacingBefore(5f);
                datatable.setSpacingAfter(15f);
                document.add(datatable);
                document.newPage();
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
