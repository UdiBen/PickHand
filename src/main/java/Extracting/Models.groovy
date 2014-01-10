package Extracting
/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 28/12/13
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */

class Game {
    public Game() {
    }

    public Game(long id, String videoLink, String title, GameFormat format, boolean isLive) {
        this.id = id
        this.videoLink = videoLink
        this.title = title
        this.format = format
        this.isLive = isLive
    }

    long id;
    String videoLink;
    String title;
    GameFormat format;
    boolean isLive;
}

enum GameFormat
{
    CASH, TOURNAMENT

    public static GameFormat fromString(String str) {
        if (str.toLowerCase().equals("tournament"))
            return TOURNAMENT;
        else
            return CASH;
    }
}

class Hand {
    long gameId;
    long handId
    int startTimeInSec;
    int endTimeInSec;
    List<PlayerInRound> players;
    List<Round> round;
}

class Player {
    long id;
    String Name;
}

class PlayerInRound {
    long id;
    int position;
    Integer stack;
    List<String> cards;
}

class Round {
    RoundType type;
    List<String> cards;
    int pot;
    List<RoundAction> round;
}

enum RoundType
{
    PRE_FLOP, FLOP, TURN, RIVER;
}

class RoundAction {
    long playerId;
    ActionType actionType;
    int betAmount;
}

enum ActionType
{
    FOLD, CHECK, RAISE;
}
