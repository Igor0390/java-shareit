package ru.practicum.shareit.item.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private Long id;
    @Size(max = 300)
    @NotBlank
    private String name;
    @Size(max = 1000)
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private Long requestId;
}
