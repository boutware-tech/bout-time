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

package bouttime.mainview;

import java.io.FileInputStream;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.apache.log4j.PropertyConfigurator;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.apache.log4j.Logger;

/**
 * The main class of the application.
 */
public class BoutTimeApp extends SingleFrameApplication implements Thread.UncaughtExceptionHandler {

    private BoutTimeView boutTimeView;
    static Logger logger;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.boutTimeView = new BoutTimeView(this);
        show(this.boutTimeView);
    }

    @Override protected void shutdown() {
        this.boutTimeView.shutdown();
        super.shutdown();
    }

    /**
     * Called after all initial GUI events related to your UI startup have been processed.
     * Perform tasks that will not delay your initial UI.
     * Place here any work that depends on your UI being ready and visible.
     */
    @Override protected void ready() {
        this.boutTimeView.doInitAction();
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of BoutTimeApp
     */
    public static BoutTimeApp getApplication() {
        return Application.getInstance(BoutTimeApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        setupLogger();

        Runtime rt = Runtime.getRuntime();
        logger.info("\n    Max memory=" + rt.maxMemory() +
                    "\n    Total JVM memory=" + rt.totalMemory() +
                    "\n    Free memory=" + rt.freeMemory());

        setPropertiesFromFile();
        
        launch(BoutTimeApp.class, args);
    }

    /**
     * Implementation method for Thread.UncaughtExceptionHandler interface.
     * @param t the thread
     * @param e the exception
     */
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        if (this.boutTimeView != null) {
            JOptionPane.showMessageDialog(this.boutTimeView.getFrame(),
                    "BoutTime has encountered a runtime exception.\n" +
                    "See 'logs/bouttime.log' for more information.\n\n" +
                    e.toString(), "Runtime Exception", JOptionPane.ERROR_MESSAGE);
        }

        logger.error("Uncaught Exception", e);
    }

    private static void setupLogger() {
        PropertyConfigurator.configure("config/logger.conf");
        logger = Logger.getLogger(BoutTimeApp.class);
    }

    private static void setPropertiesFromFile() {
        FileInputStream propFile = null;
        try {
            propFile = new FileInputStream("BoutTime.properties");
            Properties p = new Properties();
            p.load(propFile);
            for (String s : p.stringPropertyNames()) {
                System.setProperty(s, p.getProperty(s));
                logger.debug("Added property [" + s + "=" + p.getProperty(s) + "]");
            }
        } catch (java.io.FileNotFoundException fnfe) {
            logger.debug("Trouble with properties file", fnfe);
        } catch (java.io.IOException ioe) {
            logger.debug("Trouble with properties file", ioe);
        } finally {
            if (propFile != null) {
                try {
                    propFile.close();
                } catch (java.io.IOException ioe) {
                    logger.debug("Trouble closing properties file", ioe);
                }
            }
        }

        logger.debug(System.getProperties());
    }
}
