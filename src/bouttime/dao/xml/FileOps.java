/**
 * ***COPYRIGHT STARTS HERE*** BoutTime - the wrestling tournament
 * administrator.
 *
 * Copyright (C) 2012 Jeffrey K. Rutt
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>. ***COPYRIGHT ENDS
 * HERE***
 */
package bouttime.dao.xml;

import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 * This class handles the file operations for persisting a tournament object for
 * bouttime.dao.xml.XmlDao.
 */
class FileOps {

    static Logger logger = Logger.getLogger(FileOps.class);
    private File file;
    private boolean open;

    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    FileOps() {
        this.file = null;
        this.open = false;
    }

    /**
     * Indicate whether or not a file is open.
     *
     * @return "true" means the file is open.
     */
    boolean isOpen() {
        return open;
    }

    /**
     * Entry point to create and open a new file.
     *
     * @param filename The absolute path of the file to create.
     * @return A Tournament object associated with the persistence file.
     */
    Tournament openNew(String filename) {
        logger.trace("BEGIN");
        Tournament t = createFile(filename);
        logger.trace("END");
        return t;
    }

    /**
     * Entry point to open an existing file.
     *
     * @return A Tournament object associated with the persistence file.
     */
    Tournament openExisting() {
        logger.trace("BEGIN");
        Tournament t = readContentsFromFile(true);
        logger.trace("END");
        return t;
    }

    /**
     * Entry point to open an existing file.
     *
     * @param filename Path to file to existing tournament to open
     * @return A Tournament object associated with the persistence file.
     */
    Tournament openExisting(String filename) {
        logger.trace("BEGIN");
        this.file = new File(filename);
        Tournament t = readContentsFromFile(false);
        logger.trace("END");
        return t;
    }

    /**
     * Write the contents of the Tournament object to the persistence file and
     * close the file.
     *
     * @param tourney The object to write to the persistence file.
     */
    void close(Tournament tourney) {
        logger.trace("BEGIN");
        if (this.isOpen()) {
            writeContentsToFile(tourney);
        }

        this.file = null;
        this.open = false;
        logger.trace("END");
    }

    /**
     * Write the contents of the Tournament object to the persistence file.
     *
     * @param tourney The object to write to the persistence file.
     */
    void flush(Tournament tourney) {
        logger.trace("BEGIN");
        if (this.isOpen()) {
            writeContentsToFile(tourney);
        }
        logger.trace("END");
    }

    /**
     * Set the aliases for the XStream object. This makes the XML file a bit
     * nicer/easier to look at.
     *
     * @param xs The XStream object to set the aliases on.
     */
    private void setXStreamAliases(XStream xs) {
        logger.trace("BEGIN");
        xs.alias("tournament", Tournament.class);
        xs.alias("wrestler", bouttime.model.Wrestler.class);
        xs.alias("group", bouttime.model.Group.class);
        xs.alias("bout", bouttime.model.Bout.class);
        xs.setMode(XStream.ID_REFERENCES);
        logger.trace("END");
    }

    /**
     * Write the contents of the "tourney" object to the persistence file.
     *
     * @param tourney The object that will be written to the file.
     */
    private void writeContentsToFile(Tournament tourney) {
        logger.trace("BEGIN");
        FileWriter fw = null;
        try {
            fw = new FileWriter(this.file);
            XStream xs = new XStream();
            setXStreamAliases(xs);
            ObjectOutputStream out = xs.createObjectOutputStream(fw);
            out.writeObject(tourney);
            out.close();
            logger.debug("Successfully wrote tournament data to file : " + this.file);
        } catch (IOException e) {
            logger.warn("Unable to write contents to " + this.file + "\n"
                    + Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (java.io.IOException ioe) {
                    logger.debug("Unable to close FileWriter\n" + Arrays.toString(ioe.getStackTrace()));
                }
            }
        }
        logger.trace("END");
    }

    private void getUserFile() {
        logger.trace("BEGIN");
        JFileChooser infile = new JFileChooser();
        JFrame mainFrame = bouttime.mainview.BoutTimeApp.getApplication().getMainFrame();

        if (infile.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            this.file = infile.getSelectedFile();
        } else {
            logger.debug("User cancelled dialog to select file to read tournament data from");
        }

        logger.trace("END");
    }

    /**
     * Read the contents from the persistence file into a Tournament object.
     * Prompt the user to select a file.
     *
     * @param isApp True if the runtime is in application context
     * @return On success, a Tournament object with the persisted data. On
     * failure, "null".
     */
    private Tournament readContentsFromFile(boolean isApp) {
        logger.trace("BEGIN");

        Tournament tourney;

        if (isApp) {
            getUserFile();

            if (this.file == null) {
                this.open = false;
                return null;
            }
        }

        FileReader fr = null;
        try {
            fr = new FileReader(this.file);
            XStream xs = new XStream();
            setXStreamAliases(xs);
            ObjectInputStream in = xs.createObjectInputStream(fr);
            tourney = (Tournament) in.readObject();
            in.close();
            this.open = true;
            logger.info("Successfully read tournament data from file : " + this.file);
        } catch (Exception e) {
            if (isApp) {
                JFrame mainFrame = bouttime.mainview.BoutTimeApp.getApplication().getMainFrame();
                JOptionPane.showMessageDialog(mainFrame, "Error opening file.\n" + e.getLocalizedMessage(),
                        "Open existing tournament file error", JOptionPane.ERROR_MESSAGE);
            }
            this.file = null;
            this.open = false;
            tourney = null;
            logger.warn("Unable to read contents from file " + this.file + "\n"
                    + Arrays.toString(e.getStackTrace()));
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (java.io.IOException ioe) {
                    logger.debug("Unable to close FileReader\n" + Arrays.toString(ioe.getStackTrace()));
                }
            }
        }

        logger.trace("END");
        return tourney;
    }

    /**
     * Create a new file for persisting a Tournament object.
     *
     * @return An empty Tournament object.
     */
    private Tournament createFile(String filename) {
        logger.trace("BEGIN");
        this.file = new File(filename);
        try {
            if (this.file.createNewFile()) {
                logger.info("Created new tournament file : " + filename);
            } else {
                throw new IOException();
            }
        } catch (IOException ex) {
            logger.warn("Unable to create new tournament file : " + filename + "\n"
                    + Arrays.toString(ex.getStackTrace()));
            ex.printStackTrace();
            this.file = null;
            this.open = false;
            return null;
        }

        this.open = true;
        Tournament tourney = new Tournament();

        writeContentsToFile(tourney);

        logger.trace("END");
        return tourney;
    }
}