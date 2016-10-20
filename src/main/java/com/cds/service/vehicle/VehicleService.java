package com.cds.service.vehicle;

import java.util.ArrayList;

import com.cds.entity.product.ProductTransfer;
import com.cds.entity.vehicle.InVehicle;
import com.cds.entity.vehicle.OutVehicle;
import com.cds.entity.vehicle.Vehicle;

public interface VehicleService {
public ArrayList<Vehicle> getListVehicle();
	
	public ArrayList<Vehicle> getPageVehicle(int currentPage, int sizePage);
	
	public boolean checkInsertVehicle(Vehicle v);
	public boolean insertVehicle(Vehicle v);
	
	/*--------------------Income Vehicle------------------*/
	public int countInVehicle();
	public int countInVehicle_byStatus(int status);
	public int countInVehicle_whereUnloadStatus();
	public int countInVehicle_whereAssignDoor();
	public int countInVehicle_byDateAndCodeVehicle(String strDate, String endDate, String vehicleCode);
	
	public boolean checkInsertIV(InVehicle iv);
	public void insertInVehicle(InVehicle iVehicle);
	
	public boolean assignDoorInVehicle(int idInVehicle, int idInDoor);
	
	public boolean upStartUnloadTime(int idInVehicle);
	public boolean upFinishUnloadTime(int idInVehicle);
	public boolean upStatusInVehicle(int idInVehicle, int status);
	
	
	public ArrayList<InVehicle> getPageInVehicle(int currentPage, int sizePage);
	public ArrayList<InVehicle> getListInVehicle_byCurDate(String date);
	public ArrayList<InVehicle> getPageInVehicle_whereAssignDoor(int currentPage, int sizePage);
	public ArrayList<InVehicle> getPageInVehicle_whereUnloadStatus(int currentPage, int sizePage);
	public ArrayList<InVehicle> getPageInVehicle_byStatus(int status, int currentPage, int sizePage);
	public ArrayList<InVehicle> getPageInVehicle_byDateAndCodeVehicle(
				String strDate, String endDate, String vehicleCode, int currentPage, int sizePage);
	
	public ArrayList<OutVehicle> getPageOutVehicleCurDate_byKeySearch(String key, int currentPage, int sizePage);
	public int countAllOutVehicleCurDate_byKeySearch(String key);
	
	public void delInVehicle_byIdVehicle(int idInVehicle);
	
	public ArrayList<InVehicle> getPageInVehicleCurDate_byKeySearch(String key, int currentPage, int sizePage);
	public int countAllInVehicleCurDate_byKeySearch(String key); 
	
	public Double getVolumnInVehicle(int idInVehicle);
	/*--------------------Outcome Vehicle------------------*/
	public int countOutVehicle();
	public int countOutVehicle_byStatus(int status);
	public int countOutVehicle_whereLoadStatus();
	public int countOutVehicle_whereAssignDoor();
	public int countOutVehicle_byDateAndCodeVehicle(String strDate, String endDate, String vehicleCode);
	
	public boolean checkInsertOV(OutVehicle ov);
	public void insertOutVehicle(OutVehicle ov);
	
	public boolean assignDoorOutVehicle(int idOutVehicle, int idOutDoor);
	
	public boolean upStartLoadTime(int idOutVehicle);
	public boolean upFinishLoadTime(int idOutVehicle);
	public boolean upStatusOutVehicle(int idOutVehicle, int status);
	
	public ArrayList<OutVehicle> getPageOutVehicle_byDateAndCodeVehicle
		(String strDate, String endDate, String vehicleCode, int currentPage, int sizePage);
	
	public ArrayList<OutVehicle> getPageOutVehicle(int currentPage, int sizePage);
	public ArrayList<OutVehicle> getListOutVehicle_byCurDate(String date);
	public ArrayList<OutVehicle> getPageOutVehicle_whereAssignDoor(int currentPage, int sizePage);
	public ArrayList<OutVehicle> getPageOutVehicle_whereLoadStatus(int currentPage, int sizePage);
	public ArrayList<OutVehicle> getPageOutVehicle_byStatus(int status, int currentPage, int sizePage);
	
	public void delOutVehicle_byIdVehicle(int idOutVehicle);
	
	public Double getDemandOutVehicle(int idOutVehicle);
	
	/*-------------------------------------------------------*/
	public int getIdProductTransferInsert();
	
	public void insertTransfer(ProductTransfer pTransfer);
	
	public ArrayList<ProductTransfer> getProductTransfer(String date);
}
