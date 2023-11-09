package com.example.springboot.models;

import java.io.Serializable;

import jakarta.persistence.*;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "TB_ITENS")
public class ItemModel extends RepresentationModel<ItemModel> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String variationValue;
	private Integer quantity;
	private Float value;
	private Float discountValue;
	
	@ManyToOne
	@JoinColumn(name = "id_location")
	private InventoryModel inventory;
	
	@ManyToOne
	@JoinColumn(name = "id_product")
	private ProductModel product;
	
	@ManyToOne
	@JoinColumn(name = "id_variation")
	private VariationModel variation;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public InventoryModel getInventory() {
		return inventory;
	}
	public void setInventory(InventoryModel inventory) {
		this.inventory = inventory;
	}
	public String getVariationValue() {
		return variationValue;
	}
	public void setVariationValue(String variationValue) {
		this.variationValue = variationValue;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}
	public Float getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(Float discountValue) {
		this.discountValue = discountValue;
	}
	public ProductModel getProduct() {
		return product;
	}
	public void setProduct(ProductModel product) {
		this.product = product;
	}
	public VariationModel getVariation() {
		return variation;
	}
	public void setVariation(VariationModel variation) {
		this.variation = variation;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
