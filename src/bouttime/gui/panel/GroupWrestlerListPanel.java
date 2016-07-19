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

import bouttime.dao.Dao;
import bouttime.mainview.BoutTimeApp;
import bouttime.mainview.BoutTimeView;
import bouttime.boutmaker.BoutMaker;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import bouttime.report.award.AwardReport;
import bouttime.sort.GroupClassDivWtSort;
import bouttime.sort.WrestlerSeedSort;
import bouttime.utility.seed.RandomSeed;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.application.Action;
import org.jdesktop.observablecollections.ObservableCollections;
import org.apache.log4j.Logger;

/**
 * A class to implement a panel for a list of wrestlers belonging to a group.
 */
public class GroupWrestlerListPanel extends javax.swing.JPanel {
    static Logger logger = Logger.getLogger(GroupWrestlerListPanel.class);

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    /**
     * No-arg constructor to make netBeans GUI builder happy.  In order to
     * add this class to the Palette, you are required to have a no-arg
     * constructor.  There is no other need for this, so don't use it.
     * @deprecated
     */
    public GroupWrestlerListPanel() {this(null);}

    /** Creates new form GroupWrestlerListPanel */
    public GroupWrestlerListPanel(BoutTimeView v) {
        super();
        
        this.view = v;
        
        initComponents();

        // tracking table selection
        table.getSelectionModel().addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) {
                        return;
                    }
                    
                    firePropertyChange("tableRecordSelected", !isTableRecordSelected(), isTableRecordSelected());
                }
            });

        initSeedComboBox();
        initPlaceComboBox();
        
        // Hack to set the dimension/size of the popup menu so it displays nicely.
        // Otherwise, the first time it is displayed it will cover the moveToButton.
        moveToPopupMenu.setVisible(true);
        moveToPopupMenu.setVisible(false);
        
    }

    ////////////////////////////////////////////////////////////////////////////
    // Class Methods
    ////////////////////////////////////////////////////////////////////////////

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
     * A Dao property has changed.
     * Re-initialize the "place" combo box and fire a notification.
     */
    public void daoPropertyChange() {
        initPlaceComboBox();
    
        firePropertyChange("daoOpen", !isDaoOpen(), isDaoOpen());
    }

    /**
     * The "parent" container needs us to update our list.
     * @param g The group to use to update ourself.
     */
    public void updateList(Group g) {
        updateList(g, true);
    }

    /**
     * Update the list that the table displays.
     * @param g The group to use when we update.
     * @param doChanges If true, handle any changes that were made before
     * updating the list.
     */
    private void updateList(Group g, boolean doChanges) {
        if (doChanges) {
            handleListChanges(g);
        }

        this.list.clear();

        if (g == null) {
            logger.debug("Unable to update the list : group param is null.");
            return;
        }

        List<Wrestler> wList = new ArrayList<Wrestler>(g.getWrestlers());
        Collections.sort(wList, new WrestlerSeedSort());
        //this.list.addAll(wList);
        initList(wList);
        if (hasDuplicateSeed()) {
            JFrame mainFrame = BoutTimeApp.getApplication().getMainFrame();
            JOptionPane.showMessageDialog(mainFrame, "Two or more wrestlers have" +
                " the same seed value.", "Duplicate seed value",
                JOptionPane.WARNING_MESSAGE);
            logger.warn("Duplicate seed in group [" + g + "] " + g.getWrestlers());
        }

        initSeedComboBox();

        logger.debug("Updated list with group [" + g + "]");
    }

    /**
     * Determine if the given list of wrestlers have one or more wrestlers with
     * the same seed value.
     *
     * @param wList
     * @return True if 2 or more wrestlers have the same "seed" value.
     */
    private boolean hasDuplicateSeed() {
        Set<Integer> seedSet = new HashSet<Integer>();

        for (Wrestler w : this.list) {
            seedSet.add(w.getSeed());
        }

        boolean rv = this.list.size() != seedSet.size();
        if (rv) {
            logger.debug("Duplicate seed : " + seedSet);
        }

        return (rv);
    }

    /**
     * Initialize the seed values in the combo box.
     */
    private void initSeedComboBox() {
        this.seedComboBox.removeAllItems();

        if (this.view == null) {
            this.seedComboBox.addItem(1);
            return;
        }
        
        Dao dao = this.view.getDao();
        if ((dao == null) || !dao.isOpen()) {
            this.seedComboBox.addItem(1);
            return;
        }

        int limit;
        int size = this.list.size();

        if (dao.isRoundRobinEnabled() && (size <= dao.getRoundRobinMax())) {
                limit = size;
        } else {
            switch(size) {
                case 1:
                case 2:
                case 3:
                case 4:
                    limit = size;
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    limit = 8;
                    break;
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    limit = 16;
                    break;
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                    limit = size;
                    break;

                default:
                    limit = 1;
                    break;
            }
        }
        
        for (int i = 1; i <= limit; i++) {
            this.seedComboBox.addItem(i);
        }
        
        logger.debug("Initialized seed combo box with " + this.seedComboBox.getItemCount() + " items");
    }

    /**
     * Initialize the values in the place combo box.
     */
    private void initPlaceComboBox() {
        if (this.view == null) {
            logger.warn("Unable to initialize place combo box : this.view is null.");
            return;
        }
        
        if (!this.view.isDaoOpen()) {
            logger.warn("Unable to initialize place combo box : DAO is not open.");
            return;
        }

        // If the existing placeComboBox has the focus and we're called
        // from daoPropertyChange(), we'll get an exception unless we create
        // a new JComboBox with the new values.
        JComboBox newPlaceComboBox = new JComboBox();
        newPlaceComboBox.setName("placeComboBox");

        Dao dao = this.view.getDao();
        Integer maxPlace = dao.getMaxAward();

        newPlaceComboBox.addItem(null);
        for (Integer i = 1; i <= maxPlace; i++) {
            newPlaceComboBox.addItem(i);
        }

        this.placeComboBox = newPlaceComboBox;

        // Assumes the "Place" column is the 8th column (index 8).
        // If that changes, then the argument to the "getColumn" call in the
        // next line will need to be updated.
        table.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(placeComboBox));

        logger.debug("Initialized place combo box with " + this.placeComboBox.getItemCount() + " items");
    }

    /**
     * Update the bouts for this group.
     */
    private void updateBouts() {
        Dao dao = this.view.getDao();

        if (!dao.isOpen()) {
            logger.warn("Unable to update bouts : DAO is not open.");
            return;
        }

        Group g = this.list.get(0).getGroup();

        logger.debug("Updating bouts for group [" + g + "]");

        g.setBouts(null);
        BoutMaker.makeBouts(g, dao.isFifthPlaceEnabled(),
                dao.isSecondPlaceChallengeEnabled(), dao.isRoundRobinEnabled(),
                dao.getRoundRobinMax(), dao.getDummyWrestler());
    }

    /**
     * Initialize the list for the table.
     * @param wList The list to use.
     */
    private void initList(List<Wrestler> wList) {
        // Create proxy objects and add them to the list
        for (Wrestler w : wList) {
            WrestlerProxy wp = new WrestlerProxy(w);

            this.list.add(wp);
        }

        logger.debug("Initialized list : " + wList);
    }

    /**
     * Handle any changes that were made to the object in the list.
     */
    private void handleListChanges(Group g) {
        if (g == null) {
            return;
        }
        
        boolean change = false;
        
        // Only do this if the size of the group has NOT changed
        if (g.getWrestlers().size() == this.list.size()) {
            for (int i = 0; i < this.list.size(); i++) {
                WrestlerProxy wp = (WrestlerProxy)this.list.get(i);
                Wrestler w = wp.getWrestler();

                // Handle changes to seed values
                Integer wpSeed = wp.getSeed();
                Integer wSeed = w.getSeed();
                // Note : Java boxing rules will compare Integer primitive values
                // when using "==", but will compare object values when using "!=".
                if (!(wpSeed == wSeed)) {
                    logger.debug("Seed changed for " + w + "\n    old=" + wSeed +
                            ", new=" + wpSeed);
                    w.setSeed(wpSeed);
                    change = true;
                }
                
                w.setSeedSet(wp.isSeedSet());

                // Handle changes to place values
                Integer wpPlace = wp.getPlace();
                Integer wPlace = w.getPlace();
                // Note : Java boxing rules will compare Integer primitive values
                // when using "==", but will compare object values when using "!=".
                if (!(wpPlace == wPlace)) {
                    logger.debug("Place changed for " + w + "\n    old=" + wPlace +
                            ", new=" + wpPlace);
                    w.setPlace(wpPlace);
                }
            }
        }

        if (change) {
            updateBouts();
        }
    }

    /**
     * Generate an award report for the group.
     */
    @Action
    public void awardReportForGroup() {
        Dao dao = this.view.getDao();
        Group g = this.list.get(0).getGroup();

        if (!g.isLocked()) {
            handleListChanges(g);    // just in case the user made changes
            dao.flush();
        }

        logger.debug("Generating award report for group [" + g + "]");

        if (AwardReport.doByGroup(dao, g)) {
            this.view.showPDF(AwardReport.getoutputFilePath());
        }
    }
    
    private boolean isGroupLocked(Group g, String title) {
        if (g.isLocked()) {
            this.table.grabFocus();
            JOptionPane.showMessageDialog(this.view.getFrame(), "This group is locked." +
                    "\nYou must unlock the group before attempting this operation.",
                    title, JOptionPane.ERROR_MESSAGE);
            return true;
        }
        
        return false;
    }

    /**
     * Write any changes to the Dao.
     */
    @Action(enabledProperty="tableRecordSelected")
    public void update() {
        Group g = this.list.get(0).getGroup();
        Dao dao = this.view.getDao();

        if (isGroupLocked(g, "Update Wrestler List Error")) {
            logger.info("Attempted to update a locked group");
            return;
        }

        logger.debug("Performing update action for group [" + g + "]");

        // Re-Sort the list
        updateList(g, true);
        this.view.updateGroup(g);

        dao.flush();
    }
    
    /**
     * Randomly seed the group
     */
    @Action(enabledProperty = "daoOpen")
    public void shuffle() {
        Group g = this.list.get(0).getGroup();
        Dao dao = this.view.getDao();
        
        if (isGroupLocked(g, "Shuffle Wrestler List Error")) {
            logger.info("Attempted to shuffle a locked group");
            return;
        }

        logger.debug("Performing shuffle action for group [" + g + "]");
        
        for (int i = 0; i < this.list.size(); i++) {
            WrestlerProxy wp = (WrestlerProxy)this.list.get(i);
            Wrestler w = wp.getWrestler();
            w.setSeedSet(wp.isSeedSet());
            w.setSeed(wp.getSeed());
        }
        
        try {
            RandomSeed.execute(g);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.view.getFrame(), "An error occurred while assigning seed values." +
                    "\nCheck the seed settings and try again.",
                    "Shuffle Wrestler List Error", JOptionPane.ERROR_MESSAGE);
            logger.warn("Attempted to shuffle a group and failed due to exception", e);
            return;
        }
        g.setBouts(null);
        BoutMaker.makeBouts(g, dao.isFifthPlaceEnabled(),
                dao.isSecondPlaceChallengeEnabled(), dao.isRoundRobinEnabled(),
                dao.getRoundRobinMax(), dao.getDummyWrestler());
        updateList(g, false);

        dao.flush();
        this.view.updateGroup(g);
    }

    /**
     * Display the moveToPopupMenu.
     */
    @Action(enabledProperty="tableRecordSelected")
    public void moveTo() {
        moveToPopupMenu.show(this, this.jButton1.getX(),
                this.jButton1.getY() - this.moveToPopupMenu.getHeight());
    }

    /**
     * Move the selected wrestlers to the Free List.
     */
    @Action
    public void moveToFreeList() {
        Dao dao = this.view.getDao();

        // If a table record has been selected (the enabledProperty), then
        // 'list.get(0)' should always return a wrestler.  Furthermore, this
        // wrestler must belong to a group, since it's in the list.
        // Therefore, an NPE here would mean something is out of sync.
        Group g = this.list.get(0).getGroup();

        if (isGroupLocked(g, "Move Wrestler Error")) {
            logger.warn("Unable to remove wrestler(s) from group [" + g + "] : group is locked");
            return;
        }

        int[] selected = table.getSelectedRows();
        if (selected.length == g.getWrestlers().size()) {
            logger.info("Deleting all wrestlers from group [" + g + "]");
            dao.deleteGroup(g);
            this.view.updateGroupList();
            this.seedComboBox.removeAllItems();
        } else {
            List<WrestlerProxy> wpList = new ArrayList<WrestlerProxy>();
            for (int idx=0; idx<selected.length; idx++) {
                int i = table.convertRowIndexToModel(selected[idx]);
                wpList.add((WrestlerProxy)list.get(i));
            }
            logger.info("Deleting wrestlers from group [" + g + "] : " + wpList);
            for (WrestlerProxy wp : wpList) {
                list.remove(wp);
                Wrestler w = wp.getWrestler();
                dao.removeWrestlerFromGroup(w, g);
            }

            RandomSeed.execute(g);
            g.setBouts(null);
            BoutMaker.makeBouts(g, dao.isFifthPlaceEnabled(),
                    dao.isSecondPlaceChallengeEnabled(), dao.isRoundRobinEnabled(),
                    dao.getRoundRobinMax(), dao.getDummyWrestler());
            initSeedComboBox();
            updateList(g, false);
            this.view.updateGroup(g);
        }

        dao.flush();

        this.view.updateFreeList();
        this.view.refreshStats();
    }

    /**
     * Move the selected wrestlers to a new Group.
     */
    @Action
    public void moveToNewGroup() {
        Dao dao = this.view.getDao();

        // If a table record has been selected (the enabledProperty), then
        // 'list.get(0)' should always return a wrestler.  Furthermore, this
        // wrestler must belong to a group, since it's in the list.
        // Therefore, an NPE here would mean something is out of sync.
        Group g = this.list.get(0).getGroup();

        if (isGroupLocked(g, "Move Wrestler Error")) {
            logger.warn("Unable to move wrestler(s) from group [" + g + "] : group is locked");
            return;
        }
        
        int[] selected = table.getSelectedRows();
        if (selected.length == g.getWrestlers().size()) {
            // Makes no sense
            logger.info("Deleting all wrestlers from group [" + g + "] and moving to new group...no-op");
            return;
        }
        
        List<WrestlerProxy> wpList = new ArrayList<WrestlerProxy>();
        List<Wrestler> wList = new ArrayList<Wrestler>();
        for (int idx=0; idx<selected.length; idx++) {
            int i = table.convertRowIndexToModel(selected[idx]);
            wpList.add((WrestlerProxy)list.get(i));
        }
        logger.info("Deleting wrestlers from group [" + g + "] : " + wpList);
        for (WrestlerProxy wp : wpList) {
            list.remove(wp);
            Wrestler w = wp.getWrestler();
            dao.removeWrestlerFromGroup(w, g);
            wList.add(wp.getWrestler());
        }

        RandomSeed.execute(g);
        g.setBouts(null);
        BoutMaker.makeBouts(g, dao.isFifthPlaceEnabled(),
                dao.isSecondPlaceChallengeEnabled(), dao.isRoundRobinEnabled(),
                dao.getRoundRobinMax(), dao.getDummyWrestler());
        initSeedComboBox();
        updateList(g, false);
        this.view.updateGroup(g);
        
        // Now create a new group
        g = new Group(wList);
        RandomSeed.execute(g);
        dao.addGroup(g);
        BoutMaker.makeBouts(g, dao.isFifthPlaceEnabled(), dao.isSecondPlaceChallengeEnabled(),
                dao.isRoundRobinEnabled(), dao.getRoundRobinMax(), dao.getDummyWrestler());
        logger.debug("Created new group [" + g + "] with " + wList);
        
        dao.flush();
        this.view.updateGroupList();
        this.view.refreshStats();
    }

    /**
     * Move the selected wrestlers to a an existing Group.
     */
    @Action
    public void moveToExistingGroup() {
        Dao dao = this.view.getDao();
        
        // If a table record has been selected (the enabledProperty), then
        // 'list.get(0)' should always return a wrestler.  Furthermore, this
        // wrestler must belong to a group, since it's in the list.
        // Therefore, an NPE here would mean something is out of sync.
        Group fromGroup = this.list.get(0).getGroup();
        if (isGroupLocked(fromGroup, "Move Wrestler Error")) {
            logger.warn("Unable to move wrestler(s) from group [" + fromGroup + "] : group is locked");
            return;
        }
        
        List<Group> gList = dao.getAllGroups();
        Collections.sort(gList, new GroupClassDivWtSort());
        Object [] grps = gList.toArray();

        JFrame mainFrame = BoutTimeApp.getApplication().getMainFrame();

        Group toGroup = (Group)JOptionPane.showInputDialog(mainFrame, null,
                "Add wrestler to group", JOptionPane.PLAIN_MESSAGE, null,
                grps, grps[0]);

        if (toGroup == null) {
            logger.debug("Unable to add wrestlers to group : no group selected");
            return;
        }
        
        if (isGroupLocked(toGroup, "Move Wrestler Error")) {
            logger.warn("Unable to move wrestler(s) to group [" + fromGroup + "] : group is locked");
            return;
        }
        
        List<Wrestler> wList = new ArrayList<Wrestler>();
        int[] selected = table.getSelectedRows();
        for (int idx=0; idx<selected.length; idx++) {
            int i = table.convertRowIndexToModel(selected[idx]);
            wList.add(((WrestlerProxy)list.get(i)).getWrestler());
        }
        if (selected.length == fromGroup.getWrestlers().size()) {
            logger.info("Deleting all wrestlers from group [" + fromGroup + "] and moving to existing group.");
            dao.deleteGroup(fromGroup);
        }
        
        toGroup.addWrestlers(wList);
        RandomSeed.execute(toGroup);
        toGroup.setBouts(null);
        BoutMaker.makeBouts(toGroup, dao.isFifthPlaceEnabled(), dao.isSecondPlaceChallengeEnabled(),
                dao.isRoundRobinEnabled(), dao.getRoundRobinMax(), dao.getDummyWrestler());
        
        dao.flush();
        this.view.updateGroupList();
        this.view.refreshStats();
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

        list = ObservableCollections.observableList(new ArrayList<Wrestler>());
        seedComboBox = new javax.swing.JComboBox();
        placeComboBox = new javax.swing.JComboBox();
        moveToPopupMenu = new javax.swing.JPopupMenu();
        moveToFreeListMenuItem = new javax.swing.JMenuItem();
        moveToNewGroupMenuItem = new javax.swing.JMenuItem();
        moveToGroupMenuItem = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        awardButton = new javax.swing.JButton();
        shuffleButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        seedComboBox.setName("seedComboBox"); // NOI18N

        placeComboBox.setName("placeComboBox"); // NOI18N

        moveToPopupMenu.setName("moveToPopupMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getActionMap(GroupWrestlerListPanel.class, this);
        moveToFreeListMenuItem.setAction(actionMap.get("moveToFreeList")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(GroupWrestlerListPanel.class);
        moveToFreeListMenuItem.setText(resourceMap.getString("moveToFreeListMenuItem.text")); // NOI18N
        moveToFreeListMenuItem.setName("moveToFreeListMenuItem"); // NOI18N
        moveToPopupMenu.add(moveToFreeListMenuItem);

        moveToNewGroupMenuItem.setAction(actionMap.get("moveToNewGroup")); // NOI18N
        moveToNewGroupMenuItem.setText(resourceMap.getString("moveToNewGroupMenuItem.text")); // NOI18N
        moveToNewGroupMenuItem.setName("moveToNewGroupMenuItem"); // NOI18N
        moveToPopupMenu.add(moveToNewGroupMenuItem);

        moveToGroupMenuItem.setAction(actionMap.get("moveToExistingGroup")); // NOI18N
        moveToGroupMenuItem.setText(resourceMap.getString("moveToGroupMenuItem.text")); // NOI18N
        moveToGroupMenuItem.setName("moveToGroupMenuItem"); // NOI18N
        moveToPopupMenu.add(moveToGroupMenuItem);

        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        table.setName("table"); // NOI18N

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, table);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${seedSet}"));
        columnBinding.setColumnName("Seed Set");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${seed}"));
        columnBinding.setColumnName("Seed");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${firstName}"));
        columnBinding.setColumnName("First Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${lastName}"));
        columnBinding.setColumnName("Last Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${teamName}"));
        columnBinding.setColumnName("Team Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
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
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${place}"));
        columnBinding.setColumnName("Place");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("table.columnModel.title8")); // NOI18N
        table.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("table.columnModel.title6")); // NOI18N
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(seedComboBox));
        table.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("table.columnModel.title0")); // NOI18N
        table.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("table.columnModel.title1")); // NOI18N
        table.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("table.columnModel.title2")); // NOI18N
        table.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("table.columnModel.title3")); // NOI18N
        table.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("table.columnModel.title4")); // NOI18N
        table.getColumnModel().getColumn(7).setResizable(false);
        table.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("table.columnModel.title5")); // NOI18N
        table.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("table.columnModel.title7")); // NOI18N
        table.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(placeComboBox));
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        awardButton.setAction(actionMap.get("awardReportForGroup")); // NOI18N
        awardButton.setText(resourceMap.getString("awardButton.text")); // NOI18N
        awardButton.setName("awardButton"); // NOI18N

        shuffleButton.setAction(actionMap.get("shuffle")); // NOI18N
        shuffleButton.setText(resourceMap.getString("shuffleButton.text")); // NOI18N
        shuffleButton.setName("shuffleButton"); // NOI18N

        jButton1.setAction(actionMap.get("moveTo")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(awardButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 312, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(shuffleButton)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(awardButton)
                    .addComponent(shuffleButton)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton awardButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private java.util.List<Wrestler> list;
    private javax.swing.JMenuItem moveToFreeListMenuItem;
    private javax.swing.JMenuItem moveToGroupMenuItem;
    private javax.swing.JMenuItem moveToNewGroupMenuItem;
    private javax.swing.JPopupMenu moveToPopupMenu;
    private javax.swing.JComboBox placeComboBox;
    private javax.swing.JComboBox seedComboBox;
    private javax.swing.JButton shuffleButton;
    private javax.swing.JTable table;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private BoutTimeView view;
    
    ////////////////////////////////////////////////////////////////////////////
    // Inner Classes
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * A class to use in the table.  We want to "proxy" changes to the wrestler
     * objects, mainly so we can swap seed values when they change.  We swap seed
     * values to eliminate user errors (like duplicate values) as much as possible.
     * We use this proxy class mainly to override the "setSeed" method so we can
     * capture when the user changes the seed value.  We could have done something
     * with event listeners, but it got kinda messy since we are reusing the seed
     * combobox for all rows.  This seems....simpler.
     */
    private final class WrestlerProxy extends Wrestler {
        private Wrestler wrestler;

        // Constructor
        public WrestlerProxy(Wrestler w) {
            this.wrestler = w;
            this.setGroup(w.getGroup());
            superSetSeed(w.getSeed());
            super.setSeedSet(w.isSeedSet());
            this.setFirstName(w.getFirstName());
            this.setLastName(w.getLastName());
            this.setTeamName(w.getTeamName());
            this.setClassification(w.getClassification());
            this.setAgeDivision(w.getAgeDivision());
            this.setWeightClass(w.getWeightClass());
            super.setPlace(w.getPlace());
        }
        
        @Override
        public void setSeed(Integer seed) {
            
            if (!seed.equals(wrestler.getSeed())) {
                if (isGroupLocked(this.getGroup(), "Change Seed Error")) {
                    logger.info("Attempted to set seed on a locked group");
                    return;
                }
                
                if (this.isSeedSet()) {
                    logger.warn("Seed value is set, cannot change");
                    return;
                }
                
                WrestlerProxy wp = findWrestlerProxy(seed);
                if ((wp != null) && wp.isSeedSet()) {
                    logger.warn("Wrestler with target seed value is set, cannot change");
                    return;
                }
                
                slideSeeds(wp, seed, wrestler.getSeed().intValue() < seed.intValue() );
                update();
            }
        }
        
        private void superSetSeed(Integer seed) {
            super.setSeed(seed);
        }

        private void slideSeeds(WrestlerProxy wp, int targetSeed, boolean slideUp) {
            this.superSetSeed(targetSeed);
            while (wp != null) {
                if (slideUp) {
                    targetSeed -= 1;
                } else {
                    targetSeed += 1;
                }
                WrestlerProxy next = findWrestlerProxy(targetSeed);
                if ((next != null) && next.isSeedSet()) {
                    logger.info("Wrestler with target seed value is set, skipping");
                    continue;
                }
                
                wp.superSetSeed(targetSeed);
                wp = next;
            }
        }
        
        private WrestlerProxy findWrestlerProxy(int seed) {
            for (Wrestler w : list) {
                WrestlerProxy wp = (WrestlerProxy)w;
                if ((wp != this) && (seed == wp.getSeed())) {
                    return wp;
                }
            }
            
            return null;
        }
        
        @Override
        public void setSeedSet(boolean b) {
            super.setSeedSet(b);
            this.wrestler.setSeedSet(b);
        }
        
        @Override
        public void setPlace(Integer i) {
            super.setPlace(i);
            this.wrestler.setPlace(i);
        }
        
        public Wrestler getWrestler() { return this.wrestler; }

        @Override
        public int hashCode() { return super.hashCode(); }

        @Override
        public boolean equals(Object other) {
            if (other instanceof WrestlerProxy) {
                return super.equals(other);    
            }
                  
            return false;
        }
    }
}
