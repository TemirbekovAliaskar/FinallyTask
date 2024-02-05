package java12.entities;

import jakarta.persistence.*;
import java12.entities.enums.HouseType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "houses")
@NoArgsConstructor
@Getter @Setter
@SequenceGenerator(name = "base_id_gen",sequenceName = "house_seq",allocationSize = 1)
public class House extends BaseEntity {

    private HouseType houseType;
    private BigDecimal price;
    private Double rating;
    private String description;
    private int room;
    private boolean furniture;

    @ManyToOne
    private Owner owner;
    @OneToOne
    private RentInfo rentInfo;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    public House(HouseType houseType, BigDecimal price, Double rating, String description, int room, boolean furniture,Address address) {
        this.houseType = houseType;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.room = room;
        this.furniture = furniture;
        this.address = address;
    }

    @Override
    public String toString() {
        return "House{" +
                "houseType=" + houseType +
                ", price=" + price +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", room=" + room +
                ", furniture=" + furniture +
                "Address =" + address +
                '}';
    }
}
