package org.example.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class Task {
    private static int taskId;

    @Min(1)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastModificationDate;

    public Task(String name, String description) {
        this.id = ++taskId;
        this.name = name;
        this.description = description;
        this.lastModificationDate = LocalDateTime.now();
    }
}
