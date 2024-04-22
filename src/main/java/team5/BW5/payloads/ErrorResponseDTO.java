package team5.BW5.payloads;

import java.time.LocalDateTime;

public record ErrorResponseDTO(String message, LocalDateTime timestamp) {
}
