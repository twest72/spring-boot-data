package demo

import groovy.transform.ToString
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

/**
 * Created by westphal on 01.07.15.
 */
@ToString
@Document(indexName = "user", type = "user")
class User {

    @Id
    String id

    String name
}
