package daoImpl.door;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.door.DoorDAO;
import entity.cost.Cost;
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
	
	public ArrayList<InDoor> getListInDoor() {
		String sql="SELECT i.idInDoor, i.nameInDoor, i.capacity, i.idCrossDockingSystem, "
				+ "c.nameCrossDockingSystem, c.address, c.capacity "
				+ "FROM tblInDoor as i "
				+ "JOIN tblCrossDockingSystem as c "
				+ "ON i.idCrossDockingSystem=c.idCrossDockingSystem";
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
						rs.getInt("capacity"), cDS);
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
	public ArrayList<OutDoor> getListOutDoor() {
		String sql="SELECT i.idOutDoor, i.nameOutDoor, i.capacity, i.idCrossDockingSystem, "
				+ "c.nameCrossDockingSystem, c.address, c.capacity "
				+ "FROM tblOutDoor as i "
				+ "JOIN tblCrossDockingSystem as c "
				+ "ON i.idCrossDockingSystem=c.idCrossDockingSystem";
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
						rs.getInt("capacity"), cDS);
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
	public ArrayList<Cost> getListCost() {
		String sql="SELECT idCost, idInDoor, idOutDoor, cost FROM tblCost";
		ArrayList<Cost> listC=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listC = new ArrayList<Cost>();
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				InDoor inDoor=new InDoor(); inDoor.setIdDoor(rs.getInt("idInDoor"));				
				OutDoor outDoor=new OutDoor(); outDoor.setIdDoor(rs.getInt("idOutDoor"));
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
}
