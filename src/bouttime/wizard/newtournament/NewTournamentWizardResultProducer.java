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

package bouttime.wizard.newtournament;

import bouttime.dao.Dao;
import java.util.Map;
import org.netbeans.spi.wizard.Summary;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage;

/**
 *
 */
public class NewTournamentWizardResultProducer implements WizardPage.WizardResultProducer {
    
    private Dao dao;

    public NewTournamentWizardResultProducer(Dao dao) {
        this.dao = dao;
    }

    public Object finish(Map data) throws WizardException {
        String filename = (String)data.get("fileName");
        dao.openNew(filename);
        setConfig(data);
        
        String [] items = new String [] {
            "Name : " + dao.getName(),
            "Site : " + dao.getSite(),
            "City : " + dao.getCity(),
            "State : " + dao.getState(),
            "Month : " + dao.getMonth(),
            "Day : " + dao.getDay(),
            "Year : " + dao.getYear(),
            "5th Place : " + dao.isFifthPlaceEnabled(),
            "2nd Place : " + dao.isSecondPlaceChallengeEnabled(),
            "Round Robin : " + dao.isRoundRobinEnabled(),
            "Round Robin Max : " + dao.getRoundRobinMax(),
            "Max Award : " + dao.getMaxAward(),
            "Classifications : " + dao.getClassificationValues(),
            "Age Divisions : " + dao.getAgeDivisionValues(),
            "Weight Classes : " + dao.getWeightClassValues(),
            "Mats : " + dao.getMatValues(),
            "Sessions : " + dao.getSessionValues(),
            "Bout Times :" + dao.getBoutTimeValues()
        };

        return Summary.create(items, null);
    }

    public boolean cancel(Map data) {
        return true;
    }

    private void setConfig(Map data) {
        dao.setName((String)data.get("name"));
        dao.setSite((String)data.get("site"));
        dao.setCity((String)data.get("city"));
        dao.setState((String)data.get("state"));

        dao.setMonth((String)data.get("month"));
        dao.setDay(Integer.valueOf((String)data.get("day")));
        dao.setYear(Integer.valueOf((String)data.get("year")));

        dao.setFifthPlaceEnabled((Boolean)data.get("fifthPlace"));
        dao.setSecondPlaceChallengeEnabled((Boolean)data.get("secondPlace"));
        dao.setRoundRobinEnabled((Boolean)data.get("roundRobin"));
        if (dao.isRoundRobinEnabled()) {
            dao.setRoundRobinMax(Integer.valueOf((String)data.get("roundRobinMax")));
        }
        dao.setMaxAward(Integer.valueOf((String)data.get("maxAward")));

        dao.setAgeDivisionValues((String)data.get("divisions"));
        dao.setBoutTimeValues((String)data.get("boutTimes"));
        dao.setClassificationValues((String)data.get("classifications"));
        dao.setMatValues((String)data.get("mats"));
        dao.setSessionValues((String)data.get("sessions"));
        dao.setWeightClassValues((String)data.get("weightClasses"));

        dao.flush();
    }
}
