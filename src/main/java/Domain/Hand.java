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
    private long gameId;
    private long handId;
    private int startTimeInSec;
    private int endTimeInSec;
    private List<PlayerInHand> players;
    private Map<RoundType, Round> rounds;

    public Hand(long gameId, long handId, int startTimeInSec, int endTimeInSec, List<PlayerInHand> players, Map<RoundType, Round> round) {
        this.gameId = gameId;
        this.handId = handId;
        this.startTimeInSec = startTimeInSec;
        this.endTimeInSec = endTimeInSec;
        this.players = players;
        this.rounds = round;
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
}

