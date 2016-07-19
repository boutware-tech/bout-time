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

import bouttime.dao.Dao;
import java.util.Map;

/**
 * This interface is for retrieving Wrestler records from an input file.
 * The format of the input file could be text, xml, Excel spreadsheet, or other.
 */
public interface FileInput {

    /**
     * Get input from the given file and store it in the given data access object.
     * @param dao
     * @return FileInputResult
     */
    public FileInputResult getInputFromFile(Map config, Dao dao);
}
