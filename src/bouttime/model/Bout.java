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

import org.apache.log4j.Logger;

/**
 * A class to model a Bout entity.
 */
public class Bout {
    static Logger logger = Logger.getLogger(Bout.class);

    public static final String ROUND_1 = "1";
    public static final String ROUND_2 = "2";
    public static final String ROUND_3 = "3";
    public static final String ROUND_4 = "4";
    public static final String ROUND_5 = "5";
    public static final String ROUND_6 = "6";
    public static final String ROUND_7 = "7";
    public static final String ROUND_8 = "8";
    public static final String ROUND_9 = "9";
    public static final String ROUND_10 = "10";
    public static final String ROUND_BRACKET2_FINAL = ROUND_1;
    public static final String ROUND_BRACKET4_FINAL = ROUND_2;
    public static final String ROUND_BRACKET4_SEMIFINAL = ROUND_1;
    public static final String ROUND_BRACKET4_2ND = ROUND_3;
    public static final String ROUND_BRACKET8_FINAL = ROUND_4;
    public static final String ROUND_BRACKET8_SEMIFINAL = ROUND_2;
    public static final String ROUND_BRACKET8_QUARTERFINAL = ROUND_1;
    public static final String ROUND_BRACKET8_2ND = ROUND_5;
    public static final String ROUND_BRACKET16_FINAL = ROUND_6;
    public static final String ROUND_BRACKET16_SEMIFINAL = ROUND_4;
    public static final String ROUND_BRACKET16_QUARTERFINAL = ROUND_2;
    public static final String ROUND_BRACKET16_2ND = ROUND_7;
    public static final String ROUND_BRACKET32_FINAL = ROUND_8;
    public static final String ROUND_BRACKET32_SEMIFINAL = ROUND_6;
    public static final String ROUND_BRACKET32_QUARTERFINAL = ROUND_4;
    public static final String ROUND_BRACKET32_2ND = ROUND_9;
    public static final String FIFTH_PLACE = "5th";
    public static final String FIRST_PLACE = "A";
    public static final String SECOND_PLACE = "2nd";
    public static final String THIRD_PLACE = "Z";

    private String      label;
    private Integer     id;
    private int         sequence;
    private String      boutNum;
    private String      boutTime;
    private Wrestler    red;
    private Wrestler    green;
    private Wrestler    winner;
    private Wrestler    loser;
    private String      finalResult;
    private String      round;
    private Group       group;
    private Bout        winnerNextBout;
    private Bout        loserNextBout;
    private boolean     bye;
    private boolean     consolation;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////
    public Bout(Wrestler red, Wrestler green, Group g, String round,
            String label, Bout winNext, Bout loseNext) {
        this(red, green, g, round, 0, label);
        this.winnerNextBout = winNext;
        this.loserNextBout = loseNext;
    }

    public Bout(Wrestler red, Wrestler green, Group g, String round, int sequence) {
        this.red = red;
        this.green = green;
        this.group = g;
        this.round = round;
        this.sequence = sequence;
        this.bye = false;
    }

    public Bout(Wrestler red, Wrestler green, Group g, String round, int sequence,
            String label) {
        this(red, green, g, round, sequence);
        this.label = label;
    }

    public Bout(Wrestler red, Wrestler green, Group g, String round,
            String label) {
        this(red, green, g, round, 0, label);
    }

    public Bout(Wrestler red, Wrestler green, Group g, String round, int sequence,
            String label, boolean b) {
        this(red, green, g, round, sequence, label);
        this.bye = b;
    }

    public Bout(Wrestler red, Wrestler green, Group g, String round, int sequence,
            String label, boolean b, boolean c) {
        this(red, green, g, round, sequence, label, b);
        this.consolation = c;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Standard getters and setters
    ////////////////////////////////////////////////////////////////////////////
    
    public String getFinalResult() {return finalResult;}
    public void setFinalResult(String s) {this.finalResult = s;}

    public Wrestler getGreen() {return green;}
    public void setGreen(Wrestler w) {this.green = w;}

    public Group getGroup() {return group;}
    public void setGroup(Group g) {this.group = g;}

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public int getSequence() {return sequence;}
    public void setSequence(int sequence) {this.sequence = sequence;}

    public String getBoutTime() {return boutTime;}
    public void setBoutTime(String s) {this.boutTime = s;}

    public String getLabel() {return label;}
    public void setLabel(String s) {this.label = s;}

    public Bout getLoserNextBout() {return loserNextBout;}
    public void setLoserNextBout(Bout b) {this.loserNextBout = b;}

    public Wrestler getRed() {return red;}
    public void setRed(Wrestler w) {this.red = w;}

    public String getRound() {return round;}
    public void setRound(String s) {this.round = s;}

    public Wrestler getLoser() {return loser;}
    public Wrestler getWinner() {return winner;}

    /**
     * Set the winner based on the first initial last name string.
     * @param s The given string
     */
    public void setWinner(String s) {
        if (this.red.getFirstInitialLastName().equalsIgnoreCase(s)) {
            setWinner(this.red);
        } else if (this.green.getFirstInitialLastName().equalsIgnoreCase(s)) {
            setWinner(this.green);
        } else {
            logger.error("Winner is not valid : s=[" + s + "]");
        }
    }

    public void setWinner(Wrestler w) {
        if ((w != null) && (w != this.red) && (w != this.green)) {
            logger.error("Winner is not valid\n" +
                    "w=[" + w + "], red=[" + this.red + "], green=[" + this.green + "]");
            return;
        }

        if ((w != null) && w.isScratched()) {
            logger.error("Attempted to set a scratched wrestler as a winner : " + w);
            return;
        }
        
        if (w != null) {
            this.winner = w;
            this.loser = (w == this.red) ? this.green : this.red;
            logger.debug("winner=[" + w + ", loser=[" + loser + "]");
        } else {
            this.winner = new Wrestler();
            this.loser = this.winner;
            logger.debug("w is null");
        }
        
        advanceWrestlers();
    }

    public Bout getWinnerNextBout() {return winnerNextBout;}
    public void setWinnerNextBout(Bout b) {this.winnerNextBout = b;}

    public String getBoutNum() {return (boutNum != null) ? boutNum : "";}
    public void setBoutNum(String boutNum) {this.boutNum = boutNum;}

    public boolean isBye() {return this.bye;}
    public boolean getBye() {return this.bye;}
    public void setBye(boolean b) {this.bye = b;}

    public boolean isConsolation() {return this.consolation;}
    public boolean getConsolation() {return this.consolation;}
    public void setConsolation(boolean b) {this.consolation = b;}

    /**
     * Advance the winner and the loser to their next bouts.
     */
    private void advanceWrestlers() {
        // Advance the winner
        if (this.winnerNextBout != null) {
            setWrestlerEven2Green(this.winnerNextBout, this.winner);

            if (this.winnerNextBout.isBye()) {
                this.winnerNextBout.setWinner(this.winner);
            }
        }

        // Don't advance a scratched wrestler
        if ((this.loser != null) && this.loser.isScratched()) {
            return;
        }

        // Advance the loser
        if (!this.isBye() && (this.loserNextBout != null)) {
            if (Group.BRACKET_TYPE_16MAN_DOUBLE.equalsIgnoreCase(this.group.getBracketType())) {
                // 16-man bracket, semi-final losers advance to the same
                // sequence bout in Round 5 (back-side).  So we need to
                // set the Red/Green different so they don't clash with how
                // the winners from the Round 4 back-side bouts are set.
                if (this.round.equalsIgnoreCase(ROUND_BRACKET16_SEMIFINAL) && !isConsolation()) {
                    setWrestlerEven2Red(this.loserNextBout, this.loser);
                } else {
                    setWrestlerEven2Green(this.loserNextBout, this.loser);
                }
            } else {
                setWrestlerEven2Green(this.loserNextBout, this.loser);
            }

            if (this.loserNextBout.isBye()) {
                this.loserNextBout.setWinner(this.loser);
            }
        }
    }

    /**
     * Advance bouts with even-numbered sequences to "Green".
     * Advance bouts with odd-numbered sequences to "Red".
     * 
     * @param b the bout to set the wrestler to
     * @param w the wrestler to set
     */
    private void setWrestlerEven2Green(Bout b, Wrestler w) {
        if ((this.sequence % 2) == 0) {
            b.setGreen(w);
        } else {
            b.setRed(w);
        }
    }

    /**
     * Advance bouts with even-numbered sequences to "Red".
     * Advance bouts with odd-numbered sequences to "Green".
     *
     * @param b the bout to set the wrestler to
     * @param w the wrestler to set
     */
    private void setWrestlerEven2Red(Bout b, Wrestler w) {
        if ((this.sequence % 2) == 0) {
            b.setRed(w);
        } else {
            b.setGreen(w);
        }
    }
}
