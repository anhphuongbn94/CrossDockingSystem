package dao.impl.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.employee.EmployeeDAO;
import entity.employee.Employee;

@Component
public class EmployeeDAOImpl implements EmployeeDAO{
	private DataSource dataSource;
	
	@Autowired
	public EmployeeDAOImpl(DataSource dataSource){
		this.dataSource = dataSource;
	}
	public boolean checkLogin(String username, String password) {
		String sql="SELECT idEmployee FROM tblEmployee WHERE username=? AND password=? AND active=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, 1);
			rs = ps.executeQuery();
			if(rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null){
					ps.close();
				}
				if(rs != null){
					rs.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public boolean register(Employee em) {
		String sql="INSERT INTO tblEmployee"
				+ "(idEmployee, fullname, gender, email, phonenumber, address, username, password, active)"
				+ "VALUES(?,?,?,?,?,?,?,?,?)";
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, getIdEmployeeInsert() + 1);
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
			try {
				if(ps != null){
					ps.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public int getIdEmployeeInsert() {
		String sql="SELECT idEmployee "
				+ "FROM tblEmployee "
				+ "ORDER BY idEmployee DESC";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null){
					ps.close();
				}
				if(rs != null){
					rs.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
