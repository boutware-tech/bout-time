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

package bouttime.fileinput;

import bouttime.dao.Dao;
import bouttime.dao.xml.XmlDao;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for bouttime.fileinput.ExcelFileInput class.
 */
public class ExcelFileInputTest {

    private static File inputFile = null;
    private static final String INPUT_FILENAME = "testInput.xlsx";
    private static final String DAO_FILENAME = "testFileInput.xml";
    private Dao dao = null;

    public ExcelFileInputTest() { }

    private static void makeWrestler(Sheet s, int rownum, String fn, String ln,
            String c, String d, String wc, String aw, String tn, String l, String sn, String gn) {

        Row r = s.createRow(rownum);

        if (fn != null) { r.createCell(0).setCellValue(fn); }
        if (ln != null) { r.createCell(1).setCellValue(ln); }
        if (c != null) { r.createCell(2).setCellValue(c); }
        if (d != null) { r.createCell(3).setCellValue(d); }
        if (wc != null) { r.createCell(4).setCellValue(wc); }
        if (l != null) { r.createCell(5).setCellValue(l); }
        if (tn != null) { r.createCell(6).setCellValue(tn); }
        if (aw != null) { r.createCell(7).setCellValue(aw); }
        if (sn != null) { r.createCell(8).setCellValue(sn); }
        if (gn != null) { r.createCell(9).setCellValue(gn); }
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Create the input file
        inputFile = new File(INPUT_FILENAME);
        assertNotNull(inputFile);
        assertTrue(inputFile.createNewFile());
        Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet s = wb.createSheet();

        makeWrestler(s,0,"David","Robinson","Open","5","88","88","Spurs","A","50","SW");
        makeWrestler(s,1,"Tim","Duncan","Open","4","99","99","Spurs","A","21","SW");
        makeWrestler(s,2,"Tony","Parker","Rookie","1","77","77","Spurs","B","6","SW");
        makeWrestler(s,3,"Manu","Ginobili","Rookie","3","82","80","Spurs","B","12","SW");
        makeWrestler(s,4,"David","Robinson","Open","5","88","88","Spurs","A","50","SW");
        
        FileOutputStream outputStream = new FileOutputStream(inputFile);
        wb.write(outputStream);
        outputStream.close();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        // Delete the input file
        if (inputFile != null) {
            inputFile.delete();
        }
    }

    @Before
    public void setUp() {
        dao = new XmlDao();
        assertTrue(dao.openNew(DAO_FILENAME));
    }

    @After
    public void tearDown() {
        if (dao != null) {
            dao.close();
            new File(DAO_FILENAME).delete();
            dao =  null;
        }
    }

    /**
     * Test of getInputFromFile method, of class ExcelFileInput.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetInputFromFile_Map_Dao() {
        Map config = new HashMap();
        config.put("sheet", "1");
        config.put("startRow", "1");
        config.put("endRow", "5");
        config.put("firstName", "1");
        config.put("lastName", "2");
        config.put("teamName", "7");
        config.put("classification", "3");
        config.put("division", "4");
        config.put("weightClass", "5");
        config.put("actualWeight", "8");
        config.put("level", "6");
        config.put("serialNumber", "9");
        config.put("geo", "10");
        config.put("fileName", INPUT_FILENAME);

        ExcelFileInput instance = new ExcelFileInput();
        FileInputResult result = instance.getInputFromFile(config, dao);
        assertNotNull(result);
        assertEquals(5, result.getRecordsProcessed().intValue());
        assertEquals(4, result.getRecordsAccepted().intValue());
        assertEquals(1, result.getRecordsRejected().intValue());
        if (result.getRejects() != null) {
            assertEquals(1, result.getRejects().size());
        }
        assertEquals(4, dao.getAllWrestlers().size());

        config.put("fileName", "BadFilename");
        result = instance.getInputFromFile(config, dao);
        assertNull(result);

        ExcelFileInputConfig fiConfig = dao.getExcelFileInputConfig();
        assertEquals("1", fiConfig.getSheet());
        assertEquals("1", fiConfig.getStartRow());
        assertEquals("5", fiConfig.getEndRow());
        assertEquals("1", fiConfig.getFirstName());
        assertEquals("2", fiConfig.getLastName());
        assertEquals("7", fiConfig.getTeamName());
        assertEquals("3", fiConfig.getClassification());
        assertEquals("4", fiConfig.getDivision());
        assertEquals("5", fiConfig.getWeightClass());
        assertEquals("8", fiConfig.getActualWeight());
        assertEquals("6", fiConfig.getLevel());
        assertEquals("9", fiConfig.getSerialNumber());
        assertEquals("10", fiConfig.getGeo());
    }

}