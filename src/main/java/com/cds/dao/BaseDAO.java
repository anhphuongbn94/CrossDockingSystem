package com.cds.dao;

import java.sql.Connection;

import org.springframework.jdbc.core.JdbcTemplate;

public interface BaseDAO {
	public Connection getConnection();
	
	public JdbcTemplate getJdbcTemplate();
	
	public Long getIdSEQ(String table, String id);
	
	public int countAll(String table);
	
	public boolean delRecordTable(String table, String id, long value); 
	
	public int countAllVehicle_whereStatusAndDateAndKey(String table, String key, String status);
}
