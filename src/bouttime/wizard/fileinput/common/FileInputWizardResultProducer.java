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

import bouttime.dao.Dao;
import bouttime.fileinput.FileInput;
import bouttime.fileinput.FileInputResult;
import java.util.List;
import java.util.Map;
import org.netbeans.spi.wizard.Summary;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage;

/**
 * This class implements the {@link WizardPage.WizardResultProducer} interface
 * for the application's file input wizards.
 */
public class FileInputWizardResultProducer implements WizardPage.WizardResultProducer {

    private Dao dao;
    private FileInput fileInput;

    public FileInputWizardResultProducer(FileInput fi, Dao dao) {
        this.fileInput = fi;
        this.dao = dao;
    }

    /**
     * Process the user input for the wizard.
     * @param data Map of the user-entered data.
     * @return A summary object for the wizard to display.
     * @throws WizardException
     */
    public Object finish(Map data) throws WizardException {
        FileInputResult result = fileInput.getInputFromFile(data, dao);

        if (result == null) {
            return processError(data);
        }
        
        return processResult(result);
    }

    /**
     * Nothing to do, just return true.
     * @param data Map of the user-entered data.
     * @return True.
     */
    public boolean cancel(Map data) {
        return true;
    }

    /**
     * Create a Summary object for an error.
     * @param data Map of the user-entered data.
     * @return A summary object for the wizard to display.
     */
    private Object processError(Map data) {
        String [] items = new String [] {
            "Error occurred: " + (String)data.get("error")
        };

        return Summary.create(items, null);
    }

    /**
     * Process the file input operation and create a Summary object.
     * @param result FileInputResult object from the wizard.
     * @return A summary object for the wizard to display.
     */
    private Object processResult(FileInputResult result) {
        int numItems = 3;

        List<String> rejects = result.getRejects();
        if (!rejects.isEmpty()) {
            numItems += rejects.size() + 1;
        }

        String [] items = new String [numItems];
        items[0] = String.format("%s: %s", "Records Processed", result.getRecordsProcessed());
        items[1] = String.format("%s: %s", "Records Accepted", result.getRecordsAccepted());
        items[2] = String.format("%s: %s", "Records Rejected", result.getRecordsRejected());

        if (!rejects.isEmpty()) {
            items[3] = "Rejects";
            int i = 4;
            for(String s : rejects) {
                items[i] = String.format("  %s", s);
                i++;
            }
        }

        return Summary.create(items, null);
    }
}
