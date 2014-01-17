package Domain;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 10/01/14
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class Hand {
    private long handId;
    private long gameId;
    private int startTimeInSec;
    private int endTimeInSec;
    private int smallBlind;
    private int bigBlind;
    private int ante;
    private List<PlayerInHand> players;
    private Map<RoundType, Round> rounds;

    public Hand() {
    }

    public Hand(long handId, long gameId, int startTimeInSec, int endTimeInSec, int smallBlind, int bigBlind, int ante, List<PlayerInHand> players, Map<RoundType, Round> rounds) {
        this.handId = handId;
        this.gameId = gameId;
        this.startTimeInSec = startTimeInSec;
        this.endTimeInSec = endTimeInSec;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.ante = ante;
        this.players = players;
        this.rounds = rounds;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getId() {
        return handId;
    }

    public void setHandId(long handId) {
        this.handId = handId;
    }

    public int getStartTimeInSec() {
        return startTimeInSec;
    }

    public void setStartTimeInSec(int startTimeInSec) {
        this.startTimeInSec = startTimeInSec;
    }

    public int getEndTimeInSec() {
        return endTimeInSec;
    }

    public void setEndTimeInSec(int endTimeInSec) {
        this.endTimeInSec = endTimeInSec;
    }

    public List<PlayerInHand> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerInHand> players) {
        this.players = players;
    }

    public Map<RoundType, Round> getRounds() {
        return rounds;
    }

    public void setRounds(Map<RoundType, Round> rounds) {
        this.rounds = rounds;
    }

    public long getHandId() {
        return handId;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public void setSmallBlind(int smallBlind) {
        this.smallBlind = smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(int bigBlind) {
        this.bigBlind = bigBlind;
    }

    public int getAnte() {
        return ante;
    }

    public void setAnte(int ante) {
        this.ante = ante;
    }
}

