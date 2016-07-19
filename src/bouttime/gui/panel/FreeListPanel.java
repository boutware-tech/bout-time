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
import bouttime.model.Group;
import bouttime.model.Wrestler;
import bouttime.boutmaker.BoutMaker;
import bouttime.sort.GroupClassDivWtSort;
import bouttime.sort.WrestlerClassDivWtAlphaSort;
import bouttime.utility.seed.RandomSeed;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.application.Action;
import org.jdesktop.observablecollections.ObservableCollections;
import org.apache.log4j.Logger;

/**
 * A class to implement a JPanel for the free list, or the list of ungrouped wrestlers
 */
public class FreeListPanel extends javax.swing.JPanel implements ItemListener {
    static Logger logger = Logger.getLogger(FreeListPanel.class);

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    /**
     * No-arg constructor to make netBeans GUI builder happy.  In order to
     * add this class to the Palette, you are required to have a no-arg
     * constructor.  There is no other need for this, so don't use it.
     * @deprecated
     */
    public FreeListPanel() {this(null);}

    /**
     * Creates new form FreeListPanel
     */
    public FreeListPanel(BoutTimeView v) {
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
                }
            });

        setFilterLists();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Class Methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Called when a Dao property changes.
     * Refresh the GUI.
     */
    public void daoPropertyChange() {
        setFilterLists();
        updateList();
    }

    /**
     * Update the list that is displayed in the table.
     */
    public void updateList() {
        list.clear();

        if (this.view == null) {
            logger.warn("Unable to update list : this.view is null");
            return;
        }

        Dao dao = this.view.getDao();
        if (!dao.isOpen()) {
            logger.warn("Unable to update list : DAO is not open");
            return;  // do nothing
        }

        // Initialze the list
        this.list.clear();
        List<Wrestler> wList = new ArrayList<Wrestler>(dao.getUngroupedWrestlers());
        Collections.sort(wList, new WrestlerClassDivWtAlphaSort());
        this.list.addAll(wList);
        
        String div = divComboBox.getSelectedItem().toString();
        boolean divDoFilter = !div.equalsIgnoreCase(FILTER_CLEAR_KEYWORD);
        String cla = classComboBox.getSelectedItem().toString();
        boolean claDoFilter = !cla.equalsIgnoreCase(FILTER_CLEAR_KEYWORD);

        List<Wrestler> removeList = new ArrayList<Wrestler>();

        for (Wrestler w : this.list) {
            // Remove wrestlers that are :
            //   1. filtered by age division
            //   2. filtered by classification
            //   3. scratched
            if ((divDoFilter && !w.getAgeDivision().equalsIgnoreCase(div)) ||
                    (claDoFilter && !w.getClassification().equalsIgnoreCase(cla)) ||
                    w.isScratched()) {
                        removeList.add(w);
            }
        }

        this.list.removeAll(removeList);

        count.setText(Integer.toString(list.size()));

        logger.debug("Updated list, count=" + this.count.getText());
    }

    /**
     * Used to enabled/disable buttons on the JPanel.
     * @return True if a row in the table is selected.
     */
    public boolean isTableRecordSelected() {
        return table.getSelectedRow() != -1;
    }

    /**
     * Create a new group of wrestlers based on the selections in the table.
     */
    @Action(enabledProperty="tableRecordSelected")
    public void createNewGroup() {
        List<Wrestler> wList = new ArrayList<Wrestler>();
        int[] selected = table.getSelectedRows();
        for (int idx=0; idx<selected.length; idx++) {
            Wrestler w = list.get(table.convertRowIndexToModel(selected[idx]));
            wList.add(w);
        }

        Group g = new Group(wList);
        RandomSeed.execute(g);

        Dao dao = this.view.getDao();
        dao.addGroup(g);
        BoutMaker.makeBouts(g, dao.isFifthPlaceEnabled(), dao.isSecondPlaceChallengeEnabled(),
                dao.isRoundRobinEnabled(), dao.getRoundRobinMax(), dao.getDummyWrestler());
        dao.flush();

        list.removeAll(wList);
        count.setText(Integer.toString(list.size()));
        updateSelectedCounter();

        logger.debug("Created new group [" + g + "] with " + wList +
                "\n    count=" + this.count.getText());

        this.view.updateGroupList();
        this.view.refreshStats();
    }

    /**
     * Add the selected wrestlers to an existing group.
     */
    @Action(enabledProperty="tableRecordSelected")
    public void addToGroup() {
        // Show dialog form of existing groups
        Dao dao = this.view.getDao();
        List<Group> gList = dao.getAllGroups();
        Collections.sort(gList, new GroupClassDivWtSort());
        Object [] grps = gList.toArray();

        JFrame mainFrame = BoutTimeApp.getApplication().getMainFrame();

        Group g = (Group)JOptionPane.showInputDialog(mainFrame, null,
                "Add wrestler to group", JOptionPane.PLAIN_MESSAGE, null,
                grps, grps[0]);

        if (g == null) {
            logger.debug("Unable to add wrestlers to group : no group selected");
            return;
        }

        if (g.isLocked()) {
            JOptionPane.showMessageDialog(this.view.getFrame(), "This group is locked." +
                    "\nYou must unlock the group before attempting this operation.",
                    "Add to Group Error", JOptionPane.ERROR_MESSAGE);
            logger.warn("Unable to add wrestler(s) to group [" + g + "] : group is locked");
            return;
        }

        List<Wrestler> wList = new ArrayList<Wrestler>();
        int[] selected = table.getSelectedRows();
        for (int idx=0; idx<selected.length; idx++) {
            Wrestler w = list.get(table.convertRowIndexToModel(selected[idx]));
            wList.add(w);
        }

        g.addWrestlers(wList);
        RandomSeed.execute(g);
        g.setBouts(null);
        BoutMaker.makeBouts(g, dao.isFifthPlaceEnabled(), dao.isSecondPlaceChallengeEnabled(),
                dao.isRoundRobinEnabled(), dao.getRoundRobinMax(), dao.getDummyWrestler());

        dao.flush();

        list.removeAll(wList);
        count.setText(Integer.toString(list.size()));
        updateSelectedCounter();

        logger.debug("Added wrestlers to group [" + g + "] : " + wList +
                "\n    count=" + this.count.getText());

        this.view.updateGroupList();
        this.view.refreshStats();
    }
    
    /**
     * Called when comboBox selections change
     */
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            updateList();
        }

        updateSelectedCounter();
    }

    /**
     * Initialize the values in the filter combo boxes.
     */
    public void setFilterLists() {
        if (this.view == null) {
            logger.warn("Unable to set filter lists : this.view is null");
            return;
        }

        Dao dao = this.view.getDao();
        if (!dao.isOpen()) {
            logger.warn("Unable to set filter lists : DAO is not open");
            return;
        }

        initComboBox(this.divComboBox, dao.getAgeDivisionValues());
        initComboBox(this.classComboBox, dao.getClassificationValues());

    }

    private void updateSelectedCounter() {
        //selectedCounterLabel.setEnabled(isTableRecordSelected());
        selectedCounterLabel.setText(Integer.toString(table.getSelectedRowCount()));
    }

    /**
     * Initialize the values for a given combo box.
     * @param comboBox The combo box to initialize.
     * @param str Tokenized list of values (delimiter is ",").
     * @param addClearKeyword True if the filter "clear" keyword should be added.
     */
    private void initComboBox(JComboBox comboBox, String str) {
        comboBox.removeItemListener(this);
        comboBox.removeAllItems();

        comboBox.addItem(FILTER_CLEAR_KEYWORD);

        if ((str == null) || str.isEmpty()) {
            logger.info("Unable to initialize combo box " + comboBox.getName() +
                    " : str is >" + str + "<");
            return;
        }

        String [] tokens = str.split(",");
        for (String s : tokens) {
            comboBox.addItem(s.trim());
        }

        comboBox.addItemListener(this);

        logger.debug("Initialized combo box [" + comboBox.getName() + "] with " +
                comboBox.getItemCount() + " items");
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

        list = ObservableCollections.observableList(new ArrayList<Wrestler>());
        popupMenu = new javax.swing.JPopupMenu();
        createGroupMenuItem = new javax.swing.JMenuItem();
        addToGroupMenuItem = new javax.swing.JMenuItem();
        createGroupButton = new javax.swing.JButton();
        addToGroupButton = new javax.swing.JButton();
        countLabel = new javax.swing.JLabel();
        count = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        classLabel = new javax.swing.JLabel();
        classComboBox = new javax.swing.JComboBox();
        divLabel = new javax.swing.JLabel();
        divComboBox = new javax.swing.JComboBox();
        selectedCounterLabel = new javax.swing.JLabel();
        selectedTextLabel = new javax.swing.JLabel();

        popupMenu.setName("popupMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getActionMap(FreeListPanel.class, this);
        createGroupMenuItem.setAction(actionMap.get("createNewGroup")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(FreeListPanel.class);
        createGroupMenuItem.setText(resourceMap.getString("createGroupMenuItem.text")); // NOI18N
        createGroupMenuItem.setName("createGroupMenuItem"); // NOI18N
        popupMenu.add(createGroupMenuItem);

        addToGroupMenuItem.setAction(actionMap.get("addToGroup")); // NOI18N
        addToGroupMenuItem.setText(resourceMap.getString("addToGroupMenuItem.text")); // NOI18N
        addToGroupMenuItem.setName("addToGroupMenuItem"); // NOI18N
        popupMenu.add(addToGroupMenuItem);

        setName("Form"); // NOI18N

        createGroupButton.setAction(actionMap.get("createNewGroup")); // NOI18N
        createGroupButton.setText(resourceMap.getString("createGroupButton.text")); // NOI18N
        createGroupButton.setName("createGroupButton"); // NOI18N

        addToGroupButton.setAction(actionMap.get("addToGroup")); // NOI18N
        addToGroupButton.setText(resourceMap.getString("addToGroupButton.text")); // NOI18N
        addToGroupButton.setName("addToGroupButton"); // NOI18N

        countLabel.setText(resourceMap.getString("countLabel.text")); // NOI18N
        countLabel.setName("countLabel"); // NOI18N

        count.setText(resourceMap.getString("count.text")); // NOI18N
        count.setName("count"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        table.setBackground(resourceMap.getColor("table.background")); // NOI18N
        table.setName("table"); // NOI18N

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, table);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${firstName}"));
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
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${actualWeight}"));
        columnBinding.setColumnName("Actual Weight");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${level}"));
        columnBinding.setColumnName("Level");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${geo}"));
        columnBinding.setColumnName("Geo");
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
        table.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("table.columnModel.title0")); // NOI18N
        table.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("table.columnModel.title1")); // NOI18N
        table.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("table.columnModel.title2")); // NOI18N
        table.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("table.columnModel.title3")); // NOI18N
        table.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("table.columnModel.title4")); // NOI18N
        table.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("table.columnModel.title5")); // NOI18N
        table.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("table.columnModel.title7")); // NOI18N
        table.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("table.columnModel.title6")); // NOI18N
        table.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("table.columnModel.title8")); // NOI18N

        classLabel.setText(resourceMap.getString("classLabel.text")); // NOI18N
        classLabel.setName("classLabel"); // NOI18N

        classComboBox.setName("classComboBox"); // NOI18N

        divLabel.setText(resourceMap.getString("divLabel.text")); // NOI18N
        divLabel.setName("divLabel"); // NOI18N

        divComboBox.setName("divComboBox"); // NOI18N

        selectedCounterLabel.setText(resourceMap.getString("selectedCounterLabel.text")); // NOI18N
        selectedCounterLabel.setName("selectedCounterLabel"); // NOI18N

        selectedTextLabel.setText(resourceMap.getString("selectedTextLabel.text")); // NOI18N
        selectedTextLabel.setName("selectedTextLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(countLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(count, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(classLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(divLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(divComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(selectedTextLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectedCounterLabel)
                .addGap(18, 18, 18)
                .addComponent(addToGroupButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(createGroupButton)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(createGroupButton)
                        .addComponent(addToGroupButton)
                        .addComponent(selectedCounterLabel)
                        .addComponent(selectedTextLabel))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(countLabel)
                        .addComponent(count, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(classLabel)
                        .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(divLabel)
                        .addComponent(divComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addToGroupButton;
    private javax.swing.JMenuItem addToGroupMenuItem;
    private javax.swing.JComboBox classComboBox;
    private javax.swing.JLabel classLabel;
    private javax.swing.JTextField count;
    private javax.swing.JLabel countLabel;
    private javax.swing.JButton createGroupButton;
    private javax.swing.JMenuItem createGroupMenuItem;
    private javax.swing.JComboBox divComboBox;
    private javax.swing.JLabel divLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private java.util.List<Wrestler> list;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JLabel selectedCounterLabel;
    private javax.swing.JLabel selectedTextLabel;
    private javax.swing.JTable table;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private BoutTimeView view;
    private final String FILTER_CLEAR_KEYWORD = "All";
}
