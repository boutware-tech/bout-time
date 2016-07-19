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
 * A class to describe the file input configuration for XML file input service.
 */
public class XMLFileInputConfig extends FileInputConfig {

    private String root;
    private String wrestler;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    public XMLFileInputConfig() {}

    ////////////////////////////////////////////////////////////////////////////
    // Standard getters and setters
    ////////////////////////////////////////////////////////////////////////////

    public String getRoot() {return root;}
    public void setRoot(String s) {this.root = s;}

    public String getWrestler() {return wrestler;}
    public void setWrestler(String s) {this.wrestler = s;}


}
