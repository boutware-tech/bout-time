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
 * A generic class for statistics in 3 columns.
 * Mainly for use with {@link bouttime.gui.panel.ThreeColumnStatPanel}
 */
public class ThreeColumnStat {
    private String columnOne;
    private String columnTwo;
    private String columnThree;

    public ThreeColumnStat() {
    }

    public String getColumnOne() {
        return columnOne;
    }

    public String getColumnThree() {
        return columnThree;
    }

    public String getColumnTwo() {
        return columnTwo;
    }

    public void setColumnOne(String columnOne) {
        this.columnOne = columnOne;
    }

    public void setColumnThree(String columnThree) {
        this.columnThree = columnThree;
    }

    public void setColumnTwo(String columnTwo) {
        this.columnTwo = columnTwo;
    }
}
