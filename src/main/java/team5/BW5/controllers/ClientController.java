package team5.BW5.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team5.BW5.entities.Client;
import team5.BW5.exceptions.BadRequestException;
import team5.BW5.payloads.ClientDTO;
import team5.BW5.payloads.ClientResponseDTO;
import team5.BW5.services.ClientService;

import java.io.IOException;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ClientResponseDTO saveClient(@RequestBody @Validated ClientDTO payload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return new ClientResponseDTO(this.clientService.save(payload).getId());
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Client> getAllClient(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.clientService.getClients(page, size, sortBy);
    }

    @PostMapping("/logo/upload/{clientId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ClientResponseDTO uploadLogo(@PathVariable Long clientId, @RequestParam("image") MultipartFile image) throws IOException {
        this.clientService.uploadLogo(clientId, image);
        return new ClientResponseDTO(clientId);
    }

    @PutMapping("{clientId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Client updateClient(@PathVariable Long clientId, @RequestBody Client payload) {
        return this.clientService.update(clientId, payload);
    }

    @DeleteMapping("{clientId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long clientId) {
        this.clientService.delete(clientId);
    }

}
