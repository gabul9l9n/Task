package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class TaskDto {
    @NotNull
    private String name;
    @NotNull
    private String description;
}