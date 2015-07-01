package demo

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by westphal on 01.07.15.
 */
interface UserMongoRepository extends MongoRepository<User, String> {

}