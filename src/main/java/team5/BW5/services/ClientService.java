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
import team5.BW5.payloads.ClientRequestDTO;
import team5.BW5.repositories.ClientDAO;
import java.io.IOException;

@Service
public class ClientService {
    @Autowired
    ClientDAO clientDAO;
    @Autowired
    Cloudinary cloudinary;

    //FIND BY ID
    public Client findById(long id){
        return this.clientDAO.findById(id).orElseThrow(()->new NotFoundException("client with "+id+" not found"));

    }
    //FIND BY EMAIL
    public Client findByEmail(String email){
        return this.clientDAO.findByEmail(email).orElseThrow(()->new NotFoundException("client with "+email+" not found"));
    }
    //FIND ALL
    public Page<Client>getAllClients(int page,int size,String sort_by){
        Pageable pageable= PageRequest.of(page,size, Sort.by(sort_by));
        return this.clientDAO.findAll(pageable);
    }

    //SAVE
    public Client save (ClientRequestDTO payload) throws BadRequestException{
        this.clientDAO.findByEmail(payload.email()).ifPresent(
                client->{throw new BadRequestException("the email "+client.getEmail()+" is already in the system");}
        );
        Client client=new Client(payload.email(), payload.business_name(), payload.p_IVA(), payload.phone(), payload.annual_turnover(), payload.contactName(), payload.contactSurname(), payload.contactEmail(), payload.phone());
        return this.clientDAO.save(client);
    }
    //DELETE
    public void delete (long id){
        Client found =this.findById(id);
        this.clientDAO.delete(found);
    }
    //UPDATE COMPANY_LOGO
    public String uploadLogo(long id, MultipartFile img)throws IOException {
        Client client=findById(id);
        String url= (String) cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap()).get("url");
        client.setLogo_URL(url);
        this.clientDAO.save(client);
        return url;
    }
}
