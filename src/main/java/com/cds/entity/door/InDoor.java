package com.cds.entity.door;

public class InDoor extends Door{
	private Long idInDoor;
	private int capuse;

	public InDoor() {
		super();
	}
	public InDoor(Long idDoor, String nameDoor, Integer capacity, Integer status, CrossDockingSystem cDS) {
		super(idDoor, nameDoor, capacity, status, cDS);
	}

	public InDoor(Long idInDoor) {
		super();
		this.idInDoor = idInDoor;
	}
	public Long getIdInDoor() {
		return idInDoor;
	}
	public void setIdInDoor(Long idInDoor) {
		this.idInDoor = idInDoor;
	}
	public int getCapuse() {
		return capuse;
	}
	public void setCapuse(int capuse) {
		this.capuse = capuse;
	}	
	

	
}
