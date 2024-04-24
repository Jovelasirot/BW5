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
    private ClientDAO cDAO;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Client save(ClientDTO payload) throws BadRequestException {

        this.cDAO.findByEmail(payload.email()).ifPresent(
                user -> {
                    throw new BadRequestException("The email: " + user.getEmail() + " is already being used (ᗒᗣᗕ)՞");
                }
        );


        Client newClient = new Client();
        newClient.setBusinessName(payload.businessName());
        newClient.setPIVA(payload.pIva());
        newClient.setLogo_URL("https://ui-avatars.com/api/?name=" + payload.contactName() + "+" + payload.contactSurname());
        newClient.setEmail(payload.email());
        newClient.setPhone(Integer.parseInt(payload.phone()));
        newClient.setPec(payload.pec());
        newClient.setStartingDate(LocalDate.parse(payload.startingDate()));
        newClient.setLastContact(LocalDate.parse(payload.lastContact()));
        newClient.setAnnualTurnover(payload.annualTurnover());
        newClient.setContactName(payload.contactName());
        newClient.setContactSurname(payload.contactSurname());
        newClient.setContactPhone(payload.contactPhone());
        newClient.setContactEmail(payload.contactEmail());

        return cDAO.save(newClient);
    }

    public Page<Client> getClients(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return cDAO.findAll(pageable);
    }

    public Client findById(Long clientId) {
        return cDAO.findById(clientId)
                .orElseThrow(() -> new NotFoundException(clientId));
    }

    public Client update(Long clientId, Client updatedClient) {
        Client clientFound = cDAO.findById(clientId)
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

        return cDAO.save(clientFound);
    }

    public void delete(Long clientId) {
        Client client = cDAO.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found with ID: " + clientId));

        cDAO.delete(client);
    }

    public String uploadLogo(Long clientId, MultipartFile image) throws IOException {
        Client client = findById(clientId);

        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");

        client.setLogo_URL(url);
        cDAO.save(client);
        return url;
    }

}
