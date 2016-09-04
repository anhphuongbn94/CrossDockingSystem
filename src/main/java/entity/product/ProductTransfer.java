package entity.product;

import entity.vehicle.InVehicle;
import entity.vehicle.OutVehicle;

public class ProductTransfer {
	private Integer idProductTrasfer;
	private InVehicle iVehicle;
	private OutVehicle oVehicle;
	private int transfer;
	
	public ProductTransfer() {
		super();
	}
	public ProductTransfer(InVehicle iVehicle, OutVehicle oVehicle, int transfer) {
		super();
		this.iVehicle = iVehicle;
		this.oVehicle = oVehicle;
		this.transfer = transfer;
	}
	public ProductTransfer(Integer idProductTrasfer, InVehicle iVehicle, OutVehicle oVehicle, int transfer) {
		super();
		this.idProductTrasfer = idProductTrasfer;
		this.iVehicle = iVehicle;
		this.oVehicle = oVehicle;
		this.transfer = transfer;
	}
	public Integer getIdProductTrasfer() {
		return idProductTrasfer;
	}
	public void setIdProductTrasfer(Integer idProductTrasfer) {
		this.idProductTrasfer = idProductTrasfer;
	}
	public InVehicle getiVehicle() {
		return iVehicle;
	}
	public void setiVehicle(InVehicle iVehicle) {
		this.iVehicle = iVehicle;
	}
	public OutVehicle getoVehicle() {
		return oVehicle;
	}
	public void setoVehicle(OutVehicle oVehicle) {
		this.oVehicle = oVehicle;
	}
	public int getTransfer() {
		return transfer;
	}
	public void setTransfer(int transfer) {
		this.transfer = transfer;
	}
	
	
}
