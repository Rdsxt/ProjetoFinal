package com.example.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.models.HistoricModel;

@Repository
public interface HistoricRepository extends JpaRepository<HistoricModel, Integer>{

}
