package com.cds.entity.vehicle;

public class Vehicle {
	private Long idVehicle;
	private String vehicleCode;
	private String type;
	private String company;
	private String description;
	
	public Vehicle() {
		super();
	}
	public Vehicle(Long idVehicle, String vehicleCode, String type, String company, String description) {
		super();
		this.idVehicle = idVehicle;
		this.vehicleCode = vehicleCode;
		this.type = type;
		this.company = company;
		this.description = description;
	}
	public Long getIdVehicle() {
		return idVehicle;
	}
	public void setIdVehicle(Long idVehicle) {
		this.idVehicle = idVehicle;
	}
	public String getVehicleCode() {
		return vehicleCode;
	}
	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}	
