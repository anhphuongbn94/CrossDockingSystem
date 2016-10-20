package com.cds.dao.impl.door;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.cds.dao.door.DoorDAO;
import com.cds.dao.impl.BaseDAOImpl;
import com.cds.entity.door.Cost;
import com.cds.entity.door.CrossDockingSystem;
import com.cds.entity.door.InDoor;
import com.cds.entity.door.OutDoor;
import com.cds.field.door.Cost_;
import com.cds.field.door.CrossDockingSystem_;
import com.cds.field.door.InDoor_;
import com.cds.field.door.OutDoor_;

@Component
public class DoorDAOImpl extends BaseDAOImpl implements DoorDAO{
	
	public DoorDAOImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	public ArrayList<InDoor> getListInDoorActive() {
		String sql="SELECT i." +InDoor_.ID+ ", i." +InDoor_.NAME+ ", "
					+ "i." +InDoor_.CAPACITY+ ", i." +InDoor_.STATUS+ ", i." +InDoor_.ID_CDS+ ", "
				+ "c."+CrossDockingSystem_.NAME+", c."+CrossDockingSystem_.ADDRESS+", "
						+ "c."+CrossDockingSystem_.CAPACITY+" "
				+ "FROM " +InDoor_.TABLE+ " AS i "
				+ "JOIN " +CrossDockingSystem_.TABLE+ " AS c "
						+ "ON i." +InDoor_.ID_CDS+ "=c."+CrossDockingSystem_.ID+" "
				+ "WHERE i." +InDoor_.STATUS+ "=?";
		ArrayList<InDoor> listInDoor=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listInDoor = new ArrayList<InDoor>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt(CrossDockingSystem_.ID), 
						rs.getString(CrossDockingSystem_.NAME), 
						rs.getString(CrossDockingSystem_.ADDRESS), 
						rs.getInt(CrossDockingSystem_.CAPACITY));
				InDoor iDoor = new InDoor();
				iDoor.setIdDoor(rs.getLong(InDoor_.ID));
				iDoor.setNameDoor(rs.getString(InDoor_.NAME));
				iDoor.setCapacity(rs.getInt(InDoor_.CAPACITY));
				iDoor.setcDS(cDS);
				listInDoor.add(iDoor);
			}
			return listInDoor;
		} catch (SQLException e) { e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public ArrayList<InDoor> getListInDoor() {
		String sql="SELECT i." +InDoor_.ID+ ", i." +InDoor_.NAME+ ", "
				+ "i." +InDoor_.CAPACITY+ ", i." +InDoor_.STATUS+ ", i." +InDoor_.ID_CDS+ ", "
			+ "c."+CrossDockingSystem_.NAME+", c."+CrossDockingSystem_.ADDRESS+", "
					+ "c."+CrossDockingSystem_.CAPACITY+" "
			+ "FROM " +InDoor_.TABLE+ " AS i "
			+ "JOIN " +CrossDockingSystem_.TABLE+ " AS c "
					+ "ON i." +InDoor_.ID_CDS+ "=c."+CrossDockingSystem_.ID+" ";
		ArrayList<InDoor> listInDoor=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listInDoor = new ArrayList<InDoor>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt(CrossDockingSystem_.ID), 
						rs.getString(CrossDockingSystem_.NAME), 
						rs.getString(CrossDockingSystem_.ADDRESS), 
						rs.getInt(CrossDockingSystem_.CAPACITY));
				InDoor iDoor = new InDoor();
				iDoor.setIdDoor(rs.getLong(InDoor_.ID));
				iDoor.setNameDoor(rs.getString(InDoor_.NAME));
				iDoor.setCapacity(rs.getInt(InDoor_.CAPACITY));
				iDoor.setStatus(rs.getInt(InDoor_.STATUS));
				iDoor.setcDS(cDS);
				listInDoor.add(iDoor);
			}
			return listInDoor;
		} catch (SQLException e) { e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public boolean checkInsertInDoor(InDoor idoor){
		String sql="SELECT idInDoor FROM tblInDoor WHERE nameInDoor=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, idoor.getNameDoor());
			rs = ps.executeQuery();
			if(rs.next()) return false;
		} catch (SQLException e) { e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return true;
	}
	public boolean insertInDoor(InDoor iDoor){
		String sql="INSERT INTO tblInDoor"
				+ "(idInDoor, nameInDoor, capacity, idCrossDockingSystem, status)"
				+ "VALUES(?,?,?,?,?)";
		Long idSEQ=getIdSEQ(InDoor_.TABLE, InDoor_.ID);
		boolean flag=false;
		int n = getJdbcTemplate().update(sql, new Object[]{ 
				idSEQ ,
				iDoor.getNameDoor(), iDoor.getCapacity(), 
				iDoor.getcDS().getIdCrossDockingSystem(), iDoor.getStatus()
		});
		if(n==1) flag=true;
		iDoor.setIdDoor(idSEQ);
		ArrayList<OutDoor> listOD=getListOutDoor();			
		for(int i=0; i<listOD.size(); i++){
			Cost cost=new Cost(iDoor, listOD.get(i), 0d);
			flag=insertCost(cost);
		}
		return flag;
	}
	public boolean checkTransitonStatusActiveIndoor(long idInDoor){
		String sql="SELECT idCost FROM tblCost WHERE idIndoor=? AND cost=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idInDoor);
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
		int n = getJdbcTemplate().update(sql, new Object[]{ 
				iDoor.getNameDoor(), iDoor.getCapacity(), iDoor.getStatus(), iDoor.getIdDoor()
		});
		if(n==1) return true;
		return false;
	}
	/****************************************************************************
	 * OuDoor
	 * */
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
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				OutDoor oDoor = new OutDoor();
				oDoor.setIdDoor(rs.getLong("idOutDoor"));
				oDoor.setNameDoor(rs.getString("nameOutDoor"));
				oDoor.setCapacity(rs.getInt("capacity"));
				oDoor.setcDS(cDS);
				listOutDoor.add(oDoor);
			}
			return listOutDoor;
		} catch (SQLException e) { e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
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
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				OutDoor oDoor = new OutDoor();
				oDoor.setIdDoor(rs.getLong("idOutDoor"));
				oDoor.setNameDoor(rs.getString("nameOutDoor"));
				oDoor.setCapacity(rs.getInt("capacity"));
				oDoor.setStatus(rs.getInt("status"));
				oDoor.setcDS(cDS);
				listOutDoor.add(oDoor);
			}
			return listOutDoor;
		} catch (SQLException e) { e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public boolean checkInsertOutDoor(OutDoor odoor){
		String sql="SELECT idOutDoor FROM tblOutDoor WHERE nameOutDoor=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, odoor.getNameDoor());
			rs = ps.executeQuery();
			if(rs.next()) return false;
		} catch (SQLException e) { e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return true;
	}
	public boolean insertOutDoor(OutDoor oDoor){
		String sql="INSERT INTO tblOutDoor"
				+ "(idOutDoor, nameOutDoor, capacity, idCrossDockingSystem, status)"
				+ "VALUES(?,?,?,?,?)";
		boolean flag=false;
		Long idSEQ=getIdSEQ(OutDoor_.TABLE, OutDoor_.ID);
		int n = getJdbcTemplate().update(sql, new Object[]{ 
				idSEQ, oDoor.getNameDoor(), oDoor.getCapacity(), 
				oDoor.getcDS().getIdCrossDockingSystem(), oDoor.getStatus()
		});
		if(n==1) flag=true;
		oDoor.setIdDoor(idSEQ);
		ArrayList<InDoor> listID=getListInDoor();			
		for(int i=0; i<listID.size(); i++){
			Cost cost=new Cost(listID.get(i), oDoor, 0d);
			flag=insertCost(cost);
		}
		return flag;
	}
	public boolean checkTransitonStatusActiveOutdoor(int idOutDoor){
		String sql="SELECT idCost FROM tblCost WHERE idOutdoor=? AND cost=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			conn = getConnection();
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
		int n = getJdbcTemplate().update(sql, new Object[]{ 
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
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
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
			conn=getConnection();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setInt(2, 1);
			rs=ps.executeQuery();
			while(rs.next()){
				InDoor inDoor=new InDoor(); inDoor.setIdDoor(rs.getLong("idInDoor"));
				OutDoor outDoor=new OutDoor(); outDoor.setIdDoor(rs.getLong("idOutDoor"));
				Cost cost=new Cost(inDoor, outDoor, rs.getDouble("cost"));
				listC.add(cost);
			}
			return listC;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
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
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sIndex);
			ps.setInt(2, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				InDoor inDoor=new InDoor(); 
				inDoor.setIdDoor(rs.getLong("idInDoor")); inDoor.setNameDoor(rs.getString("nameInDoor"));			
				OutDoor outDoor=new OutDoor(); 
				outDoor.setIdDoor(rs.getLong("idOutDoor")); outDoor.setNameDoor(rs.getString("nameOutDoor"));
				Cost cost=new Cost(rs.getInt("idCost"), inDoor, outDoor, rs.getDouble("cost"));
				listC.add(cost);
			}
			return listC;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public boolean insertCost(Cost cost){
		String sql="INSERT INTO tblCost(idCost, idInDoor, idOutDoor, cost) VALUES(?,?,?,?)";
		int n = getJdbcTemplate().update(sql, new Object[]{
				getIdSEQ(Cost_.TABLE, Cost_.ID),cost.getInDoor().getIdDoor(), 
				cost.getOutDoor().getIdDoor(), cost.getCost()	
		});
		if(n==1) return true;
		return false;
	}
	public boolean editCost(Cost cost){
		String sql="UPDATE tblCost SET cost=? WHERE idCost=?";
		int n = getJdbcTemplate().update(sql, new Object[]{
			cost.getCost(), cost.getIdCost()	
		});
		if(n==1) return true;
		return false;
	}
}
