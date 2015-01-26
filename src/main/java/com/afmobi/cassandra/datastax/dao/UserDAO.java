package com.afmobi.cassandra.datastax.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.afmobi.cassandra.datastax.pojo.User;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;

public class UserDAO {

	private static final String TABLE = "user";
	private static Cluster cluster;
	private static Session userSession;

	public UserDAO(String keyspace) {
		cluster = new Cluster.Builder().addContactPoints("localhost")
				.withPort(9160).build();
		userSession = cluster.connect(keyspace);
	}
	
	public void addUser(User user) {
		Insert insert = QueryBuilder.insertInto(TABLE);
		insert.value("uid", user.getUid());
		insert.value("name", user.getName());
		insert.value("passwd", user.getPasswd());
		userSession.execute(insert);
	}

	public void removeUserById(int uid) {
		Delete delete = QueryBuilder.delete().from(TABLE);
		delete.where(QueryBuilder.eq("uid", uid));
		userSession.execute(delete);
	}

	public void updateUser(User user) {
		Update update = QueryBuilder.update(TABLE);
		update.with(QueryBuilder.set("name", user.getName())).where(
				QueryBuilder.eq("uid", user.getUid()));
		userSession.execute(update);
	}

	public User findUserBy(int uid) {
		Select select = QueryBuilder.select().from(TABLE);
		User user = new User();
		select.where(QueryBuilder.eq("uid", uid));
		ResultSet res = userSession.execute(select);
		Iterator<Row> it = res.iterator();
		while (it.hasNext()) {
			Row row = it.next();
			user.setUid(uid);
			user.setName(row.getString("name"));
			user.setPasswd(row.getString("passwd"));
		}
		return user;
	}

	public List<User> findUser() {
		Select select = QueryBuilder.select().from(TABLE);
		List<User> list = new ArrayList<User>();
		User user = null;
		select.where();
		ResultSet rs = userSession.execute(select);
		Iterator<Row> it = rs.iterator();
		while(it.hasNext()){
			user = new User();
			Row row = it.next();
			user.setUid(row.getInt("uid"));
			user.setName(row.getString("name"));
			user.setPasswd(row.getString("passwd"));
			list.add(user);
		}
		return list;
	}

}
