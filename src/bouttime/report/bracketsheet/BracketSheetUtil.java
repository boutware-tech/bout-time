/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2011  Jeffrey K. Rutt
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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import java.io.IOException;

/**
 * Class of general utility methods for drawing a Bracket Sheet via iText.
 */
public class BracketSheetUtil {

    public static void drawString(PdfContentByte cb, BaseFont bf, float x, float y,
            float fontSize, String string) throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        cb.setFontAndSize(bf, fontSize);

        cb.beginText();
        cb.setTextMatrix(x, y);
        cb.showText(string);
        cb.endText();
        cb.stroke();
    }
    
    public static void drawString(PdfContentByte cb, BaseFont bf, float x, float y,
            float fontSize, String string, boolean strikethrough) throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }
        
        drawString(cb, bf, x, y, fontSize, string);
        if (strikethrough) {
            float halfHeight = (bf.getAscentPoint(string, fontSize)) / 2;
            float width = bf.getWidthPointKerned(string, fontSize);
            drawHorizontalLine(cb, x, y + halfHeight, width, 1.5f, 0);
        }
    }

    public static void drawString(PdfContentByte cb, BaseFont bf, float mid, float y,
            float fontSize, String string, float rotation) throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        cb.setFontAndSize(bf, fontSize);

        cb.beginText();
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, string, mid, y, rotation);
        cb.endText();
        cb.stroke();
    }

    public static void drawString(PdfContentByte cb, BaseFont bf, float mid, float y,
            float fontSize, String string, float rotation, boolean strikethrough)
            throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        drawString(cb, bf, mid, y, fontSize, string, rotation);
        if (strikethrough) {
            float halfHeight = (bf.getAscentPoint(string, fontSize)) / 2;
            float width = bf.getWidthPointKerned(string, fontSize);
            if (rotation == 0) {
                drawHorizontalLine(cb, mid, y + halfHeight, width, 1.5f, 0);
            } else if (rotation == 90) {
                drawVerticalLine(cb, mid - halfHeight, y, width, 1.5f, 0);
            }
        }
    }

    public static void drawStringAligned(PdfContentByte cb, BaseFont bf, int alignment,
            float mid, float y, float fontSize, String string, float rotation) throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        cb.setFontAndSize(bf, fontSize);

        cb.beginText();
        cb.showTextAligned(alignment, string, mid, y, rotation);
        cb.endText();
        cb.stroke();
    }

    public static void drawStringCentered(PdfContentByte cb, BaseFont bf, float mid, float y,
            float fontSize, String string, float rotation) throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        cb.setFontAndSize(bf, fontSize);

        cb.beginText();
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, (string != null) ? string : "",
                mid, y, rotation);
        cb.endText();
        cb.stroke();
    }

    public static void drawHorizontalLine(PdfContentByte cb, float x, float y, float length,
            float lineWidth, float grayStroke) {
        setLineWidthGrayStroke(cb, lineWidth, grayStroke);
        cb.moveTo(x, y);
        cb.lineTo(x + length, y);
        cb.stroke();
    }

    public static void drawVerticalLine(PdfContentByte cb, float x, float y, float length,
            float lineWidth, float grayStroke) {
        setLineWidthGrayStroke(cb, lineWidth, grayStroke);
        cb.moveTo(x, y);
        cb.lineTo(x, y + length);
        cb.stroke();
    }

    public static void drawRectangle(PdfContentByte cb, float x, float y, float width,
            float height, float lineWidth, float grayStroke) {
        setLineWidthGrayStroke(cb, lineWidth, grayStroke);
        cb.rectangle(x, y, width, height);
        cb.stroke();
    }

    public static void drawCircle(PdfContentByte cb, float x, float y, float r,
            float lineWidth, float grayStroke) {
        setLineWidthGrayStroke(cb, lineWidth, grayStroke);
        cb.circle(x, y, r);
        cb.stroke();
    }

    public static void drawRectangle(PdfContentByte cb, Rectangle rect, float lineWidth,
            float grayStroke) {
        setLineWidthGrayStroke(cb, lineWidth, grayStroke);
        cb.rectangle(rect);
        cb.stroke();
    }
    
    public static void setLineWidth(PdfContentByte cb, float lineWidth) {
        cb.setLineWidth(lineWidth);     // 1.0 default
    }
    
    public static void setGrayStroke(PdfContentByte cb, float grayStroke) {
        cb.setGrayStroke(grayStroke);   // 0 = black, 1 = white
    }

    public static void setLineWidthGrayStroke(PdfContentByte cb, float lineWidth,
            float grayStroke) {

        cb.setLineWidth(lineWidth);     // 1.0 default
        cb.setGrayStroke(grayStroke);   // 0 = black, 1 = white
    }

    public static void drawFishTailRight(PdfContentByte cb, float x, float y,
            float length, float height, float lineWidth, float grayStroke) {

        setLineWidthGrayStroke(cb, lineWidth, grayStroke);

        // draw the top line
        drawHorizontalLine(cb, x, y, length, lineWidth, grayStroke);

        // draw the bottom line
        drawHorizontalLine(cb, x, y - height, length, lineWidth, grayStroke);

        drawVerticalLine(cb, x + length, y - height, height, lineWidth, grayStroke);
    }

    public static void drawFishTailUp(PdfContentByte cb, float x, float y,
            float length, float height, float lineWidth, float grayStroke) {

        setLineWidthGrayStroke(cb, lineWidth, grayStroke);

        // draw the left line
        drawVerticalLine(cb, x, y, length, lineWidth, grayStroke);

        // draw the right line
        drawVerticalLine(cb, x + height, y, length, lineWidth, grayStroke);

        drawHorizontalLine(cb, x, y + length, height, lineWidth, grayStroke);
    }
    
    public static void drawFishTailLeft(PdfContentByte cb, float x, float y,
            float length, float height, float lineWidth, float grayStroke) {

        setLineWidthGrayStroke(cb, lineWidth, grayStroke);

        // draw the top line
        drawHorizontalLine(cb, x, y, length, lineWidth, grayStroke);

        // draw the bottom line
        drawHorizontalLine(cb, x, y - height, length, lineWidth, grayStroke);

        drawVerticalLine(cb, x, y - height, height, lineWidth, grayStroke);
    }

    public static void drawFishTailDown(PdfContentByte cb, float x, float y,
            float length, float height, float lineWidth, float grayStroke) {

        setLineWidthGrayStroke(cb, lineWidth, grayStroke);

        // draw the left line
        drawVerticalLine(cb, x, y, length, lineWidth, grayStroke);

        // draw the right line
        drawVerticalLine(cb, x + height, y, length, lineWidth, grayStroke);

        drawHorizontalLine(cb, x, y, height, lineWidth, grayStroke);
    }

    public static void drawTournamentHeader(PdfContentByte cb, BaseFont bf, float x,
            float y, Dao dao, float rotation) throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        float linePad = 15;

        String name = dao.getName();
        if (name != null) {
            drawString(cb, bf, x, y, 12, name, rotation);
            if (rotation == 0) {
                y -= linePad;
            } else {
                x += linePad;
            }
        }

        String site = dao.getSite();
        if (site != null) {
            drawString(cb, bf, x, y, 12, site, rotation);
            if (rotation == 0) {
                y -= linePad;
            } else {
                x += linePad;
            }
        }

        String city = dao.getCity();
        String state = dao.getState();
        if ((city != null) || (state != null)) {
            String cityState = "";
            if (city != null) {
                cityState = city;
                if (state != null) {
                    cityState += ", ";
                }
            }
            if (state != null) {
                cityState += state;
            }
            
            drawString(cb, bf, x, y, 12, cityState, rotation);
            if (rotation == 0) {
                y -= linePad;
            } else {
                x += linePad;
            }
        }

        String month = dao.getMonth();
        Integer day = dao.getDay();
        Integer year = dao.getYear();
        if ((month != null) || (year != null)) {
            String date = "";
            if (month != null) {
                date = month;
                if (day != null) {
                    date += " " + day;
                }

                if (year != null) {
                    date += ", ";
                }
            }

            if (year != null) {
                date += year;
            }

            drawString(cb, bf, x, y, 12, date, rotation);
        }
    }

    public static void drawBoutLabel(PdfContentByte cb, BaseFont bf, float x,
            float y, float r, float topPad, float botPad, int fontsize,
            float lineWidth, float grayStroke, String boutLabel, float rotation)
            throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        drawCircle(cb, x, y, r, lineWidth, grayStroke);
        if (rotation == 0) {
            float mid = x + (r / 2);
            drawStringCentered(cb, bf, mid, y + r + topPad, fontsize, boutLabel, rotation);
        } else if (rotation == 90) {
            drawStringCentered(cb, bf, x + topPad, y, fontsize, boutLabel, rotation);
        }
    }

    public static void drawBoutNum(PdfContentByte cb, BaseFont bf, float x,
            float y, float width, float height, float topPad, float botPad, int fontsize,
            float lineWidth, float grayStroke, String boutNum, float rotation)
            throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        drawRectangle(cb, x, y, width, height, lineWidth, grayStroke);
        if (rotation == 0) {
            float mid = x + (width / 2);
            drawStringCentered(cb, bf, mid, y + botPad, fontsize, boutNum, rotation);
        } else if (rotation == 90) {
            float mid = y + (height / 2);
            drawStringCentered(cb, bf, x + width - botPad, mid, fontsize, boutNum, rotation);
        }
    }

    public static void drawMatBox(PdfContentByte cb, BaseFont bf, float x,
            float y, float width, float height, float topPad, float botPad,
            float lineWidth, float grayStroke, String mat, float rotation)
            throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        drawRectangle(cb, x, y, width, height, lineWidth, grayStroke);

        if (rotation == 0) {
            float mid = x + (width / 2);
            drawStringCentered(cb, bf, mid, y + height - topPad, 12, "Mat", rotation);
            drawStringCentered(cb, bf, mid, y + botPad, 16, mat, rotation);
        } else if (rotation == 90) {
            float mid = y + (height / 2);
            drawStringCentered(cb, bf, x + topPad, mid, 12, "Mat", rotation);
            drawStringCentered(cb, bf, x + width - botPad, mid, 16, mat, rotation);
        }
    }

    public static void drawTimestamp(PdfContentByte cb, BaseFont bf, float x,
            float y, int fontsize, String timestamp, float rotation)
            throws DocumentException, IOException {

        if (bf == null) {
            bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
        }

        drawString(cb, bf, x, y, fontsize, timestamp, rotation);
    }
}
