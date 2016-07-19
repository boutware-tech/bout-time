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

package bouttime.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import org.apache.log4j.Logger;

/**
 * A class to model a Group entity.
 * This is a group of wrestlers.
 */
public class Group {
    static Logger logger = Logger.getLogger(Group.class);

    public static final String BRACKET_TYPE_VARIABLE = "VAR";
    public static final String BRACKET_TYPE_ROUNDROBIN = "RR";
    public static final String BRACKET_TYPE_2MAN = "2";
    public static final String BRACKET_TYPE_4MAN_DOUBLE = "4D";
    public static final String BRACKET_TYPE_4MAN_SINGLE = "4S";
    public static final String BRACKET_TYPE_8MAN_DOUBLE = "8D";
    public static final String BRACKET_TYPE_8MAN_SINGLE = "8S";
    public static final String BRACKET_TYPE_16MAN_DOUBLE = "16D";
    public static final String BRACKET_TYPE_16MAN_SINGLE = "16S";
    public static final String BRACKET_TYPE_32MAN_DOUBLE = "32D";
    public static final String BRACKET_TYPE_32MAN_SINGLE = "32S";

    private String          name;
    private String          mat;
    private String          session;
    private String          boutTime;
    private String          ageDivision;
    private String          classification;
    private String          weightClass;
    private List<Wrestler>  wrestlers;
    private List<Bout>      bouts;
    private int             numWrestlers;
    private int             numBouts;
    private int             numRounds;
    private Integer         id;
    private boolean         locked;
    private String          bracketType;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////
    public Group(List<Wrestler> list) {
        locked = false;
        wrestlers = list;
        setGroupStrings();

        // Set group and initial seeding values for wrestlers
        Integer seed = Integer.valueOf(1);
        for (Wrestler w : list) {
            w.setGroup(this);
            w.setSeed(seed);
            seed++;
        }

        mat = "";
        session = "";
        boutTime = "";
        numRounds = 0;  // initialize, should be set later (by boutmaker)
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Standard getters and setters
    ////////////////////////////////////////////////////////////////////////////

    public String getBoutTime() {return boutTime;}
    public void setBoutTime(String s) {
        if (!this.locked) {
            this.boutTime = s;
        } else {
            logger.warn("LOCKED : Unable to set time for group [" + this.toString() + "]");
        }
    }

    public int getNumBouts() {
        int count = 0;

        if (this.bouts == null) {
            return 0;
        }
        
        for (Bout b : this.bouts) {
            if (!b.isBye()) {
                count++;
            }
        }
        return count;
    }

    public int getNumRounds() {return numRounds;}
    public void setNumRounds(int rounds) {this.numRounds = rounds;}

    public String getBracketType() {return bracketType;}
    public void setBracketType(String b) {this.bracketType = b;}

    public String getMat() {return mat;}
    public void setMat(String s) {
        if (!this.locked) {
            this.mat = s;
        } else {
            logger.warn("LOCKED : Unable to set mat for group [" + this.toString() + "]");
        }
    }

    public String getName() {return this.toString();}

    public String getSession() {return session;}
    public void setSession(String s) {
        if (!this.locked) {
            this.session = s;
        } else {
            logger.warn("LOCKED : Unable to set session for group [" + this.toString() + "]");
        }
    }

    public int getNumWrestlers() {return wrestlers.size();}

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public boolean isLocked() {return this.locked;}
    public void setLocked(boolean b) {this.locked = b;}

    public List<Wrestler> getWrestlers() {return wrestlers;}
    public void setWrestlers(List<Wrestler> wList) {this.wrestlers = wList;}

    public void addWrestler(Wrestler w) {
        this.wrestlers.add(w);
        w.setSeed(Integer.valueOf(this.wrestlers.size()));
        w.setGroup(this);
    }

    public void addWrestlers(List<Wrestler> wList) {
        for (Wrestler w : wList) {
            addWrestler(w);
        }
    }

    public void removeWrestler(Wrestler w) {
        this.wrestlers.remove(w);
        w.setSeed(Integer.valueOf(0));
    }

    public void removeWresters(List<Wrestler> wList) {
        for (Wrestler w : wList) {
            removeWrestler(w);
        }
    }

    public List<Bout> getBouts() {return getBouts(false);}
    public void setBouts(List<Bout> bList) {this.bouts = bList;}
    public List<Bout> getBouts(boolean includeByes) {
        List<Bout> list;
        if (includeByes || (this.bouts == null)) {
            return this.bouts;
        } else {
            list = new ArrayList<Bout>();
            for (Bout b : this.bouts) {
                if (!b.isBye()) {
                    list.add(b);
                }
            }

            return list;
        }
    }

    public List<Bout> getBoutsByRound(String round) {
        return getBoutsByRound(round, false);
    }

    public List<Bout> getBoutsByRound(String round, boolean includeByes) {
        List<Bout> list = new ArrayList<Bout>();
        for (Bout b : this.bouts) {
            if (b.getRound().equalsIgnoreCase(round)) {
                if (includeByes || !b.isBye()) {
                    list.add(b);
                }
            }
        }

        return list;
    }

    public Bout getBout(String round, int sequence) {
        List<Bout> list = getBoutsByRound(round, true);
        if (list.isEmpty()) {
            return null;
        }

        for (Bout b : list) {
            if (b.getSequence() == sequence) {
                return b;
            }
        }

        return null;
    }

    public String getWeightClass() {
        setWeightClass();
        return weightClass;
    }
    
    /**
     * Set a string to the weightclasses of the wrestlers in this group.
     * Should be something like :
     *     <li>45
     *     <li>55-66
     *     <li>120-Hwt
     */
    private void setWeightClass() {
        // Can't assume all weight classes are integers.
        // We'd also like to sort integer weight classes by integers (vs. strings).
        // So create 2 sorted TreeSets, which are sorted and contain
        // unique members by default.
        TreeSet<Integer> intWeights = new TreeSet<Integer>();
        TreeSet<String> strWeights = new TreeSet<String>();
        StringBuilder label = new StringBuilder();
        int i;
        int num;
        
        for (Wrestler w : this.wrestlers) {
            try {
                num = Integer.parseInt(w.getWeightClass());
                intWeights.add(Integer.valueOf(num));
            } catch (NumberFormatException nfe) {
                strWeights.add(w.getWeightClass());
            }
        }
        
        i = 0;
        for (Integer w : intWeights) {
            if (i != 0)
                label.append("-");
            
            label.append(w);
            i++;
        }
        
        for (String w : strWeights) {
            if (i != 0)
                label.append("-");
            
            label.append(w);
            i++;
        }
        
        this.weightClass = label.toString();
    }


    public String getAgeDivision() {
        setAgeDivision();
        return ageDivision;
    }

    /**
     * Set a string to the age divisions of the wrestlers in this group.
     * Should be something like :
     *     <li>1
     *     <li>1-2
     *     <li>1-Tot
     */
    private void setAgeDivision() {
        TreeSet<String> strDivs = new TreeSet<String>();
        StringBuilder label = new StringBuilder();
        int i;
        
        for (Wrestler w : this.wrestlers) {
            strDivs.add(w.getAgeDivision());
        }
        
        i = 0;
        for (String s : strDivs) {
            if (i != 0)
                label.append("-");
            
            label.append(s);
            i++;
        }
        
        this.ageDivision = label.toString();
    }

    public String getClassification() {
        setClassification();
        return classification;
    }
    
    /**
     * Set a string to the classifications of the wrestlers in this group.
     * Should be something like :
     *     <li>Open
     *     <li>Open-Rookie
     */
    private void setClassification() {
        TreeSet<String> strClasses = new TreeSet<String>();
        StringBuilder label = new StringBuilder();
        int i;
        
        for (Wrestler w : this.wrestlers) {
            strClasses.add(w.getClassification());
        }
        
        i = 0;
        for (String s : strClasses) {
            if (i != 0)
                label.append("-");
            
            label.append(s);
            i++;
        }
        
        this.classification = label.toString();
    }

    private void setGroupStrings() {
        setWeightClass();
        setAgeDivision();
        setClassification();
    }

    /**
     * A String representation of the object.
     * @return Something like :
     * <li> Rookie:1:55-66
     * <li> Open:2-3:95-100
     * <li> Open-Rookie:4:88
     * 
     */
    @Override
    public String toString() {
        StringBuilder label = new StringBuilder();
        
        setGroupStrings();
        
        label.append(getClassification());
        if (label.length() > 0) {
            label.append(":");
        }
        label.append(getAgeDivision());
        if (label.length() > 0) {
            label.append(":");
        }
        label.append(getWeightClass());
        
        return label.toString();
    }

    /**
     * Get the wrestler object with the given seed value.
     * @param seed The seed value to find the wrestler at.
     * @return The wrestler object on success, null on error.
     */
    public Wrestler getWrestlerAtSeed(int seed) {
        for (Wrestler w : this.wrestlers) {
            if (w.getSeed() == seed) {
                return w;
            }
        }

        // No wrestler found with the given seed value
        return null;
    }
    
}
