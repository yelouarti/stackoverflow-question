/*
 * Copyright (c)  2024 kamillion-suite contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @since 04.12.2024
 */
/*
 * Copyright (c) 2024 kamillion contributors
 *
 * This work is licensed under the GNU General Public License (GPL).
 *
 * @since 14.10.2024
 */

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
