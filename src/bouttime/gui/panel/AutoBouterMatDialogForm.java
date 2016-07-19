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

import bouttime.autobouter.AutoBouter;
import bouttime.dao.Dao;
import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.sort.BoutNumSort;
import java.awt.Frame;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.apache.log4j.Logger;

/**
 * A class to enter input values for the AutoBouter to assign bouts with
 * unique numbers per mat (vs. per tournament).
 */
public class AutoBouterMatDialogForm extends javax.swing.JDialog {
    static Logger logger = Logger.getLogger(AutoBouterMatDialogForm.class);

    /** Creates new form AutoBouterMatDialogForm */
    public AutoBouterMatDialogForm(Frame parent, boolean modal, Dao dao) {
        super(parent, modal);

        this.parent = parent;
        this.dao = dao;
        this.cancelled = false;

        initComponents();

        setComboBoxes();

        this.setVisible(true);
    }

    /**
     * @return True if the user clicked the cancel button.
     */
    public boolean isCancelled() {return this.cancelled;}

    /**
     * Run the auto-bouter.
     */
    @Action
    public void runAutoBouter() {
        int minSpacing = getMinSpacing();
        if (minSpacing < 0) {
            return;
        }

        String [] matVals = getMatValues();
        if (matVals == null) {
            return;
        }

        String session = (String)sessions.getSelectedItem();

        int startsWithVal = 0;
        if (startsWithInt.isSelected()) {
            startsWithVal = getStartsWithValue();
            if (startsWithVal < 0) {
                return;
            }
        }

        int suspendVal = getSuspendValue();

        for (int i = 0; i < matVals.length; i++) {
            logger.info("mat=" + matVals[i]);
            String mat = matVals[i];

            List<Group> list = this.dao.getGroupsBySessionMat(session, mat);
            if ((list == null) || list.isEmpty()) {
                logger.warn("No groups assigned to mat " + mat);
                continue;
            }

            if (hasLockedGroup(list)) {
                JOptionPane.showMessageDialog(this.parent, "One or more groups for mat " +
                        mat + " is locked." +
                        "\nYou must unlock the affected group(s) before attempting this operation.",
                        "Auto Bout Error", JOptionPane.ERROR_MESSAGE);
                logger.warn("Unable to assign bout numbers for mat " + mat + " : locked group");
                continue;
            }

            int firstNum = 0;
            if (startsWithOne.isSelected()) {
                firstNum = 1;
            } else if (startsWithLast.isSelected()) {
                int last = getLastBoutForMat(mat);
                if (last < 0) {
                    continue;
                }
                firstNum = last + 1;
            } else {
                firstNum = startsWithVal;
            }
            AutoBouter ab = new AutoBouter();
            ab.assignBoutNumbers(this.dao, list, firstNum, minSpacing, suspendVal);
        }

        this.setVisible(false);

        this.dao.flush();
    }

    /**
     * Cancel this operation.
     * Set the 'cancelled' property to false and discard/remove the window/panel.
     */
    @Action
    public void cancelAction() {
        this.cancelled = true;
        this.setVisible(false);
        logger.debug("cancelled");
    }

    /**
     * Set the values of all the combo boxes.
     */
    private void setComboBoxes() {
        mats.addItem(ALL_KEYWORD);
        setComboBox(this.dao.getMatValues(), mats);
        setComboBox(this.dao.getSessionValues(), sessions);
    }

    /**
     * Set the value of a given combo box with the given values.
     * @param values Comma-separated list of values.
     * @param comboBox The combo box to make changes to.
     */
    private void setComboBox(String values, JComboBox comboBox) {
        if (values == null) {
            comboBox.addItem(null);
            return;
        }

        String [] tokens = values.split(",");

        // Add the given values to the combo box.
        for (int i = 0; i < tokens.length; i++) {
            comboBox.addItem(tokens[i].trim());
        }
    }

    /**
     * Get the minimum spacing value from the form.  Check to make sure the
     * user entered a value and the value is an integer.
     * @return the minimum spacing value on success, -1 on error
     */
    private int getMinSpacing() {
        int minSpacing = 0;
        String minSpacingStr = minBoutSpacing.getText();
        if ((minSpacingStr != null) && !minSpacingStr.isEmpty()) {
            try {
                minSpacing = Integer.parseInt(minSpacingStr);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(parent, "You must enter an integer " +
                        "for the minimum bout spacing.", "Min Bout Spacing Error",
                        JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        } else {
            JOptionPane.showMessageDialog(parent, "You must enter a number for " +
                    "the minimum bout spacing.\nThis is the minimum number of " +
                    "bouts between a given wrestler's bouts.\nA decent value " +
                    "to use is 10% of the bouts for a mat/session.",
                    "Min Bout Spacing Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        return minSpacing;
    }

    /**
     * Get the user entered value to start the bout numbering with.
     * Check to make sure the user entered a value and the value is an integer.
     * This assumes the user has selected the startsWithInt radio button.
     * @return the starts with value on success, -1 on error
     */
    private int getStartsWithValue() {
        int startsWithVal = 0;
        String startsWithValStr = null;
        startsWithValStr = startsWithIntVal.getText();
        if ((startsWithValStr != null) && !startsWithValStr.isEmpty()) {
            try {
                startsWithVal = Integer.parseInt(startsWithValStr);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(parent, "You must enter an integer " +
                    "for the starts with value.", "Starts With Value Error",
                    JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        } else {
            JOptionPane.showMessageDialog(parent, "You must enter an integer " +
                    "for the starts with value.", "Starts With Value Error",
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        return startsWithVal;
    }

    private int getSuspendValue() {
        return (suspend.isSelected()) ? suspendValue.getSelectedIndex() + 1 : 0;
    }

    /**
     * Get the mat values the user wants to assign bouts to.
     * @return an array of mat values
     */
    private String [] getMatValues() {
        String mat = (String)mats.getSelectedItem();
        if ((mat == null) || mat.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "There is no mat specified.\n" +
                    "Select 'Edit->Configuration' to add mat values.",
                    "Mat Values Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        String [] matVals = null;

        if (mat.equalsIgnoreCase(ALL_KEYWORD)) {
            String daoMatValues = this.dao.getMatValues();
            if ((daoMatValues == null) || daoMatValues.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "There are no mats specified " +
                        "for this tournament\n.Select 'Edit->Configuration' to add" +
                        "mat values.", "Mat Values Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            matVals = daoMatValues.split(",");
        } else {
            matVals = new String [] {mat};
        }

        return matVals;
    }

    /**
     * Get the last bout number for the given mat.
     * @param mat Mat to retrieve the last bout number for.
     * @return last bout number on success, -1 on error
     */
    private int getLastBoutForMat(String mat) {
        int lastBout = 0;
        List<Bout> list = this.dao.getBoutsByMat(mat);
        if ((list == null) || list.isEmpty()) {
            logger.error("No bouts for mat " + mat);
            return -1;
        }

        Collections.sort(list, new BoutNumSort());
        Bout b = list.get(list.size() - 1); // Get the last bout
        String boutNum = b.getBoutNum();

        return extractIntFromString(boutNum);
    }

    /**
     * Extract an integer from a string.
     * @param str The string to extract the integer from.
     * @return the int value of the string on success, -1 on error
     */
    private int extractIntFromString(String str) {
        int i = 0;
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            // Wasn't an integer.
            // Look for an integer followed by a character (e.g. 45A).
            
            // Regular Expression for any non-digit character
            Pattern p = Pattern.compile("[^0-9]");
            Matcher m = p.matcher(str);
            if (m.find() && (m.end() != 0)) {
                try {
                    String s = str.substring(0, m.start());
                    i = Integer.parseInt(s);
                } catch (NumberFormatException nfe2) {
                    logger.error("No integer in string : " + str);
                    return -1;
                }
            }

        }

        return i;
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
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startsWith = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        matLabel = new javax.swing.JLabel();
        sessionLabel = new javax.swing.JLabel();
        mats = new javax.swing.JComboBox();
        sessions = new javax.swing.JComboBox();
        minBoutSpacingLabel = new javax.swing.JLabel();
        minBoutSpacing = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        startsWithOne = new javax.swing.JRadioButton();
        startsWithLast = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        startsWithInt = new javax.swing.JRadioButton();
        startsWithIntVal = new javax.swing.JTextField();
        suspend = new javax.swing.JCheckBox();
        suspendValue = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(AutoBouterMatDialogForm.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        matLabel.setText(resourceMap.getString("matLabel.text")); // NOI18N
        matLabel.setName("matLabel"); // NOI18N

        sessionLabel.setText(resourceMap.getString("sessionLabel.text")); // NOI18N
        sessionLabel.setName("sessionLabel"); // NOI18N

        mats.setName("mats"); // NOI18N

        sessions.setName("sessions"); // NOI18N

        minBoutSpacingLabel.setText(resourceMap.getString("minBoutSpacingLabel.text")); // NOI18N
        minBoutSpacingLabel.setToolTipText(resourceMap.getString("minBoutSpacingLabel.toolTipText")); // NOI18N
        minBoutSpacingLabel.setName("minBoutSpacingLabel"); // NOI18N

        minBoutSpacing.setText(resourceMap.getString("minBoutSpacing.text")); // NOI18N
        minBoutSpacing.setName("minBoutSpacing"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getActionMap(AutoBouterMatDialogForm.class, this);
        cancelButton.setAction(actionMap.get("cancelAction")); // NOI18N
        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N

        okButton.setAction(actionMap.get("runAutoBouter")); // NOI18N
        okButton.setText(resourceMap.getString("okButton.text")); // NOI18N
        okButton.setName("okButton"); // NOI18N

        startsWith.add(startsWithOne);
        startsWithOne.setSelected(true);
        startsWithOne.setText(resourceMap.getString("startsWithOne.text")); // NOI18N
        startsWithOne.setToolTipText(resourceMap.getString("startsWithOne.toolTipText")); // NOI18N
        startsWithOne.setName("startsWithOne"); // NOI18N

        startsWith.add(startsWithLast);
        startsWithLast.setText(resourceMap.getString("startsWithLast.text")); // NOI18N
        startsWithLast.setToolTipText(resourceMap.getString("startsWithLast.toolTipText")); // NOI18N
        startsWithLast.setName("startsWithLast"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        startsWith.add(startsWithInt);
        startsWithInt.setText(resourceMap.getString("startsWithInt.text")); // NOI18N
        startsWithInt.setToolTipText(resourceMap.getString("startsWithInt.toolTipText")); // NOI18N
        startsWithInt.setName("startsWithInt"); // NOI18N
        startsWithInt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                startsWithIntItemStateChanged(evt);
            }
        });

        startsWithIntVal.setText(resourceMap.getString("startsWithIntVal.text")); // NOI18N
        startsWithIntVal.setEnabled(false);
        startsWithIntVal.setName("startsWithIntVal"); // NOI18N

        suspend.setText(resourceMap.getString("suspend.text")); // NOI18N
        suspend.setToolTipText(resourceMap.getString("suspend.toolTipText")); // NOI18N
        suspend.setName("suspend"); // NOI18N
        suspend.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                suspendStateChanged(evt);
            }
        });

        suspendValue.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1st", "3rd", "5th" }));
        suspendValue.setEnabled(false);
        suspendValue.setName("suspendValue"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                        .addComponent(cancelButton))
                    .addComponent(jLabel2)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(matLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(sessionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sessions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(suspend)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(suspendValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(minBoutSpacingLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(minBoutSpacing, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(startsWithOne)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(startsWithInt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startsWithIntVal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(startsWithLast))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(matLabel)
                    .addComponent(mats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sessions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sessionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minBoutSpacingLabel)
                    .addComponent(minBoutSpacing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(suspend)
                    .addComponent(suspendValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startsWithOne)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startsWithLast)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startsWithInt)
                    .addComponent(startsWithIntVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startsWithIntItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_startsWithIntItemStateChanged
        startsWithIntVal.setEnabled(startsWithInt.isSelected());
    }//GEN-LAST:event_startsWithIntItemStateChanged

    private void suspendStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_suspendStateChanged
        suspendValue.setEnabled(suspend.isSelected());
    }//GEN-LAST:event_suspendStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel matLabel;
    private javax.swing.JComboBox mats;
    private javax.swing.JTextField minBoutSpacing;
    private javax.swing.JLabel minBoutSpacingLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel sessionLabel;
    private javax.swing.JComboBox sessions;
    private javax.swing.ButtonGroup startsWith;
    private javax.swing.JRadioButton startsWithInt;
    private javax.swing.JTextField startsWithIntVal;
    private javax.swing.JRadioButton startsWithLast;
    private javax.swing.JRadioButton startsWithOne;
    private javax.swing.JCheckBox suspend;
    private javax.swing.JComboBox suspendValue;
    // End of variables declaration//GEN-END:variables

    private Frame parent;
    private Dao dao;
    private boolean cancelled;
    private final String ALL_KEYWORD = "All";
}
