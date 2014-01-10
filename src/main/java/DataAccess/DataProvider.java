package DataAccess;

import Domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataProvider implements IDataProvider{

    public List<Game> GetGames() {
        return Arrays.asList(getGame());
    }

    private Game getGame() {
        return new Game(1, getHands(), "youtube.com", "cool", GameFormat.TOURNAMENT, true);
    }

    private List<Hand> getHands() {
        return Arrays.asList(new Hand(1, 1, 10, 120, new ArrayList<PlayerInRound>(), new ArrayList<Round>()
        ));
    }
}