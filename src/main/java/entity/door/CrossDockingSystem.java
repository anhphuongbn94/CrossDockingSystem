package entity.door;

public class CrossDockingSystem {
	private int idCrossDockingSystem;
	private String nameCrossDockingSystem;
	private String address;
	private int capacity;
	
	public CrossDockingSystem() {
		super();
	}
	public CrossDockingSystem(int idCrossDockingSystem, String nameCrossDockingSystem, String address, int capacity) {
		super();
		this.idCrossDockingSystem = idCrossDockingSystem;
		this.nameCrossDockingSystem = nameCrossDockingSystem;
		this.address = address;
		this.capacity = capacity;
	}
	public int getIdCrossDockingSystem() {
		return idCrossDockingSystem;
	}
	public void setIdCrossDockingSystem(int idCrossDockingSystem) {
		this.idCrossDockingSystem = idCrossDockingSystem;
	}
	public String getNameCrossDockingSystem() {
		return nameCrossDockingSystem;
	}
	public void setNameCrossDockingSystem(String nameCrossDockingSystem) {
		this.nameCrossDockingSystem = nameCrossDockingSystem;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	
}	
