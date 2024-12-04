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
 * @since 03.12.2024
 */
/*
 * Copyright (c) 2024 kamillion contributors
 *
 * This work is licensed under the GNU General Public License (GPL).
 *
 * @since 14.10.2024
 */

package elouarti.younes.stackoverflowquestion.controller;

import de.kamillionlabs.hateoflux.model.hal.HalListWrapper;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import elouarti.younes.stackoverflowquestion.repository.PersonRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import elouarti.younes.stackoverflowquestion.pojo.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static de.kamillionlabs.hateoflux.linkbuilder.SpringControllerLinkBuilder.linkTo;

/**
 * @author Younes El Ouarti
 */
@RestController
public class PersonController {

    private final PersonRepository people;

    public PersonController(PersonRepository people) {
        this.people = people;
    }


    @GetMapping("/people/{id}")
    Mono<HalResourceWrapper<Person, Void>> getPerson(@PathVariable int id) {
        Mono<Person> result = people.findById(id);
        return result.map(person -> HalResourceWrapper.wrap(person)
                .withLinks(
                        linkTo(PersonController.class,
                                controller -> controller.getPerson(id)
                        ).withSelfRel()
                )
        );
    }

    @GetMapping("/people-individual")
    Flux<HalResourceWrapper<Person, Void>> namesByLastnameIndividual(@RequestParam
                                                                     String lastname) {
        Flux<Person> result = people.findByLastname(lastname);
        return result.map(person -> HalResourceWrapper.wrap(person)
                .withLinks(
                        linkTo(PersonController.class,
                                controller -> controller.getPerson(person.getId())
                        ).withSelfRel()
                )
        );
    }

    @GetMapping("/people-as-list")
    Mono<HalListWrapper<Person, Void>> namesByLastnameAsList(@RequestParam
                                                             String lastname) {
        Flux<HalResourceWrapper<Person, Void>> wrappedFullNames;
        wrappedFullNames = namesByLastnameIndividual(lastname);

        return wrappedFullNames
                .collectList()
                .map(fullNames -> HalListWrapper.wrap(fullNames)
                        .withLinks(
                                linkTo(PersonController.class,
                                        controller -> controller.namesByLastnameAsList(lastname)
                                ).withSelfRel()
                        )
                );
    }
}