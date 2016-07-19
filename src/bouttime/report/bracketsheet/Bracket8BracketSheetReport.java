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
 * A class to draw a 8-man bracket in a pdf document.
 */
public class Bracket8BracketSheetReport extends CommonBracketSheet {

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
        }

        float xStart = leftMargin + 30;
        float x = xStart;
        float yStart = 355;
        float y = yStart;
        float length = 200;
        float height = 60;
        BracketSheetUtil.drawString(cb, bf, x - 5, y + 5, 12,
                (w1 != null) ? w1.getString4Bracket() : "", 90,
                (w1 != null) ? w1.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 12, y + 5, 12,
                (w8 != null) ? w8.getString4Bracket() : "", 90,
                (w8 != null) ? w8.isScratched() : false);
        Bout b = (group != null) ? group.getBout(Bout.ROUND_1, 1) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 8,
                    y + (length / 2), 20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 2, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 2, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 5, y + 5, 12,
                (w5 != null) ? w5.getString4Bracket() : "", 90,
                (w5 != null) ? w5.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 12, y + 5, 12,
                (w4 != null) ? w4.getString4Bracket() : "", 90,
                (w4 != null) ? w4.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 2) : null;
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
                (w6 != null) ? w6.getString4Bracket() : "", 90,
                (w6 != null) ? w6.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 3) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 8,
                    y + (length / 2), 20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 2, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 2, 0);

        x = x + (2 * height);
        BracketSheetUtil.drawString(cb, bf, x - 5, y + 5, 12,
                (w7 != null) ? w7.getString4Bracket() : "", 90,
                (w7 != null) ? w7.isScratched() : false);
        BracketSheetUtil.drawString(cb, bf, x + height + 12, y + 5, 12,
                (w2 != null) ? w2.getString4Bracket() : "", 90,
                (w2 != null) ? w2.isScratched() : false);
        b = (group != null) ? group.getBout(Bout.ROUND_1, 4) : null;
        if ((b != null) && !b.isBye() && doBoutNumbers) {
            BracketSheetUtil.drawBoutNum(cb, bf, x + (height / 2) - 8,
                    y + (length / 2), 20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
        }
        BracketSheetUtil.drawFishTailUp(cb, x, y, length, height, 2, 0);
        BracketSheetUtil.drawHorizontalLine(cb, x, y, height, 2, 0);

        // Round 2
        x = xStart + (height / 2);
        y = yStart + length;
        float round2height = height * 2;
        float round2length = (length / 5) * 2;  // 40%
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 2, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (round2height / 2) - 10,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "B", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 1) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2) + 10),
                        y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
            }

            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 7, y + 8, 12,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round2height + 15,
                        y + 8, 12, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, round2height, 2, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (round2height / 2) - 10,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "C", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 2) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2) + 10),
                        y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
            }

            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 7, y + 8, 12,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round2height + 15,
                        y + 8, 12, wa.getLastName(), 90, wa.isScratched());
            }
        }

        // Round 4 - championship bout
        b = (group != null) ? group.getBout(Bout.ROUND_4, 1) : null;
        x = xStart + (height / 2) + (round2height / 2);
        y = yStart + length + round2length;
        float round3height = round2height * 2;
        float round3length = round2length;
        BracketSheetUtil.drawFishTailUp(cb, x, y, round3length, round3height, 2, 0);
        if (b != null) {
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 7, y + 8, 12,
                        wa.getLastName(), 90, wa.isScratched());
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x + round3height + 15,
                        y + 8, 12, wa.getLastName(), 90, wa.isScratched());
            }
        }

        x = x + (round3height / 2);
        y = y + round3length;
        float finalLength = (round3length / 3) * 3;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 2, 0);

        float fontsize = 12;
        float mid = y + (finalLength / 2);
        x += 15; // padding below the line
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "Champion", 90);

        y -= 50;
        BracketSheetUtil.drawBoutLabel(cb, bf, x - 50, y + 10, 10, 5, 2, 12, 1, 0, "A", 90);
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x, y, 20, 20, 0, 6, 12, 1, 0,
                        b.getBoutNum(), 90);
            }
            wa = b.getWinner();
            if (wa != null) {
                BracketSheetUtil.drawString(cb, bf, x - 20, y + 58, 12, wa.getLastName(), 90);
            }
        }

        /////////////////////////////
        // Draw consolation/back side
        /////////////////////////////

        // Round 2
        float consYStart = yStart - round2length;
        float consXStart = xStart + (height / 2);
        x = consXStart;
        y = consYStart;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 2, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 3) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2)),
                        y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 7, y + round2length - 12, 12, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + round2height + 15, y + round2length - 12, 12, wa.getLastName(), 90);
            }
        }

        x = x + (2 * round2height);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, round2height, 2, 0);
        b = (group != null) ? group.getBout(Bout.ROUND_2, 4) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + ((round2height / 2)),
                        y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 7, y + round2length - 12, 12, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + round2height + 15, y + round2length - 12, 12, wa.getLastName(), 90);
            }
        }

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
        b = (group != null) ? group.getBout(Bout.ROUND_3, 1) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + ((consRound3height / 2) + 12),
                        y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 7, y + round2length - 12, 12, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound3height + 15, y + round2length - 12, 12, wa.getLastName(), 90);
            }
        }

        x = x + round2height + (round2height / 5);
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound3height, 2, 0);
        BracketSheetUtil.drawString(cb, bf, x + 3, y + round2length + 3,
                fontsize, "Loser B", 90);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound3height / 2) - 12,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "Y", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_3, 2) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + ((consRound3height / 2) + 12),
                        y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 7, y + round2length - 12, 12, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound3height + 15, y + round2length - 12, 12, wa.getLastName(), 90);
            }
        }

        // Round 4
        float consRound4height = (consRound3height * 2) - ((round2height / 5) * 2); // minus 40%
        x = consXStart + (round2height / 2) + (consRound3height / 2);
        y = consYStart - round2length - round2length;
        BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, consRound4height, 2, 0);
        BracketSheetUtil.drawBoutLabel(cb, bf, x + (consRound4height / 2) - 25,
                y + (round2length / 2), 10, 5, 2, 12, 1, 0, "Z", 90);
        b = (group != null) ? group.getBout(Bout.ROUND_4, 2) : null;
        if (b != null) {
            if (!b.isBye() && doBoutNumbers) {
                BracketSheetUtil.drawBoutNum(cb, bf, x + ((consRound4height / 2) + 25),
                        y + ((round2length / 2)) - 10, 20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
            }
            wa = b.getRed();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 7, y + round2length - 12, 12, wa.getLastName(), 90);
            }

            wa = b.getGreen();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x + consRound4height + 15, y + round2length - 12, 12, wa.getLastName(), 90);
            }
        }

        x = x + (consRound4height / 2);
        y = y - round2length;
        BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 2, 0);
        x += 15; // padding below the line
        mid = y + (finalLength / 2);
        fontsize = 12;
        BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "3rd Place", 90);

        if (b != null) {
            wa = b.getWinner();
            if (wa != null) {
                BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                        x - 20, y + round2length - 12, 12, wa.getLastName(), 90);
            }
        }

        if (dao.isSecondPlaceChallengeEnabled()) {
            float challengeX = 530;
            float challengeY = 620;
            x = challengeX;
            y = challengeY;
            float height2nd = (height / 3) * 2; // 66%
            b = (group != null) ? group.getBout(Bout.ROUND_5, 1) : null;
            if (b != null) {
                if (!b.isBye() && doBoutNumbers) {
                    BracketSheetUtil.drawBoutNum(cb, bf, x + (height2nd / 4), y + (length / 4),
                            20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
                }

                wa = b.getRed();
                if (wa != null) {
                    BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_LEFT,
                            x - 5, y + 8, 12, wa.getLastName(), 90);
                }

                wa = b.getGreen();
                if (wa != null) {
                    BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_LEFT,
                            x + height2nd + 14, y + 8, 12, wa.getLastName(), 90);
                }
            }
            BracketSheetUtil.drawFishTailUp(cb, x, y, round2length, height2nd, 2, 0);
            fontsize = 10;
            BracketSheetUtil.drawString(cb, bf, x, y - 40, fontsize, "Loser A", 90);
            BracketSheetUtil.drawString(cb, bf, x + height2nd, y - 47, fontsize, "Winner Z", 90);
            x += (height2nd / 2);
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

        if (dao.isFifthPlaceEnabled()) {
            if ((group != null) && (group.getNumWrestlers() <= 5)) {
                // do nothing, since no 5th place bout
            } else {
                // We can't be sure that we DON'T need the 5th place bout
                float fifthX = 530;
                float fifthY = 150;
                x = fifthX;
                y = fifthY;
                float height5th = (height / 3) * 2; // 66%
                b = (group != null) ? group.getBout(Bout.ROUND_4, 3) : null;
                if (b != null) {
                    if (!b.isBye() && doBoutNumbers) {
                        BracketSheetUtil.drawBoutNum(cb, bf, x + (height5th / 4), y + (length / 8),
                                20, 20, 0, 6, 12, 1, 0, b.getBoutNum(), 90);
                    }

                    wa = b.getRed();
                    if (wa != null) {
                        BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                                x - 5, y + round2length - 8, 12, wa.getLastName(), 90);
                    }

                    wa = b.getGreen();
                    if (wa != null) {
                        BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                                x + height5th + 14, y + round2length - 8, 12, wa.getLastName(), 90);
                    }
                }
                BracketSheetUtil.drawFishTailDown(cb, x, y, round2length, height5th, 2, 0);
                fontsize = 10;
                BracketSheetUtil.drawString(cb, bf, x, y + round2length + 3, fontsize, "Loser X", 90);
                BracketSheetUtil.drawString(cb, bf, x + height5th, y + round2length + 3, fontsize, "Loser Y", 90);
                x += (height5th / 2);
                y = y - round2length;
                BracketSheetUtil.drawVerticalLine(cb, x, y, finalLength, 2, 0);
                if (b != null) {
                    wa = b.getWinner();
                    if (wa != null) {
                        BracketSheetUtil.drawStringAligned(cb, bf, PdfContentByte.ALIGN_RIGHT,
                                x - 3, y + finalLength - 12, 12, wa.getLastName(), 90);
                    }
                }
                x += 15; // padding below the line
                mid = y + (finalLength / 2);
                fontsize = 10;
                BracketSheetUtil.drawStringCentered(cb, bf, x, mid, fontsize, "5th Place", 90);
            }
        }

        return true;
    }
}
