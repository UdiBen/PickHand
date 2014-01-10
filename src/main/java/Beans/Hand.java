package Beans;

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
    long gameId;
    long handId;
    int startTimeInSec;
    int endTimeInSec;
    List<PlayerInRound> players;
    List<Round> round;
}

