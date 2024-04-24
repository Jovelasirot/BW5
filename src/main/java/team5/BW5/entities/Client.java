package team5.BW5.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"invoices", "addresses"})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String business_name;
    private String p_IVA;
    private String logo_URL;
    private String email;
    private int phone;
    private String pec;
    private LocalDate starting_date;
    private LocalDate last_contact;
    private double annual_turnover;
    private String contactName;
    private String contactSurname;
    private String contactEmail;
    private int contactPhone;

    @OneToMany(mappedBy = "client")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "client")
    private List<Address> addresses;

    public Client(String business_name, String p_IVA, String logo_URL, String email, int phone, String pec, LocalDate starting_date, LocalDate last_contact, int annual_turnover, String contactName, String contactSurname, String contactEmail, int contactPhone, List<Invoice> invoices, List<Address> addresses) {
        this.business_name = business_name;
        this.p_IVA = p_IVA;
        this.logo_URL = logo_URL;
        this.email = email;
        this.phone = phone;
        this.pec = pec;
        this.starting_date = starting_date;
        this.last_contact = last_contact;
        this.annual_turnover = annual_turnover;
        this.contactName = contactName;
        this.contactSurname = contactSurname;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.invoices = invoices;
        this.addresses = addresses;
    }
}
