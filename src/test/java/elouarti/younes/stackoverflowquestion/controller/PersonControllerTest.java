package elouarti.younes.stackoverflowquestion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.kamillionlabs.hateoflux.model.hal.HalListWrapper;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import elouarti.younes.stackoverflowquestion.pojo.Person;
import elouarti.younes.stackoverflowquestion.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE;


class PersonControllerTest {

    private PersonController personController = new PersonController(new PersonRepository());
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void test_getPerson(){
        Mono<HalResourceWrapper<Person, Void>> actual = personController.getPerson(1);

        StepVerifier.create(actual)
                .assertNext( wrapper -> {
                    try {
                        String json = mapper.writeValueAsString(wrapper);
                        JSONAssert.assertEquals("""
                                {
                                  "id": 1,
                                  "firstName": "John",
                                  "lastName": "Smith",
                                  "_links": {
                                    "self": {
                                      "href": "/people/1"
                                    }
                                  }
                                }
                                """, json, NON_EXTENSIBLE);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .verifyComplete();
    }

    @Test
    void test_personsByLastnameIndividual(){
        Flux<HalResourceWrapper<Person, Void>> actual = personController.personsByLastnameIndividual("Smith");

        StepVerifier.create(actual)
                .assertNext( wrapper -> {
                    try {
                        String json = mapper.writeValueAsString(wrapper);
                        JSONAssert.assertEquals("""
                                {
                                  "id": 1,
                                  "firstName": "John",
                                  "lastName": "Smith",
                                  "_links": {
                                    "self": {
                                      "href": "/people/1"
                                    }
                                  }
                                }
                                """, json, NON_EXTENSIBLE);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .assertNext( wrapper -> {
                    try {
                        String json = mapper.writeValueAsString(wrapper);
                        JSONAssert.assertEquals("""
                                {
                                  "id": 2,
                                  "firstName": "Peter",
                                  "lastName": "Smith",
                                  "_links": {
                                    "self": {
                                      "href": "/people/2"
                                    }
                                  }
                                }
                                """, json, NON_EXTENSIBLE);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .verifyComplete();
    }

    @Test
    void test_personsByLastnameAsList(){
    Mono<HalListWrapper<Person, Void>> actual = personController.personsByLastnameAsList("Smith");

        StepVerifier.create(actual)
            .assertNext( wrapper -> {
        try {
            String json = mapper.writeValueAsString(wrapper);
            JSONAssert.assertEquals("""
                    {
                      "_embedded": {
                        "persons": [
                          {
                            "id": 1,
                            "firstName": "John",
                            "lastName": "Smith",
                            "_links": {
                              "self": {
                                "href": "/people/1"
                              }
                            }
                          },
                          {
                            "id": 2,
                            "firstName": "Peter",
                            "lastName": "Smith",
                            "_links": {
                              "self": {
                                "href": "/people/2"
                              }
                            }
                          }
                        ]
                      },
                      "_links": {
                        "self": {
                          "href": "/people-as-list?lastname=Smith"
                        }
                      }
                    }
                    """, json, NON_EXTENSIBLE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    })
            .verifyComplete();
}

}