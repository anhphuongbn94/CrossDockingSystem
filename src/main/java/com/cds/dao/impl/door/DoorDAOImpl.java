package com.cds.dao.impl.door;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.cds.dao.door.DoorDAO;
import com.cds.dao.impl.BaseDAOImpl;
import com.cds.entity.door.Cost;
import com.cds.entity.door.CrossDockingSystem;
import com.cds.entity.door.InDoor;
import com.cds.entity.door.OutDoor;
import com.cds.entity.product.ProductTransfer;
import com.cds.entity.vehicle.InVehicle;
import com.cds.entity.vehicle.OutVehicle;
import com.cds.field.Constants;
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
				+ "WHERE i." +InDoor_.STATUS+ "=? ORDER BY i.idInDoor ASC";
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
				iDoor.setCapuse(getCapUseInDoorbyId(iDoor.getIdDoor()));
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
			Cost cost=new Cost(iDoor, listOD.get(i), 0);
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
		String sql="UPDATE tblInDoor SET capacity=?, status=? WHERE idInDoor=?";
		int n = getJdbcTemplate().update(sql, new Object[]{ 
				iDoor.getCapacity(), iDoor.getStatus(), iDoor.getIdDoor()
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
				+ "WHERE o.status=? ORDER BY o.idOutDoor";
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
				oDoor.setCapuse(getCapUseOutDoorbyId(oDoor.getIdDoor()));
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
			Cost cost=new Cost(listID.get(i), oDoor, 0);
			flag=insertCost(cost);
		}
		return flag;
	}
	public boolean checkTransitonStatusActiveOutdoor(long idOutDoor){
		String sql="SELECT idCost FROM tblCost WHERE idOutdoor=? AND cost=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idOutDoor);
			ps.setInt(2, 0);		
			rs = ps.executeQuery();
			if(rs.next()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	public boolean editOutDoor(OutDoor oDoor){
		String sql="UPDATE tblOutDoor SET capacity=?, status=? WHERE idOutDoor=?";
		int n = getJdbcTemplate().update(sql, new Object[]{ 
				oDoor.getCapacity(), oDoor.getStatus(), oDoor.getIdDoor()
		});
		if(n==1) return true;
		return false;
	}
	/***********************************Cost****************************************/
	public ArrayList<Cost> getAllListCost(){
		String sql="SELECT c.idInDoor, c.idOutDoor, c.cost FROM tblCost c "
				+ "JOIN tblInDoor id ON c.idInDoor=id.idInDoor "
				+ "JOIN tblOutDoor od ON c.idOutDoor=od.idOutDoor "
				+ "WHERE id.status=1 and od.status=1 "
				+ "ORDER BY c.idInDoor, c.idOutDoor";
		ArrayList<Cost> list=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			list = new ArrayList<Cost>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				InDoor idoor=new InDoor();
				idoor.setIdDoor(rs.getLong("idInDoor"));
				OutDoor odoor=new OutDoor();
				odoor.setIdDoor(rs.getLong("idOutDoor"));
				
				Cost c=new Cost();
				c.setInDoor(idoor);
				c.setOutDoor(odoor);
				c.setCost(rs.getInt("cost"));
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return list;
	}
	public ArrayList<Cost> getListCostByIdInDoor(Long idDoor){
		String sql="SELECT c.idCost, i.nameInDoor, o.nameOutDoor, c.cost "
				+ "FROM tblCost c "
				+ "JOIN tblInDoor i ON c.idInDoor=i.idInDoor "
				+ "JOIN tblOutDoor o ON c.idOutDoor=o.idOutDoor "
				+ "WHERE c.idInDoor=?";
		ArrayList<Cost> list=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			list = new ArrayList<Cost>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idDoor);
			rs = ps.executeQuery();
			while(rs.next()){
				InDoor idoor=new InDoor();
				idoor.setNameDoor(rs.getString("nameInDoor"));
				OutDoor odoor=new OutDoor();
				odoor.setNameDoor(rs.getString("nameOutDoor"));
				
				Cost c=new Cost();
				c.setIdCost(rs.getLong("idCost"));
				c.setInDoor(idoor);
				c.setOutDoor(odoor);
				c.setCost(rs.getInt("cost"));
				list.add(c);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public ArrayList<Cost> getListCostByIdOutDoor(Long idDoor){
		String sql="SELECT c.idCost, i.nameInDoor, o.nameOutDoor, c.cost "
				+ "FROM tblCost c "
				+ "JOIN tblInDoor i ON c.idInDoor=i.idInDoor "
				+ "JOIN tblOutDoor o ON c.idOutDoor=o.idOutDoor "
				+ "WHERE c.idOutdoor=?";
		ArrayList<Cost> list=new ArrayList<Cost>();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idDoor);
			rs = ps.executeQuery();
			while(rs.next()){
				InDoor idoor=new InDoor();
				idoor.setNameDoor(rs.getString("nameInDoor"));
				OutDoor odoor=new OutDoor();
				odoor.setNameDoor(rs.getString("nameOutDoor"));
				
				Cost c=new Cost();
				c.setIdCost(rs.getLong("idCost"));
				c.setInDoor(idoor);
				c.setOutDoor(odoor);
				c.setCost(rs.getInt("cost"));
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return list;
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

	public boolean delIndoor(Long idDoor) {
		String sql="DELETE FROM tblCost WHERE idInDoor=?";
		String sql1="DELETE FROM tblInDoor WHERE idInDoor=?";
		Connection conn=null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idDoor);
			ps.addBatch();
			ps1 = conn.prepareStatement(sql1);
			ps1.setLong(1, idDoor);
			ps1.addBatch();
			int n = ps.executeBatch().length + ps1.executeBatch().length;
			if(n==2){
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, null);
			closeConnection(null, ps1, null);
		}
		return false;
	}

	public boolean delOutDoor(Long idDoor) {
		String sql="DELETE FROM tblCost WHERE idOutDoor=?";
		String sql1="DELETE FROM tblOutDoor WHERE idOutDoor=?";
		Connection conn=null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idDoor);
			ps.addBatch();
			ps1 = conn.prepareStatement(sql1);
			ps1.setLong(1, idDoor);
			ps1.addBatch();
			int n = ps.executeBatch().length + ps1.executeBatch().length;
			if(n==2){
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, null);
			closeConnection(null, ps1, null);
		}
		return false;
	}

	public boolean delCost(String id, long value) {	
		return delRecordTable(Cost_.TABLE, id, value);
	}
	
	public int getCapUseInDoorbyId(Long idInDoor) {
		String sql="SELECT SUM(quantity) FROM tblProductInVehicle "
				+ "WHERE idInVehicle IN("
					+ "SELECT idInVehicle FROM tblInVehicle "
					+ "WHERE DATE=CURDATE() AND idInDoor=?"
				+ ")";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idInDoor);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public int getCapUseOutDoorbyId(Long idOutDoor) {
		String sql="SELECT SUM(quantity) FROM tblProductOutVehicle "
				+ "WHERE idOutVehicle IN("
					+ "SELECT idOutVehicle FROM tblOutVehicle "
					+ "WHERE DATE=CURDATE() AND idOutDoor=?"
				+ ")";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idOutDoor);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public double getTotalCostCurDate(){
		String sql="SELECT SUM(p.transfer*c.cost) totalCost FROM tblProductTransfer p "
					+ "JOIN tblInVehicle iv ON iv.idInVehicle=p.idInVehicle " 
					+ "JOIN tblOutVehicle ov ON ov.idOutVehicle=p.idOutVehicle "
					+ "JOIN tblCost c ON (c.idInDoor=iv.idInDoor AND c.idOutDoor=ov.idOutDoor) "
					+ "WHERE iv.date=CURDATE() AND ov.date=CURDATE() AND (iv.status < 2 AND ov.status < 2)";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getDouble("totalCost");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public double getTotalCostAICurDate(){
		String sql="SELECT SUM(p.transfer*c.cost) totalCost FROM tblProductTransfer p "
					+ "JOIN tblInVehicle iv ON iv.idInVehicle=p.idInVehicle " 
					+ "JOIN tblOutVehicle ov ON ov.idOutVehicle=p.idOutVehicle "
					+ "JOIN tblCost c ON (c.idInDoor=iv.idInDoorAI AND c.idOutDoor=ov.idOutDoorAI) "
					+ "WHERE iv.date=CURDATE() AND ov.date=CURDATE() AND (iv.status < 2 AND ov.status < 2)";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getDouble("totalCost");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public double getTotalCostNotInVehicle(Long idInVehicle){
		String sql="SELECT SUM(p.transfer*c.cost) totalCost FROM tblProductTransfer p "
				+ "JOIN tblInVehicle iv ON iv.idInVehicle=p.idInVehicle " 
				+ "JOIN tblOutVehicle ov ON ov.idOutVehicle=p.idOutVehicle "
				+ "JOIN tblCost c ON (c.idInDoor=iv.idInDoor AND c.idOutDoor=ov.idOutDoor) "
				+ "WHERE iv.date=CURDATE() AND ov.date=CURDATE() "
				+ "AND p.idInVehicle <> ?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idInVehicle);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getDouble("totalCost");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public double getTotalCostNotOutVehicle(Long idOutVehicle){
		String sql="SELECT SUM(p.transfer*c.cost) totalCost FROM tblProductTransfer p "
				+ "JOIN tblInVehicle iv ON iv.idInVehicle=p.idInVehicle " 
				+ "JOIN tblOutVehicle ov ON ov.idOutVehicle=p.idOutVehicle "
				+ "JOIN tblCost c ON (c.idInDoor=iv.idInDoor AND c.idOutDoor=ov.idOutDoor) "
				+ "WHERE iv.date=CURDATE() AND ov.date=CURDATE() "
				+ "AND p.idOutVehicle <> ?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idOutVehicle);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getDouble("totalCost");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public double getCostInVehicleAssign(Long idInVehicle, Long idInDoor){
		String sql="SELECT SUM(p.transfer*c.cost) totalCost "
					+ "FROM tblProductTransfer p "
					+ "JOIN tblOutVehicle ov ON ov.idOutVehicle=p.idOutVehicle "
					+ "JOIN tblCost c ON ov.idOutDoor=c.idOutDoor "
					+ "WHERE p.idInVehicle=? AND c.idInDoor=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idInVehicle);
			ps.setLong(2, idInDoor);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getDouble("totalCost");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public double getCostOutVehicleAssign(Long idOutVehicle, Long idOutDoor){
		String sql="SELECT SUM(p.transfer*c.cost) totalCost "
				+ "FROM tblProductTransfer p "
				+ "JOIN tblInVehicle iv ON iv.idInVehicle=p.idInVehicle "
				+ "JOIN tblCost c ON iv.idInDoor=c.idInDoor "
				+ "WHERE p.idOutVehicle=? AND c.idOutDoor=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idOutVehicle);
			ps.setLong(2, idOutDoor);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getDouble("totalCost");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}

	public boolean assignDoorIV(Long idInVehicle, Long idInDoor) {
		String sql="UPDATE tblInVehicle SET idInDoor=? WHERE idInVehicle=?";
		String sql1="UPDATE tblInVehicle SET status=? WHERE idInVehicle=?";
		Connection conn = null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps1 = conn.prepareStatement(sql1);
			ps.setLong(1, idInDoor);
			ps.setLong(2, idInVehicle);
			ps.addBatch();
			ps1.setInt(1, Constants.WATTING_UNLOAD_STATUS);
			ps1.setLong(2, idInVehicle);
			ps1.addBatch();
			if(ps.executeBatch().length + ps1.executeBatch().length == 2){
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, null);
			closeConnection(null, ps1, null);
		}
		return false;
	}
	
	public boolean assignDoorOV(Long idOutVehicle, Long idOutDoor) {
		String sql="UPDATE tblOutVehicle SET idOutDoor=? WHERE idOutVehicle=?";
		String sql1="UPDATE tblOutVehicle SET status=? WHERE idOutVehicle=?";
		Connection conn = null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps1 = conn.prepareStatement(sql1);
			ps.setLong(1, idOutDoor);
			ps.setLong(2, idOutVehicle);
			ps.addBatch();
			ps1.setInt(1, Constants.WATTING_LOAD_STATUS);
			ps1.setLong(2, idOutVehicle);
			ps1.addBatch();
			if(ps.executeBatch().length + ps1.executeBatch().length == 2){
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, null);
			closeConnection(null, ps1, null);
		}
		return false;
	}
	public boolean checkUpdateInDoor(Long idInDoor) {
		String sql="SELECT * FROM tblInVehicle WHERE idInDoor=? AND DATE=CURDATE()";
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idInDoor);
			rs = ps.executeQuery();
			if(rs.next()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean checkUpdateOutDoor(Long idOutDoor) {
		String sql="SELECT * FROM tblOutVehicle WHERE idOutDoor=? AND DATE=CURDATE()";
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idOutDoor);
			rs = ps.executeQuery();
			if(rs.next()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	public ArrayList<ProductTransfer> getListProductTransfer(Long [] arrIV, Long [] arrOV){
		String strArrIV = Arrays.toString(arrIV).replace("[", "(").replace("]", ")");
		String strArrOV = Arrays.toString(arrOV).replace("[", "(").replace("]", ")");
		System.out.println("strIV=" + strArrIV);
		System.out.println("strOV" + strArrOV);
		String sql="SELECT idInVehicle, idOutVehicle, transfer FROM tblProductTransfer WHERE idInVehicle IN "+strArrIV+" AND idOutVehicle IN " + strArrOV;
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ArrayList<ProductTransfer> listPT=new ArrayList<ProductTransfer>();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
//			ps.setArray(1, conn.createArrayOf("text", arrIV));
//			ps.setArray(2, conn.createArrayOf("text", arrOV));
			rs = ps.executeQuery();
			while(rs.next()){
				InVehicle iv=new InVehicle();
				iv.setIdInVehicle(rs.getLong("idInVehicle"));
				OutVehicle ov=new OutVehicle();
				ov.setIdOutVehicle(rs.getLong("idOutVehicle"));
				
				ProductTransfer pt=new ProductTransfer();
				pt.setIv(iv);
				pt.setOv(ov);
				pt.setTransfer(rs.getInt("transfer"));
				listPT.add(pt);
			}
			return listPT;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean upAssignDoorAI(ArrayList<InVehicle> listIV, 
			ArrayList<OutVehicle> listOV, int[] arrInDoorAI, int[] arrOutDoorAI) {
		
		String strIV="(";
		String sql="UPDATE tblInVehicle SET idInDoorAI = CASE idInVehicle ";
		int i=0;
		for(InVehicle iv : listIV){
			sql += "WHEN " + iv.getIdInVehicle() + " THEN " + arrInDoorAI[i] + " ";
			strIV += iv.getIdInVehicle() + ", ";
			i++;
		}
		strIV = strIV.substring(0, strIV.length() - 2);
		strIV += ")";
		sql += "ELSE idInDoorAI END WHERE idInVehicle IN " + strIV;
		
		String strOV="(";
		String sql1="UPDATE tblOutVehicle SET idOutDoorAI = CASE idOutVehicle ";
		i=0;
		for(OutVehicle ov : listOV){
			sql1 += "WHEN " + ov.getIdOutVehicle() + " THEN " + arrOutDoorAI[i] + " ";
			strOV += ov.getIdOutVehicle() + ", ";
			i++;
		}
		strOV = strOV.substring(0, strOV.length() - 2);
		strOV += ")";
		sql1 += "ELSE idOutDoorAI END WHERE idOutVehicle IN " + strOV;
		
		Connection conn=null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps1 = conn.prepareStatement(sql1);
			ps.addBatch();
			ps1.addBatch();
			
			int n = ps.executeBatch().length + ps1.executeBatch().length;
			if(n == 2){
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			}
		} catch (SQLException e) {
			closeConnection(conn, ps, null);
			closeConnection(null, ps1, null);
		}
		return false;
	}
	@Override
	public boolean useAssignDoorAI(ArrayList<InVehicle> listIV, ArrayList<OutVehicle> listOV) {
		String strIV="(";
		String strOV="(";
		for(InVehicle iv:listIV){
			strIV += iv.getIdInVehicle() + ", ";
		}
		strIV = strIV.substring(0, strIV.length()-2);
		strIV += ")";
		
		for(OutVehicle ov:listOV){
			strOV +=ov.getIdOutVehicle() + ", ";			
		}
		strOV = strOV.substring(0, strOV.length()-2);
		strOV += ")";
		
		System.out.println("Str IV=" + strIV);		
		System.out.println("Str OV=" + strOV);
		
		String sql = "UPDATE tblInVehicle SET idInDoor=idInDoorAI WHERE idInVehicle IN " + strIV;
		String sql1 = "UPDATE tblOutVehicle SET idOutDoor=idOutDoorAI WHERE idOutVehicle IN " + strOV;
		
		Connection conn=null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps1 = conn.prepareStatement(sql1);
			ps.addBatch();
			ps1.addBatch();
			
			int n = ps.executeBatch().length + ps1.executeBatch().length;
			if(n == 2){
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			}
		} catch (SQLException e) {
			closeConnection(conn, ps, null);
			closeConnection(null, ps1, null);
		}
		return false;
	}
	@Override
	public boolean checkExistsAssignDoorAI() {
		String sql="SELECT idInVehicle FROM tblInVehicle WHERE date=CURDATE() AND status < 2 AND idInDoorAI=-1";
//		String sql1="SELECT idOutVehicle FROM tblOutVehicle WHERE date=CURDATE() AND status < 2 AND idOutDoorAI=-1";
		Connection conn=null;
		PreparedStatement ps=null;
//		PreparedStatement ps1=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			closeConnection(conn, ps, rs);
//			closeConnection(null, ps1, null);
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean checkEditIndoor(Long idInDoor) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkDelInDoor(Long idInDoor) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkEditOutDoor(Long idOutDoor) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkDelOutDoor(Long idOutDoor) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
