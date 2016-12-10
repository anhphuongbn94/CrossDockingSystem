package com.cds.entity.product;

public class ProductTransform {
	private long id;
	private ProductInVehicle piv;
	private ProductOutVehicle pov;
	
	public ProductTransform() {
		super();
	}
	public ProductTransform(long id, ProductInVehicle piv, ProductOutVehicle pov) {
		super();
		this.id = id;
		this.piv = piv;
		this.pov = pov;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ProductInVehicle getPiv() {
		return piv;
	}
	public void setPiv(ProductInVehicle piv) {
		this.piv = piv;
	}
	public ProductOutVehicle getPov() {
		return pov;
	}
	public void setPov(ProductOutVehicle pov) {
		this.pov = pov;
	}
	
	
	
}
