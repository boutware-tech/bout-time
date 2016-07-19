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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for bouttime.fileinput.XMLFileInput class.
 *
 */
public class XMLFileInputTest {

    private static File inputFile = null;
    private static final String INPUT_FILENAME = "testInput.xml";
    private static final String DAO_FILENAME = "testFileInput.xml";
    private Dao dao = null;

    public XMLFileInputTest() { }

    private static String makeWrestler(String fn, String ln, String c, String d,
            String wc, String aw, String tn, String l, String sn) {
        
        StringBuilder str = new StringBuilder();

        str.append("<w>");
        str.append(String.format("<fn>%s</fn>", fn));
        str.append(String.format("<ln>%s</ln>", ln));
        str.append(String.format("<c>%s</c>", c));
        str.append(String.format("<d>%s</d>", d));
        str.append(String.format("<wc>%s</wc>", wc));
        str.append(String.format("<aw>%s</aw>", aw));
        str.append(String.format("<tn>%s</tn>", tn));
        str.append(String.format("<l>%s</l>", l));
        str.append(String.format("<sn>%s</sn>", sn));
        str.append("</w>");
        
        return str.toString();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Create the input file
        inputFile = new File(INPUT_FILENAME);
        assertNotNull(inputFile);
        assertTrue(inputFile.createNewFile());
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(inputFile));
        bufferedWriter.write("<root>"); bufferedWriter.newLine();
        bufferedWriter.write(makeWrestler("David","Robinson","Open","5","88","","Spurs","A","")); bufferedWriter.newLine();
        bufferedWriter.write(makeWrestler("Tim","Duncan","Open","4","99","","Spurs","A","")); bufferedWriter.newLine();
        bufferedWriter.write(makeWrestler("Tony","Parker","Rookie","1","77","","Spurs","B","")); bufferedWriter.newLine();
        bufferedWriter.write(makeWrestler("Manu","Ginobili","Rookie","3","82","","Spurs","B","")); bufferedWriter.newLine();
        bufferedWriter.write(makeWrestler("David","Robinson","Open","5","88","","Spurs","A","")); bufferedWriter.newLine();
        bufferedWriter.write("</root>");
        bufferedWriter.flush();
        bufferedWriter.close();
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
     * Test of getInputFromFile method, of class XMLFileInput.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetInputFromFile() {
        Map config = new HashMap();
        config.put("root", "root");
        config.put("wrestler", "w");
        config.put("firstName", "fn");
        config.put("lastName", "ln");
        config.put("teamName", "tn");
        config.put("classification", "c");
        config.put("division", "d");
        config.put("weightClass", "wc");
        config.put("actualWeight", "aw");
        config.put("level", "l");
        config.put("serialNumber", "sn");
        config.put("fileName", INPUT_FILENAME);

        XMLFileInput instance = new XMLFileInput();
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

        XMLFileInputConfig fiConfig = dao.getXmlFileInputConfig();
        assertEquals("root", fiConfig.getRoot());
        assertEquals("w", fiConfig.getWrestler());
        assertEquals("fn", fiConfig.getFirstName());
        assertEquals("ln", fiConfig.getLastName());
        assertEquals("tn", fiConfig.getTeamName());
        assertEquals("c", fiConfig.getClassification());
        assertEquals("d", fiConfig.getDivision());
        assertEquals("wc", fiConfig.getWeightClass());
        assertEquals("aw", fiConfig.getActualWeight());
        assertEquals("l", fiConfig.getLevel());
        assertEquals("sn", fiConfig.getSerialNumber());
    }

}