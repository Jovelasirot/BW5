package team5.BW5.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"invoices","addresses"})
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
    private List <Invoice> invoices;

    @OneToMany(mappedBy = "client")
    private List<Address> addresses;

    public Client(String email, String business_name, String p_IVA, int phone, double annual_turnover, String contactName, String contactSurname, String contactEmail, int contactPhone,LocalDate starting_date) {
        this.email = email;
        this.businessName = business_name;
        this.pIVA= p_IVA;
        this.phone = phone;
        this.annualTurnover = annual_turnover;
        this.contactName = contactName;
        this.contactSurname = contactSurname;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.startingDate=LocalDate.now();
    }
    public Client(long id){
        this.id=id;
    }

}
