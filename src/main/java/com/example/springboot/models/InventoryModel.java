package com.example.springboot.models;

import java.io.Serializable;

import jakarta.persistence.*;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "TB_INVENTORY")
public class InventoryModel extends RepresentationModel<InventoryModel> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private String location;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
