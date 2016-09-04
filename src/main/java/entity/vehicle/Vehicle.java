package entity.vehicle;

public class Vehicle {
	private Integer idVehicle;
	private String vehicleCode;
	private String vehicleType;
	private Integer vehicleYear;
	private String vehicleMake;
	private Integer vehicleWeight;
	private Integer vehicleTrailerNum;
	private String vehicleDes;
	
	public Vehicle() {
		super();
	}
	public Vehicle(String vehicleCode, String vehicleType, Integer vehicleYear, String vehicleMake, Integer vehicleWeight,
			Integer vehicleTrailerNum, String vehicleDes) {
		super();
		this.vehicleCode = vehicleCode;
		this.vehicleType = vehicleType;
		this.vehicleYear = vehicleYear;
		this.vehicleMake = vehicleMake;
		this.vehicleWeight = vehicleWeight;
		this.vehicleTrailerNum = vehicleTrailerNum;
		this.vehicleDes = vehicleDes;
	}
	public Vehicle(Integer idVehicle, String vehicleCode, String vehicleType, Integer vehicleYear, String vehicleMake,
			Integer vehicleWeight, Integer vehicleTrailerNum, String vehicleDes) {
		super();
		this.idVehicle = idVehicle;
		this.vehicleCode = vehicleCode;
		this.vehicleType = vehicleType;
		this.vehicleYear = vehicleYear;
		this.vehicleMake = vehicleMake;
		this.vehicleWeight = vehicleWeight;
		this.vehicleTrailerNum = vehicleTrailerNum;
		this.vehicleDes = vehicleDes;
	}
	public Integer getIdVehicle() {
		return idVehicle;
	}
	public void setIdVehicle(Integer idVehicle) {
		this.idVehicle = idVehicle;
	}
	public String getVehicleCode() {
		return vehicleCode;
	}
	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public Integer getVehicleYear() {
		return vehicleYear;
	}
	public void setVehicleYear(Integer vehicleYear) {
		this.vehicleYear = vehicleYear;
	}
	public String getVehicleMake() {
		return vehicleMake;
	}
	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}
	public Integer getVehicleWeight() {
		return vehicleWeight;
	}
	public void setVehicleWeight(Integer vehicleWeight) {
		this.vehicleWeight = vehicleWeight;
	}
	public Integer getVehicleTrailerNum() {
		return vehicleTrailerNum;
	}
	public void setVehicleTrailerNum(Integer vehicleTrailerNum) {
		this.vehicleTrailerNum = vehicleTrailerNum;
	}
	public String getVehicleDes() {
		return vehicleDes;
	}
	public void setVehicleDes(String vehicleDes) {
		this.vehicleDes = vehicleDes;
	}
}	
