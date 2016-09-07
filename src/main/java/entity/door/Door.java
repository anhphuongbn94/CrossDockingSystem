package entity.door;

public class Door {
	private Integer idDoor;
	private String nameDoor; 
	private Integer capacity; // Sức chứa của cửa trong 1 ngày.
	private Integer status; // 1: Dang hoat dong, 2: Ngung hoat dong, 3: Dang bao tri, 4:	
	private CrossDockingSystem cDS;
	
	public Door() {
		super();
	}
	public Door(String nameDoor, Integer capacity, CrossDockingSystem cDS, Integer status) {
		super();
		this.nameDoor = nameDoor;
		this.capacity = capacity;
		this.cDS = cDS;
		this.status = status;
	}
	public Door(Integer idDoor, String nameDoor, Integer capacity) {
		super();
		this.idDoor = idDoor;
		this.nameDoor = nameDoor;
		this.capacity = capacity;
	}
	public Door(Integer idDoor, String nameDoor, Integer capacity, CrossDockingSystem cDS) {
		super();
		this.idDoor = idDoor;
		this.nameDoor = nameDoor;
		this.capacity = capacity;
		this.cDS = cDS;
	}

	public Door(int idDoor, String nameDoor, Integer capacity, Integer status) {
		super();
		this.idDoor = idDoor;
		this.nameDoor = nameDoor;
		this.capacity = capacity;
		this.status = status;
	}
	public Door(int idDoor, String nameDoor, Integer capacity, Integer status, CrossDockingSystem cDS) {
		super();
		this.idDoor = idDoor;
		this.nameDoor = nameDoor;
		this.capacity = capacity;
		this.status = status;
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
