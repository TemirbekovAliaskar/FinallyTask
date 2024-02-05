package java12.entities;

import jakarta.persistence.*;
import java12.entities.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "owners")
@Getter @Setter
@NoArgsConstructor
@ToString
@SequenceGenerator(name = "base_id_gen",sequenceName = "owner_seq",allocationSize = 1)
public class Owner extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private Gender gender;

    @ManyToMany(mappedBy ="owners",cascade = CascadeType.REMOVE)
    private List<Agency> agencies;
    @OneToMany(mappedBy = "owner",cascade = CascadeType.REMOVE)
    private List<RentInfo> rentInfos;
    @OneToMany(mappedBy = "owner",cascade = CascadeType.REMOVE)
    private List<House> houses;

    public Owner(String firstName, String lastName, String email, LocalDate dateOfBirth, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                '}';
    }
}
