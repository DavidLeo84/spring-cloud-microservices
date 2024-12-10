package co.edu.uniquindio.msvc_users.dtos;

public record MessageDTO<T>(

        boolean error,
        T message
) {
}
