package Beans;

import Domain.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 10/01/14
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class IndexDto {
    private long gameId;
    private long handId;
    private int startTimeInSec;
    private int endTimeInSec;
    private List<PlayerInRound> players;
    private List<Round> round;
    private GameFormat gameFormat;
    private String videoLink;
    private String title;
    private boolean live;

    public IndexDto(Game game, Hand hand) {
        this.gameId = game.getId();
        this.gameFormat = game.getFormat();
        this.videoLink = game.getVideoLink();
        this.title = game.getTitle();
        this.live = game.isLive();
        this.handId = hand.getId();
        this.startTimeInSec = hand.getStartTimeInSec();
        this.endTimeInSec = hand.getEndTimeInSec();
        this.players = hand.getPlayers();
        this.round = hand.getRound();
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

    public GameFormat getGameFormat() {
        return gameFormat;
    }

    public void setGameFormat(GameFormat gameFormat) {
        this.gameFormat = gameFormat;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}

