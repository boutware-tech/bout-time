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
 * 16-man bracket sheets
 */
public class Bracket16Maker {
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

        float xStart = leftMargin + 30;
        float x = xStart;
        float yStart = 395;
        float y = yStart;
        float length = 150;
        float height = 30;
        BracketSheetUtil.drawString(cb, bf, x - 2, y + 2, 10,
                "George Washington (1/100) Eagles", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 9, y + 2, 10,
                "James Monroe (16/100) Falcons", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 10, y + (length / 2), 20, 20, 0, 6, 12, 1, 0, "10", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 2, y + 2, 10,
                "Number Nine (9/100) Bluejays", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 9, y + 2, 10,
                "Number Eight (8/100) Orioles", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 10, y + (length / 2), 20, 20, 0, 6, 12, 1, 0, "11", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 2, y + 2, 10,
                "Number Five (5/100) Hawks", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 9, y + 2, 10,
                "Number Twelve (12/100) Condors", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 10, y + (length / 2), 20, 20, 0, 6, 12, 1, 0, "12", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 2, y + 2, 10,
                "Number Thirteen (13/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 9, y + 2, 10,
                "Number Four (4/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 10, y + (length / 2), 20, 20, 0, 6, 12, 1, 0, "13", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 2, y + 2, 10,
                "Number Three (3/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 9, y + 2, 10,
                "Number Fourteen (14/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 10, y + (length / 2), 20, 20, 0, 6, 12, 1, 0, "14", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 2, y + 2, 10,
                "Number Eleven (11/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 9, y + 2, 10,
                "Number Six (6/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 10, y + (length / 2), 20, 20, 0, 6, 12, 1, 0, "15", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 2, y + 2, 10,
                "Number Seven (7/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 9, y + 2, 10,
                "Number Ten (10/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 10, y + (length / 2), 20, 20, 0, 6, 12, 1, 0, "16", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 2, y + 2, 10,
                "Number Fifteen (15/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 9, y + 2, 10,
                "Number Two (2/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 10, y + (length / 2), 20, 20, 0, 6, 12, 1, 0, "17", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        // Round 2
        x = xStart + (height / 2);
        y = yStart + length;
        float round2height = height * 2;
        float round2length = (length / 5) * 2;  // 40%
        BracketSheetUtil.drawString(cb, bf, x - 3, y + 5, 10,
                "Washington", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (round2height / 2) - 15,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "D", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2) + 5),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "21", 90);

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (round2height / 2) - 15,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "E", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2) + 5),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "22", 90);

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (round2height / 2) - 15,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "F", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2) + 5),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "23", 90);

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (round2height / 2) - 15,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "G", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2) + 5),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "24", 90);

        // Round 4
        x = xStart + (height / 2) + (round2height / 2);
        y = yStart + length + round2length;
        float round4height = round2height * 2;
        float round4length = round2length;
        BracketSheetUtil.drawString(cb, bf, x - 3, y + 5, 10,
                "Washington", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round4length, round4height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (round4height / 2) - 10,
                y + (round4length / 2), 10, 5, 2, 12, 1, 0, "B", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round4height / 2) + 10),
                y + ((round4length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "41", 90);

        x = x + (2 * round4height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round4length, round4height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (round4height / 2) - 10,
                y + (round4length / 2), 10, 5, 2, 12, 1, 0, "C", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round4height / 2) + 10),
                y + ((round4length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "42", 90);

        // Round 6
        x = xStart + (height / 2) + (round2height / 2) + (round4height / 2);
        y = yStart + length + round2length + round4length;
        float round6height = round4height * 2;
        float round6length = round4length;
        BracketSheetUtil.drawFishTailUp(cb, x, y, round6length, round6height, 1, 0);

        x = x + (round6height / 2);
        y = y + round6length;
        float finalLength = (round6length / 3) * 3;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 1, 0);

        float fontsize = 8;
        float mid = y + (finalLength / 2);
        x += 15; // padding below the line
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "Champion", 90);

        y -= 50;
        BracketSheetUtil.drawBoutNum(cb, bf, x, y, 20, 20, 0, 6, 12, 1, 0, "61", 90);
        BracketSheetUtil.drawBoutLabel(cb, bf, x - 50, y + 10, 10, 5, 2, 12, 1, 0, "A", 90);

        /////////////////////////////
        // Draw consolation/back side
        /////////////////////////////

        // Round 2
        float consYStart = yStart - round2length;
        float consXStart = xStart + (height / 2);
        x = consXStart;
        y = consYStart;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2)),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "25", 90);
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2)),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "26", 90);
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2)),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "27", 90);
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2)),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "28", 90);

        // Round 3
        float consRound3height = (round2height / 5) * 4;  // 80%
        fontsize = 8;
        x = consXStart + (round2height / 2);
        y = consYStart - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound3height + 3,
                y + round2length + 3, fontsize, "Loser G", 90);
        //BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound3height / 2) - 12,
        //        y + (round2length / 2), 10, 5, 2, 1, 0, "X", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (consRound3height / 3),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "31", 90);

        x = x + round2height + (round2height / 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize, "Loser F", 90);
        //BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound3height / 2) - 12,
        //        y + (round2length / 2), 10, 5, 2, 1, 0, "Y", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (consRound3height / 3),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "32", 90);

        x = x + (round2height * 2) + ((round2height / 5) * 4);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound3height + 3,
                y + round2length + 3, fontsize, "Loser E", 90);
        //BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound3height / 2) - 12,
        //        y + (round2length / 2), 10, 5, 2, 1, 0, "X", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (consRound3height / 3),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "33", 90);

        x = x + round2height + (round2height / 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize, "Loser D", 90);
        //BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound3height / 2) - 12,
        //        y + (round2length / 2), 10, 5, 2, 1, 0, "Y", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (consRound3height / 3),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "34", 90);

        // Round 4
        float consRound4height = (consRound3height * 2) - ((round2height / 5) * 2); // minus 40%
        x = consXStart + (round2height / 2) + (consRound3height / 2);
        y = consYStart - round2length - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound4height, 1, 0);
        //BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound4height / 2) - 25,
        //        y + (round2length / 2), 10, 5, 2, 1, 0, "Z", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (consRound4height / 2),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "43", 90);
        x = x + (consRound3height * 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound4height, 1, 0);
        //BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound4height / 2) - 25,
        //        y + (round2length / 2), 10, 5, 2, 1, 0, "Z", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (consRound4height / 2),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "44", 90);

        // Round 5
        float consRound5height = (consRound4height * 2) - ((consRound4height / 5) * 3); // minus 60%
        x = consXStart + (round2height / 2) + (consRound3height / 2) + (consRound4height / 2);
        y = consYStart - round2length - round2length - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound5height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound5height + 3,
                y + round2length + 3, fontsize, "Loser B", 90);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound5height / 2) - 12,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "X", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((consRound5height / 2) + 12),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "51", 90);

        x = x + consRound5height + ((consRound5height / 5) * 2);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound5height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize, "Loser C", 90);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound5height / 2) - 12,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "Y", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((consRound5height / 2) + 12),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "52", 90);

        float consRound6height = (consRound5height * 2) - ((consRound5height / 5) * 3); // 60%
        x = consXStart + (round2height / 2) + (consRound3height / 2) + (consRound4height / 2) + (consRound5height / 2);
        y = y - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound6height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound6height / 2) - 12,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "Z", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((consRound6height / 2) + 12),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "62", 90);
        x = x + (consRound6height / 2);
        y = y - round2length;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 1, 0);
        x += 15; // padding below the line
        mid = y + (finalLength / 2);
        fontsize = 8;
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "3rd Place", 90);

        // 2nd place challenge
        float challengeX = 530;
        float challengeY = 670;
        x = challengeX;
        y = challengeY;
        float height2 = height; // 66%
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
    }
}

