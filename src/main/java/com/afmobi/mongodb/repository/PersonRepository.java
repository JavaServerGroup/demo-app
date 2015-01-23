/*
 * Copyright 2010-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afmobi.mongodb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoPage;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.afmobi.mongodb.repository.pojo.Address;
import com.afmobi.mongodb.repository.pojo.Credentials;
import com.afmobi.mongodb.repository.pojo.Person;
import com.afmobi.mongodb.repository.pojo.Person.Sex;
import com.afmobi.mongodb.repository.pojo.PersonSummary;
import com.afmobi.mongodb.repository.pojo.User;

/**
 * Sample repository managing {@link Person} entities.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Christoph Strobl
 */
public interface PersonRepository extends MongoRepository<Person, String>{//, QueryDslPredicateExecutor<Person> {

	List<Person> findByLastname(String lastname);
	
	List<Person> findByFirstname(String firstname);
	
	@Query(value = "{ 'firstname' : ?0 }", fields = "{ 'firstname': 1, 'lastname': 1}")
	List<Person> findByThePersonsFirstname(String firstname);
	
	List<Person> findByFirstnameLike(String firstname);
	
	Page<Person> findByLastnameLike(String lastname, Pageable pageable);
	
	@Query("{ 'lastname' : { '$regex' : ?0, '$options' : ''}}")
	Page<Person> findByLastnameLikeWithPageable(String lastname, Pageable pageable);
	
	List<Person> findByAgeBetween(int from, int to);
	
	Person findByShippingAddresses(Address address);
	
	List<Person> findByAddress(Address address);
	
	List<Person> findByAddressZipCode(String zipCode);
	
	List<Person> findByLocationNear(Point point);
	
	List<Person> findByLocationWithin(Circle circle);
	
	List<Person> findByLocationWithin(Box box);

	List<Person> findByLocationWithin(Polygon polygon);
	
	List<Person> findBySex(Sex sex);
	
	List<Person> findBySex(Sex sex, Pageable pageable);
	
	List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
	
	List<Person> findByFirstnameLikeOrderByLastnameAsc(String firstname, Sort sort);
	
	GeoResults<Person> findByLocationNear(Point point, Distance maxDistance);
	
	GeoPage<Person> findByLocationNear(Point point, Distance maxDistance, Pageable pageable);
	
	@Query("{'age' : { '$lt' : ?0 } }")
	List<Person> findByAgeLessThan(int age, Sort sort);
	
	List<Person> findByCreator(User user);
	
	List<Person> findByCreatedAtLessThan(Date date);
	
	List<Person> findByCreatedAtGreaterThan(Date date);
	
	List<Person> findByCreatedAtBefore(Date date);
	
	List<Person> findByCreatedAtAfter(Date date);
	
	@Query("{ 'createdAt' : { '$lt' : ?0 }}")
	List<Person> findByCreatedAtLessThanManually(Date date);
	
	List<Person> findByLastnameNot(String lastname);
	
	List<Person> findByFirstnameAndLastname(String firstname, String lastname);
	
	List<Person> findByCredentials(Credentials credentials);
	
	long countByLastname(String lastname);
	
	int countByFirstname(String firstname);
	
	@Query(value = "{ 'lastname' : ?0 }", count = true)
	long someCountQuery(String lastname);
	
	List<Person> findByLastnameStartsWith(String prefix);
	
	List<Person> findByLastnameEndsWith(String postfix);
	
	List<Person> findByFirstnameIgnoreCase(String firstName);
	
	List<Person> findByFirstnameNotIgnoreCase(String firstName);
	
	List<Person> findByFirstnameStartingWithIgnoreCase(String firstName);

	List<Person> findByFirstnameEndingWithIgnoreCase(String firstName);
	
	List<Person> findByFirstnameContainingIgnoreCase(String firstName);
	
	Slice<Person> findByAgeGreaterThan(int age, Pageable pageable);
	
	@Query(value = "{ 'firstname' : ?0 }")
	Person[] findByThePersonsFirstnameAsArray(String firstname);
	
	@Query("{ creator : { $exists : true } }")
	Page<Person> findByHavingCreator(Pageable page);
	
	List<Person> deleteByLastname(String lastname);
	
	Long deletePersonByLastname(String lastname);
	
	@Query(value = "{ 'lastname' : ?0 }", delete = true)
	List<Person> removeByLastnameUsingAnnotatedQuery(String lastname);
	
	@Query(value = "{ 'lastname' : ?0 }", delete = true)
	Long removePersonByLastnameUsingAnnotatedQuery(String lastname);
	
	Page<Person> findByAddressIn(List<Address> address, Pageable page);
	
	@Query("{firstname:{$in:?0}, lastname:?1}")
	Page<Person> findByCustomQueryFirstnamesAndLastname(List<String> firstnames, String lastname, Pageable page);

	@Query("{lastname:?0, address.street:{$in:?1}}")
	Page<Person> findByCustomQueryLastnameAndAddressStreetInList(String lastname, List<String> streetNames, Pageable page);

	List<Person> findTop3ByLastnameStartingWith(String lastname);
	
	Page<Person> findTop3ByLastnameStartingWith(String lastname, Pageable pageRequest);
	
	PersonSummary findSummaryByLastname(String lastname);
	
	@Query("{ ?0 : ?1 }")
	List<Person> findByKeyValue(String key, String value);
	
	/**
	 * Returns all {@link Person}s with a firstname contained in the given varargs.
	 * 
	 * @param firstnames
	 * @return
	 *//*
	List<Person> findByFirstnameIn(String... firstnames);

	*//**
	 * Returns all {@link Person}s with a firstname not contained in the given collection.
	 * 
	 * @param firstnames
	 * @return
	 *//*
	List<Person> findByFirstnameNotIn(Collection<String> firstnames);

	List<Person> findByLastnameLikeAndAgeBetween(String lastname, int from, int to);

	List<Person> findByAgeOrLastnameLikeAndFirstnameLike(int age, String lastname, String firstname);
	*/
}
