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

package bouttime.report.bracketsheet;

import bouttime.dao.Dao;
import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import java.io.IOException;

/**
 * A class to draw a 2-man bracket in a pdf document.
 */
public class Bracket2BracketSheetReport extends CommonBracketSheet {
    public static float matBoxStartX = 470;           // column to start the mat box
    public static float matBoxStartY = 745;           // line to start the mat box
    public static float matBoxWidth = 60;             // width of mat box
    public static float matBoxHeight = 50;            // height of mat box
    public static float matBoxTopPad = 15;            // space between mat box top and text
    public static float matBoxBottomPad = 10;         // space between mat box bottom and mat number

    @Override
    protected boolean drawBracket(PdfContentByte cb, BaseFont bf, Dao dao,
            Group group, boolean doBoutNumbers) throws DocumentException, IOException {

        Wrestler w1 = null;
        Wrestler w2 = null;
        Bout r1b1 = null;

        if (group != null) {
            w1 = group.getWrestlerAtSeed(1);
            w2 = group.getWrestlerAtSeed(2);
            r1b1 = group.getBout(Bout.ROUND_1, 1);
        }

        float leftMargin = 65;
        
        RoundRobinBracketSheetUtil.drawHeader(cb, bf, leftMargin, dao, group, doBoutNumbers);

        float x = leftMargin;
        float y = 620;
        float length = 310;
        float height = 500;
        float topLinePad = 3;
        float bottomLinePad = 15;
        float fontSize = 14;
        BracketSheetUtil.drawFishTailRight(cb, x, y, length, height, 2, 0);
        BracketSheetUtil.drawString(cb, bf, leftMargin, y + topLinePad, fontSize,
                (w1 != null) ? w1.getString4Bracket() : "",
                (w1 != null) ? w1.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, leftMargin, y - height - bottomLinePad,
                fontSize, (w2 != null) ? w2.getString4Bracket() : "",
                (w2 != null) ? w2.isScratched() : false);

        x = leftMargin + length;
        y = y - (height / 2);
        length = length / 2;
        BracketSheetUtil.drawHorizontalLine(cb, x, y, length, 2, 0);

        if (r1b1 != null) {
            Wrestler w = r1b1.getWinner();
            if (w != null) {
                BracketSheetUtil.drawString(cb, bf, x + 10, y + 18,
                        fontSize, w.getLastName());
            }
        }

        float fontsize = 12;
        float mid = x + (length / 2);
        y -= 15; // padding below the line
        BracketSheetUtil.drawStringCentered(cb, bf, mid, y, fontsize, "Champion", 0);

        x -= 100;
        if ((r1b1 != null) && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x, y, 20, 20, 0, 5, 12, 1, 0,
                    r1b1.getBoutNum(), 0);
        }
        
        return true;
    }
}
