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
import bouttime.mainview.BoutTimeApp;
import bouttime.model.Wrestler;
import com.thoughtworks.xstream.XStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 * This class retrieves Wrestler records from a XML formatted file.
 */
public class XMLFileInput implements FileInput {
    static Logger logger = Logger.getLogger(XMLFileInput.class);

    public XMLFileInput() {}

    /**
     * Input wrestler entries for an XML file.
     *
     * @param file File to read data from
     * @param config Map of config parameters for XML names
     * @param dao Data access object
     *
     * @return A FileInputResult object with the results of the input operation
     */
    @SuppressWarnings("unchecked")
    private FileInputResult addWrestlersFromFile(File file, Map config, Dao dao) {
        Integer recordsProcessed = Integer.valueOf(0);
        Integer recordsAccepted = Integer.valueOf(0);
        Integer recordsRejected = Integer.valueOf(0);
        List<String> rejects = new ArrayList<String>();

        ObjectInputStream in = null;
        FileReader fr = null;
        try {
            fr = new FileReader(file);
            XStream xs = new XStream();
            setXStreamAliases(xs, config);
            in = xs.createObjectInputStream(fr);

            // Loop will exit when EOF is encountered
            while (true) {
                Wrestler w = (Wrestler)in.readObject();
                recordsProcessed++;
                if (dao.addWrestler(w)) {
                    recordsAccepted++;
                } else {
                    recordsRejected++;
                    rejects.add(String.format("%s %s", w.getFirstName(),
                            w.getLastName()));
                    logger.warn("Duplicate : " + w.getFirstName() + " " +
                            w.getLastName());
                }
            }
        } catch (EOFException eof) {
            // Expected -- end of data to read.
        } catch (Exception e) {
            JFrame mainFrame = BoutTimeApp.getApplication().getMainFrame();
            JOptionPane.showMessageDialog(mainFrame, "Error while handling the XML file.\n\n" + e,
                    "XML File Input Error", JOptionPane.ERROR_MESSAGE);
            logger.error(e.getLocalizedMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    logger.error(ex.getLocalizedMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException ex) {
                    logger.error(ex.getLocalizedMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
                }
            }
        }

        return(new FileInputResult(recordsProcessed, recordsAccepted,
                recordsRejected, rejects));
    }

    /**
     * Get the input from the file based on the values in the 'config' parameter.
     * @param config Map of configuration values.
     * @param dao The data access object to use.
     * @return FileInputResult of the operation.
     */
    @SuppressWarnings("unchecked")
    public FileInputResult getInputFromFile(Map config, Dao dao) {
        String fileName = (String)config.get("fileName");
        File file = new File(fileName);
        if (!file.exists()) {
            String msg = "file does not exist (" + fileName + ")";
            logger.error("XMLFileInput: " + msg);
            config.put("error", msg);
            return null;
        }

        logger.info("Getting input from file [" + fileName + "]");
        FileInputResult result = addWrestlersFromFile(file, config, dao);
        updateFileInputConfig(config, dao);
        dao.flush();

        return result;
    }

    /**
     * Update the file input configuration stored in the Dao.
     * @param config Map of configuration values.
     * @param dao The data access object to use.
     */
    private void updateFileInputConfig(Map config, Dao dao) {
        XMLFileInputConfig fiConfig = dao.getXmlFileInputConfig();

        fiConfig.setActualWeight((String)config.get("actualWeight"));
        fiConfig.setClassification((String)config.get("classification"));
        fiConfig.setDivision((String)config.get("division"));
        fiConfig.setFirstName((String)config.get("firstName"));
        fiConfig.setLastName((String)config.get("lastName"));
        fiConfig.setLevel((String)config.get("level"));
        fiConfig.setTeamName((String)config.get("teamName"));
        fiConfig.setSerialNumber((String)config.get("serialNumber"));
        fiConfig.setWeightClass((String)config.get("weightClass"));
        fiConfig.setRoot((String)config.get("root"));
        fiConfig.setWrestler((String)config.get("wrestler"));
    }

    /**
     * Set the aliases for the XStream object.
     * @param xs The XStream object to set the aliases on.
     * @param config Map of config parameters
     */
    private void setXStreamAliases(XStream xs, Map config) {
        xs.alias((String)config.get("root"), ArrayList.class);
        xs.alias((String)config.get("wrestler"), bouttime.model.Wrestler.class);

        String property;
        StringBuilder sb = new StringBuilder();

        sb.append("XML File Input Configuration :");

        property = (String)config.get("firstName");
        sb.append("\n    first=" + property);
        if (!property.isEmpty()) {
            xs.aliasField(property, bouttime.model.Wrestler.class, "firstName");
        }

        property = (String)config.get("lastName");
        sb.append("\n    last=" + property);
        if (!property.isEmpty()) {
            xs.aliasField(property, bouttime.model.Wrestler.class, "lastName");
        }

        property = (String)config.get("teamName");
        sb.append("\n    team=" + property);
        if (!property.isEmpty()) {
            xs.aliasField(property, bouttime.model.Wrestler.class, "teamName");
        }

        property = (String)config.get("classification");
        sb.append("\n    class=" + property);
        if (!property.isEmpty()) {
            xs.aliasField(property, bouttime.model.Wrestler.class, "classification");
        }

        property = (String)config.get("division");
        sb.append("\n    div=" + property);
        if (!property.isEmpty()) {
            xs.aliasField(property, bouttime.model.Wrestler.class, "ageDivision");
        }

        property = (String)config.get("weightClass");
        sb.append("\n    wtClass=" + property);
        if (!property.isEmpty()) {
            xs.aliasField(property , bouttime.model.Wrestler.class, "weightClass");
        }

        property = (String)config.get("actualWeight");
        sb.append("\n    actWt=" + property);
        if (!property.isEmpty()) {
            xs.aliasField(property, bouttime.model.Wrestler.class, "actualWeight");
        }

        property = (String)config.get("level");
        sb.append("\n    level=" + property);
        if (!property.isEmpty()) {
            xs.aliasField(property, bouttime.model.Wrestler.class, "level");
        }

        property = (String)config.get("serialNumber");
        sb.append("\n    id=" + property);
        if (!property.isEmpty()) {
            xs.aliasField(property, bouttime.model.Wrestler.class, "serialNumber");
        }

        logger.info(sb.toString());
    }
}
