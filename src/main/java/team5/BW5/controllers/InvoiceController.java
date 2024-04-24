package team5.BW5.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team5.BW5.entities.Invoice;
import team5.BW5.exceptions.BadRequestException;
import team5.BW5.payloads.InvoiceRequestDTO;
import team5.BW5.payloads.InvoiceRespDTO;
import team5.BW5.services.InvoiceService;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    //http://localhost:3001/invoices
    @GetMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<Invoice> getInvoices(@RequestParam(defaultValue = "0")int page,
                                     @RequestParam(defaultValue ="15" ) int size,
                                     @RequestParam(defaultValue = "id") String sort_by){
        return this.invoiceService.getInvoices(page, size, sort_by);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceRespDTO saveInvoice(@RequestBody @Validated InvoiceRequestDTO payload, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return new InvoiceRespDTO(this.invoiceService.save(payload).getId());
    }
    //http://localhost:3001/invoices/id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable Long id){
        this.invoiceService.delete(id);
    }
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Invoice getInvoiceById(@PathVariable Long id){
        return this.invoiceService.findById(id);
    }
    @PutMapping("/{id}")
    public  Invoice updateInvoice(@PathVariable long id, @RequestBody Invoice updatedInvoice){
        return this.invoiceService.updateInvoice(id, String.valueOf(updatedInvoice.getClient().getId()), updatedInvoice.getInvoice_state());
    }

}
