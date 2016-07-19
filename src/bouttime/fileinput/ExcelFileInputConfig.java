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

package bouttime.fileinput;

/**
 * A class to describe the file input configuration for MS Excel file input service.
 */
public class ExcelFileInputConfig extends FileInputConfig {

    private String sheet;
    private String startRow;
    private String endRow;
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    public ExcelFileInputConfig() {}

    ////////////////////////////////////////////////////////////////////////////
    // Standard getters and setters
    ////////////////////////////////////////////////////////////////////////////

    public String getEndRow() {return endRow;}
    public void setEndRow(String s) {this.endRow = s;}

    public String getSheet() {return sheet;}
    public void setSheet(String s) {this.sheet = s;}

    public String getStartRow() {return startRow;}
    public void setStartRow(String s) {this.startRow = s;}
}
