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

package bouttime.wizard.fileinput.common;

import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.jdesktop.application.Action;
import org.netbeans.spi.wizard.WizardPage;

/**
 * This class implements a wizard page to select the input file.
 */
public class FileSelector extends WizardPage {

    /** Creates new form FileSelector */
    public FileSelector() {
        initComponents();
    }

    public static final String getDescription() {
        return "Select the file";
    }

    /**
     * Validate the contents of the input.
     * The user must provide a file name.
     * @param component
     * @param o
     * @return Help message or null (if contents are valid).
     */
    @Override
    protected String validateContents (Component component, Object o) {
        if (fileName.getText().isEmpty()) {
            return "You must provide a file name";
        } else {
            return null;
        }
    }

    /**
     * Display a file chooser window for the user to select a file to open.
     */
    @Action
    public void openFile() {
        JFileChooser infile = new JFileChooser();
        JFrame mainFrame = bouttime.mainview.BoutTimeApp.getApplication().getMainFrame();

        if (infile.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            fileName.setText(infile.getSelectedFile().getAbsolutePath());
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

        header1Label = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        fileLabel = new javax.swing.JLabel();
        fileName = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(FileSelector.class);
        header1Label.setText(resourceMap.getString("header1Label.text")); // NOI18N
        header1Label.setName("header1Label"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        fileLabel.setText(resourceMap.getString("fileLabel.text")); // NOI18N
        fileLabel.setName("fileLabel"); // NOI18N

        fileName.setText(resourceMap.getString("fileName.text")); // NOI18N
        fileName.setName("fileName"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getActionMap(FileSelector.class, this);
        browseButton.setAction(actionMap.get("openFile")); // NOI18N
        browseButton.setText(resourceMap.getString("browseButton.text")); // NOI18N
        browseButton.setName("browseButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                    .addComponent(header1Label)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileName, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(browseButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header1Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileLabel)
                    .addComponent(fileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton))
                .addContainerGap(167, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JTextField fileName;
    private javax.swing.JLabel header1Label;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables

}
