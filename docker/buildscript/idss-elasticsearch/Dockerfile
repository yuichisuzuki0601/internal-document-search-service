FROM docker.elastic.co/elasticsearch/elasticsearch:7.9.3

RUN elasticsearch-plugin install analysis-icu
RUN elasticsearch-plugin install analysis-kuromoji

USER root
RUN echo "indices.query.bool.max_clause_count: 1048576" >> /usr/share/elasticsearch/config/elasticsearch.yml