package dao.vehicle;

import java.util.ArrayList;

import entity.product.ProductTransfer;
import entity.vehicle.InVehicle;
import entity.vehicle.OutVehicle;
import entity.vehicle.Vehicle;

public interface VehicleDAO {
	public ArrayList<Vehicle> getListVehicle();
	public ArrayList<Vehicle> getPageVehicle(int currentPage, int sizePage);
	public boolean insertVehicle(Vehicle v);
	public int getIdVehicleInsert();
	public int countVehicle();
	/*--------------------Income Vehicle------------------*/
	public int countInVehicle();
	public int countInVehicle_byStatus(int status);
	public int countInVehicle_whereUnloadStatus();
	public int countInVehicle_whereAssignDoor();
	public void insertInVehicle(InVehicle iVehicle);
	public boolean assignDoorInVehicle(int idInVehicle, int idInDoor);
	public boolean upStartUnloadTime(int idInVehicle);
	public boolean upFinishUnloadTime(int idInVehicle);
	public boolean upStatusInVehicle(int idInVehicle, int status);
	public int getIdVehicle_byCodeVehicle(String codeVehicle);
	public ArrayList<InVehicle> getListInVehicle_byCurDate(String date);
	public ArrayList<InVehicle> getPageInVehicle(int currentPage, int sizePage);
	public ArrayList<InVehicle> getPageInVehicle_whereAssignDoor(int currentPage, int sizePage);
	public ArrayList<InVehicle> getPageInVehicle_whereUnloadStatus(int currentPage, int sizePage);
	public ArrayList<InVehicle> getPageInVehicle_byStatus(int status, int currentPage, int sizePage);
	public void delInVehicle_byIdVehicle(int idInVehicle);
	public Double getVolumnInVehicle(int idInVehicle);
	public int getIdInVehicleInsert();
	/*--------------------Outcome Vehicle------------------*/
	public int countOutVehicle();
	public int countOutVehicle_byStatus(int status);
	public int countOutVehicle_whereLoadStatus();
	public int countOutVehicle_whereAssignDoor();
	public void insertOutVehicle(OutVehicle oVihicle);
	public boolean assignDoorOutVehicle(int idOutVehicle, int idOutDoor);
	public boolean upStartLoadTime(int idOutVehicle);
	public boolean upFinishLoadTime(int idOutVehicle);
	public boolean upStatusOutVehicle(int idOutVehicle, int status);
	public ArrayList<OutVehicle> getListOutVehicle_byCurDate(String date);
	public ArrayList<OutVehicle> getPageOutVehicle(int currentPage, int sizePage);
	public ArrayList<OutVehicle> getPageOutVehicle_whereAssignDoor(int currentPage, int sizePage);
	public ArrayList<OutVehicle> getPageOutVehicle_whereLoadStatus(int currentPage, int sizePage);
	public ArrayList<OutVehicle> getPageOutVehicle_byStatus(int status, int currentPage, int sizePage);
	public void delOutVehicle_byIdVehicle(int idOutVehicle);
	public Double getDemandOutVehicle(int idOutVehicle);
	public int getIdOutVehicleInsert();
	/*-------------------------------------------------------*/
	public int getIdProductTransferInsert();
	public void insertTransfer(ProductTransfer pTransfer);
	public ArrayList<ProductTransfer> getProductTransfer(String date);
	
	
}
