package com.afmobi.cassandra.datastax.client;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.Session;

public class CassandraClient {
	
	private Cluster cluster;
	private Session session;
	private String keyspace;
	
	public void connect(String node){
		QueryOptions options = new QueryOptions();
		options.setConsistencyLevel(ConsistencyLevel.QUORUM);
		cluster = Cluster.builder().addContactPoint("localhost").withPort(9160)
				.withQueryOptions(options).build();
		session = cluster.connect(keyspace);
		
	}
	
	public void createKS(){
		Session session = cluster.connect();
		session.execute("CREATE KEYSPACE "+keyspace+" WITH replication ={'class':'SimpleStrategy','replication_factor':1};");
		session.close();
	}
	
	public void createTB(String cql){
		session = cluster.connect(keyspace);
//		session.execute("CREATE TABLE "+table+"(uid int,name varchar,passwd varchar,PRIMARY KEY(uid));");
		session.execute(cql);
	}
	
	
	
	
	public void close(){
		session.close();
		cluster.close();
	}
}
