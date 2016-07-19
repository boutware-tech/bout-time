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

package bouttime.dao;

import bouttime.configuration.DoOperation;
import bouttime.configuration.PositionOnPage;
import bouttime.fileinput.ExcelFileInputConfig;
import bouttime.fileinput.TextFileInputConfig;
import bouttime.fileinput.XMLFileInputConfig;
import bouttime.model.Wrestler;
import bouttime.model.Group;
import bouttime.model.Bout;
import java.util.List;

/**
 * An interface for accessing data in the persistent data store.
 */
public interface Dao {

    ////////////////////////////////////////////////////////////////////////////
    //  General interface operations
    ////////////////////////////////////////////////////////////////////////////
    public boolean openNew(String filename);
    public boolean openExisting();
    public void close();
    public void flush();
    public boolean isOpen();

    ////////////////////////////////////////////////////////////////////////////
    //  Data Access for Wrestler.class
    ////////////////////////////////////////////////////////////////////////////
    public Wrestler createWrestler(String ln, String fn, String tn, String r, String d,
            String wc, String l);
    public boolean add(Wrestler w);
    public boolean addWrestler(Wrestler w);
    public boolean addWrestlerToGroup(Wrestler w, Group g);
    public boolean removeWrestlerFromGroup(Wrestler w, Group g);
    public boolean updateWrestler(Wrestler w);
    public boolean deleteWrestler(Wrestler w);
    public boolean deleteWrestlers(List<Wrestler> wList);
    public Wrestler getDummyWrestler();
    public List<Wrestler> getAllWrestlers();
    public List<Wrestler> getWrestlersByGroup(Group group);
    public List<Wrestler> getWrestlersByClass(String cl);
    public List<Wrestler> getWrestlersByAgeDivision(String ageDiv);
    public List<Wrestler> getWrestlersByWeightClass(String wtClass);
    public List<Wrestler> getWrestlersByClassDiv(String cl, String ageDiv);
    public List<Wrestler> getWrestlersByClassDivWeight(String cl, String ageDiv,
            String wtClass);
    public List<Wrestler> getWrestlersByTeam(String team);
    public List<Wrestler> getWrestlersByTeamClass(String team, String classification);
    public List<Wrestler> getUngroupedWrestlers();
    /**
     * @deprecated Use getScratchedWrestlers()
     */
    public List<Wrestler> getFlaggedWrestlers();
    public List<Wrestler> getScratchedWrestlers();

    ////////////////////////////////////////////////////////////////////////////
    //  Data Access for Group.class
    ////////////////////////////////////////////////////////////////////////////
    public Group createGroup(List<Wrestler> list);
    public boolean add(Group g);
    public boolean addGroup(Group g);
    public boolean updateGroup(Group g);
    public boolean deleteGroup(Group g);
    public boolean deleteGroups(List<Group> gList);
    public List<Group> getAllGroups();
    public List<Group> getGroupsByDiv(String div);
    public List<Group> getGroupsByMat(String mat);
    public List<Group> getGroupsBySession(String session);
    public List<Group> getGroupsBySessionMat(String session, String mat);

    ////////////////////////////////////////////////////////////////////////////
    //  Data Access for Bout.class
    ////////////////////////////////////////////////////////////////////////////
    public Bout createBout(Wrestler green, Wrestler red, Group g, String round,
            String label, Bout winNext, Bout loseNext);
    public Bout createBout(Wrestler green, Wrestler red, Group g, String round,
            String label);
    public boolean add(Bout b);
    public boolean addBout(Bout b);
    public boolean updateBout(Bout b);
    public boolean deleteBout(Bout b);
    public List<Bout> getAllBouts();
    public List<Bout> getBoutsByGroup(Group group);
    public List<Bout> getBoutsByMat(String mat);
    public List<Bout> getBoutsBySession(String session);
    public List<Bout> getBoutsByMatSession(String mat, String session);

    ////////////////////////////////////////////////////////////////////////////
    //  Miscellaneous tournament data
    ////////////////////////////////////////////////////////////////////////////
    public String getName();
    public void setName(String name);
    public String getSite();
    public void setSite(String site);
    public String getCity();
    public void setCity(String city);
    public String getState();
    public void setState(String state);
    public String getMonth();
    public void setMonth(String month);
    public Integer getDay();
    public void setDay(Integer day);
    public Integer getYear();
    public void setYear(Integer year);
    public Boolean isFifthPlaceEnabled();
    public void setFifthPlaceEnabled(Boolean b);
    public Boolean isSecondPlaceChallengeEnabled();
    public void setSecondPlaceChallengeEnabled(Boolean b);
    public Boolean isRoundRobinEnabled();
    public void setRoundRobinEnabled(Boolean b);
    public Integer getRoundRobinMax();
    public void setRoundRobinMax(Integer max);
    public String getMatValues();
    public void setMatValues(String s);
    public String getBoutTimeValues();
    public void setBoutTimeValues(String s);
    public String getClassificationValues();
    public void setClassificationValues(String s);
    public String getAgeDivisionValues();
    public void setAgeDivisionValues(String s);
    public String getSessionValues();
    public void setSessionValues(String s);
    public String getWeightClassValues();
    public void setWeightClassValues(String s);
    public ExcelFileInputConfig getExcelFileInputConfig();
    public void setExcelFileInputConfig(ExcelFileInputConfig c);
    public TextFileInputConfig getTextFileInputConfig();
    public void setTextFileInputConfig(TextFileInputConfig c);
    public XMLFileInputConfig getXmlFileInputConfig();
    public void setXmlFileInputConfig(XMLFileInputConfig c);
    public Integer getMaxAward();
    public void setMaxAward(Integer i);
    public String getBoutsheetWatermarkValues();
    public void setBoutsheetWatermarkValues(String s);
    public String getBracketsheetWatermarkValues();
    public void setBracketsheetWatermarkValues(String s);
    public DoOperation getIncludeBracketsheetTimestamp();
    public void setIncludeBracketsheetTimestamp(DoOperation value);
    public String getBracketsheetAwardImage();
    public void setBracketsheetAwardImage(String path);
    public PositionOnPage getBracketsheetAwardImagePosition();
    public void setBracketsheetAwardImagePosition(PositionOnPage value);

    public List<String> getTeams();
}
