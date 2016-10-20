package com.cds.services.impl.vehicle;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cds.dao.vehicle.VehicleDAO;
import com.cds.entity.product.ProductTransfer;
import com.cds.entity.vehicle.InVehicle;
import com.cds.entity.vehicle.OutVehicle;
import com.cds.entity.vehicle.Vehicle;
import com.cds.service.vehicle.VehicleService;

@Component
public class VehicleServiceImpl implements VehicleService{
	@Autowired
	private VehicleDAO vehicleDAO;
	
	public ArrayList<Vehicle> getListVehicle() {
		return vehicleDAO.getListVehicle();
	}

	public ArrayList<Vehicle> getPageVehicle(int currentPage, int sizePage) {
		return vehicleDAO.getPageVehicle(currentPage, sizePage);
	}

	public boolean checkInsertVehicle(Vehicle v) {
		return vehicleDAO.checkInsertVehicle(v);
	}

	public boolean insertVehicle(Vehicle v) {
		return vehicleDAO.insertVehicle(v);
	}

	public int countInVehicle() {
		return vehicleDAO.countInVehicle();
	}

	public int countInVehicle_byStatus(int status) {
		return vehicleDAO.countInVehicle_byStatus(status);
	}

	public int countInVehicle_whereUnloadStatus() {
		return vehicleDAO.countInVehicle_whereUnloadStatus();
	}

	public int countInVehicle_whereAssignDoor() {
		return vehicleDAO.countInVehicle_whereAssignDoor();
	}

	public int countInVehicle_byDateAndCodeVehicle(String strDate, String endDate, String vehicleCode) {
		return vehicleDAO.countInVehicle_byDateAndCodeVehicle(strDate, endDate, vehicleCode);
	}

	public boolean checkInsertIV(InVehicle iv) {
		return vehicleDAO.checkInsertIV(iv);
	}

	public void insertInVehicle(InVehicle iVehicle) {
		vehicleDAO.insertInVehicle(iVehicle);
	}

	public boolean assignDoorInVehicle(int idInVehicle, int idInDoor) {
		return vehicleDAO.assignDoorInVehicle(idInVehicle, idInDoor);
	}

	public boolean upStartUnloadTime(int idInVehicle) {
		return vehicleDAO.upStartUnloadTime(idInVehicle);
	}

	public boolean upFinishUnloadTime(int idInVehicle) {
		return vehicleDAO.upFinishUnloadTime(idInVehicle);
	}

	public boolean upStatusInVehicle(int idInVehicle, int status) {
		return vehicleDAO.upStatusInVehicle(idInVehicle, status);
	}

	public ArrayList<InVehicle> getPageInVehicle(int currentPage, int sizePage) {
		return vehicleDAO.getPageInVehicle(currentPage, sizePage);
	}

	public ArrayList<InVehicle> getListInVehicle_byCurDate(String date) {
		return vehicleDAO.getListInVehicle_byCurDate(date);
	}

	public ArrayList<InVehicle> getPageInVehicle_whereAssignDoor(int currentPage, int sizePage) {
		return vehicleDAO.getPageInVehicle_whereAssignDoor(currentPage, sizePage);
	}

	public ArrayList<InVehicle> getPageInVehicle_whereUnloadStatus(int currentPage, int sizePage) {
		return vehicleDAO.getPageInVehicle_whereUnloadStatus(currentPage, sizePage);
	}

	public ArrayList<InVehicle> getPageInVehicle_byStatus(int status, int currentPage, int sizePage) {
		return vehicleDAO.getPageInVehicle_byStatus(status, currentPage, sizePage);
	}

	public ArrayList<InVehicle> getPageInVehicle_byDateAndCodeVehicle(String strDate, String endDate,
			String vehicleCode, int currentPage, int sizePage) {
		return vehicleDAO.getPageInVehicle_byDateAndCodeVehicle(strDate, endDate, vehicleCode, currentPage, sizePage);
	}

	public ArrayList<OutVehicle> getPageOutVehicleCurDate_byKeySearch(
			String key, int currentPage, int sizePage){
		return vehicleDAO.getPageOutVehicleCurDate_byKeySearch(key, currentPage, sizePage);
	}
	public int countAllOutVehicleCurDate_byKeySearch(String key){
		return vehicleDAO.countAllOutVehicleCurDate_byKeySearch(key);
	}
	
	public void delInVehicle_byIdVehicle(int idInVehicle) {
		vehicleDAO.delInVehicle_byIdVehicle(idInVehicle);
	}

	public ArrayList<InVehicle> getPageInVehicleCurDate_byKeySearch(String key, int currentPage, int sizePage){
		return vehicleDAO.getPageInVehicleCurDate_byKeySearch(key, currentPage, sizePage);
	}
	
	public int countAllInVehicleCurDate_byKeySearch(String key){
		return vehicleDAO.countAllInVehicleCurDate_byKeySearch(key);
	}
	
	public Double getVolumnInVehicle(int idInVehicle) {
		return vehicleDAO.getVolumnInVehicle(idInVehicle);
	}

	public int countOutVehicle() {
		return vehicleDAO.countOutVehicle();
	}

	public int countOutVehicle_byStatus(int status) {
		return vehicleDAO.countOutVehicle_byStatus(status);
	}

	public int countOutVehicle_whereLoadStatus() {
		return vehicleDAO.countOutVehicle_whereLoadStatus();
	}

	public int countOutVehicle_whereAssignDoor() {
		return vehicleDAO.countOutVehicle_whereAssignDoor();
	}

	public int countOutVehicle_byDateAndCodeVehicle(String strDate, String endDate, String vehicleCode) {
		return vehicleDAO.countOutVehicle_byDateAndCodeVehicle(strDate, endDate, vehicleCode);
	}

	public boolean checkInsertOV(OutVehicle ov) {
		return vehicleDAO.checkInsertOV(ov);
	}

	public void insertOutVehicle(OutVehicle ov) {
		vehicleDAO.insertOutVehicle(ov);
	}

	public boolean assignDoorOutVehicle(int idOutVehicle, int idOutDoor) {
		return vehicleDAO.assignDoorOutVehicle(idOutVehicle, idOutDoor);
	}

	public boolean upStartLoadTime(int idOutVehicle) {
		return vehicleDAO.upStartLoadTime(idOutVehicle);
	}

	public boolean upFinishLoadTime(int idOutVehicle) {
		return vehicleDAO.upFinishLoadTime(idOutVehicle);
	}

	public boolean upStatusOutVehicle(int idOutVehicle, int status) {
		return vehicleDAO.upStatusOutVehicle(idOutVehicle, status);
	}

	public ArrayList<OutVehicle> getPageOutVehicle_byDateAndCodeVehicle(String strDate, String endDate,
			String vehicleCode, int currentPage, int sizePage) {
		return vehicleDAO.getPageOutVehicle_byDateAndCodeVehicle(strDate, endDate, vehicleCode, currentPage, sizePage);
	}

	public ArrayList<OutVehicle> getPageOutVehicle(int currentPage, int sizePage) {
		return vehicleDAO.getPageOutVehicle(currentPage, sizePage);
	}

	public ArrayList<OutVehicle> getListOutVehicle_byCurDate(String date) {
		return vehicleDAO.getListOutVehicle_byCurDate(date);
	}

	public ArrayList<OutVehicle> getPageOutVehicle_whereAssignDoor(int currentPage, int sizePage) {
		return vehicleDAO.getPageOutVehicle_whereAssignDoor(currentPage, sizePage);
	}

	public ArrayList<OutVehicle> getPageOutVehicle_whereLoadStatus(int currentPage, int sizePage) {
		return vehicleDAO.getPageOutVehicle_whereLoadStatus(currentPage, sizePage);
	}

	public ArrayList<OutVehicle> getPageOutVehicle_byStatus(int status, int currentPage, int sizePage) {
		return vehicleDAO.getPageOutVehicle_byStatus(status, currentPage, sizePage);
	}

	public void delOutVehicle_byIdVehicle(int idOutVehicle) {
		vehicleDAO.delOutVehicle_byIdVehicle(idOutVehicle);
	}

	public Double getDemandOutVehicle(int idOutVehicle) {
		return vehicleDAO.getDemandOutVehicle(idOutVehicle);
	}

	public int getIdProductTransferInsert() {
		return vehicleDAO.getIdProductTransferInsert();
	}

	public void insertTransfer(ProductTransfer pTransfer) {
		vehicleDAO.insertTransfer(pTransfer);
	}

	public ArrayList<ProductTransfer> getProductTransfer(String date) {
		return vehicleDAO.getProductTransfer(date);
	}

}
