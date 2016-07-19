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

package bouttime.dao.xml;

import bouttime.fileinput.ExcelFileInputConfig;
import bouttime.fileinput.FileInputConfig;
import bouttime.fileinput.TextFileInputConfig;
import bouttime.fileinput.XMLFileInputConfig;
import bouttime.model.Wrestler;
import bouttime.model.Group;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * This is the class for the tournament.  All tournament data is contained
 * in this class and is persisted to an XML file (via FileOps).
 */
class Tournament {
    static Logger logger = Logger.getLogger(Tournament.class);
    private String                  name;
    private String                  site;
    private String                  city;
    private String                  state;
    private Boolean                 fifthPlaceEnabled;
    private Boolean                 secondPlaceChallengeEnabled;
    private Boolean                 roundRobinEnabled;
    private Integer                 roundRobinMax;
    private String                  month;
    private Integer                 day;
    private Integer                 year;
    private String                  matValues;
    private String                  boutTimeValues;
    private String                  classificationValues;
    private String                  ageDivisionValues;
    private String                  sessionValues;
    private String                  weightClassValues;
    private String                  boutsheetWatermarkValues;
    private String                  bracketsheetWatermarkValues;
    private TextFileInputConfig     textFileInputConfig;
    private ExcelFileInputConfig    excelFileInputConfig;
    private XMLFileInputConfig      xmlFileInputConfig;
    private List<Wrestler>          allWrestlers;
    private List<Group>             allGroups;
    private Wrestler                dummyWrestler;
    private Integer                 maxAward;
    private int                     includeBracketsheetTimestamp;
    private String                  bracketsheetAwardImage;
    private int                     bracketsheetAwardImagePosition;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////
    
    public Tournament() {
        this.allWrestlers = new ArrayList<Wrestler>();
        this.allGroups = new ArrayList<Group>();
        this.dummyWrestler = new Wrestler();

        initGeneralConfig();

        initListValues();

        initTextFileInputConfig();
        initExcelFileInputConfig();
        initXMLFileInputConfig();
        
        logger.trace("Tournament class constructed");
    }

    ////////////////////////////////////////////////////////////////////////////
    // Standard getters and setters
    ////////////////////////////////////////////////////////////////////////////

    public String getAgeDivisionValues() {return ageDivisionValues;}
    public void setAgeDivisionValues(String s) {this.ageDivisionValues = s;}

    public String getBoutTimeValues() {return boutTimeValues;}
    public void setBoutTimeValues(String s) {this.boutTimeValues = s;}

    public String getCity() {return city;}
    public void setCity(String s) {this.city = s;}

    public String getClassificationValues() {return classificationValues;}
    public void setClassificationValues(String s) {this.classificationValues = s;}

    public Integer getDay() {return day;}
    public void setDay(Integer i) {this.day = i;}

    public Boolean isFifthPlaceEnabled() {return fifthPlaceEnabled;}
    public void setFifthPlaceEnabled(Boolean b) {this.fifthPlaceEnabled = b;}

    public String getMatValues() {return matValues;}
    public void setMatValues(String s) {this.matValues = s;}

    public String getMonth() {return month;}
    public void setMonth(String s) {this.month = s;}

    public String getName() {return name;}
    public void setName(String s) {this.name = s;}

    public Boolean isRoundRobinEnabled() {return roundRobinEnabled;}
    public void setRoundRobinEnabled(Boolean b) {this.roundRobinEnabled = b;}

    public Integer getRoundRobinMax() {return roundRobinMax;}
    public void setRoundRobinMax(Integer i) {this.roundRobinMax = i;}

    public Boolean isSecondPlaceChallengeEnabled() {return secondPlaceChallengeEnabled;}
    public void setSecondPlaceChallengeEnabled(Boolean b) {this.secondPlaceChallengeEnabled = b;}

    public String getSessionValues() {return sessionValues;}
    public void setSessionValues(String s) {this.sessionValues = s;}

    public String getSite() {return site;}
    public void setSite(String s) {this.site = s;}

    public String getState() {return state;}
    public void setState(String s) {this.state = s;}

    public String getWeightClassValues() {return weightClassValues;}
    public void setWeightClassValues(String s) {this.weightClassValues = s;}

    public String getBoutsheetWatermarkValues() {return boutsheetWatermarkValues;}
    public void setBoutsheetWatermarkValues(String s) {this.boutsheetWatermarkValues = s;}

    public String getBracketsheetWatermarkValues() {return bracketsheetWatermarkValues;}
    public void setBracketsheetWatermarkValues(String s) {this.bracketsheetWatermarkValues = s;}

    public Integer getYear() {return year;}
    public void setYear(Integer i) {this.year = i;}

    public ExcelFileInputConfig getExcelFileInputConfig() {return excelFileInputConfig;}
    public void setExcelFileInputConfig(ExcelFileInputConfig c) {this.excelFileInputConfig = c;}

    public TextFileInputConfig getTextFileInputConfig() {return textFileInputConfig;}
    public void setTextFileInputConfig(TextFileInputConfig c) {this.textFileInputConfig = c;}

    public XMLFileInputConfig getXmlFileInputConfig() {return xmlFileInputConfig;}
    public void setXmlFileInputConfig(XMLFileInputConfig c) {this.xmlFileInputConfig = c;}

    public List<Group> getAllGroups() {return allGroups;}
    public void setAllGroups(List<Group> l) {this.allGroups = l;}
    public boolean addGroup(Group g) {return this.allGroups.add(g);}
    public void removeGroup(Group g) {this.allGroups.remove(g);}

    public List<Wrestler> getAllWrestlers() {return allWrestlers;}
    public void setAllWrestlers(List<Wrestler> l) {this.allWrestlers = l;}
    public void removeWrestler(Wrestler w) {allWrestlers.remove(w);}

    public Wrestler getDummyWrestler() {return this.dummyWrestler;}

    public Integer getMaxAward() {return this.maxAward;}
    public void setMaxAward(Integer i) {this.maxAward = i;}
    
    public int getIncludeBracketsheetTimestamp() {return this.includeBracketsheetTimestamp;}
    public void setIncludeBracketsheetTimestamp(int i) {this.includeBracketsheetTimestamp = i;}
    

    public String getBracketsheetAwardImage() {return this.bracketsheetAwardImage;}
    public void setBracketsheetAwardImage(String path) {this.bracketsheetAwardImage = path;}

    int getBracketsheetAwardImagePosition() {return this.bracketsheetAwardImagePosition;}

    void setBracketsheetAwardImagePosition(int i) {this.bracketsheetAwardImagePosition = i;}

    ////////////////////////////////////////////////////////////////////////////
    // Utility methods
    ////////////////////////////////////////////////////////////////////////////
    
    public boolean addWrestler(Wrestler w) {
        // We could make allWrestlers a Set and do the 'equals' checking
        // when we call add().  But the gui is expecting a List.  So this
        // little for loop saves us from some arguably uglier Set-to-List
        // conversion code later.
        for (Wrestler temp : this.allWrestlers) {
            if (temp.equals(w)) {
                return false;
            }
        }

        return this.allWrestlers.add(w);
    }

    // Common method for TextFileInputConfig and ExcelFileInputConfig
    private void initFileInputConfig(FileInputConfig config) {
        config.setActualWeight("0");
        config.setClassification("0");
        config.setDivision("0");
        config.setFirstName("0");
        config.setLastName("0");
        config.setLevel("0");
        config.setSerialNumber("0");
        config.setTeamName("0");
        config.setWeightClass("0");
    }

    private void initGeneralConfig() {
        this.name = "";
        this.site = "";
        this.city = "";
        this.state = "";
        this.day = 1;
        this.month = "January";
        this.year = 2010;
        this.fifthPlaceEnabled = false;
        this.roundRobinEnabled = false;
        this.secondPlaceChallengeEnabled = false;
        this.roundRobinMax = 0;
        this.maxAward = 3;
    }

    private void initListValues() {
        this.matValues = "";
        this.boutTimeValues = "";
        this.classificationValues = "";
        this.ageDivisionValues = "";
        this.sessionValues = "";
        this.weightClassValues = "";
        this.boutsheetWatermarkValues = "";
        this.bracketsheetWatermarkValues = "";
    }

    private void initTextFileInputConfig() {
        this.textFileInputConfig = new TextFileInputConfig();

        this.textFileInputConfig.setFieldSeparator(",");

        initFileInputConfig(this.textFileInputConfig);
    }

    private void initExcelFileInputConfig() {
        this.excelFileInputConfig = new ExcelFileInputConfig();

        this.excelFileInputConfig.setEndRow("1");
        this.excelFileInputConfig.setSheet("1");
        this.excelFileInputConfig.setStartRow("1");

        initFileInputConfig(this.textFileInputConfig);
    }

    private void initXMLFileInputConfig() {
        this.xmlFileInputConfig = new XMLFileInputConfig();

        this.xmlFileInputConfig.setActualWeight("");
        this.xmlFileInputConfig.setClassification("");
        this.xmlFileInputConfig.setDivision("");
        this.xmlFileInputConfig.setFirstName("");
        this.xmlFileInputConfig.setLastName("");
        this.xmlFileInputConfig.setLevel("");
        this.xmlFileInputConfig.setSerialNumber("");
        this.xmlFileInputConfig.setTeamName("");
        this.xmlFileInputConfig.setWeightClass("");

        this.xmlFileInputConfig.setRoot("");
        this.xmlFileInputConfig.setWrestler("");
    }
}
