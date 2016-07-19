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
 * 32-man bracket sheets
 */
public class Bracket32Maker {
    public static final float matBoxStartX = 35;           // column to start the mat box
    public static final float matBoxStartY = 760;           // line to start the mat box
    public static final float matBoxWidth = 50;             // width of mat box
    public static final float matBoxHeight = 60;            // height of mat box
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

        int strLimit = 30;  // character limit of name string
        float rightMargin = 530;  // pageWidth - 65
        float leftMargin = 65;
        float testY = 50;
        float testLength = 500;
        //BracketSheetUtil.drawVerticalLine(cb, rightMargin, testY, testLength, 1, 0);
        //BracketSheetUtil.drawVerticalLine(cb, leftMargin, testY, testLength, 1, 0);

        BracketSheetUtil.drawMatBox(cb, bf, matBoxStartX, matBoxStartY,
                matBoxWidth, matBoxHeight, matBoxTopPad, matBoxBottomPad,
                1, 0, "1", 90);

        int fontSize = 7;
        float xStart = leftMargin + 7;
        float x = xStart;
        float yStart = 425;
        float y = yStart;
        float length = 120;
        float height = 16;
        float boutNumHeight = 12;
        float boutNumWidth = 12;
        float boutNumXdiff = 2;
        float boutNumYdiff = length / 2;
        float boutNumTopPad = 2;
        float boutNumBotPad = 2;
        int boutNumFontSize = 8;
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "George Washington (1/100) Eagles", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "James Monroe (32/100) Falcons", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "10", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Seventeen (17/100) Bluejays", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Sixteen (16/100) Orioles", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "11", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Nine (9/100) Hawks", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Twenty-four (24/100) Condors", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "12", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Twenty-five (25/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Eight (8/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "13", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Five (5/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Twenty-eight (28/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "14", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Twenty-one (21/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Twelve (12/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "15", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Thirteen (13/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Twenty (20/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "16", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Twenty-nine (29/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Four (4/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "17", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Three (3/100) Eagles", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Thirty (30/100) Falcons", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "10", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Nineteen (19/100) Bluejays", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Fourteen (14/100) Orioles", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "11", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Eleven (11/100) Hawks", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Twenty-two (22/100) Condors", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "12", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Twenty-seven (27/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Six (6/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "13", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Seven (7/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Twenty-six (26/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "14", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Twenty-three (23/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Ten (10/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "15", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Fifteen (15/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Eightteen (18/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "16", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                "Number Thirty-one (31/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                "Number Two (2/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "17", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        // Round 2
        x = xStart + (height / 2);
        y = yStart + length;
        float round2height = height * 2;
        float round2length = (length / 5) * 2;  // 40%
        float boutNumYdiff2 = (round2length / 2) + 8;
        float boutNumXdiff2 = (round2height / 2) + 2;
        float boutNumWidth2 = 12;
        float boutNumHeight2 = 12;
        float boutNumTopPad2 = 2;
        float boutNumBotPad2 = 2;
        int boutNumFontSize2 = 8;
        int boutLabelFontSize = 8;
        float boutLabelRadius = 6;
        float boutLabelXdiff2 = (round2height / 2) - 7;
        float boutLabelYdiff2 = (round2length / 2) + 13;
        float boutLabelBotPad2 = 2;
        float boutLabelTopPad2 = 3;
        BracketSheetUtil.drawString(cb, bf, x - 3, y + 5, fontSize,
                "Washington", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2, boutLabelBotPad2, boutLabelFontSize, 1, 0, "H", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "21", 90);

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2, boutLabelBotPad2, boutLabelFontSize, 1, 0, "I", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "22", 90);

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2, boutLabelBotPad2, boutLabelFontSize, 1, 0, "J", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "23", 90);

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2, boutLabelBotPad2, boutLabelFontSize, 1, 0, "K", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "24", 90);

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2, boutLabelBotPad2, boutLabelFontSize, 1, 0, "L", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "25", 90);

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2, boutLabelBotPad2, boutLabelFontSize, 1, 0, "M", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "26", 90);

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2, boutLabelBotPad2, boutLabelFontSize, 1, 0, "N", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "27", 90);

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2, boutLabelBotPad2, boutLabelFontSize, 1, 0, "O", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "28", 90);

        // Round 4
        x = xStart + (height / 2) + (round2height / 2);
        y = yStart + length + round2length;
        float round4height = round2height * 2;
        float round4length = round2length;
        float boutNumYdiff4 = round4length / 2;
        float boutNumXdiff4 = (round4height / 2) + 12;
        float boutLabelXdiff4 = (round4height / 4);
        float boutLabelYdiff4 = (round4length / 2) + 7;
        float boutLabelBotPad4 = 2;
        float boutLabelTopPad4 = 4;
        boutLabelFontSize = 10;
        boutLabelRadius = 8;
        boutNumFontSize = 10;
        boutNumWidth = 15;
        boutNumHeight = 15;
        BracketSheetUtil.drawString(cb, bf, x - 3, y + 5, fontSize,
                "Washington", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round4length, round4height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff4,
                y + boutLabelYdiff4, boutLabelRadius, boutLabelTopPad4, boutLabelBotPad4, boutLabelFontSize, 1, 0, "D", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                y + boutNumYdiff4, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "41", 90);

        x = x + (2 * round4height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round4length, round4height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff4,
                y + boutLabelYdiff4, boutLabelRadius, boutLabelTopPad4, boutLabelBotPad4, boutLabelFontSize, 1, 0, "E", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                y + boutNumYdiff4, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "42", 90);

        x = x + (2 * round4height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round4length, round4height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff4,
                y + boutLabelYdiff4, boutLabelRadius, boutLabelTopPad4, boutLabelBotPad4, boutLabelFontSize, 1, 0, "F", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                y + boutNumYdiff4, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "43", 90);

        x = x + (2 * round4height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round4length, round4height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff4,
                y + boutLabelYdiff4, boutLabelRadius, boutLabelTopPad4, boutLabelBotPad4, boutLabelFontSize, 1, 0, "G", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                y + boutNumYdiff4, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "44", 90);

        // Round 6
        x = xStart + (height / 2) + (round2height / 2) + (round4height / 2);
        y = yStart + length + round2length + round4length;
        float round6height = round4height * 2;
        float round6length = round4length;
        float boutNumYdiff6 = round6length / 2;
        float boutNumXdiff6 = (round6height / 4) * 3;
        float boutLabelXdiff6 = (round6height / 4);
        float boutLabelYdiff6 = (round6length / 2) + 7;
        float boutLabelBotPad6 = 2;
        float boutLabelTopPad6 = 4;
        boutLabelFontSize = 10;
        boutLabelRadius = 8;
        boutNumFontSize = 10;
        boutNumWidth = 15;
        boutNumHeight = 15;
        BracketSheetUtil.drawString(cb, bf, x - 3, y + 5, fontSize,
                "Washington", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round6length, round6height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff6,
                y + boutLabelYdiff6, boutLabelRadius, boutLabelTopPad6, boutLabelBotPad6, boutLabelFontSize, 1, 0, "B", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff6,
                y + boutNumYdiff6, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "61", 90);

        x = x + (2 * round6height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round6length, round6height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff6,
                y + boutLabelYdiff6, boutLabelRadius, boutLabelTopPad6, boutLabelBotPad6, boutLabelFontSize, 1, 0, "C", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff6,
                y + boutNumYdiff6, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "62", 90);

        // Round 8
        x = xStart + (height / 2) + (round2height / 2) + (round4height / 2) + (round6height / 2);
        y = yStart + length + round2length + round4length + round6length;
        float round8height = round6height * 2;
        float round8length = round6length;
        float boutNumYdiff8 = (round8length / 2) - 5;
        float boutNumXdiff8 = (round8height / 3) * 2;
        boutNumFontSize = 12;
        boutNumWidth = 20;
        boutNumHeight = 20;
        boutNumTopPad = 0;
        boutNumBotPad = 5;
        BracketSheetUtil.drawFishTailUp(cb, x, y, round8length, round8height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff8, y + boutNumYdiff8,
                boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "81", 90);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (round8height / 3),
                y + boutNumYdiff8 + 8, 10, 5, 2, 12, 1, 0, "A", 90);

        x = x + (round8height / 2);
        y = y + round8length;
        float finalLength = (round6length / 3) * 3;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 1, 0);

        float fontsize = 8;
        float mid = y + (finalLength / 2);
        x += 15; // padding below the line
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "Champion", 90);

        y -= 50;

        /////////////////////////////
        // Draw consolation/back side
        /////////////////////////////

        // Round 2
        float consYStart = yStart - round2length;
        float consXStart = xStart + (height / 2);
        boutNumYdiff2 = 5;
        boutNumXdiff2 = (height / 2) + 2;
        x = consXStart;
        y = consYStart;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "25", 90);
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "26", 90);
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "27", 90);
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "28", 90);
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "29", 90);
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "30", 90);
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "31", 90);
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2, boutNumBotPad2, boutNumFontSize2, 1, 0, "32", 90);

        // Round 3
        float consRound3height = (round2height / 5) * 4;  // 80%
        float boutNumXdiff3 = (consRound3height / 4);
        fontsize = 8;
        x = consXStart + (round2height / 2);
        y = consYStart - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound3height + 3,
                y + round2length + 3, fontsize, "Loser O", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                boutNumBotPad2, boutNumFontSize2, 1, 0, "33", 90);

        x = x + round2height + (round2height / 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize, "Loser N", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                boutNumBotPad2, boutNumFontSize2, 1, 0, "34", 90);

        x = x + (round2height * 2) + ((round2height / 5) * 4);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound3height + 3,
                y + round2length + 3, fontsize, "Loser M", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                boutNumBotPad2, boutNumFontSize2, 1, 0, "35", 90);

        x = x + round2height + (round2height / 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize, "Loser L", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                boutNumBotPad2, boutNumFontSize2, 1, 0, "36", 90);

        x = x + (round2height * 2) + ((round2height / 5) * 4);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound3height + 3,
                y + round2length + 3, fontsize, "Loser K", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                boutNumBotPad2, boutNumFontSize2, 1, 0, "37", 90);

        x = x + round2height + (round2height / 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize, "Loser J", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                boutNumBotPad2, boutNumFontSize2, 1, 0, "38", 90);

        x = x + (round2height * 2) + ((round2height / 5) * 4);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound3height + 3,
                y + round2length + 3, fontsize, "Loser I", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                boutNumBotPad2, boutNumFontSize2, 1, 0, "39", 90);

        x = x + round2height + (round2height / 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize, "Loser H", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                boutNumBotPad2, boutNumFontSize2, 1, 0, "40", 90);

        // Round 4
        float consRound4height = (consRound3height * 2) - ((round2height / 5) * 2); // minus 40%
        boutNumXdiff4 = (consRound4height / 4);
        x = consXStart + (round2height / 2) + (consRound3height / 2);
        y = consYStart - round2length - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound4height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "45", 90);

        x = x + (consRound3height * 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound4height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "46", 90);

        x = x + (consRound3height * 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound4height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "47", 90);

        x = x + (consRound3height * 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound4height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "48", 90);

        // Round 5
        float consRound5height = (consRound4height * 2) - ((consRound4height / 5) * 3); // minus 60%
        float boutNumXdiff5 = (consRound5height / 3);
        x = consXStart + (round2height / 2) + (consRound3height / 2) + (consRound4height / 2);
        y = consYStart - round2length - round2length - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound5height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound5height + 3,
                y + round2length + 3, fontsize, "Loser E", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff5,
                y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "51", 90);

        x = x + consRound5height + ((consRound5height / 5) * 2);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound5height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize, "Loser D", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff5,
                y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "52", 90);

        x = x + (consRound5height * 3) + ((consRound5height / 6) * 2);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound5height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound5height + 3,
                y + round2length + 3, fontsize, "Loser G", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff5,
                y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "53", 90);

        x = x + consRound5height + ((consRound5height / 5) * 2);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound5height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize, "Loser F", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff5,
                y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "54", 90);

        // Round 6
        float consRound6height = (consRound5height * 2) - ((consRound5height / 5) * 3); // 60%
        boutNumXdiff6 = (consRound6height / 3);
        boutNumYdiff6 = round2height / 3;
        x = consXStart + (round2height / 2) + (consRound3height / 2) + (consRound4height / 2) + (consRound5height / 2);
        y = y - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound6height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff6,
                y + boutNumYdiff6, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "63", 90);

        x = x + (consRound6height * 2) + ((consRound6height / 5) * 7);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound6height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff6,
                y + boutNumYdiff6, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "64", 90);

        // Round 7
        float consRound7height = (consRound6height * 2) - ((consRound6height / 5) * 3); // 60%
        float boutNumXdiff7 = (consRound7height / 3) * 2;
        float boutLabelXdiff7 = (consRound7height / 3);
        x = consXStart + (round2height / 2) + (consRound3height / 2) + (consRound4height / 2) + (consRound5height / 2) + (consRound6height / 2);
        y = y - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound7height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound7height + 3,
                y + round2length + 3, fontsize, "Loser C", 90);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff7,
                y + boutNumYdiff2 + 10, 10, 5, 2, 12, 1, 0, "X", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff7,
                y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "71", 90);

        x = x + consRound7height + ((consRound7height / 5) * 2);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound7height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize, "Loser B", 90);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff7,
                y + boutNumYdiff2 + 10, 10, 5, 2, 12, 1, 0, "Y", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff7,
                y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "72", 90);

        // Round 8
        float consRound8height = (consRound7height * 2) - ((consRound7height / 5) * 3); // 60%
        boutNumXdiff8 = (consRound8height / 3) * 2;
        float boutLabelXdiff8 = (consRound8height / 3);
        x = consXStart + (round2height / 2) + (consRound3height / 2) + (consRound4height / 2) + (consRound5height / 2) + (consRound6height / 2) + (consRound7height / 2);
        y = y - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound8height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff8,
                y + boutNumYdiff2 + 10, 10, 5, 2, 12, 1, 0, "Z", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff8,
                y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad, boutNumFontSize, 1, 0, "82", 90);
        x = x + (consRound8height / 2);
        y = y - round2length;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 1, 0);
        x += 15; // padding below the line
        mid = y + (finalLength / 2);
        fontsize = 8;
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "3rd Place", 90);

        // 2nd place challenge
        float challengeX = 540;
        float challengeY = 700;
        x = challengeX;
        y = challengeY;
        float height2 = height + 15;
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height2 / 6), y + (length / 8), 20, 20, 0, 6, 12, 1, 0, "71", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, height2, 1, 0);
        fontsize = 8;
        BracketSheetUtil.drawString(cb, bf, x, y - 30, fontsize, "Loser A", 90);
        BracketSheetUtil.drawString(cb, bf, x + height2, y - 37, fontsize, "Winner Z", 90);
        x += (height2 / 2);
        y = y + round2length;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 1, 0);
        x += 15; // padding below the line
        mid = y + (finalLength / 2);
        fontsize = 8;
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "2nd Challenge", 90);

        // 5th place challenge
        float fifthX = 540;
        float fifthY = 100;
        x = fifthX;
        y = fifthY;
        float height5th = height2;
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height5th / 6), y + (length / 8),
                20, 20, 0, 6, 12, 1, 0, "83", 90);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, height2, 1, 0);
        fontsize = 8;
        BracketSheetUtil.drawString(cb, bf, x, y + round2length + 3, fontsize, "Loser X", 90);
        BracketSheetUtil.drawString(cb, bf, x + height2, y + round2length + 3, fontsize, "Loser Y", 90);
        x += (height2 / 2);
        y = y - round2length;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 1, 0);
        x += 15; // padding below the line
        mid = y + (finalLength / 2);
        fontsize = 8;
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "5th Place", 90);
    }
}
