package com.example.springboot.controllers;

import com.example.springboot.models.HistoricModel;
import com.example.springboot.repositories.HistoricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HistoricController {
	
	@Autowired
	HistoricRepository historicRepository;
	
	@GetMapping("/historics")
	public ResponseEntity<List<HistoricModel>> getAll(){
		List<HistoricModel> historicList = historicRepository.findAll();
		if(!historicList.isEmpty()) {
			for(HistoricModel historic : historicList) {
				Integer id = historic.getId();
				historic.add(linkTo(methodOn(HistoricController.class).getOne(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(historicList);
	}

	@GetMapping("/historics/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value="id") Integer id){
		Optional<HistoricModel> historicO = historicRepository.findById(id);
		if(historicO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Historico não encontrada.");
		}
		historicO.get().add(linkTo(methodOn(HistoricController.class).getAll()).withRel("Lista"));
		return ResponseEntity.status(HttpStatus.OK).body(historicO.get());
	}
	

}
