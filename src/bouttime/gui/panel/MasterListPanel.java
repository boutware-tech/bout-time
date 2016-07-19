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
import bouttime.mainview.BoutTimeView;
import bouttime.model.Wrestler;
import bouttime.sort.WrestlerClassDivWtAlphaSort;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * A class to implement a JPanel for the master list.
 */
public class MasterListPanel extends javax.swing.JPanel implements ItemListener, ActionListener {
    static Logger logger = Logger.getLogger(MasterListPanel.class);

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    /**
     * No-arg constructor to make netBeans GUI builder happy.  In order to
     * add this class to the Palette, you are required to have a no-arg
     * constructor.  There is no other need for this, so don't use it.
     * @deprecated
     */
    public MasterListPanel() {this(null);}

    /** Creates new form MasterListPanel */
    public MasterListPanel(BoutTimeView v) {
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

        textFilterComboBox.addActionListener(this);
        textFilter.addActionListener(this);
        setFilterLists();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Class Methods
    ////////////////////////////////////////////////////////////////////////////

    public BoutTimeView getView() { return this.view; }

    /**
     * Called when comboBox selections change
     */
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            updateList();
        }
    }

    /**
     * Called when an item is selected in a comboBox (doesn't need to change value)
     */
    public void actionPerformed(ActionEvent e) {
        updateList();
    }

    /**
     * Update the list for the table.
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
        List<Wrestler> wList = new ArrayList<Wrestler>(dao.getAllWrestlers());
        Collections.sort(wList, new WrestlerClassDivWtAlphaSort());
        this.list.addAll(wList);

        String div = divComboBox.getSelectedItem().toString();
        boolean divDoFilter = !div.equalsIgnoreCase(FILTER_CLEAR_KEYWORD);
        String cla = classComboBox.getSelectedItem().toString();
        boolean claDoFilter = !cla.equalsIgnoreCase(FILTER_CLEAR_KEYWORD);
        String text = textFilter.getText();
        boolean doTextFilter = !((text == null) || (text.isEmpty()));

        List<Wrestler> removeList = new ArrayList<Wrestler>();

        for (Wrestler w : this.list) {
            if ((divDoFilter && !w.getAgeDivision().equalsIgnoreCase(div)) ||
                    (claDoFilter && !w.getClassification().equalsIgnoreCase(cla))) {
                        removeList.add(w);
                        continue;
            }

            if (doTextFilter) {
                String field = (String)textFilterComboBox.getSelectedItem();
                if (field.equalsIgnoreCase("First")) {
                    if (!text.equalsIgnoreCase(w.getFirstName())) {
                        removeList.add(w);
                    }
                } else if (field.equalsIgnoreCase("Last")) {
                    if (!text.equalsIgnoreCase(w.getLastName())) {
                        removeList.add(w);
                    }
                } else if (field.equalsIgnoreCase("Team")) {
                    if (!text.equalsIgnoreCase(w.getTeamName())) {
                        removeList.add(w);
                    }
                } else {
                    logger.warn("unexpected filter field : " + field);
                }
            }
        }

        this.list.removeAll(removeList);

        count.setText(Integer.toString(list.size()));

        logger.debug("Updated list, count=" + this.count.getText());
    }

    /**
     * Fire a Dao property change notification.
     */
    public void daoPropertyChange() {
        setFilterLists();
        firePropertyChange("daoOpen", !isDaoOpen(), isDaoOpen());
    }

    /**
     * @return True if the Dao is open.
     */
    public boolean isDaoOpen() {
        if (this.view == null) {
            return false;
        }
        
        return this.view.isDaoOpen();
    }

    /**
     * Used to enabled/disable buttons on the JPanel.
     * @return True if a row in the table is selected.
     */
    public boolean isTableRecordSelected() {
        return table.getSelectedRow() != -1;
    }

    /**
     * Add a new wrestler.
     * Show the WrestlerDialogForm to enter the data.
     */
    @Action(enabledProperty="daoOpen")
    public void addNewWrestler() {
        Dao dao = this.view.getDao();
        if ((dao == null) || !dao.isOpen()) {
            logger.warn("Unable to add new wrestler : DAO is null or not open");
            return;
        }
        
        // Show dialog form to enter new wrestler
        JFrame mainFrame = bouttime.mainview.BoutTimeApp.getApplication().getMainFrame();
        WrestlerDialogForm form = new WrestlerDialogForm(mainFrame, true, dao);

        if (form.isCancelled()) {
            logger.debug("Add new wrestler cancelled");
            return;
        }

        updateList();

        this.view.updateFreeList();
        this.view.refreshStats();
        count.setText(Integer.toString(list.size()));

        logger.debug("Added a new wrestler, count=" + this.count.getText());
    }

    /**
     * Delete one or more selected wrestlers.
     */
    @Action(enabledProperty="tableRecordSelected")
    public void deleteWrestler() {
        List<Wrestler> wList = new ArrayList<Wrestler>();
        int[] selected = table.getSelectedRows();
        for (int idx=0; idx<selected.length; idx++) {
            Wrestler w = list.get(table.convertRowIndexToModel(selected[idx]));

            // Do not allow the delete if the selection is in a group
            if (w.getGroup() != null) {
                JFrame mainFrame = bouttime.mainview.BoutTimeApp.getApplication().getMainFrame();
                JOptionPane.showMessageDialog(mainFrame,
                String.format("%s %s is in group %s\n\nThis entry cannot be deleted until "
                        + "it is removed from the group.", w.getFirstName(), w.getLastName(),
                        w.getGroup()),
                        "Cannot Delete - In a group", JOptionPane.ERROR_MESSAGE);
                
                continue;
            }

            wList.add(w);
        }

        list.removeAll(wList);
        count.setText(Integer.toString(this.list.size()));

        logger.debug("Deleting " + wList.size() + " wrestlers : " + wList +
                "\n    count=" + this.count.getText());

        // Remove the wrestler from the data store
        Dao dao = this.view.getDao();
        dao.deleteWrestlers(wList);
        dao.flush();

        // These two calls aren't ALWAYS necessary, so could be optimized.
        this.view.updateFreeList();
        this.view.updateScratchList();
        this.view.refreshStats();
    }

    /**
     * Edit a wrestler.  Show the WrestlerDialogForm to do this.
     */
    @Action(enabledProperty="tableRecordSelected")
    public void editWrestler() {
        Dao dao = this.view.getDao();
        if ((dao == null) || !dao.isOpen()) {
            logger.warn("Unable to edit wrestler : DAO is null or not open");
            return;
        }

        // Could have > 1 row selected.  Can only edit one at a time, so just
        // get the first one.
        int selected = table.getSelectedRow();
        Wrestler w = list.get(table.convertRowIndexToModel(selected));

        // Show dialog form to enter new wrestler
        JFrame mainFrame = bouttime.mainview.BoutTimeApp.getApplication().getMainFrame();
        WrestlerDialogForm form = new WrestlerDialogForm(mainFrame, true, dao, w);

        if (form.isCancelled()) {
            logger.debug("Edit wrestler is cancelled");
            return;
        }

        logger.debug("Edited wrestler [" + w + "]");

        // TODO - if we edit a wrestler that is in a group, do we want to remove
        // the wrestler from the group?  If the user is editing the name or team,
        // then we don't want to remove.  However, if they are editing the class,
        // division, or weight class, then maybe we do want to remove?

        updateList();
        this.view.updateFreeList();
        this.view.updateScratchList();
        this.view.refreshStats();
    }

    /**
     * Scratch a wrestler.
     */
    @Action(enabledProperty="tableRecordSelected")
    public void scratchWrestler() {
        JFrame mainFrame = bouttime.mainview.BoutTimeApp.getApplication().getMainFrame();
        String comment = (String)JOptionPane.showInputDialog(mainFrame,
                "Enter a comment or reason for scratching the wrestler(s).",
                "Enter a comment", JOptionPane.PLAIN_MESSAGE);

        if (comment == null) {
            // "Cancel" button was clicked
            logger.debug("Scratch a wrestler cancelled");
            return;
        }
        
        int[] selected = table.getSelectedRows();
        for (int idx=0; idx<selected.length; idx++) {
            Wrestler w = list.get(table.convertRowIndexToModel(selected[idx]));
            if (w.getGroup() != null) {
                // Show an information message that the user is scratching a
                // wrestler that is already in a group.
                JOptionPane.showMessageDialog(mainFrame,
                String.format("%s %s is in group %s", w.getFirstName(), w.getLastName(), w.getGroup()),
                "Scratch in group", JOptionPane.INFORMATION_MESSAGE);
            }
            w.setScratched(true);
            w.setComment(comment);
            logger.debug("Scratched wrestler : [" + w + "]");
        }

        Dao dao = this.view.getDao();
        dao.flush();

        this.view.updateFreeList();
        this.view.updateScratchList();
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
        addNewMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        scratchMenuItem = new javax.swing.JMenuItem();
        addNewButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        countLabel = new javax.swing.JLabel();
        count = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        editButton = new javax.swing.JButton();
        scratchButton = new javax.swing.JButton();
        classLabel = new javax.swing.JLabel();
        classComboBox = new javax.swing.JComboBox();
        divLabel = new javax.swing.JLabel();
        divComboBox = new javax.swing.JComboBox();
        textFilter = new javax.swing.JTextField();
        textFilterComboBox = new javax.swing.JComboBox();

        popupMenu.setName("popupMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getActionMap(MasterListPanel.class, this);
        addNewMenuItem.setAction(actionMap.get("addNewWrestler")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(MasterListPanel.class);
        addNewMenuItem.setText(resourceMap.getString("addNewMenuItem.text")); // NOI18N
        addNewMenuItem.setName("addNewMenuItem"); // NOI18N
        popupMenu.add(addNewMenuItem);

        editMenuItem.setAction(actionMap.get("editWrestler")); // NOI18N
        editMenuItem.setText(resourceMap.getString("editMenuItem.text")); // NOI18N
        editMenuItem.setName("editMenuItem"); // NOI18N
        popupMenu.add(editMenuItem);

        deleteMenuItem.setAction(actionMap.get("deleteWrestler")); // NOI18N
        deleteMenuItem.setText(resourceMap.getString("deleteMenuItem.text")); // NOI18N
        deleteMenuItem.setName("deleteMenuItem"); // NOI18N
        popupMenu.add(deleteMenuItem);

        scratchMenuItem.setAction(actionMap.get("scratchWrestler")); // NOI18N
        scratchMenuItem.setText(resourceMap.getString("scratchMenuItem.text")); // NOI18N
        scratchMenuItem.setName("scratchMenuItem"); // NOI18N
        popupMenu.add(scratchMenuItem);

        setName("Form"); // NOI18N

        addNewButton.setAction(actionMap.get("addNewWrestler")); // NOI18N
        addNewButton.setText(resourceMap.getString("addNewButton.text")); // NOI18N
        addNewButton.setName("addNewButton"); // NOI18N

        deleteButton.setAction(actionMap.get("deleteWrestler")); // NOI18N
        deleteButton.setText(resourceMap.getString("deleteButton.text")); // NOI18N
        deleteButton.setName("deleteButton"); // NOI18N

        countLabel.setText(resourceMap.getString("countLabel.text")); // NOI18N
        countLabel.setName("countLabel"); // NOI18N

        count.setEditable(false);
        count.setName("count"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        table.setBackground(resourceMap.getColor("table.background")); // NOI18N
        table.setName("table"); // NOI18N

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, table);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${firstName}"));
        columnBinding.setColumnName("First");
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
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${geo}"));
        columnBinding.setColumnName("Geo");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${level}"));
        columnBinding.setColumnName("Level");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${serialNumber}"));
        columnBinding.setColumnName("Serial Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${group}"));
        columnBinding.setColumnName("Group");
        columnBinding.setColumnClass(bouttime.model.Group.class);
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
        table.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("table.columnModel.title1")); // NOI18N
        table.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("table.columnModel.title2")); // NOI18N
        table.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("table.columnModel.title3")); // NOI18N
        table.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("table.columnModel.title4")); // NOI18N
        table.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("table.columnModel.title5")); // NOI18N
        table.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("table.columnModel.title6")); // NOI18N
        table.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("table.columnModel.title9")); // NOI18N
        table.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("table.columnModel.title7")); // NOI18N
        table.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("table.columnModel.title8")); // NOI18N
        table.getColumnModel().getColumn(10).setHeaderValue(resourceMap.getString("table.columnModel.title10")); // NOI18N

        editButton.setAction(actionMap.get("editWrestler")); // NOI18N
        editButton.setText(resourceMap.getString("editButton.text")); // NOI18N
        editButton.setName("editButton"); // NOI18N

        scratchButton.setAction(actionMap.get("scratchWrestler")); // NOI18N
        scratchButton.setText(resourceMap.getString("scratchButton.text")); // NOI18N
        scratchButton.setName("scratchButton"); // NOI18N

        classLabel.setText(resourceMap.getString("classLabel.text")); // NOI18N
        classLabel.setName("classLabel"); // NOI18N

        classComboBox.setName("classComboBox"); // NOI18N

        divLabel.setText(resourceMap.getString("divLabel.text")); // NOI18N
        divLabel.setName("divLabel"); // NOI18N

        divComboBox.setName("divComboBox"); // NOI18N

        textFilter.setText(resourceMap.getString("textFilter.text")); // NOI18N
        textFilter.setName("textFilter"); // NOI18N

        textFilterComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "First", "Last", "Team" }));
        textFilterComboBox.setName("textFilterComboBox"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(countLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(count, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(classLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(divLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(divComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textFilterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addComponent(scratchButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addNewButton)
                        .addContainerGap())
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addNewButton)
                        .addComponent(editButton)
                        .addComponent(deleteButton)
                        .addComponent(scratchButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(countLabel)
                        .addComponent(count, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(classLabel)
                        .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(divLabel)
                        .addComponent(divComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFilterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
    private javax.swing.JButton addNewButton;
    private javax.swing.JMenuItem addNewMenuItem;
    private javax.swing.JComboBox classComboBox;
    private javax.swing.JLabel classLabel;
    private javax.swing.JTextField count;
    private javax.swing.JLabel countLabel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JComboBox divComboBox;
    private javax.swing.JLabel divLabel;
    private javax.swing.JButton editButton;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.JScrollPane jScrollPane1;
    private java.util.List<Wrestler> list;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JButton scratchButton;
    private javax.swing.JMenuItem scratchMenuItem;
    private javax.swing.JTable table;
    private javax.swing.JTextField textFilter;
    private javax.swing.JComboBox textFilterComboBox;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private BoutTimeView view;
    private final String FILTER_CLEAR_KEYWORD = "All";
}
