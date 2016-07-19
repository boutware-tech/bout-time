/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2010  Jeffrey K. Rutt
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

package bouttime.gui.panel;

import bouttime.dao.Dao;
import bouttime.mainview.BoutTimeView;
import bouttime.stat.ThreeColumnStat;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A class to display the wrestlers by both classification and
 * age division with a table.
 */
public class WrestlersByClassDiv extends ThreeColumnStatPanel {

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    /**
     * No-arg constructor to make netBeans GUI builder happy.  In order to
     * add this class to the Palette, you are required to have a no-arg
     * constructor.  There is no other need for this, so don't use it.
     * @deprecated
     */
    public WrestlersByClassDiv() {this(null);}

    public WrestlersByClassDiv(BoutTimeView view) {
        super(view);

        this.table.getColumnModel().getColumn(0).setHeaderValue("Class");
        this.table.getColumnModel().getColumn(1).setHeaderValue("Division");
        this.table.getColumnModel().getColumn(2).setHeaderValue("Wrestlers");
    }

    ////////////////////////////////////////////////////////////////////////////
    // Class Methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Update the statistics to display in the table.
     */
    public void updateStats() {
        if (this.view == null) {
            return;
        }

        Dao dao = this.view.getDao();
        if (!dao.isOpen()) {
            return;
        }

        // Use a LinkedHashSet to maintain order from how user entered it
        Set<String> sSet = new LinkedHashSet<String>();

        this.list.clear();

        String classValues = dao.getClassificationValues();
        String divValues = dao.getAgeDivisionValues();

        if ((classValues != null) && !classValues.isEmpty() &&
                (divValues != null) && !divValues.isEmpty()) {

            String [] classTokens = classValues.split(",");
            String [] divTokens = divValues.split(",");
            for (String clas : classTokens) {
                clas = clas.trim();
                for (String div : divTokens) {
                    // Add to a Set to ensure no duplicates
                    sSet.add(String.format("%s:%s", clas, div.trim()));
                }
            }

            for (String s : sSet) {
                ThreeColumnStat stat = new ThreeColumnStat();
                String [] tokens = s.split(":");
                stat.setColumnOne(tokens[0]);
                stat.setColumnTwo(tokens[1]);
                stat.setColumnThree(Integer.toString(dao.getWrestlersByClassDiv(tokens[0], tokens[1]).size()));
                this.list.add(stat);
            }
        }
    }

}
