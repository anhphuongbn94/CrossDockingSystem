package com.cds.entity.product;

import com.cds.entity.vehicle.OutVehicle;

public class ProductOutVehicle {
	private Long id;
	private OutVehicle ov;
	private Product p;
	private Unit u;
	private int quantity;
	
	public ProductOutVehicle() {
		super();
	}
	public ProductOutVehicle(Long id, OutVehicle ov, Product p, Unit u, int quantity) {
		super();
		this.id = id;
		this.ov = ov;
		this.p = p;
		this.u = u;
		this.quantity = quantity;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public OutVehicle getOv() {
		return ov;
	}
	public void setOv(OutVehicle ov) {
		this.ov = ov;
	}
	public Product getP() {
		return p;
	}
	public void setP(Product p) {
		this.p = p;
	}
	public Unit getU() {
		return u;
	}
	public void setU(Unit u) {
		this.u = u;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
