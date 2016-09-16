package dao.impl.vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import dao.vehicle.VehicleDAO;
import entity.door.CrossDockingSystem;
import entity.door.InDoor;
import entity.door.OutDoor;
import entity.product.ProductTransfer;
import entity.vehicle.InVehicle;
import entity.vehicle.OutVehicle;
import entity.vehicle.Vehicle;
import tool.MyTool;

@Component
public class VehicleDAOImpl implements VehicleDAO{
	private DataSource dataSource;
	
	@Autowired
	public VehicleDAOImpl(DataSource dataSource){
		this.dataSource = dataSource;
	}
	/* ****************************************************************************************************
	 * ****************************************************************************************************
	 * ****************************************************************************************************
	 * Vehicle
	 * */
	public ArrayList<Vehicle> getListVehicle(){
		String sql="SELECT v.idVehicle, v.vehicleCode, v.vehicleType, "
				+ "v.vehicleYear, v.vehicleMake, v.vehicleWeight, v.vehicleTrailerNum, v.vehicleDes "
				+ "FROM tblVehicle AS v ";
			ArrayList<Vehicle> listV=null;
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				listV = new ArrayList<Vehicle>();
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()){
					Vehicle v = new InVehicle(
							rs.getInt("idVehicle"),
							rs.getString("vehicleCode"), 
							rs.getString("vehicleType"), 
							rs.getInt("vehicleYear"), 
							rs.getString("vehicleMake"), 
							rs.getInt("vehicleWeight"), 
							rs.getInt("vehicleTrailerNum"), 
							rs.getString("vehicleDes"));
					listV.add(v);
				}
				return listV;
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
	public ArrayList<Vehicle> getPageVehicle(int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT v.idVehicle, v.vehicleCode, v.vehicleType, "
				+ "v.vehicleYear, v.vehicleMake, v.vehicleWeight, v.vehicleTrailerNum, v.vehicleDes "
				+ "FROM tblVehicle AS v "
				+ "ORDER BY v.idVehicle DESC "
				+ "LIMIT " +sIndex+ ", " +sizePage+ "";
			ArrayList<Vehicle> listV=null;
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				listV = new ArrayList<Vehicle>();
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()){
					Vehicle v = new InVehicle(
							rs.getInt("idVehicle"),
							rs.getString("vehicleCode"), 
							rs.getString("vehicleType"), 
							rs.getInt("vehicleYear"), 
							rs.getString("vehicleMake"), 
							rs.getInt("vehicleWeight"), 
							rs.getInt("vehicleTrailerNum"), 
							rs.getString("vehicleDes"));
					listV.add(v);
				}
				return listV;
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
	public boolean insertVehicle(Vehicle v){
		String sql="INSERT INTO tblVehicle(idVehicle, vehicleCode, vehicleType, "
				+ "vehicleYear, vehicleMake, vehicleWeight, vehicleTrailerNum, vehicleDes) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		int n = new JdbcTemplate(dataSource).update(sql, new Object[]{ 
				getIdVehicleInsert() + 1, v.getVehicleCode(), v.getVehicleType(), v.getVehicleYear(),
				v.getVehicleMake(), v.getVehicleWeight(), v.getVehicleTrailerNum(), v.getVehicleDes()
		});
		if(n==1) return true;
		return false;
	}
	public int getIdVehicleInsert(){
		String sql="SELECT idVehicle FROM tblVehicle ORDER BY idVehicle DESC";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt("idVehicle");
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
	public int countVehicle() {
		String sql="SELECT COUNT(*) FROM tblVehicle";
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
		    + "iv.idVehicle, v.vehicleCode, v.vehicleType, v.vehicleYear, v.vehicleMake, v.vehicleWeight, v.vehicleTrailerNum, v.vehicleDes, "
		    + "iv.idCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			+ "FROM tblInVehicle AS iv "
			+ "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle "
			+ "LEFT JOIN tblInDoor AS i ON iv.idInDoor=i.idInDoor "
			+ "JOIN tblCrossDockingSystem AS c ON iv.idCrossDockingSystem=c.idCrossDockingSystem "
			+ "ORDER BY idInVehicle DESC "
			+ "LIMIT " +sIndex+ ", " +sizePage+ "";
		ArrayList<InVehicle> listIV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listIV = new ArrayList<InVehicle>();
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				InDoor iDoor = new InDoor(
						rs.getInt("idInDoor"), 
						rs.getString("nameInDoor"), 
						rs.getInt("capacity"));
				InVehicle inV = new InVehicle(
						rs.getInt("idVehicle"),
						rs.getString("vehicleCode"), 
						rs.getString("vehicleType"), 
						rs.getInt("vehicleYear"), 
						rs.getString("vehicleMake"), 
						rs.getInt("vehicleWeight"), 
						rs.getInt("vehicleTrailerNum"), 
						rs.getString("vehicleDes"));
				inV.setIdInVehicle(rs.getInt("idInVehicle"));
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
	public ArrayList<InVehicle> getListInVehicle_byCurDate(String date){
		String sql="SELECT iv.idInVehicle, v.vehicleCode, iv.date "
			+ "FROM tblInVehicle AS iv JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle "
			+ "WHERE status=? AND date=?"
			;
		ArrayList<InVehicle> listIV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listIV = new ArrayList<InVehicle>();
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, date);
			rs = ps.executeQuery();
			while(rs.next()){
				InVehicle inV=new InVehicle();
				inV.setIdInVehicle(rs.getInt("idInVehicle"));
				inV.setVehicleCode(rs.getString("vehicleCode"));
				inV.setDate(rs.getString("date"));
				listIV.add(inV);
			}
			return listIV;
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
	public ArrayList<InVehicle> getPageInVehicle_whereAssignDoor(int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;		
		String sql="SELECT iv.idInVehicle, iv.date, "
			 + "iv.arrivalTime, iv.startUnloadTime, iv.finishUnloadTime, iv.status, iv.volumn, " 
		     + "iv.idInDoor, i.nameInDoor, i.capacity, "
		     + "iv.idVehicle, v.vehicleCode, v.vehicleType, v.vehicleYear, v.vehicleMake, v.vehicleWeight, v.vehicleTrailerNum, v.vehicleDes, "
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
			conn = dataSource.getConnection();
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
				InDoor iDoor = new InDoor(
						rs.getInt("idInDoor"), 
						rs.getString("nameInDoor"), 
						rs.getInt("capacity")); 
				InVehicle inV = new InVehicle(
						rs.getInt("idVehicle"),
						rs.getString("vehicleCode"), 
						rs.getString("vehicleType"), 
						rs.getInt("vehicleYear"), 
						rs.getString("vehicleMake"), 
						rs.getInt("vehicleWeight"), 
						rs.getInt("vehicleTrailerNum"), 
						rs.getString("vehicleDes"));
				inV.setIdInVehicle(rs.getInt("idInVehicle"));
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
	public ArrayList<InVehicle> getPageInVehicle_byStatus(int status, int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT iv.idInVehicle, iv.date, "
			 + "iv.arrivalTime, iv.startUnloadTime, iv.finishUnloadTime, iv.status, " 
		     + "iv.idInDoor, i.nameInDoor, i.capacity, "
		     + "iv.idVehicle, v.vehicleCode, v.vehicleType, v.vehicleYear, v.vehicleMake, v.vehicleWeight, v.vehicleTrailerNum, v.vehicleDes, "
		     + "iv.idCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			 + "FROM tblInVehicle AS iv "
			 + "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle "
		  	 + "LEFT JOIN tblInDoor AS i ON iv.idInDoor=i.idInDoor "
			 + "JOIN tblCrossDockingSystem AS c ON iv.idCrossDockingSystem=c.idCrossDockingSystem " 
             + "WHERE iv.status=? "
			 + "ORDER BY idInVehicle DESC " 						                          
			 + "LIMIT  ? , ?"             
             ;
		ArrayList<InVehicle> listIV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listIV = new ArrayList<InVehicle>();
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, sIndex);
			ps.setInt(3, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				InDoor iDoor = new InDoor(
						rs.getInt("idInDoor"), 
						rs.getString("nameInDoor"), 
						rs.getInt("capacity")); 
				InVehicle inV = new InVehicle(
						rs.getInt("idVehicle"),
						rs.getString("vehicleCode"), 
						rs.getString("vehicleType"), 
						rs.getInt("vehicleYear"), 
						rs.getString("vehicleMake"), 
						rs.getInt("vehicleWeight"), 
						rs.getInt("vehicleTrailerNum"), 
						rs.getString("vehicleDes"));
				inV.setIdInVehicle(rs.getInt("idInVehicle"));
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
	public ArrayList<InVehicle> getPageInVehicle_whereUnloadStatus(int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT iv.idInVehicle, iv.date, "
			 + "iv.arrivalTime, iv.startUnloadTime, iv.finishUnloadTime, iv.status, " 
		     + "iv.idInDoor, i.nameInDoor, i.capacity, "
		     + "iv.idVehicle, v.vehicleCode, v.vehicleType, v.vehicleYear, v.vehicleMake, v.vehicleWeight, v.vehicleTrailerNum, v.vehicleDes, "
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
			conn = dataSource.getConnection();
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
				InDoor iDoor = new InDoor(
						rs.getInt("idInDoor"), 
						rs.getString("nameInDoor"), 
						rs.getInt("capacity")); 
				InVehicle inV = new InVehicle(
						rs.getInt("idVehicle"),
						rs.getString("vehicleCode"), 
						rs.getString("vehicleType"), 
						rs.getInt("vehicleYear"), 
						rs.getString("vehicleMake"), 
						rs.getInt("vehicleWeight"), 
						rs.getInt("vehicleTrailerNum"), 
						rs.getString("vehicleDes"));
				inV.setIdInVehicle(rs.getInt("idInVehicle"));
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
	public int getIdVehicle_byVehicleCode(String vehicleCode){
		String sql="SELECT idVehicle FROM tblVehicle WHERE vehicleCode=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt("idVehicle");
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
	public void insertInVehicle(InVehicle iv) {
		String sql="INSERT INTO tblInVehicle"
				+ "(idInVehicle, date, arrivalTime, volumn, status, idVehicle, idCrossDockingSystem) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
		new JdbcTemplate(dataSource).update(sql, new Object[]{ 
				getIdInVehicleInsert() + 1, iv.getDate(), 
				iv.getArrivalTime(), iv.getVolumn(), iv.getStatus(), iv.getIdVehicle(), 
				iv.getcDS().getIdCrossDockingSystem()
		});
	}
	
	public boolean assignDoorInVehicle(int idInVehicle, int idInDoor){
		String sql="UPDATE tblInVehicle SET idInDoor=? WHERE idInVehicle=?";
		int i = new JdbcTemplate(dataSource).update(sql, new Object[]{ idInDoor, idInVehicle});
		return (i==1);
	}
	public boolean upStartUnloadTime(int idInVehicle){
		String sql="UPDATE tblInVehicle SET startUnloadTime=? WHERE idInVehicle=?";
		int i = new JdbcTemplate(dataSource).update(sql, new Object[]{ new MyTool().getTimeSystem(), idInVehicle});
		return (i==1);
	}
	public boolean upFinishUnloadTime(int idInVehicle){
		String sql="UPDATE tblInVehicle SET finishUnloadTime=? WHERE idInVehicle=?";
		int i = new JdbcTemplate(dataSource).update(sql, new Object[]{ new MyTool().getTimeSystem(), idInVehicle});
		return (i==1);
	}
	public boolean upStatusInVehicle(int idInVehicle, int status){
		String sql="UPDATE tblInVehicle SET status=? WHERE idInVehicle=?";
		int i = new JdbcTemplate(dataSource).update(sql, new Object[]{ status, idInVehicle});
		return (i==1);
	}
	public void delInVehicle_byIdVehicle(int idInVehicle) {
		String sql="DELETE FROM tblInVehicle WHERE idInVehicle=?";
		new JdbcTemplate(dataSource).update(sql, new Object[]{ idInVehicle });
	}
	public int countInVehicle_whereUnloadStatus(){
		String sql="SELECT COUNT(*) FROM tblInVehicle WHERE status=? OR status=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setInt(2, 2);
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
	public int countInVehicle_byStatus(int status){
		String sql="SELECT COUNT(*) FROM tblInVehicle WHERE status=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, status);
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
	public int countInVehicle_whereAssignDoor(){
		String sql="SELECT COUNT(*) FROM tblInVehicle WHERE status=? OR status=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setInt(2, 1);
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
	public int countInVehicle() {
		String sql="SELECT COUNT(*) FROM tblInVehicle";
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
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idInVehicle);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getDouble("volumn");
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
		return 0d;
	}
	public int getIdInVehicleInsert() {
		String sql="SELECT idInVehicle FROM tblInVehicle ORDER BY idInVehicle DESC";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt("idInVehicle");
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
			+ "ov.idVehicle, v.vehicleCode, v.vehicleType, v.vehicleYear, v.vehicleMake, v.vehicleWeight, v.vehicleTrailerNum, v.vehicleDes, " 
		    + "ov.idOutDoor, o.nameOutDoor, o.capacity, "
		    + "ov.idCrossDockingSystem, c.nameCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			+ "FROM tblOutVehicle AS ov "
			+ "LEFT JOIN tblOutDoor AS o ON ov.idOutDoor=o.idOutDoor "
			+ "JOIN tblVehicle AS v ON ov.idVehicle=v.idVehicle "			
			+ "JOIN tblCrossDockingSystem AS c ON ov.idCrossDockingSystem=c.idCrossDockingSystem "
			+ "ORDER BY idOutVehicle DESC "
			+ "LIMIT " +sIndex+ ", " +sizePage+ "";
		ArrayList<OutVehicle> listOV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listOV = new ArrayList<OutVehicle>();
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				OutDoor oDoor = new OutDoor(
						rs.getInt("idOutDoor"), 
						rs.getString("nameOutDoor"), 
						rs.getInt("capacity")); 
				OutVehicle outV = new OutVehicle(
						rs.getInt("idVehicle"),
						rs.getString("vehicleCode"), 
						rs.getString("vehicleType"), 
						rs.getInt("vehicleYear"), 
						rs.getString("vehicleMake"), 
						rs.getInt("vehicleWeight"), 
						rs.getInt("vehicleTrailerNum"), 
						rs.getString("vehicleDes"));
				outV.setIdOutVehicle(rs.getInt("idOutVehicle"));
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
	public ArrayList<OutVehicle> getListOutVehicle_byCurDate(String date){
		String sql="SELECT ov.idOutVehicle, v.vehicleCode, ov.date  "
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
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, date);
			rs = ps.executeQuery();
			while(rs.next()){
				OutVehicle outV=new OutVehicle();
				outV.setIdOutVehicle(rs.getInt("idOutVehicle"));
				outV.setVehicleCode(rs.getString("vehicleCode"));
				outV.setDate(rs.getString("date"));
				listOV.add(outV);
			}
			return listOV;
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
	public ArrayList<OutVehicle> getPageOutVehicle_whereLoadStatus(int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT ov.idOutVehicle, ov.date, "
			+ "ov.arrivalTime, ov.startLoadTime, ov.finishLoadTime, ov.status, "
			+ "ov.idVehicle, v.vehicleCode, v.vehicleType, v.vehicleYear, v.vehicleMake, v.vehicleWeight, v.vehicleTrailerNum, v.vehicleDes, " 
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
			conn = dataSource.getConnection();
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
				OutDoor oDoor = new OutDoor(
						rs.getInt("idOutDoor"), 
						rs.getString("nameOutDoor"), 
						rs.getInt("capacity")); 
				OutVehicle outV = new OutVehicle(
						rs.getInt("idVehicle"),
						rs.getString("vehicleCode"), 
						rs.getString("vehicleType"), 
						rs.getInt("vehicleYear"), 
						rs.getString("vehicleMake"), 
						rs.getInt("vehicleWeight"), 
						rs.getInt("vehicleTrailerNum"), 
						rs.getString("vehicleDes"));
				outV.setIdOutVehicle(rs.getInt("idOutVehicle"));
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
	public ArrayList<OutVehicle> getPageOutVehicle_whereAssignDoor(int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;		
		String sql="SELECT ov.idOutVehicle, ov.date, "
			+ "ov.arrivalTime, ov.startLoadTime, ov.finishLoadTime, ov.status, ov.demand, "
			+ "ov.idVehicle, v.vehicleCode, v.vehicleType, v.vehicleYear, v.vehicleMake, v.vehicleWeight, v.vehicleTrailerNum, v.vehicleDes, " 
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
			conn = dataSource.getConnection();
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
				OutDoor oDoor = new OutDoor(
						rs.getInt("idOutDoor"), 
						rs.getString("nameOutDoor"), 
						rs.getInt("capacity")); 
				OutVehicle outV = new OutVehicle(
						rs.getInt("idVehicle"),
						rs.getString("vehicleCode"), 
						rs.getString("vehicleType"), 
						rs.getInt("vehicleYear"), 
						rs.getString("vehicleMake"), 
						rs.getInt("vehicleWeight"), 
						rs.getInt("vehicleTrailerNum"), 
						rs.getString("vehicleDes"));
				outV.setIdOutVehicle(rs.getInt("idOutVehicle"));
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
	public ArrayList<OutVehicle> getPageOutVehicle_byStatus(int status, int currentPage, int sizePage){
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT ov.idOutVehicle, ov.date, "
			+ "ov.arrivalTime, ov.startLoadTime, ov.finishLoadTime, ov.status, "
			+ "ov.idVehicle, v.vehicleCode, v.vehicleType, v.vehicleYear, v.vehicleMake, v.vehicleWeight, v.vehicleTrailerNum, v.vehicleDes, " 
		    + "ov.idOutDoor, o.nameOutDoor, o.capacity, "
		    + "ov.idCrossDockingSystem, c.nameCrossDockingSystem, c.nameCrossDockingSystem, c.address, c.capacity "
			+ "FROM tblOutVehicle AS ov "
			+ "LEFT JOIN tblOutDoor AS o ON ov.idOutDoor=o.idOutDoor "
			+ "JOIN tblVehicle AS v ON ov.idVehicle=v.idVehicle "			
			+ "JOIN tblCrossDockingSystem AS c ON ov.idCrossDockingSystem=c.idCrossDockingSystem "
			+ "WHERE ov.status = ? "
			+ "ORDER BY idOutVehicle DESC "
			+ "LIMIT ?, ?";
		ArrayList<OutVehicle> listOV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listOV = new ArrayList<OutVehicle>();
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, sIndex);
			ps.setInt(3, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				CrossDockingSystem cDS=new CrossDockingSystem(
						rs.getInt("idCrossDockingSystem"), 
						rs.getString("nameCrossDockingSystem"), 
						rs.getString("address"), rs.getInt("capacity"));
				OutDoor oDoor = new OutDoor(
						rs.getInt("idOutDoor"), 
						rs.getString("nameOutDoor"), 
						rs.getInt("capacity")); 
				OutVehicle outV = new OutVehicle(
						rs.getInt("idVehicle"),
						rs.getString("vehicleCode"), 
						rs.getString("vehicleType"), 
						rs.getInt("vehicleYear"), 
						rs.getString("vehicleMake"), 
						rs.getInt("vehicleWeight"), 
						rs.getInt("vehicleTrailerNum"), 
						rs.getString("vehicleDes"));
				outV.setIdOutVehicle(rs.getInt("idOutVehicle"));
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
	public boolean upStartLoadTime(int idOutVehicle){
		String sql="UPDATE tblOutVehicle SET startLoadTime=? WHERE idOutVehicle=?";
		int i = new JdbcTemplate(dataSource).update(sql, new Object[]{ new MyTool().getTimeSystem(), idOutVehicle});
		return (i==1);
	}
	public boolean upFinishLoadTime(int idOutVehicle){
		String sql="UPDATE tblOutVehicle SET finishLoadTime=? WHERE idOutVehicle=?";
		int i = new JdbcTemplate(dataSource).update(
				sql, new Object[]{ new MyTool().getTimeSystem(), idOutVehicle});
		return (i==1);
	}
	public boolean upStatusOutVehicle(int idOutVehicle, int status){
		String sql="UPDATE tblOutVehicle SET status=? WHERE idOutVehicle=?";
		int i = new JdbcTemplate(dataSource).update(sql, new Object[]{ status, idOutVehicle});
		return (i==1);
	}
	public void insertOutVehicle(OutVehicle oVehicle) {
		String sql="INSERT INTO tblOutVehicle(idOutVehicle, idVehicle, "
				+ "date, arrivalTime, demand, status, idCrossDockingSystem) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
		new JdbcTemplate(dataSource).update(sql, new Object[]{ 
				getIdOutVehicleInsert() + 1, oVehicle.getIdVehicle(), 
				oVehicle.getDate(), oVehicle.getArrivalTime(), oVehicle.getDemand(), 
				oVehicle.getStatus(), oVehicle.getcDS().getIdCrossDockingSystem()
		});
	}
	public boolean assignDoorOutVehicle(int idOutVehicle, int idOutDoor){
		String sql="UPDATE tblOutVehicle SET idOutDoor=? WHERE idOutVehicle=?";
		int i = new JdbcTemplate(dataSource).update(sql, new Object[]{ idOutDoor, idOutVehicle});
		return (i==1);
	}
	public void delOutVehicle_byIdVehicle(int idOutVehicle) {
		String sql="DELETE FROM tblInVehicle WHERE idInVehicle=?";
		new JdbcTemplate(dataSource).update(sql, new Object[]{ idOutVehicle });
	}
	public int countOutVehicle_whereLoadStatus(){
		String sql="SELECT COUNT(*) FROM tblOutVehicle WHERE status=? OR status=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setInt(2, 2);
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
	public int countOutVehicle_whereAssignDoor(){
		String sql="SELECT COUNT(*) FROM tblOutVehicle WHERE status=? OR status=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setInt(2, 1);
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
	public int countOutVehicle_byStatus(int status){
		String sql="SELECT COUNT(*) FROM tblOutVehicle WHERE status=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, status);
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
	public int countOutVehicle() {
		String sql="SELECT COUNT(*) FROM tblOutVehicle";
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
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idOutVehicle);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getDouble("volumn");
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
		return 0d;
	}
	public int getIdOutVehicleInsert() {
		String sql="SELECT idOutVehicle FROM tblOutVehicle "
				+ "ORDER BY idOutVehicle DESC";
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
	/* ****************************************************************************************************
	 * ****************************************************************************************************
	 * ****************************************************************************************************
	 * 
	 * */
	public void insertTransfer(ProductTransfer pTransfer){
		String sql="INSERT INTO tblProductTransfer(idProductTransfer, "
				+ "idInVehicle, idOutVehicle, transfer) "
				+ "VALUES(?, ?, ?, ?)";
		new JdbcTemplate(dataSource).update(sql, new Object[]{ 
				getIdProductTransferInsert() + 1, pTransfer.getiVehicle().getIdInVehicle(), 
				pTransfer.getoVehicle().getIdOutVehicle(), pTransfer.getTransfer()
		});
	}
	public ArrayList<ProductTransfer> getProductTransfer(String date){
		String sql="SELECT p.idProductTransfer, p.idInVehicle, "
				+ "p.idOutVehicle, p.transfer, "
				+ "vi.vehicleCode AS vCodeI, "
				+ "vo.vehicleCode AS vCodeO "
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
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, date);
			ps.setString(2, date);
			rs = ps.executeQuery();
			while(rs.next()){
				InVehicle iVehicle=new InVehicle(); 
				iVehicle.setIdInVehicle(rs.getInt("idInVehicle")); iVehicle.setVehicleCode(rs.getString("vCodeI"));
				OutVehicle oVehicle=new OutVehicle(); 
				oVehicle.setIdOutVehicle(rs.getInt("idOutVehicle")); oVehicle.setVehicleCode(rs.getString("vCodeO"));
				ProductTransfer pt=new ProductTransfer(
						rs.getInt("idProductTransfer"), 
						iVehicle, oVehicle, rs.getInt("transfer"));
				listPT.add(pt);
			}
			return listPT;
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
	public int getIdProductTransferInsert() {
		String sql="SELECT idProductTransfer "
				+ "FROM tblProductTransfer "
				+ "ORDER BY idProductTransfer DESC";
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
	public int getIdVehicle_byCodeVehicle(String codeVehicle) {
		// TODO Auto-generated method stub
		return 0;
	}
}
