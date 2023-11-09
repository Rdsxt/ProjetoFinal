package com.example.springboot.controllers;

import com.example.springboot.dtos.InventoryRecordDto;
import com.example.springboot.models.InventoryModel;
import com.example.springboot.repositories.InventoryRepository;
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
public class InventoryController {
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@GetMapping("/inventorys")
	public ResponseEntity<List<InventoryModel>> getAll(){
		List<InventoryModel> inventoryList = inventoryRepository.findAll();
		if(!inventoryList.isEmpty()) {
			for(InventoryModel inventory : inventoryList) {
				Integer id = inventory.getId();
				inventory.add(linkTo(methodOn(InventoryController.class).getOne(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(inventoryList);
	}

	@GetMapping("/inventorys/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value="id") Integer id){
		Optional<InventoryModel> inventoryO = inventoryRepository.findById(id);
		if(inventoryO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Historico não encontrada.");
		}
		inventoryO.get().add(linkTo(methodOn(InventoryController.class).getAll()).withRel("Lista"));
		return ResponseEntity.status(HttpStatus.OK).body(inventoryO.get());
	}
	
	@PostMapping("/inventorys")
	public ResponseEntity<InventoryModel> save(@RequestBody @Valid InventoryRecordDto inventoryRecordDto) {
		var inventoryModel = new InventoryModel();
		BeanUtils.copyProperties(inventoryRecordDto, inventoryModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(inventoryRepository.save(inventoryModel));
	}
	
	@DeleteMapping("/inventorys/{id}")
	public ResponseEntity<Object> delete(@PathVariable(value="id") Integer id) {
		Optional<InventoryModel> variationO = inventoryRepository.findById(id);
		if(variationO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Variação não encontrada");
		}
		inventoryRepository.delete(variationO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso.");
	}
	
	@PutMapping("/inventorys/{id}")
	public ResponseEntity<Object> update(@PathVariable(value="id") Integer id,
													  @RequestBody @Valid InventoryRecordDto inventoryRecordDto) {
		Optional<InventoryModel> variationO = inventoryRepository.findById(id);
		if(variationO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Historico não encontrada.");
		}
		var variationModel = variationO.get();
		BeanUtils.copyProperties(inventoryRecordDto, variationModel);
		return ResponseEntity.status(HttpStatus.OK).body(inventoryRepository.save(variationModel));
	}

}
