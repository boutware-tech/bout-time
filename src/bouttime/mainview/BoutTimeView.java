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
package bouttime.mainview;

import bouttime.boutmaker.BoutMaker;
import bouttime.configuration.DoOperation;
import bouttime.dao.Dao;
import bouttime.dao.DaoFactory;
import bouttime.fileoutput.TextFileOutput;
import bouttime.gui.panel.AutoBouterMatDialogForm;
import bouttime.gui.panel.ConfigDialogForm;
import bouttime.model.Bout;
import bouttime.model.Wrestler;
import bouttime.model.Group;
import bouttime.report.award.AwardInventoryReport;
import bouttime.report.award.AwardReport;
import bouttime.report.boutsequence.BoutSequenceReport;
import bouttime.report.boutsheet.BoutSheetReport;
import bouttime.report.bracketsheet.Bracket16BracketSheetReport;
import bouttime.report.bracketsheet.Bracket2BracketSheetReport;
import bouttime.report.bracketsheet.Bracket32BracketSheetReport;
import bouttime.report.bracketsheet.Bracket4BracketSheetReport;
import bouttime.report.bracketsheet.Bracket8BracketSheetReport;
import bouttime.report.bracketsheet.BracketSheetReport;
import bouttime.report.bracketsheet.RoundRobinBracketSheetReport;
import bouttime.report.matkey.MatKeyReport;
import bouttime.report.team.TeamReport;
import bouttime.report.weighin.WeighInReport;
import bouttime.sort.BoutNumSort;
import bouttime.sort.GroupClassDivWtSort;
import bouttime.sort.WrestlerAlphaSort;
import bouttime.utility.seed.RandomSeed;
import bouttime.wizard.fileinput.excel.ExcelInputWizard;
import bouttime.wizard.fileinput.text.TextInputWizard;
import bouttime.wizard.fileinput.xml.XmlInputWizard;
import bouttime.wizard.newtournament.NewTournamentWizard;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * The application's main frame.
 */
public class BoutTimeView extends FrameView {

    public BoutTimeView(SingleFrameApplication app) {
        super(app);

        ResourceMap rm = Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(BoutTimeView.class);
        this.dao = DaoFactory.getDao(rm.getString("daoType"));

        logger.info("***** Opening BoutTimeView *****");
        logger.info("DAO : " + this.dao.getClass());

        initComponents();

        this.blankBracketOutputDirectory = rm.getString("blankBracket.report.dir");
        this.blankBracketOutputFilePath = String.format("%s/%s", this.blankBracketOutputDirectory,
                rm.getString("blankBracket.report.filename"));

        WelcomeDialog welcome = new WelcomeDialog(this.getFrame(), true);
        this.initAction = welcome.getInitAction();
        logger.debug("initAction is " + this.initAction);

        if (this.initAction == BOUTTIMEVIEW_INIT_ACTION_QUIT) {
            logger.info("***** Closing BoutTimeView *****");
            System.exit(0);
        }
    }

    protected void doInitAction() {
        logger.trace("BEGIN");
        if (this.initAction == BOUTTIMEVIEW_INIT_ACTION_OPEN_EXISTING) {
            openExistingDaoFile();
        } else if (this.initAction == BOUTTIMEVIEW_INIT_ACTION_OPEN_NEW) {
            openNewDaoFile();
        }
        logger.trace("END");
    }

    protected void shutdown() {
        logger.trace("BEGIN");
        if (dao.isOpen()) {
            dao.flush();
        }
        logger.trace("END");
        logger.info("***** Closing BoutTimeView *****");
    }

    public Dao getDao() {
        return this.dao;
    }
    
    public void updateFreeList() {
        freeListPanel.updateList();
    }

    public void updateScratchList() {
        scratchListPanel.updateList();
    }

    public void updateGroupList() {
        masterListPanel.updateList();
        groupPanel.updateGroupList();
    }
    
    public void groupDeleted() {
        masterListPanel.updateList();
    }

    public void updateGroup(Group g) {
        masterListPanel.updateList();
        groupPanel.groupChanged(g);
    }

    public void groupSelectionChanged(Group g) {
        groupPanel.groupSelectionChanged(g);
    }

    public void refreshStats() {
        groupPanel.refreshStats();
    }

    public boolean isDaoOpen() {
        return dao.isOpen();
    }

    public boolean includeTimestampForBracketsheet() {
        boolean rv;

        DoOperation value = dao.getIncludeBracketsheetTimestamp();
        switch (value) {
            case ALWAYS:
                rv = true;
                break;
            case NEVER:
                rv = false;
                break;
            case PROMPT:
                int val = JOptionPane.showConfirmDialog(
                        this.getFrame(),
                        "Would you like to include the timestamp?",
                        "Include Timestamp?",
                        JOptionPane.YES_NO_OPTION);

                rv = (val == JOptionPane.YES_OPTION);
                break;
            default:
                logger.error("Unnexpected value for bracketsheet timestamp configuration : " + value.toString());
                rv = true;
                break;
        }

        return rv;
    }

    /**
     * Automatically group ungrouped wrestlers by Classification, Age Division,
     * and Weight Class.
     */
    @Action(enabledProperty = "daoOpen")
    public void autoGroup() {
        logger.trace("BEGIN");
        List<Wrestler> fList = dao.getUngroupedWrestlers();
        if (fList.isEmpty()) {
            logger.info("Cannot do auto group : ungrouped wrestler list is empty");
            return; // do nothing
        }

        Set<String> sSet = new HashSet<String>();

        // Gather all unique "Classification:AgeDivision:WeightClass" combos.
        for (Wrestler w : fList) {
            sSet.add(String.format("%s:%s:%s", w.getClassification(),
                    w.getAgeDivision(), w.getWeightClass()));
        }

        // For each "Classification:AgeDivision:WeightClass" , find the
        // the wrestlers that match this, create a group, and put the
        // wrestlers in this group.
        for (String s : sSet) {
            // Get the list wrestlers that match.
            String[] tokens = s.split(":");
            List<Wrestler> wList = dao.getWrestlersByClassDivWeight(tokens[0],
                    tokens[1], tokens[2]);

            if (wList.isEmpty()) {
                logger.warn("empty list : class=" + tokens[0] + "  div=" + tokens[1] + "  weight=" + tokens[2]);
                continue;
            }

            // Create a group
            Group g = new Group(wList);
            RandomSeed.execute(g);
            dao.addGroup(g);
            BoutMaker.makeBouts(g, dao.isFifthPlaceEnabled(), dao.isSecondPlaceChallengeEnabled(),
                    dao.isRoundRobinEnabled(), dao.getRoundRobinMax(), dao.getDummyWrestler());
        }

        groupPanel.updateGroupList();
        groupPanel.refreshStats();
        freeListPanel.updateList();

        dao.flush();
        logger.trace("END");
    }

    /**
     * Run the auto-bouter with unique bout numbers per mat.
     */
    @Action(enabledProperty = "daoOpen")
    public void autoboutByMat() {
        logger.trace("BEGIN");
        // Show dialog form to enter new configuration
        AutoBouterMatDialogForm form = new AutoBouterMatDialogForm(this.getFrame(), true, dao);

        if (form.isCancelled()) {
            logger.trace("END : form is cancelled");
            return;
        }

        logger.trace("END");

        updateGroupList();
    }

    /**
     * Open an existing persistent storage file.
     */
    @Action
    public void openExistingDaoFile() {
        logger.trace("BEGIN");
        if (!dao.openExisting()) {
            logger.info("failed to open an existing file");
            return;
        }

        firePropertyChange("daoOpen", false, true);
        masterListPanel.daoPropertyChange();
        masterListPanel.updateList();
        freeListPanel.daoPropertyChange();
        scratchListPanel.updateList();
        groupPanel.daoPropertyChange();
        groupPanel.refreshStats();
        boutPanel.daoPropertyChange();
        logger.trace("END");
    }

    /**
     * Open a new persistent storage file.
     */
    @Action
    public void openNewDaoFile() {
        logger.trace("BEGIN");
        NewTournamentWizard wizard = new NewTournamentWizard(this.dao);
        wizard.runWizard();

        if (!isDaoOpen()) {
            logger.info("DAO is not open after running new tournament wizard");
            return;
        }

        firePropertyChange("daoOpen", false, true);
        masterListPanel.daoPropertyChange();
        masterListPanel.updateList();
        freeListPanel.daoPropertyChange();
        groupPanel.daoPropertyChange();
        boutPanel.daoPropertyChange();
        logger.trace("END");
    }

    /**
     * Run the wizard to input wrestler entries from a MS Excel formatted file.
     */
    @Action(enabledProperty = "daoOpen")
    public void inputFromExcelFile() {
        logger.trace("BEGIN");
        ExcelInputWizard wizard = new ExcelInputWizard(this.dao);
        wizard.runWizard();

        masterListPanel.updateList();
        freeListPanel.updateList();
        logger.trace("END");
    }

    /**
     * Run the wizard to input wrestler entries from a text file.
     */
    @Action(enabledProperty = "daoOpen")
    public void inputFromTextFile() {
        logger.trace("BEGIN");
        TextInputWizard wizard = new TextInputWizard(this.dao);
        wizard.runWizard();

        masterListPanel.updateList();
        freeListPanel.updateList();
        logger.trace("END");
    }

    /**
     * Run the wizard to input wrestler entries from an XML file.
     */
    @Action(enabledProperty = "daoOpen")
    public void inputFromXmlFile() {
        logger.trace("BEGIN");
        XmlInputWizard wizard = new XmlInputWizard(this.dao);
        wizard.runWizard();

        masterListPanel.updateList();
        freeListPanel.updateList();
        logger.trace("END");
    }

    /**
     * Output (export) all the wrestlers to a text file.
     */
    @Action(enabledProperty = "daoOpen")
    public void outputWrestlersToTextFile() {
        logger.trace("BEGIN");
        TextFileOutput output = new TextFileOutput();
        output.outputAllWrestlers(this.dao);
        logger.trace("END");
    }

    /**
     * Show the dialog to edit the tournament's configuration values.
     */
    @Action(enabledProperty = "daoOpen")
    public void editConfiguration() {
        logger.trace("BEGIN");
        // Show dialog form to enter new configuration
        ConfigDialogForm form = new ConfigDialogForm(this.getFrame(), true, dao);

        if (form.isCancelled()) {
            logger.trace("END : form is cancelled");
            return;
        }

        groupPanel.daoPropertyChange();
        freeListPanel.daoPropertyChange();
        boutPanel.daoPropertyChange();
        logger.trace("END");
    }

    /**
     * Show the "about" window.
     */
    @Action
    public void showAboutBox() {
        logger.trace("BEGIN");
        if (aboutBox == null) {
            aboutBox = new BoutTimeAboutBox(this.getFrame());
            aboutBox.setLocationRelativeTo(this.getFrame());
        }
        BoutTimeApp.getApplication().show(aboutBox);
        logger.trace("END");
    }

    private FileOutputStream createBlankBracketSheetReportOutputFile() {
        logger.trace("BEGIN");
        File tmpDir = new File(this.blankBracketOutputDirectory);
        if (!tmpDir.exists() || !tmpDir.isDirectory()) {
            logger.debug("creating directory : " + tmpDir);
            if (!tmpDir.mkdir()) {
                logger.warn("Unable to create directory : " + tmpDir);
                return null;
            }
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(this.blankBracketOutputFilePath);
        } catch (java.io.FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this.getFrame(), "Unable to open bracket sheet report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Blank Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.warn("Failed to create output stream for : " + this.blankBracketOutputFilePath + "\n"
                    + Arrays.toString(ex.getStackTrace()));
            fos = null;
        }

        logger.trace("END");
        return fos;
    }

    @Action
    public void reportBoutSheetBlank() {
        logger.trace("BEGIN");
        if (!dao.isOpen()) {
            JOptionPane.showMessageDialog(this.getFrame(), "Tournament file is not open.",
                    "Blank Bout Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to produce blank bout sheet : DAO is not open");
            return;
        }

        BoutSheetReport report = new BoutSheetReport();
        if (report.generateBlank(this.dao, 1)) {
            showPDF(report.getoutputFilePath());
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Unable to produce blank bout sheet."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Blank Bout Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.warn("Unable to produce blank bout sheet : report error");
        }
        logger.trace("END");
    }

    @Action
    public void reportBoutSheetForMat() {
        logger.trace("BEGIN");
        if (!dao.isOpen()) {
            logger.warn("DAO is not open");
            return;
        }

        String matValues = dao.getMatValues();
        if ((matValues == null) || (matValues.isEmpty())) {
            JOptionPane.showMessageDialog(this.getFrame(), "There are no mats defined in this tournament.",
                    "Bout Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.warn("No mat values specified");
            return;
        }

        String[] tokens = matValues.split(",");

        String mat = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                "Select mat", JOptionPane.PLAIN_MESSAGE, null,
                tokens, tokens[0]);

        if (mat != null) {
            List<Bout> list = dao.getBoutsByMat(mat);
            if ((list != null) && !list.isEmpty()) {
                Collections.sort(list, new BoutNumSort());
                BoutSheetReport report = new BoutSheetReport();
                if (report.generateReport(dao, list)) {
                    showPDF(report.getoutputFilePath());
                } else {
                    JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bout sheet report."
                            + "\nSee 'logs/bouttime.log' for more information.",
                            "Bout Sheet Error", JOptionPane.ERROR_MESSAGE);
                    logger.error("Report generation error");
                }
            } else {
                JOptionPane.showMessageDialog(this.getFrame(), "There are no bouts for mat " + mat + ".",
                        "Bout Sheet Warning", JOptionPane.WARNING_MESSAGE);
                logger.debug("No bouts to report for mat " + mat);
            }
        }

        logger.trace("END");
    }

    @Action
    public void reportBoutSheetAll() {
        logger.trace("BEGIN");
        if (!dao.isOpen()) {
            logger.warn("DAO is not open");
            return;
        }

        List<Bout> list = dao.getAllBouts();
        if ((list != null) && !list.isEmpty()) {
            Collections.sort(list, new BoutNumSort());
            BoutSheetReport report = new BoutSheetReport();
            if (report.generateReport(dao, list)) {
                showPDF(report.getoutputFilePath());
            } else {
                JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bout sheet report."
                        + "\nSee 'logs/bouttime.log' for more information.",
                        "Bout Sheet Error", JOptionPane.ERROR_MESSAGE);
                logger.error("Report generation error");
            }
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "There are no bouts.",
                    "Bout Sheet Warning", JOptionPane.WARNING_MESSAGE);
            logger.debug("No bouts to report");
        }

        logger.trace("END");
    }

    @Action
    public void reportBracketSheetForGroup() {
        logger.trace("BEGIN");
        List<Group> gList = dao.getAllGroups();
        Collections.sort(gList, new GroupClassDivWtSort());
        Object[] grps = gList.toArray();

        if ((grps == null) || (grps.length == 0)) {
            JOptionPane.showMessageDialog(this.getFrame(), "There are no groups defined in this tournament.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to produce bracket sheet for group : no groups");
            return;
        }

        Group g = (Group) JOptionPane.showInputDialog(this.getFrame(), null,
                "Select group", JOptionPane.PLAIN_MESSAGE, null,
                grps, grps[0]);

        if (g != null) {
            List<Group> list = new ArrayList<Group>();
            list.add(g);
            boolean includeTimestamp = includeTimestampForBracketsheet();
            if (BracketSheetReport.generateReport(dao, list, true, includeTimestamp)) {
                showPDF(BracketSheetReport.getoutputFilePath());
            } else {
                JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                        + "\nSee 'logs/bouttime.log' for more information.",
                        "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
                logger.error("Report generation error");
            }
        }

        logger.trace("END");
    }

    @Action(enabledProperty = "daoOpen")
    public void reportBracketSheetExportToFiles() {
        logger.trace("BEGIN");
        List<Group> gList = dao.getAllGroups();
        Collections.sort(gList, new GroupClassDivWtSort());

        if ((gList == null) || (gList.isEmpty())) {
            JOptionPane.showMessageDialog(this.getFrame(), "There are no groups defined in this tournament.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to export bracket sheets");
            return;
        }

        JFileChooser infile = new JFileChooser();
        infile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        infile.setDialogTitle("Select folder to save files");
        JFrame mainFrame = bouttime.mainview.BoutTimeApp.getApplication().getMainFrame();

        File exportDir;
        if (infile.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            exportDir = infile.getSelectedFile();
        } else {
            logger.debug("Cancelled operation to export bracket sheets");
            return;
        }

        for (Group g : gList) {
            List<Group> list = new ArrayList<Group>();
            list.add(g);
            String filename = String.format("%s_%s_%s.pdf", g.getClassification(), g.getAgeDivision(), g.getWeightClass());
            String exportFile = String.format("%s/%s", exportDir.getAbsolutePath(), filename);
            if (!BracketSheetReport.generateReport(dao, list, exportFile, true, false)) {
                logger.error("Failed to generate report for : " + exportFile);
            }
        }

        logger.trace("END");
    }

    @Action
    public void reportBracketSheetNoBoutNumbersForGroup() {
        logger.trace("BEGIN");
        List<Group> gList = dao.getAllGroups();
        Collections.sort(gList, new GroupClassDivWtSort());
        Object[] grps = gList.toArray();

        if ((grps == null) || (grps.length == 0)) {
            JOptionPane.showMessageDialog(this.getFrame(), "There are no groups defined in this tournament.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to produce bracket sheet for group : no groups");
            return;
        }

        Group g = (Group) JOptionPane.showInputDialog(this.getFrame(), null,
                "Select group", JOptionPane.PLAIN_MESSAGE, null,
                grps, grps[0]);

        if (g != null) {
            List<Group> list = new ArrayList<Group>();
            list.add(g);
            boolean includeTimestamp = includeTimestampForBracketsheet();
            if (BracketSheetReport.generateReport(dao, list, false, includeTimestamp)) {
                showPDF(BracketSheetReport.getoutputFilePath());
            } else {
                JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                        + "\nSee 'logs/bouttime.log' for more information.",
                        "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
                logger.error("Report generation error");
            }
        }

        logger.trace("END");
    }

    @Action
    public void reportBracketSheetNoBoutNumbersForDivision() {
        logger.trace("BEGIN");
        if (!dao.isOpen()) {
            logger.warn("DAO is not open");
            return;
        }

        String divValues = dao.getAgeDivisionValues();
        if ((divValues == null) || (divValues.isEmpty())) {
            JOptionPane.showMessageDialog(this.getFrame(), "There are no divisions defined in this tournament.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.warn("No age division values specified");
            return;
        }

        String[] tokens = divValues.split(",");

        String div = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                "Select age division", JOptionPane.PLAIN_MESSAGE, null,
                tokens, tokens[0]);

        if (div != null) {
            List<Group> list = dao.getGroupsByDiv(div);
            if ((list != null) && !list.isEmpty()) {
                boolean includeTimestamp = includeTimestampForBracketsheet();
                if (BracketSheetReport.generateReport(dao, list, false, includeTimestamp)) {
                    showPDF(BracketSheetReport.getoutputFilePath());
                } else {
                    JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                            + "\nSee 'logs/bouttime.log' for more information.",
                            "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
                    logger.error("Report generation error");
                }
            }
        }

        logger.trace("END");
    }

    @Action
    public void reportBracketSheetNoBoutNumbersForMat() {
        logger.trace("BEGIN");
        if (!dao.isOpen()) {
            logger.warn("DAO is not open");
            return;
        }

        String matValues = dao.getMatValues();
        if ((matValues == null) || (matValues.isEmpty())) {
            JOptionPane.showMessageDialog(this.getFrame(), "There are no mats defined in this tournament.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.warn("No mat values specified");
            return;
        }

        String[] tokens = matValues.split(",");

        String mat = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                "Select mat", JOptionPane.PLAIN_MESSAGE, null,
                tokens, tokens[0]);

        if (mat != null) {
            List<Group> list = dao.getGroupsByMat(mat);
            if ((list != null) && !list.isEmpty()) {
                boolean includeTimestamp = includeTimestampForBracketsheet();
                if (BracketSheetReport.generateReport(dao, list, false, includeTimestamp)) {
                    showPDF(BracketSheetReport.getoutputFilePath());
                } else {
                    JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                            + "\nSee 'logs/bouttime.log' for more information.",
                            "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
                    logger.error("Report generation error");
                }
            }
        }

        logger.trace("END");
    }

    @Action
    public void reportBracketSheetBlank2RR() {
        logger.trace("BEGIN");
        FileOutputStream fos = createBlankBracketSheetReportOutputFile();
        if (fos == null) {
            logger.info("blank bracket sheet output file not created");
            return;
        }

        if (RoundRobinBracketSheetReport.doBlankPage(fos, this.dao, 2)) {
            showPDF(this.blankBracketOutputFilePath);
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action
    public void reportBracketSheetBlank3RR() {
        logger.trace("BEGIN");
        FileOutputStream fos = createBlankBracketSheetReportOutputFile();
        if (fos == null) {
            logger.info("blank bracket sheet output file not created");
            return;
        }

        if (RoundRobinBracketSheetReport.doBlankPage(fos, this.dao, 3)) {
            showPDF(this.blankBracketOutputFilePath);
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action
    public void reportBracketSheetBlank4RR() {
        logger.trace("BEGIN");
        FileOutputStream fos = createBlankBracketSheetReportOutputFile();
        if (fos == null) {
            logger.info("blank bracket sheet output file not created");
            return;
        }

        if (RoundRobinBracketSheetReport.doBlankPage(fos, this.dao, 4)) {
            showPDF(this.blankBracketOutputFilePath);
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action
    public void reportBracketSheetBlank5RR() {
        logger.trace("BEGIN");
        FileOutputStream fos = createBlankBracketSheetReportOutputFile();
        if (fos == null) {
            logger.info("blank bracket sheet output file not created");
            return;
        }

        if (RoundRobinBracketSheetReport.doBlankPage(fos, this.dao, 5)) {
            showPDF(this.blankBracketOutputFilePath);
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action
    public void reportBracketSheetBlank2() {
        logger.trace("BEGIN");
        FileOutputStream fos = createBlankBracketSheetReportOutputFile();
        if (fos == null) {
            logger.info("blank bracket sheet output file not created");
            return;
        }

        Bracket2BracketSheetReport report = new Bracket2BracketSheetReport();
        if (report.doBlankPage(fos, this.dao)) {
            showPDF(this.blankBracketOutputFilePath);
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action
    public void reportBracketSheetBlank4() {
        logger.trace("BEGIN");
        FileOutputStream fos = createBlankBracketSheetReportOutputFile();
        if (fos == null) {
            logger.info("blank bracket sheet output file not created");
            return;
        }

        Bracket4BracketSheetReport report = new Bracket4BracketSheetReport();
        if (report.doBlankPage(fos, this.dao)) {
            showPDF(this.blankBracketOutputFilePath);
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action
    public void reportBracketSheetBlank8() {
        logger.trace("BEGIN");
        FileOutputStream fos = createBlankBracketSheetReportOutputFile();
        if (fos == null) {
            logger.info("blank bracket sheet output file not created");
            return;
        }

        Bracket8BracketSheetReport report = new Bracket8BracketSheetReport();
        if (report.doBlankPage(fos, this.dao)) {
            showPDF(this.blankBracketOutputFilePath);
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action
    public void reportBracketSheetBlank16() {
        logger.trace("BEGIN");
        FileOutputStream fos = createBlankBracketSheetReportOutputFile();
        if (fos == null) {
            logger.info("blank bracket sheet output file not created");
            return;
        }

        Bracket16BracketSheetReport report = new Bracket16BracketSheetReport();
        if (report.doBlankPage(fos, this.dao)) {
            showPDF(this.blankBracketOutputFilePath);
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action
    public void reportBracketSheetBlank32() {
        logger.trace("BEGIN");
        FileOutputStream fos = createBlankBracketSheetReportOutputFile();
        if (fos == null) {
            logger.info("blank bracket sheet output file not created");
            return;
        }

        Bracket32BracketSheetReport report = new Bracket32BracketSheetReport();
        if (report.doBlankPage(fos, this.dao)) {
            showPDF(this.blankBracketOutputFilePath);
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a bracket sheet report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Bracket Sheet Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action
    public void reportTeamSummary() {
        logger.trace("BEGIN");
        if (TeamReport.doSummary(this.dao)) {
            showPDF(TeamReport.getoutputFilePath());
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a team report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Team Report Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action
    public void reportTeamDetail() {
        logger.trace("BEGIN");
        if (TeamReport.doDetail(this.dao)) {
            showPDF(TeamReport.getoutputFilePath());
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a team report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Team Report Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action
    public void reportMatKey() {
        logger.trace("BEGIN");
        if (MatKeyReport.doReport(this.dao)) {
            showPDF(MatKeyReport.getoutputFilePath());
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a mat key report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Mat Key Report Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action(enabledProperty = "daoOpen")
    public void reportCoachBoutsAll() {
        logger.trace("BEGIN");
        
        // Need to get input from user for the session
        String sessionValues = dao.getSessionValues();
        String session = null;
        
        if ((sessionValues != null) && !sessionValues.isEmpty()) {
            Object [] sessionTokens = (Object[]) sessionValues.split(",");

            if (sessionTokens.length > 1) {
                session = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                    "Choose a Session", JOptionPane.PLAIN_MESSAGE, null,
                    sessionTokens, sessionTokens[0]);
            }
        }
        
        if (BoutSequenceReport.generateReport(this.dao, session)) {
            showPDF(BoutSequenceReport.getoutputFilePath());
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a coach bout report."
                    + "\nThere could be no wrestlers with bouts (in this session)."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Coach Bout Report Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action(enabledProperty = "daoOpen")
    public void reportCoachBoutsForTeam() {
        logger.trace("BEGIN");
        
        // Need to get input from user for the team
        List<String> teamValues = dao.getTeams();
        if ((teamValues == null) || (teamValues.isEmpty())) {
            JOptionPane.showMessageDialog(this.getFrame(), "No team exist for this tournament."
                    + "\nEdit the configuration and add values for the sessions.",
                    "Coach's Bout Report for Team Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to produce coach's bout for team report : no teams exist for this tournament");
            return;
        }

        Object [] tokens = (Object[]) teamValues.toArray();

        String team = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                "Choose a Team", JOptionPane.PLAIN_MESSAGE, null,
                tokens, tokens[0]);
        
        if (team != null) {
            // Need to get input from user for the session
            String sessionValues = dao.getSessionValues();
            String session = null;
        
            if ((sessionValues != null) && !sessionValues.isEmpty()) {
                Object [] sessionTokens = (Object[]) sessionValues.split(",");

                if (sessionTokens.length > 1) {
                    session = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                        "Choose a Session", JOptionPane.PLAIN_MESSAGE, null,
                        sessionTokens, sessionTokens[0]);
                }
            }
            
            if (BoutSequenceReport.generateReport(this.dao, team, session)) {
                showPDF(BoutSequenceReport.getoutputFilePath());
            } else {
                JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a coach's bout report."
                        + "\nThere could be no wrestlers with bouts for this team (in this session)."
                        + "\nSee 'logs/bouttime.log' for more information.",
                        "Coach Bout Report Error", JOptionPane.ERROR_MESSAGE);
                logger.error("Report generation error");
            }
        }
        logger.trace("END");
    }

    @Action(enabledProperty = "daoOpen")
    public void reportAwardBySession() {
        logger.trace("BEGIN");
        if (!dao.isOpen()) {
            JOptionPane.showMessageDialog(this.getFrame(), "Tournament file is not open.",
                    "Award Report by Session Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to produce award by session report : DAO is not open");
            return;
        }

        // Need to get input from user
        String sessionValues = dao.getSessionValues();
        if ((sessionValues == null) || (sessionValues.isEmpty())) {
            JOptionPane.showMessageDialog(this.getFrame(), "No session values exist for this tournament."
                    + "\nEdit the configuration and add values for the sessions.",
                    "Award Report by Session Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to produce award by session report : no session values exist for this tournament");
            return;
        }

        String[] tokens = sessionValues.split(",");

        String session = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                "Award report by Session", JOptionPane.PLAIN_MESSAGE, null,
                tokens, tokens[0]);

        if (AwardReport.doBySession(this.dao, session)) {
            showPDF(AwardReport.getoutputFilePath());
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate an award report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Award Report Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action(enabledProperty = "daoOpen")
    public void reportAwardAll() {
        logger.trace("BEGIN");
        if (AwardReport.doAll(this.dao)) {
            showPDF(AwardReport.getoutputFilePath());
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate an award report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Award Report Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action(enabledProperty = "daoOpen")
    public void reportAwardInventory() {
        logger.trace("BEGIN");
        if (AwardInventoryReport.doReport(this.dao)) {
            showPDF(AwardInventoryReport.getoutputFilePath());
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate an award inventory report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Award Inventory Report Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action(enabledProperty = "daoOpen")
    public void reportWeighIn() {
        logger.trace("BEGIN");
        
        int numSections = 1;
        
        while (true) {
            String numSectionsStr = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                    "How many scales?", JOptionPane.PLAIN_MESSAGE, null, null, "1");
            try {
                numSections = Integer.parseInt(numSectionsStr);
                if (numSections <= 0) {
                    logger.warn("Bad number entered [" + numSections + "], resetting to 1");
                    numSections = 1;
                }
                break;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this.getFrame(), "You must enter a valid number."
                        + "\nPlease try again.",
                        "Weigh-in Report Error", JOptionPane.ERROR_MESSAGE);
                logger.error("Invalid input - not a number : " + numSectionsStr);
            }
        }
        
        List<List<Wrestler>> sections = new ArrayList<List<Wrestler>>();
        Collections.sort(this.dao.getAllWrestlers(), new WrestlerAlphaSort());
        int size = this.dao.getAllWrestlers().size();
        int sectionSize = size / numSections;
        logger.debug("size : " + size);
        int beginIndex = 0;
        int endIndex = size / numSections;
        for (int i = 0; i < (numSections - 1); i++) {
            int currentIndex = beginIndex + sectionSize;
            if (currentIndex >= size) {
                logger.info("Exceeded index during section " + i + " of " + numSections);
                break;
            }
            String currentLastName = this.dao.getAllWrestlers().get(currentIndex).getLastName();
            char currentChar = currentLastName.toUpperCase().charAt(0);
            int prevIndex = currentIndex - 1;
            int nextIndex = currentIndex + 1;
            while (true) {
                if ((prevIndex <= 0) || (nextIndex >= size)) {
                    logger.warn("Index overrun: prevIndex=" + prevIndex + "  nextIndex=" + nextIndex +
                            "  currentIndex=" + currentIndex + "  iteration=" + i);
                    endIndex = currentIndex;
                    break;
                }
                
                String prevLastName = this.dao.getAllWrestlers().get(prevIndex).getLastName();
                char prevChar = prevLastName.toUpperCase().charAt(0);
                String nextLastName = this.dao.getAllWrestlers().get(nextIndex).getLastName();
                char nextChar = nextLastName.toUpperCase().charAt(0);
                
                if (currentChar != nextChar) {
                    logger.debug("end index is forward from current index");
                    endIndex = nextIndex;
                    break;
                }
                
                if (currentChar != prevChar) {
                    logger.debug("end index is backward from current index");
                    endIndex = prevIndex + 1;
                    break;
                }
                
                prevIndex -= 1;
                nextIndex += 1;
            }
            
            logger.debug("begin : " + beginIndex + "  end : " + endIndex);
            sections.add(this.dao.getAllWrestlers().subList(beginIndex, endIndex));
            beginIndex = endIndex;
        }
        
        // Add the last section -- take it all the way to the end.
        endIndex = size;
        sections.add(this.dao.getAllWrestlers().subList(beginIndex, endIndex));
        
        if (WeighInReport.doReport(sections, "")) {
            showPDF(WeighInReport.getoutputFilePath());
        } else {
            JOptionPane.showMessageDialog(this.getFrame(), "Failed to generate a weigh-in report."
                    + "\nSee 'logs/bouttime.log' for more information.",
                    "Weigh-in Report Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Report generation error");
        }
        logger.trace("END");
    }

    @Action(enabledProperty = "daoOpen")
    public void clearAllBouts() {
        if (hasLockedGroup(this.dao.getAllGroups())) {
            JOptionPane.showMessageDialog(this.getFrame(), "One or more affected groups is locked."
                    + "\nYou must unlock the affected group(s) before attempting this operation.",
                    "Clear All Bouts Error", JOptionPane.ERROR_MESSAGE);
            logger.warn("Unable to clear all bouts : locked group");
            return;
        }

        List<Bout> list = this.dao.getAllBouts();
        for (Bout b : list) {
            b.setBoutNum("");
        }

        updateGroupList();
    }

    @Action(enabledProperty = "daoOpen")
    public void clearBoutsByMat() {
        String matStr = this.dao.getMatValues();
        if ((matStr == null) || matStr.isEmpty()) {
            JOptionPane.showMessageDialog(this.getFrame(), "No mat values exist for this tournament."
                    + "\nEdit the configuration and add values for the mats.",
                    "Clear Bouts by Mat Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to clear bouts by mat : no mat values exist for this tournament");
            return;
        }

        String[] mats = matStr.split(",");

        String mat = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                "Clear bouts by mat", JOptionPane.PLAIN_MESSAGE, null,
                mats, mats[0]);

        if (mat == null) {
            logger.info("No mat value selected.");
            return;
        }

        List<Bout> list = this.dao.getBoutsByMat(mat);
        if ((list == null) || list.isEmpty()) {
            logger.warn("No bouts to clear");
            return;
        }

        if (hasLockedGroup(this.dao.getGroupsByMat(mat))) {
            JOptionPane.showMessageDialog(this.getFrame(), "One or more affected groups is locked."
                    + "\nYou must unlock the affected group(s) before attempting this operation.",
                    "Clear Bouts by Mat Error", JOptionPane.ERROR_MESSAGE);
            logger.warn("Unable to clear bouts by mat : locked group");
            return;
        }

        for (Bout b : list) {
            b.setBoutNum("");
        }

        updateGroupList();
    }

    @Action(enabledProperty = "daoOpen")
    public void clearBoutsBySession() {
        String sessionStr = this.dao.getSessionValues();
        if ((sessionStr == null) || sessionStr.isEmpty()) {
            JOptionPane.showMessageDialog(this.getFrame(), "No session values exist for this tournament."
                    + "\nEdit the configuration and add values for the sessions.",
                    "Clear Bouts by Session Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to clear bouts by session : no session values exist for this tournament");
            return;
        }

        String[] sessions = sessionStr.split(",");

        String session = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                "Clear bouts by session", JOptionPane.PLAIN_MESSAGE, null,
                sessions, sessions[0]);

        if (session == null) {
            logger.info("No session value selected.");
            return;
        }

        List<Bout> list = this.dao.getBoutsBySession(session);
        if ((list == null) || list.isEmpty()) {
            logger.warn("No bouts to clear");
            return;
        }

        if (hasLockedGroup(this.dao.getGroupsBySession(session))) {
            JOptionPane.showMessageDialog(this.getFrame(), "One or more affected groups is locked."
                    + "\nYou must unlock the affected group(s) before attempting this operation.",
                    "Clear Bouts by Session Error", JOptionPane.ERROR_MESSAGE);
            logger.warn("Unable to clear bouts by session : locked group");
            return;
        }

        for (Bout b : list) {
            b.setBoutNum("");
        }

        updateGroupList();
    }

    @Action(enabledProperty = "daoOpen")
    public void clearBoutsByMatSession() {
        String matStr = this.dao.getMatValues();
        if ((matStr == null) || matStr.isEmpty()) {
            JOptionPane.showMessageDialog(this.getFrame(), "No mat values exist for this tournament."
                    + "\nEdit the configuration and add values for the mats.",
                    "Clear Bouts by Mat & Session Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to clear bouts by mat and session : no mat values exist for this tournament");
            return;
        }

        String sessionStr = this.dao.getSessionValues();
        if ((sessionStr == null) || sessionStr.isEmpty()) {
            JOptionPane.showMessageDialog(this.getFrame(), "No session values exist for this tournament."
                    + "\nEdit the configuration and add values for the sessions.",
                    "Clear Bouts by Mat & Session Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to clear bouts by mat and session : no session values exist for this tournament");
            return;
        }

        String[] mats = matStr.split(",");

        String mat = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                "Clear bouts by mat", JOptionPane.PLAIN_MESSAGE, null,
                mats, mats[0]);

        if (mat == null) {
            logger.info("No mat value selected.");
            return;
        }

        String[] sessions = sessionStr.split(",");

        String session = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                "Clear bouts by session", JOptionPane.PLAIN_MESSAGE, null,
                sessions, sessions[0]);

        if (session == null) {
            logger.info("No session value selected.");
            return;
        }

        List<Bout> list = this.dao.getBoutsByMatSession(mat, session);
        if ((list == null) || list.isEmpty()) {
            logger.warn("No bouts to clear");
            return;
        }

        if (hasLockedGroup(this.dao.getGroupsBySessionMat(mat, session))) {
            JOptionPane.showMessageDialog(this.getFrame(), "One or more affected groups is locked."
                    + "\nYou must unlock the affected group(s) before attempting this operation.",
                    "Clear Bouts by Mat & Session Error", JOptionPane.ERROR_MESSAGE);
            logger.warn("Unable to clear bouts by mat and session : locked group");
            return;
        }

        for (Bout b : list) {
            b.setBoutNum("");
        }

        updateGroupList();
    }

    /**
     * Check if at least one group in the list is locked
     * @param list List of Group objects to check
     * @return true if at least one group in the list is locked, false otherwise
     */
    private boolean hasLockedGroup(List<Group> list) {
        for (Group g : list) {
            if (g.isLocked()) {
                return true;
            }
        }

        return false;
    }

    @Action(enabledProperty = "daoOpen")
    public void lockAllGroups() {
        lockUnlockAllGroups(true);
    }

    @Action(enabledProperty = "daoOpen")
    public void unlockAllGroups() {
        lockUnlockAllGroups(false);
    }

    private void lockUnlockAllGroups(boolean doLock) {
        List<Group> list = this.dao.getAllGroups();
        for (Group g : list) {
            g.setLocked(doLock);
        }

        updateGroupList();
    }

    @Action(enabledProperty = "daoOpen")
    public void lockGroupsByMat() {
        lockUnlockGroupsByMat(true);
    }

    @Action(enabledProperty = "daoOpen")
    public void unlockGroupsByMat() {
        lockUnlockGroupsByMat(false);
    }

    private void lockUnlockGroupsByMat(boolean doLock) {
        String capLabel = (doLock) ? "Lock" : "Unlock";
        String label = (doLock) ? "lock" : "unlock";
        String matStr = this.dao.getMatValues();
        if ((matStr == null) || matStr.isEmpty()) {
            JOptionPane.showMessageDialog(this.getFrame(), "No mat values exist for this tournament."
                    + "\nEdit the configuration and add values for the mats.",
                    capLabel + " Groups by Mat Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to " + label + " groups by mat : no mat values exist for this tournament");
            return;
        }

        String[] mats = matStr.split(",");

        String mat = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                capLabel + " groups by mat", JOptionPane.PLAIN_MESSAGE, null,
                mats, mats[0]);

        if (mat == null) {
            logger.info("No mat value selected.");
            return;
        }

        List<Group> list = this.dao.getGroupsByMat(mat);
        if ((list == null) || list.isEmpty()) {
            logger.warn("No groups to " + label);
            return;
        }

        for (Group g : list) {
            g.setLocked(doLock);
        }

        updateGroupList();
    }

    @Action(enabledProperty = "daoOpen")
    public void lockGroupsBySession() {
        lockUnlockGroupsBySession(true);
    }

    @Action(enabledProperty = "daoOpen")
    public void unlockGroupsBySession() {
        lockUnlockGroupsBySession(false);
    }

    private void lockUnlockGroupsBySession(boolean doLock) {
        String capLabel = (doLock) ? "Lock" : "Unlock";
        String label = (doLock) ? "lock" : "unlock";
        String sessionStr = this.dao.getSessionValues();
        if ((sessionStr == null) || sessionStr.isEmpty()) {
            JOptionPane.showMessageDialog(this.getFrame(), "No session values exist for this tournament."
                    + "\nEdit the configuration and add values for the sessions.",
                    capLabel + " Groups by Session Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to " + label + " groups by session : no session values exist for this tournament");
            return;
        }

        String[] sessions = sessionStr.split(",");

        String session = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                capLabel + " groups by session", JOptionPane.PLAIN_MESSAGE, null,
                sessions, sessions[0]);

        if (session == null) {
            logger.info("No session value selected.");
            return;
        }

        List<Group> list = this.dao.getGroupsBySession(session);
        if ((list == null) || list.isEmpty()) {
            logger.warn("No groups to " + label);
            return;
        }

        for (Group g : list) {
            g.setLocked(doLock);
        }

        updateGroupList();
    }

    @Action(enabledProperty = "daoOpen")
    public void lockGroupsByMatSession() {
        lockUnlockGroupsByMatSession(true);
    }

    @Action(enabledProperty = "daoOpen")
    public void unlockGroupsByMatSession() {
        lockUnlockGroupsByMatSession(false);
    }

    private void lockUnlockGroupsByMatSession(boolean doLock) {
        String capLabel = (doLock) ? "Lock" : "Unlock";
        String label = (doLock) ? "lock" : "unlock";
        String matStr = this.dao.getMatValues();
        if ((matStr == null) || matStr.isEmpty()) {
            JOptionPane.showMessageDialog(this.getFrame(), "No mat values exist for this tournament."
                    + "\nEdit the configuration and add values for the mats.",
                    capLabel + " Groups by Mat & Session Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to " + label + " groups by mat and session : no mat values exist for this tournament");
            return;
        }

        String sessionStr = this.dao.getSessionValues();
        if ((sessionStr == null) || sessionStr.isEmpty()) {
            JOptionPane.showMessageDialog(this.getFrame(), "No session values exist for this tournament."
                    + "\nEdit the configuration and add values for the sessions.",
                    capLabel + " Groups by Mat & Session Error", JOptionPane.ERROR_MESSAGE);
            logger.info("Unable to " + label + " groups by mat and session : no session values exist for this tournament");
            return;
        }

        String[] mats = matStr.split(",");

        String mat = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                capLabel + " groups by mat", JOptionPane.PLAIN_MESSAGE, null,
                mats, mats[0]);

        if (mat == null) {
            logger.info("No mat value selected.");
            return;
        }

        String[] sessions = sessionStr.split(",");

        String session = (String) JOptionPane.showInputDialog(this.getFrame(), null,
                capLabel + " groups by session", JOptionPane.PLAIN_MESSAGE, null,
                sessions, sessions[0]);

        if (session == null) {
            logger.info("No session value selected.");
            return;
        }

        List<Group> list = this.dao.getGroupsBySessionMat(session, mat);
        if ((list == null) || list.isEmpty()) {
            logger.warn("No groups to " + label);
            return;
        }

        for (Group g : list) {
            g.setLocked(doLock);
        }

        updateGroupList();
    }

    public void showPDF(String filename) {
        logger.trace("BEGIN");
        SwingController controller = new SwingController();
        SwingViewBuilder factory = new SwingViewBuilder(controller);
        JFrame frame = factory.buildViewerFrame();
        controller.openDocument(filename);
        frame.setTitle(filename);
        frame.setLocation(this.getFrame().getLocationOnScreen());
        frame.pack();
        frame.setVisible(true);
        logger.trace("END");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        openExistingMenuItem = new javax.swing.JMenuItem();
        openNewMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        inputFromTextMenuItem = new javax.swing.JMenuItem();
        inputFromSSMenuItem = new javax.swing.JMenuItem();
        inputFromXMLMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        outputWrestlersMenu = new javax.swing.JMenu();
        outputWrestlersToTextFileMenuItem = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        configMenuItem = new javax.swing.JMenuItem();
        functionMenu = new javax.swing.JMenu();
        autoGroupMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        autobouterMenu = new javax.swing.JMenu();
        boutByMatMenuItem = new javax.swing.JMenuItem();
        boutByTourneyMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        clearBoutsMenu = new javax.swing.JMenu();
        clearAllBoutsMenuItem = new javax.swing.JMenuItem();
        clearBoutsByMatMenuItem = new javax.swing.JMenuItem();
        clearBoutsBySessionMenuItem = new javax.swing.JMenuItem();
        clearBoutsByMatSessionMenuItem = new javax.swing.JMenuItem();
        lockGroupsMenu = new javax.swing.JMenu();
        lockAllGroupsMenuItem = new javax.swing.JMenuItem();
        lockGroupsByMatMenuItem = new javax.swing.JMenuItem();
        lockGroupsBySessionMenuItem = new javax.swing.JMenuItem();
        lockGroupsByMatSessionMenuItem = new javax.swing.JMenuItem();
        unlockGroupsMenu = new javax.swing.JMenu();
        unlockAllGroupsMenuItem = new javax.swing.JMenuItem();
        unlockGroupsByMatMenuItem = new javax.swing.JMenuItem();
        unlockGroupsBySessionMenuItem = new javax.swing.JMenuItem();
        unlockGroupsByMatSessionMenuItem = new javax.swing.JMenuItem();
        reportMenu = new javax.swing.JMenu();
        awardReportSubMenu = new javax.swing.JMenu();
        awardReportBySessionMenuItem = new javax.swing.JMenuItem();
        awardReportAllMenuItem = new javax.swing.JMenuItem();
        awardInventoryReportMenuItem = new javax.swing.JMenuItem();
        boutSheetReportMenu = new javax.swing.JMenu();
        blankBoutsheetMenuItem = new javax.swing.JMenuItem();
        boutsheetReportByMatMenuItem = new javax.swing.JMenuItem();
        boutSheetReportAllMenuItem = new javax.swing.JMenuItem();
        bracketSheetReportMenu = new javax.swing.JMenu();
        bracketsheetReportByGroupMenuItem = new javax.swing.JMenuItem();
        blankBracketsheetReportMenu = new javax.swing.JMenu();
        bracketsheetReportBlankRRMenu = new javax.swing.JMenu();
        bracketSheetReportBlank2RRMenuItem = new javax.swing.JMenuItem();
        bracketsheetReportBlank3RRMenuItem = new javax.swing.JMenuItem();
        bracketsheetReportBlank4RRMenuItem = new javax.swing.JMenuItem();
        bracketsheetReportBlank5RRMenuItem = new javax.swing.JMenuItem();
        bracketsheetReportBlankBktMenu = new javax.swing.JMenu();
        bracketsheetReportBlank2BktMenuItem = new javax.swing.JMenuItem();
        bracketsheetReportBlank4BktMenuItem = new javax.swing.JMenuItem();
        bracketsheetReportBlank8BktMenuItem = new javax.swing.JMenuItem();
        bracketsheetReportBlank16BktMenuItem = new javax.swing.JMenuItem();
        bracketsheetReportBlank32BktMenuItem = new javax.swing.JMenuItem();
        boutsWithoutNumbersMenu = new javax.swing.JMenu();
        bracketsheetNoBoutsForGroupMenuItem = new javax.swing.JMenuItem();
        bracketsheetNoBoutsForDivMenuItem = new javax.swing.JMenuItem();
        bracketsheetNoBoutsForMatMenuItem = new javax.swing.JMenuItem();
        exportToFilesMenuItem = new javax.swing.JMenuItem();
        teamReportMenu = new javax.swing.JMenu();
        teamReportSummaryMenuItem = new javax.swing.JMenuItem();
        teamReportDetailMenuItem = new javax.swing.JMenuItem();
        coachBoutReportMenu = new javax.swing.JMenu();
        allCoachesBoutReportMenuItem = new javax.swing.JMenuItem();
        teamCoachesBoutReportMenuItem = new javax.swing.JMenuItem();
        matKeyReportMenuItem = new javax.swing.JMenuItem();
        weighinMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        mainTabPane = new javax.swing.JTabbedPane();
        masterListPanel = new bouttime.gui.panel.MasterListPanel(this);
        freeListPanel = new bouttime.gui.panel.FreeListPanel(this);
        groupPanel = new bouttime.gui.panel.GroupPanel(this);
        boutPanel = new bouttime.gui.panel.BoutPanel(this);
        scratchListPanel = new bouttime.gui.panel.ScratchListPanel(this);

        menuBar.setName("menuBar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(BoutTimeView.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getActionMap(BoutTimeView.class, this);
        openExistingMenuItem.setAction(actionMap.get("openExistingDaoFile")); // NOI18N
        openExistingMenuItem.setName("openExistingMenuItem"); // NOI18N
        fileMenu.add(openExistingMenuItem);

        openNewMenuItem.setAction(actionMap.get("openNewDaoFile")); // NOI18N
        openNewMenuItem.setName("openNewMenuItem"); // NOI18N
        fileMenu.add(openNewMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        inputFromTextMenuItem.setAction(actionMap.get("inputFromTextFile")); // NOI18N
        inputFromTextMenuItem.setName("inputFromTextMenuItem"); // NOI18N
        fileMenu.add(inputFromTextMenuItem);

        inputFromSSMenuItem.setAction(actionMap.get("inputFromExcelFile")); // NOI18N
        inputFromSSMenuItem.setName("inputFromSSMenuItem"); // NOI18N
        fileMenu.add(inputFromSSMenuItem);

        inputFromXMLMenuItem.setAction(actionMap.get("inputFromXmlFile")); // NOI18N
        inputFromXMLMenuItem.setText(resourceMap.getString("inputFromXMLMenuItem.text")); // NOI18N
        inputFromXMLMenuItem.setName("inputFromXMLMenuItem"); // NOI18N
        fileMenu.add(inputFromXMLMenuItem);

        jSeparator2.setName("jSeparator2"); // NOI18N
        fileMenu.add(jSeparator2);

        outputWrestlersMenu.setText(resourceMap.getString("outputWrestlersMenu.text")); // NOI18N
        outputWrestlersMenu.setName("outputWrestlersMenu"); // NOI18N

        outputWrestlersToTextFileMenuItem.setAction(actionMap.get("outputWrestlersToTextFile")); // NOI18N
        outputWrestlersToTextFileMenuItem.setText(resourceMap.getString("outputWrestlersToTextFileMenuItem.text")); // NOI18N
        outputWrestlersToTextFileMenuItem.setName("outputWrestlersToTextFileMenuItem"); // NOI18N
        outputWrestlersMenu.add(outputWrestlersToTextFileMenuItem);

        fileMenu.add(outputWrestlersMenu);

        jSeparator5.setName("jSeparator5"); // NOI18N
        fileMenu.add(jSeparator5);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N

        configMenuItem.setAction(actionMap.get("editConfiguration")); // NOI18N
        configMenuItem.setText(resourceMap.getString("configMenuItem.text")); // NOI18N
        configMenuItem.setName("configMenuItem"); // NOI18N
        editMenu.add(configMenuItem);

        menuBar.add(editMenu);

        functionMenu.setText(resourceMap.getString("functionMenu.text")); // NOI18N
        functionMenu.setName("functionMenu"); // NOI18N

        autoGroupMenuItem.setAction(actionMap.get("autoGroup")); // NOI18N
        autoGroupMenuItem.setToolTipText(resourceMap.getString("autoGroupMenuItem.toolTipText")); // NOI18N
        autoGroupMenuItem.setName("autoGroupMenuItem"); // NOI18N
        functionMenu.add(autoGroupMenuItem);

        jSeparator3.setName("jSeparator3"); // NOI18N
        functionMenu.add(jSeparator3);

        autobouterMenu.setText(resourceMap.getString("autobouterMenu.text")); // NOI18N
        autobouterMenu.setName("autobouterMenu"); // NOI18N

        boutByMatMenuItem.setAction(actionMap.get("autoboutByMat")); // NOI18N
        boutByMatMenuItem.setText(resourceMap.getString("boutByMatMenuItem.text")); // NOI18N
        boutByMatMenuItem.setName("boutByMatMenuItem"); // NOI18N
        autobouterMenu.add(boutByMatMenuItem);

        boutByTourneyMenuItem.setText(resourceMap.getString("boutByTourneyMenuItem.text")); // NOI18N
        boutByTourneyMenuItem.setEnabled(false);
        boutByTourneyMenuItem.setName("boutByTourneyMenuItem"); // NOI18N
        autobouterMenu.add(boutByTourneyMenuItem);

        functionMenu.add(autobouterMenu);

        jSeparator4.setName("jSeparator4"); // NOI18N
        functionMenu.add(jSeparator4);

        clearBoutsMenu.setText(resourceMap.getString("clearBoutsMenu.text")); // NOI18N
        clearBoutsMenu.setName("clearBoutsMenu"); // NOI18N

        clearAllBoutsMenuItem.setAction(actionMap.get("clearAllBouts")); // NOI18N
        clearAllBoutsMenuItem.setText(resourceMap.getString("clearAllBoutsMenuItem.text")); // NOI18N
        clearAllBoutsMenuItem.setName("clearAllBoutsMenuItem"); // NOI18N
        clearBoutsMenu.add(clearAllBoutsMenuItem);

        clearBoutsByMatMenuItem.setAction(actionMap.get("clearBoutsByMat")); // NOI18N
        clearBoutsByMatMenuItem.setText(resourceMap.getString("clearBoutsByMatMenuItem.text")); // NOI18N
        clearBoutsByMatMenuItem.setName("clearBoutsByMatMenuItem"); // NOI18N
        clearBoutsMenu.add(clearBoutsByMatMenuItem);

        clearBoutsBySessionMenuItem.setAction(actionMap.get("clearBoutsBySession")); // NOI18N
        clearBoutsBySessionMenuItem.setText(resourceMap.getString("clearBoutsBySessionMenuItem.text")); // NOI18N
        clearBoutsBySessionMenuItem.setName("clearBoutsBySessionMenuItem"); // NOI18N
        clearBoutsMenu.add(clearBoutsBySessionMenuItem);

        clearBoutsByMatSessionMenuItem.setAction(actionMap.get("clearBoutsByMatSession")); // NOI18N
        clearBoutsByMatSessionMenuItem.setText(resourceMap.getString("clearBoutsByMatSessionMenuItem.text")); // NOI18N
        clearBoutsByMatSessionMenuItem.setName("clearBoutsByMatSessionMenuItem"); // NOI18N
        clearBoutsMenu.add(clearBoutsByMatSessionMenuItem);

        functionMenu.add(clearBoutsMenu);

        lockGroupsMenu.setText(resourceMap.getString("lockGroupsMenu.text")); // NOI18N
        lockGroupsMenu.setName("lockGroupsMenu"); // NOI18N

        lockAllGroupsMenuItem.setAction(actionMap.get("lockAllGroups")); // NOI18N
        lockAllGroupsMenuItem.setText(resourceMap.getString("lockAllGroupsMenuItem.text")); // NOI18N
        lockAllGroupsMenuItem.setName("lockAllGroupsMenuItem"); // NOI18N
        lockGroupsMenu.add(lockAllGroupsMenuItem);

        lockGroupsByMatMenuItem.setAction(actionMap.get("lockGroupsByMat")); // NOI18N
        lockGroupsByMatMenuItem.setText(resourceMap.getString("lockGroupsByMatMenuItem.text")); // NOI18N
        lockGroupsByMatMenuItem.setName("lockGroupsByMatMenuItem"); // NOI18N
        lockGroupsMenu.add(lockGroupsByMatMenuItem);

        lockGroupsBySessionMenuItem.setAction(actionMap.get("lockGroupsBySession")); // NOI18N
        lockGroupsBySessionMenuItem.setText(resourceMap.getString("lockGroupsBySessionMenuItem.text")); // NOI18N
        lockGroupsBySessionMenuItem.setName("lockGroupsBySessionMenuItem"); // NOI18N
        lockGroupsMenu.add(lockGroupsBySessionMenuItem);

        lockGroupsByMatSessionMenuItem.setAction(actionMap.get("lockGroupsByMatSession")); // NOI18N
        lockGroupsByMatSessionMenuItem.setText(resourceMap.getString("lockGroupsByMatSessionMenuItem.text")); // NOI18N
        lockGroupsByMatSessionMenuItem.setName("lockGroupsByMatSessionMenuItem"); // NOI18N
        lockGroupsMenu.add(lockGroupsByMatSessionMenuItem);

        functionMenu.add(lockGroupsMenu);

        unlockGroupsMenu.setText(resourceMap.getString("unlockGroupsMenu.text")); // NOI18N
        unlockGroupsMenu.setName("unlockGroupsMenu"); // NOI18N

        unlockAllGroupsMenuItem.setAction(actionMap.get("unlockAllGroups")); // NOI18N
        unlockAllGroupsMenuItem.setText(resourceMap.getString("unlockAllGroupsMenuItem.text")); // NOI18N
        unlockAllGroupsMenuItem.setName("unlockAllGroupsMenuItem"); // NOI18N
        unlockGroupsMenu.add(unlockAllGroupsMenuItem);

        unlockGroupsByMatMenuItem.setAction(actionMap.get("unlockGroupsByMat")); // NOI18N
        unlockGroupsByMatMenuItem.setText(resourceMap.getString("unlockGroupsByMatMenuItem.text")); // NOI18N
        unlockGroupsByMatMenuItem.setName("unlockGroupsByMatMenuItem"); // NOI18N
        unlockGroupsMenu.add(unlockGroupsByMatMenuItem);

        unlockGroupsBySessionMenuItem.setAction(actionMap.get("unlockGroupsBySession")); // NOI18N
        unlockGroupsBySessionMenuItem.setText(resourceMap.getString("unlockGroupsBySessionMenuItem.text")); // NOI18N
        unlockGroupsBySessionMenuItem.setName("unlockGroupsBySessionMenuItem"); // NOI18N
        unlockGroupsMenu.add(unlockGroupsBySessionMenuItem);

        unlockGroupsByMatSessionMenuItem.setAction(actionMap.get("unlockGroupsByMatSession")); // NOI18N
        unlockGroupsByMatSessionMenuItem.setText(resourceMap.getString("unlockGroupsByMatSessionMenuItem.text")); // NOI18N
        unlockGroupsByMatSessionMenuItem.setName("unlockGroupsByMatSessionMenuItem"); // NOI18N
        unlockGroupsMenu.add(unlockGroupsByMatSessionMenuItem);

        functionMenu.add(unlockGroupsMenu);

        menuBar.add(functionMenu);

        reportMenu.setText(resourceMap.getString("reportMenu.text")); // NOI18N
        reportMenu.setName("reportMenu"); // NOI18N

        awardReportSubMenu.setText(resourceMap.getString("awardReportSubMenu.text")); // NOI18N
        awardReportSubMenu.setName("awardReportSubMenu"); // NOI18N

        awardReportBySessionMenuItem.setAction(actionMap.get("reportAwardBySession")); // NOI18N
        awardReportBySessionMenuItem.setText(resourceMap.getString("awardReportBySessionMenuItem.text")); // NOI18N
        awardReportBySessionMenuItem.setName("awardReportBySessionMenuItem"); // NOI18N
        awardReportSubMenu.add(awardReportBySessionMenuItem);

        awardReportAllMenuItem.setAction(actionMap.get("reportAwardAll")); // NOI18N
        awardReportAllMenuItem.setText(resourceMap.getString("awardReportAllMenuItem.text")); // NOI18N
        awardReportAllMenuItem.setName("awardReportAllMenuItem"); // NOI18N
        awardReportSubMenu.add(awardReportAllMenuItem);

        awardInventoryReportMenuItem.setAction(actionMap.get("reportAwardInventory")); // NOI18N
        awardInventoryReportMenuItem.setText(resourceMap.getString("awardInventoryReportMenuItem.text")); // NOI18N
        awardInventoryReportMenuItem.setName("awardInventoryReportMenuItem"); // NOI18N
        awardReportSubMenu.add(awardInventoryReportMenuItem);

        reportMenu.add(awardReportSubMenu);

        boutSheetReportMenu.setText(resourceMap.getString("boutSheetReportMenu.text")); // NOI18N
        boutSheetReportMenu.setName("boutSheetReportMenu"); // NOI18N

        blankBoutsheetMenuItem.setAction(actionMap.get("reportBoutSheetBlank")); // NOI18N
        blankBoutsheetMenuItem.setText(resourceMap.getString("blankBoutsheetMenuItem.text")); // NOI18N
        blankBoutsheetMenuItem.setName("blankBoutsheetMenuItem"); // NOI18N
        boutSheetReportMenu.add(blankBoutsheetMenuItem);

        boutsheetReportByMatMenuItem.setAction(actionMap.get("reportBoutSheetForMat")); // NOI18N
        boutsheetReportByMatMenuItem.setText(resourceMap.getString("boutsheetReportByMatMenuItem.text")); // NOI18N
        boutsheetReportByMatMenuItem.setName("boutsheetReportByMatMenuItem"); // NOI18N
        boutSheetReportMenu.add(boutsheetReportByMatMenuItem);

        boutSheetReportAllMenuItem.setAction(actionMap.get("reportBoutSheetAll")); // NOI18N
        boutSheetReportAllMenuItem.setText(resourceMap.getString("boutSheetReportAllMenuItem.text")); // NOI18N
        boutSheetReportAllMenuItem.setName("boutSheetReportAllMenuItem"); // NOI18N
        boutSheetReportMenu.add(boutSheetReportAllMenuItem);

        reportMenu.add(boutSheetReportMenu);

        bracketSheetReportMenu.setText(resourceMap.getString("bracketSheetReportMenu.text")); // NOI18N
        bracketSheetReportMenu.setName("bracketSheetReportMenu"); // NOI18N

        bracketsheetReportByGroupMenuItem.setAction(actionMap.get("reportBracketSheetForGroup")); // NOI18N
        bracketsheetReportByGroupMenuItem.setText(resourceMap.getString("bracketsheetReportByGroupMenuItem.text")); // NOI18N
        bracketsheetReportByGroupMenuItem.setName("bracketsheetReportByGroupMenuItem"); // NOI18N
        bracketSheetReportMenu.add(bracketsheetReportByGroupMenuItem);

        blankBracketsheetReportMenu.setText(resourceMap.getString("blankBracketsheetReportMenu.text")); // NOI18N
        blankBracketsheetReportMenu.setName("blankBracketsheetReportMenu"); // NOI18N

        bracketsheetReportBlankRRMenu.setText(resourceMap.getString("bracketsheetReportBlankRRMenu.text")); // NOI18N
        bracketsheetReportBlankRRMenu.setName("bracketsheetReportBlankRRMenu"); // NOI18N

        bracketSheetReportBlank2RRMenuItem.setAction(actionMap.get("reportBracketSheetBlank2RR")); // NOI18N
        bracketSheetReportBlank2RRMenuItem.setText(resourceMap.getString("bracketSheetReportBlank2RRMenuItem.text")); // NOI18N
        bracketSheetReportBlank2RRMenuItem.setName("bracketSheetReportBlank2RRMenuItem"); // NOI18N
        bracketsheetReportBlankRRMenu.add(bracketSheetReportBlank2RRMenuItem);

        bracketsheetReportBlank3RRMenuItem.setAction(actionMap.get("reportBracketSheetBlank3RR")); // NOI18N
        bracketsheetReportBlank3RRMenuItem.setText(resourceMap.getString("bracketsheetReportBlank3RRMenuItem.text")); // NOI18N
        bracketsheetReportBlank3RRMenuItem.setName("bracketsheetReportBlank3RRMenuItem"); // NOI18N
        bracketsheetReportBlankRRMenu.add(bracketsheetReportBlank3RRMenuItem);

        bracketsheetReportBlank4RRMenuItem.setAction(actionMap.get("reportBracketSheetBlank4RR")); // NOI18N
        bracketsheetReportBlank4RRMenuItem.setText(resourceMap.getString("bracketsheetReportBlank4RRMenuItem.text")); // NOI18N
        bracketsheetReportBlank4RRMenuItem.setName("bracketsheetReportBlank4RRMenuItem"); // NOI18N
        bracketsheetReportBlankRRMenu.add(bracketsheetReportBlank4RRMenuItem);

        bracketsheetReportBlank5RRMenuItem.setAction(actionMap.get("reportBracketSheetBlank5RR")); // NOI18N
        bracketsheetReportBlank5RRMenuItem.setText(resourceMap.getString("bracketsheetReportBlank5RRMenuItem.text")); // NOI18N
        bracketsheetReportBlank5RRMenuItem.setName("bracketsheetReportBlank5RRMenuItem"); // NOI18N
        bracketsheetReportBlankRRMenu.add(bracketsheetReportBlank5RRMenuItem);

        blankBracketsheetReportMenu.add(bracketsheetReportBlankRRMenu);

        bracketsheetReportBlankBktMenu.setText(resourceMap.getString("bracketsheetReportBlankBktMenu.text")); // NOI18N
        bracketsheetReportBlankBktMenu.setName("bracketsheetReportBlankBktMenu"); // NOI18N

        bracketsheetReportBlank2BktMenuItem.setAction(actionMap.get("reportBracketSheetBlank2")); // NOI18N
        bracketsheetReportBlank2BktMenuItem.setText(resourceMap.getString("bracketsheetReportBlank2BktMenuItem.text")); // NOI18N
        bracketsheetReportBlank2BktMenuItem.setName("bracketsheetReportBlank2BktMenuItem"); // NOI18N
        bracketsheetReportBlankBktMenu.add(bracketsheetReportBlank2BktMenuItem);

        bracketsheetReportBlank4BktMenuItem.setAction(actionMap.get("reportBracketSheetBlank4")); // NOI18N
        bracketsheetReportBlank4BktMenuItem.setText(resourceMap.getString("bracketsheetReportBlank4BktMenuItem.text")); // NOI18N
        bracketsheetReportBlank4BktMenuItem.setName("bracketsheetReportBlank4BktMenuItem"); // NOI18N
        bracketsheetReportBlankBktMenu.add(bracketsheetReportBlank4BktMenuItem);

        bracketsheetReportBlank8BktMenuItem.setAction(actionMap.get("reportBracketSheetBlank8")); // NOI18N
        bracketsheetReportBlank8BktMenuItem.setText(resourceMap.getString("bracketsheetReportBlank8BktMenuItem.text")); // NOI18N
        bracketsheetReportBlank8BktMenuItem.setName("bracketsheetReportBlank8BktMenuItem"); // NOI18N
        bracketsheetReportBlankBktMenu.add(bracketsheetReportBlank8BktMenuItem);

        bracketsheetReportBlank16BktMenuItem.setAction(actionMap.get("reportBracketSheetBlank16")); // NOI18N
        bracketsheetReportBlank16BktMenuItem.setText(resourceMap.getString("bracketsheetReportBlank16BktMenuItem.text")); // NOI18N
        bracketsheetReportBlank16BktMenuItem.setName("bracketsheetReportBlank16BktMenuItem"); // NOI18N
        bracketsheetReportBlankBktMenu.add(bracketsheetReportBlank16BktMenuItem);

        bracketsheetReportBlank32BktMenuItem.setAction(actionMap.get("reportBracketSheetBlank32")); // NOI18N
        bracketsheetReportBlank32BktMenuItem.setText(resourceMap.getString("bracketsheetReportBlank32BktMenuItem.text")); // NOI18N
        bracketsheetReportBlank32BktMenuItem.setName("bracketsheetReportBlank32BktMenuItem"); // NOI18N
        bracketsheetReportBlankBktMenu.add(bracketsheetReportBlank32BktMenuItem);

        blankBracketsheetReportMenu.add(bracketsheetReportBlankBktMenu);

        bracketSheetReportMenu.add(blankBracketsheetReportMenu);

        boutsWithoutNumbersMenu.setText(resourceMap.getString("boutsWithoutNumbersMenu.text")); // NOI18N
        boutsWithoutNumbersMenu.setToolTipText(resourceMap.getString("boutsWithoutNumbersMenu.toolTipText")); // NOI18N
        boutsWithoutNumbersMenu.setName("boutsWithoutNumbersMenu"); // NOI18N

        bracketsheetNoBoutsForGroupMenuItem.setAction(actionMap.get("reportBracketSheetNoBoutNumbersForGroup")); // NOI18N
        bracketsheetNoBoutsForGroupMenuItem.setText(resourceMap.getString("bracketsheetNoBoutsForGroupMenuItem.text")); // NOI18N
        bracketsheetNoBoutsForGroupMenuItem.setName("bracketsheetNoBoutsForGroupMenuItem"); // NOI18N
        boutsWithoutNumbersMenu.add(bracketsheetNoBoutsForGroupMenuItem);

        bracketsheetNoBoutsForDivMenuItem.setAction(actionMap.get("reportBracketSheetNoBoutNumbersForDivision")); // NOI18N
        bracketsheetNoBoutsForDivMenuItem.setText(resourceMap.getString("bracketsheetNoBoutsForDivMenuItem.text")); // NOI18N
        bracketsheetNoBoutsForDivMenuItem.setName("bracketsheetNoBoutsForDivMenuItem"); // NOI18N
        boutsWithoutNumbersMenu.add(bracketsheetNoBoutsForDivMenuItem);

        bracketsheetNoBoutsForMatMenuItem.setAction(actionMap.get("reportBracketSheetNoBoutNumbersForMat")); // NOI18N
        bracketsheetNoBoutsForMatMenuItem.setText(resourceMap.getString("bracketsheetNoBoutsForMatMenuItem.text")); // NOI18N
        bracketsheetNoBoutsForMatMenuItem.setName("bracketsheetNoBoutsForMatMenuItem"); // NOI18N
        boutsWithoutNumbersMenu.add(bracketsheetNoBoutsForMatMenuItem);

        bracketSheetReportMenu.add(boutsWithoutNumbersMenu);

        exportToFilesMenuItem.setAction(actionMap.get("reportBracketSheetExportToFiles")); // NOI18N
        exportToFilesMenuItem.setText(resourceMap.getString("exportToFilesMenuItem.text")); // NOI18N
        exportToFilesMenuItem.setToolTipText(resourceMap.getString("exportToFilesMenuItem.toolTipText")); // NOI18N
        exportToFilesMenuItem.setName("exportToFilesMenuItem"); // NOI18N
        bracketSheetReportMenu.add(exportToFilesMenuItem);

        reportMenu.add(bracketSheetReportMenu);

        teamReportMenu.setText(resourceMap.getString("teamReportMenu.text")); // NOI18N
        teamReportMenu.setName("teamReportMenu"); // NOI18N

        teamReportSummaryMenuItem.setAction(actionMap.get("reportTeamSummary")); // NOI18N
        teamReportSummaryMenuItem.setText(resourceMap.getString("teamReportSummaryMenuItem.text")); // NOI18N
        teamReportSummaryMenuItem.setName("teamReportSummaryMenuItem"); // NOI18N
        teamReportMenu.add(teamReportSummaryMenuItem);

        teamReportDetailMenuItem.setAction(actionMap.get("reportTeamDetail")); // NOI18N
        teamReportDetailMenuItem.setText(resourceMap.getString("teamReportDetailMenuItem.text")); // NOI18N
        teamReportDetailMenuItem.setName("teamReportDetailMenuItem"); // NOI18N
        teamReportMenu.add(teamReportDetailMenuItem);

        reportMenu.add(teamReportMenu);

        coachBoutReportMenu.setText(resourceMap.getString("coachBoutReportMenu.text")); // NOI18N
        coachBoutReportMenu.setName("coachBoutReportMenu"); // NOI18N

        allCoachesBoutReportMenuItem.setAction(actionMap.get("reportCoachBoutsAll")); // NOI18N
        allCoachesBoutReportMenuItem.setText(resourceMap.getString("allCoachesBoutReportMenuItem.text")); // NOI18N
        allCoachesBoutReportMenuItem.setName("allCoachesBoutReportMenuItem"); // NOI18N
        coachBoutReportMenu.add(allCoachesBoutReportMenuItem);

        teamCoachesBoutReportMenuItem.setAction(actionMap.get("reportCoachBoutsForTeam")); // NOI18N
        teamCoachesBoutReportMenuItem.setText(resourceMap.getString("teamCoachesBoutReportMenuItem.text")); // NOI18N
        teamCoachesBoutReportMenuItem.setName("teamCoachesBoutReportMenuItem"); // NOI18N
        coachBoutReportMenu.add(teamCoachesBoutReportMenuItem);

        reportMenu.add(coachBoutReportMenu);

        matKeyReportMenuItem.setAction(actionMap.get("reportMatKey")); // NOI18N
        matKeyReportMenuItem.setText(resourceMap.getString("matKeyReportMenuItem.text")); // NOI18N
        matKeyReportMenuItem.setName("matKeyReportMenuItem"); // NOI18N
        reportMenu.add(matKeyReportMenuItem);

        weighinMenuItem.setAction(actionMap.get("reportWeighIn")); // NOI18N
        weighinMenuItem.setText(resourceMap.getString("weighinMenuItem.text")); // NOI18N
        weighinMenuItem.setName("weighinMenuItem"); // NOI18N
        reportMenu.add(weighinMenuItem);

        menuBar.add(reportMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        mainTabPane.setName("mainTabPane"); // NOI18N

        masterListPanel.setName("masterListPanel"); // NOI18N
        mainTabPane.addTab("Master List", masterListPanel);

        freeListPanel.setName("freeListPanel"); // NOI18N
        mainTabPane.addTab("Free List", freeListPanel);

        groupPanel.setName("groupPanel"); // NOI18N
        mainTabPane.addTab("Groups", groupPanel);

        boutPanel.setName("boutPanel"); // NOI18N
        mainTabPane.addTab(resourceMap.getString("boutPanel.TabConstraints.tabTitle"), boutPanel); // NOI18N

        scratchListPanel.setName("scratchListPanel"); // NOI18N
        mainTabPane.addTab(resourceMap.getString("scratchListPanel.TabConstraints.tabTitle"), scratchListPanel); // NOI18N

        setComponent(mainTabPane);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem allCoachesBoutReportMenuItem;
    private javax.swing.JMenuItem autoGroupMenuItem;
    private javax.swing.JMenu autobouterMenu;
    private javax.swing.JMenuItem awardInventoryReportMenuItem;
    private javax.swing.JMenuItem awardReportAllMenuItem;
    private javax.swing.JMenuItem awardReportBySessionMenuItem;
    private javax.swing.JMenu awardReportSubMenu;
    private javax.swing.JMenuItem blankBoutsheetMenuItem;
    private javax.swing.JMenu blankBracketsheetReportMenu;
    private javax.swing.JMenuItem boutByMatMenuItem;
    private javax.swing.JMenuItem boutByTourneyMenuItem;
    private bouttime.gui.panel.BoutPanel boutPanel;
    private javax.swing.JMenuItem boutSheetReportAllMenuItem;
    private javax.swing.JMenu boutSheetReportMenu;
    private javax.swing.JMenu boutsWithoutNumbersMenu;
    private javax.swing.JMenuItem boutsheetReportByMatMenuItem;
    private javax.swing.JMenuItem bracketSheetReportBlank2RRMenuItem;
    private javax.swing.JMenu bracketSheetReportMenu;
    private javax.swing.JMenuItem bracketsheetNoBoutsForDivMenuItem;
    private javax.swing.JMenuItem bracketsheetNoBoutsForGroupMenuItem;
    private javax.swing.JMenuItem bracketsheetNoBoutsForMatMenuItem;
    private javax.swing.JMenuItem bracketsheetReportBlank16BktMenuItem;
    private javax.swing.JMenuItem bracketsheetReportBlank2BktMenuItem;
    private javax.swing.JMenuItem bracketsheetReportBlank32BktMenuItem;
    private javax.swing.JMenuItem bracketsheetReportBlank3RRMenuItem;
    private javax.swing.JMenuItem bracketsheetReportBlank4BktMenuItem;
    private javax.swing.JMenuItem bracketsheetReportBlank4RRMenuItem;
    private javax.swing.JMenuItem bracketsheetReportBlank5RRMenuItem;
    private javax.swing.JMenuItem bracketsheetReportBlank8BktMenuItem;
    private javax.swing.JMenu bracketsheetReportBlankBktMenu;
    private javax.swing.JMenu bracketsheetReportBlankRRMenu;
    private javax.swing.JMenuItem bracketsheetReportByGroupMenuItem;
    private javax.swing.JMenuItem clearAllBoutsMenuItem;
    private javax.swing.JMenuItem clearBoutsByMatMenuItem;
    private javax.swing.JMenuItem clearBoutsByMatSessionMenuItem;
    private javax.swing.JMenuItem clearBoutsBySessionMenuItem;
    private javax.swing.JMenu clearBoutsMenu;
    private javax.swing.JMenu coachBoutReportMenu;
    private javax.swing.JMenuItem configMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exportToFilesMenuItem;
    private bouttime.gui.panel.FreeListPanel freeListPanel;
    private javax.swing.JMenu functionMenu;
    private bouttime.gui.panel.GroupPanel groupPanel;
    private javax.swing.JMenuItem inputFromSSMenuItem;
    private javax.swing.JMenuItem inputFromTextMenuItem;
    private javax.swing.JMenuItem inputFromXMLMenuItem;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JMenuItem lockAllGroupsMenuItem;
    private javax.swing.JMenuItem lockGroupsByMatMenuItem;
    private javax.swing.JMenuItem lockGroupsByMatSessionMenuItem;
    private javax.swing.JMenuItem lockGroupsBySessionMenuItem;
    private javax.swing.JMenu lockGroupsMenu;
    private javax.swing.JTabbedPane mainTabPane;
    private bouttime.gui.panel.MasterListPanel masterListPanel;
    private javax.swing.JMenuItem matKeyReportMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openExistingMenuItem;
    private javax.swing.JMenuItem openNewMenuItem;
    private javax.swing.JMenu outputWrestlersMenu;
    private javax.swing.JMenuItem outputWrestlersToTextFileMenuItem;
    private javax.swing.JMenu reportMenu;
    private bouttime.gui.panel.ScratchListPanel scratchListPanel;
    private javax.swing.JMenuItem teamCoachesBoutReportMenuItem;
    private javax.swing.JMenuItem teamReportDetailMenuItem;
    private javax.swing.JMenu teamReportMenu;
    private javax.swing.JMenuItem teamReportSummaryMenuItem;
    private javax.swing.JMenuItem unlockAllGroupsMenuItem;
    private javax.swing.JMenuItem unlockGroupsByMatMenuItem;
    private javax.swing.JMenuItem unlockGroupsByMatSessionMenuItem;
    private javax.swing.JMenuItem unlockGroupsBySessionMenuItem;
    private javax.swing.JMenu unlockGroupsMenu;
    private javax.swing.JMenuItem weighinMenuItem;
    // End of variables declaration//GEN-END:variables
    private JDialog aboutBox;
    private Dao dao;
    private String blankBracketOutputDirectory;
    private String blankBracketOutputFilePath;
    private int initAction;
    static Logger logger = Logger.getLogger(BoutTimeView.class);
    public static final int BOUTTIMEVIEW_INIT_ACTION_OPEN_EXISTING = 1;
    public static final int BOUTTIMEVIEW_INIT_ACTION_OPEN_NEW = 2;
    public static final int BOUTTIMEVIEW_INIT_ACTION_QUIT = 3;
}
