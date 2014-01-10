
/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 28/12/13
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */
class Indexer {

    public static void main(String[] str){
        def gateway = new ElasticSearchGateway()

        def games = getGames();

        games.each{
            game->
                gateway.node.client.index {
                    index = "pickhand"
                    type = "game"
                    source {
                        game
                    }
                }
        }
    }
}
