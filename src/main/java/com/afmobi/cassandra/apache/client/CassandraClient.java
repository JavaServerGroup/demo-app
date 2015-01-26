package com.afmobi.cassandra.apache.client;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class CassandraClient {
	
	private TTransport tr;
	private Cassandra.Client client;
	
	public CassandraClient(String ip,int port){
		this.tr = new  TFramedTransport(new TSocket(ip, port));
		TProtocol proto = new TBinaryProtocol(tr);
		this.client = new Cassandra.Client(proto);
	}
	
	public void open() throws Exception{
		tr.open();
		if(!tr.isOpen())
			throw new Exception("connect failed");
	}
	
	public void close(){
		tr.close();
	}
	
	public void setKeySpace(String keyspace) throws Exception{
		client.set_keyspace(keyspace);
	}
	
	public Cassandra.Client getClient(){
		return client;
	}
	
}
