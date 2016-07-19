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
import bouttime.model.Wrestler;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import org.jdesktop.application.Action;
import org.apache.log4j.Logger;

/**
 * A class to enter data for a wrestler object.
 * This class is used to enter a new wrestler and also to edit
 * an existing wrestler.
 */
public class WrestlerDialogForm extends javax.swing.JDialog {
    static Logger logger = Logger.getLogger(WrestlerDialogForm.class);

    /** Creates new form WrestlerDialogForm */
    public WrestlerDialogForm(JFrame parent, boolean modal, Dao dao) {
        super(parent, modal);

        this.dao = dao;
        this.wrestler = null;
        this.cancelled = false;

        initComponents();

        setComboBoxes();

        this.setVisible(true);
    }

    public WrestlerDialogForm(JFrame parent, boolean modal, Dao dao, Wrestler w) {
        super(parent, modal);

        this.dao = dao;
        this.wrestler = w;
        this.cancelled = false;

        initComponents();

        setComboBoxes();

        setValues();

        this.setVisible(true);
    }

    /**
     * Set the values of all the combo boxes.
     */
    private void setComboBoxes() {
        String currentClass = null;
        String currentDivision = null;
        String currentWeight = null;

        if (this.wrestler != null) {
            currentClass = this.wrestler.getClassification();
            currentDivision = this.wrestler.getAgeDivision();
            currentWeight = this.wrestler.getWeightClass();
        }

        setComboBox(this.dao.getClassificationValues(), currentClass, classification);
        setComboBox(this.dao.getAgeDivisionValues(), currentDivision, div);
        setComboBox(this.dao.getWeightClassValues(), currentWeight, weight);
    }

    /**
     * Set the value of a given combo box with the given values.
     * @param values Comma-separated list of values.
     * @param current The current value (if any) for this wrestler.
     * @param comboBox The combo box to make changes to.
     */
    private void setComboBox(String values, String current, JComboBox comboBox) {
        if (values == null) {
            if (current != null) {
                comboBox.addItem(current);
                comboBox.setSelectedItem(current);
            }

            return;
        }
        
        String [] tokens = values.split(",");

        // Add the given values to the combo box.
        // Check to see if the current value is in the given list of values.
        boolean foundIt = false;
        for (int i = 0; i < tokens.length; i++) {
            comboBox.addItem(tokens[i].trim());
            if ((current != null) && current.equalsIgnoreCase(tokens[i].trim())) {
                // Found the current value in the list of given values.
                // Mark it as the selected item.
                foundIt = true;
                comboBox.setSelectedItem(current);
            }
        }

        // The current value may not be in the given values list.
        // If not, add the current value to the list of items and set it
        // as the selected item.
        if (!foundIt && (current != null)) {
            comboBox.addItem(current);
            comboBox.setSelectedItem(current);
        }
    }

    /**
     * Initialize the values of the graphical components.
     */
    private void setValues() {
        first.setText(this.wrestler.getFirstName());
        last.setText(this.wrestler.getLastName());
        team.setText(this.wrestler.getTeamName());
        geo.setText(this.wrestler.getGeo());
        classification.setSelectedItem(this.wrestler.getClassification());
        actWeight.setText(this.wrestler.getActualWeight());
        level.setText(this.wrestler.getLevel());
        id.setText(this.wrestler.getSerialNumber());
    }

    /**
     * @return True if the user clicked the cancel button.
     */
    public boolean isCancelled() {return this.cancelled;}

    /**
     * Create or update the wrestler.
     */
    @Action
    public void createWrestler() {
        if (!isDataOK()) {
            this.setVisible(false);
            return;
        }

        boolean isNew = false;
        if (this.wrestler == null) {
            isNew = true;
            this.wrestler = new Wrestler();
        }

        this.wrestler.setFirstName(first.getText());
        this.wrestler.setLastName(last.getText());
        this.wrestler.setTeamName(team.getText());
        this.wrestler.setGeo(geo.getText());
        this.wrestler.setClassification((String)classification.getSelectedItem());
        this.wrestler.setAgeDivision((String)div.getSelectedItem());
        this.wrestler.setWeightClass((String)weight.getSelectedItem());
        this.wrestler.setActualWeight(actWeight.getText());
        this.wrestler.setLevel((String)level.getText());
        this.wrestler.setSerialNumber(id.getText());

        if (isNew) {
            if (!this.dao.addWrestler(wrestler)) {
                logger.info("Wrestler [" + wrestler + "] not added");
            }
        }

        this.dao.flush();

        this.setVisible(false);
    }

    /**
     * Cancel this operation.
     * Set the 'cancelled' property to false and discard/remove the window/panel.
     */
    @Action
    public void cancelAction() {
        this.cancelled = true;
        this.setVisible(false);
    }

    /**
     * Verify the minimum data has been entered.
     * @return True if the data entered is OK.
     */
    private boolean isDataOK() {
        if (first.getText().isEmpty() ||
                last.getText().isEmpty()) {
            logger.warn("No first or last name entered.");
            return false;
        }

        return true;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        firstLabel = new javax.swing.JLabel();
        first = new javax.swing.JTextField();
        lastLabel = new javax.swing.JLabel();
        last = new javax.swing.JTextField();
        teamLabel = new javax.swing.JLabel();
        team = new javax.swing.JTextField();
        classLabel = new javax.swing.JLabel();
        classification = new javax.swing.JComboBox();
        divLabel = new javax.swing.JLabel();
        div = new javax.swing.JComboBox();
        weightLabel = new javax.swing.JLabel();
        weight = new javax.swing.JComboBox();
        actWeightLabel = new javax.swing.JLabel();
        actWeight = new javax.swing.JTextField();
        levelLabel = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        level = new javax.swing.JTextField();
        geoLabel = new javax.swing.JLabel();
        geo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(WrestlerDialogForm.class);
        firstLabel.setText(resourceMap.getString("firstLabel.text")); // NOI18N
        firstLabel.setName("firstLabel"); // NOI18N

        first.setName("first"); // NOI18N

        lastLabel.setText(resourceMap.getString("lastLabel.text")); // NOI18N
        lastLabel.setName("lastLabel"); // NOI18N

        last.setName("last"); // NOI18N

        teamLabel.setText(resourceMap.getString("teamLabel.text")); // NOI18N
        teamLabel.setName("teamLabel"); // NOI18N

        team.setName("team"); // NOI18N

        classLabel.setText(resourceMap.getString("classLabel.text")); // NOI18N
        classLabel.setName("classLabel"); // NOI18N

        classification.setEditable(true);
        classification.setName("classification"); // NOI18N

        divLabel.setText(resourceMap.getString("divLabel.text")); // NOI18N
        divLabel.setName("divLabel"); // NOI18N

        div.setEditable(true);
        div.setName("div"); // NOI18N

        weightLabel.setText(resourceMap.getString("weightLabel.text")); // NOI18N
        weightLabel.setName("weightLabel"); // NOI18N

        weight.setEditable(true);
        weight.setName("weight"); // NOI18N

        actWeightLabel.setText(resourceMap.getString("actWeightLabel.text")); // NOI18N
        actWeightLabel.setName("actWeightLabel"); // NOI18N

        actWeight.setName("actWeight"); // NOI18N

        levelLabel.setText(resourceMap.getString("levelLabel.text")); // NOI18N
        levelLabel.setName("levelLabel"); // NOI18N

        idLabel.setText(resourceMap.getString("idLabel.text")); // NOI18N
        idLabel.setName("idLabel"); // NOI18N

        id.setName("id"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getActionMap(WrestlerDialogForm.class, this);
        okButton.setAction(actionMap.get("createWrestler")); // NOI18N
        okButton.setText(resourceMap.getString("okButton.text")); // NOI18N
        okButton.setName("okButton"); // NOI18N

        cancelButton.setAction(actionMap.get("cancelAction")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N

        level.setText(resourceMap.getString("level.text")); // NOI18N
        level.setName("level"); // NOI18N

        geoLabel.setText(resourceMap.getString("geoLabel.text")); // NOI18N
        geoLabel.setName("geoLabel"); // NOI18N

        geo.setName("geo"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(geoLabel)
                            .addComponent(firstLabel)
                            .addComponent(lastLabel)
                            .addComponent(teamLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(last, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addComponent(team, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addComponent(first, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addComponent(geo, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(classLabel)
                                .addComponent(divLabel)
                                .addComponent(weightLabel)
                                .addComponent(actWeightLabel)
                                .addComponent(levelLabel)
                                .addComponent(idLabel))
                            .addComponent(cancelButton))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(classification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(div, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(actWeight, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                    .addComponent(id, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                    .addComponent(level, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)))
                            .addComponent(okButton, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstLabel)
                    .addComponent(first, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastLabel)
                    .addComponent(last, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teamLabel)
                    .addComponent(team, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(geoLabel)
                    .addComponent(geo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(classLabel)
                    .addComponent(classification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(divLabel)
                    .addComponent(div, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weightLabel)
                    .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actWeightLabel)
                    .addComponent(actWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(levelLabel)
                    .addComponent(level, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idLabel)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField actWeight;
    private javax.swing.JLabel actWeightLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel classLabel;
    private javax.swing.JComboBox classification;
    private javax.swing.JComboBox div;
    private javax.swing.JLabel divLabel;
    private javax.swing.JTextField first;
    private javax.swing.JLabel firstLabel;
    private javax.swing.JTextField geo;
    private javax.swing.JLabel geoLabel;
    private javax.swing.JTextField id;
    private javax.swing.JLabel idLabel;
    private javax.swing.JTextField last;
    private javax.swing.JLabel lastLabel;
    private javax.swing.JTextField level;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField team;
    private javax.swing.JLabel teamLabel;
    private javax.swing.JComboBox weight;
    private javax.swing.JLabel weightLabel;
    // End of variables declaration//GEN-END:variables

    private Dao dao;
    private Wrestler wrestler;
    private boolean cancelled;
}
