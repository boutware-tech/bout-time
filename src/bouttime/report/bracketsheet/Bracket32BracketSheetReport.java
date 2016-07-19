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
 * A class to draw a 32-man bracket in a pdf document.
 */
public class Bracket32BracketSheetReport extends CommonBracketSheet {

    public static final float matBoxStartX = 35;           // column to start the mat box
    public static final float matBoxStartY = 760;           // line to start the mat box
    public static final float matBoxWidth = 50;             // width of mat box
    public static final float matBoxHeight = 60;            // height of mat box
    public static final float matBoxTopPad = 15;            // space between mat box top and text
    public static final float matBoxBottomPad = 10;         // space between mat box bottom and mat number

    @Override
    protected boolean drawBracket(PdfContentByte cb, BaseFont bf, Dao dao,
            Group group, boolean doBoutNumbers) throws DocumentException, IOException {

        float leftMargin = 65;

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

        Wrestler w1 = null;
        Wrestler w2 = null;
        Wrestler w3 = null;
        Wrestler w4 = null;
        Wrestler w5 = null;
        Wrestler w6 = null;
        Wrestler w7 = null;
        Wrestler w8 = null;
        Wrestler w9 = null;
        Wrestler w10 = null;
        Wrestler w11 = null;
        Wrestler w12 = null;
        Wrestler w13 = null;
        Wrestler w14 = null;
        Wrestler w15 = null;
        Wrestler w16 = null;
        Wrestler w17 = null;
        Wrestler w18 = null;
        Wrestler w19 = null;
        Wrestler w20 = null;
        Wrestler w21 = null;
        Wrestler w22 = null;
        Wrestler w23 = null;
        Wrestler w24 = null;
        Wrestler w25 = null;
        Wrestler w26 = null;
        Wrestler w27 = null;
        Wrestler w28 = null;
        Wrestler w29 = null;
        Wrestler w30 = null;
        Wrestler w31 = null;
        Wrestler w32 = null;
        Wrestler wa = null;

        if (group != null) {
            w1 = group.getWrestlerAtSeed(1);
            w2 = group.getWrestlerAtSeed(2);
            w3 = group.getWrestlerAtSeed(3);
            w4 = group.getWrestlerAtSeed(4);
            w5 = group.getWrestlerAtSeed(5);
            w6 = group.getWrestlerAtSeed(6);
            w7 = group.getWrestlerAtSeed(7);
            w8 = group.getWrestlerAtSeed(8);
            w9 = group.getWrestlerAtSeed(9);
            w10 = group.getWrestlerAtSeed(10);
            w11 = group.getWrestlerAtSeed(11);
            w12 = group.getWrestlerAtSeed(12);
            w13 = group.getWrestlerAtSeed(13);
            w14 = group.getWrestlerAtSeed(14);
            w15 = group.getWrestlerAtSeed(15);
            w16 = group.getWrestlerAtSeed(16);
            w17 = group.getWrestlerAtSeed(17);
            w18 = group.getWrestlerAtSeed(18);
            w19 = group.getWrestlerAtSeed(19);
            w20 = group.getWrestlerAtSeed(20);
            w21 = group.getWrestlerAtSeed(21);
            w22 = group.getWrestlerAtSeed(22);
            w23 = group.getWrestlerAtSeed(23);
            w24 = group.getWrestlerAtSeed(24);
            w25 = group.getWrestlerAtSeed(25);
            w26 = group.getWrestlerAtSeed(26);
            w27 = group.getWrestlerAtSeed(27);
            w28 = group.getWrestlerAtSeed(28);
            w29 = group.getWrestlerAtSeed(29);
            w30 = group.getWrestlerAtSeed(30);
            w31 = group.getWrestlerAtSeed(31);
            w32 = group.getWrestlerAtSeed(32);
        }

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
                (w1 != null) ? w1.getString4Bracket() : "", 90,
                (w1 != null) ? w1.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w32 != null) ? w32.getString4Bracket() : "", 90,
                (w32 != null) ? w32.isScratched() : false);
        Bout b = (group != null) ? group.getBout(Bout.ROUND_1, 1) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w17 != null) ? w17.getString4Bracket() : "", 90,
                (w17 != null) ? w17.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w16 != null) ? w16.getString4Bracket() : "", 90,
                (w16 != null) ? w16.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 2) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w9 != null) ? w9.getString4Bracket() : "", 90,
                (w9 != null) ? w9.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w24 != null) ? w24.getString4Bracket() : "", 90,
                (w24 != null) ? w24.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 3) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w25 != null) ? w25.getString4Bracket() : "", 90,
                (w25 != null) ? w25.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w8 != null) ? w8.getString4Bracket() : "", 90,
                (w8 != null) ? w8.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 4) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w5 != null) ? w5.getString4Bracket() : "", 90,
                (w5 != null) ? w5.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w28 != null) ? w28.getString4Bracket() : "", 90,
                (w28 != null) ? w28.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 5) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w21 != null) ? w21.getString4Bracket() : "", 90,
                (w21 != null) ? w21.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w12 != null) ? w12.getString4Bracket() : "", 90,
                (w12 != null) ? w12.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 6) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w13 != null) ? w13.getString4Bracket() : "", 90,
                (w13 != null) ? w13.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w20 != null) ? w20.getString4Bracket() : "", 90,
                (w20 != null) ? w20.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 7) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w29 != null) ? w29.getString4Bracket() : "", 90,
                (w29 != null) ? w29.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w4 != null) ? w4.getString4Bracket() : "", 90,
                (w4 != null) ? w4.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 8) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w3 != null) ? w3.getString4Bracket() : "", 90,
                (w3 != null) ? w3.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w30 != null) ? w30.getString4Bracket() : "", 90,
                (w30 != null) ? w30.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 9) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w19 != null) ? w19.getString4Bracket() : "", 90,
                (w19 != null) ? w19.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w14 != null) ? w14.getString4Bracket() : "", 90,
                (w14 != null) ? w14.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 10) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w11 != null) ? w11.getString4Bracket() : "", 90,
                (w11 != null) ? w11.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w22 != null) ? w22.getString4Bracket() : "", 90,
                (w22 != null) ? w22.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 11) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w27 != null) ? w27.getString4Bracket() : "", 90,
                (w27 != null) ? w27.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w6 != null) ? w6.getString4Bracket() : "", 90,
                (w6 != null) ? w6.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 12) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w7 != null) ? w7.getString4Bracket() : "", 90,
                (w7 != null) ? w7.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w26 != null) ? w26.getString4Bracket() : "", 90,
                (w26 != null) ? w26.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 13) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w23 != null) ? w23.getString4Bracket() : "", 90,
                (w23 != null) ? w23.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w10 != null) ? w10.getString4Bracket() : "", 90,
                (w10 != null) ? w10.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 14) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w15 != null) ? w15.getString4Bracket() : "", 90,
                (w15 != null) ? w15.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w18 != null) ? w18.getString4Bracket() : "", 90,
                (w18 != null) ? w18.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 15) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 1, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 1, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 1, y + 2, fontSize,
                (w31 != null) ? w31.getString4Bracket() : "", 90,
                (w31 != null) ? w31.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 6, y + 2, fontSize,
                (w2 != null) ? w2.getString4Bracket() : "", 90,
                (w2 != null) ? w2.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 16) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff, y + boutNumYdiff,
                    boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                    boutNumFontSize, 1, 0, b.getBoutNum(), 90);
        }
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
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2,
                boutLabelBotPad2, boutLabelFontSize, 1, 0, "H", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 1) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round2height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2,
                boutLabelBotPad2, boutLabelFontSize, 1, 0, "I", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 2) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round2height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2,
                boutLabelBotPad2, boutLabelFontSize, 1, 0, "J", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 3) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round2height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2,
                boutLabelBotPad2, boutLabelFontSize, 1, 0, "K", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 4) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round2height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2,
                boutLabelBotPad2, boutLabelFontSize, 1, 0, "L", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 5) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round2height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2,
                boutLabelBotPad2, boutLabelFontSize, 1, 0, "M", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 6) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round2height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2,
                boutLabelBotPad2, boutLabelFontSize, 1, 0, "N", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 7) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round2height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff2,
                y + boutLabelYdiff2, boutLabelRadius, boutLabelTopPad2,
                boutLabelBotPad2, boutLabelFontSize, 1, 0, "O", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 8) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round2height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

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
        BracketSheetUtil.drawFishTailUp(cb, x, y, round4length, round4height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff4,
                y + boutLabelYdiff4, boutLabelRadius, boutLabelTopPad4, boutLabelBotPad4,
                boutLabelFontSize, 1, 0, "D", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_4, 1) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                        y + boutNumYdiff4, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round4height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (2 * round4height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round4length, round4height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff4,
                y + boutLabelYdiff4, boutLabelRadius, boutLabelTopPad4, boutLabelBotPad4,
                boutLabelFontSize, 1, 0, "E", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_4, 2) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                        y + boutNumYdiff4, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round4height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (2 * round4height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round4length, round4height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff4,
                y + boutLabelYdiff4, boutLabelRadius, boutLabelTopPad4, boutLabelBotPad4,
                boutLabelFontSize, 1, 0, "F", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_4, 3) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                        y + boutNumYdiff4, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round4height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (2 * round4height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round4length, round4height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff4,
                y + boutLabelYdiff4, boutLabelRadius, boutLabelTopPad4, boutLabelBotPad4,
                boutLabelFontSize, 1, 0, "G", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_4, 4) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                        y + boutNumYdiff4, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round4height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

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
        BracketSheetUtil.drawFishTailUp(cb, x, y, round6length, round6height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff6,
                y + boutLabelYdiff6, boutLabelRadius, boutLabelTopPad6, boutLabelBotPad6,
                boutLabelFontSize, 1, 0, "B", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_6, 1) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff6,
                        y + boutNumYdiff6, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round6height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (2 * round6height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round6length, round6height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff6,
                y + boutLabelYdiff6, boutLabelRadius, boutLabelTopPad6, boutLabelBotPad6,
                boutLabelFontSize, 1, 0, "C", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_6, 2) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff6,
                        y + boutNumYdiff6, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round6height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }

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
        b = (group != null) ? group.getBout(Bout.ROUND_8, 1) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff8, y + boutNumYdiff8,
                        boutNumWidth, boutNumHeight, boutNumTopPad, boutNumBotPad,
                        boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 3, y + 8, fontSize,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round8height + 10,
                        y + 8, fontSize, wa.getLastName(), 90, wa.isScratched());
            }
        }
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
        if (b != null) {
            wa = b.getWinner();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 18, y + 8, fontSize,
                        wa.getLastName(), 90);
            }
        }

        //y -= 50;

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
        b = (group != null) ? group.getBout(Bout.ROUND_2, 9) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + round2height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 10) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + round2height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 11) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + round2height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 12) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + round2height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 13) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + round2height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 14) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + round2height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 15) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + round2height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }
        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 16) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff2,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + round2height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        // Round 3
        float consRound3height = (round2height / 5) * 4;  // 80%
        float boutNumXdiff3 = (consRound3height / 4);
        fontsize = 8;
        x = consXStart + (round2height / 2);
        y = consYStart - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound3height + 5,
                y + round2length + 3, fontsize - 1, "Loser O", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_3, 1) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound3height + 6, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + round2height + (round2height / 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 1, y + round2length + 3,
                fontsize - 1, "Loser N", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_3, 2) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound3height + 6, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + (round2height * 2) + ((round2height / 5) * 4);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound3height + 5,
                y + round2length + 3, fontsize - 1, "Loser M", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_3, 3) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound3height + 6, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + round2height + (round2height / 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 1, y + round2length + 3,
                fontsize - 1, "Loser L", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_3, 4) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound3height + 6, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + (round2height * 2) + ((round2height / 5) * 4);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound3height + 5,
                y + round2length + 3, fontsize - 1, "Loser K", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_3, 5) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound3height + 6, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + round2height + (round2height / 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 1, y + round2length + 3,
                fontsize - 1, "Loser J", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_3, 6) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound3height + 6, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + (round2height * 2) + ((round2height / 5) * 4);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound3height + 5,
                y + round2length + 3, fontsize - 1, "Loser I", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_3, 7) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound3height + 6, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + round2height + (round2height / 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 1, y + round2length + 3,
                fontsize - 1, "Loser H", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_3, 8) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff3,
                        y + boutNumYdiff2, boutNumWidth2, boutNumHeight2, boutNumTopPad2,
                        boutNumBotPad2, boutNumFontSize2, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound3height + 6, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        // Round 4
        float consRound4height = (consRound3height * 2) - ((round2height / 5) * 2); // minus 40%
        boutNumXdiff4 = (consRound4height / 4);
        x = consXStart + (round2height / 2) + (consRound3height / 2);
        y = consYStart - round2length - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound4height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_4, 5) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                        y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound4height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + (consRound3height * 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound4height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_4, 6) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                        y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound4height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + (consRound3height * 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound4height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_4, 7) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                        y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound4height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + (consRound3height * 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound4height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_4, 8) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff4,
                        y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound4height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        // Round 5
        float consRound5height = (consRound4height * 2) - ((consRound4height / 5) * 3); // minus 60%
        float boutNumXdiff5 = (consRound5height / 3);
        x = consXStart + (round2height / 2) + (consRound3height / 2) + (consRound4height / 2);
        y = consYStart - round2length - round2length - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound5height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound5height + 3,
                y + round2length + 3, fontsize - 1, "Loser E", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_5, 1) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff5,
                        y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound5height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + consRound5height + ((consRound5height / 5) * 2);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound5height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize - 1, "Loser D", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_5, 2) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff5,
                        y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound5height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + (consRound5height * 3) + ((consRound5height / 6) * 2);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound5height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound5height + 3,
                y + round2length + 3, fontsize - 1, "Loser G", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_5, 3) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff5,
                        y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound5height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + consRound5height + ((consRound5height / 5) * 2);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound5height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize - 1, "Loser F", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_5, 4) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff5,
                        y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound5height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        // Round 6
        float consRound6height = (consRound5height * 2) - ((consRound5height / 5) * 3); // 60%
        boutNumXdiff6 = (consRound6height / 3);
        boutNumYdiff6 = round2height / 3;
        x = consXStart + (round2height / 2) + (consRound3height / 2) + (consRound4height / 2) + (consRound5height / 2);
        y = y - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound6height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_6, 3) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff6,
                        y + boutNumYdiff6, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound6height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + (consRound6height * 2) + ((consRound6height / 5) * 7);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound6height, 1, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_6, 4) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff6,
                        y + boutNumYdiff6, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound6height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        // Round 7
        float consRound7height = (consRound6height * 2) - ((consRound6height / 5) * 3); // 60%
        float boutNumXdiff7 = (consRound7height / 3) * 2;
        float boutLabelXdiff7 = (consRound7height / 3);
        x = consXStart + (round2height / 2) + (consRound3height / 2) + (consRound4height / 2) + (consRound5height / 2) + (consRound6height / 2);
        y = y - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound7height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + consRound7height + 3,
                y + round2length + 3, fontsize - 1, "Loser C", 90);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff7,
                y + boutNumYdiff2 + 10, 10, 5, 2, 12, 1, 0, "X", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_7, 1) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff7,
                        y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound7height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        x = x + consRound7height + ((consRound7height / 5) * 2);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound7height, 1, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize - 1, "Loser B", 90);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff7,
                y + boutNumYdiff2 + 10, 10, 5, 2, 12, 1, 0, "Y", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_7, 2) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff7,
                        y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound7height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }

        // Round 8
        float consRound8height = (consRound7height * 2) - ((consRound7height / 5) * 3); // 60%
        boutNumXdiff8 = (consRound8height / 3) * 2;
        float boutLabelXdiff8 = (consRound8height / 3);
        x = consXStart + (round2height / 2) + (consRound3height / 2) + (consRound4height / 2) + (consRound5height / 2) + (consRound6height / 2) + (consRound7height / 2);
        y = y - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound8height, 1, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + boutLabelXdiff8,
                y + boutNumYdiff2 + 10, 10, 5, 2, 12, 1, 0, "Z", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_8, 2) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + boutNumXdiff8,
                        y + boutNumYdiff2, boutNumWidth, boutNumHeight, boutNumTopPad,
                        boutNumBotPad, boutNumFontSize, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 1, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound8height + 7, y + round2length - 12, fontSize, wa.getLastName(), 90);
            }
        }
        x = x + (consRound8height / 2);
        y = y - round2length;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 1, 0);
        x += 15; // padding below the line
        mid = y + (finalLength / 2);
        fontsize = 8;
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "3rd Place", 90);
        if (b != null) {
            wa = b.getWinner();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 18, y + finalLength - 12, fontSize, wa.getLastName(), 90);
            }
        }

        // 2nd place challenge
        if (dao.isSecondPlaceChallengeEnabled()) {
            float challengeX = 540;
            float challengeY = 700;
            x = challengeX;
            y = challengeY;
            float height2 = height + 15;
            b = (group != null) ? group.getBout(Bout.ROUND_9, 1) : null;
            if (b != null) {
                if (!b.isBye() && doBoutNumbers) {
                    BracketSheetUtil.drawBoutNum(cb, bf, x + (height2 / 6), y + (length / 8),
                            20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
                }
                wa = b.getRed();
                if (wa != null) {
                    BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_LEFT,
                            x - 3, y + 8, fontSize, wa.getLastName(), 90);
                }

                wa = b.getGreen();
                if (wa != null) {
                    BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_LEFT,
                            x + height2 + 10, y + 8, fontSize, wa.getLastName(), 90);
                }
            }
            BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, height2, 1, 0);
            fontsize = 8;
            BracketSheetUtil.drawString(cb, bf, x, y - 30, fontsize - 1, "Loser A", 90);
            BracketSheetUtil.drawString(cb, bf, x + height2, y - 37, fontsize - 1, "Winner Z", 90);
            x += (height2 / 2);
            y = y + round2length;
            BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 1, 0);
            x += 15; // padding below the line
            mid = y + (finalLength / 2);
            fontsize = 8;
            BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "2nd Challenge", 90);
        }

        // 5th place challenge
        if (dao.isFifthPlaceEnabled()) {
            float fifthX = 540;
            float fifthY = 100;
            float height2 = height + 15;
            x = fifthX;
            y = fifthY;
            float height5th = height2;
            b = (group != null) ? group.getBout(Bout.ROUND_8, 3) : null;
            if (b != null) {
                if (!b.isBye() && doBoutNumbers) {
                    BracketSheetUtil.drawBoutNum(cb, bf, x + (height5th / 6), y + (length / 8),
                            20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
                }
                wa = b.getRed();
                if (wa != null) {
                    BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                            x - 3, y + round2length - 6, fontSize, wa.getLastName(), 90);
                }

                wa = b.getGreen();
                if (wa != null) {
                    BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                            x + height2 + 10, y + round2length - 6, fontSize, wa.getLastName(), 90);
                }
            }
            BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, height2, 1, 0);
            fontsize = 8;
            BracketSheetUtil.drawString(cb, bf, x, y + round2length + 3, fontsize - 1, "Loser X", 90);
            BracketSheetUtil.drawString(cb, bf, x + height2, y + round2length + 3, fontsize - 1, "Loser Y", 90);
            x += (height2 / 2);
            y = y - round2length;
            BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 1, 0);
            if (b != null) {
                wa = b.getWinner();
                if (wa != null) {
                    BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                            x - 3, y + finalLength - 12, fontSize, wa.getLastName(), 90);
                }
            }
            x += 15; // padding below the line
            mid = y + (finalLength / 2);
            fontsize = 8;
            BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "5th Place", 90);
        }

        return true;
    }
}
