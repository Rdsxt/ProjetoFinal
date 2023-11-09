package com.example.springboot.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "TB_HISTORICAL")
public class HistoricModel extends RepresentationModel<HistoricModel> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Integer iQuantity;
	private Integer fQuantity;
	@CreationTimestamp
	private Date editDate;
	@CreationTimestamp
	private Time editTime;
	
	@ManyToOne
	@JoinColumn(name = "id_item")
	private ItemModel item;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getiQuantity() {
		return iQuantity;
	}

	public void setiQuantity(Integer iQuantity) {
		this.iQuantity = iQuantity;
	}

	public Integer getfQuantity() {
		return fQuantity;
	}

	public void setfQuantity(Integer fQuantity) {
		this.fQuantity = fQuantity;
	}

	public ItemModel getItem() {
		return item;
	}

	public void setItem(ItemModel item) {
		this.item = item;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
