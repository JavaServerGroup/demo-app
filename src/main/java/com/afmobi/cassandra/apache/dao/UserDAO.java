package com.afmobi.cassandra.apache.dao;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;

import com.afmobi.cassandra.apache.pojo.User;
import com.afmobi.cassandra.apache.util.Util;

public class UserDAO {

	private final String COLUMNFAMILY = "users";

	private Cassandra.Client client;

	public UserDAO(Cassandra.Client client) {
		this.client = client;
	}

	public void insert(User user) throws Exception {
		ColumnParent parent = new ColumnParent(COLUMNFAMILY);
		ByteBuffer columnKey = Util.toByteBuffer(user.getUid() + "");
		long timestamp = System.currentTimeMillis();

		Column uidColumn = new Column(Util.toByteBuffer("uid"));
		uidColumn.setValue(Util.toByteBuffer(user.getUid() + ""));
		uidColumn.setTimestamp(timestamp);
		client.insert(columnKey, parent, uidColumn, ConsistencyLevel.ONE);

		Column nameColumn = new Column(Util.toByteBuffer("name"));
		nameColumn.setValue(Util.toByteBuffer(user.getName()));
		nameColumn.setTimestamp(timestamp);
		client.insert(columnKey, parent, nameColumn, ConsistencyLevel.ONE);

		Column ageColumn = new Column(Util.toByteBuffer("passwd"));
		ageColumn.setValue(Util.toByteBuffer(user.getPasswd()));
		ageColumn.setTimestamp(timestamp);
		client.insert(columnKey, parent, ageColumn, ConsistencyLevel.ONE);
	}
	
	
	public User findUserByUid(int uid) throws Exception {
		ColumnParent parent = new ColumnParent(COLUMNFAMILY);
		String columnKey = uid + "";

		SlicePredicate predicate = new SlicePredicate();
		SliceRange sliceRange = new SliceRange(Util.toByteBuffer(""),Util.toByteBuffer(""), false, 10);

		predicate.setSlice_range(sliceRange);
		List<ColumnOrSuperColumn> results = client.get_slice(Util.toByteBuffer(columnKey), parent, predicate,
				ConsistencyLevel.ONE);
		User user = new User();
		for (ColumnOrSuperColumn result : results) {
			String name = Util.toString(result.column.name);
			String value = Util.toString(result.column.value);
			if (name.equals("uid")) {
				user.setUid(Integer.parseInt(value));
			} else if (name.equals("name")) {
				user.setName(value);
			} else if (name.equals("passwd")) {
				user.setPasswd(value);
			}
		}
		return user;
	}
	
	
	public void removeUserBy(int uid) throws Exception{
		ColumnPath path = new ColumnPath(COLUMNFAMILY);
		String columnKey = uid + "";
		long timestamp = System.currentTimeMillis();
		client.remove(Util.toByteBuffer(columnKey), path, timestamp, ConsistencyLevel.ONE);
	}
	
	 public void update(int uid,User user)throws Exception{
	        ColumnParent parent = new ColumnParent(COLUMNFAMILY);// column family
	        ByteBuffer columnKey = Util.toByteBuffer(uid + "");
	        
	        long timestamp = System.currentTimeMillis();// 时间戳
	        Column nameColumn = new Column(Util.toByteBuffer("name"));  
	        nameColumn.setValue(Util.toByteBuffer(user.getName()));
	        nameColumn.setTimestamp(timestamp);
	        client.insert(columnKey, parent, nameColumn, ConsistencyLevel.ONE);
	        
	        Column psColumn = new Column(Util.toByteBuffer("passwd"));  
	        psColumn.setValue(Util.toByteBuffer(user.getPasswd()));
	        psColumn.setTimestamp(timestamp);
	        client.insert(columnKey, parent, psColumn, ConsistencyLevel.ONE);
	 }

}
