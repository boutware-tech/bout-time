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
import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import bouttime.sort.BoutSort;
import bouttime.sort.WrestlerSeedSort;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Class to draw a bracket sheet for round robin groups.
 */
public class RoundRobinBracketSheetReport {
    static Logger logger = Logger.getLogger(RoundRobinBracketSheetReport.class);

    public static Boolean doBlankPage(FileOutputStream fos, Dao dao, Integer numWrestlers) {
        if (!dao.isOpen()) {
            return false;
        }

        // step 1: creation of a document-object
        Document document = new Document();

        try {

            // step 2: creation of the writer
            if (fos == null) {
                return false;
            }
            PdfWriter writer = PdfWriter.getInstance(document, fos);

            // step 3: we open the document
            document.open();

            // step 4: we grab the ContentByte and do some stuff with it
            PdfContentByte cb = writer.getDirectContent();

            BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);

            drawBracket(cb, bf, dao, null, numWrestlers);

        } catch(DocumentException de) {
            logger.error("Document Exception", de);
            return false;
        } catch (IOException ioe) {
            logger.error("IO Exception", ioe);
            return false;
        }

        // step 5: we close the document
        document.close();

        return true;
    }

    public static Boolean doPage(PdfContentByte cb, Dao dao, Group g) {
        if (!dao.isOpen()) {
            return false;
        }

        try {
            BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
            drawBracket(cb, bf, dao, g, 0);
        } catch(DocumentException de) {
            logger.error("Document Exception", de);
            return false;
        } catch (IOException ioe) {
            logger.error("IO Exception", ioe);
            return false;
        }

        return true;
    }

    private static boolean drawBracket(PdfContentByte cb, BaseFont bf, Dao dao,
            Group group, Integer numWrestlers) throws DocumentException, IOException {

        float leftMargin = 50;      // left margin
        float nameLineLength = 350; // length of name line
        float rectWidth = 35;       // width of rectangles
        float rectHeight = 30;      // height of rectangles
        int linePad = 3;            // space between name line and name
        int tablePad = 25;          // space between name line and win/loss/place table
        float nameStart = 620;      // line where names start
        float boutHeaderPad = 5;    // space between bout line header and bout line
        float boutPad = 0;          // space between bout lines
        float boutNamePad = 0;      // space between name lines and bout lines
        String name;
        Bout bout;
        Integer numBouts = Integer.valueOf(0);
        Wrestler red, green;
        List<Wrestler> wList = null;
        List<Bout> bList = null;

        if (group != null) {
            wList = new ArrayList<Wrestler>(group.getWrestlers());
            Collections.sort(wList, new WrestlerSeedSort());

            bList = group.getBouts();
            Collections.sort(bList, new BoutSort());

            numWrestlers = group.getNumWrestlers();
            numBouts = group.getNumBouts();
        }

        switch (numWrestlers) {
            case 2 :
                boutPad = 160;
                boutNamePad = 70;
                if (group == null) {
                    numBouts = Integer.valueOf(3);
                }
                break;
            case 3 :
                boutPad = 160;
                boutNamePad = 40;
                if (group == null) {
                    numBouts = Integer.valueOf(3);
                }
                break;
            case 4 :
                boutPad = 120;
                boutNamePad = 40;
                if (group == null) {
                    numBouts = Integer.valueOf(6);
                }
                break;
            case 5 :
                boutPad = 55;
                boutNamePad = 10;
                if (group == null) {
                    numBouts = Integer.valueOf(10);
                }
                break;
            default :
                // Error -- invalid number of wrestlers
                return false;
        }

        RoundRobinBracketSheetUtil.drawHeader(cb, bf, leftMargin, dao, group, true);

        RoundRobinBracketSheetUtil.drawWrestlerNameHeader(cb, bf,
                leftMargin + nameLineLength + tablePad, nameStart + rectHeight + 2, rectWidth);
        
        // Draw wrestler name lines
        boolean groupDone = isGroupDone(group);
        float names = nameStart;
        boolean strikethrough = false;
        for (int i = 0; i < numWrestlers; i++) {
            float fontSize = 12;
            int win = -1;
            int loss = -1;
            Integer place = null;
            
            if (group != null) {
                Wrestler w = wList.get(i);
                name = w.getString4Bracket();
                strikethrough = w.isScratched();
                place = w.getPlace();
                if (groupDone) {
                    win = getWins(group, w);
                    loss = getLosses(group, w);
                }
            } else {
                name = String.format("%d)", i + 1);
                strikethrough = false;
            }
            
            RoundRobinBracketSheetUtil.drawWrestlerNameLine(cb, bf, leftMargin, names,
                    nameLineLength, tablePad, rectWidth, rectHeight, win, loss, place);
            
            BracketSheetUtil.drawString(cb, bf, leftMargin, names + linePad,
                    fontSize, name, strikethrough);

            names -= rectHeight;
        }

        // Draw bout header
        float boutStart = names - boutNamePad;
        RoundRobinBracketSheetUtil.drawBoutHeader(cb, bf, leftMargin, boutStart);

        // Draw bouts
        boutStart = boutStart - boutHeaderPad - RoundRobinBracketSheetUtil.boutRectHeight;
        for (int i = 0, round = 0; i < numBouts; i++) {
            if (group == null) {
                if (numWrestlers >= 4) {
                    if ((i % 2) == 0) {
                        round++;
                    }
                } else {
                    round++;
                }
                RoundRobinBracketSheetUtil.drawBout(cb, bf, leftMargin, boutStart, null,
                        false, null, false, null, Integer.toString(round), 0);
            } else {
                bout = bList.get(i);
                red = bout.getRed();
                String roundStr = bout.getRound();
                String redStr = String.format("%s. %s", red.getFirstName().substring(0, 1),
                        red.getLastName());
                green = bout.getGreen();
                String greenStr = String.format("%s. %s", green.getFirstName().substring(0, 1),
                        green.getLastName());
                String boutNum = bout.getBoutNum();
                RoundRobinBracketSheetUtil.drawBout(cb, bf, leftMargin, boutStart,
                        redStr, red.isScratched(), greenStr, green.isScratched(),
                        (boutNum != null) ? boutNum : "", roundStr, getWinnerInt(bout));
            }

            // Adjust line spacing for the next bout.
            // For groups with 4 and 5 wrestlers, there are 2 bouts per round.
            // So put the bouts in the same round right next to each other.
            if ((numWrestlers >= 4) && ((i % 2) == 0)) {
                 boutStart = boutStart - RoundRobinBracketSheetUtil.boutRectHeight;
            } else {
                boutStart = boutStart - boutPad;
            }
        }

        return true;
    }
    
    private static int getWinnerInt(Bout b) {
        int rv = 0;
        
        if (b.getWinner() != null) {
            if (b.getWinner().equals(b.getRed())) {
                rv = 1;
            } else if (b.getWinner().equals(b.getGreen())) {
                rv = 2;
            }
        }
        
        return rv;
    }

    private static boolean isGroupDone(Group group) {
        if (group == null) {
            return false;
        }
        
        for (Bout b : group.getBouts()) {
            Wrestler winner = b.getWinner();
            if ((winner == null) || !(winner.equals(b.getRed()) || winner.equals(b.getGreen()))) {
                return false;
            }
        }
        
        return true;
    }
    
    private static int getWins(Group g, Wrestler w) {
        int count = 0;
        for (Bout b : g.getBouts()) {
            if (w.equals(b.getWinner())) {
                count += 1;
            }
        }
        
        return count;
    }
    
    private static int getLosses(Group g, Wrestler w) {
        int count = 0;
        for (Bout b : g.getBouts()) {
            if (w.equals(b.getLoser())) {
                count += 1;
            }
        }
        
        return count;
    }
}
