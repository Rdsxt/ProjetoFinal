package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;

public record VariationRecordDto(@NotBlank String name) {
}
