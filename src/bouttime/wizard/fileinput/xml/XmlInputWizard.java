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

import bouttime.dao.Dao;
import bouttime.fileinput.XMLFileInput;
import bouttime.fileinput.XMLFileInputConfig;
import bouttime.wizard.fileinput.common.FileInputWizardResultProducer;
import bouttime.wizard.fileinput.common.FileSelector;
import java.awt.Rectangle;
import org.netbeans.api.wizard.WizardDisplayer;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;

/**
 * A class to implement a wizard for receiving Wrestler data from a XML
 * formatted file.
 */
public class XmlInputWizard {

    private Dao dao;

    public XmlInputWizard(Dao dao) {
        this.dao = dao;
    }

    /**
     * Run the wizard.
     */
    public void runWizard() {
        XMLFileInputConfig config = this.dao.getXmlFileInputConfig();
        WizardPage [] pages = new WizardPage [] {
            new XmlElementStringSelector(config),
            new XmlPropertyStringSelector(config),
            new FileSelector()
        };

        XMLFileInput fi = new XMLFileInput();
        FileInputWizardResultProducer rp = new FileInputWizardResultProducer(fi, dao);
        Wizard wizard = WizardPage.createWizard(pages, rp);
        WizardDisplayer.showWizard(wizard, new Rectangle(800, 500));
    }
}
