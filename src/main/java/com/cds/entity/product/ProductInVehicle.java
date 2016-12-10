package com.cds.entity.product;

import com.cds.entity.vehicle.InVehicle;

public class ProductInVehicle {
	private Long id;
	private InVehicle iv;
	private Product p;
	private Unit u;
	private int quantity;
	
	public ProductInVehicle() {
		super();
	}
	public ProductInVehicle(Long id, InVehicle iv, Product p, Unit u, int quantity) {
		super();
		this.id = id;
		this.iv = iv;
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
	public InVehicle getIv() {
		return iv;
	}
	public void setIv(InVehicle iv) {
		this.iv = iv;
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
