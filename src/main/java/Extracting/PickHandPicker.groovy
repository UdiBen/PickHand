import Beans.Mapper
import DataAccess.DataProvider
import Extracting.ExcelBuilder
import Indexing.Indexer
import Domain.*

/**
 * Created by Noga on 28/12/13.
 */
class PickHandPicker {
    static Map<Long, Game> games = new HashMap<>();
    static Map<Long, Hand> hands = new HashMap<>();
    static Map<String, Player> playersByName = new HashMap<>()
    static long playerIdIndex = 1;

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

        updateHandsBettingTypeData()

        def indexer = new Indexer(new DataProvider(games, hands), new Mapper());
        new Thread(indexer).start();
        println("FINISHED!!!")
    }

    static def updateHandsBettingTypeData() {
        hands.values().each {
            hand ->
            Map<Integer, Long> positionToPlayerId = new HashMap<>()
            hand.getPlayers().each {
                player ->
                positionToPlayerId.put(player.getPosition(), player.getId());
            }

            hand.getRounds().values().each {
                round ->
                Map<Long, List<RoundAction>> playerIdToActions = new HashMap<>()
                Map<Long, Integer> playerIdActionIndex = new HashMap<>()
                round.getRoundActions().each {
                    action ->
                    def playerId = action.getPlayerId()
                    if (!playerIdToActions.containsKey(playerId)) {
                        playerIdToActions.put(playerId, new ArrayList<RoundAction>());
                    }
                    playerIdToActions.get(playerId).add(action)
                    playerIdActionIndex.put(playerId, 0);
                }


                def numberOfPlayersInRound = playerIdToActions.size()
                def currentPosition = 1;
                int maxAmount = 0;

                if (round.getType() == RoundType.PRE_FLOP) {
                    maxAmount = hand.bigBlind
                    if (numberOfPlayersInRound < 3) {
                        if (numberOfPlayersInRound == 3) {
                            currentPosition = 0;
                        } else {
                            currentPosition = 1;
                        }
                    } else {
                        currentPosition = 3;
                    }
                }
                List<RoundAction> orderedRoundAction = new ArrayList<RoundAction>()
                while (playerIdToActions.size() > 0) {
                    def playerId = positionToPlayerId.get(currentPosition);
                    def actions = playerIdToActions.get(playerId)

                    if (actions == null) {
                        currentPosition = calcNextPosition(currentPosition, positionToPlayerId.size())
                        continue;
                    };
                    def actionIndex = playerIdActionIndex.get(playerId);

                    if (actionIndex == actions.size()) {
                        playerIdToActions.remove(playerId)
                    }
                    else {
                        def action = actions.get(actionIndex);

                        def amount = action.betAmount
                        if (amount == 0) {
                            if (maxAmount > 0)
                                action.actionType = ActionType.FOLD;
                            else
                                action.actionType = ActionType.CHECK;
                        } else if (amount == maxAmount) {
                            action.actionType = ActionType.CALL;
                        } else if (amount > maxAmount) {
                            maxAmount = amount;
                            action.actionType = ActionType.RAISE;
                        }
                        playerIdActionIndex.put(playerId, ++actionIndex)
                        orderedRoundAction.add(action);
                    }

                    currentPosition = calcNextPosition(currentPosition, positionToPlayerId.size())
                }
                round.setRoundActions(orderedRoundAction);
            }
        }
    }

    private static int calcNextPosition(int currentPosition, int numberOfPlayersInHand) {
        currentPosition++;
        if (currentPosition == numberOfPlayersInHand)
            currentPosition = 0;

        return currentPosition
    }


    private static void ExtractGAMES() {
        new ExcelBuilder("data\\POKER_EPISODES_GAMES.xls").eachLine {
            def id = cell(0)
            if (id == null || id.equals(""))
                return;

            Game game = new Game(id as long, new ArrayList<Hand>(), cell(1), cell(2), GameFormat.fromString(cell(3)), true);
            games.put(game.id, game);
        }
    }

    private static void ExtractHANDS() {
        new ExcelBuilder("data\\POKER_EPISODES_HANDS.xls").eachLine {
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


            long gameId = cell(1) as long;
            Hand hand = new Hand(id as long, gameId, startTimeInSec, endTimeInSec, smallBlind, bigBlind, ante, new ArrayList<PlayerInHand>(), rounds);

            hands.put(hand.handId, hand);
            Game game = games.get(gameId);

            if (game != null)
                game.getHands().add(hand);

        }
    }

    private static void ExtractPLAYERS() {
        new ExcelBuilder("data\\POKER_EPISODES_PLAYERS.xls").eachLine {
            def handId = cell(0)
            if (handId == null || handId.equals(""))
                return;

            Hand hand = hands.get(handId as Long);
            if (hand == null) {
                println("Dind not find hand with Id: ${handId}")
                return;
            }

            String playerName = cell(1)
            Player player = playersByName.get(playerName)

            if (player == null) {
                player = new Player(playerIdIndex, playerName)
                playerIdIndex++
                playersByName.put(playerName, player)
            }

            StackType stack = StackType.UNDEFINED;

            String stackType = cell(2)
            if (stackType != null && !stackType.trim().equals(""))
                stack = StackType.valueOf(stackType.toUpperCase())

            int position = cell(3)
            List<String> cards = getCardsFromString(cell(4))

            PlayerInHand playerInHand = new PlayerInHand(player.getId(), position, stack, cards)

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

    static def extractRoundActions(String roundActionsStr, RoundType roundType, Hand hand, Player player) {
        def round = hand.getRounds().get(roundType)

        if (round == null)
            return;

        List<RoundAction> actions = round.getRoundActions();
        if (actions == null) {
            actions = new ArrayList<>();
        }

        if (roundActionsStr != null && !roundActionsStr.equals("")) {
            List<RoundAction> roundActions = extractRoundActionsFromUser(roundActionsStr, player.getId())
            actions.addAll(roundActions);
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
                int bet = (int) (action as double);
                actionList.add(new RoundAction(playerId, ActionType.UNDEFINED, bet))
            } catch (Exception e) {
                println("Could not parse bet of player with Id ${playerId}, actions were: ${actions}")
                return actionList;
            }
        }

        return actionList;
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
