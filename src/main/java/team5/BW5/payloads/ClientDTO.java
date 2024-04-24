package team5.BW5.payloads;

import jakarta.validation.constraints.*;

public record ClientDTO(@NotEmpty(message = "Business name is required")
                        @Size(min = 5, message = "Business name must be more than 5")
                        String businessName,
                        @NotEmpty(message = "Partita IVA is required")
                        String pIva,
                        @NotEmpty(message = "Email is required")
                        @Email(message = "Invalid email format")
                        String email,
                        @NotNull(message = "Phone is required")
                        int phone,
                        @NotEmpty(message = "The pec is required")
                        String pec,
                        @NotEmpty(message = "The last contact date is required")
                        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format (yyyy-mm-dd)")
                        String lastContact,

                        @NotNull(message = "Contact annual turnover is required")
                        int annualTurnover,
                        @NotEmpty(message = "Contact name is required")
                        String contactName,
                        @NotEmpty(message = "Contact surname is required")
                        String contactSurname,
                        @NotEmpty(message = "Contact email is required")
                        @Email(message = "Invalid email format")
                        String contactEmail,
                        @NotNull(message = "Contact phone is required")
                        int contactPhone) {
}
