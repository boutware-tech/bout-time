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

package bouttime.stat;

/**
 * A generic class for statistics in 2 columns.
 * Mainly for use with {@link bouttime.gui.panel.TwoColumnStatPanel}
 */
public class TwoColumnStat {
    private String columnOne;
    private String columnTwo;

    public TwoColumnStat() {
    }

    public String getColumnOne() {
        return columnOne;
    }

    public String getColumnTwo() {
        return columnTwo;
    }

    public void setColumnOne(String columnOne) {
        this.columnOne = columnOne;
    }

    public void setColumnTwo(String columnTwo) {
        this.columnTwo = columnTwo;
    }
}
