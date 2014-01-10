package Domain;

import Domain.PlayerInRound;
import Domain.Round;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 10/01/14
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class Hand {
    private long gameId;
    private long handId;
    private int startTimeInSec;
    private int endTimeInSec;
    private List<PlayerInRound> players;
    private List<Round> round;

    public Hand(long gameId, long handId, int startTimeInSec, int endTimeInSec, List<PlayerInRound> players, List<Round> round) {
        this.gameId = gameId;
        this.handId = handId;
        this.startTimeInSec = startTimeInSec;
        this.endTimeInSec = endTimeInSec;
        this.players = players;
        this.round = round;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getHandId() {
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

    public List<PlayerInRound> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerInRound> players) {
        this.players = players;
    }

    public List<Round> getRound() {
        return round;
    }

    public void setRound(List<Round> round) {
        this.round = round;
    }
}

