package com.cds.dao;

import java.sql.Connection;

import org.springframework.jdbc.core.JdbcTemplate;

public interface BaseDAO {
	public Connection getConnection();
	
	public JdbcTemplate getJdbcTemplate();
	
	public Long getIdSEQ(String table, String id);
	
	public int countAll(String table);
}
