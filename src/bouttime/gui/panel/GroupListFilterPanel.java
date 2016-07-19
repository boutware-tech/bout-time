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
package bouttime.gui.panel;

import bouttime.boutmaker.BoutMaker;
import bouttime.dao.Dao;
import bouttime.mainview.BoutTimeApp;
import bouttime.mainview.BoutTimeView;
import bouttime.model.Bout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import bouttime.model.Group;
import bouttime.report.boutsheet.BoutSheetReport;
import bouttime.report.bracketsheet.BracketSheetReport;
import bouttime.sort.BoutNumSort;
import org.jdesktop.application.Action;
import bouttime.sort.GroupClassDivWtSort;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.observablecollections.ObservableCollections;
import org.apache.log4j.Logger;

/**
 * A class that implements a panel to filter the list of groups.
 */
public class GroupListFilterPanel extends javax.swing.JPanel implements ItemListener {

    static Logger logger = Logger.getLogger(GroupListFilterPanel.class);

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////
    /**
     * No-arg constructor to make netBeans GUI builder happy.  In order to
     * add this class to the Palette, you are required to have a no-arg
     * constructor.  There is no other need for this, so don't use it.
     * @deprecated
     */
    public GroupListFilterPanel() {
        this(null);
    }

    /** Creates new form GroupListFilterPanel */
    public GroupListFilterPanel(BoutTimeView v) {
        super();

        this.view = v;

        initComponents();

        // tracking table selection
        table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {

                    public void valueChanged(ListSelectionEvent e) {
                        if (e.getValueIsAdjusting()) {
                            updateSelectedCounter();
                            return;
                        }

                        firePropertyChange("tableRecordSelected", !isTableRecordSelected(), isTableRecordSelected());
                        // Let 'parent' container know that the value changed
                        // so other panels can be updated
                        int selected = table.getSelectedRow();

                        if (selected == -1) {
                            view.groupSelectionChanged(null);
                        } else {
                            Group g = list.get(table.convertRowIndexToModel(selected));
                            view.groupSelectionChanged(g);
                        }
                    }
                });

        setFilterLists();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Class Methods
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Called when comboBox selections change
     */
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            updateList();
        }
    }

    /**
     * Used to enabled/disable buttons on the JPanel.
     * @return True if a row in the table is selected.
     */
    public boolean isTableRecordSelected() {
        return table.getSelectedRow() != -1;
    }

    /**
     * Used to enabled/disable buttons on the JPanel.
     * @return True if the Dao is open.
     */
    public boolean isDaoOpen() {
        if (this.view == null) {
            return false;
        }

        return this.view.isDaoOpen();
    }

    /**
     * Called when a Dao property changes.
     * Refresh the GUI.
     */
    public void daoPropertyChange() {
        setFilterLists();
        clearFilters();
        initList();

        Group g = null;
        if (!list.isEmpty()) {
            // Select the first row.
            table.setRowSelectionInterval(0, 0);
            g = list.get(0);
        }

        // Let the parent container know that the value changed
        // so other panels can be updated
        this.view.groupSelectionChanged(g);

        firePropertyChange("daoOpen", !isDaoOpen(), isDaoOpen());
    }

    /**
     * Initialize the values in the filter combo boxes.
     */
    public void setFilterLists() {
        if (this.view == null) {
            logger.warn("Unable to set filter lists : this.view is null.");
            return;
        }

        Dao dao = this.view.getDao();
        if (!dao.isOpen()) {
            logger.warn("Unable to set filter lists : DAO is not open.");
            return;
        }

        matValues = convertCsvToObjectArray(dao.getMatValues());
        initComboBox(this.matComboBox, matValues);
        sessionValues = convertCsvToObjectArray(dao.getSessionValues());
        initComboBox(this.sessionComboBox, sessionValues);
        initComboBox(this.divComboBox, convertCsvToObjectArray(dao.getAgeDivisionValues()));
        initComboBox(this.classComboBox, convertCsvToObjectArray(dao.getClassificationValues()));
        boutTimeValues = convertCsvToObjectArray(dao.getBoutTimeValues());
        initComboBox(this.boutTimeComboBox, boutTimeValues);

        clearButton.setEnabled(true);
    }

    /**
     * Update the list and set the selection to the given group.
     * @param g Group to set as selected.
     */
    public void updateGroup(Group g) {
        updateList();
        int idx = this.list.indexOf(g);
        this.table.setRowSelectionInterval(idx, idx);
        logger.debug("Updated group [" + g + "]");

    }

    /**
     * Initialize the list of groups.
     */
    private void initList() {
        Dao dao = this.view.getDao();
        if (!dao.isOpen()) {
            logger.warn("Unable to initialize the list : DAO is not open.");
            return;
        }

        this.list.clear();
        List<Group> gList = new ArrayList<Group>(dao.getAllGroups());
        Collections.sort(gList, new GroupClassDivWtSort());
        this.list.addAll(gList);
        setBoutCount();

        logger.debug("Initialized list with " + this.list.size() + " groups");
    }

    /**
     * Update the list of groups.
     */
    public void updateList() {
        Dao dao = this.view.getDao();
        if (!dao.isOpen()) {
            logger.warn("Unable to update the list : DAO is not open.");
            return;  // do nothing
        }

        // Save the table's group selection so it can be restored.
        Group selectedGroup = null;
        int selected = table.getSelectedRow();
        if (selected != -1) {
            selectedGroup = list.get(table.convertRowIndexToModel(selected));
        }

        this.list.clear();
        initList();

        String mat = matComboBox.getSelectedItem().toString();
        boolean matDoFilter = !mat.equalsIgnoreCase(FILTER_CLEAR_KEYWORD);
        String div = divComboBox.getSelectedItem().toString();
        boolean divDoFilter = !div.equalsIgnoreCase(FILTER_CLEAR_KEYWORD);
        String cla = classComboBox.getSelectedItem().toString();
        boolean claDoFilter = !cla.equalsIgnoreCase(FILTER_CLEAR_KEYWORD);
        String ses = sessionComboBox.getSelectedItem().toString();
        boolean sesDoFilter = !ses.equalsIgnoreCase(FILTER_CLEAR_KEYWORD);
        String btime = boutTimeComboBox.getSelectedItem().toString();
        boolean btimeDoFilter = !btime.equalsIgnoreCase(FILTER_CLEAR_KEYWORD);
        List<Group> removeList = new ArrayList<Group>();

        for (Group g : this.list) {
            if ((matDoFilter && !g.getMat().equalsIgnoreCase(mat))
                    || (divDoFilter && !g.getAgeDivision().equalsIgnoreCase(div))
                    || (claDoFilter && !g.getClassification().equalsIgnoreCase(cla))
                    || (btimeDoFilter && !g.getBoutTime().equalsIgnoreCase(btime))
                    || (sesDoFilter && !g.getSession().equalsIgnoreCase(ses))) {
                removeList.add(g);
            }
        }

        logger.debug("Removing/filtering groups from list : " + removeList);

        this.list.removeAll(removeList);
        setBoutCount();

        // Set the row in the table
        if (list.contains(selectedGroup)) {
            // Restore the row selection for the previously selected group.
            int idx = list.indexOf(selectedGroup);
            table.setRowSelectionInterval(idx, idx);
        } else {
            // The previously selected group is not in the new list.
            if (!list.isEmpty()) {
                // Select the first row.
                table.setRowSelectionInterval(0, 0);
                selectedGroup = list.get(0);
            } else {
                // List is empty, nothing to select.
                selectedGroup = null;
            }

            // Let the parent container know that the value changed
            // so other panels can be updated
            this.view.groupSelectionChanged(selectedGroup);
            logger.debug("Selecting group [" + selectedGroup + "] in row "
                    + this.table.getSelectedRow());
        }
    }

    /**
     * Convert a comma-separated value String to an Object array.
     * @param str the comma-separated string to convert.
     * @return An Object array of trimmed Strings
     */
    private Object [] convertCsvToObjectArray(String str) {
        if ((str == null) || str.isEmpty()) {
            logger.debug("Unable to convert string : >" + str + "<");
            return null;
        }

        String[] tokens = str.split(",");
        Object [] values = new Object [tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            values[i] = tokens[i].trim();
        }

        logger.debug("Converted csv string : " + values);
        return values;
    }

    /**
     * Initialize the values for a given combo box.
     * @param comboBox The combo box to initialize.
     * @param values Array of Strings
     */
    private void initComboBox(JComboBox comboBox, Object [] values) {
        comboBox.removeItemListener(this);
        comboBox.removeAllItems();

        comboBox.addItem(FILTER_CLEAR_KEYWORD);

        if (values == null) {
            logger.debug("Unable to initialize combo box");
            return;
        }
        
        for (Object o : values) {
            comboBox.addItem(o);
        }

        comboBox.addItemListener(this);

        logger.debug("Initialized combo box [" + comboBox.getName() + "] with "
                + comboBox.getItemCount() + " items");
    }

    /**
     * Count the number of bouts for the groups that are displayed and set
     * the value in the bout count textfield.
     */
    private void setBoutCount() {
        int count = 0;
        for (Group g : this.list) {
            count += g.getNumBouts();
        }

        boutCount.setText(Integer.toString(count));
    }

    /**
     * Clear the filters combo boxes.
     */
    @Action
    public void clearFilters() {
        matComboBox.setSelectedItem(FILTER_CLEAR_KEYWORD);
        sessionComboBox.setSelectedItem(FILTER_CLEAR_KEYWORD);
        divComboBox.setSelectedItem(FILTER_CLEAR_KEYWORD);
        classComboBox.setSelectedItem(FILTER_CLEAR_KEYWORD);
        boutTimeComboBox.setSelectedItem(FILTER_CLEAR_KEYWORD);
        logger.debug("Cleared filters");
    }

    /**
     * Delete a group.
     * This removes the bouts and puts the wrestlers back on the free list.
     */
    @Action(enabledProperty = "tableRecordSelected")
    public void deleteGroup() {
        Dao dao = this.view.getDao();
        boolean hasLockedGroup = false;

        List<Group> gList = new ArrayList<Group>();
        List<Group> lockedList = new ArrayList<Group>();
        int[] selected = table.getSelectedRows();
        for (int idx = 0; idx < selected.length; idx++) {
            Group g = list.get(table.convertRowIndexToModel(selected[idx]));
            if (g.isLocked()) {
                hasLockedGroup = true;
                lockedList.add(g);
            } else {
                gList.add(g);
            }
        }

        if (hasLockedGroup) {
            JOptionPane.showMessageDialog(this.view.getFrame(), "One or more selected groups are locked."
                    + "\nThese groups will not be removed."
                    + "\nYou must unlock these group before attempting this operation.",
                    "Locked Group", JOptionPane.WARNING_MESSAGE);
            logger.warn("Unable to remove group(s) : group is locked\n" + lockedList);
        }

        logger.debug("Deleting " + gList.size() + " groups : " + gList);

        for (Group g : gList) {
            list.remove(g);
            dao.deleteGroup(g);
        }

        dao.flush();

        this.view.updateFreeList();
        this.view.refreshStats();
        setBoutCount();

        Group grp;
        if (!list.isEmpty()) {
            // Select the first row.
            table.setRowSelectionInterval(0, 0);
            grp = list.get(0);
        } else {
            // List is empty, nothing to select.
            grp = null;
        }

        // Let the parent container know that the value changed
        // so other panels can be updated
        this.view.groupSelectionChanged(grp);
        this.view.groupDeleted();
    }

    /**
     * Write any changes to the Dao and refresh the statistics.
     */
    @Action(enabledProperty = "daoOpen")
    public void update() {
        Dao dao = this.view.getDao();
        if (!dao.isOpen()) {
            logger.warn("Unable update : DAO is not open.");
            return;  // do nothing
        }

        logger.debug("Performing update action");

        dao.flush();

        this.view.refreshStats();
    }

    /**
     * Generate a bout sheet report for the groups in the (filtered) list.
     */
    @Action(enabledProperty = "daoOpen")
    public void doBoutSheet() {
        if (this.list.isEmpty()) {
            logger.warn("Unable to generate bout sheet report : list is empty.");
            return;
        }

        Dao dao = this.view.getDao();
        if (!dao.isOpen()) {
            logger.warn("Unable to generate bout sheet report : DAO is not open.");
            return;  // do nothing
        }

        List<Bout> bList = new ArrayList<Bout>();
        for (Group g : this.list) {
            bList.addAll(g.getBouts());
        }

        Collections.sort(bList, new BoutNumSort());

        logger.debug("Generating bout sheet report for : " + bList);

        BoutSheetReport report = new BoutSheetReport();
        if (report.generateReport(dao, bList)) {
            this.view.showPDF(report.getoutputFilePath());
        }
    }

    /**
     * Generate a bracket sheet report for groups in the (filtered) list.
     */
    @Action(enabledProperty = "daoOpen")
    public void doBracketSheet() {
        if (this.list.isEmpty()) {
            logger.warn("Unable to generate bracket sheet report : list is empty.");
            return;
        }

        Dao dao = this.view.getDao();
        if (!dao.isOpen()) {
            logger.warn("Unable to generate bracket sheet report : DAO is not open.");
            return;  // do nothing
        }

        boolean includeTimestamp = this.view.includeTimestampForBracketsheet();

        if (BracketSheetReport.generateReport(dao, this.list, true, includeTimestamp)) {
            this.view.showPDF(BracketSheetReport.getoutputFilePath());
        }
    }

    /**
     * Set the session value for one or more groups.
     */
    @Action(enabledProperty = "tableRecordSelected")
    public void setSession() {
        if ((sessionValues == null) || (sessionValues.length < 1)) {
            logger.debug("Unable to set session : null or empty");
            return;
        }
        
        JFrame mainFrame = BoutTimeApp.getApplication().getMainFrame();

        String session = (String) JOptionPane.showInputDialog(mainFrame, null,
                "Set session", JOptionPane.PLAIN_MESSAGE, null,
                sessionValues, sessionValues[0]);

        if (session == null) {
            logger.debug("Unable to set session : no session selected");
            return;
        }

        boolean lockedGroupMessageDisplayed = false;
        int[] selected = table.getSelectedRows();
        for (int idx = 0; idx < selected.length; idx++) {
            Group g = list.get(table.convertRowIndexToModel(selected[idx]));
            if (g.isLocked()) {
                if (!lockedGroupMessageDisplayed) {
                    displayLockedGroupMessage();
                    lockedGroupMessageDisplayed = true;
                }

                logger.warn("Attempted to change session for a locked group :" + g);

                continue;
            }
            
            // Optimization - no need to set session if it's the same
            if (session.equals(g.getSession())) {
                continue;
            }
            
            g.setSession(session);
            logger.debug("Setting session for group [" + g + "] to [" + session + "]");
        }

        this.view.getDao().flush();
        this.view.refreshStats();
        updateList();
    }

    /**
     * Set the mat value for one or more groups.
     */
    @Action(enabledProperty = "tableRecordSelected")
    public void setMat() {
        if ((matValues == null) || (matValues.length < 1)) {
            logger.debug("Unable to set mat : null or empty");
            return;
        }
        
        JFrame mainFrame = BoutTimeApp.getApplication().getMainFrame();

        String mat = (String) JOptionPane.showInputDialog(mainFrame, null,
                "Set mat", JOptionPane.PLAIN_MESSAGE, null,
                matValues, matValues[0]);

        if (mat == null) {
            logger.debug("Unable to set mat : no mat selected");
            return;
        }

        boolean lockedGroupMessageDisplayed = false;
        int[] selected = table.getSelectedRows();
        for (int idx = 0; idx < selected.length; idx++) {
            Group g = list.get(table.convertRowIndexToModel(selected[idx]));
            if (g.isLocked()) {
                if (!lockedGroupMessageDisplayed) {
                    displayLockedGroupMessage();
                    lockedGroupMessageDisplayed = true;
                }

                logger.warn("Attempted to change mat for a locked group :" + g);

                continue;
            }
            
            // Optimization - no need to set mat if it's the same
            if (mat.equals(g.getMat())) {
                continue;
            }
            
            g.setMat(mat);
            logger.debug("Setting mat for group [" + g + "] to [" + mat + "]");
        }

        this.view.getDao().flush();
        this.view.refreshStats();
        updateList();
    }

    /**
     * Set the bout time value for one or more groups.
     */
    @Action(enabledProperty = "tableRecordSelected")
    public void setBoutTime() {
        if ((boutTimeValues == null) || (boutTimeValues.length < 1)) {
            logger.debug("Unable to set bout time : null or empty");
            return;
        }
        
        JFrame mainFrame = BoutTimeApp.getApplication().getMainFrame();

        String time = (String) JOptionPane.showInputDialog(mainFrame, null,
                "Set bout time", JOptionPane.PLAIN_MESSAGE, null,
                boutTimeValues, boutTimeValues[0]);

        if (time == null) {
            logger.debug("Unable to set bout time : no time selected");
            return;
        }

        int[] selected = table.getSelectedRows();
        boolean lockedGroupMessageDisplayed = false;
        for (int idx = 0; idx < selected.length; idx++) {
            Group g = list.get(table.convertRowIndexToModel(selected[idx]));
            if (g.isLocked()) {
                if (!lockedGroupMessageDisplayed) {
                    displayLockedGroupMessage();
                    lockedGroupMessageDisplayed = true;
                }

                logger.warn("Attempted to change bout time for a locked group :" + g);

                continue;
            }
            
            // Optimization - no need to set bout time if it's the same
            if (time.equals(g.getBoutTime())) {
                continue;
            }
            
            g.setBoutTime(time);
            logger.debug("Setting bout time for group [" + g + "] to [" + time + "]");
            BoutMaker.setBoutTime(g);
        }

        this.view.getDao().flush();
        this.view.refreshStats();
        updateList();
    }
    
    private void updateSelectedCounter() {
        int selectedCount = 0;
        int[] selected = table.getSelectedRows();
        for (int idx = 0; idx < selected.length; idx++) {
            Group g = list.get(table.convertRowIndexToModel(selected[idx]));
            selectedCount += g.getNumBouts();
        }
        selectedCounterLabel.setText(Integer.toString(selectedCount));
    }

    /**
     * Display a warning message that the group is locked.
     */
    private void displayLockedGroupMessage() {
        JOptionPane.showMessageDialog(this.view.getFrame(),
                "The group is locked, so no changes are allowed."
                + "\nYou must unlock the group to make changes to it.",
                "Locked Group", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Display a pop-up menu for user-friendliness.
     * @param evt The mouse event.
     */
    private void maybeShowPopup(MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        list = ObservableCollections.observableList(new ArrayList<Group>());
        popupMenu = new javax.swing.JPopupMenu();
        setSessionMenuItem = new javax.swing.JMenuItem();
        setMatMenuItem = new javax.swing.JMenuItem();
        setBoutTimeMenuItem = new javax.swing.JMenuItem();
        classLabel = new javax.swing.JLabel();
        divLabel = new javax.swing.JLabel();
        sessionLabel = new javax.swing.JLabel();
        matLabel = new javax.swing.JLabel();
        clearButton = new javax.swing.JButton();
        matComboBox = new javax.swing.JComboBox();
        sessionComboBox = new javax.swing.JComboBox();
        divComboBox = new javax.swing.JComboBox();
        classComboBox = new javax.swing.JComboBox();
        deleteButton = new javax.swing.JButton();
        boutCountLabel = new javax.swing.JLabel();
        boutCount = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        boutsheetButton = new javax.swing.JButton();
        bracketsheetButton = new javax.swing.JButton();
        boutTimeLabel = new javax.swing.JLabel();
        boutTimeComboBox = new javax.swing.JComboBox();
        selectedCounterLabel = new javax.swing.JLabel();

        popupMenu.setName("popupMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getActionMap(GroupListFilterPanel.class, this);
        setSessionMenuItem.setAction(actionMap.get("setSession")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(GroupListFilterPanel.class);
        setSessionMenuItem.setText(resourceMap.getString("setSessionMenuItem.text")); // NOI18N
        setSessionMenuItem.setName("setSessionMenuItem"); // NOI18N
        popupMenu.add(setSessionMenuItem);

        setMatMenuItem.setAction(actionMap.get("setMat")); // NOI18N
        setMatMenuItem.setText(resourceMap.getString("setMatMenuItem.text")); // NOI18N
        setMatMenuItem.setName("setMatMenuItem"); // NOI18N
        popupMenu.add(setMatMenuItem);

        setBoutTimeMenuItem.setAction(actionMap.get("setBoutTime")); // NOI18N
        setBoutTimeMenuItem.setText(resourceMap.getString("setBoutTimeMenuItem.text")); // NOI18N
        setBoutTimeMenuItem.setName("setBoutTimeMenuItem"); // NOI18N
        popupMenu.add(setBoutTimeMenuItem);

        setName("Form"); // NOI18N

        classLabel.setText(resourceMap.getString("classLabel.text")); // NOI18N
        classLabel.setName("classLabel"); // NOI18N

        divLabel.setText(resourceMap.getString("divLabel.text")); // NOI18N
        divLabel.setName("divLabel"); // NOI18N

        sessionLabel.setText(resourceMap.getString("sessionLabel.text")); // NOI18N
        sessionLabel.setName("sessionLabel"); // NOI18N

        matLabel.setText(resourceMap.getString("matLabel.text")); // NOI18N
        matLabel.setName("matLabel"); // NOI18N

        clearButton.setAction(actionMap.get("clearFilters")); // NOI18N
        clearButton.setText(resourceMap.getString("clearButton.text")); // NOI18N
        clearButton.setName("clearButton"); // NOI18N

        matComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALL" }));
        matComboBox.setName("matComboBox"); // NOI18N
        matComboBox.addItemListener(this);

        sessionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALL" }));
        sessionComboBox.setName("sessionComboBox"); // NOI18N
        sessionComboBox.addItemListener(this);

        divComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALL" }));
        divComboBox.setName("divComboBox"); // NOI18N
        divComboBox.addItemListener(this);

        classComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALL" }));
        classComboBox.setName("classComboBox"); // NOI18N
        classComboBox.addItemListener(this);

        deleteButton.setAction(actionMap.get("deleteGroup")); // NOI18N
        deleteButton.setText(resourceMap.getString("deleteButton.text")); // NOI18N
        deleteButton.setName("deleteButton"); // NOI18N

        boutCountLabel.setText(resourceMap.getString("boutCountLabel.text")); // NOI18N
        boutCountLabel.setName("boutCountLabel"); // NOI18N

        boutCount.setEditable(false);
        boutCount.setText(resourceMap.getString("boutCount.text")); // NOI18N
        boutCount.setName("boutCount"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        table.setName("table"); // NOI18N
        table.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, table);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${locked}"));
        columnBinding.setColumnName("Locked");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${classification}"));
        columnBinding.setColumnName("Classification");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${ageDivision}"));
        columnBinding.setColumnName("Age Division");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${weightClass}"));
        columnBinding.setColumnName("Weight Class");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${numWrestlers}"));
        columnBinding.setColumnName("Num Wrestlers");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${numBouts}"));
        columnBinding.setColumnName("Num Bouts");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${session}"));
        columnBinding.setColumnName("Session");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mat}"));
        columnBinding.setColumnName("Mat");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${boutTime}"));
        columnBinding.setColumnName("Bout Time");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("table.columnModel.title8")); // NOI18N
        table.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("table.columnModel.title1")); // NOI18N
        table.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("table.columnModel.title2")); // NOI18N
        table.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("table.columnModel.title3")); // NOI18N
        table.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("table.columnModel.title4")); // NOI18N
        table.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("table.columnModel.title5")); // NOI18N
        table.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("table.columnModel.title0")); // NOI18N
        table.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("table.columnModel.title7")); // NOI18N
        table.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("table.columnModel.title6")); // NOI18N
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        boutsheetButton.setAction(actionMap.get("doBoutSheet")); // NOI18N
        boutsheetButton.setText(resourceMap.getString("boutsheetButton.text")); // NOI18N
        boutsheetButton.setName("boutsheetButton"); // NOI18N

        bracketsheetButton.setAction(actionMap.get("doBracketSheet")); // NOI18N
        bracketsheetButton.setText(resourceMap.getString("bracketsheetButton.text")); // NOI18N
        bracketsheetButton.setName("bracketsheetButton"); // NOI18N

        boutTimeLabel.setText(resourceMap.getString("boutTimeLabel.text")); // NOI18N
        boutTimeLabel.setName("boutTimeLabel"); // NOI18N

        boutTimeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALL" }));
        boutTimeComboBox.setName("boutTimeComboBox"); // NOI18N
        matComboBox.addItemListener(this);

        selectedCounterLabel.setText(resourceMap.getString("selectedCounterLabel.text")); // NOI18N
        selectedCounterLabel.setName("selectedCounterLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boutCountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boutCount, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectedCounterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addComponent(bracketsheetButton)
                .addGap(18, 18, 18)
                .addComponent(boutsheetButton)
                .addGap(18, 18, 18)
                .addComponent(deleteButton)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(classLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(divComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(divLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sessionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sessionLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(matComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(matLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(boutTimeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clearButton))
                    .addComponent(boutTimeLabel))
                .addContainerGap(130, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(classLabel)
                            .addComponent(divLabel)
                            .addComponent(sessionLabel)
                            .addComponent(matLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(divComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sessionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(matComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(boutTimeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(boutTimeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boutCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutCountLabel)
                    .addComponent(selectedCounterLabel)
                    .addComponent(deleteButton)
                    .addComponent(boutsheetButton)
                    .addComponent(bracketsheetButton))
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void tableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMousePressed
        maybeShowPopup(evt);
    }//GEN-LAST:event_tableMousePressed

    private void tableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseReleased
        maybeShowPopup(evt);
    }//GEN-LAST:event_tableMouseReleased
    ////////////////////////////////////////////////////////////////////////////
    //     D E S I G N     N O T E :
    // Combo boxes for table cells use suffix "2" to differentiate them.
    // We can't use the same ones from the "filters" panel since they disappear
    // from the "filters" panel when used in a table cell.
    ////////////////////////////////////////////////////////////////////////////
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField boutCount;
    private javax.swing.JLabel boutCountLabel;
    private javax.swing.JComboBox boutTimeComboBox;
    private javax.swing.JLabel boutTimeLabel;
    private javax.swing.JButton boutsheetButton;
    private javax.swing.JButton bracketsheetButton;
    private javax.swing.JComboBox classComboBox;
    private javax.swing.JLabel classLabel;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JComboBox divComboBox;
    private javax.swing.JLabel divLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private java.util.List<Group> list;
    private javax.swing.JComboBox matComboBox;
    private javax.swing.JLabel matLabel;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JLabel selectedCounterLabel;
    private javax.swing.JComboBox sessionComboBox;
    private javax.swing.JLabel sessionLabel;
    private javax.swing.JMenuItem setBoutTimeMenuItem;
    private javax.swing.JMenuItem setMatMenuItem;
    private javax.swing.JMenuItem setSessionMenuItem;
    private javax.swing.JTable table;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private BoutTimeView view;
    private final String FILTER_CLEAR_KEYWORD = "All";
    private Object [] boutTimeValues;
    private Object [] matValues;
    private Object [] sessionValues;
}
