package com.example.springboot.dtos;

import com.example.springboot.models.InventoryModel;
import com.example.springboot.models.ProductModel;
import com.example.springboot.models.VariationModel;

import jakarta.validation.constraints.NotNull;

public record ItemRecordDto(@NotNull InventoryModel inventory, @NotNull String variationValue,@NotNull Integer quantity,
		@NotNull Float value, @NotNull Float discountValue,@NotNull ProductModel product, @NotNull VariationModel variation) {
}
