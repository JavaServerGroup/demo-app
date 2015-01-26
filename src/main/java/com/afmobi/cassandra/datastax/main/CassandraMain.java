package com.afmobi.cassandra.datastax.main;

import java.util.List;

import com.afmobi.cassandra.datastax.dao.UserDAO;
import com.afmobi.cassandra.datastax.pojo.User;


public class CassandraMain {

	private static final String KEYSPACES="userspace";
	
	public static void main(String[] args) {
		
		UserDAO userDao = new UserDAO(KEYSPACES);
		
		User  u1 = new User();
		u1.setName("aaName");
		u1.setPasswd("xxxxxxxxxxx");
		u1.setUid(1);
		userDao.addUser(u1);
		
		User  u4 = new User();
		u4.setName("ddName");
		u4.setPasswd("wwww");
		u4.setUid(4);
		userDao.addUser(u4);
		System.out.println("----------------根据uid查找user----------------------");
		User u = userDao.findUserBy(4);//根据uid查找user
		System.out.println(u.toString());
		System.out.println("-----------------根据uid删除user---------------------");
		userDao.removeUserById(4);//根据uid删除user
		System.out.println("-----------------查找所有user---------------------");
		List<User> users = userDao.findUser();//查找所有user
		for(User user:users){
			System.out.println(user.toString());
		}
		System.out.println("---------------根据uid，修改user-----------------------");
		u1.setName("aabbccdd");
		userDao.updateUser(u1);//根据uid，修改user
		System.out.println("-------------查找所有user-------------------------");
		users = userDao.findUser();//查找所有user
		for(User user:users){
			System.out.println(user.toString());
		}
		
	}
	
}
