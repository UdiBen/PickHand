import Domain.ActionType
import Domain.Game
import Domain.GameFormat
import Domain.Hand
import Domain.Player
import Domain.PlayerInHand
import Domain.Round
import Domain.RoundAction
import Domain.RoundType
import Domain.StackType
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
        ExtractPLAYERS()

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
            Map<RoundType, Round> rounds = new HashMap<>();

            int smallBlind = cell(9);
            int bigBlind = cell(10);
            int ante = cell(11);

            rounds.put(RoundType.PRE_FLOP, new Round(RoundType.PRE_FLOP, new ArrayList<String>(), 0, new ArrayList<RoundAction>()));


            def flop = cell(6)
            if (flop != null && !flop.equals("")) {
                rounds.put(RoundType.FLOP, new Round(RoundType.FLOP, getCardsFromString(flop), 0, new ArrayList<RoundAction>()))

                def turn = cell(7)
                if (turn != null && !turn.equals("")) {
                    rounds.put(RoundType.TURN, new Round(RoundType.TURN, getCardsFromString(turn), 0, new ArrayList<RoundAction>()));

                    def river = cell(8)
                    if (river != null && !river.equals(""))
                        rounds.put(RoundType.RIVER, new Round(RoundType.RIVER, getCardsFromString(river), 0, new ArrayList<RoundAction>()))
                }
            }

            Hand hand = new Hand(id as long, cell(1) as long, startTimeInSec, endTimeInSec, smallBlind, bigBlind, ante, new ArrayList<PlayerInHand>(), rounds);

            hands.put(hand.handId, hand);
        }
    }

    private static void ExtractPLAYERS() {
        Map<String, Player> playersByName = new HashMap<>()
        long playerIdIndex = 1;
        new ExcelBuilder("C:\\Amit\\POKER_EPISODES_PLAYERS.xls").eachLine {
            def handId = cell(0)
            if (handId == null || handId.equals(""))
                return;

            Hand hand = hands.get(handId as long);
            if (hand == null) {
                println("Dind not find hand with Id: ${handId}")
                return;
            }

            String playerName = cell(1)
            Player player = playerName.get(playerName)

            if (player == null) {
                player = new Player(playerIdIndex, playerName)
                playerIdIndex++
                playersByName.put(playerName, player)
            }

            String stackType = cell(2)
            StackType stack = StackType.valueOf(stackType)

            int position = cell(3)
            List<String> cards = getCardsFromString(cell(4))

            PlayerInHand playerInHand = new PlayerInHand(player.getId(), position, stackType, cards)

            hand.players.add(playerInHand)

            String preFlopActions = cell(5)
            extractRoundActions(preFlopActions, RoundType.PRE_FLOP, hand, player)

            String flopActions = cell(6)
            extractRoundActions(flopActions, RoundType.FLOP, hand, player)

            String turnActions = cell(7)
            extractRoundActions(turnActions, RoundType.TURN, hand, player)

            String riverActions = cell(8)
            extractRoundActions(riverActions, RoundType.RIVER, hand, player)

        }
    }

    static def extractRoundActions(String roundActionsStr, RoundType roundType, Hand hand, Player player)
    {
        if (roundActionsStr == null || roundActionsStr.equals("")) {
            List<RoundAction> roundActions = extractRoundActionsFromUser(roundActionsStr, player.getId())
            List<RoundAction> actions = hand.getRounds().get(RoundType.PRE_FLOP).getRoundActions();
            if (actions == null) {
                actions = new ArrayList<>();
            }

            actions.addAll(roundActionsStr);
        }
    }

    static def extractRoundActionsFromUser(String actions, long playerId) {
        List<RoundAction> actionList = new ArrayList<>()

        String[] playerActions = actions.trim().split(",");

        if (playerActions == null || playerActions.size() == 0) {
            return actionList;
        }

        playerActions.each {
            action ->
                try {
                    int bet = Integer.valueOf(action);
                    actionList.add(new RoundAction(playerId, ActionType.UNDEFINED, bet))
                } catch (Exception e) {
                    println("Could not parse bet of player with Id ${playerId}, actions were: ${actions}")
                    return actionList;
                }
                return actionList;
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
}
