package com.cds.entity.door;

public class Cost {
	private Long idCost;
	private InDoor inDoor;
	private OutDoor outDoor;
	private int cost;
	
	public Cost() {
		super();
	}
	public Cost(InDoor inDoor, OutDoor outDoor, int cost) {
		super();
		this.inDoor = inDoor;
		this.outDoor = outDoor;
		this.cost = cost;
	}
	public Cost(Long idCost, InDoor inDoor, OutDoor outDoor, int cost) {
		super();
		this.idCost = idCost;
		this.inDoor = inDoor;
		this.outDoor = outDoor;
		this.cost = cost;
	}
	public Long getIdCost() {
		return idCost;
	}
	public void setIdCost(Long idCost) {
		this.idCost = idCost;
	}
	public InDoor getInDoor() {
		return inDoor;
	}
	public void setInDoor(InDoor inDoor) {
		this.inDoor = inDoor;
	}
	public OutDoor getOutDoor() {
		return outDoor;
	}
	public void setOutDoor(OutDoor outDoor) {
		this.outDoor = outDoor;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
}
