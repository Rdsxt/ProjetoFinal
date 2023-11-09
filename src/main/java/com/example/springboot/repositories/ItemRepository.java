package com.example.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.models.ItemModel;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Integer>{
	
	List<ItemModel> findByInventoryId(Integer id);
	List<ItemModel> findByVariationId(Integer id);
	List<ItemModel> findByvariationValue(String value);
}
