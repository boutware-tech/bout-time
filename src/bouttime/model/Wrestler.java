/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2012  Jeffrey K. Rutt
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

package bouttime.model;

/**
 * A class to model a Wrestler entity.
 */
public class Wrestler {
    private String  firstName;
    private String  lastName;
    private String  teamName;
    private String  geo;
    private String  classification;
    private String  ageDivision;
    private String  weightClass;
    private String  actualWeight;
    private Integer seed;
    private boolean seedSet;
    private String  level;
    private Integer id;
    private String  serialNumber;
    private Integer place;
    private Group   group;
    private String  comment;
    private boolean flagged;
    private static final int PRIMENO = 37;  // used for hash code

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////
    public Wrestler() {this("", "", "", "", "", "", "");}

    public Wrestler(String ln, String fn, String tn, String r, String d,
            String wc, String l) {
        this.lastName = ln;
        this.firstName = fn;
        this.teamName = tn;
        this.classification = r;
        this.ageDivision = d;
        this.weightClass = wc;
        this.actualWeight = null;
        this.level = l;
        this.seed = 0;
        this.serialNumber = null;
        this.group = null;
        this.place = null;
        this.comment = null;
        this.flagged = false;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Standard getters and setters
    ////////////////////////////////////////////////////////////////////////////

    public String getActualWeight() {return actualWeight;}
    public void setActualWeight(String s) {this.actualWeight = s;}

    public String getSerialNumber() {return serialNumber;}
    public void setSerialNumber(String serialNumber) {this.serialNumber = serialNumber;}

    public String getAgeDivision() {return ageDivision;}
    public void setAgeDivision(String s) {this.ageDivision = s;}
    
    public String getClassification() {return classification;}
    public void setClassification(String s) {this.classification = s;}
    
    public String getFirstName() {return firstName;}
    public void setFirstName(String s) {this.firstName = s;}
    
    public Integer getId() {return id;}
    public void setId(Integer i) {this.id = i;}

    public String getLastName() {return lastName;}
    public void setLastName(String s) {this.lastName = s;}
    
    public String getLevel() {return level;}
    public void setLevel(String s) {this.level = s;}
    
    public Integer getSeed() {return seed;}
    public void setSeed(Integer i) {this.seed = i;}
    
    public boolean isSeedSet() {return seedSet;}
    public void setSeedSet(boolean b) {this.seedSet = b;}
    
    public String getTeamName() {return teamName;}
    public void setTeamName(String s) {this.teamName = s;}
    
    public String getGeo() {return geo;}
    public void setGeo(String s) {this.geo = s;}
    
    public String getWeightClass() {return weightClass;}
    public void setWeightClass(String s) {this.weightClass = s;}
    
    public Integer getPlace() {return place;}
    public void setPlace(Integer i) {this.place = i;}
    
    public Group getGroup() {return group;}
    public void setGroup(Group g) {this.group = g;}

    public String getComment() {return comment;}
    public void setComment(String s) {this.comment = s;}

    /**
     * @deprecated Use isScratched()
     */
    public boolean isFlagged() {return flagged;}
    public boolean isScratched() {return flagged;}
    /**
     * @deprecated Use setScratched()
     */
    public void setFlagged(boolean b) {this.flagged = b;}
    public void setScratched(boolean b) {this.flagged = b;}

    /**
     * Return a short string that describes this wrestler.
     * @return String like : Wilson (100) River City Wrestling
     */
    public String getShortName() {
        return (String.format("%s (%s) %s", this.lastName, this.weightClass,
                this.teamName));
    }

    /**
     * Return a string to print on the bracket for this wrestler.
     * @return String like : John Wilson (3/100) River City Wrestling
     */
    public String getString4Bracket() {
        String ageWeightString = String.format("(%s/%s)", this.ageDivision, this.weightClass);
        String endString = ((this.ageDivision != null) && !this.ageDivision.isEmpty()) ?
                ageWeightString + " " + this.teamName : "(" + this.teamName + ")";
        String returnString = String.format("%s %s %s", this.firstName, this.lastName, endString);
        return returnString.trim();
    }

    /**
     * Return a string that is the first initial and last name.
     * @return String like : J. Wilson
     */
    public String getFirstInitialLastName() {
        if ((this.firstName != null) && !this.firstName.isEmpty() &&
                (this.lastName != null) && !this.lastName.isEmpty()) {
            String firstSubString = this.firstName.substring(0, 1);
            return String.format("%s. %s", firstSubString, this.lastName);
        } else {
            return " ";
        }
    }

    /**
     * Return a string that desribes the object.
     * @return String like : George Washington:Eagles:Varsity:5:150
     */
    @Override
    public String toString() {
        return (String.format("%s %s:%s:%s:%s:%s", this.firstName, this.lastName,
                this.teamName, this.classification, this.ageDivision, this.weightClass));
    }

    /**
     *  Create a hashCode.
     */
    @Override
    public int hashCode() {
        int value = 1;
        value = value*PRIMENO + (lastName == null ? 0 : lastName.hashCode());
        value = value*PRIMENO + (teamName == null ? 0 : teamName.hashCode());
        value = value*PRIMENO + (firstName == null ? 0 : firstName.hashCode());
        value = value*PRIMENO + (serialNumber == null ? 0 : serialNumber.hashCode());
        value = value*PRIMENO + (classification == null ? 0 : classification.hashCode());
        value = value*PRIMENO + (ageDivision == null ? 0 : ageDivision.hashCode());

        return value;
    }

    /**
     *  Wrestler objects are equal if lastName, firstName, teamName, serialNumber,
     *  classification, and ageDivision properties are the same.
     */
    @Override
    public boolean equals(Object other) {
        boolean bEqual = false;
        if (this == other) {
            bEqual = true;
        } else if (other instanceof Wrestler) {
            Wrestler that = (Wrestler) other;
            if ((lastName == null ? that.lastName == null : lastName.equalsIgnoreCase(that.lastName)) &&
                    (firstName == null ? that.firstName == null : firstName.equalsIgnoreCase(that.firstName)) &&
                    (teamName == null ? that.teamName == null : teamName.equalsIgnoreCase(that.teamName)) &&
                    (serialNumber == null ? that.serialNumber == null : serialNumber.equalsIgnoreCase(that.serialNumber)) &&
                    (classification == null ? that.classification == null : classification.equalsIgnoreCase(that.classification)) &&
                    (ageDivision == null ? that.ageDivision == null : ageDivision.equalsIgnoreCase(that.ageDivision))) {

                bEqual = true;
            }
        }

        return bEqual;
    }
}
