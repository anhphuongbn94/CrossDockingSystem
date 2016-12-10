package com.cds.dao.vehicle;

import java.util.ArrayList;

import com.cds.entity.product.ProductTransfer;
import com.cds.entity.vehicle.InVehicle;
import com.cds.entity.vehicle.OutVehicle;
import com.cds.entity.vehicle.Vehicle;

public interface VehicleDAO {
	public ArrayList<Vehicle> getListVehicle();
	
	public ArrayList<Vehicle> getPageVehicle(int currentPage, int sizePage);
	
	public boolean checkInsertVehicle(Vehicle v);
	public boolean insertVehicle(Vehicle v);
	
	/*--------------------Income Vehicle------------------*/
	public int countInVehicle();
	public int countInVehicle_byStatusAndKey(String key, String status);
	public int countInVehicle_whereUnloadStatus();
	public int countInVehicle_whereAssignDoor();
	public int countInVehicle_byDateAndCodeVehicle(String strDate, String endDate, String vehicleCode);
	
	public boolean checkInsertIV(InVehicle iv);
	public void insertInVehicle(InVehicle iVehicle);
	
	public boolean assignDoorInVehicle(long idInVehicle, long idInDoor);
	
	public boolean upDataUnloadTime(long idInVehicle, String startTime, String endTime);
	public boolean upFinishUnloadTime(long idInVehicle);
	public boolean upInVehicle(long idInVehicle, String date, String arrivalTime);
	
	public ArrayList<InVehicle> getAllInVehicleCurDate();
	public ArrayList<InVehicle> getPageInVehicle(int currentPage, int sizePage);
	public ArrayList<InVehicle> getListInVehicle_byCurDate(String date);
	public ArrayList<InVehicle> getPageInVehicle_whereAssignDoor(int currentPage, int sizePage);
	public ArrayList<InVehicle> getPageInVehicle_whereUnloadStatus(int currentPage, int sizePage);
	public ArrayList<InVehicle> getPageInVehicle_byStatusAndKey(String key, String status, int currentPage, int sizePage);
	public ArrayList<InVehicle> getPageInVehicle_byDateAndCodeVehicle(String strDate, String endDate, String vehicleCode, int currentPage, int sizePage);
	
	public ArrayList<InVehicle> getPageInVehicleCurDate_byKeySearch(String key, int currentPage, int sizePage);
	public int countAllInVehicleCurDate_byKeySearch(String key);
	
	public boolean delInVehicle_byIdVehicle(long idInVehicle);
	
	public int getVolumnInVehicle(Long idInVehicle);
	/*--------------------Outcome Vehicle------------------*/
	public int countOutVehicle();
	public int countOutVehicle_byStatusAndKey(String key, String status);
	public int countOutVehicle_whereLoadStatus();
	public int countOutVehicle_whereAssignDoor();
	public int countOutVehicle_byDateAndCodeVehicle(String strDate, String endDate, String vehicleCode);
	
	public boolean checkInsertOV(OutVehicle ov);
	public void insertOutVehicle(OutVehicle ov);
	
	public boolean assignDoorOutVehicle(long idOutVehicle, long idOutDoor);
	
	public boolean upDataLoadTime(long idOutVehicle, String startTime, String endTime);
	public boolean upFinishLoadTime(long idOutVehicle);
	public boolean upOutVehicle(long idInVehicle, String date, String arrivalTime);
	
	public ArrayList<OutVehicle> getPageOutVehicle_byDateAndCodeVehicle(String strDate, String endDate, String vehicleCode, int currentPage, int sizePage);
	
	public ArrayList<OutVehicle> getAllOutVehicleCurDate();
	public ArrayList<OutVehicle> getPageOutVehicle(int currentPage, int sizePage);
	public ArrayList<OutVehicle> getListOutVehicle_byCurDate(String date);
	public ArrayList<OutVehicle> getPageOutVehicle_whereAssignDoor(int currentPage, int sizePage);
	public ArrayList<OutVehicle> getPageOutVehicle_whereLoadStatus(int currentPage, int sizePage);
	public ArrayList<OutVehicle> getPageOutVehicle_byStatusAndKey(String key, String status, int currentPage, int sizePage);
	
	public ArrayList<OutVehicle> getPageOutVehicleCurDate_byKeySearch(String key, int currentPage, int sizePage);
	public int countAllOutVehicleCurDate_byKeySearch(String key);
	
	public boolean delOutVehicle_byIdVehicle(long idOutVehicle);
	
	public int getDemandOutVehicle(Long idOutVehicle);
	
	/*-------------------------------------------------------*/
}
