package demo

import com.mongodb.Mongo
import com.mongodb.MongoClientOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.SimpleMongoDbFactory

import javax.annotation.PreDestroy

/**
 * Created by westphal on 01.07.15.
 */
@Configuration
@ConditionalOnClass(Mongo.class)
@EnableConfigurationProperties(MongoProperties.class)
class MongoConfiguration extends MongoAutoConfiguration {

    @Autowired
    private MongoProperties mongoProperties

    @Autowired(required = false)
    private MongoClientOptions options;

    private Mongo mongo;

    @PreDestroy
    public void close() {
        if (this.mongo != null) {
            this.mongo.close();
        }
    }

    @Bean
    MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new Mongo(), this.mongoProperties.getDatabase())
    }

    @Bean
    Mongo mongo() throws UnknownHostException {
        this.mongo = this.mongoProperties.createMongoClient(this.options)
        return this.mongo
    }
}
