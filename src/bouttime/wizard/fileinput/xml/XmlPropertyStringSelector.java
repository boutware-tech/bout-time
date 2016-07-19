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

package bouttime.wizard.fileinput.xml;

import bouttime.fileinput.XMLFileInputConfig;
import java.awt.Component;
import java.util.Map;
import org.netbeans.spi.wizard.WizardPage;

/**
 * This class implements a wizard page to specify the property strings used
 * in a XML file for the Wrestler element.
 */
public class XmlPropertyStringSelector extends WizardPage {

    /** Creates new form XmlPropertyStringSelector */
    public XmlPropertyStringSelector(XMLFileInputConfig config) {
        initComponents();
        initValues(config);
    }

    public static final String getDescription() {
        return "Enter element property strings";
    }

    /**
     * Whenever a value is changed in one of the graphical components, this
     * method is called.
     * Use this to update the sample text area.
     * @param component
     * @param o
     * @return Always returns null.
     */
    @Override
    protected String validateContents (Component component, Object o) {
        if (component == this.sample) {
            return null;
        }

        updateSample();

        return null;
    }

    /**
     * Update the sample text area.  This is a feature to help the user
     * when entering the data.
     */
    private void updateSample() {
        Map data = this.getWizardDataMap();

        String root = (String)data.get("root");
        String wrestler = (String)data.get("wrestler");
        String first = (String)data.get("firstName");
        String last = (String)data.get("lastName");
        String team = (String)data.get("teamName");
        String classi = (String)data.get("classification");
        String div = (String)data.get("division");
        String wt = (String)data.get("weightClass");
        String actWt = (String)data.get("actualWeight");
        String lvl = (String)data.get("level");
        String serNum = (String)data.get("serialNumber");

        StringBuilder str = new StringBuilder();
        str.append(String.format("<%s>\n", root));
        str.append(String.format("  <%s>\n", wrestler));

        if ((first != null) && !first.isEmpty()) {
            str.append(String.format("    <%s>John</%s>\n", first, first));
        }

        if ((last != null) && !last.isEmpty()) {
            str.append(String.format("    <%s>Doe</%s>\n", last, last));
        }

        if ((team != null) && !team.isEmpty()) {
            str.append(String.format("    <%s>Team</%s>\n", team, team));
        }

        if ((classi != null) && !classi.isEmpty()) {
            str.append(String.format("    <%s>Open</%s>\n", classi, classi));
        }

        if ((div != null) && !div.isEmpty()) {
            str.append(String.format("    <%s>1</%s>\n", div, div));
        }

        if ((wt != null) && !wt.isEmpty()) {
            str.append(String.format("    <%s>100</%s>\n", wt, wt));
        }

        if ((actWt != null) && !actWt.isEmpty()) {
            str.append(String.format("    <%s>100</%s>\n", actWt, actWt));
        }

        if ((lvl != null) && !lvl.isEmpty()) {
            str.append(String.format("    <%s>A</%s>\n", lvl, lvl));
        }

        if ((serNum != null) && !serNum.isEmpty()) {
            str.append(String.format("    <%s>123456789</%s>\n", serNum, serNum));
        }

        str.append(String.format("  </%s>\n", wrestler));
        str.append(String.format("</%s>", root));

        sample.setText(str.toString());
    }
    
    /**
     * Initialize the values of the graphical components.
     * @param config FileInputConfig object containing the current configuration.
     */
    private void initValues(XMLFileInputConfig config) {
        String s;

        s = config.getActualWeight();
        if (s != null) {
            actualWeight.setText(s);
        }

        s = config.getClassification();
        if (s != null) {
            classification.setText(s);
        }

        s = config.getDivision();
        if (s != null) {
            division.setText(s);
        }

        s = config.getFirstName();
        if (s != null) {
            firstName.setText(s);
        }

        s = config.getLastName();
        if (s != null) {
            lastName.setText(s);
        }

        s = config.getLevel();
        if (s != null) {
            level.setText(s);
        }

        s = config.getSerialNumber();
        if (s != null) {
            serialNumber.setText(s);
        }

        s = config.getTeamName();
        if (s != null) {
            teamName.setText(s);
        }

        s = config.getWeightClass();
        if (s != null) {
            weightClass.setText(s);
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
        header1Label = new javax.swing.JLabel();
        header2Label = new javax.swing.JLabel();
        weightClassLabel = new javax.swing.JLabel();
        actualWeightLabel = new javax.swing.JLabel();
        levelLabel = new javax.swing.JLabel();
        serialNumberLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        firstName = new javax.swing.JTextField();
        weightClass = new javax.swing.JTextField();
        lastName = new javax.swing.JTextField();
        teamName = new javax.swing.JTextField();
        classification = new javax.swing.JTextField();
        division = new javax.swing.JTextField();
        actualWeight = new javax.swing.JTextField();
        level = new javax.swing.JTextField();
        serialNumber = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        sample = new javax.swing.JTextArea();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(XmlPropertyStringSelector.class);
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

        jSeparator1.setName("jSeparator1"); // NOI18N

        firstName.setText(resourceMap.getString("firstName.text")); // NOI18N
        firstName.setName("firstName"); // NOI18N

        weightClass.setText(resourceMap.getString("weightClass.text")); // NOI18N
        weightClass.setName("weightClass"); // NOI18N

        lastName.setText(resourceMap.getString("lastName.text")); // NOI18N
        lastName.setName("lastName"); // NOI18N

        teamName.setText(resourceMap.getString("teamName.text")); // NOI18N
        teamName.setName("teamName"); // NOI18N

        classification.setText(resourceMap.getString("classification.text")); // NOI18N
        classification.setName("classification"); // NOI18N

        division.setText(resourceMap.getString("division.text")); // NOI18N
        division.setName("division"); // NOI18N

        actualWeight.setText(resourceMap.getString("actualWeight.text")); // NOI18N
        actualWeight.setName("actualWeight"); // NOI18N

        level.setText(resourceMap.getString("level.text")); // NOI18N
        level.setName("level"); // NOI18N

        serialNumber.setText(resourceMap.getString("serialNumber.text")); // NOI18N
        serialNumber.setName("serialNumber"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        sample.setColumns(20);
        sample.setEditable(false);
        sample.setRows(5);
        sample.setName("sample"); // NOI18N
        jScrollPane1.setViewportView(sample);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lastLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(teamLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(classLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(divisionLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(weightClassLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(actualWeightLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(levelLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(serialNumberLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(serialNumber)
                            .addComponent(level)
                            .addComponent(actualWeight)
                            .addComponent(weightClass)
                            .addComponent(division)
                            .addComponent(lastName)
                            .addComponent(teamName)
                            .addComponent(classification)
                            .addComponent(firstName, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(header2Label))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(header1Label))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(firstLabel)
                            .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lastLabel)
                            .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(teamLabel)
                            .addComponent(teamName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(classLabel)
                            .addComponent(classification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(divisionLabel)
                            .addComponent(division, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(weightClassLabel)
                            .addComponent(weightClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(actualWeightLabel)
                            .addComponent(actualWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(levelLabel)
                            .addComponent(level, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(serialNumberLabel)
                            .addComponent(serialNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField actualWeight;
    private javax.swing.JLabel actualWeightLabel;
    private javax.swing.JLabel classLabel;
    private javax.swing.JTextField classification;
    private javax.swing.JTextField division;
    private javax.swing.JLabel divisionLabel;
    private javax.swing.JLabel firstLabel;
    private javax.swing.JTextField firstName;
    private javax.swing.JLabel header1Label;
    private javax.swing.JLabel header2Label;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lastLabel;
    private javax.swing.JTextField lastName;
    private javax.swing.JTextField level;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JTextArea sample;
    private javax.swing.JTextField serialNumber;
    private javax.swing.JLabel serialNumberLabel;
    private javax.swing.JLabel teamLabel;
    private javax.swing.JTextField teamName;
    private javax.swing.JTextField weightClass;
    private javax.swing.JLabel weightClassLabel;
    // End of variables declaration//GEN-END:variables

}
