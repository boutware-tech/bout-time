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
package bouttime.report.bracketsheet;

import bouttime.dao.Dao;
import bouttime.model.Group;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import java.io.IOException;

/**
 * Class of general utility methods for drawing a Round Robin bracket with iText.
 */
public class RoundRobinBracketSheetUtil {

    public static final float boutRectWidth = 30;   // width of rectangles in bout line
    public static final float boutRectHeight = 30;  // height of rectangles in bout line
    public static final float boutLineLength = 170; // length of line for name in bout line
    public static final float spaceForVS = 10;      // space for "vs" text
    public static final float padForVS = 5;         // space between box and "vs" text
    public static final float boxPad = 3;           // space between box and text

    public static void drawBoutHeader(PdfContentByte cb, BaseFont bf, float x, float y)
            throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        float width = boutRectWidth;
        float fontSize = 8;

        x += (width / 2);
        BracketSheetUtil.drawStringCentered(cb, bf, x, y, fontSize, "Round", 0);
        x += width;
        BracketSheetUtil.drawStringCentered(cb, bf, x, y, fontSize, "Bout", 0);

        x += boutLineLength + width;
        BracketSheetUtil.drawStringCentered(cb, bf, x, y, fontSize, "Winner", 0);
        x += boutLineLength + spaceForVS + boutRectWidth + (padForVS * 2);
        BracketSheetUtil.drawStringCentered(cb, bf, x, y, fontSize, "Winner", 0);
    }

    public static void drawBout(PdfContentByte cb, BaseFont bf, float x, float y,
            String round) throws DocumentException, IOException {

        drawBout(cb, bf, x, y, null, false, null, false, null, round, 0);
    }

    public static void drawBout(PdfContentByte cb, BaseFont bf, float x, float y,
            String red, boolean strikeRed, String green, boolean strikeGreen,
            String boutNum, String round, int winner) throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        float width = boutRectWidth;
        float height = boutRectHeight;
        float length = boutLineLength;
        float fontSize = 12;
        float pad = padForVS;
        float linePad = 2;

        BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + (boutRectHeight / 4), fontSize, round, 0);
        x += width;
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + (boutRectHeight / 4), fontSize, boutNum, 0);
        x += width;
        BracketSheetUtil.drawHorizontalLine(cb, x, y, length, 1, 0);
        if (red != null) {
            BracketSheetUtil.drawString(cb, bf, x + boxPad, y + linePad, fontSize,
                    red, strikeRed);
        }

        x += length;
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        if (winner == 1) {
            BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + (height / 4), fontSize, "X", 0);
        }
        x += width + pad;
        BracketSheetUtil.drawString(cb, bf, x, y + linePad, fontSize, "vs");
        x += pad + spaceForVS;
        BracketSheetUtil.drawHorizontalLine(cb, x, y, length, 1, 0);
        if (green != null) {
            BracketSheetUtil.drawString(cb, bf, x + boxPad, y + linePad, fontSize,
                    green, strikeGreen);
        }
        x += length;
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0);
        if (winner == 2) {
            BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + (height / 4), fontSize, "X", 0);
        }
    }

    public static void drawWrestlerNameHeader(PdfContentByte cb, BaseFont bf, float x,
            float y, float width) throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        float fontSize = 8;
        float mid;

        mid = x + (width / 2);
        BracketSheetUtil.drawStringCentered(cb, bf, mid, y, fontSize, "Wins", 0);
        mid += width;
        BracketSheetUtil.drawStringCentered(cb, bf, mid, y, fontSize, "Losses", 0);
        mid += width;
        BracketSheetUtil.drawStringCentered(cb, bf, mid, y, fontSize, "Place", 0);
    }

    public static void drawWrestlerNameLine(PdfContentByte cb, BaseFont bf, float x, float y,
            float length, int tablePad, float width, float height, int wins, int losses, Integer place)
            throws DocumentException, IOException {
        
        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        BracketSheetUtil.drawHorizontalLine(cb, x, y, length, 1, 0);

        x = x + length + tablePad;
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0); // wins
        if (wins >= 0) {
            BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + (height / 4), 12, Integer.toString(wins), 0);
        }
        x += width;
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0); // losses
        if (losses >= 0) {
            BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + (height / 4), 12, Integer.toString(losses), 0);
        }
        x += width;
        BracketSheetUtil.drawRectangle(cb, x, y, width, height, 1, 0); // place
        if (place != null) {
            BracketSheetUtil.drawStringCentered(cb, bf, x + (width / 2), y + (height / 4), 12, place.toString(), 0);
        }
    }

    public static void drawTitle(PdfContentByte cb, BaseFont bf, float mid,
            float y, Group group, float rotation) throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        if (group == null) {
            return;
        }

        String classification = group.getClassification();
        if (classification == null) {
            classification = "";
        }

        String div = group.getAgeDivision();
        if (div == null) {
            div = "";
        }

        String weightClass = group.getWeightClass();
        if (weightClass == null) {
            weightClass = "";
        }

        String title = String.format("%s    %s    %s", classification, div, weightClass);
        title.trim();

        BracketSheetUtil.drawStringCentered(cb, bf, mid, y, 18, title, rotation);
    }
    public static final float matBoxStartX = 470;           // column to start the mat box
    public static final float matBoxStartY = 745;           // line to start the mat box
    public static final float matBoxWidth = 60;             // width of mat box
    public static final float matBoxHeight = 50;            // height of mat box
    public static final float matBoxTopPad = 15;            // space between mat box top and text
    public static final float matBoxBottomPad = 10;         // space between mat box bottom and mat number
    public static final float tournamentHeaderStartY = 780; // line to start tournament header
    public static final float groupTitleStartY = 710;       // line to start group title

    public static void drawMatBox(PdfContentByte cb, BaseFont bf, float x,
            float y, float width, float height, float topPad, float botPad,
            float lineWidth, float grayStroke, String mat)
            throws DocumentException, IOException {

        BracketSheetUtil.drawMatBox(cb, bf, matBoxStartX, matBoxStartY, matBoxWidth,
                matBoxHeight, matBoxTopPad, matBoxBottomPad, 1, 0, mat, 0);
    }

    public static void drawMatBox(PdfContentByte cb, BaseFont bf)
            throws DocumentException, IOException {

        BracketSheetUtil.drawMatBox(cb, bf, matBoxStartX, matBoxStartY, matBoxWidth,
                matBoxHeight, matBoxTopPad, matBoxBottomPad, 1, 0, "", 0);
    }

    public static void drawHeader(PdfContentByte cb, BaseFont bf, float x,
            Dao dao, Group group, boolean doBoutNumbers) throws DocumentException, IOException {

        if ((dao != null) && dao.isOpen()) {
            BracketSheetUtil.drawTournamentHeader(cb, bf, x, tournamentHeaderStartY, dao, 0);
        }

        String mat = (group != null) ? group.getMat() : "";

        if (doBoutNumbers) {
            drawMatBox(cb, bf, matBoxStartX, matBoxStartY, matBoxWidth,
                    matBoxHeight, matBoxTopPad, matBoxBottomPad, 1, 0, mat);
        }

        drawTitle(cb, bf, cb.getPdfDocument().getPageSize().getWidth() / 2,
                groupTitleStartY, group, 0);
    }
}
