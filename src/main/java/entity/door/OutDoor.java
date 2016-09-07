package entity.door;

public class OutDoor extends Door{
	private Integer idOutDoor;

	public OutDoor() {
		super();
	}
	public OutDoor(String nameDoor, Integer capacity, CrossDockingSystem cDS, Integer status) {
		super(nameDoor, capacity, cDS, status);
	}
	public OutDoor(Integer idDoor, String nameDoor, Integer capacity) {
		super(idDoor, nameDoor, capacity);
	}
	public OutDoor(Integer idDoor, String nameDoor, Integer capacity, CrossDockingSystem cDS) {
		super(idDoor, nameDoor, capacity, cDS);
	}
	public OutDoor(int idDoor, String nameDoor, Integer capacity, Integer status, CrossDockingSystem cDS) {
		super(idDoor, nameDoor, capacity, status, cDS);
	}
	public OutDoor(int idDoor, String nameDoor, Integer capacity, Integer status) {
		super(idDoor, nameDoor, capacity, status);
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
