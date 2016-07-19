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

package bouttime.report.award;

import bouttime.dao.Dao;
import bouttime.mainview.BoutTimeApp;
import bouttime.model.Group;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;

/**
 * A class to generate a report for the awards.
 */
public class AwardInventoryReport {
    static final Logger logger = Logger.getLogger(AwardInventoryReport.class);
    private static final String outputDirectory;
    private static final String outputFilePath;

    // This stuff never changes, so only need to do it once.
    // Putting it in a static initialization block to ensure this.
    static {
        Application app = Application.getInstance(BoutTimeApp.class);
        ApplicationContext appCtx = app.getContext();
        ResourceMap map = appCtx.getResourceMap(AwardInventoryReport.class);
        outputDirectory = map.getString("report.dir");
        outputFilePath = String.format("%s/%s", outputDirectory,
                map.getString("report.filename"));
        logger.debug(String.format("outputDirectory=%s  outputFilePath=%s",
                outputDirectory, outputFilePath));
    }

    public static String getoutputFilePath() {return outputFilePath;}

    /**
     * Generate an award report.
     * @param dao Dao object to use to retrieve data.
     * @param session Session to generate the report for.
     * @param group Group to generate report for.  This takes precedence, so
     * if not null, then the report will be generated for this group.
     * @return True if the report was generated.
     */
    public static boolean doReport(Dao dao) {
        if (!dao.isOpen()) {
            logger.warn("DAO is not open");
            return false;
        }
        
        if (dao.getMaxAward() == null) {
            logger.warn("Max Award value is NULL");
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

            Paragraph p2 = new Paragraph(new Paragraph("Award Inventory Report",
                    FontFactory.getFont(FontFactory.HELVETICA, 14)));
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p2);
            
            PdfPTable datatable;
            int [] awardCounts = getAwardCountsForClassDivWeight(dao);
            if (awardCounts != null) {
                Paragraph p3 = new Paragraph(new Paragraph("\nFor class/age division/weight class",
                        FontFactory.getFont(FontFactory.HELVETICA, 11)));
                p3.setAlignment(Paragraph.ALIGN_LEFT);
                document.add(p3);
                datatable = getDataTable(awardCounts, dao.getMaxAward());
                document.add(datatable);
            }
            
            awardCounts = getAwardCountsForExistingGroups(dao);
            if (awardCounts != null) {
                Paragraph p3 = new Paragraph(new Paragraph("\nFor existing groups",
                        FontFactory.getFont(FontFactory.HELVETICA, 11)));
                p3.setAlignment(Paragraph.ALIGN_LEFT);
                document.add(p3);
                datatable = getDataTable(awardCounts, dao.getMaxAward());
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
    
    private static PdfPTable getDataTable(int [] awardCounts, int maxAward) throws DocumentException {
        Font detailFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);

        // create and add the table
        PdfPTable datatable = new PdfPTable(2);
        int colWidths[] = { 90, 10 };   // percentage
        datatable.setWidths(colWidths);
        datatable.setWidthPercentage(100);
        datatable.getDefaultCell().setBorderWidth(1);
        datatable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        for (int i = 0; i < maxAward; i++) {
            if ((i % 2) == 0) {
                datatable.getDefaultCell().setGrayFill(0.9f);
            } else {
                datatable.getDefaultCell().setGrayFill(1);
            }

            String place = null;
            switch (i) {
                case 0:
                    place = "1st Place";
                    break;
                case 1:
                    place = "2nd Place";
                    break;
                case 2:
                    place = "3rd Place";
                    break;
                case 3:
                    place = "4th Place";
                    break;
                case 4:
                    place = "5th Place";
                    break;
                case 5:
                    place = "6th Place";
                    break;
                case 6:
                    place = "7th Place";
                    break;
                default:
                    logger.warn("unexpected place value : " + i);
                    place = "???";
                    break;
            }

            datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            datatable.addCell(new Phrase(place, detailFont));

            datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            datatable.addCell(new Phrase(Integer.toString(awardCounts[i]), detailFont));
        }

        datatable.setSpacingBefore(5f);
        datatable.setSpacingAfter(15f);

        return datatable;
    }
    
    private static Set<String> getGroupStrings(Dao dao) {
        List<Wrestler> masterList = dao.getAllWrestlers();
        Set<String> sSet = new HashSet<String>();

        // Gather all unique "Classification:AgeDivision:WeightClass" combos.
        for (Wrestler w : masterList) {
            sSet.add(String.format("%s:%s:%s", w.getClassification(),
                    w.getAgeDivision(), w.getWeightClass()));
        }
        
        return sSet;
    }
    
    private static int [] getAwardCountsForClassDivWeight(Dao dao) {
        Integer maxAward = dao.getMaxAward();
        if (maxAward == null) {
            logger.warn("Cannot get award counts : max award value is NULL");
            return null;
        }
        
        Set<String> set = getGroupStrings(dao);
        int [] awardCounts = new int [7];

        for (String s : set) {
            // For each "Classification:AgeDivision:WeightClass" string,
            // find the wrestlers that match this.
            String[] tokens = s.split(":");
            List<Wrestler> wList = dao.getWrestlersByClassDivWeight(tokens[0],
                    tokens[1], tokens[2]);

            if (wList.isEmpty()) {
                logger.warn("empty list : class=" + tokens[0] + "  div=" + tokens[1] + "  weight=" + tokens[2]);
                continue;
            }

            setAwardCounts(awardCounts, maxAward, wList);
        }
        
        return awardCounts;
    }
    
    private static int [] getAwardCountsForExistingGroups(Dao dao) {
        Integer maxAward = dao.getMaxAward();
        if (maxAward == null) {
            logger.warn("Cannot get award counts : max award value is NULL");
            return null;
        }
        
        List<Group> gList = dao.getAllGroups();
        if ((gList == null) || gList.isEmpty()) {
            logger.warn("Cannot get award counts : no groups exist");
            return null;
        }
        int [] awardCounts = new int [7];

        for (Group g : gList) {
            List<Wrestler> wList = g.getWrestlers();

            if (wList.isEmpty()) {
                logger.warn("empty list : " + g);
                continue;
            }
            
            setAwardCounts(awardCounts, maxAward, wList);
        }
        
        return awardCounts;
    }
    
    private static void setAwardCounts(int [] awardCounts, int maxAward, List<Wrestler> list) {
        int size = list.size();
        switch (maxAward) {
            case 7:
                awardCounts[6] = (size > 6) ? awardCounts[6] + 1 : awardCounts[6];
            case 6:
                awardCounts[5] = (size > 5) ? awardCounts[5] + 1 : awardCounts[5];
            case 5:
                awardCounts[4] = (size > 4) ? awardCounts[4] + 1 : awardCounts[4];
            case 4:
                awardCounts[3] = (size > 3) ? awardCounts[3] + 1 : awardCounts[3];
            case 3:
                awardCounts[2] = (size > 2) ? awardCounts[2] + 1 : awardCounts[2];
            case 2:
                awardCounts[1] = (size > 1) ? awardCounts[1] + 1 : awardCounts[1];
            case 1:
                awardCounts[0] = (size > 0) ? awardCounts[0] + 1 : awardCounts[0];
                break;
            default:
                logger.warn("unexpected maxAward value : " + maxAward);
                break;
        }
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
