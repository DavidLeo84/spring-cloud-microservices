package co.edu.uniquindio.dtos;

public record MessageDTO<T>(

        boolean error,
        T message
) {
}
