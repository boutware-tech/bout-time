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

package bouttime.wizard.fileinput.text;

import bouttime.dao.Dao;
import bouttime.fileinput.TextFileInput;
import bouttime.fileinput.TextFileInputConfig;
import bouttime.wizard.fileinput.common.*;
import org.netbeans.api.wizard.WizardDisplayer;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;

/**
 * A class to implement a wizard for receiving Wrestler data from a text file.
 */
public class TextInputWizard {
    
    private Dao dao;

    public TextInputWizard(Dao dao) {
        this.dao = dao;
    }

    /**
     * Run the wizard.
     */
    public void runWizard() {
        TextFileInputConfig config = this.dao.getTextFileInputConfig();
        WizardPage[] pages = new WizardPage [] {
            new ColumnSelector(config),
            new FieldSeparatorSelector(config),
            new FileSelector()
        };

        TextFileInput fi = new TextFileInput();
        FileInputWizardResultProducer rp = new FileInputWizardResultProducer(fi, dao);
        Wizard wizard = WizardPage.createWizard(pages, rp);
        WizardDisplayer.showWizard(wizard);
    }
}
