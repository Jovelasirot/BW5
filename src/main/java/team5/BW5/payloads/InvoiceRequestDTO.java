package team5.BW5.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record InvoiceRequestDTO(
        @NotNull(message = "Required field: date")
        LocalDate date,
        @NotNull(message = "Required field: amount")
        Double amount,
        @NotNull(message = "Required field: state")
        @Pattern(regexp = "EMITTED|IN_PROGRESS|PAYED",message="need an input on status of invoice:EMITTED|IN_PROGRESS|PAYED")
        String state
        ) {
}
