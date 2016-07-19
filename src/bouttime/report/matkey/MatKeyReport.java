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

package bouttime.report.matkey;

import bouttime.dao.Dao;
import bouttime.mainview.BoutTimeApp;
import bouttime.model.Group;
import bouttime.sort.GroupClassDivWtSort;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.apache.log4j.Logger;

/**
 * A class to generate a Mat Key report.
 */
public class MatKeyReport {
    private static String outputDirectory;
    private static String outputFilePath;
    private static Map<Integer, BaseColor> colorMap;
    static Logger logger = Logger.getLogger(MatKeyReport.class);

    // This stuff never changes, so only need to do it once.
    // Putting it in a static initialization block to ensure this.
    static {
        Application app = Application.getInstance(BoutTimeApp.class);
        ApplicationContext appCtx = app.getContext();
        ResourceMap map = appCtx.getResourceMap(MatKeyReport.class);
        outputDirectory = map.getString("report.dir");
        outputFilePath = String.format("%s/%s", outputDirectory,
                map.getString("report.filename"));
    }

    // Create the ColorMap hashmap.
    // This maps an index of the mat values list to a color.
    static {
        colorMap = new HashMap<Integer, BaseColor>();
        colorMap.put(0, BaseColor.WHITE);  // white
        colorMap.put(1, BaseColor.YELLOW);
        colorMap.put(2, BaseColor.LIGHT_GRAY);
        colorMap.put(3, BaseColor.ORANGE);
        colorMap.put(4, BaseColor.PINK);
        colorMap.put(5, BaseColor.GREEN);
        colorMap.put(6, BaseColor.CYAN);
        colorMap.put(7, BaseColor.MAGENTA);
        colorMap.put(8, BaseColor.BLUE);
        colorMap.put(9, BaseColor.GRAY);
        colorMap.put(-1, BaseColor.RED);    // MAT VALUE NOT FOUND IN LIST!!!!
    }

    public static String getoutputFilePath() {return outputFilePath;}

    /**
     * Generate the Mat Key report.
     * This is a color-coded report that maps the groups to a mat.
     * @param dao Dao object to use to retrieve data.
     * @return True if the report was generated.
     */
    public static boolean doReport(Dao dao) {
        if (!dao.isOpen()) {
            logger.warn("Cannot create report : DAO not open");
            return false;
        }

        String matValues = dao.getMatValues();
        if ((matValues == null) || matValues.isEmpty()) {
            JFrame mainFrame = BoutTimeApp.getApplication().getMainFrame();
                JOptionPane.showMessageDialog(mainFrame, "Mat values are not configured." +
                        "\nSet the mat values in 'Edit -> Configuration'",
                        "Mat Key Report error",
                JOptionPane.WARNING_MESSAGE);
            logger.warn("Cannot create report : mat values not configured");
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

            Paragraph p2 = new Paragraph(new Paragraph("Mat Key Report",
                    FontFactory.getFont(FontFactory.HELVETICA, 14)));
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p2);

            int cols = 4;   // Class, Div, Weight, Mat

            // create and add the table
            PdfPTable datatable = new PdfPTable(cols);
            //int colWidths[] = { 55, 15, 15, 15 };   // percentage
            //datatable.setWidths(colWidths);
            datatable.getDefaultCell().setPadding(3);
            datatable.getDefaultCell().setBorderWidth(2);
            datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            datatable.addCell("Class");
            datatable.addCell("Div");
            datatable.addCell("Weight");
            datatable.addCell("Mat");
            datatable.setHeaderRows(1); // this is the end of the table header

            datatable.getDefaultCell().setBorderWidth(1);

            // Prepare the list of groups
            List<Group> groups = dao.getAllGroups();
            Collections.sort(groups, new GroupClassDivWtSort());

            // Prepare the list of mat values
            String [] mats = matValues.split(",");
            List<String> matList = new ArrayList<String>();
            for (String m : mats) {
                matList.add(m.trim());
            }

            for (Group g : groups) {
                String mat = g.getMat();
                int idx = matList.indexOf(mat);
                datatable.getDefaultCell().setBackgroundColor(colorMap.get(idx));

                datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                datatable.addCell(g.getClassification());
                datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                datatable.addCell(g.getAgeDivision());
                datatable.addCell(g.getWeightClass());
                datatable.addCell(g.getMat());
            }

            datatable.setSpacingBefore(15f);    // space between title and table
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
