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

package bouttime.preview;

import bouttime.dao.Dao;
import bouttime.mainview.BoutTimeApp;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;

/**
 * A class to display the preview of the bracket sheet for a group.
 */
public class BracketPreviewComponent extends Component {
    static Logger logger = Logger.getLogger(BracketPreviewComponent.class);

    public BracketPreviewComponent() {this(null, null);}

    public BracketPreviewComponent(Group g, Dao dao) {
        super();

        initImage(g, dao);
    }

    /**
     * Update the display.
     * @param g Graphics object to draw on.
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(this.image, 0, 0, null);
    }

    /**
     * @return Dimension object for the image being used.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.image.getWidth(null), this.image.getHeight(null));
    }

    /**
     * Top-level method called by the Constructors.
     * Initialize the image to be used.
     * @param g Group to display the bracket preview for.
     * @param dao Dao to be used to retrieve data from.
     */
    private void initImage(Group g, Dao dao) {
        Application app = Application.getInstance(BoutTimeApp.class);
        ApplicationContext appCtx = app.getContext();
        ResourceMap map = appCtx.getResourceMap(BracketPreviewComponent.class);

        if ((g == null) || (dao == null)) {
            doBlank(map);
            return;
        }

        switch(g.getWrestlers().size()) {
            case 1:
            case 2:
                do2(map, dao, g);
                break;

            case 3:
                do3(map, dao, g);
                break;

            case 4:
                do4(map, dao, g);
                break;

            case 5:
                do5(map, dao, g);
                break;

            // Fall thru
            case 6:
            case 7:
            case 8:
                do8Bracket(map, g);
                break;
                
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                do16Bracket(map, g);
                break;

            case 17:
            case 18:
            case 19:
            case 20:
                do20Bracket(map, g);
                break;

            case 21:
            case 22:
            case 23:
            case 24:
                do24Bracket(map, g);
                break;

            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
                do32Bracket(map, g);
                break;
                
            default:
                doBlank(map);
        }
    }

    /**
     * Set the image that will be used.
     * @param map ResourceMap to retrieve properties.
     * @param filename Name of the image file to use.
     */
    private void setImage(ResourceMap map, String filename) {
        String path = map.getResourcesDir() + filename;
        URL url = map.getClassLoader().getResource(path);
        try {
            this.image = ImageIO.read(url);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage() + "\n" + Arrays.toString(e.getStackTrace()));
            this.image = null;
            return;
        }
    }

    /**
     * Display the default image indicating there is no group selected.
     * @param map ResourceMap to retrieve properties.
     */
    private void doBlank(ResourceMap map) {
        String filename = map.getString("blank.image.filename");
        setImage(map, filename);
        if (this.image == null) {
            return;
        }

        Graphics2D g2d = this.image.createGraphics();
        g2d.setBackground(Color.WHITE);
        g2d.dispose();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Second-level methods.
    // Methods called from the Constructor/initImage().
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Determine which image to show (round-robin vs. bracket) for a 2-man group.
     * @param map ResourceMap to retrieve properties.
     * @param dao Dao to be used to retrieve data from.
     * @param wList List of wrestlers to display.
     */
    private void do2(ResourceMap map, Dao dao, Group g) {
        if (dao.isRoundRobinEnabled() && dao.getRoundRobinMax() >= 2) {
            do2RoundRobin(map, g);
        } else {
            do2Bracket(map, g);
        }
    }

    /**
     * Determine which image to show (round-robin vs. bracket) for a 3-man group.
     * @param map ResourceMap to retrieve properties.
     * @param dao Dao to be used to retrieve data from.
     * @param wList List of wrestlers to display.
     */
    private void do3(ResourceMap map, Dao dao, Group g) {
        if (dao.isRoundRobinEnabled() && (dao.getRoundRobinMax() >= 3)) {
            do3RoundRobin(map, g);
        } else {
            do4Bracket(map, g);
        }
    }

    /**
     * Determine which image to show (round-robin vs. bracket) for a 4-man group.
     * @param map ResourceMap to retrieve properties.
     * @param dao Dao to be used to retrieve data from.
     * @param wList List of wrestlers to display.
     */
    private void do4(ResourceMap map, Dao dao, Group g) {
        if (dao.isRoundRobinEnabled() && (dao.getRoundRobinMax() >= 4)) {
            do4RoundRobin(map, g);
        } else {
            do4Bracket(map, g);
        }

    }

    /**
     * Determine which image to show (round-robin vs. bracket) for a 5-man group.
     * @param map ResourceMap to retrieve properties.
     * @param dao Dao to be used to retrieve data from.
     * @param wList List of wrestlers to display.
     */
    private void do5(ResourceMap map, Dao dao, Group g) {
        if (dao.isRoundRobinEnabled() && (dao.getRoundRobinMax() >= 5)) {
            do5RoundRobin(map, g);
        } else {
            do8Bracket(map, g);
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    // Third-level methods.
    // Methods called from second-level methods.
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Draw the display for a 2-man round robin bracket.
     * @param map ResourceMap to retrieve properties.
     * @param wList List of wrestlers to display.
     */
    private void do2RoundRobin(ResourceMap map, Group g) {
        String filename = map.getString("2roundRobin.image.filename");
        setImage(map, filename);
        if (this.image == null) {
            return;
        }

        int column1 = 25;
        int column2 = 322;
        int row1 = 70;
        int row2 = 215;
        int row3 = 360;

        Graphics2D g2d = this.image.createGraphics();
        g2d.setBackground(Color.WHITE);
        Font fnt = new Font("TimesRoman",Font.BOLD,14);
        g2d.setFont(fnt);
        g2d.setColor(Color.BLACK);

        Wrestler seed1 = g.getWrestlerAtSeed(1);
        String s = (seed1 != null) ? seed1.getShortName() : "-- BYE --";
        String w1 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        Wrestler seed2 = g.getWrestlerAtSeed(2);
        s = (seed2 != null) ? seed2.getShortName() : "-- BYE --";
        String w2 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        g2d.drawString(w1, column1, row1);
        g2d.drawString(w2, column2, row1);

        g2d.drawString(w1, column1, row2);
        g2d.drawString(w2, column2, row2);

        g2d.drawString(w1, column1, row3);
        g2d.drawString(w2, column2, row3);

        g2d.dispose();
    }

    /**
     * Draw the display for a 2-man bracket.
     * @param map ResourceMap to retrieve properties.
     * @param wList List of wrestlers to display.
     */
    private void do2Bracket(ResourceMap map, Group g) {
        String filename = map.getString("2bracket.image.filename");
        setImage(map, filename);
        if (this.image == null) {
            return;
        }

        int column1 = 25;
        int row1 = 30;
        int row2 = 385;

        Graphics2D g2d = this.image.createGraphics();
        g2d.setBackground(Color.WHITE);
        Font fnt = new Font("TimesRoman",Font.BOLD,14);
        g2d.setFont(fnt);
        g2d.setColor(Color.BLACK);

        Wrestler seed1 = g.getWrestlerAtSeed(1);
        Wrestler seed2 = g.getWrestlerAtSeed(2);
        String w1 = "1) " + ((seed1 != null) ? seed1.getShortName() : "-- BYE --");
        String w2 = "2) " + ((seed2 != null) ? seed2.getShortName() : "-- BYE --");

        g2d.drawString(w1, column1, row1);
        g2d.drawString(w2, column1, row2);

        g2d.dispose();
    }

    /**
     * Draw the display for a 3-man round robin bracket.
     * @param map ResourceMap to retrieve properties.
     * @param wList List of wrestlers to display.
     */
    private void do3RoundRobin(ResourceMap map, Group g) {
        String filename = map.getString("3roundRobin.image.filename");
        setImage(map, filename);
        if (this.image == null) {
            return;
        }

        int column1 = 25;
        int column2 = 322;
        int row1 = 70;
        int row2 = 100;
        int row3 = 235;
        int row4 = 265;
        int row5 = 395;
        int row6 = 425;

        Graphics2D g2d = this.image.createGraphics();
        g2d.setBackground(Color.WHITE);
        Font fnt = new Font("TimesRoman",Font.BOLD,14);
        g2d.setFont(fnt);
        g2d.setColor(Color.BLACK);

        Wrestler seed1 = g.getWrestlerAtSeed(1);
        String s = (seed1 != null) ? seed1.getShortName() : "-- BYE --";
        String w1 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);


        Wrestler seed2 = g.getWrestlerAtSeed(2);
        s = (seed2 != null) ? seed2.getShortName() : "-- BYE --";
        String w2 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        Wrestler seed3 = g.getWrestlerAtSeed(3);
        s = (seed3 != null) ? seed3.getShortName() : "-- BYE --";
        String w3 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        g2d.drawString(w1, column1, row1);
        g2d.drawString(w2, column2, row1);
        g2d.drawString(w3, column1, row2);

        g2d.drawString(w1, column1, row3);
        g2d.drawString(w3, column2, row3);
        g2d.drawString(w2, column1, row4);

        g2d.drawString(w2, column1, row5);
        g2d.drawString(w3, column2, row5);
        g2d.drawString(w1, column1, row6);

        g2d.setColor(Color.BLUE);
        g2d.drawString("BYE", column2 + 75, row2);
        g2d.drawString("BYE", column2 + 75, row4);
        g2d.drawString("BYE", column2 + 75, row6);

        g2d.dispose();
    }

    /**
     * Draw the display for a 4-man round robin bracket.
     * @param map ResourceMap to retrieve properties.
     * @param wList List of wrestlers to display.
     */
    private void do4RoundRobin(ResourceMap map, Group g) {
        String filename = map.getString("3roundRobin.image.filename");
        setImage(map, filename);
        if (this.image == null) {
            return;
        }

        int column1 = 25;
        int column2 = 322;
        int row1 = 70;
        int row2 = 100;
        int row3 = 235;
        int row4 = 265;
        int row5 = 395;
        int row6 = 425;

        Graphics2D g2d = this.image.createGraphics();
        g2d.setBackground(Color.WHITE);
        Font fnt = new Font("TimesRoman",Font.BOLD,14);
        g2d.setFont(fnt);
        g2d.setColor(Color.BLACK);

        Wrestler seed1 = g.getWrestlerAtSeed(1);
        String s = (seed1 != null) ? seed1.getShortName() : "-- BYE --";
        String w1 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        Wrestler seed2 = g.getWrestlerAtSeed(2);
        s = (seed2 != null) ? seed2.getShortName() : "-- BYE --";
        String w2 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        Wrestler seed3 = g.getWrestlerAtSeed(3);
        s = (seed3 != null) ? seed3.getShortName() : "-- BYE --";
        String w3 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        Wrestler seed4 = g.getWrestlerAtSeed(4);
        s = (seed4 != null) ? seed4.getShortName() : "-- BYE --";
        String w4 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        g2d.drawString(w1, column1, row1);
        g2d.drawString(w2, column2, row1);
        g2d.drawString(w3, column1, row2);
        g2d.drawString(w4, column2, row2);

        g2d.drawString(w1, column1, row3);
        g2d.drawString(w3, column2, row3);
        g2d.drawString(w2, column1, row4);
        g2d.drawString(w4, column2, row4);

        g2d.drawString(w1, column1, row5);
        g2d.drawString(w4, column2, row5);
        g2d.drawString(w2, column1, row6);
        g2d.drawString(w3, column2, row6);

        g2d.dispose();
    }

    /**
     * Draw the display for a 4-man bracket.
     * @param map ResourceMap to retrieve properties.
     * @param wList List of wrestlers to display.
     */
    private void do4Bracket(ResourceMap map, Group g) {
        String filename = map.getString("4bracket.image.filename");
        setImage(map, filename);
        if (this.image == null) {
            return;
        }

        int column1 = 25;
        int row1 = 30;
        int row2 = 150;
        int row3 = 265;
        int row4 = 385;

        Graphics2D g2d = this.image.createGraphics();
        g2d.setBackground(Color.WHITE);
        Font fnt = new Font("TimesRoman",Font.BOLD,14);
        g2d.setFont(fnt);
        g2d.setColor(Color.BLACK);

        Wrestler seed1 = g.getWrestlerAtSeed(1);
        Wrestler seed2 = g.getWrestlerAtSeed(2);
        Wrestler seed3 = g.getWrestlerAtSeed(3);
        Wrestler seed4 = g.getWrestlerAtSeed(4);
        String w1 = "1) " + ((seed1 != null) ? seed1.getShortName() : "-- BYE --");
        String w2 = "2) " + ((seed2 != null) ? seed2.getShortName() : "-- BYE --");
        String w3 = "3) " + ((seed3 != null) ? seed3.getShortName() : "-- BYE --");
        String w4 = "4) " + ((seed4 != null) ? seed4.getShortName() : "-- BYE --");

        g2d.drawString(w1, column1, row1);
        g2d.drawString(w2, column1, row4);
        g2d.drawString(w3, column1, row3);
        g2d.drawString(w4, column1, row2);

        g2d.dispose();
    }

    /**
     * Draw the display for a 5-man round robin bracket.
     * @param map ResourceMap to retrieve properties.
     * @param wList List of wrestlers to display.
     */
    private void do5RoundRobin(ResourceMap map, Group g) {
        String filename = map.getString("5roundRobin.image.filename");
        setImage(map, filename);
        if (this.image == null) {
            return;
        }

        int column1 = 25;
        int column2 = 322;
        int row1 = 50;
        int row2 = 70;
        int row3 = 90;
        int row4 = 167;
        int row5 = 187;
        int row6 = 207;
        int row7 = 284;
        int row8 = 304;
        int row9 = 324;
        int row10 = 399;
        int row11 = 419;
        int row12 = 439;
        int row13 = 514;
        int row14 = 534;
        int row15 = 554;

        Graphics2D g2d = this.image.createGraphics();
        g2d.setBackground(Color.WHITE);
        Font fnt = new Font("TimesRoman",Font.BOLD,14);
        g2d.setFont(fnt);
        g2d.setColor(Color.BLACK);

        Wrestler seed1 = g.getWrestlerAtSeed(1);
        String s = (seed1 != null) ? seed1.getShortName() : "-- BYE --";
        String w1 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        Wrestler seed2 = g.getWrestlerAtSeed(2);
        s = (seed2 != null) ? seed2.getShortName() : "-- BYE --";
        String w2 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        Wrestler seed3 = g.getWrestlerAtSeed(3);
        s = (seed3 != null) ? seed3.getShortName() : "-- BYE --";
        String w3 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        Wrestler seed4 = g.getWrestlerAtSeed(4);
        s = (seed4 != null) ? seed4.getShortName() : "-- BYE --";
        String w4 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        Wrestler seed5 = g.getWrestlerAtSeed(5);
        s = (seed5 != null) ? seed5.getShortName() : "-- BYE --";
        String w5 = (s.length() <= CHAR_WIDTH) ? s : s.substring(0, CHAR_WIDTH);

        g2d.drawString(w1, column1, row1);
        g2d.drawString(w2, column2, row1);
        g2d.drawString(w3, column1, row2);
        g2d.drawString(w4, column2, row2);
        g2d.drawString(w5, column1, row3);

        g2d.drawString(w1, column1, row4);
        g2d.drawString(w3, column2, row4);
        g2d.drawString(w2, column1, row5);
        g2d.drawString(w5, column2, row5);
        g2d.drawString(w4, column1, row6);

        g2d.drawString(w1, column1, row7);
        g2d.drawString(w5, column2, row7);
        g2d.drawString(w2, column1, row8);
        g2d.drawString(w4, column2, row8);
        g2d.drawString(w3, column1, row9);

        g2d.drawString(w1, column1, row10);
        g2d.drawString(w4, column2, row10);
        g2d.drawString(w3, column1, row11);
        g2d.drawString(w5, column2, row11);
        g2d.drawString(w2, column1, row12);

        g2d.drawString(w2, column1, row13);
        g2d.drawString(w3, column2, row13);
        g2d.drawString(w4, column1, row14);
        g2d.drawString(w5, column2, row14);
        g2d.drawString(w1, column1, row15);

        g2d.setColor(Color.BLUE);
        g2d.drawString("BYE", column2 + 75, row3);
        g2d.drawString("BYE", column2 + 75, row6);
        g2d.drawString("BYE", column2 + 75, row9);
        g2d.drawString("BYE", column2 + 75, row12);
        g2d.drawString("BYE", column2 + 75, row15);

        g2d.dispose();
    }

    /**
     * Draw the display for a 8-man bracket.
     * @param map ResourceMap to retrieve properties.
     * @param wList List of wrestlers to display.
     */
    private void do8Bracket(ResourceMap map, Group g) {
        String filename = map.getString("8bracket.image.filename");
        setImage(map, filename);
        if (this.image == null) {
            return;
        }

        int column1 = 25;
        int row1 = 31;
        int row2 = 95;
        int row3 = 162;
        int row4 = 225;
        int row5 = 295;
        int row6 = 355;
        int row7 = 429;
        int row8 = 490;

        Graphics2D g2d = this.image.createGraphics();
        g2d.setBackground(Color.WHITE);
        Font fnt = new Font("TimesRoman",Font.BOLD,14);
        g2d.setFont(fnt);
        g2d.setColor(Color.BLACK);

        Wrestler seed1 = g.getWrestlerAtSeed(1);
        Wrestler seed2 = g.getWrestlerAtSeed(2);
        Wrestler seed3 = g.getWrestlerAtSeed(3);
        Wrestler seed4 = g.getWrestlerAtSeed(4);
        Wrestler seed5 = g.getWrestlerAtSeed(5);
        Wrestler seed6 = g.getWrestlerAtSeed(6);
        Wrestler seed7 = g.getWrestlerAtSeed(7);
        Wrestler seed8 = g.getWrestlerAtSeed(8);
        String w1 = "1) " + ((seed1 != null) ? seed1.getShortName() : "-- BYE --");
        String w2 = "2) " + ((seed2 != null) ? seed2.getShortName() : "-- BYE --");
        String w3 = "3) " + ((seed3 != null) ? seed3.getShortName() : "-- BYE --");
        String w4 = "4) " + ((seed4 != null) ? seed4.getShortName() : "-- BYE --");
        String w5 = "5) " + ((seed5 != null) ? seed5.getShortName() : "-- BYE --");
        String w6 = "6) " + ((seed6 != null) ? seed6.getShortName() : "-- BYE --");
        String w7 = "7) " + ((seed7 != null) ? seed7.getShortName() : "-- BYE --");
        String w8 = "8) " + ((seed8 != null) ? seed8.getShortName() : "-- BYE --");

        g2d.drawString(w1, column1, row1);
        g2d.drawString(w2, column1, row8);
        g2d.drawString(w3, column1, row5);
        g2d.drawString(w4, column1, row4);
        g2d.drawString(w5, column1, row3);
        g2d.drawString(w6, column1, row6);
        g2d.drawString(w7, column1, row7);
        g2d.drawString(w8, column1, row2);

        g2d.dispose();
    }

    /**
     * Draw the display for a 16-man bracket.
     * @param map ResourceMap to retrieve properties.
     * @param wList List of wrestlers to display.
     */
    private void do16Bracket(ResourceMap map, Group g) {
        String filename = map.getString("16bracket.image.filename");
        setImage(map, filename);
        if (this.image == null) {
            return;
        }

        int column1 = 25;
        int row1 = 33;
        int row2 = 61;
        int row3 = 101;
        int row4 = 127;
        int row5 = 167;
        int row6 = 193;
        int row7 = 231;
        int row8 = 260;
        int row9 = 296;
        int row10 = 325;
        int row11 = 362;
        int row12 = 391;
        int row13 = 430;
        int row14 = 456;
        int row15 = 493;
        int row16 = 522;

        Graphics2D g2d = this.image.createGraphics();
        g2d.setBackground(Color.WHITE);
        Font fnt = new Font("TimesRoman",Font.BOLD,12);
        g2d.setFont(fnt);
        g2d.setColor(Color.BLACK);

        Wrestler seed1 = g.getWrestlerAtSeed(1);
        Wrestler seed2 = g.getWrestlerAtSeed(2);
        Wrestler seed3 = g.getWrestlerAtSeed(3);
        Wrestler seed4 = g.getWrestlerAtSeed(4);
        Wrestler seed5 = g.getWrestlerAtSeed(5);
        Wrestler seed6 = g.getWrestlerAtSeed(6);
        Wrestler seed7 = g.getWrestlerAtSeed(7);
        Wrestler seed8 = g.getWrestlerAtSeed(8);
        Wrestler seed9 = g.getWrestlerAtSeed(9);
        Wrestler seed10 = g.getWrestlerAtSeed(10);
        Wrestler seed11 = g.getWrestlerAtSeed(11);
        Wrestler seed12 = g.getWrestlerAtSeed(12);
        Wrestler seed13 = g.getWrestlerAtSeed(13);
        Wrestler seed14 = g.getWrestlerAtSeed(14);
        Wrestler seed15 = g.getWrestlerAtSeed(15);
        Wrestler seed16 = g.getWrestlerAtSeed(16);
        String w1 = "1) " + ((seed1 != null) ? seed1.getShortName() : "-- BYE --");
        String w2 = "2) " + ((seed2 != null) ? seed2.getShortName() : "-- BYE --");
        String w3 = "3) " + ((seed3 != null) ? seed3.getShortName() : "-- BYE --");
        String w4 = "4) " + ((seed4 != null) ? seed4.getShortName() : "-- BYE --");
        String w5 = "5) " + ((seed5 != null) ? seed5.getShortName() : "-- BYE --");
        String w6 = "6) " + ((seed6 != null) ? seed6.getShortName() : "-- BYE --");
        String w7 = "7) " + ((seed7 != null) ? seed7.getShortName() : "-- BYE --");
        String w8 = "8) " + ((seed8 != null) ? seed8.getShortName() : "-- BYE --");
        String w9 = "9) " + ((seed9 != null) ? seed9.getShortName() : "-- BYE --");
        String w10 = "10) " + ((seed10 != null) ? seed10.getShortName() : "-- BYE --");
        String w11 = "11) " + ((seed11 != null) ? seed11.getShortName() : "-- BYE --");
        String w12 = "12) " + ((seed12 != null) ? seed12.getShortName() : "-- BYE --");
        String w13 = "13) " + ((seed13 != null) ? seed13.getShortName() : "-- BYE --");
        String w14 = "14) " + ((seed14 != null) ? seed14.getShortName() : "-- BYE --");
        String w15 = "15) " + ((seed15 != null) ? seed15.getShortName() : "-- BYE --");
        String w16 = "16) " + ((seed16 != null) ? seed16.getShortName() : "-- BYE --");

        g2d.drawString(w1, column1, row1);
        g2d.drawString(w2, column1, row16);
        g2d.drawString(w3, column1, row9);
        g2d.drawString(w4, column1, row8);
        g2d.drawString(w5, column1, row5);
        g2d.drawString(w6, column1, row12);
        g2d.drawString(w7, column1, row13);
        g2d.drawString(w8, column1, row4);
        g2d.drawString(w9, column1, row3);
        g2d.drawString(w10, column1, row14);
        g2d.drawString(w11, column1, row11);
        g2d.drawString(w12, column1, row6);
        g2d.drawString(w13, column1, row7);
        g2d.drawString(w14, column1, row10);
        g2d.drawString(w15, column1, row15);
        g2d.drawString(w16, column1, row2);

        g2d.dispose();
    }

    /**
     * Draw the display for a 20-man bracket.
     * @param map ResourceMap to retrieve properties.
     * @param wList List of wrestlers to display.
     */
    private void do20Bracket(ResourceMap map, Group g) {
        String filename = map.getString("20bracket.image.filename");
        setImage(map, filename);
        if (this.image == null) {
            return;
        }

        int column1 = 25;
        int column2 = 97;
        int row1 = 34;
        int row2 = 77;
        int row3 = 106;
        int row4 = 142;
        int row5 = 171;
        int row6 = 208;
        int row7 = 236;
        int row8 = 272;
        int row9 = 301;
        int row10 = 344;
        int row11 = 382;
        int row12 = 424;
        int row13 = 451;
        int row14 = 490;
        int row15 = 517;
        int row16 = 556;
        int row17 = 586;
        int row18 = 622;
        int row19 = 652;
        int row20 = 693;

        Graphics2D g2d = this.image.createGraphics();
        g2d.setBackground(Color.WHITE);
        Font fnt = new Font("TimesRoman",Font.BOLD,12);
        g2d.setFont(fnt);
        g2d.setColor(Color.BLACK);

        Wrestler seed1 = g.getWrestlerAtSeed(1);
        Wrestler seed2 = g.getWrestlerAtSeed(2);
        Wrestler seed3 = g.getWrestlerAtSeed(3);
        Wrestler seed4 = g.getWrestlerAtSeed(4);
        Wrestler seed5 = g.getWrestlerAtSeed(5);
        Wrestler seed6 = g.getWrestlerAtSeed(6);
        Wrestler seed7 = g.getWrestlerAtSeed(7);
        Wrestler seed8 = g.getWrestlerAtSeed(8);
        Wrestler seed9 = g.getWrestlerAtSeed(9);
        Wrestler seed10 = g.getWrestlerAtSeed(10);
        Wrestler seed11 = g.getWrestlerAtSeed(11);
        Wrestler seed12 = g.getWrestlerAtSeed(12);
        Wrestler seed13 = g.getWrestlerAtSeed(13);
        Wrestler seed14 = g.getWrestlerAtSeed(14);
        Wrestler seed15 = g.getWrestlerAtSeed(15);
        Wrestler seed16 = g.getWrestlerAtSeed(16);
        Wrestler seed17 = g.getWrestlerAtSeed(17);
        Wrestler seed18 = g.getWrestlerAtSeed(18);
        Wrestler seed19 = g.getWrestlerAtSeed(19);
        Wrestler seed20 = g.getWrestlerAtSeed(20);
        String w1 = "1) " + ((seed1 != null) ? seed1.getShortName() : "-- BYE --");
        String w2 = "2) " + ((seed2 != null) ? seed2.getShortName() : "-- BYE --");
        String w3 = "3) " + ((seed3 != null) ? seed3.getShortName() : "-- BYE --");
        String w4 = "4) " + ((seed4 != null) ? seed4.getShortName() : "-- BYE --");
        String w5 = "5) " + ((seed5 != null) ? seed5.getShortName() : "-- BYE --");
        String w6 = "6) " + ((seed6 != null) ? seed6.getShortName() : "-- BYE --");
        String w7 = "7) " + ((seed7 != null) ? seed7.getShortName() : "-- BYE --");
        String w8 = "8) " + ((seed8 != null) ? seed8.getShortName() : "-- BYE --");
        String w9 = "9) " + ((seed9 != null) ? seed9.getShortName() : "-- BYE --");
        String w10 = "10) " + ((seed10 != null) ? seed10.getShortName() : "-- BYE --");
        String w11 = "11) " + ((seed11 != null) ? seed11.getShortName() : "-- BYE --");
        String w12 = "12) " + ((seed12 != null) ? seed12.getShortName() : "-- BYE --");
        String w13 = "13) " + ((seed13 != null) ? seed13.getShortName() : "-- BYE --");
        String w14 = "14) " + ((seed14 != null) ? seed14.getShortName() : "-- BYE --");
        String w15 = "15) " + ((seed15 != null) ? seed15.getShortName() : "-- BYE --");
        String w16 = "16) " + ((seed16 != null) ? seed16.getShortName() : "-- BYE --");
        String w17 = "17) " + ((seed17 != null) ? seed17.getShortName() : "-- BYE --");
        String w18 = "18) " + ((seed18 != null) ? seed18.getShortName() : "-- BYE --");
        String w19 = "19) " + ((seed19 != null) ? seed19.getShortName() : "-- BYE --");
        String w20 = "20) " + ((seed20 != null) ? seed20.getShortName() : "-- BYE --");

        g2d.drawString(w1, column2, row1);
        g2d.drawString(w2, column2, row20);
        g2d.drawString(w3, column2, row11);
        g2d.drawString(w4, column2, row10);
        g2d.drawString(w5, column2, row6);
        g2d.drawString(w6, column2, row15);
        g2d.drawString(w7, column2, row16);
        g2d.drawString(w8, column2, row5);
        g2d.drawString(w9, column2, row4);
        g2d.drawString(w10, column2, row17);
        g2d.drawString(w11, column2, row14);
        g2d.drawString(w12, column2, row7);
        g2d.drawString(w13, column1, row8);
        g2d.drawString(w14, column1, row13);
        g2d.drawString(w15, column1, row18);
        g2d.drawString(w16, column1, row3);
        g2d.drawString(w17, column1, row2);
        g2d.drawString(w18, column1, row19);
        g2d.drawString(w19, column1, row12);
        g2d.drawString(w20, column1, row9);

        g2d.dispose();
    }

    /**
     * Draw the display for a 24-man bracket.
     * @param map ResourceMap to retrieve properties.
     * @param wList List of wrestlers to display.
     */
    private void do24Bracket(ResourceMap map, Group g) {
        String filename = map.getString("24bracket.image.filename");
        setImage(map, filename);
        if (this.image == null) {
            return;
        }

        int column1 = 25;
        int column2 = 97;
        int height1 = 23;
        int height2 = 37;
        int row1 = 34;
        int row2 = row1 + height2;
        int row3 = row2 + height1;
        int row4 = row3 + height1;
        int row5 = row4 + height1;
        int row6 = row5 + height2;
        int row7 = row6 + height1;
        int row8 = row7 + height2;
        int row9 = row8 + height1;
        int row10 = row9 + height1;
        int row11 = row10 + height1 + 3;
        int row12 = row11 + height2;
        int row13 = row12 + height1;
        int row14 = row13 + height2;
        int row15 = row14 + height1;
        int row16 = row15 + height1;
        int row17 = row16 + height1;
        int row18 = row17 + height2 + 3;
        int row19 = row18 + height1;
        int row20 = row19 + height2;
        int row21 = row20 + height1;
        int row22 = row21 + height1;
        int row23 = row22 + height1;
        int row24 = row23 + height2;

        Graphics2D g2d = this.image.createGraphics();
        g2d.setBackground(Color.WHITE);
        Font fnt = new Font("TimesRoman",Font.BOLD,12);
        g2d.setFont(fnt);
        g2d.setColor(Color.BLACK);

        Wrestler seed1 = g.getWrestlerAtSeed(1);
        Wrestler seed2 = g.getWrestlerAtSeed(2);
        Wrestler seed3 = g.getWrestlerAtSeed(3);
        Wrestler seed4 = g.getWrestlerAtSeed(4);
        Wrestler seed5 = g.getWrestlerAtSeed(5);
        Wrestler seed6 = g.getWrestlerAtSeed(6);
        Wrestler seed7 = g.getWrestlerAtSeed(7);
        Wrestler seed8 = g.getWrestlerAtSeed(8);
        Wrestler seed9 = g.getWrestlerAtSeed(9);
        Wrestler seed10 = g.getWrestlerAtSeed(10);
        Wrestler seed11 = g.getWrestlerAtSeed(11);
        Wrestler seed12 = g.getWrestlerAtSeed(12);
        Wrestler seed13 = g.getWrestlerAtSeed(13);
        Wrestler seed14 = g.getWrestlerAtSeed(14);
        Wrestler seed15 = g.getWrestlerAtSeed(15);
        Wrestler seed16 = g.getWrestlerAtSeed(16);
        Wrestler seed17 = g.getWrestlerAtSeed(17);
        Wrestler seed18 = g.getWrestlerAtSeed(18);
        Wrestler seed19 = g.getWrestlerAtSeed(19);
        Wrestler seed20 = g.getWrestlerAtSeed(20);
        Wrestler seed21 = g.getWrestlerAtSeed(21);
        Wrestler seed22 = g.getWrestlerAtSeed(22);
        Wrestler seed23 = g.getWrestlerAtSeed(23);
        Wrestler seed24 = g.getWrestlerAtSeed(24);
        String w1 = "1) " + ((seed1 != null) ? seed1.getShortName() : "-- BYE --");
        String w2 = "2) " + ((seed2 != null) ? seed2.getShortName() : "-- BYE --");
        String w3 = "3) " + ((seed3 != null) ? seed3.getShortName() : "-- BYE --");
        String w4 = "4) " + ((seed4 != null) ? seed4.getShortName() : "-- BYE --");
        String w5 = "5) " + ((seed5 != null) ? seed5.getShortName() : "-- BYE --");
        String w6 = "6) " + ((seed6 != null) ? seed6.getShortName() : "-- BYE --");
        String w7 = "7) " + ((seed7 != null) ? seed7.getShortName() : "-- BYE --");
        String w8 = "8) " + ((seed8 != null) ? seed8.getShortName() : "-- BYE --");
        String w9 = "9) " + ((seed9 != null) ? seed9.getShortName() : "-- BYE --");
        String w10 = "10) " + ((seed10 != null) ? seed10.getShortName() : "-- BYE --");
        String w11 = "11) " + ((seed11 != null) ? seed11.getShortName() : "-- BYE --");
        String w12 = "12) " + ((seed12 != null) ? seed12.getShortName() : "-- BYE --");
        String w13 = "13) " + ((seed13 != null) ? seed13.getShortName() : "-- BYE --");
        String w14 = "14) " + ((seed14 != null) ? seed14.getShortName() : "-- BYE --");
        String w15 = "15) " + ((seed15 != null) ? seed15.getShortName() : "-- BYE --");
        String w16 = "16) " + ((seed16 != null) ? seed16.getShortName() : "-- BYE --");
        String w17 = "17) " + ((seed17 != null) ? seed17.getShortName() : "-- BYE --");
        String w18 = "18) " + ((seed18 != null) ? seed18.getShortName() : "-- BYE --");
        String w19 = "19) " + ((seed19 != null) ? seed19.getShortName() : "-- BYE --");
        String w20 = "20) " + ((seed20 != null) ? seed20.getShortName() : "-- BYE --");
        String w21 = "21) " + ((seed21 != null) ? seed21.getShortName() : "-- BYE --");
        String w22 = "22) " + ((seed22 != null) ? seed22.getShortName() : "-- BYE --");
        String w23 = "23) " + ((seed23 != null) ? seed23.getShortName() : "-- BYE --");
        String w24 = "24) " + ((seed24 != null) ? seed24.getShortName() : "-- BYE --");

        g2d.drawString(w1, column2, row1);
        g2d.drawString(w2, column2, row24);
        g2d.drawString(w3, column2, row13);
        g2d.drawString(w4, column2, row12);
        g2d.drawString(w5, column2, row7);
        g2d.drawString(w6, column2, row18);
        g2d.drawString(w7, column2, row19);
        g2d.drawString(w8, column2, row6);
        g2d.drawString(w9, column1, row4);
        g2d.drawString(w10, column1, row21);
        g2d.drawString(w11, column1, row16);
        g2d.drawString(w12, column1, row9);
        g2d.drawString(w13, column1, row10);
        g2d.drawString(w14, column1, row15);
        g2d.drawString(w15, column1, row22);
        g2d.drawString(w16, column1, row3);
        g2d.drawString(w17, column1, row2);
        g2d.drawString(w18, column1, row23);
        g2d.drawString(w19, column1, row14);
        g2d.drawString(w20, column1, row11);
        g2d.drawString(w21, column1, row8);
        g2d.drawString(w22, column1, row17);
        g2d.drawString(w23, column1, row20);
        g2d.drawString(w24, column1, row5);

        g2d.dispose();
    }

    /**
     * Draw the display for a 32-man bracket.
     * @param map ResourceMap to retrieve properties.
     * @param wList List of wrestlers to display.
     */
    private void do32Bracket(ResourceMap map, Group g) {
        String filename = map.getString("32bracket.image.filename");
        setImage(map, filename);
        if (this.image == null) {
            return;
        }

        int column1 = 25;
        int height = 22;
        int row1 = 17;
        int row2 = row1 + height;
        int row3 = row2 + height;
        int row4 = row3 + height;
        int row5 = row4 + height;
        int row6 = row5 + height;
        int row7 = row6 + height;
        int row8 = row7 + height;
        int row9 = row8 + height;
        int row10 = row9 + height;
        int row11 = row10 + height;
        int row12 = row11 + height;
        int row13 = row12 + height;
        int row14 = row13 + height;
        int row15 = row14 + height;
        int row16 = row15 + height;
        int row17 = row16 + height;
        int row18 = row17 + height;
        int row19 = row18 + height;
        int row20 = row19 + height;
        int row21 = row20 + height;
        int row22 = row21 + height;
        int row23 = row22 + height;
        int row24 = row23 + height;
        int row25 = row24 + height;
        int row26 = row25 + height;
        int row27 = row26 + height;
        int row28 = row27 + height;
        int row29 = row28 + height;
        int row30 = row29 + height;
        int row31 = row30 + height;
        int row32 = row31 + height;

        Graphics2D g2d = this.image.createGraphics();
        g2d.setBackground(Color.WHITE);
        Font fnt = new Font("TimesRoman",Font.BOLD,12);
        g2d.setFont(fnt);
        g2d.setColor(Color.BLACK);

        Wrestler seed1 = g.getWrestlerAtSeed(1);
        Wrestler seed2 = g.getWrestlerAtSeed(2);
        Wrestler seed3 = g.getWrestlerAtSeed(3);
        Wrestler seed4 = g.getWrestlerAtSeed(4);
        Wrestler seed5 = g.getWrestlerAtSeed(5);
        Wrestler seed6 = g.getWrestlerAtSeed(6);
        Wrestler seed7 = g.getWrestlerAtSeed(7);
        Wrestler seed8 = g.getWrestlerAtSeed(8);
        Wrestler seed9 = g.getWrestlerAtSeed(9);
        Wrestler seed10 = g.getWrestlerAtSeed(10);
        Wrestler seed11 = g.getWrestlerAtSeed(11);
        Wrestler seed12 = g.getWrestlerAtSeed(12);
        Wrestler seed13 = g.getWrestlerAtSeed(13);
        Wrestler seed14 = g.getWrestlerAtSeed(14);
        Wrestler seed15 = g.getWrestlerAtSeed(15);
        Wrestler seed16 = g.getWrestlerAtSeed(16);
        Wrestler seed17 = g.getWrestlerAtSeed(17);
        Wrestler seed18 = g.getWrestlerAtSeed(18);
        Wrestler seed19 = g.getWrestlerAtSeed(19);
        Wrestler seed20 = g.getWrestlerAtSeed(20);
        Wrestler seed21 = g.getWrestlerAtSeed(21);
        Wrestler seed22 = g.getWrestlerAtSeed(22);
        Wrestler seed23 = g.getWrestlerAtSeed(23);
        Wrestler seed24 = g.getWrestlerAtSeed(24);
        Wrestler seed25 = g.getWrestlerAtSeed(25);
        Wrestler seed26 = g.getWrestlerAtSeed(26);
        Wrestler seed27 = g.getWrestlerAtSeed(27);
        Wrestler seed28 = g.getWrestlerAtSeed(28);
        Wrestler seed29 = g.getWrestlerAtSeed(29);
        Wrestler seed30 = g.getWrestlerAtSeed(30);
        Wrestler seed31 = g.getWrestlerAtSeed(31);
        Wrestler seed32 = g.getWrestlerAtSeed(32);
        String w1 = "1) " + ((seed1 != null) ? seed1.getShortName() : "-- BYE --");
        String w2 = "2) " + ((seed2 != null) ? seed2.getShortName() : "-- BYE --");
        String w3 = "3) " + ((seed3 != null) ? seed3.getShortName() : "-- BYE --");
        String w4 = "4) " + ((seed4 != null) ? seed4.getShortName() : "-- BYE --");
        String w5 = "5) " + ((seed5 != null) ? seed5.getShortName() : "-- BYE --");
        String w6 = "6) " + ((seed6 != null) ? seed6.getShortName() : "-- BYE --");
        String w7 = "7) " + ((seed7 != null) ? seed7.getShortName() : "-- BYE --");
        String w8 = "8) " + ((seed8 != null) ? seed8.getShortName() : "-- BYE --");
        String w9 = "9) " + ((seed9 != null) ? seed9.getShortName() : "-- BYE --");
        String w10 = "10) " + ((seed10 != null) ? seed10.getShortName() : "-- BYE --");
        String w11 = "11) " + ((seed11 != null) ? seed11.getShortName() : "-- BYE --");
        String w12 = "12) " + ((seed12 != null) ? seed12.getShortName() : "-- BYE --");
        String w13 = "13) " + ((seed13 != null) ? seed13.getShortName() : "-- BYE --");
        String w14 = "14) " + ((seed14 != null) ? seed14.getShortName() : "-- BYE --");
        String w15 = "15) " + ((seed15 != null) ? seed15.getShortName() : "-- BYE --");
        String w16 = "16) " + ((seed16 != null) ? seed16.getShortName() : "-- BYE --");
        String w17 = "17) " + ((seed17 != null) ? seed17.getShortName() : "-- BYE --");
        String w18 = "18) " + ((seed18 != null) ? seed18.getShortName() : "-- BYE --");
        String w19 = "19) " + ((seed19 != null) ? seed19.getShortName() : "-- BYE --");
        String w20 = "20) " + ((seed20 != null) ? seed20.getShortName() : "-- BYE --");
        String w21 = "21) " + ((seed21 != null) ? seed21.getShortName() : "-- BYE --");
        String w22 = "22) " + ((seed22 != null) ? seed22.getShortName() : "-- BYE --");
        String w23 = "23) " + ((seed23 != null) ? seed23.getShortName() : "-- BYE --");
        String w24 = "24) " + ((seed24 != null) ? seed24.getShortName() : "-- BYE --");
        String w25 = "25) " + ((seed25 != null) ? seed25.getShortName() : "-- BYE --");
        String w26 = "26) " + ((seed26 != null) ? seed26.getShortName() : "-- BYE --");
        String w27 = "27) " + ((seed27 != null) ? seed27.getShortName() : "-- BYE --");
        String w28 = "28) " + ((seed28 != null) ? seed28.getShortName() : "-- BYE --");
        String w29 = "29) " + ((seed29 != null) ? seed29.getShortName() : "-- BYE --");
        String w30 = "30) " + ((seed30 != null) ? seed30.getShortName() : "-- BYE --");
        String w31 = "31) " + ((seed31 != null) ? seed31.getShortName() : "-- BYE --");
        String w32 = "32) " + ((seed32 != null) ? seed32.getShortName() : "-- BYE --");

        g2d.drawString(w1, column1, row1);
        g2d.drawString(w2, column1, row32);
        g2d.drawString(w3, column1, row17);
        g2d.drawString(w4, column1, row16);
        g2d.drawString(w5, column1, row9);
        g2d.drawString(w6, column1, row24);
        g2d.drawString(w7, column1, row25);
        g2d.drawString(w8, column1, row8);
        g2d.drawString(w9, column1, row5);
        g2d.drawString(w10, column1, row28);
        g2d.drawString(w11, column1, row21);
        g2d.drawString(w12, column1, row12);
        g2d.drawString(w13, column1, row13);
        g2d.drawString(w14, column1, row20);
        g2d.drawString(w15, column1, row29);
        g2d.drawString(w16, column1, row4);
        g2d.drawString(w17, column1, row3);
        g2d.drawString(w18, column1, row30);
        g2d.drawString(w19, column1, row19);
        g2d.drawString(w20, column1, row14);
        g2d.drawString(w21, column1, row11);
        g2d.drawString(w22, column1, row22);
        g2d.drawString(w23, column1, row27);
        g2d.drawString(w24, column1, row6);
        g2d.drawString(w25, column1, row7);
        g2d.drawString(w26, column1, row26);
        g2d.drawString(w27, column1, row23);
        g2d.drawString(w28, column1, row10);
        g2d.drawString(w29, column1, row15);
        g2d.drawString(w30, column1, row18);
        g2d.drawString(w31, column1, row31);
        g2d.drawString(w32, column1, row2);

        g2d.dispose();
    }

    private BufferedImage image;
    private final int CHAR_WIDTH = 29;
}
