package team5.BW5.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team5.BW5.entities.Address;
import team5.BW5.payloads.AddressDTO;
import team5.BW5.repositories.AddressDAO;

@Service
public class AddressService {

    @Autowired
    private AddressDAO aDAO;

    @Autowired
    private TownService townService;

    @Autowired
    private ClientService clientService;

    public Address save(AddressDTO payload) {

        Address address = new Address();
        address.setAddress(payload.address());
        address.setAddressNumber(payload.addressNumber());
        address.setZipcode(payload.zipcode());
        address.setTown(townService.findTownIdByTownName(payload.townName()));
        address.setClient(clientService.findById(payload.clientId()));
        address.setLocation(payload.townName() + " - " + payload.zipcode());

        return aDAO.save(address);
    }


    public Page<Address> getAllAddress(int page, int size, String sort_by) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort_by).ascending());
        return this.aDAO.findAll(pageable);
    }

}
