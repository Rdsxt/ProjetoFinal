package com.example.springboot.dtos;

import com.example.springboot.models.ItemModel;
import jakarta.validation.constraints.NotNull;

public record HistoricRecordDto(@NotNull Integer iQuantity, @NotNull Integer fQuantity,
		@NotNull ItemModel item) {
}
