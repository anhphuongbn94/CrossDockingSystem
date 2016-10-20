package com.cds.entity.vehicle;

import com.cds.entity.door.CrossDockingSystem;
import com.cds.entity.door.Door;

public class OutVehicle extends Vehicle{
	private Long idOutVehicle;
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
	public OutVehicle(Long idVehicle, String vehicleCode, String type, String company, String description) {
		super(idVehicle, vehicleCode, type, company, description);
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
	public OutVehicle(Long idOutVehicle, String date, String arrivalTime, String startLoadTime,
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
	public Long getIdOutVehicle() {
		return idOutVehicle;
	}
	public void setIdOutVehicle(Long idOutVehicle) {
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

