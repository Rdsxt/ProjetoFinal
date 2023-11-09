package com.example.springboot.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.models.InventoryModel;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryModel, Integer>{
}
