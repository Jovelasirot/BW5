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
        newClient.setBusiness_name(payload.businessName());
        newClient.setP_IVA(payload.pIva());
        newClient.setLogo_URL("https://ui-avatars.com/api/?name=" + payload.contactName() + "+" + payload.contactSurname());
        newClient.setEmail(payload.email());
        newClient.setPhone(Integer.parseInt(payload.phone()));
        newClient.setPec(payload.pec());
        newClient.setStarting_date(LocalDate.parse(payload.startingDate()));
        newClient.setLast_contact(LocalDate.parse(payload.lastContact()));
        newClient.setAnnual_turnover(payload.annualTurnover());
        newClient.setContactName(payload.contactName());
        newClient.setContactSurname(payload.contactSurname());
        newClient.setContactPhone(payload.contactPhone());
        newClient.setContactEmail(payload.contactEmail());

        return cDAO.save(newClient);
    }

    public Page<Client> getClients(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return cDAO.findAll(pageable);
    }

    public Client findById(Long clientId) {
        return cDAO.findById(clientId)
                .orElseThrow(() -> new NotFoundException(clientId));
    }

    public Client update(Long clientId, Client updatedClient) {
        Client clientFound = cDAO.findById(clientId)
                .orElseThrow(() -> new NotFoundException(clientId));

        clientFound.setBusiness_name(updatedClient.getBusiness_name());
        clientFound.setP_IVA(updatedClient.getP_IVA() == null ? clientFound.getP_IVA() : updatedClient.getP_IVA());
        clientFound.setEmail(updatedClient.getEmail() == null ? clientFound.getEmail() : updatedClient.getEmail());
        clientFound.setPhone(updatedClient.getPhone() == -1 ? clientFound.getPhone() : updatedClient.getPhone());
        clientFound.setPec(updatedClient.getPec() == null ? clientFound.getPec() : updatedClient.getPec());
        clientFound.setStarting_date(updatedClient.getStarting_date() == null ? clientFound.getStarting_date() : updatedClient.getStarting_date());
        clientFound.setLast_contact(updatedClient.getLast_contact() == null ? clientFound.getLast_contact() : updatedClient.getLast_contact());
        clientFound.setAnnual_turnover(updatedClient.getAnnual_turnover() == -1 ? clientFound.getAnnual_turnover() : updatedClient.getAnnual_turnover());
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
