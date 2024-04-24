package team5.BW5.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team5.BW5.entities.Client;
import team5.BW5.exceptions.BadRequestException;
import team5.BW5.exceptions.NotFoundException;
import team5.BW5.payloads.ClientDTO;
import team5.BW5.repositories.ClientDAO;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class ClientService {
    @Autowired
    ClientDAO clientDAO;
    @Autowired
    Cloudinary cloudinary;

    //FIND BY ID
    public Client findById(long id) {
        return this.clientDAO.findById(id).orElseThrow(() -> new NotFoundException("client with " + id + " not found"));

    }

    //FIND BY EMAIL
    public Client findByEmail(String email) {
        return this.clientDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("client with " + email + " not found"));
    }

    //FIND ALL
    public Page<Client> getAllClients(int page, int size, String sort_by) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort_by).ascending());
        return this.clientDAO.findAll(pageable);
    }

    //SAVE
    public Client save(ClientDTO payload) throws BadRequestException {
        this.clientDAO.findByEmail(payload.email()).ifPresent(
                client -> {
                    throw new BadRequestException("the email " + client.getEmail() + " is already in the system");
                }
        );
        Client client = new Client(payload.email(), payload.businessName(), payload.pIva(), payload.phone(), payload.annualTurnover(),
                payload.contactName(), payload.contactSurname(), payload.contactEmail(), payload.phone(), LocalDate.now(), LocalDate.parse(payload.lastContact()));
        return this.clientDAO.save(client);
    }

    //DELETE
    public void delete(long id) {
        Client found = this.findById(id);
        this.clientDAO.delete(found);
    }

    //UPDATE COMPANY_LOGO
    public String uploadLogo(long id, MultipartFile img) throws IOException {
        Client client = findById(id);
        String url = (String) cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap()).get("url");
        client.setLogo_URL(url);
        this.clientDAO.save(client);
        return url;
    }

    //GENERIC UPDATE
    public Client update(Long clientId, Client updatedClient) {
        Client clientFound = clientDAO.findById(clientId)
                .orElseThrow(() -> new NotFoundException(clientId));

        clientFound.setBusinessName(updatedClient.getBusinessName());
        clientFound.setPIVA(updatedClient.getPIVA() == null ? clientFound.getPIVA() : updatedClient.getPIVA());
        clientFound.setEmail(updatedClient.getEmail() == null ? clientFound.getEmail() : updatedClient.getEmail());
        clientFound.setPhone(updatedClient.getPhone() == -1 ? clientFound.getPhone() : updatedClient.getPhone());
        clientFound.setPec(updatedClient.getPec() == null ? clientFound.getPec() : updatedClient.getPec());
        clientFound.setStartingDate(updatedClient.getStartingDate() == null ? clientFound.getStartingDate() : updatedClient.getStartingDate());
        clientFound.setLastContact(updatedClient.getLastContact() == null ? clientFound.getLastContact() : updatedClient.getLastContact());
        clientFound.setAnnualTurnover(updatedClient.getAnnualTurnover() == -1 ? clientFound.getAnnualTurnover() : updatedClient.getAnnualTurnover());
        clientFound.setContactName(updatedClient.getContactName() == null ? clientFound.getContactName() : updatedClient.getContactName());
        clientFound.setContactSurname(updatedClient.getContactSurname() == null ? clientFound.getContactSurname() : updatedClient.getContactSurname());
        clientFound.setContactPhone(updatedClient.getPhone() == -1 ? clientFound.getContactPhone() : updatedClient.getContactPhone());

        return clientDAO.save(clientFound);
    }
}
