package com.cds.entity.vehicle;

import com.cds.entity.door.CrossDockingSystem;
import com.cds.entity.door.Door;

public class OutVehicle extends Vehicle{
	private Long idOutVehicle;
	private String date;
	private String arrivalTime;
	private String startLoadTime;
	private String finishLoadTime;
	private int demand;
	private Integer status; // 0: Dang cho gan cua, 1: Dang cho do hang, 2: Bat dau do hang, 3: Ket thuc do hang
	private Door door;
	private CrossDockingSystem cDS;
	private Integer idDoorAI;
	
	public OutVehicle() {
		super();
	}
	public OutVehicle(Long idVehicle, String vehicleCode, String type, String company, String description) {
		super(idVehicle, vehicleCode, type, company, description);
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
	public int getDemand() {
		return demand;
	}
	public void setDemand(int demand) {
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
	public Integer getIdDoorAI() {
		return idDoorAI;
	}
	public void setIdDoorAI(Integer idDoorAI) {
		this.idDoorAI = idDoorAI;
	}
	
}

