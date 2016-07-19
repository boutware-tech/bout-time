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
package bouttime.utility.seed;

import bouttime.model.Group;
import bouttime.model.Wrestler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 * A class to randomly assign seeds to a group
 */
public class RandomSeed {

    static Logger logger = Logger.getLogger(RandomSeed.class);
    static private final int NO_CHECK = 0;
    static private final int CHECK_TEAM = 1;
    static private final int CHECK_GEO = 2;
    
    ////////////////////////////////////////////////////////////////////////////
    // 4-man bracket
    ////////////////////////////////////////////////////////////////////////////
    static protected final int[] BRACKET4_HALF1 = {0, 3};
    static protected final int[] BRACKET4_HALF2 = {2, 1};
    static protected final int[][] BRACKET4_HALVES = {
        BRACKET4_HALF1,
        BRACKET4_HALF2
    };
    
    ////////////////////////////////////////////////////////////////////////////
    // 8-man bracket
    ////////////////////////////////////////////////////////////////////////////
    static protected final int[] BRACKET8_HALF1 = {0, 7, 4, 3};
    static protected final int[] BRACKET8_HALF2 = {2, 5, 6, 1};
    static protected final int[] BRACKET8_QUARTER1 = {0, 7};
    static protected final int[] BRACKET8_QUARTER2 = {4, 3};
    static protected final int[] BRACKET8_QUARTER3 = {2, 5};
    static protected final int[] BRACKET8_QUARTER4 = {6, 1};
    static protected final int[][] BRACKET8_HALVES = {
        BRACKET8_HALF1,
        BRACKET8_HALF2
    };
    static protected final int[][] BRACKET8_QUARTERS = {
        BRACKET8_QUARTER1,
        BRACKET8_QUARTER2,
        BRACKET8_QUARTER3,
        BRACKET8_QUARTER4
    };
    
    ////////////////////////////////////////////////////////////////////////////
    // 16-man bracket
    ////////////////////////////////////////////////////////////////////////////
    static protected final int[] BRACKET16_HALF1 = {0, 15, 8, 7, 11, 4, 12, 3};
    static protected final int[] BRACKET16_HALF2 = {2, 13, 10, 5, 6, 9, 14, 1};
    static protected final int[] BRACKET16_QUARTER1 = {0, 15, 8, 7};
    static protected final int[] BRACKET16_QUARTER2 = {11, 4, 12, 3};
    static protected final int[] BRACKET16_QUARTER3 = {2, 13, 10, 5};
    static protected final int[] BRACKET16_QUARTER4 = {6, 9, 14, 1};
    static protected final int[] BRACKET16_EIGHTH1 = {0, 15};
    static protected final int[] BRACKET16_EIGHTH2 = {8, 7};
    static protected final int[] BRACKET16_EIGHTH3 = {11, 4};
    static protected final int[] BRACKET16_EIGHTH4 = {12, 3};
    static protected final int[] BRACKET16_EIGHTH5 = {2, 13};
    static protected final int[] BRACKET16_EIGHTH6 = {10, 5};
    static protected final int[] BRACKET16_EIGHTH7 = {6, 9};
    static protected final int[] BRACKET16_EIGHTH8 = {14, 1};
    static protected final int[][] BRACKET16_HALVES = {
        BRACKET16_HALF1,
        BRACKET16_HALF2
    };
    static protected final int[][] BRACKET16_QUARTERS = {
        BRACKET16_QUARTER1,
        BRACKET16_QUARTER2,
        BRACKET16_QUARTER3,
        BRACKET16_QUARTER4
    };
    static protected final int[][] BRACKET16_EIGHTHS = {
        BRACKET16_EIGHTH1,
        BRACKET16_EIGHTH2,
        BRACKET16_EIGHTH3,
        BRACKET16_EIGHTH4,
        BRACKET16_EIGHTH5,
        BRACKET16_EIGHTH6,
        BRACKET16_EIGHTH7,
        BRACKET16_EIGHTH8
    };
    
    ////////////////////////////////////////////////////////////////////////////
    // 32-man bracket
    ////////////////////////////////////////////////////////////////////////////
    static protected final int[] BRACKET32_HALF1 = {0, 31, 16, 15, 8, 23, 24, 7, 4, 27, 20, 11, 12, 19, 28, 3};
    static protected final int[] BRACKET32_HALF2 = {2, 29, 18, 13, 10, 21, 26, 5, 6, 25, 22, 9, 14, 17, 30, 1};
    static protected final int[] BRACKET32_QUARTER1 = {0, 31, 16, 15, 8, 23, 24, 7};
    static protected final int[] BRACKET32_QUARTER2 = {4, 27, 20, 11, 12, 19, 28, 3};
    static protected final int[] BRACKET32_QUARTER3 = {2, 29, 18, 13, 10, 21, 26, 5};
    static protected final int[] BRACKET32_QUARTER4 = {6, 25, 22, 9, 14, 17, 30, 1};
    static protected final int[] BRACKET32_EIGHTH1 = {0, 31, 16, 15};
    static protected final int[] BRACKET32_EIGHTH2 = {8, 23, 24, 7};
    static protected final int[] BRACKET32_EIGHTH3 = {4, 27, 20, 11};
    static protected final int[] BRACKET32_EIGHTH4 = {12, 19, 28, 3};
    static protected final int[] BRACKET32_EIGHTH5 = {2, 29, 18, 13};
    static protected final int[] BRACKET32_EIGHTH6 = {10, 21, 26, 5};
    static protected final int[] BRACKET32_EIGHTH7 = {6, 25, 22, 9};
    static protected final int[] BRACKET32_EIGHTH8 = {14, 17, 30, 1};
    static protected final int[][] BRACKET32_HALVES = {
        BRACKET32_HALF1,
        BRACKET32_HALF2
    };
    static protected final int[][] BRACKET32_QUARTERS = {
        BRACKET32_QUARTER1,
        BRACKET32_QUARTER2,
        BRACKET32_QUARTER3,
        BRACKET32_QUARTER4
    };
    static protected final int[][] BRACKET32_EIGHTHS = {
        BRACKET32_EIGHTH1,
        BRACKET32_EIGHTH2,
        BRACKET32_EIGHTH3,
        BRACKET32_EIGHTH4,
        BRACKET32_EIGHTH5,
        BRACKET32_EIGHTH6,
        BRACKET32_EIGHTH7,
        BRACKET32_EIGHTH8
    };

    /**
     * Assign random seed values for the given group.
     * @param group Group to assign random seed values
     */
    static public void execute(Group group) {
        if (group == null) {
            logger.warn("group is null");
            return;
        }

        int numWrestlers = group.getNumWrestlers();

        if (numWrestlers < 3) {
            // Can't do any random seeding, just assign seeds
            int seed = 1;
            for (Wrestler w : group.getWrestlers()) {
                w.setSeed(seed);
                seed++;
            }

            return;
        }

        resetSeedValues(group.getWrestlers());
        List<Wrestler> groupWrestlerList = group.getWrestlers();
        List<Wrestler> unseededWrestlerList = getNonPreSeededWrestlers(groupWrestlerList);
        Wrestler[] wrestlerArray = preLoadArray(groupWrestlerList);
        Map<String, List<Wrestler>> map = getTeamWrestlers(groupWrestlerList);

        if (map.keySet().size() > 1) {
            while (!map.isEmpty()) {
                String team = getKeyWithMostWrestlers(map);
                if ((team == null) || team.isEmpty()) {
                    // Wrestlers with no team are NOT on the same team.
                    map.remove(team);
                    continue;
                }
                List<Wrestler> teamWrestlerList = map.get(team);
                if (teamWrestlerList.size() < 2) {
                    break;
                }

                if ((numWrestlers > 0) && (numWrestlers < 5)) {
                    execute4Team(team, teamWrestlerList, wrestlerArray, unseededWrestlerList);
                } else if ((numWrestlers > 4) && (numWrestlers < 9)) {
                    execute8Team(team, teamWrestlerList, wrestlerArray, unseededWrestlerList);
                } else if ((numWrestlers > 8) && (numWrestlers < 17)) {
                    execute16Team(team, teamWrestlerList, wrestlerArray, unseededWrestlerList);
                } else if ((numWrestlers > 16) && (numWrestlers < 33)) {
                    execute32Team(team, teamWrestlerList, wrestlerArray, unseededWrestlerList);
                }
                map.remove(team);
            }
        }
        
        map = getGeoWrestlers(groupWrestlerList);
        if (map.keySet().size() > 1) {
            while (!map.isEmpty()) {
                String geo = getKeyWithMostWrestlers(map);
                if ((geo == null) || geo.isEmpty()) {
                    // Wrestlers with no geo are NOT on the same geo.
                    map.remove(geo);
                    continue;
                }
                List<Wrestler> geoWrestlerList = map.get(geo);
                if (geoWrestlerList.size() < 2) {
                    break;
                }

                if ((numWrestlers > 0) && (numWrestlers < 5)) {
                    execute4Geo(geo, geoWrestlerList, wrestlerArray, unseededWrestlerList);
                } else if ((numWrestlers > 4) && (numWrestlers < 9)) {
                    execute8Geo(geo, geoWrestlerList, wrestlerArray, unseededWrestlerList);
                } else if ((numWrestlers > 8) && (numWrestlers < 17)) {
                    execute16Geo(geo, geoWrestlerList, wrestlerArray, unseededWrestlerList);
                } else if ((numWrestlers > 16) && (numWrestlers < 33)) {
                    execute32Geo(geo, geoWrestlerList, wrestlerArray, unseededWrestlerList);
                }
                map.remove(geo);
            }
        }

        // Randomly assign the remaining unseeded wrestlers
        Random r = new Random();
        for (int i = 0; i < wrestlerArray.length; i++) {
            if (wrestlerArray[i] == null) {
                int idx = r.nextInt(unseededWrestlerList.size());
                unseededWrestlerList.get(idx).setSeed(i + 1);
                unseededWrestlerList.remove(idx);
            } else {
                wrestlerArray[i].setSeed(i + 1);
            }
        }
    }

    /**
     * Logic to assign seeds for teammates in a 4-man bracket
     * @param team The team to assign seeds for
     * @param teamWrestlerList List of wrestlers on the team
     * @param wrestlerArray The array of wrestlers that represents the group.
     * @param unseededWrestlerList List of unseededWrestlers for the group.
     */
    static private void execute4Team(String team, List<Wrestler> teamWrestlerList, Wrestler[] wrestlerArray, List<Wrestler> unseededWrestlerList) {
        switch (teamWrestlerList.size()) {
            case 2:// Put 1 in each half
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET4_HALVES, CHECK_TEAM, 1, 2);
                break;
            default:
                // This is case for 3 or 4
                // Randomly seed each one
                break;
        }
    }

    /**
     * Logic to assign seeds for teammates in a 4-man bracket
     * @param geo The geo to assign seeds for
     * @param geoWrestlerList List of wrestlers on the geo
     * @param wrestlerArray The array of wrestlers that represents the group.
     * @param unseededWrestlerList List of unseededWrestlers for the group.
     */
    static private void execute4Geo(String geo, List<Wrestler> geoWrestlerList, Wrestler[] wrestlerArray, List<Wrestler> unseededWrestlerList) {
        switch (geoWrestlerList.size()) {
            case 2:// Put 1 in each half
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET4_HALVES, CHECK_GEO, 1, 2);
                break;
            default:
                // This is case for 3 or 4
                // Randomly seed each one
                break;
        }
    }

    /**
     * Logic to assign seeds for teammates in a 8-man bracket
     * @param team The team to assign seeds for
     * @param teamWrestlerList List of wrestlers on the team
     * @param wrestlerArray The array of wrestlers that represents the group.
     * @param unseededWrestlerList List of unseededWrestlers for the group.
     */
    static private void execute8Team(String team, List<Wrestler> teamWrestlerList, Wrestler[] wrestlerArray, List<Wrestler> unseededWrestlerList) {
        switch (teamWrestlerList.size()) {
            case 2:// Put 1 in each half
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET8_HALVES, CHECK_TEAM, 1, 2);
                break;
            case 3: // fall through
            case 4: // Put 1 in each quarter
            case 5: // Put 1 in each quarter, randomly place the last one
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET8_QUARTERS, CHECK_TEAM, 1, 4);
                break;
            case 6:
                // Put 3 in each half
                int[][] half = {BRACKET8_HALF1};
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, half, CHECK_TEAM, 3, 3);
                half = new int[][]{BRACKET8_HALF2};
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, half, CHECK_TEAM, 3, 3);
                break;
            default:
                // This is case for 7 or 8
                // Randomly seed each one
                break;
        }
    }

    /**
     * Logic to assign seeds for teammates in a 8-man bracket
     * @param geo The geo to assign seeds for
     * @param geoWrestlerList List of wrestlers on the geo
     * @param wrestlerArray The array of wrestlers that represents the group.
     * @param unseededWrestlerList List of unseededWrestlers for the group.
     */
    static private void execute8Geo(String geo, List<Wrestler> geoWrestlerList, Wrestler[] wrestlerArray, List<Wrestler> unseededWrestlerList) {
        switch (geoWrestlerList.size()) {
            case 2:// Put 1 in each half
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET8_HALVES, CHECK_GEO, 1, 2);
                break;
            case 3: // fall through
            case 4: // Put 1 in each quarter
            case 5: // Put 1 in each quarter, randomly place the last one
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET8_QUARTERS, CHECK_GEO, 1, 4);
                break;
            case 6:
                // Put 3 in each half
                int[][] half = {BRACKET8_HALF1};
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, half, CHECK_GEO, 3, 3);
                half = new int[][]{BRACKET8_HALF2};
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, half, CHECK_GEO, 3, 3);
                break;
            default:
                // This is case for 7 or 8
                // Randomly seed each one
                break;
        }
    }

    /**
     * Logic to assign seeds for teammates in a 16-man bracket
     * @param team The team to assign seeds for
     * @param teamWrestlerList List of wrestlers on the team
     * @param wrestlerArray The array of wrestlers that represents the group.
     * @param unseededWrestlerList List of unseededWrestlers for the group.
     */
    static private void execute16Team(String team, List<Wrestler> teamWrestlerList, Wrestler[] wrestlerArray, List<Wrestler> unseededWrestlerList) {
        switch (teamWrestlerList.size()) {
            case 2:// Put 1 in each half
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET16_HALVES, CHECK_TEAM, 1, 2);
                break;
            case 3: // fall through
            case 4: // Put 1 in each quarter
            case 5: // Put 1 in each quarter, place the last one randomly in an 8th bucket to avoid 1st round matchup
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET16_QUARTERS, CHECK_TEAM, 1, 4);
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET16_EIGHTHS, CHECK_TEAM, 1, 1);
                break;
            case 6:
                // Put 3 in each half, 1 in each 8th of the half
                int[][] half = {BRACKET16_EIGHTH1, BRACKET16_EIGHTH2, BRACKET16_EIGHTH3, BRACKET16_EIGHTH4};
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, half, CHECK_TEAM, 1, 3);
                half = new int[][]{BRACKET16_EIGHTH5, BRACKET16_EIGHTH6, BRACKET16_EIGHTH7, BRACKET16_EIGHTH8};
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, half, CHECK_TEAM, 1, 4);
                break;
            case 7: // fall through
            case 8: // Put 1 in each 8th
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET16_EIGHTHS, CHECK_TEAM, 1, 8);
                break;
            default:
                // This is case for 9 through 16
                // Put 1 in each 8th, then randomly seed the rest.
                // We could do something more fancy, like put half in each half.
                // This should be VERY rare case.
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET16_EIGHTHS, CHECK_TEAM, 1, 8);
                break;
        }
    }

    /**
     * Logic to assign seeds for geo-mates in a 16-man bracket
     * @param geo The geo to assign seeds for
     * @param geoWrestlerList List of wrestlers on the geo
     * @param wrestlerArray The array of wrestlers that represents the group.
     * @param unseededWrestlerList List of unseededWrestlers for the group.
     */
    static private void execute16Geo(String geo, List<Wrestler> geoWrestlerList, Wrestler[] wrestlerArray, List<Wrestler> unseededWrestlerList) {
        switch (geoWrestlerList.size()) {
            case 2:// Put 1 in each half
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET16_HALVES, CHECK_GEO, 1, 2);
                break;
            case 3: // fall through
            case 4: // Put 1 in each quarter
            case 5: // Put 1 in each quarter, place the last one randomly in an 8th bucket to avoid 1st round matchup
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET16_QUARTERS, CHECK_GEO, 1, 4);
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET16_EIGHTHS, CHECK_GEO, 1, 1);
                break;
            case 6:
                // Put 3 in each half, 1 in each 8th of the half
                int[][] half = {BRACKET16_EIGHTH1, BRACKET16_EIGHTH2, BRACKET16_EIGHTH3, BRACKET16_EIGHTH4};
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, half, CHECK_GEO, 1, 3);
                half = new int[][]{BRACKET16_EIGHTH5, BRACKET16_EIGHTH6, BRACKET16_EIGHTH7, BRACKET16_EIGHTH8};
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, half, CHECK_GEO, 1, 4);
                break;
            case 7: // fall through
            case 8: // Put 1 in each 8th
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET16_EIGHTHS, CHECK_GEO, 1, 8);
                break;
            default:
                // This is case for 9 through 16
                // Put 1 in each 8th, then randomly seed the rest.
                // We could do something more fancy, like put half in each half.
                // This should be VERY rare case.
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET16_EIGHTHS, CHECK_GEO, 1, 8);
                break;
        }
    }

    /**
     * Logic to assign seeds for teammates in a 32-man bracket
     * @param team The team to assign seeds for
     * @param teamWrestlerList List of wrestlers on the team
     * @param wrestlerArray The array of wrestlers that represents the group.
     * @param unseededWrestlerList List of unseededWrestlers for the group.
     */
    static private void execute32Team(String team, List<Wrestler> teamWrestlerList, Wrestler[] wrestlerArray, List<Wrestler> unseededWrestlerList) {
        switch (teamWrestlerList.size()) {
            case 2:// Put 1 in each half
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET32_HALVES, CHECK_TEAM, 1, 2);
                break;
            case 3: // fall through
            case 4: // Put 1 in each quarter
            case 5: // Put 1 in each quarter, place the last one randomly in an 8th bucket to avoid 1st round matchup
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET32_QUARTERS, CHECK_TEAM, 1, 4);
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET32_EIGHTHS, CHECK_TEAM, 1, 1);
                break;
            case 6:
                // Put 3 in each half, 1 in each 8th of the half
                int[][] half = {BRACKET32_EIGHTH1, BRACKET32_EIGHTH2, BRACKET32_EIGHTH3, BRACKET32_EIGHTH4};
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, half, CHECK_TEAM, 1, 3);
                half = new int[][]{BRACKET32_EIGHTH5, BRACKET32_EIGHTH6, BRACKET32_EIGHTH7, BRACKET32_EIGHTH8};
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, half, CHECK_TEAM, 1, 4);
                break;
            case 7: // fall through
            case 8: // Put 1 in each 8th
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET32_EIGHTHS, CHECK_TEAM, 1, 8);
                break;
            default:
                // This is case for 9 through 32
                // Put 1 in each 8th, then randomly seed the rest.
                // We could do something more fancy, like put half in each half.
                // This should be VERY rare case.
                assignSeedValuesForTeam(team, wrestlerArray, unseededWrestlerList, BRACKET32_EIGHTHS, CHECK_TEAM, 1, 8);
                break;
        }
    }

    /**
     * Logic to assign seeds for geo-mates in a 32-man bracket
     * @param geo The geo to assign seeds for
     * @param geoWrestlerList List of wrestlers on the geo
     * @param wrestlerArray The array of wrestlers that represents the group.
     * @param unseededWrestlerList List of unseededWrestlers for the group.
     */
    static private void execute32Geo(String geo, List<Wrestler> geoWrestlerList, Wrestler[] wrestlerArray, List<Wrestler> unseededWrestlerList) {
        switch (geoWrestlerList.size()) {
            case 2:// Put 1 in each half
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET32_HALVES, CHECK_GEO, 1, 2);
                break;
            case 3: // fall through
            case 4: // Put 1 in each quarter
            case 5: // Put 1 in each quarter, place the last one randomly in an 8th bucket to avoid 1st round matchup
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET32_QUARTERS, CHECK_GEO, 1, 4);
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET32_EIGHTHS, CHECK_GEO, 1, 1);
                break;
            case 6:
                // Put 3 in each half, 1 in each 8th of the half
                int[][] half = {BRACKET32_EIGHTH1, BRACKET32_EIGHTH2, BRACKET32_EIGHTH3, BRACKET32_EIGHTH4};
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, half, CHECK_GEO, 1, 3);
                half = new int[][]{BRACKET32_EIGHTH5, BRACKET32_EIGHTH6, BRACKET32_EIGHTH7, BRACKET32_EIGHTH8};
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, half, CHECK_GEO, 1, 4);
                break;
            case 7: // fall through
            case 8: // Put 1 in each 8th
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET32_EIGHTHS, CHECK_GEO, 1, 8);
                break;
            default:
                // This is case for 9 through 32
                // Put 1 in each 8th, then randomly seed the rest.
                // We could do something more fancy, like put half in each half.
                // This should be VERY rare case.
                assignSeedValuesForGeo(geo, wrestlerArray, unseededWrestlerList, BRACKET32_EIGHTHS, CHECK_GEO, 1, 8);
                break;
        }
    }

    /**
     * Get a wrestler from the given list that is on the given team
     * @param team Team to get a wrestler from
     * @param list List to search
     * @return A wrestler from the given team, or null if none found
     */
    static private Wrestler getWrestlerFromTeam(String team, List<Wrestler> list) {
        for (Wrestler w : list) {
            if (team.equalsIgnoreCase(w.getTeamName())) {
                return w;
            }
        }

        return null;
    }
    
    /**
     * Get a wrestler from the given list that is on the given geo
     * @param geo Geo to get a wrestler from
     * @param list List to search
     * @return A wrestler from the given geo, or null if none found
     */
    static private Wrestler getWrestlerFromGeo(String geo, List<Wrestler> list) {
        for (Wrestler w : list) {
            if (geo.equalsIgnoreCase(w.getGeo())) {
                return w;
            }
        }

        return null;
    }

    /**
     * Assign seed values for the given team.
     * @param team The team to assign seed values for
     * @param wrestlerArray The array of wrestlers that represents the group.
     * @param unseededWrestlerList The list of unseeded wrestlers.
     * @param buckets Array of buckets to place teammates
     * @param check Should check if teammate already exists in the bucket?
     * @param maxInBucket Max number of teammates to put in the same bucket
     * @param maxAdd Max number of teammates to add in this method call
     * @return Number of wrestlers that were added
     */
    static private int assignSeedValuesForTeam(String team, Wrestler[] wrestlerArray, List<Wrestler> unseededWrestlerList, int[][] buckets, int check, int maxInBucket, int maxAdd) {
        int numAdded = 0;
        int bucketIdx = 0;
        if (buckets.length > 1) {
            // Pick a random bucket
            Random r = new Random();
            bucketIdx = r.nextInt(buckets.length);
        }

        // Loop through the buckets
        for (int i = 0; i < buckets.length; i++) {
            if (unseededWrestlerList.size() < 1) {
                break;
            }
            
            int[] bucket = buckets[(bucketIdx + i) % buckets.length];
            int numInBucket = 0;
            while (numInBucket++ < maxInBucket) {
                Wrestler w = getWrestlerFromTeam(team, unseededWrestlerList);
                if (w == null) {
                    break;
                }

                if ((check == NO_CHECK) || ((check == CHECK_TEAM) && !isTeamInBucket(team, wrestlerArray, bucket))) {
                    if (addToBucket(w, wrestlerArray, bucket)) {
                        unseededWrestlerList.remove(w);

                        if (++numAdded >= maxAdd) {
                            break;
                        }
                    } else {
                        logger.debug("Unable to add wrestler to bucket");
                        break;
                    }
                }
            }
            
            if (numAdded >= maxAdd) {
                break;
            }
        }

        return numAdded;
    }

    /**
     * Assign seed values for the given geo.
     * @param geo The team to assign seed values for
     * @param wrestlerArray The array of wrestlers that represents the group.
     * @param unseededWrestlerList The list of unseeded wrestlers.
     * @param buckets Array of buckets to place geo-mates
     * @param check Should check if geo-mate already exists in the bucket?
     * @param maxInBucket Max number of geo-mates to put in the same bucket
     * @param maxAdd Max number of geo-mates to add in this method call
     * @return Number of wrestlers that were added
     */
    static private int assignSeedValuesForGeo(String geo, Wrestler[] wrestlerArray, List<Wrestler> unseededWrestlerList, int[][] buckets, int check, int maxInBucket, int maxAdd) {
        int numAdded = 0;
        int bucketIdx = 0;
        if (buckets.length > 1) {
            // Pick a random bucket
            Random r = new Random();
            bucketIdx = r.nextInt(buckets.length);
        }

        // Loop through the buckets
        for (int i = 0; i < buckets.length; i++) {
            if (unseededWrestlerList.size() < 1) {
                break;
            }
            
            int[] bucket = buckets[(bucketIdx + i) % buckets.length];
            int numInBucket = 0;
            while (numInBucket++ < maxInBucket) {
                Wrestler w = getWrestlerFromGeo(geo, unseededWrestlerList);
                if (w == null) {
                    break;
                }

                if ((check == NO_CHECK) || ((check == CHECK_GEO) && !isGeoInBucket(geo, wrestlerArray, bucket))) {
                    if (addToBucket(w, wrestlerArray, bucket)) {
                        unseededWrestlerList.remove(w);

                        if (++numAdded >= maxAdd) {
                            break;
                        }
                    } else {
                        logger.debug("Unable to add wrestler to bucket");
                        break;
                    }
                }
            }
            
            if (numAdded >= maxAdd) {
                break;
            }
        }

        return numAdded;
    }

    /**
     * Get a Map of the wrestlers on the same team.
     * The key is the team name.
     * The value is a list of wrestlers that have the same team name.
     * @param list The List of wrestlers in a group.
     * @return The Map of wrestlers on teams.
     */
    static private Map<String, List<Wrestler>> getTeamWrestlers(List<Wrestler> list) {
        Map<String, List<Wrestler>> teamMap = new HashMap<String, List<Wrestler>>();

        for (Wrestler w : list) {
            if (teamMap.containsKey(w.getTeamName())) {
                teamMap.get(w.getTeamName()).add(w);
            } else {
                List<Wrestler> l = new ArrayList<Wrestler>();
                l.add(w);
                teamMap.put(w.getTeamName(), l);
            }
        }

        return teamMap;
    }
    
    static private Map<String, List<Wrestler>> getGeoWrestlers(List<Wrestler> list) {
        Map<String, List<Wrestler>> geoMap = new HashMap<String, List<Wrestler>>();

        for (Wrestler w : list) {
            if (geoMap.containsKey(w.getGeo())) {
                geoMap.get(w.getGeo()).add(w);
            } else {
                List<Wrestler> l = new ArrayList<Wrestler>();
                l.add(w);
                geoMap.put(w.getGeo(), l);
            }
        }

        return geoMap;
    }

    /**
     * Get the key to the given Map that has the largest list
     * @param map
     * @return the key with the largest list
     */
    static private String getKeyWithMostWrestlers(Map<String, List<Wrestler>> map) {
        String key = null;
        int count = 0;
        for (String k : map.keySet()) {
            if (map.get(k).size() > count) {
                key = k;
                count = map.get(k).size();
            }
        }

        return key;
    }

    /**
     * Reset the seed values for the given list of wrestlers.
     * If a pre-seed value is specified, use it.
     * @param list 
     */
    static private void resetSeedValues(List<Wrestler> list) {
        for (Wrestler w : list) {
            if (!w.isSeedSet()) {
                w.setSeed(null);
            }
        }
    }

    /**
     * Load a wrestler array with pre-seed values
     * @param list
     * @return A wrestler array with (pre) seed values populated
     */
    static private Wrestler[] preLoadArray(List<Wrestler> list) {
        Wrestler[] wrestlerArray = new Wrestler[list.size()];

        for (Wrestler w : list) {
            if (w.getSeed() != null) {
                wrestlerArray[w.getSeed() - 1] = w;
            }
        }

        return wrestlerArray;
    }

    /**
     * Get a list of wrestlers that only includes the non-pre-seeded wrestlers.
     * @param list List of wrestlers in the group
     * @return List of wrestlers that do not have a (pre) seed value
     */
    static private List<Wrestler> getNonPreSeededWrestlers(List<Wrestler> list) {
        List<Wrestler> rvList = new ArrayList<Wrestler>();

        for (Wrestler w : list) {
            if (w.getSeed() == null) {
                rvList.add(w);
            }
        }

        return rvList;
    }

    /**
     * Determine if a wrestler from the given team exists in the given bucket
     * @param team
     * @param wrestlerArray
     * @param bucket
     * @return True if in bucket, false if not
     */
    static private boolean isTeamInBucket(String team, Wrestler[] wrestlerArray, int[] bucket) {
        for (int i : bucket) {
            if (i >= wrestlerArray.length) {
                continue;
            }

            Wrestler w = wrestlerArray[i];
            if ((w != null) && team.equalsIgnoreCase(w.getTeamName())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Determine if a wrestler from the given geo exists in the given bucket
     * @param geo
     * @param wrestlerArray
     * @param bucket
     * @return 
     */
    static private boolean isGeoInBucket(String geo, Wrestler[] wrestlerArray, int[] bucket) {
        for (int i : bucket) {
            if (i >= wrestlerArray.length) {
                continue;
            }

            Wrestler w = wrestlerArray[i];
            if ((w != null) && geo.equalsIgnoreCase(w.getGeo())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add the given wrestler to the given bucket in the given array
     * @param wrestler Wrestler object to add
     * @param wrestlerArray The wrestler array
     * @param bucket The bucket within the wrestlerArray
     * @return True if added, false if not added
     */
    static private boolean addToBucket(Wrestler wrestler, Wrestler[] wrestlerArray, int[] bucket) {
        Random random = new Random();
        int idx = random.nextInt(bucket.length);

        // Bucket may be full, so don't try forever
        for (int i = 0; i < bucket.length; i++) {
            if ((bucket[idx] < wrestlerArray.length) && wrestlerArray[bucket[idx]] == null) {
                wrestlerArray[bucket[idx]] = wrestler;
                return true;
            } else {
                idx = (idx + 1) % bucket.length;
            }
        }

        return false;
    }
}
