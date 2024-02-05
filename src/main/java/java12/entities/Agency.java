package java12.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.util.List;

@Entity
@Table(name = "agencies")
@Getter @Setter
@NoArgsConstructor
@ToString
@SequenceGenerator(name = "base_id_gen",sequenceName = "agency_seq",allocationSize = 1)
public class Agency extends BaseEntity{
    private String name;
    private String phoneNumber;

    @OneToOne
    private Address address;
    @OneToMany(mappedBy = "agency")
    @ToString.Exclude
    private List<RentInfo> rentInfos;
    @ManyToMany()
    @ToString.Exclude
    private List<Owner> owners;

    public Agency(String name, String phoneNumber, Address address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }


}
