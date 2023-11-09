package com.example.springboot.controllers;

import com.example.springboot.dtos.VariationRecordDto;
import com.example.springboot.models.VariationModel;
import com.example.springboot.repositories.VariationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class VariationController {
	
	@Autowired
	VariationRepository variationRepository;
	
	@GetMapping("/variations")
	public ResponseEntity<List<VariationModel>> getAll(){
		List<VariationModel> variationsList = variationRepository.findAll();
		if(!variationsList.isEmpty()) {
			for(VariationModel variation : variationsList) {
				Integer id = variation.getId();
				variation.add(linkTo(methodOn(VariationController.class).getOne(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(variationsList);
	}

	@GetMapping("/variations/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value="id") Integer id){
		Optional<VariationModel> variationO = variationRepository.findById(id);
		if(variationO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Variação não encontrada.");
		}
		variationO.get().add(linkTo(methodOn(VariationController.class).getAll()).withRel("Lista"));
		return ResponseEntity.status(HttpStatus.OK).body(variationO.get());
	}
	
	@PostMapping("/variations")
	public ResponseEntity<VariationModel> save(@RequestBody @Valid VariationRecordDto variationRecordDto) {
		var variationModel = new VariationModel();
		BeanUtils.copyProperties(variationRecordDto, variationModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(variationRepository.save(variationModel));
	}
	
	@DeleteMapping("/variations/{id}")
	public ResponseEntity<Object> delete(@PathVariable(value="id") Integer id) {
		Optional<VariationModel> variationO = variationRepository.findById(id);
		if(variationO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Variação não encontrada");
		}
		variationRepository.delete(variationO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso.");
	}
	
	@PutMapping("/variations/{id}")
	public ResponseEntity<Object> update(@PathVariable(value="id") Integer id,
													  @RequestBody @Valid VariationRecordDto variationRecordDto) {
		Optional<VariationModel> variationO = variationRepository.findById(id);
		if(variationO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Variação não encontrada.");
		}
		var variationModel = variationO.get();
		BeanUtils.copyProperties(variationRecordDto, variationModel);
		return ResponseEntity.status(HttpStatus.OK).body(variationRepository.save(variationModel));
	}

}
