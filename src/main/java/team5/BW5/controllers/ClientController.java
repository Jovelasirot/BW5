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
import team5.BW5.payloads.ClientRespDTO;
import team5.BW5.services.ClientService;

import java.io.IOException;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    ClientService clientService;

    //http://localhost:3001/clients
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientRespDTO saveClient(@RequestBody @Validated ClientDTO payload, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return new ClientRespDTO(this.clientService.save(payload).getId());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Client> getClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "id") String sort_by
    ) {
        return this.clientService.getAllClients(page, size, sort_by);
    }

    //http://localhost:3001/clients/logo/{id}
    @PostMapping("/logo/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientRespDTO uploadCompanyLogo(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
        this.clientService.uploadLogo(id, image);
        return new ClientRespDTO(id);
    }

    //http://localhost:3001/clients/{id}
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Client getCompanyById(@PathVariable Long id) {
        Client found = this.clientService.findById(id);
        return found;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        this.clientService.delete(id);
    }

    @PutMapping("{clientId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Client updateClient(@PathVariable Long clientId, @RequestBody Client payload) {
        return this.clientService.update(clientId, payload);
    }

}
