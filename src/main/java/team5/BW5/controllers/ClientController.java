package team5.BW5.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team5.BW5.exceptions.BadRequestException;
import team5.BW5.payloads.ClientRequestDTO;
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
    public ClientRespDTO saveClient(@RequestBody @Validated ClientRequestDTO payload, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return new ClientRespDTO(this.clientService.save(payload).getId());
    }
    //http://localhost:3001/clients/company_logo/{id}
    @PostMapping("/company_logo/{id}")
    public ClientRespDTO uploadCompanyLogo(@PathVariable Long id, @RequestParam("img")MultipartFile img)throws IOException{
        this.clientService.uploadLogo(id,img);
        return new ClientRespDTO(id);
    }

}
