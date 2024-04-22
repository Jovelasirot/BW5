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
@JsonIgnoreProperties({"invoices","addresses"})
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
    private List <Invoice> invoices;

    @OneToMany(mappedBy = "client")
    private List<Address> addresses;
}
