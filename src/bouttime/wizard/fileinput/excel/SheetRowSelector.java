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

package bouttime.wizard.fileinput.excel;

import bouttime.fileinput.ExcelFileInputConfig;
import java.awt.Component;
import org.netbeans.spi.wizard.WizardPage;

/**
 * This class implements a wizard page to select the starting and ending rows
 * for data in the input file.
 */
public class SheetRowSelector extends WizardPage {

    /** Creates new form SheetRowSelector */
    public SheetRowSelector(ExcelFileInputConfig config) {
        initComponents();
        initValues(config);
    }

    public static final String getDescription() {
        return "Enter sheet and rows";
    }

    /**
     * Validate the contents of the input.
     * The user must enter values for the start and end rows.
     * @param component
     * @param o
     * @return Help message or null (if contents are valid).
     */
    @Override
    protected String validateContents (Component component, Object o) {
        if (sheet.getText().isEmpty() || startRow.getText().isEmpty() || endRow.getText().isEmpty()) {
            return "You must enter numbers for the sheet and start and end rows";
        } else {
            return null;
        }
    }

    /**
     * Initialize the values of the graphical components.
     * @param config FileInputConfig object containing the current configuration.
     */
    private void initValues(ExcelFileInputConfig config) {
        String s;

        s = config.getEndRow();
        if (s != null) {
            endRow.setText(s);
        }

        s = config.getSheet();
        if (s != null) {
            sheet.setText(s);
        }

        s = config.getStartRow();
        if (s != null) {
            startRow.setText(s);
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

        header2Label = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        startLabel = new javax.swing.JLabel();
        endLabel = new javax.swing.JLabel();
        startRow = new javax.swing.JTextField();
        endRow = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        sheet = new javax.swing.JTextField();
        header1Label = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(SheetRowSelector.class);
        header2Label.setText(resourceMap.getString("header2Label.text")); // NOI18N
        header2Label.setName("header2Label"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        startLabel.setText(resourceMap.getString("startLabel.text")); // NOI18N
        startLabel.setName("startLabel"); // NOI18N

        endLabel.setText(resourceMap.getString("endLabel.text")); // NOI18N
        endLabel.setName("endLabel"); // NOI18N

        startRow.setText(resourceMap.getString("startRow.text")); // NOI18N
        startRow.setName("startRow"); // NOI18N

        endRow.setText(resourceMap.getString("endRow.text")); // NOI18N
        endRow.setName("endRow"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        sheet.setName("sheet"); // NOI18N

        header1Label.setText(resourceMap.getString("header1Label.text")); // NOI18N
        header1Label.setName("header1Label"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(startLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(endLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(endRow, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(startRow, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(sheet, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(293, 293, 293))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(header2Label)
                .addContainerGap(104, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header1Label)
                .addContainerGap(123, Short.MAX_VALUE))
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
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(sheet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startLabel)
                    .addComponent(startRow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(endLabel)
                    .addComponent(endRow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(88, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel endLabel;
    private javax.swing.JTextField endRow;
    private javax.swing.JLabel header1Label;
    private javax.swing.JLabel header2Label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField sheet;
    private javax.swing.JLabel startLabel;
    private javax.swing.JTextField startRow;
    // End of variables declaration//GEN-END:variables

}