package cn.yano.samples.elastic.java.service;

import cn.yano.modules.utils.encrypt.MD5Utils;
import cn.yano.modules.utils.es.ElasticConnectionPool;
import cn.yano.samples.elastic.java.config.AppConfig;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Elastic服务类
 * Created by xieyangyang0636 on 2020/2/2.
 **/
public class ElasticService {

    /**
     * LOGGER
     */
    private final static Logger logger = LoggerFactory.getLogger(ElasticService.class);

    /**
     * ElasticConnectionPool
     */
    private ElasticConnectionPool elasticConnectionPool;

    /**
     * 构造函数
     *
     * @param nodes       elastic 集群hosts and port
     * @param clusterName elastic 集群名称
     * @param maxActive   elastic 最大活跃连接数
     * @param minIdle     elastic 最小空闲连接数
     */
    public ElasticService(String nodes, String clusterName, int maxActive, int minIdle) {
        elasticConnectionPool = new ElasticConnectionPool(nodes, clusterName, maxActive, minIdle);
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (elasticConnectionPool != null) {
            elasticConnectionPool.close();
        }
    }

    /**
     * 将标签结果更新到es
     *
     * @param news
     * @param summary
     * @param titleCut
     * @param contentCut
     * @return 标签数组转json字符串
     */
    public void saveOrUpdate(JSONObject news, String summary, String titleCut, String contentCut) {
        TransportClient client = null;
        try {
            // 获取连接
            client = elasticConnectionPool.getConnection();

            String esId = MD5Utils.getMd5(news.getString("news_id"));

            SearchResponse searchResponse = client.prepareSearch("yano")
                    .setTypes("default")
                    //实际返回的数量为10*index的主分片数
                    .setSize(2 * 5)
                    //查询的字段名及值
                    .setQuery(QueryBuilders.matchQuery("_id", esId))
                    .execute()
                    .actionGet();

            //修改状态后的对象转换成json数据
            news.put("summary", summary);
            news.put("title_cuts", titleCut);
            news.put("content_cuts", contentCut);
            news.put("record_date", System.currentTimeMillis());

            String index = getIndex(client);
            SearchHits hits = searchResponse.getHits();
            String finalTagsJson = "";
            if (hits.getTotalHits() > 0) {
                logger.info("prepare update elastic, type : update, news_id[{}]", news.getString("news_id"));
                String oldIndex = hits.getAt(0).getIndex();
                // 更新
                UpdateResponse updateResponse = client.prepareUpdate(oldIndex, "default", esId)
                        .setDoc(news)
                        .get();
                if (updateResponse.getId() != null) {
                    logger.info("update elastic success, news_id[{}]", news.getString("news_id"));
                }
            } else {
                logger.info("prepare update elastic, type : insert, news_id[{}]", news.getString("news_id"));
                // 新增
                IndexResponse response = client.prepareIndex(index, "default")
                        .setId(esId)
                        .setSource(news, XContentType.JSON).get();
                if (response.getId() != null) {
                    logger.info("insert into elastic success, news_id[{}]", news.getString("news_id"));
                }

            }
        } catch (Exception e) {
            logger.error("insert/update error:", e);
        } finally {
            // 连接归还连接池
            if (client != null) {
                elasticConnectionPool.returnConnection(client);
            }
        }
    }

    public String getIndex(TransportClient client) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String oldIndex = AppConfig.ES_OLD_INDEX;
        String alias = AppConfig.ES_INDEX_ALIAS;
        String index = alias + "_" + format.format(new Date());
        //判断是否存在索引 若不存在，重新创建索引
        boolean check = checkIndex(client, index);
        if (!check) {
            Map<String, Object> settings = new HashMap<>();
            settings.put("number_of_shards", 5);
            settings.put("number_of_replicas", 1);
            settings.put("alias", alias);
            Map<String, Object> analysis = new HashMap<>();
            Map<String, Object> analyzer = new HashMap<>();
            Map<String, Object> u01_analyzer = new HashMap<>();
            u01_analyzer.put("type", "pattern");
            u01_analyzer.put("pattern", "\u0001");
            u01_analyzer.put("lowercase", false);
            analyzer.put("u01_analyzer", u01_analyzer);
            analysis.put("analyzer", analyzer);
            settings.put("analysis", analysis);
            boolean isAck = createIndex(client, index, settings, alias);
            boolean ismapping = false;
            if (isAck) {
                Map<String, Object> mapping = getMapping(client, oldIndex, "default");
                ismapping = putMapping(client, index, "default", mapping);
            }
            if (!isAck || !ismapping) {
                logger.error("error create the index and mapping ");
            }
            return index;
        } else {
            return index;
        }
    }

    private boolean checkIndex(TransportClient client, String index) {
        IndicesExistsRequest request = new IndicesExistsRequest(index);
        IndicesExistsResponse response = client.admin().indices().exists(request).actionGet();
        return response.isExists();

    }

    public boolean createIndex(TransportClient client, String index, Map<String, Object> settings, String alias) {
        try {
            CreateIndexResponse response = client.admin().indices().prepareCreate(index)
                    .setSettings(settings).get();
            IndicesAliasesRequestBuilder request = client.admin().indices().prepareAliases().addAlias(index, alias);
            IndicesAliasesResponse aliasesResponse = request.execute().get();
            if (response.isAcknowledged()) {
                return true;
            }
            if (!aliasesResponse.isAcknowledged()) {
                logger.error("error create the index alias");
            }
        } catch (Exception e) {
            logger.error("error create the index:", e);
        }
        return false;
    }

    public boolean putMapping(TransportClient client, String index, String type, Map<String, Object> mapping) {
        PutMappingRequestBuilder requestBuilder = client.admin().indices().preparePutMapping(index).setType(type);
        boolean isAcknowledged = false;
        try {
            requestBuilder.setSource(mapping);
            isAcknowledged = requestBuilder.execute().actionGet().isAcknowledged();
        } catch (Exception ex) {
            logger.error("failed to put mapping into elasticsearch", ex);
        }
        return isAcknowledged;
    }

    public Map<String, Object> getMapping(TransportClient client, String index, String type) throws IOException {
        return client.admin().indices()
                .getMappings(new GetMappingsRequest().indices(index).types(type)).actionGet()
                .getMappings().get(index).get(type).getSourceAsMap();
    }


}
