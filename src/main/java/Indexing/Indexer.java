package Indexing;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.bootstrap.ElasticSearch;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.*;

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

    public static void main(String[] args){
        Indexer indexer = new Indexer();
        indexer.start();
    }

    private void start() {
        // on startup

        Node node = nodeBuilder()
                .clusterName("pickhand_cluster")
                .client(true)
                .node();
        Client client = node.client();

        createIndex(client);

        List<Map<String, String>> documents = getDocuments();

        index(documents, node);

        // on shutdown

        client.admin().indices().prepareRefresh().execute().actionGet();
        node.close();
    }

    private void index(List<Map<String, String>> documents, Node node) {
        BulkRequestBuilder bulk = node.client().prepareBulk();
        for (Map<String,String> doc : documents)
        {
            bulk.add(new IndexRequest(INDEX_NAME, TYPE).source(doc));
        }

        bulk.execute().actionGet();
    }

    private List<Map<String, String>> getDocuments() {
//        XContentBuilder doc= jsonBuilder()
//                                        .startObject()
//                .;
//        doc.put("id", "1");
//        doc.put("stam", "aaa");

        return Arrays.asList(null);
    }

    private void createIndex(Client client) {
        client.delete(new DeleteRequest(INDEX_NAME));
        client.prepareIndex(INDEX_NAME, TYPE).setIndex(INDEX_NAME);
    }
}
