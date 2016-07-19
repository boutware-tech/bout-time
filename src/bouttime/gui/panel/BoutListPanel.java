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
import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import bouttime.report.boutsheet.BoutSheetReport;
import bouttime.sort.BoutSort;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.observablecollections.ObservableCollections;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;

/**
 * A class to show a list of bouts in a table in a  JPanel.
 */
public class BoutListPanel extends javax.swing.JPanel {
    static Logger logger = Logger.getLogger(BoutListPanel.class);

    /**
     * No-arg constructor to make netBeans GUI builder happy.  In order to
     * add this class to the Palette, you are required to have a no-arg
     * constructor.  There is no other need for this, so don't use it.
     * @deprecated
     */
    public BoutListPanel() {}

    /** Creates new form BoutListPanel */
    public BoutListPanel(BoutTimeView v) {
        this.view = v;
        initComponents();
        
        table.getColumnModel().getColumn(5).getCellEditor().addCellEditorListener(new CellEditorListener() {
            // This will process the event when the "Winner" column has been edited
            public void editingStopped(ChangeEvent ce) {
                DefaultCellEditor dce = (DefaultCellEditor) ce.getSource();
                Wrestler w = (Wrestler) dce.getCellEditorValue();
                boolean needViewUpdateGroup = false;
                if (w != null) {
                    int i = table.convertRowIndexToModel(table.getSelectedRow());
                    Bout b = list.get(i);
                    int maxAward = view.getDao().getMaxAward();
                    if (Bout.FIRST_PLACE.equalsIgnoreCase(b.getLabel())) {
                        w.setPlace(1);
                        needViewUpdateGroup = true;
                        if ((maxAward >= 2) && b.getLoser() != null) {
                            b.getLoser().setPlace(2);
                        } else {
                            logger.info("No loser for 2nd place bout so cannot set place");
                        }
                    } else if ((maxAward >= 3) && Bout.THIRD_PLACE.equalsIgnoreCase(b.getLabel())) {
                        w.setPlace(3);
                        needViewUpdateGroup = true;
                        if ((maxAward >= 4) && (b.getLoser() != null)) {
                            b.getLoser().setPlace(4);
                        } else {
                            logger.info("No loser for 3rd place bout so cannot set place");
                        }
                    } else if (Bout.SECOND_PLACE.equalsIgnoreCase(b.getLabel())) {
                        w.setPlace(2);
                        needViewUpdateGroup = true;
                        if ((maxAward >= 3) && (b.getLoser() != null)) {
                            b.getLoser().setPlace(3);
                        } else {
                            logger.info("No loser for 2nd place bout so cannot set place");
                        }
                    } else if (Bout.FIFTH_PLACE.equalsIgnoreCase(b.getLabel())) {
                        w.setPlace(5);
                        needViewUpdateGroup = true;
                        if ((maxAward >= 6) && (b.getLoser() != null)) {
                            b.getLoser().setPlace(6);
                        } else {
                            logger.info("No loser for 5th place bout so cannot set place");
                        }
                    }
                }
                updateAction();
                if (needViewUpdateGroup) {
                    view.updateGroup(w.getGroup());
                }
            }

            public void editingCanceled(ChangeEvent ce) {
                // Nothing to do
            }
            
        });

        // tracking table selection
        table.getSelectionModel().addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) {
                        return;
                    }

                    firePropertyChange("tableRecordSelected", !isTableRecordSelected(), isTableRecordSelected());
                    
                    initWinnerComboBox();
                }
            });
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
     * Update the GUI table with the list of bouts from the given group.
     * @param g The group to get the list of bouts from.
     */
    public void updateList(Group g) {
        list.clear();

        if (g == null) {
            return;
        }

        if (g.getBouts() == null) {
            logger.error("No bouts in group [" + g + "]");
            return;
        }

        List<Bout> bList = new ArrayList<Bout>(g.getBouts());
        Collections.sort(bList, new BoutSort());
        list.addAll(bList);
    }

    /**
     * Initialize the values for the winner combo box.
     */
    private void initWinnerComboBox() {
        // Remove the action listeners.  We do this because the 'Winner' column
        // in the table is bound to the winnerComboBox.  And when we call
        // removeAllItems(), it causes Bout.setWinner() to be called.
        // Since there are no items in the comboBox's list (we just removed them
        // all), the parameter to setWinner() is 'null'.  This makes a mess.
        ActionListener [] al = this.winnerComboBox.getActionListeners();
        for (ActionListener a : al) {
            this.winnerComboBox.removeActionListener(a);
        }
        
        this.winnerComboBox.removeAllItems();

        // Get the table's current selection
        int selected = table.getSelectedRow();
        if (selected == -1) {
            logger.debug("Cannot set winner combo box, no bout selected");
            return;
        }


        Bout selectedBout  = list.get(table.convertRowIndexToModel(selected));
        Wrestler red = selectedBout.getRed();
        Wrestler green = selectedBout.getGreen();
        this.winnerComboBox.addItem(red);
        this.winnerComboBox.addItem(green);
        this.winnerComboBox.addItem(null);

        Wrestler winner = selectedBout.getWinner();
        if ((winner == red) || (winner == green)) {
            this.winnerComboBox.setSelectedItem(selectedBout.getWinner());
        } else {
            this.winnerComboBox.setSelectedIndex(2);
        }

        //Now that we've reset the comboBox's items, we can re-add the
        // action listeners.
        for (ActionListener a : al) {
            this.winnerComboBox.addActionListener(a);
        }
        
        logger.debug("Initialized winner combo box : red=[" + red +
                "], green=[" + green + "]");
    }

    @Action
    public void updateAction() {
        if ((this.list == null) || this.list.isEmpty()) {
            return;
        }
        
        Group g = this.list.get(0).getGroup();
        updateList(g);
    }

    @Action(enabledProperty="tableRecordSelected")
    public void printBoutSheet() {
        Dao dao = this.view.getDao();
        if (!dao.isOpen()) {
            logger.warn("Unable to generate bout sheet report : DAO is not open.");
            return;  // do nothing
        }

        List<Bout> bList = new ArrayList<Bout>();
        int[] selected = table.getSelectedRows();

        for (int idx : selected) {
            Bout b = list.get(table.convertRowIndexToModel(idx));
            bList.add(b);
        }

        BoutSheetReport report = new BoutSheetReport();
        if (report.generateReport(dao, bList)) {
            this.view.showPDF(report.getoutputFilePath());
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

        list = ObservableCollections.observableList(new ArrayList<Bout>());
        winnerComboBox = new javax.swing.JComboBox();
        resultTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        boutsheetButton = new javax.swing.JButton();

        winnerComboBox.setName("winnerComboBox"); // NOI18N
        winnerComboBox.setRenderer(new MyWrestlerRenderer());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(BoutListPanel.class);
        resultTextField.setText(resourceMap.getString("resultTextField.text")); // NOI18N
        resultTextField.setName("resultTextField"); // NOI18N

        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        table.setColumnSelectionAllowed(true);
        table.setName("table"); // NOI18N

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, table);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${red.firstInitialLastName}"));
        columnBinding.setColumnName("Red.first Initial Last Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${green.firstInitialLastName}"));
        columnBinding.setColumnName("Green.first Initial Last Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${round}"));
        columnBinding.setColumnName("Round");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${label}"));
        columnBinding.setColumnName("Label");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${boutNum}"));
        columnBinding.setColumnName("Bout Num");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${winner}"));
        columnBinding.setColumnName("Winner");
        columnBinding.setColumnClass(bouttime.model.Wrestler.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${finalResult}"));
        columnBinding.setColumnName("Final Result");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("table.columnModel.title0")); // NOI18N
        table.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("table.columnModel.title1")); // NOI18N
        table.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("table.columnModel.title2")); // NOI18N
        table.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("table.columnModel.title3")); // NOI18N
        table.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("table.columnModel.title4")); // NOI18N
        table.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("table.columnModel.title5")); // NOI18N
        table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(winnerComboBox));
        table.getColumnModel().getColumn(5).setCellRenderer(new MyWrestlerRenderer());
        table.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("table.columnModel.title6")); // NOI18N
        table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(resultTextField));
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getActionMap(BoutListPanel.class, this);
        boutsheetButton.setAction(actionMap.get("printBoutSheet")); // NOI18N
        boutsheetButton.setText(resourceMap.getString("boutsheetButton.text")); // NOI18N
        boutsheetButton.setName("boutsheetButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(492, Short.MAX_VALUE)
                .addComponent(boutsheetButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(boutsheetButton)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boutsheetButton;
    private javax.swing.JScrollPane jScrollPane1;
    protected java.util.List<Bout> list;
    private javax.swing.JTextField resultTextField;
    private javax.swing.JTable table;
    private javax.swing.JComboBox winnerComboBox;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    protected BoutTimeView view;

    ////////////////////////////////////////////////////////////////////////////
    // INNER CLASS
    ////////////////////////////////////////////////////////////////////////////

    class MyWrestlerRenderer extends DefaultTableCellRenderer implements ListCellRenderer {

        /**
         * Used to render the 'winner' column when the column is not in 'edit' mode.
         * @param table
         * @param value
         * @param isSelected
         * @param hasFocus
         * @param row
         * @param col
         * @return
         */
        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus,
                int row, int col) {
            
            JLabel label = new JLabel("", SwingConstants.LEFT);

            if (isSelected) {
                label.setBackground(table.getSelectionBackground());
                label.setOpaque(true);
                label.setForeground(table.getSelectionForeground());
            }
            if (hasFocus) {
                label.setForeground(table.getSelectionBackground());
                label.setBackground(table.getSelectionForeground());
                label.setOpaque(true);
            }

            Bout selectedBout  = list.get(table.convertRowIndexToModel(row));

            Wrestler w = selectedBout.getWinner();
            if (w == null) {
                logger.debug("Cannot render winner, winner is null");
                return label;
            }
            
            label.setText(w.getFirstInitialLastName());

            return label;
        }

        /**
         * Used to render the Wrestler objects in the winnerComboBox.
         * We'd like for them to show another string besides Wrestler.toString().
         * @param list
         * @param value
         * @param index
         * @param isSelected
         * @param cellHasFocus
         * @return
         */
        public Component getListCellRendererComponent(JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = new JLabel(" -", SwingConstants.LEFT);

            if (isSelected) {
                label.setBackground(table.getSelectionBackground());
                label.setOpaque(true);
                label.setForeground(table.getSelectionForeground());
            }
            if (cellHasFocus) {
                label.setForeground(table.getSelectionBackground());
                label.setBackground(table.getSelectionForeground());
                label.setOpaque(true);
            }

            if (value == null) {
                // One item should be 'null'
                return label;
            }

            Wrestler w = (Wrestler)value;

            label.setText(w.getFirstInitialLastName());

            return label;
        }
    }
}
