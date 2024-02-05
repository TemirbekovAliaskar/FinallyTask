package java12.entities;


import jakarta.persistence.*;
import java12.entities.enums.FamilyStatus;
import java12.entities.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "custumers")
@Data
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen",sequenceName = "custumer_seq",allocationSize = 1)
public class Custumer extends BaseEntity{
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String nationality;
    private FamilyStatus familyStatus;
    @OneToMany(mappedBy = "custumer")
    @ToString.Exclude
    private List<RentInfo> rentInfos;

    public Custumer(String firstName, String lastName, String email, LocalDate dateOfBirth, Gender gender, String nationality, FamilyStatus familyStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.familyStatus = familyStatus;
    }
}
