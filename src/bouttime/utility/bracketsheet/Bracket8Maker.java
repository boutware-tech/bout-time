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
 * 8-man bracket sheets
 */
public class Bracket8Maker {
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

        int strLimit = 32;  // character limit of name string
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
        float yStart = 355;
        float y = yStart;
        float length = 200;
        float height = 60;
        BracketSheetUtil.drawString(cb, bf, x - 5, y + 5, 12,
                "George Washington (1/100) Eagles", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 12, y + 5, 12,
                "James Monroe (8/100) Falcons", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 8, y + (length / 2), 20, 20, 0, 6, 12, 1, 0, "13", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 2, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 2, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 5, y + 5, 12,
                "Number Five (5/100) Bluejays", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 12, y + 5, 12,
                "Number Four (4/100) Orioles", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 8, y + (length / 2), 20, 20, 0, 6, 12, 1, 0, "14", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 2, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 2, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 5, y + 5, 12,
                "Number Three (3/100) Hawks", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 12, y + 5, 12,
                "Number Six (6/100) Condors", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 8, y + (length / 2), 20, 20, 0, 6, 12, 1, 0, "15", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 2, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 2, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 5, y + 5, 12,
                "Number Seven (7/100) Owls", 90);
        BracketSheetUtil.drawString(cb, bf, x + height + 12, y + 5, 12,
                "Number Two (2/100) Ravens", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 8, y + (length / 2), 20, 20, 0, 6, 12, 1, 0, "16", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 2, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 2, 0);

        x = xStart + (height / 2);
        y = yStart + length;
        float round2height = height * 2;
        float round2length = (length / 5) * 2;  // 40%
        BracketSheetUtil.drawString(cb, bf, x - 3, y + 5, 12,
                "Washington", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 2, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (round2height / 2) - 10,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "B", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2) + 10),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "35", 90);
        
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 2, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (round2height / 2) - 10,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "C", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2) + 10),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "36", 90);

        x = xStart + (height / 2) + (round2height / 2);
        y = yStart + length + round2length;
        float round3height = round2height * 2;
        float round3length = round2length;
        BracketSheetUtil.drawFishTailUp(cb, x, y, round3length, round3height, 2, 0);

        x = x + (round3height / 2);
        y = y + round3length;
        float finalLength = (round3length / 3) * 3;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 2, 0);

        float fontsize = 12;
        float mid = y + (finalLength / 2);
        x += 15; // padding below the line
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "Champion", 90);

        y -= 50;
        BracketSheetUtil.drawBoutNum(cb, bf, x, y, 20, 20, 0, 6, 12, 1, 0, "23", 90);
        BracketSheetUtil.drawBoutLabel(cb, bf, x - 50, y + 10, 10, 5, 2, 12, 1, 0, "A", 90);

        /////////////////////////////
        // Draw consolation/back side
        /////////////////////////////

        // Round 2
        float consYStart = yStart - round2length;
        float consXStart = xStart + (height / 2);
        x = consXStart;
        y = consYStart;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 2, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2)),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "31", 90);
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 2, 0);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2)),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "32", 90);

        // Round 3
        float consRound3height = (round2height / 5) * 4;  // 80%
        fontsize = 10;
        x = consXStart + (round2height / 2);
        y = consYStart - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 2, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound3height + 3,
                y + round2length + 3, fontsize, "Loser C", 90);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound3height / 2) - 12,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "X", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((consRound3height / 2) + 12),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "39", 90);

        x = x + round2height + (round2height / 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 2, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize, "Loser B", 90);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound3height / 2) - 12,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "Y", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((consRound3height / 2) + 12),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "38", 90);

        // Round 4
        float consRound4height = (consRound3height * 2) - ((round2height / 5) * 2); // minus 40%
        x = consXStart + (round2height / 2) + (consRound3height / 2);
        y = consYStart - round2length - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound4height, 2, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound4height / 2) - 25,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "Z", 90);
        BracketSheetUtil.drawBoutNum(cb, bf, x + ((consRound4height / 2) + 25),
                y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, "40", 90);


        x = x + (consRound4height / 2);
        y = y - round2length;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 2, 0);
        x += 15; // padding below the line
        mid = y + (finalLength / 2);
        fontsize = 12;
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "3rd Place", 90);

        // 2nd place challenge
        float challengeX = 530;
        float challengeY = 620;
        x = challengeX;
        y = challengeY;
        height = (height / 3) * 2; // 66%
        BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 4), y + (length / 4), 20, 20, 0, 6, 12, 1, 0, "42", 90);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, height, 2, 0);
        fontsize = 10;
        BracketSheetUtil.drawString(cb, bf, x, y - 40, fontsize, "Loser A", 90);
        BracketSheetUtil.drawString(cb, bf, x + height, y - 47, fontsize, "Winner Z", 90);
        x += (height / 2);
        y = y + round2length;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 2, 0);
        x += 15; // padding below the line
        mid = y + (finalLength / 2);
        fontsize = 10;
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "2nd Challenge", 90);
    }
}
