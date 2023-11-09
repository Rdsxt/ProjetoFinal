package com.example.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.models.VariationModel;

@Repository
public interface VariationRepository extends JpaRepository<VariationModel, Integer>{

}
