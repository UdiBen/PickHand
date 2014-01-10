package Beans;

import Domain.PlayerInHand;
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
    List<PlayerInHand> players;
    List<Round> round;
}

