package DataAccess;

import Domain.*;

import java.util.*;

public class DataProvider implements IDataProvider{

    private Collection<Game> games;

    public DataProvider(Map<Long, Game> games, Map<Long, Hand> hands) {
        for(Hand hand : hands.values()){
            Game game = games.get(hand.getGameId());
            if (game.getHands() == null)
                game.setHands(new ArrayList<Hand>());
            game.getHands().add(hand);
        }

        this.games = games.values();
    }

    public Collection<Game> GetGames() {
        return games;
    }
}
