package demo

import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.MongoClientURI
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Created by westphal on 01.07.15.
 */
@ConfigurationProperties(prefix = "spring.data.mongodb")
class MongoProperties extends org.springframework.boot.autoconfigure.mongo.MongoProperties {

    public MongoClient createMongoClient(MongoClientOptions options)
            throws UnknownHostException {
        try {
            if (hasCustomAddress() || hasCustomCredentials()) {
                if (options == null) {
                    options = MongoClientOptions.builder().build();
                }
                List<MongoCredential> credentials = null;
                if (hasCustomCredentials()) {
                    String database = this.authenticationDatabase == null ? getMongoClientDatabase()
                            : this.authenticationDatabase;
                    credentials = Arrays.asList(MongoCredential.createPlainCredential(
                            this.username, database, this.password));
                }
                String host = this.host == null ? "localhost" : this.host;
                int port = this.port == null ? DEFAULT_PORT : this.port;
                return new MongoClient(Arrays.asList(new ServerAddress(host, port)),
                        credentials, options);
            }
            // The options and credentials are in the URI
            return new MongoClient(new MongoClientURI(this.uri, builder(options)));
        }
        finally {
            clearPassword();
        }
    }

    private boolean hasCustomAddress() {
        return this.host != null || this.port != null;
    }

    private boolean hasCustomCredentials() {
        return this.username != null && this.password != null;
    }

    private MongoClientOptions.Builder builder(MongoClientOptions options) {
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        if (options != null) {
            builder.alwaysUseMBeans(options.isAlwaysUseMBeans());
            builder.connectionsPerHost(options.getConnectionsPerHost());
            builder.connectTimeout(options.getConnectTimeout());
            builder.cursorFinalizerEnabled(options.isCursorFinalizerEnabled());
            builder.dbDecoderFactory(options.getDbDecoderFactory());
            builder.dbEncoderFactory(options.getDbEncoderFactory());
            builder.description(options.getDescription());
            builder.maxWaitTime(options.getMaxWaitTime());
            builder.readPreference(options.getReadPreference());
            builder.socketFactory(options.getSocketFactory());
            builder.socketKeepAlive(options.isSocketKeepAlive());
            builder.socketTimeout(options.getSocketTimeout());
            builder.threadsAllowedToBlockForConnectionMultiplier(options
                    .getThreadsAllowedToBlockForConnectionMultiplier());
            builder.writeConcern(options.getWriteConcern());
        }
        return builder;
    }
}
