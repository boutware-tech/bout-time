/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2009  Jeffrey K. Rutt
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

package bouttime.wizard.newtournament;

import java.awt.Component;
import org.netbeans.spi.wizard.WizardPage;

/**
 *
 */
public class TournamentConfigForm extends WizardPage {

    /** Creates new form TournamentConfigForm */
    public TournamentConfigForm() {
        initComponents();
    }

    public static final String getDescription() {
        return "Enter configuration";
    }

    @Override
    protected String validateContents (Component component, Object o) {
        if (roundRobin.isSelected()) {
            rrUpToLabel.setEnabled(true);
            roundRobinMax.setEnabled(true);
        } else {
            rrUpToLabel.setEnabled(false);
            roundRobinMax.setEnabled(false);
        }

        return null;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        header1Label = new javax.swing.JLabel();
        fifthPlace = new javax.swing.JCheckBox();
        secondPlace = new javax.swing.JCheckBox();
        roundRobin = new javax.swing.JCheckBox();
        rrUpToLabel = new javax.swing.JLabel();
        roundRobinMax = new javax.swing.JComboBox();
        maxAward = new javax.swing.JComboBox();
        maxAwardLabel = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(TournamentConfigForm.class);
        header1Label.setText(resourceMap.getString("header1Label.text")); // NOI18N
        header1Label.setName("header1Label"); // NOI18N

        fifthPlace.setText(resourceMap.getString("fifthPlace.text")); // NOI18N
        fifthPlace.setName("fifthPlace"); // NOI18N

        secondPlace.setText(resourceMap.getString("secondPlace.text")); // NOI18N
        secondPlace.setName("secondPlace"); // NOI18N

        roundRobin.setText(resourceMap.getString("roundRobin.text")); // NOI18N
        roundRobin.setName("roundRobin"); // NOI18N

        rrUpToLabel.setText(resourceMap.getString("rrUpToLabel.text")); // NOI18N
        rrUpToLabel.setEnabled(false);
        rrUpToLabel.setName("rrUpToLabel"); // NOI18N

        roundRobinMax.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "3", "4", "5" }));
        roundRobinMax.setEnabled(false);
        roundRobinMax.setName("roundRobinMax"); // NOI18N

        maxAward.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7" }));
        maxAward.setSelectedIndex(4);
        maxAward.setName("maxAward"); // NOI18N

        maxAwardLabel.setText(resourceMap.getString("maxAwardLabel.text")); // NOI18N
        maxAwardLabel.setName("maxAwardLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(header1Label)
                .addGap(109, 109, 109))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fifthPlace)
                .addContainerGap(323, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(secondPlace)
                .addContainerGap(253, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundRobin)
                .addGap(18, 18, 18)
                .addComponent(rrUpToLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundRobinMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(189, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(maxAwardLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(maxAward, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(272, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header1Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(fifthPlace)
                .addGap(18, 18, 18)
                .addComponent(secondPlace)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roundRobin)
                    .addComponent(rrUpToLabel)
                    .addComponent(roundRobinMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maxAwardLabel)
                    .addComponent(maxAward, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox fifthPlace;
    private javax.swing.JLabel header1Label;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox maxAward;
    private javax.swing.JLabel maxAwardLabel;
    private javax.swing.JCheckBox roundRobin;
    private javax.swing.JComboBox roundRobinMax;
    private javax.swing.JLabel rrUpToLabel;
    private javax.swing.JCheckBox secondPlace;
    // End of variables declaration//GEN-END:variables

}
