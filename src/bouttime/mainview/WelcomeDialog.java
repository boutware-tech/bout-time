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

package bouttime.mainview;

import org.jdesktop.application.Action;

/**
 * A class to present a welcome screen before the main application starts.
 */
public class WelcomeDialog extends javax.swing.JDialog {

    /** Creates new form WelcomeDialog */
    public WelcomeDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setUndecorated(true);
        initComponents();
        this.setLocationRelativeTo(parent);

        this.initAction = BoutTimeView.BOUTTIMEVIEW_INIT_ACTION_OPEN_EXISTING;
        this.openRadioButton.setSelected(true);

        this.setVisible(true);
    }

    @Action
    public void okButton() {
        if (this.startRadioButton.isSelected()) {
            this.initAction = BoutTimeView.BOUTTIMEVIEW_INIT_ACTION_OPEN_NEW;
        } else if (this.openRadioButton.isSelected()) {
            this.initAction = BoutTimeView.BOUTTIMEVIEW_INIT_ACTION_OPEN_EXISTING;
        } else {
            this.initAction = BoutTimeView.BOUTTIMEVIEW_INIT_ACTION_QUIT;
        }
        
        this.setVisible(false);
    }

    public int getInitAction() { return this.initAction; }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel imageLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        openRadioButton = new javax.swing.JRadioButton();
        startRadioButton = new javax.swing.JRadioButton();
        exitRadioButton = new javax.swing.JRadioButton();
        okButton = new javax.swing.JButton();
        subtitleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getResourceMap(WelcomeDialog.class);
        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(0, 0, 0)));
        jPanel1.setName("jPanel1"); // NOI18N

        imageLabel.setIcon(resourceMap.getIcon("imageLabel.icon")); // NOI18N
        imageLabel.setName("imageLabel"); // NOI18N

        titleLabel.setFont(resourceMap.getFont("titleLabel.font")); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText(resourceMap.getString("titleLabel.text")); // NOI18N
        titleLabel.setName("titleLabel"); // NOI18N

        openRadioButton.setBackground(resourceMap.getColor("startRadioButton.background")); // NOI18N
        buttonGroup.add(openRadioButton);
        openRadioButton.setFont(resourceMap.getFont("openRadioButton.font")); // NOI18N
        openRadioButton.setSelected(true);
        openRadioButton.setText(resourceMap.getString("openRadioButton.text")); // NOI18N
        openRadioButton.setName("openRadioButton"); // NOI18N

        startRadioButton.setBackground(resourceMap.getColor("startRadioButton.background")); // NOI18N
        buttonGroup.add(startRadioButton);
        startRadioButton.setFont(resourceMap.getFont("startRadioButton.font")); // NOI18N
        startRadioButton.setText(resourceMap.getString("startRadioButton.text")); // NOI18N
        startRadioButton.setName("startRadioButton"); // NOI18N

        exitRadioButton.setBackground(resourceMap.getColor("startRadioButton.background")); // NOI18N
        buttonGroup.add(exitRadioButton);
        exitRadioButton.setFont(resourceMap.getFont("exitRadioButton.font")); // NOI18N
        exitRadioButton.setText(resourceMap.getString("exitRadioButton.text")); // NOI18N
        exitRadioButton.setName("exitRadioButton"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(bouttime.mainview.BoutTimeApp.class).getContext().getActionMap(WelcomeDialog.class, this);
        okButton.setAction(actionMap.get("okButton")); // NOI18N
        okButton.setText(resourceMap.getString("okButton.text")); // NOI18N
        okButton.setName("okButton"); // NOI18N

        subtitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        subtitleLabel.setText(resourceMap.getString("subtitleLabel.text")); // NOI18N
        subtitleLabel.setName("subtitleLabel"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(exitRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(subtitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addComponent(openRadioButton)
                    .addComponent(startRadioButton))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGap(2, 2, 2)
                        .addComponent(subtitleLabel)
                        .addGap(18, 18, 18)
                        .addComponent(openRadioButton)
                        .addGap(18, 18, 18)
                        .addComponent(startRadioButton)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                                .addComponent(okButton))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(exitRadioButton))))
                    .addComponent(imageLabel))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JRadioButton exitRadioButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton okButton;
    private javax.swing.JRadioButton openRadioButton;
    private javax.swing.JRadioButton startRadioButton;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

    private int initAction;
}
