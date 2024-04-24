package team5.BW5.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"invoices", "addresses"})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String businessName;
    private String pIVA;
    private String logo_URL;
    private String email;
    private int phone;
    private String pec;
    private LocalDate startingDate;
    private LocalDate lastContact;
    private double annualTurnover;
    private String contactName;
    private String contactSurname;
    private String contactEmail;
    private int contactPhone;

    @OneToMany(mappedBy = "client")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "client")
    private List<Address> addresses;


    public Client(String businessName, String pIVA, String email, int phone, String pec, LocalDate startingDate, LocalDate lastContact, double annualTurnover, String contactName, String contactSurname, String contactEmail, int contactPhone) {
        this.businessName = businessName;
        this.pIVA = pIVA;
        this.email = email;
        this.phone = phone;
        this.pec = pec;
        this.startingDate = startingDate;
        this.lastContact = lastContact;
        this.annualTurnover = annualTurnover;
        this.contactName = contactName;
        this.contactSurname = contactSurname;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.addresses=new ArrayList<>();
    }



    public Client(long id) {
        this.id = id;
    }

}
