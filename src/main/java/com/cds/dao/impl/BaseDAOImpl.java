package com.cds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cds.dao.BaseDAO;
import com.cds.tool.MyTool;

public class BaseDAOImpl implements BaseDAO{
	private DataSource dataSource; 
	
	@Autowired
	public BaseDAOImpl(DataSource dataSource){
		this.dataSource = dataSource;
	}
	public Connection getConnection(){
		try {
			Connection conn = dataSource.getConnection();
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public JdbcTemplate getJdbcTemplate(){
		return new JdbcTemplate(dataSource);
	}
	public void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs){
		try {
			if(conn != null){
				conn.close();
			}
			if(ps != null){
				ps.close();
			}
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Long getIdSEQ(String table, String id){
		String sql="SELECT MAX("+id+") AS idMax FROM " + table;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) return (rs.getLong("idMax") + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public int countAll(String table){
		String sql="SELECT COUNT(*) FROM " + table + " WHERE date=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, new MyTool().getDateSystem());
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
}
