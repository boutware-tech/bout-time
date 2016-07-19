/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2012  Jeffrey K. Rutt
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

package bouttime.gui.panel;

import bouttime.dao.Dao;
import bouttime.boutmaker.BoutMaker;
import bouttime.configuration.DoOperation;
import bouttime.configuration.PositionOnPage;
import bouttime.model.Group;
import java.awt.Frame;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.apache.log4j.Logger;

/**
 * A class to graphically edit the tournament configuration.
 */
public class ConfigDialogForm extends javax.swing.JDialog {
    static Logger logger = Logger.getLogger(ConfigDialogForm.class);

    /** Creates new form ConfigDialogForm */
    public ConfigDialogForm(JFrame parent, boolean modal, Dao dao) {
        super(parent, modal);
        this.parent = parent;
        this.dao = dao;
        this.cancelled = false;
        initComponents();
        setCurrentValues();
        setUIValues();
        this.setVisible(true);
    }

    /**
     * @return True if the user clicked the 'cancel' button.
     */
    public boolean isCancelled() {return this.cancelled;}

    /**
     * Set the initial/current values for the user interface components.
     */
    private void setUIValues() {
        if (this.dao == null) {
            return;
        }

        name.setText(dao.getName());
        site.setText(dao.getSite());
        city.setText(dao.getCity());
        state.setText(dao.getState());
        month.setSelectedItem(dao.getMonth());
        day.setSelectedItem(dao.getDay().toString());
        year.setSelectedItem(dao.getYear().toString());
        secondPlace.setSelected(dao.isSecondPlaceChallengeEnabled());
        fifthPlace.setSelected(dao.isFifthPlaceEnabled());
        roundRobin.setSelected(dao.isRoundRobinEnabled());
        roundRobinMax.setEnabled(roundRobin.isSelected());
        rrUpToLabel.setEnabled(roundRobin.isSelected());
        if (roundRobinMax.isEnabled()) {
            roundRobinMax.setSelectedItem(dao.getRoundRobinMax().toString());
        }
        maxAward.setSelectedItem(dao.getMaxAward().toString());

        classifications.setText(dao.getClassificationValues());
        divisions.setText(dao.getAgeDivisionValues());
        weightClasses.setText(dao.getWeightClassValues());
        mats.setText(dao.getMatValues());
        sessions.setText(dao.getSessionValues());
        boutTimes.setText(dao.getBoutTimeValues());
        watermark4Boutsheet.setText(dao.getBoutsheetWatermarkValues());
        watermark4Bracketsheet.setText(dao.getBracketsheetWatermarkValues());
        bracketsheetAwardImage.setText(dao.getBracketsheetAwardImage());
        
        DoOperation includeBracketsheetTimestamp = dao.getIncludeBracketsheetTimestamp();
        if (includeBracketsheetTimestamp == DoOperation.ALWAYS) {
            bracketsheetTimestampAlwaysRadioButton.setSelected(true);
        } else if (includeBracketsheetTimestamp == DoOperation.NEVER) {
            bracketsheetTimestampNeverRadioButton.setSelected(true);
        } else {
            bracketsheetTimestampPromptRadioButton.setSelected(true);
        }
        
        PositionOnPage brackesheetAwardImagePosition = dao.getBracketsheetAwardImagePosition();
        if (brackesheetAwardImagePosition == PositionOnPage.UPPER_RIGHT) {
            bracketsheetAwardImageUpperRightRadioButton.setSelected(true);
        } else if (brackesheetAwardImagePosition == PositionOnPage.CENTER) {
            bracketsheetAwardImageCenterRadioButton.setSelected(true);
        }
    }

    /**
     * Save these values before any changes have been made.
     * We need to track these to determine if we need to remake the bouts.
     */
    private void setCurrentValues() {
        oldFifthPlaceEnabled = dao.isFifthPlaceEnabled();
        oldSecondPlaceChallengeEnabled = dao.isSecondPlaceChallengeEnabled();
        oldRoundRobinEnabled = dao.isRoundRobinEnabled();
        oldRoundRobinMax = dao.getRoundRobinMax();
    }

    /**
     * Check if a configuration change will cause bouts for groups to be re-done.
     * Assumes caller will call dao.flush() to write to the persistent store.
     */
    private boolean handleBoutConfigChange() {
        boolean second = secondPlace.isSelected();
        boolean fifth = fifthPlace.isSelected();
        boolean rr = roundRobin.isSelected();
        int rrmax = Integer.valueOf((String)roundRobinMax.getSelectedItem());
        boolean rv = true;
        List<Group> gList = dao.getAllGroups();

        // secondPlaceChallengeEnabled hits nearly every group, so just do them all
        if (oldSecondPlaceChallengeEnabled != second) {
            if ((gList == null) || gList.isEmpty()) {
                logger.debug("No bouts to redo while altering 2nd place challenge enabled");
            } else if (hasLockedGroupFloor(gList, 1)) {
                JOptionPane.showMessageDialog(this.parent, "One or more affected groups is locked." +
                        "\nYou must unlock the affected group(s) before attempting this operation.",
                        "Edit Configuration Error", JOptionPane.ERROR_MESSAGE);
                logger.warn("Unable to redo bouts for 2nd place challenge change : locked group");
                rv = false;
            } else {
                int count = 0;
                for (Group g : gList) {
                    g.setBouts(null);
                    BoutMaker.makeBouts(g, fifth, second, rr, rrmax, dao.getDummyWrestler());
                    count++;
                }
                logger.debug("Recreated bouts for " + count + " groups : 2nd place challenge change");
            }
        } else {
            // We might double-process some groups.
            // We try to keep this at a minimum.
            if (oldFifthPlaceEnabled != fifth) {
                // only change groups with more than 5 wrestlers
                if ((gList == null) || gList.isEmpty()) {
                    logger.debug("No bouts to redo while altering 5th place enabled");
                } else if (hasLockedGroupFloor(gList, 6)) {
                    JOptionPane.showMessageDialog(this.parent, "One or more affected groups is locked." +
                            "\nYou must unlock the affected group(s) before attempting this operation.",
                            "Edit Configuration Error", JOptionPane.ERROR_MESSAGE);
                    logger.warn("Unable to redo bouts for 5th place change : locked group");
                    rv = false;
                } else {
                    int count = 0;
                    for (Group g : gList) {
                        if (g.getNumWrestlers() >= 6) {
                            g.setBouts(null);
                            BoutMaker.makeBouts(g, fifth, second, rr, rrmax, dao.getDummyWrestler());
                            count++;
                        }
                    }
                    logger.debug("Recreated bouts for " + count + " groups : 5th place change");
                }
            }

            // only change groups affected by changed round robin parameters
            if ((oldRoundRobinEnabled != rr)) {
                Integer limit = (oldRoundRobinEnabled) ? oldRoundRobinMax : rrmax;
                if ((gList == null) || gList.isEmpty()) {
                    logger.debug("No bouts to redo while altering round robin enabled");
                } else if (hasLockedGroupCeiling(gList, limit)) {
                    JOptionPane.showMessageDialog(this.parent, "One or more affected groups is locked." +
                            "\nYou must unlock the affected group(s) before attempting this operation.",
                            "Edit Configuration Error", JOptionPane.ERROR_MESSAGE);
                    logger.warn("Unable to redo bouts for round robin enabled change : locked group");
                    rv = false;
                } else {
                    int count = 0;
                    for (Group g : gList) {
                        if (g.getNumWrestlers() <= limit) {
                            g.setBouts(null);
                            BoutMaker.makeBouts(g, fifth, second, rr, rrmax, dao.getDummyWrestler());
                            count++;
                        }
                    }
                    logger.debug("Recreated bouts for " + count + " groups : round robin enabled change");
                }
            } else if (rr && (oldRoundRobinMax != rrmax)) {
                int limit = java.lang.Math.max(oldRoundRobinMax, rrmax);
                if ((gList == null) || gList.isEmpty()) {
                    logger.debug("No bouts to redo while altering round robin max");
                } else if (hasLockedGroupCeiling(gList, limit)) {
                    JOptionPane.showMessageDialog(this.parent, "One or more affected groups is locked." +
                            "\nYou must unlock the affected group(s) before attempting this operation.",
                            "Edit Configuration Error", JOptionPane.ERROR_MESSAGE);
                    logger.warn("Unable to redo bouts for round robin max change : locked group");
                    rv = false;
                } else {
                    int count = 0;
                    for (Group g : gList) {
                        if (g.getNumWrestlers() <= limit) {
                            g.setBouts(null);
                            BoutMaker.makeBouts(g, fifth, second, rr, rrmax, dao.getDummyWrestler());
                            count++;
                        }
                    }
                    logger.debug("Recreated bouts for " + count + " groups : round robin max change");
                }
            }
        }
        return rv;
    }

    /**
     * Save the values from the GUI to the Dao.
     */
    @Action
    public void saveAction() {
        if (!handleBoutConfigChange()) {
            setUIValues();
            return;
        }

        this.dao.setName(name.getText());
        this.dao.setSite(site.getText());
        this.dao.setCity(city.getText());
        this.dao.setState(state.getText());
        this.dao.setMonth((String)month.getSelectedItem());
        this.dao.setDay(Integer.valueOf((String)day.getSelectedItem()));
        this.dao.setYear(Integer.valueOf((String)year.getSelectedItem()));
        this.dao.setSecondPlaceChallengeEnabled(secondPlace.isSelected());
        this.dao.setFifthPlaceEnabled(fifthPlace.isSelected());
        this.dao.setRoundRobinEnabled(roundRobin.isSelected());
        if (roundRobin.isSelected()) {
            this.dao.setRoundRobinMax(Integer.valueOf((String)roundRobinMax.getSelectedItem()));
        }
        this.dao.setMaxAward(Integer.valueOf((String)maxAward.getSelectedItem()));

        this.dao.setClassificationValues(classifications.getText());
        this.dao.setAgeDivisionValues(divisions.getText());
        this.dao.setWeightClassValues(weightClasses.getText());
        this.dao.setMatValues(mats.getText());
        this.dao.setSessionValues(sessions.getText());
        this.dao.setBoutTimeValues(boutTimes.getText());
        this.dao.setBoutsheetWatermarkValues(watermark4Boutsheet.getText());
        this.dao.setBracketsheetWatermarkValues(watermark4Bracketsheet.getText());
        
        DoOperation includeBracketsheetTimestamp = DoOperation.PROMPT;
        if (bracketsheetTimestampNeverRadioButton.isSelected()) {
            includeBracketsheetTimestamp = DoOperation.NEVER;
        } else if (bracketsheetTimestampAlwaysRadioButton.isSelected()) {
            includeBracketsheetTimestamp = DoOperation.ALWAYS;
        }
        this.dao.setIncludeBracketsheetTimestamp(includeBracketsheetTimestamp);
        
        this.dao.setBracketsheetAwardImage(bracketsheetAwardImage.getText());
        
        PositionOnPage bracketsheetAwardImagePosition;
        if (bracketsheetAwardImageCenterRadioButton.isSelected()) {
            bracketsheetAwardImagePosition = PositionOnPage.CENTER;
        } else {
            // Only allow CENTER or UPPER_RIGHT
            bracketsheetAwardImagePosition = PositionOnPage.UPPER_RIGHT;
        }
        this.dao.setBracketsheetAwardImagePosition(bracketsheetAwardImagePosition);
        
        this.dao.flush();

        logger.info("***** Configuration changed *****\n" +
                "    Name=" + this.dao.getName() + "\n" +
                "    Site=" + this.dao.getSite() + "\n" +
                "    City=" + this.dao.getCity() + "\n" +
                "    State=" + this.dao.getState() + "\n" +
                "    Month=" + this.dao.getMonth() + "\n" +
                "    Day=" + this.dao.getDay() + "\n" +
                "    Year=" + this.dao.getYear() + "\n" +
                "    2nd=" + this.dao.isSecondPlaceChallengeEnabled() + "\n" +
                "    5th=" + this.dao.isFifthPlaceEnabled() + "\n" +
                "    RoundRobin=" + this.dao.isRoundRobinEnabled() + "\n" +
                "    RoundRobinMax=" + this.dao.getRoundRobinMax() + "\n" +
                "    MaxAward=" + this.dao.getMaxAward() + "\n" +
                "    Classes=" + this.dao.getClassificationValues() + "\n" +
                "    Divisions=" + this.dao.getAgeDivisionValues() + "\n" +
                "    Weights=" + this.dao.getWeightClassValues() + "\n" +
                "    Mats=" + this.dao.getMatValues() + "\n" +
                "    Sessions=" + this.dao.getSessionValues() + "\n" +
                "    Times=" + this.dao.getBoutTimeValues() + "\n" +
                "    BoutsWM=" + this.dao.getBoutsheetWatermarkValues() + "\n" +
                "    BracketsWM=" + this.dao.getBracketsheetWatermarkValues() + "\n" +
                "    BracketsAwardImage=" + this.dao.getBracketsheetAwardImage() + "\n" +
                "*****");

        this.setVisible(false);
    }

    /**
     * Remove the dialog form and do not make any changes to the tournament configuration.
     */
    @Action
    public void cancelAction() {
        this.cancelled = true;
        this.setVisible(false);
    }

    /**
     * Display a file chooser window for the user to select a file to use for
     * the bracketsheet award image.
     */
    @Action
    public void bracketsheetAwardImageOpenFile() {
        JFileChooser infile = new JFileChooser();
        JFrame mainFrame = bouttime.mainview.BoutTimeApp.getApplication().getMainFrame();

        if (infile.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            bracketsheetAwardImage.setText(infile.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * Check if at least one group in the list that meets the criteria is locked
     * @param list List of Group objects to check
     * @param ceiling highest number of wrestlers in group to check
     * @return true if at least one group in the list is locked, false otherwise
     */
    private boolean hasLockedGroupCeiling(List<Group> list, int ceiling) {
        for (Group g : list) {
            if (g.isLocked() && (g.getNumWrestlers() <= ceiling)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if at least one group in the list that meets the criteria is locked
     * @param list List of Group objects to check
     * @param floor lowest number of wrestlers in group to check
     * @return true if at least one group in the list is locked, false otherwise
     */
    private boolean hasLockedGroupFloor(List<Group> list, int floor) {
        for (Group g : list) {
            if (g.isLocked() && (g.getNumWrestlers() >= floor)) {
                return true;
            }
        }

        return false;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bracketsheetTimestampButtonGroup = new javax.swing.ButtonGroup();
        awardImagePositionButtonGroup = new javax.swing.ButtonGroup();
        saveButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        generalPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        siteLabel = new javax.swing.JLabel();
        site = new javax.swing.JTextField();
        cityLabel = new javax.swing.JLabel();
        city = new javax.swing.JTextField();
        stateLabel = new javax.swing.JLabel();
        state = new javax.swing.JTextField();
        monthLabel = new javax.swing.JLabel();
        month = new javax.swing.JComboBox();
        dayLabel = new javax.swing.JLabel();
        day = new javax.swing.JComboBox();
        yearLabel = new javax.swing.JLabel();
        year = new javax.swing.JComboBox();
        fifthPlace = new javax.swing.JCheckBox();
        secondPlace = new javax.swing.JCheckBox();
        roundRobin = new javax.swing.JCheckBox();
        rrUpToLabel = new javax.swing.JLabel();
        roundRobinMax = new javax.swing.JComboBox();
        maxAwardLabel = new javax.swing.JLabel();
        maxAward = new javax.swing.JComboBox();
        valuesPanel = new javax.swing.JPanel();
        classificationLabel = new javax.swing.JLabel();
        classifications = new javax.swing.JTextField();
        divLabel = new javax.swing.JLabel();
        divisions = new javax.swing.JTextField();
        weightClassLabel = new javax.swing.JLabel();
        weightClasses = new javax.swing.JTextField();
        matLabel = new javax.swing.JLabel();
        mats = new javax.swing.JTextField();
        sessionLabel = new javax.swing.JLabel();
        sessions = new javax.swing.JTextField();
        boutTimeLabel = new javax.swing.JLabel();
        boutTimes = new javax.swing.JTextField();
        boutsheetWMLabel = new javax.swing.JLabel();
        watermark4Boutsheet = new javax.swing.JTextField();
        bracketsheetWMLabel = new javax.swing.JLabel();
        watermark4Bracketsheet = new javax.swing.JTextField();
        reportsPanel = new javax.swing.JPanel();
        bracketsheetTimestampLabel = new javax.swing.JLabel();
        bracketsheetTimestampAlwaysRadioButton = new javax.swing.JRadioButton();
        bracketsheetTimestampNeverRadioButton = new javax.swing.JRadioButton();
        bracketsheetTimestampPromptRadioButton = new javax.swing.JRadioButton();
        bracketsheetAwardImageLabel = new javax.swing.JLabel();
        bracketsheetAwardImage = new javax.swing.JTextField();
        browseBracketsheetAwardImageButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        bracketSheetAwardImageDescTextArea = new javax.swing.JTextArea();
        bracketsheetAwardImagePositionLabel = new javax.swing.JLabel();
        bracketsheetAwardImageUpperRightRadioButton = new javax.swing.JRadioButton();
        bracketsheetAwardImageCenterRadioButton = new javax.swing.JRadioButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getActionMap(ConfigDialogForm.class, this);
        saveButton.setAction(actionMap.get("saveAction")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(ConfigDialogForm.class);
        saveButton.setText(resourceMap.getString("saveButton.text")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        generalPanel.setName("generalPanel"); // NOI18N

        nameLabel.setText(resourceMap.getString("nameLabel.text")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N

        name.setName("name"); // NOI18N

        siteLabel.setText(resourceMap.getString("siteLabel.text")); // NOI18N
        siteLabel.setName("siteLabel"); // NOI18N

        site.setName("site"); // NOI18N

        cityLabel.setText(resourceMap.getString("cityLabel.text")); // NOI18N
        cityLabel.setName("cityLabel"); // NOI18N

        city.setName("city"); // NOI18N

        stateLabel.setText(resourceMap.getString("stateLabel.text")); // NOI18N
        stateLabel.setName("stateLabel"); // NOI18N

        state.setName("state"); // NOI18N

        monthLabel.setText(resourceMap.getString("monthLabel.text")); // NOI18N
        monthLabel.setName("monthLabel"); // NOI18N

        month.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        month.setName("month"); // NOI18N

        dayLabel.setText(resourceMap.getString("dayLabel.text")); // NOI18N
        dayLabel.setName("dayLabel"); // NOI18N

        day.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        day.setName("day"); // NOI18N

        yearLabel.setText(resourceMap.getString("yearLabel.text")); // NOI18N
        yearLabel.setName("yearLabel"); // NOI18N

        year.setEditable(true);
        year.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020" }));
        year.setName("year"); // NOI18N

        fifthPlace.setText(resourceMap.getString("fifthPlace.text")); // NOI18N
        fifthPlace.setName("fifthPlace"); // NOI18N

        secondPlace.setText(resourceMap.getString("secondPlace.text")); // NOI18N
        secondPlace.setName("secondPlace"); // NOI18N

        roundRobin.setText(resourceMap.getString("roundRobin.text")); // NOI18N
        roundRobin.setName("roundRobin"); // NOI18N
        roundRobin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                roundRobinStateChanged(evt);
            }
        });

        rrUpToLabel.setText(resourceMap.getString("rrUpToLabel.text")); // NOI18N
        rrUpToLabel.setEnabled(false);
        rrUpToLabel.setName("rrUpToLabel"); // NOI18N

        roundRobinMax.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "3", "4", "5" }));
        roundRobinMax.setEnabled(false);
        roundRobinMax.setName("roundRobinMax"); // NOI18N

        maxAwardLabel.setText(resourceMap.getString("maxAwardLabel.text")); // NOI18N
        maxAwardLabel.setName("maxAwardLabel"); // NOI18N

        maxAward.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7" }));
        maxAward.setName("maxAward"); // NOI18N

        javax.swing.GroupLayout generalPanelLayout = new javax.swing.GroupLayout(generalPanel);
        generalPanel.setLayout(generalPanelLayout);
        generalPanelLayout.setHorizontalGroup(
            generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(siteLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cityLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stateLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(monthLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dayLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(yearLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(city)
                        .addComponent(site)
                        .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                        .addComponent(state))
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(day, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fifthPlace)
                            .addComponent(secondPlace)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(roundRobin)
                                .addGap(18, 18, 18)
                                .addComponent(rrUpToLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(roundRobinMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(maxAwardLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(maxAward, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        generalPanelLayout.setVerticalGroup(
            generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(siteLabel)
                    .addComponent(site, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cityLabel)
                    .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stateLabel)
                    .addComponent(state, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monthLabel)
                            .addComponent(month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dayLabel)
                            .addComponent(day, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(yearLabel)
                            .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(fifthPlace)
                        .addGap(18, 18, 18)
                        .addComponent(secondPlace)
                        .addGap(18, 18, 18)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(roundRobin)
                            .addComponent(rrUpToLabel)
                            .addComponent(roundRobinMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maxAwardLabel)
                    .addComponent(maxAward, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(115, 115, 115))
        );

        jTabbedPane1.addTab(resourceMap.getString("generalPanel.TabConstraints.tabTitle"), generalPanel); // NOI18N

        valuesPanel.setName("valuesPanel"); // NOI18N

        classificationLabel.setText(resourceMap.getString("classificationLabel.text")); // NOI18N
        classificationLabel.setName("classificationLabel"); // NOI18N

        classifications.setToolTipText(resourceMap.getString("classifications.toolTipText")); // NOI18N
        classifications.setName("classifications"); // NOI18N

        divLabel.setText(resourceMap.getString("divLabel.text")); // NOI18N
        divLabel.setName("divLabel"); // NOI18N

        divisions.setToolTipText(resourceMap.getString("divisions.toolTipText")); // NOI18N
        divisions.setName("divisions"); // NOI18N

        weightClassLabel.setText(resourceMap.getString("weightClassLabel.text")); // NOI18N
        weightClassLabel.setName("weightClassLabel"); // NOI18N

        weightClasses.setToolTipText(resourceMap.getString("weightClasses.toolTipText")); // NOI18N
        weightClasses.setName("weightClasses"); // NOI18N

        matLabel.setText(resourceMap.getString("matLabel.text")); // NOI18N
        matLabel.setName("matLabel"); // NOI18N

        mats.setToolTipText(resourceMap.getString("mats.toolTipText")); // NOI18N
        mats.setName("mats"); // NOI18N

        sessionLabel.setText(resourceMap.getString("sessionLabel.text")); // NOI18N
        sessionLabel.setName("sessionLabel"); // NOI18N

        sessions.setToolTipText(resourceMap.getString("sessions.toolTipText")); // NOI18N
        sessions.setName("sessions"); // NOI18N

        boutTimeLabel.setText(resourceMap.getString("boutTimeLabel.text")); // NOI18N
        boutTimeLabel.setName("boutTimeLabel"); // NOI18N

        boutTimes.setToolTipText(resourceMap.getString("boutTimes.toolTipText")); // NOI18N
        boutTimes.setName("boutTimes"); // NOI18N

        boutsheetWMLabel.setText(resourceMap.getString("boutsheetWMLabel.text")); // NOI18N
        boutsheetWMLabel.setName("boutsheetWMLabel"); // NOI18N

        watermark4Boutsheet.setText(resourceMap.getString("watermark4Boutsheet.text")); // NOI18N
        watermark4Boutsheet.setToolTipText(resourceMap.getString("watermark4Boutsheet.toolTipText")); // NOI18N
        watermark4Boutsheet.setName("watermark4Boutsheet"); // NOI18N

        bracketsheetWMLabel.setText(resourceMap.getString("bracketsheetWMLabel.text")); // NOI18N
        bracketsheetWMLabel.setName("bracketsheetWMLabel"); // NOI18N

        watermark4Bracketsheet.setText(resourceMap.getString("watermark4Bracketsheet.text")); // NOI18N
        watermark4Bracketsheet.setToolTipText(resourceMap.getString("watermark4Bracketsheet.toolTipText")); // NOI18N
        watermark4Bracketsheet.setName("watermark4Bracketsheet"); // NOI18N

        javax.swing.GroupLayout valuesPanelLayout = new javax.swing.GroupLayout(valuesPanel);
        valuesPanel.setLayout(valuesPanelLayout);
        valuesPanelLayout.setHorizontalGroup(
            valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(valuesPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bracketsheetWMLabel)
                    .addComponent(boutsheetWMLabel)
                    .addComponent(classificationLabel)
                    .addComponent(divLabel)
                    .addComponent(weightClassLabel)
                    .addComponent(matLabel)
                    .addComponent(sessionLabel)
                    .addComponent(boutTimeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(watermark4Bracketsheet)
                    .addComponent(watermark4Boutsheet)
                    .addComponent(mats)
                    .addComponent(divisions)
                    .addComponent(weightClasses, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                    .addComponent(boutTimes)
                    .addComponent(sessions)
                    .addComponent(classifications, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        valuesPanelLayout.setVerticalGroup(
            valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(valuesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(classificationLabel)
                    .addComponent(classifications, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(divLabel)
                    .addComponent(divisions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weightClassLabel)
                    .addComponent(weightClasses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(matLabel)
                    .addComponent(mats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sessionLabel)
                    .addComponent(sessions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boutTimeLabel)
                    .addComponent(boutTimes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boutsheetWMLabel)
                    .addComponent(watermark4Boutsheet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(valuesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bracketsheetWMLabel)
                    .addComponent(watermark4Bracketsheet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(resourceMap.getString("valuesPanel.TabConstraints.tabTitle"), valuesPanel); // NOI18N

        reportsPanel.setBackground(resourceMap.getColor("reportsPanel.background")); // NOI18N
        reportsPanel.setName("reportsPanel"); // NOI18N

        bracketsheetTimestampLabel.setText(resourceMap.getString("bracketsheetTimestampLabel.text")); // NOI18N
        bracketsheetTimestampLabel.setName("bracketsheetTimestampLabel"); // NOI18N

        bracketsheetTimestampButtonGroup.add(bracketsheetTimestampAlwaysRadioButton);
        bracketsheetTimestampAlwaysRadioButton.setText(resourceMap.getString("bracketsheetTimestampAlwaysRadioButton.text")); // NOI18N
        bracketsheetTimestampAlwaysRadioButton.setName("bracketsheetTimestampAlwaysRadioButton"); // NOI18N

        bracketsheetTimestampButtonGroup.add(bracketsheetTimestampNeverRadioButton);
        bracketsheetTimestampNeverRadioButton.setText(resourceMap.getString("bracketsheetTimestampNeverRadioButton.text")); // NOI18N
        bracketsheetTimestampNeverRadioButton.setName("bracketsheetTimestampNeverRadioButton"); // NOI18N

        bracketsheetTimestampButtonGroup.add(bracketsheetTimestampPromptRadioButton);
        bracketsheetTimestampPromptRadioButton.setText(resourceMap.getString("bracketsheetTimestampPromptRadioButton.text")); // NOI18N
        bracketsheetTimestampPromptRadioButton.setName("bracketsheetTimestampPromptRadioButton"); // NOI18N

        bracketsheetAwardImageLabel.setText(resourceMap.getString("bracketsheetAwardImageLabel.text")); // NOI18N
        bracketsheetAwardImageLabel.setName("bracketsheetAwardImageLabel"); // NOI18N

        bracketsheetAwardImage.setText(resourceMap.getString("bracketsheetAwardImage.text")); // NOI18N
        bracketsheetAwardImage.setName("bracketsheetAwardImage"); // NOI18N

        browseBracketsheetAwardImageButton.setAction(actionMap.get("bracketsheetAwardImageOpenFile")); // NOI18N
        browseBracketsheetAwardImageButton.setText(resourceMap.getString("browseBracketsheetAwardImageButton.text")); // NOI18N
        browseBracketsheetAwardImageButton.setName("browseBracketsheetAwardImageButton"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        bracketSheetAwardImageDescTextArea.setColumns(20);
        bracketSheetAwardImageDescTextArea.setEditable(false);
        bracketSheetAwardImageDescTextArea.setLineWrap(true);
        bracketSheetAwardImageDescTextArea.setRows(3);
        bracketSheetAwardImageDescTextArea.setText("The bracketsheet award image is optional and used in \"Award\" style bracket sheets.  If position is \"Upper Right\", the image should be no larger than 142x142 pixels in order to fit nicely on all bracket sizes.  If the position is \"Center\", the image will be displayed in the background."); // NOI18N
        bracketSheetAwardImageDescTextArea.setWrapStyleWord(true);
        bracketSheetAwardImageDescTextArea.setName("bracketSheetAwardImageDescTextArea"); // NOI18N
        jScrollPane1.setViewportView(bracketSheetAwardImageDescTextArea);

        bracketsheetAwardImagePositionLabel.setText(resourceMap.getString("bracketsheetAwardImagePositionLabel.text")); // NOI18N
        bracketsheetAwardImagePositionLabel.setName("bracketsheetAwardImagePositionLabel"); // NOI18N

        awardImagePositionButtonGroup.add(bracketsheetAwardImageUpperRightRadioButton);
        bracketsheetAwardImageUpperRightRadioButton.setText(resourceMap.getString("bracketsheetAwardImageUpperRightRadioButton.text")); // NOI18N
        bracketsheetAwardImageUpperRightRadioButton.setName("bracketsheetAwardImageUpperRightRadioButton"); // NOI18N

        awardImagePositionButtonGroup.add(bracketsheetAwardImageCenterRadioButton);
        bracketsheetAwardImageCenterRadioButton.setText(resourceMap.getString("bracketsheetAwardImageCenterRadioButton.text")); // NOI18N
        bracketsheetAwardImageCenterRadioButton.setName("bracketsheetAwardImageCenterRadioButton"); // NOI18N

        javax.swing.GroupLayout reportsPanelLayout = new javax.swing.GroupLayout(reportsPanel);
        reportsPanel.setLayout(reportsPanelLayout);
        reportsPanelLayout.setHorizontalGroup(
            reportsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(reportsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                    .addGroup(reportsPanelLayout.createSequentialGroup()
                        .addComponent(bracketsheetTimestampLabel)
                        .addGap(18, 18, 18)
                        .addComponent(bracketsheetTimestampAlwaysRadioButton)
                        .addGap(18, 18, 18)
                        .addComponent(bracketsheetTimestampNeverRadioButton)
                        .addGap(18, 18, 18)
                        .addComponent(bracketsheetTimestampPromptRadioButton)
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportsPanelLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportsPanelLayout.createSequentialGroup()
                        .addComponent(bracketsheetAwardImageLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bracketsheetAwardImage, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseBracketsheetAwardImageButton)
                        .addContainerGap())
                    .addGroup(reportsPanelLayout.createSequentialGroup()
                        .addComponent(bracketsheetAwardImagePositionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bracketsheetAwardImageUpperRightRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bracketsheetAwardImageCenterRadioButton)
                        .addContainerGap(128, Short.MAX_VALUE))))
        );
        reportsPanelLayout.setVerticalGroup(
            reportsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(reportsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bracketsheetTimestampLabel)
                    .addComponent(bracketsheetTimestampAlwaysRadioButton)
                    .addComponent(bracketsheetTimestampNeverRadioButton)
                    .addComponent(bracketsheetTimestampPromptRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reportsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(browseBracketsheetAwardImageButton)
                    .addComponent(bracketsheetAwardImageLabel)
                    .addComponent(bracketsheetAwardImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(reportsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bracketsheetAwardImagePositionLabel)
                    .addComponent(bracketsheetAwardImageUpperRightRadioButton)
                    .addComponent(bracketsheetAwardImageCenterRadioButton))
                .addContainerGap(181, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(resourceMap.getString("reportsPanel.TabConstraints.tabTitle"), reportsPanel); // NOI18N

        cancelButton.setAction(actionMap.get("cancelAction")); // NOI18N
        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 473, Short.MAX_VALUE)
                .addComponent(cancelButton)
                .addContainerGap())
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void roundRobinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_roundRobinStateChanged
        roundRobinMax.setEnabled(roundRobin.isSelected());
        rrUpToLabel.setEnabled(roundRobin.isSelected());
    }//GEN-LAST:event_roundRobinStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup awardImagePositionButtonGroup;
    private javax.swing.JLabel boutTimeLabel;
    private javax.swing.JTextField boutTimes;
    private javax.swing.JLabel boutsheetWMLabel;
    private javax.swing.JTextArea bracketSheetAwardImageDescTextArea;
    private javax.swing.JTextField bracketsheetAwardImage;
    private javax.swing.JRadioButton bracketsheetAwardImageCenterRadioButton;
    private javax.swing.JLabel bracketsheetAwardImageLabel;
    private javax.swing.JLabel bracketsheetAwardImagePositionLabel;
    private javax.swing.JRadioButton bracketsheetAwardImageUpperRightRadioButton;
    private javax.swing.JRadioButton bracketsheetTimestampAlwaysRadioButton;
    private javax.swing.ButtonGroup bracketsheetTimestampButtonGroup;
    private javax.swing.JLabel bracketsheetTimestampLabel;
    private javax.swing.JRadioButton bracketsheetTimestampNeverRadioButton;
    private javax.swing.JRadioButton bracketsheetTimestampPromptRadioButton;
    private javax.swing.JLabel bracketsheetWMLabel;
    private javax.swing.JButton browseBracketsheetAwardImageButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField city;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JLabel classificationLabel;
    private javax.swing.JTextField classifications;
    private javax.swing.JComboBox day;
    private javax.swing.JLabel dayLabel;
    private javax.swing.JLabel divLabel;
    private javax.swing.JTextField divisions;
    private javax.swing.JCheckBox fifthPlace;
    private javax.swing.JPanel generalPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel matLabel;
    private javax.swing.JTextField mats;
    private javax.swing.JComboBox maxAward;
    private javax.swing.JLabel maxAwardLabel;
    private javax.swing.JComboBox month;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JTextField name;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPanel reportsPanel;
    private javax.swing.JCheckBox roundRobin;
    private javax.swing.JComboBox roundRobinMax;
    private javax.swing.JLabel rrUpToLabel;
    private javax.swing.JButton saveButton;
    private javax.swing.JCheckBox secondPlace;
    private javax.swing.JLabel sessionLabel;
    private javax.swing.JTextField sessions;
    private javax.swing.JTextField site;
    private javax.swing.JLabel siteLabel;
    private javax.swing.JTextField state;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JPanel valuesPanel;
    private javax.swing.JTextField watermark4Boutsheet;
    private javax.swing.JTextField watermark4Bracketsheet;
    private javax.swing.JLabel weightClassLabel;
    private javax.swing.JTextField weightClasses;
    private javax.swing.JComboBox year;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables

    private Frame parent;
    private Dao dao;
    private boolean cancelled;
    private Boolean oldFifthPlaceEnabled;
    private Boolean oldSecondPlaceChallengeEnabled;
    private Boolean oldRoundRobinEnabled;
    private Integer oldRoundRobinMax;
}
