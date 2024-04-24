package team5.BW5.payloads;

import team5.BW5.entities.Client;
import team5.BW5.entities.Town;

public record AddressDTORequest(String address, String addressNumber, String zipcode, String location, Long town_id, Long client_id) {
}
