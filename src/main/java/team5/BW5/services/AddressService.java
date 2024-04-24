package team5.BW5.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import team5.BW5.entities.Address;
import team5.BW5.entities.Client;
import team5.BW5.exceptions.BadRequestException;
import team5.BW5.exceptions.NotFoundException;
import team5.BW5.payloads.AddressDTORequest;
import team5.BW5.repositories.AddressDAO;

public class AddressService {
    @Autowired
    private AddressDAO addressDAO;

    public Page<Address> getAddress (int page, int size, String sortBy){
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.addressDAO.findAll(pageable);
    }

    public Address findById(long id){
        return this.addressDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Address save(AddressDTORequest payload) throws BadRequestException {
        Address address = new Address(payload.a)
    }


    public void delete(long id){
        Address found = this.findById(id);
        addressDAO.delete(found);
    }
}
