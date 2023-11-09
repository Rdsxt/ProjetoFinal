package com.example.springboot.dtos;

import jakarta.validation.constraints.NotNull;

public record InventoryRecordDto(@NotNull String name, @NotNull String location) {
}