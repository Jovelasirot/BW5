package team5.BW5.controllers;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import team5.BW5.entities.Address;
import team5.BW5.services.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private AddressService addressService;

    @GetMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Page<Address> getAllAddresses(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy){
        return this.addressService.getAddress(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Address findById(@PathVariable long id){
        return addressService.findById(id);
    }


}
