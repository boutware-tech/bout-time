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

package bouttime.gui.panel;

import bouttime.dao.Dao;
import bouttime.mainview.BoutTimeView;
import bouttime.model.Wrestler;
import bouttime.sort.WrestlerClassDivWtAlphaSort;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.application.Action;
import org.jdesktop.observablecollections.ObservableCollections;
import org.apache.log4j.Logger;

/**
 * A class to display the "scratched" wrestlers in a table.
 */
public class ScratchListPanel extends javax.swing.JPanel {
    static Logger logger = Logger.getLogger(ScratchListPanel.class);

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    /**
     * No-arg constructor to make netBeans GUI builder happy.  In order to
     * add this class to the Palette, you are required to have a no-arg
     * constructor.  There is no other need for this, so don't use it.
     * @deprecated
     */
    public ScratchListPanel() {initComponents();}

    /** Creates new form ScratchListPanel */
    public ScratchListPanel(BoutTimeView v) {
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
    }

    ////////////////////////////////////////////////////////////////////////////
    // Class Methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Update the list to display in the table.
     */
    public void updateList() {
        Dao dao = this.view.getDao();
        if (dao.isOpen()) {
            list.clear();
            List<Wrestler> wList = new ArrayList<Wrestler>(dao.getScratchedWrestlers());
            Collections.sort(wList, new WrestlerClassDivWtAlphaSort());
            list.addAll(wList);
            count.setText(Integer.toString(list.size()));
            logger.debug("Updated list, count=" + this.count.getText());
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
     * Remove the wrestlers from the "scratch" list.
     */
    @Action(enabledProperty="tableRecordSelected")
    public void restoreWrestler() {
        int[] selected = table.getSelectedRows();
        List<Wrestler> wList = new ArrayList<Wrestler>();
        for (int idx=0; idx<selected.length; idx++) {
            Wrestler w = list.get(table.convertRowIndexToModel(selected[idx]));
            wList.add(w);
        }

        for (Wrestler w : wList) {
            w.setScratched(false);
            w.setComment(null);
            list.remove(w);
        }

        count.setText(Integer.toString(list.size()));

        logger.debug("Restored " + wList.size() + " wrestlers : " + wList);

        Dao dao = this.view.getDao();
        dao.flush();
        this.view.updateFreeList();
    }

    /**
     * Save any updates that were made.
     */
    @Action(enabledProperty="tableRecordSelected")
    public void saveUpdates() {
        logger.debug("Performing update action");
        Dao dao = this.view.getDao();
        dao.flush();
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
        restoreMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        countLabel = new javax.swing.JLabel();
        count = new javax.swing.JTextField();
        restoreButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        saveButton = new javax.swing.JButton();

        popupMenu.setName("popupMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getActionMap(ScratchListPanel.class, this);
        restoreMenuItem.setAction(actionMap.get("restoreWrestler")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(ScratchListPanel.class);
        restoreMenuItem.setText(resourceMap.getString("restoreMenuItem.text")); // NOI18N
        restoreMenuItem.setName("restoreMenuItem"); // NOI18N
        popupMenu.add(restoreMenuItem);

        saveMenuItem.setAction(actionMap.get("saveUpdates")); // NOI18N
        saveMenuItem.setText(resourceMap.getString("saveMenuItem.text")); // NOI18N
        saveMenuItem.setName("saveMenuItem"); // NOI18N
        popupMenu.add(saveMenuItem);

        setName("Form"); // NOI18N

        countLabel.setText(resourceMap.getString("countLabel.text")); // NOI18N
        countLabel.setName("countLabel"); // NOI18N

        count.setText(resourceMap.getString("count.text")); // NOI18N
        count.setName("count"); // NOI18N

        restoreButton.setAction(actionMap.get("restoreWrestler")); // NOI18N
        restoreButton.setText(resourceMap.getString("restoreButton.text")); // NOI18N
        restoreButton.setName("restoreButton"); // NOI18N

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
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${comment}"));
        columnBinding.setColumnName("Comment");
        columnBinding.setColumnClass(String.class);
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
        table.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("table.columnModel.title6")); // NOI18N

        saveButton.setAction(actionMap.get("saveUpdates")); // NOI18N
        saveButton.setText(resourceMap.getString("saveButton.text")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(countLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(count, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 336, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addGap(18, 18, 18)
                .addComponent(restoreButton)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(countLabel)
                    .addComponent(count, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(restoreButton)
                    .addComponent(saveButton))
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
    private javax.swing.JTextField count;
    private javax.swing.JLabel countLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private java.util.List<Wrestler> list;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JButton restoreButton;
    private javax.swing.JMenuItem restoreMenuItem;
    private javax.swing.JButton saveButton;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JTable table;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private BoutTimeView view;
}
