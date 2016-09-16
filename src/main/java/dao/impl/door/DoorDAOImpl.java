package dao.impl.door;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import dao.door.DoorDAO;
import entity.door.Cost;
import entity.door.CrossDockingSystem;
import entity.door.InDoor;
import entity.door.OutDoor;

@Component
public class DoorDAOImpl implements DoorDAO{
	private DataSource dataSource;
	
	@Autowired
	public DoorDAOImpl(DataSource dataSource){
		this.dataSource = dataSource;
	}
	public Integer getIdInDoorInsert() {
		String sql="SELECT idInDoor FROM tblInDoor ORDER BY idInDoor DESC";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt("idInDoor");
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
	public ArrayList<InDoor> getListInDoorActive() {
		String sql="SELECT i.idInDoor, i.nameInDoor, i.capacity, i.status, i.idCrossDockingSystem, "
				+ "c.nameCrossDockingSystem, c.address, c.capacity "
				+ "FROM tblInDoor as i "
				+ "JOIN tblCrossDockingSystem as c ON i.idCrossDockingSystem=c.idCrossDockingSystem "
				+ "WHERE i.status=?";
		ArrayList<InDoor> listInDoor=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listInDoor = new ArrayList<InDoor>();
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				InDoor iDoor=new InDoor(
						rs.getInt("idInDoor"), 
						rs.getString("nameInDoor"), 
						rs.getInt("capacity"), 
						rs.getInt("status"), cDS);
				listInDoor.add(iDoor);
			}
			return listInDoor;
		} catch (SQLException e) { e.printStackTrace();
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(ps != null){
					ps.close();
				}
				if(rs != null){
					rs.close();
				}
			} catch (SQLException e) { e.printStackTrace(); }
		}
		return null;
	}
	public ArrayList<InDoor> getListInDoor() {
		String sql="SELECT i.idInDoor, i.nameInDoor, i.capacity, i.status, i.idCrossDockingSystem, "
				+ "c.nameCrossDockingSystem, c.address, c.capacity "
				+ "FROM tblInDoor as i "
				+ "JOIN tblCrossDockingSystem as c ON i.idCrossDockingSystem=c.idCrossDockingSystem ";
		ArrayList<InDoor> listInDoor=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listInDoor = new ArrayList<InDoor>();
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				InDoor iDoor=new InDoor(
						rs.getInt("idInDoor"), 
						rs.getString("nameInDoor"), 
						rs.getInt("capacity"), 
						rs.getInt("status"), cDS);
				listInDoor.add(iDoor);
			}
			return listInDoor;
		} catch (SQLException e) { e.printStackTrace();
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(ps != null){
					ps.close();
				}
				if(rs != null){
					rs.close();
				}
			} catch (SQLException e) { e.printStackTrace(); }
		}
		return null;
	}
	public boolean insertInDoor(InDoor iDoor){
		String sql="INSERT INTO tblInDoor(idInDoor, nameInDoor, capacity, idCrossDockingSystem, status)"
				+ "VALUES(?,?,?,?,?)";
		int n = new JdbcTemplate(dataSource).update(sql, new Object[]{ 
				getIdInDoorInsert()+1 ,iDoor.getNameDoor(), iDoor.getCapacity(), 
				iDoor.getcDS().getIdCrossDockingSystem(), iDoor.getStatus()
		});
		if(n==1) return true;
		return false;
	}
	public boolean checkTransitonStatusActiveIndoor(int idInDoor){
		String sql="SELECT idCost FROM tblCost WHERE idIndoor=? AND cost=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idInDoor);
			ps.setInt(2, 0);		
			rs = ps.executeQuery();
			if(rs.next()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	public boolean editInDoor(InDoor iDoor){
		String sql="UPDATE tblInDoor SET nameInDoor=?, capacity=?, status=? WHERE idInDoor=?";
		int n = new JdbcTemplate(dataSource).update(sql, new Object[]{ 
				iDoor.getNameDoor(), iDoor.getCapacity(), iDoor.getStatus(), iDoor.getIdDoor()
		});
		if(n==1) return true;
		return false;
	}
	/****************************************************************************
	 * OuDoor
	 * */
	
	public Integer getIdOutDoorInsert() {
		String sql="SELECT idOutDoor FROM tblOutDoor ORDER BY idOutDoor DESC";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt("idOutDoor");
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
	public ArrayList<OutDoor> getListOutDoorActive() {
		String sql="SELECT o.idOutDoor, o.nameOutDoor, o.capacity, o.status, o.idCrossDockingSystem, "
				+ "c.nameCrossDockingSystem, c.address, c.capacity "
				+ "FROM tblOutDoor as o "
				+ "JOIN tblCrossDockingSystem as c ON o.idCrossDockingSystem=c.idCrossDockingSystem "
				+ "WHERE o.status=?";
		ArrayList<OutDoor> listOutDoor=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listOutDoor = new ArrayList<OutDoor>();
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				OutDoor oDoor=new OutDoor(
						rs.getInt("idOutDoor"), 
						rs.getString("nameOutDoor"), 
						rs.getInt("capacity"), 
						rs.getInt("status"), cDS);
				listOutDoor.add(oDoor);
			}
			return listOutDoor;
		} catch (SQLException e) { e.printStackTrace();
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(ps != null){
					ps.close();
				}
				if(rs != null){
					rs.close();
				}
			} catch (SQLException e) { e.printStackTrace(); }
		}
		return null;
	}
	public ArrayList<OutDoor> getListOutDoor() {
		String sql="SELECT o.idOutDoor, o.nameOutDoor, o.capacity, o.status, o.idCrossDockingSystem, "
				+ "c.nameCrossDockingSystem, c.address, c.capacity "
				+ "FROM tblOutDoor as o "
				+ "JOIN tblCrossDockingSystem as c ON o.idCrossDockingSystem=c.idCrossDockingSystem ";
		ArrayList<OutDoor> listOutDoor=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listOutDoor = new ArrayList<OutDoor>();
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				OutDoor oDoor=new OutDoor(
						rs.getInt("idOutDoor"), 
						rs.getString("nameOutDoor"), 
						rs.getInt("capacity"), 
						rs.getInt("status"), cDS);
				listOutDoor.add(oDoor);
			}
			return listOutDoor;
		} catch (SQLException e) { e.printStackTrace();
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(ps != null){
					ps.close();
				}
				if(rs != null){
					rs.close();
				}
			} catch (SQLException e) { e.printStackTrace(); }
		}
		return null;
	}
	public boolean insertOutDoor(OutDoor oDoor){
		String sql="INSERT INTO tblOutDoor(idOutDoor, nameOutDoor, capacity, idCrossDockingSystem, status)"
				+ "VALUES(?,?,?,?,?)";
		int n = new JdbcTemplate(dataSource).update(sql, new Object[]{ 
				getIdOutDoorInsert()+1 ,oDoor.getNameDoor(), oDoor.getCapacity(), 
				oDoor.getcDS().getIdCrossDockingSystem(), oDoor.getStatus()
		});
		if(n==1) return true;
		return false;
	}
	public boolean checkTransitonStatusActiveOutdoor(int idOutDoor){
		String sql="SELECT idCost FROM tblCost WHERE idOutdoor=? AND cost=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idOutDoor);
			ps.setInt(2, 0);		
			rs = ps.executeQuery();
			if(rs.next()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	public boolean editOutDoor(OutDoor oDoor){
		String sql="UPDATE tblOutDoor SET nameOutDoor=?, capacity=?, status=? WHERE idOutDoor=?";
		int n = new JdbcTemplate(dataSource).update(sql, new Object[]{ 
				oDoor.getNameDoor(), oDoor.getCapacity(), oDoor.getStatus(), oDoor.getIdDoor()
		});
		if(n==1) return true;
		return false;
	}
	/***********************************Cost****************************************/
	public Integer countCost() {
		String sql="SELECT COUNT(*) FROM tblCost";
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
	public Integer getIdCostInsert(){
		String sql="SELECT idCost FROM tblCost ORDER BY idCost DESC";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt("idCost");
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
	public ArrayList<Cost> getListCost(){
		String sql="SELECT c.idCost, c.idInDoor, c.idOutDoor, c.cost "
				+ "FROM tblCost AS c "
				+ "JOIN tblInDoor AS i ON c.idInDoor=i.idInDoor "
				+ "JOIN tblOutDoor AS o ON c.idOutDoor=o.idOutDoor "
				+ "WHERE i.status=? AND o.status=?";
		ArrayList<Cost> listC=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listC=new ArrayList<Cost>();
			conn=dataSource.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setInt(2, 1);
			rs=ps.executeQuery();
			while(rs.next()){
				InDoor inDoor=new InDoor(); inDoor.setIdDoor(rs.getInt("idInDoor"));
				OutDoor outDoor=new OutDoor(); outDoor.setIdDoor(rs.getInt("idOutDoor"));
				Cost cost=new Cost(inDoor, outDoor, rs.getDouble("cost"));
				listC.add(cost);
			}
			return listC;
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
		return null;
	}
	public ArrayList<Cost> getPageCost(int currentPage, int sizePage) {
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT c.idCost, "
				+ "c.idInDoor, i.nameInDoor, "
				+ "c.idOutDoor, o.nameOutDoor, "
				+ "c.cost "
				+ "FROM tblCost AS c "
				+ "JOIN tblInDoor AS i ON c.idInDoor=i.idInDoor "
				+ "JOIN tblOutDoor AS o ON c.idOutDoor=o.idOutDoor "
				+ "ORDER BY cost, nameInDoor, nameOutDoor "
				+ "LIMIT ?, ?";
		ArrayList<Cost> listC=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listC = new ArrayList<Cost>();
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sIndex);
			ps.setInt(2, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				InDoor inDoor=new InDoor(); 
				inDoor.setIdDoor(rs.getInt("idInDoor")); inDoor.setNameDoor(rs.getString("nameInDoor"));			
				OutDoor outDoor=new OutDoor(); 
				outDoor.setIdDoor(rs.getInt("idOutDoor")); outDoor.setNameDoor(rs.getString("nameOutDoor"));
				Cost cost=new Cost(rs.getInt("idCost"), inDoor, outDoor, rs.getDouble("cost"));
				listC.add(cost);
			}
			return listC;
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
		return null;
	}
	public boolean insertCost(Cost cost){
		String sql="INSERT INTO tblCost(idCost, idInDoor, idOutDoor, cost) VALUES(?,?,?,?)";
		int n = new JdbcTemplate(dataSource).update(sql, new Object[]{
				getIdCostInsert()+1,cost.getInDoor().getIdDoor(), 
				cost.getOutDoor().getIdDoor(), cost.getCost()	
		});
		if(n==1) return true;
		return false;
	}
	public boolean editCost(Cost cost){
		String sql="UPDATE tblCost SET cost=? WHERE idCost=?";
		int n = new JdbcTemplate(dataSource).update(sql, new Object[]{
			cost.getCost(), cost.getIdCost()	
		});
		if(n==1) return true;
		return false;
	}
}
