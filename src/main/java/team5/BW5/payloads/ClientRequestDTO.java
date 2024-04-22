package team5.BW5.payloads;

import jakarta.validation.constraints.NotEmpty;

public record ClientRequestDTO(
        @NotEmpty(message = "Required field: business_name")
        String business_name,
        @NotEmpty(message = "Required field: p_IVA")
        String p_IVA,
        @NotEmpty(message = "Required field: email of the company")
        String email,
        @NotEmpty(message = "Required field: phone of the company")
        Integer phone,
        @NotEmpty(message = "Required field: annual turnover of the company")
        double annual_turnover,
        @NotEmpty(message = "Required field: contact name for the company")
        String contactName,
        @NotEmpty(message = "Required field: contact surname for the company")
        String contactSurname,
        @NotEmpty(message = "Required field: contact email for the company")
        String contactEmail,
        @NotEmpty(message = "Required field: contact phone for the company")
        String contactPhone

) {
}
