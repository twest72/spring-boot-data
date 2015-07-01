package demo

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

/**
 * Created by westphal on 01.07.15.
 */
interface UserElasticRepository extends ElasticsearchRepository<User, String> {

}