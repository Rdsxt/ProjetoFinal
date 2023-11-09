package com.example.springboot.controllers;

import com.example.springboot.dtos.ItemRecordDto;
import com.example.springboot.models.HistoricModel;
import com.example.springboot.models.ItemModel;
import com.example.springboot.repositories.HistoricRepository;
import com.example.springboot.repositories.ItemRepository;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ItemController {
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	HistoricRepository historicRepository;
	
	@GetMapping("/items")
	public ResponseEntity<List<ItemModel>> getAll(@RequestParam(value="inventoryId", required = false) Integer inventoryId, 
			@RequestParam(value="variationId", required = false) Integer variationId,
			@RequestParam(value="variationValue", required = false) String variationValue){
		
		List<ItemModel> itensList;
		
		if(inventoryId != null) {
			itensList = itemRepository.findByInventoryId(inventoryId);
		}else if(variationId != null){
			itensList = itemRepository.findByVariationId(variationId);
		}else if(variationValue != null){
			itensList = itemRepository.findByvariationValue(variationValue);
		}  else {
			itensList = itemRepository.findAll();
		}
		
		if(!itensList.isEmpty()) {
			for(ItemModel item : itensList) {
				Integer id = item.getId();
				item.add(linkTo(methodOn(ItemController.class).getOne(id)).withSelfRel());
			}
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(itensList);
	}
	
	@GetMapping("/items/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value="id") Integer id){
		Optional<ItemModel> productO = itemRepository.findById(id);
		if(productO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado");
		}
		productO.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Lista"));
		return ResponseEntity.status(HttpStatus.OK).body(productO.get());
	}
	
	@PostMapping("/items")
	public ResponseEntity<ItemModel> save(@RequestBody @Valid ItemRecordDto itemRecordDto) {
		var itemModel = new ItemModel();
		
		BeanUtils.copyProperties(itemRecordDto, itemModel);
		
		var itemSaved = itemRepository.save(itemModel);
		var historicModel = new HistoricModel();
		historicModel.setfQuantity(itemSaved.getQuantity());
		historicModel.setiQuantity(0);
		historicModel.setItem(itemSaved);
		
		historicRepository.save(historicModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(itemSaved);	
	}
	
	@PutMapping("/items/{id}")
	public ResponseEntity<Object> update(@PathVariable(value="id") Integer id,
													  @RequestBody ItemRecordDto itemRecordDto) {
		Optional<ItemModel> itemO = itemRepository.findById(id);
		if(itemO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado");
		}
		var itemModel = itemO.get();
		var qestoque = itemModel.getQuantity();
		
		
		if(itemRecordDto.discountValue() != null) {
			itemModel.setDiscountValue(itemRecordDto.discountValue());
		}
		if(itemModel.getQuantity() != null) {
			itemModel.setQuantity(itemRecordDto.quantity());
		}
		if(itemRecordDto.inventory() != null) {
			itemModel.setInventory(itemRecordDto.inventory());
		}
		if(itemRecordDto.variationValue() != null) {
			itemModel.setVariationValue(itemRecordDto.variationValue());
		}
		if(itemRecordDto.value() != null) {
			itemModel.setValue(itemRecordDto.value());
		}
		
		var itemEdited =  itemRepository.save(itemModel);
		
		if(qestoque == itemEdited.getQuantity()) {
			return ResponseEntity.status(HttpStatus.OK).body(itemEdited);
		}
		
		var historicModel = new HistoricModel();
		historicModel.setfQuantity(itemEdited.getQuantity());
		historicModel.setiQuantity(qestoque);
		historicModel.setItem(itemEdited);
		
		historicRepository.save(historicModel);
		
		return ResponseEntity.status(HttpStatus.OK).body(itemEdited);
	}
	
}
