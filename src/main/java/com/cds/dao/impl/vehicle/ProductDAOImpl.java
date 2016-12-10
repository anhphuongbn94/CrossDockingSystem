package com.cds.dao.impl.vehicle;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cds.dao.impl.BaseDAOImpl;
import com.cds.dao.vehicle.ProductDAO;
import com.cds.entity.product.Product;
import com.cds.entity.product.ProductInVehicle;
import com.cds.entity.product.ProductOutVehicle;
import com.cds.entity.product.ProductTransfer;
import com.cds.entity.product.ProductTransform;
import com.cds.entity.product.Unit;
import com.cds.entity.vehicle.InVehicle;
import com.cds.entity.vehicle.OutVehicle;
import com.cds.field.Constants;
import com.cds.field.product.ProductInVehicle_;
import com.cds.field.product.ProductOutVehicle_;
import com.cds.field.product.ProductTransfer_;
import com.cds.field.product.ProductTransform_;
import com.cds.utils.DateUtils;

@Component
public class ProductDAOImpl extends BaseDAOImpl implements ProductDAO{
	private DateUtils myTool=new DateUtils();
	
	@Autowired
	public ProductDAOImpl(DataSource dataSource) {
		super(dataSource);
	}

	public ArrayList<Product> getListProduct() {
		String sql="SELECT p.id, p.name, p.desc FROM tblProduct AS p";
		ArrayList<Product> listP=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listP=new ArrayList<Product>();
			conn=getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				Product p=new Product();
				p.setId(rs.getLong("id"));
				p.setName(rs.getString("name"));
				p.setDesc(rs.getString("desc"));
				listP.add(p);
			}
			return listP;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}

	public ArrayList<ProductInVehicle> getListProductIV_byVehicle(Long idInVehicle){
		String sql="SELECT piv.id AS pivId, piv.quantity, piv.idUnit, piv.idProduct, "
				+ "piv.idInVehicle, v.code, v.company, "
				+ "p.id AS pId, p.name AS pName, "
				+ "u.id AS uId, u.name AS uName "
				+ "FROM tblProductInVehicle AS piv "
				+ "JOIN tblInVehicle AS iv ON piv.idInVehicle=iv.idInVehicle "
					+ "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle "
				+ "JOIN tblProduct AS p ON piv.idProduct=p.id "
				+ "JOIN tblUnit AS u ON piv.idUnit=u.id "
				+ "WHERE piv.idInVehicle=?";
		ArrayList<ProductInVehicle> listPIV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listPIV = new ArrayList<ProductInVehicle>();
			conn=getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idInVehicle);
			rs = ps.executeQuery();
			while(rs.next()){
				ProductInVehicle piv=new ProductInVehicle();
				InVehicle iv=new InVehicle();
				iv.setIdInVehicle(rs.getLong("idInVehicle"));
				iv.setVehicleCode(rs.getString("code"));
				iv.setCompany(rs.getString("company"));
				Product p=new Product();
				p.setId(rs.getLong("pId"));
				p.setName(rs.getString("pName"));
				Unit u=new Unit();
				u.setId(rs.getLong("uId"));
				u.setName(rs.getString("uName"));
				
				piv.setId(rs.getLong("pivId"));
				piv.setIv(iv);
				piv.setP(p);
				piv.setU(u);
				piv.setQuantity(rs.getInt("quantity"));
				listPIV.add(piv);
			}
			return listPIV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	
	public ProductInVehicle checkInsertProductIV(Long idProduct, Long idInVehicle){
		String sql="SELECT id, quantity FROM tblProductInVehicle WHERE idProduct=? AND idInVehicle=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn=getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idProduct);
			ps.setLong(2, idInVehicle);
			rs = ps.executeQuery();
			if(rs.next()) {
				ProductInVehicle piv=new ProductInVehicle();
				piv.setId(rs.getLong("id"));
				piv.setQuantity(rs.getInt("quantity"));
				return piv;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	
	public boolean insertProductIV(ProductInVehicle piv) {
		String sql="INSERT INTO tblProductInVehicle(id, idInVehicle, idProduct, quantity, idUnit) "
				+ "VALUES(?,?,?,?,?)";
		int n=getJdbcTemplate().update(sql, new Object[] {
			getIdSEQ(ProductInVehicle_.TABLE, ProductInVehicle_.ID), piv.getIv().getIdInVehicle(), 
				piv.getP().getId(), piv.getQuantity(), piv.getU().getId()
		});
		if(n > 0) return true;
		return false;
	}
	
	public int getQuantityTransform_byInVehicle(Long idInVehicle){
		String sql="SELECT SUM(quantity) AS uQuantity FROM tblProductTransform WHERE idInVehicle=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idInVehicle);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt("uQuantity");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean editProductIV(Long idPIV, int quantity){
		String sql="UPDATE tblProductInVehicle SET quantity=? WHERE id=?";
		int n = getJdbcTemplate().update(sql, new Object[] {quantity, idPIV });
		if(n > 0) return true;
		return false;
	}
	
	public boolean checkDeleteProductIV(Long idInVehicle){
		String sql="SELECT id FROM tblProductTransform WHERE idInVehicle=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idInVehicle);
			rs = ps.executeQuery();
			if(rs.next()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean delProductIV(Long idPIV){
		String sql="DELETE FROM tblProductInVehicle WHERE id=?";
		int n = getJdbcTemplate().update(sql, new Object[] {idPIV });
		if(n > 0) return true;
		return false;
	}


	public ArrayList<ProductOutVehicle> getListProductOV_byVehicle(Long idOutVehicle) {
		String sql="SELECT pov.id AS povId, pov.quantity, pov.idUnit, pov.idProduct, "
				+ "pov.idOutVehicle, v.code, v.company, "
				+ "p.id AS pId, p.name AS pName, "
				+ "u.id AS uId, u.name AS uName "
				+ "FROM tblProductOutVehicle AS pov "
				+ "JOIN tblOutVehicle AS iv ON pov.idOutVehicle=iv.idOutVehicle "
					+ "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle "
				+ "JOIN tblProduct AS p ON pov.idProduct=p.id "
				+ "JOIN tblUnit AS u ON pov.idUnit=u.id "
				+ "WHERE pov.idOutVehicle=?";
		ArrayList<ProductOutVehicle> listPOV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listPOV = new ArrayList<ProductOutVehicle>();
			conn=getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idOutVehicle);
			rs = ps.executeQuery();
			while(rs.next()){
				ProductOutVehicle pov=new ProductOutVehicle();
				OutVehicle ov=new OutVehicle();
				ov.setIdOutVehicle(rs.getLong("idOutVehicle"));
				ov.setVehicleCode(rs.getString("code"));
				ov.setCompany(rs.getString("company"));
				Product p=new Product();
				p.setId(rs.getLong("pId"));
				p.setName(rs.getString("pName"));
				Unit u=new Unit();
				u.setId(rs.getLong("uId"));
				u.setName(rs.getString("uName"));
				
				pov.setId(rs.getLong("povId"));
				pov.setOv(ov);
				pov.setP(p);
				pov.setU(u);
				pov.setQuantity(rs.getInt("quantity"));
				listPOV.add(pov);
			}
			return listPOV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	
	public ProductOutVehicle checkInsertProductOV(Long idOutVehicle, Long idProduct){
		String sql="SELECT id, quantity FROM tblProductOutVehicle WHERE idProduct=? AND idOutVehicle=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn=getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idProduct);
			ps.setLong(2, idOutVehicle);
			rs = ps.executeQuery();
			if(rs.next()) {
				ProductOutVehicle pov=new ProductOutVehicle();
				pov.setQuantity(rs.getInt("quantity"));
				return pov;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	
	public ProductTransform checkInsertProductTransform(Long idInVehicle, Long idOutVehicle, Long idProduct){
		String sql="SELECT id, quantity FROM tblProductTransform WHERE idProduct=? AND idOutVehicle=? AND idInVehicle=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn=getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idProduct);
			ps.setLong(2, idOutVehicle);
			ps.setLong(3, idInVehicle);
			rs = ps.executeQuery();
			if(rs.next()) {
				ProductOutVehicle pov=new ProductOutVehicle();
				pov.setQuantity(rs.getInt("quantity"));
				ProductTransform pt=new ProductTransform();
				pt.setId(rs.getLong("id"));
				pt.setPov(pov);
				return pt;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}
	
	public void insertProductOV(PreparedStatement ps, Long idOutVehicle, Long idProduct, int quantity, Long idUnit){
		try {
			ps.setLong(1, getIdSEQ(ProductOutVehicle_.TABLE, ProductInVehicle_.ID));
			ps.setLong(2, idOutVehicle);
			ps.setLong(3, idProduct);
			ps.setInt(4, quantity);
			ps.setLong(5, idUnit);
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertProductTransform(PreparedStatement ps, Long idInVehicle, Long idOutVehicle, Long idProduct, int quantity, Long idUnit){
		try {
			ps.setLong(1, getIdSEQ(ProductTransform_.TABLE, ProductTransform_.ID));
			ps.setLong(2, idInVehicle);
			ps.setLong(3, idOutVehicle);
			ps.setLong(4, idProduct);
			ps.setInt(5, quantity);
			ps.setLong(6, idUnit);
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertProducTransfer(PreparedStatement ps, Long idInVehicle, Long idOutVehicle, int transfer){
		
		try {
			ps.setLong(1, getIdSEQ(ProductTransfer_.TABLE, ProductTransfer_.ID));
			ps.setLong(2, idInVehicle);
			ps.setLong(3, idOutVehicle);
			ps.setInt(4, transfer);
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean insertProductTransform(ProductTransform pt) {
		String sql1="INSERT INTO tblProductOutVehicle(id, idOutVehicle, idProduct, quantity, idUnit) VALUES(?,?,?,?,?)";
		String sql2="INSERT INTO tblProductTransform(id, idInVehicle, idOutVehicle, idProduct, quantity, idUnit) VALUES(?,?,?,?,?,?)";
		String sql3="INSERT INTO tblProductTransfer(id, idInVehicle, idOutVehicle, transfer) VALUES(?, ?, ?, ?)";
		String sql4="UPDATE tblProductOutVehicle SET quantity=? WHERE idProduct=? AND idOutVehicle=?";
		String sql5="UPDATE tblProductTransform SET quantity=? WHERE idProduct=? AND idInVehicle=? AND idOutVehicle=?";
		String sql6="UPDATE tblProductTransfer SET transfer=? WHERE idInVehicle=? AND idOutVehicle=?";
		Connection conn=null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		PreparedStatement ps2=null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			Long idInVehicle = pt.getPiv().getIv().getIdInVehicle();
			Long idOutVehicle = pt.getPov().getOv().getIdOutVehicle();
			Long idProduct = pt.getPov().getP().getId();
			int quantity = pt.getPov().getQuantity();
			Long idUnit = pt.getPov().getU().getId();
			int tfRule = getTrasnferNumberRule_byProduct(idProduct);
			int transfer = getTransferNumber(quantity, tfRule);
			
			ProductOutVehicle pov=checkInsertProductOV(idOutVehicle, idProduct);
			ProductTransform pt1=checkInsertProductTransform(idInVehicle, idOutVehicle, idProduct);
			
			if(pov == null && pt1 == null){
				ps = conn.prepareStatement(sql1);
				ps1 = conn.prepareStatement(sql2);
				ps2 = conn.prepareStatement(sql3);
				
				insertProductOV(ps, idOutVehicle, idProduct, quantity, idUnit);
				insertProductTransform(ps1, idInVehicle, idOutVehicle, idProduct, quantity, idUnit);
				insertProducTransfer(ps2, idInVehicle, idOutVehicle, transfer);
			}else if(pov != null && pt1 == null){
				ps = conn.prepareStatement(sql4);
				ps1 = conn.prepareStatement(sql2);
				ps2 = conn.prepareStatement(sql3);
				
				int qNew = quantity + pov.getQuantity();
				transfer = getTransferNumber(qNew, tfRule);
				editProductOV(ps, idOutVehicle, idProduct, qNew);
				insertProductTransform(ps1, idInVehicle, idOutVehicle, idProduct, quantity, idUnit);
				insertProducTransfer(ps2, idInVehicle, idOutVehicle, transfer);
			}else if(pov != null && pt1 != null){
				ps = conn.prepareStatement(sql4);
				ps1 = conn.prepareStatement(sql5);
				ps2 = conn.prepareStatement(sql6);
				
				int qNew = quantity + pov.getQuantity();
				transfer = getTransferNumber(qNew, tfRule);
				editProductOV(ps, idOutVehicle, idProduct, qNew);
				editProductTransform(ps1, idInVehicle, idOutVehicle, idProduct, qNew);
				editProductTransfer(ps2, idInVehicle, idOutVehicle, transfer);
			}
			if(ps.executeBatch().length + ps1.executeBatch().length + ps2.executeBatch().length == 3){
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, null);
			closeConnection(null, ps1, null);
			closeConnection(null, ps2, null);
		}
		return false;
	}
	public boolean editProductOV(Long idProduct, Long idInVehicle, Long idOutVehicle, int quantity) {
		String sql="UPDATE tblProductOutVehicle SET quantity=? WHERE idProduct=? AND idOutVehicle=?";
		String sql1="UPDATE tblProductTransform SET quantity=? WHERE idProduct=? AND idInVehicle=? AND idOutVehicle=?";
		String sql2="UPDATE tblProductTransfer SET transfer=? WHERE idInVehicle=? AND idOutVehicle=?";
		Connection conn=null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		PreparedStatement ps2=null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps1 = conn.prepareStatement(sql1);
			ps2 = conn.prepareStatement(sql2);
			
			int tfRule = getTrasnferNumberRule_byProduct(idProduct);
			int transfer = getTransferNumber(quantity, tfRule);
			
			editProductTransform(ps1, idInVehicle, idOutVehicle, idProduct, quantity);
			editProductTransfer(ps2, idInVehicle, idOutVehicle, transfer);
			int qNew = getTotalQuantity_byProductAndOutVehicle(idInVehicle, idOutVehicle, idProduct) + quantity;
			System.out.println("QNEW=" + qNew);
			editProductOV(ps, idOutVehicle, idProduct, qNew);
			
			if(ps.executeBatch().length + ps1.executeBatch().length + ps2.executeBatch().length == 3){
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, null);
			closeConnection(null, ps1, null);
			closeConnection(null, ps2, null);
		}
		return false;
	}
	public int getTotalQuantity_byProductAndOutVehicle(Long idInVehicle, Long idOutVehicle, Long idProduct){
		String sql="SELECT SUM(quantity) AS totalQuantity FROM tblProductTransform WHERE idOutVehicle=? AND idProduct=? AND idInVehicle <> ?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idOutVehicle);
			ps.setLong(2, idProduct);
			ps.setLong(3, idInVehicle);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt("totalQuantity");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public void editProductOV(PreparedStatement ps, Long idOutVehicle, Long idProduct, int quantity){
		try {
			ps.setInt(1, quantity);
			ps.setLong(2, idProduct);
			ps.setLong(3, idOutVehicle);
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void editProductTransform(PreparedStatement ps, Long idInVehicle, Long idOutVehicle, Long idProduct, int quantity){
		try {
			ps.setInt(1, quantity);
			ps.setLong(2, idProduct);
			ps.setLong(3, idInVehicle);
			ps.setLong(4, idOutVehicle);
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void editProductTransfer(PreparedStatement ps, Long idInVehicle, Long idOutVehicle, int transfer){
		try {
			ps.setInt(1, transfer);
			ps.setLong(2, idInVehicle);
			ps.setLong(3, idOutVehicle);
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delProductOV(PreparedStatement ps, Long idOutVehicle, Long idProduct){
		try {
			ps.setLong(1, idProduct);
			ps.setLong(2, idOutVehicle);
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delProductTransform(PreparedStatement ps, Long idInVehicle, Long idOutVehicle, Long idProduct){
		try {
			ps.setLong(1, idProduct);
			ps.setLong(2, idInVehicle);
			ps.setLong(3, idOutVehicle);
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delProductTransfer(PreparedStatement ps, Long idInVehicle, Long idOutVehicle){
		try {
			ps.setLong(1, idInVehicle);
			ps.setLong(2, idOutVehicle);
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean delProductOV(Long idProduct, Long idInVehicle, Long idOutVehicle) {
		String sql="DELETE FROM tblProductOutVehicle WHERE idProduct=? AND idOutVehicle=?";
		String sql1="DELETE FROM tblProductTransform WHERE idProduct=? AND idInVehicle=? AND idOutVehicle=?";
		String sql2="DELETE FROM tblProductTransfer WHERE idInVehicle=? AND idOutVehicle=?";
		String sql3="UPDATE tblProductOutVehicle SET quantity=? WHERE idProduct=? AND idOutVehicle=?";
		Connection conn=null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		PreparedStatement ps2=null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			ps1 = conn.prepareStatement(sql1);
			ps2 = conn.prepareStatement(sql2);
			
			delProductTransform(ps1, idInVehicle, idOutVehicle, idProduct);
			delProductTransfer(ps2, idInVehicle, idOutVehicle);
			int totalQuantityNew = getTotalQuantity_byProductAndOutVehicle(idInVehicle, idOutVehicle, idProduct);
			if(totalQuantityNew > 0){
				ps = conn.prepareStatement(sql3);
				editProductOV(ps, idOutVehicle, idProduct, totalQuantityNew);
			}else{
				ps = conn.prepareStatement(sql);
				delProductOV(ps, idOutVehicle, idProduct);
			}
			if(ps.executeBatch().length + ps1.executeBatch().length + ps2.executeBatch().length == 3){
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, null);
			closeConnection(null, ps1, null);
			closeConnection(null, ps2, null);
		}
		return false;
	}

	public ArrayList<Unit> getListUnit() {
		String sql="SELECT u.id, u.name, u.desc FROM tblUnit AS u";
		ArrayList<Unit> listU=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listU=new ArrayList<Unit>();
			conn=getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				Unit u=new Unit();
				u.setId(rs.getLong("id"));
				u.setName(rs.getString("name"));
				u.setDesc(rs.getString("desc"));
				listU.add(u);
			}
			return listU;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}

	public ArrayList<ProductTransform> getListProductTransfrom_byInVehicle(Long idInVehicle) {
		String sql="SELECT pt.id, pt.idInVehicle, pt.idOutVehicle, pt.quantity, pt.idUnit, pt.idProduct, "
				+ "vi.code AS codeVehicleIV, vo.code AS codeVehicleOV, "
				+ "p.name AS nameProduct, u.name AS nameUnit "
				+ "FROM tblProductTransform AS pt "
				+ "JOIN tblInVehicle AS iv ON pt.idInVehicle=iv.idInVehicle "
					+ "JOIN tblVehicle AS vi ON iv.idVehicle=vi.idVehicle "
				+ "JOIN tblOutVehicle AS ov ON pt.idOutVehicle=ov.idOutVehicle "
					+ "JOIN tblVehicle AS vo ON ov.idVehicle=vo.idVehicle "
				+ "JOIN tblProduct AS p ON pt.idProduct=p.id "
				+ "JOIN tblUnit AS u ON pt.idUnit=u.id "
				+ "WHERE pt.idInVehicle=?";
		ArrayList<ProductTransform> listPT=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listPT = new ArrayList<ProductTransform>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idInVehicle);
			rs = ps.executeQuery();
			while(rs.next()){
				InVehicle iv=new InVehicle();
				iv.setIdInVehicle(rs.getLong("idInVehicle"));
				iv.setVehicleCode(rs.getString("codeVehicleIV"));
				OutVehicle ov=new OutVehicle();
				ov.setIdOutVehicle(rs.getLong("idOutVehicle"));
				ov.setVehicleCode(rs.getString("codeVehicleOV"));
				Product p=new Product();
				p.setId(rs.getLong("idProduct"));
				p.setName(rs.getString("nameProduct"));
				Unit u=new Unit();
				u.setId(rs.getLong("idUnit"));
				u.setName(rs.getString("nameUnit"));
				
				ProductInVehicle piv=new ProductInVehicle();
				piv.setIv(iv);
				ProductOutVehicle pov=new ProductOutVehicle();
				pov.setId(getIdPOV_byIdOutVehicleAndIdProduct(ov.getIdOutVehicle(), p.getId()));
				pov.setOv(ov);
				pov.setP(p);
				pov.setU(u);
				pov.setQuantity(rs.getInt("quantity"));
				ProductTransform pt=new ProductTransform();
				pt.setId(rs.getLong("id"));
				pt.setPiv(piv);
				pt.setPov(pov);
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
	
	public ArrayList<ProductTransform> getListProductTransfrom_byOutVehicle(Long idOutVehicle) {
		String sql="SELECT pt.id, pt.idInVehicle, pt.idOutVehicle, pt.quantity, pt.idUnit, pt.idProduct, "
				+ "vi.code AS codeVehicleIV, vo.code AS codeVehicleOV, "
				+ "p.name AS nameProduct, u.name AS nameUnit "
				+ "FROM tblProductTransform AS pt "
				+ "JOIN tblInVehicle AS iv ON pt.idInVehicle=iv.idInVehicle "
					+ "JOIN tblVehicle AS vi ON iv.idVehicle=vi.idVehicle "
				+ "JOIN tblOutVehicle AS ov ON pt.idOutVehicle=ov.idOutVehicle "
					+ "JOIN tblVehicle AS vo ON ov.idVehicle=vo.idVehicle "
				+ "JOIN tblProduct AS p ON pt.idProduct=p.id "
				+ "JOIN tblUnit AS u ON pt.idUnit=u.id "
				+ "WHERE pt.idOutVehicle=?";
		ArrayList<ProductTransform> listPT=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listPT = new ArrayList<ProductTransform>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idOutVehicle);
			rs = ps.executeQuery();
			while(rs.next()){
				InVehicle iv=new InVehicle();
				iv.setIdInVehicle(rs.getLong("idInVehicle"));
				iv.setVehicleCode(rs.getString("codeVehicleIV"));
				OutVehicle ov=new OutVehicle();
				ov.setIdOutVehicle(rs.getLong("idOutVehicle"));
				ov.setVehicleCode(rs.getString("codeVehicleOV"));
				Product p=new Product();
				p.setId(rs.getLong("idProduct"));
				p.setName(rs.getString("nameProduct"));
				Unit u=new Unit();
				u.setId(rs.getLong("idUnit"));
				u.setName(rs.getString("nameUnit"));
				
				ProductInVehicle piv=new ProductInVehicle();
				piv.setIv(iv);
				ProductOutVehicle pov=new ProductOutVehicle();
				pov.setId(getIdPOV_byIdOutVehicleAndIdProduct(ov.getIdOutVehicle(), p.getId()));
				pov.setOv(ov);
				pov.setP(p);
				pov.setU(u);
				pov.setQuantity(rs.getInt("quantity"));
				ProductTransform pt=new ProductTransform();
				pt.setId(rs.getLong("id"));
				pt.setPiv(piv);
				pt.setPov(pov);
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

	public Long getIdPOV_byIdOutVehicleAndIdProduct(Long idOutVehicle, Long idProduct) {
		String sql="SELECT id FROM tblProductOutVehicle WHERE idOutVehicle=? AND idProduct=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idOutVehicle);
			ps.setLong(2, idProduct);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getLong("id");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}

	public ArrayList<ProductInVehicle> getPagePIVCurMonth_byKeyAndDate(int currentPage, int sizePage, String key, String date, String company) {
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT piv.id AS pivId, piv.quantity, "
					+ "iv.date, iv.arrivalTime, " 
					+ "v.code, v.company, p.name AS pName, u.name AS uName "
					+ "FROM tblProductInVehicle piv " 
					+ "JOIN tblInVehicle AS iv ON piv.idInVehicle=iv.idInVehicle "
					+ "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle AND v.code LIKE ? AND v.company LIKE ? "
					+ "LEFT JOIN tblProduct AS p ON piv.idProduct=p.id "
					+ "LEFT JOIN tblUnit AS u ON piv.idUnit=u.id "
					+ "WHERE iv.date LIKE ? AND month(iv.date) LIKE ? AND year(iv.date) LIKE ? "
					+ "ORDER BY iv.date, v.code "
					+ "LIMIT ?, ?";
					
		ArrayList<ProductInVehicle> pagePIV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			pagePIV=new ArrayList<ProductInVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +key+ "%");
			ps.setString(2, "%" +company+ "%");
			ps.setString(3, "%" +date);
			ps.setString(4, "%" +myTool.getMonth(date));
			ps.setString(5, "%" +myTool.getYear(date));
			ps.setInt(6, sIndex);
			ps.setInt(7, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				ProductInVehicle piv=new ProductInVehicle();
				InVehicle iv=new InVehicle();
				iv.setVehicleCode(rs.getString("code"));
				iv.setCompany(rs.getString("company"));
				iv.setDate(rs.getString("date"));
				iv.setArrivalTime(rs.getString("arrivalTime"));
				Product p=new Product();
				p.setName(rs.getString("pName"));
				Unit u=new Unit();
				u.setName(rs.getString("uName"));
				
				piv.setId(rs.getLong("pivId"));
				piv.setIv(iv);
				piv.setP(p);
				piv.setQuantity(rs.getInt("quantity"));
				piv.setU(u);
				pagePIV.add(piv);
			}
			return pagePIV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}

	public int countAllPIVCurMonth_byKeyAndDate(String key, String date, String company) {
		String sql="SELECT COUNT(*) "
				+ "FROM tblProductInVehicle piv " 
				+ "JOIN tblInVehicle AS iv ON piv.idInVehicle=iv.idInVehicle "
				+ "JOIN tblVehicle AS v ON iv.idVehicle=v.idVehicle AND v.code LIKE ? AND v.company LIKE ?"
				+ "WHERE iv.date LIKE ? ";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +key+ "%");
			ps.setString(2, "%" +company+ "%");
			ps.setString(3, "%" +date+ "%");
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	
	public ArrayList<ProductOutVehicle> getPagePOVCurMonth_byKeyAndDate(int currentPage, int sizePage, String key, String date, String company) {
		int sIndex = (currentPage-1) * sizePage;
		String sql="SELECT pov.id AS povId, pov.quantity, "
					+ "ov.date, ov.arrivalTime, " 
					+ "v.code, v.company, p.name AS pName, u.name AS uName "
					+ "FROM tblProductOutVehicle pov  " 
					+ "JOIN tblOutVehicle AS ov ON pov.idOutVehicle=ov.idOutVehicle "
					+ "JOIN tblVehicle AS v ON ov.idVehicle=v.idVehicle AND v.code LIKE ? AND v.company LIKE ? "
					+ "LEFT JOIN tblProduct AS p ON pov.idProduct=p.id "
					+ "LEFT JOIN tblUnit AS u ON pov.idUnit=u.id "
					+ "WHERE ov.date LIKE ? AND month(ov.date) LIKE ? AND year(ov.date) LIKE ? "
					+ "ORDER BY ov.date, v.code "
					+ "LIMIT ?, ?";
					
		ArrayList<ProductOutVehicle> pagePOV=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			pagePOV=new ArrayList<ProductOutVehicle>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +key+ "%");
			ps.setString(2, "%" +company+ "%");
			ps.setString(3, "%" +date+ "%");
			ps.setString(4, "%" +myTool.getMonth(date));
			ps.setString(5, "%" +myTool.getYear(date));
			ps.setInt(6, sIndex);
			ps.setInt(7, sizePage);
			rs = ps.executeQuery();
			while(rs.next()){
				ProductOutVehicle pov=new ProductOutVehicle();
				OutVehicle ov=new OutVehicle();
				ov.setVehicleCode(rs.getString("code"));
				ov.setCompany(rs.getString("company"));
				ov.setDate(rs.getString("date"));
				ov.setArrivalTime(rs.getString("arrivalTime"));
				Product p=new Product();
				p.setName(rs.getString("pName"));
				Unit u=new Unit();
				u.setName(rs.getString("uName"));
				
				pov.setId(rs.getLong("povId"));
				pov.setOv(ov);
				pov.setP(p);
				pov.setQuantity(rs.getInt("quantity"));
				pov.setU(u);
				pagePOV.add(pov);
			}
			return pagePOV;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return null;
	}

	public int countAllPOVCurMonth_byKeyAndDate(String key, String date, String company) {
		String sql="SELECT COUNT(*) "
				+ "FROM tblProductOutVehicle pov " 
				+ "JOIN tblOutVehicle AS ov ON pov.idOutVehicle=ov.idOutVehicle "
				+ "JOIN tblVehicle AS v ON ov.idVehicle=v.idVehicle AND v.code LIKE ? AND v.company LIKE ?"
				+ "WHERE ov.date LIKE ? ";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +key+ "%");
			ps.setString(2, "%" +company+ "%");
			ps.setString(3, "%" +date+ "%");
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}

	public int getTrasnferNumberRule_byProduct(Long idProduct) {
		String sql="SELECT transferNumber FROM tblTransferRule r "
				+ "JOIN tblProductType AS t ON r.idProductType=t.id "
				+ "JOIN tblProduct AS p ON t.id=p.idProductType AND p.id=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, idProduct);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getInt("transferNumber");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, ps, rs);
		}
		return 0;
	}
	public ArrayList<ProductTransfer> getProductTransfer(ArrayList<InVehicle> pageIV){
		String sql="SELECT idInVehicle, idOutVehicle, transfer FROM tblProductTransfer WHERE idInVehicle IN ?";
		ArrayList<ProductTransfer> listPT=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listPT = new ArrayList<ProductTransfer>();
			conn = getConnection();
			
			String[] data = new String[pageIV.size()];
			for(int i=0; i<pageIV.size(); i++){
				data[i] = pageIV.get(i).getIdInVehicle() + "";
			}
			Array arr = conn.createArrayOf("VARCHAR", data);
			ps = conn.prepareStatement(sql);
//			ps.setArray(1, c);
			rs = ps.executeQuery();
			while(rs.next()){
				InVehicle iv=new InVehicle(); 
				iv.setIdInVehicle(rs.getLong("idInVehicle")); 
				OutVehicle ov=new OutVehicle(); 
				ov.setIdOutVehicle(rs.getLong("idOutVehicle")); 
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

	public ArrayList<ProductTransfer> getAllProductTransferCurDate(){
		String sql="SELECT pt.idInVehicle, pt.idOutVehicle, pt.transfer "
				+ "FROM tblProductTransfer pt "
				+ "JOIN tblInVehicle iv ON pt.idInVehicle=iv.idInVehicle "
				+ "JOIN tblOutVehicle ov ON pt.idOutVehicle=ov.idOutVehicle "
				+ "WHERE iv.date=CURDATE() AND ov.date=CURDATE() AND (iv.status < 2 AND ov.status < 2)";
		ArrayList<ProductTransfer> listPT=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			listPT = new ArrayList<ProductTransfer>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				ProductTransfer pt=new ProductTransfer();
				InVehicle iv=new InVehicle(); iv.setIdInVehicle(rs.getLong("idInVehicle"));
				OutVehicle ov=new OutVehicle(); ov.setIdOutVehicle(rs.getLong("idOutVehicle"));
				pt.setIv(iv);
				pt.setOv(ov);
				pt.setTransfer(rs.getInt("transfer"));
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

	public int getTransferNumber(int quantity, int tfRule){
		int tfNumber=0;
		int m = quantity % Constants.QUANTITY_TRANSFER;
		int n = quantity / Constants.QUANTITY_TRANSFER;
		if(m == 0){
			tfNumber = n*tfRule;
		}else{
			tfNumber = n*tfRule + 1;
		}
		return tfNumber;
	}
}
