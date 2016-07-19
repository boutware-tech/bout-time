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

package bouttime.report.boutsheet;

import bouttime.dao.Dao;
import bouttime.mainview.BoutTimeApp;
import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import bouttime.report.bracketsheet.BracketSheetUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.apache.log4j.Logger;

/**
 * A class to generate a PDF file of Bout Sheets.
 */
public class BoutSheetReport extends PdfPageEventHelper {
    static Logger logger = Logger.getLogger(BoutSheetReport.class);

    private static final String outputDirectory;
    private static final String outputFilePath;
    private BaseFont baseFont;
    private String headerString;

    // This stuff never changes, so only need to do it once.
    // Putting it in a static initialization block to ensure this.
    static {
        Application app = Application.getInstance(BoutTimeApp.class);
        ApplicationContext appCtx = app.getContext();
        ResourceMap map = appCtx.getResourceMap(BoutSheetReport.class);
        outputDirectory = map.getString("report.dir");
        outputFilePath = String.format("%s/%s", outputDirectory,
                map.getString("report.filename"));
    }

    public String getoutputFilePath() {return outputFilePath;}

    /**
     * Generate a bout sheet report that has no data in it (only the image).
     * The length is the given number of pages.
     * 
     * @param numPages
     * @return True if the report was generated.
     */
    public boolean generateBlank(Dao dao, Integer numPages) {
        // step 1: creation of a document-object
        // rotate to make page landscape
        Document document = new Document(PageSize.A4.rotate());

        try {

            // step 2: creation of the writer
            FileOutputStream fos = createOutputFile();
            if (fos == null) {
                return false;
            }
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.setPageEvent(this);

            // step 3: we open the document
            document.open();

            // step 4: we grab the ContentByte and do some stuff with it
            PdfContentByte cb = writer.getDirectContent();
            
            BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
            float pageWidth = cb.getPdfDocument().getPageSize().getWidth();
            float midPage = pageWidth / 2;

            setHeaderString(dao);

            int count = 1;
            while (true) {
                drawBout(cb, bf, 35, midPage - 35, null);
                drawBout(cb, bf, midPage + 35, pageWidth - 35, null);

                if (++count > numPages) {
                    break;
                }

                document.newPage();
            }

        } catch(DocumentException de) {
            logger.error("Document Exception", de);
            return false;
        } catch (IOException ioe) {
            logger.error("IO Exception", ioe);
            return false;
        }

        // step 5: we close the document
        document.close();

        return true;
    }

    /**
     * Generate a bout sheet report for the given list of bouts.
     * It is assumed that the list is in the desired order (no sorting is done here).
     *
     * @param list
     * @return True if the report was generated.
     */
    public boolean generateReport(Dao dao, List<Bout> list) {

        // step 1: creation of a document-object
        // rotate to make page landscape
        Document document = new Document(PageSize.A4.rotate());

        try {

            // step 2: creation of the writer
            FileOutputStream fos = createOutputFile();
            if (fos == null) {
                return false;
            }
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.setPageEvent(this);

            // step 3: we open the document
            document.open();

            // step 4: we grab the ContentByte and do some stuff with it
            PdfContentByte cb = writer.getDirectContent();

            BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
            float pageWidth = cb.getPdfDocument().getPageSize().getWidth();
            float midPage = pageWidth / 2;

            setHeaderString(dao);

            int count = 0;
            for (Bout b : list) {
                boolean rightSide = false;
                if ((count++ % 2) == 0) {
                    if (count > 2) {
                        // We could put this after Bout 2 is added, but
                        // that could leave a blank last page.
                        document.newPage();
                    }

                    // Bout 1 (Left side)
                    drawBout(cb, bf, 35, midPage - 35, b);
                } else {
                    // Bout 2 (Right side)
                    drawBout(cb, bf, midPage + 35, pageWidth - 35, b);
                    rightSide = true;
                }
                
                // Print the watermark, if necessary
                boolean doWatermark = false;
                String gClass = b.getGroup().getClassification();
                String wmValues = dao.getBoutsheetWatermarkValues();
                if ((wmValues != null) && !wmValues.isEmpty()) {
                    String [] tokens = wmValues.split(",");
                    for (String s : tokens) {
                        if (s.trim().equalsIgnoreCase(gClass)) {
                            doWatermark = true;
                            break;
                        }
                    }
                }
                if (doWatermark) {
                    int rotation = 45;
                    PdfContentByte ucb = writer.getDirectContentUnder();
                    BaseFont helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
                    ucb.saveState();
                    ucb.setColorFill(BaseColor.LIGHT_GRAY);
                    ucb.beginText();
                    ucb.setFontAndSize(helv, 86);
                    float centerWidth = document.getPageSize().getWidth() / 4;
                    if (rightSide) {
                        centerWidth = centerWidth * 3;
                    }
                    ucb.showTextAligned(Element.ALIGN_CENTER, gClass, centerWidth,
                            document.getPageSize().getHeight() / 2, rotation);
                    ucb.endText();
                    ucb.restoreState();
                }
            }

        } catch(DocumentException de) {
            logger.error("Document Exception", de);
            return false;
        } catch (IOException ioe) {
            logger.error("IO Exception", ioe);
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
    private FileOutputStream createOutputFile() {
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

    private void drawBout(PdfContentByte cb, BaseFont bf, float xStart,
            float xEnd, Bout b) throws DocumentException, IOException {

        Group g = (b != null) ? b.getGroup() : null;
        float midRange = xStart + ((xEnd - xStart) / 2);
        float x = xStart;
        float y = 560;
        float widthUnit = (xEnd - xStart) / 4;

        float fontSize = 10;

        // draw mat and bout boxes
        float width = widthUnit;
        float height = 20;
        y -= 30;
        float matX = x + (width / 2);
        fontSize = 12;
        BracketSheetUtil.drawStringCentered(cb, bf, matX, y + 5, fontSize, "Mat", 0);
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + 5,
                fontSize, (g != null) ? g.getMat() : "", 0);
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        float boutX = x + (width / 2);
        BracketSheetUtil.drawStringCentered(cb, bf, boutX, y + 5, fontSize, "Bout", 0);
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + 5,
                fontSize, (b != null) ? b.getBoutNum() : "", 0);
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);

        // draw group name
        // String groupName = "Open    Div 5    135-140";
        String groupName = "";
        if (g != null)  {
            String classification = g.getClassification();
            if (classification == null) {
                classification = "";
            }
            
            String div = g.getAgeDivision();
            if (div == null) {
                div = "";
            }
            
            String weightClass = g.getWeightClass();
            if (weightClass == null) {
                weightClass = "";
            }
            groupName = String.format("%s  %s  %s", classification, div, weightClass);
            groupName.trim();
        
            // append the bout time to the group name, if set
            String boutTime = b.getBoutTime();
            if ((boutTime != null) && !boutTime.isEmpty()) {
                groupName = groupName + "  [ " + boutTime + " ]";
            }
        }
        fontSize = 16;
        y -= 25;
        BracketSheetUtil.drawStringCentered(cb, bf, midRange, y, fontSize, groupName, 0);

        // draw judge's Score Sheet
        y -= 20;
        fontSize = 8;
        BracketSheetUtil.drawStringCentered(cb, bf, midRange, y, fontSize,
                "Judge's Score Sheet", 0);
        width = width * 2;
        height = 20;
        y -= 23;
        x = xStart;
        float redX = x + (width / 2);
        fontSize = 12;
        BracketSheetUtil.drawStringCentered(cb, bf, redX, y + 5, fontSize, "RED", 0);
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        float greenX = x + (width / 2);
        BracketSheetUtil.drawStringCentered(cb, bf, greenX, y + 5, fontSize, "GREEN", 0);
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);

        // Print wrestler names
        y -= height;
        x = xStart;
        fontSize = 10;
        Wrestler red = (b != null) ? b.getRed() : null;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + 4, fontSize,
                (red != null) ? red.getFirstName() + " " + red.getLastName() : "");
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        Wrestler green = (b != null) ? b.getGreen() : null;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + 4, fontSize,
                (green != null) ? green.getFirstName() + " " + green.getLastName() : "");
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);

        // Print wrestler team names
        y -= height;
        x = xStart;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + 4, fontSize,
                (red != null) ? red.getTeamName() : "");
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + 4, fontSize,
                (green != null) ? green.getTeamName() : "");
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);

        y -= height;
        x = xStart;
        fontSize = 8;
        BracketSheetUtil.drawStringCentered(cb, bf, midRange, y + 6, fontSize,
                "Points and Cautions", 0);
        BracketSheetUtil.drawRectangle(cb, x, y, width * 2, height, 1, 0);

        height = height * 3;
        y -= height;
        x = xStart;
        fontSize = 8;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + height - 10, fontSize, "Period 1");
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + height - 10, fontSize, "Period 1");
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);

        y -= height;
        x = xStart;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + height - 10, fontSize, "Period 2");
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + height - 10, fontSize, "Period 2");
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);

        y -= height;
        x = xStart;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + height - 10, fontSize, "Period 3/OT");
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + height - 10, fontSize, "Period 3/OT");
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);

        height = height / 3;
        y -= height;
        x = xStart;
        fontSize = 12;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + 5, fontSize, "Total");
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + 5, fontSize, "Total");
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);

        // winner's signature
        height = height * 2;
        y = y - height - 10;
        x = xStart;
        fontSize = 10;
        BracketSheetUtil.drawString(cb, bf, x + 2, y + 15, fontSize, "Winner's");
        BracketSheetUtil.drawString(cb, bf, x + 2, y + 2, fontSize, "Signature:");
        BracketSheetUtil.drawRectangle(cb, x, y, width * 2, height, 1, 0);

        height = height / 2;
        y -= height;
        x = xStart;
        width = width / 2;
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);

        y -= height;
        x = xStart;
        fontSize = 8;
        BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + 8, fontSize, "Fall Time", 0);
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + 8, fontSize, "Tech Fall Time", 0);
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + 8, fontSize, "Decision Score", 0);
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        x += width;
        BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + 8, fontSize, "Injury Default Time", 0);
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);

        x = xStart;
        y -= 60;
        BracketSheetUtil.drawHorizontalLine(cb, x, y, width * 4, 1, 0);
        y -= 10;
        fontSize = 10;
        BracketSheetUtil.drawString(cb, bf, x, y, fontSize, "Judge's Signature");
    }

    /**
     * Set the string for the page header.
     * @param dao Dao object to use to retrieve data.
     */
    private void setHeaderString(Dao dao) {
        try {
            this.baseFont = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
            this.headerString = String.format("%s    %s %s, %s", dao.getName(),
                dao.getMonth(), dao.getDay(), dao.getYear());
        } catch(DocumentException de) {
            logger.error("Document Exception", de);
            this.headerString = null;
        } catch (IOException ioe) {
            logger.error("IO Exception", ioe);
            this.headerString = null;
        }
    }

    /**
     * Add the header string to the document.
     * @param writer PdfWriter object to use.
     * @param document Document object to add the header to.
     */
    private void addHeader(PdfWriter writer, Document document) {
        if (this.headerString == null) {
            return;
        }

        PdfContentByte cb = writer.getDirectContent();

        float textBase = document.top() + 5;
        cb.beginText();
        cb.setFontAndSize(this.baseFont, 12);
        cb.setTextMatrix(document.left(), textBase);
        cb.showText(this.headerString);
        cb.endText();
    }

    /**
     * Called when a page in the document is being closed.
     * Add the header at this time, otherwise the header will be overwritten
     * by the bout images being added.
     * @param writer
     * @param document
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer, document);
    }
}
