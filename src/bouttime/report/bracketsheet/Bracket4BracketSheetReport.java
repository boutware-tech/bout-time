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
import java.util.List;

/**
 * A class to draw a 4-man bracket in a pdf document.
 */
public class Bracket4BracketSheetReport extends CommonBracketSheet {

    public static final float matBoxStartX = 35;           // column to start the mat box
    public static final float matBoxStartY = 760;          // line to start the mat box
    public static final float matBoxWidth = 50;            // width of mat box
    public static final float matBoxHeight = 60;           // height of mat box
    public static final float matBoxTopPad = 15;           // space between mat box top and text
    public static final float matBoxBottomPad = 10;        // space between mat box bottom and mat number
    public static final float leftMargin = 65;             // space for the left margin

    @Override
    protected boolean drawBracket(PdfContentByte cb, BaseFont bf, Dao dao,
            Group group, boolean doBoutNumbers) throws DocumentException, IOException {

        if (doBoutNumbers) {
            BracketSheetUtil.drawMatBox(cb, bf, matBoxStartX, matBoxStartY,
                    matBoxWidth, matBoxHeight, matBoxTopPad, matBoxBottomPad,
                    1, 0, (group != null) ? group.getMat() : "", 90);
        }

        BracketSheetUtil.drawTournamentHeader(cb, bf, matBoxStartX, 35, dao, 90);
        if (group != null) {
            RoundRobinBracketSheetUtil.drawTitle(cb, bf, 35,
                    cb.getPdfDocument().getPageSize().getHeight() / 2, group, 90);
        }

        List<Wrestler> wList = null;
        Wrestler w1 = null;
        Wrestler w2 = null;
        Wrestler w3 = null;
        Wrestler w4 = null;
        Wrestler wa = null;

        if (group != null) {
            w1 = group.getWrestlerAtSeed(1);
            w2 = group.getWrestlerAtSeed(2);
            w3 = group.getWrestlerAtSeed(3);
            w4 = group.getWrestlerAtSeed(4);
        }

        float xStart = leftMargin + 30;
        float x = xStart;
        float yStart = 290;
        float y = yStart;
        float length = 250;
        float height = 120;
        BracketSheetUtil.drawString(cb, bf, x - 5, y + 5, 12,
                (w1 != null) ? w1.getString4Bracket() : "", 90,
                (w1 != null) ? w1.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 12, y + 5, 12,
                (w4 != null) ? w4.getString4Bracket() : "", 90,
                (w4 != null) ? w4.isScratched() : false);
        Bout b = (group != null) ? group.getBout(Bout.ROUND_1, 1) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 8,
                    y + (length / 2), 20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 2, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 2, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 5, y + 5, 12,
                (w3 != null) ? w3.getString4Bracket() : "", 90,
                (w3 != null) ? w3.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 12, y + 5, 12,
                (w2 != null) ? w2.getString4Bracket() : "", 90,
                (w2 != null) ? w2.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 2) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 8,
                    y + (length / 2), 20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 2, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 2, 0);

        x = xStart + (height / 2);
        y = yStart + length;
        float round2height = height * 2;
        float round2length = length / 2;
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 2, 0);

        b = (group != null) ? group.getBout(Bout.ROUND_2, 1) : null;
        if (b != null) {
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 10, y + 8, 12,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round2height + 20,
                        y + 8, 12, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (round2height / 2);
        y = y + round2length;
        float finalLength = (round2length / 3) * 3;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 2, 0);

        float fontsize = 12;
        float mid = y + (finalLength / 2);
        x += 15; // padding below the line
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "Champion", 90);

        y -= 50;
        b = (group != null) ? group.getBout(Bout.ROUND_2, 1) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x, y, 20, 20, 0, 6, 12, 1,
                        0, b.getBoutNum(), 90);
            }
            wa = b.getWinner();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 20, y + 58, 12, wa.getLastName(), 90);
            }
        }

        BracketSheetUtil.drawBoutLabel(cb, bf, x - 50, y + 10, 10, 5, 2, 12, 1, 0, "A", 90);

        // Draw consolation bracket
        float consYStart = yStart - round2length;
        float consXStart = xStart + (height / 2);
        x = consXStart;
        y = consYStart;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 2, 0);

        b = (group != null) ? group.getBout(Bout.ROUND_2, 2) : null;
        if (b != null) {
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 10, y + round2length - 12, 12, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + round2height + 20, y + round2length - 12, 12, wa.getLastName(), 90);
            }
        }

        x = x + (round2height / 2);
        y = y - round2length;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 2, 0);
        x += 15; // padding below the line
        mid = y + (finalLength / 2);
        fontsize = 12;
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "3rd Place", 90);

        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x, consYStart + 30, 20,
                        20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getWinner();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 20, y + round2length - 12, 12, wa.getLastName(), 90);
            }
        }
        BracketSheetUtil.drawBoutLabel(cb, bf, x - 50, consYStart + 40, 10, 5, 2, 12, 1, 0, "Z", 90);

        if (dao.isSecondPlaceChallengeEnabled()) {
            float challengeX = 500;
            float challengeY = 550;
            x = challengeX;
            y = challengeY;
            height = height / 2;
            b = (group != null) ? group.getBout(Bout.ROUND_3, 1) : null;
            if (b != null) {
                if (!b.isBye() && doBoutNumbers) {
                    BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 3),
                            y + (length / 4), 20, 20, 0, 6, 12, 1, 0,
                            b.getBoutNum(), 90);
                }

                wa = b.getRed();
                if (wa != null) {
                    BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_LEFT,
                            x - 5, y + 8, 12, wa.getLastName(), 90);
                }

                wa = b.getGreen();
                if (wa != null) {
                    BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_LEFT,
                            x + height + 14, y + 8, 12, wa.getLastName(), 90);
                }
            }
            BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, height, 2, 0);
            fontsize = 10;
            BracketSheetUtil.drawString(cb, bf, x, y - 40, fontsize, "Loser A", 90);
            BracketSheetUtil.drawString(cb, bf, x + height, y - 47, fontsize, "Winner Z", 90);
            x += (height / 2);
            y = y + round2length;
            BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 2, 0);
            if (b != null) {
                wa = b.getWinner();
                if (wa != null) {
                    BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_LEFT,
                            x - 3, y + 5, 12, wa.getLastName(), 90);
                }
            }
            x += 15; // padding below the line
            mid = y + (finalLength / 2);
            fontsize = 10;
            BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "2nd Challenge", 90);
        }

        return true;
    }
}
