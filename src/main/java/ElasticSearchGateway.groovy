import org.elasticsearch.groovy.client.GClient
import org.elasticsearch.groovy.node.GNode
import org.elasticsearch.groovy.node.GNodeBuilder

import static org.elasticsearch.groovy.node.GNodeBuilder.nodeBuilder
import org.elasticsearch.groovy.common.xcontent.GXContentBuilder
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse

/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 28/12/13
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
class ElasticSearchGateway {
    GNode node

    ElasticSearchGateway() {
        GXContentBuilder.rootResolveStrategy = Closure.DELEGATE_FIRST; // required to use a closure as settings

        GNodeBuilder nodeBuilder = nodeBuilder();
        nodeBuilder.settings {
            node {
                client = true
            }

            cluster {
                name = "pickhand_cluster"
            }
        }

        node = nodeBuilder.node()
    }

    public close() {
        node.stop().close()
    }
}