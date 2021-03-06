package Indexing;

import Beans.HandIndexDto;
import Beans.IMapper;
import DataAccess.IDataProvider;
import Domain.Game;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.bootstrap.ElasticSearch;
import org.elasticsearch.client.Client;
import org.elasticsearch.indices.IndexMissingException;
import org.elasticsearch.node.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 10/01/14
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class Indexer implements Runnable {
    private static ElasticSearch elasticSearch = new ElasticSearch();
    private static final String INDEX_NAME = "pickhand";
    private static final String TYPE = "hand";

    private Logger logger = Logger.getLogger(Indexer.class.getName());

    private IDataProvider dataProvider;
    private IMapper mapper;

    public static void main(String[] args) {
//        Indexer indexer = new Indexer(new DataProvider(), new Mapper());
//        indexer.start();
    }

    public Indexer(IDataProvider dataProvider, IMapper mapper) {
        this.dataProvider = dataProvider;
        this.mapper = mapper;
    }

    public void run() {
        // on startup

        Node node = nodeBuilder()
                .clusterName("pickhand_cluster")
                .client(true)
                .node();
        try {
            Client client = node.client();

            deleteExistingIndex(client);
            createIndex(client);

            List<HandIndexDto> documents = getHands();

            index(documents, node);

            // on shutdown

            client.admin().indices().prepareRefresh().execute().actionGet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            node.close();
        }
    }

    private void deleteExistingIndex(Client client) {
        try {
            client.admin().indices().delete(new DeleteIndexRequest(INDEX_NAME)).actionGet();
        } catch (IndexMissingException ex) {
            // It's ok
        }
    }

    private void index(List<HandIndexDto> documents, Node node) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        BulkRequestBuilder bulk = node.client().prepareBulk();

        for (HandIndexDto doc : documents) {
            bulk.add(new IndexRequest(INDEX_NAME, TYPE, String.valueOf(doc.getHandId()))
                    .source(mapper.writeValueAsString(doc)));

            if (bulk.numberOfActions() > 50) {
                bulk.execute().actionGet();
                bulk = node.client().prepareBulk();
            }
        }

        if (bulk.numberOfActions() > 50)
            bulk.execute().actionGet();
    }

    private void createIndex(Client client) {
        client.prepareIndex(INDEX_NAME, TYPE).setIndex(INDEX_NAME);
    }

    public List<HandIndexDto> getHands() {
        return flatten(Collections2.transform(dataProvider.GetGames(), new Function<Game, List<HandIndexDto>>() {
            public List<HandIndexDto> apply(Game game) {
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
