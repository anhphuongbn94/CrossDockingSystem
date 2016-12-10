package com.cds.dao.impl.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.cds.dao.employee.EmployeeDAO;
import com.cds.dao.impl.BaseDAOImpl;
import com.cds.entity.employee.Employee;

@Component
public class EmployeeDAOImpl extends BaseDAOImpl implements EmployeeDAO{
	
	public EmployeeDAOImpl(DataSource dataSource) {
		super(dataSource);
	}
	public Employee checkLogin(String username, String password) {
		String sql="SELECT idEmployee, fullname, gender, email, "
						+ "phonenumber, address, username, level "
				+ "FROM tblEmployee "
				+ "WHERE username=? AND password=? AND active=1";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next()){
				Employee em=new Employee();
				em.setIdEmployee(rs.getLong("idEmployee"));
				em.setFullname(rs.getString("fullname"));
				em.setGender(rs.getInt("gender"));
				em.setEmail(rs.getString("email"));
				em.setPhonenumber(rs.getString("phonenumber"));
				em.setAddress(rs.getString("address"));
				em.setUsername(rs.getString("username"));
				em.setLevel(rs.getInt("level"));
				return em;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public boolean register(Employee em) {
		String sql="INSERT INTO tblEmployee"
				+ "(idEmployee, fullname, gender, email, phonenumber, "
					+ "address, username, password, active)"
				+ "VALUES(?,?,?,?,?,?,?,?,?)";
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, getIdSEQ("tblEmployee", "idEmployee"));
			ps.setString(2, em.getFullname());
			ps.setInt(3, em.getGender());
			ps.setString(4, em.getEmail());
			ps.setString(5, em.getPhonenumber());
			ps.setString(6, em.getAddress());
			ps.setString(7, em.getUsername());
			ps.setString(8, em.getPassword());
			ps.setInt(9, em.getActive());
			int n=ps.executeUpdate();
			if(n==1) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, null);
		}
		return false;
	}
	public List<Employee> getPageEmployee(int sizePage, int currentPage, String key) {
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT idEmployee, fullname, gender, email, "
						+ "phonenumber, address, username, password, level, active "
				+ "FROM tblEmployee WHERE (fullname LIKE ? OR username LIKE ?) AND level <> 1 "
				+ "LIMIT ?, ?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Employee> list=null;
		try {
			list = new ArrayList<Employee>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +key+ "%");
			ps.setString(2, "%" +key+ "%");
			ps.setInt(3, sIndex);
			ps.setInt(4, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				Employee em=new Employee();
				em.setIdEmployee(rs.getLong("idEmployee"));
				em.setFullname(rs.getString("fullname"));
				em.setGender(rs.getInt("gender"));
				em.setEmail(rs.getString("email"));
				em.setPhonenumber(rs.getString("phonenumber"));
				em.setAddress(rs.getString("address"));
				em.setUsername(rs.getString("username"));
				em.setPassword(rs.getString("password"));
				em.setLevel(rs.getInt("level"));
				em.setActive(rs.getInt("active"));
				list.add(em);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public int countAllEmployee(String key) {
		String sql="SELECT COUNT(*) FROM tblEmployee WHERE (fullname LIKE ? OR username LIKE ?) AND level <> 1";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +key+ "%");
			ps.setString(2, "%" +key+ "%");
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}	
		return 0;
	}
	public void changeEmployee(Employee em){
		String sql="UPDATE tblEmployee SET fullname=?, gender=?, email=?, phonenumber=?, address=?, password=? WHERE idEmployee=?";
		getJdbcTemplate().update(sql, new Object[]{
			em.getFullname(), em.getGender(), em.getEmail(), em.getPhonenumber(), 
			em.getAddress(), em.getPassword(), em.getIdEmployee()
		});
	}
}
