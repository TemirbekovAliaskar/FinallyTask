package java12.service;

import java12.entities.House;
import java12.entities.Owner;

import java.time.LocalDate;
import java.util.List;

public interface HouseService {

    String saveHouse(House house);
    String saveHouse(Owner owner, List<House> house);

    House getHouseById(Long houseId);

    List<House> getAllHouse();

    House update(Long oldHouse, House newHouse);

    String deleteHouse(Long houseId);
    List<House> getRegion(String region);
    List<House> getAgencyHouses(Long agencyId);
    List<House> getOwnerHouses(Long id);
    List<House> getCheck(LocalDate checkin, LocalDate checkout);
}
