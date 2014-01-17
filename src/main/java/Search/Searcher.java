package Search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.bootstrap.ElasticSearch;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 17/01/14
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class Searcher {
    private static ElasticSearch elasticSearch = new ElasticSearch();
    private static final String INDEX_NAME = "pickhand";
    private static final String TYPE = "hand";

    public Searcher() {
        init();
    }

    private void init() {

    }

    public List<SearchResult> Search(String player, String hand) {
        Client client = getClient();

        SearchRequestBuilder builder = client
                .prepareSearch(INDEX_NAME)
                .setQuery(
                        QueryBuilders
                                .boolQuery()
                                .must(QueryBuilders.termQuery("player", player))
                                .must(QueryBuilders.termQuery("hand", hand)));
        return Map(client
                .search(new SearchRequest().source(builder.internalBuilder()))
                .actionGet());
    }

    private List<SearchResult> Map(SearchResponse searchResponse) {
        SearchHit[] hits = searchResponse.getHits().hits();
        List<SearchResult> results = new ArrayList<SearchResult>();
        for (SearchHit hit : hits){
            results.add(new SearchResult(Long.valueOf(hit.getId())));
        }
        return results;
    }

    private Client getClient() {
        return nodeBuilder()
                .clusterName("pickhand_cluster")
                .client(true)
                .node().client();
    }
}
