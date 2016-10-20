package com.cds.entity.door;

public class OutDoor extends Door{
	private Long idOutDoor;

	public OutDoor() {
		super();
	}
	
	public OutDoor(Long idDoor, String nameDoor, Integer capacity, Integer status, CrossDockingSystem cDS) {
		super(idDoor, nameDoor, capacity, status, cDS);
	}
	public OutDoor(Long idOutDoor) {
		super();
		this.idOutDoor = idOutDoor;
	}
	public Long getIdOutDoor() {
		return idOutDoor;
	}
	public void setIdOutDoor(Long idOutDoor) {
		this.idOutDoor = idOutDoor;
	}
}
