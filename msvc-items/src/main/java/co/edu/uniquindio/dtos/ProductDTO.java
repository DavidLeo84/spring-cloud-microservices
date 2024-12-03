package co.edu.uniquindio.dtos;

import java.time.LocalDate;

public record ProductDTO(

        Long id,
        String name,
        Double price,
        LocalDate createAt
) {
}
