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

import bouttime.dao.Dao;
import bouttime.fileinput.ExcelFileInput;
import bouttime.fileinput.ExcelFileInputConfig;
import bouttime.wizard.fileinput.common.ColumnSelector;
import bouttime.wizard.fileinput.common.FileInputWizardResultProducer;
import bouttime.wizard.fileinput.common.FileSelector;
import org.netbeans.api.wizard.WizardDisplayer;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;

/**
 * A class to implement a wizard for receiving Wrestler data from a MS Excel
 * formatted file.
 */
public class ExcelInputWizard {

    private Dao dao;

    public ExcelInputWizard(Dao dao) {
        this.dao = dao;
    }

    /**
     * Run the wizard.
     */
    public void runWizard() {
        ExcelFileInputConfig config = this.dao.getExcelFileInputConfig();
        WizardPage [] pages = new WizardPage [] {
            new ColumnSelector(config),
            new SheetRowSelector(config),
            new FileSelector()
        };

        ExcelFileInput fi = new ExcelFileInput();
        FileInputWizardResultProducer rp = new FileInputWizardResultProducer(fi, dao);
        Wizard wizard = WizardPage.createWizard(pages, rp);
        WizardDisplayer.showWizard(wizard);
    }
}
