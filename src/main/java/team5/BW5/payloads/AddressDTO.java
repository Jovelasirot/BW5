package team5.BW5.payloads;

import jakarta.validation.constraints.NotEmpty;

public record AddressDTO(@NotEmpty(message = "Address is required")
                         String address,
                         @NotEmpty(message = "Address number is required")
                         String addressNumber,
                         @NotEmpty(message = "Zip code is required")
                         String zipcode,
                         String townName,
                         Long clientId
) {
}
