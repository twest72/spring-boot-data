package demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by westphal on 01.07.15.
 */
@RestController
class TestController {

    @Autowired
    UserElasticRepository userElasticRepository

    @Autowired
    UserMongoRepository userMongoRepository

    @Autowired
    MongoProperties mongoProperties

    @RequestMapping(value = '/add', method = RequestMethod.GET)
    User addUser(@RequestParam(value = 'name') String name) {

        User user = new User(name: name)

        println user
        userMongoRepository.insert(user)
        println user
        userElasticRepository.index(user)
        println user

        return user
    }

    @RequestMapping(value = '/findAllMongo', method = RequestMethod.GET)
    List<User> findAllMongo() {
        return userMongoRepository.findAll()
    }

    @RequestMapping(value = '/findAllElastic', method = RequestMethod.GET)
    List<User> findAllElastic() {
        return userElasticRepository.findAll() as List<User>
    }
}
