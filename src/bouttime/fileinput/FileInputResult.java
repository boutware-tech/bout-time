/**                 ***COPYRIGHT STARTS HERE***
 *  BoutTime - the wrestling tournament administrator.
 *
 *  Copyright (C) 2010  Jeffrey K. Rutt
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

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * This class holds the result of a 'file input' operation.
 */
public class FileInputResult {

    static Logger logger = Logger.getLogger(FileInputResult.class);
    private Integer         recordsProcessed;
    private Integer         recordsAccepted;
    private Integer         recordsRejected;
    private List<String>    rejects;

    ////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    ////////////////////////////////////////////////////////////////////////////
    public FileInputResult() {
        recordsProcessed = Integer.valueOf(0);
        recordsAccepted = Integer.valueOf(0);
        recordsRejected = Integer.valueOf(0);
        rejects = new ArrayList<String>();
    }

    public FileInputResult(Integer recordsProcessed,
            Integer recordsAccepted, Integer recordsRejected,
            List<String> rejects) {
        
        this.recordsProcessed = recordsProcessed;
        this.recordsAccepted = recordsAccepted;
        this.recordsRejected = recordsRejected;
        this.rejects = rejects;
        logger.info(String.format("processed=%d  accepted=%d  rejected=%d",
                recordsProcessed, recordsAccepted, recordsRejected));
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // Standard getters and setters
    //
    ////////////////////////////////////////////////////////////////////////////
    public Integer getRecordsAccepted() {return recordsAccepted;}
    public void setRecordsAccepted(Integer i) {this.recordsAccepted = i;}

    public Integer getRecordsProcessed() {return recordsProcessed;}
    public void setRecordsProcessed(Integer i) {this.recordsProcessed = i;}

    public Integer getRecordsRejected() {return recordsRejected;}
    public void setRecordsRejected(Integer i) {this.recordsRejected = i;}

    public List<String> getRejects() {return rejects;}
    public void setRejects(List<String> rejects) {this.rejects = rejects;}

}
