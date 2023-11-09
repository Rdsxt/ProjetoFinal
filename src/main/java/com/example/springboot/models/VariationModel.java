package com.example.springboot.models;

import java.io.Serializable;

import jakarta.persistence.*;

import org.springframework.hateoas.RepresentationModel;


@Entity
@Table(name = "TB_VARIATIONS")
public class VariationModel extends RepresentationModel<VariationModel> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	
	
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
	
}
