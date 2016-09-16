package entity.vehicle;

import entity.door.CrossDockingSystem;
import entity.door.Door;

public class InVehicle extends Vehicle{
	private Integer idInVehicle;
	private String date;
	private String arrivalTime;
	private String startUnloadTime;
	private String finishUnloadTime;
	private Double volumn;
	private Integer status; // 0: Dang cho gan cua, 1: Dang cho do hang, 2: Bat dau do hang, 3: Ket thuc do hang
	private Door door;
	private CrossDockingSystem cDS;
	
	public InVehicle() {
		super();
	}
	public InVehicle(Integer idVehicle, String vehicleCode, String vehicleType, Integer vehicleYear, String vehicleMake,
			Integer vehicleWeight, Integer vehicleTrailerNum, String vehicleDes) {
		super(idVehicle, vehicleCode, vehicleType, vehicleYear, vehicleMake, vehicleWeight, vehicleTrailerNum, vehicleDes);
	}
	public InVehicle(String vehicleCode, String vehicleType, Integer vehicleYear, String vehicleMake,
			Integer vehicleWeight, Integer vehicleTrailerNum, String vehicleDes) {
		super(vehicleCode, vehicleType, vehicleYear, vehicleMake, vehicleWeight, vehicleTrailerNum, vehicleDes);
	}
	public InVehicle(Integer idInVehicle, String date, String arrivalTime, String startUnloadTime,
			String finishUnloadTime, Double volumn, Integer status, Door door, CrossDockingSystem cDS) {
		super();
		this.idInVehicle = idInVehicle;
		this.date = date;
		this.arrivalTime = arrivalTime;
		this.startUnloadTime = startUnloadTime;
		this.finishUnloadTime = finishUnloadTime;
		this.volumn = volumn;
		this.status = status;
		this.door = door;
		this.cDS = cDS;
	}
	public Integer getIdInVehicle() {
		return idInVehicle;
	}
	public void setIdInVehicle(Integer idInVehicle) {
		this.idInVehicle = idInVehicle;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getStartUnloadTime() {
		return startUnloadTime;
	}
	public void setStartUnloadTime(String startUnloadTime) {
		this.startUnloadTime = startUnloadTime;
	}
	public String getFinishUnloadTime() {
		return finishUnloadTime;
	}
	public void setFinishUnloadTime(String finishUnloadTime) {
		this.finishUnloadTime = finishUnloadTime;
	}
	public Double getVolumn() {
		return volumn;
	}
	public void setVolumn(Double volumn) {
		this.volumn = volumn;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Door getDoor() {
		return door;
	}
	public void setDoor(Door door) {
		this.door = door;
	}
	public CrossDockingSystem getcDS() {
		return cDS;
	}
	public void setcDS(CrossDockingSystem cDS) {
		this.cDS = cDS;
	}
}
