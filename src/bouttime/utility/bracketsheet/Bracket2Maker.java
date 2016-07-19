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

package bouttime.utility.bracketsheet;

import bouttime.report.bracketsheet.BracketSheetUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A helper class/application to use when building and testing
 * 2-man bracket sheets
 */
public class Bracket2Maker {
    public static final float matBoxStartX = 470;           // column to start the mat box
    public static final float matBoxStartY = 745;           // line to start the mat box
    public static final float matBoxWidth = 60;             // width of mat box
    public static final float matBoxHeight = 50;            // height of mat box
    public static final float matBoxTopPad = 15;            // space between mat box top and text
    public static final float matBoxBottomPad = 10;         // space between mat box bottom and mat number

    /**
     * @param args the command line arguments
     * arg0 String filename for output file
     * arg1 boolean value "true" will render/show PDF file in viewer, default is false
     *
     * For example :
     *     Bracket2Maker /tmp/test.pdf true
     */
    public static void main(String[] args) {
        // step 1: creation of a document-object
        Document document = new Document();

        try {

            // step 2: creation of the writer
            PdfWriter writer;
            if (args.length >= 1) {
                writer = PdfWriter.getInstance(document,
                        new FileOutputStream(args[0]));
            } else {
                System.err.println("ERROR : Must specify output file.");
                return;
            }

            // step 3: we open the document
            document.open();

            // step 4: we grab the ContentByte and do some stuff with it
            PdfContentByte cb = writer.getDirectContent();

            BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);

            drawBracket(cb, bf);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
            return;
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
            return;
        }

        // step 5: we close the document
        document.close();

        if ((args.length == 2) && (Boolean.parseBoolean(args[1]))) {
            RoundRobinBracketMaker.showPDF(args[0]);
        }
    }

    private static void drawBracket(PdfContentByte cb, BaseFont bf)
            throws DocumentException, IOException {

        float rightMargin = 530;  // pageWidth - 65
        float leftMargin = 65;
        float testY = 350;
        float testLength = 420;
        BracketSheetUtil.drawVerticalLine(cb, rightMargin, testY, testLength, 1, 0);
        BracketSheetUtil.drawVerticalLine(cb, leftMargin, testY, testLength, 1, 0);

        BracketSheetUtil.drawMatBox(cb, bf, matBoxStartX, matBoxStartY,
                matBoxWidth, matBoxHeight, matBoxTopPad, matBoxBottomPad,
                1, 0, "1", 0);

        float x = leftMargin;
        float y = 620;
        float length = 310;
        float height = 500;
        BracketSheetUtil.drawFishTailRight(cb, x, y, length, height, 2, 0);

        x = leftMargin + length;
        y = y - (height / 2);
        length = length / 2;
        BracketSheetUtil.drawHorizontalLine(cb, x, y, length, 2, 0);

        float fontsize = 12;
        float mid = x + (length / 2);
        y -= 15; // padding below the line
        BracketSheetUtil.drawStringCentered(cb, bf, mid, y, fontsize, "Champion", 0);

        x -= 100;
        BracketSheetUtil.drawString(cb, bf, x, y, fontsize, "23");
    }

}
