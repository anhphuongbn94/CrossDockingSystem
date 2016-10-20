package com.cds.entity.door;

public class Door {
	private Long idDoor;
	private String nameDoor; 
	private Integer capacity; // Sức chứa của cửa trong 1 ngày.
	private Integer status; // 1: Dang hoat dong, 2: Ngung hoat dong, 3: Dang bao tri, 4:	
	private CrossDockingSystem cDS;
	
	public Door() {
		super();
	}
	public Door(Long idDoor, String nameDoor, Integer capacity, Integer status, CrossDockingSystem cDS) {
		super();
		this.idDoor = idDoor;
		this.nameDoor = nameDoor;
		this.capacity = capacity;
		this.status = status;
		this.cDS = cDS;
	}
	public Long getIdDoor() {
		return idDoor;
	}
	public void setIdDoor(Long idDoor) {
		this.idDoor = idDoor;
	}
	public String getNameDoor() {
		return nameDoor;
	}
	public void setNameDoor(String nameDoor) {
		this.nameDoor = nameDoor;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public CrossDockingSystem getcDS() {
		return cDS;
	}
	public void setcDS(CrossDockingSystem cDS) {
		this.cDS = cDS;
	}
}
