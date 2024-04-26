package team5.BW5.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Address {
    //ATTRIBUTES LIST:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    private String addressNumber;
    private String zipcode;
    private String location;

    @ManyToOne
    @JoinColumn
    private Town town;

    @ManyToOne
    @JoinColumn
    private Client client;

    //to create method to return complete location


    public Address(String address, String addressNumber, String zipcode, String location, Town town, Client client) {
        this.address = address;
        this.addressNumber = addressNumber;
        this.zipcode = zipcode;
        this.location = location;
        this.town = town;
        this.client = client;
    }
}
