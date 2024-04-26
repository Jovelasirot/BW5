package team5.BW5.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team5.BW5.entities.Address;
import team5.BW5.exceptions.BadRequestException;
import team5.BW5.payloads.AddressDTO;
import team5.BW5.payloads.AddressResponseDTO;
import team5.BW5.services.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponseDTO saveAddress(@RequestBody @Validated AddressDTO payload, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return new AddressResponseDTO(this.addressService.save(payload).getId());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Address> getAddresses(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "15") int size,
                                      @RequestParam(defaultValue = "id") String sort_by) {
        return this.addressService.getAllAddress(page, size, sort_by);
    }
}
