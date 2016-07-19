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

package bouttime.dao.xml;

import bouttime.configuration.DoOperation;
import bouttime.configuration.PositionOnPage;
import bouttime.dao.Dao;
import bouttime.fileinput.ExcelFileInputConfig;
import bouttime.fileinput.TextFileInputConfig;
import bouttime.fileinput.XMLFileInputConfig;
import bouttime.model.Bout;
import bouttime.model.Group;
import bouttime.model.Wrestler;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;

/**
 * A class that implements the data access interface (bouttime.dao.Dao) using
 * XML file operations for persistence.
 */
public class XmlDao implements Dao {
    public static final int PROMPT_FOR_OPERATION = 0;
    public static final int ALWAYS_DO_OPERATION = 1;
    public static final int NEVER_DO_OPERATION = 2;
    public static final int UPPER_RIGHT_POSITION = 0;
    public static final int CENTER_POSITION = 1;
    
    static Logger logger = Logger.getLogger(XmlDao.class);
    private Tournament tourney;
    private FileOps fileOps;
    
    public XmlDao() {
        this.tourney = null;
        this.fileOps = new FileOps();
    }
    
    public boolean openNew(String filename) {
        logger.trace("BEGIN");
        this.tourney = this.fileOps.openNew(filename);
        if (this.tourney != null) {
            logger.trace("END");
            return true;
        } else {
            logger.trace("END");
            return false;
        }
    }

    public boolean openExisting() {
        logger.trace("BEGIN");
        this.tourney = this.fileOps.openExisting();
        if (this.tourney != null) {
            logger.trace("END");
            return true;
        } else {
            logger.trace("END");
            return false;
        }
    }
    
    public boolean openExisting(String filename) {
        logger.trace("BEGIN");
        this.tourney = this.fileOps.openExisting(filename);
        if (this.tourney != null) {
            logger.trace("END");
            return true;
        } else {
            logger.trace("END");
            return false;
        }
    }
    
    public void close() {
        logger.trace("BEGIN");
        this.fileOps.close(this.tourney);
        this.tourney = null;
        logger.trace("END");
    }

    public boolean isOpen() {
        return this.fileOps.isOpen();
    }

    public void flush() {
        logger.trace("BEGIN");
        this.fileOps.flush(this.tourney);
        logger.trace("END");
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //  Data Access for Wrestler.class
    ////////////////////////////////////////////////////////////////////////////
    
    public Wrestler createWrestler(String ln, String fn, String tn, String r, String d,
            String wc, String l) {
        logger.trace("BEGIN");
        Wrestler w = new Wrestler(ln, fn, tn, r, d, wc, l);
        logger.debug("Created new wrestler : " + w);

        if (!this.tourney.addWrestler(w)) {
            logger.warn("Failed to add wrestler just created : " + w);
            w = null;
        }

        logger.trace("END");
        return w;
    }

    public boolean add(Wrestler w) {
        logger.trace("BEGIN");
        logger.debug("Adding wrestler : " + w);
        boolean b = this.tourney.addWrestler(w);
        logger.trace("END");
        return b;
    }

    public boolean addWrestler(Wrestler w) {
        logger.trace("BEGIN");
        logger.debug("Adding wrestler : " + w);
        boolean b = this.tourney.addWrestler(w);
        logger.trace("END");
        return b;
    }

    public boolean addWrestlerToGroup(Wrestler w, Group g) {
        logger.trace("BEGIN");
        logger.debug("Adding wrestler ( " + w + ") to group (" + g + ")");
        w.setSeed(Integer.valueOf(getWrestlersByGroup(g).size() + 1));
        w.setGroup(g);
        updateWrestler(w);

        updateGroup(g);

        logger.trace("END");
        return true;
    }

    public boolean removeWrestlerFromGroup(Wrestler w, Group g) {
        logger.trace("BEGIN");
        logger.debug("Removing wrestler ( " + w + ") from group (" + g + ")");
        w.setGroup(null);
        w.setSeed(0);
        w.setSeedSet(false);
        w.setPlace(null);
        g.removeWrestler(w);
        updateWrestler(w);

        updateGroup(g);

        logger.trace("END");
        return true;
    }

    public boolean updateWrestler(Wrestler w) {
        logger.trace("BEGIN");
        logger.trace("END");
        return true;    // do nothing
    }
    
    public boolean deleteWrestler(Wrestler w) {
        logger.trace("BEGIN");
        logger.debug("Deleting wrestler : " + w);
        Group g = w.getGroup();
        if (this.tourney.getAllWrestlers().remove(w)) {
            if (g != null) {
                List<Wrestler> wList = getWrestlersByGroup(g);
                if (wList.size() > 0) {
                    // update dynamic group information
                    // XXX something needed here?
                } else {
                    deleteGroup(g);
                }
            }
            logger.trace("END");
            return true;
        }

        logger.trace("END");
        return false;
    }

    public Wrestler getDummyWrestler() {
        logger.trace("BEGIN");
        logger.debug("Getting dummy wrestler");
        Wrestler w = this.tourney.getDummyWrestler();
        logger.trace("END");
        return w;
    }

    public boolean deleteWrestlers(List<Wrestler> wList) {
        logger.trace("BEGIN");
        logger.debug("Deleting wrestlers : " + wList);
        for (Wrestler w : wList) {
            deleteWrestler(w);
        }

        logger.trace("END");
        return true;
    }
    
    /**
     * Retrieve all wrestlers.
     */
    public List<Wrestler> getAllWrestlers() {
        logger.trace("BEGIN");
        List<Wrestler> list = this.tourney.getAllWrestlers();
        logger.trace("END");
        return list;
    }
    
    /**
     * Retrieve all wrestlers that belong to a given group.
     */
    public List<Wrestler> getWrestlersByGroup(Group group) {
        logger.trace("BEGIN");
        List<Wrestler> wList = new ArrayList<Wrestler>();

        for (Wrestler w : this.tourney.getAllWrestlers()) {
            Group g = w.getGroup();
            if (g == null) {
                continue;
            }
            
            if (g.equals(group)) {
                wList.add(w);
            }
        }

        logger.debug("Wrestlers in group (" + group + ") : " + wList);
        logger.trace("END");
        return wList;
    }
    
    /**
     * Retrieve all wrestlers that belong to a given classification.
     */
    public List<Wrestler> getWrestlersByClass(String cl) {
        logger.trace("BEGIN");
        if (cl == null) {
            logger.warn("null parameter : cl");
            return null;
        }

        List<Wrestler> wList = new ArrayList<Wrestler>();

        for (Wrestler w : this.tourney.getAllWrestlers()) {
            String wClass = w.getClassification();
            if (wClass == null) {
                continue;
            }
            if (wClass.equalsIgnoreCase(cl)) {
                wList.add(w);
            }
        }

        logger.trace("Number of wrestler in class (" + cl + ") : " + wList.size());
        logger.trace("END");
        return wList;
    }
    
    /**
     * Retrieve all wrestlers that belong to a given age division.
     */
    public List<Wrestler> getWrestlersByAgeDivision(String ageDiv) {
        logger.trace("BEGIN");
        if (ageDiv == null) {
            logger.warn("null parameter : ageDiv");
            return null;
        }

        List<Wrestler> wList = new ArrayList<Wrestler>();

        for (Wrestler w : this.tourney.getAllWrestlers()) {
            String wDiv = w.getAgeDivision();
            if (wDiv == null) {
                continue;
            }
            if (wDiv.equalsIgnoreCase(ageDiv)) {
                wList.add(w);
            }
        }

        logger.trace("Number of wrestler in div (" + ageDiv + ") : " + wList.size());
        logger.trace("END");
        return wList;
    }
    
    /**
     * Retrieve all wrestlers that belong to a given weight class.
     */
    public List<Wrestler> getWrestlersByWeightClass(String wtClass) {
        logger.trace("BEGIN");
        if (wtClass == null) {
            logger.warn("null parameter : wtClass");
            return null;
        }
        List<Wrestler> wList = new ArrayList<Wrestler>();

        for (Wrestler w : this.tourney.getAllWrestlers()) {
            String weightClass = w.getWeightClass();
            if (weightClass == null) {
                continue;
            }
            if (weightClass.equalsIgnoreCase(wtClass)) {
                wList.add(w);
            }
        }

        logger.trace("Number of wrestler in weight class (" + wtClass + ") : " + wList.size());
        logger.trace("END");
        return wList;
    }
    
    /**
     * Retrieve all wrestlers that belong to a given classification and
     * age division.
     */
    public List<Wrestler> getWrestlersByClassDiv(String cl, String ageDiv) {
        logger.trace("BEGIN");
        if ((cl == null) || (ageDiv == null)) {
            logger.warn("null parameter : cl=" + cl + " ageDiv=" + ageDiv);
            return null;
        }

        List<Wrestler> wList = new ArrayList<Wrestler>();

        for (Wrestler w : this.tourney.getAllWrestlers()) {
            String wClass = w.getClassification();
            String wDiv = w.getAgeDivision();
            if ((wClass == null) || (wDiv == null)) {
                continue;
            }
            
            if (wClass.equalsIgnoreCase(cl) && wDiv.equalsIgnoreCase(ageDiv)) {
                wList.add(w);
            }
        }

        logger.trace("Number of wrestler in class (" + cl + ") and div (" + ageDiv
                + ") : " + wList.size());
        logger.trace("END");
        return wList;
    }
    
    /**
     * Retrieve all wrestlers that belong to a given classification and
     * age division and weight class.
     */
    public List<Wrestler> getWrestlersByClassDivWeight(String cl, String ageDiv, String wtClass) {
        logger.trace("BEGIN");
        if ((cl == null) || (ageDiv == null) || (wtClass == null)) {
            logger.warn("null parameter : cl=" + cl + " ageDiv=" + ageDiv +
                    " wtClass=" + wtClass);
            return null;
        }

        List<Wrestler> wList = new ArrayList<Wrestler>();

        for (Wrestler w : this.tourney.getAllWrestlers()) {
            String wClass = w.getClassification();
            String wDiv = w.getAgeDivision();
            String weightClass = w.getWeightClass();
            if ((wClass == null) || (wDiv == null) || (weightClass == null)) {
                continue;
            }
            if ((wClass.equalsIgnoreCase(cl)) &&
                    (wDiv.equalsIgnoreCase(ageDiv)) &&
                    weightClass.equalsIgnoreCase(wtClass)) {
                wList.add(w);
            }
        }

        logger.trace("Number of wrestler in class (" + cl + ") and div (" + ageDiv
                + ") and weight (" + wtClass + ") : " + wList.size());
        logger.trace("END");
        return wList;
    }

    public List<Wrestler> getWrestlersByTeam(String team) {
        logger.trace("BEGIN");
        if (team == null) {
            logger.warn("null parameter : team");
            return null;
        }

        List<Wrestler> wList = new ArrayList<Wrestler>();

        for (Wrestler w : this.tourney.getAllWrestlers()) {
            String wTeam = w.getTeamName();
            if (wTeam == null) {
                continue;
            }
            if (wTeam.equalsIgnoreCase(team)) {
                wList.add(w);
            }
        }

        logger.trace("Number of wrestlers on team (" + team + ") : " + wList.size());
        logger.trace("END");
        return wList;
    }

    public List<Wrestler> getWrestlersByTeamClass(String team, String cla) {
        logger.trace("BEGIN");
        if ((team == null) || (cla == null)) {
            logger.warn("null parameter : team=" + team + " cla=" + cla);
            return null;
        }

        List<Wrestler> wList = new ArrayList<Wrestler>();

        for (Wrestler w : this.tourney.getAllWrestlers()) {
            String wTeam = w.getTeamName();
            String wClass = w.getClassification();
            if ((wTeam == null) || (wClass == null)) {
                continue;
            }
            if (wTeam.equalsIgnoreCase(team) && wClass.equalsIgnoreCase(cla)) {
                wList.add(w);
            }
        }

        logger.trace("Number of wrestlers on team (" + team + ") and in class (" + cla + ") : " + wList.size());
        logger.trace("END");
        return wList;
    }
    
    /**
     * Retrieve all wrestlers that are not assigned to a group.
     */
    public List<Wrestler> getUngroupedWrestlers() {
        logger.trace("BEGIN");
        List<Wrestler> wList = new ArrayList<Wrestler>();

        for (Wrestler w : this.tourney.getAllWrestlers()) {
            if (w.getGroup() == null) {
                wList.add(w);
            }
        }

        logger.trace("END");
        return wList;
    }

    /**
     * @deprecated Use getScratchedWrestlers()
     */
    public List<Wrestler> getFlaggedWrestlers() {
        logger.trace("BEGIN");
        List<Wrestler> wList = new ArrayList<Wrestler>();

        for (Wrestler w : this.tourney.getAllWrestlers()) {
            if (w.isFlagged()) {
                wList.add(w);
            }
        }

        logger.trace("END");
        return wList;
    }

    public List<Wrestler> getScratchedWrestlers() {
        logger.trace("BEGIN");
        List<Wrestler> wList = new ArrayList<Wrestler>();

        for (Wrestler w : this.tourney.getAllWrestlers()) {
            if (w.isScratched()) {
                wList.add(w);
            }
        }

        logger.trace("END");
        return wList;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //  Data Access for Group.class
    ////////////////////////////////////////////////////////////////////////////
    
    public Group createGroup(List<Wrestler> list) {
        logger.trace("BEGIN");
        Group g = new Group(list);
        
        if (!this.tourney.addGroup(g)) {
            g = null;
        }

        logger.trace("END");
        return g;
    }

    public boolean add(Group g) {
        logger.trace("BEGIN");
        boolean b = this.tourney.addGroup(g);
        logger.trace("END");
        return b;
    }

    public boolean addGroup(Group g) {
        logger.trace("BEGIN");
        boolean b = this.tourney.addGroup(g);
        logger.trace("END");
        return b;
    }

    public boolean updateGroup(Group g) {
        logger.trace("BEGIN");
        logger.trace("END");
        return true;    // do nothing
    }
    
    public boolean deleteGroup(Group g) {
        logger.trace("BEGIN");
        List<Wrestler> wList = getWrestlersByGroup(g);
        for(Wrestler w : wList) {
            w.setGroup(null);
            w.setSeed(0);
            w.setSeedSet(false);
            w.setPlace(null);
        }

        boolean b = this.tourney.getAllGroups().remove(g);
        logger.trace("END");
        return b;
    }

    public boolean deleteGroups(List<Group> gList) {
        logger.trace("BEGIN");
        for (Group g : gList) {
            List<Wrestler> wList = g.getWrestlers();
            for(Wrestler w : wList) {
                w.setGroup(null);
                w.setSeed(0);
            }

            List<Bout> bList = g.getBouts();
            if (bList != null) {
                for (Bout b : bList) {
                    deleteBout(b);
                }
            }

        }

        boolean b = this.tourney.getAllGroups().removeAll(gList);
        logger.trace("END");
        return b;
    }
    
    /**
     * Retrieve all groups.
     */
    public List<Group> getAllGroups() {
        logger.trace("BEGIN");
        List<Group> list = this.tourney.getAllGroups();
        logger.trace("END");
        return list;
    }

    /**
     * Retrieve all groups that have the given AgeDivision.
     */
    public List<Group> getGroupsByDiv(String div) {
        logger.trace("BEGIN");
        if (div == null) {
            logger.warn("null parameter : div");
            return null;
        }

        List<Group> gList = new ArrayList<Group>();

        for (Group g : this.tourney.getAllGroups()) {
            String gDiv = g.getAgeDivision();
            if (gDiv == null) {
                continue;
            }
            if (gDiv.equalsIgnoreCase(div)) {
                gList.add(g);
            }
        }

        logger.trace("END");
        return gList;
    }

    /**
     * Retrieve all groups that have the given mat.
     */
    public List<Group> getGroupsByMat(String mat) {
        logger.trace("BEGIN");
        if (mat == null) {
            logger.warn("null parameter : mat");
            return null;
        }

        List<Group> gList = new ArrayList<Group>();

        if (mat.isEmpty()) {
            return gList;
        }

        for (Group g : this.tourney.getAllGroups()) {
            String gMat = g.getMat();
            if (gMat == null) {
                continue;
            }
            if ((g.getMat() != null) && g.getMat().equalsIgnoreCase(mat)) {
                gList.add(g);
            }
        }

        logger.trace("END");
        return gList;
    }

    /**
     * Retrieve all groups that have the given session.
     */
    public List<Group> getGroupsBySession(String session) {
        logger.trace("BEGIN");
        if (session == null) {
            logger.warn("null parameter : session");
            return null;
        }

        List<Group> gList = new ArrayList<Group>();

        if ((session == null) || (session.isEmpty())) {
            return gList;
        }

        for (Group g : this.tourney.getAllGroups()) {
            String gSession = g.getSession();
            if (gSession == null) {
                continue;
            }
            if ((g.getSession() != null) && g.getSession().equalsIgnoreCase(session)) {
                gList.add(g);
            }
        }

        logger.trace("END");
        return gList;
    }

    /**
     * Retrieve all groups that have the given session and mat.
     */
    public List<Group> getGroupsBySessionMat(String session, String mat) {
        logger.trace("BEGIN");
        if ((session == null) || (mat == null)) {
            logger.warn("null parameter : session=" + session + " mat=" + mat);
            return null;
        }

        List<Group> gList = new ArrayList<Group>();

        for (Group g : this.tourney.getAllGroups()) {
            String gSession = g.getSession();
            String gMat = g.getMat();
            if ((gSession == null) || (gMat == null)) {
                continue;
            }
            if (gSession.equalsIgnoreCase(session) &&
                    gMat.equalsIgnoreCase(mat)) {
                gList.add(g);
            }
        }

        logger.trace("END");
        return gList;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //  Data Access for Bout.class
    ////////////////////////////////////////////////////////////////////////////

    public Bout createBout(Wrestler green, Wrestler red, Group g, String round,
            String label, Bout winNext, Bout loseNext) {
        logger.trace("BEGIN");
        // We're not explicitly persisting Bouts.
        // Bouts will get persisted along with the Group that they are
        // associated with.
        Bout b = new Bout(green, red, g, round, label, winNext, loseNext);
        logger.trace("END");
        return b;
    }

    public Bout createBout(Wrestler green, Wrestler red, Group g, String round,
            String label) {
        logger.trace("BEGIN");
        // We're not explicitly persisting Bouts.
        Bout b = new Bout(green, red, g, round, label);
        logger.trace("END");
        return b;
    }

    public boolean add(Bout b) {
        logger.trace("BEGIN");
        logger.trace("END");
        return true;    // do nothing, we're not explicitly persisting Bouts.
    }

    public boolean addBout(Bout b) {
        logger.trace("BEGIN");
        logger.trace("END");
        return true;    // do nothing, we're not explicitly persisting Bouts.
    }

    public boolean updateBout(Bout b) {
        logger.trace("BEGIN");
        logger.trace("END");
        return true;    // do nothing, we're not explicitly persisting Bouts.
    }
    
    public boolean deleteBout(Bout b) {
        logger.trace("BEGIN");
        logger.trace("END");
        return true;    // do nothing, we're not explicitly persisting Bouts.
    }
    
    /**
     * Retrieve all bouts.
     */
    public List<Bout> getAllBouts() {
        logger.trace("BEGIN");
        List<Bout> bList = new ArrayList<Bout>();
        List<Group> gList = this.getAllGroups();

        for(Group g : gList) {
            bList.addAll(g.getBouts());
        }

        logger.trace("END");
        return bList;
    }
    
    /**
     * Retrieve all bouts that belong to a given group.
     */
    public List<Bout> getBoutsByGroup(Group g) {
        logger.trace("BEGIN");
        List<Bout> list = g.getBouts();
        logger.trace("END");
        return list;
    }
    
    /**
     * Retrieve all bouts that belong to a given mat.
     */
    public List<Bout> getBoutsByMat(String mat) {
        logger.trace("BEGIN");
        List<Bout> bList = new ArrayList<Bout>();
        List<Group> gList = this.getAllGroups();

        for(Group g : gList) {
            if (g.getMat().equalsIgnoreCase(mat)) {
                bList.addAll(g.getBouts());
            }
        }

        logger.trace("END");
        return bList;
    }
    
    /**
     * Retrieve all bouts that belong to a given session.
     */
    public List<Bout> getBoutsBySession(String session) {
        logger.trace("BEGIN");
        List<Bout> bList = new ArrayList<Bout>();
        List<Group> gList = this.getAllGroups();

        for(Group g : gList) {
            if (g.getSession().equalsIgnoreCase(session)) {
                bList.addAll(g.getBouts());
            }
        }

        logger.trace("END");
        return bList;
    }

    /**
     * Retrieve all bouts that belong to a given mat and a given session.
     */
    public List<Bout> getBoutsByMatSession(String mat, String session) {
        logger.trace("BEGIN");
        if ((session == null) || (mat == null)) {
            logger.warn("null parameter : session=" + session + " mat=" + mat);
            return null;
        }

        List<Bout> bList = new ArrayList<Bout>();
        List<Group> gList = this.getAllGroups();

        for(Group g : gList) {
            String gSession = g.getSession();
            String gMat = g.getMat();
            if ((gSession == null) || (gMat == null)) {
                continue;
            }
            if (gSession.equalsIgnoreCase(session) &&
                    gMat.equalsIgnoreCase(mat)) {
                bList.addAll(g.getBouts());
            }
        }

        logger.trace("END");
        return bList;
    }

    ////////////////////////////////////////////////////////////////////////////
    //  Miscellaneous tournament data
    ////////////////////////////////////////////////////////////////////////////
    
    public String getName() {
        logger.trace("BEGIN");
        String s = this.tourney.getName();
        logger.trace("END");
        return s;
    }

    public void setName(String name) {
        logger.trace("BEGIN");
        this.tourney.setName(name);
        logger.trace("END");
    }

    public String getSite() {
        logger.trace("BEGIN");
        String s = this.tourney.getSite();
        logger.trace("END");
        return s;
    }

    public void setSite(String site) {
        logger.trace("BEGIN");
        this.tourney.setSite(site);
        logger.trace("END");
    }

    public String getCity() {
        logger.trace("BEGIN");
        String s = this.tourney.getCity();
        logger.trace("END");
        return s;
    }

    public void setCity(String city) {
        logger.trace("BEGIN");
        this.tourney.setCity(city);
        logger.trace("END");
    }

    public String getState() {
        logger.trace("BEGIN");
        String s = this.tourney.getState();
        logger.trace("END");
        return s;
    }

    public void setState(String state) {
        logger.trace("BEGIN");
        this.tourney.setState(state);
        logger.trace("END");
    }

    public String getMonth() {
        logger.trace("BEGIN");
        String s = this.tourney.getMonth();
        logger.trace("END");
        return s;
    }

    public void setMonth(String month) {
        logger.trace("BEGIN");
        this.tourney.setMonth(month);
        logger.trace("END");
    }

    public Integer getDay() {
        logger.trace("BEGIN");
        Integer i = this.tourney.getDay();
        logger.trace("END");
        return i;
    }

    public void setDay(Integer day) {
        logger.trace("BEGIN");
        this.tourney.setDay(day);
        logger.trace("END");
    }

    public Integer getYear() {
        logger.trace("BEGIN");
        Integer i = this.tourney.getYear();
        logger.trace("END");
        return i;
    }

    public void setYear(Integer year) {
        logger.trace("BEGIN");
        this.tourney.setYear(year);
        logger.trace("END");
    }

    public Boolean isFifthPlaceEnabled() {
        logger.trace("BEGIN");
        Boolean b = this.tourney.isFifthPlaceEnabled();
        logger.trace("END");
        return b;
    }

    public void setFifthPlaceEnabled(Boolean b) {
        logger.trace("BEGIN");
        this.tourney.setFifthPlaceEnabled(b);
        logger.trace("END");
    }

    public Boolean isSecondPlaceChallengeEnabled() {
        logger.trace("BEGIN");
        Boolean b = this.tourney.isSecondPlaceChallengeEnabled();
        logger.trace("END");
        return b;
    }

    public void setSecondPlaceChallengeEnabled(Boolean b) {
        logger.trace("BEGIN");
        this.tourney.setSecondPlaceChallengeEnabled(b);
        logger.trace("END");
    }

    public Boolean isRoundRobinEnabled() {
        logger.trace("BEGIN");
        Boolean b = this.tourney.isRoundRobinEnabled();
        logger.trace("END");
        return b;
    }

    public void setRoundRobinEnabled(Boolean b) {
        logger.trace("BEGIN");
        this.tourney.setRoundRobinEnabled(b);
        logger.trace("END");
    }

    public Integer getRoundRobinMax() {
        logger.trace("BEGIN");
        Integer i = this.tourney.getRoundRobinMax();
        logger.trace("END");
        return i;
    }

    public void setRoundRobinMax(Integer max) {
        logger.trace("BEGIN");
        this.tourney.setRoundRobinMax(max);
        logger.trace("END");
    }
    
    public String getMatValues() {
        logger.trace("BEGIN");
        String s = this.tourney.getMatValues();
        logger.trace("END");
        return s;
    }

    public void setMatValues(String s) {
        logger.trace("BEGIN");
        this.tourney.setMatValues(s);
        logger.trace("END");
    }

    public String getBoutTimeValues() {
        logger.trace("BEGIN");
        String s = this.tourney.getBoutTimeValues();
        logger.trace("END");
        return s;
    }

    public void setBoutTimeValues(String s) {
        logger.trace("BEGIN");
        this.tourney.setBoutTimeValues(s);
        logger.trace("END");
    }

    public String getClassificationValues() {
        logger.trace("BEGIN");
        String s = this.tourney.getClassificationValues();
        logger.trace("END");
        return s;
    }

    public void setClassificationValues(String s) {
        logger.trace("BEGIN");
        this.tourney.setClassificationValues(s);
        logger.trace("END");
    }

    public String getAgeDivisionValues() {
        logger.trace("BEGIN");
        String s = this.tourney.getAgeDivisionValues();
        logger.trace("END");
        return s;
    }

    public void setAgeDivisionValues(String s) {
        logger.trace("BEGIN");
        this.tourney.setAgeDivisionValues(s);
        logger.trace("END");
    }

    public String getSessionValues() {
        logger.trace("BEGIN");
        String s = this.tourney.getSessionValues();
        logger.trace("END");
        return s;
    }

    public void setSessionValues(String s) {
        logger.trace("BEGIN");
        this.tourney.setSessionValues(s);
        logger.trace("END");
    }

    public String getWeightClassValues() {
        logger.trace("BEGIN");
        String s = this.tourney.getWeightClassValues();
        logger.trace("END");
        return s;
    }

    public void setWeightClassValues(String s) {
        logger.trace("BEGIN");
        this.tourney.setWeightClassValues(s);
        logger.trace("END");
    }

    public ExcelFileInputConfig getExcelFileInputConfig() {
        logger.trace("BEGIN");
        ExcelFileInputConfig fic = this.tourney.getExcelFileInputConfig();
        logger.trace("END");
        return fic;
    }
    public void setExcelFileInputConfig(ExcelFileInputConfig c) {
        logger.trace("BEGIN");
        this.tourney.setExcelFileInputConfig(c);
        logger.trace("END");
    }

    public TextFileInputConfig getTextFileInputConfig() {
        logger.trace("BEGIN");
        TextFileInputConfig fic = this.tourney.getTextFileInputConfig();
        logger.trace("END");
        return fic;
    }

    public void setTextFileInputConfig(TextFileInputConfig c) {
        logger.trace("BEGIN");
        this.tourney.setTextFileInputConfig(c);
        logger.trace("END");
    }

    public XMLFileInputConfig getXmlFileInputConfig() {
        logger.trace("BEGIN");
        XMLFileInputConfig fic = this.tourney.getXmlFileInputConfig();
        logger.trace("END");
        return fic;
    }

    public void setXmlFileInputConfig(XMLFileInputConfig c) {
        logger.trace("BEGIN");
        this.tourney.setXmlFileInputConfig(c);
        logger.trace("END");
    }

    public Integer getMaxAward() {
        logger.trace("BEGIN");
        Integer i = this.tourney.getMaxAward();
        logger.trace("END");
        return i;
    }

    public void setMaxAward(Integer i) {
        logger.trace("BEGIN");
        this.tourney.setMaxAward(i);
        logger.trace("END");
    }

    public String getBoutsheetWatermarkValues() {
        logger.trace("BEGIN");
        String s = this.tourney.getBoutsheetWatermarkValues();
        logger.trace("END");
        return s;
    }

    public void setBoutsheetWatermarkValues(String s) {
        logger.trace("BEGIN");
        this.tourney.setBoutsheetWatermarkValues(s);
        logger.trace("END");
    }

    public String getBracketsheetWatermarkValues() {
        logger.trace("BEGIN");
        String s = this.tourney.getBracketsheetWatermarkValues();
        logger.trace("END");
        return s;
    }

    public void setBracketsheetWatermarkValues(String s) {
        logger.trace("BEGIN");
        this.tourney.setBracketsheetWatermarkValues(s);
        logger.trace("END");
    }
    
    public DoOperation getIncludeBracketsheetTimestamp() {
        logger.trace("BEGIN");
        DoOperation rv;
        
        int i = this.tourney.getIncludeBracketsheetTimestamp();
        switch (i) {
            case ALWAYS_DO_OPERATION:
                rv = DoOperation.ALWAYS;
                break;
            case NEVER_DO_OPERATION:
                rv = DoOperation.NEVER;
                break;
            default:
                rv = DoOperation.PROMPT;
                break;
        }
        
        logger.trace("END");
        return rv;
    }
    
    public void setIncludeBracketsheetTimestamp(DoOperation value) {
        logger.trace("BEGIN");
        int i;
        
        switch (value) {
            case ALWAYS:
                i = ALWAYS_DO_OPERATION;
                break;
            case NEVER:
                i = NEVER_DO_OPERATION;
                break;
            default:
                i = PROMPT_FOR_OPERATION;
                break;
        }
        
        this.tourney.setIncludeBracketsheetTimestamp(i);
        logger.trace("END");
    }
    
    public String getBracketsheetAwardImage() {
        return this.tourney.getBracketsheetAwardImage();
    }
    
    public void setBracketsheetAwardImage(String path) {
        this.tourney.setBracketsheetAwardImage(path);
    }
    
    public PositionOnPage getBracketsheetAwardImagePosition() {
        logger.trace("BEGIN");
        PositionOnPage rv;
        
        int i = this.tourney.getBracketsheetAwardImagePosition();
        switch (i) {
            case UPPER_RIGHT_POSITION:
                rv = PositionOnPage.UPPER_RIGHT;
                break;
            case CENTER_POSITION:
                rv = PositionOnPage.CENTER;
                break;
            default:
                rv = PositionOnPage.UPPER_RIGHT;
                break;
        }
        
        logger.trace("END");
        return rv;
    }
    
    public void setBracketsheetAwardImagePosition(PositionOnPage value) {
        logger.trace("BEGIN");
        int i;
        
        switch (value) {
            case CENTER:
                i = CENTER_POSITION;
                break;
            case UPPER_RIGHT:
                i = UPPER_RIGHT_POSITION;
                break;
            default:
                i = UPPER_RIGHT_POSITION;
                break;
        }
        
        this.tourney.setBracketsheetAwardImagePosition(i);
        logger.trace("END");
    }

    public List<String> getTeams() {
        logger.trace("BEGIN");
        Set<String> teamSet = new TreeSet<String>();

        for (Wrestler w : this.tourney.getAllWrestlers()) {
            teamSet.add(w.getTeamName());
        }

        List<String> teamList = new ArrayList<String>();

        for (String s : teamSet) {
            teamList.add(s);
        }

        logger.trace("END");
        return teamList;
    }
}
