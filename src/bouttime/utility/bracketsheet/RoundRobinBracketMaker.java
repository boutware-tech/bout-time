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

package bouttime.utility.bracketsheet;

import bouttime.report.bracketsheet.BracketSheetUtil;
import bouttime.report.bracketsheet.RoundRobinBracketSheetUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFrame;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

/**
 * A helper class/application to use when building and testing
 * round robin bracket sheets
 */
public class RoundRobinBracketMaker {

    /**
     * @param args the command line arguments
     * arg0 String filename for output file
     * arg1 int number of wrestlers
     * arg2 boolean value "true" will render/show PDF file in viewer, default is false
     *
     * For example :
     *     RoundRobinBracketMaker /tmp/test.pdf 3 true
     */
    public static void main(String[] args) {
        // step 1: creation of a document-object
        Document document = new Document();

        try {

            // step 2: creation of the writer
            PdfWriter writer;
            int numWrestlers;
            if (args.length >= 2) {
                writer = PdfWriter.getInstance(document,
                        new FileOutputStream(args[0]));
                numWrestlers = Integer.parseInt(args[1]);
            } else {
                System.err.println("ERROR : Must specify output file and number of wrestlers.");
                return;
            }

            // step 3: we open the document
            document.open();

            // step 4: we grab the ContentByte and do some stuff with it
            PdfContentByte cb = writer.getDirectContent();

            BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);

            drawBracket(cb, bf, numWrestlers);
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

        if ((args.length == 3) && (Boolean.parseBoolean(args[2]))) {
            showPDF(args[0]);
        }
    }

    public static void showPDF(String filename) {
        SwingController controller = new SwingController();
        SwingViewBuilder factory = new SwingViewBuilder(controller);
        JFrame frame = factory.buildViewerFrame();
        controller.openDocument(filename);
        frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        frame.addWindowListener(new WindowListener() {
            public void windowClosed(WindowEvent arg0) {}
            public void windowActivated(WindowEvent arg0) {}
            public void windowClosing(WindowEvent arg0) {}
            public void windowDeactivated(WindowEvent arg0) { System.exit(0); }
            public void windowDeiconified(WindowEvent arg0) {}
            public void windowIconified(WindowEvent arg0) {}
            public void windowOpened(WindowEvent arg0) {}
        });

        frame.pack();
        frame.setVisible(true);
    }

    private static void drawBracket(PdfContentByte cb, BaseFont bf,
            int numWrestlers) throws DocumentException, IOException {

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
        int numBouts = 0;

        switch (numWrestlers) {
            case 2 :
                boutPad = 160;
                boutNamePad = 70;
                numBouts = 3;
                break;
            case 3 :
                boutPad = 160;
                boutNamePad = 40;
                numBouts = 3;
                break;
            case 4 :
                boutPad = 120;
                boutNamePad = 40;
                numBouts = 6;
                break;
            case 5 :
                boutPad = 55;
                boutNamePad = 10;
                numBouts = 10;
                break;
            default :
                // Error -- invalid number of wrestlers
                return;
        }

        RoundRobinBracketSheetUtil.drawHeader(cb, bf, leftMargin, null, null, true);

        RoundRobinBracketSheetUtil.drawWrestlerNameHeader(cb, bf,
                leftMargin + nameLineLength + tablePad, nameStart + rectHeight + 2, rectWidth);

        // Draw wrestler name lines
        float names = nameStart;
        for (int i = 0; i < numWrestlers; i++) {
            float fontSize = 12;
            RoundRobinBracketSheetUtil.drawWrestlerNameLine(cb, bf, leftMargin, names,
                    nameLineLength, tablePad, rectWidth, rectHeight, -1, -1, 0);
            
            name = String.format("%d)", i + 1);

            BracketSheetUtil.drawString(cb, bf, leftMargin, names + linePad, fontSize, name);

            names -= rectHeight;
        }

        // Draw bout header
        float boutStart = names - boutNamePad;
        RoundRobinBracketSheetUtil.drawBoutHeader(cb, bf, leftMargin, boutStart);

        // Draw bouts
        boutStart = boutStart - boutHeaderPad - RoundRobinBracketSheetUtil.boutRectHeight;
        for (int i = 0, round = 0; i < numBouts; i++) {
            if (numWrestlers >= 4) {
                if ((i % 2) == 0) {
                    round++;
                }
            } else {
                round++;
            }
            RoundRobinBracketSheetUtil.drawBout(cb, bf, leftMargin, boutStart, null,
                    false, null, false, null, Integer.toString(round), 0);

            // Adjust line spacing for the next bout.
            // For groups with 4 and 5 wrestlers, there are 2 bouts per round.
            // So put the bouts in the same round right next to each other.
            if ((numWrestlers >= 4) && ((i % 2) == 0)) {
                 boutStart = boutStart - RoundRobinBracketSheetUtil.boutRectHeight;
            } else {
                boutStart = boutStart - boutPad;
            }
        }

        return;
    }

}
