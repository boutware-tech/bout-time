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

package bouttime.report.bracketsheet;

import bouttime.dao.Dao;
import bouttime.model.Group;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 */
public class CommonBracketSheet {
    static Logger logger = Logger.getLogger(CommonBracketSheet.class);

    public Boolean doBlankPage(FileOutputStream fos, Dao dao) {
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

            drawBracket(cb, bf, dao, null, false);

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

    public Boolean doPage(PdfContentByte cb, Dao dao, Group g, boolean doBoutNumbers) {
        if (!dao.isOpen()) {
            return false;
        }

        try {
            BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.EMBEDDED);
            drawBracket(cb, bf, dao, g, doBoutNumbers);
        } catch(DocumentException de) {
            logger.error("Document Exception", de);
            return false;
        } catch (IOException ioe) {
            logger.error("IO Exception", ioe);
            return false;
        }

        return true;
    }

    /**
     * This method is expected to be overridden by an extending class
     * @param cb
     * @param bf
     * @param dao
     * @param group
     * @return
     * @throws com.itextpdf.text.DocumentException
     * @throws java.io.IOException
     */
    protected boolean drawBracket(PdfContentByte cb, BaseFont bf, Dao dao,
            Group group, boolean doBoutNumbers) throws DocumentException, IOException {

        BracketSheetUtil.drawString(cb, bf, 100, 500, 32, "TBD");

        return true;
    }
}
