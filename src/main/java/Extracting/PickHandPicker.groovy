import Extracting.ExcelBuilder

/**
 * Created by Noga on 28/12/13.
 */
class PickHandPicker {
    static Map<Long, Game> games = new HashMap<>();
    static Map<Long, Hand> hands = new HashMap<>();

    public static void main(String[] args) {

        ExtractGAMES()
        ExtractHANDS()

        games.values().each {
            game ->
                println("${game.id}, ${game.title}");
        }

        hands.values().each {
            hand ->
                println("${hand.handId}, ${hand.gameId}, ${hand.endTimeInSec}");
        }

    }

    private static void ExtractGAMES() {
        new ExcelBuilder("C:\\Amit\\POKER_EPISODES_GAMES.xls").eachLine {
            def id = cell(0)
            if (id == null || id.equals(""))
                return;

            Game game = new Game(id as long, cell(1), cell(2), GameFormat.fromString(cell(3)), true);
            games.put(game.id, game);
        }
    }

    private static void ExtractHANDS() {
        new ExcelBuilder("C:\\Amit\\POKER_EPISODES_HANDS.xls").eachLine {
            def id = cell(0)
            if (id == null || id.equals(""))
                return;

            int startTimeInSec = (cell(2) as int) * 60 + (cell(3) as int);
            int endTimeInSec = (cell(4) as int) * 60 + (cell(5) as int);
            List<Round> rounds = new ArrayList<>();

            int smallBlind = cell(9);
            int bigBlind = cell(10);
            int ante = cell(11);

            rounds.add(new Round(RoundType.PRE_FLOP, new ArrayList<String>(), 0, new ArrayList<RoundAction>()));


            def flop = cell(6)
            if (flop != null && !flop.equals("")) {
                rounds.add(new Round(RoundType.FLOP, getCardsFromString(flop), 0, new ArrayList<RoundAction>()))

                def turn = cell(7)
                if (turn != null && !turn.equals("")) {
                    rounds.add(new Round(RoundType.TURN, getCardsFromString(turn), 0, new ArrayList<RoundAction>()));

                    def river = cell(8)
                    if (river != null && !river.equals(""))
                        rounds.add(new Round(RoundType.RIVER, getCardsFromString(river), 0, new ArrayList<RoundAction>()))
                }
            }

            Hand hand = new Hand(id as long, cell(1) as long, startTimeInSec, endTimeInSec, smallBlind, bigBlind, ante, new ArrayList<PlayerInHand>(), rounds);

            hands.put(hand.handId, hand);
        }
    }

    static List<String> getCardsFromString(String cardsString) {
        List<String> cards = new ArrayList<>();
        String currentCard = new String();

        def chars = cardsString.chars;
        for (def i = 0; i < chars.size(); i++) {
            def currChar = cardsString.charAt(i)
            currentCard += currChar;
            if (i % 2 != 0) {
                if (currChar.equals("h" as char) || currChar.equals("d" as char) || currChar.equals("s" as char) || currChar.equals("c" as char))
                    cards.add(currentCard);
                else
                    println("Got A CARD of bad format and did not add it to the cards game: ${currentCard}");

                currentCard = "";
            }
        }

        return cards;
    }

    static class Game {
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

    static class Hand {
        Hand(long handId, long gameId, int startTimeInSec, int endTimeInSec, int smallBlind, int bigBlind, int ante, List<PlayerInHand> players, List<Round> round) {
            this.handId = handId
            this.gameId = gameId
            this.startTimeInSec = startTimeInSec
            this.endTimeInSec = endTimeInSec
            this.smallBlind = smallBlind
            this.bigBlind = bigBlind
            this.ante = ante
            this.players = players
            this.round = round
        }
        long handId
        long gameId;
        int startTimeInSec;
        int endTimeInSec;
        int smallBlind;
        int bigBlind;
        int ante;
        List<PlayerInHand> players;
        List<Round> round;
    }

    class Player {
        long id;
        String Name;
    }

    class PlayerInHand {
        long id;
        int position;
        Integer stack;
        List<String> cards;
    }

    static class Round {
        RoundType type;
        List<String> cards;
        int pot;
        List<RoundAction> round;

        Round(RoundType type, List<String> cards, int pot, List<RoundAction> round) {
            this.type = type
            this.cards = cards
            this.pot = pot
            this.round = round
        }
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
}
