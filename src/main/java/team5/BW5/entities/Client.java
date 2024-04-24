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
    private List <Invoice> invoices;

    @OneToMany(mappedBy = "client")
    private List<Address> addresses;

    public Client(String email, String business_name, String p_IVA, int phone, double annual_turnover, String contactName, String contactSurname, String contactEmail, int contactPhone) {
        this.email = email;
        this.business_name = business_name;
        this.p_IVA = p_IVA;
        this.phone = phone;
        this.annual_turnover = annual_turnover;
        this.contactName = contactName;
        this.contactSurname = contactSurname;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }
    public Client(long id){
        this.id=id;
    }

}
