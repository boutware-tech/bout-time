/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2013  Jeffrey K. Rutt
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

import org.apache.log4j.Logger;

/**
 * A class to describe generic file input configuration.
 */
public class FileInputConfig {
    static Logger logger = Logger.getLogger(FileInputConfig.class);

    private String firstName;
    private String lastName;
    private String teamName;
    private String geo;
    private String classification;
    private String division;
    private String weightClass;
    private String actualWeight;
    private String serialNumber;
    private String level;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    public FileInputConfig() {}

    ////////////////////////////////////////////////////////////////////////////
    // Standard getters and setters
    ////////////////////////////////////////////////////////////////////////////
    
    public String getActualWeight() {return actualWeight;}
    public void setActualWeight(String s) {this.actualWeight = s;}

    public String getClassification() {return classification;}
    public void setClassification(String s) {this.classification = s;}

    public String getDivision() {return division;}
    public void setDivision(String s) {this.division = s;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String s) {this.firstName = s;}

    public String getLastName() {return lastName;}
    public void setLastName(String s) {this.lastName = s;}

    public String getLevel() {return level;}
    public void setLevel(String s) {this.level = s;}

    public String getSerialNumber() {return serialNumber;}
    public void setSerialNumber(String s) {this.serialNumber = s;}

    public String getTeamName() {return teamName;}
    public void setTeamName(String s) {this.teamName = s;}

    public String getGeo() {return geo;}
    public void setGeo(String s) {this.geo = s;}

    public String getWeightClass() {return weightClass;}
    public void setWeightClass(String s) {this.weightClass = s;}
}
