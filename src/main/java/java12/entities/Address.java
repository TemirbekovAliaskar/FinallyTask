package java12.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter @Setter
@NoArgsConstructor
@ToString(exclude = "agency")
@SequenceGenerator(name = "base_id_gen",sequenceName = "address_seq",allocationSize = 1)
public class Address extends BaseEntity{
    private String city;
    private String region;
    @Column(unique = true)
    private String street;

    @OneToOne(mappedBy = "address", cascade = {CascadeType.REMOVE})
    private Agency agency;

    public Address(String city, String region, String street) {
        this.city = city;
        this.region = region;
        this.street = street;
    }
}
