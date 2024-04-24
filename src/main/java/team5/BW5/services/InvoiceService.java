package team5.BW5.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team5.BW5.entities.Client;
import team5.BW5.entities.Invoice;
import team5.BW5.exceptions.NotFoundException;
import team5.BW5.payloads.InvoiceRequestDTO;
import team5.BW5.repositories.ClientDAO;
import team5.BW5.repositories.InvoiceDAO;

import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    InvoiceDAO invoiceDAO;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientDAO clientDAO;

    //FINDING By ID
    public Invoice findById(long id){
        return this.invoiceDAO.findById(id).orElseThrow(()->new NotFoundException("invoice n. "+id+" does not exist!"));
    }

    //SAVE
    public Invoice save(InvoiceRequestDTO payload){
        Invoice invoice= new Invoice(payload.date(),payload.amount(),payload.state());
        return this.invoiceDAO.save(invoice);
    }

    //DELETE
    public void delete(long id){
       Invoice currentInvoice=this.findById(id);
       this.invoiceDAO.delete(currentInvoice);
    }

    //FIND ALL
    public Page<Invoice>getInvoices(int page,int size,String sort_by){

        Pageable pageable= PageRequest.of(page,size, Sort.by(sort_by));
        return this.invoiceDAO.findAll(pageable);
    }

    // Update
    //qui si potrebbe settare il client e cambiare lo state
    public Invoice updateInvoice(long id, String clientId, String newState) {
        Optional<Invoice> optionalInvoice = this.invoiceDAO.findById(id);
        if(optionalInvoice.isEmpty()){
            throw new NotFoundException("invoice with "+id +" does not exist");
        }
        Invoice existingInvoice= optionalInvoice.get();

        Optional<Client> optionalClient = this.clientDAO.findById(Long.valueOf(clientId));
        if(optionalClient.isEmpty()){
            throw  new NotFoundException("client with "+ id +" does not exist");
        }
        Client existingClient=optionalClient.get();

        //Se c'è tutto, setta il client per l'invoice
        if(clientId !=null)
        existingInvoice.setClient(existingClient);

        // se newState is provided, settalo
        if (newState != null) {
            existingInvoice.setInvoice_state(newState);
        }

        return invoiceDAO.save(existingInvoice);
    }
}
