package com.cds.entity.product;

import com.cds.entity.vehicle.InVehicle;
import com.cds.entity.vehicle.OutVehicle;

public class ProductTransfer {
	private Integer id;
	private InVehicle iv;
	private OutVehicle ov;
	private int transfer;
	
	public ProductTransfer() {
		super();
	}
	public ProductTransfer(Integer id, InVehicle iv, OutVehicle ov, int transfer) {
		super();
		this.id = id;
		this.iv = iv;
		this.ov = ov;
		this.transfer = transfer;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public InVehicle getIv() {
		return iv;
	}
	public void setIv(InVehicle iv) {
		this.iv = iv;
	}
	public OutVehicle getOv() {
		return ov;
	}
	public void setOv(OutVehicle ov) {
		this.ov = ov;
	}
	public int getTransfer() {
		return transfer;
	}
	public void setTransfer(int transfer) {
		this.transfer = transfer;
	}
	
	
	
}
