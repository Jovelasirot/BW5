package team5.BW5.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "town")
@Data
@NoArgsConstructor
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    private String provinceId;

    @Id
    private String townId;

    private String townName;

    private String provinceName;


    @JsonIgnore
    @OneToMany(mappedBy = "town")
    private List<Address> addressList; // importare classe address quando presente in Entities

    public Town(String provinceId, String townId, String townName, String provinceName) {
        this.provinceId = provinceId;
        this.townId = townId;
        this.townName = townName;
        this.provinceName = provinceName;
    }
}
