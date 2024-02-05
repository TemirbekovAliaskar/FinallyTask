package java12.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "rent_info")
@Getter @Setter
@ToString(exclude = "agency")
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen",sequenceName = "rent_info_seq",allocationSize = 1)
public class RentInfo extends BaseEntity {
    private LocalDate checkin ;
    private LocalDate checkOut;
    @ManyToOne
    private Agency agency;
    @ManyToOne
    private Owner owner;
    @ManyToOne
    private Custumer custumer;
    @OneToOne
    private House house;
}
