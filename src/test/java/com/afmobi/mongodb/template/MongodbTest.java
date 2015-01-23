package com.afmobi.mongodb.template;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.afmobi.mongodb.template.pojo.Address;
import com.afmobi.mongodb.template.pojo.Person;
import com.afmobi.mongodb.template.pojo.Venue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/mongodb-application-context.xml" })
public class MongodbTest {

	private final Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "mongoTemplate")
	private MongoOperations mongoOps;

	@Before
	public void setup() {
		mongoOps.dropCollection(Person.class);
		mongoOps.dropCollection(Venue.class);
	}

	@Test
	public void testCRUD() {
		Person person = makePerson();

		// Insert is used to initially store the object into the database.
		mongoOps.insert(person);
		log.info("Insert: " + person);

		// Find
		Person personFromDB = mongoOps.findById(person.getId(), Person.class);
		log.info("Found: " + personFromDB);

		Assert.assertEquals(person, personFromDB);

		person.setAge(31);

		// Update
		mongoOps.upsert(query(where("id").is(person.getId())), update("age", person.getAge()), Person.class);
		personFromDB = mongoOps.findOne(query(where("id").is(person.getId())), Person.class);
		log.info("Updated: " + person);

		Assert.assertEquals(person, personFromDB);

		// Delete
		mongoOps.remove(person);

		personFromDB = mongoOps.findOne(query(where("id").is(person.getId())), Person.class);

		Assert.assertNull(personFromDB);

		// Check that deletion worked
		List<Person> people = mongoOps.findAll(Person.class);
		log.info("Number of people = : " + people.size());

		Assert.assertEquals(0, people.size());
	}

	private Person makePerson() {
		Person person = new Person();
		person.setName("Joe");
		person.setAge(34);
		person.setBirthday(new Date());
		person.setEmails(Arrays.asList("jiale.chan@gmail.com,chen369035906@126.com".split(",")));
		person.setLastLoginTime(System.currentTimeMillis());
		Address address = new Address();
		address.setCountry("China");
		address.setState("GD");
		address.setCity("SZ");
		person.setAddress(address);
		return person;
	}

	@Test
	public void testBatchInsert() {
		List<Person> persons = new ArrayList<>();

		Person person1 = makePerson();
		Person person2 = makePerson();

		persons.add(person1);
		persons.add(person2);

		mongoOps.insert(persons, Person.class);

		List<Person> personsFromDB = mongoOps.findAll(Person.class);

		Assert.assertEquals(2, personsFromDB.size());

		Assert.assertEquals(person1, mongoOps.findById(person1.getId(), Person.class));
		Assert.assertEquals(person2, mongoOps.findById(person2.getId(), Person.class));
	}

	@Test
	public void testInc() {
		Person person = makePerson();

		mongoOps.insert(person);

		mongoOps.updateMulti(query(where("id").is(person.getId())), new Update().inc("age", 1), Person.class);

		Person personFromDB = mongoOps.findById(person.getId(), Person.class);

		Assert.assertEquals(person.getAge() + 1, personFromDB.getAge());

		mongoOps.updateMulti(query(where("id").is(person.getId())), new Update().inc("age", -1), Person.class);

		personFromDB = mongoOps.findById(person.getId(), Person.class);

		Assert.assertEquals(person.getAge(), personFromDB.getAge());
	}

	@Test
	public void testPushAndPushall() {
		Person person = makePerson();

		mongoOps.insert(person);

		mongoOps.updateMulti(query(where("id").is(person.getId())), new Update().push("emails", "369035906@qq.com"),
				Person.class);

		Person personFromDB = mongoOps.findById(person.getId(), Person.class);

		Assert.assertEquals(3, personFromDB.getEmails().size());

		Assert.assertTrue(personFromDB.getEmails().contains("369035906@qq.com"));

		mongoOps.updateMulti(query(where("id").is(person.getId())),
				new Update().pushAll("emails", "369035906_1@qq.com,369035906_2@qq.com".split(",")), Person.class);

		personFromDB = mongoOps.findById(person.getId(), Person.class);

		Assert.assertEquals(5, personFromDB.getEmails().size());

		Assert.assertTrue(personFromDB.getEmails().contains("369035906_1@qq.com"));
		Assert.assertTrue(personFromDB.getEmails().contains("369035906_2@qq.com"));
	}

	@Test
	public void testQueryInstanceFromAPlainJSONString() {
		Person person = makePerson();

		mongoOps.insert(person);

		BasicQuery query = new BasicQuery("{ age : { $eq : 34 } }");
		List<Person> result = mongoOps.find(query, Person.class);

		Assert.assertEquals(1, result.size());

		Assert.assertEquals(person, result.get(0));
	}

	@Test
	public void testGeoByNear() {
		mongoOps.indexOps(Venue.class).ensureIndex(new GeospatialIndex("location"));
		
		Venue venue = new Venue();
		venue.setName("SZ");
		double[] locationArray = {-73.99170, 40.738867};
		venue.setLocation(locationArray);
		
		mongoOps.insert(venue);
		
		Venue venue2 = new Venue();
		venue2.setName("SZ2");
		double[] locationArray2 = {-73.99170, 40.738855};
		venue2.setLocation(locationArray2);
		
		mongoOps.insert(venue2);
		
		NearQuery geoNear = NearQuery.near(-73.99170, 40.738, Metrics.KILOMETERS).num(10).maxDistance(150000);
		
		GeoResults<Venue> result = mongoOps.geoNear(geoNear, Venue.class);

		Assert.assertEquals(2, result.getContent().size());
		
		Assert.assertEquals(venue2, result.getContent().get(0).getContent());
		Assert.assertEquals(venue, result.getContent().get(1).getContent());
	}
	
	@Test
	public void testGeoByWithin() {
		Venue venueByWithin = new Venue();
		venueByWithin.setName("SZ");
		double[] locationArrayByWithin = {-73.99170, 40.738867};
		venueByWithin.setLocation(locationArrayByWithin);
		
		mongoOps.insert(venueByWithin);
		
		Circle circle = new Circle(-73.99171, 40.738868, 0.01);
		List<Venue> venues = mongoOps.find(new Query(Criteria.where("location").within(circle)), Venue.class);
		
		Assert.assertEquals(1, venues.size());
		
		Assert.assertEquals(venueByWithin, venues.get(0));
		
	}
}
