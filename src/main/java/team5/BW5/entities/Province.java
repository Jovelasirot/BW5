package team5.BW5.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "province")
@Data
@NoArgsConstructor
public class Province {

    @Id
    private String provinceCode;

    private String provinceName;

    private String region;

//    @JsonIgnore
//    @OneToMany(mappedBy = "province")
//    private List<Town> townList;


}
