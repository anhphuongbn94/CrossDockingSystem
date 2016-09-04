package entity.door;

public class Door {
	private Integer idDoor;
	private String nameDoor; 
	private Integer capacity; // Sức chứa của cửa trong 1 ngày.
	private CrossDockingSystem cDS;
	
	public Door() {
		super();
	}
	public Door(int idDoor, String nameDoor, Integer capacity) {
		super();
		this.idDoor = idDoor;
		this.nameDoor = nameDoor;
		this.capacity = capacity;
	}
	public Door(int idDoor, String nameDoor, Integer capacity, CrossDockingSystem cDS) {
		super();
		this.idDoor = idDoor;
		this.nameDoor = nameDoor;
		this.capacity = capacity;
		this.cDS = cDS;
	}
	public Integer getIdDoor() {
		return idDoor;
	}
	public void setIdDoor(int idDoor) {
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
	public CrossDockingSystem getcDS() {
		return cDS;
	}
	public void setcDS(CrossDockingSystem cDS) {
		this.cDS = cDS;
	}
}
