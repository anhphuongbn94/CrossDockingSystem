package com.cds.dao.impl.vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cds.dao.impl.BaseDAOImpl;
import com.cds.dao.vehicle.VehicleDAO;
import com.cds.entity.door.CrossDockingSystem;
import com.cds.entity.door.InDoor;
import com.cds.entity.door.OutDoor;
import com.cds.entity.product.ProductTransfer;
import com.cds.entity.vehicle.InVehicle;
import com.cds.entity.vehicle.OutVehicle;
import com.cds.entity.vehicle.Vehicle;
import com.cds.field.vehicle.InVehicle_;
import com.cds.field.vehicle.OutVehicle_;
import com.cds.field.vehicle.Vehicle_;
import com.cds.tool.MyTool;

@Component
public class VehicleDAOImpl extends BaseDAOImpl implements VehicleDAO{
	private MyTool myTool=new MyTool();
	
	@Autowired
	public VehicleDAOImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	/* ****************************************************************************************************
	 * ****************************************************************************************************
	 * ****************************************************************************************************
	 * Vehicle
	 * */
	public ArrayList<Vehicle> getListVehicle(){
		String sql="SELECT v.idVehicle, v.code, v.type, v.company, v.description "
				+ "FROM tblVehicle AS v ";
			ArrayList<Vehicle> listV=null;
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				listV = new ArrayList<Vehicle>();
				conn = getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()){
					Vehicle v = new InVehicle();
					v.setIdVehicle(rs.getLong("idVehicle"));
					v.setVehicleCode(rs.getString("code"));
					v.setType(rs.getString("type"));
					v.setDescription(rs.getString("description"));
					listV.add(v);
				}
				return listV;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(conn, ps, rs);				
			}
			return null;
	}
	public ArrayList<Vehicle> getPageVehicle(int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT v.idVehicle, v.code, v.type, v.company, v.description "
				+ "FROM tblVehicle AS v "
				+ "ORDER BY v.idVehicle DESC "
				+ "LIMIT ?, ?";
			ArrayList<Vehicle> listV=null;
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				listV = new ArrayList<Vehicle>();
				conn = getConnection();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, sIndex);
				ps.setInt(2, sizePage);
				rs = ps.executeQuery();
				while(rs.next()){
					Vehicle v = new InVehicle();
					v.setIdVehicle(rs.getLong("idVehicle"));
					v.setVehicleCode(rs.getString("code"));
					v.setType(rs.getString("type"));
					v.setCompany(rs.getString("company"));
					v.setDescription(rs.getString("description"));
					listV.add(v);
				}
				return listV;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(conn, ps, rs);
			}
			return null;
	}
	public boolean checkInsertVehicle(Vehicle v){
		String sql="SELECT idVehicle FROM tblVehicle WHERE code=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, v.getVehicleCode());
			rs = ps.executeQuery();
			if(rs.next()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return true;
	}
	public boolean insertVehicle(Vehicle v){
		String sql="INSERT INTO tblVehicle(idVehicle, code, type, company, description) "
				+ "VALUES(?, ?, ?, ?, ?)";
		int n = getJdbcTemplate().update(sql, new Object[]{ 
				getIdSEQ(Vehicle_.TABLE, Vehicle_.ID), v.getVehicleCode(),
				v.getType(), v.getCompany(), v.getDescription()
		});
		if(n==1) return true;
		return false;
	}
	/* ****************************************************************************************************
	 * ****************************************************************************************************
	 * ****************************************************************************************************
	 * InVehicle
	 * */
	public ArrayList<InVehicle> getPageInVehicle(int currentPage, int sizePage) {
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT iv.idInVehicle, iv.date, "
			+ "iv.arrivalTime, iv.startUnloadTime, iv.finishUnloadTime, iv.status, iv.volumn, " 
		    + "iv.idInDoor, i.nameInDoor, i.capacity, "
		    + "iv.idVehicle, v.code, "
		    + "iv.idCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			+ "FROM tblInVehicle AS iv "
			+ "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle "
			+ "LEFT JOIN tblInDoor AS i ON iv.idInDoor=i.idInDoor "
			+ "JOIN tblCrossDockingSystem AS c ON iv.idCrossDockingSystem=c.idCrossDockingSystem "
			+ "WHERE date=? "
			+ "ORDER BY idInVehicle DESC "
			+ "LIMIT ?, ?";
		ArrayList<InVehicle> listIV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listIV = new ArrayList<InVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, myTool.getDateSystem());
			ps.setInt(2, sIndex);
			ps.setInt(3, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				InDoor iDoor = new InDoor();
				iDoor.setIdDoor(rs.getLong("idInDoor"));
				iDoor.setNameDoor(rs.getString("nameInDoor"));
				iDoor.setCapacity(rs.getInt("capacity"));
				
				InVehicle inV = new InVehicle();
				inV.setIdVehicle(rs.getLong("idVehicle"));
				inV.setVehicleCode(rs.getString("code"));
				inV.setIdInVehicle(rs.getLong("idInVehicle"));
				inV.setDate(rs.getString("date"));
				inV.setArrivalTime(rs.getString("arrivalTime"));
				inV.setStatus(rs.getInt("status"));
				inV.setVolumn(rs.getDouble("volumn"));
				inV.setDoor(iDoor);
				inV.setcDS(cDS);
				inV.setStartUnloadTime(rs.getString("startUnloadTime"));
				inV.setFinishUnloadTime(rs.getString("finishUnloadTime"));
				listIV.add(inV);
			}
			return listIV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public ArrayList<InVehicle> getPageInVehicle_byDateAndCodeVehicle(
			String strDate, String endDate, String vehicleCode, int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT iv.idInVehicle, v.code, iv.date, iv.arrivalTime, iv.volumn "
			+ "FROM tblInVehicle AS iv "
			+ "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle AND v.code LIKE ? "
			+ "WHERE date BETWEEN ? AND ? "
			+ "LIMIT ?, ?";
			;
		ArrayList<InVehicle> listIV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listIV = new ArrayList<InVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +vehicleCode+ "%");
			ps.setString(2, strDate);
			ps.setString(3, endDate);
			ps.setInt(4, sIndex);
			ps.setInt(5, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				InVehicle inV=new InVehicle();
				inV.setIdInVehicle(rs.getLong("idInVehicle"));
				inV.setVehicleCode(rs.getString("code"));
				inV.setArrivalTime(rs.getString("arrivalTime"));
				inV.setVolumn(rs.getDouble("volumn"));
				inV.setDate(rs.getString("date"));
				listIV.add(inV);
			}
			return listIV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null; 
	}
	public ArrayList<InVehicle> getListInVehicle_byCurDate(String date){
		String sql="SELECT iv.idInVehicle, v.code, iv.date "
			+ "FROM tblInVehicle AS iv "
			+ "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle "
			+ "WHERE status=? AND date=?"
			;
		ArrayList<InVehicle> listIV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listIV = new ArrayList<InVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, date);
			rs = ps.executeQuery();
			while(rs.next()){
				InVehicle inV=new InVehicle();
				inV.setIdInVehicle(rs.getLong("idInVehicle"));
				inV.setVehicleCode(rs.getString("code"));
				inV.setDate(rs.getString("date"));
				listIV.add(inV);
			}
			return listIV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null; 
	}
	public ArrayList<InVehicle> getPageInVehicle_whereAssignDoor(int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;		
		String sql="SELECT iv.idInVehicle, iv.date, "
			 + "iv.arrivalTime, iv.startUnloadTime, iv.finishUnloadTime, iv.status, iv.volumn, " 
		     + "iv.idInDoor, i.nameInDoor, i.capacity, "
		     + "iv.idVehicle, v.code, "
		     + "iv.idCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			 + "FROM tblInVehicle AS iv "
			 + "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle "
			 + "LEFT JOIN tblInDoor AS i ON iv.idInDoor=i.idInDoor "
			 + "JOIN tblCrossDockingSystem AS c ON iv.idCrossDockingSystem=c.idCrossDockingSystem " 
			 + "WHERE (iv.status=? OR iv.status=?) AND iv.date=? "
			 + "ORDER BY idInVehicle DESC " 						                          
			 + "LIMIT  ? , ?"             
             ;
		ArrayList<InVehicle> listIV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listIV = new ArrayList<InVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setInt(2, 1);
			ps.setString(3, myTool.getDateSystem());
			ps.setInt(4, sIndex);
			ps.setInt(5, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				InDoor iDoor = new InDoor();
				iDoor.setIdDoor(rs.getLong("idInDoor"));
				iDoor.setNameDoor(rs.getString("nameInDoor"));
				iDoor.setCapacity(rs.getInt("capacity"));
				
				InVehicle inV = new InVehicle();
				inV.setIdVehicle(rs.getLong("idVehicle"));
				inV.setVehicleCode(rs.getString("code"));
				inV.setIdInVehicle(rs.getLong("idInVehicle"));
				inV.setDate(rs.getString("date"));
				inV.setArrivalTime(rs.getString("arrivalTime"));
				inV.setStatus(rs.getInt("status"));
//				inV.setVolumn(getVolumnInVehicle(rs.getInt("idInVehicle")));
				inV.setVolumn(rs.getDouble("volumn"));
				inV.setDoor(iDoor);
				inV.setcDS(cDS);
				inV.setStartUnloadTime(rs.getString("startUnloadTime"));
				inV.setFinishUnloadTime(rs.getString("finishUnloadTime"));
				listIV.add(inV);
			}
			return listIV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public ArrayList<InVehicle> getPageInVehicle_byStatus(int status, int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT iv.idInVehicle, iv.date, "
			 + "iv.arrivalTime, iv.startUnloadTime, iv.finishUnloadTime, iv.status, " 
		     + "iv.idInDoor, i.nameInDoor, i.capacity, "
		     + "iv.idVehicle, v.code, "
		     + "iv.idCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			 + "FROM tblInVehicle AS iv "
			 + "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle "
		  	 + "LEFT JOIN tblInDoor AS i ON iv.idInDoor=i.idInDoor "
			 + "JOIN tblCrossDockingSystem AS c ON iv.idCrossDockingSystem=c.idCrossDockingSystem " 
             + "WHERE iv.status=? AND iv.date=? "
			 + "ORDER BY idInVehicle DESC " 						                          
			 + "LIMIT  ? , ?"             
             ;
		ArrayList<InVehicle> listIV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listIV = new ArrayList<InVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setString(2, myTool.getDateSystem());
			ps.setInt(3, sIndex);
			ps.setInt(4, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				InDoor iDoor = new InDoor();
				iDoor.setIdDoor(rs.getLong("idInDoor"));
				iDoor.setNameDoor(rs.getString("nameInDoor"));
				iDoor.setCapacity(rs.getInt("capacity"));
				
				InVehicle inV = new InVehicle();
				inV.setIdVehicle(rs.getLong("idVehicle"));
				inV.setVehicleCode(rs.getString("code"));
				inV.setIdInVehicle(rs.getLong("idInVehicle"));
				inV.setDate(rs.getString("date"));
				inV.setArrivalTime(rs.getString("arrivalTime"));
				inV.setStatus(rs.getInt("status"));
				inV.setVolumn(getVolumnInVehicle(rs.getInt("idInVehicle")));
				inV.setDoor(iDoor);
				inV.setcDS(cDS);
				inV.setStartUnloadTime(rs.getString("startUnloadTime"));
				inV.setFinishUnloadTime(rs.getString("finishUnloadTime"));
				listIV.add(inV);
			}
			return listIV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public ArrayList<InVehicle> getPageInVehicle_whereUnloadStatus(int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT iv.idInVehicle, iv.date, "
			 + "iv.arrivalTime, iv.startUnloadTime, iv.finishUnloadTime, iv.status, " 
		     + "iv.idInDoor, i.nameInDoor, i.capacity, "
		     + "iv.idVehicle, v.code, "
		     + "iv.idCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			 + "FROM tblInVehicle AS iv "
			 + "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle "
		  	 + "LEFT JOIN tblInDoor AS i ON iv.idInDoor=i.idInDoor "
			 + "JOIN tblCrossDockingSystem AS c ON iv.idCrossDockingSystem=c.idCrossDockingSystem "
             + "WHERE iv.status=? OR iv.status=? "
			 + "ORDER BY idInVehicle DESC " 						                          
			 + "LIMIT  ? , ?"             
             ;
		ArrayList<InVehicle> listIV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listIV = new ArrayList<InVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setInt(2, 2);
			ps.setInt(3, sIndex);
			ps.setInt(4, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				InDoor iDoor = new InDoor();
				iDoor.setIdDoor(rs.getLong("idInDoor"));
				iDoor.setNameDoor(rs.getString("nameInDoor"));
				iDoor.setCapacity(rs.getInt("capacity"));
				
				InVehicle inV = new InVehicle();
				inV.setIdVehicle(rs.getLong("idVehicle"));
				inV.setVehicleCode(rs.getString("code"));
				inV.setIdInVehicle(rs.getLong("idInVehicle"));
				inV.setDate(rs.getString("date"));
				inV.setArrivalTime(rs.getString("arrivalTime"));
				inV.setStatus(rs.getInt("status"));
				inV.setVolumn(getVolumnInVehicle(rs.getInt("idInVehicle")));
				inV.setDoor(iDoor);
				inV.setcDS(cDS);
				inV.setStartUnloadTime(rs.getString("startUnloadTime"));
				inV.setFinishUnloadTime(rs.getString("finishUnloadTime"));
				listIV.add(inV);
			}
			return listIV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public int getIdVehicle_byVehicleCode(String vehicleCode){
		String sql="SELECT idVehicle FROM tblVehicle WHERE vehicleCode=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt("idVehicle");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public boolean checkInsertIV(InVehicle iv){
		String sql="SELECT idInVehicle FROM tblInVehicle iv "
				+ "JOIN tblVehicle v ON iv.idVehicle=v.idVehicle AND v.idVehicle=? "
				+ "WHERE date=? AND arrivalTime=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, iv.getIdVehicle());
			ps.setString(2, iv.getDate());
			ps.setString(3, iv.getArrivalTime());
			rs = ps.executeQuery();
			if(rs.next()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return true;
	}
	public void insertInVehicle(InVehicle iv) {
		String sql="INSERT INTO tblInVehicle"
				+ "(idInVehicle, date, arrivalTime, volumn, "
				+ "status, idVehicle, idCrossDockingSystem) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql, new Object[]{ 
				getIdSEQ(InVehicle_.TABLE, InVehicle_.ID), iv.getDate(), 
				iv.getArrivalTime(), iv.getVolumn(), iv.getStatus(), iv.getIdVehicle(), 
				iv.getcDS().getIdCrossDockingSystem()
		});
	}
	
	public boolean assignDoorInVehicle(int idInVehicle, int idInDoor){
		String sql="UPDATE tblInVehicle SET idInDoor=? WHERE idInVehicle=?";
		int i = getJdbcTemplate().update(sql, new Object[]{ idInDoor, idInVehicle});
		return (i==1);
	}
	public boolean upStartUnloadTime(int idInVehicle){
		String sql="UPDATE tblInVehicle SET startUnloadTime=? WHERE idInVehicle=?";
		int i = getJdbcTemplate().update(sql, new Object[]{ myTool.getTimeSystem(), idInVehicle});
		return (i==1);
	}
	public boolean upFinishUnloadTime(int idInVehicle){
		String sql="UPDATE tblInVehicle SET finishUnloadTime=? WHERE idInVehicle=?";
		int i = getJdbcTemplate().update(sql, new Object[]{ myTool.getTimeSystem(), idInVehicle});
		return (i==1);
	}
	public boolean upStatusInVehicle(int idInVehicle, int status){
		String sql="UPDATE tblInVehicle SET status=? WHERE idInVehicle=?";
		int i = getJdbcTemplate().update(sql, new Object[]{ status, idInVehicle});
		return (i==1);
	}
	public void delInVehicle_byIdVehicle(int idInVehicle) {
		String sql="DELETE FROM tblInVehicle WHERE idInVehicle=?";
		getJdbcTemplate().update(sql, new Object[]{ idInVehicle });
	}
	public int countInVehicle_whereUnloadStatus(){
		String sql="SELECT COUNT(*) FROM tblInVehicle WHERE status=? OR status=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setInt(2, 2);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public int countInVehicle_byDateAndCodeVehicle(String strDate, String endDate, String vehicleCode){
		String sql="SELECT COUNT(*) FROM tblInVehicle iv "
				+ "JOIN tblVehicle v ON iv.idVehicle=v.idVehicle AND code LIKE ? "
				+ "WHERE date BETWEEN ? AND ?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +vehicleCode+ "%");
			ps.setString(2, strDate);
			ps.setString(3, endDate);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public int countInVehicle_byStatus(int status){
		String sql="SELECT COUNT(*) FROM tblInVehicle WHERE status=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, status);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public int countInVehicle_whereAssignDoor(){
		String sql="SELECT COUNT(*) FROM tblInVehicle "
				+ "WHERE (status=? OR status=?) "
				+ "AND date='"+myTool.getDateSystem()+"'";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setInt(2, 1);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public int countInVehicle() {
		int count=countAll(InVehicle_.TABLE);
		return count;
	}
	public Double getVolumnInVehicle(int idInVehicle){
		String sql="SELECT pV.idProductInVehicle, "
				+ "SUM(pV.quantity) AS volumn, "
				+ "pV.unit, pV.idProduct, pV.idInVehicle "
				+ "FROM tblProductInVehicle AS pV "
				+ "WHERE idInVehicle=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idInVehicle);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getDouble("volumn");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0d;
	}
	/* ****************************************************************************************************
	 * ****************************************************************************************************
	 * ****************************************************************************************************
	 * ****************************************************************************************************
	 * OutVehicle
	 * */
	public ArrayList<OutVehicle> getPageOutVehicle(int currentPage, int sizePage) {
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT ov.idOutVehicle, ov.date, "
			+ "ov.arrivalTime, ov.startLoadTime, ov.finishLoadTime, ov.status, ov.demand, "
			+ "ov.idVehicle, v.code, " 
		    + "ov.idOutDoor, o.nameOutDoor, o.capacity, "
		    + "ov.idCrossDockingSystem, c.nameCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			+ "FROM tblOutVehicle AS ov "
			+ "LEFT JOIN tblOutDoor AS o ON ov.idOutDoor=o.idOutDoor "
			+ "JOIN tblVehicle AS v ON ov.idVehicle=v.idVehicle "			
			+ "JOIN tblCrossDockingSystem AS c ON ov.idCrossDockingSystem=c.idCrossDockingSystem "
			+ "WHERE date=? "
			+ "ORDER BY idOutVehicle DESC "
			+ "LIMIT ?, ?";
		ArrayList<OutVehicle> listOV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listOV = new ArrayList<OutVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, myTool.getDateSystem());
			ps.setInt(2, sIndex);
			ps.setInt(3, sizePage);
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
				
				OutVehicle outV = new OutVehicle();
				outV.setIdVehicle(rs.getLong("idVehicle"));
				outV.setVehicleCode(rs.getString("code"));
				outV.setIdOutVehicle(rs.getLong("idOutVehicle"));
				outV.setDate(rs.getString("date"));
				outV.setArrivalTime(rs.getString("arrivalTime"));
				outV.setStartLoadTime(rs.getString("startLoadTime"));
				outV.setFinishLoadTime(rs.getString("finishLoadTime"));
				outV.setDemand(rs.getDouble("demand"));
				outV.setStatus(rs.getInt("status"));
				outV.setDoor(oDoor);
				outV.setcDS(cDS);
				listOV.add(outV);
			}
			return listOV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public ArrayList<OutVehicle> getPageOutVehicle_byDateAndCodeVehicle
		(String strDate, String endDate, String vehicleCode, int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT ov.idOutVehicle, v.code, ov.date, ov.arrivalTime, ov.demand "
			+ "FROM tblOutVehicle AS ov "
			+ "JOIN tblVehicle AS v ON ov.idVehicle=v.idVehicle AND code LIKE ? "
			+ "WHERE date BETWEEN ? AND ? "
			+ "LIMIT ?, ?";
			;
		ArrayList<OutVehicle> listOV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listOV = new ArrayList<OutVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +vehicleCode+ "%");
			ps.setString(2, strDate);
			ps.setString(3, endDate);
			ps.setInt(4, sIndex);
			ps.setInt(5, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				OutVehicle outV=new OutVehicle();
				outV.setIdOutVehicle(rs.getLong("idOutVehicle"));
				outV.setVehicleCode(rs.getString("code"));
				outV.setArrivalTime(rs.getString("arrivalTime"));
				outV.setDemand(rs.getDouble("demand"));
				outV.setDate(rs.getString("date"));
				listOV.add(outV);
			}
			return listOV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public ArrayList<OutVehicle> getListOutVehicle_byCurDate(String date){
		String sql="SELECT ov.idOutVehicle, v.code, ov.date  "
			+ "FROM tblOutVehicle AS ov "
			+ "JOIN tblVehicle AS v ON ov.idVehicle=v.idVehicle "			
			+ "WHERE status=? AND date=?"
			;
		ArrayList<OutVehicle> listOV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listOV = new ArrayList<OutVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, date);
			rs = ps.executeQuery();
			while(rs.next()){
				OutVehicle outV=new OutVehicle();
				outV.setIdOutVehicle(rs.getLong("idOutVehicle"));
				outV.setVehicleCode(rs.getString("code"));
				outV.setDate(rs.getString("date"));
				listOV.add(outV);
			}
			return listOV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public ArrayList<OutVehicle> getPageOutVehicle_whereLoadStatus(int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT ov.idOutVehicle, ov.date, "
			+ "ov.arrivalTime, ov.startLoadTime, ov.finishLoadTime, ov.status, "
			+ "ov.idVehicle, v.code, " 
		    + "ov.idOutDoor, o.nameOutDoor, o.capacity, "
		    + "ov.idCrossDockingSystem, c.nameCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			+ "FROM tblOutVehicle AS ov "
			+ "LEFT JOIN tblOutDoor AS o ON ov.idOutDoor=o.idOutDoor "
			+ "JOIN tblVehicle AS v ON ov.idVehicle=v.idVehicle "			
			+ "JOIN tblCrossDockingSystem AS c ON ov.idCrossDockingSystem=c.idCrossDockingSystem "
             + "WHERE ov.status=? OR ov.status=? "
			 + "ORDER BY idOutVehicle DESC " 						                          
			 + "LIMIT  ? , ?"             
             ;
		ArrayList<OutVehicle> listOV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listOV = new ArrayList<OutVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setInt(2, 2);
			ps.setInt(3, sIndex);
			ps.setInt(4, sizePage);
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
				 
				OutVehicle outV = new OutVehicle();
				outV.setIdVehicle(rs.getLong("idVehicle"));
				outV.setVehicleCode(rs.getString("code"));
				outV.setIdOutVehicle(rs.getLong("idOutVehicle"));
				outV.setDate(rs.getString("date"));
				outV.setArrivalTime(rs.getString("arrivalTime"));
				outV.setStartLoadTime(rs.getString("startLoadTime"));
				outV.setFinishLoadTime(rs.getString("finishLoadTime"));
				outV.setStatus(rs.getInt("status"));
				outV.setDoor(oDoor);
				outV.setcDS(cDS);
				listOV.add(outV);
			}
			return listOV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public ArrayList<OutVehicle> getPageOutVehicle_whereAssignDoor(int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;		
		String sql="SELECT ov.idOutVehicle, ov.date, "
			+ "ov.arrivalTime, ov.startLoadTime, ov.finishLoadTime, ov.status, ov.demand, "
			+ "ov.idVehicle, v.code, " 
		    + "ov.idOutDoor, o.nameOutDoor, o.capacity, "
		    + "ov.idCrossDockingSystem, c.nameCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			+ "FROM tblOutVehicle AS ov "
			+ "LEFT JOIN tblOutDoor AS o ON ov.idOutDoor=o.idOutDoor "
			+ "JOIN tblVehicle AS v ON ov.idVehicle=v.idVehicle "			
			+ "JOIN tblCrossDockingSystem AS c ON ov.idCrossDockingSystem=c.idCrossDockingSystem "
			+ "WHERE (ov.status=? OR ov.status=?) AND date='"+myTool.getDateSystem()+"' "
			+ "ORDER BY idOutVehicle DESC " 						                          
			+ "LIMIT  ? , ?"             
            ;
		ArrayList<OutVehicle> listOV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listOV = new ArrayList<OutVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setInt(2, 1);
			ps.setInt(3, sIndex);
			ps.setInt(4, sizePage);
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
				
				OutVehicle outV = new OutVehicle();
				outV.setIdVehicle(rs.getLong("idVehicle"));
				outV.setVehicleCode(rs.getString("code"));
				outV.setIdOutVehicle(rs.getLong("idOutVehicle"));
				outV.setDate(rs.getString("date"));
				outV.setArrivalTime(rs.getString("arrivalTime"));
				outV.setStartLoadTime(rs.getString("startLoadTime"));
				outV.setFinishLoadTime(rs.getString("finishLoadTime"));
				outV.setStatus(rs.getInt("status"));
//				outV.setDemand(getDemandOutVehicle(rs.getInt("idOutVehicle")));
				outV.setDemand(rs.getDouble("demand"));
				outV.setDoor(oDoor);
				outV.setcDS(cDS);
				listOV.add(outV);
			}
			return listOV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public ArrayList<OutVehicle> getPageOutVehicle_byStatus(int status, int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT ov.idOutVehicle, ov.date, "
			+ "ov.arrivalTime, ov.startLoadTime, ov.finishLoadTime, ov.status, "
			+ "ov.idVehicle, v.code, " 
		    + "ov.idOutDoor, o.nameOutDoor, o.capacity, "
		    + "ov.idCrossDockingSystem, c.nameCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			+ "FROM tblOutVehicle AS ov "
			+ "LEFT JOIN tblOutDoor AS o ON ov.idOutDoor=o.idOutDoor "
			+ "JOIN tblVehicle AS v ON ov.idVehicle=v.idVehicle "			
			+ "JOIN tblCrossDockingSystem AS c ON ov.idCrossDockingSystem=c.idCrossDockingSystem "
			+ "WHERE ov.status = ? AND ov.date=? "
			+ "ORDER BY idOutVehicle DESC "
			+ "LIMIT ?, ?";
		ArrayList<OutVehicle> listOV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listOV = new ArrayList<OutVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setString(2, myTool.getDateSystem());
			ps.setInt(3, sIndex);
			ps.setInt(4, sizePage);
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
				
				OutVehicle outV = new OutVehicle();
				outV.setIdVehicle(rs.getLong("idVehicle"));
				outV.setVehicleCode(rs.getString("code"));
				outV.setIdOutVehicle(rs.getLong("idOutVehicle"));
				outV.setDate(rs.getString("date"));
				outV.setArrivalTime(rs.getString("arrivalTime"));
				outV.setStartLoadTime(rs.getString("startLoadTime"));
				outV.setFinishLoadTime(rs.getString("finishLoadTime"));
				outV.setStatus(rs.getInt("status"));
				outV.setDemand(getDemandOutVehicle(rs.getInt("idOutVehicle")));
				outV.setDoor(oDoor);
				outV.setcDS(cDS);
				listOV.add(outV);
			}
			return listOV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public boolean upStartLoadTime(int idOutVehicle){
		String sql="UPDATE tblOutVehicle SET startLoadTime=? WHERE idOutVehicle=?";
		int i = getJdbcTemplate().update(sql, new Object[]{ myTool.getTimeSystem(), idOutVehicle});
		return (i==1);
	}
	public boolean upFinishLoadTime(int idOutVehicle){
		String sql="UPDATE tblOutVehicle SET finishLoadTime=? WHERE idOutVehicle=?";
		int i = getJdbcTemplate().update(
				sql, new Object[]{ myTool.getTimeSystem(), idOutVehicle});
		return (i==1);
	}
	public boolean upStatusOutVehicle(int idOutVehicle, int status){
		String sql="UPDATE tblOutVehicle SET status=? WHERE idOutVehicle=?";
		int i = getJdbcTemplate().update(sql, new Object[]{ status, idOutVehicle});
		return (i==1);
	}
	public boolean checkInsertOV(OutVehicle ov){
		String sql="SELECT idOutVehicle FROM tblOutVehicle ov "
				+ "JOIN tblVehicle v ON ov.idVehicle=v.idVehicle AND v.idVehicle=? "
				+ "WHERE date=? AND arrivalTime=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, ov.getIdVehicle());
			ps.setString(2, ov.getDate());
			ps.setString(3, ov.getArrivalTime());
			rs = ps.executeQuery();
			if(rs.next()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return true;
	}
	public void insertOutVehicle(OutVehicle oVehicle) {
		String sql="INSERT INTO tblOutVehicle"
				+ "(idOutVehicle, date, arrivalTime, demand, "
				+ "status, idVehicle, idCrossDockingSystem) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql, new Object[]{ 
				getIdSEQ(OutVehicle_.TABLE,  OutVehicle_.ID), 
				oVehicle.getDate(), oVehicle.getArrivalTime(), 
				oVehicle.getDemand(), oVehicle.getStatus(), 
				oVehicle.getIdVehicle(), oVehicle.getcDS().getIdCrossDockingSystem()
		});
	}
	public boolean assignDoorOutVehicle(int idOutVehicle, int idOutDoor){
		String sql="UPDATE tblOutVehicle SET idOutDoor=? WHERE idOutVehicle=?";
		int i = getJdbcTemplate().update(sql, new Object[]{ idOutDoor, idOutVehicle});
		return (i==1);
	}
	public void delOutVehicle_byIdVehicle(int idOutVehicle) {
		String sql="DELETE FROM tblInVehicle WHERE idInVehicle=?";
		getJdbcTemplate().update(sql, new Object[]{ idOutVehicle });
	}
	public int countOutVehicle_whereLoadStatus(){
		String sql="SELECT COUNT(*) FROM tblOutVehicle WHERE status=? OR status=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setInt(2, 2);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public int countOutVehicle_whereAssignDoor(){
		String sql="SELECT COUNT(*) FROM tblOutVehicle "
				+ "WHERE (status=? OR status=?) "
				+ "AND date=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setInt(2, 1);
			ps.setString(3, myTool.getDateSystem());
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public int countOutVehicle_byDateAndCodeVehicle(String strDate, String endDate, String vehicleCode){
		String sql="SELECT COUNT(*) FROM tblOutVehicle ov "
				+ "JOIN tblVehicle v ON ov.idVehicle=v.idVehicle AND code LIKE ? "
				+ "WHERE date BETWEEN ? AND ?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +vehicleCode+ "%");
			ps.setString(2, strDate);
			ps.setString(3, endDate);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public int countOutVehicle_byStatus(int status){
		String sql="SELECT COUNT(*) FROM tblOutVehicle WHERE status=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, status);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public int countOutVehicle() {
		int count = countAll(OutVehicle_.TABLE);
		return count;
	}
	public Double getDemandOutVehicle(int idOutVehicle){
		String sql="SELECT pV.idProductOutVehicle, "
				+ "SUM(pV.quantity) AS volumn, "
				+ "pV.unit, pV.idProduct, pV.idOutVehicle "
				+ "FROM tblProductOutVehicle AS pV "
				+ "WHERE idOutVehicle=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idOutVehicle);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getDouble("volumn");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0d;
	}
	/* ****************************************************************************************************
	 * ****************************************************************************************************
	 * ****************************************************************************************************
	 * 
	 * */
	public void insertTransfer(ProductTransfer pTransfer){
		String sql="INSERT INTO tblProductTransfer(idProductTransfer, "
				+ "idInVehicle, idOutVehicle, transfer) "
				+ "VALUES(?, ?, ?, ?)";
		getJdbcTemplate().update(sql, new Object[]{ 
				getIdProductTransferInsert() + 1, pTransfer.getiVehicle().getIdInVehicle(), 
				pTransfer.getoVehicle().getIdOutVehicle(), pTransfer.getTransfer()
		});
	}
	public ArrayList<ProductTransfer> getProductTransfer(String date){
		String sql="SELECT p.idProductTransfer, p.idInVehicle, "
				+ "p.idOutVehicle, p.transfer, "
				+ "vi.code AS vCodeI, "
				+ "vo.code AS vCodeO "
				+ "FROM tblProductTransfer AS p "
				+ "JOIN tblInVehicle AS iv ON p.idInVehicle = iv.idInVehicle "
					+ "JOIN tblVehicle AS vi ON iv.idVehicle=vi.idVehicle AND iv.date=? "
				+ "JOIN tblOutVehicle AS ov ON p.idOutVehicle = ov.idOutVehicle "
					+ "JOIN tblVehicle AS vo ON ov.idVehicle=vo.idVehicle AND ov.date=?"
//				+ "WHERE p.idInVehicle IN (SELECT idInVehicle FROM tblInVehicle WHERE iv.date=?) "
//					+ "AND o.idOutVehicle IN (SELECT idOutVehicle FROM tblOutVehicle WHERE ov.date=?)"
				;
		ArrayList<ProductTransfer> listPT=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listPT = new ArrayList<ProductTransfer>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			ps.setString(2, date);
			rs = ps.executeQuery();
			while(rs.next()){
				InVehicle iv=new InVehicle(); 
				iv.setIdInVehicle(rs.getLong("idInVehicle")); 
				iv.setVehicleCode(rs.getString("vCodeI"));
				OutVehicle ov=new OutVehicle(); 
				ov.setIdOutVehicle(rs.getLong("idOutVehicle")); 
				ov.setVehicleCode(rs.getString("vCodeO"));
				ProductTransfer pt=new ProductTransfer(
						rs.getInt("idProductTransfer"), 
						iv, ov, rs.getInt("transfer"));
				listPT.add(pt);
			}
			return listPT;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public int getIdProductTransferInsert() {
		String sql="SELECT idProductTransfer "
				+ "FROM tblProductTransfer "
				+ "ORDER BY idProductTransfer DESC";
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
	public int getIdVehicle_byCodeVehicle(String codeVehicle) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int countAllInVehicleCurDate_byKeySearch(String key){
		String sql="SELECT COUNT(*) FROM tblInVehicle AS iv "
				+ "JOIN tblVehicle AS v "
				+ "ON iv.idVehicle=v.idVehicle AND code LIKE ? "
				+ "WHERE date=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +key+ "%");
			ps.setString(2, myTool.getDateSystem());
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public ArrayList<InVehicle> getPageInVehicleCurDate_byKeySearch
			(String key, int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT iv.idInVehicle, iv.date, "
			+ "iv.arrivalTime, iv.startUnloadTime, iv.finishUnloadTime, iv.status, iv.volumn, " 
		    + "iv.idInDoor, i.nameInDoor, i.capacity, "
		    + "iv.idVehicle, v.code, "
		    + "iv.idCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			+ "FROM tblInVehicle AS iv "
			+ "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle AND code LIKE ? "
			+ "LEFT JOIN tblInDoor AS i ON iv.idInDoor=i.idInDoor "
			+ "JOIN tblCrossDockingSystem AS c ON iv.idCrossDockingSystem=c.idCrossDockingSystem "
			+ "WHERE date=? "
			+ "ORDER BY idInVehicle DESC "
			+ "LIMIT ?, ?";
		ArrayList<InVehicle> listIV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listIV = new ArrayList<InVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +key+ "%");
			ps.setString(2, myTool.getDateSystem());
			ps.setInt(3, sIndex);
			ps.setInt(4, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				InDoor iDoor = new InDoor();
				iDoor.setIdDoor(rs.getLong("idInDoor"));
				iDoor.setNameDoor(rs.getString("nameInDoor"));
				iDoor.setCapacity(rs.getInt("capacity"));
				
				InVehicle inV = new InVehicle();
				inV.setIdVehicle(rs.getLong("idVehicle"));
				inV.setVehicleCode(rs.getString("code"));
				inV.setIdInVehicle(rs.getLong("idInVehicle"));
				inV.setDate(rs.getString("date"));
				inV.setArrivalTime(rs.getString("arrivalTime"));
				inV.setStatus(rs.getInt("status"));
				inV.setVolumn(rs.getDouble("volumn"));
				inV.setDoor(iDoor);
				inV.setcDS(cDS);
				inV.setStartUnloadTime(rs.getString("startUnloadTime"));
				inV.setFinishUnloadTime(rs.getString("finishUnloadTime"));
				listIV.add(inV);
			}
			return listIV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	public int countAllOutVehicleCurDate_byKeySearch(String key){
		String sql="SELECT COUNT(*) FROM tblOutVehicle AS ov "
				+ "JOIN tblVehicle AS v "
				+ "ON ov.idVehicle=v.idVehicle AND code LIKE ? "
				+ "WHERE date=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +key+ "%");
			ps.setString(2, myTool.getDateSystem());
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public ArrayList<OutVehicle> getPageOutVehicleCurDate_byKeySearch
			(String key, int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT ov.idOutVehicle, ov.date, "
			+ "ov.arrivalTime, ov.startLoadTime, ov.finishLoadTime, ov.status, ov.demand, " 
		    + "ov.idOutDoor, o.nameOutDoor, o.capacity, "
		    + "ov.idVehicle, v.code, "
		    + "ov.idCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			+ "FROM tblOutVehicle AS ov "
			+ "JOIN tblVehicle AS v ON ov.idVehicle=v.idVehicle AND code LIKE ? "
			+ "LEFT JOIN tblOutDoor AS o ON ov.idOutDoor=o.idOutDoor "
			+ "JOIN tblCrossDockingSystem AS c ON ov.idCrossDockingSystem=c.idCrossDockingSystem "
			+ "WHERE date=? "
			+ "ORDER BY idOutVehicle DESC "
			+ "LIMIT ?, ?";
		ArrayList<OutVehicle> listOV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listOV = new ArrayList<OutVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +key+ "%");
			ps.setString(2, myTool.getDateSystem());
			ps.setInt(3, sIndex);
			ps.setInt(4, sizePage);
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
				
				OutVehicle outV = new OutVehicle();
				outV.setIdVehicle(rs.getLong("idVehicle"));
				outV.setVehicleCode(rs.getString("code"));
				outV.setIdOutVehicle(rs.getLong("idOutVehicle"));
				outV.setDate(rs.getString("date"));
				outV.setArrivalTime(rs.getString("arrivalTime"));
				outV.setStatus(rs.getInt("status"));
				outV.setDemand(rs.getDouble("demand"));
				outV.setDoor(oDoor);
				outV.setcDS(cDS);
				outV.setStartLoadTime(rs.getString("startLoadTime"));
				outV.setFinishLoadTime(rs.getString("finishLoadTime"));
				listOV.add(outV);
			}
			return listOV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
}