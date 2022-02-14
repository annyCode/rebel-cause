package com.letscode.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Builder
@Data
public class Rebel{
    @NotBlank(message = "Rebelde sem nome não pode ingressa na causa.")
    private String name;
    @Min(0)@Max(30)
    private int age;

    private Race race;

}
