package com.cds.services.impl.vehicle;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cds.dao.vehicle.ProductDAO;
import com.cds.entity.product.Product;
import com.cds.entity.product.ProductInVehicle;
import com.cds.entity.product.ProductOutVehicle;
import com.cds.entity.product.ProductTransfer;
import com.cds.entity.product.ProductTransform;
import com.cds.entity.product.Unit;
import com.cds.entity.vehicle.InVehicle;
import com.cds.service.vehicle.ProductService;

@Component
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDAO pDAO;

	public ArrayList<Product> getListProduct() {
		return pDAO.getListProduct();
	}
	
	public ArrayList<ProductInVehicle> getListProductIV_byVehicle(Long idInVehicle) {
		return pDAO.getListProductIV_byVehicle(idInVehicle);
	}

	public ProductInVehicle checkInsertProductIV(Long idProduct, Long idInVehicle){
		return pDAO.checkInsertProductIV(idProduct, idInVehicle);
	}
	
	public boolean insertProductIV(ProductInVehicle piv) {
		return pDAO.insertProductIV(piv);
	}
	
	public int getQuantityTransform_byInVehicle(Long idInVehicle){
		return pDAO.getQuantityTransform_byInVehicle(idInVehicle);
	}

	public boolean editProductIV(Long idPIV, int quantity) {
		return pDAO.editProductIV(idPIV, quantity);
	}
	
	public boolean checkDeleteProductIV(Long idInVehicle){
		return pDAO.checkDeleteProductIV(idInVehicle);
	}
	
	public boolean delProductIV(Long idPIV) {
		return pDAO.delProductIV(idPIV);
	}

	public ArrayList<Unit> getListUnit() {
		return pDAO.getListUnit();
	}

	public ArrayList<ProductOutVehicle> getListProductOV_byVehicle(Long idInVehicle) {
		return pDAO.getListProductOV_byVehicle(idInVehicle);
	}
	
	public ProductTransform checkInsertProductTransform(Long idProduct, Long idInVehicle, Long idOutVehicle){
		return pDAO.checkInsertProductTransform(idProduct, idInVehicle, idOutVehicle);
	}

	public boolean insertProductTransform(ProductTransform pt) {
		return pDAO.insertProductTransform(pt);
	}

	public boolean editProductOV(Long idProduct, Long idInVehicle, Long idOutVehicle, int quantity) {
		return pDAO.editProductOV(idProduct, idInVehicle, idOutVehicle, quantity);
	}

	public boolean delProductOV(Long idProduct, Long idInVehicle, Long idOutVehicle) {
		return pDAO.delProductOV(idProduct, idInVehicle, idOutVehicle);
	}

	public ArrayList<ProductTransform> getListProductTransfrom_byInVehicle(Long idInVehicle) {
		return pDAO.getListProductTransfrom_byInVehicle(idInVehicle);
	}
	
	public ArrayList<ProductTransform> getListProductTransfrom_byOutVehicle(Long idOutVehicle) {
		return pDAO.getListProductTransfrom_byOutVehicle(idOutVehicle);
	}

	public int countAllPIVCurMonth_byKeyAndDate(String key, String date, String company){
		return pDAO.countAllPIVCurMonth_byKeyAndDate(key, date, company);
	}
	
	public ArrayList<ProductInVehicle> getPagePIVCurMonth_byKeyAndDate(int currentPage, int sizePage, String key, String date, String company) {
		return pDAO.getPagePIVCurMonth_byKeyAndDate(currentPage, sizePage, key, date, company);
	}
	
	public int countAllPOVCurMonth_byKeyAndDate(String key, String date, String company){
		return pDAO.countAllPOVCurMonth_byKeyAndDate(key, date, company);
	}
	
	public ArrayList<ProductOutVehicle> getPagePOVCurMonth_byKeyAndDate(int currentPage, int sizePage, String key, String date, String company){
		return pDAO.getPagePOVCurMonth_byKeyAndDate(currentPage, sizePage, key, date, company);
	}

	public int getTrasnferNumberRule_byProduct(Long idProduct){
		return pDAO.getTrasnferNumberRule_byProduct(idProduct);
	}
	
//	public ArrayList<ProductTransfer> getProductTransfer(ArrayList<InVehicle> pageIV) {
//		return pDAO.getProductTransfer(pageIV);
//	}

	public ProductOutVehicle checkInsertProductOV(Long idProduct, Long idOutVehicle) {
		return pDAO.checkInsertProductOV(idProduct, idOutVehicle);
	}

	public Long getIdPOV_byIdOutVehicleAndIdProduct(Long idOutVehicle, Long idProduct) {
		return pDAO.getIdPOV_byIdOutVehicleAndIdProduct(idOutVehicle, idProduct);
	}

	public ArrayList<ProductTransfer> getAllProductTransferCurDate() {
		return pDAO.getAllProductTransferCurDate();
	}
}
