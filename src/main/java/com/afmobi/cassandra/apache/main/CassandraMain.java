package com.afmobi.cassandra.apache.main;

import com.afmobi.cassandra.apache.client.CassandraClient;
import com.afmobi.cassandra.apache.dao.UserDAO;
import com.afmobi.cassandra.apache.pojo.User;


public class CassandraMain {
	
	public static void main(String[] args) throws Exception {
		CassandraClient cassandraClient = new CassandraClient("localhost", 9160);
		cassandraClient.open();
		cassandraClient.setKeySpace("usermanager");
		
		UserDAO userDao = new UserDAO(cassandraClient.getClient());
		
		User user1 = new User();
		user1.setUid(1);
		user1.setName("11Name");
		user1.setPasswd("password");
		userDao.insert(user1);
		
		User user2 = new User();
		user2.setUid(2);
		user2.setName("222Name");
		user2.setPasswd("password222");
		userDao.insert(user2);
		
		User user = userDao.findUserByUid(2);
		System.out.println(user.toString());
		
		userDao.removeUserBy(2);
		
		user = userDao.findUserByUid(2);
		System.out.println(user.toString());
		
		cassandraClient.close();
	}

}
