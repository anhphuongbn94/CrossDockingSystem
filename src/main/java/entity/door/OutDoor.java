package entity.door;

public class OutDoor extends Door{
	private Integer idOutDoor;

	public OutDoor() {
		super();
	}
	public OutDoor(int idDoor, String nameDoor, Integer capacity, CrossDockingSystem cDS) {
		super(idDoor, nameDoor, capacity, cDS);
	}
	public OutDoor(int idDoor, String nameDoor, Integer capacity) {
		super(idDoor, nameDoor, capacity);
	}
	public OutDoor(Integer idOutDoor) {
		super();
		this.idOutDoor = idOutDoor;
	}
	public Integer getIdOutDoor() {
		return idOutDoor;
	}
	public void setIdOutDoor(Integer idOutDoor) {
		this.idOutDoor = idOutDoor;
	}
}
