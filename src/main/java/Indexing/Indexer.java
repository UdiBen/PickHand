package Indexing;

import Beans.IMapper;
import Beans.IndexDto;
import DataAccess.DataProvider;
import DataAccess.IDataProvider;
import Domain.Game;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.bootstrap.ElasticSearch;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;

import java.io.IOException;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 10/01/14
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class Indexer {
    private static ElasticSearch elasticSearch = new ElasticSearch();
    private static final String INDEX_NAME = "pickhand";
    private static final String TYPE = "hand";

    private IDataProvider dataProvider;
    private IMapper mapper;

    public static void main(String[] args) {
        Indexer indexer = new Indexer(new DataProvider());
        indexer.start();
    }

    public Indexer(IDataProvider dataProvider) {
        this.dataProvider = dataProvider;
        this.mapper = mapper;
    }

    private void start() {
        // on startup

        Node node = nodeBuilder()
                .clusterName("pickhand_cluster")
                .client(true)
                .node();
        Client client = node.client();

        createIndex(client);

        List<IndexDto> documents = getHands();

        index(documents, node);

        // on shutdown

        client.admin().indices().prepareRefresh().execute().actionGet();
        node.close();
    }

    private void index(List<IndexDto> documents, Node node) {
        BulkRequestBuilder bulk = node.client().prepareBulk();

        for (IndexDto doc : documents) {
            bulk.add(new IndexRequest(INDEX_NAME, TYPE, String.valueOf(doc.getHandId()))
                    .source(Build(doc)));
        }

        bulk.execute().actionGet();
    }

    private XContentBuilder Build(IndexDto doc) {
        try {
            return jsonBuilder()
                    .startObject()
                        .field("id", doc.getHandId())
                        .field("gameId", doc.getGameId())
                        .field("videoLink", doc.getVideoLink())
                    .endObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createIndex(Client client) {
        client.delete(new DeleteRequest(INDEX_NAME));
        client.prepareIndex(INDEX_NAME, TYPE).setIndex(INDEX_NAME);
    }

    public List<IndexDto> getHands() {
        return flatten(Collections2.transform(dataProvider.GetGames(), new Function<Game, List<IndexDto>>() {
            public List<IndexDto> apply(Game game) {
                return mapper.toIndexDtos(game);
            }
        }));
    }

    public <T> List<T> flatten(Collection<List<T>> input) {
        List<T> result = new ArrayList<T>();

        for (List<T> o : input) {
            for (T i : o) {
                result.add(i);
            }
        }

        return result;
    }
}
