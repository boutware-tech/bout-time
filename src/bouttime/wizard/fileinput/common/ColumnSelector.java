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

package bouttime.wizard.fileinput.common;

import bouttime.fileinput.FileInputConfig;
import org.netbeans.spi.wizard.WizardPage;

/**
 * This class implements a wizard page to specify which columns in a file
 * contain data associated with a wrestler entity.
 */
public class ColumnSelector extends WizardPage {

    /** Creates new form ColumnSelector */
    public ColumnSelector(FileInputConfig config) {
        initComponents();
        initValues(config);
    }

    public static final String getDescription() {
        return "Select data columns";
    }

    /**
     * Initialize the values of the graphical components.
     * @param config FileInputConfig object containing the current configuration.
     */
    private void initValues(FileInputConfig config) {
        String s;

        s = config.getActualWeight();
        if (s != null) {
            actualWeight.setSelectedItem(s);
        }

        s = config.getClassification();
        if (s != null) {
            classification.setSelectedItem(s);
        }

        s = config.getDivision();
        if (s != null) {
            division.setSelectedItem(s);
        }

        s = config.getFirstName();
        if (s != null) {
            firstName.setSelectedItem(s);
        }

        s = config.getLastName();
        if (s != null) {
            lastName.setSelectedItem(s);
        }

        s = config.getLevel();
        if (s != null) {
            level.setSelectedItem(s);
        }

        s = config.getSerialNumber();
        if (s != null) {
            serialNumber.setSelectedItem(s);
        }

        s = config.getTeamName();
        if (s != null) {
            teamName.setSelectedItem(s);
        }

        s = config.getGeo();
        if (s != null) {
            geo.setSelectedItem(s);
        }

        s = config.getWeightClass();
        if (s != null) {
            weightClass.setSelectedItem(s);
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

        firstLabel = new javax.swing.JLabel();
        lastLabel = new javax.swing.JLabel();
        teamLabel = new javax.swing.JLabel();
        classLabel = new javax.swing.JLabel();
        divisionLabel = new javax.swing.JLabel();
        firstName = new javax.swing.JComboBox();
        lastName = new javax.swing.JComboBox();
        teamName = new javax.swing.JComboBox();
        classification = new javax.swing.JComboBox();
        division = new javax.swing.JComboBox();
        header1Label = new javax.swing.JLabel();
        header2Label = new javax.swing.JLabel();
        weightClassLabel = new javax.swing.JLabel();
        actualWeightLabel = new javax.swing.JLabel();
        levelLabel = new javax.swing.JLabel();
        serialNumberLabel = new javax.swing.JLabel();
        weightClass = new javax.swing.JComboBox();
        actualWeight = new javax.swing.JComboBox();
        level = new javax.swing.JComboBox();
        serialNumber = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        geoLabel = new javax.swing.JLabel();
        geo = new javax.swing.JComboBox();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(ColumnSelector.class);
        firstLabel.setText(resourceMap.getString("firstLabel.text")); // NOI18N
        firstLabel.setName("firstLabel"); // NOI18N

        lastLabel.setText(resourceMap.getString("lastLabel.text")); // NOI18N
        lastLabel.setName("lastLabel"); // NOI18N

        teamLabel.setText(resourceMap.getString("teamLabel.text")); // NOI18N
        teamLabel.setName("teamLabel"); // NOI18N

        classLabel.setText(resourceMap.getString("classLabel.text")); // NOI18N
        classLabel.setName("classLabel"); // NOI18N

        divisionLabel.setText(resourceMap.getString("divisionLabel.text")); // NOI18N
        divisionLabel.setName("divisionLabel"); // NOI18N

        firstName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        firstName.setName("firstName"); // NOI18N

        lastName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        lastName.setName("lastName"); // NOI18N

        teamName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        teamName.setName("teamName"); // NOI18N

        classification.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        classification.setName("classification"); // NOI18N

        division.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        division.setName("division"); // NOI18N

        header1Label.setText(resourceMap.getString("header1Label.text")); // NOI18N
        header1Label.setName("header1Label"); // NOI18N

        header2Label.setText(resourceMap.getString("header2Label.text")); // NOI18N
        header2Label.setName("header2Label"); // NOI18N

        weightClassLabel.setText(resourceMap.getString("weightClassLabel.text")); // NOI18N
        weightClassLabel.setName("weightClassLabel"); // NOI18N

        actualWeightLabel.setText(resourceMap.getString("actualWeightLabel.text")); // NOI18N
        actualWeightLabel.setName("actualWeightLabel"); // NOI18N

        levelLabel.setText(resourceMap.getString("levelLabel.text")); // NOI18N
        levelLabel.setName("levelLabel"); // NOI18N

        serialNumberLabel.setText(resourceMap.getString("serialNumberLabel.text")); // NOI18N
        serialNumberLabel.setName("serialNumberLabel"); // NOI18N

        weightClass.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        weightClass.setName("weightClass"); // NOI18N

        actualWeight.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        actualWeight.setName("actualWeight"); // NOI18N

        level.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        level.setName("level"); // NOI18N

        serialNumber.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        serialNumber.setName("serialNumber"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        geoLabel.setText(resourceMap.getString("geoLabel.text")); // NOI18N
        geoLabel.setName("geoLabel"); // NOI18N

        geo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        geo.setName("geo"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lastLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(teamLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(classLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(divisionLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(classification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(teamName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(weightClassLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(actualWeightLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(levelLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(serialNumberLabel, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(division, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(geoLabel)))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(geo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(weightClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(actualWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(level, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(serialNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(header1Label)
                    .addComponent(header2Label))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header1Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(header2Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstLabel)
                    .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weightClassLabel)
                    .addComponent(weightClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastLabel)
                    .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(actualWeightLabel)
                    .addComponent(actualWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teamLabel)
                    .addComponent(teamName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(levelLabel)
                    .addComponent(level, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(classLabel)
                    .addComponent(classification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serialNumberLabel)
                    .addComponent(serialNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(divisionLabel)
                    .addComponent(division, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(geoLabel)
                    .addComponent(geo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox actualWeight;
    private javax.swing.JLabel actualWeightLabel;
    private javax.swing.JLabel classLabel;
    private javax.swing.JComboBox classification;
    private javax.swing.JComboBox division;
    private javax.swing.JLabel divisionLabel;
    private javax.swing.JLabel firstLabel;
    private javax.swing.JComboBox firstName;
    private javax.swing.JComboBox geo;
    private javax.swing.JLabel geoLabel;
    private javax.swing.JLabel header1Label;
    private javax.swing.JLabel header2Label;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lastLabel;
    private javax.swing.JComboBox lastName;
    private javax.swing.JComboBox level;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JComboBox serialNumber;
    private javax.swing.JLabel serialNumberLabel;
    private javax.swing.JLabel teamLabel;
    private javax.swing.JComboBox teamName;
    private javax.swing.JComboBox weightClass;
    private javax.swing.JLabel weightClassLabel;
    // End of variables declaration//GEN-END:variables

}