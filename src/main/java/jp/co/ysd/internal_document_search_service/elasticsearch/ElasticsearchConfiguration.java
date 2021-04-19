package jp.co.ysd.internal_document_search_service.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 *
 * @author yuichi
 *
 */
@Configuration
public class ElasticsearchConfiguration {

	@Value("${elasticsearch.host:localhost}")
	private String host;

	@Value("${elasticsearch.port:9200}")
	private int port;

	@Bean
	public RestHighLevelClient restHighLevelClient() {
		return RestClients.create(ClientConfiguration.builder().connectedTo(host + ":" + port).build()).rest();
	}

	@Bean
	public ElasticsearchOperations elasticsearchOperations() {
		return new ElasticsearchRestTemplate(restHighLevelClient());
	}

}
