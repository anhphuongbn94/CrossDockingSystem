package entity.door;

public class InDoor extends Door{
	private Integer idInDoor;

	public InDoor() {
		super();
	}
	public InDoor(int idDoor, String nameDoor, Integer capacity, CrossDockingSystem cDS) {
		super(idDoor, nameDoor, capacity, cDS);
	}
	public InDoor(int idDoor, String nameDoor, Integer capacity) {
		super(idDoor, nameDoor, capacity);
	}
	public InDoor(Integer idInDoor) {
		super();
		this.idInDoor = idInDoor;
	}
	public Integer getIdInDoor() {
		return idInDoor;
	}
	public void setIdInDoor(Integer idInDoor) {
		this.idInDoor = idInDoor;
	}	
	

	
}
