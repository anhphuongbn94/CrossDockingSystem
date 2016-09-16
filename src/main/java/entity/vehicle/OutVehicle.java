package entity.vehicle;

import entity.door.CrossDockingSystem;
import entity.door.Door;

public class OutVehicle extends Vehicle{
	private Integer idOutVehicle;
	private String date;
	private String arrivalTime;
	private String startLoadTime;
	private String finishLoadTime;
	private Double demand;
	private Integer status; // 0: Dang cho gan cua, 1: Dang cho do hang, 2: Bat dau do hang, 3: Ket thuc do hang
	private Door door;
	private CrossDockingSystem cDS;
	
	public OutVehicle() {
		super();
	}
	public OutVehicle(Integer idVehicle, String vehicleCode, String vehicleType, Integer vehicleYear,
			String vehicleMake, Integer vehicleWeight, Integer vehicleTrailerNum, String vehicleDes) {
		super(idVehicle, vehicleCode, vehicleType, vehicleYear, vehicleMake, vehicleWeight, vehicleTrailerNum, vehicleDes);
	}
	public OutVehicle(String vehicleCode, String vehicleType, Integer vehicleYear, String vehicleMake,
			Integer vehicleWeight, Integer vehicleTrailerNum, String vehicleDes) {
		super(vehicleCode, vehicleType, vehicleYear, vehicleMake, vehicleWeight, vehicleTrailerNum, vehicleDes);
	}
	public OutVehicle(String date, String arrivalTime, String startLoadTime, String finishLoadTime, Double demand,
			Integer status, Door door, CrossDockingSystem cDS) {
		super();
		this.date = date;
		this.arrivalTime = arrivalTime;
		this.startLoadTime = startLoadTime;
		this.finishLoadTime = finishLoadTime;
		this.demand = demand;
		this.status = status;
		this.door = door;
		this.cDS = cDS;
	}
	public OutVehicle(Integer idOutVehicle, String date, String arrivalTime, String startLoadTime,
			String finishLoadTime, Double demand, Integer status, Door door, CrossDockingSystem cDS) {
		super();
		this.idOutVehicle = idOutVehicle;
		this.date = date;
		this.arrivalTime = arrivalTime;
		this.startLoadTime = startLoadTime;
		this.finishLoadTime = finishLoadTime;
		this.demand = demand;
		this.status = status;
		this.door = door;
		this.cDS = cDS;
	}
	public Integer getIdOutVehicle() {
		return idOutVehicle;
	}
	public void setIdOutVehicle(Integer idOutVehicle) {
		this.idOutVehicle = idOutVehicle;
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
	public String getStartLoadTime() {
		return startLoadTime;
	}
	public void setStartLoadTime(String startLoadTime) {
		this.startLoadTime = startLoadTime;
	}
	public String getFinishLoadTime() {
		return finishLoadTime;
	}
	public void setFinishLoadTime(String finishLoadTime) {
		this.finishLoadTime = finishLoadTime;
	}
	public Double getDemand() {
		return demand;
	}
	public void setDemand(Double demand) {
		this.demand = demand;
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

