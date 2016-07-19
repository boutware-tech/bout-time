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

package bouttime.dao;

import bouttime.dao.xml.*;
import org.apache.log4j.Logger;

/**
 * A class to get the object that implements the {@link Dao} interface.
 */
public class DaoFactory {
    static Logger logger = Logger.getLogger(DaoFactory.class);
    /**
     * String to specify use of the XML file data store.
     */
    public static final String DAO_XML="XML";

    /**
     * Get the Dao object of the given type.
     * @param daoType Type of the Dao to retrieve.  Currently only supporting
     * {@link DAO_XML}.
     * @return Object that implements the Dao interface
     */
    public static Dao getDao(String daoType) {
        Dao dao = null;
        if (daoType.equalsIgnoreCase(DAO_XML)) {
            dao = new XmlDao();
        } else {
            logger.error("Illegal DAO type [" + daoType + "]");
        }

        return dao;
    }

}
