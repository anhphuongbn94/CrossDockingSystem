package com.cds.dao.vehicle;

import java.util.ArrayList;

import com.cds.entity.product.Product;
import com.cds.entity.product.ProductInVehicle;
import com.cds.entity.product.ProductOutVehicle;
import com.cds.entity.product.ProductTransfer;
import com.cds.entity.product.ProductTransform;
import com.cds.entity.product.Unit;
import com.cds.entity.vehicle.InVehicle;

public interface ProductDAO {
	public ArrayList<Product> getListProduct();
	
	public ArrayList<ProductInVehicle> getListProductIV_byVehicle(Long idInVehicle);
	
	public ProductInVehicle checkInsertProductIV(Long idProduct, Long idInVehicle);
	
	public boolean insertProductIV(ProductInVehicle piv);
	
	public int getQuantityTransform_byInVehicle(Long idInVehicle);
	
	public boolean editProductIV(Long idPIV, int quantity);
	
	public boolean checkDeleteProductIV(Long idInVehicle);
	
	public boolean delProductIV(Long idPIV);
	
	public ArrayList<ProductOutVehicle> getListProductOV_byVehicle(Long idInVehicle);
	
	public ProductOutVehicle checkInsertProductOV(Long idProduct, Long idOutVehicle);
	
	public ProductTransform checkInsertProductTransform(Long idProduct, Long idInVehicle, Long idOutVehicle);
	
	public boolean insertProductTransform(ProductTransform pt);
	
	public boolean editProductOV(Long idProduct, Long idInVehicle, Long idOutVehicle, int quantity);
	
	public boolean delProductOV(Long idProduct, Long idInVehicle, Long idOutVehicle);
	
	public ArrayList<ProductTransform> getListProductTransfrom_byInVehicle(Long idInVehicle);
	
	public ArrayList<ProductTransform> getListProductTransfrom_byOutVehicle(Long idOutVehicle);
	
	public ArrayList<Unit> getListUnit();
	
	public Long getIdPOV_byIdOutVehicleAndIdProduct(Long idOutVehicle, Long idProduct);
	
	public int countAllPIVCurMonth_byKeyAndDate(String key, String date, String company);
	
	public ArrayList<ProductInVehicle> getPagePIVCurMonth_byKeyAndDate(int currentPage, int sizePage, String key, String date, String company);
	
	public int countAllPOVCurMonth_byKeyAndDate(String key, String date, String company);
	
	public ArrayList<ProductOutVehicle> getPagePOVCurMonth_byKeyAndDate(int currentPage, int sizePage, String key, String date, String company);
	
	public int getTrasnferNumberRule_byProduct(Long idProduct);
	
	public ArrayList<ProductTransfer> getAllProductTransferCurDate();
}
