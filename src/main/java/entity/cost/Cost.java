package entity.cost;

import entity.door.InDoor;
import entity.door.OutDoor;

public class Cost {
	private Integer idCost;
	private InDoor inDoor;
	private OutDoor outDoor;
	private Double cost;
	
	public Cost() {
		super();
	}
	public Cost(Integer idCost, InDoor inDoor, OutDoor outDoor, Double cost) {
		super();
		this.idCost = idCost;
		this.inDoor = inDoor;
		this.outDoor = outDoor;
		this.cost = cost;
	}
	public Integer getIdCost() {
		return idCost;
	}
	public void setIdCost(Integer idCost) {
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
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
}
