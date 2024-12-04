package elouarti.younes.stackoverflowquestion.repository;

import elouarti.younes.stackoverflowquestion.pojo.Person;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Younes El Ouarti
 */
@Component
public class PersonRepository {

    Person john = new Person(1, "John", "Smith");
    Person peter = new Person(2, "Peter", "Smith");

    public Flux<Person> findByLastname(String lastName) {

        // This would be a call to a database. We simply return some dummy data instead
        if (lastName == null || !lastName.equalsIgnoreCase("Smith")) {
            return Flux.empty();
        } else {
            return Flux.just(john, peter);
        }
    }

    public Mono<Person> findById(int id) {
        if (id == 1) {
            return Mono.just(john);
        }

        if (id == 2) {
            return Mono.just(peter);
        }
        return Mono.empty();
    }
}
